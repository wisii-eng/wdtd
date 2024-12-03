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
package com.wisii.wisedoc.view.ui.actions.text;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JSpinner;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.parts.text.FontPropertyPanel;

/**
 * 字母间距动作类，这个目前FOP0.94仅对英文字符有效
 * 
 * @author	闫舒寰
 * @version 0.1 2008/09/23
 *
 */
public class FontSpaceAction extends Actions {

	Object value;
	
	private ActionCommandType actionCommandType;

	private void getComponents(){
		
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(Font.SPACE_INPUT_ACTION).get(actionCommandType);
		
		for (List<Object> list : comSet) {
			for (Object uiComponents : list) {
				if (uiComponents instanceof JSpinner) {
					JSpinner ui = (JSpinner) uiComponents;
					this.value = ui;
				}
			}
		}
	}
	
	@Override
	public void doAction(ActionEvent e) {
		getComponents();
		if (e.getSource() instanceof JComboBox) {
			JComboBox jb = (JComboBox) e.getSource();
			if(jb.getSelectedIndex() != 1){
				((JSpinner)value).setEnabled(false);
				if (jb.getSelectedIndex() == 0) {
					// 正常情况下
					FontPropertyModel.setFOProperty(Constants.PR_LETTER_SPACING, new EnumProperty(Constants.EN_NORMAL,""));
				}
			} else {
				((JSpinner)value).setEnabled(true);
			}
		}
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		
		actionCommandType = actionCommand;
		
		super.setDefaultState(null);
		getComponents();
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			 Set<List<Object>> tempSet = RibbonUIManager.getUIComponents().get(this.actionID).get(actionCommand);
			 for (List<Object> list : tempSet) {
				for (Object object : list) {
					if (object instanceof JComboBox) {
						JComboBox ui = (JComboBox) object;
						Object value = InitialManager.getInitialValue(Constants.PR_LETTER_SPACING, null);
						Map<Integer,Object> atts= 
							RibbonUIModel.getCurrentPropertiesByType().get(this.actionType);
						if(atts!=null&&atts.containsKey(Constants.PR_LETTER_SPACING))
						{
							value = atts.get(Constants.PR_LETTER_SPACING);
						}
						if (value.equals(Constants.NULLOBJECT)) {
							ui.setSelectedIndex(0);
						} else {
							if (value.equals(new EnumProperty(Constants.EN_NORMAL,""))) {
								ui.setSelectedIndex(0);
								setComponentEnable(false);
							} else {
								ui.setSelectedIndex(1);
								setComponentEnable(true);
							}
						}
					}
				}
			}
		}
		
		if (actionCommand == ActionCommandType.DYNAMIC_ACTION) {
			 Set<List<Object>> tempSet = RibbonUIManager.getUIComponents().get(this.actionID).get(actionCommand);
			 for (List<Object> list : tempSet) {
				for (Object object : list) {
					if (object instanceof JComboBox) {
						JComboBox ui = (JComboBox) object;
						Object value =  FontPropertyPanel.getInitialPropertise().get(Constants.PR_LETTER_SPACING);
						if (value.equals(Constants.NULLOBJECT)) {
							ui.setSelectedIndex(0);
						} else {
							if (value.equals(new EnumProperty(Constants.EN_NORMAL,""))) {
								ui.setSelectedIndex(0);
								setComponentEnable(false);
							} else {
								ui.setSelectedIndex(1);
								setComponentEnable(true);
							}
						}
					}
				}
			}
		}
	}
	
	private void setComponentEnable(boolean state){
		if (value != null /*&& measurement != null*/) {
			((JSpinner)value).setEnabled(state);
		}
	}

}
