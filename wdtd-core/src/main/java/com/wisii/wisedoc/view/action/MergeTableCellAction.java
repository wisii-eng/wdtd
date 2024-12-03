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
 * @MergeTableCellAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;

/**
 * 类功能描述：合并单元格
 * 
 * 作者：zhangqiang 创建日期：2008-11-6
 */
public class MergeTableCellAction extends TableOperateAction
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent
	 * )
	 */
	public void doAction(ActionEvent e)
	{
		List<CellElement> cells = getAllSelectObjects();
		List<TableCell> tablecells = getTableCells(cells);
		Collections.sort(tablecells, new TableCellComparator());
		int size = tablecells.size();
		// 操作的单元格所在表格行
		List<TableRow> rows = new ArrayList<TableRow>();
		// 操作的单元格所在表格行中第一个选中单元格的索引号
		TableRow firstrow = null;
		// 选中的单元格所在的第一行中选中的单元格
		List<TableCell> firstrowtcs = new ArrayList<TableCell>();
		Map<TableRow, List<CellElement>> rowtablecells = new HashMap<TableRow, List<CellElement>>();
		TableBody tbody = (TableBody) tablecells.get(0).getParent().getParent();
		// 记录所有单元格的内容，单元格合并后，单元格的内容也合并到了合并后的单元格中去
		List<CellElement> tcellcontents = new ArrayList<CellElement>();
		for (int i = 0; i < size; i++)
		{
			TableCell tablecell = tablecells.get(i);
			int childcount = tablecell.getChildCount();
			if (childcount > 0)
			{
				for (int j = 0; j < childcount; j++)
				{
					tcellcontents.add(tablecell.getChildAt(j));
				}
			}
			TableRow tablerow = (TableRow) tablecell.getParent();
			if (i == 0)
			{
				firstrow = tablerow;
			}
			// 找到选中的第一行的所有单元格
			if (tablerow.equals(firstrow))
			{
				firstrowtcs.add(tablecell);
			} else
			{
				// nothing
			}
			int rowspan = tablecell.getNumberRowsSpanned();
			if (!rows.contains(tablerow))
			{
				rows.add(tablerow);
				List<CellElement> elements = new ArrayList<CellElement>();
				elements.add(tablecell);
				rowtablecells.put(tablerow, elements);
			} else
			{
				List<CellElement> list = rowtablecells.get(tablerow);
				list.add(tablecell);
				rowtablecells.put(tablerow, list);
			}
			if (rowspan > 1)
			{
				int rowindex = tbody.getIndex(tablerow);
				for (int j = 1; j < rowspan; j++)
				{
					TableRow trow = (TableRow) tbody.getChildAt(rowindex + j);
					{
						if (!rows.contains(trow))
						{
							rows.add(trow);
							List<CellElement> elements = new ArrayList<CellElement>();
							elements.add(tablecell);
							rowtablecells.put(trow, elements);
						} else
						{
							List<CellElement> list = rowtablecells.get(trow);
							list.add(tablecell);
							rowtablecells.put(trow, list);
						}
					}
				}
			}
		}
		// 合并后的单元格宽
		// 以第一行的所有选中单元格的宽度之和作为合并后的单元格宽度
		LengthRangeProperty cellwidth = getLength(firstrowtcs);
		int selectrowsize = rows.size();
		// 生成合并后的单元格
		TableCell mergedtablecell = createMergedtablecell(tablecells.get(0),
				selectrowsize, cellwidth, tcellcontents);
		Document doc = getCurrentDocument();
		List<CellElement> tcells = new ArrayList<CellElement>();
		tcells.add(mergedtablecell);

		// 添加合并后的单元格
		for (int i = 0; i < selectrowsize; i++)
		{
			final TableRow row = rows.get(i);
			List<CellElement> elements = rowtablecells.get(row);
			Collections.sort(elements, new Comparator<CellElement>()
			{
				public int compare(CellElement o1, CellElement o2)
				{
					int index1 = row.getIndex(o1);
					int index2 = row.getIndex(o2);
					if (index1 == index2)
					{
						return 0;
					} else if (index1 > index2)
					{
						return 1;
					} else
					{
						return -1;
					}
				}
			});
			int index = row.getIndex(elements.get(0));
			doc.insertElements(tcells, row, index);
			// 删除已合并的单元格
			doc.deleteElements(elements, (CellElement) row);
		}
		// 格式化合并操作后的表格行，使得只有一个单元格的连续的行合并为一行
		formateTableRows(getTableRow(mergedtablecell));
		//选中当前合并后的单元格
		getEditorPanel().getSelectionModel().setObjectSelecttion(mergedtablecell);
	}

	/**
	 * {方法的功能/动作描述}
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	
	private List<TableRow> getTableRow(TableCell mergedtablecell)
	{
		TableRow row = (TableRow) mergedtablecell.getParent();
		List<TableRow> rows = new ArrayList<TableRow>();
		int rowspan = mergedtablecell.getNumberRowsSpanned();
		if (rowspan > 1)
		{
			TableBody tablebody = (TableBody) row.getParent();
			List<CellElement> tablerows = tablebody.getAllChildren();
			for (CellElement tablerow : tablerows)
			{
				if (tablerow.getIndex(mergedtablecell) > -1)
				{
					rows.add((TableRow) tablerow);
				}
			}
		}
		return rows;
	}

	private void formateTableRows(List<TableRow> rows)
	{
		Document doc = getCurrentDocument();
		if (rows != null && !rows.isEmpty()&&rows.size()>1)
		{
			int size = rows.size();
			// 收集只有一个单元格的行
			for (int i = 0; i < size;)
			{
				TableRow tr = rows.get(i);
				//找到当前行中公共的跨行的单元格
				List<CellElement> tablecells = tr.getAllChildren();
				int toincrease = 1;
				int tablecellsize = tablecells.size(); 
				int minspanrow = size-i;
				for(int j=0;j<tablecellsize;j++)
				{
					TableCell tablecell = (TableCell) tablecells.get(j);
					int rowspan =  getrowspan(tr,tablecell); 
					if(rowspan<minspanrow)
					{
						minspanrow = rowspan;
					}
					if(minspanrow<=1)
					{
						break;
					}
				}
				//往后遍历该单元格之后的表格行，确保该单元格后在其他行中没有其之后的单元格，
				//如如下情况下删除最后一个单元格
				//|||
				//|||-
				if(minspanrow>1)
				{
					for (int k = minspanrow - 1; k > 0; k--)
					{
						TableRow nexttr = rows.get(i + k);
						int index = nexttr.getIndex(tablecells.get(tablecells
								.size() - 1));
						if (index < 0)
						{
							break;
						} else
						{
							if (index != nexttr.getChildCount() - 1)
							{
								minspanrow--;
							} else
							{
								break;
							}
						}
					}
				}
				if(minspanrow>1)
				{
					toincrease = minspanrow;
					for(int j=0;j<tablecellsize;j++)
					{
						TableCell tablecell = (TableCell) tablecells.get(j);
						int newrowspan = tablecell.getNumberRowsSpanned()-minspanrow+1; 
						Map<Integer,Object> tcattrs = new HashMap<Integer, Object>();
						tcattrs.put(Constants.PR_NUMBER_ROWS_SPANNED, newrowspan);
						// 设置单元新的rowspan属性
						doc.setElementAttributes(tablecell, tcattrs, false);
					}
					List<CellElement> toMergerow = new ArrayList<CellElement>();
					for(int k=i+1;k<i+minspanrow;k++)
					{
						toMergerow.add(rows.get(k));
					}
					if (!toMergerow.isEmpty())
					{
						doc.deleteElements(toMergerow,(CellElement) rows.get(0).getParent());
					}
				}
				i+=toincrease;
			}
			
		}
	}

	/**
	 * {方法的功能/动作描述}
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	
	private int getrowspan(TableRow tr, TableCell tablecell)
	{
		int rowspan = tablecell.getNumberRowsSpanned();
		if (rowspan > 1)
		{
			TableBody tablebody = (TableBody) tr.getParent();
			for (CellElement tablerow : tablebody.getAllChildren())
			{
				if (tablerow == tr)
				{
					break;
				}
				if (tablerow.getIndex(tablecell) > -1)
				{
					rowspan--;
				}
			}
			return rowspan;
		}
		return 1;
	}
	private TableCell createMergedtablecell(TableCell oldtclell,
			int selectrowsize, LengthRangeProperty cellwidth,
			List<CellElement> tcellcontents)
	{
		TableCell tablecell = new TableCell(oldtclell.getAttributes()
				.getAttributes());
		tablecell.insert(tcellcontents, 0);
		Map<Integer, Object> att = new HashMap<Integer, Object>();
		if (selectrowsize > 1)
		{
			att.put(Constants.PR_NUMBER_ROWS_SPANNED, selectrowsize);
		}
		att.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, cellwidth);
		tablecell.setAttributes(att, false);
		return tablecell;
	}

	private LengthRangeProperty getLength(List<TableCell> rowtcs)
	{
		LengthRangeProperty len = null;
		if (rowtcs != null && !rowtcs.isEmpty())
		{
			int size = rowtcs.size();
			LengthRangeProperty[] celllens = new LengthRangeProperty[size];
			for (int i = 0; i < size; i++)
			{
				celllens[i] = rowtcs.get(i).getInLineProgressionDimension();
			}
			len = LengthRangeProperty.combinationLength(celllens);
		}
		return len;
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}

		List<CellElement> cells = getAllSelectObjects();
		// 判断所有的选中对象是否都是表格对象选中，如果有其他对象处于选中，则合并也不可用
		if (!isAllTableObject(cells))
		{
			return false;
		}
		List<TableCell> tablecells = getTableCells(cells);
		// 只有一个单元格,则返回false
		if (tablecells.size() == 1)
		{
			return false;
		}
		// 判断所有的单元格是否在同一TableBody级别的区域内， 即如果同时选择
		// 的当前表格的单元格和其嵌套表格内的单元格
		// 则也不容许合并
		if (!isAllInOneTableBody(tablecells))
		{
			return false;
		}
		return isSelectedInRectangle(tablecells);

	}

	private boolean isSelectedInRectangle(List<TableCell> tablecells)
	{
		if (tablecells != null & !tablecells.isEmpty())
		{
			int minrow = Integer.MAX_VALUE;
			int mincolumn = Integer.MAX_VALUE;
			int maxrow = -1;
			int maxcolumn = -1;
			int area = 0;
			for (TableCell tablecell : tablecells)
			{
				TableRow row = (TableRow) tablecell.getParent();
				if(row==null)
				{
					return false;
				}
				int columnindex = tablecell.getColumnNumber();
				if (mincolumn > columnindex)
				{
					mincolumn = columnindex;
				}
				if(row.getParent()==null)
				{
					return false;
				}
				int rowindex = row.getParent().getIndex(row);
				if (minrow > rowindex)
				{
					minrow = rowindex;
				}
				int rowspan = tablecell.getNumberRowsSpanned();
				int columnspan = tablecell.getNumberColumnsSpanned();
				area = area + rowspan * columnspan;
				int rowmaxindex = rowindex + rowspan;
				int colmaxindex = columnindex + columnspan;
				if (maxrow < rowmaxindex)
				{
					maxrow = rowmaxindex;
				}
				if (maxcolumn < colmaxindex)
				{
					maxcolumn = colmaxindex;
				}
			}
			return area == (maxrow - minrow) * (maxcolumn - mincolumn);
		}
		return false;
	}

	/*
	 * 
	 * 类功能描述：
	 * 
	 * 作者：zhangqiang 创建日期：2008-11-7
	 */
	private class TableCellComparator implements Comparator<TableCell>
	{
		public int compare(TableCell tc1, TableCell tc2)
		{
			// 如果tc1为空
			if (tc1 == null)
			{
				// 如果都为空，则返回相等
				if (tc2 == null)
				{
					return 0;
				}
				// 如果tc2不为空
				else
				{
					return -1;
				}
			}
			// 如果tc1不为空
			else
			{
				// 如果tc2不为空
				if (tc2 == null)
				{
					return 1;
				}
				// 如果tc1，tc2均不为空
				else
				{
					if (tc1.equals(tc2))
					{
						return 0;
					}
					// tc1所在的行
					TableRow tc1row = (TableRow) tc1.getParent();
					if (tc1row != null)
					{
						TableRow tc2row = (TableRow) tc2.getParent();
						if (tc2row != null)
						{

							int tc1rowindex = tc1row.getParent().getIndex(
									tc1row);
							int tc2rowindex = tc2row.getParent().getIndex(
									tc2row);
							if (tc1rowindex > tc2rowindex)
							{
								return 1;
							} else if (tc1rowindex < tc2rowindex)
							{
								return -1;
							} else
							{
								int tc1columnindex = tc1row.getIndex(tc1);
								int tc2columnindex = tc2row.getIndex(tc2);
								return tc1columnindex > tc2columnindex ? 1 : -1;
							}

						} else
						{
							return 1;
						}
					} else
					{
						TableRow tc2row = (TableRow) tc2.getParent();
						if (tc2row == null)
						{
							return 0;
						} else
						{
							return -1;
						}
					}
				}
			}
		}

	}
}
