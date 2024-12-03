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
 * @CaretLocationConvert.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.LineArea;
import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.area.Span;
import com.wisii.wisedoc.area.AreaTreeObject.AreaKind;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.DocumentPositionUtil;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.view.LocationConvert.PositionImpOP;

/**
 * 类功能描述：如果通过UP、Down、Page-UP、Page-Down Home、End健来移动光标，该对象提供了相应的光标位置的计算方法。
 * 1.Home：光标跳转至当前行的行首。
 * 2.End：光标跳转至当前行的行尾。
 * 3.Up：光标跳转至上一行。
 * 	●效果：光标上顶点[x, y]，等效鼠标点击[x, y - line-height / 2]。
 *  ●坐标系：相对body区域。
 *  ●如果已经是第一页的第一行，不再移动。
 *  ●如果已经到达非首页的第一行，则跳转至上一页的最后一行。
 *  ●如果相邻页，页布局不一致，则看作[x, y]中x相等。
 * 4.Down：参照Up。
 * 5.Left：光标左移一个字符。
 *  ●如果到达行首，跳转到上一行的行尾。
 *  ●如果到达当前页的第一行的行首，则需要调整至上一页的最后一行的行尾。
 *  ●如果已经到达第一页的第一行行首，不再移动。
 * 6.Right：参照Left。
 * 7.Page-Up：翻页，滚动条可是区域向上平移scroll-panel中可视区域的高度。
 *  ●如果已经到达第一页，或滚动条已经到达最上方，不再移动。
 *  ●是否考虑光标定位仅仅在region-body内？
 *  ●现在的做法时，当前光标位置[x, y],相对scroll-panel左上顶点。
 *  ●翻页后，等效用鼠标在相对scroll-panel中的[x, y]压下鼠标左键。
 *  ●这样做有个问题， 如果点击位置在也之间的空白区域，不能定位.
 * 8.Page-Down：参照Page-Up
 * 
 * 作者：李晓光 创建日期：2009-6-2
 */
public class CaretLocationConvert {
	public static enum PositionType{
		/* 表示押下LEFT健  */
		LEFT,
		/* 表示押下RIGHT健  */
		RIGHT,
		/* 表示押下UP健  */
		UP,
		/* 表示押下DOWN健  */
		DOWN,
		/* 表示押下HOME健  */
		HOME,
		/* 表示押下END健  */
		END,
		/* 表示押下PAGE_UP健  */
		PAGE_UP,
		/* 表示押下PAGE_DOWN健 */
		PAGE_DOWN
	}
	/**
	 * 根据制定的信息类型，和当前的缺省的光标信息，获得新的位置信息。
	 * @param type	指定新的位置信息类型。
	 * @return	{@link DocumentPosition}	返回复合指定类型的新的位置信息。
	 */
	public final static DocumentPosition getPosition(final PositionType type){
		final AbstractEditComponent editor = SystemManager.getMainframe().getEidtComponent();
		return getPosition(editor.getCaretPosition(), type);
	}
	/**
	 * 根据当前的位置信息，和制定的要获得的位置信息类型，获得新的位置信息。
	 * @param pos	指定位置信息。
	 * @param type	指定要获得位置信息类型。
	 * @return	{@link DocumentPosition}	返回复合指定类型的新的位置信息。
	 */
	public final static DocumentPosition getPosition(final DocumentPosition pos, final PositionType type){
		DocumentPosition imp = null;
		switch (type) {
		case LEFT:
			imp = getLeftPosition(pos);
			break;
		case RIGHT:
			imp = getRightPosition(pos);
			break;
		case UP:
			imp = getUPPosition(pos);
			break;
		case DOWN:
			imp = getDownPosition(pos);
			break;
		case HOME:
			imp = getHomePostion(pos);
			break;
		case END:
			imp = getEndPosition(pos);
			break;
		case PAGE_UP:
			imp = getPageUPPosition(pos);
			break;
		case PAGE_DOWN:
			imp = getPageDownPostion(pos);
			break;
		}
		return imp;
	}
	private final static DocumentPosition getLeftPosition(final DocumentPosition pos){
		DocumentPosition result = pos;
		if (pos != null) {
			final CellElement element = pos.getLeafElement();
			if (!pos.isStartPos()) {
				result = new DocumentPosition(element, Boolean.TRUE);
			} else {

				final CellElement pre = DocumentPositionUtil.findpreviousInline(element);
				if (pre != null) {
					result = new DocumentPosition(pre, Boolean.TRUE);
				}
			}
		}
		/*if(result != null){
			result.setPageIndex(pos.getPageIndex());
		}*/
		return result;
	}
	private final static DocumentPosition getRightPosition(final DocumentPosition pos){
		DocumentPosition result = pos;
		if (pos != null) {
			final CellElement element = pos.getLeafElement();
			if (pos.isStartPos()) {
				result = new DocumentPosition(element, Boolean.FALSE);
			} else {
				
				final CellElement next = DocumentPositionUtil.findnextInline(element);
				if (next != null) {
					result = new DocumentPosition(next, Boolean.FALSE);
				}
			}
		}
		/*if(result != null){
			result.setPageIndex(pos.getPageIndex());
		}*/
		return result;
	}
	private final static DocumentPosition getPageUPPosition(final DocumentPosition pos){
		
		return createPosition(pos, PositionType.PAGE_UP);
	}
	private final static DocumentPosition getPageDownPostion(final DocumentPosition pos){
		
		return createPosition(pos, PositionType.PAGE_DOWN);
	}
	private final static DocumentPosition getUPPosition(final DocumentPosition pos) {
		final Area area = getLineAreaUp(getLineArea(pos,true));
		
		return createUPDownPosition(pos, area);
	}
	
