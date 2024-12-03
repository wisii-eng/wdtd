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

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class SetPageSequenceMasterBand
{

	public JRibbonBand getBand()
	{
		JRibbonBand band = new JRibbonBand(RibbonUIText.PAGE_BAND,
				MediaResource.getResizableIcon("09379.ico"), /* new PageLayout() */
				null);

		// 设置简单页布局序列
		JCommandButton setsimplepsm = new JCommandButton("设置简单页布局序列",
				MediaResource.getResizableIcon("01826.ico"));
		setsimplepsm.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Page.SET_SIMPLE_PAGE_SEQUENCE_MASTER_ACTION, setsimplepsm);
		band.addCommandButton(setsimplepsm, RibbonElementPriority.MEDIUM);

		// 设置复杂页布局序列
		JCommandButton setcomplexpsm = new JCommandButton("设置复杂页布局序列",
				MediaResource.getResizableIcon("01826.ico"));
		setcomplexpsm.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		band.addCommandButton(setcomplexpsm, RibbonElementPriority.MEDIUM);
		RibbonUIManager.getInstance().bind(
				Page.SET_COMPLEX_PAGE_SEQUENCE_MASTER_ACTION, setcomplexpsm);

		return band;
	}

}
