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
package com.wisii.wisedoc.view.ui.ribbon.table;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 表格行和列操作task
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class TableRowColumnBand
{

	public JRibbonBand getTableRowColumnBand()
	{

		// 行和列标签
		JRibbonBand tableRowColumnBand = new JRibbonBand(RibbonUIText.TABLE_ROW_COLUMN_BAND, MediaResource.getResizableIcon("09379.ico"));

		// 删除表格按钮
		JCommandButton deleteTableButton = new JCommandButton(RibbonUIText.TABLE_DELETE_BUTTON, MediaResource.getResizableIcon("03679.ico"));
		// 鼠标浮动显示解释说明
		deleteTableButton.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_DELETE_BUTTON_TITLE, RibbonUIText.TABLE_DELETE_BUTTON_DESCRIPTION));
		// 下拉菜单的按钮
		deleteTableButton.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(JCommandButton arg0)
			{
				return new Delete();
			}
		});
		// 添加可下拉按钮
		deleteTableButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		// 这个是在地方紧张的时候，显示的优先级
		tableRowColumnBand.addCommandButton(deleteTableButton, RibbonElementPriority.TOP);

		// 上方插入按钮
		JCommandButton insertStart = new JCommandButton(RibbonUIText.TABLE_INSERT_UP_BUTTON, MediaResource.getResizableIcon("03681.ico"));
		insertStart.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_INSERT_UP_BUTTON_TITLE, RibbonUIText.TABLE_INSERT_UP_BUTTON_DESCRIPTION));
		insertStart.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(ActionFactory.Defalult.ADD_TABLEROW_ABOVE_ACTION, insertStart);
		tableRowColumnBand.addCommandButton(insertStart, RibbonElementPriority.TOP);

		// 上方插入按钮
		JCommandButton insertBottom = new JCommandButton(RibbonUIText.TABLE_INSERT_BOTTOM_BUTTON, MediaResource.getResizableIcon("03683.ico"));
		insertBottom.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_INSERT_BOTTOM_BUTTON_TITLE, RibbonUIText.TABLE_INSERT_BOTTOM_BUTTON_DESCRIPTION));
		RibbonUIManager.getInstance().bind(ActionFactory.Defalult.ADD_TABLEROW_BELOW_ACTION, insertBottom);
		insertBottom.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		tableRowColumnBand.addCommandButton(insertBottom, RibbonElementPriority.TOP);

		// 左侧插入单元格按钮
		JCommandButton insertLeft = new JCommandButton(RibbonUIText.TABLE_INSERT_LEFT_BUTTON, MediaResource.getResizableIcon("03686.ico"));
		insertLeft.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_INSERT_LEFT_BUTTON_TITLE, RibbonUIText.TABLE_INSERT_LEFT_BUTTON_DESCRIPTION));
		RibbonUIManager.getInstance().bind(ActionFactory.Defalult.ADD_TABLECELL_LEFT_ACTION, insertLeft);
		insertLeft.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		tableRowColumnBand.addCommandButton(insertLeft, RibbonElementPriority.TOP);

		// 右侧插入按钮
		JCommandButton insertRight = new JCommandButton(RibbonUIText.TABLE_INSERT_RIGHT_BUTTON, MediaResource.getResizableIcon("03687.ico"));
		insertRight.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_INSERT_RIGHT_BUTTON_TITLE, RibbonUIText.TABLE_INSERT_RIGHT_BUTTON_DESCRIPTION));
		RibbonUIManager.getInstance().bind(ActionFactory.Defalult.ADD_TABLECELL_RIGHT_ACTION, insertRight);
		insertRight.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		tableRowColumnBand.addCommandButton(insertRight, RibbonElementPriority.TOP);
		
		// 添加表头按钮
		JCommandButton insertheader = new JCommandButton(RibbonUIText.TABLE_INSERT_HEADER_BUTTON, MediaResource.getResizableIcon("03686.ico"));
		insertheader.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_INSERT_HEADER_BUTTON_TITLE, RibbonUIText.TABLE_INSERT_HEADER_BUTTON_DESCRIPTION));
		RibbonUIManager.getInstance().bind(ActionFactory.Defalult.ADD_TABLEHEADER_ACTION, insertheader);
		insertheader.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		tableRowColumnBand.addCommandButton(insertheader, RibbonElementPriority.TOP);

		// 添加表尾按钮
		JCommandButton insertfooter = new JCommandButton(RibbonUIText.TABLE_INSERT_FOOTER_BUTTON, MediaResource.getResizableIcon("03687.ico"));
		insertfooter.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_INSERT_FOOTER_BUTTON_TITLE, RibbonUIText.TABLE_INSERT_FOOTER_BUTTON_DESCRIPTION));
		insertfooter.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(ActionFactory.Defalult.ADD_TABLEFOOTER_ACTION, insertfooter);
		tableRowColumnBand.addCommandButton(insertfooter, RibbonElementPriority.TOP);
		
		return tableRowColumnBand;
	}

	// 删除下拉菜单
	public static class Delete extends JCommandPopupMenu
	{
		public Delete()
		{
			JCommandMenuButton deleteCell = new JCommandMenuButton(RibbonUIText.TABLE_DELETE_MENU[0], MediaResource.getResizableIcon("02164.ico"));
			this.addMenuButton(deleteCell);
			RibbonUIManager.getInstance().bind(Defalult.DELETE_TABLECELL_ACTION, deleteCell);
			deleteCell.setEnabled(ActionFactory.getAction(Defalult.DELETE_TABLECELL_ACTION).isAvailable());
			
//			JCommandMenuButton deleteColumn = new JCommandMenuButton("删除列",
//					MediaResource.getResizableIcon("02940.ico"));
//			this.addMenuButton(deleteColumn);

			JCommandMenuButton deleteRow = new JCommandMenuButton(RibbonUIText.TABLE_DELETE_MENU[2], MediaResource.getResizableIcon("00334.ico"));
			RibbonUIManager.getInstance().bind(ActionFactory.Defalult.DELETE_TABLEROW_ACTION, deleteRow);
			this.addMenuButton(deleteRow);
			deleteRow.setEnabled(ActionFactory.getAction(Defalult.DELETE_TABLEROW_ACTION).isAvailable());
			
			JCommandMenuButton deleteTableheader = new JCommandMenuButton(RibbonUIText.TABLE_DELETE_MENU[3], MediaResource.getResizableIcon("03679.ico"));
			RibbonUIManager.getInstance().bind(ActionFactory.Defalult.DELETE_TABLEHEADER_ACTION, deleteTableheader);
			this.addMenuButton(deleteTableheader);
			deleteTableheader.setEnabled(ActionFactory.getAction(Defalult.DELETE_TABLEHEADER_ACTION).isAvailable());
			
			JCommandMenuButton deleteTablefooter = new JCommandMenuButton(RibbonUIText.TABLE_DELETE_MENU[4], MediaResource.getResizableIcon("03679.ico"));
			RibbonUIManager.getInstance().bind(ActionFactory.Defalult.DELETE_TABLEFOOTER_ACTION, deleteTablefooter);
			this.addMenuButton(deleteTablefooter);
			deleteTablefooter.setEnabled(ActionFactory.getAction(Defalult.DELETE_TABLEFOOTER_ACTION).isAvailable());
			
			JCommandMenuButton deleteTable = new JCommandMenuButton(RibbonUIText.TABLE_DELETE_MENU[5], MediaResource.getResizableIcon("03679.ico"));
			RibbonUIManager.getInstance().bind(ActionFactory.Defalult.DELETE_TABLE_ACTION, deleteTable);
			this.addMenuButton(deleteTable);
			deleteTable.setEnabled(ActionFactory.getAction(Defalult.DELETE_TABLE_ACTION).isAvailable());
		}
	}

}
