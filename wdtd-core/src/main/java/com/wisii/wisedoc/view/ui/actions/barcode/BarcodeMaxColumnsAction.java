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

import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 定义最大数据区列数
 * @author 闫舒寰
 * @version 1.0 2009/04/17
 */
public class BarcodeMaxColumnsAction extends Actions {
	
	//要设置属性的属性ID
	private final int PROPERTY_ID = Constants.PR_BARCODE_MAX_COLUMNS;

	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof WiseSpinner) {
			final WiseSpinner value = (WiseSpinner) e.getSource();
			setFOProperty(PROPERTY_ID, value.getValue());
		}
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		if (hasPropertyKey(PROPERTY_ID)) {
			if (uiComponent instanceof WiseSpinner) {
				final WiseSpinner ui = (WiseSpinner) uiComponent;
				final int value;
				final Object oldvalue = evt.getNewValue();
				if (oldvalue instanceof Integer) {
					value = (Integer) oldvalue;
					ui.initValue(value);
				} else {
					setDefaultState(null);
				}
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof WiseSpinner) {
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(null);
		}
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof WiseSpinner) {
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.setValue(30);
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
					if (value == Constants.EN_PDF417) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
