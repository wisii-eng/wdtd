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
 * @PositionAndShape4Convert.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;
import static com.wisii.wisedoc.view.LocationConvert.NULL_LINE_WIDTH;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.area.BlockViewport;
import com.wisii.wisedoc.area.LineArea;
import com.wisii.wisedoc.area.NormalFlow;
import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.area.inline.InlineArea;
import com.wisii.wisedoc.area.inline.TextArea;
import com.wisii.wisedoc.area.inline.Viewport;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.FOText;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.view.PositionBean.FigureStyle;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

/**
 * 类功能描述：
 * ●用于计算高亮区域
 * ●坐标系：PageViewport【BufferedImage】左上顶点。
 * ●Position → Shape
 * 分为以下情况进行处理：
 * 1、在同一行。
 * 2、在多行中。
 * 	●在一个Block中。
 * 	●在一个单元格中。
 * 	●在一个Container中。
 * 	
 * 
 * 作者：李晓光 
 * 创建日期：2010-1-25
 */
final class PositionShape4Convert {
	
	/**
	 * 根据指定选择位置，计算高亮区域
	 * 1.指定起始位置。
	 * 2.位置时有序的。
	 * 
	 * @param range				指定选择位置信息
	 * @param pages				指定位置包含的也索引[0, N]
	 * @return {@link Shape} 	返回包含选择位置的高亮区域
	 */
	public final static List<PositionBean> getSelectShape(final DocumentPositionRange range, final List<PageViewport> views, final Collection<Integer> pages) {
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (isNull(range)) {
			return list;
		}
		final List<PositionBean> result = createPath4Position(range.getStartPosition(), range.getEndPosition(), views, pages);
		if(isNull(result))
			return list;
		
		list.addAll(result);

		return list;
	}
	
	/**
	 * 
	 * 根据指定起始位置信息，创建高亮区域
	 * 
	 * @param start					指定起点位置
	 * @param end					指定终点位置
	 * @param views					指定当前所有的page-viewport
	 * @param pages					指定位置包含的也索引[0, N]
	 * @return List<PositionBean>	返回根据起始位置，创建的高亮区域
	 */
	public final static List<PositionBean> createPath4Position(final DocumentPosition start, final DocumentPosition end, final List<PageViewport> views, final Collection<Integer> pages) {
		List<PositionBean> list = new ArrayList<PositionBean>();
		if (isNull(start) || isNull(end)) {
			return list;
		}

		if (isDiffrentPage(start, end)) {
			return createMorePagePath(start, end, views, pages);
		} else if (isEnterContainer(start, end)) {
			return createContainerPath(start, end);
		}

		list = new ArrayList<PositionBean>();
		list.addAll(createSamePageShape(start, end));

		return list;
	}
	
	/**
	 * 用于处理表、行、单元格、目录、svg图高亮区域计算。
	 * ●【这个区域还没有累加偏移量，也就是说这个位置是相对于BufferedImage的】
	 * ●【这些信息存储在SelectionModel中】
	 * @param cells					指定选择表、行、单元格
	 * @return {@link List} 		返回高亮区域 。
	 */
	public static List<PositionBean> createSlectionPath(final List<CellElement> cells) {
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (isNull(cells)) {
			return list;
		}
		for (final CellElement ele : cells) {
			if (ele instanceof TableRow) {
				final TableRow row = (TableRow) ele;
				list.addAll(createSlectionPath(filterRowElements(row)));
			} else if (ele instanceof TableCell) {
				final TableCell cell = (TableCell) ele;
				list.addAll(createTableCellPath(cell));
			} else if (ele instanceof Table) {
				final Table table = (Table) ele;
				list.addAll(createSlectionPath(table.getAllChildren()));
			} else if (ele instanceof TableBody) {
				final TableBody body = (TableBody) ele;
				list.addAll(createSlectionPath(body.getAllChildren()));
			} else if (ele instanceof AbstractSVG) {
				list.addAll(createSVGFigurePath((AbstractSVG) ele));
			} else if(ele instanceof TableContents) {
				//创建目录的选择区域
				list.addAll(createTableContentPath((TableContents)ele));
			}
			else if(ele instanceof ZiMoban) {
				//创建目录的选择区域
				list.addAll(createSlectionPath(((ZiMoban)ele).getAllChildren()));
			}
			else if(ele instanceof QianZhang)
			{
				list.addAll(createQianZhangPath((QianZhang)ele));
			}
			else if(ele instanceof com.wisii.wisedoc.document.Block)
			{
				com.wisii.wisedoc.document.Block block = (com.wisii.wisedoc.document.Block) ele;
				list.addAll(createBlockPath(block));
			}
		}
		return list;
	}
	//-------------------------计算选择Position的高亮区域-------------------------------
	/**
	 * 用于计算在不同页中的位置，的高亮显示区域。
	 * 
	 * @param start						指定起始位置。
	 * @param end						指定结束位置。
	 * @param views						指定所有的page-viewport。
	 * @param pages						指定当前高亮区域包含的页范围。	
	 * @return {@link List} 			返回计算后的高亮显示区域，并封装位置PositionBean集合。
	 */
	private final static List<PositionBean> createMorePagePath(DocumentPosition start,  DocumentPosition end, final List<PageViewport> views, final Collection<Integer> pages) {
		final List<PositionBean> list = new ArrayList<PositionBean>();
		PageViewport startV = null, endV = null;
		if(LocationConvert.isBlankPosition(start)){
			startV = LocationConvert.getViewportWithArea(start.getLeafElement().getArea());
		}else{
			startV = LocationConvert.getViewportWithPosition(start);
		}
		
		if(LocationConvert.isBlankPosition(end)){
			endV = LocationConvert.getViewportWithArea(end.getLeafElement().getArea());
		}else{
			endV = LocationConvert.getViewportWithPosition(end);
		}
		
		
		if (startV == endV) {
			return null;
		}
		if(startV == null || endV == null)
			return null;
		
		/* 【添加：START】 by 李晓光	2009-12-23 */
		//交换位置，目的使得start始终对应最靠前的位置，end对应靠后的位置。
		int startIndex = -1, endIndex = -1;
		if((start.getLeafElement() instanceof StaticContent) && start.getPageIndex() != -1){
			startIndex = start.getPageIndex();
		}else{
			startIndex = startV.getPageIndex();
		}
		if((end.getLeafElement() instanceof StaticContent) && end.getPageIndex() != -1){
			endIndex = end.getPageIndex();
		}else{
			endIndex = endV.getPageIndex();
		}
		if (endIndex < startIndex) {
			final DocumentPosition temp = start;
			start = end;
			end = temp;
			PageViewport view = startV;
			startV = endV;
			endV = view;
		}
		/* 【添加：END】 by 李晓光	2009-12-23 */
		final TableCellBean bean = isSameCell(start, end);
		Area region = null;
		List<Block> blocks = null;
		if (bean.isSameCell) {
			blocks = bean.cell.getBlocks();
			region = blocks.get(0);
		} else {
			region = startV.getPage().getRegionViewport(Constants.FO_REGION_BODY);
		}
		/* 计算开始位置处的高亮区域 */
		if(pages.contains(startV.getPageIndex())) {
			final Area area = getFirstLastArea(region, Boolean.FALSE);
			DocumentPosition pos = null;
			if(area instanceof LineArea){
				CellElement source = area.getParentArea().getSource();
				pos = new DocumentPosition(source, Boolean.FALSE);
			}else if(area instanceof TextArea){
				TextArea text = (TextArea)area;
				pos = createPositionWithTextArea(text, Boolean.FALSE);
			}else{//area instanceof viewport
				CellElement source = area.getSource();
				pos = new DocumentPosition(source, Boolean.FALSE);
			}
			list.addAll(createSamePageShape(start, pos));
		}

		/* 计算结束位置所在页的高亮区域 */
		if(pages.contains(endV.getPageIndex())){
			/*final int count = endV.getPageIndex();*/
			if (bean.isSameCell) {
				region = blocks.get(endV.getPageIndex() - startV.getPageIndex());
			} else {
				region = endV.getPage().getRegionViewport(Constants.FO_REGION_BODY);
			}
			Area area = getFirstLastArea(region, Boolean.TRUE);
			DocumentPosition pos = null;
			if(area instanceof LineArea){
				CellElement source = area.getParentArea().getSource();
				pos = new DocumentPosition(source, Boolean.TRUE);
			}else if(area instanceof TextArea){
				pos = createPositionWithTextArea((InlineArea)area, Boolean.TRUE);
			}else{//area instanceof viewport
				CellElement source = area.getSource();
				pos = new DocumentPosition(source, Boolean.TRUE);
			}
			list.addAll(createSamePageShape(pos, end));
		}
		
		/* 计算中间页的高亮区域 */
		final Integer[] arr = pages.toArray(new Integer[0]);
		final List<Integer>  col = Arrays.asList(arr);
		Collections.sort(col);
		
		final int length = Math.min(((Integer)(col.get(pages.size() - 1))), endV.getPageIndex() - 1);
		final int begin = Math.max(startV.getPageIndex() + 1, col.get(0));
		
		for (int i = begin; i <= length; i++) {
			if (bean.isSameCell) {
				region = blocks.get(i - startV.getPageIndex());
			} else {
				region = views.get(i).getRegionReference(Constants.FO_REGION_BODY);
			}
			list.addAll(createPageShape(region));
		}
		return list;
	}
	