	private final static DocumentPosition getDownPosition(final DocumentPosition pos) {
		final Area area = getLineAreaDown(getLineArea(pos,false));
		
		return createUPDownPosition(pos, area);
	}
	
	private final static DocumentPosition getHomePostion(final DocumentPosition pos) {
		final Area area = LocationConvert.getLineArea(pos);
		if (area == null) {
			return null;
		}
		final Rectangle2D r = area.getViewport();
		final Point2D point = new Point2D.Double(r.getX(), r.getY());

		return createPosition(area, point);
	}

	private final static DocumentPosition getEndPosition(final DocumentPosition pos) {
		final Area area = LocationConvert.getLineArea(pos);
		if (area == null) {
			return null;
		}
		final Rectangle2D r = area.getViewport();
		final Point2D point = new Point2D.Double(r.getX() + r.getWidth(), r.getY());
		
		return createPosition(area, point);
	}

	// -----------------------------------------------------------------------------
	
	private final static DocumentPosition createUPDownPosition(final DocumentPosition pos, final Area area){
		if (area == null) {
			return null;
		}else if(area.getAreaKind() == AreaKind.CELL){
			return new DocumentPosition(area.getSource());
		}
		
		Rectangle2D r = LocationConvert.getRectWithPosition(pos);
		if(r == null && area != null) {
			r = area.getViewport();
		}
		if(r == null) {
			return null;
		}
		
		final Point2D point = new Point2D.Double(r.getX(), r.getY());
		
		return createPosition(area, point);
	}
	private final static DocumentPosition createPosition(final Area area, final Point2D point){
		final PositionImpOP imp = LocationConvert.getPositionWithArea(area, point);
		CellElement inline = LocationConvert.getInlineLevelCell(imp.getElement());
		Element parent = inline.getParent();
		//如果inline在子模板内，则光标定位到子模板上（因为子模板内的内容不允许编辑）
		while(parent!=null&&!(parent instanceof PageSequence))
		{
			if(parent instanceof ZiMoban)
			{
				inline = (CellElement) parent;
				break;
			}
			parent = parent.getParent();
		}
		imp.setElement(inline);
		
		//del by 李晓光	2010-1-26
		//现在不在用page-index来确定页
		/*PageViewport page = LocationConvert.getViewportWithArea(area);
		imp.setPageIndex(page.getPageIndex());*/
		
		
		return new DocumentPosition(inline, imp.isStart(), imp.isOnTop(), imp.getOffset()/*, imp.getPageIndex()*/);
	}

