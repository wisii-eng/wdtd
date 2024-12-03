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

package com.wisii.wisedoc.view.ui.ribbon.blockcontainer;

import org.jvnet.flamingo.common.CommandToggleButtonGroup;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.BlockContainer;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
/**
 * 块内容溢出处理
 * 
 * @author 闫舒寰
 * @version 1.0 2009/01/16
 */
public class BlockContainerOverFlowBand
{

	public JRibbonBand getBlockContainerOverFlowBand()
	{

		// 溢出处理设置
		JRibbonBand blockContainerOverFlowBand = new JRibbonBand(
				RibbonUIText.BLOCK_CONTAINER_OVERFLOW_BAND, MediaResource
						.getResizableIcon("09379.ico"));
		CommandToggleButtonGroup overFlowGroup = new CommandToggleButtonGroup();

		JCommandToggleButton visible = new JCommandToggleButton(
				RibbonUIText.BLOCK_CONTAINER_OVERFLOW_VISIBLE_BUTTON,
				MediaResource.getResizableIcon("09378.ico"));
		visible
				.setActionRichTooltip(new RichTooltip(
						RibbonUIText.BLOCK_CONTAINER_OVERFLOW_VISIBLE_BUTTON_TITLE,
						RibbonUIText.BLOCK_CONTAINER_OVERFLOW_VISIBLE_BUTTON_DESCRIPTION));
		overFlowGroup.add(visible);
		RibbonUIManager.getInstance().bind(
				BlockContainer.OVERFLOW_VISIBLE_ACTION, visible);
		blockContainerOverFlowBand.addCommandButton(visible,
				RibbonElementPriority.TOP);

		JCommandToggleButton hidden = new JCommandToggleButton(
				RibbonUIText.BLOCK_CONTAINER_OVERFLOW_HIDDEN_BUTTON,
				MediaResource.getResizableIcon("09379.ico"));
		hidden
				.setActionRichTooltip(new RichTooltip(
						RibbonUIText.BLOCK_CONTAINER_OVERFLOW_HIDDEN_BUTTON_TITLE,
						RibbonUIText.BLOCK_CONTAINER_OVERFLOW_HIDDEN_BUTTON_DESCRIPTION));
		overFlowGroup.add(hidden);
		RibbonUIManager.getInstance().bind(
				BlockContainer.OVERFLOW_HIDDEN_ACTION, hidden);
		blockContainerOverFlowBand.addCommandButton(hidden,
				RibbonElementPriority.TOP);
		JCommandToggleButton autofontsize = new JCommandToggleButton(
				RibbonUIText.BLOCK_CONTAINER_AUTOFONTSIZE_BUTTON,
				MediaResource.getResizableIcon("00063.ico"));
		autofontsize
				.setActionRichTooltip(new RichTooltip(
						RibbonUIText.BLOCK_CONTAINER_AUTOFONTSIZE_TITLE,
						RibbonUIText.BLOCK_CONTAINER_AUTOFONTSIZE_DESCRIPT));
		overFlowGroup.add(autofontsize);
		RibbonUIManager.getInstance().bind(
				BlockContainer.OVERFLOW_AUTOFONTSIZE_ACTION, autofontsize);
		blockContainerOverFlowBand.addCommandButton(autofontsize,
				RibbonElementPriority.TOP);
		return blockContainerOverFlowBand;
	}

}
