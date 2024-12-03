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
 * @ReomveBlockConditionAction.java
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
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-12-9
 */
public class RemoveBlockConditionAction extends BaseAction
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
		List<DocumentPositionRange> ranges = getSelects();
		Document doc = getCurrentDocument();
		if (doc != null)
		{
			Map<Integer, Object> attrs = new HashMap<Integer, Object>();
			attrs.put(Constants.PR_CONDTION, null);
			if (ranges != null && !ranges.isEmpty())
			{
				DocumentPositionRange[] rs = new DocumentPositionRange[ranges
						.size()];
				rs = ranges.toArray(rs);
				doc.setParagraphsAttributes(rs, attrs, false);
			}
			// 否则
			else
			{
				DocumentPosition pos = getCaretPosition();
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

					doc.setElementAttributes(poselement,
							new HashMap<Integer, Object>(attrs), false);
					pos.setBlockAttributes(attrs, false);
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
		List<DocumentPositionRange> ranges = getSelects();
		DocumentPositionRange[] rs = null;
		Document doc = getCurrentDocument();
		DocumentPosition pos = getCaretPosition();
		LogicalExpression oldle = null;
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
				oldle = (LogicalExpression) lecon;
			}
		} else
		{
			if (pos != null)
			{
				Map<Integer, Object> attrs = doc.getParagraphAttributes(pos);
				if (attrs != null)
				{
					oldle = (LogicalExpression) attrs
							.get(Constants.PR_CONDTION);
				}
			}
		}
		if (oldle != null)
		{
			return true;
		} else
		{
			return false;
		}
	}
}
