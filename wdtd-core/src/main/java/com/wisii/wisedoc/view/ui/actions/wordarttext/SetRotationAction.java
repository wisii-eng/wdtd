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
 * @SetRotationAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.wordarttext;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：设置旋转角度
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-15
 */
public class SetRotationAction extends Actions
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.ui.actions.Actions#doAction(java.awt.event.ActionEvent
	 * )
	 */
	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseSpinner)
		{
			WiseSpinner widthsp = (WiseSpinner) e.getSource();
			setFOProperty(Constants.PR_WORDARTTEXT_ROTATION, widthsp.getValue());
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof WiseSpinner
				&& hasPropertyKey(Constants.PR_WORDARTTEXT_ROTATION))
		{
			WiseSpinner flsp = (WiseSpinner) uiComponent;
			Object obj = evt.getNewValue();
			flsp.initValue(obj);
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		if (evt.hasPropertyKey(Constants.PR_WORDARTTEXT_ROTATION))
		{
			if (uiComponent instanceof WiseSpinner)
			{
				WiseSpinner box = (WiseSpinner) uiComponent;
				box.initValue(null);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		if (uiComponent instanceof WiseSpinner)
		{
			WiseSpinner box = (WiseSpinner) uiComponent;
			box.initValue(null);
		}

	}

}
