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
 * @ContainerSelectMouseHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.select;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.area.BlockViewport;
import com.wisii.wisedoc.area.NormalFlow;
import com.wisii.wisedoc.area.AreaTreeObject.AreaKind;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.LocationConvert;
import com.wisii.wisedoc.view.SVGLocationConvert;
import com.wisii.wisedoc.view.SVGLocationConvert.Orientations;
import com.wisii.wisedoc.view.mousehandler.SelectHandler;
import com.wisii.wisedoc.view.select.TableSelectMouseHandler.MouseState;

/**
 * 类功能描述：根据捕获的鼠标事件，判读处理进行Container的选择。
 * 
 * 作者：李晓光 创建日期：2009-1-22
 */
public class ContainerSelectMouseHandler extends SelectHandler {
	@Override
	public void mouseMoved(MouseEvent e) {
		Orientations ori = calculatePositionOnBorder(_editor, e.getPoint());
		SVGLocationConvert.updateCursorStyle(ori, _editor);
		updateSlectStat(ori);
		if(currectState == MouseState.OTHER){
			super.mouseMoved(e);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.isConsumed())
			return;
		if (canSelectContainer()){
			processSelection(e);
		}else
			super.mousePressed(e);
		e.consume();
	}
	/** 该方法主要用于处理表中单元格、行的选择处理 */
	private void processSelection(MouseEvent e){
		int count = e.getClickCount();
		if(count != 1)
			return;
		SelectionModel model = getSelectionModel();
		model.clearSelection();
		model.addObjectSelection(block.getSource());
	}
	public SelectionModel getSelectionModel() {
		if (isNull(_editor))
			return null;
		return _editor.getSelectionModel();
	}
	/**
	 * 
	 * 根据指定的坐标点、控件，判断指定是否在控件的表内，如果在表内，判断鼠标位于单元格的具体位置【枚举类型表述】
	 * 
	 * @param comp
	 *            指定控件
	 * @param p
	 *            指定坐标点
	 * @return {@link Orientations} 返回方向枚举
	 */
	public Orientations calculatePositionOnBorder(AbstractEditComponent comp, Point p) {
		if (comp == null || p == null)
			return Orientations.NORMAL;
		PageViewportPanel panel = comp.findPageViewportPanel(p);;//comp.getCurrentPagePanel();
		if (panel == null)
			return Orientations.NORMAL;
		Point p0 = SwingUtilities.convertPoint(comp.getGridPanel(), (Point) p
				.clone(), panel);
		p0 = (Point) panel.getDPIPoint(p0);
		Area a = panel.getConvert().getCurrentArea(p0);
		
		if (a == null)
			return Orientations.NORMAL;
		a = LocationConvert.searchArea(a, BlockViewport.class);
		if (a == null)
			return Orientations.NORMAL;

		Block block = (Block) a;
		this.block = block;
		
		Rectangle2D rect = (Rectangle2D) block.getViewport().clone();

		double scaleX = panel.getPreviewScaleX();
		double scaleY = panel.getPreviewScaleY();
		rect = LocationConvert.getScaleRectangle(rect, scaleX, scaleY);

		double[] offset = LocationConvert.getOffsetForNestedArea(a, a.isReference());
		double offsetX = offset[0] * scaleX;
		offsetX += rect.getX();
		offsetX += panel.getOffsetX() * Constants.PRECISION;

		double offsetY = offset[1] * scaleY;
		offsetY += rect.getY();
		offsetY += panel.getOffsetY() * Constants.PRECISION;
		int top = block.getBorderAndPaddingWidthBefore();
		int left = block.getBorderAndPaddingWidthStart();
		rect.setRect(offsetX + left, offsetY + top, rect.getWidth() - left, rect.getHeight() - top);
		rect = LocationConvert.getScaleRectangle(rect, 1 / Constants.PRECISION);
		p0 = SwingUtilities.convertPoint(comp.getGridPanel(), p, panel);

		return processOrientations(rect, p0, DEFAULT_STEP);
	}
	/** 用于判断指定点在指定的矩形区域的位置【方向】 */
	private Orientations processOrientations(Rectangle2D rect, Point p, int step) {
		if (!rect.contains(p))
			return Orientations.NORMAL;
		if(block.isTableContent()){
			return Orientations.SELECT_CONTAINER;
		}else if (isWestInside(rect, p, step)) {// WEST
			return Orientations.SELECT_CONTAINER;
		} /*else if (isEastInside(rect, p, step)) {// EAST
			return Orientations.EAST;
		} else if (isNorthInside(rect, p, step)) {// NORTH
			return Orientations.NORTH;
		} else if (isSouthInside(rect, p, step)) {// SOUTH
			return Orientations.SOUTH;
		}*/ else
			return Orientations.NORMAL;
	}
	/** 根据指定的鼠标位置枚举，确定是否为可选择单元格、可拖动改变单元格大小、可拖动改变行高	 */
	private void updateSlectStat(Orientations ori) {
		switch (ori) {
		case SELECT_CONTAINER:
			currectState = MouseState.SELECT_CELL;
			break;
		case EAST:
			currectState = MouseState.CHANGE_CELL;
			break;
		case SOUTH:
			currectState = MouseState.CHANGE_ROW;
			break;
		default:
			currectState = MouseState.OTHER;
			break;
		}
	}
	/** 判断指定的点是否在左边框内侧 */
	private boolean isWestInside(Rectangle2D rect, Point p, int step)
	{
		return (rect.contains(p))
				&& ((rect.getX() + step >= p.getX())
						|| (rect.getY() + step >= p.getY())
						|| (rect.getX() + rect.getWidth() - step <= p.getX()) || (rect
						.getY()
						+ rect.getHeight() - step <= p.getY()));
	}
	public boolean isInControl(MouseEvent e) {
		/*Point p = e.getPoint();
		PageViewportPanel panel = _editor.findPageViewportPanel(p);//_editor.getCurrentPagePanel();
		if (panel == null)
			return false;
		Point p0 = SwingUtilities.convertPoint(_editor.getGridPanel(), (Point) p.clone(), panel);
		p0 = (Point) panel.getDPIPoint(p0);
		Area a = panel.getConvert().getCurrentArea(p0);*/
		/*BlockViewport view = (BlockViewport)LocationConvert.searchArea(a, BlockViewport.class);*/
		/*Area view = getContainer(a);
		return (view != null) && (view.isContainer()) && !((BlockViewport)view).isFigure();*/  // 【删除】by 李晓光 2009-3-30
		
		LocationConvert convert = _editor.getCurrentConvert(e.getPoint());
		if(isNull(convert))
			return Boolean.FALSE;
		AreaKind kind = convert.getCurrentAreaKind(e);
		return (kind == AreaKind.CONTAINER) || (kind == AreaKind.SVG_CONTAINER) || (kind == AreaKind.TABLE_CONTENT);
	}
	private Area getContainer(Area area){
		if(isNull(area))
			return area;
		if(area instanceof NormalFlow)
			return null;
		if(area.isContainer() || area.isTableCell())
			return area;
		return getContainer(area.getParentArea());
	}
	private boolean canSelectContainer() {
		return (currectState == MouseState.SELECT_CELL);
	}
	private final static int DEFAULT_STEP = 5;
	private MouseState currectState = MouseState.OTHER;
	private Block block = null;
}