	/**
	 * 根据制定的Region-body，返回当前页的高亮区域。
	 * @param region				指定region
	 * @return	{@link List}		返回高亮区域。
	 */
	private static List<PositionBean> createPageShape(final Area region) {
		Area area = getFirstLastArea(region, Boolean.TRUE);
		final DocumentPosition start = createPositionWithTextArea((InlineArea) area, Boolean.TRUE);
		DocumentPosition end = null;
		area = getFirstLastArea(region, Boolean.FALSE);
		end = createPositionWithTextArea((TextArea) area, Boolean.FALSE);

		return createSamePageShape(start, end);
	}
	
	/**
	 * 选择BlockContainer中数据高亮显示处理.
	 * 如果：选择的起始位置在一个block-container内。
	 * 选择高亮处理，在该代码中计算。
	 * 
	 * @param start						指定选择的开始位置。
	 * @param end						指定选择的结束位置。
	 * @return	{@link List}			返回计算好的高亮区域。
	 */
	private static List<PositionBean> createContainerPath(
			final DocumentPosition start, final DocumentPosition end) {
		Area startInline = null;//getAreaWithPosition(start);
		if(LocationConvert.isBlankPosition(start)){
			startInline= start.getLeafElement().getArea();
		}else{
			startInline = LocationConvert.getAreaWithPosition(start);
		}
		Area endInline = LocationConvert.getAreaWithPosition(end);
		if(LocationConvert.isBlankPosition(end)){
			endInline = end.getLeafElement().getArea();
		}else{
			endInline = LocationConvert.getAreaWithPosition(end);
		}
		final BlockViewport blockS = (BlockViewport) LocationConvert.searchArea(startInline, BlockViewport.class);
		final BlockViewport blockE = (BlockViewport) LocationConvert.searchArea(endInline, BlockViewport.class);
		if (isNull(blockS) && isNull(blockE)) {
			return null;
		}
		PageViewport startV = LocationConvert.getViewportWithPosition(start);
		//----------------------------add by lxg 2009-11-20-------------------------
		/*if(startV == null){
			startV = LocationConvert.getViewportWithPosition(end);
		}*/
		if(isNull(startV))
			return null;
		//----------------------------add by lxg 2009-11-20-------------------------
		Rectangle2D rect = null;
		BlockViewport block = null;
		if (isNull(blockS)) {
			rect = blockE.getViewport();
			block = blockE;
		} else if (isNull(blockE)) {
			rect = blockS.getViewport();
			block = blockS;
		}
		//----------------------------add by lxg 2009-11-20-------------------------
		else if(blockS != blockE){
			int pos = blockS.getPositioning();
			if(pos == Block.ABSOLUTE){
				rect = blockS.getViewport();
				block = blockS;
			}else{
				rect = blockE.getViewport();
				block = blockE;
			}
		}	
		//----------------------------add by lxg 2009-11-20-------------------------
		if (isNull(rect)) {
			return createSamePageShape(start, end);
		}
		final List<PositionBean> list = new ArrayList<PositionBean>();
		final GeneralPath path = new GeneralPath();
		rect = (Rectangle2D) rect.clone();
		final Area parent = block.getParentArea();
		final double[] offset = LocationConvert.getOffsetForContainer(parent);
		rect.setRect(rect.getX() + offset[0], rect.getY() + offset[1], rect
				.getWidth(), rect.getHeight());
		path.append(rect, Boolean.FALSE);
		
		list.add(new PositionBean(startV.getPageIndex(), path));
		return list;
	}
	/**
	 * 计算起始位置在同一页时，高亮显示区域集合。
	 * ●起始位置在同一页中。
	 * ●计算其产生的高亮区域。
	 * @param start					指定开始位置。
	 * @param end					指定结束位置。
	 * @return	{@link List}		返回计算后的高亮区域。如果没有任何区域，则返回0长度的list。
	 */
	@SuppressWarnings("unchecked")
	private final static List<PositionBean> createSamePageShape(final DocumentPosition start, final DocumentPosition end) {
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (isNull(start) || isNull(end)) {
			return list;
		}

		// 处理在不同的Normal Flow中的位置。
		list.addAll(processInDifferentFlow(start, end));
		if (!isNull(list) && list.size() > 0) {
			return list;
		}

		// 处理首尾位置处在相同的Normal Flow时，高亮区域计算。
		
		Area area = LocationConvert.getAreaWithPosition(start);
		if(LocationConvert.isBlankPosition(start)){
			area = start.getLeafElement().getArea();
		}else{
			area = LocationConvert.getAreaWithPosition(start);
		}
		if (isNull(area)) {
			return list;
		}

		Area flow = LocationConvert.getNormalFlow(area);
		if (isNull(flow)) {
			flow = LocationConvert.getRegionReference(area);
		}
		if(flow == null)
			return list;
		final List<Area> areas = flow.getChildAreas();
		Rectangle2D startR = LocationConvert.getRectWithPosition(start);
		Rectangle2D endR = LocationConvert.getRectWithPosition(end);		
		/*if (needChange(startR, endR)) {
			final Rectangle2D temp = startR;
			startR = endR;
			endR = temp;
		}*/
		LineArea lineS = LocationConvert.getLineArea(start);
		if(LocationConvert.isBlankPosition(start)){
			lineS = LocationConvert.getLineAreaDown(start);
			if(lineS != null)
				startR = lineS.getViewport();
		}else{
			lineS = LocationConvert.getLineArea(start);
		}
		LineArea lineE = LocationConvert.getLineArea(end);
		if(LocationConvert.isBlankPosition(end)){
			lineE = LocationConvert.getLineAreaDown(end);
			if(lineE != null)
				endR = lineE.getViewport();
		}else{
			lineE = LocationConvert.getLineArea(end);
		}
		if (isNull(startR) || isNull(endR)) {
			return list;
		}
		final double[] offset = LocationConvert.getOffsetForContainer(lineS);// getOffsetOfContainer(lineS,
														// Boolean.FALSE);//2009-1-14
		
		AffineTransform at = new AffineTransform();
		at.translate(offset[0], offset[1]);
		if (lineS == lineE) {// 首尾位置处在相同的行。
			list.addAll(createSameLineShape(startR, endR, lineS));
		} else {// 首尾位置处在不同的行。
			// -------------------第一行区域计算------------------------
			List<PositionBean> beans = null;//createFirstOrLastLineBean(start, end, Boolean.TRUE);
			if(LocationConvert.isBlankPosition(start)){
				final PageViewport view = LocationConvert.getViewportWithArea(lineS);
				Shape s = createFirstOrLastLineShape(startR, lineS, Boolean.TRUE);
				s = modifyShape(s, start);
				PositionBean bean = new PositionBean(view.getPageIndex(), s);
				//----------------------------add by lxg 2009-11-20--------------------------
				double[] offs = LocationConvert.getOffsetForContainer(lineS);
				at = new AffineTransform();
				at.translate(offs[0], offs[1]);
				beans = new ArrayList<PositionBean>();
				beans.add(bean);
				beans = transformBeans(beans, at);
				list.addAll(beans);
				//----------------------------add by lxg 2009-11-20--------------------------
				//del by lxg 2009-11-20
				//list.add(bean);
			}else{
				beans = createFirstOrLastLineBean(start, end, Boolean.TRUE);
				//----------------------------add by lxg 2009-11-19--------------------------
				double[] offs = LocationConvert.getOffsetForContainer(lineS);
				at = new AffineTransform();
				at.translate(offs[0], offs[1]);
				beans = transformBeans(beans, at);
				//----------------------------add by lxg 2009-11-19--------------------------				
				list.addAll(beans);
			}
			// -------------------中间行区域计算------------------------
			final Rectangle2D rect = getMaxRect(startR, endR);
			Rectangle2D r = null;
			/*rect.setRect(rect.getX() + offset[0], rect.getY() + offset[1], rect
					.getWidth(), rect.getHeight());*/
			
			BooleanItem item = new BooleanItem();
			for (final Area a : areas) {
				if (isNull(rect)) {
					break;
				}
				if(item.isFinished){
					break;
				}
				//删除原来的处理
//				beans = createMoreLineShapes(a, rect, isInSameCell(start, end));
				//----------------------------add by lxg 2009-11-19--------------------------
				if(a instanceof BlockViewport){
					BlockViewport view = (BlockViewport)a;
					int pos = view.getPositioning();
					if(!isEnterContainer(start, end) && pos == Block.ABSOLUTE)
						continue;
				}
				beans = createMoreLineShapes(a, lineE, lineS, isInSameCell(start, end), item);
				if(beans == null || beans.isEmpty())
					continue;
				//【删除：START】 by 李晓光 2010-1-6
				//现在不需要进行计算，在觉得line上单独计算，这样更合理。				
//				double[] offs = getOffsetForContainer(a);
				//【删除：START】 by 李晓光 2010-1-6
				at = AffineTransform.getTranslateInstance(0, 0);
				beans = transformBeans(beans, at);
				//----------------------------add by lxg 2009-11-19--------------------------
				list.addAll(beans);
			}
			// -------------------最后一行区域计算------------------------
			if(LocationConvert.isBlankPosition(end)){
				final PageViewport view = LocationConvert.getViewportWithArea(lineE);
				Shape s = createFirstOrLastLineShape(endR, lineE, Boolean.FALSE);
				s = modifyShape(s, end);
				PositionBean bean = new PositionBean(view.getPageIndex(), s);
				//----------------------------add by lxg 2009-11-20--------------------------
				double[] offs = LocationConvert.getOffsetForContainer(lineS);
				at = new AffineTransform();
				at.translate(offs[0], offs[1]);
				beans = new ArrayList<PositionBean>();
				beans.add(bean);
				beans = transformBeans(beans, at);
				list.addAll(beans);
				//----------------------------add by lxg 2009-11-20--------------------------
				/*list.add(bean);*/ // del by lxg 2009-11-20
			}else{
				beans = createFirstOrLastLineBean(start, end, Boolean.FALSE);
				//----------------------------add by lxg 2009-11-19--------------------------
				double[] offs = LocationConvert.getOffsetForContainer(lineE);
				at = new AffineTransform();
				at.translate(offs[0], offs[1]);
				beans = transformBeans(beans, at);
				//----------------------------add by lxg 2009-11-19--------------------------
				list.addAll(beans);
			}
		}
		return list;
		//每部分单独处理，最终统一平移处理有问题！
		/*return transformBeans(list, at);*/
	}
	private final static List<PositionBean> createSameLineShape(final Rectangle2D start, final Rectangle2D end, final LineArea line){
		List<PositionBean> beans = new ArrayList<PositionBean>();
		final Shape shape = createSameLineFigure(start, end, line);
		if(shape == null)
			return beans;
		
		final PageViewport view = LocationConvert.getViewportWithArea(line);
		if(!isNull(view))
			beans.add(new PositionBean(view.getPageIndex(), shape));		
		double[] offs = LocationConvert.getOffsetForContainer(line);
		AffineTransform at = AffineTransform.getTranslateInstance(offs[0], offs[1]);
		beans = transformBeans(beans, at);
		
		return beans;
	}
	/**
	 * 起始选择位置在同一行时，获得高亮现在区域
	 * ●起始位置在同一行中。
	 * ●根据起始位置的位置信息，计算高亮区域。
	 * @param start					指定开始Position的界面位置。
	 * @param end					指定结束Position的界面位置。
	 * @param line					当前的line-area。
	 * @return	{@link Shape}		返回计算后的高亮图形。
	 */
	private final static Shape createSameLineFigure(final Rectangle2D start, final Rectangle2D end, final LineArea line) {
		
		final Rectangle2D r = line.getViewport();
		final double x = Math.min(start.getX(), end.getX());
		final double y = line.getOffsetTop();
		final double w = end.getX() - start.getX();
		final double h = r.getHeight();
		return new Rectangle2D.Double(x, y, w, h);
	}
	//原来的方法存在缺陷，但是效率要好些！
	//该方式效率不够好。
	/** 起始选择位置在不同行时，获得中间【除去第一行、最后一行的】行的高亮显示区域。 */
	private static List<PositionBean> createMoreLineShapes(final Area area,
			final LineArea lastLine, final LineArea firstLine,final boolean isSameCell, BooleanItem item) {
		final List<PositionBean> beans = new ArrayList<PositionBean>();
		/* GeneralPath path = new GeneralPath(); */
		if (area instanceof LineArea) {
			final LineArea line = (LineArea) area;
			Rectangle2D shape = area.getViewport();
			shape = (Rectangle2D) shape.clone();
			//【添加：START】 by 李晓光 2010-1-6
			//为每个单独的行计算器偏移量，相对body区域的。原来是在外层统一计算这样不准确，
			//如：在表中，嵌套了了其它对象，这样在外出计算出来的偏移量是table的，这显然不够合理。
			double[] offset = LocationConvert.getOffsetForContainer(line);
			//【添加：END】 by 李晓光 2010-1-6
			if (shape.getWidth() != line.getDifference()) {
				//【添加：START】 by 李晓光 2010-1-6
				shape.setRect(shape.getX() + offset[0], shape.getY() + offset[1], shape.getWidth()
						- line.getDifference(), shape.getHeight());
				//【添加：END】 by 李晓光 2010-1-6
				//【删除：START】 by 李晓光 2010-1-6
//				shape.setRect(shape.getX(), shape.getY(), shape.getWidth()
//						- line.getDifference(), shape.getHeight());
				//【删除：END】 by 李晓光 2010-1-6
			} else {
				shape.setRect(shape.getX(), shape.getY(), NULL_LINE_WIDTH,
						shape.getHeight());
			}
			final PageViewport view = LocationConvert.getViewportWithArea(area);
			
			beans.add(new PositionBean(view.getPageIndex(), shape));
			return beans;
		} else if (area.isTableCell() && !isSameCell) {
			final TableRow row = getCurrentRow(area);
			boolean flag = searchLineAeaaInTableCell(area, firstLine);
			if(flag){
				item.canStart = Boolean.TRUE;
			}
			flag = searchLineAeaaInTableCell(area, lastLine);
			if(flag){
				item.isFound = Boolean.TRUE;
				item.isFinished = Boolean.TRUE;
			}
			if(item.canStart && !item.isFinished)
				beans.addAll(calcTableRowPath(row));

			return beans;
		}

		final List<Area> areas = area.getChildAreas();
		if (isNull(areas) || areas.size() == 0) {
			return beans;
		}
		for (final Area a : areas) {
			if(item.isFound)
				return beans;
			if(a == firstLine){
				item.canStart = Boolean.TRUE;
				continue;
			}			
			if((a instanceof LineArea) && !item.canStart)
				continue;
			if(a == lastLine){
				item.isFound = Boolean.TRUE;
				item.isFinished = Boolean.TRUE;
				continue;
			}
			beans.addAll(createMoreLineShapes(a, lastLine, firstLine, isSameCell, item));
		}
		return beans;
	}
	
	
	private static List<PositionBean> createFirstOrLastLineBean(final DocumentPosition start, final DocumentPosition end, final boolean isFirst) {
		final List<PositionBean> beans = new ArrayList<PositionBean>();
		final Area startA = LocationConvert.getAreaWithPosition(start);
		final Area endA = LocationConvert.getAreaWithPosition(end);
		LineArea line = null;//getLineArea(startA);
		if (!isFirst) {
			line = LocationConvert.getLineArea(endA);
		}else{
			line = LocationConvert.getLineArea(startA);
		}
		if(line == null)
			return beans;
		final Area startB = LocationConvert.searchAbsoluteArea(startA);
		final Area endB = LocationConvert.searchAbsoluteArea(endA);
		Rectangle2D rect = null;//getRectWithPosition(start);
		if (!isFirst) {
			rect = LocationConvert.getRectWithPosition(end);
		}else{
			rect = LocationConvert.getRectWithPosition(start);
		}

		/* GeneralPath path = new GeneralPath(); */
		Shape shape = null;
		Area a = startA;
		if (!isFirst) {
			a = endA;
		}
		final PageViewport view = LocationConvert.getViewportWithArea(a);
		if (startB == endB) {
			shape = createFirstOrLastLineShape(rect, line, isFirst);
			/* path.append(shape, Boolean.FALSE); */
			beans.add(new PositionBean(view.getPageIndex(), shape));
			return beans;
		}
		Area block = startB;
		if (!isFirst) {
			block = endB;
		}

		if (isNull(block)) {
			shape = createFirstOrLastLineShape(rect, line, isFirst);
			/* path.append(shape, Boolean.FALSE); */
			beans.add(new PositionBean(view.getPageIndex(), shape));
			return beans;
		} else {
			final TableRow row = getCurrentRow(block);
			return calcTableRowPath(row);
		}
	}
	private static Shape createFirstOrLastLineShape(final Rectangle2D rect,
			final LineArea line, final boolean isFirst) {
		final Rectangle2D r = line.getViewport();
		double x = rect.getX();
		if (!isFirst) {
			x = r.getX();
		}
		final double y = r.getY();
		double w = NULL_LINE_WIDTH;
		if (r.getWidth() != line.getDifference()) {
			w = r.getWidth() + r.getX() - rect.getX() - line.getDifference();
		}
		if (!isFirst) {
			w = rect.getX() - r.getX();
		}
		final double h = r.getHeight();
		return new Rectangle2D.Double(x, y, w, h);
	}
	private final static boolean searchLineAeaaInTableCell(Area area, LineArea line){
		if(area instanceof InlineArea)
			return Boolean.FALSE;
		if(line == area)
			return Boolean.TRUE;
		List<Area> areas = area.getChildAreas();
		if(areas == null || areas.isEmpty())
			return Boolean.FALSE;
		
		for (Area a : areas) {
			boolean temp = searchLineAeaaInTableCell(a, line);
			if(temp)
				return Boolean.TRUE;
		}
		return Boolean.FALSE; 	
	}

