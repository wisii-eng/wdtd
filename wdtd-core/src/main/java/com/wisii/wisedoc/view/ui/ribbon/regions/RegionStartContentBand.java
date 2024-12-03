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

import java.awt.FlowLayout;

import javax.swing.JPanel;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionStart;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：删除左区域内容面板
 * 
 * 作者：钟亚军 创建日期：2010-1-7
 */
public class RegionStartContentBand
{

	public JRibbonBand getBand()
	{
		JRibbonBand band = new JRibbonBand(RibbonUIText.CONTENT,
				new format_justify_left(), null);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JCommandButton deletestaticcontent = new JCommandButton(
				RibbonUIText.DELETE_REGION_CONTENT, MediaResource
						.getResizableIcon("01088.ico"));
		deletestaticcontent.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(RegionStart.DELETE_CONTENT_ACTION,
				deletestaticcontent);
		panel.add(deletestaticcontent);
		JRibbonComponent panelWrapper = new JRibbonComponent(panel);
		band.addRibbonComponent(panelWrapper);
		return band;
	}
}
