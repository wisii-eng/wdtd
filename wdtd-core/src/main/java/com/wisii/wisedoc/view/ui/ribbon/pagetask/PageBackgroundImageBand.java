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
package com.wisii.wisedoc.view.ui.ribbon.pagetask;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.ribbon.BackGroundImageList;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 简单页布局的背景设置
 * 
 * @author 闫舒寰
 * @version 1.0 2009/01/14
 */
public class PageBackgroundImageBand {
	public JRibbonBand getBand() {
		
		JRibbonBand pageBackgroundImageBand = new JRibbonBand(RibbonUIText.BODY_BACKGROUND_BAND,
				MediaResource.getResizableIcon("09379.ico"), null);
		
		//背景图片按钮
		JCommandButton backgroundimage = new JCommandButton(RibbonUIText.BODY_BACKGROUND_PICTURE_BUTTON,
				MediaResource.getResizableIcon("00931.ico"));
		backgroundimage.setPopupRichTooltip(new RichTooltip(RibbonUIText.BODY_BACKGROUND_PICTURE_BUTTON_TITLE,
				RibbonUIText.BODY_BACKGROUND_PICTURE_BUTTON_DESCRIPTION));
		backgroundimage.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundimage.setPopupCallback(new PopupPanelCallback() {
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new BackGroundImageList(ActionType.LAYOUT);
			}
		});
		pageBackgroundImageBand.addCommandButton(backgroundimage, RibbonElementPriority.TOP);
		//这里后面那个object是为了和实际绑定的功能按钮区分开类，这里绑定这个动作仅仅是借用其isAvailable方法来看用户是否选择了文档
		RibbonUIManager.getInstance().bind(Page.BODY_BACKGROUND_IMAGE_ACTION, backgroundimage, new Object());
		
