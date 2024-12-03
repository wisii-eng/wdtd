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
package com.wisii.wisedoc.view.ui.ribbon.insertband;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 插入章节面板
 * @author 闫舒寰
 * @version 1.0 2009/02/09
 */
public class PagesequenceBand
{

	public JRibbonBand getBand()
	{

		// 图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		JRibbonBand pagesequenceBand = new JRibbonBand(RibbonUIText.INSERT_PAGE_SEQUENCE_BAND, MediaResource
				.getResizableIcon("Table.gif"), null);

		// 章节按钮
		JCommandButton insertPagesequenceButton = new JCommandButton(RibbonUIText.INSERT_PAGE_SEQUENCE_BUTTON,
				MediaResource.getResizableIcon("01947.ico"));
		RibbonUIManager.getInstance().bind(Defalult.INSERT_PAGE_SEQUENCE,
				insertPagesequenceButton);
		// 鼠标浮动显示解释说明
		insertPagesequenceButton.setActionRichTooltip(new RichTooltip(RibbonUIText.INSERT_PAGE_SEQUENCE_BUTTON_TITLE,
				RibbonUIText.INSERT_PAGE_SEQUENCE_BUTTON_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		pagesequenceBand.addCommandButton(insertPagesequenceButton,
				RibbonElementPriority.TOP);
		
		// 章节按钮
		JCommandButton insertZimobanButton = new JCommandButton(RibbonUIText.INSERT_ZIMOBAN_BUTTON,
				MediaResource.getResizableIcon("01947.ico"));
		RibbonUIManager.getInstance().bind(Defalult.INSERT_ZIMOBAN,
				insertZimobanButton);
		// 鼠标浮动显示解释说明
		insertZimobanButton.setActionRichTooltip(new RichTooltip(RibbonUIText.IINSERT_ZIMOBAN_TITLE,
				RibbonUIText.IINSERT_ZIMOBAN_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		pagesequenceBand.addCommandButton(insertZimobanButton,
				RibbonElementPriority.TOP);

		return pagesequenceBand;
	}
}
