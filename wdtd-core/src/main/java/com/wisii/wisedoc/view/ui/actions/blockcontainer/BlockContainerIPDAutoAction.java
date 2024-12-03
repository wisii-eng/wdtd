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
import java.util.Map;

import javax.swing.JRadioButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

@SuppressWarnings("serial")
public class BlockContainerIPDAutoAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JRadioButton)
		{
			JRadioButton width = (JRadioButton) e.getSource();
			Object lengthold = (LengthRangeProperty) RibbonUIModel
					.getReadCompletePropertiesByType().get(actionType).get(
							Constants.PR_INLINE_PROGRESSION_DIMENSION);
			LengthProperty newlength = ((LengthRangeProperty) lengthold)
					.getOptimum(null);
			if (width.isSelected())
			{
				FixedLength fl = null;
				if (newlength instanceof FixedLength)
				{
					fl = (FixedLength) newlength;
				} else if (newlength instanceof EnumLength)
				{
					fl = (FixedLength) ((EnumLength) newlength).getFixLength();
				}
				EnumLength enumlength = new EnumLength(Constants.EN_AUTO, fl);
				LengthRangeProperty lrlength = new LengthRangeProperty(
						enumlength);
				setFOProperty(Constants.PR_INLINE_PROGRESSION_DIMENSION,
						lrlength);
			} else
			{
				FixedLength fl = null;
				if (newlength instanceof FixedLength)
				{
					fl = (FixedLength) newlength;
				} else if (newlength instanceof EnumLength)
				{
					fl = (FixedLength) ((EnumLength) newlength).getFixLength();
				}
				LengthRangeProperty lrlength = new LengthRangeProperty(fl);
				setFOProperty(Constants.PR_INLINE_PROGRESSION_DIMENSION,
						lrlength);
			}
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_INLINE_PROGRESSION_DIMENSION))
		{
			if (uiComponent instanceof JRadioButton)
			{
				JRadioButton ui = (JRadioButton) uiComponent;
				Object value = evt.getNewValue();

				if (value instanceof LengthRangeProperty)
				{
					LengthRangeProperty lrp = (LengthRangeProperty) value;
					LengthProperty length = lrp.getOptimum(null);
					if (length instanceof EnumLength)
					{
						int auto = ((EnumLength) length).getEnum();
						if (auto == Constants.EN_AUTO)
						{
							ui.setSelected(true);
						} else
						{
							ui.setSelected(false);
						}
					} else
					{
						ui.setSelected(false);
					}

				} else
				{
					ui.setSelected(false);
				}
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof JRadioButton)
		{
			JRadioButton ui = (JRadioButton) uiComponent;
			ui.setSelected(false);
		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		// if (uiComponent instanceof JRadioButton)
		// {
		// JRadioButton ui = (JRadioButton) uiComponent;
		// ui.setSelected(false);
		// }
	}

	@Override
	public boolean isAvailable()
	{
		boolean isvailable = super.isAvailable();
		if (!isvailable)
		{
			return false;
		}
		Map<Integer, Object> map = RibbonUIModel
				.getReadCompletePropertiesByType().get(
						ActionType.BLOCK_CONTAINER);
		if (map != null)
		{
			Object value = map.get(Constants.PR_ABSOLUTE_POSITION);
			if (value != null || value != Constants.NULLOBJECT)
			{
				if (value instanceof EnumProperty)
				{
					EnumProperty enumvalue = (EnumProperty) value;
					if (enumvalue.getEnum() == Constants.EN_ABSOLUTE)
					{
						return false;
					}
				}
			}
		}
		return true;
	}

}
