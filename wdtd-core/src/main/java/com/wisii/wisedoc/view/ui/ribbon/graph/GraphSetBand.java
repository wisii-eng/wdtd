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

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LAYER;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_ROTATION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_SET_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_TRANSPARENCY;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 图形属性设置面板
 * @author 闫舒寰
 * @version 1.0 2009/02/27
 */
public class GraphSetBand {
	
	public JRibbonBand getBand()
	{
		JRibbonBand band = new JRibbonBand(SVG_GRAPH_SET_BAND, MediaResource.getResizableIcon("09379.ico"), null);
		
		WiseSpinner orientationValue = new WiseSpinner();
		SpinnerNumberModel directionModel = new SpinnerNumberModel(0, -360, 360, 10);
		orientationValue.setModel(directionModel);
		JRibbonComponent games = new JRibbonComponent(MediaResource.getResizableIcon("03458.ico"),
				SVG_GRAPH_ROTATION, orientationValue);
		band.addRibbonComponent(games);
		RibbonUIManager.getInstance().bind(SvgGraphic.ORIENTATION_ACTION, orientationValue);
		
		WiseCombobox layervalue = new WiseCombobox(new DefaultComboBoxModel(UiText.COMMON_COLOR_LAYER));
		layervalue.setPreferredSize(new Dimension(70,20));
		RibbonUIManager.getInstance().bind(SvgGraphic.LAYER_ACTION, layervalue);
		JRibbonComponent layer = new JRibbonComponent(MediaResource.getResizableIcon("06184.ico"),
				SVG_GRAPH_LAYER, layervalue);
		band.addRibbonComponent(layer);
		
		WiseSpinner alaphvalue = new WiseSpinner();
		alaphvalue.setPreferredSize(new Dimension(70, 20));
		SpinnerNumberModel bottomModel = new SpinnerNumberModel(1.0, 0.0, 1.0, 0.1);
		alaphvalue.setModel(bottomModel);
		RibbonUIManager.getInstance().bind(SvgGraphic.OPACITY_ACTION, alaphvalue);
		JRibbonComponent apaph = new JRibbonComponent(MediaResource.getResizableIcon("05874.ico"),
				SVG_GRAPH_TRANSPARENCY, alaphvalue);
		band.addRibbonComponent(apaph);
		
		
//		FixedLengthSpinner lineWidth = new FixedLengthSpinner();
//		lineWidth.setPreferredSize(new Dimension(70, 20));
//		SpinnerNumberModel lineWidthModel = new SpinnerNumberModel(0.0, 0.0, null, 0.5);
//		lineWidth.setModel(lineWidthModel);
////		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_ALAPH_ACTION, alaphvalue);
//		JRibbonComponent line = new JRibbonComponent(MediaResource.getResizableIcon("00692.ico"),
//				"线宽", lineWidth);
//		band.addRibbonComponent(line);
		
//		FixedLengthSpinner radius = new FixedLengthSpinner();
//		radius.setPreferredSize(new Dimension(70, 20));
//		SpinnerNumberModel radiusModel = new SpinnerNumberModel(0.0, 0.0, null, 0.5);
//		radius.setModel(radiusModel);
////		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_ALAPH_ACTION, alaphvalue);
//		JRibbonComponent radiusComponent = new JRibbonComponent(MediaResource.getResizableIcon("01119.ico"),
//				"半径", radius);
//		band.addRibbonComponent(radiusComponent);
		
		return band;
	}
}
