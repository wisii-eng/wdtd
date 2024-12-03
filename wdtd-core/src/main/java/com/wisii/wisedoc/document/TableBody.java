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
 * @TableBody.java 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.TableColLength;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-28
 */
public class TableBody extends TableFObj implements Groupable
{
	protected boolean firstRow = true;

	/**
	 * used for initial values of column-number property
	 */
	// protected List pendingSpans;
	private int columnIndex = 1;
	// 边框，背景，padding等属性
	private CommonBorderPaddingBackground commonbpbackground;

	// protected BitSet usedColumnIndices;
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public TableBody()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public TableBody(final Map<Integer, Object> attributes)
	{
		super(attributes);
		// TODO Auto-generated constructor stub
	}

	public void initFOProperty()
	{
		super.initFOProperty();
		if (commonbpbackground == null)
		{
			commonbpbackground = new CommonBorderPaddingBackground(this);
		} else
		{
			commonbpbackground.init(this);
		}
		int size = getChildCount();
		for (int i = 0; i < size; i++)
		{
			CellElement cellelement = getChildAt(i);
			cellelement.initFOProperty();
		}
	}

	/**
	 * @see com.wisii.fov.fo.FONode#addChildNode(FONode)
	 */
	public void add(CellElement child)
	{
		if (child == null || !(child instanceof TableRow))
		{
			return;
		}
		if (firstRow)
		{
			firstRow = false;
		}
		super.add(child);
	}

	/**
	 * @return the Common Border, Padding, and Background Properties.
	 */
	public CommonBorderPaddingBackground getCommonBorderPaddingBackground()
	{
		return commonbpbackground;
	}

	/** @see com.wisii.fov.fo.FONode#getLocalName() */
	public String getLocalName()
	{
		return "table-body";
	}

	/**
	 * @see com.wisii.fov.fo.FObj#getNameId()
	 */
	public int getNameId()
	{
		return Constants.FO_TABLE_BODY;
	}

	/**
	 * @param obj
	 *            table row in question
	 * @return true if the given table row is the first row of this body.
	 */
	public boolean isFirst(TableRow obj)
	{
		return (_children == null || (!_children.isEmpty() && _children.get(0) == obj));
	}

	/**
	 * @param obj
	 *            table row in question
	 * @return true if the given table row is the first row of this body.
	 */
	public boolean isLast(TableRow obj)
	{
		return (_children == null || (_children.size() > 0 && _children
				.get(_children.size() - 1) == obj));
	}

	/**
	 * Returns the current column index of the TableBody
	 * 
	 * @return the next column number to use
	 */
	protected int getCurrentColumnIndex()
	{
		return columnIndex;
	}

	/**
	 * Sets the current column index to a specific value (used by
	 * ColumnNumberPropertyMaker.make() in case the column-number was explicitly
	 * specified on the cell)
	 * 
	 * @param newIndex
	 *            the new column index
	 */
	protected void setCurrentColumnIndex(int newIndex)
	{
		columnIndex = newIndex;
	}

	/* 【添加:START】 by 李晓光 2008-12-11 */
	public boolean isTableHeader()
	{
		return isClassOf(TableHeader.class);
	}

	public boolean isTableFooter()
	{
		return isClassOf(TableFooter.class);
	}

	private boolean isClassOf(Class clazz)
	{
		return (this.getClass() == clazz);
	}
	public boolean iscanremoveall()
	{
		return false;
	}

	/* 【添加:END】 by 李晓光 2008-12-11 */
	public boolean isGroupAble()
	{
		if ((this instanceof TableFooter) || (this instanceof TableHeader))
		{
			return false;
		} else
		{
			return true;
		}
	}
	@Override
	public Element clone()
	{
		TableBody tb = (TableBody) super.clone();
		Map<TableCell, TableCell> rowsplncell = new HashMap<TableCell, TableCell>();
		int rowsize = getChildCount();
		for (int i = 0; i < rowsize; i++)
		{
			TableRow row = (TableRow) getChildAt(i);
			TableRow clonerow = (TableRow) tb.getChildAt(i);
			int cellsize = row.getChildCount();
			for (int j = 0; j < cellsize; j++)
			{
				TableCell tcell = (TableCell) row.getChildAt(j);
				if (tcell.getNumberRowsSpanned() > 1)
				{
					TableCell clonecell = rowsplncell.get(tcell);
					if (clonecell != null)
					{
						clonerow.remove(j);
						clonerow.insert(clonecell, j);
					} else
					{
						rowsplncell.put(tcell, (TableCell) clonerow
								.getChildAt(j));
					}
				}
			}
		}
		return tb;
	}

