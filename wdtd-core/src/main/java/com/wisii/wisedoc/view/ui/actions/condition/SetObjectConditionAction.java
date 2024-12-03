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
 * @SetObjectConditionAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.condition;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.dialog.ConditionEditorDialog;

/**
 * 类功能描述：设置当前选中对象的显示条件属性操作
 * 
 * 作者：zhangqiang 创建日期：2008-12-10
 */
public class SetObjectConditionAction extends BaseAction
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent
	 * )
	 */
	public void doAction(ActionEvent e)
	{
		LogicalExpression oldle = null;
		List<CellElement> elements = getObjectSelects();
		Document doc = getCurrentDocument();
		if (!elements.isEmpty())
		{
			// 获得当前选中对象的属性
			Map<Integer, Object> attrs = DocumentUtil
					.getElementAttributes(elements);
			if (attrs != null)
			{
				// 获得当前条件属性
				Object lecon = attrs.get(Constants.PR_CONDTION);
				// 如果条件属性不为不相等表示对象，则赋值
				if (!Constants.NULLOBJECT.equals(lecon))
				{
					oldle = (LogicalExpression) lecon;
				}
			}
		}
		ConditionEditorDialog condia = new ConditionEditorDialog(oldle);
		DialogResult result = condia.showDialog();
		if (DialogResult.OK.equals(result))
		{
			LogicalExpression newle = condia.getCondition();

			if (doc == null || newle == null)
			{
				return;
			}
			Map<Integer, Object> newatts = new HashMap<Integer, Object>();
			newatts.put(Constants.PR_CONDTION, newle);

			if (elements != null)
			{
				doc.setElementAttributes(elements, newatts, false);
			}

		}

	}

	@SuppressWarnings("unchecked")
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		List<CellElement> objectselects = getObjectSelects();
		if (objectselects != null && !objectselects.isEmpty())
		{
			Document doc = getCurrentDocument();
			if (doc.getDataStructure() != null)
			{
				int size = objectselects.size();
				if (size == 1)
				{
					CellElement only = objectselects.get(0);
					if (only instanceof TableRow)
					{
						Enumeration<CellElement> listobj = only.children();
						if (listobj.hasMoreElements())
						{
							while (listobj.hasMoreElements())
							{
								CellElement obj = listobj.nextElement();
								if (obj
										.getAttribute(Constants.PR_NUMBER_ROWS_SPANNED) != null)
								{
									int rownum = (Integer) obj
											.getAttribute(Constants.PR_NUMBER_ROWS_SPANNED);
									if (rownum > 1)
									{
										return false;
									}
								}
							}
						}

					}
				}
				return true;
			} else
			{
				return false;
			}
		}
		return false;
	}
}
