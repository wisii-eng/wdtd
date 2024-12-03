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

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_BACKGROUND_COLOR_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_BACKGROUND_COLOR_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_BACKGROUND_COLOR_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_FILL_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_JOIN_STROKE_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_JOIN_STROKE_BUTTON_DESCRPITION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_JOIN_STROKE_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_LINE_JOIN_STROKE_LIST;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.SVG_GRAPH_TRANSPARENCY;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
/**
 * 图形样式面板
 * @author 闫舒寰
 * @version 1.0 2009/02/27
 */
public class GraphStyleBand {
	
	public JRibbonBand getBand() {
		

		
		final JRibbonBand band = new JRibbonBand(SVG_GRAPH_FILL_BAND,
				new format_justify_left(), null);
		ColorComboBox backgroundColorbox=null;
		try
		{
			backgroundColorbox = new ColorComboBox();
		} catch (IncompatibleLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final JRibbonComponent backgroundColorWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00417.ico"), SVG_GRAPH_BACKGROUND_COLOR_BUTTON, backgroundColorbox);
		backgroundColorWrapper.setRichTooltip(new RichTooltip(SVG_GRAPH_BACKGROUND_COLOR_BUTTON_TITLE,
				SVG_GRAPH_BACKGROUND_COLOR_BUTTON_DESCRIPTION));
		band.addRibbonComponent(backgroundColorWrapper);
		WiseSpinner alaphvalue = new WiseSpinner(new SpinnerNumberModel(1.0, 0.0, 1.0, 0.1));
		alaphvalue.setPreferredSize(new Dimension(40, 22));
		final JRibbonComponent alaphWrapper = new JRibbonComponent(MediaResource.getResizableIcon("05874.ico"),
				SVG_GRAPH_TRANSPARENCY, alaphvalue);
		band.addRibbonComponent(alaphWrapper);
		WiseCombobox lineJoinStroke =new WiseCombobox(new DefaultComboBoxModel(SVG_GRAPH_LINE_JOIN_STROKE_LIST));
		final JRibbonComponent lineJoinStrokeWrapper = new JRibbonComponent(MediaResource.getResizableIcon("01115.ico"),SVG_GRAPH_LINE_JOIN_STROKE_BUTTON,lineJoinStroke);
		lineJoinStrokeWrapper.setRichTooltip(new RichTooltip(SVG_GRAPH_LINE_JOIN_STROKE_BUTTON_TITLE,
				SVG_GRAPH_LINE_JOIN_STROKE_BUTTON_DESCRPITION));
		band.addRibbonComponent(lineJoinStrokeWrapper);
		RibbonUIManager.getInstance().bind(SvgGraphic.FILL_COLOR_ACTION, backgroundColorbox);
		RibbonUIManager.getInstance().bind(SvgGraphic.STROKE_LINE_JOIN, lineJoinStroke);
		RibbonUIManager.getInstance().bind(SvgGraphic.OPACITY_ACTION, alaphvalue);
		return band;
	}
}
