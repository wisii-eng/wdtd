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
 * @SelectPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import org.jvnet.flamingo.common.JCommandButton;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Edit;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-7-13
 */
class SelectPanel extends JPanel
{
	SelectPanel()
	{
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		JPanel viewpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		JLabel typelabel = new JLabel(RibbonUIText.SELECT_TYPE);
		WiseCombobox typebox = new WiseCombobox(new String[]
		{ RibbonUIText.SELECT_TYPE_COMBOBOX, RibbonUIText.SELECT_TYPE_LIST });
		RibbonUIManager.getInstance()
				.bind(Edit.EDIT_SELECTTYPE_ACTION, typebox);
		JLabel linelabel = new JLabel(RibbonUIText.SELECT_LINES);
		WiseSpinner linespinner = new WiseSpinner(new SpinnerNumberModel(20, 5,
				1000, 5));
		viewpanel.add(typelabel);
		viewpanel.add(typebox);
		viewpanel.add(linelabel);
		viewpanel.add(linespinner);
		RibbonUIManager.getInstance().bind(Edit.EDIT_SELECTLINES_ACTION,
				linespinner);
		JPanel editpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		JCheckBox iseditradio = new JCheckBox(RibbonUIText.SELECT_ISEDIT);
		RibbonUIManager.getInstance().bind(Edit.EDIT_SELECTEDITABLE_ACTION,
				iseditradio);
		JCheckBox showListradio = new JCheckBox(RibbonUIText.SELECT_SHOWLIST);
		RibbonUIManager.getInstance().bind(Edit.EDIT_SELECTSHOWLIST_ACTION,
				showListradio);
		WiseSpinner multiplespinner = new WiseSpinner(new SpinnerNumberModel(1,
				1, 1000, 1));
		RibbonUIManager.getInstance().bind(Edit.EDIT_SELECTMULTIPLE_ACTION,
				multiplespinner);
		editpanel.add(iseditradio);
		editpanel.add(showListradio);
		editpanel.add(multiplespinner);
		JPanel namepanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		JLabel namelable = new JLabel(RibbonUIText.SELECT_NAME);
		WiseTextField nametext = new WiseTextField(8);
		RibbonUIManager.getInstance().bind(Edit.EDIT_SELECTNEXTNAME_ACTION,
				nametext);
		namepanel.add(namelable);
		namepanel.add(nametext);
		JPanel mainpanel = new JPanel(new GridLayout(3, 1));
		mainpanel.add(viewpanel);
		mainpanel.add(editpanel);
		mainpanel.add(namepanel);
		JCommandButton othersetbutton = new JCommandButton(
				RibbonUIText.SELECT_OTHER, MediaResource
						.getResizableIcon("09379.ico"));
		RibbonUIManager.getInstance().bind(Edit.EDIT_SELECTINFO_ACTION,
				othersetbutton);
		JPanel nextpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nextpanel.setBorder(new TitledBorder(RibbonUIText.SELECT_NEXT));
		JButton nextsetbutton = new JButton(RibbonUIText.SELECT_NEXT_SET);
		nextsetbutton.setMargin(new Insets(5, 2, 5, 2));
		JButton nextremovebutton = new JButton(RibbonUIText.SELECT_NEXT_REMOVE);
		nextremovebutton.setMargin(new Insets(5, 2, 5, 2));
		RibbonUIManager.getInstance().bind(Edit.EDIT_SELECTNEXT_ACTION,
				nextsetbutton);
		RibbonUIManager.getInstance().bind(Edit.REMOVE_SELECTNEXT_ACTION,
				nextremovebutton);
		nextpanel.add(nextsetbutton);
		nextpanel.add(nextremovebutton);
		add(mainpanel);
		add(othersetbutton);
		add(nextpanel);
	}
}
