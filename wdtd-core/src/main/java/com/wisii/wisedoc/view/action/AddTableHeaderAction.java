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
 * AddTableHeaderAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableHeader;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.attribute.Attributes;

/**
 * 
 * 类功能描述：添加表头事件项
 * 
 * 作者：zhangqiang 创建日期：2008-11-11
 */
public class AddTableHeaderAction extends OnlyInOneTableAction
{

	/**
	 * 添加表头
	 */
	public void doAction(ActionEvent e)
	{
		ListIterator<TableBody> bodys = table.getTableBody();
		// 找到该表格中第一个tablebody
		TableBody tablebody = null;
		if (bodys.hasNext())
		{
			tablebody = bodys.next();
		}
		Document doc = getCurrentDocument();
		if (tablebody != null)
		{
			TableRow firstrow = (TableRow) tablebody.getChildAt(0);
			List<CellElement> toaddedtablecells = new ArrayList<CellElement>();
			int size = firstrow.getChildCount();
			for (int i = 0; i < size; i++)
			{
				TableCell tablecell = (TableCell) firstrow.getChildAt(i);
				Attributes oldatt = tablecell.getAttributes();
				Map<Integer, Object> attmap = null;
				if (oldatt != null)
				{
					attmap = oldatt.getAttributes();
					if (attmap != null)
					{
						attmap.remove(Constants.PR_NUMBER_ROWS_SPANNED);
					}

				}
				TableCell newtablecell = new TableCell(attmap);
				toaddedtablecells.add(newtablecell);
			}
			TableRow tablerow = new TableRow(firstrow.getAttributes().getAttributes());
			tablerow.insert(toaddedtablecells, 0);
			TableHeader tableheader = new TableHeader();
			tableheader.add(tablerow);
			List<CellElement> toaddcells = new ArrayList<CellElement>();
			toaddcells.add(tableheader);
			doc.insertElements(toaddcells, table, 0);
		}

	}

	public boolean isAvailable()
	{
		boolean isvailable = super.isAvailable();
		if (!isvailable)
		{
			return false;
		}
		if (table != null && table.getTableHeader() == null)
		{
			return true;
		}
		return false;
	}
}
