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

import javax.swing.JComboBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg线段拐点类型
 * 
 * @author 闫舒寰
 * @version 1.0 2009/03/06
 */
public class SvgGraphicStrokeLineJoin extends Actions
{
	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JComboBox)
		{
			JComboBox ui = (JComboBox) e.getSource();
			setProperty(ui.getSelectedIndex());
		}
	}

	private void setProperty(int index)
	{

		switch (index)
		{
		case 0:
			// 尖角
			setFOProperty(Constants.PR_SVG_STROKE_LINEJOIN, "miter");
			break;
		case 1:
			// 圆角
			setFOProperty(Constants.PR_SVG_STROKE_LINEJOIN, "round");
			break;
		case 2:
			// 平角
			setFOProperty(Constants.PR_SVG_STROKE_LINEJOIN, "bevel");
			break;

		default:
			break;
		}

	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_SVG_STROKE_LINEJOIN))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				final WiseCombobox ui = (WiseCombobox) uiComponent;
				final Object value = evt.getNewValue();
				if ("round".equals(value))
				{
					ui.initIndex(1);
				} else if ("bevel".equals(value))
				{
					ui.initIndex(2);
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
	public boolean isAvailable()
	{
		if(!super.isAvailable())
		{
			return false;
		}
		List<Object> elementList = RibbonUIModel.getElementList();
		if(elementList==null)
		{
			return false;
		}
		for (Object object : elementList)
		{
			if (object instanceof AbstractSVG&&!(object instanceof Line)&&!(object instanceof Ellipse))
			{
				return true;
			}
		}
			return false;
	}
}
