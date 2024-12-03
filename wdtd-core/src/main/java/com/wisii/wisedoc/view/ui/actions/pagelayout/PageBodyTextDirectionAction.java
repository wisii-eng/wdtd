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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jvnet.flamingo.common.JCommandMenuButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 版心文字的写作方向动作，即writing-model
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/05
 */
@SuppressWarnings("serial")
public class PageBodyTextDirectionAction extends DefaultSimplePageMasterActions
{

	@Override
	public void doAction(ActionEvent e)
	{
		// Ribbon上的动作
		if (e.getSource() instanceof JCommandMenuButton)
		{

			Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
					.getUIComponents().get(this.getActionID());

			List<Object> temp = new ArrayList<Object>();

			for (List<Object> list : set.get(ActionCommandType.RIBBON_ACTION))
			{
				if (!list.isEmpty()
						&& list.get(0) instanceof JCommandMenuButton)
				{
					temp = list;
				}
			}
			setProperty(temp.indexOf(e.getSource()));
		}
	}

	private void setProperty(int index)
	{
		SimplePageMaster current = null;
		switch (index)
		{
			case 0:
				current = setPageBodyTextDirection(Constants.EN_LR_TB);
				break;
			case 1:
				current = setPageBodyTextDirection(Constants.EN_RL_TB);
				break;
			case 2:
				current = setPageBodyTextDirection(Constants.EN_TB_RL);
				break;
			case 3:
				// FOP中没有bt-rl
				// setFOProperty(propertyType, Constants.PR_WRITING_MODE,
				// Constants.EN_);
				break;
			case 4:
				// 也没有lr-bt
				// setFOProperty(propertyType, Constants.PR_WRITING_MODE,
				// Constants.EN_LR_b);
				break;
			default:
				current = setPageBodyTextDirection(Constants.EN_LR_TB);
				break;
		}

		SimplePageMaster old = ViewUiUtil
				.getCurrentSimplePageMaster(this.actionType);
		Object result = ViewUiUtil.getMaster(current, old, this.actionType);
		setFOProperties(result, current);
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);

		if (actionCommand == ActionCommandType.RIBBON_ACTION)
		{
			Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
					.getUIComponents().get(this.getActionID());
			for (List<Object> list : set.get(ActionCommandType.RIBBON_ACTION))
			{
				if (!list.isEmpty()
						&& list.get(0) instanceof JCommandMenuButton)
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
						int trValue = value.getRegion(Constants.FO_REGION_BODY)
								.getWritingMode();
						if (trValue == Constants.EN_LR_TB)
						{
							((JCommandMenuButton) list.get(0)).getActionModel()
									.setSelected(true);
						} else if (trValue == Constants.EN_RL_TB)
						{
							((JCommandMenuButton) list.get(1)).getActionModel()
									.setSelected(true);
						} else if (trValue == Constants.EN_TB_RL)
						{
							((JCommandMenuButton) list.get(2)).getActionModel()
									.setSelected(true);
						}
					}
				}
			}
		}
	}

}
