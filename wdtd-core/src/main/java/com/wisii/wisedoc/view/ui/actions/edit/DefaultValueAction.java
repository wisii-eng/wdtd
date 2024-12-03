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
/**
 * 
 */
package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * @author wisii
 *
 */
public class DefaultValueAction extends AbstractEditAction {

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseTextField)
		{
			WiseTextField hint = (WiseTextField) e.getSource();
			String text = hint.getText();
			if (text == null || "".equals(text.trim()))
			{
				setFOProperty(Constants.PR_DEFAULT_VALUE, null);
			} else
			{
				setFOProperty(Constants.PR_DEFAULT_VALUE, text.trim());
			}
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof WiseTextField
				&& hasPropertyKey(Constants.PR_DEFAULT_VALUE))
		{
			WiseTextField textfield = (WiseTextField) uiComponent;
			Object obj = evt.getNewValue();

			if (obj == null)
			{
				textfield.setText("");
			} else
			{
				textfield.setText(obj.toString());
			}
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_DEFAULT_VALUE))
		{
			if (uiComponent instanceof WiseTextField)
			{
				WiseTextField textfield = (WiseTextField) uiComponent;
				textfield.setText("");
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseTextField)
		{
			WiseTextField textfield = (WiseTextField) uiComponent;
			textfield.setText("");
		}

	}
}
