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
 * @SetIsChineseAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.numbertext;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-11-13
 */
public class SetIsChineseAction extends Actions
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
		if (e.getSource() instanceof JCheckBox)
		{
			JCheckBox checkbox = (JCheckBox) e.getSource();
			Map<Integer, Object> propertys = new HashMap<Integer, Object>();
			setFOProperties(propertys);
			if (checkbox.isSelected())
			{
				propertys.put(Constants.PR_NUMBERTEXT_TYPE, new EnumProperty(
						Constants.EN_CHINESELOWERCASE, "chinese2"));
				propertys.put(Constants.PR_NUMBERFORMAT, null);
			} else
			{
				propertys.put(Constants.PR_NUMBERTEXT_TYPE, null);
			}
			setFOProperties(propertys);
		}
	}

}
