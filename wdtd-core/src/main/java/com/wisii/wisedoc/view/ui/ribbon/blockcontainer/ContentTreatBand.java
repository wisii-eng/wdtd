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

package com.wisii.wisedoc.view.ui.ribbon.blockcontainer;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.BlockContainer;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

public class ContentTreatBand
{

	public JRibbonBand getBand()
	{

		JRibbonBand blockContainerOverFlowBand = new JRibbonBand(
				UiText.CONTENT_TREAT, MediaResource
						.getResizableIcon("09379.ico"), null);
		JCommandButton set = new JCommandButton(RibbonUIText.SET, MediaResource
				.getResizableIcon("00906.ico"));
		set.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				BlockContainer.OVERFLOW_OTHER_ACTION, set);
		blockContainerOverFlowBand.addCommandButton(set,
				RibbonElementPriority.TOP);

		JCommandButton clear = new JCommandButton(RibbonUIText.CLEAR,
				MediaResource.getResizableIcon("00906.ico"));
		clear.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				BlockContainer.CLEAR_OVERFLOW_OTHER_ACTION, clear);
		blockContainerOverFlowBand.addCommandButton(clear,
				RibbonElementPriority.TOP);

		return blockContainerOverFlowBand;

	}
}
