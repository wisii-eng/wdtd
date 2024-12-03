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

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_CANVAS_BAND_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_CANVAS_HEIGHT_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_CANVAS_HEIGHT_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_CANVAS_HEIGHT_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_CANVAS_WIDTH_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_CANVAS_WIDTH_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_CANVAS_WIDTH_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LAYER;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;

import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 画布大小按钮
 * @author 闫舒寰
 * @version 1.0 2009/03/02
 */
public class CanvasSizeBand {
	
	FixedLengthSpinner heightValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
	FixedLengthSpinner widthValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
	public JRibbonBand getBand() {
		
		JRibbonBand band = new JRibbonBand(SVG_GRAPH_CANVAS_BAND_TITLE,
				new format_justify_left(), null);

		band.startGroup();
		
		JRibbonComponent heightValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00541.ico"), SVG_GRAPH_CANVAS_HEIGHT_BUTTON, heightValue);
		heightValueWrapper.setKeyTip("PL");

		RichTooltip heightValueTooltip = new RichTooltip();
		heightValueTooltip.setTitle(SVG_GRAPH_CANVAS_HEIGHT_BUTTON_TITLE);
		heightValueTooltip.addDescriptionSection(SVG_GRAPH_CANVAS_HEIGHT_BUTTON_DESCRIPTION);
		heightValueWrapper.setRichTooltip(heightValueTooltip);

		band.addRibbonComponent(heightValueWrapper);

		JRibbonComponent widthValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00542.ico"), SVG_GRAPH_CANVAS_WIDTH_BUTTON, widthValue);
		widthValueWrapper.setKeyTip("PR");

		RichTooltip widthValueTooltip = new RichTooltip();
		widthValueTooltip.setTitle(SVG_GRAPH_CANVAS_WIDTH_BUTTON_TITLE);
		widthValueTooltip.addDescriptionSection(SVG_GRAPH_CANVAS_WIDTH_BUTTON_DESCRIPTION);
		widthValueWrapper.setRichTooltip(widthValueTooltip);

		band.addRibbonComponent(widthValueWrapper);
		
		RibbonUIManager.getInstance().bind(SvgGraphic.CANVAS_HEIGHT, heightValue);
		RibbonUIManager.getInstance().bind(SvgGraphic.CANVAS_WIDTH, widthValue);
		WiseCombobox layervalue = new WiseCombobox(new DefaultComboBoxModel(UiText.COMMON_COLOR_LAYER));
		layervalue.setPreferredSize(new Dimension(70,20));
		RibbonUIManager.getInstance().bind(SvgGraphic.LAYER_ACTION, layervalue);
		JRibbonComponent layer = new JRibbonComponent(MediaResource.getResizableIcon("06184.ico"),
				SVG_GRAPH_LAYER, layervalue);
		band.addRibbonComponent(layer);
		return band;
	}
}
