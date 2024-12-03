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
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
@SuppressWarnings("serial")
public class SetWhiteSpaceTreatmentAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox combobox = (WiseCombobox) e.getSource();
			int index = combobox.getSelectedIndex();
			setProperty(index);
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_WHITE_SPACE_TREATMENT))
		{
			init((Integer) evt.getNewValue());
		}
	}
	private void init(Integer treat)
	{
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox ui = (WiseCombobox) uiComponent;
			if (treat != null)
			{
				ui.initIndex(getIndex(treat));
			}
			else
			{
				ui.initIndex(0);
			}
		}
	}

	private void setProperty(int value)
	{
		int result = Constants.EN_PRESERVE;
		switch (value)
		{
		case 1:
		{
			result = Constants.EN_IGNORE;
			break;
		}
		case 2:
		{
			result = Constants.EN_IGNORE_IF_BEFORE_LINEFEED;
			break;
		}
		case 3:
		{
			result = Constants.EN_IGNORE_IF_AFTER_LINEFEED;
			break;
		}
		case 4:
		{
			result = Constants.EN_IGNORE_IF_SURROUNDING_LINEFEED;
			break;
		}
		}
		((WiseDocDocument)getCurrentDocument()).setAttribute(Constants.PR_WHITE_SPACE_TREATMENT,
				result,true);
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseCombobox)
		{
			((WiseCombobox) uiComponent).initIndex(0);
		}
	}

	public int getIndex(int value)
	{
		int result = 0;
		switch (value)
		{
		case Constants.EN_IGNORE:
		{
			result = 1;
			break;
		}
		case Constants.EN_IGNORE_IF_BEFORE_LINEFEED:
		{
			result = 2;
			break;
		}
		case Constants.EN_IGNORE_IF_AFTER_LINEFEED:
		{
			result = 3;
			break;
		}
		case Constants.EN_IGNORE_IF_SURROUNDING_LINEFEED:
		{
			result = 4;
			break;
		}
		}
		return result;
	}
    /* (non-Javadoc)
     * @see com.wisii.wisedoc.view.ui.actions.Actions#isAvailable()
     */
    @Override
    public boolean isAvailable()
    {
    	init((Integer) getCurrentDocument().getAttribute(Constants.PR_WHITE_SPACE_TREATMENT));
    	return super.isAvailable();
    }
}