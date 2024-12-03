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
package com.wisii.wisedoc.view.ui.ribbon.insertband;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 插入FO元素面板
 * 
 * @author 闫舒寰
 * @version 1.0 2008/11/13
 */
public class FOBand {
	
	public JRibbonBand getBand() {
		
		//图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		JRibbonBand fOBand = new JRibbonBand(RibbonUIText.BLOCK_CONTAINER_BAND, MediaResource.getResizableIcon("Table.gif"), null);

		//Block Container按钮
		JCommandButton blockContainer = new JCommandButton(RibbonUIText.BLOCK_CONTAINER_ABS_BUTTON, MediaResource.getResizableIcon("01947.ico"));
		blockContainer.setActionRichTooltip(new RichTooltip(RibbonUIText.BLOCK_CONTAINER_ABS_BUTTON_TITLE, RibbonUIText.BLOCK_CONTAINER_ABS_BUTTON_DESCRIPTION));
		//主按钮的动作为绘制一个block container
		RibbonUIManager.getInstance().bind(Defalult.INSERT_BLOCKCONTAINER_ACTION, blockContainer);		
		
		//添加可下拉按钮
		blockContainer
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		//Block Container按钮
		JCommandButton relativeblockContainer = new JCommandButton(RibbonUIText.BLOCK_CONTAINER_REL_BUTTON, MediaResource.getResizableIcon("01947.ico"));
		relativeblockContainer.setActionRichTooltip(new RichTooltip(RibbonUIText.BLOCK_CONTAINER_REL_BUTTON_TITLE, RibbonUIText.BLOCK_CONTAINER_REL_BUTTON_DESCRIPTION));
		//主按钮的动作为绘制一个block container
		RibbonUIManager.getInstance().bind(Defalult.INSERT_RELATIVEBLOCKCONTAINER_ACTION, relativeblockContainer);		
		//添加可下拉按钮
		relativeblockContainer.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		
		
		//这个是在地方紧张的时候，显示的优先级
		fOBand.addCommandButton(blockContainer, RibbonElementPriority.TOP);	
		fOBand.addCommandButton(relativeblockContainer, RibbonElementPriority.TOP);	

		return fOBand;
	}	

}
