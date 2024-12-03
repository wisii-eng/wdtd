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
 * @UnGroupAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
/**
 * 类功能描述：将重复组合对象撤销为非重复的组合对象
 * 
 * 作者：zhangqiang 创建日期：2009-3-11
 */
public class UnGroupAction extends BaseAction
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent
	 * )
	 */
	@Override
	public void doAction(ActionEvent e)
	{
		DocumentPosition pos = getCaretPosition();
		CellElement element = null;
		if (pos != null)
		{
			element = pos.getLeafElement();
		}
//		如果不是光标选中状态，则去第一个选中对象为所要拆分组所包含对象
		else
		{
			element = getAllSelectObjects().get(0);
		}
		Group group = null;
		// 选中元素最近的祖先类型为Group的对象
		while (element != null && !(element instanceof Flow))
		{
			if (element instanceof Group)
			{
				group = (Group) element;
				break;
			}
			element = (CellElement) element.getParent();
		}
		if (group != null)
		{
			Document doc = getCurrentDocument();
			List<CellElement> toaddelements = group.getAllChildren();
			List<CellElement> todelets = new ArrayList<CellElement>();
			todelets.add(group);
			CellElement toaddparent = (CellElement) group.getParent();
			int index = toaddparent.getIndex(group);
			doc.deleteElements(todelets, toaddparent);
			doc.insertElements(toaddelements, toaddparent, index);
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		DocumentPosition pos = getCaretPosition();
		List<DocumentPositionRange> ranges = getSelects();
		if (pos != null && (ranges == null || ranges.isEmpty()))
		{
			CellElement poselement = pos.getLeafElement();
			while (poselement != null && !(poselement instanceof Flow))
			{
				if (poselement instanceof Group)
				{
					return true;
				}
				poselement = (CellElement) poselement.getParent();
			}
		}
		if (ranges == null || ranges.size() != 1)
		{
			return false;
		}
		List<CellElement> rangeelements = DocumentUtil.getElements(ranges);
		if (rangeelements.size() <= 0)
		{
			return false;
		}
		CellElement rangeelement = rangeelements.get(0);
		// 只有选择第一个元素在一个组对象下，才可撤销组
		while (rangeelement != null && !(rangeelement instanceof Flow))
		{
			if (rangeelement instanceof Group)
			{
				return true;
			}
			rangeelement = (CellElement) rangeelement.getParent();
		}
		return false;
	}

}
