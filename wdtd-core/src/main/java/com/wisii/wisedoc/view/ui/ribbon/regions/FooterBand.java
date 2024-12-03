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
package com.wisii.wisedoc.view.ui.ribbon.regions;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.REGION_AFTER_OVER_FLOW_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.REGION_AFTER_OVER_FLOW_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.REGION_AFTER_OVER_FLOW_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.REGION_AFTER_REFERENCE_ORIENTATION_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.REGION_AFTER_REFERENCE_ORIENTATION_BUTTON_TITLE;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionAfter;
import com.wisii.wisedoc.view.ui.parts.regionsbg.RegionsBackGroundImageList;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;
/**
 * 页脚属性设置面板
 * @author 闫舒寰
 * @version 1.0 2009/03/18
 */
public class FooterBand {
	
	public JRibbonBand getBand() {
		
		JRibbonBand band = new JRibbonBand(RibbonUIText.WRITEMODE_REFERENCEORIENTATION_ALIGN_OVERFLOW_BACKGROUND, MediaResource.getResizableIcon("09379.ico"), null);
		
		//写作模式
		JCommandButton writingMode = new JCommandButton(RibbonUIText.TEXT_ORIENTATION_BUTTON_TITLE, MediaResource.getResizableIcon("02872.ico"));
		//添加可下拉按钮
		writingMode.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		//下拉菜单的按钮
		writingMode.setPopupCallback(new WritingMode());
		//鼠标浮动显示解释说明
		writingMode.setActionRichTooltip(new RichTooltip(RibbonUIText.TEXT_ORIENTATION_BUTTON_TITLE, RibbonUIText.TEXT_ORIENTATION_BUTTON_DESCRIPTION));
		//这个是在地方紧张的时候，显示的优先级
		band.addCommandButton(writingMode, RibbonElementPriority.MEDIUM);
		//和下拉菜单中的动作绑定以便更新界面
		RibbonUIManager.getInstance().bind(Page.BODY_TEXT_DIRECTION_ACTION, writingMode);
		
		//文字方向
		JCommandButton referenceOrientation = new JCommandButton(UiText.REFERENCE_ORIENTATION, MediaResource.getResizableIcon("00405.ico"));
		referenceOrientation.setPopupCallback(new ReferenceOrientation());
		referenceOrientation.setPopupRichTooltip(new RichTooltip(REGION_AFTER_REFERENCE_ORIENTATION_BUTTON_TITLE, REGION_AFTER_REFERENCE_ORIENTATION_BUTTON_DESCRIPTION));
		referenceOrientation.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		band.addCommandButton(referenceOrientation, RibbonElementPriority.MEDIUM);
		
		//内容对齐方式
		JCommandButton displayAlign = new JCommandButton(RibbonUIText.REGION_AFTER_DISPLAY_ALIGN_BUTTON, MediaResource.getResizableIcon("00664.ico"));
		displayAlign.setPopupCallback(new DisplayAlign());
		displayAlign.setPopupRichTooltip(new RichTooltip(RibbonUIText.BODY_DISPLAY_ALIGN_BUTTON_TITLE, RibbonUIText.BODY_DISPLAY_ALIGN_BUTTON_DESCRIPTION));
		displayAlign.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		band.addCommandButton(displayAlign, RibbonElementPriority.MEDIUM);
		RibbonUIManager.getInstance().bind(Page.BODY_DISPLAY_ALIGN, displayAlign);
		
		//溢出处理
		JCommandButton overflow = new JCommandButton(REGION_AFTER_OVER_FLOW_BUTTON, MediaResource.getResizableIcon("09636.ico"));
		overflow.setPopupCallback(new Overflow());
		overflow.setPopupRichTooltip(new RichTooltip(REGION_AFTER_OVER_FLOW_BUTTON_TITLE, REGION_AFTER_OVER_FLOW_BUTTON_DESCRIPTION));
		overflow.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		band.addCommandButton(overflow, RibbonElementPriority.MEDIUM);
		
		//背景图片按钮
		JCommandButton backgroundimage = new JCommandButton(RibbonUIText.BODY_BACKGROUND_PICTURE_BUTTON,
				MediaResource.getResizableIcon("00931.ico"));
		backgroundimage.setPopupRichTooltip(new RichTooltip(RibbonUIText.BODY_BACKGROUND_PICTURE_BUTTON_TITLE,
				RibbonUIText.BODY_BACKGROUND_PICTURE_BUTTON_DESCRIPTION));
		backgroundimage.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundimage.setPopupCallback(new PopupPanelCallback() {
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new RegionsBackGroundImageList(RegionAfter.INSTANCE);
			}
		});
		band.addCommandButton(backgroundimage, RibbonElementPriority.MEDIUM);
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
		band.addCommandButton(backgroundColor, RibbonElementPriority.MEDIUM);
		RibbonUIManager.getInstance().bind(Page.BODY_BACKGROUND_COLOR_ACTION, backgroundColor);
		
