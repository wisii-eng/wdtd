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
 * @TableOperationHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.select;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.area.AreaTreeObject.AreaKind;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.Property;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.LocationConvert;
import com.wisii.wisedoc.view.select.TableSelectMouseHandler.KeyState;

/**
 * 类功能描述：该类用于处理Table的相关处理操作， 如：改变单元格的大小、行高、表的大小、单元格的合并、拆分等
 * 
 * 作者：李晓光 创建日期：2008-11-5
 */
public class TableOperationHandler {

	public TableOperationHandler(final TableSelectMouseHandler select) {
		setSelectHandler(select);
	}

	/**
	 * 根据指定的位置【鼠标左键押下是的位置】、行高的增量【向下为正】，设置行的高度。
	 * 
	 * @param e
	 *            指定鼠标左键押下时的位置
	 * @param increment
	 *            指定行高的增量
	 */
	public void changeRowHeight(final Point p, double increment) {
		if (increment == 0 || isNull(p)) {
			return;
		}
		final TableCell cell = getCurrentCell(p);
		final Block block = getSelectBlock(editor, p);
		/* 修正精度使得参照线的位置和单元格下边框的位置一直。 */
		increment += (block.getBorderAndPaddingWidthBefore());
		final TableRow row = getNeedChangeRow(cell);
		updateRowHeight(row, increment,
				Constants.PR_BLOCK_PROGRESSION_DIMENSION);
		/* 直接改变当前的行的高度，不再调整下一个相邻行的高度，以保持整个表的高度。【删除原因】 */
	}

	/** 根据指定的增量值，设置指定行的行高【KEY：设置行高】 */
	private void updateRowHeight(final TableRow row, final double increment, final int key) {
		final Document doc = getEditor().getDocument();
		final double height = getRowHeight(row);
		final FixedLength length = new FixedLength(Math.round((float)(height + increment)));
		final Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(key, new LengthRangeProperty(length));
		doc.setElementAttributes(row, map, Boolean.FALSE);
	}

	/** 获得指定行的相邻下一行 */
	private TableRow getNextRow(final TableRow row) {
		TableRow result = null;
		if (isNull(row)) {
			return result;
		}
		final TableBody body = (TableBody) row.getParent();
		final List<CellElement> rows = body.getAllChildren();
		final int index = rows.indexOf(row);
		if (rows.size() == 1) {

		} else if (index == rows.size() - 1) {
			/* row = (TableRow)rows.get(index - 1); */
		} else {
			result = (TableRow) rows.get(index + 1);
		}
		return result;
	}

	/** 获得指定行的行高 */
	private double getRowHeight(final TableRow row) {
		final double height = 0;
		if (isNull(row)) {
			return height;
		}

		final List<CellElement> cells = row.getAllChildren();
		if (isNull(cells) || cells.size() == 0) {
			return height;
		}
		TableCell cell = null;
		for (final CellElement element : cells) {
			cell = (TableCell) element;
			if (cell.getNumberRowsSpanned() == 1) {
				break;
			}
		}
		return getRowHeight(cell);
	}

	/** 获得包含指定单元格所在行的行高 */
	private double getRowHeight(final TableCell cell) {
		double height = 0;
		if (isNull(cell)) {
			return height;
		}
		final List<Block> blocks = cell.getBlocks();
		if (isNull(blocks) || blocks.size() == 0) {
			return height;
		}
		Rectangle2D rect = null;
		for (final Block block : blocks) {
			rect = block.getViewport();
			if (isNull(rect)) {
				continue;
			}
			height += rect.getHeight();
		}
		return height;
	}

	/**
	 * 根据指定坐标点【鼠标押下时的位置】，单元格的增量【向右为正】，设置单元格的宽度。
	 * 
	 * @param p
	 *            指定鼠标左键押下时的位置
	 * @param increment
	 *            指定鼠标位置当前单元格需要增加的宽度。
	 */
	public void changeCellWidth(final Point p, double increment)
	{
		if (increment == 0)
		{
			return;
		}
		if (increment < cellchangemin)
		{
			increment = cellchangemin;
		} else if (increment > cellchangemax)
		{
			increment = cellchangemax;
		}
		final TableCell cell = getCurrentCell(p);
		increment = modifyPrecision(increment);
		increment = calcAutoAlignOffset(cell, increment);
		increment = calcPercentLength(cell, increment);
		updateCellsWidth(leftCells, increment,
				Constants.PR_INLINE_PROGRESSION_DIMENSION);
		updateCellsWidth(rightCells, -increment,
				Constants.PR_INLINE_PROGRESSION_DIMENSION);
		clear();
	}
	private double calcPercentLength(final TableCell cell, final double increment){
		Property pro = (Property)cell.getAttribute(Constants.PR_INLINE_PROGRESSION_DIMENSION);
		if (!(pro instanceof LengthRangeProperty)) {
			return	increment;
		}
		final LengthRangeProperty range = (LengthRangeProperty)pro;
		pro = range.getOptimum(null);
		if(!(pro instanceof PercentLength)) {
			return increment;
		}
		
		final PercentLength length = (PercentLength) pro;
		final Rectangle2D r = cell.getArea().getViewport();
		final double factor = increment / r.getWidth();
		return (length.value() * factor);
	}
	/** 误差修正处理，在double型数据使用前先转化为Integer型再使用，现在的做法是忽略掉10mpt之内的误差 */
	private double modifyPrecision(double increment) {
		increment /= MODIFY_VALUE;
		return (int) increment * MODIFY_VALUE;
	}

