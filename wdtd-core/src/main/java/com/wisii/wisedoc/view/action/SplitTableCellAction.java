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
 * @SplitTableCellAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
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
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.SplitDialog;

/**
 * 类功能描述：拆分单元格功能实现
 * 
 * 作者：zhangqiang 创建日期：2008-11-10
 */
public class SplitTableCellAction extends TableOperateAction
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

		List<TableCell> tablecells = getTableCells(getAllSelectObjects());
		int rowspan = -1;
		TableCell tablecell = tablecells.get(0);
		Integer rowSpan = (Integer) tablecell
				.getAttribute(Constants.PR_NUMBER_ROWS_SPANNED);
		if (rowSpan != null && rowSpan > 1)
		{
			rowspan = rowSpan;
		}
		SplitDialog dialog = new SplitDialog(rowspan);
		if (dialog.showDialog() == DialogResult.OK)
		{
			int rowsplit = dialog.getRow();
			int column = dialog.getCol();
			splitTableCell(tablecell, rowsplit, column);

		}

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
		// 只有一个单元格处于选中状态，则拆分操作可用
		if (tablecells.size() != 1)
		{
			return false;
		} else
		{
			return true;
		}
	}

	/**
	 * 
	 * 以指定拆分行数和列数来拆分单元格 拆分 处理过程： 1，判断是否选中了一个单元格 1.1 否，则不做操作 1.2
	 * 是，按照拆分行数和拆分列数拆分单元格
	 * 
	 * @param splitrow
	 *            ：要拆分的行数，splitcol：要拆分的列数
	 * @return
	 * @exception
	 */
	public void splitTableCell(TableCell tablecell, int splitrow, int splitcol)
	{
		int rowspan = tablecell.getNumberRowsSpanned();
		TableRow firstrow = (TableRow) tablecell.getParent();
		List<CellElement> tablecells = new ArrayList<CellElement>();
		tablecells.add(tablecell);
		TableBody tablebody = (TableBody) firstrow.getParent();
		Document doc = getCurrentDocument();
		if (doc == null)
		{
			return;
		}
		LengthRangeProperty oldwidth = tablecell
				.getInLineProgressionDimension();
		LengthRangeProperty newwidth = LengthRangeProperty.divideLength(
				oldwidth, splitcol);
		// 判断要拆分的单元格是否有合并
		// 2.1有合并时，处理如下
		if (rowspan > 1)
		{
			int newrowspan = rowspan / splitrow; // 新的行扩展数
			List<TableRow> rows = getRows(tablecell); // 需要修改的行
			List<CellElement> newcells = new ArrayList<CellElement>(); // 新单元格
			boolean flag = false; // 用于判断是否新行开始
			Map<Integer, Object> attmap = new HashMap<Integer, Object>();
			attmap.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, newwidth);
			attmap.put(Constants.PR_NUMBER_ROWS_SPANNED, newrowspan);
			Map<Integer, Object> tablecellatt = tablecell.getAttributes().getAttributes();
			for (int i = 0; i < rows.size(); i++)
			{
				flag = ((i % newrowspan) == 0); // 判断是否是新的开始行
				TableRow row = rows.get(i);
				// 从本行中删除旧单元格
				int colindex = row.getIndex(tablecell); // 旧单元格在行中的位置
				if (flag) // 新的开始行,需要创建新单元格
				{
					newcells.clear();
					TableCell tc;
					if (i == 0)
					{
						tc = tablecell;
						doc.setElementAttributes(tc, attmap, false);
					} else
					{

						tc = new TableCell(tablecellatt);
						tc.setAttributes(attmap, false);

					}
					newcells.add(tc);
					for (int k = 1; k < splitcol; k++)
					{
						tc = new TableCell(tablecellatt);
						tc.setAttributes(attmap, false);
						newcells.add(tc);
					}
				}
				doc.deleteElements(tablecells, row);
				doc.insertElements(newcells, row, colindex);

			}
		}
		// 2.2 要拆分单元格没有行合并时
		else
		{
			TableRow row = firstrow; // 拆分单元格所在行
			LengthRangeProperty newrowheight = LengthRangeProperty
					.divideLength(row.getBlockProgressionDimension(), splitrow); // 新的行高
			// 拆分单元格所在列的列号
			int colindex = row.getIndex(tablecell); 
			// 拆分单元格所在行的行号
			int rowindex = tablebody.getIndex(row);
			List<CellElement> cells = row.getAllChildren(); 
			// 获得拆分单元格所在行的其他单元格
			cells.removeAll(tablecells);
			TableRow[] newrows = new TableRow[splitrow - 1]; // 添加的新行
			TableCell newcell;
			Map<Integer, Object> tcattmap = new HashMap<Integer, Object>();
			tcattmap.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, newwidth);

			// 如果有行方向上拆分
			if (splitrow > 1)
			{
				// 遍历设置拆分单元格所在行所有单元格的rowSpan
				for (int i = 0; i < cells.size(); i++)
				{
					TableCell temp = (TableCell) cells.get(i);
					int newrowspan = temp.getNumberRowsSpanned();
					newrowspan = newrowspan + splitrow - 1;
					Map<Integer, Object> attmap = new HashMap<Integer, Object>();
					attmap.put(Constants.PR_NUMBER_ROWS_SPANNED, newrowspan);
					doc.setElementAttributes(temp, attmap, false);
				}
				// 将拆分单元格所在行的其他单元格添加到新行中
				List<CellElement> rowelements = new ArrayList<CellElement>();
				for (int i = 0; i < newrows.length; i++)
				{
					newrows[i] = new TableRow(row.getAttributes().getAttributes());
					newrows[i].setAttribute(
							Constants.PR_BLOCK_PROGRESSION_DIMENSION,
							newrowheight);
					rowelements.add(newrows[i]);
					newrows[i].insert(cells, 0);
					for (int j = 0; j < splitcol; j++)
					{
						newcell = new TableCell(tablecell.getAttributes().getAttributes());
						newcell.setAttributes(tcattmap, false);
						List<CellElement> ntcells = new ArrayList<CellElement>();
						ntcells.add(newcell);
						newrows[i].insert(ntcells, colindex + j);
					}
				}
				doc.insertElements(rowelements, tablebody, rowindex + 1);
			}

			// 再将拆分单元格添加回原行中
			Map<Integer, Object> rowattmap = new HashMap<Integer, Object>();
			rowattmap.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
					newrowheight);
			doc.setElementAttributes(row, rowattmap, false);
			doc.setElementAttributes(tablecell, tcattmap, false);
			// 向拆分单元格所在行中添加新单元格
			for (int i = 1; i < splitcol; i++)
			{
				newcell = new TableCell(tablecell.getAttributes().getAttributes());
				newcell.setAttributes(tcattmap, false);
				List<CellElement> ntcells = new ArrayList<CellElement>();
				ntcells.add(newcell);
				doc.insertElements(ntcells, row, colindex + i);
			}
		}
	}

	private List<TableRow> getRows(TableCell tablecell)
	{
		List<TableRow> list = new ArrayList<TableRow>();
		if (tablecell != null)
		{
			TableRow firstrow = (TableRow) tablecell.getParent();
			if (firstrow != null)
			{
				list.add(firstrow);
				Integer rowspano = tablecell.getNumberRowsSpanned();
				if (rowspano > 1)
				{
					TableBody tablebody = (TableBody) firstrow.getParent();
					if (tablebody != null)
					{
						int firstrowindex = tablebody.getIndex(firstrow);
						if (firstrowindex > -1)
						{
							for (int i = 1; i < rowspano; i++)
							{
								TableRow tablerow = (TableRow) tablebody
										.getChildAt(firstrowindex + i);
								if (tablerow != null)
								{
									list.add(tablerow);
								}
							}
						}
					}
				}
			}
		}
		return list;
	}

}
