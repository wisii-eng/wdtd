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

package com.wisii.wisedoc.view.ui.actions.checkbox;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.SetGroupUIDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;

@SuppressWarnings("serial")
public class CheckBoxCreatGroupUiAction extends Actions
{

	public void doAction(ActionEvent e)
	{
		SetGroupUIDialog dialog = new SetGroupUIDialog();
		DialogResult result = dialog.showDialog();
		if (result == DialogResult.OK)
		{
			GroupUI groupui = dialog.getGroupui();
			((WiseDocDocument)SystemManager.getCurruentDocument()).addGroupUI(groupui);
			setFOProperty(Constants.PR_GROUP_REFERANCE, groupui);
		}
	}
}