	/** 计算自动贴近是需要调整的增量，如果没有贴近情况，这保持原增量。 */
	private double calcAutoAlignOffset(final TableCell cell, final double increment) {
		if (isNull(cell)) {
			return increment;
		}
		final TableRow curRow = getCurrentRow(cell);
		final List<CellElement> rows = getAllRows(cell);
		List<CellElement> cells = null;
		/* Y - X -Increment = Step【abs(step)<=2】 */
		final double x = getCellOffset(cell) + increment;
		double y = 0, compare = 0;
		for (final CellElement row : rows) {
			if (row == curRow) {
				continue;
			}
			cells = getAllCells((TableRow) row);
			for (final CellElement c : cells) {
				if (c == cell) {
					continue;
				}
				y = getCellOffset((TableCell) c);
				compare = y - x;
				if (Math.abs(compare) <= ROUND) {
					return (increment + compare);
				}
			}
		}

		return increment;
	}

	/** 更新指定单元格集合的宽度为Width + increment */
	private void updateCellsWidth(final List<TableCell> cells, final double increment,
			final int key) {
		final Document doc = getEditor().getDocument();
		LengthRangeProperty obj = null, temp = null;
		for (final CellElement cell : cells) {
			if (isNull(cell)) {
				continue;
			}
			obj = (LengthRangeProperty) cell.getAttribute(key);
			temp = add((TableCell)cell,obj, increment);
			if(obj == temp) {
				continue;
			}
			
			final Map<Integer, Object> map = new HashMap<Integer, Object>();
			map.put(key, temp);
			doc.setElementAttributes(cell, map, Boolean.FALSE);
		}
	}

	/***
	 * 计算给指定的LengthRangeProperty的Min、Opt、Max分别 加上一个增量
	 * 
	 * @param source
	 *            指定Property
	 * @param increment
	 *            指定增量
	 * @return {@link LengthRangeProperty} 返回添加了增量的Property
	 */
	private LengthRangeProperty add(TableCell cell,final LengthRangeProperty source, final double increment) {
		if (increment == 0 || isNull(source)) {
			return source;
		}
		LengthProperty newOpt = source.getOptimum(null);
		newOpt = add(cell,newOpt, increment);
		LengthProperty newMin = source.getMinimum(null);
		newMin = add(cell,newMin, increment);
		LengthProperty newMax = source.getMaximum(null);
		newMax = add(cell,newMax, increment);
		/*FixedLength oldOpt = (FixedLength) source.getOptimum(null);
		double temp = Math.max((oldOpt.getNumericValue() + increment),
				MIN_CELL_WIDTH);
		FixedLength newOpt = new FixedLength(temp, DEFAULT_UNIT);
		FixedLength oldMin = (FixedLength) source.getMaximum(null);
		temp = Math.max((oldMin.getNumericValue() + increment), MIN_CELL_WIDTH);
		FixedLength newMin = new FixedLength(temp, DEFAULT_UNIT);
		FixedLength oldMax = (FixedLength) source.getMaximum(null);
		temp = Math.max((oldMax.getNumericValue() + increment), MIN_CELL_WIDTH);
		FixedLength newMax = new FixedLength(temp, DEFAULT_UNIT);*/

		return new LengthRangeProperty(newMin, newOpt, newMax);
	}
	private LengthProperty add(TableCell cell,final LengthProperty source, final double increment){
		if(source instanceof FixedLength) {
			return add((FixedLength)source, increment);
		} else if(source instanceof PercentLength){
			return add(cell,(PercentLength)source, increment);
		}
		return source;
	}
	/**
	 * 计算指定的PercentLenght添加了增量后的值。
	 * @param source	指定Property
	 * @param increment	指定增量【百分比小数如：50%-->0.5】
	 * @return	{@link PercentLength}	返回添加了增量的Property。
	 */
	private PercentLength add(TableCell cell,final PercentLength source, final double increment){
		if(isNull(source) || increment == 0) {
			return source;
		}
		double oldvalue=source.value();;
		double value = oldvalue;
		value += increment;
		final Rectangle2D r = cell.getArea().getViewport();
		double cellwidth=r.getWidth();
		if(value==0d||value*cellwidth/oldvalue<MIN_CELL_WIDTH){
			value=MIN_CELL_WIDTH*oldvalue/cellwidth;
		}
		source.setFactor(value);
		return source;
	}
	private FixedLength add(final FixedLength source, final double increment){
		if(isNull(source) || increment == 0) {
			return source;
		}
		double value = source.getNumericValue();
		value += increment;
		value = Math.max(value, MIN_CELL_WIDTH);
		return new FixedLength(Math.round((float)value));
	}
	/** 设置单元格宽度时，这里初始化需要改变单元格的集合【leftCells、rightCells】 */
	 void initAllNeedChangedCell(final TableCell cell) {
		if (hasSelectedCell(cell)) {
			/* 有单元格被选择时，改变单元格大小时，处理如下： */
			processHaveSelectCell(cell);
		} else {
			/* 没有单元格被选择时，改变单元格大小时，处理如下： */
			processNoneSelectCell(cell);
		}
	}

