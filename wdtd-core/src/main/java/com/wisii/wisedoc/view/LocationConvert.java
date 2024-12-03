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
 * @LocationConvert.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.swing.SwingUtilities;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.area.BlockParent;
import com.wisii.wisedoc.area.BlockViewport;
import com.wisii.wisedoc.area.BodyRegion;
import com.wisii.wisedoc.area.LineArea;
import com.wisii.wisedoc.area.MainReference;
import com.wisii.wisedoc.area.NormalFlow;
import com.wisii.wisedoc.area.Page;
import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.area.RegionReference;
import com.wisii.wisedoc.area.RegionViewport;
import com.wisii.wisedoc.area.Span;
import com.wisii.wisedoc.area.AreaTreeObject.AreaKind;
import com.wisii.wisedoc.area.inline.GroupInlineArea;
import com.wisii.wisedoc.area.inline.InlineArea;
import com.wisii.wisedoc.area.inline.QianZhangArea;
import com.wisii.wisedoc.area.inline.TextArea;
import com.wisii.wisedoc.area.inline.Viewport;
import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.FOText;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.PageNumber;
import com.wisii.wisedoc.document.PageNumberCitation;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonFont;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.fonts.Font;
import com.wisii.wisedoc.fonts.FontInfo;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.awt.viewer.MarginBean;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.PositionOffset4Convert.OffsetBean;

/**
 * 类功能描述：实现界面中的点到创建包含该点Area的查找，根据Area可以获得
 * 产生给Area的的FOText对象，同时指定一个FOText可以获得其产生Area的左上 顶点。
 * 
 * 作者：李晓光 创建日期：2008-09-08
 */
public class LocationConvert {
	/** 指定当前PageViewport */
	private PageViewport viewport = null;
	private final static int[] REGIONS_ID = { Constants.FO_REGION_BODY,
			Constants.FO_REGION_BEFORE, Constants.FO_REGION_AFTER,
			Constants.FO_REGION_START, Constants.FO_REGION_END };
	private final static Rectangle2D zero = new Rectangle2D.Double(0, 0, 0, 0);
	/** 定义空行高亮时、高亮的宽度。 */
	public final static float NULL_LINE_WIDTH = 6.5F * Constants.PRECISION;
	/** 单例模式用 */
	private final static LocationConvert convert = new LocationConvert();
	/**
	 * 创建一个没有指定具体PageViewport的对象。
	 * @return	{@link LocationConvert}	返回创建好对象。
	 */
	public final static synchronized LocationConvert getInstance(){
		return convert;
	}
	/**
	 * 获得单例模式的LocationConvert对象。
	 * @param viewport		指定PageViewport对象
	 * @return	{@link LocationConvert}		返回设置了PageViewport的LocationConvert对象。
	 */
	public final static synchronized LocationConvert getInstance(final PageViewport viewport){
		convert.setViewport(viewport);
		return convert;
	}
	
	public LocationConvert() {
		
	}

	/**
	 * 
	 * 根据指定的PageViewport对象创建该对象。
	 * 
	 * @param viewport
	 *            指定PageViewport对象
	 */
	/*public LocationConvert(final PageViewport viewport) {
		setViewport(viewport);
	}*/

	/**
	 * 
	 * 获得当前的PageViewport对象
	 * 
	 * @return {@link PageViewport} 返回当前的PageViewport对象
	 */
	public PageViewport getViewport() {
		return viewport;
	}

	/**
	 * 
	 * 设置当前的PageViewport对象。
	 * 
	 * @param viewport
	 *            指定PageViewport
	 */
	public void setViewport(final PageViewport viewport) {
		if(this.viewport != viewport) {
			this.viewport = viewport;
		}
	}

	/**
	 * 
	 * 根据指定的点，获得包含该点的TextArea所占据的矩形区域
	 * 
	 * @param p
	 *            指定坐标点
	 * @return {@link Rectangle2D} 包含指定点的TextArea所占据的区域
	 */
	public Rectangle2D getRectWithPoint(final Point2D p) {
		final Area area = getAreaWithPoint(p);
		if (isNull(area)) {
			return null;
		}
		return area.getViewport();
	}
	/**
	 * 获得包含指定位置的SimplePageMaster对象。
	 * @param pos	指定位置信息
	 * @return 返回SimplePageMaster对象。
	 */
	public final static SimplePageMaster getSimplePageMaster(DocumentPosition pos){
		AbstractEditComponent comp = SystemManager.getMainframe().getEidtComponent();
		PointerInfo pointer = MouseInfo.getPointerInfo();
		Point p = pointer.getLocation();
		SwingUtilities.convertPointFromScreen(p, comp);
		
		PageViewport view = comp.getPageViewport(p);
		if(view == null)
			return null;
		
		return view.getSimplePageMaster();
	}

	/**
	 * 
	 * 根据指定位置信息，获得包含该位置的LineArea
	 * 
	 * @param position
	 *            指定位置信息
	 * @return {@link LineArea} 返回包含指定位置的LineArea
	 */
	public final static LineArea getLineArea(final DocumentPosition position) {
		LineArea area = null;
		final Area a = getAreaWithPosition(position);
		area = getLineArea(a);
		return area;
	}
	/**
	 * 获得空位置【Block】中的Line-Area
	 * @param position
	 * @return
	 */
	public final static LineArea getLineAreaDown(final DocumentPosition position){
		Area area = position.getLeafElement().getArea();
		
		return getLineAreaDownImp(area);
	}
	private final static LineArea getLineAreaDownImp(Area area){
		if(area == null)
			return null;
		if(area instanceof LineArea)
			return (LineArea)area;
		List<Area> areas = area.getChildAreas();
		if(areas == null || areas.isEmpty())
			return null;
		
		return getLineAreaDownImp(areas.get(0));
	}
	/**
	 * 
	 * 根据指定TextArea获得包含该Area的LineArea
	 * 
	 * @param area
	 *            指定TextArea
	 * @return {@link LineArea} 返回包含指定TextArea的LineArea
	 */
	public static LineArea getLineArea(final Area area) {
		LineArea line = null;
		if (isNull(area)) {
			return line;
		}
		// TODO 获得包含该TextArea的LineArea
		line = searchLineArea(area);
		return line;
	}
	
	/**
	 * 
	 * 获得包含指定InleinLeveArea的LineArea对象
	 * 
	 * @param area
	 *            指定InlineLevelArea对象
	 * @return {@link LineArea} 返回包含指定are的LineArea对象
	 */
	public static LineArea searchLineArea(final Area area) {
		return (LineArea) searchArea(area, LineArea.class);
	}

	/**
	 * 根据指定的Area，获得NormalFlow【向上遍历】
	 * 
	 * @param area
	 *            指定Area
	 * @return 返回获得的NormalFlow
	 */
	public static NormalFlow getNormalFlow(final Area area) {
		return (NormalFlow) searchArea(area, NormalFlow.class);
	}

	/**
	 * 获得Block-Level的Area，如：NormalFlow，Block，BlockViewport、BeforeFloat、
	 * Footernote
	 * 
	 * @param area
	 *            指定一个Inline-Level的Area
	 * @return {@link BlockParent} 返回检索的结果。
	 */
	public static RegionReference getRegionReference(final Area area) {
		return (RegionReference) searchAllArea(area, RegionReference.class);
	}

	/**
	 * 根据指定Area，获得当前页包含的所有NormalFlow对象。
	 * 
	 * @param area
	 *            指定Area
	 * @return {@link List} 返回当前页的NormalFlow集合。
	 */
	public static List<NormalFlow> getNormalFlows(final Area area) {
		final Span span = getCurrentSpan(area);
		if (isNull(span)) {
			return new ArrayList<NormalFlow>();
		}
		return span.getAllFlows();
	}

	/**
	 * 根据指定的Area获得当前页的Spane对象。
	 * 
	 * @param area
	 *            指定Area
	 * @return {@link Span} 获得当前也的Spane
	 */
	private static Span getCurrentSpan(final Area area) {
		return (Span) searchArea(area, Span.class);
	}

	/**
	 * 
	 * 根据指定Area向上遍历AreaTree，获得指定类型的Area
	 * 
	 * @param area
	 *            指定Area
	 * @param type
	 *            指定类型
	 * @return {@link Area} 返回指定类型的Area，如果没有返回Null。
	 */
	public static Area searchArea(final Area area, final Class type) {
		if (isNull(area) || isNull(type)) {
			return area;
		}

		if (area.getClass() == type) {
			return area;
		}

		return searchArea(area.getParentArea(), type);
	}

	/**
	 * 根据指定的Area，向上遍历AreaTree，获得指定类型、及子类型的对象。
	 * 
	 * @param area
	 *            指定Area
	 * @param type
	 *            指定类型
	 * @return {@link Area} 返回指定类型、子类型的对象。
	 */
	private static Area searchAllArea(Area area, final Class type) {
		if (isNull(area) || isNull(type)) {
			return area;
		}
		area = area.getParentArea();
		if (isNull(area)) {
			return area;
		}
		if (type.isAssignableFrom(area.getClass())) {
			return area;
		}
		return searchAllArea(area, type);
	}

	/**
	 * 根据指定的位置信息，向上遍历AreaTree，获得指定类型的Area
	 * 
	 * @param pos
	 *            指定位置信息
	 * @param type
	 *            指定类型
	 * @return {@link Area} 返回找到的Arae，如果没有找到返回Null。
	 */
	public static Area searchArea(final DocumentPosition pos, final Class type) {
		final Area area = getAreaWithPosition(pos);
		return searchArea(area, type);
	}

	/**
	 * 
	 * 判断指定TextArea是否被包含在TableCell产生的Block中，如果包含，则返回TableCell产生的Block 否则返回Null。
	 * 
	 * @param area
	 *            指定TextAread对象
	 * @return {@link Area} 返回TableCell产生的Block
	 */
	public static Area searchAbsoluteArea(final Area area) {
		return searchAbsoluteArea(area, AreaKind.CELL);
		/*
		 * if (isNull(area)) return area;
		 * 
		 * if (area.getClass() == Block.class) { Block block = (Block) area; if
		 * (block.isTableCell()) { return area; } }
		 * 
		 * return searchAbsoluteArea(area.getParentArea());
		 */
	}

