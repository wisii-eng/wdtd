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
 * @ChartBasicTask.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.view.ui.ribbon.WiseTask;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：当选择统计图控件时，用于在工具栏展示相关的属性设置。
 * 【统计图的位置】、【统计图的大小】、【颜色设置】
 * 
 * 作者：李晓光 创建日期：2009-5-18
 */
public class ChartBasicTask implements WiseTask {
	private final static String BASIC = UiText.BASIC_TASK_LABEL;//MessageResource.getMessage("wisedoc.chart.basic.task");
	@Override
	public RibbonTask getTask() {
		/* 设置统计图大小 */
		final JRibbonBand boundBand  = new ChartBoundBand().getBand();
		/* 设置内边距 */
		final JRibbonBand marginBand = new ChartMarginBand().getBand();
		/*final JFlowRibbonBand style = new BarcodeTextStyleBand().getBand();*/
		/* 设置统计图标题 */
		final JRibbonBand title = new ChartTitleBand().getBand();
		/* 用于设置统计图值个数、系列数，个系列采用的颜色、文本及样式。 */
		final JRibbonBand count = new SeriesAndValueCountBand().getBand();
		/* 设置系列标签的字体、角度 */
//		final JRibbonBand style = new ChartSeriesStyleBand().getBand();
		/* 定义指示标签相关信息，标签字体、标签对应的颜色、标签位置 */
		final JRibbonBand indicator = new ChartIndicatorLabelBand().getBand();
		/* 边框、底纹、背景前景透明、层 */
		final JRibbonBand border = new ChartBorderAndBackgroundBand().getBand();
		final RibbonTask chartBasicTask = new RibbonTask(BASIC, boundBand, marginBand, title, count, indicator, border);
		return chartBasicTask;
	}
}
