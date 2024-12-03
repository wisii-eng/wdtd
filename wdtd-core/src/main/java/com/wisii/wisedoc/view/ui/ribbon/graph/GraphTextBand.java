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
package com.wisii.wisedoc.view.ui.ribbon.graph;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_TEXT_BAND_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_TEXT_INPUT_FIELD;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_TEXT_LABEL;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_TEXT_NODE_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_TEXT_NODE_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_TEXT_NODE_BUTTON_TITLE;

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
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;
import com.wisii.wisedoc.view.ui.ribbon.BindingTree;

/**
 * 图形内文字面板
 * @author 闫舒寰
 * @version 1.0 2009/02/27
 */
public class GraphTextBand {

	RichTooltip rnNodeTip = new RichTooltip();
	
	public JRibbonBand getBand() {
		
		//条形码内容设置
		JRibbonBand band = new JRibbonBand(SVG_GRAPH_TEXT_BAND_TITLE, MediaResource.getResizableIcon("09379.ico"));
		
		JPanel mainPanel = new JPanel(new GridLayout(1,2));
		JPanel barcodeContent = new JPanel(new GridLayout(2, 1, 0, 0));
		
		JLabel contentLabel = new JLabel(SVG_GRAPH_TEXT_LABEL);
		barcodeContent.add(contentLabel);
		
		JTextField content = new JTextField(SVG_GRAPH_TEXT_INPUT_FIELD);
		RibbonUIManager.getInstance().bind(SvgGraphic.TEXT_CONTENT_ACTION, content);
		
		barcodeContent.add(content);
		
		JCommandButton rnNode = new JCommandButton(SVG_GRAPH_TEXT_NODE_BUTTON, MediaResource.getResizableIcon("07461.ico"));
		rnNode.setDisplayState(CommandButtonDisplayState.MEDIUM);
		
		rnNodeTip.setTitle(SVG_GRAPH_TEXT_NODE_BUTTON_TITLE);
		rnNodeTip.addDescriptionSection(SVG_GRAPH_TEXT_NODE_BUTTON_DESCRIPTION);
//		rnNodeTip.addDescriptionSection("当前所选节点在分割线后显示：");
		rnNode.setActionRichTooltip(rnNodeTip);
		
		rnNode.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		rnNode.setPopupCallback(new PopupPanelCallback(){
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new BindingTree();
			}
		});
		
		mainPanel.add(rnNode);
		mainPanel.add(barcodeContent);
		
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent mainPanelWrapper = new JRibbonComponent(mainPanel);
		band.addRibbonComponent(mainPanelWrapper);
		
		return band;
	}
}
