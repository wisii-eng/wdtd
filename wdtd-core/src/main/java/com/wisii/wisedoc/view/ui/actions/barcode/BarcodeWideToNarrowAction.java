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
 * 条形码宽窄比动作 定义宽窄比N，此处为宽单元与窄单元的比率。
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/03
 */
public class BarcodeWideToNarrowAction extends Actions
{

	@Override
	public void doAction(final ActionEvent e)
	{
		if (e.getSource() instanceof WiseSpinner)
		{
			final WiseSpinner value = (WiseSpinner) e.getSource();
			setFOProperty(Constants.PR_BARCODE_WIDE_TO_NARROW, value.getValue());
		}
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_BARCODE_WIDE_TO_NARROW))
		{
			if (uiComponent instanceof WiseSpinner)
			{
				final WiseSpinner ui = (WiseSpinner) uiComponent;
				double wtn = 0.0;
				Object wtona = evt.getNewValue();
				if(wtona instanceof Double)
				{
					wtn = (Double)wtona;
				}
				ui.initValue(wtn);

			}
		}
	}

	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);

		if (uiComponent instanceof WiseSpinner)
		{
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(null);
		}
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (uiComponent instanceof WiseSpinner)
		{
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(0.0);
		}
	}

	@Override
	public boolean isAvailable()
	{
		final List<Object> elements = RibbonUIModel.getElementList();
		if(elements==null)
		{
			return false;
		}
		for (final Object temp : elements)
		{
			if (temp instanceof BarCode)
			{
				final Object valueTemp = RibbonUIModel
						.getReadCompletePropertiesByType().get(actionType).get(
								Constants.PR_BARCODE_TYPE);
				if (valueTemp != null && valueTemp instanceof EnumProperty)
				{
					final int value = ((EnumProperty) valueTemp).getEnum();
					if (value == Constants.EN_2OF5
							|| value == Constants.EN_CODE39)
					{
						return true;
					}
				}
			}
		}

		// 当遇到没有这个属性的对象时应该设回默认值，因为没有这个属性就不会发送消息到这里。
		if (RibbonUIModel.getReadPropertiesByType().get(this.getActionType()) != null) {
			if (RibbonUIModel.getReadPropertiesByType().get(this.getActionType()).get(Constants.PR_BARCODE_WIDE_TO_NARROW) == null)
			{
				setDefaultState(null);
			}
		}
		
		
		return false;
	}

}
