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

package com.wisii.wisedoc.view.ui.actions.pagelayout;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.ComplexMasterDialog;
import com.wisii.wisedoc.view.ui.StaticContentManeger;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

@SuppressWarnings("serial")
public class SetComplexPageSequenceMasterAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{
		Object master = RibbonUIModel.getCurrentPropertiesByType().get(
				this.actionType).get(Constants.PR_PAGE_SEQUENCE_MASTER);
		if (master == null)
		{
			master = RibbonUIModel.getCurrentPropertiesByType().get(
					this.actionType).get(Constants.PR_SIMPLE_PAGE_MASTER);
		}
		PageSequence currentps = WisedocUtil.getCurrentPageSequence();
		if (currentps != null)
		{
			List<CellElement> children = currentps.getAllChildren();
			Map<String, StaticContent> staticcontentmap = new HashMap<String, StaticContent>();
			List<CellElement> delete = new ArrayList<CellElement>();
			if (children != null)
			{
				for (CellElement current : children)
				{
					if (current instanceof StaticContent)
					{
						StaticContent currentst = (StaticContent) current;
						StaticContent currentstc = (StaticContent) currentst
								.clone();
						staticcontentmap.put(currentstc.getFlowName(),
								currentstc);
						delete.add(currentst);
					}
				}
			}
			ComplexMasterDialog dia = new ComplexMasterDialog(master,
					staticcontentmap);
			if (dia.showDialog() == DialogResult.OK)
			{
				Object newmaster = dia.getPagemaster();
				Document doc = SystemManager.getCurruentDocument();
				doc.deleteElements(delete, currentps);
				if (newmaster instanceof SimplePageMaster)
				{
					setFOProperty(Constants.PR_SIMPLE_PAGE_MASTER, newmaster);
				} else if (newmaster instanceof PageSequenceMaster)
				{
					setFOProperty(Constants.PR_PAGE_SEQUENCE_MASTER, newmaster);
				}
				Map<String, StaticContent> staticcontentmapnew = StaticContentManeger
						.getFinalScmap();
				List<CellElement> staticcontents = new ArrayList<CellElement>();
				Object[] keys = staticcontentmapnew.keySet().toArray();
				if (keys != null && keys.length > 0)
				{
					for (Object key : keys)
					{
						staticcontents.add(staticcontentmapnew.get(key));
					}
				}
				doc.insertElements(staticcontents, currentps, 0);
			}
			StaticContentManeger.clearScmap();
			StaticContentManeger.clearRealstaticcontent();
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType) == null)
		{
			return false;
		}
		if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType)
				.get(Constants.PR_SIMPLE_PAGE_MASTER) != null
				|| RibbonUIModel.getCurrentPropertiesByType().get(
						this.actionType).get(Constants.PR_PAGE_SEQUENCE_MASTER) != null)
		{
			return true;
		}
		return false;
	}
}
