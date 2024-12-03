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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jvnet.flamingo.common.JCommandMenuButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.actions.pagelayout.DefaultSimplePageMasterActions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 页脚内容溢出处理动作
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/18
 */
@SuppressWarnings("serial")
public class RegionAfterOverflowAction extends DefaultSimplePageMasterActions
{

	private List<JCommandMenuButton> uiList;

	private void getComponents()
	{

		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				this.actionID).get(ActionCommandType.RIBBON_ACTION);

		uiList = new ArrayList<JCommandMenuButton>();

		for (List<Object> list : comSet)
		{
			for (Object uiComponents : list)
			{
				if (uiComponents instanceof JCommandMenuButton)
				{
					JCommandMenuButton ui = (JCommandMenuButton) uiComponents;
					uiList.add(ui);
				}
			}
		}
	}

	@Override
	public void doAction(ActionEvent e)
	{

		if (e.getSource() instanceof JCommandMenuButton)
		{
			getComponents();
			setProperty(uiList.indexOf(e.getSource()));
		}

	}

	private void setProperty(int index)
	{
		SimplePageMaster current = null;
		switch (index)
		{
			case 0:
				current = setRegionAfterOverflow(Constants.EN_VISIBLE);
				break;
			case 1:
				current = setRegionAfterOverflow(Constants.EN_HIDDEN);
				break;
		}
		SimplePageMaster old = ViewUiUtil
				.getCurrentSimplePageMaster(this.actionType);
		Object result = ViewUiUtil.getMaster(current, old, this.actionType);
		setFOProperties(result, current);
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);

		if (actionCommand == ActionCommandType.RIBBON_ACTION)
		{
			if (RibbonUIModel.getCurrentPropertiesByType() == null
					|| RibbonUIModel.getCurrentPropertiesByType().get(
							ActionType.LAYOUT) == null)
			{
				return;
			}
			SimplePageMaster value = ViewUiUtil
					.getCurrentSimplePageMaster(this.actionType);
			if (value != null)
			{
				int trValue = value.getRegion(Constants.FO_REGION_AFTER)
						.getOverflow();
				getComponents();
				if (trValue == Constants.EN_VISIBLE)
				{
					uiList.get(0).getActionModel().setSelected(true);
				} else if (trValue == Constants.EN_HIDDEN)
				{
					uiList.get(1).getActionModel().setSelected(true);
				}
			}
		}
	}

}
