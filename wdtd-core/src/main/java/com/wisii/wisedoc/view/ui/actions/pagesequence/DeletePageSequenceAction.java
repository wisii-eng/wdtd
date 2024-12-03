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
 * @DeletePageSequenceAction.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.pagesequence;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2011-1-20
 */
public class DeletePageSequenceAction extends Actions
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.ui.actions.Actions#doAction(java.awt.event.ActionEvent)
	 */
	@Override
	public void doAction(ActionEvent e)
	{
		DocumentPosition pos = getCaretPosition();
		if(pos!=null&&getCurrentDocument().getChildCount()>1)
		{
			CellElement cell = pos.getLeafElement();
			while(cell!=null&&!(cell instanceof PageSequence))
			{
				cell = (CellElement) cell.getParent();
			}
			if(cell instanceof PageSequence)
			{
				getCurrentDocument().deletePageSequence((PageSequence) cell);
			}
		}

	}
	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.ui.actions.Actions#isAvailable()
	 */
	@Override
	public boolean isAvailable()
	{
		if(!super.isAvailable())
		{
			return false;
		}
		return getCurrentDocument().getChildCount()>1&&getCaretPosition()!=null;
	}

}
