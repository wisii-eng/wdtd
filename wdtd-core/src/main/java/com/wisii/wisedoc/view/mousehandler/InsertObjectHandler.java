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
/**
 * @InsertObjectHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.mousehandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.swing.SwingUtil;
import com.wisii.wisedoc.view.cursor.CursorManager;
import com.wisii.wisedoc.view.cursor.CursorManager.CursorType;

/**
 * 类功能描述：插入对象鼠标事件
 * 
 * 作者：zhangqiang 创建日期：2008-10-30
 */
public class InsertObjectHandler extends AbstractMouseHandler
{

	private Point startpoint;

	private Point currentpoint;

	private Point pagestartpoint;

	private Point pageendpoint;

	private static float lineheight = 1.0f;

	private Color color = Color.GRAY;

	public void paint(Graphics g)
	{
		if (startpoint != null && currentpoint != null
				&& !currentpoint.equals(startpoint)
				&& InsertManager.getType() != InsertManager.NOTYPE)
		{
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(SwingUtil.getStrokefromLineType(SwingUtil.DASGEDLINE,
					lineheight));
			Color old = g2d.getColor();
			g2d.setXORMode(color);
			Rectangle rect = findBound(currentpoint, startpoint);
			// 如果是直线，则绘制直线
			if (InsertManager.getType() == InsertManager.LINE)
			{
				g2d.drawLine(startpoint.x, startpoint.y, currentpoint.x,
						currentpoint.y);
			}
			// 如果是椭圆，则绘制椭圆框
			else if (InsertManager.getType() == InsertManager.ELLIPSE)
			{
				g2d.drawOval(rect.x - 1, rect.y - 1, rect.width + 2,
						rect.height + 2);
			} else
			{
				g2d.drawRect(rect.x - 1, rect.y - 1, rect.width + 2,
						rect.height + 2);
			}
			g2d.setColor(old);
		}
	}

	public void mousePressed(MouseEvent event)
	{
		// 如果是左键，则进行处理
		if (SwingUtilities.isLeftMouseButton(event))
		{
			pagestartpoint = event.getPoint();
			startpoint = SwingUtilities.convertPoint(event.getComponent(),
					pagestartpoint, _editor);
			currentpoint = startpoint;
		}
		// 如果是右键，则清空选中状态
		else if (SwingUtilities.isRightMouseButton(event))
		{
			InsertManager.clearType();
		} else
		{
			// do nothing
		}
	}

	public void mouseReleased(MouseEvent event)
	{
		pageendpoint = event.getPoint();
		currentpoint = SwingUtilities.convertPoint(event.getComponent(),
				pageendpoint, _editor);
		if (SwingUtilities.isLeftMouseButton(event))
		{
			CellElement element = createCell(InsertManager.getType(), event);
			DocumentPosition pos = _editor.pointtodocpos(pagestartpoint);
			if (element != null && pos != null)
			{
				if (element instanceof AbstractSVG)
				{
					if (pos != null)
					{
						CellElement leaf = pos.getLeafElement().getChildAt(0);
						if (leaf instanceof Canvas)
						{
							List<CellElement> inserts = new ArrayList<CellElement>();
							inserts.add(element);
							SystemManager.getCurruentDocument().insertElements(
									inserts, leaf, leaf.getChildCount());
						}
					}
				} else
				{
					insertCell(element, pos);
				}
			}
			// 插入不成功，则更新界面
			else
			{
				Rectangle rect = findBound(startpoint, currentpoint);
				_editor.repaint((int) (rect.x - 2 * lineheight),
						(int) (rect.y - 2 * lineheight),
						(int) (rect.width + 4 * lineheight),
						(int) (rect.height + 4 * lineheight));
			}
		}
		InsertManager.clearType();
	}

	public void mouseDragged(MouseEvent event)
	{
		Point point = SwingUtilities.convertPoint(event.getComponent(), event
				.getPoint(), _editor);
		updataView(point);

	}

	public boolean isInControl(MouseEvent event)
	{
		int type = InsertManager.getType();
		if (type == InsertManager.NOTYPE)
		{
			return false;
		}
		DocumentPosition pos = _editor.pointtodocpos(event.getPoint());
		if (pos == null)
		{
			return false;
		}
		if (type != InsertManager.RELATIVEBLOCKCONTAINER)
		{
			return true;
		}
		CellElement leaf =pos.getLeafElement();
		//是相对位置容器，且插入点位置在在一个inline组内时，不可插入
		if(leaf instanceof Inline&&leaf.getParent() instanceof Group)
		{
			return false;
		}
		return true;
	}

