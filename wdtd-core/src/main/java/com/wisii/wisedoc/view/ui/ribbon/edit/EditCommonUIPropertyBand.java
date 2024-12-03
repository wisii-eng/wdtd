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

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Edit;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class EditCommonUIPropertyBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand inputSetBand = new JRibbonBand(
				RibbonUIText.EDIT_UIATTRIBUTE, MediaResource
						.getResizableIcon("09379.ico"));
//		JPanel uistylepanel = new JPanel();
//		uistylepanel.setLayout(new GridLayout(2, 1));
//		JPanel typepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		WiseCombobox box = new WiseCombobox(new DefaultComboBoxModel(
//				new String[]
//				{ RibbonUIText.EDIT_FULL, RibbonUIText.EDIT_COMPACT,
//						RibbonUIText.EDIT_MINIMAL }));
//		JLabel uistyle = new JLabel(RibbonUIText.EDIT_UISTYLE);
//		RibbonUIManager.getInstance().bind(Edit.EDIT_APPEARANCE_ACTION, box);
//		typepanel.add(uistyle);
//		typepanel.add(box);
//		JLabel hintlabel = new JLabel(RibbonUIText.EDIT_INFORMATION);
//		WiseTextField hint = new WiseTextField(13);
//		RibbonUIManager.getInstance().bind(Edit.EDIT_HINT_ACTION, hint);
//		typepanel.add(hintlabel);
//		typepanel.add(hint);
//		JPanel whpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		JLabel widthlabel = new JLabel(RibbonUIText.EDIT_WIDTH);
//		FixedLengthSpinner width = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
//		RibbonUIManager.getInstance().bind(Edit.EDIT_WIDTH_ACTION, width);
//		whpanel.add(widthlabel);
//		whpanel.add(width);
//		JLabel heightlabel = new JLabel(RibbonUIText.EDIT_HEIGHT);
//		FixedLengthSpinner height = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
//		RibbonUIManager.getInstance().bind(Edit.EDIT_HEIGHT_ACTION, height);
//		whpanel.add(heightlabel);
//		whpanel.add(height);
//		uistylepanel.add(typepanel);
//		uistylepanel.add(whpanel);
//		JRibbonComponent maincom = new JRibbonComponent(uistylepanel);
		JPanel uistylepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel hintlabel = new JLabel(RibbonUIText.EDIT_INFORMATION);
		WiseTextField hint = new WiseTextField(13);
		RibbonUIManager.getInstance().bind(Edit.EDIT_HINT_ACTION, hint);
		uistylepanel.add(hintlabel);
		uistylepanel.add(hint);
		JRibbonComponent maincom = new JRibbonComponent(uistylepanel);
		inputSetBand.addRibbonComponent(maincom);
		return inputSetBand;
	}

}
