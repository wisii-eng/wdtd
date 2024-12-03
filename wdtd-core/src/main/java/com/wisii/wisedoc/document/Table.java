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
 * @Table.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.document.attribute.LengthPairProperty;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.TableColLength;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.document.datatype.PercentBase;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-18
 */
public class Table extends TableFObj implements Groupable
{
	private transient int columnIndex = 1;

	private transient BitSet usedColumnIndices = new BitSet();
	/* 【添加：START】by 李晓光2009-1-16  */
	private transient Block area = null;
	private transient CommonBorderPaddingBackground commonbpbackground;
	private transient CommonMarginBlock commonmarginblock;
	private List<TableColumn> columns;
	private TableHeader tableHeader;
	private TableFooter tableFooter;
	//标示第一个单元格，用于定位光标。
	private transient TableCell firstCell = null;
	public Block getArea() {
		return area;
	}

	public void setArea(Block area) {
		this.area = area;
	}
	public TableCell getFirstCell(){
		return this.firstCell;
	}
	/* 【添加：END】by 李晓光2009-1-16  */
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public Table()
	{
		// TODO Auto-generated constructor stub
		this(null);
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public Table(final Map<Integer,Object> attributes)
	{
		super(attributes);
	}

	/**
	 * 
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public Table(final boolean hasheader, final boolean hasfooter, final int row, final int column,
			final Map<Integer, Object> attributes)
	{
		this(attributes);
		init(hasheader, hasfooter, row, column, null, null);
	}

	/**
	 * 
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public Table(final boolean hasheader, final boolean hasfooter, final int row, final int column,
			final Map<Integer, Object> attributes, final LengthProperty colwidth,
			final LengthProperty rowheight)
	{
		this(attributes);
		init(hasheader, hasfooter, row, column, colwidth, rowheight);
	}

	private void init(boolean hasheader, boolean hasfooter, int row,
			int column, LengthProperty colwidth, LengthProperty rowheight)
	{
		if (colwidth == null)
		{
			colwidth = new PercentLength(1.0d/column, new LengthBase(
					LengthBase.TABLE_UNITS));
		}
		if (row < 1)
		{
			row = 1;
		}
		if (column < 1)
		{
			column = 1;
		}
		if (hasheader)
		{
			TableHeader header = new TableHeader();
			TableRow headerrow = new TableRow(rowheight);
			for (int i = 0; i < column; i++)
			{
				TableCell cell = new TableCell(colwidth);
				//【添加：START】 by 李晓光 	2010-1-27
				if(firstCell == null){
					firstCell = cell;
				}
				//【添加：END】 by 李晓光 	2010-1-27
				headerrow.add(cell);
			}
			header.add(headerrow);
			add(header);
		}
		TableBody body = new TableBody();
		for (int i = 0; i < row; i++)
		{
			TableRow bodyrow = new TableRow(rowheight);
			for (int j = 0; j < column; j++)
			{
				TableCell cell = new TableCell(colwidth);
				//【添加：START】 by 李晓光 	2010-1-27
				if(firstCell == null){
					firstCell = cell;
				}
				//【添加：END】 by 李晓光 	2010-1-27
				bodyrow.add(cell);
			}
			body.add(bodyrow);
		}
		add(body);
		if (hasfooter)
		{
			TableFooter footer = new TableFooter();
			TableRow footerrow = new TableRow(rowheight);
			for (int i = 0; i < column; i++)
			{
				TableCell cell = new TableCell(colwidth);
				//【添加：START】 by 李晓光 	2010-1-27
				if(firstCell == null){
					firstCell = cell;
				}
				//【添加：END】 by 李晓光 	2010-1-27
				footerrow.add(cell);
			}
			footer.add(footerrow);
			add(footer);
		}
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
		if (commonmarginblock == null)
		{
			commonmarginblock = new CommonMarginBlock(this);
		} else
		{
			commonmarginblock.init(this);
		}
		reinitTable();
	}
	/**
	 * @see com.wisii.fov.fo.FONode#addChildNode(FONode)
	 */
	public void add(CellElement child)
	{
		if (child == null || !(child instanceof TableBody))
		{
			return;
		}

		if (child instanceof TableHeader)
		{
			// 判断是否已添加表头
			if (getTableHeader() != null)
			{
				return;
			}
		} else if (child instanceof TableFooter)
		{
			// 判断是否已添加表尾
			if (getTableFooter() != null)
			{
				return;
			}
		} else
		{
			// nothing
		}
		super.add(child);
	}

	public boolean isAutoLayout()
	{
		int tableLayout = ((EnumProperty) getAttribute(Constants.PR_TABLE_LAYOUT))
				.getEnum();
		return (tableLayout != Constants.EN_FIXED);
	}

	/** @return the default table column */
	public TableColumn getDefaultColumn()
	{
		return null;
	}

	/** @return the list of table-column elements. */
	public List getColumns()
	{
		return columns;
	}

	/**
	 * @param index
	 *            index of the table-body element.
	 * @return the requested table-body element
	 */
	public TableBody getBody(int index)
	{
		ListIterator it = getChildNodes();
		TableBody body = (TableBody) it.next();
		int i = 0;
		while (it.hasNext())
		{
			if (i++ == index && body.getClass() == TableBody.class)
			{
				return body;
			}
			body = (TableBody) it.next();
		}
		return null;
	}

	/* 【添加：START】 by 李晓光 2008-10-09 */
	/** 获得Table中包含的所有TableBody对象 */
	public ListIterator<TableBody> getTableBody()
	{
		ListIterator<TableFObj> it = getChildNodes();
		List<TableBody> bodys = new ArrayList<TableBody>();

		TableFObj body = null;
		while (it.hasNext())
		{
			body = it.next();
			if (!isNull(body) && (body.getClass() == TableBody.class))
			{
				bodys.add((TableBody) body);
			}
		}
		return bodys.listIterator();
	}

	/* 【添加：END】 by 李晓光 2008-10-09 */
	/** @return the body for the table-header. */
	public TableBody getTableHeader()
	{
		return tableHeader;
	}

	/** @return the body for the table-footer. */
	public TableBody getTableFooter()
	{
		return tableFooter;
	}

	/**
	 * 获得表中包含的所有行
	 * 
	 * @return {@link List} 返回表包含的所有TableRow
	 */
	public List<CellElement> getAllRows()
	{
		List<CellElement> rows = new ArrayList<CellElement>();
		rows.addAll(getRowsOfBody(getTableHeader()));
		ListIterator<TableBody> bodys = getTableBody();
		while (bodys.hasNext())
		{
			rows.addAll(getRowsOfBody(bodys.next()));
		}
		rows.addAll(getRowsOfBody(getTableFooter()));

		return rows;
	}

	private List<? extends CellElement> getRowsOfBody(TableBody body)
	{
		if (isNull(body))
			return new ArrayList<CellElement>();
		return body.getAllChildren();
	}

	/** @return true if the table-header should be omitted at breaks */
	public boolean omitHeaderAtBreak()
	{
		int tableOmitHeaderAtBreak = (Integer) getAttribute(Constants.PR_TABLE_OMIT_HEADER_AT_BREAK);
		return (tableOmitHeaderAtBreak == Constants.EN_TRUE);
	}

	/** @return true if the table-footer should be omitted at breaks */
	public boolean omitFooterAtBreak()
	{
		int tableOmitfooterAtBreak = (Integer) getAttribute(Constants.PR_TABLE_OMIT_FOOTER_AT_BREAK);
		return (tableOmitfooterAtBreak == Constants.EN_TRUE);
	}

	/**
	 * @return the "inline-progression-dimension" property.
	 */
	public LengthRangeProperty getInlineProgressionDimension()
	{
		return (LengthRangeProperty) getAttribute(Constants.PR_INLINE_PROGRESSION_DIMENSION);
	}

	/**
	 * @return the "block-progression-dimension" property.
	 */
	public LengthRangeProperty getBlockProgressionDimension()
	{
		return (LengthRangeProperty) getAttribute(Constants.PR_BLOCK_PROGRESSION_DIMENSION);
	}

	/**
	 * @return the Common Margin Properties-Block.
	 */
	public CommonMarginBlock getCommonMarginBlock()
	{
		return commonmarginblock;
	}

	/**
	 * @return the Common Border, Padding, and Background Properties.
	 */
	public CommonBorderPaddingBackground getCommonBorderPaddingBackground()
	{
		return commonbpbackground;
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

	/** @return the "keep-with-next" property. */
	public KeepProperty getKeepWithNext()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_WITH_NEXT);
	}

