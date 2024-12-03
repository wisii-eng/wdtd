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
import java.util.Map;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.DynamicStylesDialog;
import com.wisii.wisedoc.view.dialog.DynamicStylesDialog.DynamicStylesClass;
import com.wisii.wisedoc.view.ui.actions.Actions;

@SuppressWarnings("serial")
public class SetWisedocumentDynamicStyleAction extends Actions
{

	public void doAction(ActionEvent e)
	{
		Document doc = getCurrentDocument();
		Map<Integer, Object> attrs = doc != null && doc.getAttributes() != null ? doc
				.getAttributes().getAttributes()
				: null;
		DynamicStylesDialog dynamicstyle = new DynamicStylesDialog(attrs,
				DynamicStylesClass.Document);
		DialogResult result = dynamicstyle.showDialog();
		if (DialogResult.OK.equals(result))
		{
			Map<Integer, Object> newatts = dynamicstyle.getAttributes();
			((WiseDocDocument)doc).setAttributes(newatts, false,true);
		}
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		if (getCaretPosition() != null)
		{
			Document doc = getCurrentDocument();
			if (doc.getDataStructure() != null)
			{
				return true;
			}
		}
		return false;
	}

}