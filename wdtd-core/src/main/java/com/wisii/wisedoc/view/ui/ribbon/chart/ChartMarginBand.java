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
 * @ChartMarginBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import org.jvnet.flamingo.ribbon.JRibbonBand;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.ribbon.pagetask.MarginPanel;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;


/**
 * 用于设置统计图内部边距。
 * 
 * 作者：李晓光 创建日期：2009-5-19
 */
public class ChartMarginBand {
	private final static String TITLE = UiText.MARGIN_BAND_TITLE;//MessageResource.getMessage("wisedoc.chart.margin.title");
public JRibbonBand getBand() {
		
		final JRibbonBand bodyBand = new JRibbonBand(TITLE, MediaResource.getResizableIcon("09379.ico"), null);
		
		//版心边距
		final MarginPanel bodyBorderSpacePanel = new MarginPanel(RibbonUIText.BODY_BORDER_LABEL);
		bodyBand.addRibbonComponent(bodyBorderSpacePanel.getTopComponent());
		bodyBand.addRibbonComponent(bodyBorderSpacePanel.getBottomComponent());
		RibbonUIManager.getInstance().bind(WisedocChart.MARGIN_ACTION, bodyBorderSpacePanel.getFixedlengthSpinners());
		return bodyBand;
	}
}
