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
 * @SetPathVisableAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.wordarttext;

import java.awt.event.ActionEvent;

import javax.swing.JRadioButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：设置文本绘制路径是否显示Action
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-15
 */
public class SetPathVisableAction extends Actions
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
		if (e.getSource() instanceof JRadioButton)
		{
			JRadioButton pathvisable = (JRadioButton) e.getSource();
			if (pathvisable.isSelected())
			{
				setFOProperty(Constants.PR_WORDARTTEXT_PATHVISABLE,
						new EnumProperty(Constants.EN_TRUE, ""));
			} else
			{
				setFOProperty(Constants.PR_WORDARTTEXT_PATHVISABLE, null);
			}
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof JRadioButton
				&& hasPropertyKey(Constants.PR_WORDARTTEXT_PATHVISABLE))
		{
			JRadioButton rb = (JRadioButton) uiComponent;
			Object obj = evt.getNewValue();
			rb.setSelected(obj != null
					&& ((EnumProperty) obj).getEnum() == Constants.EN_TRUE);
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		if (evt.hasPropertyKey(Constants.PR_WORDARTTEXT_PATHVISABLE))
		{
			if (uiComponent instanceof JRadioButton)
			{
				JRadioButton rb = (JRadioButton) uiComponent;
				rb.setSelected(false);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		if (uiComponent instanceof JRadioButton)
		{
			JRadioButton rb = (JRadioButton) uiComponent;
			rb.setSelected(false);
		}

	}

}