	private void updataView(Point point)
	{
		if (currentpoint != null && startpoint != null && point != null
				&& !point.equals(currentpoint))
		{
			Rectangle rect = findBound(startpoint, point, currentpoint);
			currentpoint = point;
			_editor.repaint((int) (rect.x - 2 * lineheight),
					(int) (rect.y - 2 * lineheight),
					(int) (rect.width + 4 * lineheight),
					(int) (rect.height + 4 * lineheight));

		}
	}

	private CellElement createCell(int type, MouseEvent event)
	{
		CellElement cell = null;
		if (startpoint != null && currentpoint != null)
		{
			Map<Integer, Object> att = new HashMap<Integer, Object>();
			Rectangle rect = findBound(startpoint, currentpoint);
			int pagestartx = pagestartpoint.x;
			int pagestarty = pagestartpoint.y;
			if (startpoint.x > currentpoint.x)
			{
				pagestartx = pageendpoint.x;
			}
			if (startpoint.y > currentpoint.y)
			{
				pagestarty = pageendpoint.y;
			}
			rect.setBounds(pagestartx, pagestarty, rect.width, rect.height);
			Rectangle2D rect2d = _editor.fromScreen(rect);
			LengthProperty top = new FixedLength(rect2d.getY(), ConfigureUtil
					.getUnit(), 3);
			LengthProperty left = new FixedLength(rect2d.getX(), ConfigureUtil
					.getUnit(), 3);
			LengthProperty height = new FixedLength(rect2d.getHeight(),
					ConfigureUtil.getUnit(), 3);
			LengthProperty width = new FixedLength(rect2d.getWidth(),
					ConfigureUtil.getUnit(), 3);
			if(height.getValue()<InitialManager.MINLEN.getValue())
			{
				height = InitialManager.MINLEN;
			}
			if(width.getValue()<InitialManager.MINLEN.getValue())
			{
				width = InitialManager.MINLEN;
			}
			att.put(Constants.PR_TOP, top);
			att.put(Constants.PR_LEFT, left);
			EnumLength length = new EnumLength(Constants.EN_AUTO, null);
			att.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
					new LengthRangeProperty(height, height, length));
			att.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
					new LengthRangeProperty(width, width, length));

