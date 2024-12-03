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

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_RECTANGLE_STYLE_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_RECTANGLE_STYLE_GROUP;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_RECTANGLE_STYLE_RX_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_RECTANGLE_STYLE_RX_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_RECTANGLE_STYLE_RX_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_RECTANGLE_STYLE_RY_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_RECTANGLE_STYLE_RY_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_RECTANGLE_STYLE_RY_BUTTON_TITLE;

import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;

/**
 * 矩形圆角设置面板
 * @author 闫舒寰
 * @version 1.0 2009/03/09
 */
public class GraphRectangleStyle {
	
	FixedLengthSpinner rxSpinner = new FixedLengthSpinner();
	FixedLengthSpinner rySpinner = new FixedLengthSpinner();
	
	public JRibbonBand getBand()
	{
		JRibbonBand band = new JRibbonBand(SVG_GRAPH_RECTANGLE_STYLE_BAND, MediaResource.getResizableIcon("09379.ico"), null);
		
		band.startGroup(SVG_GRAPH_RECTANGLE_STYLE_GROUP);
		
		JRibbonComponent rxButton = new JRibbonComponent(MediaResource.getResizableIcon("03458.ico"),
				SVG_GRAPH_RECTANGLE_STYLE_RX_BUTTON, rxSpinner);
		rxButton.setRichTooltip(new RichTooltip(SVG_GRAPH_RECTANGLE_STYLE_RX_BUTTON_TITLE,
				SVG_GRAPH_RECTANGLE_STYLE_RX_BUTTON_DESCRIPTION));
		band.addRibbonComponent(rxButton);
		
		RibbonUIManager.getInstance().bind(SvgGraphic.RECTANGLE_RX, rxSpinner);
		
		
		JRibbonComponent ryButton = new JRibbonComponent(MediaResource.getResizableIcon("05874.ico"),
				SVG_GRAPH_RECTANGLE_STYLE_RY_BUTTON, rySpinner);
		ryButton.setRichTooltip(new RichTooltip(SVG_GRAPH_RECTANGLE_STYLE_RY_BUTTON_TITLE,
				SVG_GRAPH_RECTANGLE_STYLE_RY_BUTTON_DESCRIPTION));
		band.addRibbonComponent(ryButton);
		
		RibbonUIManager.getInstance().bind(SvgGraphic.RECTANGLE_RY, rySpinner);
		
		return band;
	}

}
