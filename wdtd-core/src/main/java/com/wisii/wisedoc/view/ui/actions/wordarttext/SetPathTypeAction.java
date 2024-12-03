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
 * @SetPathTypeAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.wordarttext;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jvnet.flamingo.common.JCommandToggleWisiiButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;

/**
 * 类功能描述：设置文本绘制路径类型
 * 
 * 作者：zhangqiang 创建日期：2009-9-15
 */
public class SetPathTypeAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCommandToggleWisiiButton)
		{

			Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
					.getUIComponents().get(this.getActionID());

			Iterator<List<Object>> iterator = set.get(
					ActionCommandType.RIBBON_ACTION).iterator();
			List<Object> list = (List<Object>) iterator.next();
			setProperty(list.indexOf(e.getSource()));
		}
	}

	private void setProperty(int index)
	{
		switch (index)
		{
		case 0:
			// letter
			setFOProperty(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
					Constants.EN_WORDARTTEXT_TYPE_LINE, ""));
			break;
		case 1:
			// Tabloid
			setFOProperty(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
					Constants.EN_WORDARTTEXT_TYPE_ZHEXIAN2, ""));
			break;
		case 2:
			// Ledger
			setFOProperty(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
					Constants.EN_WORDARTTEXT_TYPE_ZHEXIAN3, ""));
			break;
		case 3:
			// Tabloid
			setFOProperty(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
					Constants.EN_WORDARTTEXT_TYPE_ELLIPSEUP, ""));
			break;
		case 4:
			// Ledger
			setFOProperty(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
					Constants.EN_WORDARTTEXT_TYPE_ELLIPSEDOWN, ""));
			break;
		case 5:
			// Tabloid
			setFOProperty(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
					Constants.EN_WORDARTTEXT_TYPE_ELLIPSE, ""));
			break;
		case 6:
			// Ledger
			setFOProperty(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
					Constants.EN_WORDARTTEXT_TYPE_ELLIPSEINNER, ""));
			break;
		case 7:
			// Tabloid
			setFOProperty(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
					Constants.EN_WORDARTTEXT_TYPE_BEZIERCURVES2, ""));
			break;
		case 8:
			// Ledger
			setFOProperty(Constants.PR_WORDARTTEXT_TYPE, new EnumProperty(
					Constants.EN_WORDARTTEXT_TYPE_BEZIERCURVES3, ""));
			break;
		default:
			break;
		}
	}
}
