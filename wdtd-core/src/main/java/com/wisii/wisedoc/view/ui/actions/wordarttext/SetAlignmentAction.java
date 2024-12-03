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
 * @SetAlignmentAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.wordarttext;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：设置艺术字在绘制路径上的对齐方式
 * 
 * 作者：zhangqiang 创建日期：2009-9-15
 */
public class SetAlignmentAction extends Actions
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
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox align = (WiseCombobox) e.getSource();
			int selectindex = align.getSelectedIndex();
			Map<Integer,Object> newAtts = new HashMap<Integer, Object>();
			if (selectindex == 0)
			{
				newAtts.put(Constants.PR_TEXT_ALIGN, null);
				newAtts.put(Constants.PR_WORDARTTEXT_STARTPOSITION, 0);
			} else if (selectindex == 1)
			{
				newAtts.put(Constants.PR_TEXT_ALIGN, new EnumProperty(
						Constants.EN_MIDDLE, ""));
				newAtts.put(Constants.PR_WORDARTTEXT_STARTPOSITION, 50);
			} else
			{
				newAtts.put(Constants.PR_TEXT_ALIGN, new EnumProperty(
						Constants.EN_END, ""));
				newAtts.put(Constants.PR_WORDARTTEXT_STARTPOSITION, 100);
			}
			setFOProperties(newAtts);
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof WiseCombobox
				&& hasPropertyKey(Constants.PR_TEXT_ALIGN))
		{
			WiseCombobox box = (WiseCombobox) uiComponent;
			Object obj = evt.getNewValue();

			if (obj == null)
			{
				box.initIndex(0);
			} else if (obj instanceof EnumProperty)
			{
				int align = ((EnumProperty) obj).getEnum();
				if (align == Constants.EN_MIDDLE)
				{
					box.initIndex(1);
				} else if (align == Constants.EN_END)
				{
					box.initIndex(2);
				} else
				{
					box.initIndex(0);
				}
			}
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		if (evt.hasPropertyKey(Constants.PR_TEXT_ALIGN))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox box = (WiseCombobox) uiComponent;
				box.initIndex(0);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox box = (WiseCombobox) uiComponent;
			box.initIndex(0);
		}
	}

}
