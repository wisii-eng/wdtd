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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg图形X点的位置
 * 
 * @author 闫舒寰
 * @version 1.0 2009/03/03
 */
public class SvgGraphicXAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner value = (FixedLengthSpinner) e.getSource();
			Map<Integer, Object> temp = new HashMap<Integer, Object>(
					RibbonUIModel.getReadCompletePropertiesByType().get(
							this.actionType));

			// 针对绝对定位设置属性
			Object container = temp.get(Constants.PR_SVG_CONTAINER);
			if (container != null && container instanceof Map)
			{
				Map<Integer, Object> containerMap = new HashMap<Integer, Object>(
						(Map<Integer, Object>) container);
				containerMap.put(Constants.PR_LEFT, value.getValue());
				temp.put(Constants.PR_SVG_CONTAINER, containerMap);
			}

			// 针对相对定位设置属性
			Object canvas = temp.get(Constants.PR_SVG_CANVAS);
			if (canvas != null && canvas instanceof Map)
			{
				temp.put(Constants.PR_X, value.getValue());
			}

			setFOProperties(temp);
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		Map<Integer, Object> svgMap = RibbonUIModel
				.getReadCompletePropertiesByType().get(this.actionType);

		if (svgMap.get(Constants.PR_SVG_CONTAINER) != null)
		{
			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				FixedLength uivalue = null;
				Object mValue = svgMap.get(Constants.PR_SVG_CONTAINER);

				Object value = null;
				if (mValue instanceof Map)
				{
					Map<Integer, Object> vMap = (Map<Integer, Object>) mValue;
					if (vMap != null)
					{
						value = vMap.get(Constants.PR_LEFT);
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
				if (uivalue != null)
				{
					ui.initValue(uivalue);
				}
			}
		}

		else if (svgMap.get(Constants.PR_SVG_CANVAS) != null)
		{
			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				FixedLength uivalue = null;

				Object value = null;
				List<Object> elementList = RibbonUIModel.getElementList();
				if(elementList==null||elementList.isEmpty())
				{
					return;
				}
				Object element = elementList.get(0);

				if (element instanceof Line)
				{
					// 当图形为线的情况下
					value = svgMap.get(Constants.PR_X1);
				}
				 else if (element instanceof Rectangle||element instanceof Ellipse)
				{
					// 当图形为方框的时候
					value = svgMap.get(Constants.PR_X);
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
				if (uivalue != null)
				{
					ui.initValue(uivalue);
				}
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
			if (object instanceof AbstractSVG&&!(object instanceof Line))
			{
				return true;
			}
		}

			return false;
	}

}
