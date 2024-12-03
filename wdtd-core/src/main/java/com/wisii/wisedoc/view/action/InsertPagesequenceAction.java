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
package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

public class InsertPagesequenceAction extends BaseAction
{

	public void doAction(ActionEvent e)
	{
		SystemManager.getCurruentDocument().insertPageSequence(
				getCaretPosition());
	}

	public boolean isAvailable()
	{
		if(!super.isAvailable())
		{
			return false;
		}
		DocumentPositionRange range = getSelect();
		if(range!=null)
		{
			return false;
		}
		DocumentPosition pos = getCaretPosition();
		if (pos != null)
		{
			CellElement element = pos.getLeafElement();
			if (element instanceof Inline
					&& element.getParent() instanceof Group)
			{
				return false;
			}
			while (element != null && !(element instanceof Flow))
			{
				element = (CellElement) element.getParent();
			}
			if (element instanceof Flow)
			{
				// 光标如果在页眉，页脚区域，则插入章节菜单不可用
				if (element instanceof StaticContent)
				{
					return false;
				} else
				{
					return true;
				}
			} else
			{
				return false;
			}
		}
		return false;
	}
}