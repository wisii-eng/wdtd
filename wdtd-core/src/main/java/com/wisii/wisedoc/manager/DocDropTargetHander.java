/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wisii.wisedoc.manager;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.DocumentPositionUtil;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.swing.SwingUtil;
import com.wisii.wisedoc.swing.basic.BindNodeData;
import com.wisii.wisedoc.swing.basic.WSDData;
import com.wisii.wisedoc.swing.basic.WsdTransferable;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.SVGLocationConvert;
import com.wisii.wisedoc.view.WisedocDefualtCaret;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.select.SelectionModel;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class DocDropTargetHander extends DropTargetAdapter
{
	private Point startpoint = null;
	List<Shape> shapes;
	Rectangle bound;
	private Point currentpoint;

	private SelectionModel selectModel;
	private Document document;
	private final AbstractEditComponent documentPanel;
	private JComponent gridPanel;
	// 【添加：START】 by 李晓光 2009-12-31
	// 添加拖拽光标
	private WisedocDefualtCaret caret = null;

	// 【添加：END】 by 李晓光 2009-12-31
	public DocDropTargetHander(final AbstractEditComponent documentPanel)
	{
		this.documentPanel = documentPanel;
		updateInfo();
	}

	private void updateInfo()
	{
		this.selectModel = this.documentPanel.getSelectionModel();
		this.document = this.documentPanel.getDocument();
		this.gridPanel = this.documentPanel.getGridPanel();
		// 【添加：START】 by 李晓光 2009-12-31
		// 初始化拖拽光标
		if (caret == null)
		{
			caret = new WisedocDefualtCaret(documentPanel);
		}
		// 【添加：END】 by 李晓光 2009-12-31
	}

	@Override
	public void dragEnter(final DropTargetDragEvent dtde)
	{
		updateInfo();
		// 【添加：START】 by 李晓光 2010-1-4
		if (caret != null)
		{
			caret.setBindNodeSource(true);
		}
		// 【添加：END】 by 李晓光 2010-1-4
		final Object source = dtde.getSource();
		Component comp = null;
		if (source instanceof DropTarget)
		{
			comp = ((DropTarget) source).getComponent();
		}
		if (comp == documentPanel)
		{

			final AbstractEditComponent sourceeditor = (AbstractEditComponent) comp;
			final List<CellElement> selectobjects = sourceeditor
					.getSelectionModel().getObjectSelection();
			if (selectobjects != null && !selectobjects.isEmpty())
			{
				shapes = new ArrayList<Shape>();
				bound = null;
				for (final CellElement element : selectobjects)
				{
					if (element instanceof BlockContainer
							&& (((EnumProperty) (((BlockContainer) element)
									.getAttribute(Constants.PR_ABSOLUTE_POSITION)))
									.getEnum() == Constants.EN_ABSOLUTE))
					{
						final BlockContainer blockcontainer = (BlockContainer) element;
						final Area area = blockcontainer.getArea();
						if (area != null)
						{
							final Rectangle rect = sourceeditor.toScreen(area
									.getViewport(), area);
							if (rect != null)
							{
								shapes.add(rect);
								if (bound == null)
								{
									bound = rect;
								} else
								{
									bound.union(rect);
								}
							}
						}
					} else if (element instanceof AbstractSVG)
					{
						final CellElement parentelement = (CellElement) element
								.getParent();
						if (parentelement instanceof SVGContainer)
						{
							final SVGContainer svgcontainer = (SVGContainer) parentelement;
							final Area area = svgcontainer.getArea();
							if (area != null)
							{
								final Rectangle rect = sourceeditor.toScreen(
										area.getViewport(), area);
								if (rect != null)
								{
									if (bound == null)
									{
										bound = rect;
									} else
									{
										bound = bound.union(rect);
									}

									if (element instanceof Ellipse)
									{
										shapes
												.add(new Ellipse2D.Float(
														rect.x, rect.y,
														rect.width, rect.height));
									} else if (element instanceof com.wisii.wisedoc.document.svg.Rectangle)
									{
										shapes.add(rect);
									} else if (element instanceof Line)
									{
										final FixedLength x1len = (FixedLength) element
												.getAttribute(Constants.PR_X1);
										final FixedLength y1len = (FixedLength) element
												.getAttribute(Constants.PR_Y1);
										final FixedLength x2len = (FixedLength) element
												.getAttribute(Constants.PR_X2);
										final FixedLength y2len = (FixedLength) element
												.getAttribute(Constants.PR_Y2);
										int x1 = rect.x;
										int y1 = rect.y;
										int x2 = rect.x;
										int y2 = rect.y;
										if (x1len.getValue() > 100)
										{
											x1 = rect.x + rect.width;
										}
										if (y1len.getValue() > 100)
										{
											y1 = rect.y + rect.height;
										}
										if (x2len.getValue() > 100)
										{
											x2 = rect.x + rect.width;
										}
										if (y2len.getValue() > 100)
										{
											y2 = rect.y + rect.height;
										}
										shapes.add(new Line2D.Float(x1, y1, x2,
												y2));
									}
								}
							}
						} else if (parentelement instanceof Canvas)
						{
							final Canvas svgcontainer = (Canvas) parentelement;
							final Area area = svgcontainer.getArea();
							if (area != null)
							{
								Rectangle rect = sourceeditor.toScreen(area
										.getViewport(), area);
								if (rect != null)
								{
									final Shape shape = SVGLocationConvert
											.createSVGHightLightShape((AbstractSVG) element);
									Rectangle2D r = (Rectangle2D) shape
											.getBounds2D().clone();
									final Rectangle2D r0 = area.getViewport();
									r.setRect(r.getX() + r0.getX(), r.getY()
											+ r0.getY(), r.getWidth(), r
											.getHeight());
									r = sourceeditor.toScreen(r, area);
									rect = r.getBounds();
									if (bound == null)
									{
										bound = rect;
									} else
									{
										bound = bound.union(rect);
									}
									if (element instanceof Ellipse)
									{
										shapes
												.add(new Ellipse2D.Float(
														rect.x, rect.y,
														rect.width, rect.height));
									} else if (element instanceof com.wisii.wisedoc.document.svg.Rectangle)
									{
										shapes.add(rect);
									} else if (element instanceof Line)
									{
										final FixedLength x1len = (FixedLength) element
												.getAttribute(Constants.PR_X1);
										final FixedLength y1len = (FixedLength) element
												.getAttribute(Constants.PR_Y1);
										final FixedLength x2len = (FixedLength) element
												.getAttribute(Constants.PR_X2);
										final FixedLength y2len = (FixedLength) element
												.getAttribute(Constants.PR_Y2);
										int x1;
										int x2;
										int y1;
										int y2;
										if (x1len.getValue() > x2len.getValue())
										{
											x1 = rect.x + rect.width;
											x2 = rect.x;
										} else
										{
											x2 = rect.x + rect.width;
											x1 = rect.x;
										}
										if (y1len.getValue() > y2len.getValue())
										{
											y1 = rect.y + rect.height;
											y2 = rect.y;
										} else
										{
											y2 = rect.y + rect.height;
											y1 = rect.y;
										}
										shapes.add(new Line2D.Float(x1, y1, x2,
												y2));
									}
								}
							}
						}
					}
				}
				if (bound != null && !shapes.isEmpty())
				{
					final Point point = dtde.getLocation();
					currentpoint = point;
					startpoint = point;
				}
			}
		}

	}

	@Override
	public void dragExit(final DropTargetEvent dte)
	{
		reset();
	}

	@Override
	public void dragOver(final DropTargetDragEvent dtde)
	{
		updateInfo();
		final Point p = dtde.getLocation();
		// 【添加：START】 by 李晓光 2009-12-31
		// 随着鼠标的拖动，绘制拖拽光标，标示要插入信息的位置。
		DocumentPosition pos = documentPanel.pointtodocpos(p);
		caret.setPosition(pos);
		// 【添加：END】 by 李晓光 2009-12-31
		if (!p.equals(currentpoint) && bound != null && shapes != null
				&& !shapes.isEmpty())
		{
			int x = currentpoint.x;
			int y = currentpoint.y;
			if (p.x < currentpoint.x)
			{
				x = p.x;
			}
			if (p.y < currentpoint.y)
			{
				y = p.y;
			}
			final int distantx = currentpoint.x - x;
			final int distanty = currentpoint.y - y;
			currentpoint = p;
			documentPanel.repaint(bound.x + x - startpoint.x - 2, bound.y + y
					- startpoint.y - 2, bound.width + distantx + 4,
					bound.height + distanty + 4);

		}
	}

	private Point convertPoint(final Point p)
	{
		if (isNull(p))
		{
			return null;
		}
		return SwingUtilities.convertPoint(documentPanel, p, gridPanel);
	}

	public void drop(final DropTargetDropEvent dtde)
	{
		updateInfo();
		Object dragobject = null;
		Transferable transfer = dtde.getTransferable();
		try
		{
			BindNodeData bindnodedata = (BindNodeData) transfer
					.getTransferData(WsdTransferable.BindNodeFlavor);

			if (bindnodedata != null)
			{
				dragobject = bindnodedata.getBindNode();
			}
		} catch (final Exception e)
		{
			LogUtil.debugException("拖拽解析时出错", e);
		}
		if (dragobject == null)
		{
			try
			{
				WSDData wsddata = (WSDData) transfer
						.getTransferData(WsdTransferable.WSDFlavor);
				if (wsddata != null)
				{
					dragobject = wsddata.getElements();
				}
			} catch (final Exception e)
			{
				LogUtil.debugException("拖拽解析时出错", e);
			}
			if (dragobject == null)
			{
				try
				{
					dragobject = transfer
							.getTransferData(DataFlavor.stringFlavor);
				} catch (final Exception e)
				{
					LogUtil.debugException("拖拽解析时出错", e);
				}
			}
		}
		if (dragobject == null)
		{
			return;
		}
		final Point endpoint = dtde.getLocation();

		/* 【添加：START】by 李晓光 2008-10-17 */
		final Point p = convertPoint(endpoint);
		/* 【添加：END】by 李晓光 2008-10-17 */
		final DocumentPosition pos = documentPanel.pointtodocpos(p);
		// setCaretPosition(pos);
		if (pos != null && dragobject != null)
		{
			if (dragobject instanceof BindNode)
			{
				final CellElement leaf = pos.getLeafElement();
				if (pos.isOn() && (leaf instanceof Inline)
						&& !(leaf instanceof TextInline))
				{
					final Map<Integer, Object> attrs = new HashMap<Integer, Object>();
					attrs.put(Constants.PR_INLINE_CONTENT, new Text(
							(BindNode) dragobject));
					document.setElementAttributes(leaf, attrs, false);
				} else
				{
					document.insertString((BindNode) dragobject, pos, pos
							.getInlineAttriute());
				}
			} else if (dragobject instanceof List)
			{
				final List<CellElement> elements = new ArrayList<CellElement>();
				final List list = (List) dragobject;
				if (!list.isEmpty())
				{
					final int size = list.size();

					// 拖拽点是否在画布上
					final boolean isincanvas = pos.isOn()
							&& (pos.getLeafElement().getChildAt(0) instanceof Canvas);
					final List<CellElement> canvaselements = new ArrayList<CellElement>();
					int distantsx = 0;
					int distantsy = 0;
					if (startpoint != null)
					{
						distantsx = endpoint.x - startpoint.x;
						distantsy = endpoint.y - startpoint.y;
					}

					// 拖拽的
					FixedLength innerdistantx = null;
					FixedLength innerdistanty = null;
					boolean isininlinegroup = false;
					if (pos.getLeafElement() instanceof Inline
							&& pos.getLeafElement().getParent() instanceof Group)
					{
						isininlinegroup = true;
					}
					for (int i = 0; i < size; i++)
					{
						final Object o = list.get(i);
						if (o instanceof CellElement)
						{
							if (isininlinegroup)
							{
								if (!(o instanceof Inline)
										&& !DocumentUtil
												.isInlineGroup((CellElement) o))
								{
									SystemManager
											.getMainframe()
											.setStatus(
													RibbonUIText.FRAME_STATUS_UNABLE_DROP);
									return;
								}
							}
							final CellElement cellelement = (CellElement) o;
							// 如果是绝对定位块容器，则计算新的起始位置
							if (((cellelement instanceof BlockContainer) && (((EnumProperty) (((BlockContainer) cellelement)
									.getAttribute(Constants.PR_ABSOLUTE_POSITION)))
									.getEnum() == Constants.EN_ABSOLUTE))
									|| (cellelement instanceof SVGContainer && (!isincanvas)))
							{
								final FixedLength oldtop = (FixedLength) cellelement
										.getAttribute(Constants.PR_TOP);
								final FixedLength oldleft = (FixedLength) cellelement
										.getAttribute(Constants.PR_LEFT);
								if (innerdistantx == null)
								{
									if (startpoint != null)
									{
										final Rectangle2D innerrect = getInnnerLength(
												distantsx, distantsy);
										innerdistantx = new FixedLength(
												innerrect.getWidth(),
												ConfigureUtil.getUnit(), 3);
										innerdistanty = new FixedLength(
												innerrect.getHeight(),
												ConfigureUtil.getUnit(), 3);
									} else
									{
										final Point pospoint = SwingUtilities
												.convertPoint(documentPanel,
														endpoint, gridPanel);
										final Rectangle screenrect = new Rectangle(
												pospoint.x, pospoint.y, 1, 1);
										final Rectangle2D innerrect = documentPanel
												.fromScreen(screenrect);
										final FixedLength innerx = new FixedLength(
												innerrect.getX(), ConfigureUtil
														.getUnit(), 3);
										final FixedLength innery = new FixedLength(
												innerrect.getY(), ConfigureUtil
														.getUnit(), 3);
										innerdistantx = new FixedLength(innerx
												.getValue()
												- oldleft.getValue(), 3);
										innerdistanty = new FixedLength(innery
												.getValue()
												- oldtop.getValue(), 3);

									}
								}
								final LengthProperty top = new FixedLength(
										oldtop.getValue()
												+ innerdistanty.getValue(),
										oldtop.getPrecision());
								final LengthProperty left = new FixedLength(
										oldleft.getValue()
												+ innerdistantx.getValue(),
										oldleft.getPrecision());
								final Map<Integer, Object> att = new HashMap<Integer, Object>();
								att.put(Constants.PR_TOP, top);
								att.put(Constants.PR_LEFT, left);
								cellelement.setAttributes(att, false);
								elements.add(cellelement);
							} else if (isincanvas
									&& cellelement instanceof SVGContainer)
							{
								final FixedLength oldtop = (FixedLength) cellelement
										.getAttribute(Constants.PR_TOP);
								final FixedLength oldleft = (FixedLength) cellelement
										.getAttribute(Constants.PR_LEFT);
								if (innerdistantx == null)
								{
									final Canvas canvas = (Canvas) pos
											.getLeafElement().getChildAt(0);
									final Area area = canvas.getArea();
									final Point relativepoin = new Point(0, 0);
									// 得到画布所在的位置（相对于版心的界面像素位置）
									if (area != null)
									{
										final Rectangle relatirect = documentPanel
												.toScreen(area.getViewport(),
														area);
										final Point pagepoint = new Point(
												endpoint.x - startpoint.x,
												endpoint.y - startpoint.y);
										final Point pagestartpoint = SwingUtilities
												.convertPoint(documentPanel,
														new Point(relatirect.x,
																relatirect.y),
														gridPanel);
										final Rectangle screenrect = new Rectangle(
												pagestartpoint.x,
												pagestartpoint.y, pagepoint.x,
												pagepoint.y);
										final Rectangle2D innerrect = documentPanel
												.fromScreen(screenrect);
										innerdistantx = new FixedLength(
												innerrect.getWidth()
														- innerrect.getX(),
												ConfigureUtil.getUnit(), 3);
										innerdistanty = new FixedLength(
												innerrect.getHeight()
														- innerrect.getY(),
												ConfigureUtil.getUnit(), 3);
									}
								}

								final Map<Integer, Object> att = new HashMap<Integer, Object>();
								final CellElement svgelement = cellelement
										.getChildAt(0);
								if (svgelement instanceof com.wisii.wisedoc.document.svg.Rectangle)
								{
									final LengthProperty y = new FixedLength(
											oldtop.getValue()
													+ innerdistanty.getValue(),
											oldtop.getPrecision());
									final LengthProperty x = new FixedLength(
											oldleft.getValue()
													+ innerdistantx.getValue(),
											oldleft.getPrecision());
									att.put(Constants.PR_X, x);
									att.put(Constants.PR_Y, y);

								} else if (svgelement instanceof Ellipse)
								{
									final FixedLength rx = (FixedLength) svgelement
											.getAttribute(Constants.PR_RX);
									final FixedLength ry = (FixedLength) svgelement
											.getAttribute(Constants.PR_RY);
									final LengthProperty cy = new FixedLength(
											oldtop.getValue()
													+ innerdistanty.getValue()
													+ ry.getValue(), oldtop
													.getPrecision());
									final LengthProperty cx = new FixedLength(
											oldleft.getValue()
													+ innerdistantx.getValue()
													+ rx.getValue(), oldleft
													.getPrecision());
									att.put(Constants.PR_CX, cx);
									att.put(Constants.PR_CY, cy);
								} else if (svgelement instanceof Line)
								{
									final FixedLength x1 = (FixedLength) svgelement
											.getAttribute(Constants.PR_X1);
									final FixedLength x2 = (FixedLength) svgelement
											.getAttribute(Constants.PR_X2);
									final FixedLength y1 = (FixedLength) svgelement
											.getAttribute(Constants.PR_Y1);
									final FixedLength y2 = (FixedLength) svgelement
											.getAttribute(Constants.PR_Y2);
									final FixedLength nx1 = new FixedLength(x1
											.getValue()
											+ oldleft.getValue()
											+ innerdistantx.getValue(), 3);
									final FixedLength nx2 = new FixedLength(x2
											.getValue()
											+ oldleft.getValue()
											+ innerdistantx.getValue(), 3);
									final FixedLength ny1 = new FixedLength(y1
											.getValue()
											+ oldtop.getValue()
											+ innerdistanty.getValue(), 3);
									final FixedLength ny2 = new FixedLength(y2
											.getValue()
											+ oldtop.getValue()
											+ innerdistanty.getValue(), 3);
									att.put(Constants.PR_X1, nx1);
									att.put(Constants.PR_X2, nx2);
									att.put(Constants.PR_Y1, ny1);
									att.put(Constants.PR_Y2, ny2);
								}
								svgelement.setAttributes(att, false);
								canvaselements.add(svgelement);
							} else if (cellelement instanceof AbstractSVG)
							{
								FixedLength xloc = null;
								FixedLength yloc = null;
								Point locpoint = endpoint;
								if (shapes != null && i < shapes.size())
								{
									final Shape firstshape = shapes.get(i);
									locpoint = new Point(
											firstshape.getBounds().x
													+ endpoint.x - startpoint.x,
											firstshape.getBounds().y
													+ endpoint.y - startpoint.y);
								}

								if (isincanvas)
								{
									final Canvas canvas = (Canvas) pos
											.getLeafElement().getChildAt(0);
									final Area area = canvas.getArea();
									// 得到画布所在的位置（相对于版心的界面像素位置）
									if (area != null)
									{
										final Rectangle relatirect = documentPanel
												.toScreen(area.getViewport(),
														area);
										final int screenx = locpoint.x
												- relatirect.x;
										final int screeny = locpoint.y
												- relatirect.y;
										final Rectangle2D relativescreenlocrect = documentPanel
												.fromScreen(new Rectangle(0, 0,
														screenx, screeny));
										xloc = new FixedLength(
												relativescreenlocrect
														.getWidth(),
												ConfigureUtil.getUnit(), 3);
										yloc = new FixedLength(
												relativescreenlocrect
														.getHeight(),
												ConfigureUtil.getUnit(), 3);
									} else
									{
										xloc = new FixedLength(0);
										yloc = new FixedLength(0);
									}
								} else
								{
									final Point apoint = SwingUtilities
											.convertPoint(documentPanel,
													locpoint, gridPanel);
									final Rectangle2D screenlocrect = documentPanel
											.fromScreen(new Rectangle(apoint.x,
													apoint.y, 0, 0));
									xloc = new FixedLength(
											screenlocrect.getX(), ConfigureUtil
													.getUnit(), 3);
									yloc = new FixedLength(
											screenlocrect.getY(), ConfigureUtil
													.getUnit(), 3);
								}
								if (innerdistantx == null)
								{
									if (cellelement instanceof Line)
									{
										FixedLength oldxloc = (FixedLength) cellelement
												.getAttribute(Constants.PR_X1);
										final FixedLength oldx2loc = (FixedLength) cellelement
												.getAttribute(Constants.PR_X2);
										if (oldx2loc.getValue() < oldxloc
												.getValue())
										{
											oldxloc = oldx2loc;
										}
										FixedLength oldyloc = (FixedLength) cellelement
												.getAttribute(Constants.PR_Y1);
										final FixedLength oldy2loc = (FixedLength) cellelement
												.getAttribute(Constants.PR_Y2);
										if (oldy2loc.getValue() < oldyloc
												.getValue())
										{
											oldyloc = oldy2loc;
										}
										innerdistantx = new FixedLength(xloc
												.getValue()
												- oldxloc.getValue(), oldxloc
												.getPrecision());
										innerdistanty = new FixedLength(yloc
												.getValue()
												- oldyloc.getValue(), oldyloc
												.getPrecision());
									} else if (cellelement instanceof Ellipse)
									{
										final FixedLength oldcxloc = (FixedLength) cellelement
												.getAttribute(Constants.PR_CX);
										final FixedLength oldcyloc = (FixedLength) cellelement
												.getAttribute(Constants.PR_CY);
										final FixedLength oldrxloc = (FixedLength) cellelement
												.getAttribute(Constants.PR_RX);
										final FixedLength oldryloc = (FixedLength) cellelement
												.getAttribute(Constants.PR_RY);
										innerdistantx = new FixedLength(xloc
												.getValue()
												+ oldrxloc.getValue()
												- oldcxloc.getValue(), oldcxloc
												.getPrecision());
										innerdistanty = new FixedLength(yloc
												.getValue()
												+ oldryloc.getValue()
												- oldcyloc.getValue(), oldcyloc
												.getPrecision());
									} else
									{
										final FixedLength oldxloc = (FixedLength) cellelement
												.getAttribute(Constants.PR_X);
										final FixedLength oldyloc = (FixedLength) cellelement
												.getAttribute(Constants.PR_Y);
										innerdistantx = new FixedLength(xloc
												.getValue()
												- oldxloc.getValue(), oldxloc
												.getPrecision());
										innerdistanty = new FixedLength(yloc
												.getValue()
												- oldyloc.getValue(), oldyloc
												.getPrecision());
									}
								}
								if (isincanvas)
								{
									final Map<Integer, Object> atts = new HashMap<Integer, Object>();
									if (cellelement instanceof Line)
									{
										final FixedLength oldx1 = (FixedLength) cellelement
												.getAttribute(Constants.PR_X1);
										final FixedLength oldx2 = (FixedLength) cellelement
												.getAttribute(Constants.PR_X2);
										final FixedLength oldy1 = (FixedLength) cellelement
												.getAttribute(Constants.PR_Y1);
										final FixedLength oldy2 = (FixedLength) cellelement
												.getAttribute(Constants.PR_Y2);
										atts.put(Constants.PR_X1,
												new FixedLength(oldx1
														.getValue()
														+ innerdistantx
																.getValue(),
														oldx1.getPrecision()));
										atts.put(Constants.PR_X2,
												new FixedLength(oldx2
														.getValue()
														+ innerdistantx
																.getValue(),
														oldx2.getPrecision()));
										atts.put(Constants.PR_Y1,
												new FixedLength(oldy1
														.getValue()
														+ innerdistanty
																.getValue(),
														oldy1.getPrecision()));
										atts.put(Constants.PR_Y2,
												new FixedLength(oldy2
														.getValue()
														+ innerdistanty
																.getValue(),
														oldy2.getPrecision()));
									} else if (cellelement instanceof Ellipse)
									{
										final FixedLength oldcx = (FixedLength) cellelement
												.getAttribute(Constants.PR_CX);
										final FixedLength oldcy = (FixedLength) cellelement
												.getAttribute(Constants.PR_CY);
										atts.put(Constants.PR_CX,
												new FixedLength(oldcx
														.getValue()
														+ innerdistantx
																.getValue(),
														oldcx.getPrecision()));
										atts.put(Constants.PR_CY,
												new FixedLength(oldcy
														.getValue()
														+ innerdistanty
																.getValue(),
														oldcy.getPrecision()));
									} else
									{
										final FixedLength oldx = (FixedLength) cellelement
												.getAttribute(Constants.PR_X);
										final FixedLength oldy = (FixedLength) cellelement
												.getAttribute(Constants.PR_Y);
										atts.put(Constants.PR_X,
												new FixedLength(oldx.getValue()
														+ innerdistantx
																.getValue(),
														oldx.getPrecision()));
										atts.put(Constants.PR_Y,
												new FixedLength(oldy.getValue()
														+ innerdistanty
																.getValue(),
														oldy.getPrecision()));
									}
									cellelement.setAttributes(atts, false);
									canvaselements.add(cellelement);
								} else
								{
									final Map<Integer, Object> atts = new HashMap<Integer, Object>();
									final Map<Integer, Object> svgatts = new HashMap<Integer, Object>();
									FixedLength svgwidth = null;
									FixedLength svgheight = null;
									if (cellelement instanceof Line)
									{
										final FixedLength oldx1 = (FixedLength) cellelement
												.getAttribute(Constants.PR_X1);
										final FixedLength oldx2 = (FixedLength) cellelement
												.getAttribute(Constants.PR_X2);
										FixedLength oldminx = oldx1;
										if (oldx2.getValue() < oldminx
												.getValue())
										{
											oldminx = oldx2;
											svgwidth = new FixedLength(oldx1
													.getValue()
													- oldminx.getValue(), oldx1
													.getPrecision());
										} else
										{
											svgwidth = new FixedLength(oldx2
													.getValue()
													- oldminx.getValue(), oldx1
													.getPrecision());
										}
										final FixedLength oldy1 = (FixedLength) cellelement
												.getAttribute(Constants.PR_Y1);
										final FixedLength oldy2 = (FixedLength) cellelement
												.getAttribute(Constants.PR_Y2);
										FixedLength oldminy = oldy1;
										if (oldy2.getValue() < oldminy
												.getValue())
										{
											oldminy = oldy2;
											svgheight = new FixedLength(oldy1
													.getValue()
													- oldminy.getValue(), oldy1
													.getPrecision());
										} else
										{
											svgheight = new FixedLength(oldy2
													.getValue()
													- oldminy.getValue(), oldy1
													.getPrecision());
										}
										atts.put(Constants.PR_X1,
												new FixedLength(oldx1
														.getValue()
														- oldminx.getValue(),
														oldx1.getPrecision()));
										atts.put(Constants.PR_X2,
												new FixedLength(oldx2
														.getValue()
														- oldminx.getValue(),
														oldx2.getPrecision()));
										atts.put(Constants.PR_Y1,
												new FixedLength(oldy1
														.getValue()
														- oldminy.getValue(),
														oldy1.getPrecision()));
										atts.put(Constants.PR_Y2,
												new FixedLength(oldy2
														.getValue()
														- oldminy.getValue(),
														oldy2.getPrecision()));
									} else if (cellelement instanceof Ellipse)
									{
										final FixedLength newcx = (FixedLength) cellelement
												.getAttribute(Constants.PR_RX);
										final FixedLength newcy = (FixedLength) cellelement
												.getAttribute(Constants.PR_RY);
										svgwidth = new FixedLength(newcx
												.getValue() * 2, newcx
												.getPrecision());
										svgheight = new FixedLength(newcy
												.getValue() * 2, newcy
												.getPrecision());
										atts.put(Constants.PR_CX, newcy);
										atts.put(Constants.PR_CY, newcy);
									} else
									{
										svgwidth = (FixedLength) cellelement
												.getAttribute(Constants.PR_WIDTH);
										svgheight = (FixedLength) cellelement
												.getAttribute(Constants.PR_HEIGHT);
										atts.put(Constants.PR_X,
												new FixedLength(0, 3));
										atts.put(Constants.PR_Y,
												new FixedLength(0, 3));
									}
									cellelement.setAttributes(atts, false);
									svgatts.put(Constants.PR_TOP, yloc);
									svgatts.put(Constants.PR_LEFT, xloc);
									svgatts
											.put(
													Constants.PR_BLOCK_PROGRESSION_DIMENSION,
													new LengthRangeProperty(
															svgheight,
															svgheight,
															svgheight));
									svgatts
											.put(
													Constants.PR_INLINE_PROGRESSION_DIMENSION,
													new LengthRangeProperty(
															svgwidth, svgwidth,
															svgwidth));
									svgatts.put(Constants.PR_ABSOLUTE_POSITION,
											new EnumProperty(
													Constants.EN_ABSOLUTE, ""));
									svgatts.put(Constants.PR_OVERFLOW,
											new EnumProperty(
													Constants.EN_VISIBLE, ""));
									final SVGContainer svgcontainer = new SVGContainer(
											svgatts);
									svgcontainer.add(cellelement);
									elements.add(svgcontainer);
								}

							} else
							{
								elements.add(cellelement);
							}

						} else
						{
							return;
						}
					}
					if (isincanvas && !canvaselements.isEmpty())
					{
						final CellElement canvas = pos.getLeafElement()
								.getChildAt(0);
						document.insertElements(canvaselements, canvas, canvas
								.getChildCount());
					}
				}
				if (!elements.isEmpty())
				{
					document.insertElements(elements, pos);
					if (dtde.getDropAction() != DnDConstants.ACTION_COPY)
					{
						document
								.deleteElements(selectModel.getSelectedObject());
					}
					final CellElement firstcell = DocumentPositionUtil
							.findFirstDocumentpositionCell(elements);
					final CellElement lastcell = DocumentPositionUtil
							.findLastDocumentpositionCell(elements);
					if (firstcell != null && lastcell != null
							&& firstcell instanceof Inline
							&& lastcell instanceof Inline)
					{
						final DocumentPosition firstpos = new DocumentPosition(
								firstcell, true);
						final DocumentPosition lastpos = new DocumentPosition(
								lastcell, false);
						final DocumentPositionRange range = DocumentPositionRange
								.creatSelectCell(firstpos, lastpos);
						selectModel.setSelection(range);
						documentPanel.setCaretPosition(null);
					} else
					{
						if (lastcell instanceof Inline)
						{
							documentPanel
									.setCaretPosition(new DocumentPosition(
											lastcell, false));
						}
						else if (firstcell instanceof Inline)
						{
							documentPanel
									.setCaretPosition(new DocumentPosition(
											firstcell, true));
						}
						else
						{
							selectModel.setObjectSelecttion(elements.get(0));
							documentPanel.setCaretPosition(null);
						}
					}
				}
			} else
			{
				if (pos.getLeafElement() instanceof Inline
						&& pos.getLeafElement().getParent() instanceof Group)
				{
					SystemManager.getMainframe().setStatus(
							RibbonUIText.FRAME_STATUS_UNABLE_DROP);
					return;
				}
				document.insertString(dragobject.toString(), pos, pos
						.getLeafElement().getAttributes().getAttributes());
			}
		}
		reset();
	}

	@Override
	public void dropActionChanged(final DropTargetDragEvent dtde)
	{

	}

	private Rectangle2D getInnnerLength(final int width, final int height)
	{
		final Rectangle rect = new Rectangle(0, 0, width, height);
		return documentPanel.fromScreen(rect);
	}

	public void paint(final Graphics g)
	{
		if (shapes != null && !shapes.isEmpty())
		{
			final Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(SwingUtil.getStrokefromLineType(SwingUtil.DASGEDLINE,
					1.0f));
			g2d.setXORMode(Color.GRAY);
			g2d.translate(currentpoint.x - startpoint.x, currentpoint.y
					- startpoint.y);
			for (final Shape shape : shapes)
			{
				g2d.draw(shape);
			}

		}
		// 【添加：START】 by 李晓光 2009-12-31
		// 在此调用绘制光标。
		if (caret != null)
			caret.paintCaret(g);
		// 【添加：END】 by 李晓光 2009-12-31
	}

	private void reset()
	{
		shapes = null;
		bound = null;
		startpoint = null;
		// 【添加：START】 by 李晓光 2009-12-31
		// 释放拖拽光标资源
		caret.setPosition(null);
		caret = null;
		// 【添加：END】 by 李晓光 2009-12-31
	}
}