	/**
	 * 根据指定的Area【任意子Area】，查找其所在的Table Area。
	 * 
	 * @param block
	 *            指定任意Table中的子Area。
	 * @return {@link Block} 返回找到的Table Area。
	 */
	public static Block getCurrentTableArea(final Area block) {
		final Area area = searchAbsoluteArea(block, AreaKind.TABLE);
		if (area instanceof Block) {
			return (Block) area;
		}
		return null;
	}

	private static Area searchAbsoluteArea(final Area area, final AreaKind kind) {
		if (isNull(area)) {
			return area;
		}
		if (area.getClass() == Block.class) {
			final Block block = (Block) area;
			if (block.getAreaKind() == kind) {
				return area;
			}
		}
		return searchAbsoluteArea(area.getParentArea(), kind);
	}

	/**
	 * 
	 * 根据指定的位置获得直接包含指定位置的Block对象【最近的父对象是Block的】
	 * 
	 * @param position
	 *            指定位置
	 * @return {@link Block} 返回包含指定位置的Block对象
	 */
	public static Block getBlockWithPosition(final DocumentPosition position) {
		Block block = null;
		final Area area = getAreaWithPosition(position);
		if (isNull(area)) {
			return block;
		}

		block = (Block) searchArea(area, Block.class);

		return block;
	}

	/**
	 * 
	 * 把指定的矩形区域根据指定水平、竖直的放缩比率，进行放缩处理。
	 * 
	 * @param source
	 *            指定要放缩的矩形区域
	 * @param scaleX
	 *            指定水平放缩系数
	 * @param scaleY
	 *            指定竖直放缩系数
	 * @return {@link Rectangle2D} 返回放缩后的矩形区域
	 */
	public static Rectangle2D getScaleRectangle(final Rectangle2D source,
			final double scaleX, final double scaleY) {
		if (isNull(source)) {
			return source;
		}
		double x = source.getX(), y = source.getY(), width = source.getWidth(), height = source
				.getHeight();

		x *= scaleX;
		y *= scaleY;
		width *= scaleX;
		height *= scaleY;

		source.setRect(x, y, width, height);

		return source;
	}

	/**
	 * 
	 * 把指定的矩形区域，按指定的放缩系数，进行放缩处理。
	 * 
	 * @param source
	 *            指定要缩放的矩形区域
	 * @param scale
	 *            指定缩放系数
	 * @return {@link Rectangle2D} 返回缩放后的矩形区域
	 */
	public static Rectangle2D getScaleRectangle(final Rectangle2D source, final double scale) {
		return getScaleRectangle(source, scale, scale);
	}

	/**
	 * 
	 * 根据指定的点获得包含该点的RegionReference
	 * 
	 * @param point
	 *            指定点的位置
	 * @return {@link RegionReference} 返回包含指定点的RegionReference
	 */
	public RegionReference getReferenceWithPoint(final Point2D point) {
		RegionReference reference = null;

		final RegionViewport viewport = getRegionWithPoint(point);
		if (isNull(viewport)) {
			return reference;
		}

		reference = viewport.getRegionReference();
		return reference;
	}

	/**
	 * 
	 * 获得Regionbody的Viewport对象
	 * 
	 * @return {@link RegionViewport} 返回包含Regionbody的Viewport对象
	 */
	public RegionViewport getBodyViewport() {
		return getRegionViewport(Constants.FO_REGION_BODY);
	}

	public RegionViewport getBeforeViewport() {
		return getRegionViewport(Constants.FO_REGION_BEFORE);
	}

	public RegionViewport getAfterViewport() {
		return getRegionViewport(Constants.FO_REGION_AFTER);
	}

	public RegionViewport getStartViewport() {
		return getRegionViewport(Constants.FO_REGION_START);
	}

	public RegionViewport getEndViewport() {
		return getRegionViewport(Constants.FO_REGION_END);
	}

	private RegionViewport getRegionViewport(final int id) {
		final Page page = getPage();
		if (isNull(page)) {
			return null;
		}
		final RegionViewport view = page.getRegionViewport(id);

		return view;
	}

	/**
	 * 
	 * 获得包含指定点的RegionViewport
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private RegionViewport getRegionWithPoint(final Point2D point) {
		RegionViewport viewport = null;
		final Page page = getPage();
		if (isNull(point) || isNull(page)) {
			return viewport;
		}

		for (final int id : REGIONS_ID) {
			viewport = page.getRegionViewport(id);

			if (isNull(viewport)) {
				continue;
			}
			if (viewport.getViewport().contains(point)) {
				break;
			}
		}
		if(isNull(viewport)){
			viewport = page.getRegionViewport(Constants.FO_REGION_BODY);
			convertToBody(point, viewport.getViewport());
		}
		return viewport;
	}
	private Point2D convertToBody(final Point2D point, final Rectangle2D body){
		final int code = body.outcode(point);
		double x = point.getX();
		double y = point.getY();
		if(code == Rectangle2D.OUT_TOP){
			y = body.getY();
		}else if(code == Rectangle2D.OUT_BOTTOM) {
			y = body.getMaxY() - 500;
		}else if(code == Rectangle2D.OUT_LEFT){
			x = body.getX();
		}else if(code == Rectangle2D.OUT_RIGHT){
			x = body.getMaxX() - 500;
		}else if(code == (Rectangle2D.OUT_LEFT | Rectangle2D.OUT_TOP)){
			x = body.getX();
			y = body.getY();
		}else if(code == (Rectangle2D.OUT_RIGHT | Rectangle2D.OUT_TOP)){
			x = body.getMaxX() - 500;
			y = body.getY();
		}else if(code == (Rectangle2D.OUT_LEFT | Rectangle2D.OUT_BOTTOM)){
			x = body.getX();
			y = body.getMaxY() - 500;			
		}else if(code == (Rectangle2D.OUT_RIGHT | Rectangle2D.OUT_BOTTOM)){
			x = body.getMaxX() - 500;
			y = body.getMaxY() - 500;
		}
		point.setLocation(x, y);
		return point;
	}
	/**
	 * 
	 * 获得当前Page
	 * 
	 * @return {@link Page} 返回当前PageViewport包含的Page
	 */
	private Page getPage() {
		if (isNull(viewport)) {
			return null;
		}
		return viewport.getPage();
	}

	/**
	 * 
	 * 根据指定的点，获得包含该点的TextArea对象
	 * 
	 * @param point
	 *            指定点
	 * @return {@link TextArea} 返回包含指定的点的TextArea
	 */
	public InlineArea getAreaWithPoint(final Point2D point) {
		final List<Block> blocks = getBlocksWithPoint(point);

		if (isNull(blocks)) {
			return null;
		}

		return searchTextArea(blocks, point);
	}

	/**
	 * 
	 * 从指定的Area集合中获得，包含指定点的Area对象，如果没有返回Null
	 * 
	 * @param araes
	 *            指定检索的集合
	 * @param point
	 *            指定Point2D
	 * @return {@link TextArea} 返回包含的指定点的TextArea对象，如果没有返回Null
	 */
	@SuppressWarnings("unchecked")
	private InlineArea searchTextArea(final List<? extends Area> areas, final Point2D point) {
		InlineArea area = null;
		if (isNull(areas)) {
			return area;
		}
		for (final Area a : areas) {
			if (a instanceof TextArea) {
				final Rectangle2D r = ((TextArea) a).getViewport();
				if (r.contains(point)) {
					area = (TextArea) a;
				}
			}
			if (a instanceof Viewport) {
				final Rectangle2D r = ((Viewport) a).getViewport();
				if (r.contains(point)) {
					area = (Viewport) a;
				}
			}
			if (!(a instanceof TextArea) && !(a instanceof Viewport)) {
				area = searchTextArea(a.getChildAreas(), point);
			}

			if (area != null) {
				break;
			}
		}

		return area;

	}

	/**
	 * 
	 * 获得包含该坐标点的Reference中所有Block对象，并以List的形式返回
	 * 
	 * @param point
	 *            指定坐标点
	 * @return {@link List} Block集合
	 */
	@SuppressWarnings("unchecked")
	public List<Block> getBlocksWithPoint(final Point2D point) {
		List<Block> blocks = null;
		final RegionReference reference = getReferenceWithPoint(point);
		if (isNull(reference)) {
			return blocks;
		}

		if (reference instanceof BodyRegion) {
			blocks = getBlocksWithPoint((BodyRegion) reference);

			final double x = point.getX() - ((BodyRegion) reference).getMarginLeft();
			final double y = point.getY() - ((BodyRegion) reference).getMarginTop();
			point.setLocation(x, y);
		} else {
			blocks = reference.getBlocks();
		}

		return blocks;
	}

	/**
	 * 
	 * 获得BodyRegion中包含的所有Block对象集
	 * 
	 * @param body
	 *            指定BodyRegion
	 * @return {@link List} 返回包含的所有Block集合
	 */
	private List<Block> getBlocksWithPoint(final BodyRegion body) {
		final MainReference main = body.getMainReference();
		final Span span = main.getCurrentSpan();
		final List<NormalFlow> flows = span.getAllFlows();
		final List<Block> blocks = new ArrayList<Block>();

		List<Block> temp = null;
		for (final NormalFlow flow : flows) {
			temp = flow.getChildAreas();
			if (isNull(temp)) {
				continue;
			}

			blocks.addAll(temp);
		}

		return blocks;
	}

