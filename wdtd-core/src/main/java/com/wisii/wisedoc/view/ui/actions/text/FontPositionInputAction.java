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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

/**
 * 该类是用于设定文字基线的偏移量的属性的设定
 * 
 * @author	闫舒寰
 * @since	2008/09/26
 */
public class FontPositionInputAction extends Actions implements ChangeListener {
	
	/**
	 * 这个是用来让系统不考虑枚举类型的
	 */
	private final int NEGATIVE_NUMBER = -1;
	
	private Object value;
	/*private Object measurement;*/
	
	private ActionCommandType actionCommandType;

	private void getComponents(){
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(Font.POSITION_INPUT_ACTION).get(actionCommandType);
		for (List<Object> list : comSet) {
			for (Object object : list) {
				if (object instanceof JSpinner) {
					JSpinner ui = (JSpinner) object;
					this.value = ui;
				}
			}
		}
	}

	@Override
	public void doAction(ActionEvent e) {
		getComponents();
		// 设置正确的属性类型和属性值
		FontPropertyModel.setFOProperty(Constants.PR_BASELINE_SHIFT, getLength());
	}

	public void stateChanged(ChangeEvent e) {
		getComponents();
		
		FontPropertyModel.setFOProperty(Constants.PR_BASELINE_SHIFT, getLength());
	}
	
	private EnumLength getLength(){
		EnumLength el = null;
		if(!(value instanceof FixedLengthSpinner)){
			return el;
		}
		FixedLength length = ((FixedLengthSpinner)value).getValue();
		el = new EnumLength(NEGATIVE_NUMBER, length);
		return el;
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		
		actionCommandType = actionCommand;
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			super.setDefaultState(null);
			getComponents();
			
			Object currnetValue = InitialManager.getInitialValue(Constants.PR_BASELINE_SHIFT, null);
			Map<Integer,Object> atts= 
				RibbonUIModel.getCurrentPropertiesByType().get(this.actionType);
			if(atts!=null&&atts.containsKey(Constants.PR_BASELINE_SHIFT))
			{
				currnetValue = atts.get(Constants.PR_BASELINE_SHIFT);
			}
			FixedLength length = null;
			if (currnetValue instanceof EnumLength) {
				EnumLength cValue = (EnumLength) currnetValue;
				length = (FixedLength)cValue.getFixLength();
			}
			
			JComboBox comboBoxUI = null;
			if (currnetValue.equals(Constants.NULLOBJECT)) {
				JTextComponent editor = (JTextComponent) comboBoxUI.getEditor().getEditorComponent();
				editor.setText("");
			} else {
				if(value instanceof FixedLengthSpinner && length != null) {
					((FixedLengthSpinner)value).initValue(length);
				}
			}
		}
		
		if (actionCommand == ActionCommandType.DYNAMIC_ACTION) {
			super.setDefaultState(null);
			getComponents();
			
			Object currnetValue = InitialManager.getInitialValue(Constants.PR_BASELINE_SHIFT, null);
			Map<Integer,Object> atts= 
				RibbonUIModel.getCurrentPropertiesByType().get(this.actionType);
			if(atts!=null&&atts.containsKey(Constants.PR_BASELINE_SHIFT))
			{
				currnetValue = atts.get(Constants.PR_BASELINE_SHIFT);
			}
			int enu = NEGATIVE_NUMBER;
			
			FixedLength length = null;
			
			if (currnetValue instanceof EnumLength) {
				EnumLength cValue = (EnumLength) currnetValue;
				enu = cValue.getEnum();
				length = (FixedLength)cValue.getFixLength();
			}
			
			JSpinner spinnerUI = null;
			JComboBox comboBoxUI = null;
			
			if (this.value instanceof JSpinner) {
				spinnerUI = (JSpinner) value;
			}
			
			if (currnetValue.equals(Constants.NULLOBJECT)) {
				JTextComponent editor = (JTextComponent) comboBoxUI.getEditor().getEditorComponent();
				editor.setText("");
			} else {
				if(value instanceof FixedLengthSpinner && length != null) {
					((FixedLengthSpinner)spinnerUI).initValue(length);
				}
			}
		}
		
		
	}

}
