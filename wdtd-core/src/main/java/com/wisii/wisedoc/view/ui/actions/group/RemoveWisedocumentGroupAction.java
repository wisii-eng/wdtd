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
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.view.action.BaseAction;

@SuppressWarnings("serial")
public class RemoveWisedocumentGroupAction extends BaseAction
{

	public void doAction(ActionEvent e)
	{
		Document doc = getCurrentDocument();
		if (doc != null)
		{
			Map<Integer, Object> attrs = new HashMap<Integer, Object>();
			attrs.put(Constants.PR_GROUP, null);
			doc.setAttributes(attrs, false);
		}
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		Document doc = getCurrentDocument();
		if (doc != null)
		{
			Map<Integer, Object> att = doc.getAttributes() == null ? null : doc
					.getAttributes().getAttributes();
			if (att != null)
			{
				return att.get(Constants.PR_GROUP) != null;
			}
		}
		return false;
	}
}
