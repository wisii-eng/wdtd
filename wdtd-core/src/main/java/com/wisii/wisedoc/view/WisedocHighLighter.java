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
 * @WisedocHighLighter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.area.RegionViewport;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.view.PositionBean.FigureStyle;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

/**
 * 类功能描述：用于处理高亮显示
 * 
 * 作者：李晓光 创建日期：2008-9-26
 */
public class WisedocHighLighter {
	/* 背景被绘制的位置【控件】 */
	private PageViewportPanel component = null;
	private AbstractEditComponent painter = null;
	/* 高亮显示的颜色 */
	private final static Paint DEFAULT_PAINT = new Color(102, 102, 102, 128); 
	private Paint paint = DEFAULT_PAINT;
	/* 高亮显示的区域 */
	private List<PositionBean> posBeans = new ArrayList<PositionBean>();
	private List<DocumentPositionRange> selectionRange = null;
	private List<CellElement> objectSelections = null;
	/* 保存当前可是窗口，显示的页序号集 */
	private Collection<Integer> set = null;
	public WisedocHighLighter() {
		this(null);
	}

	// public WisedocHighLighter(PageViewportPanel component) {
	// setComponent(component);
	// }
	public WisedocHighLighter(final AbstractEditComponent comp) {
		this.painter = comp;
	}

	public void setComponent(final PageViewportPanel component) {
		this.component = component;
	}

	public PageViewportPanel getComponent() {
		return component;
	}

	public List<DocumentPositionRange> getSelectionRange() {
		return selectionRange;
	}

	public List<CellElement> getObjectSelection() {
		return objectSelections;
	}

	public void setSelections(final List<CellElement> cells, final List<DocumentPositionRange> ranges) {
		clearSelection();
		setObjectSelection(cells);
		setSelectionRange(ranges);
		painter.repaint(painter.getViewportBound());
	}

	private void setObjectSelection(final List<CellElement> selections) {
		this.objectSelections = selections;
		if (isNull(selections) || selections.size() == 0) {
			return;
		}
		createShape(selections);
	}

	private void setSelectionRange(
			final List<DocumentPositionRange> selectionRange) {
		this.selectionRange = selectionRange;
		if (isNull(selectionRange) || selectionRange.size() == 0) {
			return;
		}
		creatShapes(selectionRange);
	}

	private void createShape(final List<CellElement> selection) {
		clearRepeateTableCell(selection);
		List<PositionBean> beans = PositionShape4Convert.createSlectionPath(selection);
		beans = getShapeWithPosition(beans);
		processPositionBeans(beans);
	}

	private void creatShapes(final List<DocumentPositionRange> rangles) {
		List<PositionBean> list = null;
		for (final DocumentPositionRange range : rangles) {
			list = getShapeWithPosition(range);
			processPositionBeans(list);
		}
	}

	private void processPositionBeans(final List<PositionBean> beans) {
		if (isNull(beans) || beans.size() == 0) {
			return;
		}
		for (final PositionBean bean : beans) {
			if (bean.getPageIndex() == -1) {
				continue;
			}
			component = painter.getPageOf(bean.getPageIndex());
			if (isNull(bean.getViewport())) {
				final List<PageViewport> pageList = painter.getPageViewports();
				final PageViewport page = pageList.get(bean.getPageIndex());
				final RegionViewport region = page.getPage().getRegionViewport(
						Constants.FO_REGION_BODY);

				final GeneralPath path = new GeneralPath();
				final AffineTransform at = new AffineTransform();
				/* PageViewportPanel component = getPageViewportPanel(); */
				final double scaleX = component.getPreviewScaleX();
				final double scaleY = component.getPreviewScaleY();
				final double factor = Constants.PRECISION;
				final double offsetX = component.getOffsetX(), offsetY = component
						.getOffsetY();
				path.append(region.getViewport(), false);
				at.translate(offsetX, offsetY);
				at.scale(scaleX / factor, scaleY / factor);
				path.transform(at);
				bean.setViewport(path);
			}
			addShape(bean);
			WisedocHighLighter temp = component.getHighLighter();

			if (isNull(temp)) {
				temp = new WisedocHighLighter(painter);
				component.setHighLighter(temp);
			}
			temp.addShape(bean);
			/*component.repaint(bean.getViewport().getBounds());*/
		}
	}

	/**
	 * 如果在所有的选择对象中即选择了单元格、也选择了单元格所在的行， 则消除单元格，这样在高亮显示时就不会出现高亮颜色叠加的现象了。
	 */
	private void clearRepeateTableCell(final List<CellElement> cells) {
		final Set<CellElement> set = new HashSet<CellElement>();
		set.addAll(cells);
		for (final ListIterator<CellElement> iterator = cells.listIterator(); iterator
				.hasNext();) {
			final CellElement cell = iterator.next();
			if (cell instanceof TableCell) {
				final TableRow row = (TableRow) cell.getParent();
				if (cells.contains(row)) {
					iterator.remove();
				}
			}
		}
	}

	public void clearSelection() {
		if (isNull(posBeans) || posBeans.size() == 0) {
			return;
		}
		for (final PositionBean bean : posBeans) {
			if (bean.getPageIndex() == -1) {
				continue;
			}
			component = painter.getPageOf(bean.getPageIndex());
			if (isNull(component)) {
				LogUtil.debug("页索引[" + bean.getPageIndex() + "]超出  [" + "]范围！");
				continue;
			}

			component.setHighLighter(null);
			component = null;
		}
		posBeans.clear();
	}

	public void addShape(final PositionBean shape) {
		if (isNull(shape)) {
			return;
		}
		if (isNull(posBeans)) {
			posBeans = new ArrayList<PositionBean>();
		}
		posBeans.add(shape);
	}

