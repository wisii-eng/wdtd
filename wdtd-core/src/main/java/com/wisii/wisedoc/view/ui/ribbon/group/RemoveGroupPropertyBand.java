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

public class RemoveGroupPropertyBand implements WiseBand
{

	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand removeGroupBand = new JRibbonBand(
				RibbonUIText.REMOVE_GROUP, MediaResource.getResizableIcon("09379.ico"), null);
		JCommandButton currentInline = new JCommandButton(
				RibbonUIText.CURRENT_INLINE, MediaResource
						.getResizableIcon("00906.ico"));
		currentInline.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.REMOVE_CURRENT_INLINE,
				currentInline);
		removeGroupBand.addCommandButton(currentInline,
				RibbonElementPriority.TOP);

		JCommandButton currentBlock = new JCommandButton(
				RibbonUIText.CURRENT_BLOCK, MediaResource
						.getResizableIcon("00906.ico"));
		currentBlock.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.REMOVE_CURRENT_BLOCK,
				currentBlock);
		removeGroupBand.addCommandButton(currentBlock,
				RibbonElementPriority.TOP);

		JCommandButton currentObject = new JCommandButton(
				RibbonUIText.CURRENT_OBJECT, MediaResource
						.getResizableIcon("00906.ico"));
		currentObject.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.REMOVE_CURRENT_OBJECT,
				currentObject);
		removeGroupBand.addCommandButton(currentObject,
				RibbonElementPriority.TOP);

		JCommandButton currentPagesequence = new JCommandButton(
				RibbonUIText.CURRENT_PAGE_SEQUENCE, MediaResource
						.getResizableIcon("00906.ico"));
		currentPagesequence.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_CURRENT_PAGE_SEQUENCE, currentPagesequence);
		removeGroupBand.addCommandButton(currentPagesequence,
				RibbonElementPriority.TOP);

		JCommandButton currentDocument = new JCommandButton(
				RibbonUIText.CURRENT_WISEDOCUMENT, MediaResource
						.getResizableIcon("00906.ico"));
		currentDocument.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_CURRENT_WISEDOCEMENT, currentDocument);
		removeGroupBand.addCommandButton(currentDocument,
				RibbonElementPriority.TOP);

		JCommandButton currentGroup = new JCommandButton(
				RibbonUIText.CURRENT_GROUP, MediaResource
						.getResizableIcon("00906.ico"));
		currentGroup.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.REMOVE_CURRENT_GROUP,
				currentGroup);
		removeGroupBand.addCommandButton(currentGroup,
				RibbonElementPriority.TOP);

		return removeGroupBand;
	}

}
