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

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_HEIGHT_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_HEIGHT_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_HEIGHT_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_POSITION_GROUP;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_SIZE_BAND_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_SIZE_GROUP;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_START_POINT_X_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_START_POINT_X_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_START_POINT_X_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_START_POINT_Y_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_START_POINT_Y_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_START_POINT_Y_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_WIDTH_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_WIDTH_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_WIDTH_BUTTON_TITLE;

import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;

/**
 * 图形的大小
 * @author 闫舒寰
 * @version 1.0 2009/02/27
 */
public class GraphSizeBand {
	
	FixedLengthSpinner xValue = new FixedLengthSpinner();
	FixedLengthSpinner yValue = new FixedLengthSpinner();
	FixedLengthSpinner heightValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
	FixedLengthSpinner widthValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
	public JRibbonBand getBand() {
		JRibbonBand band = new JRibbonBand(SVG_GRAPH_SIZE_BAND_TITLE,
				new format_justify_left(), null);
		
		band.startGroup(SVG_GRAPH_POSITION_GROUP);
		JRibbonComponent xValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00867.ico"), SVG_GRAPH_START_POINT_X_BUTTON, xValue);
		xValueWrapper.setKeyTip("PL");
		
		RibbonUIManager.getInstance().bind(SvgGraphic.X_ACTION, xValue);

		RichTooltip xValueTooltip = new RichTooltip();
		xValueTooltip.setTitle(SVG_GRAPH_START_POINT_X_BUTTON_TITLE);
		xValueTooltip.addDescriptionSection(SVG_GRAPH_START_POINT_X_BUTTON_DESCRIPTION);
		xValueWrapper.setRichTooltip(xValueTooltip);

		band.addRibbonComponent(xValueWrapper);

		JRibbonComponent yValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00867left.ico"), SVG_GRAPH_START_POINT_Y_BUTTON, yValue);
		yValueWrapper.setKeyTip("PR");
		
		RibbonUIManager.getInstance().bind(SvgGraphic.Y_ACTION, yValue);

		RichTooltip yValueTooltip = new RichTooltip();
		yValueTooltip.setTitle(SVG_GRAPH_START_POINT_Y_BUTTON_TITLE);
		yValueTooltip.addDescriptionSection(SVG_GRAPH_START_POINT_Y_BUTTON_DESCRIPTION);
		yValueWrapper.setRichTooltip(yValueTooltip);

		band.addRibbonComponent(yValueWrapper);

		band.startGroup(SVG_GRAPH_SIZE_GROUP);
		JRibbonComponent heightValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00541.ico"), SVG_GRAPH_HEIGHT_BUTTON, heightValue);
		heightValueWrapper.setKeyTip("PL");

		RibbonUIManager.getInstance().bind(SvgGraphic.HEIGHT_ACTION, heightValue);
		
		RichTooltip heightValueTooltip = new RichTooltip();
		heightValueTooltip.setTitle(SVG_GRAPH_HEIGHT_BUTTON_TITLE);
		heightValueTooltip.addDescriptionSection(SVG_GRAPH_HEIGHT_BUTTON_DESCRIPTION);
		heightValueWrapper.setRichTooltip(heightValueTooltip);

		band.addRibbonComponent(heightValueWrapper);

		JRibbonComponent widthValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00542.ico"), SVG_GRAPH_WIDTH_BUTTON, widthValue);
		widthValueWrapper.setKeyTip("PR");
		
		RibbonUIManager.getInstance().bind(SvgGraphic.WIDTH_ACTION, widthValue);

		RichTooltip widthValueTooltip = new RichTooltip();
		widthValueTooltip.setTitle(SVG_GRAPH_WIDTH_BUTTON_TITLE);
		widthValueTooltip.addDescriptionSection(SVG_GRAPH_WIDTH_BUTTON_DESCRIPTION);
		widthValueWrapper.setRichTooltip(widthValueTooltip);

		band.addRibbonComponent(widthValueWrapper);

		return band;
	}
}
