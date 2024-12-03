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
 * @TableSelectMouseHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.select;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.area.AreaTreeObject.AreaKind;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.LocationConvert;
import com.wisii.wisedoc.view.SVGLocationConvert;
import com.wisii.wisedoc.view.SVGLocationConvert.Orientations;
import com.wisii.wisedoc.view.cursor.CursorManager;
import com.wisii.wisedoc.view.cursor.CursorManager.CursorType;
import com.wisii.wisedoc.view.mousehandler.SelectHandler;
/**
 * 类功能描述：根据捕获的鼠标事件，判读处理进行表的行、列选择处理
 * 
 * 作者：李晓光 创建日期：2008-10-22
 */
public class TableSelectMouseHandler extends SelectHandler {
	/** 定义鼠标的当前状态 */
	public static enum MouseState {
		/* 可选择单元格、行 */
		SELECT_CELL,
		/* 可改变单元格宽度 */
		CHANGE_CELL,
		/* 可选单元格高度、行高 */
		CHANGE_ROW,
		/* 其它 */
		OTHER
	}
	/** 定义辅助键的状态 */
	public static enum KeyState{
		CTRL, SHIFT, ALT, META, CTRL_ALT, NONE
	}
	
	/**
	 * 根据指定的Editor创建当前对象。
	 * @param compnent 指定Editor控件
	 */
	public TableSelectMouseHandler(AbstractEditComponent compnent) {
		this._editor = compnent;
		operation = new TableOperationHandler(this);
	}
	@Override
	public void init() {
		_editor.setCursor(CursorManager.getSytemCursor(CursorType.TEXT_CURSOR));
	}
	
	/**----------------------------鼠标事件实现---------------------------------*/
	@Override
	public void mouseMoved(MouseEvent e) {		
		resetState(e.getPoint());
		if(currectState == MouseState.OTHER){
			super.mouseMoved(e);
		}
	}
	
