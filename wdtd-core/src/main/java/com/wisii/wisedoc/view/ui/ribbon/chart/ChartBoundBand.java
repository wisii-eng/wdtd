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
 * @ChartBoundBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 用于设置统计图位置、大小。
 * 
 * 作者：李晓光 创建日期：2009-5-18
 */
public class ChartBoundBand {
	private final static String SIZE_TITLE = UiText.BOUND_BAND_TITLE;//MessageResource.getMessage("wisedoc.chart.bound.title");
	private final static String HEIGHT = UiText.BOUND_BAND_HEIGHT;//MessageResource.getMessage("wisedoc.chart.bound.height");
	private final static String WIGHT = UiText.BOUND_BAND_WIGHT;//MessageResource.getMessage("wisedoc.chart.bound.wight");
	public JRibbonBand getBand(){
		final JRibbonBand band = new JRibbonBand(SIZE_TITLE, new format_justify_left(), null);
		final FixedLengthSpinner heightValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
		RibbonUIManager.getInstance().bind(WisedocChart.BPD_ACTION, heightValue);//ActionID需要自己定义
		
		final FixedLengthSpinner widthValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
		RibbonUIManager.getInstance().bind(WisedocChart.IPD_ACTION, widthValue);
		final JRibbonComponent heightValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00541.ico"), HEIGHT, heightValue);
		final JRibbonComponent widthValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00542.ico"), WIGHT, widthValue);
		band.addRibbonComponent(heightValueWrapper);
		band.addRibbonComponent(widthValueWrapper);
		
		return band;
	}
}