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
 * @InsertBlockAfterAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.attribute.EnumProperty;

/**
 * 类功能描述：在当前表格，相对定位的块容器，组对象后新加一段落， 这个可以解决在文档开始处添加了表格等对象后不能在其后添加内容的问题
 * 
 * 作者：zhangqiang 创建日期：2009-12-28
 */
public class InsertBlockAfterAction extends BaseAction
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
		if (pos != null)
		{
			CellElement poscelle = pos.getLeafElement();
			Element parent = poscelle;
			//如果当前光标在一个重复组的Inline上
			if (poscelle instanceof Inline
					&& poscelle.getParent() instanceof Group)
			{
				parent = poscelle.getParent();
				Group  group = (Group) parent;
				while(parent != null && !(parent instanceof Flow)&&parent instanceof Group)
				{
					parent = parent.getParent();
				}
				//则在该inline所在组后添加一空段落
				if(parent instanceof Block)
				{
					CellElement toinsertce = (CellElement) parent.getParent();
					if (toinsertce != null)
					{
						Block block = new Block(pos.getBlockAttriute());
						List<CellElement> elements = new ArrayList<CellElement>();
						elements.add(block);
						int index = toinsertce.getIndex(parent);
						getCurrentDocument().insertElements(elements,
								toinsertce, index+1);
					}
				}
			} else
			{
				while (parent != null && !(parent instanceof Flow))
				{
					if ((parent instanceof Group)||(parent instanceof Table)
							|| (parent instanceof BlockContainer && ((EnumProperty) parent
									.getAttribute(Constants.PR_ABSOLUTE_POSITION))
									.getEnum() != Constants.EN_ABSOLUTE))
					{
						break;
					}
					parent = parent.getParent();
				}
				if (parent instanceof Table || parent instanceof BlockContainer|| parent instanceof Group)
				{
					CellElement toinsertce = (CellElement) parent.getParent();
					if (toinsertce != null)
					{
						Block block = new Block(pos.getBlockAttriute());
						List<CellElement> elements = new ArrayList<CellElement>();
						elements.add(block);
						int index = toinsertce.getIndex(parent);
						getCurrentDocument().insertElements(elements,
								toinsertce, index+1);
					}
				}
			}
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
		if (pos == null)
		{
			return false;
		}
		CellElement poscelle = pos.getLeafElement();
		Element parent = poscelle.getParent();
		while (parent != null && !(parent instanceof Flow))
		{
			if ((parent instanceof Table)
					|| (parent instanceof BlockContainer && ((EnumProperty) parent
							.getAttribute(Constants.PR_ABSOLUTE_POSITION))
							.getEnum() == Constants.EN_ABSOLUTE))
			{
				return true;
			}
			parent = parent.getParent();
		}
		return false;
	}
}
