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
 * @GroupAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
/**
 * 类功能描述：组合成一个重复组操作
 *
 * 作者：zhangqiang
 * 创建日期：2009-3-11
 */
public class GroupAction extends BaseAction
{
	public void doAction(ActionEvent e)
	{
		DocumentPositionRange range = getSelect();
		List<CellElement> rangeelements = DocumentUtil.getElements(range);
		//		
		CellElement comancestor = (CellElement) rangeelements.get(0)
				.getParent();
		int size = rangeelements.size();
		// 找到所有元素共同的祖先对象
		l1:for (int i = 1; i < size; i++)
		{
			CellElement rangeelement = rangeelements.get(i);

			CellElement parent1 = comancestor;
			while (parent1 != null && !(parent1 instanceof PageSequence))
			{
				CellElement rangeelementparent = (CellElement) rangeelement
						.getParent();
				while (rangeelementparent != null
						&& !(rangeelementparent instanceof PageSequence))
				{
//					如果找到两者的共同父对象，则进入下一个循环步
					if (rangeelementparent == parent1)
					{
						comancestor = parent1;
						continue l1;
					}
					rangeelementparent = (CellElement) rangeelementparent
							.getParent();
				}
				parent1 = (CellElement) parent1.getParent();
			}
		}
		CellElement startelement = rangeelements.get(0);
		CellElement endelement = rangeelements.get(rangeelements.size() - 1);
		int indexstart = comancestor.getIndex(startelement);
		while (indexstart < 0)
		{
			startelement = (CellElement) startelement.getParent();
			indexstart = comancestor.getIndex(startelement);
		}
		int indexend = comancestor.getIndex(endelement);
		
		while (indexend < 0)
		{
			
			endelement = (CellElement) endelement.getParent();
			indexend = comancestor.getIndex(endelement);
		}
		List<CellElement> togroupelements = comancestor.getChildren(indexstart,
				indexend + 1);
		Document doc = getCurrentDocument();
		doc.deleteElements(togroupelements);
		Group group = new Group();
		group.insert(togroupelements, 0);
		List<CellElement> toinserts = new ArrayList<CellElement>();
		toinserts.add(group);
		doc.insertElements(toinserts, comancestor, indexstart);

	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		List<DocumentPositionRange> ranges = getSelects();
		if (ranges == null || ranges.size() != 1)
		{
			return false;
		}
		DocumentPositionRange range = ranges.get(0);
		List<CellElement> rangeelements = DocumentUtil.getElements(range);
		// 如果选中元素少于2个，则不可以合并为一个组
		if (rangeelements == null || rangeelements.size() < 2)
		{
			return false;
		}
		CellElement startposelement = rangeelements.get(0);
		CellElement endposelement = rangeelements.get(rangeelements.size() - 1);
		Group startgroup = DocumentUtil.getFirstGroupofCell(startposelement);
		Group endgroup = DocumentUtil.getFirstGroupofCell(endposelement);
		// 必须保证开始位置和结束位置完全包含于一个相当group或完全不包含于Group
		if (startgroup == null)
		{
			if (endgroup != null)
			{
				return false;
			}
			return true;
		} else
		{
			if (endgroup == null)
			{
				return false;
			} else
			{
				// 如果选中元素的起始元素和结束元素在同一重复组内
				if (endgroup == startgroup)
				{
					CellElement groupstartelement = startgroup.getChildAt(0);
					// 边界元素是Group元素的起始元素么
					boolean isstartelement = false;
					while (startposelement != startgroup)
					{
						if (groupstartelement == startposelement)
						{
							isstartelement = true;
						}
						startposelement = (CellElement) startposelement
								.getParent();
					}
					CellElement groupendelement = startgroup
							.getChildAt(startgroup.getChildCount() - 1);
					// 边界元素是Group元素的结束元素么
					boolean isendelement = false;
					while (endposelement != startgroup)
					{
						if (groupendelement == endposelement)
						{
							isendelement = true;
						}
						endposelement = (CellElement) endposelement.getParent();
					}
					// 如果选中的起始元素不是找到组的边界元素，则可组合，即组合必须完全的包含于一个相同的组了
					if (!isstartelement || !isendelement)
					{
						return true;
					}
					return false;
				}
				return false;
			}
		}
	}

}
