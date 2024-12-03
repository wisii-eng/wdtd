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
 * 定义是否按UCC/EAN编码，即由条码生成器添加相应的UCC/EAN特殊字符
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/03
 */
public class BarcodeMakeUCCAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCheckBox)
		{
			JCheckBox value = (JCheckBox) e.getSource();
			setFOProperty(Constants.PR_BARCODE_MAKEUCC, new EnumProperty(
					convert(value.isSelected()), ""));
		}
	}

	private int convert(boolean value)
	{
		if (value)
		{
			return Constants.EN_TRUE;
		} else
		{
			return Constants.EN_FALSE;
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_BARCODE_MAKEUCC))
		{
			if (uiComponent instanceof JCheckBox)
			{
				JCheckBox ui = (JCheckBox) uiComponent;
				Object makeucc = evt.getNewValue();
				if (makeucc instanceof EnumProperty
						&& ((EnumProperty) makeucc).getEnum() == Constants.EN_TREE)
				{
					ui.setSelected(true);
				} else
				{
					ui.setSelected(false);
				}
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_BARCODE_MAKEUCC))
		{
			if (uiComponent instanceof JCheckBox)
			{
				JCheckBox ui = (JCheckBox) uiComponent;
				ui.setSelected(false);
			}
		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (uiComponent instanceof JCheckBox)
		{
			JCheckBox ui = (JCheckBox) uiComponent;
			ui.setSelected(false);
		}
	}

	@Override
	public boolean isAvailable()
	{
		if(!super.isAvailable())
		{
			return false;
		}
		List<Object> elements = RibbonUIModel.getElementList();
		if (elements != null)
		{
			for (Object temp : elements)
			{
				if (temp instanceof BarCode)
				{
					Object valueTemp = RibbonUIModel
							.getReadCompletePropertiesByType().get(actionType)
							.get(Constants.PR_BARCODE_TYPE);
					if (valueTemp != null && valueTemp instanceof EnumProperty)
					{
						int value = ((EnumProperty) valueTemp).getEnum();
						if (value == Constants.EN_CODE128)
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
