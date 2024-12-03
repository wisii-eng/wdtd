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
 * @TableOperateAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableFObj;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 类功能描述：表格操作相关的事件类
 * 
 * 作者：zhangqiang 创建日期：2008-11-10
 */
public abstract class TableOperateAction extends Actions
{
	/*
	 * 
	 * 判断所有的对象是否都是表格相关的对象
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	protected boolean isAllTableObject(List<CellElement> cells)
	{
		if (cells == null || cells.isEmpty())
		{
			return false;
		} else
		{
			int size = cells.size();
			for (int i = 0; i < size; i++)
			{
				CellElement cell = cells.get(i);
				if (cell == null || !(cell instanceof TableFObj))
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * 判断所有的TableCell是否在同一个TableBody级别的区域上，
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	protected boolean isAllInOneTableBody(List<TableCell> tablecells)
	{

		if (tablecells != null && !tablecells.isEmpty())
		{
			TableBody tablebody = null;
			int size = tablecells.size();
			for (int i = 0; i < size; i++)
			{
				TableCell tcell = tablecells.get(i);
				TableFObj temptb = (TableFObj) tcell.getParent();
				// 向上遍历找到该tableCell所在的tableBody
				while (temptb != null && !(temptb instanceof TableBody))
				{
					temptb = (TableFObj) temptb.getParent();
				}
				if (temptb != null && (temptb instanceof TableBody))
				{
					if (tablebody == null)
					{
						tablebody = (TableBody) temptb;
					} else
					{
						// 如果新找到的TableBody和原来的TableBody不相等，则直接返回false。
						if (tablebody != temptb)
						{
							return false;
						}
					}
				}
			}
			return true;
		}

		return false;
	}

	/**
	 * 
	 * 获得所有选中的TableCell对象，如果选中的是行，则应该将行所包含的单元格取出来
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	protected List<TableCell> getTableCells(List<CellElement> cells)
	{
		List<TableCell> tablecells = new ArrayList<TableCell>();
		if (cells != null && !cells.isEmpty())
		{
			// 用Set主要是为了确保相同的对象只被加一次，这个主要是针对用跨行单元格的情况
			Set<TableCell> tcs = new HashSet<TableCell>();
			int size = cells.size();
			for (int i = 0; i < size; i++)
			{
				TableFObj tableobject = (TableFObj) cells.get(i);
				tcs.addAll(getTableCellsfromTableobject(tableobject));
			}
			tablecells.addAll(tcs);
		}
		return tablecells;

	}

	private Set<TableCell> getTableCellsfromTableobject(TableFObj tableobject)
	{
		List<TableCell> tablecells = new ArrayList<TableCell>();
		if (tableobject != null)
		{
			if (tableobject instanceof TableCell)
			{
				tablecells.add((TableCell) tableobject);
			} else
			{
				int size = tableobject.getChildCount();
				for (int i = 0; i < size; i++)
				{
					tablecells
							.addAll(getTableCellsfromTableobject((TableFObj) tableobject
									.getChildAt(i)));
				}
			}
		}
		Set<TableCell> set = new HashSet<TableCell>();
		// 确保相同的对象只被加入一次
		set.addAll(tablecells);
		return set;
	}
}
