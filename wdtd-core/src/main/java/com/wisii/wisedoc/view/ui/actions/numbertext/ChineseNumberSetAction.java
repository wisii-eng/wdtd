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
 * @ChineseNumberSetAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.numbertext;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.ribbon.numberformat.NumberFormatSetBand.NFSetBand;

/**
 * 类功能描述：中文数字格式化设置
 * 
 * 作者：zhangqiang 创建日期：2008-12-1
 */
public class ChineseNumberSetAction extends Actions
{

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox value = (WiseCombobox) e.getSource();
			int index = value.getSelectedIndex();
			EnumProperty set = null;
			if (index == 0)
			{
				set = new EnumProperty(Constants.EN_CHINESELOWERCASE,
						"chinese2");
			} else if (index == 1)
			{

				set = new EnumProperty(Constants.EN_CHINESECAPITAL, "chinese1");
			} else if (index == 2)
			{

				set = new EnumProperty(Constants.EN_CHINESECAPITAL_ZC,
						"chinese3");
			}
			else if (index == 3)
			{

				set = new EnumProperty(Constants.EN_CHINESELOWERCASE_ADDZHENG,
						"chinese4");
			}
			else if (index == 4)
			{

				set = new EnumProperty(Constants.EN_CHINESECAPITAL_ADDZHENG,
						"chinese5");
			}
			else if (index == 5)
			{

				set = new EnumProperty(Constants.EN_CHINESECAPITAL_ZC_ADDZHENG,
						"chinese6");
			}
			Map<Integer, Object> propertys = new HashMap<Integer, Object>();
			propertys.put(Constants.PR_NUMBERTEXT_TYPE, set);
			propertys.put(Constants.PR_NUMBERFORMAT, null);
			setFOProperties(propertys);
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_NUMBERTEXT_TYPE))
		{
			Object obj = evt.getNewValue();
			// 这段代码主要为了处理数字格式面板的切换
			if (uiComponent instanceof JComponent)
			{
				Container parent = ((JComponent) uiComponent).getParent();
				while (parent != null)
				{
					if (parent instanceof NFSetBand)
					{
						NFSetBand nfs = (NFSetBand) parent;
						nfs.changeCardPanel(obj != null);
						break;
					}
					parent = parent.getParent();
				}
			}
			// 切换代码end
			if (obj != null)
			{
				if (uiComponent instanceof WiseCombobox)
				{
					WiseCombobox ui = (WiseCombobox) uiComponent;

					if (obj instanceof EnumProperty)
					{
						int type = ((EnumProperty) obj).getEnum();
						if (type == Constants.EN_CHINESELOWERCASE)
						{
							ui.initIndex(0);
						} else if (type == Constants.EN_CHINESECAPITAL)
						{
							ui.initIndex(1);
						} else if (type == Constants.EN_CHINESECAPITAL_ZC)
						{
							ui.initIndex(2);
						}
						else if (type == Constants.EN_CHINESELOWERCASE_ADDZHENG)
						{
							ui.initIndex(3);
						}
						else if (type == Constants.EN_CHINESECAPITAL_ADDZHENG)
						{
							ui.initIndex(4);
						} else if (type == Constants.EN_CHINESECAPITAL_ZC_ADDZHENG)
						{
							ui.initIndex(5);
						}
					}

				}
			}
		} else
		{
			if (uiComponent instanceof JComponent)
			{
				Container parent = ((JComponent) uiComponent).getParent();
				while (parent != null)
				{
					if (parent instanceof NFSetBand)
					{
						NFSetBand nfs = (NFSetBand) parent;
						nfs.changeCardPanel(false);
						return;
					}
					parent = parent.getParent();
				}
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox ui = (WiseCombobox) uiComponent;

			ui.initIndex(0);
		}

		if (uiComponent instanceof JComponent)
		{
			JComponent component = (JComponent) uiComponent;
			Container parent = component.getParent();
			while (parent != null)
			{
				if (parent instanceof NFSetBand)
				{
					NFSetBand nfs = (NFSetBand) parent;
					nfs.changeCardPanel(false);
					return;
				}
				parent = parent.getParent();
			}
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		boolean ischinese = false;
		if (evt.hasPropertyKey(Constants.PR_NUMBERTEXT_TYPE))
		{
			ischinese = true;
		}
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox ui = (WiseCombobox) uiComponent;
			ui.setSelectedIndex(0);
			Container parent = ui.getParent();
			while (parent != null)
			{
				if (parent instanceof NFSetBand)
				{
					NFSetBand nfs = (NFSetBand) parent;
					nfs.changeCardPanel(ischinese);
					return;
				}
				parent = parent.getParent();
			}
		}
	}
}
