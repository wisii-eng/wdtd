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
package com.wisii.wisedoc.view.ui.actions.barcode;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 定义供人识别字符内容
 * @author 闫舒寰
 * @version 1.0 2009/02/03
 */
public class BarcodeStringAction extends Actions{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JTextField)
		{
			JTextField value = (JTextField) e.getSource();
			String text = value.getText();
			if (text == null || text.trim().isEmpty())
			{
				setFOProperty(Constants.PR_BARCODE_STRING, null);
			} else
			{
				setFOProperty(Constants.PR_BARCODE_STRING, text);
			}
		}
	}
	
	@Override
	protected void uiStateChange(UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		
		Object obj = evt.getNewValue();
		if (hasPropertyKey(Constants.PR_BARCODE_STRING)) {
			if (uiComponent instanceof JTextField) {
				JTextField ui = (JTextField) uiComponent;
				if (obj instanceof BarCodeText) {
					BarCodeText value = (BarCodeText) obj;
					ui.setText(value.getText());
				}
			}
		}
	}
	
	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_BARCODE_STRING)) {
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
			ui.setText("没有条形码所显示内容....");
		}
	}
	
	@Override
	public boolean isAvailable() {
		return super.isAvailable();
	}

}
