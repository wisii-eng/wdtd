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
 * @BackGroundImageList.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.parts.regionsbg;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionID;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionAfter;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionBefore;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionEnd;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionStart;

/**
 * 为四个区域设置背景的Ribbbon下拉菜单
 * 
 * 作者：闫舒寰
 * 创建日期：2009-2-18
 */
public class RegionsBackGroundImageList extends JCommandPopupMenu
{
	public RegionsBackGroundImageList(final ActionID actionID)
	{
		JCommandMenuButton setbgimage = new JCommandMenuButton("选择图片",
				MediaResource.getResizableIcon("00931.ico"));
		JCommandMenuButton removebgimage = new JCommandMenuButton("清除背景图片",
				MediaResource.getResizableIcon("delimg1.ico"));
		
		if (actionID instanceof RegionBefore) {
			RibbonUIManager.getInstance().bind(
					RegionBefore.BACKGROUND_IMAGE_ACTION, setbgimage);
			RibbonUIManager.getInstance().bind(RegionBefore.BACKGROUND_IMAGE_REMOVE_ACTION,
					removebgimage);
		} else if (actionID instanceof RegionAfter) {
			RibbonUIManager.getInstance().bind(
					RegionAfter.BACKGROUND_IMAGE_ACTION, setbgimage);
			RibbonUIManager.getInstance().bind(RegionAfter.BACKGROUND_IMAGE_REMOVE_ACTION,
					removebgimage);
		} else if (actionID instanceof RegionStart) {
			RibbonUIManager.getInstance().bind(
					RegionStart.BACKGROUND_IMAGE_ACTION, setbgimage);
			RibbonUIManager.getInstance().bind(RegionStart.BACKGROUND_IMAGE_REMOVE_ACTION,
					removebgimage);
		} else if (actionID instanceof RegionEnd) {
			RibbonUIManager.getInstance().bind(
					RegionEnd.BACKGROUND_IMAGE_ACTION, setbgimage);
			RibbonUIManager.getInstance().bind(RegionEnd.BACKGROUND_IMAGE_REMOVE_ACTION,
					removebgimage);
		}
		
		this.addMenuButton(setbgimage);
		this.addMenuButton(removebgimage);
		this.addMenuSeparator();
		JCommandMenuButton borderDialog = new JCommandMenuButton("边框和底纹",
				MediaResource.getResizableIcon("03466.ico"));
		
		borderDialog.addActionListener(new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				new RegionsBorderAndBackGroundDialog(actionID);
			}
		});
		
		this.addMenuButton(borderDialog);
	}
}
