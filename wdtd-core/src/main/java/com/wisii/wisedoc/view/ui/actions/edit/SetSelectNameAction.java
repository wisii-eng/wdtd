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
 * @SetSelectNameAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.view.component.WiseTextField;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-12-16
 */
public class SetSelectNameAction extends AbstractEditAction
{

	@Override
	protected boolean checkSetting(ActionEvent e)
	{
		WiseTextField nametf = (WiseTextField) e.getSource();
		String text = nametf.getText();
		if (text == null || text.trim().isEmpty())
		{
			return true;
		} else
		{
			String selectname = text.trim();
			//由于自动生成的编辑名字是以###+数字方式的，所以得限制用户输入###+数字的字符作为名称
			if (selectname.matches("###[\\d]+"))
			{
				nametf.requestFocus();
				nametf.selectAll();
				return false;
			}
			List<CellElement> inlines = DocumentUtil.getElementsofDocument(
					getCurrentDocument(), Inline.class);
			// 必须保证下拉列表的名字值是唯一的，已经有相同的名字，则校验不通过
			if (inlines != null && !inlines.isEmpty())
			{
				for (CellElement inline : inlines)
				{
					if (selectname.equals(inline
							.getAttribute(Constants.PR_SELECT_NAME)))
					{
						nametf.requestFocus();
						nametf.selectAll();
						return false;
					}
				}
			}
			return true;
		}
	}

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseTextField)
		{
			WiseTextField nametf = (WiseTextField) e.getSource();
			String text = nametf.getText();
			if (text == null || text.trim().isEmpty())
			{
				setFOProperty(Constants.PR_SELECT_NAME, null);
			} else
			{
				setFOProperty(Constants.PR_SELECT_NAME, text.trim());
			}
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof WiseTextField
				&& hasPropertyKey(Constants.PR_SELECT_NAME))
		{
			WiseTextField textfield = (WiseTextField) uiComponent;
			Object obj = evt.getNewValue();

			if (obj == null)
			{
				textfield.setText("");
			} else
			{
				textfield.setText(obj.toString());
			}
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_SELECT_NAME))
		{
			if (uiComponent instanceof WiseTextField)
			{
				WiseTextField textfield = (WiseTextField) uiComponent;
				textfield.setText("");
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseTextField)
		{
			WiseTextField textfield = (WiseTextField) uiComponent;
			textfield.setText("");
		}

	}
}
