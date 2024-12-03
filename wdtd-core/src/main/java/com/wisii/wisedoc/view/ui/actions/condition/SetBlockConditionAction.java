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
 * @SetLogicalExpressionAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.condition;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.dialog.ConditionEditorDialog;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

/**
 * 类功能描述：设置当前选中段落的条件属性事件
 * 
 * 作者：zhangqiang 创建日期：2008-12-3
 */
@SuppressWarnings("serial")
public class SetBlockConditionAction extends BaseAction
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
		List<DocumentPositionRange> ranges = getSelects();
		DocumentPositionRange[] rs = null;
		Document doc = getCurrentDocument();
		DocumentPosition pos = getCaretPosition();
		if (!ranges.isEmpty())
		{
			rs = new DocumentPositionRange[ranges.size()];
			rs = ranges.toArray(rs);
			// 获得当前选择的段落的属性
			Map<Integer, Object> attrs = doc.getParagraphAttributes(rs);
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
		} else
		{
			Map<Integer, Object> attrs = doc.getParagraphAttributes(pos);
			if (attrs != null)
			{
				oldle = (LogicalExpression) attrs.get(Constants.PR_CONDTION);
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
			// 如果有选中范围，则设置选中范围所在段落的条件属性
			if (rs != null)
			{
				doc.setParagraphsAttributes(rs, newatts, false);
			}
			// 否则
			else
			{
				CellElement poselement = pos.getLeafElement();
				// 获得当前光标所在的段落
				while (poselement != null && !(poselement instanceof Block)
						&& !(poselement instanceof Flow))
				{
					poselement = (CellElement) poselement.getParent();
				}
				// 如果不在某个段落内，则返回false，即不能设置段落的条件属性
				if (poselement instanceof Block)
				{
					// 更新当前光标的段落属性
					pos.setBlockAttributes(newatts, false);
					doc.setElementAttributes(poselement, newatts, false);
				}
			}
		}

	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		List<CellElement> objects = getObjectSelects();
		if (objects != null && !objects.isEmpty())
		{
			return false;
		}
		List<DocumentPositionRange> ranges = getSelects();
		DocumentPosition pos = getCaretPosition();
		boolean isinblock = false;
		if (pos != null)
		{
			CellElement poselement = pos.getLeafElement();
			// 判断当前光标是否在某个段落内
			while (poselement != null && !(poselement instanceof Block)
					&& !(poselement instanceof Flow))
			{
				poselement = (CellElement) poselement.getParent();
			}
			// 如果不在某个段落内，则返回false，即不能设置段落的条件属性
			if (poselement instanceof Block)
			{
				isinblock = true;
			}
		}
		// 如果在一个段落内或是当前选中返回不为空
		if (isinblock || (ranges != null && !ranges.isEmpty()))
		{
			Document doc = getCurrentDocument();
			// 如果导入了动态数据结构。则可设置段落的显示条件属性
			if (doc.getDataStructure() != null)
			{
				return true;
			} else
			{
				return false;
			}
		}
		return false;
	}
}
