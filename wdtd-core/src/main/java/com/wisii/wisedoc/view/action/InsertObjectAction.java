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
 * @InSertObjectAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.List;

import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

/**
 * 类功能描述：所有插入对象事件类的父类
 * 
 * 作者：zhangqiang 创建日期：2008-11-4
 */
public abstract class InsertObjectAction extends BaseAction
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
		List<CellElement> inserts = creatCells();
		if (inserts != null && !inserts.isEmpty())
		{
			Document doc = getCurrentDocument();
			DocumentPositionRange range = getSelect();
			if (range != null)
			{
				doc.replaceElements(range, inserts);
			} else
			{
				DocumentPosition pos = getCaretPosition();
				if (pos == null)
				{
					List<CellElement> objselects = getObjectSelects();
					if (objselects != null && !objselects.isEmpty())
					{
						CellElement element = objselects
								.get(objselects.size() - 1);
						if (element instanceof AbstractGraphics)
						{
							element = (CellElement) element.getParent();
							if (element != null)
							{
								pos = new DocumentPosition(element, true);
							}
						} else if (element instanceof Inline)
						{
							pos = new DocumentPosition(element, true);
						}
					}

				}
				if (pos != null)
				{
					doc.insertElements(inserts, pos);
				}
			}
		}
	}

	protected abstract List<CellElement> creatCells();

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		DocumentPosition pos = getCaretPosition();
		if (pos != null)
		{
			return true;
		}
		List<CellElement> objselects = getObjectSelects();
		if (objselects != null && !objselects.isEmpty())
		{
			CellElement element = objselects.get(objselects.size() - 1);
			if (element instanceof AbstractGraphics)
			{
				return true;
			} else if (element instanceof Inline)
			{
				return true;
			}
		}
		return false;
	}
}
