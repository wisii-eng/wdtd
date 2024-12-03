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
 * @SelectTypeAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-1
 */
public class SelectTypeAction extends AbstractEditAction
{
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox selecttype = (WiseCombobox) e.getSource();
			int selectindex = selecttype.getSelectedIndex();
			EnumProperty type;
			if(selectindex==1){
				type = new EnumProperty(Constants.EN_LIST,"");
			}
			else
			{
				type = null;
			}
			setFOProperty(Constants.PR_SELECT_TYPE, type);
		}

	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof WiseCombobox
				&& hasPropertyKey(Constants.PR_SELECT_TYPE))
		{
			WiseCombobox typebox = (WiseCombobox) uiComponent;
			EnumProperty obj = (EnumProperty) evt.getNewValue();

			if (obj != null && obj.getEnum() == Constants.EN_LIST)
			{
				typebox.initIndex(1);
			} else
			{

				typebox.initIndex(0);
			}
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_SELECT_TYPE))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox typebox = (WiseCombobox) uiComponent;
				typebox.initIndex(0);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox typebox = (WiseCombobox) uiComponent;
			typebox.initIndex(0);
		}

	}
}
