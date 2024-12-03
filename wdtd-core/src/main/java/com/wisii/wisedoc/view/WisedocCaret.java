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
 * @WisedocCaret.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;

/**
 * 类功能描述：自定义光标样式
 * 
 * 作者：李晓光 创建日期：2008-9-11
 */
@SuppressWarnings("serial")
public class WisedocCaret extends Rectangle {
	
	/* 光标的默认宽度【像素】 */
	private final static int DEFAULT_WIDTH = 2;
	/* 光标绘制的颜色 */
	private Paint caretColor = Color.BLACK;// Color.BLUE;//Color.RED;
	/* 以毫秒为单位。如果为零，则插入符不闪烁 */
	private int blinkRate = 500;
	/* 光标与行增长方向的夹角【弧度】 */
	private double angle = 0;
	/* 光标样式 */
	protected Shape shape = null;
	/* 光标样式变化【旋转、平移】 */
	@SuppressWarnings("unused")
	private final AffineTransform transform = new AffineTransform();
	/* 定义光标宽度【像素】 */
	private int caretWidth = DEFAULT_WIDTH;
	/* 定义当前页 */
	protected PageViewportPanel component = null;
	/* 定义需要绘制光标的控件 */
	protected AbstractEditComponent painter = null;
	/* 指示光标是否可见 */
	private boolean visible = Boolean.TRUE;
	private boolean active = Boolean.FALSE;
	/* 定时绘制光标用定时器 */
	private Timer timer = null;
	/* 描述位置信息 */
	protected DocumentPosition position = null;
	/* 定义Timer中处理的事件 */
	private final ActionListener handler = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
			visible = !visible;
			repaint();
		}
	};

	public WisedocCaret() {
		this(null);
	}

	public WisedocCaret(final AbstractEditComponent comp) {
		this.painter = comp;
	}

	public DocumentPosition getPosition() {
		return position;
	}

	public void setPosition(final DocumentPosition position) {		
		painter.requestFocus();
		/*position = CaretLocationConvert.getPosition(position, PositionType.PAGE_DOWN);*/
		if (position == null) {
			/*if (timer != null && timer.isRunning()) {
				timer.stop();
			}*/
			stopCaret();
			shape = null;
		}
		{
			this.position = position;
			if (isNull(timer) && !isNull(position)) {
				load();
			}
			try{
			Shape s = getShapeWithPosition(position);
			s = getCaretShape(s);			
			setShape(s);
			//add by zq 增加了在主界面的状态栏上显示当前光标所在页的页号的功能
			if(component!=null)
			{
				Document doc = SystemManager.getCurruentDocument();
			    int psindex = -1;
				if(doc!=null&&doc.getChildCount()>1&&position!=null)
				{
					Element element = position.getLeafElement();
					Element parent = element.getParent();
					while(parent!=null&&!(parent instanceof Document))
					{
						element = parent;
						parent = element.getParent();
					}
					if(parent instanceof Document)
					{
						psindex = parent.getIndex(element)+1;
					}
				}
				SystemManager.getMainframe().notifyPageIndexChanged(component.getPage()+1,psindex);
			}
			//zq add end
			}
			catch (Exception e) {
				LogUtil.debugException("拖拽解析时出错", e);
				e.printStackTrace();
			}
		}
	}

	/** 光标位置改变后，保证改变后的位置在视窗中可见。 */
	public synchronized void updateViewShape() {
		Rectangle rect = getCaretRectangle();
		if (!isNull(rect) && !(isNull(painter))) {
			rect = (Rectangle) rect.clone();
			final Rectangle bounds = painter.getVisibleRect();
			if (!bounds.contains(rect)) {
				final int x = (rect.x > bounds.width)? rect.x : 0;
				rect.setBounds(x, rect.y - rect.height, bounds.width, 3 * rect.height);
				painter.scrollRectToVisible(rect);
			}
		}
	}

	public void load() {
		if (isNull(timer)) {
			initBlinkTimer(getBlinkRate());
		}
	}

	public void unload() {
		setVisible(Boolean.FALSE);
		component = null;
		painter = null;
		position = null;
		if (!isNull(timer)) {
			timer.stop();
			setActive(Boolean.FALSE);
			setVisible(Boolean.FALSE);
		}
	}

	/**
	 * 停掉当前的光标，也就是在界面中不显示光标。
	 */
	public void stopCaret() {
		if (isNull(timer)) {
			return;
		}
		if (timer.isRunning()) {
			setActive(Boolean.FALSE);
			setVisible(Boolean.FALSE);
			timer.stop();
			repaint();
		}
	}
	/**
	 * add by zq
	 * 停掉当前的光标，不重绘
	 */
	public void stop() {
		if (isNull(timer)) {
			return;
		}
		if (timer.isRunning()) {
			setActive(Boolean.FALSE);
			setVisible(Boolean.FALSE);
			timer.stop();
		}
	}
	/**
	 * 重新启动光标。
	 */
	public void restartCaret(){
		if(isNull(timer) || timer.isRunning()) {
			return;
		}
		setActive(Boolean.TRUE);
		setVisible(Boolean.TRUE);
		timer.restart();
		repaint();
	}
	public boolean isActive() {
		return active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(final boolean visible) {
		this.visible = visible;
	}

	private void initBlinkTimer(final int blink) {
		if (!isNull(timer)) {
			timer.stop();
			timer.setDelay(blink);
			timer.setInitialDelay(blink);
		} else {
			timer = new Timer(blink, handler);
			timer.setRepeats(Boolean.TRUE);
		}
	}

	public int getCaretWidht() {
		return caretWidth;
	}

	public void setCaretWidht(int caretWidth) {
		if (caretWidth < 0) {
			caretWidth = DEFAULT_WIDTH;
		}
		this.caretWidth = caretWidth;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(final Shape shape) {
		if (this.shape == shape) {
			return;
		}
		this.shape = shape;
		update();
	}

	private void update() {
		if (isNull(shape)) {
			timer.stop();
			return;
		}
		if (timer.isRunning()) {
			timer.stop();
		}
		setVisible(Boolean.TRUE);
		repaint();

		timer.restart();
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(final double angle) {
		this.angle = angle;
	}

	public int getBlinkRate() {
		return blinkRate;
	}

	public void setBlinkRate(final int blinkRate) {
		if (blinkRate == 0) {
			if (!isNull(timer)) {
				timer.stop();
				setVisible(Boolean.TRUE);
			}
		} else if (this.blinkRate != blinkRate) {
			initBlinkTimer(blinkRate);
			this.blinkRate = blinkRate;
		}
	}

	public Paint getCaretColor() {
		return caretColor;
	}

	public void setCaretColor(final Paint caretColor) {
		this.caretColor = caretColor;
	}
	
	public void paintCaret(final Graphics g) {
		if (!isVisible() || isNull(shape)) {
			return;
		}
		if(timer.isRunning()) {
			timer.stop();
		}
		final Rectangle shape = getCaretRectangle();
		if (!this.equals(shape)) {
			repaint(this);
		}

		final Graphics2D g2d = (Graphics2D) g.create();
		g2d.setPaint(getCaretColor());
		/* 如果是非封闭图像【Line2D】，就的用draw方法绘制 */
		
		g2d.fill(shape);
		this.setRect(shape);
		g2d.dispose();
		timer.restart();
	}

	public void repaint() {
		repaint(shape);
	}

	/**
	 * 释放资源，销毁当前光标位置信息。
	 */
	public void destroy() {
		position = null;
		shape = null;
	}

	private void repaint(final Shape shape) {
		if (isNull(shape) || isNull(painter)) {
			return;
		}

		final Rectangle r = shape.getBounds();
		if (isNull(r)) {
			return;
		}

		painter.getGridPanel().repaint(r);// new by 李晓光 2009-2-18
	}

	/***
	 * 获得光标Shape的Bound信息【坐标系：GridPanel】
	 * 
	 * @return 返回光标的Shape的Bound信息
	 */
	private Rectangle getCaretRectangle() {
		if (isNull(shape)) {
			return null;
		}

		return shape.getBounds();
	}

	/** 获得光标可直接用于显示的Shape */
	private synchronized Shape getCaretShape(final Shape shape) {
		Rectangle rect = null;
		if (isNull(shape)) {
			return rect;
		}

		rect = shape.getBounds();

		rect = getRectangle2D(Constants.PRECISION, rect);

		return rect;
	}

	/** 把单位是mpt的矩形信息，放缩为当前显示面板的矩形信息 */
	private Rectangle getRectangle2D(final double factor, final Rectangle source) {
		final Rectangle r = source;
		if (isNull(source)) {
			return r;
		}

		final double x = (source.getX()) / factor;
		final double y = (source.getY()) / factor;
		final double height = source.getHeight() / factor;

		final Point p0 = SwingUtilities.convertPoint(component, (int) x,
				(int) y, painter.getGridPanel());// painter.gridPanel by 李晓光
													// 2009-2-18
		r.setRect(p0.getX(), p0.getY(), getCaretWidht(), height);
		return r;
	}

	/** 把位置信息转换为单位是mpt的Shape信息 */
	public Shape getShapeWithPosition(final DocumentPosition pos) {
		PositionBean bean = null;
		Rectangle2D r = null;
		if (isNull(pos)) {
			return r;
		}
		bean = LocationConvert.getCaretWithPosition(pos, painter);
		/*System.out.println("bean = " + bean.getViewport());*/
		if (LogUtil.canDebug()) {
			LogUtil.debug("页索引：", bean.getPageIndex());
			LogUtil.debug("光标位置：", bean.getViewport());
		}
		/*System.out.println("page index = " + bean.getPageIndex());*/
		component = painter.getPageOf(bean.getPageIndex());
		/*System.out.println("valid = " + component.isValid());
		System.out.println("comp = " + component);*/
		/*System.out.println("valid 0 = " + component.isValid());*/
		if (isNull(component)) {
			return r;
		}
		if(!component.isValid()) {
			painter.validate();
		}
		painter.setCurrentPagePanel(component);
		// 【修改】by 李晓光 2008-12-22
		final Shape shape = bean.getViewport();
		if (isNull(shape)) {
			return r;
		}
		r = shape.getBounds2D();
		
		if (isNull(r) || isNull(component)) {
			return r;
		}
		final double scaleX = component.getPreviewScaleX();
		final double scaleY = component.getPreviewScaleY();
		final int x = component.getOffsetX();
		final int y = component.getOffsetY();
		r = LocationConvert.getScaleRectangle(r, scaleX, scaleY);
		/*System.out.println("scale bean = " + r);*/
		double[] offset = { 0, 0 };
		if (!(pos.getLeafElement() instanceof TableCell)) {
			offset = LocationConvert.getOffsetOfBlocks(pos);
		}
		/*System.out.println("offset = " + offset[0] + "  " + offset[1]);
		System.out.println("x = " + x + "  y = " + y);*/
		double offsetX = offset[0] * scaleX;
		offsetX += r.getX();
		offsetX += x * Constants.PRECISION;
		double offsetY = offset[1] * scaleY;
		offsetY += r.getY();
		offsetY += y * Constants.PRECISION;
		r.setRect(offsetX, offsetY, r.getWidth(), r.getHeight());
		/*System.out.println("result = " + r);*/
		return r;
	}
}
