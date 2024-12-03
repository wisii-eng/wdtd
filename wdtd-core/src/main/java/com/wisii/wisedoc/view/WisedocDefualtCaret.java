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
 * @WisedocDefualtCaret.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.EditSetAble;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：自定义光标样式
 * 
 * 作者：李晓光 创建日期：2009-2-18
 */
@SuppressWarnings("serial")
public class WisedocDefualtCaret extends WisedocCaret {
	// 【添加：START】 by 李晓光 2010-1-4
	//判断拖拽数据，是否来自xml结构节点。如果是：True,否则：False
	private boolean bindNodeSource = Boolean.FALSE;
	//判断是否采用Area光标形式。
	private boolean areaCaret = Boolean.FALSE;
	// 【添加：END】 by 李晓光 2010-1-4

	public WisedocDefualtCaret(final AbstractEditComponent comp) {
		this.painter = comp;
		setCaretColor(Color.GRAY);
	}

	public boolean isBindNodeSource() {
		return bindNodeSource;
	}

	public void setBindNodeSource(boolean bindNodeSource) {
		this.bindNodeSource = bindNodeSource;
	}

	public void setPosition(final DocumentPosition position) {
		// 【添加：START】 by 李晓光 2010-1-4
		//判断是否采用Area光标样式。
		areaCaret = isAreaCaret(position);
		// 【添加：END】 by 李晓光 2010-1-4
		painter.requestFocus();
		if (position == null) {
			stopCaret();
			shape = null;
		}
		{
			this.position = position;
			if (!isNull(position)) {
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
		
	}

	public void unload() {
		setVisible(Boolean.FALSE);
		component = null;
		painter = null;
		position = null;
		setActive(Boolean.FALSE);
		setVisible(Boolean.FALSE);
	}

	/**
	 * 停掉当前的光标，也就是在界面中不显示光标。
	 */
	public void stopCaret() {
		setActive(Boolean.FALSE);
		setVisible(Boolean.FALSE);
		repaint();
	}
	/**
	 * add by zq
	 * 停掉当前的光标，不重绘
	 */
	public void stop() {
		setActive(Boolean.FALSE);
		setVisible(Boolean.FALSE);
	}
	/**
	 * 重新启动光标。
	 */
	public void restartCaret(){		
		setActive(Boolean.TRUE);
		setVisible(Boolean.TRUE);
		repaint();
	}

	public void setShape(Shape shape) {
		if (this.shape == shape) {
			return;
		}
		this.shape = shape;
		update();
	}
	
	private void update() {
		setVisible(Boolean.TRUE);
		repaint();
	}
	
	public void paintCaret(final Graphics g) {		
		if (!isVisible() || isNull(shape)) {
			return;
		}
		final Rectangle shape = getCaretRectangle();
		if (!this.equals(shape)) {
			repaint(this);
		}

		final Graphics2D g2d = (Graphics2D) g.create();
		g2d.setPaint(getCaretColor());
		/* 如果是非封闭图像【Line2D】，就的用draw方法绘制 */
		if(areaCaret){
			BasicStroke s = new BasicStroke(2.0F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[] { 1, 3, 5 }, 0);
			g2d.setStroke(s);
			g2d.draw(shape);
		}else{
			g2d.fill(shape);
		}
		this.setRect(shape);
		g2d.dispose();
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
		final double wight = source.getWidth() / factor;

		final Point p0 = SwingUtilities.convertPoint(component, (int) x,
				(int) y, painter.getGridPanel());// painter.gridPanel by 李晓光
		if(areaCaret){
			r.setRect(p0.getX(), p0.getY(), wight, height);
		}else{
			r.setRect(p0.getX(), p0.getY(), getCaretWidht(), height);
		}
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
		if (LogUtil.canDebug()) {
			LogUtil.debug("页索引：", bean.getPageIndex());
			LogUtil.debug("光标位置：", bean.getViewport());
		}
		component = painter.getPageOf(bean.getPageIndex());
		
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
		// 【添加：START】 by 李晓光 2010-1-4
		if(areaCaret && !pos.isStartPos()){
			r.setRect(r.getX() - r.getWidth(), r.getY(), r.getWidth(), r.getHeight());
		}
		// 【添加：END】 by 李晓光 2010-1-4
		
		if (isNull(r) || isNull(component)) {
			return r;
		}
		final double scaleX = component.getPreviewScaleX();
		final double scaleY = component.getPreviewScaleY();
		final int x = component.getOffsetX();
		final int y = component.getOffsetY();
		r = LocationConvert.getScaleRectangle(r, scaleX, scaleY);
		double[] offset = { 0, 0 };
		if (!(pos.getLeafElement() instanceof TableCell)) {
			offset = LocationConvert.getOffsetOfBlocks(pos);
		}
		double offsetX = offset[0] * scaleX;
		offsetX += r.getX();
		offsetX += x * Constants.PRECISION;
		double offsetY = offset[1] * scaleY;
		offsetY += r.getY();
		offsetY += y * Constants.PRECISION;
		r.setRect(offsetX, offsetY, r.getWidth(), r.getHeight());
		return r;
	}
	// 【添加：START】 by 李晓光 2010-1-4
	private final boolean isAreaCaret(DocumentPosition pos){
		if(!isBindNodeSource() || pos == null || !pos.isOn())
			return Boolean.FALSE;
		Element e = pos.getLeafElement();
		
		return (e instanceof EditSetAble) && !(e instanceof TextInline);
	}	
	// 【添加：END】 by 李晓光 2010-1-4
}
