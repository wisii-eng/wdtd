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
 * @ChartLayerAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-11-26
 */
public class ChartLayerAction extends Actions
{
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JComboBox)
		{
			JComboBox value = (JComboBox) e.getSource();
			int index = value.getSelectedIndex();
			if (index > -1)
			{
				setFOProperty(Constants.PR_GRAPHIC_LAYER, index);
			}
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_GRAPHIC_LAYER))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				Object value = evt.getNewValue();
				if (value instanceof Integer)
				{
					ui.initIndex((Integer)value);
				} 
				else
				{
					ui.initIndex(0);
				}
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox ui = (WiseCombobox) uiComponent;
			ui.initIndex(0);
		}
	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox ui = (WiseCombobox) uiComponent;
			ui.InitValue(null);
		}
	}
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		List<CellElement> elements = getObjectSelects();
		if (elements == null)
		{
			return false;
		}
		List<Class<?>> elementClass = new LinkedList<Class<?>>();
		for (Object object : elements)
		{
			if(object instanceof Chart)
			{
				return true;
			}
		}

		return false;
	}
}
