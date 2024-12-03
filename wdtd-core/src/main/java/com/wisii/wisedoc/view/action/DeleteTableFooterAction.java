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
 * @DeleteTableFooterAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.TableFooter;

/**
 * 
 * 类功能描述：删除表尾
 *
 * 作者：zhangqiang
 * 创建日期：2008-11-11
 */
public class DeleteTableFooterAction  extends OnlyInOneTableAction
{

	/**
	 * 删除表尾
	 */
	public void doAction(ActionEvent e)
	{
		TableFooter footer = (TableFooter) table.getTableFooter();
		List<CellElement> todelete = new ArrayList<CellElement>();
		todelete.add(footer);
		Document doc = getCurrentDocument();
		doc.deleteElements(todelete,table);
		getEditorPanel().getSelectionModel().clearObjectSelection();
	}
	public boolean isAvailable()
	{
		boolean isvailable = super.isAvailable();
		if (!isvailable)
		{
			return false;
		}
		if (table != null && table.getTableFooter() != null)
		{
			return true;
		}
		return false;
	}
}
