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
package com.wisii.wisedoc.view.ui.actions.paragraph;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.actions.MeasureConversion;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.model.ParagraphPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

/**
 * 
 * 设置行高的动作
 * 
 * @author	闫舒寰
 * @since	2008/09/27
 */
public class ParagraphLineHeightAction extends Actions implements ChangeListener{
	
	
	//行高的值	 
	JSpinner value;
	
	 //行高的单位	 
	JComboBox measurement;
	
	 //行高的策略	 
	JComboBox strategy;

	
	private void getComponents(){
		
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(Paragraph.LINE_HEIGHT_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		List<JComboBox> temp = new ArrayList<JComboBox>();
		
		for (Iterator<List<Object>> iterator2 = comSet.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					temp.add(ui);
				}
				
				if (uiComponents instanceof JSpinner) {
					JSpinner ui = (JSpinner) uiComponents;
					this.value = ui;
				}
				
			}
		}
		this.measurement = temp.get(0);
		this.strategy = temp.get(1);
	}

	@Override
	public void doAction(ActionEvent e) {
		setPorperty();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		setPorperty();
	}
	
	private void setPorperty(){
		
		getComponents();
		
		//读取行高的值
		FixedLength length = new FixedLength((Double)((JSpinner) value).getValue(),
				MeasureConversion.getIndexConversion(((JComboBox) measurement).getSelectedIndex()));
		EnumProperty conditionality = new EnumProperty(Constants.EN_DISCARD,"");
		EnumNumber precedence = new EnumNumber(-1,0);
		SpaceProperty space = new SpaceProperty(length,precedence,conditionality);		
		
		// 设置正确的属性类型和属性值
		ParagraphPropertyModel.setFOProperty(Constants.PR_LINE_HEIGHT, space);
		
		//读取并设置策略
		switch (strategy.getSelectedIndex()) {
		case 0:
			ParagraphPropertyModel.setFOProperty(Constants.PR_LINE_STACKING_STRATEGY, Constants.EN_MAX_HEIGHT);
			break;
		case 1:
			ParagraphPropertyModel.setFOProperty(Constants.PR_LINE_STACKING_STRATEGY, Constants.EN_LINE_HEIGHT);
			break;
		case 2:
			ParagraphPropertyModel.setFOProperty(Constants.PR_LINE_STACKING_STRATEGY, Constants.EN_FONT_HEIGHT);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(actionCommand);
		
		
		
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			getComponents();
			
			Object currnetValue =  RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_LINE_HEIGHT);
			
			
			double setValue = 0;
			String unit = null;
			
			
			if (currnetValue instanceof SpaceProperty) {
				SpaceProperty pValue = (SpaceProperty) currnetValue;
//				EnumLength cValue = (EnumLength) pValue.getOptimum(null);
				FixedLength fl = (FixedLength)pValue.getOptimum(null);
				setValue = fl.getInnerLengthValue();
				unit = fl.getUnits();
//				System.out.println("enu: " + enu + " fl: " + fl.getLengthValue() + " nuits:" + fl.getUnits());
			}
			
			JSpinner spinnerUI = null;
			JComboBox comboBoxUI = null;
			
			if (this.value instanceof JSpinner) {
				spinnerUI = (JSpinner) value;
			}
			
			if (measurement instanceof JComboBox) {
				comboBoxUI = (JComboBox) measurement;
			}
			
			
			
			if (currnetValue.equals(Constants.NULLOBJECT)) {
				JTextComponent editor = (JTextComponent) comboBoxUI.getEditor().getEditorComponent();
				editor.setText("");
			} else {
//				if (enu == 0) {
					spinnerUI.setValue(setValue);
					if (unit != null) {
//						comboBoxUI.setSelectedItem(unit);
						comboBoxUI.setSelectedIndex(MeasureConversion.getIndexConversion(unit));
					}
//				}
			}
			
			Object strategy = RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_LINE_STACKING_STRATEGY);
			if (strategy != null) {
				if (strategy.equals(Constants.EN_MAX_HEIGHT)) {
					this.strategy.setSelectedIndex(0);
				} else if (strategy.equals(Constants.EN_LINE_HEIGHT)){
					this.strategy.setSelectedIndex(1);
				} else if (strategy.equals(Constants.EN_FONT_HEIGHT)){
					this.strategy.setSelectedIndex(2);
				}
			}
			
			
		}
		
	}

}
