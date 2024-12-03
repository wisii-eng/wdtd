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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel.SPMLayoutType;
import com.wisii.wisedoc.view.ui.parts.dialogs.SimplePageMasterDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

public class NewPageBand
{

	public JRibbonBand getBand()
	{

		JRibbonBand bodyBand = new JRibbonBand(RibbonUIText.PAGE_BAND,
				MediaResource.getResizableIcon("09379.ico"), null);

		// 纸张方向
		JCommandButton pageOrientationButton = new JCommandButton(
				RibbonUIText.PAGE_ORIENTATION_BUTTON, MediaResource
						.getResizableIcon("00013.ico"));
		pageOrientationButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.PAGE_ORIENTATION_BUTTON_TITLE,
				RibbonUIText.PAGE_ORIENTATION_BUTTON_DESCRIPTION));
		pageOrientationButton.setPopupCallback(new PopupPanelCallback()
		{

			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton)
			{
				return new PageOrientation();
			}

		});
		pageOrientationButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		bodyBand.addCommandButton(pageOrientationButton,
				RibbonElementPriority.MEDIUM);
		RibbonUIManager.getInstance().bind(Page.ORIENTATION_ACTION,
				pageOrientationButton);

		// 纸张大小
		JCommandButton pageSizeButton = new JCommandButton(
				RibbonUIText.PAGE_SIZE_BUTTON, MediaResource
						.getResizableIcon("09379.ico"));
		pageSizeButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.PAGE_SIZE_BUTTON_TITLE,
				RibbonUIText.PAGE_SIZE_BUTTON_DESCRIPTION));
		pageSizeButton.setPopupCallback(new PopupPanelCallback()
		{

			public JPopupPanel getPopupPanel(JCommandButton arg0)
			{
				return new PageSizePopupMenue();
			}
		});
		pageSizeButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		bodyBand.addCommandButton(pageSizeButton, RibbonElementPriority.MEDIUM);
		RibbonUIManager.getInstance().bind(Page.PAPER_SIZE_ACTION,
				pageSizeButton);

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
		RibbonUIManager.getInstance().bind(Page.TEXT_DIRECTION_ACTION,
				textDirectionActionButton);

		return bodyBand;
	}

	@SuppressWarnings("serial")
	private class PageOrientation extends JCommandPopupMenu
	{

		public PageOrientation()
		{
			JCommandMenuButton vertical = new JCommandMenuButton(
					RibbonUIText.PAGE_ORIENTATION_VERTICAL, MediaResource
							.getResizableIcon("06259.ico"));
			this.addMenuButton(vertical);
			JCommandMenuButton horizontal = new JCommandMenuButton(
					RibbonUIText.PAGE_ORIENTATION_HORIZONTAL, MediaResource
							.getResizableIcon("06258.ico"));
			this.addMenuButton(horizontal);

			RibbonUIManager.getInstance().bind(Page.ORIENTATION_ACTION,
					vertical, horizontal);
		}
	}

	// 纸张大小下拉菜单
	@SuppressWarnings("serial")
	private class PageSizePopupMenue extends JCommandPopupMenu
	{

		public PageSizePopupMenue()
		{
			List<Object> pageSizeMenue = new ArrayList<Object>();

			for (int i = 0; i < UiText.PAGE_PAPER_FORMAT_LIST.length; i++)
			{
				pageSizeMenue.add(new JCommandMenuButton(
						UiText.PAGE_PAPER_FORMAT_LIST[i],
						new EmptyResizableIcon(16)));
			}

			for (Iterator<Object> iterator = pageSizeMenue.iterator(); iterator
					.hasNext();)
			{
				JCommandMenuButton menuItem = (JCommandMenuButton) iterator
						.next();
				this.addMenuButton(menuItem);
			}

			RibbonUIManager.getInstance().bind(Page.PAPER_SIZE_ACTION,
					pageSizeMenue.toArray());

			this.addMenuSeparator();

			JCommandMenuButton detailSet = new JCommandMenuButton(
					RibbonUIText.PAGE_SIZE_DETAIL_MENU, new EmptyResizableIcon(
							16));
			detailSet.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					new SimplePageMasterDialog(SPMLayoutType.setFO);
				}
			});
			this.addMenuButton(detailSet);
		}
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

			RibbonUIManager.getInstance().bind(Page.TEXT_DIRECTION_ACTION,
					lrtb, rltb, tbrl);
			// lrtb.getActionModel().setSelected(true);

			this.addMenuButton(lrtb);
			this.addMenuButton(rltb);
			this.addMenuButton(tbrl);
		}
	}
}
