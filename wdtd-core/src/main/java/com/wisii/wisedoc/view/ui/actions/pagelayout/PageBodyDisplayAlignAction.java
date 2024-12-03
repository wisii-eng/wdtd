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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jvnet.flamingo.common.JCommandMenuButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 版心区域内容对齐方式
 * 
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
@SuppressWarnings("serial")
public class PageBodyDisplayAlignAction extends DefaultSimplePageMasterActions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCommandMenuButton)
		{
			Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
					.getUIComponents().get(this.getActionID());

			List<Object> temp = new ArrayList<Object>();

			for (Iterator<List<Object>> iterator = set.get(
					ActionCommandType.RIBBON_ACTION).iterator(); iterator
					.hasNext();)
			{
				List<Object> list = (List<Object>) iterator.next();
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
				// letter
				current = setDisplayAlign(Constants.EN_BEFORE);
				break;
			case 1:
				// Tabloid
				current = setDisplayAlign(Constants.EN_CENTER);
				break;
			case 2:
				// Ledger
				current = setDisplayAlign(Constants.EN_AFTER);
				break;

			default:
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

			for (Iterator<List<Object>> iterator = set.get(
					ActionCommandType.RIBBON_ACTION).iterator(); iterator
					.hasNext();)
			{
				List<Object> list = (List<Object>) iterator.next();
				if (!list.isEmpty()
						&& list.get(0) instanceof JCommandMenuButton)
				{
					if (RibbonUIModel.getCurrentPropertiesByType().get(
							this.actionType) != null)
					{
						SimplePageMaster value = ViewUiUtil
								.getCurrentSimplePageMaster(this.actionType);
						if (value != null)
						{
							int trValue = value.getRegion(
									Constants.FO_REGION_BODY).getDisplayAlign();
							if (trValue == Constants.EN_BEFORE)
							{
								((JCommandMenuButton) list.get(0))
										.getActionModel().setSelected(true);
							} else if (trValue == Constants.EN_CENTER)
							{
								((JCommandMenuButton) list.get(1))
										.getActionModel().setSelected(true);
							} else if (trValue == Constants.EN_AFTER)
							{
								((JCommandMenuButton) list.get(2))
										.getActionModel().setSelected(true);
							}
						}
					}
				}
			}
		}
	}

}