	/**
	 * 指定的单元格、或右侧单元格中有被选中的， 此时进改变指定单元格及右侧单元格的宽度。
	 * 
	 * @param left
	 *            指定当前单元格
	 */
	private void processHaveSelectCell(TableCell left)
	{
		cellchangemin = Integer.MIN_VALUE;
		cellchangemax = Integer.MAX_VALUE;
		TableCell right = getNextAdjacentCell(left);
		if (!isSpanRows(left) && !isSpanRows(right))
		{
			updataLeftcells(left);
			updataRightcells(right);
			return;
		}
		if (isNull(right))
		{
			updataLeftcells(left);
			return;
		}

		int startrow = -1;
		int endrow = 0;
		if (isSpanRows(left))
		{
			startrow = getIndexOfRows(getCurrentRow(left));
			endrow = startrow + left.getNumberRowsSpanned();
		}
		if (isSpanRows(right))
		{
			final int temp = getIndexOfRows(getCurrentRow(right));
			final int span = right.getNumberRowsSpanned();
			if (startrow == -1)
			{
				startrow = temp;
			} else
			{
				startrow = Math.min(startrow, temp);
			}
			endrow = Math.max(endrow, (temp + span));
		}

		startrow = calcMinRowIndex(startrow, left, right);
		endrow = calcMaxRowIndex(endrow, left, right);

		final List<CellElement> rows = getAllRows(left);
		final double compare = getCellOffset(left);
		/* String leftID = "", rightID = "", tempID = ""; */
		for (int i = startrow; i < endrow; i++)
		{
			final CellElement row = rows.get(i);
			final List<CellElement> cells = row.getChildren(0);
			for (int column = 1; column < cells.size(); column++)
			{
				left = (TableCell) cells.get(column - 1);
				if (!isLeftCell(left, compare))
				{
					continue;
				}
				updataLeftcells(left);
				right = (TableCell) cells.get(column);
				if (isNull(right))
				{
					break;
				}
				updataRightcells(right);
				break;
			}
		}
	}
	private boolean isLeftCell(final TableCell left, final double compare){
		final List<Block> blocks = left.getBlocks();
		final Rectangle2D r = blocks.get(0).getViewport();
		final double d = (r.getX() + r.getWidth()) - compare;
		return Math.abs(d) < 1000;
	}
	/*private double calcCellPosition(TableCell left){
		List<Block> blocks = left.getBlocks();
		if(isNull(blocks) || blocks.size() == 0)
			return -1;
		Rectangle2D r = blocks.get(0).getViewport();
		return (r.getX() + r.getWidth());
	}*/
	/** 计算因行方向上有单元格合并，要想添加所有需要改变单元格数目的最大行索引。 */
	private int calcMaxRowIndex(final int end, TableCell left, TableCell right) {
		final List<CellElement> rows = getAllRows(left);
		final CellElement row = rows.get(end - 1);
		final List<CellElement> cells = row.getChildren(0);
		int indexS = -1, indexE = -1;
		int max = end, rowIndex = -1;
		indexS = cells.indexOf(left);
		if (indexS != -1) {
			rowIndex = getIndexOfRows(getCurrentRow(left));
			final int leftT = rowIndex + left.getNumberRowsSpanned();
			right = (TableCell) cells.get(indexS + 1);
			rowIndex = getIndexOfRows(getCurrentRow(right));
			final int rightT = rowIndex + right.getNumberRowsSpanned();
			if (leftT == rightT) {
				return max;
			} else if (rightT > leftT) {
				max = rightT;
			}
		}
		indexE = cells.indexOf(right);
		if (indexE != -1) {
			rowIndex = getIndexOfRows(getCurrentRow(right));
			final int rightT = rowIndex + right.getNumberRowsSpanned();
			left = (TableCell) cells.get(indexE - 1);
			rowIndex = getIndexOfRows(getCurrentRow(left));
			final int leftT = rowIndex + left.getNumberRowsSpanned();
			if (leftT == rightT) {
				return max;
			} else if (leftT > rightT) {
				max = leftT;
			}
		}
		return calcMaxRowIndex(max, left, right);
	}