	@Override
	/** 拖拽改变单元格、行的大小、高度 */
	public synchronized void mouseDragged(MouseEvent e)
	{
		if (!canChangeCell() && !canChangeRow())
		{// 拖动鼠标进行单元格的连选操作。
			TableCell cell = operation.getCurrentCell(e.getPoint());
			// 如果出了单元格选中区域，则不更新选择状态
			if (cell == null)
			{
				return;
			}
			operation.clearSelectionModel();
			Point p = (Point) e.getPoint().clone();
			DocumentPosition end = _editor.pointtodocpos(p);
			if (!(curElement instanceof TableCell))
			{
				// 【删除】 by 李晓光 2008-11-28
				/*super.mouseDragged(e);*/
				if (curElement instanceof TableRow)
					operation.selectTableRow(cell, (TableRow) curElement,
							KeyState.SHIFT);
				else
					super.mouseDragged(e);
			} else if (cell == curElement)
				super.mouseDragged(e);
			else if (isNull(cell) && !isNull(end))
			{
				SelectionModel model = operation.getSelectionModel();
				model.clearSelection();
				DocumentPositionRange range = DocumentPositionRange
						.creatSelectCell(startPos, end);
				model.addSelectionCell(range);
			} else
				operation.calcRangeSelection((TableCell) curElement, cell);
			return;
		}

		Point p = e.getPoint();
		if (canChangeCell())
		{
			double increment = calcIncrementX(default_start, p);
			if (operation.isInRangeCellChange(default_start, increment))
			{// 判断当前点是否在单元格宽度的调整范围内。
				end = p;
				start = convertPoint(p);
			}
		} else if (canChangeRow())
		{
			double increment = calcInrementY(default_start, p);
			if (operation.isInRangeRowChange(default_start, increment))
			{// 判断当前点是否在行高的调整范围内。
				end = p;
				start = convertPoint(p);
			}
		}
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!canSelectCell())
			super.mouseClicked(e);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.isConsumed())
			return;
		if (canChangeCell() || canChangeRow())
		{
			start = convertPoint(e.getPoint());
			default_start = e.getPoint();// 【LocationConvert中用的点都是GridPanel上的点】
			end = default_start;
			final TableCell cell = operation.getCurrentCell(default_start);
			//计算得到可拖动的范围，以及所影响的单元格
			if (canChangeCell())
			{
				operation.initAllNeedChangedCell(cell);
			}
			repaint();
		} else if (canSelectCell()){
			processSelectionForCellRow(e);
		}else if(e.isShiftDown()){
			TableCell cell = operation.getCurrentCell(e.getPoint());
			if(isDifferentCellInTable(curElement, cell) || e.isControlDown())
				processSelectionForCellRow(e);
			else{
				operation.getSelectionModel().clearObjectSelection();
				super.mousePressed(e);
			}
		}else{
			curElement = operation.getCurrentCell(e.getPoint());
			Point p = (Point)e.getPoint().clone();
			startPos = _editor.pointtodocpos(p);
			super.mousePressed(e);
		}
		e.consume();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		double increment = 0;	
		repaint();
		Point start = this.default_start;
		Point end = this.end;
		line = null;
		if (canChangeCell()) {
			increment = calcIncrementX(start, end);
			operation.changeCellWidth(start, increment);
		}else if(canChangeRow()){
			increment = calcInrementY(start, end);
			operation.changeRowHeight(start, increment);
		}else if (!canSelectCell())
			super.mouseReleased(e);
		
		resetState(e.getPoint());
	}
	private void resetState(Point p){
		Orientations ori = calculatePositionOnBorder(_editor, p);
		SVGLocationConvert.updateCursorStyle(ori, _editor);
		updateSlectStat(ori);
	}
	/**----------------------------鼠标事件实现---------------------------------*/
	private void repaint(){
		line = createLine(start, calculateOffset(0));
		_editor.repaint();
	}
	private void repaint(Shape shape){
		if(isNull(shape))
			return;
		Rectangle r = shape.getBounds();
		_editor.repaint(r);
	}
	
	/** 根据起始位置计算X方向上的增量 */
	private double calcIncrementX(Point start, Point end){
		if(isNull(start) || isNull(end))
			return 0;
		
		return (end.getX() - start.getX()) * Constants.PRECISION / _editor.getPreviewScaleX();
	}
	/** 根据起始位置计算Y方向上的增量 */
	private double calcInrementY(Point start, Point end){
		if(isNull(start) || isNull(end))
			return 0;
		return (end.getY() - start.getY()) * Constants.PRECISION / _editor.getPreviewScaleY();
	}
	/** 根据指定的鼠标位置枚举，确定是否为可选择单元格、可拖动改变单元格大小、可拖动改变行高	 */
	private void updateSlectStat(Orientations ori) {
		switch (ori) {
		case SELECT_CELL:
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
		a = LocationConvert.searchAbsoluteArea(a);
		if (a == null)
			return Orientations.NORMAL;

		Block block = (Block) a;

		Rectangle2D rect = (Rectangle2D) block.getViewport().clone();

		double scaleX = panel.getPreviewScaleX();
		double scaleY = panel.getPreviewScaleY();
		rect = LocationConvert.getScaleRectangle(rect, scaleX, scaleY);

		double[] offset = LocationConvert.getOffsetForNestedArea(a, Boolean.FALSE);//getOffsetContainerOrOther(a); 【删除】 by 李晓光 2009-3-27 10:02:31
		double offsetX = offset[0] * scaleX;
		offsetX += rect.getX();
		offsetX += panel.getOffsetX() * Constants.PRECISION;

		double offsetY = offset[1] * scaleY;
		offsetY += rect.getY();
		offsetY += panel.getOffsetY() * Constants.PRECISION;
		double top = 0;//block.getBorderAndPaddingWidthBefore() * scaleX;
		double left = 0;//block.getBorderAndPaddingWidthStart() * scaleY;
		rect.setRect(offsetX + left, offsetY + top, rect.getWidth() - left, rect.getHeight() - top);
		rect = LocationConvert.getScaleRectangle(rect, 1 / Constants.PRECISION);
		p0 = SwingUtilities.convertPoint(comp.getGridPanel(), p, panel);
		left = block.getBorderAndPaddingWidthStart() * scaleY / Constants.PRECISION;
		return processOrientations(rect, p0, DEFAULT_STEP, (int)left);
	}
	/** 用于判断指定点在指定的矩形区域的位置【方向】 */
	private Orientations processOrientations(Rectangle2D rect, Point p, int step, int...space) {//
		if (!rect.contains(p))
			return Orientations.NORMAL;
		if (isWestInside(rect, p, step, space)) {// WEST
			return Orientations.SELECT_CELL;
		} else if (isEastInside(rect, p, step)) {// EAST
			return Orientations.EAST;
		} else if (isNorthInside(rect, p, step)) {// NORTH
			return Orientations.NORTH;
		} else if (isSouthInside(rect, p, step)) {// SOUTH
			return Orientations.SOUTH;
		} else
			return Orientations.NORMAL;
	}
	/** 该方法主要用于处理表中单元格、行的选择处理 */
	private void processSelectionForCellRow(MouseEvent e){
		if (e.getButton() != MouseEvent.BUTTON1)
			return;
		
		Block currentBlock = operation.getSelectBlock(_editor, e.getPoint());
		if (currentBlock == null || !currentBlock.isTableCell())
			return;
		if(curElement instanceof Table)//【添加】by 李晓光2008-12-19
			operation.clearSelectionModel();
		
		TableCell temp = (TableCell)currentBlock.getSource();
		int count = e.getClickCount();
		if (count % 3 == 1) {
			if (e.isControlDown()) {
				if(e.isShiftDown())
					operation.selectTableBody(temp);
				else
					curElement = operation.selectRowOrCell(curElement, temp, KeyState.CTRL);
			} else if (e.isShiftDown()) {
				operation.selectRowOrCell(curElement, temp, KeyState.SHIFT);
				if(isNull(curElement))
					curElement = temp;
			}else if(e.isAltDown()){
				if(isNull(curElement) || curElement instanceof TableCell || curElement instanceof Table){//lxg 2008-12-19
					curElement = operation.selectTableRow(temp, null, KeyState.ALT);
				}else{
					operation.selectRowOrCell(curElement, temp, KeyState.ALT);
					curElement = temp;
				}
			}else{
				operation.selectRowOrCell(curElement, temp, KeyState.NONE);
				curElement = temp;
			}
		} else if (count % 3 == 2 && canSelectCell()) {
			if(e.isControlDown())
				curElement = operation.selectTableRow(temp, null, KeyState.CTRL);
			else if(e.isShiftDown())
				curElement = operation.selectTableRow(temp, null, KeyState.SHIFT);
			else
				curElement = operation.selectTableRow(temp, null, KeyState.NONE);
		} else if (count % 3 == 0 && canSelectCell()) {
			curElement = operation.selectTable(temp);
		}
	}
	/**
	 * 判断指定的两个单元格是否属于相同的表 
	 * @param c1
	 * @param c2
	 * @return
	 */
	private boolean isDifferentCellInTable(CellElement c1, TableCell c2){
		if(isNull(c1) || isNull(c2))
			return Boolean.FALSE;
		Table t1 = TableOperationHandler.getCurrentTable(c1);
		Table t2 = TableOperationHandler.getCurrentTable(c2);	
		return (t1 == t2) && (c1 != c2);
	}
	@Override
	public boolean isInControl(MouseEvent e) {
		LocationConvert convert = _editor.getCurrentConvert(e.getPoint());
		if(isNull(convert))
			return Boolean.FALSE;
		AreaKind kind = convert.getCurrentAreaKind(e);
		return (kind == AreaKind.CELL);
		/*return _editor.isEnterTable(e.getPoint());*/  // 【删除】by 李晓光 2009-3-30
	}

	/** 判断指定的点是否在上边框内侧 */
	private boolean isNorthInside(Rectangle2D rect, Point p, int step) {
		return (rect.contains(p)) && (rect.getY() + step >= p.getY());
	}

	/** 判断指定的点是否在下边框内侧 */
	private boolean isSouthInside(Rectangle2D rect, Point p, int step) {
		space = (rect.getY() + rect.getHeight()) - p.getY();
		return ((rect.contains(p)) && (step >= space));
	}

	/** 判断指定的点是否在左边框内侧 */
	private boolean isWestInside(Rectangle2D rect, Point p, int step, int...space) {
		step = Math.max(step, space[0]);
		return (rect.contains(p)) && (rect.getX() + step >= p.getX());
	}

	/** 判断指定的点是否在右边框内侧 */
	private boolean isEastInside(Rectangle2D rect, Point p, int step) {
		space = (rect.getX() + rect.getWidth()) - p.getX();
		return (rect.contains(p)) && (step >= space);
	}
	private Point convertPoint(Point source){
		return SwingUtilities.convertPoint(_editor.getGridPanel(), source, _editor);
	}
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(STROKE);
		g2d.setColor(Color.GRAY);		
		
		if (isNull(line))
			return;

		g2d.draw(line);
	}
	private double calculateOffset(double offset){
		if(canChangeCell())
			return space;
		return space + offset;
	}
	/**
	 * 根据指定的坐标点绘制用于标识改变单元格大小、行高的虚线。
	 * 
	 * @param p
	 *            指定当前的坐标点
	 * @return {@link Line2D} 根据指定的坐标点获得要绘制的标识线。
	 */
	private Shape createLine(Point2D p, double offset) {
		if (isNull(p))
			return null;
		Rectangle2D rect = _editor.getViewportBound();
		int header = 30;
		double x = 0, y = 0, w = 0, h = 0;
		if (canChangeCell()) {// 竖直线
			x = p.getX() + offset;
			y = 1;
			w = 0;
			h = rect.getHeight() + header;
		} else if (canChangeRow()) {// 水平线
			x = 1;
			y = p.getY()+ offset;
			w = rect.getWidth() + header;
			h = 0;
		}
		return new Rectangle2D.Double(x, y, w, h);
	}

	private boolean canSelectCell() {
		return (currectState == MouseState.SELECT_CELL);
	}

	private boolean canChangeCell() {
		return (currectState == MouseState.CHANGE_CELL);
	}

	private boolean canChangeRow() {
		return (currectState == MouseState.CHANGE_ROW);
	}
	private void reset(){
		curElement = null;
	}
	public AbstractEditComponent getEditor() {
		return _editor;
	}
	
	private final static int DEFAULT_STEP = 5;
	private Point start = null;
	private Point end = null;
	private Point default_start = null;
	private CellElement curElement = null;
	private DocumentPosition startPos = null;
	// 保存鼠标押下时所在的单元格。在处理拖动鼠标连选单元格时使用。
	/*private TableCell cell = null;*/
	private double space = 0;
	private MouseState currectState = MouseState.OTHER;
	private TableOperationHandler operation = null;
	private Shape line = null;
	private final static Stroke STROKE = new BasicStroke(0.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, (float) 1.0, new float[] { 3, 1 }, 0);
}
