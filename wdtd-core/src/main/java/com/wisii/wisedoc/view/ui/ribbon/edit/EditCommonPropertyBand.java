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
 * @EditCommondSetBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Edit;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：编辑的通用属性设置界面
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-6
 */
public class EditCommonPropertyBand implements WiseBand
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand inputSetBand = new JRibbonBand(RibbonUIText.EDIT_COMMON,
				MediaResource.getResizableIcon("09379.ico"));
		WiseCombobox box = new WiseCombobox(new DefaultComboBoxModel(
				new String[]
				{ RibbonUIText.EDIT_TYPE_UNEDITABLE,
						RibbonUIText.EDIT_TYPE_INPUT,
						RibbonUIText.EDIT_TYPE_SELECT,
						RibbonUIText.EDIT_TYPE_DATE,
						"网页回填控件"}));
		JRibbonComponent typecom = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"), RibbonUIText.EDIT_TYPE, box);
		RibbonUIManager.getInstance().bind(Edit.EDIT_TYPE_ACTION, box);
		inputSetBand.addRibbonComponent(typecom);
		WiseTextField authoritytext = new WiseTextField(10);
		JRibbonComponent authoritycom = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"), RibbonUIText.EDIT_AUTHORITY,
				authoritytext);
		RibbonUIManager.getInstance().bind(Edit.EDIT_AUTHORITY_ACTION, authoritytext);
		inputSetBand.addRibbonComponent(authoritycom);
	//	JLabel hintlabel = new JLabel(RibbonUIText.EDIT_INFORMATION);
		WiseTextField hint = new WiseTextField(10);
		RibbonUIManager.getInstance().bind(Edit.EDIT_HINT_ACTION, hint);
		JRibbonComponent hintcom = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"), RibbonUIText.EDIT_INFORMATION,
				hint);
//		JCheckBox isreload = new JCheckBox();
//		JRibbonComponent isreloadcom = new JRibbonComponent(MediaResource
//				.getResizableIcon("02285.ico"), RibbonUIText.EDIT_ISREROAD,
//				isreload);
//		RibbonUIManager.getInstance().bind(Edit.EDIT_ISRELOAD_ACTION, isreload);
		inputSetBand.addRibbonComponent(hintcom);
		return inputSetBand;
	}
	
 
}