	/** 计算因行方向上有单元格合并，要想添加所有需要改变单元格数目的最小行索引。 */
	private int calcMinRowIndex(final int start, TableCell left, TableCell right) {
		final List<CellElement> rows = getAllRows(left);
		final CellElement row = rows.get(start);
		final List<CellElement> cells = row.getChildren(0);
		int indexS = -1, indexE = -1;
		int min = start;
		indexS = cells.indexOf(left);
		if (indexS != -1) {
			final int leftT = getIndexOfRows(getCurrentRow(left));
			right = (TableCell) cells.get(indexS + 1);
			final int rightT = getIndexOfRows(getCurrentRow(right));
			if (leftT == rightT) {
				return min;
			} else if (leftT > rightT) {
				min = rightT;
			}
		}
		indexE = cells.indexOf(right);
		if (indexE != -1) {
			final int rightT = getIndexOfRows(getCurrentRow(right));
			left = (TableCell) cells.get(indexE - 1);
			final int leftT = getIndexOfRows(getCurrentRow(left));
			if (leftT == rightT) {
				return min;
			} else if (leftT < rightT) {
				min = leftT;
			}
		}
		return calcMinRowIndex(min, left, right);
	}

	/***
	 * 指定的单元格左侧、右侧均无单元格被选中， 获得所有需要改变宽度的单元格集合
	 * 
	 * @param left
	 *            指定当前单元格
	 */
	private void processNoneSelectCell(final TableCell left)
	{
		final List<CellElement> rows = getAllRows(left);
		final int rowsize = rows.size();
		final double compare = getCellOffset(left);
        cellchangemin = Integer.MIN_VALUE;
        cellchangemax = Integer.MAX_VALUE;
		for (int i = 0; i < rowsize; i++)
		{
			final CellElement row = rows.get(i);
			final List<CellElement> cells = row.getChildren(0);
			final int cellsize = cells.size();
			for (int col = 0; col < cellsize; col++)
			{
				CellElement cell = cells.get(col);
				TableCell c = (TableCell) cell;
				/* width = getCellOffset(c); */
				/* if (width != compare) */
				if (!isLeftCell(c, compare))
				{
					continue;
				}
				if (c.getNumberRowsSpanned() == 1
						|| (c.getNumberRowsSpanned() > 1 && getIndexOfRows(c) == i))
				{
					updataLeftcells(c);
				}
				if (col < cellsize - 1)
				{
					cell = cells.get(col + 1);
					c = (TableCell) cell;
					if (c.getNumberRowsSpanned() == 1
							|| (c.getNumberRowsSpanned() > 1 && getIndexOfRows(c) == i))
					{
						updataRightcells(c);
					}
				}
				break;
			}
		}
	}
	/*
     * 拖动改变单元格宽度时，算出可拖动的最小大小
     */
	private void updataLeftcells(TableCell leftcell)
	{
		if (!leftCells.contains(leftcell))
		{
			double width = getCellWidth(leftcell);
			double minwidth = MIN_CELL_WIDTH - width;
			if (minwidth > cellchangemin)
			{
				cellchangemin = minwidth;
			}
			leftCells.add(leftcell);
		}
	}
/*
     * 拖动改变单元格宽度时，算出可拖动的最大大小
     */
	private void updataRightcells(TableCell rightcell)
	{
		if (!rightCells.contains(rightcell))
		{
			double width = getCellWidth(rightcell);
			double maxwidth = width - MIN_CELL_WIDTH;
			if (maxwidth < cellchangemax)
			{
				cellchangemax = maxwidth;
			}
			rightCells.add(rightcell);
		}
	}
	/**
	 * 获得指定单元格Border-End的位置【坐标：Table】
	 * 
	 * @param cell
	 *            指定单元格
	 * @return double 返回Border-End的偏移量【坐标：Table】
	 */
	private double getCellOffset(final TableCell cell) {
		final List<Block> blocks = cell.getBlocks();
		if (isNull(blocks) || blocks.size() == 0) {
			return -1;
		}
		final Block block = blocks.get(0);
		final Rectangle2D rect = block.getViewport();

		return (rect.getWidth() + rect.getX());
	}

	/**
	 * 获得指定单元格的宽度
	 * 
	 * @param cell
	 *            指定单元格
	 * @return double 返回指定单元格的宽度
	 */
	private double getCellWidth(final TableCell cell) {
		final List<Block> blocks = cell.getBlocks();
		final Block block = blocks.get(0);
		final Rectangle2D rect = block.getViewport();
		return rect.getWidth();
	}

	/**
	 * 获得指定点的TableCell
	 * 
	 * @param p
	 *            指定坐标位置
	 * @return {@link TableCell} 返回包含指定点的TableCell
	 */
	public TableCell getCurrentCell(final Point p) {
		final Block block = getSelectBlock(editor, p);
		if (isNull(block) || block.getAreaKind() != AreaKind.CELL) {
			return null;
		}
		return (TableCell) block.getSource();
	}

