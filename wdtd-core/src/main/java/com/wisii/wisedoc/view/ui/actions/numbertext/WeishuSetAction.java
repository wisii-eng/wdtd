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
 * @ThousandsSetAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.numbertext;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.NumberFormat;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：千分位设置事件类
 * 
 * 作者：zhangqiang 创建日期：2008-11-27
 */
public class WeishuSetAction extends Actions
{
	private NumberFormat oldformat;

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseSpinner)
		{
			WiseSpinner value = (WiseSpinner) e.getSource();
			Object count = value.getValue();
			NumberFormat nf = getNumberformat(count);
			if (nf != null)
			{
				Map<Integer, Object> propertys = new HashMap<Integer, Object>();
				propertys.put(Constants.PR_NUMBERTEXT_TYPE, null);
				propertys.put(Constants.PR_NUMBERFORMAT, nf);
				setFOProperties(propertys);
			}
		}
	}

	private NumberFormat getNumberformat(Object count)
	{
		if (count instanceof Integer)
		{
			NumberFormat nf = null;
			if (oldformat != null)
			{
				nf = new NumberFormat((Integer) count, oldformat.isHasthousseparator(), oldformat
						.isIsbaifenbi());
			} else
			{
				nf = new NumberFormat((Integer) count, true, false);
			}
			return nf;
		}
		return null;
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_NUMBERFORMAT))
		{
			Object obj = evt.getNewValue();
			if (uiComponent instanceof WiseSpinner)
			{
				WiseSpinner ui = (WiseSpinner) uiComponent;
				if (obj instanceof NumberFormat)
				{
					oldformat = (NumberFormat) obj;
					ui.initValue(oldformat.getDecimalDigits());
				} else
				{
					ui.initValue(2);
					oldformat = new NumberFormat(2, true, false);
				}
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_NUMBERFORMAT))
		{
			if (uiComponent instanceof WiseSpinner)
			{
				WiseSpinner ui = (WiseSpinner) uiComponent;
				ui.initValue(null);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		oldformat = new NumberFormat(2, true,false);
		if (uiComponent instanceof WiseSpinner)
		{
			WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(2);
		}

	}

	public boolean isAvailable()
	{
		return super.isAvailable();
	}

}