	// 用来生成供排版用的rowGroups对象
	void generateRowGroup()
	{
		Iterator<CellElement> rowsit = getChildren();
		//用来记录跨行的单元格，在第一次包含该单元格的表格行时添加
		List<TableCell> rowspancells = new ArrayList<TableCell>();
		while (rowsit.hasNext())
		{
			TableRow row = (TableRow) rowsit.next();
			Iterator<CellElement> tablecellit = row.getChildren();
			Number currentlen = 0;
			while (tablecellit.hasNext())
			{

				TableCell tablecell = (TableCell) tablecellit.next();
				int rowspan = tablecell.getNumberRowsSpanned();  
				boolean isaddfirst = true;
				//tablecell的父对象设置成第一个包含它的表格行
				if(rowspan>1)
				{
					if (!rowspancells.contains(tablecell))
					{
						tablecell.setParent(row);
						rowspancells.add(tablecell);
					}else
					{
						isaddfirst = false;
					}
				}
				else
				{
					tablecell.setParent(row);
				}
				Length width = tablecell.getInLineProgressionDimension()
						.getOptimum(null);
				// 如果是百分比长度
				if (width instanceof PercentLength)
				{
					PercentLength perlen = (PercentLength) width;
					double widthfactor = perlen.value();
					if(isaddfirst)
					{
						initTableCell(tablecell, widthfactor, currentlen, false);
					}
					currentlen = currentlen.doubleValue() + widthfactor;
				}
				// 否则是固定长度
				else
				{
					int widthint = ((FixedLength) width).getValue();
					if (isaddfirst)
					{
						initTableCell(tablecell, widthint, currentlen, true);
					}
					currentlen = currentlen.intValue() + widthint;
				}
			}
		}
	}

	private void initTableCell(TableCell tablecell, Number width,
			Number currentlen, boolean isfixedlen)
	{
		List columns = getTable().getColumns();
		int columnnumber = 1;
		int numbercolumnspan = 1;
		// 固定长度时
		if (isfixedlen)
		{
			int len = 0;
			int columnsize = columns.size();
			int celllen = currentlen.intValue();
			for (int i = 0; i < columnsize; i++)
			{
				TableColumn column = (TableColumn) columns.get(i);
				int columnwidth = column.getColumnWidth().getValue();

				if (Math.abs(celllen - len)<100)
				{
					columnnumber = i + 1;
					int cellwidth = width.intValue();
					cellwidth = cellwidth - columnwidth;
					for (int j = i + 1; j < columnsize && cellwidth> 100&&cellwidth>-100; j++)
					{
						TableColumn spancolumn = (TableColumn) columns.get(j);
						int spancolumnwidth = spancolumn.getColumnWidth()
								.getValue();
						cellwidth = cellwidth - spancolumnwidth;
						numbercolumnspan++;
					}
					break;
				}
				len = len + columnwidth;
			}
		}
		// 百分比长度时
		else
		{
			double len = 0;
			int columnsize = columns.size();
			double celllen = currentlen.doubleValue();
			for (int i = 0; i < columnsize; i++)
			{
				TableColumn column = (TableColumn) columns.get(i);
				double columnwidth = ((TableColLength) column.getColumnWidth())
						.getTableUnits();
				if (Math.abs(celllen - len) <PercentLength.PRECISION)
				{
					columnnumber = i + 1;
					double cellwidth = width.doubleValue();
					cellwidth = cellwidth - columnwidth;
					for (int j = i + 1; j < columnsize
							&& cellwidth > PercentLength.PRECISION
							&& cellwidth > -PercentLength.PRECISION; j++)
					{
						TableColumn spancolumn = (TableColumn) columns.get(j);
						double spancolumnwidth = ((TableColLength) spancolumn
								.getColumnWidth()).getTableUnits();
						cellwidth = cellwidth - spancolumnwidth;
						numbercolumnspan++;
					}
					break;
				}
				len = len + columnwidth;
			}
		}
		Map<Integer, Object> cellmap = new HashMap<Integer, Object>();
		cellmap.put(Constants.PR_COLUMN_NUMBER, columnnumber);
		cellmap.put(Constants.PR_NUMBER_COLUMNS_SPANNED, numbercolumnspan);
		tablecell.setAttributes(cellmap, false);
	}
}
