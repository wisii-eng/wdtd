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
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.TableCell;
import com.wisii.wisedoc.view.ui.ribbon.BackGroundImageList;
import com.wisii.wisedoc.view.ui.ribbon.BorderAndBackGroundList;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 单元格属性band
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class TableCellPropertyBand {
	
	public JRibbonBand getBand() {
		
		//单元格样式设置
		JRibbonBand tableCellPropertyBand = new JRibbonBand(RibbonUIText.TABLE_CELL_PROPERTY_BAND, MediaResource.getResizableIcon("09379.ico"), null);
		
		//单元格自动调整按钮
		JCommandButton autoSizebutton = new JCommandButton(RibbonUIText.CELL_AUTO_SIZE_BUTTON, MediaResource.getResizableIcon("00664.ico"));
		autoSizebutton.setPopupCallback(new SizeType());
		autoSizebutton.setPopupRichTooltip(new RichTooltip(RibbonUIText.CELL_AUTO_SIZE_BUTTON_TITLE, RibbonUIText.CELL_AUTO_SIZE_BUTTON_DESCRIPTION));
		autoSizebutton.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		tableCellPropertyBand.addCommandButton(autoSizebutton, RibbonElementPriority.MEDIUM);
		
		//单元格边框按钮
		JCommandButton border = new JCommandButton(RibbonUIText.CELL_BORDER_BUTTON, MediaResource.getResizableIcon("03466.ico"));
		border.setPopupRichTooltip(new RichTooltip(RibbonUIText.CELL_BORDER_BUTTON_TITLE, RibbonUIText.CELL_BORDER_BUTTON_DESCRIPTION));
		border.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		border.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(JCommandButton arg0)
			{
				return new BorderAndBackGroundList(ActionType.TABLE_CELL);
			}
		});
		tableCellPropertyBand.addCommandButton(border, RibbonElementPriority.MEDIUM);
		
		//单元格背景颜色按钮
		JCommandButton backgroundColor = new JCommandButton(RibbonUIText.CELL_BACKGROUND_COLOR_BUTTON, MediaResource.getResizableIcon("00417.ico"));
		backgroundColor.setPopupRichTooltip(new RichTooltip(RibbonUIText.CELL_BACKGROUND_COLOR_BUTTON_TITLE, RibbonUIText.CELL_BACKGROUND_COLOR_BUTTON_DESCRIPTION));
		backgroundColor.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundColor.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new BackGroundColor();
			}
		});
		tableCellPropertyBand.addCommandButton(backgroundColor, RibbonElementPriority.MEDIUM);
		
		//单元格背景图按钮
		JCommandButton backgroundimage = new JCommandButton(RibbonUIText.CELL_BACKGROUND_PICTURE_BUTTON, MediaResource.getResizableIcon("00931.ico"));
		backgroundimage.setPopupRichTooltip(new RichTooltip(RibbonUIText.CELL_BACKGROUND_PICTURE_BUTTON_TITLE, RibbonUIText.CELL_BACKGROUND_PICTURE_BUTTON_DESCRIPTION));
		backgroundimage.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundimage.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new BackGroundImageList(ActionType.TABLE_CELL);
			}
		});
		tableCellPropertyBand.addCommandButton(backgroundimage, RibbonElementPriority.MEDIUM);
		
		//单元格对齐方式按钮
		JCommandButton displayAlign = new JCommandButton(RibbonUIText.CELL_DISPLAY_ALIGN_BUTTON, MediaResource.getResizableIcon("00666.ico"));
		displayAlign.setPopupCallback(new DisplayAlign());
		displayAlign.setPopupRichTooltip(new RichTooltip(RibbonUIText.CELL_DISPLAY_ALIGN_BUTTON_TITLE, RibbonUIText.CELL_DISPLAY_ALIGN_BUTTON_DESCRIPTION));
		displayAlign.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		tableCellPropertyBand.addCommandButton(displayAlign, RibbonElementPriority.MEDIUM);
		
		return tableCellPropertyBand;
	}
	
	//单元格对齐方式下拉菜单
	private class DisplayAlign extends JCommandPopupMenu implements PopupPanelCallback {
		
		public DisplayAlign() {
			JCommandMenuButton before = new JCommandMenuButton(RibbonUIText.CELL_DISPLAY_ALIGN_MENU[0], MediaResource.getResizableIcon("00666.ico"));
			JCommandMenuButton center = new JCommandMenuButton(RibbonUIText.CELL_DISPLAY_ALIGN_MENU[1], MediaResource.getResizableIcon("00669.ico"));
			JCommandMenuButton after = new JCommandMenuButton(RibbonUIText.CELL_DISPLAY_ALIGN_MENU[2], MediaResource.getResizableIcon("00667.ico"));
			
			RibbonUIManager.getInstance().bind(TableCell.DISPLAY_ALIGN_ACTION, before, center, after);
			
			this.addMenuButton(before);
			this.addMenuButton(center);
			this.addMenuButton(after);
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new DisplayAlign();
		}
	}
	
	//单元格自动调整大小下拉菜单
	private class SizeType extends JCommandPopupMenu implements PopupPanelCallback {
		
		public SizeType() {
			JCommandMenuButton auto = new JCommandMenuButton(RibbonUIText.CELL_AUTO_SIZE_MENU[0], MediaResource.getResizableIcon("00666.ico"));
			JCommandMenuButton fixed = new JCommandMenuButton(RibbonUIText.CELL_AUTO_SIZE_MENU[1], MediaResource.getResizableIcon("00669.ico"));			
			RibbonUIManager.getInstance().bind(TableCell.SIZE_TYPE, auto, fixed);
			this.addMenuButton(auto);
			this.addMenuButton(fixed);
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new SizeType();
		}
		
	}
	
	//背景颜色下拉菜单
	private class BackGroundColor extends JCommandPopupMenu {
		
		public BackGroundColor() {
			try {
				RibbonColorList colorBox = new RibbonColorList();
				RibbonUIManager.getInstance().bind(TableCell.BACKGROUND_COLOR_ACTION, colorBox);
				this.add(colorBox.getNullColorButton());
				this.add(colorBox.getPopupComponent());
				this.add(colorBox.getCustomColorButton());
			} catch (IncompatibleLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
