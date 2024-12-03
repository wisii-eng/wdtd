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

import javax.swing.text.JTextComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg图片内文字的字体设置
 * @author 闫舒寰
 * @version 1.0 2009/03/04
 */
public class SvgGraphicFontFamilyAction extends Actions {
	
	/**
	 * TODO
	 * 1、需要对输入字体进行输入验证
	 * 2、字体预览功能
	 * 
	 */

	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof WiseCombobox) {
			WiseCombobox jb = (WiseCombobox) e.getSource();
			setFOProperty(Constants.PR_FONT_FAMILY, jb.getSelectedItem());
		}
	}
	
	
	@Override
	protected void uiStateChange(UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_FONT_FAMILY)) {
			if (uiComponent instanceof WiseCombobox) {
				WiseCombobox ui = (WiseCombobox) uiComponent;
				ui.InitValue(evt.getNewValue());
			}
		}		
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		
		if (uiComponent != null) {
			if (uiComponent instanceof WiseCombobox) {
				WiseCombobox ui = (WiseCombobox) uiComponent;
				ui.InitValue("宋体");
			}
		}
	}
	
	@Override
	public void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		
		if (evt.hasPropertyKey(Constants.PR_FONT_FAMILY)) {
			if (uiComponent != null) {
				if (uiComponent instanceof WiseCombobox) {
					WiseCombobox ui = (WiseCombobox) uiComponent;
					JTextComponent editor = (JTextComponent) ui.getEditor().getEditorComponent();
					editor.setText("");
				}
			}
		}
	}
	
	@Override
	public boolean isAvailable() {
		return true;
	}
	
}
