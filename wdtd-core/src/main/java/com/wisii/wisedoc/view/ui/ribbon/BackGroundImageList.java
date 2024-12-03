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

package com.wisii.wisedoc.view.ui.ribbon;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.BlockContainer;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Table;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.TableCell;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.parts.dialogs.BorderAndBackGroundDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-1-8
 */
public class BackGroundImageList extends JCommandPopupMenu
{

	public BackGroundImageList(final ActionType propertyType)
	{
		final JCommandMenuButton setbgimage = new JCommandMenuButton(
				RibbonUIText.BACKGROUND_CHOOSE_PICTURE, MediaResource
						.getResizableIcon("00931.ico"));
		final JCommandMenuButton removebgimage = new JCommandMenuButton(
				RibbonUIText.BACKGROUND_CLEAR_PICTURE, MediaResource
						.getResizableIcon("delimg1.ico"));
		if (propertyType == ActionType.TABLE)
		{
			RibbonUIManager.getInstance().bind(Table.BACKGROUND_IMAGE_ACTION,
					setbgimage);
			RibbonUIManager.getInstance().bind(Table.REMOVE_BACKGROUNDIMAGE,
					removebgimage);
		} else if (propertyType == ActionType.TABLE_CELL)
		{
			RibbonUIManager.getInstance().bind(
					TableCell.BACKGROUND_IMAGE_ACTION, setbgimage);
			RibbonUIManager.getInstance().bind(
					TableCell.REMOVE_BACKGROUNDIMAGE, removebgimage);
		} else if (propertyType == ActionType.BLOCK_CONTAINER)
		{
			RibbonUIManager.getInstance().bind(
					BlockContainer.BACKGROUND_IMAGE_ACTION, setbgimage);
			RibbonUIManager.getInstance().bind(
					BlockContainer.REMOVE_BACKGROUNDIMAGE, removebgimage);
		} else if (propertyType == ActionType.LAYOUT)
		{
			RibbonUIManager.getInstance().bind(
					Page.BODY_BACKGROUND_IMAGE_ACTION, setbgimage);
			RibbonUIManager.getInstance().bind(Page.REMOVE_BODYBGIMAGEACTION,
					removebgimage);
		} else if (propertyType == ActionType.SVG_GRAPHIC)
		{
			RibbonUIManager.getInstance().bind(SvgGraphic.FILL_PICTURE_ACTION,
					setbgimage);
			RibbonUIManager.getInstance().bind(
					SvgGraphic.REMOVE_PICTURE_ACTION, removebgimage);
		} else if (propertyType == ActionType.Chart)
		{
			RibbonUIManager.getInstance().bind(
					WisedocChart.FILL_PICTURE_ACTION, setbgimage);
			RibbonUIManager.getInstance().bind(
					WisedocChart.REMOVE_PICTURE_ACTION, removebgimage);
		}
		this.addMenuButton(setbgimage);
		this.addMenuButton(removebgimage);
		this.addMenuSeparator();
		final JCommandMenuButton borderDialog = new JCommandMenuButton(
				RibbonUIText.BACKGROUND_AND_BORDER, MediaResource
						.getResizableIcon("03466.ico"));
		borderDialog.addActionListener(new AbstractAction()
		{

			public void actionPerformed(final ActionEvent e)
			{
				new BorderAndBackGroundDialog(propertyType);

			}
		});
		this.addMenuButton(borderDialog);
	}

}
