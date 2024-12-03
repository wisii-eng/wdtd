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

package com.wisii.wisedoc.swing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.AdjustmentEvent;

import javax.swing.JLabel;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.configure.ConfigureEvent;
import com.wisii.wisedoc.configure.ConfigureListener;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.render.awt.viewer.MarginBean;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.util.WisedocUtil;

public class Ruler extends JLabel implements ConfigureListener
{

	// 刻度尺宽度
	int SIZE = 18;

	// 滚动面板偏移量
	int shift;

	// 数字相对刻度长线的距离
	int DIGITOFFSET = 5;

	// 刻度线颜色
	Color linecolor = Color.BLACK;

	// 背景颜色
	Color backcolor = Color.GRAY;

	// 刻度游标颜色
	Color CursorColor = Color.DARK_GRAY;

	// 刻度游标所在点
	Point cursorpoint;

	Point oldPoint = null;

	// PreviewPanelAPP app;

	PageViewportPanel view = null;

	double stepl;

	double oneLongGrap;

	String unit = ConfigureUtil.getUnit().toUpperCase();

	/**
	 * 
	 * 设置刻度尺为固定宽度
	 * 
	 * @param
	 * @exception
	 */
	public Ruler()
	{

	}

	// /**
	// *
	// * 滚动面板滚动监听函数， 根据滚动的情况重新绘制刻度尺
	// *
	// * @param start
	// * ：
	// * @exception
	// */
	// public void adjustmentValueChanged(AdjustmentEvent e)
	// {
	// shift = e.getValue();
	// repaint();
	// }

	/*
	 * 
	 * 覆写绘制函数，绘制刻度线和刻度值
	 * 
	 * @param start： @exception
	 */
	protected void paintComponent(Graphics g)
	{
		super.paintChildren(g);
		if (view == null)
		{
			return;
		} else
		{
			paintPanel(g, view);
		}
	}

	protected void paintPanel(Graphics g, PageViewportPanel panel)
	{
		String unit = ConfigureUtil.getUnit().toUpperCase();
		int yoffset = panel.getOffsetPanel();
		MarginBean pageMargin = panel.getPageMargin();
		MarginBean bodyMargin = panel.getBodyMargin();
		double beforePage = 0;
		double afterPage = 0;
		if (pageMargin != null)
		{
			beforePage = pageMargin.getMarginTop();
			afterPage = pageMargin.getMarginBottom();
		}
		double beforeBody = 0;
		double afterBody = 0;
		if (bodyMargin != null)
		{
			beforeBody = bodyMargin.getMarginTop();
			afterBody = bodyMargin.getMarginBottom();
		}
		// Rectangle2D rect;
		// rect = panel.getBeforeMargin();
		// double before = 0;
		// if (rect != null)
		// {
		// before = rect.getHeight();
		// }
		// rect = panel.getAfterMargin();
		// double after = 0;
		// if (rect != null)
		// {
		// after = rect.getHeight();
		// }
		// rect = panel.getStartMargin();
		// double start = 0;
		// if (rect != null)
		// {
		// start = rect.getWidth();
		// }
		// rect = panel.getEndMargin();
		// double end = 0;
		// if (rect != null)
		// {
		// end = rect.getWidth();
		// }

		int width = getWidth();
		int top = ((Double) (beforePage + beforeBody)).intValue();
		int bottom = ((Double) (afterPage + afterBody)).intValue();
		int height = panel.getImageHeight() + shift + yoffset;
		int lineEnd = yoffset + panel.getImageHeight() - bottom;

		Graphics2D graphics2d = (Graphics2D) g;
		// 高度设置偏移量
		Color oldcolor = g.getColor();
		// 设置背景色
		graphics2d.setColor(backcolor);
		// 绘制背景
		graphics2d.fillRect(0, yoffset, width, top);
		graphics2d.fillRect(0, lineEnd, width, bottom);
		if (cursorpoint != null)
		{
			graphics2d.setColor(CursorColor);
			int center = cursorpoint.y;
			graphics2d.fillPolygon(new Polygon(new int[]
			{ 10, 15, 17, 17, 15 }, new int[]
			{ center, center - 5, center - 5, center + 5, center + 5 }, 5));
		}
		// graphics2d.translate(0, -shift + 1);
		// 设置前景色
		graphics2d.setColor(linecolor);
		double starts = yoffset;
		double scale = SystemManager.getMainframe().getEidtComponent()
				.getPreviewScaleY();
		double startX = starts + top;
		// int k2 = 1;
		double step = WisedocUtil.getPointofmm(10) * scale;
		double oneLongGrap = 10;

		if ("M".equalsIgnoreCase(unit))
		{
			oneLongGrap = 0.01;
		} else if ("DM".equalsIgnoreCase(unit))
		{
			oneLongGrap = 0.1;
		} else if ("CM".equalsIgnoreCase(unit))
		{
			oneLongGrap = 1;
		} else if ("IN".equalsIgnoreCase(unit))
		{
			oneLongGrap = 1;
			step = step * 2.54;
		} else if ("PT".equalsIgnoreCase(unit))
		{
			oneLongGrap = 100;
			step = step * 2.54 * 100 / 72;
		} else if ("MPT".equalsIgnoreCase(unit))
		{
			oneLongGrap = 100000;
			step = step * 2.54 * 100 / 72;
		}
		double shortStep = step / 5;
		drawDigits(graphics2d, startX + DIGITOFFSET, step, height, oneLongGrap);
		drawLongLines(graphics2d, startX, step, height);
		drawShortLines(graphics2d, startX, shortStep, height);
		drawDigitsBT(graphics2d, startX + DIGITOFFSET, step, starts,
				oneLongGrap);
		drawLongLinesBT(graphics2d, startX, step, starts);
		drawShortLinesBT(graphics2d, startX, shortStep, starts);
		graphics2d.setXORMode(Color.RED);
		if (oldPoint != null)
		{
			double oldPointX = oldPoint.getX();
			double oldPointY = oldPoint.getY();
			int x = ((Number) oldPointX).intValue();
			int y = ((Number) oldPointY).intValue();
			graphics2d.drawLine(0, y, x, y);
			graphics2d.setColor(oldcolor);
		}
	}

