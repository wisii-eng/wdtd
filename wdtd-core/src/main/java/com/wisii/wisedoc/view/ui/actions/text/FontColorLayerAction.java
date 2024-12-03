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
package com.wisii.wisedoc.view.ui.actions.text;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 设置字体颜色的层
 * @author 闫舒寰
 * @version 1.0 2009/02/03
 */
public class FontColorLayerAction extends Actions
{

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox ui = (WiseCombobox) e.getSource();
			int index = ui.getSelectedIndex();
			if (index > -1)
			{
				setFOProperty(Constants.PR_COLOR, new WiseDocColor(index));
			}
		}
	}

	public void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_COLOR))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				WiseDocColor color = (WiseDocColor) evt.getNewValue();
				if (color != null)
				{
					ui.initIndex(color.getLayer());
				} else
				{
					ui.initIndex(0);
				}
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (uiComponent != null)
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				ui.initIndex(0);
			}
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox ui = (WiseCombobox) uiComponent;
			ui.InitValue(null);
		}
	}

}
