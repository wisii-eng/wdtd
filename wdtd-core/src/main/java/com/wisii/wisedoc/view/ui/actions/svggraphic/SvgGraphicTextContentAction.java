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

import javax.swing.JTextField;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg图片上文字输入的动作
 * @author 闫舒寰
 * @version 1.0 2009/03/04
 */
public class SvgGraphicTextContentAction extends Actions {
	
	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField value = (JTextField) e.getSource();
			
			setFOProperty(Constants.PR_SVG_TEXT_CONTENT, value.getText());
		}
	}
	
	
	@Override
	protected void uiStateChange(UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		
		if (hasPropertyKey(Constants.PR_SVG_TEXT_CONTENT)) {
			Object obj = evt.getNewValue();
			if (uiComponent instanceof JTextField) {
				JTextField ui = (JTextField) uiComponent;
				if (obj instanceof String) {
					String value = (String) obj;
					ui.setText(value);
				}
				else
				{
					ui.setText("");
				}
			}
		} 
	}
	
	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_BARCODE_CONTENT)) {
			if (uiComponent instanceof JTextField) {
				JTextField ui = (JTextField) uiComponent;
				ui.setText("");
			}
		}
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof JTextField) {
			JTextField ui = (JTextField) uiComponent;
			ui.setText("请输入条形码内容");
		}
	}
	
	@Override
	public boolean isAvailable() {
		return super.isAvailable();
	}

}
