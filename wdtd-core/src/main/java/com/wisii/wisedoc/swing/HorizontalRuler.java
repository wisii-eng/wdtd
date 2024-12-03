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
 * @HorizontalRuler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.configure.ChangedItem;
import com.wisii.wisedoc.configure.ConfigureEvent;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.render.awt.viewer.MarginBean;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;

/**
 * 水平刻度尺，根据当前面板的滚动情况， 以及当前的面板的显示比率来完成刻度 的显示 作者：zhangqiang 创建日期：2007-6-15
 */
@SuppressWarnings("serial")
public class HorizontalRuler extends Ruler
{

	/**
	 * 
	 * 设置刻度尺为固定高度
	 * 
	 * @param
	 * @exception
	 */
	public HorizontalRuler()
	{
		super();
		ConfigureUtil.addConfigureListener(this);
		if ("MPT".equalsIgnoreCase(ConfigureUtil.getUnit().toUpperCase()))
		{
			setMinimumSize(new Dimension(1, 40));
			setPreferredSize(new Dimension(50000, 40));
			setMaximumSize(new Dimension(10000, 40));
		} else
		{
			setMinimumSize(new Dimension(1, SIZE));
			setPreferredSize(new Dimension(50000, SIZE));
			setMaximumSize(new Dimension(100000, SIZE));
		}
		setStepAndGrap();
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
		MarginBean pageMargin = panel.getPageMargin();
		MarginBean bodyMargin = panel.getBodyMargin();
		int xoffset = panel.getOffsetX();
		double startPage = 0;
		double endPage = 0;
		if (pageMargin != null)
		{
			startPage = pageMargin.getMarginLeft();
			endPage = pageMargin.getMarginRight();
		}

		double startBody = 0;
		double endBody = 0;
		if (bodyMargin != null)
		{
			startBody = bodyMargin.getMarginLeft();
			endBody = bodyMargin.getMarginRight();
		}

		// 获得宽度值

		int height = getHeight();
		int left = ((Double) (startPage + startBody)).intValue();
		int right = ((Double) (endPage + endBody)).intValue();
		int width = panel.getImageWidth() + xoffset;
		int lineEnd = panel.getImageWidth() + xoffset - right;
		Graphics2D graphics2d = (Graphics2D) g;
		// 设置宽度偏移量

		// Color oldcolor = g.getColor();
		// 设置背景色
		graphics2d.setColor(backcolor);
		double startX = xoffset + left;
		// 绘制背景
		graphics2d.fillRect(xoffset, 0, left, height);
		graphics2d.fillRect(lineEnd, 0, right, height);
		if (cursorpoint != null)
		{
			graphics2d.setColor(CursorColor);
			int center = cursorpoint.x;
			graphics2d.fillPolygon(new Polygon(new int[]
			{ center, center - 5, center - 5, center + 5, center + 5 },
					new int[]
					{ 10, 15, 17, 17, 15 }, 5));
		}
		// 设置前景色
		graphics2d.setColor(linecolor);

		double scale = SystemManager.getMainframe().getEidtComponent()
				.getPreviewScaleX();
		double step = stepl * scale;
		double shortStep = step / 5;
		drawDigits(graphics2d, startX + DIGITOFFSET, step, width, oneLongGrap);
		drawLongLines(graphics2d, startX, step, width);
		drawShortLines(graphics2d, startX, shortStep, width);
		drawDigitsRL(graphics2d, startX + DIGITOFFSET, step, xoffset,
				oneLongGrap);
		drawLongLinesRL(graphics2d, startX, step, xoffset);
		drawShortLinesRL(graphics2d, startX, shortStep, xoffset);
		graphics2d.setXORMode(Color.RED);
		if (oldPoint != null)
		{
			double oldPointX = oldPoint.getX();
			double oldPointY = oldPoint.getY();
			int x = ((Number) oldPointX).intValue();
			int y = ((Number) oldPointY).intValue();
			graphics2d.drawLine(x, 0, x, y);
			// graphics2d.setColor(oldcolor);
		}
	}