	/**
	 * 把DocumentPanel的坐标转换到PageViewportPanel中的页中【BufferedImage】
	 * 
	 * @param e
	 *            指定发生在AbstractEditComponent上的鼠标事件
	 * @return {@link Point2D} 返回坐标为BufferedImage上的坐标点。
	 */
	public Point2D convertPoint(final MouseEvent e) {
		if (isNull(e)) {
			return null;
		}
		final Component comp = e.getComponent();
		final AbstractEditComponent edit = getParentPanel(comp);
		if(isNull(edit)) {
			return null;
		}
		final Point p0 = e.getPoint();
		final PageViewportPanel panel = edit.findPageViewportPanel(p0);
		final Point2D p = SwingUtilities.convertPoint(comp, p0, panel);
		return panel.getDPIPoint(p);
	}
	/**
	 * 根据制定的控件，获得指定控件的AbstractEditComponent类型的父对象。
	 * @param comp		指定控件
	 * @return	{@link AbstractEditComponent}	返回父对象，如果没有AbstractEditComponent类型的父对象，返回Null
	 */
	public AbstractEditComponent getParentPanel(Component comp) {
		if (isNull(comp)) {
			return null;
		}else if (comp instanceof AbstractEditComponent) {
			return (AbstractEditComponent) comp;
		} else {
			comp = comp.getParent();
			return getParentPanel(comp);
		}
	}
	/**
	 * 获得鼠标事件位置的Area的类型
	 * 
	 * @param e
	 *            指定鼠标事件。
	 * @return {@link AreaKind} 返回鼠标事件位置的Area 类型。
	 */
	public AreaKind getCurrentAreaKind(final MouseEvent e) {
		final Point2D p = convertPoint(e);
		if (isNull(p)) {
			return AreaKind.NONE;
		}
		return getCurrentAreaKind(p);
	}

	/**
	 * 获得包含当前坐标点的Area类型。
	 * 
	 * @param p
	 *            指定坐标点【PageViewportPanel中getDPIPoint处理后的坐标，可用convertPoint方法获得】
	 * @return {@link AreaKind} 返回包含当前坐标点的Area类型。
	 */
	public AreaKind getCurrentAreaKind(Point2D p) {
		final Area region = getRegionWithPoint(p);
		final Point2D point = (Point2D) p.clone();
		Area area = getCurrentContainer(region, point);
		if (isNull(area)) {
			area = region;
		} else {
			//---------------------add by lxg 2009-11-20--------------------
			//获得Container所在的Region
			Area a = searchArea(area, RegionViewport.class);
			if(a != region){
				area = region;
			}else{
				p = point;
			}
			//---------------------add by lxg 2009-11-20--------------------
			
			//p = point; del by lxg 2009-11-20
		}
		return getCurrentAreaKind(area, p);
	}
	
	@SuppressWarnings("unchecked")
	private AreaKind getCurrentAreaKind(final Area area, final Point2D p) {
		if (isNull(area) || isNull(p)) {
			return AreaKind.NONE;
		}
		AreaKind result = AreaKind.NONE;
		if (area.isViewport()) {
			final Rectangle2D rect = area.getViewport();
			if (!rect.contains(p)) {
				return area.getAreaKind();
			} else {
				result = area.getAreaKind();
			}
			final double left = area.getBorderAndPaddingWidthBefore();
			final double top = area.getBorderAndPaddingWidthStart();
			final double x = p.getX() - rect.getX() - left;
			final double y = p.getY() - rect.getY() - top;
			p.setLocation(x, y);
		}
		final List<Area> children = area.getChildAreas();
		for (final Area a : children) {
			if (a.isBackground()) {
				continue;
			}

			if (isContainPoint(a, p)) {
				if (isValidKind(a.getAreaKind())) {
					result = a.getAreaKind();
				}
			} else if (a.isEquateViewport()) {
				continue;
			}
			if (!(a instanceof TextArea) && hasChildren(a)) {
				final AreaKind temp = getCurrentAreaKind(a, p);
				if (temp != AreaKind.NONE) {
					result = temp;
				}
			}
			if (isValidKind(result)) {
				break;
			}
		}

		return result;
	}

	private boolean isValidKind(final AreaKind kind) {
		return (kind == AreaKind.CELL) 
				|| (kind == AreaKind.CONTAINER) 
				|| (kind == AreaKind.SVG_CONTAINER)
				||	(kind == AreaKind.TABLE_CONTENT)
				|| (kind == AreaKind.SVG_FIGURE);
	}

	public Area getViewportArea(final Point2D point) {
		final Area area = getRegionWithPoint(point);
		return getAreaWithClass(area, point, Viewport.class,
				BlockViewport.class);
	}

	@SuppressWarnings("unchecked")
	private Area getAreaWithClass(final Area area, final Point2D p, final Class... clazz) {
		if (isNull(area) || isNull(p)) {
			return area;
		}
		Area result = null;
		final List<Class> list = Arrays.asList(clazz);
		final List<Area> children = area.getChildAreas();
		for (final Area a : children) {
			if (a instanceof BodyRegion) {
				final double x = p.getX() - ((BodyRegion) a).getMarginLeft();
				final double y = p.getY() - ((BodyRegion) a).getMarginTop();
				p.setLocation(x, y);
			}
			if (isContainPoint(a, p) && (list.size() == 0)) {
				return a;
			}
			if (!(a instanceof TextArea) && hasChildren(a)) {
				final Area temp = getAreaWithClass(a, p, clazz);
				if (!isNull(temp)) {
					result = temp;
				}
			}

			if (!isNull(result)) {
				break;
			}
		}

		return result;
	}

	/**
	 * 
	 * 获得包含当前坐标点的Area,【最小范围的Area】
	 * 
	 * @param point
	 *            指定坐标点
	 * @return {@link Area} 包含指定点的Area
	 */
	public Area getCurrentArea(final Point2D point) {
		return getCurrentArea(point, Boolean.TRUE);
	}

	/**
	 * 获得包含当前坐标点的Area,【最小范围的Area】
	 * 
	 * @param point
	 *            指定坐标点。
	 * @param isFixed
	 *            指定是否在保持坐标点，Ture：保持，False，可以修改点坐标。
	 * @return {@link Area} 包含指定点的Area
	 */
	public Area getCurrentArea(final Point2D point, final boolean isFixed) {
		final Area region = getRegionWithPoint(point);

		Point2D p = point;
		if (isFixed) {
			p = (Point2D) point.clone();
		}

		Area temp = getContainerWithPoint(region, p, Boolean.TRUE);

		if (isNull(temp)) {
			temp = getAreaWithPoint(region, point);
		}
		if (!isNull(temp)) {
			return temp;
		}

		return region;
	}
	public QianZhangArea getCurrentQianZhangArea(Point2D point)
	{
		Area area = getRegionWithPoint(point);
		if (isNull(point) || isNull(viewport) || isNull(area))
		{
			return null;
		}
		Point2D pc = (Point2D) point.clone();
		Area temp = getCurrentContainer(area, pc);
		if (temp != null)
		{

			area = temp;
			final double[] offset = getOffsetContainerOrOther(area);
			double x = point.getX(), y = point.getY();
			point.setLocation((x - offset[0]) / 1000, (y - offset[1]) / 1000);
		} else
		{
			final double[] offset = getOffset(area);
			final double x = point.getX(), y = point.getY();
			point.setLocation((x - offset[0]) / 1000, (y - offset[1]) / 1000);
		}
		Rectangle2D rect = null;
		Point2D p = point;
		final List<QianZhangArea> areas = viewport.getQianZhangAreas();
		if (isNull(areas) || areas.size() == 0)
		{
			return null;
		}
		QianZhangArea block = null;
		double min = Double.MAX_VALUE;
		for (final QianZhangArea qianzhangarea : areas)
		{
			rect = qianzhangarea.getViewport();
			if (!rect.contains(p))
			{
				continue;
			}
			Area parentarea = qianzhangarea.getParentArea();
			while (parentarea != null && parentarea != area
					&& !(parentarea instanceof BlockViewport))
			{
				parentarea = parentarea.getParentArea();
			}
			if (parentarea != area)
			{
				continue;
			}
			final double tempd = p.distance(rect.getX(), rect.getY());
			if (tempd >= min)
			{
				continue;
			}

			min = tempd;
			block = qianzhangarea;
		}
		return block;
	}

	/**
	 * 
	 * 根据指定ReigonViewport获得包含指定坐标的最小范围的Area，如：Block包含了该坐标，同时
	 * Block中的TextArea也包含了该坐标，则返回TextArea对象。
	 * 
	 * @param area
	 *            指定RegionViewport【要遍历的父对象】
	 * @param p
	 *            指定坐标点
	 * @return {@link Area} 包含指定点的最小Area
	 */
	@SuppressWarnings("unchecked")
	public Area getAreaWithPoint(final Area area, final Point2D p) {
		if (isNull(area) || isNull(p)) {
			return area;
		}
		if (area.isViewport()) {
			final Rectangle2D rect = area.getViewport();
			if (!rect.contains(p)) {
				return null;
			}
			final double left = area.getBorderAndPaddingWidthBefore();
			final double top = area.getBorderAndPaddingWidthStart();
			final double x = p.getX() - rect.getX() - left;
			final double y = p.getY() - rect.getY() - top;
			p.setLocation(x, y);
		}
		if (area.isSVGContainer()) {//area instanceof BlockViewport
			final Viewport view = searchViewportFromContainer((BlockViewport) area);
			if (isContainPoint(view, p)) {
				return view;
			/*
			 * else return null;
			 */
			}
		}else if(area.isTableContent()) {
			return area;
		}
		Area result = null;
		final List<Area> children = area.getChildAreas();
		for (final Area a : children) {
			/*
			 * if (a instanceof BlockViewport) continue;
			 */
			if (a.isBackground()) {
				continue;
			}

			if (isContainPoint(a, p)) {
				result = a;
				if(a.isContainer() && a.getSource().getChildCount() == 0) {
					return result;
				}
			} else if (a.isEquateViewport()) {
				continue;
			}

			/*
			 * if (a.isViewport()) { Rectangle2D rect = a.getViewport(); double
			 * left = a.getBorderAndPaddingWidthBefore(); double top =
			 * a.getBorderAndPaddingWidthStart(); double x = p.getX() -
			 * rect.getX() - left; double y = p.getY() - rect.getY() - top;
			 * p.setLocation(x, y); }
			 */

			if (!(a instanceof TextArea) && hasChildren(a)) {
				final Area temp = getAreaWithPoint(a, p);
				if (!isNull(temp)) {
					result = temp;
				}
			}

			if (!isNull(result)) {
				break;
			}
		}
		return result;
	}

	/** 判断指定Area是否包含指定的点。 */
	private static boolean isContainPoint(final Area area, final Point2D p) {
		final Rectangle2D rect = area.getViewport();

		return (!isNull(rect)) && (rect.contains(p));
	}

