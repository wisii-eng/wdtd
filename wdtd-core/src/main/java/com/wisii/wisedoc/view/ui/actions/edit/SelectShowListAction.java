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
 * @SelectShowListAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-1
 */
public class SelectShowListAction extends AbstractEditAction
{

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCheckBox)
		{
			JCheckBox wrap = (JCheckBox) e.getSource();
			boolean isselect = wrap.isSelected();
			if (isselect)
			{
				setFOProperty(Constants.PR_SELECT_SHOWLIST, new EnumProperty(
						Constants.EN_CHECK, ""));
			} else
			{
				setFOProperty(Constants.PR_SELECT_SHOWLIST, new EnumProperty(
						Constants.EN_UNCHECK, ""));
			}
		}

	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof JCheckBox
				&& hasPropertyKey(Constants.PR_SELECT_SHOWLIST))
		{
			JCheckBox wrapradio = (JCheckBox) uiComponent;
			EnumProperty obj = (EnumProperty) evt.getNewValue();
			boolean flg = obj != null && obj.getEnum() == Constants.EN_CHECK;
			wrapradio.setSelected(flg);
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_SELECT_SHOWLIST))
		{
			if (uiComponent instanceof JCheckBox)
			{
				JCheckBox wrapradio = (JCheckBox) uiComponent;
				wrapradio.setSelected(false);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof JCheckBox)
		{
			JCheckBox wrapradio = (JCheckBox) uiComponent;
			wrapradio.setSelected(false);
		}
	}
}