	public Paint getPaint() {
		if(isNull(paint)) {
			setPaint(DEFAULT_PAINT);
		}
		return paint;
	}

	public void setPaint(final Paint paint) {
		this.paint = paint;
	}

	public void paintHighLighter(final Graphics g,PageViewportPanel currenpanel) {
		if (isNull(posBeans) || posBeans.size() == 0) {
			return;
		}
		final Graphics2D g2d = (Graphics2D) g.create();
		g2d.setPaint(paint);
		g2d.fill(calceHighLighterShape(posBeans, g,currenpanel));
		g2d.dispose();
	}
	private Shape calceHighLighterShape(final List<PositionBean> beans,
			final Graphics g,PageViewportPanel currenpanel) {
		final GeneralPath shape = new GeneralPath();
		for (final PositionBean bean : beans) {
			if (bean.getPageIndex() == -1) {
				continue;
			}
			if (isNull(painter)) {
				continue;
			}
			component = painter.getPageOf(bean.getPageIndex());
			if (isNull(component)||component!=currenpanel) {
				continue;
			}

			final Shape s = translateShape(bean.getViewport());
			if (bean.getFigureStyle() == FigureStyle.DRAG_POINT) {
				SVGLocationConvert.paintSelectedShape(g, s);
			} else if (bean.getFigureStyle() == FigureStyle.BORDER) {
				SVGLocationConvert.paintShapeBorder(g, s);
			} else {
				shape.append(s, false);
			}
		}
		return shape;
	}

	private Shape translateShape(final Shape shape) {
		GeneralPath path = new GeneralPath();
		path = new GeneralPath();
		path.append(shape, Boolean.FALSE);
		final AffineTransform at = AffineTransform.getTranslateInstance(component.getOffsetX(), component.getOffsetY());
		path.transform(at);
		return path;
	}

	private List<PositionBean> getShapeWithPosition(
			final DocumentPositionRange range) {
		final Collection<Integer> col = AbstractEditComponent.calcAllPageNumbersToShown(painter);
		if(set != null && set.containsAll(col) && col.containsAll(set)){
			
		}else{
			/*try {
				for (final Integer i : col) {
					painter.getCurrentRenderer().getPageImage(i);
				}
			} catch (final FOVException e) {
				e.printStackTrace();
			}*/
			PageViewportPanel panel = null;
			for (final Integer i : col) {
				panel = painter.getPageOf(i);
				if(panel.isLoad()) {
					continue;
				}
				panel.loadImage();
			}
			set = col;
		}
		final List<PositionBean> list = PositionShape4Convert.getSelectShape(range, painter.getPageViewports(), col);
		Shape shape = null;
		for (final PositionBean bean : list) {
			if (bean.getPageIndex() == -1) {
				continue;
			}
			component = painter.getPageOf(bean.getPageIndex());
			shape = getShapeWithShape(bean.getViewport(), range
					.getStartPosition());
			bean.setViewport(shape);
		}
		return list;
	}

	private List<PositionBean> getShapeWithPosition(
			final List<PositionBean> beans) {
		if (isNull(beans) || beans.size() == 0) {
			return beans;
		}
		Shape shape = null;
		for (final PositionBean bean : beans) {
			if (bean.getPageIndex() == -1) {
				continue;
			}
			component = painter.getPageOf(bean.getPageIndex());
			/* 偏移量在计算获得Block的Rectangel的时候就已经加上了，故传递Null */
			shape = getShapeWithShape(bean.getViewport(), null);
			bean.setViewport(shape);
		}

		return beans;
	}

	public void destroy() {
		posBeans.clear();
		if (!isNull(selectionRange)) {
			selectionRange.clear();
		}
		if (!isNull(objectSelections)) {
			objectSelections.clear();
		}
		component = null;
	}

	/**
	 * 
	 * 把指定的Shape转为为PageViewportPanel坐标系统中
	 * 
	 * @param shape
	 *            指定要转换的Shape
	 * @param position
	 *            指定选择的第一个位置，目的是用于计算偏移量
	 * @return {@link Shape} 返回转换后的Shape
	 */
	private Shape getShapeWithShape(Shape shape, final DocumentPosition position) {
		if (isNull(shape)) {
			return shape;
		}
		/* PageViewportPanel component = getPageViewportPanel(); */
		final double scaleX = component.getPreviewScaleX();
		final double scaleY = component.getPreviewScaleY();

		if (shape instanceof GeneralPath) {
			final GeneralPath path = (GeneralPath) shape;
			final AffineTransform at = new AffineTransform();
			final double factor = Constants.PRECISION;
			double[] offset = { 0, 0 };
			if (!isNull(position)) {
				if(LocationConvert.isBlankPosition(position)){
					offset = LocationConvert.getOffsetOfBlocks(position.getLeafElement().getArea());
				}else{
					offset = LocationConvert.getOffsetOfBlocks(position);
				}
			}
			final double offsetX = offset[0] * scaleX / factor;
			/* offsetX += component.getOffsetX(); */

			final double offsetY = offset[1] * scaleY / factor;
			/* offsetY += component.getOffsetY(); */

			at.translate(offsetX, offsetY);
			at.scale(scaleX / factor, scaleY / factor);
			path.transform(at);
			shape = path;
		}
		return shape;
	}

	public void reload() {
		if(isNull(set)) {
			return;
		}
		final Collection<Integer> old = AbstractEditComponent.calcAllPageNumbersToShown(painter);
		
		if ((set.size() == old.size()) && set.containsAll(old)) {
			return;
		}
		
		clearSelection();
		if (objectSelections != null && !objectSelections.isEmpty()) {
			createShape(getObjectSelection());
		}
		if (selectionRange != null && !selectionRange.isEmpty()) {
			creatShapes(getSelectionRange());
		}
	}
}
