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

package com.wisii.wisedoc.view.ui.actions.blockcontainer;

import java.awt.event.ActionEvent;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.Fixedarea;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.ContentTreatDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

@SuppressWarnings("serial")
public class BlockContainerContentTreatAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{
		Fixedarea area = null;
		Map<Integer, Object> map = RibbonUIModel
				.getReadCompletePropertiesByType().get(
						ActionType.BLOCK_CONTAINER);
		if (map != null)
		{
			Object value = map.get(Constants.PR_CONTENT_TREAT);
			if (value != null || value != Constants.NULLOBJECT)
			{
				if (value instanceof Fixedarea)
				{
					area = (Fixedarea) value;
				}
			}
		}
		ContentTreatDialog dialog = new ContentTreatDialog(area);
		DialogResult result = dialog.showDialog();
		if (result == DialogResult.OK)
		{
			Fixedarea newarea = dialog.getFixedarea();
			setFOProperty(Constants.PR_CONTENT_TREAT, newarea);
		}
	}

	@Override
	public boolean isAvailable()
	{
		return super.isAvailable();
	}
}
