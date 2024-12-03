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
 * @SizeHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.mousehandler;

import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.UIManager;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.swing.SwingUtil;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.cursor.CursorManager;
import com.wisii.wisedoc.view.cursor.CursorManager.CursorType;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 类功能描述：拖拽方式该表对象大小的鼠标操作类
 * 
 * 作者：zhangqiang 创建日期：2008-11-3
 */
public class SizeHandler extends AbstractMouseHandler
{
	// 标识8个可拖拽点的常量
	// 左上拖拽点
	public static final int LT = 0;
	// 上拖拽点
	public static final int T = 1;
	// 右上拖拽点
	public static final int RT = 2;
	// 右拖拽点
	public static final int R = 3;
	// 右下拖拽点
	public static final int RB = 4;
	// 下拖拽点
	public static final int B = 5;
	// 左下拖拽点
	public static final int LB = 6;
	// 左拖拽点
	public static final int L = 7;
	// 不在拖拽点上
	public static final int NONE = -1;
	// 可拖拽点以界面像素为单位的大小
	public static final int HANDLESIZE = 3;
	public static final int MIN = 10;
	// 拖拽点类型
	private int _type = NONE;
	private CellElement _currentcell;
	Point _startpoint;
	Point _currentpoint;
	// 显示用的虚框线宽
	private static float lineheight = 1.0f;
	// 虚框颜色
	private Color color = UIManager.getColor("MenuItem.background");
	// 拖拽对象的原始矩形区域（界面上的举行区域）
	private transient Rectangle initialBounds;
	private transient Rectangle oldBounds;
	private List<PortRectangle> r;
	private Cursor[] _cursors = new Cursor[]
	{ CursorManager.getSytemCursor(CursorType.NW_RESIZE_CURSOR),
			CursorManager.getSytemCursor(CursorType.N_RESIZE_CURSOR),
			CursorManager.getSytemCursor(CursorType.NE_RESIZE_CURSOR),
			CursorManager.getSytemCursor(CursorType.E_RESIZE_CURSOR),
			CursorManager.getSytemCursor(CursorType.SE_RESIZE_CURSOR),
			CursorManager.getSytemCursor(CursorType.S_RESIZE_CURSOR),
			CursorManager.getSytemCursor(CursorType.SW_RESIZE_CURSOR),
			CursorManager.getSytemCursor(CursorType.W_RESIZE_CURSOR) };

	public boolean isInControl(MouseEvent event)
	{
		Point point = SwingUtilities.convertPoint(event.getComponent(), event
				.getPoint(), _editor);
		boolean isincontrol = false;
		if (r != null && !r.isEmpty())
		{
			int size = r.size();
			for (int i = 0; i < size; i++)
			{
				PortRectangle pr = r.get(i);
				if (pr.contains(point))
				{
					isincontrol = true;
					_type = pr.getType();
					break;
				}
			}
		}
		return isincontrol;
	}

	public void mouseMoved(MouseEvent e)
	{
		super.mouseMoved(e);
		_editor.setCursor(_cursors[_type]);
	}

	public void mousePressed(MouseEvent e)
	{
		super.mousePressed(e);
		if (SwingUtilities.isLeftMouseButton(e))
		{
			Point point = SwingUtilities.convertPoint(e.getComponent(), e
					.getPoint(), _editor);
			_startpoint = point;
			_currentpoint = point;
			int size = r.size();
			PortRectangle portrect = null;
			for (int i = 0; i < size; i++)
			{
				PortRectangle pr = r.get(i);
				if (pr.contains(point))
				{
					portrect = pr;
					break;
				}
			}
			_currentcell = portrect.getElement();
			_type = portrect.getType();
			Area area = _currentcell.getArea();

			if (area != null)
			{
				initialBounds = _editor.toScreen(area.getViewport(), area);
			}
		}
	}

	public void mouseDragged(MouseEvent e)
	{
		super.mouseDragged(e);
		// if (SwingUtilities.isLeftMouseButton(e))
		// {
		Point point = SwingUtilities.convertPoint(e.getComponent(), e
				.getPoint(), _editor);
		updataView(point);
		// }

	}

	private void updataView(Point point)
	{
		if (_currentpoint != null && _startpoint != null && point != null
				&& !point.equals(_currentpoint))
		{
			_currentpoint = point;
			Rectangle rect = createBound();
			if (oldBounds != null)
			{
				rect.add(oldBounds);
			}
			_editor.repaint((int) (rect.x - 2 * lineheight),
					(int) (rect.y - 2 * lineheight),
					(int) (rect.width + 4 * lineheight),
					(int) (rect.height + 4 * lineheight));

		}
	}

