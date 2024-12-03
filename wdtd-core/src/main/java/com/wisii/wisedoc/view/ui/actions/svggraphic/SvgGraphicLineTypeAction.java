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

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg图形线条样式动作
 * @author 闫舒寰
 * @version 1.0 2009/03/06
 */
public class SvgGraphicLineTypeAction extends Actions {
	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof WiseCombobox) {
			final WiseCombobox ui = (WiseCombobox) e.getSource();
			setProperty(ui.getSelectedIndex());
		}
	}
	
	private void setProperty(final int index){
		setFOProperty(Constants.PR_SVG_LINE_TYPE, index);
	}
	
	@Override
	protected void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_SVG_LINE_TYPE)) {
			if (uiComponent instanceof WiseCombobox) {
				final WiseCombobox ui = (WiseCombobox)uiComponent;
				final Object value = evt.getNewValue();
				// System.out.println("in action: " + value);
				if (value instanceof Integer) {
					ui.initIndex((Integer)value);
				}
				else
				{
					ui.initIndex(0);
				}
			}
		} 
	}
	
	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseCombobox) {
			final WiseCombobox ui = (WiseCombobox)uiComponent;
			ui.initIndex(0);
		}
	}
}
