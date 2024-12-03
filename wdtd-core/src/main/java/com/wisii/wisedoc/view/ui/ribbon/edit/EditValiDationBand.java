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
 * @EditValiDationBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionID;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Edit;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：验证属性设置面板
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-15
 */
public class EditValiDationBand implements WiseBand
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand validationband = new JRibbonBand(RibbonUIText.VALIDATION,
				MediaResource.getResizableIcon("09379.ico"));
		final JCommandButton onblurbutton = new JCommandButton(
				RibbonUIText.VALIDATION_ONBLUR, MediaResource
						.getResizableIcon("03466.ico"));
		RibbonUIManager.getInstance().bind(Edit.EDIT_ONBLURVALIDATION_ACTION,
				onblurbutton);
		// 复制按钮的弹出式下拉菜单
		onblurbutton.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(final JCommandButton arg0)
			{
				return new SetList(Edit.EDIT_ONBLURVALIDATION_ACTION,
						Edit.REMOVE_ONBLURVALIDATION_ACTION);
			}
		});
		onblurbutton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		validationband.addCommandButton(onblurbutton,
				RibbonElementPriority.MEDIUM);
		final JCommandButton oneditbutton = new JCommandButton(
				RibbonUIText.VALIDATION_ONEDIT, MediaResource
						.getResizableIcon("03466.ico"));
		RibbonUIManager.getInstance().bind(Edit.EDIT_ONEDITVALIDATION_ACTION,
				oneditbutton);
		// 复制按钮的弹出式下拉菜单
		oneditbutton.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(final JCommandButton arg0)
			{
				return new SetList(Edit.EDIT_ONEDITVALIDATION_ACTION,
						Edit.REMOVE_ONEDITVALIDATION_ACTION);
			}
		});
		oneditbutton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		validationband.addCommandButton(oneditbutton,
				RibbonElementPriority.MEDIUM);
		final JCommandButton onresultbutton = new JCommandButton(
				RibbonUIText.VALIDATION_ONRESULT, MediaResource
						.getResizableIcon("03466.ico"));
		RibbonUIManager.getInstance().bind(Edit.EDIT_ONRESULTVALIDATION_ACTION,
				onresultbutton);
		// 复制按钮的弹出式下拉菜单
		onresultbutton.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(final JCommandButton arg0)
			{
				return new SetList(Edit.EDIT_ONRESULTVALIDATION_ACTION,
						Edit.REMOVE_ONRESULTVALIDATION_ACTION);
			}
		});
		onresultbutton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		validationband.addCommandButton(onresultbutton,
				RibbonElementPriority.MEDIUM);
		return validationband;
	}

	// 弹出式下拉菜单项目
	private class SetList extends JCommandPopupMenu
	{

		public SetList(Enum<? extends ActionID> validationaction,
				Enum<? extends ActionID> removevalidationaction)
		{
			final JCommandMenuButton validationsetbutton = new JCommandMenuButton(
					RibbonUIText.EDIT_SET, MediaResource
							.getResizableIcon("00223.ico"));
			RibbonUIManager.getInstance().bind(validationaction,
					validationsetbutton);

			final JCommandMenuButton removevalibutton = new JCommandMenuButton(
					RibbonUIText.EDIT_REMOVE, MediaResource
							.getResizableIcon("00219.ico"));
			RibbonUIManager.getInstance().bind(removevalidationaction,
					removevalibutton);
			this.addMenuButton(validationsetbutton);
			this.addMenuButton(removevalibutton);
		}
	}
}
