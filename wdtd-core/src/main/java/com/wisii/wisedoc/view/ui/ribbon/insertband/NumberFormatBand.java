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
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 插入格式化数字面板
 * @author 闫舒寰
 * @version 1.0 2009/02/09
 */
public class NumberFormatBand
{

	public JRibbonBand getNumberFormatBand()
	{

		JRibbonBand numberFormatBand = new JRibbonBand(
				RibbonUIText.NUMBER_FORMAT_BAND, MediaResource
						.getResizableIcon("09379.ico"));

		JCommandButton dateFormat = new JCommandButton(
				RibbonUIText.DATE_TIME_BUTTON, MediaResource
						.getResizableIcon("00768.ico"));
		RibbonUIManager.getInstance().bind(Defalult.INSERT_DATETIME_ACTION,
				dateFormat);
		dateFormat.setActionRichTooltip(new RichTooltip(
				RibbonUIText.DATE_TIME_BUTTON_TITLE,
				RibbonUIText.DATE_TIME_BUTTON_DESCRIPTION));
		dateFormat.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);

		numberFormatBand
				.addCommandButton(dateFormat, RibbonElementPriority.TOP);

		JCommandButton numberFormat = new JCommandButton(
				RibbonUIText.NUMBER_FORMAT_BUTTON, MediaResource
						.getResizableIcon("05740.ico"));
		RibbonUIManager.getInstance().bind(Defalult.INSERT_NUMBERTEXT_ACTION,
				numberFormat);
		numberFormat.setActionRichTooltip(new RichTooltip(
				RibbonUIText.NUMBER_FORMAT_BUTTON_TITLE,
				RibbonUIText.NUMBER_FORMAT_BUTTON_DESCRIPTION));
		numberFormat.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);

		numberFormatBand.addCommandButton(numberFormat,
				RibbonElementPriority.TOP);
		// toc means table of contents目录按钮
		final JCommandButton posinline = new JCommandButton(RibbonUIText.POSITIONINLINE_BUTTON, MediaResource
				.getResizableIcon("01094.ico"));
		posinline.setActionRichTooltip(new RichTooltip(
				RibbonUIText.POSITIONINLINE_BUTTON_TITLE,
				RibbonUIText.POSITIONINLINE_BUTTON_DESCRIPTION));
		posinline.setPopupCallback(new PopupPanelCallback()
		{
			@Override
			public JPopupPanel getPopupPanel(final JCommandButton commandButton)
			{
				return new PositionNumberTypeList();
			}

		});
		posinline
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		numberFormatBand.addCommandButton(posinline,
				RibbonElementPriority.MEDIUM);

		// JCommandButton encrypttext = new JCommandButton("加密文本",
		// MediaResource.getResizableIcon("05740.ico"));
		// RibbonUIManager.getInstance().bind(Defalult.INSERT_ENCRYPTTEXT_ACTION,
		// encrypttext);
		// encrypttext.setActionRichTooltip(new RichTooltip("插入加密文本",
		// "加密文本中的源内容将会以加密的方式进行显示"));
		// encrypttext.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		// numberFormatBand.addCommandButton(encrypttext,
		// RibbonElementPriority.TOP);
		return numberFormatBand;
	}

	private class PositionNumberTypeList extends JCommandPopupMenu
	{
		public PositionNumberTypeList()
		{
			final JCommandMenuButton T_1 = new JCommandMenuButton(RibbonUIText.POSITIONINLINE_BUTTON_1,
					MediaResource.getResizableIcon("00012.ico"));
			RibbonUIManager.getInstance().bind(
					Defalult.INSERT_POSITIONINLINE_1_ACTION, T_1);
			this.addMenuButton(T_1);
			final JCommandMenuButton T_a = new JCommandMenuButton(RibbonUIText.POSITIONINLINE_BUTTON_a,
					MediaResource.getResizableIcon("00012.ico"));
			RibbonUIManager.getInstance().bind(
					Defalult.INSERT_POSITIONINLINE_a_ACTION, T_a);
			this.addMenuButton(T_a);
			final JCommandMenuButton T_A = new JCommandMenuButton(RibbonUIText.POSITIONINLINE_BUTTON_A,
					MediaResource.getResizableIcon("00012.ico"));
			RibbonUIManager.getInstance().bind(
					Defalult.INSERT_POSITIONINLINE_A_ACTION, T_A);
			this.addMenuButton(T_A);
			final JCommandMenuButton T_i = new JCommandMenuButton(RibbonUIText.POSITIONINLINE_BUTTON_i,
					MediaResource.getResizableIcon("00012.ico"));
			RibbonUIManager.getInstance().bind(
					Defalult.INSERT_POSITIONINLINE_i_ACTION, T_i);
			this.addMenuButton(T_i);
			final JCommandMenuButton T_I = new JCommandMenuButton(RibbonUIText.POSITIONINLINE_BUTTON_I,
					MediaResource.getResizableIcon("00012.ico"));
			RibbonUIManager.getInstance().bind(
					Defalult.INSERT_POSITIONINLINE_I_ACTION, T_I);
			this.addMenuButton(T_I);

		}
	}

}
