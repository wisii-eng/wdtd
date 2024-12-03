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
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 定义条码方向，即条码与水平方向的逆时针角度。
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/03
 */
public class BarcodeOrientationAction extends Actions {

	/**
	 * TODO 1、目前条码只支持4个方向的值的设定，要是用户输入其他值需要有提示，目前还没有
	 */

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseSpinner)
		{
			WiseSpinner value = (WiseSpinner) e.getSource();

			int dValue = (Integer) value.getValue();

			// 目前只支持这四个值的设置
			if (dValue == 90 || dValue == 180 || dValue == 270 || dValue == 0)
			{
				setFOProperty(Constants.PR_BARCODE_ORIENTATION, dValue);
			} else
			{
				Map<Integer, Object> oldatt = RibbonUIModel
						.getReadCompletePropertiesByType().get(actionType);
				int olddValue = 0;
				if (oldatt != null)
				{
					Object olddobject = oldatt.get(Constants.PR_BARCODE_ORIENTATION);
				    if(olddobject instanceof Integer)
				    {
				    	olddValue = (Integer)olddobject;
				    }
				}
				value.initValue(olddValue);
			}
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_BARCODE_ORIENTATION))
		{
			if (uiComponent instanceof WiseSpinner)
			{
				WiseSpinner ui = (WiseSpinner) uiComponent;
				Object orientation = evt.getNewValue();
				if (orientation == null)
				{
					orientation = 0;
				}
				ui.initValue(orientation);
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);

		if (uiComponent instanceof WiseSpinner) {
			WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(null);
		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof WiseSpinner) {
			WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(0.0);
		}
	}

	@Override
	public boolean isAvailable() {
		return super.isAvailable();
	}

}