	/** 当Viewport上放置的是svg图形时，判断指定的点是否在Viewport的图形上。 */
	private static boolean isContainPoint(final Viewport view, final Point2D p) {
		if (isNull(view)) {
			return Boolean.FALSE;
		}
		if (!view.isFigure()) {
			return isContainPoint((Area) view, p);
		}
		final Canvas canvas = (Canvas) view.getSource();
		final List<CellElement> svgs = canvas.getAllChildren();
		AbstractSVG svg = null;
		boolean flag = Boolean.FALSE;
		for (final CellElement cell : svgs) {
			svg = (AbstractSVG) cell;
			flag = SVGLocationConvert.isSelectable(svg, p);
			if (flag) {
				break;
			}
		}
		return flag;
	}

	/* 如果当前的Container是用于放置图形，则找到图形所在的Viewport */
	public static Viewport searchViewportFromContainer(final BlockViewport area) {
		if (isNull(area)) {
			return null;
		}
		if (!area.isFigure()) {
			return null;
		}
		final Area a = searchFigure(area, Viewport.class);
		if (a instanceof Viewport) {
			return (Viewport) a;
		} else {
			return null;
		}
	}

	/* 向下遍历Area Tree，查找指定类型的对象。 */
	@SuppressWarnings("unchecked")
	private static Area searchFigure(final Area area, final Class type) {
		if (isNull(area) || isNull(type)) {
			return area;
		}
		if (area.getClass() == type) {
			return area;
		}
		final List<Area> areas = area.getChildAreas();
		if (isNull(areas) || areas.isEmpty()) {
			return area;
		}
		return searchFigure(areas.get(0), type);
	}

	/** 判断RegionViewport中Container是否有包含指定点的Container，isContainer指定是否查找Container */
	private Area getContainerWithPoint(final Area region, final Point2D point,
			final boolean isContinue) {
		/*
		 * if (isNull(point) || isNull(viewport) || isNull(region)) return null;
		 * 
		 * double[] offset = getOffsetOfBlocks(region); double x = point.getX(),
		 * y = point.getY(); point.setLocation(x - offset[0], y - offset[1]);
		 * Rectangle2D rect = null; Point2D p = point; List<BlockViewport> areas
		 * = viewport.getContainers();
		 * 
		 * if (isNull(areas) || areas.size() == 0) return null; BlockViewport
		 * block = null; double min = Double.MAX_VALUE; for (BlockViewport area
		 * : areas) { rect = area.getViewport(); p = (Point2D) p.clone(); if
		 * (!rect.contains(p)) if (!isEnterRectangel(area, p)) continue; double
		 * temp = p.distance(rect.getX(), rect.getY()); if (temp >= min)
		 * continue;
		 * 
		 * min = temp; block = area; }
		 */
		final BlockViewport block = (BlockViewport) getCurrentContainer(region, point);
		Area view = searchArea(block, RegionViewport.class);
		if(view != region){
			return null;
		}
		if (!isContinue) {
			return block;
		}
		if(!isNull(block) && block.isContainer() && block.getSource().getChildCount() == 0){
			return block;
		}

		final Area a = getAreaWithPoint(block, point);
		return (a == null && (!isNull(block) && !block.isFigure()))? block : a;
	}

	/* 找到包含指定坐标点的Container */
	private Area getCurrentContainer(final Area region, final Point2D point) {
		if (isNull(point) || isNull(viewport) || isNull(region)) {
			return null;
		}
		final double[] offset = getOffsetOfBlocks(region);
		final double x = point.getX(), y = point.getY();
		point.setLocation(x - offset[0], y - offset[1]);
		Rectangle2D rect = null;
		Point2D p = point;
		final List<BlockViewport> areas = viewport.getContainers();

		if (isNull(areas) || areas.size() == 0) {
			return null;
		}
		BlockViewport block = null;
		double min = Double.MAX_VALUE;
		for (final BlockViewport area : areas) {
			rect = area.getViewport();
			p = (Point2D) p.clone();
			if (!isEnterRectangel(area, p)) {
				continue;
			}
			final double temp = p.distance(rect.getX(), rect.getY());
			if (temp >= min) {
				continue;
			}

			min = temp;
			block = area;
		}
		return block;
	}

	/** 判断指定的点是否在指定的区域呢，如果区域是相对的，那么点在区域的两侧【仅仅是两侧时】也看作是在区域内。 */
	private boolean isEnterRectangel(final BlockViewport block, final Point2D p) {
		final Rectangle2D rect = block.getViewport();
		if (isNull(rect)) {
			return Boolean.FALSE;
		}
		boolean flag = rect.contains(p);
		if (block.isRelative()) {
			final Rectangle2D temp = getBlockParent(block);
			boolean b = true;
			if (!isNull(temp)) {
				b = temp.contains(p);
			}
			final int outer = rect.outcode(p);
			flag |= ((outer == Rectangle.OUT_LEFT || outer == Rectangle.OUT_RIGHT) & b);
		}
		return flag;
	}

	private Rectangle2D getBlockParent(final BlockViewport block) {
		final Block b = (Block) searchAllArea(block, Block.class);
		if (isNull(b)) {
			return null;
		}
		final Rectangle2D r = b.getViewport(), rect = block.getViewport();
		final double max1 = r.getX() + r.getWidth(), max2 = rect.getX()
				+ rect.getWidth(), max = Math.max(max1, max2);
		return new Rectangle2D.Double(rect.getX(), rect.getY(), max, rect
				.getHeight());
	}

	/**
	 * 向上遍历AreaTree，获得Block级别的Area，在空位置时，如果是Container
	 * 需要获得BlockViewport，其它的他情况直接找的其对应的Block
	 */
	private final static Area searchBlockLevelArea(final Area line, final boolean isContainer) {
		if (isNull(line)) {
			return line;
		}

		if (line instanceof Block) {
			if (!isContainer) {
				return line;
			}

			if (line.isContainer()) {
				return line;
			}
		}
		return searchBlockLevelArea(line.getParentArea(), isContainer);
	}

	/**
	 * 向下遍历Area，找到指定Area中第一行【Line-Area】，最后一行【Line-Area】
	 * 
	 * @param block
	 *            指定Area
	 * @param isFirst
	 *            指定是第一行：True、最后一行：False
	 * @return {@link Area} 返回找到的Line-Area，如果没有找到返回Null。
	 */
	public static Area getFirstOrLastLineArea(final Area block, final boolean isFirst) {
		if (isNull(block)) {
			return block;
		}
		final List<Area> areas = block.getChildAreas();
		if (isNull(areas) || areas.size() == 0) {
			return block;
		}
		Area area = null;
		if (isFirst) {
			area = areas.get(0);
		} else {
			area = areas.get(areas.size() - 1);
		}
		if (!(area instanceof LineArea)) {
			return getFirstOrLastLineArea(area, isFirst);
		} else if (area instanceof LineArea) {
			return area;
		}

		return null;
	}

	/* 获得Line Level 的Area ，如果没有返回传入的Area */
	@SuppressWarnings("unchecked")
	private final static Area findLineLevelArea(final Area block, final Point2D point) {
		if (isNull(block) || isNull(point)) {
			return block;
		}
		//---------------------add by lxg 2009-11-19--------------------
		if (block.isContainer()) {
			final Rectangle2D rect = block.getViewport();
			/*if (!rect.contains(point)) {
				return null;
			}*/
			final double left = block.getBorderAndPaddingWidthBefore();
			final double top = block.getBorderAndPaddingWidthStart();
			final double x = point.getX() - rect.getX() - left;
			final double y = point.getY() - rect.getY() - top;
			point.setLocation(x, y);
			if(rect.getHeight() < point.getY() || point.getY() < 0){
				return block;
			}
		}
		//---------------------add by lxg 2009-11-19--------------------
		if (block instanceof LineArea) {
			return block;
		}

		final List<Area> areas = block.getChildAreas();
		if (isNull(areas) || areas.size() == 0) {
			return block;
		}

		Rectangle2D viewport = null;
		final int last = areas.size() - 1;
		int outer = 0, count = -1;
		boolean flag = Boolean.FALSE;
		for (final Area area : areas) {
			flag = Boolean.FALSE;
			count++;
			viewport = area.getViewport();
			if (!isNull(viewport) && viewport.contains(point)) {
				return area;
			}
			if (!(area instanceof LineArea)) {
				final Area temp = findLineLevelArea(area, point);
				if(temp != area) {
					return temp;
				}
			}
			outer = viewport.outcode(point);
			if (count == 0) {
				//----------------------------add by lxg 2009-11-19--------------------------				
				if((outer & Rectangle2D.OUT_TOP) == Rectangle2D.OUT_TOP){
					return area;
				}
				//----------------------------add by lxg 2009-11-19--------------------------				
				flag = isOuterOf(outer, Rectangle2D.OUT_TOP);
			}
			if (!flag && count == last) {
				//----------------------------add by lxg 2009-11-19--------------------------
				if((outer & Rectangle2D.OUT_BOTTOM) == Rectangle2D.OUT_BOTTOM){
					return area;                                         
				} 
				//----------------------------add by lxg 2009-11-19--------------------------			
				flag = isOuterOf(outer, Rectangle2D.OUT_BOTTOM);
			}

			if (isOuterOf(outer, Rectangle2D.OUT_LEFT)
					|| isOuterOf(outer, Rectangle2D.OUT_RIGHT) || flag) {
				return area;
			}
		}
		return block;
	}

	private final static Area findBlockLevelArea(final Area block, final Point2D p) {
		if (isNull(block)) {
			return block;
		}
		final List<Area> areas = block.getChildAreas();
		if (isNull(areas) || areas.size() == 0) {
			return block;
		}
		Rectangle2D viewport = null;
		final int last = areas.size() - 1;
		int outer = 0, count = -1;
		boolean flag = Boolean.FALSE;
		for (final Area area : areas) {
			if (area.isReference()) {
				final Point2D point = (Point2D) p.clone();
				final int left = block.getBorderAndPaddingWidthStart();
				final int top = block.getBorderAndPaddingWidthBefore();
				final Rectangle2D r = area.getViewport();
				point.setLocation(point.getX() - left - r.getX(), point.getY()
						- top - r.getY());
				final Area a = findBlockLevelArea(area, point);
				if (a != area) {
					return a;
				}
				continue;
			}
			flag = Boolean.FALSE;
			count++;
			if (!(area instanceof Block)) {
				continue;
			}
			final Block b = (Block) area;
			viewport = b.getViewport();
			if (isNull(viewport)) {
				continue;
			}
			outer = viewport.outcode(p);
			if (count == 0) {
				flag = isOuterOf(outer, Rectangle2D.OUT_TOP);
			}
			if (!flag && count == last) {
				flag = isOuterOf(outer, Rectangle2D.OUT_BOTTOM);
			}

			if (isOuterOf(outer, Rectangle2D.OUT_LEFT)
					|| isOuterOf(outer, Rectangle2D.OUT_RIGHT) || flag) {
				return area;
			}
			
		}
		return block;
	}