	/** 获得被选择的TableCell的Area【Block】 */
	public Block getSelectBlock(final AbstractEditComponent comp, final Point p) {
		Area a = getCurrentArea(comp, p);
		a = LocationConvert.searchAbsoluteArea(a);
		return (Block) a;
	}

	/** 获得包含指定的点的最小Area */
	private Area getCurrentArea(final AbstractEditComponent comp, final Point p) {
		if (isNull(comp) || isNull(p)) {
			return null;
		}
		comp.findPageViewportPanel(p);
		final PageViewportPanel panel = comp.getCurrentPagePanel();
		if (isNull(panel)) {
			return null;
		}
		Point p0 = SwingUtilities.convertPoint(comp.getGridPanel(), (Point) p
				.clone(), panel);
		p0 = (Point) panel.getDPIPoint(p0);
		final Area a = panel.getConvert().getCurrentArea(p0);
		return a;
	}

	/***
	 * 根据指定的获得当前的Position
	 * 
	 * @param p
	 *            指定当前的鼠标位置
	 * @return 返回计算的Position
	 */
	public DocumentPosition getCurrentPosition(final Point p) {
		return editor.pointtodocpos(p);
	}

	/**
	 * 根据指定的TableCell获得当前Table
	 * 
	 * @param cell
	 *            指定TableCell
	 * @return {@link Table} 返回包含指定Cell的Table
	 */
	public static Table getCurrentTable(final CellElement cell) {
		final Table table = (Table) LocationConvert.searchCellElement(cell,
				Table.class);
		return table;
	}

	/**
	 * 根据当前点获得包含该点的Table
	 * 
	 * @param p
	 *            指定当前点
	 * @return {@link Table} 返回包含当前点的Table
	 */
	public Table getCurrentTable(final Point p) {
		final TableCell cell = getCurrentCell(p);
		return getCurrentTable(cell);
	}

	/**
	 * 获得包含指定Cell的Table下的所有Row，
	 * 
	 * @param cell
	 *            指定Cell
	 * @return {@link List} 返回表包含的所有行。
	 */
	public List<CellElement> getAllRows(final CellElement cell) {
		final Table table = getCurrentTable(cell);
		if (isNull(table)) {
			return new ArrayList<CellElement>();
		}
		return table.getAllRows();
	}

	/** 获得指定行【Row】下包含的所有单元格【Cell】 */
	private List<CellElement> getAllCells(final TableRow row) {
		if (isNull(row)) {
			return new ArrayList<CellElement>();
		}
		return row.getAllChildren();
	}

	/** 获得包含该单元格的行 */
	public TableRow getCurrentRow(final TableCell cell) {
		if (isNull(cell)) {
			return null;
		}
		final CellElement ele = (CellElement) cell.getParent();
		return (TableRow) ele;
	}

	/** 获得在改变行高时，需要改变的行 */
	private TableRow getNeedChangeRow(final TableCell cell) {
		final List<CellElement> rows = getAllRows(cell);
		final TableRow row = getCurrentRow(cell);
		int rowIndex = rows.indexOf(row);
		final int span = cell.getNumberRowsSpanned();
		rowIndex = rowIndex + span - 1;
		return (TableRow) rows.get(rowIndex);
	}

	/** 根据指定的坐标位置，获得包含该位置的行 */
	private TableRow getCurrentRow(final Point p) {
		final TableCell cell = getCurrentCell(p);
		if (isNull(cell)) {
			return null;
		}
		return getCurrentRow(cell);
	}

	/** 获得指定行所在表中的索引 */
	private int getIndexOfRows(final TableRow row) {
		final List<CellElement> rows = getAllRows(row);
		if (isNull(rows)) {
			return -1;
		}
		return rows.indexOf(row);
	}

	/** 获得指定单元格所在表中的索引 */
	private int getIndexOfRows(final TableCell cell) {
		final TableRow row = getCurrentRow(cell);
		return getIndexOfRows(row);
	}

	/**
	 * 
	 * 获得指定Cell的下一个相邻的Cell
	 * 
	 * @param cell
	 *            指定Cell
	 * @return {@link TableCell} 返回指定Cell的下一个相邻的Cell
	 */
	public TableCell getNextAdjacentCell(final TableCell cell) {
		if (isNull(cell)) {
			return cell;
		}
		final TableRow row = (TableRow) cell.getParent();
		final List<CellElement> cells = row.getAllChildren();
		final int index = cells.indexOf(cell) + 1, size = cells.size();
		if ((size == 1) || (index == size)) {
			return null;
		}

		return (TableCell) cells.get(index);
	}

