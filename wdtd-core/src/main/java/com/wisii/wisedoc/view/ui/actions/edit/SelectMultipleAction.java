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
 * @SelectMultipleAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-1
 */
public class SelectMultipleAction extends AbstractEditAction
{

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseSpinner)
		{
			WiseSpinner linespinner = (WiseSpinner) e.getSource();
			int lines = (Integer) linespinner.getValue();
			setFOProperty(Constants.PR_SELECT_MULTIPLE, lines);
		}

	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof WiseSpinner
				&& hasPropertyKey(Constants.PR_SELECT_MULTIPLE))
		{
			WiseSpinner linespinner = (WiseSpinner) uiComponent;
			Integer obj = (Integer) evt.getNewValue();
			if (obj != null)
			{
				linespinner.initValue(obj);
			} else
			{

				linespinner.initValue(1);
			}
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_SELECT_MULTIPLE))
		{
			if (uiComponent instanceof WiseSpinner)
			{
				WiseSpinner linespinner = (WiseSpinner) uiComponent;
				linespinner.initValue(1);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseSpinner)
		{
			WiseSpinner linespinner = (WiseSpinner) uiComponent;
			linespinner.initValue(1);
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
		EnumProperty showlist = (EnumProperty) temp
				.get(Constants.PR_SELECT_SHOWLIST);
		return showlist != null && showlist.getEnum() == Constants.EN_CHECK;
	}
}
