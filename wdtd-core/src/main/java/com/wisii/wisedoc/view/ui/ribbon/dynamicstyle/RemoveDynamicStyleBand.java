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

package com.wisii.wisedoc.view.ui.ribbon.dynamicstyle;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class RemoveDynamicStyleBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	public JRibbonBand getBand()
	{
		JRibbonBand removeBand = new JRibbonBand(RibbonUIText.REMOVE_BAND, MediaResource.getResizableIcon("09379.ico"),
				null);

		JCommandButton inline = new JCommandButton(RibbonUIText.CURRENT_INLINE,
				MediaResource.getResizableIcon("00906.ico"));
		inline.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_CURRENT_INLINE_DYNAMIC_STYLE, inline);
		removeBand.addCommandButton(inline, RibbonElementPriority.TOP);

		JCommandButton block = new JCommandButton(RibbonUIText.CURRENT_BLOCK,
				MediaResource.getResizableIcon("00906.ico"));
		block.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_CURRENT_BLOCK_DYNAMIC_STYLE, block);
		removeBand.addCommandButton(block, RibbonElementPriority.TOP);

		JCommandButton object = new JCommandButton(RibbonUIText.CURRENT_OBJECT,
				MediaResource.getResizableIcon("00906.ico"));
		object.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_CURRENT_OBJECT_DYNAMIC_STYLE, object);
		removeBand.addCommandButton(object, RibbonElementPriority.TOP);

		JCommandButton pagesequence = new JCommandButton(
				RibbonUIText.CURRENT_PAGE_SEQUENCE, MediaResource
						.getResizableIcon("00906.ico"));
		pagesequence.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_CURRENT_PAGE_SEQUENCE_DYNAMIC_STYLE,
				pagesequence);
		removeBand.addCommandButton(pagesequence, RibbonElementPriority.TOP);

		JCommandButton document = new JCommandButton(
				RibbonUIText.CURRENT_WISEDOCUMENT, MediaResource
						.getResizableIcon("00906.ico"));
		document.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(
				Defalult.REMOVE_CURRENT_WISEDOCEMENT_DYNAMIC_STYLE, document);
		removeBand.addCommandButton(document, RibbonElementPriority.TOP);

		return removeBand;
	}

}
