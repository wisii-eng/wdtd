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
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.parts.text.FontPropertyPanel;

/**
 * 
 * 字母间距动作类，这个目前FOP0.94仅对英文字符有效
 * 
 * @author	闫舒寰
 * @since	2008/09/24
 *
 */
public class FontSpaceInputAction extends Actions implements ChangeListener{
	Object value;
	Object measurement;
	
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
		if(!(value instanceof FixedLengthSpinner)) {
			return;
		}
		FixedLength length = ((FixedLengthSpinner)value).getValue();
		EnumProperty conditionality = new EnumProperty(Constants.EN_DISCARD,"");
		EnumNumber precedence = new EnumNumber(-1,0);
		SpaceProperty space = new SpaceProperty(length,precedence,conditionality);
		
		// 设置正确的属性类型和属性值
		FontPropertyModel.setFOProperty(Constants.PR_LETTER_SPACING, space);
	}

	public void stateChanged(ChangeEvent e) {
		getComponents();
		if(!(value instanceof FixedLengthSpinner)) {
			return;
		}
		FixedLength length = ((FixedLengthSpinner)value).getValue();
		EnumProperty conditionality = new EnumProperty(Constants.EN_DISCARD,"");
		EnumNumber precedence = new EnumNumber(-1,0);
		SpaceProperty space = new SpaceProperty(length,precedence,conditionality);
		
		FontPropertyModel.setFOProperty(Constants.PR_LETTER_SPACING, space);
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		
		actionCommandType = actionCommand;
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			super.setDefaultState(null);
			getComponents();
			Object value = InitialManager.getInitialValue(Constants.PR_LETTER_SPACING, null);
			Map<Integer,Object> atts= 
				RibbonUIModel.getCurrentPropertiesByType().get(this.actionType);
			if(atts!=null&&atts.containsKey(Constants.PR_LETTER_SPACING))
			{
				value = atts.get(Constants.PR_LETTER_SPACING);
			}
			FixedLength length = null;
			
			if (value instanceof SpaceProperty) {
				SpaceProperty pValue = (SpaceProperty) value;
				length = (FixedLength)pValue.getOptimum(null);
			}
			
			FixedLengthSpinner spinnerUI = null;
			
			if (this.value instanceof FixedLengthSpinner)
			{
				spinnerUI = (FixedLengthSpinner) this.value;

				if (value.equals(Constants.NULLOBJECT))
				{
				} else
				{
					spinnerUI.initValue(length);
				}
			}
		}
		
		if (actionCommand == ActionCommandType.DYNAMIC_ACTION) {
			super.setDefaultState(null);
			getComponents();
			
			Object currnetValue = FontPropertyPanel.getInitialPropertise().get(Constants.PR_LETTER_SPACING);
			
			FixedLength length = null;
			
			if (currnetValue instanceof SpaceProperty) {
				SpaceProperty pValue = (SpaceProperty) currnetValue;
				length = (FixedLength)pValue.getOptimum(null);
			}
			
			JSpinner spinnerUI = null;
			
			if (this.value instanceof JSpinner) {
				spinnerUI = (JSpinner) value;
			}
			
			if (currnetValue.equals(Constants.NULLOBJECT)) {
			} else {
				if(value instanceof FixedLengthSpinner) {
					((FixedLengthSpinner)spinnerUI).initValue(length);
				}
			}
		}
	}
}