	private final static Area/*LineArea*/getLineArea(final DocumentPosition pos,boolean isup)
	{
		CellElement leaf = pos.getLeafElement();
		//如果当前光标在子模板上，则根据是否是向上导航取子模板的第一个Area或最后一个Area
		//这样向上时才会移动到当前子模板的上面，向下时导航到下面
		if (leaf instanceof ZiMoban)
		{
			if (isup)
			{
				leaf = leaf.getChildAt(0);
				Area area=leaf.getArea();
				while(area==null&&leaf!=null&&leaf.getChildCount()>0)
				{
					leaf = leaf.getChildAt(0);
					area=leaf.getArea();
				}
				return LocationConvert.getFirstOrLastLineArea(leaf.getArea(),
						Boolean.TRUE);
			} else
			{
				leaf = leaf.getChildAt(leaf.getChildCount() - 1);
				Area area=leaf.getArea();
				while(area==null&&leaf!=null&&leaf.getChildCount()>0)
				{
					leaf = leaf.getChildAt(leaf.getChildCount()-1);
					area=leaf.getArea();
				}
				return LocationConvert.getFirstOrLastLineArea(leaf.getArea(),
						false);
			}
		}
		// 如果当前的位置是空的单元格、段落、Block-Container时，就不可能返回Line-Area
		// 故现在把返回值该为Area 
		else if ((leaf instanceof Block) || (leaf instanceof TableCell)
				|| (leaf instanceof BlockContainer))
		{
			return /*(LineArea)*/LocationConvert.getFirstOrLastLineArea(leaf
					.getArea(), Boolean.TRUE);
		} else if (leaf instanceof Flow)
		{
			final Flow flow = (Flow) leaf;
			return flow.getArea();
		}
		return LocationConvert.getLineArea(pos);
	}
	private final static DocumentPosition createPosition(final DocumentPosition pos, final PositionType type){
		final Area area = getLineArea(pos,true);
		Rectangle2D r = LocationConvert.getRectWithPosition(pos);
		if(r == null && area != null) {
			r = area.getViewport();
		}
		if(r == null) {
			return null;
		}
		final PageViewport page = LocationConvert.getViewportWithArea(area);
		if(page == null) {
			return null;
		}
		final Point2D point = new Point2D.Double(r.getX(), r.getY());
		final AbstractEditComponent editor = SystemManager.getMainframe().getEidtComponent();
		final PageViewportPanel comp = editor.getPageOf(page.getPageIndex());
		final double[] offset = LocationConvert.getOffsetForNestedArea(area, Boolean.FALSE);
		final double scaleX = editor.getPreviewScaleX();
		final double scaleY = editor.getPreviewScaleY();
		double x = (point.getX() + offset[0]) * scaleX / Constants.PRECISION;
		double y = (point.getY() + offset[1]) * scaleY / Constants.PRECISION;
		x += comp.getOffsetX();
		y += comp.getOffsetY();
		point.setLocation(x, y);
		final Point p = SwingUtilities.convertPoint(comp, (int)x, (int)y, editor);
		final JViewport pane = getScrollPane(editor);
		if(pane != null) {
			final Point viewP = pane.getViewPosition();
			int increment = pane.getExtentSize().height;
			final Dimension size = pane.getViewSize();
			switch (type) {
			case PAGE_UP:
				if(viewP.y - increment <= 0) {
					viewP.y = 0;
				} else {
					viewP.y -= increment;
				}
				p.y -= increment;
				break;
			case PAGE_DOWN:
				if(viewP.y + increment >= size.height) {
					increment = (size.height - increment);
				} else {
					viewP.y += increment;
				}
				p.y += increment;
				break;
			default:
				break;
			}
			pane.setViewPosition(viewP);
		}
		return editor.pointtodocpos(p);
	}
	private final static JViewport getScrollPane(final AbstractEditComponent comp){
		final Component c = comp.getParent();
		if(c instanceof JViewport) {
			return (JViewport)c;
		}
		return null;
	}
	private final static /*LineArea*/Area getLineAreaUp(final /*LineArea*/Area line) {
		Area/*LineArea*/ temp = /*(LineArea)*/ getUPLine(line);
		if (temp != null) {
			return temp;
		}
		PageViewport page = LocationConvert.getViewportWithArea(line);
		if (page == null || page.getPageIndex() == 0) {
			return line;
		}
		final AbstractEditComponent editor = SystemManager.getMainframe().getEidtComponent();
		final List<PageViewport> list = editor.getPageViewports();
		final PageViewportPanel panel = editor.getPageOf(page.getPageIndex() - 1);
		if(!panel.isLoad()){
			panel.loadImage();
		}
		page = list.get(page.getPageIndex() - 1);

		temp = LocationConvert.getFirstOrLastLineArea(page.getCurrentFlow(), Boolean.FALSE);
		return temp;
	}
	
	private final static Area/*LineArea*/ getLineAreaDown(final /*LineArea*/Area line) {
		/*LineArea*/Area temp = /*(LineArea)*/ getDownLine(line);
		if (temp != null) {
			return temp;
		}
		final AbstractEditComponent editor = SystemManager.getMainframe().getEidtComponent();
		final List<PageViewport> list = editor.getPageViewports();
		PageViewport page = LocationConvert.getViewportWithArea(line);
		if (page == null || page.getPageIndex() == list.size() - 1) {
			return line;
		}
		final PageViewportPanel panel = editor.getPageOf(page.getPageIndex() + 1);
		if(!panel.isLoad()){
			panel.loadImage();
		}
		page = list.get(page.getPageIndex() + 1);
		
		temp = LocationConvert.getFirstOrLastLineArea(page.getCurrentFlow(), Boolean.TRUE);
		return temp;
	}

	@SuppressWarnings("unchecked")
	private final static Area getUPLine(final Area line) {
		if (line == null || line instanceof Span) {
			return null;
		}

		Area parent = line.getParentArea();
		if(parent == null) {
			return null;
		}
		final List<Area> areas = parent.getChildAreas();
		final int index = areas.indexOf(line);
		if (index != 0) {
			parent = areas.get(index - 1);
		} else {
			return getUPLine(parent);
		}
		return searchNearArea(parent, Boolean.TRUE);
	}

	@SuppressWarnings("unchecked")
	private final static Area getDownLine(final Area line) {
		if (line == null || line instanceof Span) {
			return null;
		}
		Area parent = line.getParentArea();
		if(parent == null) {
			return null;
		}
		final List<Area> areas = parent.getChildAreas();
		final int index = areas.indexOf(line);
		if (index != areas.size() - 1) {
			parent = areas.get(index + 1);
		} else {
			return getDownLine(parent);
		}
		return searchNearArea(parent, Boolean.FALSE);
	}

	private final static Area searchNearArea(final Area parent,
			final boolean isUP) {
		if (parent == null) {
			return parent;
		}
		if (parent instanceof LineArea) {
			return parent;
		}
		return LocationConvert.getFirstOrLastLineArea(parent, !isUP);
	}
}
