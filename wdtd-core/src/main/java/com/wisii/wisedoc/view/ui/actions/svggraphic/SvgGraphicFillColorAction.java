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
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 填充svg图形
 * 
 * @author 闫舒寰
 * @version 1.0 2009/03/06
 */
public class SvgGraphicFillColorAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof ColorComboBox)
		{
			ColorComboBox colorCom = (ColorComboBox) e.getSource();
			Object selectedColor = colorCom.getSelectedItem();
			Map<Integer, Object> att = new HashMap<Integer, Object>();
			if (selectedColor == null)
			{
				selectedColor = new EnumProperty(Constants.EN_NONE, "");
			}
			att.put(Constants.PR_FILL, selectedColor);
			setFOProperties(att);
		}
	}
	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_FILL)) {
			if (uiComponent instanceof ColorComboBox) {
				final ColorComboBox ui = (ColorComboBox)uiComponent;
				final Object value = evt.getNewValue();
				ui.InitValue(value);
			}
		}
	}
	
	
	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof ColorComboBox) 
		{
			((ColorComboBox)uiComponent).InitValue(null);
		}
	}
	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		List<Object> elementList = RibbonUIModel.getElementList();
		if (elementList == null)
		{
			return false;
		}
		for (Object object : elementList)
		{
			if (object instanceof AbstractSVG && !(object instanceof Line))
			{
				return true;
			}
		}
		return false;
	}
}
