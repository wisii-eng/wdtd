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
 * @TextBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.wordarttext;


import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WordArtText;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：文字设置相关的属性板
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-15
 */
public class TextBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand textband = new JRibbonBand(
				RibbonUIText.RIBBON_WORDARTTEXT_TEXT, MediaResource
						.getResizableIcon("09379.ico"), null);
		WiseTextField contenttf = new WiseTextField(20);
		JRibbonComponent contentCom = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"),
				RibbonUIText.RIBBON_WORDARTTEXT_TEXT_CONTENT, contenttf);
		textband.addRibbonComponent(contentCom);
		FixedLengthSpinner spacespiner = new FixedLengthSpinner();
		JRibbonComponent spaceCom = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"),
				RibbonUIText.RIBBON_WORDARTTEXT_TEXT_LETTERSPACE, spacespiner);
		textband.addRibbonComponent(spaceCom);
		WiseCombobox alignmentbox = new WiseCombobox(new String[]
		{ RibbonUIText.RIBBON_WORDARTTEXT_TEXT_ALIGNMENT_LEFT,
				RibbonUIText.RIBBON_WORDARTTEXT_TEXT_ALIGNMENT_CENTER,
				RibbonUIText.RIBBON_WORDARTTEXT_TEXT_ALIGNMENT_RIGHT });
		JRibbonComponent alignCom = new JRibbonComponent(MediaResource
				.getResizableIcon("02285.ico"),
				RibbonUIText.RIBBON_WORDARTTEXT_TEXT_ALIGNMENT, alignmentbox);
		textband.addRibbonComponent(alignCom);
		RibbonUIManager.getInstance().bind(WordArtText.SET_TEXTCONTENT_ACTION,
				contenttf);
		RibbonUIManager.getInstance().bind(WordArtText.SET_LETTERSPACE_ACTION,
				spacespiner);
		RibbonUIManager.getInstance().bind(WordArtText.SET_ALIGNMENT_ACTION,
				alignmentbox);

		return textband;
	}

}
