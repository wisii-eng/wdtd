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
 * @ChartTypeTask.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.view.ui.ribbon.WiseTask;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：用于展示具体类型的相关属性设置，如当前类型是饼状图，则
 * 展示饼状图相关的属性设置界面。
 * 
 * 作者：李晓光 创建日期：2009-5-21
 */
public class ChartTypeTask implements WiseTask {
	@Override
	public RibbonTask getTask() {
		final JRibbonBand pie = new PieChartBasicBand().getBand();
		final RibbonTask chartTypeTask = new RibbonTask(UiText.PIE_BASIC_TASK_TITLE, pie);
		return chartTypeTask;
	}
}
