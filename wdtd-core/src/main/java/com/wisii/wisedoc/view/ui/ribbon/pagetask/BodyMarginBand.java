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

import org.jvnet.flamingo.ribbon.JRibbonBand;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class BodyMarginBand {
	
	public JRibbonBand getBand() {
		
		JRibbonBand bodyBand = new JRibbonBand(RibbonUIText.BODY_BORDER_LABEL, MediaResource.getResizableIcon("09379.ico"), null);
		
		//版心边距
		MarginPanel bodyBorderSpacePanel = new MarginPanel(RibbonUIText.BODY_BORDER_LABEL);
//		bodyBand.addPanel(mp);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		/*JRibbonComponent bodyBorderSpacePanelWrapper = new JRibbonComponent(bodyBorderSpacePanel);
		bodyBand.addRibbonComponent(bodyBorderSpacePanelWrapper);*/
		
		bodyBand.startGroup(RibbonUIText.BODY_BORDER_LABEL);
//		bodyBand.startGroup();
		bodyBand.addRibbonComponent(bodyBorderSpacePanel.getTopComponent());
		bodyBand.addRibbonComponent(bodyBorderSpacePanel.getBottomComponent());
		
		RibbonUIManager.getInstance().bind(Page.BODY_MARGIN_ACTION, bodyBorderSpacePanel.getFixedlengthSpinners());
		
		return bodyBand;
	}

}
