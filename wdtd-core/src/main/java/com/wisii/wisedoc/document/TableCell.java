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
 * @TableCell.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;

/**
 * 类功能描述：单元格属性
 * 
 * 作者：zhangqiang 创建日期：2008-8-18
 */
public class TableCell extends TableFObj
{

	protected int startOffset;

	/* 【添加：START】 by 李晓光 2008-10-24 */
	private List<Block> blocks = null;

	// 边框，背景，padding等属性
	private CommonBorderPaddingBackground commonbpbackground;

	private boolean isaddlast = false;

	public void addBlock(Block block)
	{
		if (blocks == null)
			blocks = new ArrayList<Block>();
		blocks.add(block);
	}

	public List<Block> getBlocks()
	{
		return blocks;
	}

	public void clearBlocks()
	{
		if (blocks == null || blocks.size() == 0)
			return;
		blocks.clear();
	}

	@Override
	public Area getArea()
	{
		if (blocks == null || blocks.size() == 0)
			return null;
		return blocks.get(0);
	}

	/* 【添加：END】 by 李晓光 2008-10-24 */
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public TableCell()
	{
		this((LengthProperty) null);
	}

	public TableCell(LengthProperty width)
	{
		if (width == null)
		{
			width = new FixedLength(6d, "cm");
		}
		LengthRangeProperty lenrange = new LengthRangeProperty(width);
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		// new FixedLength(6, "cm")
		map.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, lenrange);
		/* 边框样式 */
		map.put(Constants.PR_BORDER_BEFORE_STYLE, new EnumProperty(
				Constants.EN_SOLID, "SOLID"));
		map.put(Constants.PR_BORDER_AFTER_STYLE, new EnumProperty(
				Constants.EN_SOLID, "SOLID"));
		map.put(Constants.PR_BORDER_START_STYLE, new EnumProperty(
				Constants.EN_SOLID, "SOLID"));
		map.put(Constants.PR_BORDER_END_STYLE, new EnumProperty(
				Constants.EN_SOLID, "SOLID"));
		/* 边框宽度 */
		map.put(Constants.PR_BORDER_BEFORE_WIDTH, new CondLengthProperty(
				new FixedLength(0.5d, "pt"), true));
		map.put(Constants.PR_BORDER_AFTER_WIDTH, new CondLengthProperty(
				new FixedLength(0.5d, "pt"), true));
		map.put(Constants.PR_BORDER_START_WIDTH, new CondLengthProperty(
				new FixedLength(0.5d, "pt"), true));
		map.put(Constants.PR_BORDER_END_WIDTH, new CondLengthProperty(
				new FixedLength(0.5d, "pt"), true));
		/* 边框颜色 */
		map.put(Constants.PR_BORDER_BEFORE_COLOR, Color.black);
		map.put(Constants.PR_BORDER_AFTER_COLOR, Color.black);
		map.put(Constants.PR_BORDER_START_COLOR, Color.black);
		map.put(Constants.PR_BORDER_END_COLOR, Color.black);
		map.put(Constants.PR_PADDING_START, new CondLengthProperty(
				new FixedLength(1.9d, "mm", 2), false));
		map.put(Constants.PR_PADDING_END, new CondLengthProperty(
				new FixedLength(1.9d, "mm", 2), false));
		setAttributes(map, false);
	}

	public TableCell(LengthRangeProperty width)
	{
		if (width == null)
		{
			width = new LengthRangeProperty(new FixedLength(6d, "cm"));
		}
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		// new FixedLength(6, "cm")
		map.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, width);
		/* 边框样式 */
		map.put(Constants.PR_BORDER_BEFORE_STYLE, new EnumProperty(
				Constants.EN_SOLID, "SOLID"));
		map.put(Constants.PR_BORDER_AFTER_STYLE, new EnumProperty(
				Constants.EN_SOLID, "SOLID"));
		map.put(Constants.PR_BORDER_START_STYLE, new EnumProperty(
				Constants.EN_SOLID, "SOLID"));
		map.put(Constants.PR_BORDER_END_STYLE, new EnumProperty(
				Constants.EN_SOLID, "SOLID"));
		/* 边框宽度 */
		map.put(Constants.PR_BORDER_BEFORE_WIDTH, new CondLengthProperty(
				new FixedLength(1d, "pt"), true));
		map.put(Constants.PR_BORDER_AFTER_WIDTH, new CondLengthProperty(
				new FixedLength(1d, "pt"), true));
		map.put(Constants.PR_BORDER_START_WIDTH, new CondLengthProperty(
				new FixedLength(1d, "pt"), true));
		map.put(Constants.PR_BORDER_END_WIDTH, new CondLengthProperty(
				new FixedLength(1d, "pt"), true));
		/* 边框颜色 */
		map.put(Constants.PR_BORDER_BEFORE_COLOR, Color.red);
		map.put(Constants.PR_BORDER_AFTER_COLOR, Color.red);
		map.put(Constants.PR_BORDER_START_COLOR, Color.red);
		map.put(Constants.PR_BORDER_END_COLOR, Color.red);

		setAttributes(map, false);
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public TableCell(final Map<Integer, Object> attributes)
	{
		super(attributes);
		// TODO Auto-generated constructor stub
	}

	/** @see com.wisii.fov.fo.FObj#generatesReferenceAreas() */
	public boolean generatesReferenceAreas()
	{
		return true;
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

	/**
	 * Set position relative to table (set by body?)
	 * 
	 * @param offset
	 *            new offset
	 */
	public void setStartOffset(int offset)
	{
		startOffset = offset;
	}

	/**
	 * @return the Common Border, Padding, and Background Properties.
	 */
	public CommonBorderPaddingBackground getCommonBorderPaddingBackground()
	{
		return commonbpbackground;
	}

	/**
	 * @return the "column-number" property.
	 */
	public int getColumnNumber()
	{
		return (Integer) getAttribute(Constants.PR_COLUMN_NUMBER);
	}

	/** @return true if "empty-cells" is "show" */
	public boolean showEmptyCells()
	{
		int emptyCells = ((EnumProperty) getAttribute(Constants.PR_EMPTY_CELLS))
				.getEnum();
		return emptyCells == Constants.EN_SHOW;
	}

	/**
	 * @return the "number-columns-spanned" property.
	 */
	public int getNumberColumnsSpanned()
	{
		Integer numberColumnsSpanned = (Integer) getAttribute(Constants.PR_NUMBER_COLUMNS_SPANNED);
		return Math.max(numberColumnsSpanned, 1);
	}

	/**
	 * @return the "number-rows-spanned" property.
	 */
	public int getNumberRowsSpanned()
	{
		Integer numberRowsSpanned = (Integer) getAttribute(Constants.PR_NUMBER_ROWS_SPANNED);
		return Math.max(numberRowsSpanned, 1);
	}

	/**
	 * @return the "block-progression-dimension" property.
	 */
	public LengthRangeProperty getBlockProgressionDimension()
	{
		return (LengthRangeProperty) getAttribute(Constants.PR_BLOCK_PROGRESSION_DIMENSION);
	}

	/**
	 * @return the "inLine-progression-dimension" property.
	 */
	public LengthRangeProperty getInLineProgressionDimension()
	{
		return (LengthRangeProperty) getAttribute(Constants.PR_INLINE_PROGRESSION_DIMENSION);
	}

	/**
	 * @return the "width" property.
	 */
	public Length getWidth()
	{
		return (Length) getAttribute(Constants.PR_WIDTH);
	}

	/** @return the display-align property. */
	public int getDisplayAlign()
	{
		return ((EnumProperty) getAttribute(Constants.PR_DISPLAY_ALIGN))
				.getEnum();
	}

	/** @return true if the cell starts a row. */
	public boolean startsRow()
	{
		int startsRow = ((EnumProperty) getAttribute(Constants.PR_STARTS_ROW))
				.getEnum();
		return (startsRow == Constants.EN_TRUE);
	}

	/** @return true if the cell ends a row. */
	public boolean endsRow()
	{
		int endsRow = ((EnumProperty) getAttribute(Constants.PR_ENDS_ROW))
				.getEnum();
		return (endsRow == Constants.EN_TRUE);
	}

	/** @see com.wisii.fov.fo.FONode#getLocalName() */
	public String getLocalName()
	{
		return "table-cell";
	}

	/**
	 * @see com.wisii.fov.fo.FObj#getNameId()
	 */
	public final int getNameId()
	{
		return Constants.FO_TABLE_CELL;
	}

	public String getId()
	{
		if (getNumberRowsSpanned() == 1)
			return "";
		String id = super.getId();
		if (id == null)
		{
			id = "" + Integer.toHexString(hashCode());
		}
		return id;
	}

	public ListIterator getChildNodes()
	{
		List<CellElement> list = _children;
		// 如果block为空，则显示一换行符
		if ((_children == null || _children.isEmpty()) && !isaddlast)
		{
			com.wisii.wisedoc.document.Block block = new com.wisii.wisedoc.document.Block();
			EnumNumber precedence = new EnumNumber(-1, 0);
			EnumProperty conditionality = new EnumProperty(
					Constants.EN_DISCARD, "");
			block.setAttribute(Constants.PR_LINE_HEIGHT, new SpaceProperty(
					new PercentLength(1, new LengthBase(new FixedLength(
							6d * 1.2, "pt"), LengthBase.FONTSIZE)), precedence,
					conditionality));
			block.setParent(this);
			list = new ArrayList<CellElement>();
			list.add(block);

		}
		return list.listIterator();
	}

	/**
	 * @返回 isaddlast变量的值
	 */
	public boolean isIsaddlast()
	{
		return isaddlast;
	}

	/**
	 * @param isaddlast
	 *            设置isaddlast成员变量的值
	 * 
	 *            值约束说明
	 */
	void setIsaddlast(boolean isaddlast)
	{
		this.isaddlast = isaddlast;
	}

}
