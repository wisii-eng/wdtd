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
 * @EditTypeAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.ribbon.edit.Componentband;

/**
 * 类功能描述：编辑类型设置Action
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-9
 */
public class EditTypeAction extends Actions
{
	private Componentband componentband;

	/**
	 * @param componentband
	 *            设置componentband成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setComponentband(Componentband componentband)
	{
		this.componentband = componentband;
	}

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox editbox = (WiseCombobox) e.getSource();
			EnumProperty oldtype = (EnumProperty) RibbonUIModel
					.getReadCompletePropertiesByType().get(actionType).get(
							Constants.PR_EDITTYPE);
			EnumProperty type = create(editbox);
			setProperty(oldtype, type);
		}

	}

	private void setProperty(EnumProperty oldtype, EnumProperty type)
	{
		if (oldtype != null)
		{
			if (!oldtype.equals(type))
			{
				Map<Integer, Object> newpropertys = new HashMap<Integer, Object>();
				newpropertys.put(Constants.PR_INPUT_TYPE, null);
				newpropertys.put(Constants.PR_INPUT_MULTILINE, null);
				newpropertys.put(Constants.PR_INPUT_WRAP, null);
				newpropertys.put(Constants.PR_INPUT_FILTER, null);
				newpropertys.put(Constants.PR_INPUT_FILTERMSG, null);
				newpropertys.put(Constants.PR_DATE_TYPE, null);
				newpropertys.put(Constants.PR_DATE_FORMAT, null);
				newpropertys.put(Constants.PR_RADIO_CHECK_VALUE, null);
				newpropertys.put(Constants.PR_CHECKBOX_UNSELECT_VALUE, null);
				newpropertys.put(Constants.PR_CHECKBOX_BOXSTYLE, null);
				newpropertys.put(Constants.PR_CHECKBOX_TICKMARK, null);
				newpropertys.put(Constants.PR_RADIO_CHECK_CHECKED, null);
				newpropertys.put(Constants.PR_SELECT_TYPE, null);
				newpropertys.put(Constants.PR_SELECT_MULTIPLE, null);
				newpropertys.put(Constants.PR_SELECT_LINES, null);
				newpropertys.put(Constants.PR_SELECT_ISEDIT, null);
				newpropertys.put(Constants.PR_SELECT_NEXT, null);
				newpropertys.put(Constants.PR_SELECT_INFO, null);
				newpropertys.put(Constants.PR_POPUPBROWSER_INFO, null);
				newpropertys.put(Constants.PR_SELECT_SHOWLIST, null);
				newpropertys.put(Constants.PR_SELECT_NAME, null);
				newpropertys.put(Constants.PR_GROUP_REFERANCE, null);
				newpropertys.put(Constants.PR_DEFAULT_VALUE,null);
				newpropertys.put(Constants.PR_EDITTYPE, type);
				setFOProperties(newpropertys);

			}
		} else
		{
			setFOProperty(Constants.PR_EDITTYPE, type);
		}

	}

	private EnumProperty create(WiseCombobox editbox)
	{
		int selectindex = editbox.getSelectedIndex();
		EnumProperty type = null;
		switch (selectindex)
		{
		case 1:
		{
			type = new EnumProperty(Constants.EN_INPUT, "");
			break;
		}
		case 2:
		{
			type = new EnumProperty(Constants.EN_SELECT, "");
			break;
		}
		case 3:
		{
			type = new EnumProperty(Constants.EN_DATE, "");
			break;
		}
		case 4:
		{
			type = new EnumProperty(Constants.EN_POPUPBROWSER, "");
			break;
		}
		default:
			break;
		}
		return type;
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof WiseCombobox
				&& hasPropertyKey(Constants.PR_EDITTYPE))
		{

			Object obj = evt.getNewValue();
			updateUI((WiseCombobox) uiComponent, obj);
		}
	}

	private void updateUI(WiseCombobox uiComponent, Object obj)
	{
		if (obj == null)
		{
			uiComponent.initIndex(0);
		} else if (obj instanceof EnumProperty)
		{
			int type = ((EnumProperty) obj).getEnum();
			switch (type)
			{
			case Constants.EN_INPUT:
			{
				uiComponent.initIndex(1);
				break;
			}
			case Constants.EN_SELECT:
			{
				uiComponent.initIndex(2);
				break;
			}
			case Constants.EN_DATE:
			{
				uiComponent.initIndex(3);
				break;
			}
			case Constants.EN_POPUPBROWSER:
			{
				uiComponent.initIndex(4);
				break;
			}
			default:
				break;
			}
			componentband.typeChanged((EnumProperty) obj);
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_EDITTYPE))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox box = (WiseCombobox) uiComponent;
				box.InitValue(null);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox box = (WiseCombobox) uiComponent;
			box.initIndex(0);
		}

	}
}
