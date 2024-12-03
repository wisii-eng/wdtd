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
package com.wisii.wisedoc.view.ui.ribbon.condition;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.CONDITION_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.CONDITION_BLOCK;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.CONDITION_INLINE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.CONDITION_OBJECT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.CONDITION_PAGE_SEQUENCE;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;

/**
 * 显示条件设置
 * @author 钟亚军
 * @version 1.0 2009/03/23
 */
public class VisibleConditionBand implements WiseBand
{

	public JRibbonBand getBand()
	{
		// 条形码设置
		JRibbonBand visibleConditionBand = new JRibbonBand(CONDITION_BAND,
				MediaResource.getResizableIcon("09379.ico"), null);
		
		JCommandButton inlcondition = new JCommandButton(CONDITION_INLINE, MediaResource
				.getResizableIcon("00906.ico"));
		inlcondition.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SET_INLINECONDITION_ACTION,
				inlcondition);
		visibleConditionBand.addCommandButton(inlcondition,
				RibbonElementPriority.TOP);
		
		JCommandButton blocondition = new JCommandButton(CONDITION_BLOCK, MediaResource
				.getResizableIcon("00906.ico"));
		blocondition.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SET_BLOCKCONDITION_ACTION,
				blocondition);
		visibleConditionBand.addCommandButton(blocondition,
				RibbonElementPriority.TOP);
		
		JCommandButton objcondtion = new JCommandButton(CONDITION_OBJECT, MediaResource
				.getResizableIcon("00906.ico"));
		objcondtion.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SET_OBJECTCONDTION_ACTION,
				objcondtion);
		visibleConditionBand.addCommandButton(objcondtion,
				RibbonElementPriority.TOP);
		
		JCommandButton pscondtion = new JCommandButton(CONDITION_PAGE_SEQUENCE, MediaResource
				.getResizableIcon("00906.ico"));
		pscondtion.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SET_PAGESEQUENCECONDTION_ACTION,
				pscondtion);
		visibleConditionBand.addCommandButton(pscondtion,
				RibbonElementPriority.TOP);
		
		return visibleConditionBand;
	}
}
