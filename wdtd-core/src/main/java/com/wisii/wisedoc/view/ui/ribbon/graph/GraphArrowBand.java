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
package com.wisii.wisedoc.view.ui.ribbon.graph;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_ARROW_END_STYLE_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_ARROW_END_STYLE_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_ARROW_END_STYLE_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_ARROW_START_STYLE_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_ARROW_START_STYLE_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_ARROW_START_STYLE_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_ARROW_STYLE_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_ARROW_STYLE_LIST;

import javax.swing.DefaultComboBoxModel;

import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;

/**
 * 箭头样式面板
 * @author 闫舒寰
 * @version 1.0 2009/03/09
 */
public class GraphArrowBand {
	
	WiseCombobox startArrowStyle = new WiseCombobox();
	
	WiseCombobox endArrowStyle = new WiseCombobox();
	
	public JRibbonBand getBand() {
		
		final JRibbonBand band = new JRibbonBand(SVG_GRAPH_ARROW_STYLE_BAND,
				new format_justify_left(), null);
		
		band.startGroup(SVG_GRAPH_ARROW_STYLE_BAND);
		
		startArrowStyle.setModel(new DefaultComboBoxModel(SVG_GRAPH_ARROW_STYLE_LIST));
		
		final JRibbonComponent heightValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("01142.ico"), SVG_GRAPH_ARROW_START_STYLE_BUTTON, startArrowStyle);
		heightValueWrapper.setKeyTip("PL");
		
		RibbonUIManager.getInstance().bind(SvgGraphic.ARROW_START_STYLE, startArrowStyle);

		final RichTooltip heightValueTooltip = new RichTooltip();
		heightValueTooltip.setTitle(SVG_GRAPH_ARROW_START_STYLE_BUTTON_TITLE);
		heightValueTooltip.addDescriptionSection(SVG_GRAPH_ARROW_START_STYLE_BUTTON_DESCRIPTION);
		heightValueWrapper.setRichTooltip(heightValueTooltip);

		band.addRibbonComponent(heightValueWrapper);
		
		endArrowStyle.setModel(new DefaultComboBoxModel(SVG_GRAPH_ARROW_STYLE_LIST));
		
		final JRibbonComponent endArrowStyleWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00692.ico"), SVG_GRAPH_ARROW_END_STYLE_BUTTON, endArrowStyle);
		endArrowStyleWrapper.setKeyTip("PL");

		RibbonUIManager.getInstance().bind(SvgGraphic.ARROW_END_STYLE, endArrowStyle);
		
		final RichTooltip endArrowStyleTooltip = new RichTooltip();
		endArrowStyleTooltip.setTitle(SVG_GRAPH_ARROW_END_STYLE_BUTTON_TITLE);
		endArrowStyleTooltip.addDescriptionSection(SVG_GRAPH_ARROW_END_STYLE_BUTTON_DESCRIPTION);
		endArrowStyleWrapper.setRichTooltip(endArrowStyleTooltip);

		band.addRibbonComponent(endArrowStyleWrapper);
		
		return band;
	}

}
