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

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.view.action.BaseAction;

@SuppressWarnings("serial")
public class RemoveCurrentGroupGroupAction extends BaseAction
{

	public void doAction(ActionEvent e)
	{
		CellElement elements = getCaretPosition().getLeafElement();
		while (!(elements instanceof com.wisii.wisedoc.document.Group)
				&& !(elements instanceof PageSequence))
		{
			elements = (CellElement) elements.getParent();
		}
		Document doc = getCurrentDocument();
		if (doc != null)
		{
			Map<Integer, Object> attrs = new HashMap<Integer, Object>();
			attrs.put(Constants.PR_GROUP, null);
			doc.setElementAttributes(elements, attrs, false);
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
				CellElement elements = getCaretPosition().getLeafElement();
				while (elements != null
						&& !(elements instanceof com.wisii.wisedoc.document.Group)
						&& !(elements instanceof WiseDocDocument))
				{
					if (elements.getParent() instanceof WiseDocDocument)
					{
						return false;
					} else
					{
						elements = (CellElement) elements.getParent();
					}
				}
				if (elements instanceof com.wisii.wisedoc.document.Group)
				{
					Map<Integer, Object> att = elements.getAttributes() == null ? null
							: elements.getAttributes().getAttributes();
					if (att != null)
					{
						return att.get(Constants.PR_GROUP) != null;
					}
				}
			}
		}
		return false;
	}
}
