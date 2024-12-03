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

package com.wisii.wisedoc.view.ui.actions.pagesequence;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

@SuppressWarnings("serial")
public class SetForcePageCountAction extends Actions
{


	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox combobox = (WiseCombobox) e.getSource();
			int index = combobox.getSelectedIndex();
			setProperty(index);
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_FORCE_PAGE_COUNT))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				EnumProperty enumpnum = (EnumProperty) evt.getNewValue();
				if (enumpnum != null && enumpnum.getEnum() > 0)
				{
					ui.initIndex(getIndex(enumpnum.getEnum()));
				} else
				{
					ui.initIndex(0);
				}
			}
		}
	}

	private void setProperty(int value)
	{
		int result = Constants.EN_AUTO;
		switch (value)
		{
			case 1:
			{
				result = Constants.EN_ODD;
				break;
			}
			case 2:
			{
				result = Constants.EN_EVEN;
				break;
			}
			case 3:
			{
				result = Constants.EN_END_ON_ODD;
				break;
			}
			case 4:
			{
				result = Constants.EN_END_ON_EVEN;
				break;
			}
			case 5:
			{
				result = Constants.EN_NO_FORCE;
				break;
			}
		}
		EnumProperty enumValue = new EnumProperty(result, "");
		setFOProperty(Constants.PR_FORCE_PAGE_COUNT, enumValue);
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent != null)
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				ui.initIndex(0);
				ui.updateUI();
			}
		}
	}

	public int getIndex(int value)
	{
		int result = 0;
		// System.out.println("value:::" + value);
		switch (value)
		{
			case Constants.EN_ODD:
			{
				result = 1;
				break;
			}
			case Constants.EN_EVEN:
			{
				result = 2;
				break;
			}
			case Constants.EN_END_ON_ODD:
			{
				result = 3;
				break;
			}
			case Constants.EN_END_ON_EVEN:
			{
				result = 4;
				break;
			}
			case Constants.EN_NO_FORCE:
			{
				result = 5;
				break;
			}
		}
		return result;
	}
}