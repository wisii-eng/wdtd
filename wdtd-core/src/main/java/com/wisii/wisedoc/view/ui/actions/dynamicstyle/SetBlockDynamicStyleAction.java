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

package com.wisii.wisedoc.view.ui.actions.dynamicstyle;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.DynamicStylesDialog;
import com.wisii.wisedoc.view.dialog.DynamicStylesDialog.DynamicStylesClass;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

@SuppressWarnings("serial")
public class SetBlockDynamicStyleAction extends Actions
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
		Map<Integer, Object> attrs = RibbonUIModel.getCurrentPropertiesByType()
				.get(ActionType.BLOCK);
		DynamicStylesDialog dynamicstyle = new DynamicStylesDialog(attrs,
				DynamicStylesClass.Paragraph);
		DialogResult result = dynamicstyle.showDialog();
		if (DialogResult.OK.equals(result))
		{
			Map<Integer, Object> newatts = dynamicstyle.getAttributes();
			this.actionType = ActionType.BLOCK;
			setFOProperties(newatts);
		}
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		List<CellElement> objects = getObjectSelects();
		if (objects != null && !objects.isEmpty())
		{
			return false;
		}
		List<DocumentPositionRange> ranges = getSelects();
		DocumentPosition pos = getCaretPosition();
		boolean isinblock = false;
		if (pos != null)
		{
			CellElement poselement = pos.getLeafElement();
			// 判断当前光标是否在某个段落内
			while (poselement != null && !(poselement instanceof Block)
					&& !(poselement instanceof Flow))
			{
				poselement = (CellElement) poselement.getParent();
			}
			// 如果不在某个段落内，则返回false，即不能设置段落的条件属性
			if (poselement instanceof Block)
			{
				isinblock = true;
			}
		}
		// 如果在一个段落内或是当前选中返回不为空
		if (isinblock || (ranges != null && !ranges.isEmpty()))
		{
			Document doc = getCurrentDocument();
			// 如果导入了动态数据结构。则可设置段落的显示条件属性
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
