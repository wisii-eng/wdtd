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
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

@SuppressWarnings("serial")
public class SetInitialPageNumberAction extends Actions
{

	String one = "1";

	String auto = "自动";

	String odd = "奇数";

	String even = "偶数";

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox combobox = (WiseCombobox) e.getSource();
			String current = combobox.getSelectedItem().toString();
			Object oldvalue = RibbonUIModel.getCurrentPropertiesByType().get(
					this.actionType) != null ? RibbonUIModel
					.getCurrentPropertiesByType().get(this.actionType).get(
							Constants.PR_INITIAL_PAGE_NUMBER) : null;
			Object newipn = null;
			if (current.equalsIgnoreCase(auto))
			{
				newipn = new EnumNumber(Constants.EN_AUTO, 0);
			} else if (current.equalsIgnoreCase(odd))
			{
				newipn = new EnumNumber(Constants.EN_AUTO_ODD, 0);
			} else if (current.equalsIgnoreCase(even))
			{
				newipn = new EnumNumber(Constants.EN_AUTO_EVEN, 0);
			} else
			{
				try
				{
					int number = Integer.parseInt(current);
					newipn = new EnumNumber(-1, number);

				} catch (Exception e2)
				{
					if (oldvalue == null)
					{
						combobox.InitValue(one);
					} else
					{
						if (oldvalue instanceof EnumNumber)
						{
							EnumNumber envalue = (EnumNumber) oldvalue;
							int enumber = envalue.getEnum();
							if (enumber <= 0)
							{
								combobox.InitValue(envalue.getNumber());
							} else if (enumber == Constants.EN_AUTO)
							{
								combobox.InitValue(auto);
							} else if (enumber == Constants.EN_AUTO_ODD)
							{
								combobox.InitValue(odd);
							} else if (enumber == Constants.EN_AUTO_EVEN)
							{
								combobox.InitValue(even);
							}
						} else
						{
							combobox.InitValue(one);
						}
					}
					return;
				}
			}
			setProperty(newipn);
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_INITIAL_PAGE_NUMBER))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				EnumNumber enumpnum = (EnumNumber) evt.getNewValue();
				if (enumpnum != null)
				{
					int current = enumpnum.getEnum();
					if (current > 0)
					{
						ui.InitValue(getItem(current));
					} else
					{
						ui.InitValue(getItem(enumpnum.getNumber().intValue()));
					}
				}
				else
				{
					ui.InitValue(one);
				}
			}
		}
	}

	private void setProperty(Object value)
	{
		setFOProperty(Constants.PR_INITIAL_PAGE_NUMBER, value);
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent != null)
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				ui.InitValue(one);
			}
		}
	}

	public String getItem(int value)
	{
		String item = "";
		if (value == 1)
		{
			item = one;
		} else if (value == Constants.EN_AUTO_EVEN)
		{
			item = even;
		} else if (value == Constants.EN_AUTO_ODD)
		{
			item = odd;
		} else if (value == Constants.EN_AUTO)
		{
			item = auto;
		} else
		{
			item = value + "";
		}
		return item;
	}
}