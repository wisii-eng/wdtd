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

package com.wisii.wisedoc.view.ui.actions.datatreat;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

@SuppressWarnings("serial")
public class RemoveDatatreatTransformTableAction extends BaseAction
{

	@Override
	public void doAction(ActionEvent e)
	{
		List<DocumentPositionRange> ranges = getSelects();
		Document doc = getCurrentDocument();
		if (doc != null)
		{
			Map<Integer, Object> attrs = new HashMap<Integer, Object>();
			attrs.put(Constants.PR_TRANSLATEURL, null);
			DocumentPositionRange[] rangearrats = new DocumentPositionRange[ranges
					.size()];
			rangearrats = ranges.toArray(rangearrats);
			doc.setInlineAttributes(rangearrats, attrs, false);
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
			DocumentPositionRange[] rangearrats = new DocumentPositionRange[ranges
					.size()];
			rangearrats = ranges.toArray(rangearrats);
			Map<Integer, Object> att = doc.getInlineAttributes(rangearrats);
			if (att != null)
			{
				return att.get(Constants.PR_TRANSLATEURL) != null;
			}
		}
		return false;
	}
}