	public void mouseReleased(MouseEvent e)
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			super.mouseReleased(e);
			_currentpoint = SwingUtilities.convertPoint(e.getComponent(), e
					.getPoint(), _editor);
			Rectangle rect = createBound();
			_currentpoint = null;
			_startpoint = null;
			// 如果新的大小和原来大小相等，则更新
			if (initialBounds.equals(rect))
			{
				_editor.repaint((int) (oldBounds.x - 2 * lineheight),
						(int) (oldBounds.y - 2 * lineheight),
						(int) (oldBounds.width + 4 * lineheight),
						(int) (oldBounds.height + 4 * lineheight));
			} else
			{
				updatamodel(rect,e);
			}
		}
		reset();
	}

	private void reset()
	{
		_type = NONE;
		_currentcell = null;
		initialBounds = null;
		oldBounds = null;
		_currentpoint = null;
	}

	private void updatamodel(Rectangle rect,MouseEvent e)
	{
		Document doc = _editor.getDocument();
		Map<Integer, Object> attrs = new HashMap<Integer, Object>();
		Point point = new Point(rect.x, rect.y);
		point = SwingUtilities.convertPoint(_editor, point, e.getComponent());
		rect.setBounds(point.x, point.y, rect.width, rect.height);
		Rectangle2D rect2d = _editor.fromScreen(rect);
		LengthProperty top = new FixedLength(rect2d.getY(), ConfigureUtil
				.getUnit());
		LengthProperty left = new FixedLength(rect2d.getX(), ConfigureUtil
				.getUnit());
		LengthProperty height = new FixedLength(rect2d.getHeight(),
				ConfigureUtil.getUnit());
		LengthProperty width = new FixedLength(rect2d.getWidth(), ConfigureUtil
				.getUnit());
		EnumLength length = new EnumLength(Constants.EN_AUTO, null);
		if (_currentcell instanceof BlockContainer)
		{
			attrs.put(Constants.PR_TOP, top);
			attrs.put(Constants.PR_LEFT, left);
			attrs.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
					new LengthRangeProperty(height, height, length));
			attrs.put(Constants.PR_INLINE_PROGRESSION_DIMENSION,
					new LengthRangeProperty(width, width, length));
		} else if (_currentcell instanceof Canvas)
		{
			attrs.put(Constants.PR_HEIGHT, height);
			attrs.put(Constants.PR_WIDTH, width);
		} else if (_currentcell instanceof AbstractGraphics)
		{
			AbstractGraphics abgrahics = (AbstractGraphics) _currentcell;
			Length contentwidth = abgrahics.getContentWidth();
			if (contentwidth.getEnum() != Constants.EN_AUTO)
			{
				attrs.put(Constants.PR_CONTENT_HEIGHT, new EnumLength(abgrahics
						.getContentHeight().getEnum(), height));
				attrs.put(Constants.PR_CONTENT_WIDTH, new EnumLength(
						contentwidth.getEnum(), width));
			}
		}
		if (attrs != null)
		{
			doc.setElementAttributes(_currentcell, attrs, false);
		}

	}

	public void paint(Graphics g)
	{
		if (_startpoint != null && _currentpoint != null
				&& !_currentpoint.equals(_startpoint))
		{
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(SwingUtil.getStrokefromLineType(SwingUtil.DASGEDLINE,
					lineheight));
			Color old = g2d.getColor();
			g2d.setXORMode(color);
			Rectangle rect;
			rect = createBound();
			oldBounds = rect;
			g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
			g2d.setColor(old);
		}
	}

	// cre
	private Rectangle createBound()
	{

		int left = initialBounds.x;
		int right = initialBounds.x + initialBounds.width;
		int top = initialBounds.y;
		int bottom = initialBounds.y + initialBounds.height;
		if (_type < 3)
		{
			top = _currentpoint.y;
		}
		if (_type > 1 && _type < 5)
		{
			right = _currentpoint.x;
		}
		if (_type > 3 && _type < 7)
		{
			bottom = _currentpoint.y;
		}
		if (_type == 0 || _type == 6 || _type == 7)
		{
			left = _currentpoint.x;
		}
		int width = right - left;
		int height = bottom - top;
		if (width < 0)
		{ 
			//限制最小宽度
			if(Math.abs(width)<MIN)
			{
				width = -MIN;
			}
			left += width;
			width = Math.abs(width);
		} else
		{
			//限制最小宽度
			width = Math.max(MIN, width);
		}
		if (height < 0)
		{ 
			//限制最小高度
			if(Math.abs(height)<MIN)
			{
				height = -MIN;
			}
			top += height;
			height = Math.abs(height);
		} 
		else
		{
			//限制最小高度
			height = Math.max(MIN, height);
		}
		return new Rectangle(left, top, width, height);
	}

	public static List<Rectangle2D> getReSizeRect(Rectangle2D rect)
	{
		List<Rectangle2D> rect2d = null;
		AbstractEditComponent EDIT = RibbonUpdateManager.Instance.getCurrentEditPanel();
		if (rect != null && EDIT != null)
		{
			rect.setFrame(rect.getX() / Constants.PRECISION, rect.getY()
					/ Constants.PRECISION, rect.getWidth()
					/ Constants.PRECISION, rect.getHeight()
					/ Constants.PRECISION);
			rect2d = new ArrayList<Rectangle2D>();
			double xhandlesize = EDIT.getPreviewScaleX() * HANDLESIZE;
			double yhandlesize = EDIT.getPreviewScaleY() * HANDLESIZE;
			double s2 = xhandlesize + yhandlesize;
			double left = rect.getX();
			double top = rect.getY();
			double w2 = rect.getX() + rect.getWidth() / 2 - xhandlesize;
			double h2 = rect.getY() + rect.getHeight() / 2 - yhandlesize;
			double right = rect.getX() + rect.getWidth() - s2;
			double bottom = rect.getY() + rect.getHeight() - s2;
			double width = 2 * xhandlesize;
			double height = 2 * yhandlesize;
			rect2d.add(new Rectangle2D.Double(left, top, width, height));
			rect2d.add(new Rectangle2D.Double(w2, top, width, height));
			rect2d.add(new Rectangle2D.Double(right, top, width, height));
			rect2d.add(new Rectangle2D.Double(right, h2, width, height));
			rect2d.add(new Rectangle2D.Double(right, bottom, width, height));
			rect2d.add(new Rectangle2D.Double(w2, bottom, width, height));
			rect2d.add(new Rectangle2D.Double(left, bottom, width, height));
			rect2d.add(new Rectangle2D.Double(left, h2, width, height));
		}
		return rect2d;
	}

	public void paintResizePort(Graphics g)
	{
		List<CellElement> selectelements = _editor.getSelectionModel()
				.getObjectSelection();
		r = createPortRectangle(selectelements);
		if (r != null && !r.isEmpty())
		{
			Graphics2D g2d = (Graphics2D) g;
			int size = r.size();
			for (int i = 0; i < size; i++)
			{
				Color old = g2d.getColor();
				g2d.setColor(color);
				PortRectangle pr = r.get(i);
				g2d.fill3DRect(pr.x, pr.y, pr.width, pr.height, true);
				g2d.setColor(old);
			}
		}
	}

	private List<PortRectangle> createPortRectangle(
			List<CellElement> selectelements)
	{
		List<PortRectangle> portr = new ArrayList<PortRectangle>();
		if (selectelements != null && !selectelements.isEmpty())
		{
			int size = selectelements.size();
			for (int i = 0; i < size; i++)
			{
				CellElement element = selectelements.get(i);
				portr.addAll(createPortrectforcell(element));

			}
		}
		return portr;
	}

	private List<PortRectangle> createPortrectforcell(CellElement element)
	{
		List<PortRectangle> portrects = new ArrayList<PortRectangle>();
		if (element != null
				&& ((element instanceof AbstractGraphics) || (element instanceof BlockContainer)))
		{
			Area area = element.getArea();
			if (area != null)
			{
				Rectangle2D viewport = area.getViewport();
				if (viewport != null)
				{
					Rectangle rect = _editor.toScreen(viewport, area);
					int s2 = 2 * HANDLESIZE;
					int left = rect.x - HANDLESIZE;
					int top = rect.y - HANDLESIZE;
					int w2 = rect.x + rect.width / 2 - HANDLESIZE;
					int h2 = rect.y + rect.height / 2 - HANDLESIZE;
					int right = rect.x + rect.width - HANDLESIZE;
					int bottom = rect.y + rect.height - HANDLESIZE;
					// Update control point positions
					Rectangle prect = new Rectangle(left, top, s2, s2);
					portrects.add(new PortRectangle(prect, element, LT));
					prect = new Rectangle(w2, top, s2, s2);
					portrects.add(new PortRectangle(prect, element, T));
					prect = new Rectangle(right, top, s2, s2);
					portrects.add(new PortRectangle(prect, element, RT));
					prect = new Rectangle(right, h2, s2, s2);
					portrects.add(new PortRectangle(prect, element, R));
					prect = new Rectangle(right, bottom, s2, s2);
					portrects.add(new PortRectangle(prect, element, RB));
					prect = new Rectangle(w2, bottom, s2, s2);
					portrects.add(new PortRectangle(prect, element, B));
					prect = new Rectangle(left, bottom, s2, s2);
					portrects.add(new PortRectangle(prect, element, LB));
					prect = new Rectangle(left, h2, s2, s2);
					portrects.add(new PortRectangle(prect, element, L));
				}
			}
		}
		return portrects;
	}

	private class PortRectangle extends Rectangle
	{
		CellElement _element;
		int _type;

		PortRectangle(Rectangle rect, CellElement element, int type)
		{
			super(rect);
			_element = element;
			_type = type;
		}

		CellElement getElement()
		{
			return _element;
		}

		int getType()
		{
			return _type;
		}
	}
}