	private static List<PositionBean> calcTableRowPath(final TableRow row)
	{
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (row == null)
		{
			return list;
		}
		final List<CellElement> cells = row.getAllChildren();
		for (final CellElement cell : cells)
		{
			list.addAll(calcTableCellPath((TableCell) cell));
		}
		return list;
	}
	
	private static List<PositionBean> calcTableCellPath(final TableCell cell) {
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (isNull(cell)) {
			return list;
		}

		final List<Block> blocks = cell.getBlocks();
		
		if(blocks == null || blocks.isEmpty()){
			return list;
		}
			
		PageViewport view = null;
		for (final Block block : blocks) {
			view = LocationConvert.getViewportWithArea(block);
			/* GeneralPath path = new GeneralPath(); */
			final Rectangle2D rect = block.getViewport();
			/* path.append(rect, Boolean.FALSE); */
			list.add(new PositionBean(view.getPageIndex(), rect));
		}
		return list;
	}
	/**
	 * 根据指定的TableCell的Arae，获得FO 元素中TableRow
	 * @param block					指定table-cell对应的block-area。
	 * @return	{@link TableRow}	返回包含该table-cell的table-row。
	 */
	private static TableRow getCurrentRow(final Area block) {
		final CellElement cell = block.getSource();
		return (TableRow) cell.getParent();
	}
	/** 计算获得起始选择位置在不同行时，中间行的有效区域【行增长方向上】 */
	private static Rectangle2D getMaxRect(final Rectangle2D start, final Rectangle2D end) {
		final double minY = (start.getY() + start.getHeight());
		final double minX = Math.min(start.getX(), end.getX());
		double maxW = Math.abs((end.getX() - start.getX()));
		maxW = Math.max(maxW, 1);
		final double maxH = (end.getY() - minY);
		return new Rectangle2D.Double(minX, minY, maxW, maxH);
	}
	/**
	 * 根据指定的Inline Level Area创建Position
	 * 
	 * @param area							指定Area
	 * @param isStart						指定是否在Area的前。
	 * @return {@link DocumentPosition} 	返回创建的Position对象。
	 */
	public static DocumentPosition createPositionWithTextArea(final InlineArea area, final boolean isStart) {
		if (isNull(area)) {
			return null;
		}
		int offset = calcOffsetForInline(area, isStart);
		
		CellElement ele = null;
		if (area instanceof TextArea) {
			final FOText text = (FOText) PositionOffset4Convert.getElementWithArea(area, offset);
			/* 算法的不再依赖Area，而是通过偏移量来计算来获得具体的位置。 */
			/* 只要确定合并后的fo-Text和text-area之间关系即可。 */
			/*text.setArea((TextArea)area);*/
			ele = text.getParent();
		} else if (area instanceof Viewport) {
			ele = ((Viewport) area).getSource();
		}
		return new DocumentPosition(ele, isStart, offset);
	}

