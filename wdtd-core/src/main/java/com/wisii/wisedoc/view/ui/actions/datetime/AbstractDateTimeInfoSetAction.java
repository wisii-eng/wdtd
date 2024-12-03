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
 * @AbstractDateTimeInfoSetAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.datetime;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：设置日期时间项的抽象类
 * 
 * 作者：zhangqiang 创建日期：2008-12-2
 */
public abstract class AbstractDateTimeInfoSetAction extends Actions
{

	protected WisedocDateTimeFormat oldformat;

	public void doAction(ActionEvent e)
	{
		WisedocDateTimeFormat newformat = createformat(e);
		if (newformat != null)
		{
			setFOProperty(Constants.PR_DATETIMEFORMAT, newformat);
		}

	}

	/**
	 * 
	 *根据界面操作创建新的fomat对象
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	protected abstract WisedocDateTimeFormat createformat(ActionEvent e);

	protected abstract void UpDateUI();

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_DATETIMEFORMAT))
		{
			Object obj = evt.getNewValue();
			if ((obj==null&&oldformat!=null)||(obj!=null&&!obj.equals(oldformat)))
			{
				if (oldformat instanceof WisedocDateTimeFormat)
				{
					oldformat = (WisedocDateTimeFormat) obj;
				}
				if (oldformat == null)
				{
					oldformat = createDefaultFormat();
				}
				if (uiComponent instanceof JComboBox)
				{
					JComboBox box = (JComboBox) uiComponent;
					if (box.isEnabled())
					{
						UpDateUI();
					}
				} else
				{
					UpDateUI();
				}

			}
		}
	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_DATETIMEFORMAT))
		{
			oldformat = createDefaultFormat();
			if (uiComponent instanceof JComboBox)
			{
				JComboBox box = (JComboBox) uiComponent;
				if (box.isEnabled())
				{
					UpDateUI();
				}
			} else
			{
				UpDateUI();
			}
		}
	}

	protected WisedocDateTimeFormat createDefaultFormat()
	{
		return (WisedocDateTimeFormat) InitialManager.getInitialValue(
				Constants.PR_DATETIMEFORMAT, null);
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		oldformat = createDefaultFormat();
		if (uiComponent instanceof JComboBox)
		{
			JComboBox box = (JComboBox) uiComponent;
			if (box.isEnabled())
			{
				UpDateUI();
			}
		} else
		{
			UpDateUI();
		}

	}
	public boolean isAvailable()
	{
		return super.isAvailable();
	}

}
