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
package com.wisii.wisedoc.view.ui.ribbon.pagetask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel.SPMLayoutType;
import com.wisii.wisedoc.view.ui.parts.dialogs.SimplePageMasterDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 页面属性设置的Ribbon菜单
 * @author 闫舒寰
 * @version 1.0 2008/11/25
 */
public class PageBand {
	
	public JRibbonBand getBand() {
		//图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		JRibbonBand band = new JRibbonBand(RibbonUIText.PAGE_BAND, MediaResource.getResizableIcon("09379.ico"), /*new PageLayout()*/null);

		//创建单个页布局
		JCommandButton createNormalLayout = new JCommandButton(RibbonUIText.LAYOUT_CREATE_NORMAL_BUTTON, MediaResource.getResizableIcon("01826.ico"));
		createNormalLayout.setActionRichTooltip(new RichTooltip(RibbonUIText.LAYOUT_CREATE_NORMAL_BUTTON_TITLE, RibbonUIText.LAYOUT_CREATE_NORMAL_BUTTON_DESCRIPTION));
		createNormalLayout.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Page.ADD_SIMPLE_MASTER_LAYOUT_ACTION, createNormalLayout);
		band.addCommandButton(createNormalLayout, RibbonElementPriority.MEDIUM);
		
		//编辑单个页布局
		JCommandButton editNormalLayout = new JCommandButton(RibbonUIText.LAYOUT_EDIT_NORMAL_BUTTON, MediaResource.getResizableIcon("01826.ico"));
		editNormalLayout.setActionRichTooltip(new RichTooltip(RibbonUIText.LAYOUT_EDIT_NORMAL_BUTTON_TITLE, RibbonUIText.LAYOUT_EDIT_NORMAL_BUTTON_DESCRIPTION));
		editNormalLayout.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Page.EDIT_SIMPLE_MASTER_LAYOUT_ACTION, editNormalLayout);
		band.addCommandButton(editNormalLayout, RibbonElementPriority.MEDIUM);
		
		JCommandButton customLayout = new JCommandButton(RibbonUIText.PAGE_SEQUENCE_MASTER_ADD_BUTTON, MediaResource.getResizableIcon("01826.ico"));
		customLayout.setActionRichTooltip(new RichTooltip(RibbonUIText.PAGE_SEQUENCE_MASTER_ADD_BUTTON_TITLE, RibbonUIText.PAGE_SEQUENCE_MASTER_ADD_BUTTON_DESCRIPTION));
		customLayout.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		band.addCommandButton(customLayout, RibbonElementPriority.MEDIUM);
		RibbonUIManager.getInstance().bind(Page.ADD_SEQUENCE_MASTER_LAYOUT_ACTION, customLayout);
		
		
		JCommandButton editCustomLayout = new JCommandButton(RibbonUIText.PAGE_SEQUENCE_MASTER_EDIT_BUTTON, MediaResource.getResizableIcon("01826.ico"));
		editCustomLayout.setActionRichTooltip(new RichTooltip(RibbonUIText.PAGE_SEQUENCE_MASTER_EDIT_BUTTON_TITLE, RibbonUIText.PAGE_SEQUENCE_MASTER_EDIT_BUTTON_DESCRIPTION));
		editCustomLayout.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Page.EDIT_SEQUENCE_MASTER_LAYOUT_ACTION, editCustomLayout);
		band.addCommandButton(editCustomLayout, RibbonElementPriority.MEDIUM);
		
		
		return band;
	}
	
	private class PageLayout implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new SimplePageMasterDialog(SPMLayoutType.setFO);
		}
	}

}
