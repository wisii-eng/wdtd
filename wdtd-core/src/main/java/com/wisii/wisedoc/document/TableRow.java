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
 * @TableRow.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-28
 */
public class TableRow extends TableFObj implements Groupable
{

	protected BitSet usedColumnIndices;

	private int columnIndex = 1;
	// 边框，背景，padding等属性
	private CommonBorderPaddingBackground commonbpbackground;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public TableRow()
	{
		this((LengthProperty) null);
	}

	public TableRow(LengthProperty rowheight)
	{
		if (rowheight == null)
		{
			rowheight = (LengthProperty) InitialManager.getInitialValue(
					Constants.PR_FONT_SIZE, this);
			rowheight = new FixedLength(rowheight.getValue());
		}
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
				new LengthRangeProperty(rowheight));
		setAttributes(map, false);
	}

	public TableRow(LengthRangeProperty rowheight)
	{
		if (rowheight == null)
		{
			rowheight = new LengthRangeProperty(new FixedLength(3d, "cm"));
		}
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION, rowheight);
		setAttributes(map, false);
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public TableRow(final Map<Integer, Object> attributes)
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
	}

	/** @return the "break-after" property. */
	public int getBreakAfter()
	{
		return ((EnumProperty) getAttribute(Constants.PR_BREAK_AFTER))
				.getEnum();
	}

	/** @return the "break-before" property. */
	public int getBreakBefore()
	{
		return ((EnumProperty) getAttribute(Constants.PR_BREAK_BEFORE))
				.getEnum();
	}

	/** @return the "keep-with-previous" property. */
	public KeepProperty getKeepWithPrevious()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_WITH_PREVIOUS);
	}

	/** @return the "keep-with-next" property. */
	public KeepProperty getKeepWithNext()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_WITH_NEXT);
	}

	/** @return the "keep-together" property. */
	public KeepProperty getKeepTogether()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_TOGETHER);
	}

	/**
	 * Convenience method to check if a keep-together constraint is specified.
	 * 
	 * @return true if keep-together is active.
	 */
	public boolean mustKeepTogether()
	{
		return !getKeepTogether().getWithinPage().isAuto()
				|| !getKeepTogether().getWithinColumn().isAuto();
	}

	/**
	 * Convenience method to check if a keep-with-next constraint is specified.
	 * 
	 * @return true if keep-with-next is active.
	 */
	public boolean mustKeepWithNext()
	{
		return !getKeepWithNext().getWithinPage().isAuto()
				|| !getKeepWithNext().getWithinColumn().isAuto();
	}

	/**
	 * Convenience method to check if a keep-with-previous constraint is
	 * specified.
	 * 
	 * @return true if keep-with-previous is active.
	 */
	public boolean mustKeepWithPrevious()
	{
		return !getKeepWithPrevious().getWithinPage().isAuto()
				|| !getKeepWithPrevious().getWithinColumn().isAuto();
	}

	/**
	 * @return the "block-progression-dimension" property.
	 */
	public LengthRangeProperty getBlockProgressionDimension()
	{
		return (LengthRangeProperty) getAttribute(Constants.PR_BLOCK_PROGRESSION_DIMENSION);
	}

	/**
	 * @return the "height" property.
	 */
	public Length getHeight()
	{
		return (Length) getAttribute(Constants.PR_HEIGHT);
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
		return "table-row";
	}

	/** @see com.wisii.fov.fo.FObj#getNameId() */
	public int getNameId()
	{
		return Constants.FO_TABLE_ROW;
	}

	/**
	 * Returns the current column index of the TableRow
	 * 
	 * @return the next column number to use
	 */
	public int getCurrentColumnIndex()
	{
		return columnIndex;
	}

	/**
	 * Sets the current column index to a specific value in case a column-number
	 * was explicitly specified (used by ColumnNumberPropertyMaker.make())
	 * 
	 * @param newIndex
	 *            new value for column index
	 */
	public void setCurrentColumnIndex(int newIndex)
	{
		columnIndex = newIndex;
	}

	/**
	 * Checks whether a given column-number is already in use for the current
	 * row (used by TableCell.bind());
	 * 
	 * @param colNr
	 *            the column-number to check
	 * @return true if column-number is already occupied
	 */
	public boolean isColumnNumberUsed(int colNr)
	{
		return usedColumnIndices.get(colNr - 1);
	}

	/**
	 * @see com.wisii.fov.fo.flow.TableFObj#flagColumnIndices(int, int)
	 */
	protected void flagColumnIndices(int start, int end)
	{
		for (int i = start; i < end; i++)
		{
			usedColumnIndices.set(i);
		}
		// update columnIndex for the next cell
		while (usedColumnIndices.get(columnIndex - 1))
		{
			columnIndex++;
		}
	}

	@Override
	public ListIterator getChildNodes()
	{
		List<TableCell> tablecells = new ArrayList<TableCell>();
		if (_children != null && !_children.isEmpty())
		{
			int size = _children.size();
			for (int i = 0; i < size; i++)
			{
				TableCell tablecell = (TableCell) _children.get(i);
				int rowspan = tablecell.getNumberRowsSpanned();
				// 如果单元格跨行，且起始行不是当前行，则生成一空单元格
				if (rowspan == 1 || tablecell.getParent() == this)
				{

					tablecells.add(tablecell);
				}
			}
		}
		// 排版的时候，表格行中必须得至少有一个单元格，因此添加一个空单元格，且列宽为0（表格中多加了一个列宽为0的列）
		if (tablecells.isEmpty())
		{
			Map<Integer, Object> atts = new HashMap<Integer, Object>();
			atts.put(Constants.PR_COLUMN_NUMBER, ((Table) this.getParent()
					.getParent()).getColumns().size() + 1);
			TableCell tablecell = new TableCell(atts);
			tablecell.setParent(this);
			tablecell.setIsaddlast(true);
			tablecells.add(tablecell);
		} else
		{
			boolean isallrowspan = true;
			//表格行中如果全部是跨行的单元格，也需要添加空单元格，否则排版有问题
			for (TableCell tablecell : tablecells)
			{
				if (tablecell.getNumberRowsSpanned() == 1)
				{
					isallrowspan = false;
					break;
				}
			}
			if (isallrowspan)
			{
				Map<Integer, Object> atts = new HashMap<Integer, Object>();
				atts.put(Constants.PR_COLUMN_NUMBER, ((Table) this.getParent()
						.getParent()).getColumns().size() + 1);
				TableCell tablecell = new TableCell(atts);
				tablecell.setParent(this);
				tablecell.setIsaddlast(true);
				tablecells.add(tablecell);
			}
		}
		return tablecells.listIterator();
	}

	public boolean isGroupAble()
	{
		int size = getChildCount();
		for (int i = 0; i < size; i++)
		{
			TableCell tableCell = (TableCell) getChildAt(i);
			if ((tableCell == null) || tableCell.getNumberRowsSpanned() != 1)
			{
				return false;
			}
		}
		return true;
	}
}
