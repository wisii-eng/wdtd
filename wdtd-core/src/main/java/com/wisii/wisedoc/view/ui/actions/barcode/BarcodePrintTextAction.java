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

import javax.swing.JCheckBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 是否打印条形码内容动作
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class BarcodePrintTextAction extends Actions {

	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JCheckBox) {
			JCheckBox value = (JCheckBox) e.getSource();
			setFOProperty(Constants.PR_BARCODE_PRINT_TEXT, new EnumProperty(convert(value.isSelected()),""));
		}
	}
	
	private int convert(boolean value){
		if(value){
			return Constants.EN_TRUE;
		} else{
			return Constants.EN_FALSE;
		}
	}
	
	@Override
	protected void uiStateChange(UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		
		if (hasPropertyKey(Constants.PR_BARCODE_PRINT_TEXT)) {
			if (uiComponent != null) {
				if (uiComponent instanceof JCheckBox) {
					JCheckBox ui = (JCheckBox) uiComponent;
					Object printtext = evt.getNewValue();
					if (printtext instanceof EnumProperty
							&& ((EnumProperty) printtext).getEnum() == Constants.EN_TRUE)
					{
						ui.setSelected(true);
					} else {
						ui.setSelected(false);
					}
				}
			}
		}
	}
	
	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_BARCODE_PRINT_TEXT)) {
			if (uiComponent != null) {
				if (uiComponent instanceof JCheckBox) {
					JCheckBox ui = (JCheckBox) uiComponent;
					ui.setSelected(false);
				}
			}
		}
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent != null) {
			if (uiComponent instanceof JCheckBox) {
				JCheckBox ui = (JCheckBox) uiComponent;
				ui.setSelected(false);
			}
		}
	}
	
	@Override
	public boolean isAvailable() {
		return super.isAvailable();
	}
	
	
}
