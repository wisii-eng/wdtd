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
 * @SetHeightAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.wordarttext;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：设置艺术字框的大小
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-15
 */
public class SetHeightAction extends Actions
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
		if (e.getSource() instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner widthsp = (FixedLengthSpinner) e.getSource();
			setFOProperty(Constants.PR_HEIGHT, widthsp.getValue());
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof FixedLengthSpinner
				&& hasPropertyKey(Constants.PR_HEIGHT))
		{
			FixedLengthSpinner flsp = (FixedLengthSpinner) uiComponent;
			Object obj = evt.getNewValue();
			flsp.initValue(obj);
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		if (evt.hasPropertyKey(Constants.PR_HEIGHT))
		{
			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner box = (FixedLengthSpinner) uiComponent;
				box.initValue(null);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		if (uiComponent instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner box = (FixedLengthSpinner) uiComponent;
			box.initValue(null);
		}

	}

}
