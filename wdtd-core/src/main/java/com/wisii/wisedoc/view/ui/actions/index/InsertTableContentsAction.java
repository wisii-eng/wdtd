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
 * @InsertTableContentsAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.index;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.view.action.BaseAction;

/**
 * 类功能描述：插入目录事件类
 *
 * 作者：zhangqiang
 * 创建日期：2009-4-13
 */
public class InsertTableContentsAction extends BaseAction
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent)
	 */
	@Override
	public void doAction(ActionEvent e)
	{
		Document doc = getCurrentDocument();
		DocumentPosition pos = getCaretPosition();
		List<CellElement> insertelements = new ArrayList<CellElement>();
		insertelements.add(new TableContents(false));
		doc.insertElements(insertelements, pos);
	}
    @Override
    public boolean isAvailable()
    {
		if (!super.isAvailable())
		{
			return false;
		}
		return getCaretPosition() != null;
	}
}
