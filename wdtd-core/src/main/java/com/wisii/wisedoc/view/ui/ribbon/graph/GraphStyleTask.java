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

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.view.ui.ribbon.WiseTask;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 形状样式动态面板
 * @author 闫舒寰
 * @version 1.0 2009/02/27
 */
public class GraphStyleTask implements WiseTask {

	@Override
	public RibbonTask getTask() {
		
		JRibbonBand canvasSizeBand = new CanvasSizeBand().getBand();
		JRibbonBand graphSizeBand = new GraphSizeBand().getBand();
		JRibbonBand	lineBand = new GraphLineStyleBand().getBand();
//		JRibbonBand graphSetBand = new GraphSetBand().getBand();
		JRibbonBand arrowStyleBand = new GraphArrowBand().getBand();
		
		JRibbonBand graphStyleBand = new GraphStyleBand().getBand();
		
		JRibbonBand graphRecBand = new GraphRectangleStyle().getBand();
		JRibbonBand toolband = new GraphToolBand().getBand();
		RibbonTask task = new RibbonTask(RibbonUIText.SVG_GRAPH_STYLE, canvasSizeBand, graphSizeBand, lineBand, arrowStyleBand, graphRecBand, graphStyleBand,toolband);
		return task;
	}
}