	private final static boolean isOuterOf(final int outer, final int indicator) {
		// Rectangle2D.OUT_TOP || Rectangle2D.OUT_BOTTOM || Rectangle2D.OUT_LEFT
		// || Rectangle2D.OUT_RIGHT
		assert (indicator <= 8) : "非法参数，请检查【OUT_TOP|OUT_BOTTOM|OUT_LEFT|OUT_RIGHT】！";
		return ((outer | indicator) == indicator);
	}

	/* 矩形区域外两侧 指示器 */
	/*
	 * private int getBothSideIndicator(Area a){ //TODO 根据书写方向计算 return
	 * (Rectangle2D.OUT_LEFT | Rectangle2D.OUT_RIGHT); }
	 */

	/** 根据指定的点，或的LineArea中距离点最近的TextArea */
	@SuppressWarnings("unchecked")
	private static Area getTextAreaFromLine(final Area line, final Point2D point) {
		if (isNull(line) || !(line instanceof LineArea)) {
			return line;
		}
		final List<Area> list = line.getChildAreas();
		final double lineX = point.getX();
		double minX = -1, maxX = -1;
		int count = -1;
		InlineArea text = null;
		Rectangle2D rect = null;
		for (final Area area : list) {
			count++;
			if(area instanceof GroupInlineArea)
				continue;
			final InlineArea temp = searchTextAreaFromInLine(area);
			/*if (isNull(temp)) {//删除：by 李晓光	2009-7-23
				return text;
			}*/
			if(temp == null)
				continue;
			text = temp;
			if (text instanceof TextArea || text instanceof Viewport) {
				rect = getRectWithInContainer(text);
			} else {
				LogUtil.debug("area class = " + area.getClass());
				continue;
			}

			if (isNull(rect)) {
				LogUtil.debug("Inline Area 区域为空！");
				continue;
			}
			minX = rect.getX();
			maxX = rect.getX() + rect.getWidth();
			if (lineX >= minX && lineX <= maxX) {
				return text;
			} else if (lineX < minX && count == 0) {
				return text;
			} else if (lineX > maxX && count == list.size() - 1) {
				return text;
			}
		}
		if(text != null)
			return text;
		return line;
	}

	private static Rectangle2D getRectWithInContainer(final Area area) {
		Rectangle2D rect = null;
		if (isNull(area)) {
			return rect;
		}

		rect = area.getViewport();
		if(isNull(rect)) {
			return rect;
		}
		final double[] offset = getOffsetOfContainer(area, Boolean.FALSE);
		rect = (Rectangle2D) rect.clone();
		rect.setRect(rect.getX() + offset[0], rect.getY() + offset[1], rect
				.getWidth(), rect.getHeight());
		return rect;
	}

