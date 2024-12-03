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

package com.wisii.wisedoc.view.ui.ribbon.group;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class VisibleGroupPropertyBand implements WiseBand
{

	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand visibleGroupBand = new JRibbonBand(RibbonUIText.SET_GROUP,
				MediaResource.getResizableIcon("09379.ico"), null);
		JCommandButton currentinline = new JCommandButton(
				RibbonUIText.CURRENT_INLINE, MediaResource
						.getResizableIcon("00906.ico"));
		currentinline.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SET_CURRENT_INLINE,
				currentinline);
		visibleGroupBand.addCommandButton(currentinline,
				RibbonElementPriority.TOP);

		JCommandButton currentblock = new JCommandButton(
				RibbonUIText.CURRENT_BLOCK, MediaResource
						.getResizableIcon("00906.ico"));
		currentblock.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SET_CURRENT_BLOCK,
				currentblock);
		visibleGroupBand.addCommandButton(currentblock,
				RibbonElementPriority.TOP);

		JCommandButton currentobject = new JCommandButton(
				RibbonUIText.CURRENT_OBJECT, MediaResource
						.getResizableIcon("00906.ico"));
		currentobject.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SET_CURRENT_OBJECT,
				currentobject);
		visibleGroupBand.addCommandButton(currentobject,
				RibbonElementPriority.TOP);

		JCommandButton currentpagesequence = new JCommandButton(
				RibbonUIText.CURRENT_PAGE_SEQUENCE, MediaResource
						.getResizableIcon("00906.ico"));
		currentpagesequence.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SET_CURRENT_PAGE_SEQUENCE,
				currentpagesequence);
		visibleGroupBand.addCommandButton(currentpagesequence,
				RibbonElementPriority.TOP);

		JCommandButton currentdocument = new JCommandButton(
				RibbonUIText.CURRENT_WISEDOCUMENT, MediaResource
						.getResizableIcon("00906.ico"));
		currentdocument.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SET_CURRENT_WISEDOCEMENT,
				currentdocument);
		visibleGroupBand.addCommandButton(currentdocument,
				RibbonElementPriority.TOP);

		JCommandButton currentgroup = new JCommandButton(
				RibbonUIText.CURRENT_GROUP, MediaResource
						.getResizableIcon("00906.ico"));
		currentgroup.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SET_CURRENT_GROUP,
				currentgroup);
		visibleGroupBand.addCommandButton(currentgroup,
				RibbonElementPriority.TOP);

		return visibleGroupBand;
	}

}
