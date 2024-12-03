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
 * @SVGSelectMouseHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.select;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.AreaTreeObject.AreaKind;
import com.wisii.wisedoc.area.inline.Viewport;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.LocationConvert;
import com.wisii.wisedoc.view.SVGLocationConvert;
import com.wisii.wisedoc.view.SVGLocationConvert.Orientations;
import com.wisii.wisedoc.view.mousehandler.SelectHandler;

/**
 * 类功能描述：根据捕获的鼠标事件，判读处理进行SVG图形选择。
 * 
 * 作者：李晓光 创建日期：2009-3-4
 */
public class SVGSelectMouseHandler extends SelectHandler {
	@Override
	public boolean isInControl(MouseEvent e) {
		/*Point p = e.getPoint();
		Viewport view = getViewport(p);
		if(view == null)
			return Boolean.FALSE;
		
		return  (view != null) && view.isFigure();*/ // 【删除】by 李晓光 2009-3-30
		
		LocationConvert convert = _editor.getCurrentConvert(e.getPoint());
		if(isNull(convert))
			return Boolean.FALSE;
		AreaKind kind = convert.getCurrentAreaKind(e);
		return (kind == AreaKind.SVG_FIGURE);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Viewport view = getViewport(e.getPoint());
		if(view == null || !view.isFigure()){
			super.mouseMoved(e);
			return;
		}
		Canvas canvas = (Canvas)view.getSource();
		List<CellElement> svgs = canvas.getAllChildren();
		AbstractSVG svg = null;
		boolean flag = Boolean.FALSE;
		for (CellElement cell : svgs) {
			svg = (AbstractSVG)cell;
			double[] offset = LocationConvert.getOffsetContainerOrOther(view);
			Shape javaFigure = SVGLocationConvert.createSVGHightLightShape(svg);
			Shape dragFigure = SVGLocationConvert.createSelectedShape(javaFigure);
			PageViewportPanel panel = _editor.findPageViewportPanel(e.getPoint());
			/*double[] width= SVGLocationConvert.parseLengths(svg, Constants.PR_STROKE_WIDTH);*/
			Point2D p = panel.getDPIPoint(e.getPoint());
			if(SVGLocationConvert.isRelativeFigure(svg)){
				Rectangle2D r = javaFigure.getBounds2D();				
				p.setLocation(p.getX() - offset[0], p.getY() - offset[1]);
				r = view.getViewport();
				p.setLocation(p.getX() - r.getX(), p.getY() - r.getY());
				currentState = SelectState.VIEWPORT;
			}else{
				p.setLocation(p.getX() - offset[0], p.getY() - offset[1]);
			}
			if(hasFigure(getSelectionModel(), svg) && SVGLocationConvert.isEnterDragPoint(dragFigure, p)){
				currentOri = SVGLocationConvert.calcOrientations(javaFigure, p);
				SVGLocationConvert.updateCursorStyle(currentOri, _editor);
				currentState = SelectState.RESIZE_FIGURE;
				figure = svg;
				flag = Boolean.TRUE;
				return;
			}else if(SVGLocationConvert.isSelectable(svg, p)){
				SVGLocationConvert.updateCursorStyle(Orientations.ON_FIGURE, _editor);
				currentState = SelectState.SELECT_FIGURE;
				figure = svg;
				flag = Boolean.TRUE;
				return;
			}
		}
		if(!flag){
			figure = null;
			SVGLocationConvert.updateCursorStyle(Orientations.NORMAL, _editor);
			super.mouseMoved(e);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		SelectionModel model = getSelectionModel();
		if(isNormal()){
			super.mousePressed(e);
			return;
		}else if(isViewport()){
			model.clearSelection();
			super.mousePressed(e);
			/*model.addObjectSelection((CellElement)figure.getParent());*/
			return;
		}else if(canSelect()){
			List<CellElement> elements = model.getObjectSelection();
			elements.add(figure);
			int type = SVGLocationConvert.setSelectType(elements);
			if(type == 0 || !e.isControlDown())
				model.clearSelection();
			
			if(!isNull(figure)){
				model.addObjectSelection(figure);
			}
		}else if(canResize()){
			start = e.getPoint();
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(!canResize()){
			super.mouseDragged(e);
			return;
		}
		if(isNull(figure))
			return;
		end = e.getPoint();
		shape = createFigureBound();
		_editor.repaint();
		
	}
	private Shape createFigureBound(){
		Orientations ori = currentOri;
		Shape shape = SVGLocationConvert.createSVGHightLightShape(figure);
		Rectangle2D r = (Rectangle2D)shape.getBounds2D().clone();
		if(figure instanceof Line){
			Line2D line = (Line2D)shape;
			Point2D p = null;
			if(ori == Orientations.LINE_P1){
				p = line.getP2();
			}else if(ori == Orientations.LINE_P2){
				p = line.getP1();
			}
			r = new Rectangle2D.Double(p.getX() + 375, p.getY() + 375, p.getX() + 375, p.getY() + 375);
		}
		double offsetX = 0, offsetY = 2;
		CellElement ele = (CellElement)figure.getParent();
		if(ele instanceof Canvas){
			Viewport viewport = (Viewport)ele.getArea();
			Rectangle2D r0 = viewport.getViewport();
			r.setRect(r.getX() + r0.getX(), r.getY() + r0.getY(), r.getWidth(), r.getHeight());
			offsetX = 0;
			offsetY = 0.5;
		}
		r = _editor.toScreen(r, getViewport(start));
		
		double x = r.getX(),
		y = r.getY(),
		w = r.getWidth(),
		h = r.getHeight();
		Point end = SwingUtilities.convertPoint(_editor.getGridPanel(), (Point)this.end.clone(), _editor);
		double left = x;
		double right = x + w;
		double top = y;
		double bottom = y + h;
		int _type = ori.getType();
		if (_type < 3)
		{
			top = end.y;
		}
		if (_type > 1 && _type < 5)
		{
			right = end.x;
		}
		if (_type > 3 && _type < 7)
		{
			bottom = end.y;
		}
		if (_type == 0 || _type == 6 || _type == 7)
		{
			left = end.x;
		}
		double width = right - left;
		double height = bottom - top;
		if (width < 0)
		{ // Flip over left side
			left += width;
			width = Math.abs(width);
		}
		if (height < 0)
		{ // Flip over top side
			top += height;
			height = Math.abs(height);
		}
		if(figure instanceof com.wisii.wisedoc.document.svg.Rectangle){
			RoundRectangle2D round = (RoundRectangle2D)shape;
			double arcWidth = round.getArcWidth() * _editor.getPreviewScaleX() / 1000F;
			double arcHeight = round.getArcHeight() * _editor.getPreviewScaleY() / 1000F;			
			return new RoundRectangle2D.Double(left + offsetX, top + offsetY, width + 1, height + 1, arcWidth, arcHeight);
		}else if(figure instanceof Ellipse){
			return new Ellipse2D.Double(left + offsetX, top + offsetY, width + 1, height + 1);
		}else if(figure instanceof Line){
			if(ori == Orientations.LINE_P1){
				return new Line2D.Double(end, new Point2D.Double(r.getX(), r.getY()));
			}else if(ori == Orientations.LINE_P2){
				return new Line2D.Double(new Point2D.Double(r.getX(), r.getY()), end);
			}
		}
		return null;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(!canResize()){
			super.mouseReleased(e);
			return;
		}
		if(isNull(figure) || isNull(shape))
			return;
		updateUI(figure, shape, e);
		reset();
	}
	private void updateUI(AbstractSVG svg, Shape shape, MouseEvent e){
		Document doc = _editor.getDocument();
		Map<Integer, Object> attrs = new HashMap<Integer, Object>();
		java.awt.Rectangle rect = shape.getBounds();
		Point point = new Point(rect.x, rect.y);
		PageViewportPanel panel = _editor.findPageViewportPanel(e.getPoint());
		point = SwingUtilities.convertPoint(_editor, point, panel);
		rect.setBounds(point.x, point.y, rect.width, rect.height);
		Rectangle2D rect2d = _editor.fromScreen(rect);
		LengthProperty top = new FixedLength(rect2d.getY(), ConfigureUtil.getUnit());
		LengthProperty left = new FixedLength(rect2d.getX(), ConfigureUtil.getUnit());
		LengthProperty height = new FixedLength(rect2d.getHeight(),ConfigureUtil.getUnit());
		LengthProperty width = new FixedLength(rect2d.getWidth(), ConfigureUtil.getUnit());
		EnumLength length = new EnumLength(Constants.EN_AUTO, null);
		CellElement ele = (CellElement)figure.getParent();
		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		double offsetX = rect2d.getX(), offsetY = rect2d.getY();
		if(ele instanceof SVGContainer){
			map.put(Constants.PR_TOP, top);
			map.put(Constants.PR_LEFT, left);
			if(svg instanceof Line){
				offsetX = 0;
				offsetY = 0;
			}
		}else if(ele instanceof Canvas){
			Viewport view = (Viewport)ele.getArea();
			Rectangle2D r = view.getViewport();
			double[] offset = LocationConvert.getOffsetForContainer(view);
			offsetX -= WisedocUtil.convertMPTToLength(r.getX()) / 1000;
			offsetX -= WisedocUtil.convertMPTToLength(offset[0]) / 1000;
			offsetY -= WisedocUtil.convertMPTToLength(r.getY()) / 1000;
			offsetY -= WisedocUtil.convertMPTToLength(offset[1]) / 1000;
		}
		if(svg instanceof Line){
			Line2D line = (Line2D)shape;
			java.awt.Rectangle r = line.getBounds();
			Point2D p = line.getP1();
			rect2d.setRect(offsetX, offsetY, rect2d.getWidth(), rect2d.getHeight());
			Point2D temp = createPoint(p, r, rect2d);
			left = new FixedLength(temp.getX(), ConfigureUtil.getUnit());
			top = new FixedLength(temp.getY(), ConfigureUtil.getUnit());
			attrs.put(Constants.PR_X1, left);
			attrs.put(Constants.PR_Y1, top);
			p = line.getP2();
			temp = createPoint(p, r, rect2d);
			left = new FixedLength(temp.getX(), ConfigureUtil.getUnit());
			top = new FixedLength(temp.getY(), ConfigureUtil.getUnit());
			attrs.put(Constants.PR_X2, left);
			attrs.put(Constants.PR_Y2, top);
		}else if(svg instanceof Rectangle||svg instanceof Ellipse){
			if(ele instanceof Canvas){
				left = new FixedLength(offsetX, ConfigureUtil.getUnit());
				top = new FixedLength(offsetY, ConfigureUtil.getUnit());
				
				attrs.put(Constants.PR_X, left);
				attrs.put(Constants.PR_Y, top);
			}
			attrs.put(Constants.PR_WIDTH, width);
			attrs.put(Constants.PR_HEIGHT, height);
		}
//		else if(svg instanceof Ellipse){
//			if(ele instanceof Canvas){
//				left = new FixedLength(rect2d.getWidth() / 2 + offsetX, ConfigureUtil.getUnit());
//				top = new FixedLength(rect2d.getHeight() / 2 + offsetY, ConfigureUtil.getUnit());
//			}else{
//				left = new FixedLength(rect2d.getWidth() / 2, ConfigureUtil.getUnit());
//				top = new FixedLength(rect2d.getHeight() / 2, ConfigureUtil.getUnit());
//			}
//			width = new FixedLength(rect2d.getWidth() / 2, ConfigureUtil.getUnit());
//			height = new FixedLength(rect2d.getHeight() / 2, ConfigureUtil.getUnit());
//			attrs.put(Constants.PR_CX, left);
//			attrs.put(Constants.PR_CY, top);
//			attrs.put(Constants.PR_RX, width);
//			attrs.put(Constants.PR_RY, height);
//		}
		if (attrs != null) {
			doc.setElementAttributes(figure, attrs, false);
		}
		if(!map.isEmpty())
			doc.setElementAttributes(ele, map, false);
	}
	private Point2D createPoint(Point2D p, Rectangle2D source, Rectangle2D result){
		Point2D temp = null;
		if(p.getX() > source.getCenterX()){
			if(p.getY() > source.getCenterY())
				temp = new Point2D.Double(result.getMaxX(), result.getMaxY());
			else
				temp = new Point2D.Double(result.getMaxX(), result.getY());
		}else{
			if(p.getY() > source.getCenterY())
				temp = new Point2D.Double(result.getX(), result.getMaxY());
			else
				temp = new Point2D.Double(result.getX(), result.getY());
		}
		return temp;
	}
	private void reset(){
		currentOri = Orientations.NORMAL;
		start = null;
		end = null;
		shape = null;
		figure = null;
	}
	@Override
	public void paint(Graphics g) {
		if(!canResize())
			return;
		if(isNull(shape))
			return;
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setStroke(STROKE);
		g2d.setPaint(LINE_PAINT);
		
		g2d.draw(shape);
		g2d.dispose();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(isNormal())
			super.mouseClicked(e);
	}

	private boolean hasFigure(SelectionModel model, AbstractSVG svg){
		if(isNull(model) || isNull(svg))
			return Boolean.FALSE;
		List<CellElement> cells = model.getSelectedObject();
		if(isNull(cells) || cells.isEmpty())
			return Boolean.FALSE;
		return cells.contains(svg);
	}
	private Viewport getViewport(Point p){
		PageViewportPanel panel = _editor.findPageViewportPanel(p);
		if (panel == null)
			return null;
		Point p0 = SwingUtilities.convertPoint(_editor.getGridPanel(), (Point) p.clone(), panel);
		p0 = (Point) panel.getDPIPoint(p0);
		Area a = panel.getConvert().getCurrentArea(p0, Boolean.TRUE);
		if(!(a instanceof Viewport))
			return null;
		Viewport view = (Viewport)a;
		return view;
	}
	private boolean canSelect(){
		return (currentState == SelectState.SELECT_FIGURE);
	}
	public boolean canResize(){
		return (currentState == SelectState.RESIZE_FIGURE);
	}
	private boolean isViewport(){
		return (currentState == SelectState.VIEWPORT);
	}
	private boolean isNormal(){
		return (currentState == SelectState.NORMAL);
	}
	private static enum SelectState{
		SELECT_FIGURE,
		RESIZE_FIGURE,
		VIEWPORT,
		NORMAL
	}
	private SelectState currentState = SelectState.NORMAL;
	private AbstractSVG figure = null;
	private Point start = null, end = null;
	private Orientations currentOri = Orientations.NORMAL;
	/* 定义线的绘制类型 */
	private final static Stroke STROKE = new BasicStroke(1.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, (float) 1.0, new float[] { 3, 1 }, 0);
	// 虚框颜色
	private Paint LINE_PAINT = Color.BLACK;
	private Shape shape = null;
}
