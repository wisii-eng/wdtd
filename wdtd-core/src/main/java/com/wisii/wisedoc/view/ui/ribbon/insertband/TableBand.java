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
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonTableListPanel;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 插入表格面板
 * 
 * @author 闫舒寰
 * @version 1.0 2008/11/13
 */
public class TableBand {


	public JRibbonBand getBand() {
		
		//图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		JRibbonBand tableBand = new JRibbonBand(RibbonUIText.TABLE_BAND,	MediaResource.getResizableIcon("Table.gif"), null);

		//表格按钮
		JCommandButton insertTableButton = new JCommandButton(RibbonUIText.TABLE_BUTTON, MediaResource.getResizableIcon("00008.ico"));
		
		//下拉菜单的按钮
		insertTableButton.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new TableList();
			}
		});
		
		//添加可下拉按钮
		insertTableButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		//鼠标浮动显示解释说明
		insertTableButton.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_BUTTON_TITLE, RibbonUIText.TABLE_BUTTON_DESCRIPTION));
		//这个是在地方紧张的时候，显示的优先级
		tableBand.addCommandButton(insertTableButton, RibbonElementPriority.TOP);
		
		
		JCommandButton insertTable = new JCommandButton(RibbonUIText.INSERT_TABLE_BUTTON, MediaResource.getResizableIcon("00008.ico"));
		insertTable.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		tableBand.addCommandButton(insertTable, RibbonElementPriority.TOP);
		RibbonUIManager.getInstance().bind(ActionFactory.Defalult.INSERT_TABLE_ACTION, insertTable,insertTableButton);
		return tableBand;
	}	
	
	
	//弹出式下拉菜单项目
	private class TableList extends JCommandPopupMenu {
		
		public TableList() {
			RibbonTableListPanel tableList = new RibbonTableListPanel();
			this.add(tableList);
			/*this.addMenuSeparator();
			JCommandMenuButton insertTable = new JCommandMenuButton("插入表格", MediaResource.getResizableIcon("00008.ico"));
			insertTable.addActionListener(new InsertTableAction());
			this.addMenuButton(insertTable);*/
			/*JCommandMenuButton drawTable = new JCommandMenuButton("绘制表格", MediaResource.getResizableIcon("00008.ico"));
			this.addMenuButton(drawTable);	*/
		}		
	}
}