	public List<TableCell> getNextAdjacentCells(final TableCell cell) {
		final List<TableCell> cells = new ArrayList<TableCell>();
		if (isNull(cell)) {
			return cells;
		}
		final TableRow row = getCurrentRow(cell);
		final int rowIndex = getIndexOfRows(row);
		final int max = rowIndex + cell.getNumberRowsSpanned();
		final List<CellElement> rows = getAllRows(cell);
		List<CellElement> temp = null;
		for (int i = rowIndex; i < max; i++) {
			final TableRow r = (TableRow) rows.get(i);
			temp = r.getAllChildren();
			final int index = temp.indexOf(cell) + 1, size = temp.size();
			if ((size == 1) || (index == size)) {
				continue;
			}
			cells.add((TableCell) temp.get(index));
		}
		return cells;
	}

	/**
	 * 
	 * 判断指定的单元格是否跨行了
	 * 
	 * @param cell
	 *            指定单元格
	 * @return {@link Boolean} 如果跨行：True 否则：False
	 */
	public boolean isSpanRows(final TableCell cell) {
		if (isNull(cell)) {
			return Boolean.FALSE;
		}
		final int count = cell.getNumberRowsSpanned();
		return (count > 1);
	}

	/**
	 * 
	 * 判断当前Cell、下一个相邻的Cell是否有被选择
	 * 
	 * @param cell
	 *            鼠标位置的当前Cell
	 * @return {@link Boolean} 如果有Cell被选择：True 否则：False
	 */
	public boolean hasSelectedCell(final TableCell cell) {
		final SelectionModel model = editor.getSelectionModel();
		final boolean flag = model.hasTableCell(cell);
		if (flag) {
			return flag;
		}

		return model.hasTableCells(getNextAdjacentCells(cell));
	}

	/**
	 * 获得指定单元格的可调整的范围，-表示向左调整，+表示向右调整。
	 * 
	 * @param cell
	 *            指定当前的单元格
	 * @return double[] 返回可调整的范围
	 */
	public double[] calcCellChangeRange(TableCell cell) {
		final double min = -(getCellWidth(cell) - MIN_CELL_WIDTH);

		double max = Double.MAX_VALUE;
		cell = getNextAdjacentCell(cell);
		if (!isNull(cell)) {
			max = (getCellWidth(cell) - MIN_CELL_WIDTH);
		}

		return new double[] { min, max };
	}

	/**
	 * 获得指定单元格所在行的行调整范围， -表示向上调整，+表示向下调整。
	 * 
	 * @param cell
	 *            指定当前单元格
	 * @return double[] 返回可调整的范围
	 */
	public double[] calcRowChangeRange(final TableCell cell) {
		final double min = -(getRowHeight(cell) - MIN_ROW_HEIGHT);

		TableRow row = getCurrentRow(cell);
		row = getNextRow(row);
		final double max = Double.MAX_VALUE;

		return new double[] { min, max };
	}

	/**
	 *判断指定的值是否在指定的范围内。
	 * 
	 * @param range
	 *            指定值范围
	 * @param value
	 *            指定值
	 * @return 如果在范围内：True 否则：False
	 */
	public boolean isInRange(final double[] range, final double value) {
		if (isNull(range) || isNull(value) || range.length < 2) {
			return Boolean.FALSE;
		}
		return (range[0] <= value && value <= range[1]);
	}

	/**
	 * 判断指定的值是否包含在指定点的TableCell的调整范围内
	 * 
	 * @param start
	 *            指定坐标点
	 * @param increment
	 *            指定增量值
	 * @return 如果在范围内：True 否则：False
	 */
	public boolean isInRangeCellChange(final Point start, final double increment) {
		if (isNull(start)) {
			return Boolean.FALSE;
		}
//		final TableCell cell = getCurrentCell(start);
//		final double[] range = calcCellChangeRange(cell);
		return isInRange(new double[]{cellchangemin,cellchangemax}, increment);
	}

	/**
	 * 判断指定的值是否包含在指定点的TableRow的行调整范围。
	 * 
	 * @param start
	 *            指定坐标点
	 * @param increment
	 *            指定增量值
	 * @return 如果在范围内：True 否则：False
	 */
	public boolean isInRangeRowChange(final Point start, final double increment) {
		if (isNull(start)) {
			return Boolean.FALSE;
		}
		final TableCell cell = getCurrentCell(start);
		final double[] range = calcRowChangeRange(cell);
		return isInRange(range, increment);
	}

	/**
	 * 用于处理表选择操作
	 * 
	 * @param cell
	 *            指定鼠标所在的单元格
	 */
	public CellElement selectTable(final TableCell cell) {
		final Table table = getCurrentTable(cell);
		final SelectionModel model = getSelectionModel();
		if (isNull(model)) {
			return cell;
		}
		model.clearObjectSelection();
		model.addObjectSelection(table);
		return table;
	}

