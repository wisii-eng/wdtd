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

package com.wisii.wisedoc.view.ui.ribbon.datatreat;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Datatreat;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class SimpleDataTransformBand implements WiseBand
{

	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand visibleGroupBand = new JRibbonBand(
				RibbonUIText.DATA_TRANSFORM, MediaResource
						.getResizableIcon("09379.ico"), null);

		JCommandButton currentinline = new JCommandButton(
				RibbonUIText.SET_SIMPLE_DATA_TRANSFORM_TABLE, MediaResource
						.getResizableIcon("00906.ico"));
		currentinline.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Datatreat.SET_SIMPLE_DATA_TRANSFORM_TABLE, currentinline);
		visibleGroupBand.addCommandButton(currentinline,
				RibbonElementPriority.TOP);

		JCommandButton remove = new JCommandButton(
				RibbonUIText.REMOVE_SIMPLE_DATA_TRANSFORM_TABLE, MediaResource
						.getResizableIcon("00906.ico"));
		remove.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Datatreat.REMOVE_SIMPLE_DATA_TRANSFORM_TABLE, remove);
		visibleGroupBand.addCommandButton(remove, RibbonElementPriority.TOP);

		return visibleGroupBand;
	}

}