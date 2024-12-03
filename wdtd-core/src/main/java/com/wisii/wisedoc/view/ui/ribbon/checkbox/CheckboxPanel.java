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
 * @RadioAndCheckboxPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.checkbox;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.CheckBox;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：单选和复选按钮属性设置面板
 * 
 * 作者：zhangqiang 创建日期：2009-7-13
 */
class CheckboxPanel extends JPanel
{

	CheckboxPanel()
	{
		setLayout(new GridLayout(2, 1));
		JPanel valuepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel valuelabel = new JLabel(RibbonUIText.CHECHBOX_SELECTVALUE);
		WiseTextField valuetext = new WiseTextField(6);
		valuepanel.add(valuelabel);
		valuepanel.add(valuetext);
		RibbonUIManager.getInstance().bind(CheckBox.CHECKBOX_SELECT_ACTION,
				valuetext);
		JLabel unselectvaluelabel = new JLabel(
				RibbonUIText.CHECHBOX_UNSELECTVALUE);
		WiseTextField unselectvaluetext = new WiseTextField(6);
		valuepanel.add(unselectvaluelabel);
		valuepanel.add(unselectvaluetext);
		RibbonUIManager.getInstance().bind(CheckBox.CHECKBOX_UNSELECT_ACTION,
				unselectvaluetext);
		JCheckBox selectbox = new JCheckBox(RibbonUIText.CHECHBOX_SELECTED);
		RibbonUIManager.getInstance().bind(CheckBox.CHECKBOX_ISSELECTED_ACTION,
				selectbox);
		valuepanel.add(selectbox);
		JPanel boxstylepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel boxstylelabel = new JLabel(RibbonUIText.CHECHBOX_BOXSTYLE);
		WiseCombobox boxstylebox = new WiseCombobox(new String[]
		{ RibbonUIText.CHECHBOX_BOXSTYLE_SQUARE,
				RibbonUIText.CHECHBOX_BOXSTYLE_CIRCLE });
		boxstylepanel.add(boxstylelabel);
		boxstylepanel.add(boxstylebox);
		RibbonUIManager.getInstance().bind(CheckBox.CHECKBOX_BOXSTYLE_ACTION,
				boxstylebox);
		WiseCombobox boxstylelayer = new WiseCombobox(new DefaultComboBoxModel(
				UiText.COMMON_COLOR_LAYER));
		RibbonUIManager.getInstance().bind(
				CheckBox.CHECKBOX_BOXSTYLE_LAYER_ACTION, boxstylelayer);
		boxstylepanel.add(boxstylelayer);
		JLabel tickMarklabel = new JLabel(RibbonUIText.CHECHBOX_TICKMARK);
		WiseCombobox tickMarkbox = new WiseCombobox(new String[]
		{ RibbonUIText.CHECHBOX_TICKMARK_TICK,
				RibbonUIText.CHECHBOX_TICKMARK_DOT });
		boxstylepanel.add(tickMarklabel);
		boxstylepanel.add(tickMarkbox);
		RibbonUIManager.getInstance().bind(CheckBox.CHECKBOX_TICKMARK_ACTION,
				tickMarkbox);
		add(boxstylepanel);
		add(valuepanel);
	}
}
