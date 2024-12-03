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
 * @DeleteTableCellAction.java
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

/**
 * 类功能描述：删除单元格事件类
 * 
 * 作者：zhangqiang 创建日期：2008-11-12
 */
public class DeleteTableCellAction extends OnlyInOneTableCellAction
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
		List<CellElement> todeletes = new ArrayList<CellElement>();
		todeletes.add(tablecell);
		Document doc = getCurrentDocument();
		int rowspan = tablecell.getNumberRowsSpanned();
		TableRow row = (TableRow) tablecell.getParent();
		int childindex = row.getIndex(tablecell);
		if (rowspan > 1)
		{
			TableBody tbody = (TableBody) row.getParent();
			int index = row.getParent().getIndex(row);
//			如果单元格跨多行，则需要在所有行中删除该单元格
			for (int i = 0; i < rowspan; i++)
			{
				doc.deleteElements(todeletes, tbody.getChildAt(index + i));
			}

		} else
		{
			doc.deleteElements(todeletes, row);
		}
		if(childindex>0)
		{
			TableCell formatecell = (TableCell) row.getChildAt(childindex - 1);
			int crowspan = formatecell.getNumberRowsSpanned();
			if (crowspan > 1)
			{
				TableBody body = (TableBody) row.getParent();
				List<TableRow> formaterows = new ArrayList<TableRow>();
				TableRow formaterow = (TableRow) formatecell.getParent();
				int rowindex = body.getIndex(formaterow);
				for (int i = 0; i < crowspan; i++)
				{
					formaterows.add((TableRow) body.getChildAt(rowindex + i));
				}
				formateTableRows(formaterows);
			}
		}
		getEditorPanel().getSelectionModel().clearObjectSelection();
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
	public boolean isAvailable()
	{
		boolean isvailable = super.isAvailable();
		if (!isvailable)
		{
			return false;
		}
		if (tablecell != null)
		{
			TableRow tablerow = (TableRow) tablecell.getParent();
			if (tablerow != null)
			{
				int tablecellindex = tablerow.getIndex(tablecell);
				int tablecellrowspan = tablecell.getNumberRowsSpanned();
				int size = tablerow.getChildCount();
				// 遍历其后的单元格，如果其后面的单元格的行合并数和该单元格的行合并数不相等，则不可删除
				for (int i = tablecellindex + 1; i < size; i++)
				{
					TableCell temp = (TableCell) tablerow.getChildAt(i);
					if (temp.getNumberRowsSpanned() != tablecellrowspan)
					{
						return false;
					}
					else
					{
						if(tablerow!=temp.getParent())
						{
							return false;
						}
					}
				}
				//12|
				//12-这种情况下1或2（1和2分别跨2行，但2的第二行中有个单元格）这样的单元格也不能删除
				if(tablecellrowspan>1)
				{
					TableBody body = (TableBody) tablerow.getParent();
					if (body == null)
					{
						return false;
					}
					int rowindex = body.getIndex(tablerow);
					int rowsize = body.getChildCount();
					for (int i = rowindex + 1; i < rowsize; i++)
					{
						TableRow nextrow = (TableRow) body.getChildAt(i);
						if (nextrow.getChildCount() - 1 != nextrow
								.getIndex(tablerow.getChildAt(tablerow
										.getChildCount() - 1)))
						{
							return false;
						}
					}
				}
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}
}
