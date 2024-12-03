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
 * @RemoveConditionBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.condition;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.CONDITION_REMOVE_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.CONDITION_REMOVE_BLOCK;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.CONDITION_REMOVE_INLINE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.CONDITION_REMOVE_OBJECT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.CONDITION_REMOVE_PAGE_SEQUENCE;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-12-9
 */
public class RemoveConditionBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	public JRibbonBand getBand()
	{
		// 条形码设置
		JRibbonBand removeBand = new JRibbonBand(CONDITION_REMOVE_BAND, MediaResource.getResizableIcon("09379.ico"),
				null);
		JCommandButton removeinlcondition = new JCommandButton(CONDITION_REMOVE_INLINE,
				MediaResource.getResizableIcon("00906.ico"));
		removeinlcondition.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_INLINECONDITION_ACTION, removeinlcondition);
		removeBand.addCommandButton(removeinlcondition,
				RibbonElementPriority.TOP);
		JCommandButton removeblocondition = new JCommandButton(CONDITION_REMOVE_BLOCK,
				MediaResource.getResizableIcon("00906.ico"));
		removeblocondition.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_BLOCKCONDITION_ACTION, removeblocondition);
		removeBand.addCommandButton(removeblocondition,
				RibbonElementPriority.TOP);
		JCommandButton removeobjcondtion = new JCommandButton(CONDITION_REMOVE_OBJECT,
				MediaResource.getResizableIcon("00906.ico"));
		removeobjcondtion.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_OBJECTCONDTION_ACTION, removeobjcondtion);
		removeBand.addCommandButton(removeobjcondtion,
				RibbonElementPriority.TOP);

		JCommandButton removepscondtion = new JCommandButton(CONDITION_REMOVE_PAGE_SEQUENCE,
				MediaResource.getResizableIcon("00906.ico"));
		removepscondtion.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_PAGESEQUENCECONDTION_ACTION, removepscondtion);
		removeBand
				.addCommandButton(removepscondtion, RibbonElementPriority.TOP);

		return removeBand;
	}
}