	/*
	 * 
	 * 绘制刻度值
	 * 
	 * @param start：开始位置，step：刻度间隔值，width：绘制宽度 stepDigit：刻度间隔数字 @exception
	 */
	private void drawDigits(Graphics2D g, double start, double step,
			double height, double stepDigit)
	{
		FontMetrics fontMetrics = g.getFontMetrics();
		int digit = 0;
		for (double y = start; y < height; y += step)
		{
			String text = String.valueOf(digit);
			float yy = (fontMetrics.getAscent() - fontMetrics.getDescent()) / 2;
			float xx = fontMetrics.stringWidth(text);
			g.drawString(text, xx >= SIZE ? 0 : (SIZE - xx) / 2, (float) (y
					+ yy + 1));
			digit += stepDigit;
			// g.translate(startx, starty);
			// g.rotate(-90);
			// g.drawString(text, 0, 20);
			// g.rotate(90);
			// g.translate(-startx, -starty);

		}
	}

	private void drawDigitsBT(Graphics2D g, double start, double step,
			double top, double stepDigit)
	{
		FontMetrics fontMetrics = g.getFontMetrics();
		int digit = 0;
		for (double y = start; y > top; y -= step)
		{
			String text = String.valueOf(digit);
			float yy = (fontMetrics.getAscent() - fontMetrics.getDescent()) / 2;
			float xx = fontMetrics.stringWidth(text);
			g.drawString(text, xx >= SIZE ? 0 : (SIZE - xx) / 2, (float) (y
					+ yy + 1));
			digit += stepDigit;
		}
	}

	/*
	 * 
	 * 绘制刻度短线
	 * 
	 * @param start：开始位置，step：刻度间隔值，width：绘制宽度 @exception
	 */
	private void drawShortLines(Graphics2D g, double start, double step,
			double height)
	{
		for (double y = start; y < height; y += step)
		{
			g
					.drawLine(SIZE - 5, (int) Math.round(y), SIZE, (int) Math
							.round(y));

		}

	}

	private void drawShortLinesBT(Graphics2D g, double start, double step,
			double top)
	{
		for (double y = start; y > top; y -= step)
		{
			g
					.drawLine(SIZE - 5, (int) Math.round(y), SIZE, (int) Math
							.round(y));

		}

	}

	/*
	 * 
	 * 绘制刻度长线
	 * 
	 * @param start：开始位置，step：刻度间隔值，width：绘制宽度 @exception
	 */
	private void drawLongLines(Graphics2D g, double start, double step,
			double height)
	{
		for (double y = start; y < height; y += step)
		{
			g.drawLine(SIZE - 10, (int) Math.round(y), SIZE, (int) Math
					.round(y));
		}

	}

	private void drawLongLinesBT(Graphics2D g, double start, double step,
			double top)
	{
		for (double y = start; y > top; y -= step)
		{
			g.drawLine(SIZE - 10, (int) Math.round(y), SIZE, (int) Math
					.round(y));
		}

	}

