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
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 版心区域属性band
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/05
 */
public class BodyBand
{

	public JRibbonBand getBand()
	{

		JRibbonBand bodyBand = new JRibbonBand(RibbonUIText.BODY_BAND,
				MediaResource.getResizableIcon("09379.ico"), null);

		// 文字方向
		JCommandButton textDirectionActionButton = new JCommandButton(
				RibbonUIText.TEXT_ORIENTATION_BUTTON, MediaResource
						.getResizableIcon("00782.ico"));
		// 添加可下拉按钮
		textDirectionActionButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		// 下拉菜单的按钮
		textDirectionActionButton.setPopupCallback(new PopupPanelCallback()
		{

			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton)
			{
				return new TextOrientation();
			}
		});
		// 鼠标浮动显示解释说明
		textDirectionActionButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.TEXT_ORIENTATION_BUTTON_TITLE,
				RibbonUIText.TEXT_ORIENTATION_BUTTON_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		bodyBand.addCommandButton(textDirectionActionButton,
				RibbonElementPriority.MEDIUM);
		// bodyPropStrip.add(textDirectionActionButton);
		// JRibbonComponent textDirectionActionButtonWrapper = new
		// JRibbonComponent(textDirectionActionButton);
		// 和下拉菜单中的动作绑定以便更新界面
		RibbonUIManager.getInstance().bind(Page.BODY_TEXT_DIRECTION_ACTION,
				textDirectionActionButton);

		// 版心内容对齐方式
		JCommandButton displayAlign = new JCommandButton(
				RibbonUIText.BODY_DISPLAY_ALIGN_BUTTON, MediaResource
						.getResizableIcon("00664.ico"));
		displayAlign.setPopupCallback(new DisplayAlign());
		displayAlign.setPopupRichTooltip(new RichTooltip(
				RibbonUIText.BODY_DISPLAY_ALIGN_BUTTON_TITLE,
				RibbonUIText.BODY_DISPLAY_ALIGN_BUTTON_DESCRIPTION));
		displayAlign.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		bodyBand.addCommandButton(displayAlign, RibbonElementPriority.MEDIUM);
		// bodyPropStrip.add(displayAlign);
		// JRibbonComponent displayAlignWrapper = new
		// JRibbonComponent(displayAlign);
		RibbonUIManager.getInstance().bind(Page.BODY_DISPLAY_ALIGN,
				displayAlign);

		// 分栏个数
		JCommandButton pageColumnButton = new JCommandButton(
				RibbonUIText.PAGE_COLUMN_BUTTON, MediaResource
						.getResizableIcon("00009.ico"));
		pageColumnButton.setPopupRichTooltip(new RichTooltip(
				RibbonUIText.PAGE_COLUMN_BUTTON_TITLE,
				RibbonUIText.PAGE_COLUMN_BUTTON_DESCRIPTION));
		pageColumnButton.setPopupCallback(new PopupPanelCallback()
		{

			public JPopupPanel getPopupPanel(JCommandButton arg0)
			{
				return new PageColumn();
			}
		});
		pageColumnButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		bodyBand.addCommandButton(pageColumnButton,
				RibbonElementPriority.MEDIUM);
		// bodyPropStrip.add(pageColumnButton);
		// JRibbonComponent pageColumnButtonWrapper = new
		// JRibbonComponent(pageColumnButton);
		RibbonUIManager.getInstance().bind(Page.COLUMN_COUNT_ACTION,
				pageColumnButton);

		// 以Ribbon组件对象包装当前组件，然后以流式排版排列
		/*
		 * bodyBand.addRibbonComponent(textDirectionActionButtonWrapper);
		 * bodyBand.addRibbonComponent(displayAlignWrapper);
		 * bodyBand.addRibbonComponent(pageColumnButtonWrapper);
		 */

		// 溢出处理
		/*
		 * JCommandButton overFlow = new JCommandButton("溢出处理",
		 * MediaResource.getResizableIcon("00009.ico"));
		 * bodyBand.addCommandButton(overFlow, RibbonElementPriority.LOW);
		 */

		return bodyBand;
		// return bodyBorderSpacePanel.getParagraphBand();
	}

	@SuppressWarnings("serial")
	private class TextOrientation extends JCommandPopupMenu
	{

		public TextOrientation()
		{
			// JCommandToggleButton tbrl = new JCommandToggleButton("", );
			JCommandMenuButton lrtb = new JCommandMenuButton(
					RibbonUIText.PAGE_ORIENTATION_LRTB, MediaResource
							.getResizableIcon("02872.ico"));
			JCommandMenuButton rltb = new JCommandMenuButton(
					RibbonUIText.PAGE_ORIENTATION_RLTB, MediaResource
							.getResizableIcon("02872new.ico"));
			JCommandMenuButton tbrl = new JCommandMenuButton(
					RibbonUIText.PAGE_ORIENTATION_TBRL, MediaResource
							.getResizableIcon("02875.ico"));

			RibbonUIManager.getInstance().bind(Page.BODY_TEXT_DIRECTION_ACTION,
					lrtb, rltb, tbrl);
			// lrtb.getActionModel().setSelected(true);

			this.addMenuButton(lrtb);
			this.addMenuButton(rltb);
			this.addMenuButton(tbrl);
		}
	}

	@SuppressWarnings("serial")
	private class DisplayAlign extends JCommandPopupMenu implements
			PopupPanelCallback
	{

		public DisplayAlign()
		{
			JCommandMenuButton before = new JCommandMenuButton(
					RibbonUIText.BODY_DISPLAY_ALIGN_BEFORE, MediaResource
							.getResizableIcon("00666.ico"));
			JCommandMenuButton center = new JCommandMenuButton(
					RibbonUIText.BODY_DISPLAY_ALIGN_CENTER, MediaResource
							.getResizableIcon("00669.ico"));
			JCommandMenuButton after = new JCommandMenuButton(
					RibbonUIText.BODY_DISPLAY_ALIGN_AFTER, MediaResource
							.getResizableIcon("00667.ico"));

			RibbonUIManager.getInstance().bind(Page.BODY_DISPLAY_ALIGN, before,
					center, after);

			this.addMenuButton(before);
			this.addMenuButton(center);
			this.addMenuButton(after);
		}

		@Override
		public JPopupPanel getPopupPanel(JCommandButton commandButton)
		{
			return new DisplayAlign();
		}
	}

	// 弹出式下拉菜单项目
	@SuppressWarnings("serial")
	private class PageColumn extends JCommandPopupMenu
	{

		public PageColumn()
		{
			JCommandMenuButton oneColumn = new JCommandMenuButton(
					RibbonUIText.PAGE_COLUMN_ONE, new EmptyResizableIcon(16));
			JCommandMenuButton twoColumn = new JCommandMenuButton(
					RibbonUIText.PAGE_COLUMN_TWO, new EmptyResizableIcon(16));
			JCommandMenuButton threeColumn = new JCommandMenuButton(
					RibbonUIText.PAGE_COLUMN_THREE, new EmptyResizableIcon(16));

			RibbonUIManager.getInstance().bind(Page.COLUMN_COUNT_ACTION,
					oneColumn, twoColumn, threeColumn);

			this.addMenuButton(oneColumn);
			this.addMenuButton(twoColumn);
			this.addMenuButton(threeColumn);
		}
	}

}
