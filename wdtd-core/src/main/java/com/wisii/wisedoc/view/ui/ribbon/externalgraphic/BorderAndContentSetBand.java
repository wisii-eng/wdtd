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
 * @BorderAndContentSetBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.externalgraphic;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ExternalGraphic;
import com.wisii.wisedoc.view.ui.parts.dialogs.BorderAndBackGroundDialog;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：表框和内容设置
 * 
 * 作者：zhangqiang 创建日期：2008-12-23
 */
public class BorderAndContentSetBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	public JRibbonBand getBand()
	{
		JRibbonBand band = new JRibbonBand(RibbonUIText.PICTURE_BORDER_CONTENT_BAND_TITLE, MediaResource.getResizableIcon("09379.ico"), null);
		
		JCommandButton content = new JCommandButton(RibbonUIText.PICTURE_CHANGE_BUTTON, MediaResource
				.getResizableIcon("04224.ico"));
		content.setActionRichTooltip(new RichTooltip(RibbonUIText.PICTURE_CHANGE_BUTTON_TITLE, RibbonUIText.PICTURE_CHANGE_BUTTON_DESCRIPTION));
		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_SRCURL_ACTION, content);
		
		JCommandButton border = new JCommandButton(RibbonUIText.PICTURE_BORDER_BUTTON, MediaResource
				.getResizableIcon("03466.ico"));
		border.setActionRichTooltip(new RichTooltip(RibbonUIText.PICTURE_BORDER_BUTTON_TITLE, RibbonUIText.PICTURE_BORDER_BUTTON_DESCRIPTION));
		border.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		border.addActionListener(new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				new BorderAndBackGroundDialog(ActionType.GRAPH);
			}
		});
		
		JCommandButton reset = new JCommandButton(RibbonUIText.PICTURE_RESET_BUTTON, MediaResource
				.getResizableIcon("01362.ico"));
		RibbonUIManager.getInstance().bind(ExternalGraphic.RESET_ACTION, reset);
		
		band.addCommandButton(content, RibbonElementPriority.TOP);
		band.addCommandButton(border, RibbonElementPriority.TOP);
		band.addCommandButton(reset, RibbonElementPriority.TOP);
		
		return band;
	}
}
