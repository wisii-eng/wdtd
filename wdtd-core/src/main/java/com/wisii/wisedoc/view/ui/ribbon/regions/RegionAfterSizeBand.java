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

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.REGION_AFTER_PRECEDENCE;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionAfter;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 页脚面板中的页眉页脚大小面板
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/18
 */
public class RegionAfterSizeBand
{

	// 整个面板更换，包括标题、图标（更改高度图标，去掉优先级图标） by钟亚军
	public JRibbonBand getBand()
	{
		JRibbonBand band = new JRibbonBand(RibbonUIText.HEIGHT_AND_PRECEDENCE,
				new format_justify_left(), null);
		JPanel mainpanel = new JPanel(new GridLayout(2, 1));
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		FixedLengthSpinner extent = new FixedLengthSpinner(
				new SpinnerFixedLengthModel(null, new FixedLength(0d, "mm"),
						null, -1));
		RibbonUIManager.getInstance().bind(RegionAfter.EXTENT_ACTION, extent);
		JRibbonComponent startextentWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00541.ico"),
				RibbonUIText.HEIGHT_COLON, extent);
		panel.add(startextentWrapper);
		JPanel paneltwo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JCheckBox footerCheck = new JCheckBox(REGION_AFTER_PRECEDENCE);
		RibbonUIManager.getInstance().bind(RegionAfter.PRECEDENCE_ACTION,
				footerCheck);
		paneltwo.add(footerCheck);
		mainpanel.add(panel);
		mainpanel.add(paneltwo);
		JRibbonComponent panelWrapper = new JRibbonComponent(mainpanel);
		band.addRibbonComponent(panelWrapper);
		return band;
	}
}
