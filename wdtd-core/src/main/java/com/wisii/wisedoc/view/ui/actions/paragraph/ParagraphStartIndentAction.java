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
package com.wisii.wisedoc.view.ui.actions.paragraph;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 左缩进动作
 * 
 * @author 闫舒寰
 * @version 0.1 2008/10/7
 */
public class ParagraphStartIndentAction extends Actions
{
	// 单位按钮有变化的时候
	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner fixedlenspiner = (FixedLengthSpinner) e
					.getSource();
			setFOProperty(Constants.PR_START_INDENT, fixedlenspiner.getValue());
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_START_INDENT))
		{
			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				FixedLength uivalue = null;
				Object value = evt.getNewValue();

				if (value instanceof LengthRangeProperty)
				{
					value = ((LengthRangeProperty) value).getOptimum(null);
					if (value instanceof FixedLength)
					{
						uivalue = (FixedLength) value;
					}
				} else if (value instanceof FixedLength)
				{
					uivalue = (FixedLength) evt.getNewValue();
				}
				ui.initValue(uivalue);
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			ui.initValue(null);
		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (uiComponent instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			ui.initValue(new FixedLength(0d,"pt"));
		}
	}
}