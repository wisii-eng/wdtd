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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 设置svg图形线的颜色
 * @author 闫舒寰
 * @version 1.0 2009/03/06
 */
public class SvgGraphicLineColorAction extends Actions {

	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof ColorComboBox) {
			final ColorComboBox colorCom = (ColorComboBox) e.getSource();
			final Color selectedColor = (Color) colorCom.getSelectedItem();
			final Map<Integer, Object> att = new HashMap<Integer, Object>();
			att.put(Constants.PR_COLOR, selectedColor);
			
			if (selectedColor != null) {
				att.put(Constants.PR_BACKGROUND_IMAGE, null);
			}
			setFOProperties(att);
		}
	}
	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_COLOR)) {
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

}