			if (type == InsertManager.BLOCKCONTAINER)
			{
				att.put(Constants.PR_ABSOLUTE_POSITION, new EnumProperty(
						Constants.EN_ABSOLUTE, ""));
				cell = new BlockContainer(att);
				// cell.add(new Block());
			} else if (type == InsertManager.RELATIVEBLOCKCONTAINER)
			{
				att.put(Constants.PR_ABSOLUTE_POSITION, new EnumProperty(
						Constants.EN_RELATIVE, ""));
				att.remove(Constants.PR_TOP);
				att.remove(Constants.PR_LEFT);
				cell = new BlockContainer(att);
			} else if (type == InsertManager.LINE)
			{

				Map<Integer, Object> lineatt = new HashMap<Integer, Object>();
				lineatt.put(Constants.PR_COLOR, Color.black);
				lineatt.put(Constants.PR_STROKE_WIDTH, new FixedLength(0.75d,
						"pt", 2));
				DocumentPosition pos = _editor.pointtodocpos(pagestartpoint);
				// 如果光标在画布上
				if (pos != null && pos.isOn()
						&& pos.getLeafElement().getChildAt(0) instanceof Canvas)
				{
					Canvas canvas = (Canvas) pos.getLeafElement().getChildAt(0);
					Area area = canvas.getArea();
					Point relativepoin = new Point(0, 0);
					// 得到画布所在的位置（相对于版心的界面像素位置）
					if (area != null)
					{
						Rectangle relatirect = _editor.toScreen(area
								.getViewport(), area);
						relativepoin = SwingUtilities.convertPoint(_editor,
								new Point(relatirect.x, relatirect.y), event
										.getComponent());
					}
					Rectangle2D startrect = new Rectangle(50, 50,
							pagestartpoint.x - relativepoin.x, pagestartpoint.y
									- relativepoin.y);
					Rectangle2D screenstart = _editor.fromScreen(startrect);
					Rectangle2D endrect = new Rectangle(50, 50, pageendpoint.x
							- relativepoin.x, pageendpoint.y - relativepoin.y);
					Rectangle2D screenend = _editor.fromScreen(endrect);
					LengthProperty x1 = new FixedLength(screenstart.getWidth(),
							ConfigureUtil.getUnit(), 3);
					LengthProperty y1 = new FixedLength(
							screenstart.getHeight(), ConfigureUtil.getUnit(), 3);
					LengthProperty x2 = new FixedLength(screenend.getWidth(),
							ConfigureUtil.getUnit(), 3);
					LengthProperty y2 = new FixedLength(screenend.getHeight(),
							ConfigureUtil.getUnit(), 3);
					lineatt.put(Constants.PR_X1, x1);
					lineatt.put(Constants.PR_Y1, y1);
					lineatt.put(Constants.PR_X2, x2);
					lineatt.put(Constants.PR_Y2, y2);
					Line line = new Line(lineatt);
					cell = line;
				} else
				{
					Rectangle2D startrect = new Rectangle(1, 1,
							pagestartpoint.x - pagestartx, pagestartpoint.y
									- pagestarty);
					Rectangle2D screenstart = _editor.fromScreen(startrect);
					Rectangle2D endrect = new Rectangle(1, 1, pageendpoint.x
							- pagestartx, pageendpoint.y - pagestarty);
					Rectangle2D screenend = _editor.fromScreen(endrect);
					LengthProperty x1 = new FixedLength(screenstart.getWidth(),
							ConfigureUtil.getUnit(), 3);
					LengthProperty y1 = new FixedLength(
							screenstart.getHeight(), ConfigureUtil.getUnit(), 3);
					LengthProperty x2 = new FixedLength(screenend.getWidth(),
							ConfigureUtil.getUnit(), 3);
					LengthProperty y2 = new FixedLength(screenend.getHeight(),
							ConfigureUtil.getUnit(), 3);
					lineatt.put(Constants.PR_X1, x1);
					lineatt.put(Constants.PR_Y1, y1);
					lineatt.put(Constants.PR_X2, x2);
					lineatt.put(Constants.PR_Y2, y2);
					Line line = new Line(lineatt);
					att.put(Constants.PR_ABSOLUTE_POSITION, new EnumProperty(
							Constants.EN_ABSOLUTE, ""));
					att.put(Constants.PR_OVERFLOW, new EnumProperty(
							Constants.EN_VISIBLE, ""));
					cell = new SVGContainer(att);
					cell.add(line);

				}
			} else if (type == InsertManager.RECTANGLE)
			{
				Map<Integer, Object> rectatt = new HashMap<Integer, Object>();
				rectatt.put(Constants.PR_WIDTH, width);
				rectatt.put(Constants.PR_HEIGHT, height);
				rectatt.put(Constants.PR_COLOR, Color.black);
				rectatt.put(Constants.PR_STROKE_WIDTH, new FixedLength(0.75d,
						"pt", 2));
				rectatt.put(Constants.PR_FILL, new EnumProperty(
						Constants.EN_NONE, ""));
				DocumentPosition pos = _editor.pointtodocpos(pagestartpoint);
				if (pos != null && pos.isOn()
						&& pos.getLeafElement().getChildAt(0) instanceof Canvas)
				{
					Canvas canvas = (Canvas) pos.getLeafElement().getChildAt(0);
					Area area = canvas.getArea();
					Point relativepoin = new Point(0, 0);
					// 得到画布所在的位置（相对于版心的界面像素位置）
					if (area != null)
					{
						Rectangle relatirect = _editor.toScreen(area
								.getViewport(), area);
						relativepoin = SwingUtilities.convertPoint(_editor,
								new Point(relatirect.x, relatirect.y), event
										.getComponent());
					}
					Rectangle2D startrect = new Rectangle(1, 1, pagestartx
							- relativepoin.x, pagestarty - relativepoin.y);
					Rectangle2D screenstart = _editor.fromScreen(startrect);
					rectatt.put(Constants.PR_X, new FixedLength(screenstart
							.getWidth(), ConfigureUtil.getUnit(), 3));
					rectatt.put(Constants.PR_Y, new FixedLength(screenstart
							.getHeight(), ConfigureUtil.getUnit(), 3));
					com.wisii.wisedoc.document.svg.Rectangle rectangle = new com.wisii.wisedoc.document.svg.Rectangle(
							rectatt);
					cell = rectangle;
				} else
				{
					rectatt.put(Constants.PR_X, new FixedLength(0));
					rectatt.put(Constants.PR_Y, new FixedLength(0));
					com.wisii.wisedoc.document.svg.Rectangle rectangle = new com.wisii.wisedoc.document.svg.Rectangle(
							rectatt);
					att.put(Constants.PR_ABSOLUTE_POSITION, new EnumProperty(
							Constants.EN_ABSOLUTE, ""));
					att.put(Constants.PR_OVERFLOW, new EnumProperty(
							Constants.EN_VISIBLE, ""));
					cell = new SVGContainer(att);
					cell.add(rectangle);
				}
			} else if (type == InsertManager.ELLIPSE)
			{
				Map<Integer, Object> ellipseatt = new HashMap<Integer, Object>();
				ellipseatt.put(Constants.PR_WIDTH, width);
				ellipseatt.put(Constants.PR_HEIGHT, height);
				ellipseatt.put(Constants.PR_COLOR, Color.black);
				ellipseatt.put(Constants.PR_STROKE_WIDTH, new FixedLength(
						0.75d, "pt", 2));
				ellipseatt.put(Constants.PR_FILL, new EnumProperty(
						Constants.EN_NONE, ""));

				DocumentPosition pos = _editor.pointtodocpos(pagestartpoint);
				if (pos != null && pos.isOn()
						&& pos.getLeafElement().getChildAt(0) instanceof Canvas)
				{
					Canvas canvas = (Canvas) pos.getLeafElement().getChildAt(0);
					Area area = canvas.getArea();
					Point relativepoin = new Point(0, 0);
					// 得到画布所在的位置（相对于版心的界面像素位置）
					if (area != null)
					{
						Rectangle relatirect = _editor.toScreen(area
								.getViewport(), area);
						relativepoin = SwingUtilities.convertPoint(_editor,
								new Point(relatirect.x, relatirect.y), event
										.getComponent());
					}
					Rectangle2D startrect = new Rectangle(1, 1, pagestartx
							- relativepoin.x, pagestarty - relativepoin.y);
					Rectangle2D screenstart = _editor.fromScreen(startrect);
					LengthProperty x = new FixedLength(screenstart.getWidth(), ConfigureUtil.getUnit(), 3);
					LengthProperty y = new FixedLength(screenstart.getHeight(), ConfigureUtil.getUnit(),
							3);
					ellipseatt.put(Constants.PR_X, x);
					ellipseatt.put(Constants.PR_Y, y);
					Ellipse ellipse = new Ellipse(ellipseatt);
					cell = ellipse;

				} else
				{
					LengthProperty x = new FixedLength(0d,
							ConfigureUtil.getUnit(), 3);
					LengthProperty y = new FixedLength(0d,
							ConfigureUtil.getUnit(), 3);
					ellipseatt.put(Constants.PR_X, x);
					ellipseatt.put(Constants.PR_Y, y);
					Ellipse ellipse = new Ellipse(ellipseatt);
					att.put(Constants.PR_ABSOLUTE_POSITION, new EnumProperty(
							Constants.EN_ABSOLUTE, ""));
					att.put(Constants.PR_OVERFLOW, new EnumProperty(
							Constants.EN_VISIBLE, ""));
					cell = new SVGContainer(att);
					cell.add(ellipse);
				}
			} else if (type == InsertManager.CANVAS)
			{
				cell = new Inline();
				Map<Integer, Object> canvasatt = new HashMap<Integer, Object>();
				canvasatt.put(Constants.PR_WIDTH, width);
				canvasatt.put(Constants.PR_HEIGHT, height);
				Canvas canvas = new Canvas(canvasatt);
				cell.add(canvas);
			}
			else
			{
			}

		}
		return cell;
	}

	private void insertCell(CellElement element,DocumentPosition pos)
	{
		if (element != null)
		{
			List<CellElement> elements = new ArrayList<CellElement>();
			elements.add(element);
			SystemManager.getCurruentDocument().insertElements(elements, pos);
		}
	}
	@Override
	public void mouseMoved(MouseEvent e)
	{
		_editor.setCursor(CursorManager.getSytemCursor(CursorType.CROSSHAIR_CURSOR));
	}

}