		//背景颜色按钮
		JCommandButton backgroundColor = new JCommandButton(RibbonUIText.BODY_BACKGROUND_COLOR_BUTTON,
				MediaResource.getResizableIcon("00417.ico"));
		backgroundColor.setPopupRichTooltip(new RichTooltip(RibbonUIText.BODY_BACKGROUND_COLOR_BUTTON_TITLE,
				RibbonUIText.BODY_BACKGROUND_COLOR_BUTTON_DESCRIPTION));
		backgroundColor.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundColor.setPopupCallback(new PopupPanelCallback() {
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new BackGroundColor();
			}
		});
		pageBackgroundImageBand.addCommandButton(backgroundColor, RibbonElementPriority.TOP);
		RibbonUIManager.getInstance().bind(Page.BODY_BACKGROUND_COLOR_ACTION, backgroundColor);
		
		//背景重复按钮
		JCommandButton imageRepeat = new JCommandButton(RibbonUIText.BODY_BACKGROUND_PIC_REPEAT_BUTTON,
				MediaResource.getResizableIcon("00931new.ico"));
		imageRepeat.setActionRichTooltip(new RichTooltip(RibbonUIText.BODY_BACKGROUND_PIC_REPEAT_BUTTON_TITLE, RibbonUIText.BODY_BACKGROUND_PIC_REPEAT_BUTTON_DESCRIPTION));
		imageRepeat.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		imageRepeat.setPopupCallback(new PopupPanelCallback() {
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new ImageRepeat();
			}
		});
		pageBackgroundImageBand.addCommandButton(imageRepeat, RibbonElementPriority.MEDIUM);
		RibbonUIManager.getInstance().bind(Page.BODY_BACKGROUND_IMAGE_REPEAT_ACTION, imageRepeat);

		// bodyBackgroundPositionHorizontal
		JCommandButton bph = new JCommandButton(RibbonUIText.BODY_BACKGROUND_HORIZONTAL_BUTTON, MediaResource
				.getResizableIcon("00664.ico"));
		bph.setActionRichTooltip(new RichTooltip(RibbonUIText.BODY_BACKGROUND_HORIZONTAL_BUTTON_TITLE, RibbonUIText.BODY_BACKGROUND_HORIZONTAL_BUTTON_DESCRIPTION));
		bph.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		bph.setPopupCallback(new PopupPanelCallback() {
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new BackgroundHorizontal();
			}
		});
		pageBackgroundImageBand.addCommandButton(bph, RibbonElementPriority.MEDIUM);
		RibbonUIManager.getInstance().bind(Page.BODY_BACKGROUND_POSITION_HORIZONTAL, bph);

		// bodyBackgroundPositionVertical
		JCommandButton bpv = new JCommandButton(RibbonUIText.BODY_BACKGROUND_VERTICAL_BUTTON, MediaResource
				.getResizableIcon("00666.ico"));
		bpv.setActionRichTooltip(new RichTooltip(RibbonUIText.BODY_BACKGROUND_VERTICAL_BUTTON_TITLE,
				RibbonUIText.BODY_BACKGROUND_VERTICAL_BUTTON_DESCRIPTION));
		bpv.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		bpv.setPopupCallback(new PopupPanelCallback() {
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new BackgroundVertical();
			}
		});
		pageBackgroundImageBand.addCommandButton(bpv, RibbonElementPriority.MEDIUM);
		RibbonUIManager.getInstance().bind(Page.BODY_BACKGROUND_POSITION_VERTICAL, bpv);

		return pageBackgroundImageBand;
	}
	
	private class ImageRepeat extends JCommandPopupMenu {

		public ImageRepeat() {
			JCommandMenuButton repeat = new JCommandMenuButton(RibbonUIText.BACKGROUND_PIC_REPEAT_MENU[0],
					new EmptyResizableIcon(16));
			JCommandMenuButton repeatX = new JCommandMenuButton(RibbonUIText.BACKGROUND_PIC_REPEAT_MENU[1],
					new EmptyResizableIcon(16));
			JCommandMenuButton repeatY = new JCommandMenuButton(RibbonUIText.BACKGROUND_PIC_REPEAT_MENU[2],
					new EmptyResizableIcon(16));
			JCommandMenuButton repeatNo = new JCommandMenuButton(RibbonUIText.BACKGROUND_PIC_REPEAT_MENU[3],
					new EmptyResizableIcon(16));

			RibbonUIManager.getInstance().bind(
					Page.BODY_BACKGROUND_IMAGE_REPEAT_ACTION, repeat, repeatX,
					repeatY, repeatNo);

			this.addMenuButton(repeat);
			this.addMenuButton(repeatX);
			this.addMenuButton(repeatY);
			this.addMenuButton(repeatNo);
		}
	}

	private class BackgroundHorizontal extends JCommandPopupMenu {

		public BackgroundHorizontal() {
			JCommandMenuButton left = new JCommandMenuButton(RibbonUIText.BACKGROUND_HORIZONTAL_MENU[0],
					MediaResource.getResizableIcon("00664.ico"));
			JCommandMenuButton center = new JCommandMenuButton(RibbonUIText.BACKGROUND_HORIZONTAL_MENU[1],
					MediaResource.getResizableIcon("00668.ico"));
			JCommandMenuButton right = new JCommandMenuButton(RibbonUIText.BACKGROUND_HORIZONTAL_MENU[2],
					MediaResource.getResizableIcon("00665.ico"));

			RibbonUIManager.getInstance().bind(
					Page.BODY_BACKGROUND_POSITION_HORIZONTAL, left, center,	right);

			this.addMenuButton(left);
			this.addMenuButton(center);
			this.addMenuButton(right);
		}
	}

	private class BackgroundVertical extends JCommandPopupMenu {

		public BackgroundVertical() {
			JCommandMenuButton top = new JCommandMenuButton(RibbonUIText.BACKGROUND_VERTICAL_MENU[0],
					MediaResource.getResizableIcon("00666.ico"));
			JCommandMenuButton center = new JCommandMenuButton(RibbonUIText.BACKGROUND_VERTICAL_MENU[1],
					MediaResource.getResizableIcon("00669.ico"));
			JCommandMenuButton bottom = new JCommandMenuButton(RibbonUIText.BACKGROUND_VERTICAL_MENU[2],
					MediaResource.getResizableIcon("00667.ico"));

			RibbonUIManager.getInstance()
					.bind(Page.BODY_BACKGROUND_POSITION_VERTICAL, top, center, bottom);

			this.addMenuButton(top);
			this.addMenuButton(center);
			this.addMenuButton(bottom);
		}
	}

	// 背景颜色下拉菜单
	private class BackGroundColor extends JCommandPopupMenu {

		public BackGroundColor() {
			try {
				RibbonColorList colorBox = new RibbonColorList();
				RibbonUIManager.getInstance().bind(
						Page.BODY_BACKGROUND_COLOR_ACTION, colorBox);
				this.add(colorBox.getNullColorButton());
				this.add(colorBox.getPopupComponent());
				this.add(colorBox.getCustomColorButton());
			} catch (IncompatibleLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
