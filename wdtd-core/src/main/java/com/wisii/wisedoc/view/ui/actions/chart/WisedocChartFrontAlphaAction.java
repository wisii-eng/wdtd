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
 * @WisedocChartFrontAlphaAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.chart;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：用于设置统计图的前景透明度。
 * 
 * 作者：李晓光 创建日期：2009-5-21
 */
public class WisedocChartFrontAlphaAction extends Actions {
	@Override
	public void doAction(final ActionEvent e)
	{
		if (e.getSource() instanceof WiseSpinner)
		{
			final WiseSpinner valuecom = (WiseSpinner) e.getSource();
			final Object value = valuecom.getValue();
			setFOProperty(Constants.PR_FOREGROUND_ALAPH, value);
		}
	}

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_FOREGROUND_ALAPH))
		{
			final Object obj = evt.getNewValue();
			if (uiComponent instanceof WiseSpinner)
			{
				final WiseSpinner ui = (WiseSpinner) uiComponent;
				if (obj instanceof Integer)
				{

					ui.initValue(obj);

				} else
				{
					ui.initValue(1);
				}
			}

		} 
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseSpinner)
		{
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			/*ui.initValue(InitialManager.getInitialValue(Constants.PR_FOREGROUND_ALAPH, null));*/
		}
	}

	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof WiseSpinner)
		{
			final WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(null);
		}
	}

	@Override
	public boolean isAvailable()
	{
		return super.isAvailable();
	}
}
