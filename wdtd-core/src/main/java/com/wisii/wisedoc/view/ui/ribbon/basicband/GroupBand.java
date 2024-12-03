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
package com.wisii.wisedoc.view.ui.ribbon.basicband;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.GROUP_BAND;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.GROUP_CANCEL_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.GROUP_SET_BUTTON;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.svg.transcoded.edit_find;
import com.wisii.wisedoc.view.ui.svg.transcoded.edit_find_replace;
import com.wisii.wisedoc.view.ui.svg.transcoded.system_search;

/**
 * 组属性面板
 * @author 闫舒寰
 * @version 1.0 2009/03/24
 */
public class GroupBand {
	
	public JRibbonBand getBand() {
		JRibbonBand band = new JRibbonBand(GROUP_BAND, new edit_find());
		
		JCommandButton groupButton = new JCommandButton(GROUP_SET_BUTTON,
				new system_search());
		band.addCommandButton(groupButton, RibbonElementPriority.MEDIUM);
        RibbonUIManager.getInstance().bind(ActionFactory.Defalult.GROUP_ACTION, groupButton);
		JCommandButton ungroupButton = new JCommandButton(GROUP_CANCEL_BUTTON, new edit_find_replace());
		band.addCommandButton(ungroupButton, RibbonElementPriority.MEDIUM);
		   RibbonUIManager.getInstance().bind(ActionFactory.Defalult.UNGROUP_ACTION, ungroupButton);
		return band;
	}

}