	/*
	 * 
	 * 绘制刻度值
	 * 
	 * @param start：开始位置，step：刻度间隔值，width：绘制宽度 stepDigit：刻度间隔数字 @exception
	 */
	private void drawDigits(Graphics2D g, double start, double step,
			double width, double stepDigit)
	{
		FontMetrics fontMetrics = g.getFontMetrics();
		int digit = 0;
		for (double x = start; x < width; x += step)
		{
			int xInt = (int) Math.round(x);
			String text = String.valueOf(digit);
			int xx = fontMetrics.stringWidth(text) / 2;
			if (x - shift >= SIZE)
			{
				g.drawString(text, xInt - xx, SIZE / 2);
			}
			digit += stepDigit;
		}
	}

	private void drawDigitsRL(Graphics2D g, double start, double step,
			double left, double stepDigit)
	{
		FontMetrics fontMetrics = g.getFontMetrics();
		int digit = 0;
		for (double x = start; x > left; x -= step)
		{
			int xInt = (int) Math.round(x);
			String text = String.valueOf(digit);
			int xx = fontMetrics.stringWidth(text) / 2;
			g.drawString(text, xInt - xx, SIZE / 2);
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
			double width)
	{
		for (double x = start; x < width; x += step)
		{
			if (x - shift >= SIZE)
			{
				g.drawLine((int) Math.round(x), SIZE - 5, (int) Math.round(x),
						SIZE);
			}
		}
	}

	private void drawShortLinesRL(Graphics2D g, double start, double step,
			double left)
	{
		for (double x = start; x > left; x -= step)
		{

			g
					.drawLine((int) Math.round(x), SIZE - 5, (int) Math
							.round(x), SIZE);

		}
	}

	/*
	 * 
	 * 绘制刻度长线
	 * 
	 * @param start：开始位置，step：刻度间隔值，width：绘制宽度 @exception
	 */
	private void drawLongLines(Graphics2D g, double start, double step,
			double width)
	{
		for (double x = start; x < width; x += step)
		{
			if (x - shift >= SIZE)
			{
				g.drawLine((int) Math.round(x), 8, (int) Math.round(x), SIZE);
			}
		}
	}

	private void drawLongLinesRL(Graphics2D g, double start, double step,
			double left)
	{
		for (double x = start; x > left; x -= step)
		{
			g.drawLine((int) Math.round(x), 8, (int) Math.round(x), SIZE);
		}
	}

	// public void drawRulerCursor(Point point)
	// {
	// int center = point.x + SIZE + 1 - shift;
	// cursorpoint = new Point(center, 10);
	// repaint(center - 5, 0, 10, SIZE);
	//
	// }
	//
	// public void removeRulerCursor(Point point)
	// {
	// int center = point.x + 1 + SIZE - shift;
	// cursorpoint = null;
	// repaint(center - 5, 0, 10, SIZE);
	// }

	@Override
	public void configureChanged(ConfigureEvent event)
	{
		if (event.isConfigurationElementChanged(new ChangedItem(
				"UnitOfMeasure", "", "")))
		{
			repaint();
		}

	}

	// public void documentCaretUpdate(DocumentCaretEvent event)
	// {
	// PageViewportPanel newView = app.getCurrentPagePanel();
	// if (view != null && newView != null && !newView.equals(view))
	// {
	// // int xoffsetview = view.getOffsetX();
	// // int xoffsetnewView = newView.getOffsetX();
	// // if (xoffsetview == xoffsetnewView)
	// // {
	// // int heightview = getHeight();
	// // int heightnewView = getHeight();
	// // double startPageview = 0;
	// // double endPageview = 0;
	// // MarginBean pageMarginview = view.getPageMargin();
	// // MarginBean bodyMarginview = view.getBodyMargin();
	// // if (pageMarginview != null)
	// // {
	// // startPageview = pageMarginview.getMarginLeft();
	// // endPageview = pageMarginview.getMarginRight();
	// // }
	// // double startBodyview = 0;
	// // double endBodyview = 0;
	// // if (bodyMarginview != null)
	// // {
	// // startBodyview = bodyMarginview.getMarginLeft();
	// // endBodyview = bodyMarginview.getMarginRight();
	// // }
	// //
	// // int leftview = ((Double) (startPageview + startBodyview))
	// // .intValue();
	// // int rightview = ((Double) (endPageview + endBodyview))
	// // .intValue();
	// //
	// // double startPagenewView = 0;
	// // double endPagenewView = 0;
	// // MarginBean pageMarginnewView = newView.getPageMargin();
	// // MarginBean bodyMarginnewView = newView.getBodyMargin();
	// // if (pageMarginnewView != null)
	// // {
	// // startPagenewView = pageMarginnewView.getMarginLeft();
	// // endPagenewView = pageMarginnewView.getMarginRight();
	// // }
	// // double startBodynewView = 0;
	// // double endBodynewView = 0;
	// // if (bodyMarginnewView != null)
	// // {
	// // startBodynewView = bodyMarginnewView.getMarginLeft();
	// // endBodynewView = bodyMarginnewView.getMarginRight();
	// // }
	// //
	// // int leftnewView = ((Double) (startPagenewView +
	// // startBodynewView))
	// // .intValue();
	// // int rightnewView = ((Double) (endPagenewView + endBodynewView))
	// // .intValue();
	// // if (!(heightnewView == heightview && leftnewView == leftview &&
	// // rightnewView == rightview)
	// // && heightnewView != 0)
	// // {
	// // repaint();
	// // }
	// // } else
	// // {
	// repaint();
	// // }
	// } else if (view == null && newView != null)
	// {
	// repaint();
	// }
	// view = app.getCurrentPagePanel();
	// }

	// @Override
	// public void mouseDragged(java.awt.event.MouseEvent e)
	// {
	// // Point current = e.getPoint();
	// // double xPiont = current.getX();
	// // int x = ((Number) xPiont).intValue();
	// // if (oldPoint != null && oldPoint.getX() != current.getX())
	// // {
	// // double oldPointX = oldPoint.getX();
	// // int oldx = ((Number) oldPointX).intValue();
	// // int startX = x < oldx ? x - 2 : oldx - 2;
	// // int startY = 0;
	// // int width = Math.abs(x - oldx) + 4;
	// // int height = getHeight();
	// // repaint(1, startX, startY, width, height);
	// // } else if (oldPoint == null)
	// // {
	// // int startX = x - 2;
	// // int startY = 0;
	// // int width = 4;
	// // int height = getHeight();
	// // repaint(1, startX, startY, width, height);
	// // }
	// // oldPoint = current;
	// }
	// @Override
	// public void mouseMoved(java.awt.event.MouseEvent e)
	// {
	// Point current = e.getPoint();
	// double xPiont = current.getX();
	// int x = ((Number) xPiont).intValue();
	// if (oldPoint != null)
	// {
	// double oldPointX = oldPoint.getX();
	// int oldx = ((Number) oldPointX).intValue();
	// int startX = x < oldx ? x - 2 : oldx - 2;
	// int startY = 0;
	// int width = Math.abs(x - oldx) + 4;
	// int height = getHeight();
	// repaint(1, startX, startY, width, height);
	// } else
	// {
	// repaint();
	// }
	// oldPoint = current;
	// }
	public void mouseRepaint(MouseEvent e)
	{
		Point current = e.getPoint();
		double xPiont = current.getX();
		int x = ((Number) xPiont).intValue();
		if (oldPoint != null)
		{
			double oldPointX = oldPoint.getX();
			int oldx = ((Number) oldPointX).intValue();
			int startX = x < oldx ? x - 2 : oldx - 2;
			int startY = 0;
			int width = Math.abs(x - oldx) + 4;
			int height = getHeight();
			repaint(1, startX, startY, width, height);
		} else
		{
			repaint();
		}
		oldPoint = current;
	}
}
