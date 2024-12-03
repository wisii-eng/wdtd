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
package com.wisii.wisedoc.view.ui.ribbon.barcode;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Barcode;
import com.wisii.wisedoc.view.ui.ribbon.BindingTree;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 条形码内容面板
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class BarcodeContentBand {
	
	RichTooltip rnNodeTip = new RichTooltip();
	
	public JRibbonBand getBand() {
		
		//条形码内容设置
		JRibbonBand barcodeContentBand = new JRibbonBand(RibbonUIText.BARCODE_CONTENT_BAND, MediaResource.getResizableIcon("09379.ico"));
		JPanel barcodeContent = new JPanel(new GridLayout(2, 1, 0, 0));
		
		JLabel contentLabel = new JLabel(RibbonUIText.BARCODE_CONTENT_LABEL);
		barcodeContent.add(contentLabel);
		
		JTextField content = new JTextField(RibbonUIText.BARCODE_CONTENT_FIELD);
		RibbonUIManager.getInstance().bind(Barcode.INPUT_CONTENT_ACTION, content);
		
		barcodeContent.add(content);
		
		JCommandButton rnNode = new JCommandButton(RibbonUIText.BARCODE_NODE_BUTTON, MediaResource.getResizableIcon("07461.ico"));
		rnNode.setDisplayState(CommandButtonDisplayState.MEDIUM);
		rnNodeTip.setTitle(RibbonUIText.BARCODE_NODE_BUTTON_TITLE);
		rnNodeTip.addDescriptionSection(RibbonUIText.BARCODE_NODE_BUTTON_DESCRIPTION);
//		rnNodeTip.addDescriptionSection("当前所选节点在分割线后显示：");
		rnNode.setActionRichTooltip(rnNodeTip);
		rnNode.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		rnNode.setPopupCallback(new PopupPanelCallback(){
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new BindingTree();
			}
		});
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent mainPanelWrapper = new JRibbonComponent(barcodeContent);
		barcodeContentBand.addRibbonComponent(mainPanelWrapper);
		
		return barcodeContentBand;
	}
}
