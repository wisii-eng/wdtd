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
 * @DecimalSeparatorSetAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.numbertext;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.NumberFormat;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：小数点符号设置Action类
 * 
 * 作者：zhangqiang 创建日期：2008-11-27
 */
public class BaifenbiSetAction extends Actions
{
	private NumberFormat oldformat;

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCheckBox)
		{
			JCheckBox value = (JCheckBox) e.getSource();
			boolean hasthans = value.isSelected();
			NumberFormat nf = getNumberformat(hasthans);
			if (nf != null)
			{
				Map<Integer, Object> propertys = new HashMap<Integer, Object>();
				propertys.put(Constants.PR_NUMBERTEXT_TYPE, null);
				propertys.put(Constants.PR_NUMBERFORMAT, nf);
				setFOProperties(propertys);
			}
		}
	}

	private NumberFormat getNumberformat(boolean baifenbi)
	{

		NumberFormat nf = null;
		if (oldformat != null)
		{
			nf = new NumberFormat(oldformat.getDecimalDigits(), oldformat.isHasthousseparator(),
					baifenbi);
		} else
		{
			nf = new NumberFormat(2, oldformat.isHasthousseparator(), baifenbi);
		}
		return nf;
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_NUMBERFORMAT))
		{
			Object obj = evt.getNewValue();
			if (uiComponent instanceof JCheckBox)
			{
				JCheckBox ui = (JCheckBox) uiComponent;
				if (obj instanceof NumberFormat)
				{
					oldformat = (NumberFormat) obj;
					ui.setSelected(oldformat.isIsbaifenbi());
				} else
				{
					oldformat = new NumberFormat(2, true, false);
					ui.setSelected(oldformat.isIsbaifenbi());
				}

			}

		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		oldformat = new NumberFormat(2, true, false);
		if (uiComponent instanceof JCheckBox)
		{
			JCheckBox ui = (JCheckBox) uiComponent;
			ui.setSelected(false);
		}

	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_NUMBERFORMAT))
		{
			if (uiComponent instanceof WiseTextField)
			{
				WiseTextField ui = (WiseTextField) uiComponent;
				ui.setText("");
			}
		}
	}
}
