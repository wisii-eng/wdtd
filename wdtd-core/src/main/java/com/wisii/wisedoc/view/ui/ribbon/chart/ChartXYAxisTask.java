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
 * @ChartXYAxisTask.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.view.ui.ribbon.WiseTask;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：如果统计图带有坐标轴，则此类提供了坐标轴相关信息的设置功能。
 * 
 * 
 * 作者：李晓光 创建日期：2009-5-20
 */
public class ChartXYAxisTask implements WiseTask {
	private final static String TITLE = UiText.XY_AXIS_TASK_TITLE;//MessageResource.getMessage("wisedoc.chart.axis.title");
	@Override
	public RibbonTask getTask() {
		/* 设置显示/隐藏坐标线、零基线、3D效果、值。 */
		final JRibbonBand showOrHidden = new ChartShowOrHiddenBand().getBand();
		/* 用于设置坐标系标签【值坐标系标签、域坐标系标签】 */
		final JRibbonBand axisLabel = new ChartAxisLabelBand().getBand();
		/* 用于设置值坐标轴上，刻度范围、刻度跨度 */
		final JRibbonBand aduate = new ChartYAxisgrAduateBand().getBand();
		/* 定义指示标签相关信息，标签字体、标签对应的颜色、标签位置 
		final JRibbonBand indicator = new ChartIndicatorLabelBand().getBand();*/
		
		final RibbonTask chartAxisTask = new RibbonTask(TITLE, showOrHidden, axisLabel, aduate/*, indicator*/);
		return chartAxisTask;
	}

}