	// public void configurationChanged(ConfigureEvent configurationenent)
	// {
	// if (configurationenent.isConfigurationElementChanged(new ChangedItem(
	// "UnitOfMeasure", "", "")))
	// {
	// repaint();
	// }
	// }

	// public void drawRulerCursor(Point point)
	// {
	// int center = point.y + 1 - shift;
	// cursorpoint = new Point(10, center);
	// repaint(0, center - 4, SIZE, 10);
	//
	// }
	//
	// public void removeRulerCursor(Point point)
	// {
	// int center = point.y + 1 - shift;
	// cursorpoint = null;
	// repaint(0, center - 5, SIZE, 10);
	// }

	@Override
	public void configureChanged(ConfigureEvent event)
	{
		setUnit();
		if (!getUnit().equals(""))
		{
			repaint();
		}
	}

	//
	// public void mouseDragged(MouseEvent e)
	// {
	// // Point current = e.getPoint();
	// // double yPiont = current.getY();
	// // int y = ((Number) yPiont).intValue();
	// // if (oldPoint != null && oldPoint.getY() != current.getY())
	// // {
	// // double oldPointY = oldPoint.getY();
	// // int oldy = ((Number) oldPointY).intValue();
	// // int startX = 0;
	// // int startY = y < oldy ? y - 2 : oldy - 2;
	// // int width = getWidth();
	// // int height = Math.abs(y - oldy) + 4;
	// // repaint(1, startX, startY, width, height);
	// // } else if (oldPoint == null)
	// // {
	// // int startX = 0;
	// // int startY = y - 2;
	// // int width = getWidth();
	// // int height = 4;
	// // repaint(1, startX, startY, width, height);
	// // }
	// // oldPoint = current;
	// }
	//
	// //
	// // @Override
	// public void mouseMoved(MouseEvent e)
	// {
	// Point current = e.getPoint();
	// double yPiont = current.getY();
	// int y = ((Number) yPiont).intValue();
	// if (oldPoint != null)
	// {
	// double oldPointY = oldPoint.getY();
	// int oldy = ((Number) oldPointY).intValue();
	// int startX = 0;
	// int startY = y < oldy ? y - 2 : oldy - 2;
	// int width = getWidth();
	// int height = Math.abs(y - oldy) + 4;
	// repaint(1, startX, startY, width, height);
	// } else
	// {
	// repaint();
	// }
	// oldPoint = current;
	// }

	public int getShift()
	{
		return shift;
	}

	// 滚动条滚动先设置shift再重绘
	public void setShift(int shift)
	{
		this.shift = shift;
	}

	public PageViewportPanel getView()
	{
		return view;
	}

	// 面板改变先设置view再重绘
	public void setView(PageViewportPanel view)
	{
		this.view = view;
	}

	public Point getCursorpoint()
	{
		return cursorpoint;
	}

	// 鼠标移动先设置cursorpoint再重绘
	public void setCursorpoint(Point cursorpoint)
	{
		this.cursorpoint = cursorpoint;
	}

	public double getStepl()
	{
		return stepl;
	}

	public void setStepl(double stepl)
	{
		this.stepl = stepl;
	}

	public void setStepAndGrap()
	{
		String unit = getUnit();
		stepl = WisedocUtil.getPointofmm(10);
		oneLongGrap = 10;
		if ("M".equalsIgnoreCase(unit))
		{
			oneLongGrap = 0.01;
		} else if ("DM".equalsIgnoreCase(unit))
		{
			oneLongGrap = 0.1;
		} else if ("CM".equalsIgnoreCase(unit))
		{
			oneLongGrap = 1;
		} else if ("IN".equalsIgnoreCase(unit))
		{
			oneLongGrap = 1;
			stepl = stepl * 2.54;
		} else if ("PT".equalsIgnoreCase(unit))
		{
			oneLongGrap = 100;
			stepl = stepl * 2.54 * 100 / 72;
		} else if ("MPT".equalsIgnoreCase(unit))
		{
			oneLongGrap = 100000;
			stepl = stepl * 2.54 * 100 / 72;
		}
	}

	public Point getOldPoint()
	{
		return oldPoint;
	}

	public void setOldPoint(Point oldPoint)
	{
		this.oldPoint = oldPoint;
	}

	public void shiftRepaint(AdjustmentEvent e)
	{
		setShift(e.getValue());
		repaint();
	}

	public void viewRepaint(PageViewportPanel view)
	{
		setView(view);
		repaint();
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit()
	{
		this.unit = ConfigureUtil.getUnit().toUpperCase();
	}
}
