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
 * @BackGroundBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.blockcontainer;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.BlockContainer;
import com.wisii.wisedoc.view.ui.ribbon.BackGroundImageList;
import com.wisii.wisedoc.view.ui.ribbon.BorderAndBackGroundList;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：块容器边框和底纹设置面板
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-16
 */
public class BackGroundBand implements WiseBand
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	public JRibbonBand getBand()
	{
		//条形码位置设置
		JRibbonBand backgroundband = new JRibbonBand(RibbonUIText.BLOCK_CONTAINER_BORDER_BACKGROUND_BAND, MediaResource.getResizableIcon("09379.ico"));
		
		JCommandButton border = new JCommandButton(RibbonUIText.BLOCK_CONTAINER_BORDER_BUTTON, MediaResource
				.getResizableIcon("03466.ico"));
		border.setPopupRichTooltip(new RichTooltip(RibbonUIText.BLOCK_CONTAINER_BORDER_BUTTON_TITLE, RibbonUIText.BLOCK_CONTAINER_BORDER_BUTTON_DESCRIPTION));
		border.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		border.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(JCommandButton arg0)
			{
				return new BorderAndBackGroundList(ActionType.BLOCK_CONTAINER);
			}
		});
		backgroundband.addCommandButton(border, RibbonElementPriority.MEDIUM);
		
		JCommandButton backgroundColor = new JCommandButton(RibbonUIText.BLOCK_CONTAINER_BACKGROUND_BUTTON, MediaResource.getResizableIcon("00417.ico"));
		backgroundColor.setPopupRichTooltip(new RichTooltip(RibbonUIText.BLOCK_CONTAINER_BACKGROUND_BUTTON_TITLE, RibbonUIText.BLOCK_CONTAINER_BACKGROUND_BUTTON_DESCRIPTION));
		backgroundColor.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundColor.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new BackGroundColor();
			}
		});
		backgroundband.addCommandButton(backgroundColor, RibbonElementPriority.MEDIUM);
		
		JCommandButton backgroundimage = new JCommandButton(RibbonUIText.BLOCK_CONTAINER_PICTURE_BUTTON, MediaResource.getResizableIcon("00931.ico"));
		backgroundimage.setPopupRichTooltip(new RichTooltip(RibbonUIText.BLOCK_CONTAINER_PICTURE_BUTTON_TITLE, RibbonUIText.BLOCK_CONTAINER_PICTURE_BUTTON_DESCRIPTION));
		backgroundimage.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundimage.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new BackGroundImageList(ActionType.BLOCK_CONTAINER);
			}
		});
		backgroundband.addCommandButton(backgroundimage, RibbonElementPriority.MEDIUM);
		
		return backgroundband;
	}
	
	//背景颜色下拉菜单
	private class BackGroundColor extends JCommandPopupMenu {
		
		public BackGroundColor() {
			try {
				RibbonColorList colorBox = new RibbonColorList();
				RibbonUIManager.getInstance().bind(BlockContainer.BACKGROUND_COLOR_ACTION, colorBox);
				this.add(colorBox.getNullColorButton());
				this.add(colorBox.getPopupComponent());
				this.add(colorBox.getCustomColorButton());
			} catch (IncompatibleLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
