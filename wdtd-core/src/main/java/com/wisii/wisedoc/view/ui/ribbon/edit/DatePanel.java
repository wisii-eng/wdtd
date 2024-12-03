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
 * @DatePanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Edit;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：日期控件属性设置面板
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-13
 */
class DatePanel extends JPanel
{
	DatePanel()
	{
		super(new FlowLayout(FlowLayout.LEFT));
		JLabel typelabel = new JLabel(RibbonUIText.DATE_TYPE);
		WiseCombobox typebox = new WiseCombobox(new DefaultComboBoxModel(
				new String[]
				{ RibbonUIText.DATE_TYPE_C, RibbonUIText.DATE_TYPE_T }));
		add(typelabel);
		add(typebox);
		RibbonUIManager.getInstance().bind(Edit.EDIT_DATETYPE_ACTION, typebox);
	}
}