		return band;
	}
	
	/**
	 * 写作方式下拉菜单
	 */
	private class WritingMode extends JCommandPopupMenu	implements PopupPanelCallback {
		public WritingMode() {
			JCommandMenuButton lrtb = new JCommandMenuButton(RibbonUIText.PAGE_ORIENTATION_LRTB, MediaResource.getResizableIcon("02872.ico"));
			JCommandMenuButton rltb = new JCommandMenuButton(RibbonUIText.PAGE_ORIENTATION_RLTB, MediaResource.getResizableIcon("02872new.ico"));
			JCommandMenuButton tbrl = new JCommandMenuButton(RibbonUIText.PAGE_ORIENTATION_TBRL, MediaResource.getResizableIcon("02875.ico"));
			
			RibbonUIManager.getInstance().bind(RegionAfter.WRITING_MODE_ACTION, lrtb, rltb, tbrl);
			
			this.addMenuButton(lrtb);
			this.addMenuButton(rltb);
			this.addMenuButton(tbrl);
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new WritingMode();
		}
	}
	
	/**
	 * 对齐方式下拉菜单
	 */
	private class DisplayAlign extends JCommandPopupMenu implements PopupPanelCallback {
		
		public DisplayAlign() {
			JCommandMenuButton before = new JCommandMenuButton(RibbonUIText.BODY_DISPLAY_ALIGN_BEFORE, MediaResource.getResizableIcon("00666.ico"));
			JCommandMenuButton center = new JCommandMenuButton(RibbonUIText.BODY_DISPLAY_ALIGN_CENTER, MediaResource.getResizableIcon("00669.ico"));
			JCommandMenuButton after = new JCommandMenuButton(RibbonUIText.BODY_DISPLAY_ALIGN_AFTER, MediaResource.getResizableIcon("00667.ico"));
			
			RibbonUIManager.getInstance().bind(RegionAfter.DISPLAY_ALIGN_ACTION, before, center, after);
			
			this.addMenuButton(before);
			this.addMenuButton(center);
			this.addMenuButton(after);
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new DisplayAlign();
		}
	}
	
	/**
	 * 文字方向下拉菜单
	 */
	private class ReferenceOrientation extends JCommandPopupMenu implements PopupPanelCallback {
		
		public ReferenceOrientation() {
			
			JCommandMenuButton[] jmb = new JCommandMenuButton[UiText.PAGE_BODY_ORIENTATION_LIST.length];
			
			for (int i = 0; i < UiText.PAGE_BODY_ORIENTATION_LIST.length; i++) {
				jmb[i] = new JCommandMenuButton(UiText.PAGE_BODY_ORIENTATION_LIST[i], new EmptyResizableIcon(16));
			}
			RibbonUIManager.getInstance().bind(RegionAfter.REFERENCE_ORIENTATION_ACTION, jmb);
			for (JCommandMenuButton commandMenuButton : jmb) {
				this.addMenuButton(commandMenuButton);
			}
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new ReferenceOrientation();
		}
	}
	
	/**
	 * 溢出处理下拉菜单
	 */
	private class Overflow extends JCommandPopupMenu implements PopupPanelCallback {
		
		public Overflow() {
			
			JCommandMenuButton[] jmb = new JCommandMenuButton[UiText.PAGE_BODY_CONTENT_OVERFLOW_LIST.length];
			
			ResizableIcon[] icons = {MediaResource.getResizableIcon("09378.ico"),
					MediaResource.getResizableIcon("09379.ico")};
			
			for (int i = 0; i < jmb.length; i++) {
				jmb[i] = new JCommandMenuButton(UiText.PAGE_BODY_CONTENT_OVERFLOW_LIST[i], icons[i]);
			}
			
			RibbonUIManager.getInstance().bind(RegionAfter.OVERFLOW_ACTION, jmb);
			
			for (JCommandMenuButton commandMenuButton : jmb) {
				this.addMenuButton(commandMenuButton);
			}
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton) {
			return new Overflow();
		}
	}
	
	// 背景颜色下拉菜单
	private class BackGroundColor extends JCommandPopupMenu {

		public BackGroundColor() {
			try {
				RibbonColorList colorBox = new RibbonColorList();
				RibbonUIManager.getInstance().bind(
						RegionAfter.BACKGROUND_COLOR_ACTION, colorBox);
				this.add(colorBox.getNullColorButton());
				this.add(colorBox.getPopupComponent());
				this.add(colorBox.getCustomColorButton());
			} catch (IncompatibleLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
