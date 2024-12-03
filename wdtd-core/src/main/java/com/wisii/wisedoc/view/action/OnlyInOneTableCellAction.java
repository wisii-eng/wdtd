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
 * @OnlyInOneTableCellAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;


import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.TableCell;

/**
 * 类功能描述：只有一个单元格或其内的对象处于选择状态时可用的操作事件类
 *
 * 作者：zhangqiang
 * 创建日期：2008-11-12
 */
public abstract class OnlyInOneTableCellAction extends TableOperateAction
{
 protected TableCell tablecell;
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}

		List<CellElement> cells = getAllSelectObjects();
		DocumentPosition pos = getCaretPosition();
		if ((cells == null||cells.isEmpty())&&pos != null)
		{
			CellElement poselement = pos.getLeafElement();
			if(cells == null)
			{
				cells = new ArrayList<CellElement>();
			}
			if (!cells.contains(poselement))
			{
				cells.add(poselement);
			}
		}
		if (cells == null || cells.isEmpty())
		{
			return false;
		}
		int size = cells.size();
		TableCell oldtablecell = null;
		for (int i = 0; i < size; i++)
		{
			CellElement cell = cells.get(i);
			// 找到该对象的最近的是表格的对象
			while (cell != null && !(cell instanceof TableCell)
					&& !(cell instanceof Flow))
			{
				cell = (CellElement) cell.getParent();
			}
			if (cell instanceof TableCell)
			{
				if (oldtablecell == null)
				{
					oldtablecell = (TableCell) cell;
				} else
				{
					if (oldtablecell != cell)
					{
						return false;
					}
				}

			}
			// 如果父对象不是表格，则返回假
			else
			{
				return false;
			}
		}
		tablecell = oldtablecell;
		return true;
	}


}
