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
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

@SuppressWarnings("serial")
public class SetFormatAction extends Actions
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

		if (hasPropertyKey(Constants.PR_FORMAT))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				String current = (String) evt.getNewValue();
				if(current==null)
				{
					ui.initIndex(0);
				}
				else if (current.equals("1"))
				{
					ui.initIndex(0);
				} else if (current.equals("i"))
				{
					ui.initIndex(1);
				}
			}
		}
	}

	private void setProperty(int index)
	{
		String value = "1";
		if (index == 1)
		{
			value = "i";
		}
		setFOProperty(Constants.PR_FORMAT, value);
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
			}
		}
	}
}