	/** LineArea-InlineParent-TextArea【目前结构】 */
	@SuppressWarnings("unchecked")
	private final static InlineArea searchTextAreaFromInLine(Area area) {
		if (area instanceof TextArea) {
			return (InlineArea) area;
		}

		final List<Area> list = area.getChildAreas();
		if (isNull(list) || list.size() == 0) {
			return null;
		}
		area = list.get(0);
		if (area instanceof InlineArea) {
			return (InlineArea) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 根据坐标点获得相应的TextInline or TableCell【目前还没有加进来】
	 * 
	 * @param point
	 *            指定坐标点
	 * @return {@link TextInline} 返回TextInline
	 */
	public PositionImpOP getInline(final Point2D point) {
		CellElement inline = null;
		
		final PositionImpOP imp = getTextWithPoint(point);
		final CellElement text = imp.getElement();
	
		inline = getInlineLevelCell(text);
		imp.setElement(inline);
		return imp;
	}
	/* 根据制定的CellElement【FO-Text级别对象】，获得其可用于在DocumentPosition中的Cell-Element对象 */
	final static CellElement getInlineLevelCell(final CellElement cell)
	{
		CellElement inline = cell;
		if (isNull(cell))
		{
			return inline;
		}
		if (cell instanceof FOText)
		{
			inline = (CellElement) cell.getParent();
		} else if (cell instanceof AbstractGraphics)
		{
			inline = (CellElement) cell.getParent();
		} else if (cell instanceof BlockContainer)
		{
			inline = cell;
		} else
		{
			inline = cell;
		}
		return inline;
	}
	/**
	 * 
	 * 根据指定的元素对象，向上遍历，查找第一个满足指定类型的元素对象
	 * 
	 * @param element
	 *            指定元素对象
	 * @param type
	 *            指定类型
	 * @return {@link Element} 返回满足条件的Element，如果父元素不包含指定的类型则返回Null。
	 */
	public static Element searchCellElement(final Element element,
			final Class<? extends Element>... type) {
		if (isNull(element) || isNull(type)) {
			return null;
		}

		/* if (element.getClass() == type) */
		if (isContains(element.getClass(), type)) {
			return element;
		}

		return searchCellElement(element.getParent(), type);
	}

	private static boolean isContains(final Class<? extends Element> c,
			final Class<? extends Element>... type) {
		for (final Class<? extends Element> clazz : type) {
			if (clazz == c) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public static Document getCurrentDocument(final Element ele) {
		final WiseDocDocument doc = (WiseDocDocument) searchElementFromType(ele,
				WiseDocDocument.class);
		if (isNull(doc)) {
			return null;
		}
		return doc;
	}

	private static Element searchElementFromType(final Element element, final Class type) {
		if (isNull(element)) {
			return null;
		}

		if (type.isAssignableFrom(element.getClass())) {
			return element;
		}

		return searchElementFromType(element.getParent(), type);
	}

	/**
	 * 
	 * 根据指定的位置信息，创建高亮显示区域，【具体的Location信息还需要再计算偏移量】
	 * 
	 * @param position
	 *            指定位置信息
	 * @return {@link Rectangle2D} 返回创建高亮显示的区域
	 */
	public static Rectangle2D getRectWithPosition(final DocumentPosition position) {
		final Area area = getAreaWithPosition(position);

		Rectangle2D rect = null;
		if (isNull(area)) {
			return rect;
		}

		rect = getShapeWithPosition(position);// area.getViewport();

		if (isNull(rect)) {
			return rect;
		}

		rect = (Rectangle2D) rect.clone();

		if (area.isTableCell()) {
			return rect;
		}

		final LineArea line = getLineArea(area);

		double x = rect.getX();
		final double y = line.getOffsetTop();
		final double w = rect.getWidth();
		final double h = line.getAllocBPD();

		if (!position.isStartPos()) {
			x += w;
		}

		rect.setRect(x, y, w, h);

		return rect;
	}

	/**
	 * 
	 * 根据指定位置信息获得光标Shape信息【还需要在计算偏移量才能真正得到光标的准确位置，这里主要是计算光标的高度】
	 * 
	 * @param position
	 *            指定位置信息
	 * @return {@link Rectangle2D} 返回用于绘制光标的Shape
	 */
	public static PositionBean getCaretWithPosition(final DocumentPosition position, final AbstractEditComponent painter) {
		final PositionBean bean = new PositionBean();

		if (isBlankPosition(position)) {
			return getCaretForBlank(position);
		}

		final PageViewport viewport = getViewportWithPosition(position);
		final Area area = getAreaWithPosition(position);
		if (isNull(area)) {
			return bean;
		}
		Rectangle2D rect = area.getViewport();
		if(isNull(rect)){
			/*try {*/
				/*final BufferedImage image = painter.getCurrentRenderer().getPageImage(viewport.getPageIndex());*/
				painter.getPageOf(viewport.getPageIndex()).loadImage();
			/*} catch (final FOVException e) {
				e.printStackTrace();
			}*/
		}
		//标示是否获取返回Rectangle的前沿、后边线。
		boolean isEnd = Boolean.FALSE;
		if ((area instanceof TextArea) && !((TextArea) area).isWhole()) {
			/*int off = position.getOffset();
			off -= calcOffsetBeforeArea(area);
			if (off < 0) {
				LogUtil.debug("计算后的偏移量不不合理[1, N]：" + off);
			}
			rect = getRectWithOffset(area, off - 1);*/
			//【删除：START】 by 李晓光2010-1-25
			//改变了计算offset的方式，此方式的处理废除。
			/*rect = getRectWithOffset(area, getOffsetToArea((TextArea)area, position.getOffset()));*/
			//【删除：END】 by 李晓光2010-1-25
			//【修正：START】 by 李晓光2010-1-25
			//具体计算规则，参照text-area中说明。
			
			TextArea text = (TextArea)area;
			
			int number = (position.getOffset() - text.getStartIndex());
			if(number >= 0 && number < text.getElementCount()){
				isEnd = !position.isStartPos();
			}
			rect = PositionOffset4Convert.getRectWithOffset(area, number);
			//【修正：END】 by 李晓光2010-1-25
		}else{
			isEnd = !position.isStartPos();
		}
		if (isNull(rect)) {
			return bean;
		}
		rect = (Rectangle2D) rect.clone();

		/*final LineArea line = getLineArea(area);*/
		/* int before = line.getSpaceBefore(); int after = line.getSpaceAfter();*/
		 
		final double[] offset = getOffsetForContainer(area);// getOffsetOfContainer(area,
														// Boolean.FALSE);

		double x = rect.getX() + offset[0];
		final double y = rect.getY() /*- before*/+ offset[1] + 500F;
		final double w = rect.getWidth();
		final double h = rect.getHeight() /* + after + before */;
				
		if (/*!position.isStartPos()*/isEnd) {
			x += w;
		}

		rect.setRect(x, y, w, h);
		bean.setPageIndex(viewport.getPageIndex());
		bean.setViewport(rect);

		return bean;
	}

	/**
	 * 根据指定的鼠标位置创建光标位置【主要是处理空页、空Block、空Container、空Cell】
	 * 
	 * @param position
	 *            指定当前的位置
	 * @return {@link PositionBean} 返回计算后的光标位置
	 */
	public static PositionBean getCaretForBlank(final DocumentPosition position) {
		final PositionBean bean = new PositionBean();
		Rectangle2D rect = null;
		if (isNull(position)) {
			return bean;
		}
		final CellElement element = position.getLeafElement();

		if (!isBlankPosition(position)) {
			return bean;
		}
		final Area area;
		if(element instanceof ZiMoban)
		{
			List<CellElement> children = element.getAllChildren();
			area =children.get(0).getArea();
			List<PositionBean> posbeans = PositionShape4Convert.createSlectionPath(element.getAllChildren());
			if(posbeans!=null&&!posbeans.isEmpty())
			{
				Rectangle shape=null;
				for(PositionBean posbean:posbeans)
				{
					Rectangle bound =  posbean.getViewport().getBounds();
					if(shape==null)
					{
						shape =bound;
					}
					//处理一个子模板跨多页的情况，这个时候，只取第一页的Area区域
					else if(bound.y<shape.y)
					{
						break;
					}
					else
					{
						shape = shape.union(bound);
					}
				}
				if(position.isStartPos()){
				rect = new Rectangle(shape.x, shape.y, 1, shape.height);
				}
				else
				{
					rect = new Rectangle(shape.x+shape.width, shape.y, 1, shape.height);
				}
			}
		}
		else{
		area = element.getArea();
		if (isNull(area)) {
			return bean;
		}
		final Font font = getFont(position);
		final int lead = font.getAscender();
		final int follow = -font.getDescender();
		int lineHeight = getLineHeight(position);
		final int before = (lineHeight - lead - follow) / 2;
		final int after = lineHeight - lead - follow - before;
		lineHeight = before + after + lead + follow;

		final double h = lead + follow;// lineHeight;
		final double[] offset = getOffsetOfViewport(area, Boolean.FALSE);
		final double offsetX = getOffsetXForBlank(position, area), offsetY = getOffsetYForBlank(
				position, area, h);

		final double x = offset[0] + offsetX;
		final double y = offset[1] + offsetY;
		// 【更改】 by 李晓光2009-2-1 处理设置了行间距后光标过高。
		/*
		 * if(!position.isOn() && (element instanceof Table || element
		 * instanceof BlockContainer)){ int left =
		 * area.getBorderAndPaddingWidthStart(); int top =
		 * area.getBorderAndPaddingWidthEnd(); y += area.getBPD(); y += top;
		 * if(!(element instanceof BlockContainer)) x -= left; }
		 */
		rect = new Rectangle2D.Double(x, y, 1, h);}

		//【删除：START】 by 李晓光 	2009-12-28
		//如果多页采用空页眉、页脚……时，他们公用想用的static-content，这样就不能精确定位了，也就是最后一个页产生的Area才能进行光标定位，其它页不能进行光标定位。
		//为了更准确的获得光标所在页的信息，现正该为采用光标中提供的页索引位置【pageIndex】。
		/*final PageViewport viewport = getViewportWithArea(area);*/
//		bean.setPageIndex(viewport.getPageIndex());
		//【删除：START】 by 李晓光 	2009-12-28
		//【添加：START】 by 李晓光 	2009-12-28
		if((element instanceof StaticContent) && position.getPageIndex() != -1){
			bean.setPageIndex(position.getPageIndex());
		}else{
			final PageViewport viewport = getViewportWithArea(area);
			bean.setPageIndex(viewport.getPageIndex());
		}
		//【添加：START】 by 李晓光 	2009-12-28
		
		bean.setViewport(rect);
		return bean;
	}

	/**
	 * 判断当前是否为空位置处理情况
	 */
	public static boolean isBlankPosition(final DocumentPosition pos) {
		final CellElement ele = pos.getLeafElement();

		return (ele instanceof Flow) || (ele instanceof TableCell)
				|| (ele instanceof BlockContainer || isBlankBlock(ele))
				|| (ele instanceof Table)|| (ele instanceof ZiMoban);
	}

	private static boolean isBlankBlock(final CellElement block) {
		final boolean flag = block instanceof com.wisii.wisedoc.document.Block;
		if (!flag) {
			return Boolean.FALSE;
		}
		return (block.getChildCount() == 0);
	}

	/** 获得当前系统采用的字体 */
	private static Font getFont(final DocumentPosition position) {
		final FontInfo info = WisedocUtil.getFontInfo();
		final com.wisii.wisedoc.document.Block block = position.getBlock();
		final CommonFont comm = block.getCommonFont();
		final Font font = comm.getFontState(info, null);
		return font;
	}

	/** 获得空位置的行高 */
	private static int getLineHeight(final DocumentPosition position) {
		final com.wisii.wisedoc.document.Block block = position.getBlock();
		final SpaceProperty space = (SpaceProperty) block
				.getAttribute(Constants.PR_LINE_HEIGHT);
		return space.getOptimum(null).getLength().getValue();
	}

	/** 获得空位置的前缩进 */
	private static int getStartIndent(final DocumentPosition position) {
		final com.wisii.wisedoc.document.Block block = position.getBlock();
		final int indent = block.getCommonMarginBlock().getStartIndent().getValue(null);
		return indent;
	}

	/** 获得空位置【Block-Level】父对象的IPD */
	private static int getBlockBorderAndPadding(
			final com.wisii.wisedoc.document.Block block) {
		final int padStart = getPaddingStart(block);
		final int padEnd = getPaddingEnd(block);
		final int borderStart = getBorderStart(block);
		final int borderEnd = getBorderStart(block);

		return (padStart + padEnd + borderStart + borderEnd);
		/*
		 * Block b = (Block)block.getArea(); if(!isNull(b)) return b.getIPD();
		 * CellElement ele = position.getLeafElement(); block =
		 * (com.wisii.wisedoc.document.Block) searchCellElement(ele,
		 * com.wisii.wisedoc.document.Block.class); b = block.getArea();
		 * if(isNull(b)) return 0; return b.getIPD();
		 */
	}

	/** 获得空位置的后缩进 */
	private static int getEndIndent(final DocumentPosition position) {
		final com.wisii.wisedoc.document.Block block = position.getBlock();
		final int indent = block.getCommonMarginBlock().getEndIndent().getValue(null);
		return indent;
	}

	/** 获得空位置的前边框 */
	private static int getBorderStart(final com.wisii.wisedoc.document.Block block) {
		return getBorder(block, CommonBorderPaddingBackground.START);
	}

	/** 获得空位置的后边框 */
	private static int getBorderEnd(final com.wisii.wisedoc.document.Block block) {
		return getBorder(block, CommonBorderPaddingBackground.END);
	}

	/** 获得指定Block的Border */
	private static int getBorder(final com.wisii.wisedoc.document.Block block,
			final int side) {
		final CommonBorderPaddingBackground common = block
				.getCommonBorderPaddingBackground();
		return common.getBorderWidth(side, false);
	}

	/** 获得空位置的前空 */
	private static int getPaddingStart(final com.wisii.wisedoc.document.Block block) {
		return getPadding(block, CommonBorderPaddingBackground.START);
	}

	/** 获得空位置的后空 */
	private static int getPaddingEnd(final com.wisii.wisedoc.document.Block block) {
		return getPadding(block, CommonBorderPaddingBackground.END);
	}

	/** 获得指定Block的Padding */
	private static int getPadding(final com.wisii.wisedoc.document.Block block,
			final int side) {
		final CommonBorderPaddingBackground common = block
				.getCommonBorderPaddingBackground();
		return common.getPadding(side, false, null);
	}

	/** 获得空位置的上空白 */
	private static int getSpaceBefore(final DocumentPosition position) {
		final com.wisii.wisedoc.document.Block block = position.getBlock();
		final int space = block.getCommonMarginBlock().getSpaceBefore().getOptimum(null)
				.getLength().getValue();
		return space;
	}

	/** 获得空位置的下空白 */
	private static int getSpaceAfter(final DocumentPosition position) {
		final com.wisii.wisedoc.document.Block block = position.getBlock();
		final int space = block.getCommonMarginBlock().getSpaceAfter().getOptimum(null)
				.getLength().getValue();
		return space;
	}

	/** 获得因对齐方式【Inline方向】、前缩进、后缩进引起的偏移量 */
	private static double getOffsetXForBlank(final DocumentPosition position,
			final Area area) {
		final com.wisii.wisedoc.document.Block block = position.getBlock();
		final int textAlign = block.getTextAlign();
		final Length length = block.getTextIndent();
		int textIndent = 0;
		if (!isNull(length)) {
			textIndent = block.getTextIndent().getValue();
		}
		/*
		 * int startIndent = getStartIndent(position); int endIndent =
		 * getEndIndent(position);
		 */
		final Rectangle2D rect = area.getViewport();
		if (isNull(rect)) {
			return textIndent;
		}
		double ipd = getBlockBorderAndPadding(block);
		ipd = (rect.getWidth() - ipd);
		double offset = textIndent;// startIndent;
		switch (textAlign) {
		case Constants.EN_CENTER:
			offset = (ipd - textIndent) / 2;// (rect.getWidth() - startIndent -
											// endIndent) / 2;
			break;
		case Constants.EN_END:
		case Constants.EN_RIGHT:
			offset = ipd;// (rect.getWidth() - endIndent);
			break;
		default:
			break;
		}

		return offset;
	}

	/** 获得因对齐方式【Block方向上】引起的偏移量 */
	private static double getOffsetYForBlank(final DocumentPosition position,
			final Area area, final double h) {
		final com.wisii.wisedoc.document.Block block = position.getBlock();
		final EnumProperty property = ((EnumProperty) block
				.getAttribute(Constants.PR_DISPLAY_ALIGN));
		int display = Constants.EN_BEFORE;
		final int before = getSpaceBefore(position);
		final int after = getSpaceAfter(position);
		if (!isNull(property)) {
			display = property.getEnum();
		}
		final Rectangle2D rect = area.getViewport();
		double offset = 0;
		switch (display) {
		case Constants.EN_CENTER:
			offset = (rect.getHeight() - before - after - h) / 2;
			break;
		case Constants.EN_AFTER:
			offset = (rect.getHeight() - after - h);
			break;
		default:
			break;
		}
		return offset;
	}

	/***
	 * 如果当前Area是或着是属于BlockViewport，则计算Area相对与RegionBody的偏移量。
	 * 
	 * @param area
	 *            指定要计算的Aera
	 * @param addRegion
	 *            指定是否在偏移量基础上再加上RegionBody的偏移量。
	 * @return double[] 返回中偏移量 0：表示X方向，1：表示Y方向。
	 */
	public static double[] getOffsetOfContainer(final Area area, final boolean addRegion) {
		final double[] offset = { 0, 0 };
		if (isNull(area)) {
			return offset;
		}

		BlockViewport block = null;
		if (area instanceof BlockViewport) {
			block = (BlockViewport) area;
		} else {
			block = (BlockViewport) searchArea(area, BlockViewport.class);
		}

		if (!isNull(block)) {
			final Rectangle2D rect = block.getViewport();
			final int start = block.getBorderAndPaddingWidthStart();
			final int top = block.getBorderAndPaddingWidthBefore();
			offset[0] = rect.getX() + start;
			offset[1] = rect.getY() + top;
		}
		if (addRegion) {
			final double[] off = getOffset(area);
			offset[0] += off[0];
			offset[1] += off[1];
		}
		return offset;
	}

	public static double[] getOffsetForContainer(final Area area) {
		double offsetX = 0;
		double offsetY = 0;
		if (isNull(area)) {
			return new double[] { offsetX, offsetY };
		}

		if (area instanceof BlockViewport) {
			final Rectangle2D rect = area.getViewport();
			final int left = area.getBorderAndPaddingWidthStart();
			final int top = area.getBorderAndPaddingWidthBefore();
			offsetX = rect.getX();
			offsetX += left;
			offsetY = rect.getY();
			offsetY += top;
		}
		final double[] offset = getOffsetForContainer(area.getParentArea());
		offsetX += offset[0];
		offsetY += offset[1];

		return new double[] { offsetX, offsetY };
	}

	/**
	 * 计算偏移量，主要是为toScreen方法提供偏移量，规则如下： 1、如果指定的Area是BlockViewport,直接计算其偏移量
	 * 2、如果指定的Area是BlockViewport包含的，则要累加BlockViewport的偏移量。
	 * 3、如果Area不是BlockViewport包含，直接计算偏移量。
	 * 
	 * @param area
	 *            指定当前Aera
	 * @return double[] 返回计算后的偏移量
	 */
	public static double[] getOffsetForToScreen(final Area area) {
		final double[] offset = { 0, 0 };
		if (isNull(area)) {
			return offset;
		}
		BlockViewport block = null;
		if (area instanceof BlockViewport) {
			final double[] off = getOffset(area);
			offset[0] += off[0];
			offset[1] += off[1];
			final double[] temp = getOffsetForContainer(area.getParentArea());
			offset[0] += temp[0];
			offset[1] += temp[1];
			return offset;
		} else {
			block = (BlockViewport) searchArea(area, BlockViewport.class);
		}

		if (!isNull(block)) {
			final Rectangle2D rect = block.getViewport();
			final int start = block.getBorderAndPaddingWidthStart();
			final int top = block.getBorderAndPaddingWidthBefore();
			offset[0] = rect.getX() + start;
			offset[1] = rect.getY() + top;
		}
		final double[] off = getOffset(area);
		offset[0] += off[0];
		offset[1] += off[1];

		return offset;
	}

	/**
	 * 
	 * 根据指定Area判断其是属于Container还是其它，如果是Container就添加Container的偏移量
	 * 如果是其它的Area添加其它的偏移量
	 * 
	 * @param area
	 *            指定Area
	 * @return double[] 返回指定Area相对于RegionBody的水平和垂直的偏移量。
	 */
	public static double[] getOffsetContainerOrOther(final Area area) {
		final double[] offset = { 0, 0 };
		if (isNull(area)) {
			return offset;
		}
		BlockViewport block = null;
		if (area instanceof BlockViewport) {
			block = (BlockViewport) area;
		} else {
			block = (BlockViewport) searchArea(area, BlockViewport.class);
		}
		if (isNull(block)) {
			return getOffset(area);
		} else {
			return getOffsetOfContainer(block, Boolean.TRUE);
		}
	}

	/**
	 * 返回指定Area的偏移量【计算空位置光标偏移量时使用】
	 * 
	 * @param area
	 *            指定Area
	 * @param hasCells
	 *            指定Area是否为TableCell
	 * @return double[] 返回总偏移量
	 */
	public static double[] getOffsetOfViewport(final Area area, boolean hasCells) {
			return getOffsetForNestedArea(area, !area.isReference());
	}

	/**
	 * 嵌套Area位置的计算。
	 * 
	 * @param area
	 *            指定当前的Area
	 * @param isNotReference
	 *            指定当前的Area是否是reference Area
	 * @return {@link Double} 返回计算后的偏移量
	 */
	public static double[] getOffsetForNestedArea(final Area area,
			boolean isNotReference) {
		double offsetX = 0;
		double offsetY = 0;
		if (isNull(area)) {
			return new double[] { 0, 0 };
		}

		if (area.isEquateViewport()) {
			if (isNotReference) {
				final Rectangle2D rect = area.getViewport();
				final int left = area.getBorderAndPaddingWidthStart();
				final int top = area.getBorderAndPaddingWidthBefore();
				offsetX += left;
				offsetY += top;
				if(!isNull(rect)){
					offsetX += rect.getX();
					offsetY += rect.getY();
				}
			}
			isNotReference = Boolean.FALSE;
		} else if (area.isReference()) {
			isNotReference = Boolean.TRUE;
		}
		final double[] offset = getOffsetForNestedArea(area.getParentArea(),
				isNotReference);
		offsetX += offset[0];
		offsetY += offset[1];

		return new double[] { offsetX, offsetY };
	}

	/**
	 * 
	 * 根据指定的位置信息获得包含该位置的总偏移量。【如：Block包含的Margin会产生偏移量】
	 * 
	 * @param position
	 *            指定位置信息
	 * @return Double[] 返回X、Y方向上的偏移量
	 */
	public static double[] getOffsetOfBlocks(final DocumentPosition position) {
		final Area area = getAreaWithPosition(position);
		return getOffset(area);
	}

	/**
	 * 
	 * 根据指定的Area的总偏移量。【如：Block包含的Margin会产生偏移量】
	 * 
	 * @param area
	 *            指定Area *
	 * @return Double[] 返回X、Y方向上的偏移量
	 */
	public static double[] getOffsetOfBlocks(final Area area) {
		return getOffset(area);
	}

	/**
	 * 
	 * 根据指定的Area【inline级别】获得其总偏移量。这些偏移量均采用系统的默认单位【mpt】
	 * 
	 * @param area
	 *            指定Inline级别的Area
	 * @return Double[] 返回X、Y方向上的偏移量
	 */
	private static double[] getOffset(final Area area) {
		double offsetX = 0;
		double offsetY = 0;
		if (isNull(area)) {
			return new double[] { 0, 0 };
		}

		if (area instanceof RegionViewport) {
			final Rectangle2D rect = area.getViewport();
			offsetX += rect.getX();
			offsetY += rect.getY();
		}

		final double[] date = getOffset(area.getParentArea());
		offsetX += date[0];
		offsetY += date[1];

		return new double[] { offsetX, offsetY };
	}

	/**
	 * 
	 * 根据指定的内部位置，获得相应的TextArea,如果包含多个TextArea则返回第一TextArea【用于处理绑定】
	 * 
	 * @param position
	 *            指定一个位置信息
	 * @return {@link TextArea} 返回包含该位置的第一个TextArea
	 */
	public static Area getAreaWithPosition(final DocumentPosition position) {
//		final CellElement text = getTextWithPosition(position);
//		if (isNull(text)) {
//			return null;
//		}
		
		// 修改原因：现在是根据偏移量来找到相应的Text Area或Viewport对象。
		final Area area = PositionOffset4Convert.getInlineArea(position);
		//【删除：START】  by  李晓光  2010-1-25
		//getInlineArea(position);
		//【删除：END】  by  李晓光  2010-1-25
		
		if (area instanceof InlineArea) {
			return area;
		} else if (!isNull(area) && area.isTableCell()) {
			return area;
		}
		return null;
	}

	/**
	 * 
	 * 根据指定的位置信息获得相应的FOText对象【如果】
	 * 
	 * @param position
	 *            指定位置
	 * @return {@link FOText} 返回位置对应的FOText
	 */
	private static CellElement getTextWithPosition(final DocumentPosition position) {
		if (isNull(position)) {
			return null;
		}
		CellElement element = position.getLeafElement();
		if (isNull(element)) {
			return null;
		}

		if (!(element instanceof PageNumber)
				&& !(element instanceof PageNumberCitation)
				&& !(element instanceof BlockContainer)) {
			final ListIterator listit = element.getChildNodes();
			if (listit != null && listit.hasNext()) {
				element = (CellElement) listit.next();
			}
		}
		return element;
	}

	/* ---------------------选择位置处理【START】-------------------------------------- */


	/** 判断指定的Area是否包含子Area */
	private static boolean hasChildren(final Area area) {
		if (isNull(area)) {
			return Boolean.FALSE;
		}
		final List<Area> areas = area.getChildAreas();
		if (isNull(areas)) {
			return Boolean.FALSE;
		}
		return (areas.size() > 0);
	}

	/* ---------------------选择位置处理【END】-------------------------------------- */
	public static PageViewport getViewportWithPosition(final DocumentPosition position) {
		Area area = null;
		final CellElement ele = position.getLeafElement();
		if(ele instanceof Flow) {
			area = ele.getArea();
		} else {
			area = getAreaWithPosition(position);
		}

		return getViewportWithArea(area);
	}

	public static PageViewport getViewportWithArea(final Area area) {
		PageViewport viewport = null;
		final RegionViewport region = (RegionViewport) searchArea(area,
				RegionViewport.class);
		if (isNull(region)) {
			return viewport;
		}
		final Page page = region.getParent();
		viewport = page.getParent();
		if (viewport.getPage() == null) {
			viewport.setPage(page);
		}
		return viewport;
	}

	/* ---------------------界面中Margin计算【START】------------------------------ */
	public MarginBean getPageMargin() {
		if (isNull(viewport)) {
			return new MarginBean();
		}
		final Rectangle2D view = viewport.getViewport();
		final Rectangle2D page = getPage().getViewport();
		return subtration(view, page);
	}

	public Rectangle2D getRegionBeforeMargin() {
		if (isNull(viewport)) {
			return null;
		}
		final RegionViewport view = getBeforeViewport();
		if (isNull(view)) {
			return zero;
		}
		final Rectangle2D before = view.getViewport();
		return before;
	}

	public Rectangle2D getRegionAfterMargin() {
		if (isNull(viewport)) {
			return null;
		}
		final RegionViewport view = getAfterViewport();
		if (isNull(view)) {
			return zero;
		}
		final Rectangle2D after = view.getViewport();
		return after;
	}

	public Rectangle2D getRegionStartMargin() {
		if (isNull(viewport)) {
			return null;
		}
		final RegionViewport view = getStartViewport();
		if (isNull(view)) {
			return zero;
		}
		final Rectangle2D start = view.getViewport();
		return start;
	}

	public Rectangle2D getRegionEndMargin() {
		if (isNull(viewport)) {
			return null;
		}
		final RegionViewport view = getEndViewport();
		if (isNull(view)) {
			return zero;
		}
		final Rectangle2D end = view.getViewport();
		return end;
	}

	public MarginBean getRegionBodyMargin() {
		if (isNull(viewport)) {
			return new MarginBean();
		}
		final Rectangle2D page = getPage().getViewport();
		final RegionViewport view = getBodyViewport();
		final Rectangle2D body = view.getViewport();
		return subtration(page, body);
	}

	public static MarginBean subtration(final Rectangle2D big, final Rectangle2D small) {
		if (isNull(big) || isNull(small)) {
			return new MarginBean();
		}
		double top = 0, bottom = 0, left = 0, right = 0;
		top = small.getY() - big.getY();
		bottom = big.getHeight() - top - small.getHeight();
		left = small.getX() - big.getX();
		right = big.getWidth() - left - small.getWidth();
		if (top < 0 || bottom < 0 || left < 0 || right < 0) {
			return new MarginBean();
		}
		return new MarginBean(top, bottom, left, right);
	}

	/* ---------------------界面中Margin计算【END】------------------------------ */

	// ----------------根据偏移量计算光标位置----------------------------------
	public PositionImpOP getTextWithPoint(Point2D point) {
		/*final PositionImpOP imp = new PositionImpOP();*/
		final RegionViewport region = getRegionWithPoint(point);
		if (isNull(region)) {
			return new PositionImpOP();
		}

		final Point2D p = (Point2D) point.clone();
		/*final boolean isContainer = Boolean.FALSE;*/
		Area area = getContainerWithPoint(region, p, Boolean.TRUE);
		if (isNull(area)) {
			area = getAreaWithPoint(region, point);
		} else {
			point = p;
		}
		PositionImpOP imp = getPositionWithArea(area, point);		
		//【添加：START】 by 李晓光 	2009-12-28
		//原来在getPositionWithArea进行处理，但那里的处理效率不是很好。
		int pageIndex = region.getParent().getParent().getPageIndex();
		imp.setPageIndex(pageIndex);
		//【添加：END】 by 李晓光 	2009-12-28
		return imp;
	}
	final static PositionImpOP getPositionWithArea(Area area, final Point2D point){
		final PositionImpOP imp = new PositionImpOP();
		boolean isContainer = Boolean.FALSE;
		if (isNull(area)) {
			return imp;
		}
		if(area.isTableContent()) {
			return imp;
		}
		isContainer = area.isContainer();
		if (isContainer) {
			isContainer &= area.getSource().getChildCount() == 0;
		}

		if (isNull(area)) {
			return imp;
		}
		final Area backup = area;
		if (area instanceof LineArea) {
			final Area temp = getTextAreaFromLine(area, point);
			if (!isNull(temp)) {
				area = temp;
			}
		} else if ((area instanceof Block && !area.isTable())
				|| (area instanceof RegionReference)) {// || (area instanceof
														// RegionReference)
			Area a = area;
			if (area instanceof BodyRegion) {
				a = getNormalFlowForRegion(area);
			}
			a = findBlockLevelArea(a, point);
			//---------------------add by lxg 2009-11-19--------------------
			//(a.isContainer() && ((Block)a).getPositioning() == Block.STACK)
			//为了让相对位置的Container定位，当鼠标点落在Container外部时！
			//---------------------add by lxg 2009-11-19--------------------
			boolean flag = isNotEmptyContainer(a);
			if (a.isNormalBlock() || ((area == a) && !area.isContainer()) || flag) {
				a = findLineLevelArea(a, point);
				if(a instanceof LineArea){
					final Area temp = getTextAreaFromLine(a, point);
					if (!isNull(temp)) {
						area = temp;
					}
				}else if(flag)
					area = a;
			} else if (a instanceof NormalFlow) {

			} else {
				area = a;
			}
		}/*
		 * else if((area instanceof RegionReference)){ LineArea line =
		 * getLineAreaFromBlock(area, point); Area temp =
		 * getTextAreaFromLine(line, point); if (!isNull(temp)) area = temp; }
		 */

		imp.setOnTop((area == backup));
		if (area.isTable()) {
			imp.setOnTop(Boolean.FALSE);
		}
		/*final OffsetBean bean = calcOffsetOfBlock(area, (Point2D) point.clone());*/
		final OffsetBean bean = PositionOffset4Convert.calcOffsetOfBlock(area, (Point2D) point.clone());
		imp.setOffset(bean.getOffset());
		imp.setStart(bean.isStart());
		/*imp.setPageIndex(getViewport().getPageIndex());*/
		//【删除：START】 by 李晓光 	2009-12-28
		//由其上层方法进行处理，效率要比其好，且更加准确些。
//		imp.setPageIndex(getViewportWithArea(area).getPageIndex());
		//【删除：END】 by 李晓光 	2009-12-28
		
		if (LogUtil.canDebug()) {
			LogUtil.debug("计算鼠标位置字符在整个Block中的偏移量是： " + bean.getOffset());
		}
		if (area instanceof LineArea) {
			final Area block = searchBlockLevelArea(area, isContainer);
			imp.setElement(block.getSource());
			imp.setOnTop(backup == block);
		} else if ((area instanceof RegionReference)) {// 处理空白页时，直接返回Flow
			final RegionReference ref = (RegionReference) area;
			imp.setElement(ref.getFlow());
		}else {
			final CellElement ele = PositionOffset4Convert.getElementWithArea(area, bean.getOffset());
			if (LogUtil.canDebug()) {
				LogUtil.debug("Cell Element = " + ele);
			}
		     /* 删除  by 李晓光2009-12-3 */
			//
			/*if (ele instanceof FOText) {
				if (LogUtil.canDebug()) {
					LogUtil.debug("FOTEXT = " + new String(((FOText) ele).ca));
				}
				final FOText t = (FOText) area.getSource();
				((FOText) ele).setAreas(t.getAreas());
				 ((FOText) ele).setArea((TextArea) area); 
			}*/
			imp.setElement(ele);
		}
		return imp;
	}
	//---------------------add by lxg 2009-11-20--------------------
	private final static boolean isNotEmptyContainer(Area area){
		if(!(area instanceof BlockViewport))
			return Boolean.FALSE;
		//判断是否为空的Container
		BlockViewport view = (BlockViewport)area;
		CellElement source = view.getSource();
		if(source.getChildCount() == 0)
			return Boolean.FALSE;
		//判断是否为相对位置的Container
		return (view.getPositioning() == Block.STACK);
	}
	//---------------------add by lxg 2009-11-20--------------------
	/* 向下遍历树找到第一个Normal Flow */
	private final static Area getNormalFlowForRegion(final Area area) {
		if (isNull(area)) {
			return area;
		}
		if (area instanceof NormalFlow) {
			return area;
		}
		final List<Area> areas = area.getChildAreas();

		return getNormalFlowForRegion(areas.get(0));
	}

	/**
	 * 获得指定位置的字符占据的矩形区域
	 * 
	 * @param pos
	 *            指定位置
	 * @return {@link Rectangle2D} 返回指定位置的字符占据的矩形区域。
	 */
	public static Rectangle2D getShapeWithPosition(final DocumentPosition pos) {
		final Rectangle2D rect = null;
		if (isNull(pos)) {
			return rect;
		}
		final Area a = getAreaWithPosition(pos);
		if (isNull(a)) {
			return rect;
		}
		if (a instanceof Viewport) {
			return a.getViewport();
		}

		if (a instanceof TextArea) {
			if (((TextArea) a).isWhole()) {
				return a.getViewport();
			}
		}
		
		return PositionOffset4Convert.getRectWithOffset(a, (pos.getOffset() - ((TextArea)a).getStartIndex()));
	}
		
	// ----------------根据偏移量计算光标位置----------------------------------
	public static class PositionImpOP {
		/* 获得当前的Cell */
		private CellElement element = null;
		private boolean onTop = Boolean.FALSE;
		private int offset = -1;
		private boolean start;
		private int pageIndex = -1;

		public int getPageIndex() {
			return pageIndex;
		}

		public void setPageIndex(final int pageIndex) {
			this.pageIndex = pageIndex;
		}

		public boolean isStart() {
			return start;
		}

		public void setStart(final boolean start) {
			this.start = start;
		}

		public int getOffset() {
			return offset;
		}

		public void setOffset(final int offset) {
			this.offset = offset;
		}

		public CellElement getElement() {
			return element;
		}

		public void setElement(final CellElement element) {
			this.element = element;
		}

		public boolean isOnTop() {
			return onTop;
		}

		public void setOnTop(final boolean onTop) {
			this.onTop = onTop;
		}
		@Override
		public String toString() {
			final StringBuilder s = new StringBuilder();
			s.append("offset = [");
			s.append(getOffset());
			s.append("] element = ");
			s.append(element);
			return s.toString();
		}
	}
}
