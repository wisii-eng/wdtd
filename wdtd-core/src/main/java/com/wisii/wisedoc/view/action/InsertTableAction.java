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
 * @InsertTableAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.CreateTableDialog;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 类功能描述：插入表格事件类
 * 
 * 作者：zhangqiang 创建日期：2008-10-7
 */
public class InsertTableAction extends InsertObjectAction
{
	//【添加：START】 by 李晓光 	2010-1-27
	private transient TableCell cell = null;
	//【添加：END】 by 李晓光 		2010-1-27
	protected List<CellElement> creatCells()
	{
		cell=null;
		List<CellElement> inserts = null;
		CreateTableDialog dialog = new CreateTableDialog();
		if (dialog.showDialog() == DialogResult.OK)
		{
			Table table = new Table(dialog.isHasHeader(), dialog.isHasFooter(),
					dialog.getRow(), dialog.getCol(), null, dialog
							.getColumnWidth(), dialog.getRowHeight());
			inserts = new ArrayList<CellElement>();
			inserts.add(table);
			//【添加：START】 by 李晓光 		2010-1-27
			cell = table.getFirstCell();
			//【添加：END】 by 李晓光 		2010-1-27
		}
		return inserts;
	}
	//【添加：START】 by 李晓光 	2010-1-27
	@Override
	public void doAction(ActionEvent e)
	{
		super.doAction(e);
		// 设置光标位置。
		if (cell != null)
		{
			DocumentPosition pos = new DocumentPosition(cell);
			RibbonUpdateManager.Instance.getCurrentEditPanel()
					.setCaretPosition(pos);
		}
	}
	//【添加：END】 by 李晓光 	2010-1-27
	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		DocumentPositionRange range = getSelect();
		if (range != null)
		{
			DocumentPosition startpos = range.getStartPosition();
			CellElement startleaf = startpos.getLeafElement();
			//如果当前选中范围的起始点在一个Inline重复组中，则不能插入表格
			if (startleaf instanceof Inline
					&& startleaf.getParent() instanceof Group)
			{
				return false;
			}
			DocumentPosition endpos = range.getEndPosition();
			CellElement endleaf = endpos.getLeafElement();
			//如果当前选中范围的结束点在一个Inline重复组中，则不能插入表格
			if (endleaf instanceof Inline
					&& endleaf.getParent() instanceof Group)
			{
				return false;
			}
			return true;
		}
		DocumentPosition pos = getCaretPosition();
		if (pos != null)
		{
			CellElement leaf = pos.getLeafElement();
			if (leaf instanceof Inline && leaf.getParent() instanceof Group)
			{
				return false;
			}
			return true;
		} else
		{
			List<CellElement> objselects = getObjectSelects();
			if (objselects != null && !objselects.isEmpty())
			{
				CellElement element = objselects.get(objselects.size() - 1);
				if (element instanceof AbstractGraphics)
				{
					element = (CellElement) element.getParent();
				}
				if (element instanceof Inline
						&& element.getParent() instanceof Group)
				{
					return false;
				}
				return true;
			}
			return false;
		}
	}
}
