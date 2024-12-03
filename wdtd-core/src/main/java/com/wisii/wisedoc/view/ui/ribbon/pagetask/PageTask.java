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

package com.wisii.wisedoc.view.ui.ribbon.pagetask;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.view.ui.ribbon.WiseTask;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 页布局相关属性的task
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class PageTask implements WiseTask
{

	public RibbonTask getTask()
	{

		/**** 页布局属性面板 开始 *****************/

		// JRibbonBand psmband = new PageSequenceMasterBand().getBand();

		JRibbonBand psmBand = new SetPageSequenceMasterBand().getBand();

		// JRibbonBand pageLayoutBand = new PageBand().getBand();
		JRibbonBand pageMarginBand = new PageMarginBand().getBand();
		JRibbonBand pageBand = new NewPageBand().getBand();
		JRibbonBand bodyMarginBand = new BodyMarginBand().getBand();

		JRibbonBand bodyBand = new BodyBand().getBand();

		JRibbonBand pageBackgroundImageBand = new PageBackgroundImageBand()
				.getBand();
		// JRibbonBand layoutBand = new LayoutsBand().getLayoutsBand();
		// JRibbonBand pageSequenceBand = new RegionBand().getBand();

		RibbonTask pageTask = new RibbonTask(RibbonUIText.PAGE_TASK, /*
																	 * layoutBand,
																	 */
		psmBand, /* pageLayoutBand, */pageMarginBand, pageBand, bodyMarginBand,
				bodyBand, pageBackgroundImageBand/* , pageSequenceBand */);
		/**** 页布局属性面板 结束 *****************/

		return pageTask;
	}
}
