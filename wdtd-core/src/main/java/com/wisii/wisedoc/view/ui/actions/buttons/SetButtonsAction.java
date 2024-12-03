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

package com.wisii.wisedoc.view.ui.actions.buttons;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.attribute.edit.ButtonGroup;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.dialog.ButtonDialog;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

@SuppressWarnings("serial")
public class SetButtonsAction extends BaseAction
{

	@Override
	public void doAction(ActionEvent e)
	{
		List<CellElement> cellments = getAllSelectObjects();
		if (cellments != null && cellments.size() > 0)
		{
			ButtonDialog condia = new ButtonDialog(cellments.get(0));
			DialogResult result = condia.showDialog();
			if (DialogResult.OK.equals(result))
			{
				List<ButtonGroup> newle = condia.getButtonGroup();
				Document doc = getCurrentDocument();
				if (doc == null || newle == null)
				{
					return;
				}
				Map<Integer, Object> newatts = new HashMap<Integer, Object>();
				newatts.put(Constants.PR_EDIT_BUTTON, newle);
				for (CellElement current : cellments)
				{
					doc.setElementAttributes(current, newatts, false);
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
