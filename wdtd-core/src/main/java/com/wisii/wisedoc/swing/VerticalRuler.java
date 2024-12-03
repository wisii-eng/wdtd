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
 * @VerticalRuler.java
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
import com.wisii.wisedoc.configure.ConfigureEvent;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.render.awt.viewer.MarginBean;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;

/**
 * 垂直刻度尺，根据当前面板的滚动情况， 以及当前的面板的显示比率来完成刻度 的显示
 * 
 * 作者：zhangqiang 创建日期：2007-6-15
 */
@SuppressWarnings("serial")
public class VerticalRuler extends Ruler
{

	/**
	 * 
	 * 设置刻度尺为固定宽度
	 * 
	 * @param
	 * @exception
	 */
	public VerticalRuler()
	{
		super();
		ConfigureUtil.addConfigureListener(this);
		if ("MPT".equalsIgnoreCase(ConfigureUtil.getUnit().toUpperCase()))
		{
			setMinimumSize(new Dimension(40, 1));
			setPreferredSize(new Dimension(40, 500000));
			setMaximumSize(new Dimension(40, 100000));
		} else
		{
			setMinimumSize(new Dimension(SIZE, 1));
			setPreferredSize(new Dimension(SIZE, 500000));
			setMaximumSize(new Dimension(SIZE, 1000000));
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
		// super.paintChildren(g);
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

		int width = getWidth();
		int top = ((Double) (beforePage + beforeBody)).intValue();
		int bottom = ((Double) (afterPage + afterBody)).intValue();
		int height = panel.getImageHeight() + shift + yoffset;
		int lineEnd = yoffset + panel.getImageHeight() - bottom;
		// System.out.println("width::"+width);
		// System.out.println("top::"+top);
		// System.out.println("bottom::"+bottom);
		// System.out.println("height::"+height);
		// System.out.println("shift::"+shift);
		// System.out.println("yoffset::"+yoffset);
		//System.out.println("panel.getImageHeight()::"+panel.getImageHeight());
		// System.out.println("lineEnd::"+lineEnd);
		// System.out.println("this height::"+this.getHeight());

		Graphics2D graphics2d = (Graphics2D) g;
		// 高度设置偏移量
		// Color oldcolor = g.getColor();
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
		double startX = starts + top;
		double scale = SystemManager.getMainframe().getEidtComponent()
				.getPreviewScaleY();
		double step = stepl * scale;
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
		// TODO Auto-generated method stub

	}

	// public void documentCaretUpdate(DocumentCaretEvent event)
	// {
	// PageViewportPanel newView = app.getCurrentPagePanel();
	// if (view != null && newView != null && !newView.equals(view))
	// {
	// int xoffsetview = view.getOffsetY();
	// int xoffsetnewView = newView.getOffsetY();
	// if (xoffsetview == xoffsetnewView)
	// {
	// int heightview = getWidth();
	// int heightnewView = getWidth();
	// double startPageview = 0;
	// double endPageview = 0;
	// MarginBean pageMarginview = view.getPageMargin();
	// MarginBean bodyMarginview = view.getBodyMargin();
	// if (pageMarginview != null)
	// {
	// startPageview = pageMarginview.getMarginTop();
	// endPageview = pageMarginview.getMarginBottom();
	// }
	// double startBodyview = 0;
	// double endBodyview = 0;
	// if (bodyMarginview != null)
	// {
	// startBodyview = bodyMarginview.getMarginTop();
	// endBodyview = bodyMarginview.getMarginBottom();
	// }
	//
	// int leftview = ((Double) (startPageview + startBodyview))
	// .intValue();
	// int rightview = ((Double) (endPageview + endBodyview))
	// .intValue();
	//
	// double startPagenewView = 0;
	// double endPagenewView = 0;
	// MarginBean pageMarginnewView = newView.getPageMargin();
	// MarginBean bodyMarginnewView = newView.getBodyMargin();
	// if (pageMarginnewView != null)
	// {
	// startPagenewView = pageMarginnewView.getMarginTop();
	// endPagenewView = pageMarginnewView.getMarginBottom();
	// }
	// double startBodynewView = 0;
	// double endBodynewView = 0;
	// if (bodyMarginnewView != null)
	// {
	// startBodynewView = bodyMarginnewView.getMarginTop();
	// endBodynewView = bodyMarginnewView.getMarginBottom();
	// }
	//
	// int leftnewView = ((Double) (startPagenewView +
	// startBodynewView))
	// .intValue();
	// int rightnewView = ((Double) (endPagenewView + endBodynewView))
	// .intValue();
	// if (!(heightnewView == heightview && leftnewView == leftview &&
	// rightnewView == rightview)
	// && heightnewView != 0)
	// {
	// repaint();
	// }
	// } else
	// {
	// repaint();
	// // }
	//
	// } else if (view == null && newView != null)
	// {
	// repaint();
	// }
	// view = app.getCurrentPagePanel();
	// }

	// public void mouseDragged(MouseEvent e)
	// {
	// Point current = e.getPoint();
	// double yPiont = current.getY();
	// int y = ((Number) yPiont).intValue();
	// if (oldPoint != null && oldPoint.getY() != current.getY())
	// {
	// double oldPointY = oldPoint.getY();
	// int oldy = ((Number) oldPointY).intValue();
	// int startX = 0;
	// int startY = y < oldy ? y - 2 : oldy - 2;
	// int width = getWidth();
	// int height = Math.abs(y - oldy) + 4;
	// repaint(1, startX, startY, width, height);
	// } else if (oldPoint == null)
	// {
	// int startX = 0;
	// int startY = y - 2;
	// int width = getWidth();
	// int height = 4;
	// repaint(1, startX, startY, width, height);
	// }
	// oldPoint = current;
	// }

	//
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

	// public void setStepAndGrap()
	// {
	// String unit = ConfigureUtil.getUnit().toUpperCase();
	// // double scale = SystemManager
	// // .getMainframe()
	// // .getEidtComponent()
	// // .getPreviewScaleX();
	// stepl = WisedocUtil.getPointofmm(10);
	// oneLongGrap = 10;
	// if ("M".equalsIgnoreCase(unit))
	// {
	// oneLongGrap = 0.01;
	// } else if ("DM".equalsIgnoreCase(unit))
	// {
	// oneLongGrap = 0.1;
	// } else if ("CM".equalsIgnoreCase(unit))
	// {
	// oneLongGrap = 1;
	// } else if ("IN".equalsIgnoreCase(unit))
	// {
	// oneLongGrap = 1;
	// stepl = stepl * 2.54;
	// } else if ("PT".equalsIgnoreCase(unit))
	// {
	// oneLongGrap = 100;
	// stepl = stepl * 2.54 * 100 / 72;
	// } else if ("MPT".equalsIgnoreCase(unit))
	// {
	// oneLongGrap = 100000;
	// stepl = stepl * 2.54 * 100 / 72;
	// }
	// }

	public void mouseRepaint(MouseEvent e)
	{
		Point current = e.getPoint();
		double yPiont = current.getY();
		int y = ((Number) yPiont).intValue();
		if (oldPoint != null)
		{
			double oldPointY = oldPoint.getY();
			int oldy = ((Number) oldPointY).intValue();
			int startX = 0;
			int startY = y < oldy ? y - 2 : oldy - 2;
			int width = getWidth();
			int height = Math.abs(y - oldy) + 4;
			repaint(1, startX, startY, width, height);
		} else
		{
			repaint();
		}
		oldPoint = current;
	}
}
