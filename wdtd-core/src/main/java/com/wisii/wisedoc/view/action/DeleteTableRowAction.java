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
 * DeleteTableRowAction.java
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
 * 
 * 类功能描述：删除当前选中行
 * 
 * 作者：zhangqiang 创建日期：2008-11-11
 */
public class DeleteTableRowAction extends TableRowOperateAction
{

	/**
	 * 删除当前行
	 */
	public void doAction(ActionEvent e)
	{
		TableRow selectedtablerow = getSelectedRow();
		List<CellElement> cells = selectedtablerow.getAllChildren();
		Document doc = getCurrentDocument();
		TableBody tablebody = (TableBody) selectedtablerow.getParent();
		int size = cells.size();
		for (int i = 0; i < size; i++)
		{
			TableCell cell = (TableCell) cells.get(i);
			int rowspan = cell.getNumberRowsSpanned();
			// 如果删除的行的的单元格跨行，则跨行数需要因此减1
			if (rowspan > 1)
			{
				Map<Integer, Object> attmap = new HashMap<Integer, Object>();
				attmap.put(Constants.PR_NUMBER_ROWS_SPANNED, rowspan - 1);
				doc.setElementAttributes(cell, attmap, false);
			}
		}
		List<CellElement> rowtodelete = new ArrayList<CellElement>();
		rowtodelete.add(selectedtablerow);
		doc.deleteElements(rowtodelete, tablebody);
		getEditorPanel().getSelectionModel().clearObjectSelection();
	}
	public boolean isAvailable()
	{
		boolean isvisianle = super.isAvailable();
		if (!isvisianle)
		{
			return isvisianle;
		}
		TableRow tablerow =  getSelectedRow();
		TableBody tablebody = (TableBody) tablerow.getParent();
//		如果选中区域只有少于一行的表格行，则删除不可用
		if(tablebody == null||tablebody.getChildCount() < 2)
		{
			return false;
		}
		return true;
	}

}
