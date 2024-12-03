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
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Table;
import com.wisii.wisedoc.view.ui.ribbon.BackGroundImageList;
import com.wisii.wisedoc.view.ui.ribbon.BorderAndBackGroundList;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 表格样式band
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class TableStyleBand {

	public JRibbonBand getBand() {
		
		//表格样式设置
		JRibbonBand tableStyleBand = new JRibbonBand(RibbonUIText.TABLE_STYLE_BAND, MediaResource.getResizableIcon("09379.ico"));

		JCommandButton border = new JCommandButton(RibbonUIText.TABLE_BORDER_BUTTON, MediaResource.getResizableIcon("03466.ico"));
		border.setPopupRichTooltip(new RichTooltip(RibbonUIText.TABLE_BORDER_BUTTON_TITLE, RibbonUIText.TABLE_BORDER_BUTTON_DESCRIPTION));
		border.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		border.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(JCommandButton arg0)
			{
				return new BorderAndBackGroundList(ActionType.TABLE);
			}
		});
		tableStyleBand.addCommandButton(border, RibbonElementPriority.MEDIUM);
		
		JCommandButton backgroundColor = new JCommandButton(RibbonUIText.TABLE_BACKGROUND_COLOR_BUTTON, MediaResource.getResizableIcon("00417.ico"));
		backgroundColor.setPopupRichTooltip(new RichTooltip(RibbonUIText.TABLE_BACKGROUND_COLOR_BUTTON_TITLE, RibbonUIText.TABLE_BACKGROUND_COLOR_BUTTON_DESCRIPTION));
		backgroundColor.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundColor.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new BackGroundColor();
			}
		});
		tableStyleBand.addCommandButton(backgroundColor, RibbonElementPriority.MEDIUM);
		
		JCommandButton backgroundimage = new JCommandButton(RibbonUIText.TABLE_PICTURE_BUTTON, MediaResource.getResizableIcon("00931.ico"));
		backgroundimage.setPopupRichTooltip(new RichTooltip(RibbonUIText.TABLE_PICTURE_BUTTON_TITLE, RibbonUIText.TABLE_PICTURE_BUTTON_DESCRIPTION));
		backgroundimage.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundimage.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new BackGroundImageList(ActionType.TABLE);
			}
		});
		tableStyleBand.addCommandButton(backgroundimage, RibbonElementPriority.MEDIUM);
		
		//TODO 有关表能否设置对齐方式还需要调查
		/*JCommandButton displayAlign = new JCommandButton("对齐", MediaResource.getResizableIcon("00664.ico"));
		displayAlign.setPopupCallback(new DisplayAlign());
		displayAlign.setPopupRichTooltip(new RichTooltip("对齐","页面内容的对齐方式"));
		displayAlign.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		tableStyleBand.addCommandButton(displayAlign, RibbonElementPriority.MEDIUM);*/
		
//		JCommandButton tableLayout = new JCommandButton("表格样式", MediaResource.getResizableIcon("00664.ico"));
//		tableLayout.setPopupCallback(new TableLayout());
//		tableLayout.setPopupRichTooltip(new RichTooltip("表格样式","表格样式"));
//		tableLayout.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
//		tableStyleBand.addCommandButton(tableLayout, RibbonElementPriority.MEDIUM);
		
		return tableStyleBand;
	}
	
/*	private class TableLayout extends JCommandPopupMenu implements PopupPanelCallback {
		
		public TableLayout() {
			JCommandMenuButton auto = new JCommandMenuButton("自动", MediaResource.getResizableIcon("00666.ico"));
			JCommandMenuButton fix = new JCommandMenuButton("固定", MediaResource.getResizableIcon("00667.ico"));
//			RibbonUIManager.getInstance().bind(Table.DISPLAY_ALIGN_ACTION, auto, center, after);
			
			auto.addActionListener(new TableLayoutAutoAction());
			
			fix.addActionListener(new TableLayoutFixedAction());
			
			this.addMenuButton(auto);
			this.addMenuButton(fix);
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new TableLayout();
		}
		
	}
	
	private class TableLayoutFixedAction extends Actions {

		@Override
		public void doAction(ActionEvent e) {
			this.actionType = Table.getType();
			setFOProperty(Constants.PR_TABLE_LAYOUT, new EnumProperty(Constants.EN_FIXED, ""));
			
		}
		
	}
	
	private class TableLayoutAutoAction extends Actions {

		@Override
		public void doAction(ActionEvent e) {
			this.actionType = Table.getType();
			setFOProperty(Constants.PR_TABLE_LAYOUT, new EnumProperty(Constants.EN_AUTO, ""));
			
		}
		
	}*/
	
/*	private class DisplayAlign extends JCommandPopupMenu implements PopupPanelCallback {
		
		public DisplayAlign() {
			JCommandMenuButton before = new JCommandMenuButton("左对齐", MediaResource.getResizableIcon("00666.ico"));
			JCommandMenuButton center = new JCommandMenuButton("居中", MediaResource.getResizableIcon("00669.ico"));
			JCommandMenuButton after = new JCommandMenuButton("右对齐", MediaResource.getResizableIcon("00667.ico"));
			RibbonUIManager.getInstance().bind(Table.DISPLAY_ALIGN_ACTION, before, center, after);
			this.addMenuButton(before);
			this.addMenuButton(center);
			this.addMenuButton(after);
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new DisplayAlign();
		}
	}*/
	
	//背景颜色下拉菜单
	private class BackGroundColor extends JCommandPopupMenu {
		
		public BackGroundColor() {
			try {
				RibbonColorList colorBox = new RibbonColorList();
				RibbonUIManager.getInstance().bind(Table.BACKGROUND_COLOR_ACTION, colorBox);
				this.add(colorBox.getNullColorButton());
				this.add(colorBox.getPopupComponent());
				this.add(colorBox.getCustomColorButton());
			} catch (IncompatibleLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
