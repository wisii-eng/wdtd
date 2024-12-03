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
import java.util.List;

import javax.swing.JCheckBox;

import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 定义是否添加校验码，即43模校验字符
 * @author 闫舒寰
 * @version 1.0 2009/02/03
 */
public class BarcodeAddCheckSumAction extends Actions {

	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof JCheckBox) {
			final JCheckBox value = (JCheckBox) e.getSource();
			setFOProperty(Constants.PR_BARCODE_ADDCHECKSUM, value.isSelected());
		}
	}
	
	
	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		
		if (hasPropertyKey(Constants.PR_BARCODE_ADDCHECKSUM)) {
			if (uiComponent instanceof JCheckBox) {
				final JCheckBox ui = (JCheckBox) uiComponent;
				final Boolean isaddchecksum = (Boolean) evt.getNewValue();
				if (isaddchecksum!=null&&isaddchecksum) {
					ui.setSelected(true);
				} else {
					ui.setSelected(false);
				}
			}
		}
	}
	
	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof JCheckBox) {
			final JCheckBox ui = (JCheckBox) uiComponent;
			ui.setSelected(false);
		}
	}
	
	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_BARCODE_ADDCHECKSUM)) {
			if (uiComponent instanceof JCheckBox) {
				final JCheckBox ui = (JCheckBox) uiComponent;
				ui.setSelected(false);
			}
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
		for (final Object temp : elements) {
			if (temp instanceof BarCode) {
				final Object valueTemp = RibbonUIModel.getReadCompletePropertiesByType().get(actionType).get(Constants.PR_BARCODE_TYPE);
				if (valueTemp != null && valueTemp instanceof EnumProperty) {
					final int value = ((EnumProperty) valueTemp).getEnum();
					if (value == Constants.EN_2OF5 || value == Constants.EN_CODE39) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
