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
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

@SuppressWarnings("serial")
public class SetEditAction extends Actions
{

	WiseCombobox ui;

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

		if (hasPropertyKey(Constants.PR_EDITMODE))
		{
			init((EnumProperty) evt.getNewValue());
		}
	}

	private void init(EnumProperty enumpnum)
	{
		if (uiComponent instanceof WiseCombobox)
		{

			ui = (WiseCombobox) uiComponent;
			if (enumpnum != null)
			{
				int current = enumpnum.getEnum();
				if (current > 0)
				{
					ui.initIndex(getIndex(current));
				}
			}
			else
			{
				ui.initIndex(0);
			}
		}
	}

	private void setProperty(int value)
	{
		int result = Constants.EN_UNEDITABLE;
		switch (value)
		{
		case 1:
		{
			result = Constants.EN_EDITABLE;
			break;
		}
		}
		EnumProperty enumValue = new EnumProperty(result, "");
		((WiseDocDocument) getCurrentDocument()).setAttribute(
				Constants.PR_EDITMODE, enumValue, true);
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
				EnumProperty initialpagepumber = att != null ? (EnumProperty) att
						.get(Constants.PR_EDITMODE)
						: null;
				int index = 0;
				if (initialpagepumber != null)
				{
					index = getIndex(initialpagepumber.getEnum());
				}
				ui.initIndex(index);
				ui.updateUI();
			}
		}
	}

	public int getIndex(int value)
	{
		int result = 0;
		switch (value)
		{
		case Constants.EN_EDITABLE:
		{
			result = 1;
			break;
		}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.actions.Actions#isAvailable()
	 */
	@Override
	public boolean isAvailable()
	{
		init((EnumProperty) getCurrentDocument().getAttribute(Constants.PR_EDITMODE));
		return super.isAvailable();
	}
}