	/**
	 * 用于处理行选择操作
	 * 
	 * @param cell
	 *            指定鼠标所在的单元格
	 * @param r
	 *            指定第一选择的行
	 * @param state
	 *            指定辅助健的状态
	 */
	public TableRow selectTableRow(final TableCell cell, final TableRow r, final KeyState state) {
		final SelectionModel model = getSelectionModel();
		if (isNull(model) || isNull(cell)) {
			return r;
		}
		final TableRow row = getCurrentRow(cell);		
		switch (state) {
		case CTRL:
			model.addObjectSelection(row);
			clearCache();
			break;
		case SHIFT:
			model.clearObjectSelection(cache);
			int start = getIndexOfRows(r);
			final int end = getIndexOfRows(row);
			if (start == -1) {
				start = end;
			}
			List<CellElement> rows = getAllRows(row);
			rows = rows.subList(Math.min(start, end), Math.max(start, end) + 1);
			model.addObjectSelections(rows);
			// 缓存元素
			cache.addAll(rows);
			break;
		case ALT:			
			model.addObjectSelection(row);
			clearCache();
			break;
		default:
			model.clearObjectSelection();
			model.addObjectSelection(row);
			clearCache();
			break;
		}
		return row;
	}
	
	public CellElement selectRowOrCell(final CellElement ele, final TableCell cell,
			final KeyState state) {
		final SelectionModel model = getSelectionModel();
		if (isNull(model) || isNull(cell)) {
			return cell;
		}
		
		final boolean isCell = (ele instanceof TableCell);
		final boolean isRorw = (ele instanceof TableRow);
		CellElement result = ele;
		switch (state) {
		case CTRL:
			result = cell;
			if (isCell) {
				model.addObjectSelection(cell);
			} else if (isRorw) {
				result = selectTableRow(cell, (TableRow) ele, KeyState.CTRL);
			} else {
				model.addObjectSelection(cell);
			}
			clearCache();
			break;
		case SHIFT:
			model.clearObjectSelection(cache);
			if (isCell) {
				calcRangeSelection((TableCell) ele, cell);
			} else if (isRorw) {
				selectTableRow(cell, (TableRow) ele, KeyState.SHIFT);
			} else {
				model.addObjectSelection(cell);
			}
			break;
		case ALT:
			model.addObjectSelection(cell);
			clearCache();
			break;
		default:
			model.clearSelection();
			model.addObjectSelection(cell);
			clearCache();
			break;
		}
		return result;
	}

	/**
	 * 把指定单元格所在的表体，放入SelectModel中
	 * 
	 * @param cell
	 *            指定单元格
	 */
	public void selectTableBody(final TableCell cell) {
		final SelectionModel model = getSelectionModel();
		if (isNull(cell) || isNull(model)) {
			return;
		}
		final TableBody body = (TableBody) cell.getParent().getParent();
		model.clearSelection();
		clearCache();
		model.addObjectSelection(body);
	}

	/**
	 * 根据指定的单元格，确定由连个单元格的范围，并不范围内的单元格添加到SelectModel中。
	 * 
	 * @param start
	 *            指定一个单元格
	 * @param end
	 *            指定另一个单元格。
	 */
	public void calcRangeSelection(final TableCell start, final TableCell end) {
		addSelectObjectToModel(start, end);
	}

	/** 处理选择单元格的同时，押下了Shift键 */
	private void addSelectObjectToModel(final TableCell start, final TableCell end) {
		if (isNull(start) || isNull(end)) {
			return;
		}

		final Rectangle2D rect = createMaxSelectedRect(start, end);

		final Table table = getCurrentTable(start);
		final TableRow rowS = (TableRow) start.getParent();
		final TableRow rowE = (TableRow) end.getParent();

		final List<CellElement> rows = table.getAllRows();

		int indexS = rows.indexOf(rowS);
		indexS = Math.max(indexS, 0);
		int indexE = rows.indexOf(rowE);
		indexE = Math.max(indexE, 0);
		final int min = Math.min(indexS, indexE);
		final int max = Math.max(indexS, indexE);
		final List<CellElement> changed = new ArrayList<CellElement>();
		TableRow row = null;
		for (int i = min; i <= max; i++) {
			row = (TableRow) rows.get(i);
			if ((row == null) || (rect == null)) {
				return;
			}
			final List<CellElement> temp = addSelectObjectToModel(row, rect);
			changed.addAll(temp);
		}
		if(changed.size() == 0) {
			return;
		}
		final SelectionModel model = getSelectionModel();
		model.addObjectSelections(changed);
	}

	private Rectangle2D createMaxSelectedRect(final TableCell start, final TableCell end) {
		final Rectangle2D startR = createTableCellRect(start);
		final Rectangle2D endR = createTableCellRect(end);
		final Rectangle2D result = startR.createUnion(endR);
		
		return result;
	}

	private Rectangle2D createTableCellRect(final TableCell cell) {
		Rectangle2D rect = null;
		if (cell == null) {
			return rect;
		}
		final List<Block> blocks = cell.getBlocks();
		rect = blocks.get(0).getViewport();
		return rect;
	}

