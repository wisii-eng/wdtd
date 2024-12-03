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
 * @DrawSettingBand.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.basicband;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.svg.transcoded.edit_find;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-12-29
 */
public class DrawSettingBand
{
	public JRibbonBand getBand()
	{
		final JRibbonBand layerBand = new JRibbonBand(RibbonUIText.DRAW_SETTING_BAND,
				new edit_find());
		final JCommandToggleButton drawnullborder = new JCommandToggleButton(
				RibbonUIText.DRAW_SETTING_NULLBORDER_BUTTON, /*new edit_clear()*/MediaResource
						.getResizableIcon("00151.ico"));
		final RichTooltip drawnullborderTooltip = new RichTooltip();
		drawnullborderTooltip.setTitle(RibbonUIText.DRAW_SETTING_NULLBORDER_TITLE);
		drawnullborderTooltip
				.addDescriptionSection(RibbonUIText.DRAW_SETTING_NULLBORDER_DESCRIPTION);
		drawnullborder.setActionRichTooltip(drawnullborderTooltip);
		drawnullborder.getActionModel().setSelected(
				ConfigureUtil.isDrawnullborder());
		drawnullborder.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				ConfigureUtil.setDrawnullborder(drawnullborder.getActionModel()
						.isSelected());

			}
		});
		layerBand.addCommandButton(drawnullborder, RibbonElementPriority.LOW);

		final JCommandToggleButton drawlinebreak = new JCommandToggleButton(
				RibbonUIText.DRAW_SETTING_LINEBREAK_BUTTON, /*new edit_clear()*/MediaResource
						.getResizableIcon("linebreak.gif"));
		final RichTooltip drawlinebreakTooltip = new RichTooltip();
		drawlinebreakTooltip.setTitle(RibbonUIText.DRAW_SETTING_LINEBREAK_TITLE);
		drawlinebreakTooltip
				.addDescriptionSection(RibbonUIText.DRAW_SETTING_LINEBREAK_DESCRIPTION);
		drawlinebreak.setActionRichTooltip(drawlinebreakTooltip);
		drawlinebreak.getActionModel().setSelected(
				ConfigureUtil.isDrawlinebreak());
		drawlinebreak.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				ConfigureUtil.setDrawlinebreak(drawlinebreak.getActionModel()
						.isSelected());

			}
		});
		layerBand.addCommandButton(drawlinebreak, RibbonElementPriority.LOW);
		return layerBand;
	}
}
