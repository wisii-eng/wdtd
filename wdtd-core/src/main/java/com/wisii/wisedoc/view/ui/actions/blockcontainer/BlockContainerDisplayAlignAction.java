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
package com.wisii.wisedoc.view.ui.actions.blockcontainer;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jvnet.flamingo.common.JCommandMenuButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 块容器内文字对齐方式动作
 * @author 闫舒寰
 * @version 1.0 2009/02/04
 */
public class BlockContainerDisplayAlignAction extends Actions {

	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JCommandMenuButton) {
			
			Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager.getUIComponents().get(this.getActionID());
			
			Iterator<List<Object>> iterator = set.get(ActionCommandType.RIBBON_ACTION).iterator();
			List<Object> list = iterator.next();
			setProperty(list.indexOf(e.getSource()));
		}
	}
	
	private void setProperty(int index){
		switch (index) {
		case 0:
			//letter
			setFOProperty(Constants.PR_DISPLAY_ALIGN, new EnumProperty(Constants.EN_TOP, ""));
			break;
		case 1:
			//Tabloid
			setFOProperty(Constants.PR_DISPLAY_ALIGN, new EnumProperty(Constants.EN_CENTER, ""));
			break;
		case 2:
			//Ledger
			setFOProperty(Constants.PR_DISPLAY_ALIGN, new EnumProperty(Constants.EN_AFTER, ""));
			break;
			
		default:
			break;
		}
	}
	
	@Override
	protected void uiStateChange(UIStateChangeEvent evt) {
		super.uiStateChange(evt);
			
		Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager.getUIComponents().get(this.getActionID());
		
		Iterator<List<Object>> iterator = set.get(ActionCommandType.RIBBON_ACTION).iterator();
		List<Object> list = iterator.next();
		
		if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType) != null) {
			Object value =  RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_DISPLAY_ALIGN);
			
			if (value == null) {
				((JCommandMenuButton)list.get(0)).getActionModel().setSelected(true);
				return;
			}
			
			if (value instanceof EnumProperty) {
				EnumProperty trValue = (EnumProperty) value;
				
				if (value.equals(Constants.NULLOBJECT)) {
					//none
				} else {
					if (trValue.equals(new EnumProperty(Constants.EN_TOP, ""))) {
						((JCommandMenuButton)list.get(0)).getActionModel().setSelected(true);
					} else if (trValue.equals(new EnumProperty(Constants.EN_CENTER, ""))){
						((JCommandMenuButton)list.get(1)).getActionModel().setSelected(true);
					} else if (trValue.equals(new EnumProperty(Constants.EN_AFTER, ""))){
						((JCommandMenuButton)list.get(2)).getActionModel().setSelected(true);
					}
				}
			}
		}
		
	}

}
