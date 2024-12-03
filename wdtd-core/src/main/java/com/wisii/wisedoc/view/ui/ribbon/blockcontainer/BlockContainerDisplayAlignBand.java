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

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.BlockContainer;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 块容器对齐方式面板
 * @author 闫舒寰
 * @version 1.0 2009/01/16
 */
public class BlockContainerDisplayAlignBand implements WiseBand
{

	public JRibbonBand getBand()
	{
		// 块容器对齐方式面板
		JRibbonBand blockContainerDisplayAlignBand = new JRibbonBand(RibbonUIText.BLOCK_CONTAINER_DISPLAY_ALIGN_BAND,
				MediaResource.getResizableIcon("09379.ico"));
		JCommandButton displayAlign = new JCommandButton(RibbonUIText.BLOCK_CONTAINER_DISPLAY_ALIGN_BUTTON, MediaResource
				.getResizableIcon("00664.ico"));
		displayAlign.setPopupCallback(new DisplayAlign());
		displayAlign.setPopupRichTooltip(new RichTooltip(RibbonUIText.BLOCK_CONTAINER_DISPLAY_ALIGN_BUTTON_TITLE, RibbonUIText.BLOCK_CONTAINER_DISPLAY_ALIGN_BUTTON_DESCRIPTION));
		displayAlign.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		blockContainerDisplayAlignBand.addCommandButton(displayAlign,
				RibbonElementPriority.MEDIUM);

		return blockContainerDisplayAlignBand;
	}

	private class DisplayAlign extends JCommandPopupMenu implements
			PopupPanelCallback
	{

		public DisplayAlign()
		{
			JCommandMenuButton before = new JCommandMenuButton(RibbonUIText.BLOCK_CONTAINER_DISPLAY_MENU[0],
					MediaResource.getResizableIcon("00666.ico"));
			JCommandMenuButton center = new JCommandMenuButton(RibbonUIText.BLOCK_CONTAINER_DISPLAY_MENU[1],
					MediaResource.getResizableIcon("00669.ico"));
			JCommandMenuButton after = new JCommandMenuButton(RibbonUIText.BLOCK_CONTAINER_DISPLAY_MENU[2],
					MediaResource.getResizableIcon("00667.ico"));

			RibbonUIManager.getInstance().bind(BlockContainer.DISPLAY_ALIGN_ACTION,
					before, center, after);

			this.addMenuButton(before);
			this.addMenuButton(center);
			this.addMenuButton(after);

		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton)
		{
			return new DisplayAlign();
		}

	}
}
