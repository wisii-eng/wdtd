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
 * @CutAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Icon;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.wsd.ElementsWriter;
import com.wisii.wisedoc.swing.ClipboardSupport;
/**
 * 
 * 类功能描述：剪切事件类
 *
 * 作者：zhangqiang
 * 创建日期：2008-10-15
 */
public class CutAction extends BaseAction {
	/**
	 * 用默认的字符串和默认的图标定义一个Action对象。
	 */
	public CutAction() {
		super();
	}

	/**
	 * 
	 * 用指定描述字符串指定图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 * @param icon
	 *            指定Action的图标。
	 */
	public CutAction(String name, Icon icon) {
		super(name, icon);
	}

	/**
	 * 
	 * 用指定描述字符串和默认图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 */
	public CutAction(String name) {
		super(name);
	}

	/**
	 * 
	 * 剪切菜单Action接口方法实现
	 * 
	 * @param ActioinEvent
	 *            事件源对数据的封装。
	 * @return void 无返回值
	 */
	public void doAction(ActionEvent e)
	{
		List<CellElement> elements = getAllSelectObjects();
		Document doc = getCurrentDocument();
		if (doc != null && elements != null && !elements.isEmpty())
		{

			ClipboardSupport.writeToClipboard(elements);
			doc.deleteElements(elements);
			getEditorPanel().getSelectionModel().clearSelection();
		}
	}
	public boolean isAvailable()
	{
		if(!super.isAvailable())
		{
			return false;
		}
		return isSelected();
	}
}