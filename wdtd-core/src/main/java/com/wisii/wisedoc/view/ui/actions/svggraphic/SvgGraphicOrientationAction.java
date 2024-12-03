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
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg图形方向动作
 * @author 闫舒寰
 * @version 1.0 2009/03/10
 */
public class SvgGraphicOrientationAction extends Actions
{

	/**
	 * TODO 1、目前条码只支持4个方向的值的设定，要是用户输入其他值需要有提示，目前还没有
	 */

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseSpinner)
		{
			WiseSpinner value = (WiseSpinner) e.getSource();

			int dValue = (Integer) value.getValue();

			// 目前只支持这四个值的设置
			setFOProperty(Constants.PR_SVG_ORIENTATION, dValue);
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_SVG_ORIENTATION))
		{
			if (uiComponent instanceof WiseSpinner)
			{
				WiseSpinner ui = (WiseSpinner) uiComponent;
				Object ori = evt.getNewValue();
				if (ori instanceof Integer)
				{
					ui.initValue(ori);
				} else
				{
					ui.initValue(0);
				}

			}
		}
	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);

		if (uiComponent instanceof WiseSpinner)
		{
			WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(0);
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (uiComponent instanceof WiseSpinner)
		{
			WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(0);
		}
	}

	public boolean isAvailable()
	{
		return super.isAvailable();
	}

}
