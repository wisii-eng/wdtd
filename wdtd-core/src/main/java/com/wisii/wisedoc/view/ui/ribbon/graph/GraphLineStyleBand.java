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

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_COLOR_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_COLOR_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_COLOR_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_STYLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_STYLE_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_STYLE_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_STYLE_LIST;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_STYLE_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_WIDTH_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_WIDTH_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_WIDTH_BUTTON_TITLE;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;

/**
 * svg图形线条样式面板
 * @author 闫舒寰
 * @version 1.0 2009/03/06
 */
public class GraphLineStyleBand {
	
	FixedLengthSpinner lineWidth = new FixedLengthSpinner();
	WiseCombobox lineStyle = new WiseCombobox();
	JComboBox color_comboBox;
	
	public JRibbonBand getBand() {
		
		final JRibbonBand band = new JRibbonBand(SVG_GRAPH_LINE_STYLE_BAND,
				new format_justify_left(), null);
		
		lineWidth.setPreferredSize(new Dimension(70, 22));

//		band.startGroup("线条样式");
		final JRibbonComponent heightValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00692.ico"), SVG_GRAPH_LINE_WIDTH_BUTTON, lineWidth);
		heightValueWrapper.setKeyTip("PL");

		final RichTooltip heightValueTooltip = new RichTooltip();
		heightValueTooltip.setTitle(SVG_GRAPH_LINE_WIDTH_BUTTON_TITLE);
		heightValueTooltip.addDescriptionSection(SVG_GRAPH_LINE_WIDTH_BUTTON_DESCRIPTION);
		heightValueWrapper.setRichTooltip(heightValueTooltip);

		band.addRibbonComponent(heightValueWrapper);
		
		lineStyle.setModel(new DefaultComboBoxModel(SVG_GRAPH_LINE_STYLE_LIST));
		final JRibbonComponent widthValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("01054.ico"), SVG_GRAPH_LINE_STYLE, lineStyle);
		widthValueWrapper.setKeyTip("PR");

		final RichTooltip widthValueTooltip = new RichTooltip();
		widthValueTooltip.setTitle(SVG_GRAPH_LINE_STYLE_TITLE);
		widthValueTooltip.addDescriptionSection(SVG_GRAPH_LINE_STYLE_DESCRIPTION);
		widthValueWrapper.setRichTooltip(widthValueTooltip);

		band.addRibbonComponent(widthValueWrapper);
		
		//颜色设置
		try {
			color_comboBox = new ColorComboBox();
		} catch (final IncompatibleLookAndFeelException e) {
			e.printStackTrace();
		}
		color_comboBox.setPreferredSize(new Dimension(70,22));
		final JRibbonComponent colorWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("03078.ico"), SVG_GRAPH_LINE_COLOR_BUTTON, color_comboBox);
		colorWrapper.setKeyTip("PR");

		final RichTooltip colorTooltip = new RichTooltip();
		colorTooltip.setTitle(SVG_GRAPH_LINE_COLOR_BUTTON_TITLE);
		colorTooltip.addDescriptionSection(SVG_GRAPH_LINE_COLOR_BUTTON_DESCRIPTION);
		colorWrapper.setRichTooltip(colorTooltip);

		band.addRibbonComponent(colorWrapper);
		
		RibbonUIManager.getInstance().bind(SvgGraphic.LINE_WIDTH_ACTION, lineWidth);
		RibbonUIManager.getInstance().bind(SvgGraphic.LINE_TYPE_ACTION, lineStyle);
		RibbonUIManager.getInstance().bind(SvgGraphic.LINE_COLOR_ACTION, color_comboBox);

		return band;
	}
}