	/** 计算当前Inline Level 的偏移量 */
	private static int calcOffsetForInline(final InlineArea area, final boolean isStart) {
		int offset = 0;
		if (area instanceof TextArea) {
			TextArea text = (TextArea)area;
			offset = text.getStartIndex();
			if (!isStart) {
				offset += text.getElementCount();
				offset -= 1;
			}
		} else if (area instanceof Viewport) {
			Viewport view = (Viewport)area;
			offset = view.getStartIndex();
		}
		return offset;
	}
	
	/**
	 * 根据指定的Area向下遍历，获得该Area包含的第一子inline-area
	 * 或最后一个子inline-area[text-area\viewport]
	 * 取第一个还是最后一个由isFirst指定，isFirst=True表示取第一个，否则 取最后一个。
	 * 
	 * @param region						指定要遍历的Area
	 * @param isFirst						指定是要取第一个子Area，还是最后一个子Area
	 * @return {@link Area}					返回找到的子inline-area。
	 */
	@SuppressWarnings("unchecked")
	private final static Area getFirstLastArea(final Area region, final boolean isFirst) {
		//改用新的算法处理。
		if(Boolean.TRUE){
			Area line = getLineAreaOf(region, isFirst);
			return PositionOffset4Convert.getInlineAreaOf(line, isFirst);
		}
		if (isNull(region)) {
			return region;
		}
		if (region instanceof TextArea) {
			return region;
		}else if(region instanceof Viewport){
			return region;
		}
		final List<Area> children = region.getChildAreas();
		if (isNull(children) || children.size() == 0) {
			return null;
		}

		int index = 0;
		if (!isFirst) {
			index = children.size() - 1;
		}
		Area area = null;
		while (index > -1 && index < children.size() && isNull(area)) {
			area = children.get(index);
			if (isFirst) {
				index++;
			} else {
				index--;
			}
			Area temp = getFirstLastArea(area, isFirst);
			if(!isNull(temp))
				area = temp; 
		}
		return area;
	}
	/**
	 * 获得指定region中的第一个\最后一个line-area
	 * @param region					指定region-area
	 * @param isFirst					指定是第一个、最后一个
	 * @return	{@link Area}			返回获得的line-area。
	 */
	@SuppressWarnings({ "unchecked" })
	private final static Area getLineAreaOf(final Area region, final boolean isFirst){
		if(region == null)
			return region;
		if(region instanceof LineArea)
			return region;
		final List<Area> children = region.getChildAreas();
		if (isNull(children) || children.size() == 0) {
			return null;
		}
		
		int index = 0;
		if (!isFirst) {
			index = children.size() - 1;
		}
		Area area = children.get(index);
		return getLineAreaOf(area, isFirst);
	}
	/**
	 * 处理处在不同的Normal Flow中时计算高亮显示区域。 
	 * @param start						指定一个位置。
	 * @param end						指定另一个位置。
	 * @return	{@link List}			返回由指定的位置描述的高亮区域。
	 */
	private static List<PositionBean> processInDifferentFlow(
			final DocumentPosition start, final DocumentPosition end) {
		final PositionInfoBean bean = getPositionInfo(start, end);
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (bean.isSameArea()) {
			return list;
		}
		if(isNull(bean.startArea) || isNull(bean.endArea)) {
			return list;
		}

		final NormalFlow flowS = (NormalFlow) bean.startArea;
		final NormalFlow flowE = (NormalFlow) bean.endArea;
		// 计算首位置所在的Normal Flow高亮区域
		InlineArea temp = (InlineArea) getFirstLastArea(flowS, Boolean.FALSE);
		DocumentPosition pos = createPositionWithTextArea(temp, Boolean.FALSE);
		list.addAll(createSamePageShape(start, pos));
		// 计算中间位置所在的Normal Flow高亮区域
		final List<NormalFlow> flows = LocationConvert.getNormalFlows(flowS);
		final int indexS = flows.indexOf(flowS) + 1, indexE = flows.indexOf(flowE);
		NormalFlow flow = null;
		DocumentPosition pos0 = null;
		for (int i = indexS; i < indexE; i++) {
			flow = flows.get(i);
			temp = (InlineArea) getFirstLastArea(flow, Boolean.TRUE);
			pos0 = createPositionWithTextArea(temp, Boolean.TRUE);
			temp = (InlineArea) getFirstLastArea(flow, Boolean.FALSE);
			pos = createPositionWithTextArea(temp, Boolean.FALSE);
			list.addAll(createSamePageShape(pos0, pos));
		}
		// 计算尾位置所在的Normal Flow高亮区域
		temp = (InlineArea) getFirstLastArea(flowE, Boolean.TRUE);
		pos = createPositionWithTextArea(temp, Boolean.TRUE);
		list.addAll(createSamePageShape(pos, end));
		return list;
	}
	/**
	 * 判断起始位置是否在相同的Normal Flow中 
	 * @param start							指定一个位置。
	 * @param end							指定另一个位置。
	 * @return {@link PositionInfoBean}		返回两个位置对应的normal-flow-area的封装。
	 */
	private static PositionInfoBean getPositionInfo(final DocumentPosition start, final DocumentPosition end) {
		final PositionInfoBean bean = new PositionInfoBean();
		Area area = LocationConvert.getAreaWithPosition(start);
		bean.startArea = LocationConvert.getNormalFlow(area);
		area = LocationConvert.getAreaWithPosition(end);
		bean.endArea = LocationConvert.getNormalFlow(area);
		return bean;
	}
	private static class PositionInfoBean {
		// 位置信息确定的Inline级别Area【TextArea、TableCell】
		private Area startArea, endArea;