	/** 判断指定TableRow是否有与指定区域相交的单元格，如果有添加的SelectModel中 */
	private List<CellElement> addSelectObjectToModel(final TableRow row, final Rectangle2D rect) {
		final List<CellElement> cells = row.getAllChildren();
		
		final List<CellElement> changed = new ArrayList<CellElement>();
		Rectangle2D r = null;		
		for (final CellElement cell : cells) {
			if (!(cell instanceof TableCell)) {
				continue;
			}
			final TableCell c = (TableCell) cell;
			List<Block> blocks = c.getBlocks();
			if(blocks==null||blocks.isEmpty())
			{
				continue;
			}
			r = blocks.get(0).getViewport();		
			//【删除：START】 by 李晓光  2010-2-2
			//因精度的问题，影响判断，
			/*if (rect.intersects(r)) {
				changed.add(cell);
			}*/
			//【删除：END】 by 李晓光  2010-2-2
			if (isSelctedCell(rect, r)) {
				changed.add(cell);
			}
		}
		
		// 缓存元素【Shift】
		cache.addAll(changed);
		
		return changed;
	}
	
	/**
	 * 判断指定的区域r，是否与dest区域相交。
	 * @param dest				指定区域
	 * @param r					指定区域
	 * @return	{@link Boolean}	如果两个区域相交：True，否则：False。
	 */
	private boolean isSelctedCell(Rectangle2D dest, Rectangle2D r){
		if(!dest.intersects(r)){
			return Boolean.FALSE;
		}
		if(dest.contains(r)){
			return Boolean.TRUE;
		}
		double step = 0;
		if(dest.contains(r.getX(), dest.getY())){
			step = dest.getMaxX() - r.getX();
		}else if(dest.contains(r.getMaxX(), dest.getY())){
			step = r.getMaxX() - dest.getX();
		}else{
			return Boolean.TRUE;
		}
		//对精度进行调整，如果相差1/10000pt，且仅是相交时，
		step *= 10;
		
		return (step > 1);
	}

	/***
	 * 根据指定的位置信息获得包含指定位置的单元格对象。
	 * 
	 * @param pos
	 *            指定位置信息
	 * @return 返回包含指定位置的单元格对象，如果不是被单元格包含，返回Null。
	 */
	public static TableCell getCurrentCell(final DocumentPosition pos) {
		if (isNull(pos)) {
			return null;
		}
		final TableCell cell = (TableCell) LocationConvert.searchCellElement(pos
				.getLeafElement(), TableCell.class);
		return cell;
	}

	/* -------------------------连选处理代码------------------------------ */

	/* -------------------------连选处理代码------------------------------ */

	/* -------------------------属性处理代码------------------------------ */
	public AbstractEditComponent getEditor() {
		return editor;
	}

	public void setEditor(final AbstractEditComponent editor) {
		this.editor = editor;
	}

	public SelectionModel getSelectionModel() {
		if (isNull(editor)) {
			return null;
		}
		return editor.getSelectionModel();
	}

	public void clearSelectionModel() {
		final SelectionModel model = getSelectionModel();
		if (isNull(model)) {
			return;
		}
		model.clearObjectSelection();
	}

	public TableSelectMouseHandler getSelectHandler() {
		return selectHandler;
	}

	public void setSelectHandler(final TableSelectMouseHandler selectHandler) {
		this.selectHandler = selectHandler;
		if (!isNull(selectHandler)) {
			setEditor(selectHandler.getEditor());
		}
	}

	private void clear() {
		leftCells.clear();
		rightCells.clear();
		cellchangemax=0;
		cellchangemin=0;
	}

	private void clearCache() {
		if (!isNull(cache)) {
			cache.clear();
		}
	}

	/* -------------------------属性处理代码------------------------------ */
	/* 当押下shift健时选中的所有元素的缓存。 */
	private final List<CellElement> cache = new ArrayList<CellElement>();
	private AbstractEditComponent editor = null;
	private TableSelectMouseHandler selectHandler = null;
	/* 竖直线左边需要改变宽度的单元格集合 */
	private final List<TableCell> leftCells = new ArrayList<TableCell>();
	/* 竖直线右边需要改变宽度的单元格集合 */
	private final List<TableCell> rightCells = new ArrayList<TableCell>();
	/* 指定单元格的最小宽度 */
	private final static double MIN_CELL_WIDTH = 15 * Constants.PRECISION;
	
	/* 指定单元格的最小高度 */
	private final static double MIN_ROW_HEIGHT = 10 * Constants.PRECISION;
	/* 单元格贴近处理时，范围的绝对值 */
	private final static double ROUND = 5 * Constants.PRECISION;
	/* 误差调整的范围值，100说明是调整范围是100mpt只内 */
	private final static int MODIFY_VALUE = 10;
	//单元格最小可改变数
	private double cellchangemin = 0d;
	//单元格最大可改变数
	private double cellchangemax = 0d;
}
