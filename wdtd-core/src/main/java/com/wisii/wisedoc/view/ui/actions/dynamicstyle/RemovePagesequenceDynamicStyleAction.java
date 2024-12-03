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
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

@SuppressWarnings("serial")
public class RemovePagesequenceDynamicStyleAction extends BaseAction
{

	public void doAction(ActionEvent e)
	{
		CellElement elements = getCaretPosition().getLeafElement();
		while (!(elements instanceof PageSequence))
		{
			elements = (CellElement) elements.getParent();
		}
		Document doc = getCurrentDocument();
		if (doc != null)
		{
			Map<Integer, Object> attrs = new HashMap<Integer, Object>();
			attrs.put(Constants.PR_DYNAMIC_STYLE, null);
			doc.setElementAttributes(elements, attrs, false);
			RibbonUIModel.getCurrentPropertiesByType().get(ActionType.LAYOUT).remove(Constants.PR_DYNAMIC_STYLE);
		}
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		CellElement elements = getCaretPosition() == null ? null
				: getCaretPosition().getLeafElement();
		if (elements != null)
		{
			while (!(elements instanceof PageSequence))
			{
				Element gp = elements.getParent();
				if (gp != null)
				{
					elements = (CellElement) gp;
				} else
				{
					break;
				}
			}
			Map<Integer, Object> att = elements.getAttributes() == null ? null
					: elements.getAttributes().getAttributes();
			if (att != null)
			{
				return att.get(Constants.PR_DYNAMIC_STYLE) != null;
			}
		}
		return false;
	}
}
