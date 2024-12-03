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
 * @SetBoxStyleLayerAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.checkbox;

import java.awt.event.ActionEvent;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-12-21
 */
public class SetBoxStyleLayerAction extends Actions
{
	@Override
	public void doAction(final ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			final WiseCombobox value = (WiseCombobox) e.getSource();
			final int index = value.getSelectedIndex();
			if (index > -1)
			{
				setFOProperty(Constants.PR_GRAPHIC_LAYER, index);
			}
		}
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_GRAPHIC_LAYER))
		{
			final Object obj = evt.getNewValue();
			if (uiComponent instanceof WiseCombobox)
			{
				final WiseCombobox ui = (WiseCombobox) uiComponent;
				if (obj instanceof Integer)
				{
					ui.initIndex((Integer) obj);
				} else
				{
					ui.initIndex(0);
				}
			}
		}
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseCombobox)
		{
			final WiseCombobox ui = (WiseCombobox) uiComponent;
			ui.initIndex(0);
		}
	}

	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof WiseCombobox)
		{
			final WiseCombobox ui = (WiseCombobox) uiComponent;
			ui.InitValue(null);
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		Map<Integer, Object> temp = RibbonUIModel
				.getReadCompletePropertiesByType().get(actionType);
		if (temp == null)
		{
			return true;
		}
		Object boxstyle = temp.get(Constants.PR_CHECKBOX_BOXSTYLE);
		if (boxstyle instanceof EnumProperty
				&& ((EnumProperty) boxstyle).getEnum() == Constants.EN_CHECKBOX_BOXSTYLE_CIRCLE)
		{
			return false;
		}
		return true;
	}
}