		private boolean isSameArea() {
			return (startArea == endArea);
		}
	}
	/** 判断指定的位置是否同属于一个TableCell */
	@SuppressWarnings("unchecked")
	private static boolean isInSameCell(final DocumentPosition start, final DocumentPosition end) {
		final CellElement ele0 = start.getLeafElement();
		final CellElement ele1 = end.getLeafElement();
		final TableCell cell0 = (TableCell) LocationConvert.searchCellElement(ele0, TableCell.class);
		final TableCell cell1 = (TableCell) LocationConvert.searchCellElement(ele1, TableCell.class);

		return (!isNull(cell0) && !isNull(cell1)) && (cell0 == cell1);
	}
	private final static Shape modifyShape(Shape shape, DocumentPosition pos){
		if(!(shape instanceof Rectangle2D))
			return shape;
		Rectangle2D r = (Rectangle2D)shape;
		if(!pos.isStartPos() && r.getWidth() <= 0){
			r.setRect(r.getX(), r.getY(), NULL_LINE_WIDTH, r.getHeight());
		}
		
		return r;
	}
	/**
	 * 这个主要是针对如果文本信息、表是在Container中，由于坐标系的变化 故要进行高亮区域的平移处理。
	 */
	private static List<PositionBean> transformBeans(List<PositionBean> beans,
			final AffineTransform at) {
		Shape shape = null;
		GeneralPath path = new GeneralPath();
		Collections.sort(beans, new Comparator<PositionBean>() {
			@Override
			public int compare(final PositionBean bean1, final PositionBean bean2) {
				return (bean1.getPageIndex() - bean2.getPageIndex());
			}
		});
		int pageIndex = -1;
		final List<PositionBean> result = new ArrayList<PositionBean>();
		for (final PositionBean bean : beans) {
			if (pageIndex == -1) {
				pageIndex = bean.getPageIndex();
			}
			if (pageIndex != bean.getPageIndex()) {
				path.transform(at);
				result.add(new PositionBean(pageIndex, path));
				path = new GeneralPath();
				pageIndex = bean.getPageIndex();
			}
			shape = bean.getViewport();
			path.append(shape, Boolean.FALSE);
		}
		beans.clear();
		beans = null;

		path.transform(at);
		result.add(new PositionBean(pageIndex, path));
		return result;
	}
	private static TableCellBean isSameCell(final DocumentPosition start,
			final DocumentPosition end) {
		final TableCell cell0 = getTablecell(start);
		final TableCell cell1 = getTablecell(end);
		final TableCellBean bean = new TableCellBean();
		bean.isSameCell = (!isNull(cell0) && !isNull(cell1))
				&& (cell0 == cell1);
		bean.cell = cell0;
		return bean;
	}
	@SuppressWarnings("unchecked")
	private static TableCell getTablecell(final DocumentPosition pos) {
		final CellElement ele = pos.getLeafElement();
		final TableCell cell = (TableCell) LocationConvert.searchCellElement(ele, TableCell.class);
		return cell;
	}
	/**
	 * 判断指定的位置是否在绝对位置的Container中。
	 * 任何一个位置在绝对位置中的Container中，都需要返回true。
	 * @param start						指定一个位置。
	 * @param end						指定另一个位置。
	 * @return	boolean					如果任何一个位置在绝对位置container中：True，否则：False。
	 */
	@SuppressWarnings("unchecked")
	private static boolean isEnterContainer(final DocumentPosition start, final DocumentPosition end) {
		final BlockContainer c1 = (BlockContainer) LocationConvert.searchCellElement(start.getLeafElement(), BlockContainer.class);
		final BlockContainer c2 = (BlockContainer) LocationConvert.searchCellElement(end.getLeafElement(), BlockContainer.class);
		
		int pos = -1;
		if(c1 != null){
			pos = getPositioning(c1);
		}
		if(pos == Constants.EN_ABSOLUTE){
			return Boolean.TRUE;
		}
		if(c2 != null){
			pos = getPositioning(c2);
		}
		if(pos == Constants.EN_ABSOLUTE){
			return Boolean.TRUE;
		}
		if(c1 != null && c1 == c2)
			return Boolean.TRUE;
		
		return Boolean.FALSE;
	}
	private static int getPositioning(BlockContainer c)
	{
		EnumProperty ep=(EnumProperty) c.getAttribute(Constants.PR_ABSOLUTE_POSITION);
		return ep.getEnum();
	}
	/**
	 * 根据指定的位置，判断两个位置是否在不同的页内。
	 * @param start					指定一个位置。
	 * @param end					指定另一个位置。
	 * @return	{@link Boolean}		如果两个位置在不同的页：True，否则：False。
	 */
	private static boolean isDiffrentPage(final DocumentPosition start, final DocumentPosition end) {
		int startIndex = -1, endIndex = -1;
		if(LocationConvert.isBlankPosition(start)){
			CellElement ele = start.getLeafElement();
			if((ele instanceof StaticContent) && start.getPageIndex() != -1){
				startIndex = start.getPageIndex();
			}else{
				Area area = ele.getArea();
				PageViewport view = LocationConvert.getViewportWithArea(area);
				if(view != null){
					startIndex = view.getPageIndex();
				}
			}
		}else{
			PageViewport view = LocationConvert.getViewportWithPosition(start);
			if(view != null){
				startIndex = view.getPageIndex();
			}
		}
		if(LocationConvert.isBlankPosition(end)){
			CellElement ele = end.getLeafElement();
			if((ele instanceof StaticContent) && end.getPageIndex() != -1){
				endIndex = end.getPageIndex();
			}else{
				Area area = ele.getArea();
				PageViewport view = LocationConvert.getViewportWithArea(area);
				if(view != null){
					endIndex = view.getPageIndex();
				}
			}
		}else{
			PageViewport view = LocationConvert.getViewportWithPosition(end);
			if(view != null){
				endIndex = view.getPageIndex();
			}
		}
		if(startIndex == -1 || endIndex == -1)
			return Boolean.FALSE;
		
		return (startIndex != endIndex);
	}
	//-------------------------计算选择Position的高亮区域------------------------------
	
