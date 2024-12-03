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
package com.wisii.wisedoc.view.ui.actions.svggraphic;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg图形层的设置动作
 * 
 * @author 闫舒寰
 * @version 1.0 2009/03/04
 */
public class SvgGraphicLayerAction extends Actions
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
		if (hasPropertyKey(Constants.PR_SVG_CANVAS)
				|| hasPropertyKey(Constants.PR_SVG_CONTAINER))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				Object mValue = evt.getNewValue();

				Object value = null;
				if (mValue instanceof Map)
				{
					Map<Integer, Object> vMap = (Map<Integer, Object>) mValue;
					if (vMap != null)
					{
						value = vMap.get(Constants.PR_GRAPHIC_LAYER);
					}
				}

				if (value instanceof Integer)
				{
					ui.initIndex((Integer) value);
				} else
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
		for (Object object : elements)
		{
			if (object instanceof Canvas
					|| (object instanceof AbstractSVG && ((AbstractSVG) object)
							.getParent() instanceof SVGContainer))
			{
				return true;
			}

		}
		return false;
	}
}
