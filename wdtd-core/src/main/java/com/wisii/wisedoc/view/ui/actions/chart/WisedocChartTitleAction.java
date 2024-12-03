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
/**
 * @WisedocChartTitleAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：用于设置统计图标题、字体相关信息。
 * 
 * 作者：李晓光 创建日期 2009-5-21
 */
public class WisedocChartTitleAction extends Actions {
	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof JTextField) {
			final JTextField value = (JTextField) e.getSource();
			final BarCodeText text = new BarCodeText(value.getText());
			setFOProperty(Constants.PR_TITLE, text);
		}
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_TITLE))
		{
			if (!(uiComponent instanceof JTextField))
			{
				return;
			}
			final JTextField ui = (JTextField) uiComponent;
			final Object obj = evt.getNewValue();
			String text = "";
			if (obj instanceof BarCodeText)
			{
				text = ((BarCodeText) obj).toString();
			}
			ui.setText(text);
		}
	}
	
	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_TITLE)) {
			if (uiComponent instanceof JTextField) {
				final JTextField ui = (JTextField) uiComponent;
				ui.setText("");
			}
		}
	}
	
	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof JTextField) {
			final JTextField ui = (JTextField) uiComponent;
			ui.setText("统计图");
		}
	}
}