	//-------------------------计算选择对象的高亮区域----------------------------------
	/**
	 * 过滤掉那些不属于当前行的单元格，这种现象的产生就是因为有了行方向上单元格的合并，
	 * 如果有行方向的合并的单元格，那个该单元格属于其占据行中索引最小的行。
	 * 
	 * @param	row				指定row对象。
	 * @return	{@link List}	返回过滤后的，包含的所有cell-element
	 */
	private static List<CellElement> filterRowElements(final TableRow row) {
		final List<CellElement> cells = row.getAllChildren();
		final List<CellElement> result = new ArrayList<CellElement>();
		Element temp = null;
		for (final CellElement cell : cells) {
			temp = cell.getParent();
			if (row != temp) {
				continue;
			}
			result.add(cell);
		}
		return result;
	}
	/**
	 * 为单元格计算高亮区域。
	 * 1.如果单元格跨页，累加所有的区域。
	 * 2.坐标系：PageViewport左上顶点。
	 * 
	 * ※把选择的TableCell位置转化为PositionBean表示
	 * @param cell				指定单元格对象。
	 * @return	{@link List}	返回计算后的高亮区域。
	 */
	private static List<PositionBean> createTableCellPath(final TableCell cell) {
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (isNull(cell)) {
			return list;
		}

		final List<Block> blocks = cell.getBlocks();
		PageViewport view = null;
		if(blocks==null)
		{
			return list;
		}
		for (final Block block : blocks) {
			view = LocationConvert.getViewportWithArea(block);
			final GeneralPath path = new GeneralPath();
			final double[] offset = LocationConvert.getOffsetForNestedArea(block, Boolean.FALSE);
			if (isNull(block)) {
				LogUtil.debug("Block 为空");
				continue;
			}
			Rectangle2D rect = block.getViewport();
			if (isNull(rect)) {
				LogUtil.debug("Block Area-Viewport 为空");
				continue;
			}
			rect = (Rectangle2D) rect.clone();
			rect.setRect((rect.getX() + offset[0]), (rect.getY() + offset[1]), rect.getWidth(), rect.getHeight());
			path.append(rect, false);
			list.add(new PositionBean(view.getPageIndex(), path));
		}
		
		return list;
	}
	
