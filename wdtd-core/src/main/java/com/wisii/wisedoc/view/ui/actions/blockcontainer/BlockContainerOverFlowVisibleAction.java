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

import org.jvnet.flamingo.common.JCommandToggleButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 块容器溢出部分可视动作
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/04
 */
public class BlockContainerOverFlowVisibleAction extends Actions
{

	/**
	 * TODO 这里有个问题就是默认显示的问题，FO规范中，7.21.2 overflow这个属性的默认值是auto，而不是Ribbon中现有的属性
	 * 而根据规范，这个auto属性的溢出现象应该是可以滚动的，而目前的效果和可视一样，这个需要解决一下
	 * 
	 */

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCommandToggleButton)
		{
			JCommandToggleButton button = (JCommandToggleButton) e.getSource();
			if (button.getActionModel().isSelected())
			{
				setFOProperty(Constants.PR_OVERFLOW, new EnumProperty(
						Constants.EN_VISIBLE, ""));
			} else
			{
				setFOProperty(Constants.PR_OVERFLOW, new EnumProperty(
						Constants.EN_AUTO, ""));
			}
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_OVERFLOW))
		{
			if (uiComponent instanceof JCommandToggleButton)
			{
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				Object overflow = evt.getNewValue();
				if (overflow instanceof EnumProperty
						&& ((EnumProperty) overflow).getEnum() == Constants.EN_VISIBLE)
				{
					ui.getActionModel().setSelected(true);
				} else
				{
					ui.getActionModel().setSelected(false);
				}
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_OVERFLOW))
		{
			if (uiComponent instanceof JCommandToggleButton)
			{
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				ui.getActionModel().setSelected(false);
			}
		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (uiComponent instanceof JCommandToggleButton)
		{
			JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
			ui.getActionModel().setSelected(false);
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		Map<Integer, Object> attr = RibbonUIModel
				.getReadCompletePropertiesByType().get(actionType);
		if (attr != null)
		{
			Object lengthold = attr
					.get(Constants.PR_BLOCK_PROGRESSION_DIMENSION);
			if (lengthold != null && lengthold instanceof LengthRangeProperty)
			{
				LengthRangeProperty lrp = (LengthRangeProperty) lengthold;
				LengthProperty length = lrp.getOptimum(null);
				if (length instanceof FixedLength)
				{
					return true;
				}
			}
		}
		return false;

	}
}
