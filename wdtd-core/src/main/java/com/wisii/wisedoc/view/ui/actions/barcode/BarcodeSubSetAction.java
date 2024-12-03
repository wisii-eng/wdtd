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
package com.wisii.wisedoc.view.ui.actions.barcode;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 条形码字符集动作
 * @author 闫舒寰
 * @version 1.0 2009/03/20
 */
public class BarcodeSubSetAction extends Actions{

	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox value = (JComboBox) e.getSource();
			setFOProperty(Constants.PR_BARCODE_SUBSET, 	new EnumProperty(convert(value.getSelectedIndex()), ""));
		}
	}
	
	private int convert(int index){
		
		if(index == 0){
			return Constants.EN_A;
		} else if(index == 1) {
			return Constants.EN_B;
		} else if(index == 2){
			return Constants.EN_C;
		}
		
		return 0;
	}
	
	
	@Override
	protected void uiStateChange(UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_BARCODE_SUBSET)) {
			if (uiComponent instanceof JComboBox) {
				WiseCombobox ui = (WiseCombobox) uiComponent;
				Object oValue = evt.getNewValue();
				if (oValue instanceof EnumProperty) {
					EnumProperty value = (EnumProperty) oValue;
					if (value.equals(new EnumProperty(Constants.EN_A, ""))) {
						ui.initIndex(0);
					} else if (value.equals(new EnumProperty(Constants.EN_B, ""))) {
						ui.initIndex(1);
					} else if (value.equals(new EnumProperty(Constants.EN_C, ""))) {
						ui.initIndex(2);
					}
				}
			}
		}
	}
	
	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_BARCODE_SUBSET)) {
			if (uiComponent instanceof JComboBox) {
				JComboBox ui = (JComboBox) uiComponent;
				JTextComponent editor = (JTextComponent) ui.getEditor().getEditorComponent();
				editor.setText("");
			}
		}
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof JComboBox) {
			JComboBox ui = (JComboBox) uiComponent;
			ui.setSelectedIndex(1);
		}
	}
	
	@Override
	public boolean isAvailable() {
		if(!super.isAvailable())
		{
			return false;
		}
		final List<Object> elements = RibbonUIModel.getElementList();
		if(elements==null)
		{
			return false;
		}
		for (Iterator<Object> iterator = elements.iterator(); iterator
				.hasNext();) {
			Object temp = iterator.next();
			if (temp instanceof BarCode) {
				Object valueTemp = RibbonUIModel.getReadCompletePropertiesByType().get(actionType).get(Constants.PR_BARCODE_TYPE);
				if (valueTemp != null && valueTemp instanceof EnumProperty) {
					int value = ((EnumProperty) valueTemp).getEnum();
					if (value == Constants.EN_CODE128) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