	/**
	 * 为单元格计算高亮区域。
	 * 1.如果单元格跨页，累加所有的区域。
	 * 2.坐标系：PageViewport左上顶点。
	 * 
	 * ※把选择的TableCell位置转化为PositionBean表示
	 * @param cell				指定单元格对象。
	 * @return	{@link List}	返回计算后的高亮区域。
	 */
	private static List<PositionBean> createQianZhangPath(final QianZhang cell)
	{
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (isNull(cell))
		{
			return list;
		}

		Area qianzhangarea = cell.getArea();
		PageViewport view = null;
		if (qianzhangarea == null)
		{
			return list;
		}
		view = LocationConvert.getViewportWithArea(qianzhangarea);
		final GeneralPath path = new GeneralPath();
		final double[] offset = LocationConvert.getOffsetForNestedArea(
				qianzhangarea.getParentArea(), Boolean.FALSE);
		Rectangle2D rect = ((Area)qianzhangarea.getChildAreas().get(0)).getViewport();
		if (isNull(rect))
		{
			LogUtil.debug("QianZhang Area-Viewport 为空");
			return list;
		}
		System.out.println("before:"+rect);
		rect = (Rectangle2D) rect.clone();
		rect.setRect((rect.getX()*1000 + offset[0]), (rect.getY()*1000 + offset[1]), rect
				.getWidth()*1000, rect.getHeight()*1000);
		System.out.println(rect);
		path.append(rect, false);
		PositionBean bean=new PositionBean(view.getPageIndex(), path);
		bean.setFigureStyle(FigureStyle.BORDER);
		list.add(bean);
		return list;
	}
	/**
	 * 为段落计算高亮区域。
	 * 1.如果段落跨页，累加所有的区域。
	 * 2.坐标系：PageViewport左上顶点。
	 * 
	 * ※把选择的TableCell位置转化为PositionBean表示
	 * @param blockcell				指定单元格对象。
	 * @return	{@link List}	返回计算后的高亮区域。
	 */
	private static List<PositionBean> createBlockPath(final com.wisii.wisedoc.document.Block blockcell) {
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (isNull(blockcell)) {
			return list;
		}

		final List<Block> blocks = blockcell.getAllBlocks();
		PageViewport view = null;
		for (final Block block : blocks) {
			view = LocationConvert.getViewportWithArea(block);
			final GeneralPath path = new GeneralPath();
			final double[] offset = LocationConvert.getOffsetForNestedArea(block, Boolean.FALSE);
			if (isNull(block)) {
				LogUtil.debug("Block 为空");
				continue;
			}
			Rectangle2D rect = block.getViewport();
			if (isNull(rect)) {
				LogUtil.debug("Block Area-Viewport 为空");
				continue;
			}
			rect = (Rectangle2D) rect.clone();
			rect.setRect((rect.getX() + offset[0]), (rect.getY() + offset[1]), rect.getWidth(), rect.getHeight());
			path.append(rect, false);
			list.add(new PositionBean(view.getPageIndex(), path));
		}
		
		return list;
	}
	
