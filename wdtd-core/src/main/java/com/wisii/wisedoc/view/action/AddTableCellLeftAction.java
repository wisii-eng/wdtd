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
 * @AddTableCellAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;

/**
 * 类功能描述：在当前单元格的左侧插入一新单元格
 *
 * 作者：zhangqiang
 * 创建日期：2009-2-9
 */
public  class AddTableCellLeftAction extends OnlyInOneTableCellAction
{
	public void doAction(ActionEvent e)
	{
		TableCell addcell = new TableCell(tablecell.getAttributes().getAttributes());
		Document doc = getCurrentDocument();
		int rowspan = tablecell.getNumberRowsSpanned();
		TableRow tr = (TableRow) tablecell.getParent();
		TableBody tb = (TableBody) tr.getParent();
		int trindex = tb.getIndex(tr);
		for (int i = 0; (i < rowspan) && (tr != null); i++)
		{
			int index = tr.getIndex(tablecell);
			List<CellElement> tablecells = new ArrayList<CellElement>();
			tablecells.add(addcell);
			doc.insertElements(tablecells, tr, index);
			tr = (TableRow) tb.getChildAt(trindex + i + 1);
		}
	}
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		int rowspan = tablecell.getNumberRowsSpanned();
		TableRow tr = (TableRow) tablecell.getParent();
		if(tr==null)
		{
			return false;
		}
		TableBody tb = (TableBody) tr.getParent();
		if(tb==null)
		{
			return false;
		}
		int trindex = tb.getIndex(tr);
		for (int i = 0; (i < rowspan) && (tr != null); i++)
		{
			int tcellsize = tr.getChildCount();
			for (int j = tr.getIndex(tablecell) + 1; j < tcellsize; j++)
			{
				TableCell aftercell = (TableCell) tr.getChildAt(j);
				// 如果之后单元格的父对象和选中单元格的父对象不相等
				if (aftercell.getParent() != tr)
				{
					return false;
				}
				int afterrowspan = aftercell.getNumberRowsSpanned();
				// 如果之后的单元格的跨的行比选中单元格的跨行多，则返回false。
				if (afterrowspan + i > rowspan)
				{
					return false;
				}
			}
			tr = (TableRow) tb.getChildAt(trindex + i + 1);
		}
		return true;
	}

}
