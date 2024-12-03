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
package com.wisii.wisedoc.view.ui.actions.pagelayout;

import java.awt.event.ActionEvent;

import org.jvnet.flamingo.common.JCommandButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.PageSequenceLayoutDialog;
import com.wisii.wisedoc.view.ui.parts.dialogs.PageSequenceLayoutDialog.PSMLayoutType;

/**
 * 编辑页布局序列动作
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class PageEditSequenceMasterLayoutAction extends Actions {

	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JCommandButton) {
			new PageSequenceLayoutDialog(PSMLayoutType.editLayout);
		}
	}
	
	@Override
	public boolean isAvailable() {
		
		if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType) == null) {
			return false;
		}
		
		if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_PAGE_SEQUENCE_MASTER) == null) {
			return false;
		} else {
			return true;
		}
	}
}
