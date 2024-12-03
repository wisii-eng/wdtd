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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg图形画板的宽度设置动作
 * @author 闫舒寰
 * @version 1.0 2009/03/03
 */
public class SvgGraphicCanvasWidthAction extends Actions {

	
	private int constantsKey = Constants.PR_SVG_CANVAS;

	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner value = (FixedLengthSpinner) e.getSource();
			setFOProperty(Constants.PR_WIDTH, value.getValue());
		}
	}
	
	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(constantsKey))
		{
			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				FixedLength uivalue = null;
				Object mValue = evt.getNewValue();
				
				Object value = null;
				if (mValue instanceof Map) {
					Map<Integer, Object> vMap = (Map<Integer, Object>) mValue;
					if (vMap != null) {
						value = vMap.get(Constants.PR_WIDTH);
					}
				}
				
				if (value instanceof LengthRangeProperty)
				{
					value = ((LengthRangeProperty) value).getOptimum(null);
					if (value instanceof FixedLength)
					{
						uivalue = (FixedLength) value;
					}
				} else if (value instanceof FixedLength)
				{
					uivalue = (FixedLength) value;
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
			ui.initValue(new FixedLength(0));
		}
	}
	
	@Override
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
			elementClass.add(object.getClass());
		}

		if (elementClass.contains(Canvas.class))
		{
			return true;
		}
		return false;
	}

}
