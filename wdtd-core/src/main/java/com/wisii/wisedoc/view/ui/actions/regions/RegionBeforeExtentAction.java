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

package com.wisii.wisedoc.view.ui.actions.regions;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.util.UiUtil;
import com.wisii.wisedoc.view.ui.actions.pagelayout.DefaultSimplePageMasterActions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionBefore;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 页眉高度动作
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/17
 */
@SuppressWarnings("serial")
public class RegionBeforeExtentAction extends DefaultSimplePageMasterActions
{

	FixedLengthSpinner extent;

	private void getRibbonComponents()
	{

		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				RegionBefore.EXTENT_ACTION)
				.get(ActionCommandType.RIBBON_ACTION);

		for (List<Object> list : comSet)
		{
			for (Object uiComponents : list)
			{
				if (uiComponents instanceof FixedLengthSpinner)
				{
					FixedLengthSpinner ui = (FixedLengthSpinner) uiComponents;
					extent = ui;
				}
			}
		}
	}

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner jb = (FixedLengthSpinner) e.getSource();
			SimplePageMaster current = setRegionBeforeExtent(jb.getValue());
			SimplePageMaster old = ViewUiUtil
					.getCurrentSimplePageMaster(this.actionType);
			Object result = ViewUiUtil.getMaster(current, old, this.actionType);
			setFOProperties(result, current);
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_SIMPLE_PAGE_MASTER)
				|| hasPropertyKey(Constants.PR_CURRENT_SIMPLE_PAGE_MASTER))
		{
			Object value = evt.getNewValue();
			if (value instanceof SimplePageMaster)
			{
				SimplePageMaster simValue = (SimplePageMaster) value;
				getRibbonComponents();
				if (extent != null)
				{
					if (UiUtil.hasRegionBefore(simValue))
					{
						FixedLength temp = (FixedLength) ((com.wisii.wisedoc.document.attribute.RegionBefore) simValue
								.getRegion(Constants.FO_REGION_BEFORE))
								.getExtent();
						extent.initValue(temp);
					}
				}
			}
		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);

		if (actionCommand == ActionCommandType.RIBBON_ACTION)
		{
			getRibbonComponents();
			if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType) != null)
			{
				Object value = ViewUiUtil
						.getCurrentSimplePageMaster(this.actionType);
				if (value != null)
				{
					SimplePageMaster simValue = (SimplePageMaster) value;
					if (extent != null)
					{
						if (UiUtil.hasRegionBefore(simValue))
						{
							FixedLength temp = (FixedLength) ((com.wisii.wisedoc.document.attribute.RegionBefore) simValue
									.getRegion(Constants.FO_REGION_BEFORE))
									.getExtent();
							extent.initValue(temp);
						}
					}
				}
			}
		}
	}
}
