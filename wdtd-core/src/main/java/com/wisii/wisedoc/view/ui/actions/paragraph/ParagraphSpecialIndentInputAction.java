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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.actions.MeasureConversion;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.model.ParagraphPropertyModel;

/**
 * 
 * 设置段落特殊缩进的输入的action，里面包括首行缩进和悬挂缩进的具体设置
 * 
 * @author	闫舒寰
 * @since	2008/09/27
 */
public class ParagraphSpecialIndentInputAction extends Actions implements ChangeListener{

	/*
	 * 首行缩进的设置值
	 */
	JSpinner firstLineIndent;
	/*
	 * 段落缩进的设置值
	 */
	JSpinner paragraphIndent;
	/*
	 * 选择单位的下拉菜单
	 */
	JComboBox measurement;	

	
	
	private void getComponents(){
		
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(Paragraph.SPECIAL_INDENT_INPUT_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		List<JSpinner> temp = new ArrayList<JSpinner>();
		
		for (Iterator<List<Object>> iterator2 = comSet.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					this.measurement = (JComboBox) ui;
				}
				
				if (uiComponents instanceof JSpinner) {
					JSpinner ui = (JSpinner) uiComponents;
					temp.add(ui);
					/*if (this.firstLineIndent == null) {
						this.firstLineIndent = ui;
					}
					if (this.firstLineIndent != null && this.paragraphIndent == null) {
						this.paragraphIndent = ui;
					}*/
				}
				
			}
		}
		
		this.firstLineIndent = temp.get(0);
		this.paragraphIndent = temp.get(1);
	}
	
	public void doAction(ActionEvent e) {
		getComponents();
		
		this.changeProperties();
		
	}

	
	public void stateChanged(ChangeEvent e) {
		getComponents();
		this.changeProperties();
		
	}
	
	
	//设置具体的属性值
	private void changeProperties() {
		
		Map<Integer, Object> property = new HashMap<Integer, Object>();
		//设置Text-indent属性
		property.put(Constants.PR_TEXT_INDENT, 
				new FixedLength((Double) firstLineIndent.getValue(),
						MeasureConversion.getIndexConversion(((JComboBox) measurement).getSelectedIndex())));
		//设置Start-indent属性
		property.put(Constants.PR_START_INDENT, 
				new FixedLength((Double) paragraphIndent.getValue(),
						MeasureConversion.getIndexConversion(((JComboBox) measurement).getSelectedIndex())));
		
		
		ParagraphPropertyModel.setFOProperties(property);
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(actionCommand);
		//TODO 还需要再研究
		/*getComponents();
		
		Object currnetValue =  RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_WORD_SPACING);
		
		
		double setValue = 0;
		String unit = null;
		
		
		if (currnetValue instanceof SpaceProperty) {
			SpaceProperty pValue = (SpaceProperty) currnetValue;
//			EnumLength cValue = (EnumLength) pValue.getOptimum(null);
			FixedLength fl = (FixedLength)pValue.getOptimum(null);
			setValue = fl.getLengthValue();
			unit = fl.getUnits();
//			System.out.println("enu: " + enu + " fl: " + fl.getLengthValue() + " nuits:" + fl.getUnits());
		}
		
		JSpinner spinnerUI = null;
		JComboBox comboBoxUI = null;
		
		
		if (measurement instanceof JComboBox) {
			comboBoxUI = (JComboBox) measurement;
		}
		
		
		
		if (currnetValue.equals(Constants.NULLOBJECT)) {
			JTextComponent editor = (JTextComponent) comboBoxUI.getEditor().getEditorComponent();
			editor.setText("");
		} else {
//			if (enu == 0) {
				spinnerUI.setValue(setValue);
				if (unit != null) {
//					comboBoxUI.setSelectedItem(unit);
					comboBoxUI.setSelectedIndex(MeasureConversion.getIndexConversion(unit));
				}
//			}
		}*/
		
	}

}
