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

package com.wisii.wisedoc.view.ui.actions.wisedoc;

import java.awt.event.ActionEvent;
import java.util.Map;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

@SuppressWarnings("serial")
public class SetDBTypeAction extends Actions
{

	WiseCombobox ui;

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox combobox = (WiseCombobox) e.getSource();
			String index = combobox.getSelectedItem().toString();
			setProperty(index);
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_DBTYPE))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				ui = (WiseCombobox) uiComponent;
				String current = (String) evt.getNewValue();
				if (current != null)
				{
					ui.InitValue(current);
				}
			}
		}
	}

	private void setProperty(String value)
	{
		getCurrentDocument().setAttribute(Constants.PR_DBTYPE, value);
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent != null)
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				Map<Integer, Object> att = RibbonUIModel
						.getReadCompletePropertiesByType().get(
								ActionType.DOCUMENT);
				String type = att != null ? (String) att
						.get(Constants.PR_DBTYPE) : null;
				if (type != null)
				{
					ui.InitValue(type);
				}
			}
		}
	}
}