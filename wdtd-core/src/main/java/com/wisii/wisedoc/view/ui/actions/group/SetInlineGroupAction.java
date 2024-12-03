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

package com.wisii.wisedoc.view.ui.actions.group;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.dialog.SetGroupDialog;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

@SuppressWarnings("serial")
public class SetInlineGroupAction extends BaseAction
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
		Group oldle = null;
		List<DocumentPositionRange> ranges = getSelects();
		DocumentPositionRange[] rs = null;
		Document doc = getCurrentDocument();
		if (!ranges.isEmpty())
		{
			rs = new DocumentPositionRange[ranges.size()];
			rs = ranges.toArray(rs);
			// 获得当前选择的文本的属性
			Map<Integer, Object> attrs = doc.getInlineAttributes(rs);
			if (attrs != null)
			{
				// 获得当前条件属性
				Object lecon = attrs.get(Constants.PR_GROUP);
				// 如果条件属性不为不相等表示对象，则赋值
				if (!Constants.NULLOBJECT.equals(lecon))
				{
					oldle = (Group) lecon;
				}
			}
		}
		SetGroupDialog condia = new SetGroupDialog(oldle);
		DialogResult result = condia.showDialog();
		if (DialogResult.OK.equals(result))
		{
			Group newle = condia.getGroup();
			if (doc == null || newle == null)
			{
				return;
			}
			Map<Integer, Object> newatts = new HashMap<Integer, Object>();
			newatts.put(Constants.PR_GROUP, newle);
			// 如果有选中范围，则设置选中范围所在文本的条件属性
			if (rs != null)
			{
				doc.setInlineAttributes(rs, newatts, false);
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
		if (ranges != null && !ranges.isEmpty())
		{
			Document doc = getCurrentDocument();
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