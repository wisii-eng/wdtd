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
 * @BorderAndBackGroundList.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.BlockContainer;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ExternalGraphic;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Table;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.TableCell;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.parts.dialogs.BorderAndBackGroundDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：设置边框和底纹的弹出式下拉列表控件
 *
 * 作者：zhangqiang
 * 创建日期：2009-1-8
 */
public class BorderAndBackGroundList extends JCommandPopupMenu
{
	public BorderAndBackGroundList(final ActionType propertyType)
	{
		final JCommandMenuButton afterBorder = new JCommandMenuButton(RibbonUIText.BORDERS[1],//"下边框",
				MediaResource.getResizableIcon("00146.ico"));
		final JCommandMenuButton beforeBorder = new JCommandMenuButton(RibbonUIText.BORDERS[0],//"上边框",
				MediaResource.getResizableIcon("00145.ico"));
		final JCommandMenuButton startBorder = new JCommandMenuButton(RibbonUIText.BORDERS[2],//"左边框",
				MediaResource.getResizableIcon("00147.ico"));
		final JCommandMenuButton endBorder = new JCommandMenuButton(RibbonUIText.BORDERS[3],//"右边框",
				MediaResource.getResizableIcon("00148.ico"));
		final JCommandMenuButton noneBorder = new JCommandMenuButton(RibbonUIText.BORDERS[4],//"无边框",
				MediaResource.getResizableIcon("02737.ico"));
		final JCommandMenuButton allBorder = new JCommandMenuButton(RibbonUIText.BORDERS[5],//"所有边框",
				MediaResource.getResizableIcon("01704.ico"));
		if (propertyType == ActionFactory.ActionType.BLOCK)
		{
			RibbonUIManager.getInstance().bind(Paragraph.AFTER_BORDER_ACTION,
					afterBorder);
			RibbonUIManager.getInstance().bind(Paragraph.BEFORE_BORDER_ACTION,
					beforeBorder);
			RibbonUIManager.getInstance().bind(Paragraph.START_BORDER_ACTION,
					startBorder);
			RibbonUIManager.getInstance().bind(Paragraph.END_BORDER_ACTION,
					endBorder);
			RibbonUIManager.getInstance().bind(Paragraph.NONE_BORDER_ACTION,
					noneBorder);
			RibbonUIManager.getInstance().bind(Paragraph.ALL_BORDER_ACTION,
					allBorder);
		} else if (propertyType == ActionFactory.ActionType.INLINE)
		{
			RibbonUIManager.getInstance().bind(Font.AFTER_BORDER_ACTION,
					afterBorder);
			RibbonUIManager.getInstance().bind(Font.BEFORE_BORDER_ACTION,
					beforeBorder);
			RibbonUIManager.getInstance().bind(Font.START_BORDER_ACTION,
					startBorder);
			RibbonUIManager.getInstance().bind(Font.END_BORDER_ACTION,
					endBorder);
			RibbonUIManager.getInstance().bind(Font.NONE_BORDER_ACTION,
					noneBorder);
			RibbonUIManager.getInstance().bind(Font.ALL_BORDER_ACTION,
					allBorder);
		} else if (propertyType == ActionFactory.ActionType.TABLE)
		{
			RibbonUIManager.getInstance().bind(Table.AFTER_BORDER_ACTION,
					afterBorder);
			RibbonUIManager.getInstance().bind(Table.BEFORE_BORDER_ACTION,
					beforeBorder);
			RibbonUIManager.getInstance().bind(Table.START_BORDER_ACTION,
					startBorder);
			RibbonUIManager.getInstance().bind(Table.END_BORDER_ACTION,
					endBorder);
			RibbonUIManager.getInstance().bind(Table.NONE_BORDER_ACTION,
					noneBorder);
			RibbonUIManager.getInstance().bind(Table.ALL_BORDER_ACTION,
					allBorder);
		} else if (propertyType == ActionFactory.ActionType.TABLE_CELL)
		{
			RibbonUIManager.getInstance().bind(TableCell.AFTER_BORDER_ACTION,
					afterBorder);
			RibbonUIManager.getInstance().bind(TableCell.BEFORE_BORDER_ACTION,
					beforeBorder);
			RibbonUIManager.getInstance().bind(TableCell.START_BORDER_ACTION,
					startBorder);
			RibbonUIManager.getInstance().bind(TableCell.END_BORDER_ACTION,
					endBorder);
			RibbonUIManager.getInstance().bind(TableCell.NONE_BORDER_ACTION,
					noneBorder);
			RibbonUIManager.getInstance().bind(TableCell.ALL_BORDER_ACTION,
					allBorder);
		} else if (propertyType == ActionFactory.ActionType.BLOCK_CONTAINER)
		{
			RibbonUIManager.getInstance().bind(
					BlockContainer.AFTER_BORDER_ACTION, afterBorder);
			RibbonUIManager.getInstance().bind(
					BlockContainer.BEFORE_BORDER_ACTION, beforeBorder);
			RibbonUIManager.getInstance().bind(
					BlockContainer.START_BORDER_ACTION, startBorder);
			RibbonUIManager.getInstance().bind(
					BlockContainer.END_BORDER_ACTION, endBorder);
			RibbonUIManager.getInstance().bind(
					BlockContainer.NONE_BORDER_ACTION, noneBorder);
			RibbonUIManager.getInstance().bind(
					BlockContainer.ALL_BORDER_ACTION, allBorder);
		} else if (propertyType == ActionFactory.ActionType.GRAPH)
		{
			RibbonUIManager.getInstance().bind(
					ExternalGraphic.AFTER_BORDER_ACTION, afterBorder);
			RibbonUIManager.getInstance().bind(
					ExternalGraphic.BEFORE_BORDER_ACTION, beforeBorder);
			RibbonUIManager.getInstance().bind(
					ExternalGraphic.START_BORDER_ACTION, startBorder);
			RibbonUIManager.getInstance().bind(
					ExternalGraphic.END_BORDER_ACTION, endBorder);
			RibbonUIManager.getInstance().bind(
					ExternalGraphic.NONE_BORDER_ACTION, noneBorder);
			RibbonUIManager.getInstance().bind(
					ExternalGraphic.ALL_BORDER_ACTION, allBorder);
		}else if(propertyType == ActionFactory.ActionType.Chart){
			RibbonUIManager.getInstance().bind(
					WisedocChart.AFTER_BORDER_ACTION, afterBorder);
			RibbonUIManager.getInstance().bind(
					WisedocChart.BEFORE_BORDER_ACTION, beforeBorder);
			RibbonUIManager.getInstance().bind(
					WisedocChart.START_BORDER_ACTION, startBorder);
			RibbonUIManager.getInstance().bind(
					WisedocChart.END_BORDER_ACTION, endBorder);
			RibbonUIManager.getInstance().bind(
					WisedocChart.NONE_BORDER_ACTION, noneBorder);
			RibbonUIManager.getInstance().bind(
					WisedocChart.ALL_BORDER_ACTION, allBorder);
		}
		this.addMenuButton(afterBorder);
		this.addMenuButton(beforeBorder);
		this.addMenuButton(startBorder);
		this.addMenuButton(endBorder);
		this.addMenuSeparator();
		this.addMenuButton(noneBorder);
		this.addMenuButton(allBorder);
		this.addMenuSeparator();
		final JCommandMenuButton borderDialog = new JCommandMenuButton(RibbonUIText.BORDER_SHADING, //"边框和底纹",
				MediaResource.getResizableIcon("03466.ico"));
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