	/**
	 * 为svg图计算高亮区域，并封装在PositionBean中。
	 * 1.svg图的高亮是八个可拖拽点。
	 * 2.坐标系：PageViewport左上顶点。
	 * @param svg				指定svg对象。
	 * @return	{@link List}	返回计算后的高亮区域。
	 */
	private static List<PositionBean> createSVGFigurePath(final AbstractSVG svg) {
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (isNull(svg)) {
			return list;
		}
		final Viewport viewport = SVGLocationConvert.getViewport(svg);
		final PageViewport view = LocationConvert.getViewportWithArea(viewport);
		if (isNull(view) || view.getPageIndex() == -1) {
			return list;
		}
		final double[] offset = LocationConvert.getOffsetContainerOrOther(viewport);
		final Rectangle2D rect = viewport.getViewport();
		offset[0] += rect.getX();
		offset[1] += rect.getY();
		final Shape shape = SVGLocationConvert.createSelectedShape(svg, offset);
		final PositionBean bean = new PositionBean(view.getPageIndex(), shape);
		bean.setFigureStyle(FigureStyle.DRAG_POINT);
		list.add(bean);
		return list;
	}
	/**
	 * 用于创建目录高亮区域
	 * 1.坐标系：PageViewport左上顶点。
	 * @param contents			指定目录对象。
	 * @return	{@link List}	返回为目录对象创建高亮区域。
	 */
	private static List<PositionBean> createTableContentPath(final TableContents contents){
		final List<PositionBean> list = new ArrayList<PositionBean>();
		if (isNull(contents)) {
			return list;
		}
		final PageViewport view = LocationConvert.getViewportWithArea(contents.getArea());
		if (isNull(view) || view.getPageIndex() == -1) {
			return list;
		}
		//【修正：偏移量的获取算法】 by 李晓光	2009-12-30
		//原来的做法：getOffsetContainerOrOther(contents.getArea());采用该方式获得的偏移量不够准确，他只适合直接在Body区域使用。
		//修正为：getOffsetForNestedArea((Area)contents.getArea().getChildAreas().get(0), false)
		//采用该方，无论目录在相对位置的Container中还是表格嵌套中，都可以正确的处理其高亮位置。
		final double[] offset = LocationConvert.getOffsetForNestedArea((Area)contents.getArea().getChildAreas().get(0), false);	
		final GeneralPath path = new GeneralPath();
		final List<Area> areas = getLineAreas(contents.getArea());
		for (final Area area : areas) {
			if(!(area instanceof LineArea)) {
				continue;
			}
			final Rectangle2D r = ((LineArea)area).getViewport();
			path.append(r, false);
		}
		final AffineTransform at = AffineTransform.getTranslateInstance(offset[0], offset[1]);
		path.transform(at);
		final PositionBean bean = new PositionBean(view.getPageIndex(), path);
		list.add(bean);
		return list;
	}
	
	/**
	 * 遍历指定的area对象，获得其包含的所有line-area。
	 * @param a					指定目录对象对应的area对象。
	 * @return	{@link List}	返回指定area包含的所有line-area
	 */
	@SuppressWarnings("unchecked")
	private static List<Area> getLineAreas(final Area a){
		final List<Area> areas = new ArrayList<Area>();
		if(isNull(a)) {
			return areas;
		} else if(a instanceof LineArea){
			areas.add(a);
			return areas;
		}
		final List<Area> temp = a.getChildAreas();
		for (final Area area : temp) {
			final List<Area> t = getLineAreas(area);
			if(isNull(t) || t.size() == 0) {
				continue;
			}
			areas.addAll(t);
		}
		return areas;
	}
	//-------------------------计算选择对象的高亮区域----------------------------------
	/** 数据结构单元，用于计算选择位置【起始位置】是否在同一单元格中，如果在同一个单元格中，返回当前单元格 */
	private static class TableCellBean {
		private boolean isSameCell = Boolean.FALSE;
		private TableCell cell = null;
	}
	private static class BooleanItem{
		Boolean canStart = Boolean.FALSE;
		Boolean isFound = Boolean.FALSE;
		Boolean isFinished = Boolean.FALSE;
		
	}
}