	/** @return the "keep-with-previous" property. */
	public KeepProperty getKeepWithPrevious()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_WITH_PREVIOUS);
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

	/** @return the "border-collapse" property. */
	public int getBorderCollapse()
	{
		EnumProperty p = (EnumProperty) getAttribute(Constants.PR_BORDER_COLLAPSE);
		return p.getEnum();
		/* return (Integer) getAttribute(Constants.PR_BORDER_COLLAPSE); */
	}

	/** @return true if the separate border model is active */
	public boolean isSeparateBorderModel()
	{
		return (getBorderCollapse() == Constants.EN_SEPARATE);
	}

	/** @return the "border-separation" property. */
	public LengthPairProperty getBorderSeparation()
	{
		return (LengthPairProperty) getAttribute(Constants.PR_BORDER_SEPARATION);
	}

	/** @see com.wisii.fov.fo.FONode#getLocalName() */
	public String getLocalName()
	{
		return "table";
	}

	/**
	 * @see com.wisii.fov.fo.FObj#getNameId()
	 */
	public int getNameId()
	{
		return Constants.FO_TABLE;
	}

	/**
	 * Returns the current column index of the Table
	 * 
	 * @return the next column number to use
	 */
	public int getCurrentColumnIndex()
	{
		return columnIndex;
	}

	/**
	 * Checks if a certain column-number is already occupied
	 * 
	 * @param colNr
	 *            the column-number to check
	 * @return true if column-number is already in use
	 */
	public boolean isColumnNumberUsed(int colNr)
	{
		return usedColumnIndices.get(colNr - 1);
	}

	/**
	 * Sets the current column index of the given Table (used by
	 * ColumnNumberPropertyMaker.make() in case the column-number was explicitly
	 * specified)
	 * 
	 * @param newIndex
	 *            the new value for column index
	 */
	public void setCurrentColumnIndex(int newIndex)
	{
		columnIndex = newIndex;
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
		// set index for the next column to use
		while (usedColumnIndices.get(columnIndex - 1))
		{
			columnIndex++;
		}
	}

	public boolean isGroupAble()
	{
		return true;
	}
	/**
	 * 该方法在表格的大小，跨行，跨列等属性变化时调用
	 */
	void reinitTable()
	{
		// 重新生成列信息
		reinitColumn();
		// 根据列信息生成单元格的跨行信息
		reinitTablePart();

	}

	/*
	 * 重新生成列信息，算法思路为遍历每一行中的单元格， 得到宽度信息，和已有宽度信息比较，已有宽度信息中不包括该
	 * 宽度时，则将该宽度信息添加到已有宽度信息的合适位置（保证宽度是从小到大排列的）
	 */
	private void reinitColumn()
	{
		tableHeader=null;
		tableFooter=null;
		Iterator<CellElement> tablebodyit = getChildren();
		List<Number> sortlengths = new LinkedList<Number>();
		PercentBase percentbase = null;
		while (tablebodyit.hasNext())
		{
			TableBody tablebody = (TableBody) tablebodyit
					.next();
			tablebody.initFOProperty();
			if (tablebody instanceof TableHeader)
			{
				tableHeader = (TableHeader) tablebody;
			} else if (tablebody instanceof TableFooter)
			{
				tableFooter = (TableFooter) tablebody;
			}
			Iterator<CellElement> rowsit = tablebody.getChildren();
			while (rowsit.hasNext())
			{
				TableRow row = (TableRow) rowsit.next();
				row.initFOProperty();
				Iterator<CellElement> tablecellit = row.getChildren();
				Number currentlen = 0;
				while (tablecellit.hasNext())
				{
					TableCell tablecell = (TableCell) tablecellit
							.next();
					Length width = tablecell.getInLineProgressionDimension().getOptimum(null);
					// 如果是百分比长度
					if (width instanceof PercentLength)
					{
						PercentLength perlen = (PercentLength) width;
						if (percentbase == null)
						{
							percentbase = perlen.getBaseLength();
						}
						currentlen = currentlen.doubleValue()
								+ perlen.value();
						addlenassort(sortlengths, currentlen);
					}
					// 否则是固定长度
					else
					{
						currentlen = currentlen.intValue()
								+ ((FixedLength) width).getValue();
						addlenassort(sortlengths, currentlen);
					}
				}
			}
		}
		columns = new ArrayList<TableColumn>();
		int size = sortlengths.size();
		// 如果percentbase不为null，证明单元格长度是百分比长度
		if (percentbase != null)
		{
			double oldlen = 0;
			
			for (int i = 0; i < size; i++)
			{
				double len = (Double) sortlengths.get(i);
				double width = len - oldlen;
				Map<Integer, Object> columnatt = new HashMap<Integer, Object>();
				TableColumn column = new TableColumn();
				columnatt.put(Constants.PR_COLUMN_WIDTH,
						new TableColLength(width,column));
				columnatt.put(Constants.PR_COLUMN_NUMBER,i + 1);
				column.setAttributes(columnatt, false);
				column.setParent(this);
				columns.add(column);
				column.initFOProperty();
				oldlen = len;
			}

		}
		// 是固定长度时
		else
		{
			int oldlen = 0;
			for (int i = 0; i < size; i++)
			{
				int len = (Integer) sortlengths.get(i);
				int width = len - oldlen;
				Map<Integer, Object> columnatt = new HashMap<Integer, Object>();
				columnatt.put(Constants.PR_COLUMN_WIDTH,
						new FixedLength(width));
				columnatt.put(Constants.PR_COLUMN_NUMBER,i + 1);
				TableColumn column = new TableColumn(columnatt);
				column.setParent(this);
				columns.add(column);
				column.initFOProperty();
				oldlen = len;
			}
		}
       //（表格中多加了一个列宽为0的列），这样在行中的单元格全部跨行时，添加一个指向该列的单元，
		//从而使得排版正确(因为现在的排版程序假定行中至少得包含一个不跨行的单元格)
		Map<Integer, Object> columnatt = new HashMap<Integer, Object>();
		columnatt.put(Constants.PR_COLUMN_WIDTH,
				new FixedLength(0));
		columnatt.put(Constants.PR_COLUMN_NUMBER,size + 1);
		TableColumn column = new TableColumn(columnatt);
		column.setParent(this);
		columns.add(column);
	}

	private void addlenassort(List<Number> sortlengths, Number currentlen)
	{
		// 如果长度列表中已包含该长度，则直接返回
		if (sortlengths.contains(currentlen))
		{
			return;
		}
		int size = sortlengths.size();
		int index = 0;
		// 否则，插入找到第一个小于当前长度的位置，新长度插入到该位置之后
		for (int i = size - 1; i >= 0; i--)
		{
			Number number = sortlengths.get(i);
			if(currentlen instanceof Integer)
			{
				int num = (Integer)number;
				int clen = (Integer)currentlen;
				//如果是mpt，则在一定误差范围以内，证明是相等的
				if(Math.abs(clen-num)<100)
				{
					return;
				}
				else if(num<clen)
				{
					index = i + 1;
					break;
				}
			}
			else
			{
				double num = (Double)number;
				double clen = (Double)currentlen;
				//如果是百分比，则在一定误差范围以内，证明是相等的
				if(Math.abs(clen-num)<PercentLength.PRECISION)
				{
					return;
				}
				else if(num<clen)
				{
					index = i + 1;
					break;
				}
			}
		}
		sortlengths.add(index, currentlen);
	}

	private void reinitTablePart()
	{
		Iterator<CellElement> childrenit = getChildren();
		TableBody lastbody = null;
		while (childrenit.hasNext())
		{
			TableBody tablebody = (TableBody) childrenit.next();
			tablebody.generateRowGroup();
			lastbody = tablebody;
		}
	}
}
