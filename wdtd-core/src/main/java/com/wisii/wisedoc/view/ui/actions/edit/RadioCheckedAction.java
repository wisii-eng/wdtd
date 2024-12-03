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
 * @RadioCheckedAction.java
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
 * 作者：zhangqiang 创建日期：2009-7-13
 */
public class RadioCheckedAction extends AbstractEditAction
{

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCheckBox)
		{
			JCheckBox inputsize = (JCheckBox) e.getSource();
			if (inputsize.isSelected())
			{
				setFOProperty(Constants.PR_RADIO_CHECK_CHECKED,
						new EnumProperty(Constants.EN_TRUE, ""));
			} else
			{
				setFOProperty(Constants.PR_RADIO_CHECK_CHECKED, null);
			}
		}

	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof JCheckBox
				&& hasPropertyKey(Constants.PR_RADIO_CHECK_CHECKED))
		{
			JCheckBox sizeradio = (JCheckBox) uiComponent;
			EnumProperty obj = (EnumProperty) evt.getNewValue();

			if (obj != null && obj.getEnum() == Constants.EN_TRUE)
			{
				sizeradio.setSelected(true);
			} else
			{
				sizeradio.setSelected(false);
			}
		}
	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_RADIO_CHECK_CHECKED))
		{
			if (uiComponent instanceof JCheckBox)
			{
				JCheckBox sizeradio = (JCheckBox) uiComponent;
				sizeradio.setSelected(false);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof JCheckBox)
		{
			JCheckBox sizeradio = (JCheckBox) uiComponent;
			sizeradio.setSelected(false);
		}

	}
}
