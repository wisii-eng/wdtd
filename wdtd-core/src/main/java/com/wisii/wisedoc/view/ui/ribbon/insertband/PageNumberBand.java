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
 * 插入页码面板
 * @author 闫舒寰
 * @version 1.0 2009/02/09
 */
public class PageNumberBand
{

	/**
	 * Gets the header footer band.
	 * 
	 * @return the header footer band
	 */
	public JRibbonBand getBand()
	{
		// 图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		JRibbonBand insertPageNumberBand = new JRibbonBand(RibbonUIText.PAGE_NUMBER_INSERT_BAND, MediaResource
				.getResizableIcon("Image.gif"), null);


		/*********** 添加页码 开始 *******************/
		// 添加条形码按钮
		JCommandButton insertPageNumberButton = new JCommandButton(RibbonUIText.PAGE_NUMBER_CURRNET_INSERT_BUTTON,
				MediaResource.getResizableIcon("PageNumber.gif"));
		RibbonUIManager.getInstance().bind(Defalult.INSERT_PAGENUMBER_ACTION,
				insertPageNumberButton);

		// 添加可下拉按钮
		insertPageNumberButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		// 鼠标浮动显示解释说明
		insertPageNumberButton.setActionRichTooltip(new RichTooltip(RibbonUIText.PAGE_NUMBER_CURRNET_INSERT_BUTTON_TITLE,
				RibbonUIText.PAGE_NUMBER_CURRNET_INSERT_BUTTON_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		insertPageNumberBand.addCommandButton(insertPageNumberButton,
				RibbonElementPriority.TOP);
		/*********** 添加页码 结束 *******************/

		/*********** 添加当前章节总页码 开始 add zhongyajun *******************/
		// 添加条形码按钮
		JCommandButton insertPsTotalPageNumberButton = new JCommandButton(
				RibbonUIText.PAGE_NUMBER_CHAPTER_INSERT_BUTTON, MediaResource.getResizableIcon("02825.ico"));
		RibbonUIManager.getInstance().bind(
				Defalult.INSERT_PSTOTALPAGENUMBER_ACTION,
				insertPsTotalPageNumberButton);

		// 添加可下拉按钮
		insertPsTotalPageNumberButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		// 鼠标浮动显示解释说明
		insertPsTotalPageNumberButton.setActionRichTooltip(new RichTooltip(RibbonUIText.PAGE_NUMBER_CHAPTER_INSERT_BUTTON_TITLE
				, RibbonUIText.PAGE_NUMBER_CHAPTER_INSERT_BUTTON_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		insertPageNumberBand.addCommandButton(insertPsTotalPageNumberButton,
				RibbonElementPriority.TOP);
		/*********** 添加总页码 结束 *******************/

		/*********** 添加文档总页码 开始 add zhongyajun *******************/
		// 添加条形码按钮
		JCommandButton insertTotalPageNumberButton = new JCommandButton(
				RibbonUIText.PAGE_NUMBER_TOTAL_INSERT_BUTTON, MediaResource.getResizableIcon("02825.ico"));
		RibbonUIManager.getInstance().bind(
				Defalult.INSERT_TOTALPAGENUMBER_ACTION,
				insertTotalPageNumberButton);

		// 添加可下拉按钮
		insertTotalPageNumberButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		// 鼠标浮动显示解释说明
		insertTotalPageNumberButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.PAGE_NUMBER_TOTAL_INSERT_BUTTON_TITLE, RibbonUIText.PAGE_NUMBER_TOTAL_INSERT_BUTTON_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		insertPageNumberBand.addCommandButton(insertTotalPageNumberButton,
				RibbonElementPriority.TOP);
		/*********** 添加总页码 结束 *******************/

		return insertPageNumberBand;
	}
}
