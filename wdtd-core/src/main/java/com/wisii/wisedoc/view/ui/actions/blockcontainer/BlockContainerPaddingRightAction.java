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

package com.wisii.wisedoc.view.ui.actions.blockcontainer;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

@SuppressWarnings("serial")
public class BlockContainerPaddingRightAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{

		if (e.getSource() instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner ui = (FixedLengthSpinner) e.getSource();
			FixedLength length = ui.getValue();
			if (length != null)
			{
				CondLengthProperty len = new CondLengthProperty(length, false);
				setFOProperty(Constants.PR_PADDING_END, len);
			}
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_PADDING_END))
		{
			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				CondLengthProperty length = (CondLengthProperty) evt
						.getNewValue();
				if (length != null)
				{
					FixedLength len = (FixedLength) length.getLength();
					ui.initValue(len);
				} else
				{
					ui.initValue(new FixedLength(0d, "mm"));
				}

			}
		}
	}


	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			ui.initValue(null);
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			CondLengthProperty len = (CondLengthProperty) InitialManager.getInitialValue(Constants.PR_PADDING_END, null);
			if(len!=null)
			{
				ui.initValue(len.getLength());
			}
			
		}}

}
