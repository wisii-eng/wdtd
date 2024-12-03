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
 * @InputPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jvnet.flamingo.common.JCommandButton;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Edit;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：Input空间的属性设置面板
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-9
 */
class InputPanel extends JPanel
{
	InputPanel()
	{
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		JPanel mainpanel = new JPanel(new GridLayout(3, 1));
		JPanel typepanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
		JLabel typelabel = new JLabel(RibbonUIText.INPUT_TYPE);
		WiseCombobox typebox = new WiseCombobox(new DefaultComboBoxModel(
				new String[]
				{ RibbonUIText.INPUT_TYPE_TEXT,
						RibbonUIText.INPUT_TYPE_PASSWORD }));
		RibbonUIManager.getInstance().bind(Edit.EDIT_INPUTTYPE_ACTION, typebox);
		JCheckBox multibox = new JCheckBox(RibbonUIText.INPUT_MULTILINE);
		RibbonUIManager.getInstance().bind(Edit.EDIT_INPUTMULTILINE_ACTION,
				multibox);
		JCheckBox warpbox = new JCheckBox(RibbonUIText.INPUT_WARP);
		RibbonUIManager.getInstance().bind(Edit.EDIT_INPUTWRAP_ACTION,
				warpbox);
		typepanel.add(typelabel);
		typepanel.add(typebox);
		typepanel.add(multibox);
		typepanel.add(warpbox);
		JPanel filterpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
		JLabel filterlabel = new JLabel(RibbonUIText.INPUT_FILTER);
		WiseTextField filtertf = new WiseTextField(10);
		RibbonUIManager.getInstance().bind(Edit.EDIT_INPUTFILTER_ACTION,
				filtertf);
		JLabel filtermsglabel = new JLabel(RibbonUIText.INPUT_FILTERMSG);
		WiseTextField filtermsgtf = new WiseTextField(10);
		RibbonUIManager.getInstance().bind(Edit.EDIT_INPUTFILTERMSG_ACTION,
				filtermsgtf);
		filterpanel.add(filterlabel);
		filterpanel.add(filtertf);
		filterpanel.add(filtermsglabel);
		filterpanel.add(filtermsgtf);
		JPanel defaultpanel=new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
		JLabel defaultvaluelabel = new JLabel(RibbonUIText.INPUT_DEFALUTVALUE);
		WiseTextField defaultvaluetf = new WiseTextField(24);
		RibbonUIManager.getInstance().bind(Edit.EDIT_DEFAULT_VALUE_ACTION,
				defaultvaluetf);
		defaultpanel.add(defaultvaluelabel);
		defaultpanel.add(defaultvaluetf);
		mainpanel.add(typepanel);
		mainpanel.add(filterpanel);
		mainpanel.add(defaultpanel);
//		JCommandButton othersetbutton = new JCommandButton(
//				RibbonUIText.INPUT_DATASOURCE, MediaResource
//						.getResizableIcon("09379.ico"));
//		RibbonUIManager.getInstance().bind(Edit.EDIT_INPUTDATASOURCE_ACTION,
//				othersetbutton);
		add(mainpanel);
//		add(othersetbutton);
	}
}
