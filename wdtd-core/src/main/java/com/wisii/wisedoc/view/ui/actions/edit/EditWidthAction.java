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

package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

public class EditWidthAction extends AbstractEditAction
{

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner width = (FixedLengthSpinner) e.getSource();
			FixedLength length = create(width);
			setFOProperty(Constants.PR_EDIT_WIDTH, length);
		}

	}

	private FixedLength create(FixedLengthSpinner flspinner)
	{
		return flspinner.getValue();
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof FixedLengthSpinner
				&& hasPropertyKey(Constants.PR_EDIT_WIDTH))
		{
			FixedLengthSpinner widthspinner = (FixedLengthSpinner) uiComponent;
			updateUI(widthspinner, evt.getNewValue());
		}
	}

	private void updateUI(FixedLengthSpinner uiComponent, Object obj)
	{
		uiComponent.initValue(obj);
	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_EDIT_WIDTH))
		{
			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner box = (FixedLengthSpinner) uiComponent;
				box.initValue(null);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner box = (FixedLengthSpinner) uiComponent;
			box.initValue(null);
		}

	}

}
