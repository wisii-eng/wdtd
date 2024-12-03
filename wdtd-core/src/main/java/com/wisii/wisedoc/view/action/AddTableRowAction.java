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
 * @AddTableRowAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

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
 * 类功能描述：添加行操作类
 * 
 * 作者：zhangqiang 创建日期：2008-11-11
 */
public abstract class AddTableRowAction extends TableRowOperateAction
{
	/*
	 * 
	 * 插入新行
	 * 
	 * @param isabove：标识实在当前行的上方添加行还是在下方添加行，true标识在上方添加行， false标识在下方添加行
	 * 
	 * @return
	 * 
	 * @exception
	 */
	protected void addTableRow(boolean isabove)
	{
		TableRow selectedtablerow = getSelectedRow();
		TableRow addrow = new TableRow(selectedtablerow.getAttributes().getAttributes());
		List<CellElement> selectcells = selectedtablerow.getAllChildren();
		int size = selectcells.size();
		Document doc = getCurrentDocument();
		List<CellElement> addcells = new ArrayList<CellElement>();
		for (int i = 0; i < size; i++)
		{
			TableCell cell = (TableCell) selectcells.get(i);
			int rowspan = cell.getNumberRowsSpanned();
			TableCell addcell;
			if (rowspan > 1)
			{
				Map<Integer, Object> attmap = new HashMap<Integer, Object>();
				attmap.put(Constants.PR_NUMBER_ROWS_SPANNED, rowspan + 1);
				doc.setElementAttributes(cell, attmap, false);
				addcell = cell;
			} else
			{
				addcell = new TableCell(cell.getAttributes().getAttributes());
			}
			addcells.add(addcell);
		}
		addrow.insert(addcells, 0);
		List<CellElement> rows = new ArrayList<CellElement>();
		rows.add(addrow);
		TableBody tablebody = (TableBody) selectedtablerow.getParent();
		int index = tablebody.getIndex(selectedtablerow);
		if (!isabove)
		{
			index++;
		}
		doc.insertElements(rows, tablebody, index);
	}
}
