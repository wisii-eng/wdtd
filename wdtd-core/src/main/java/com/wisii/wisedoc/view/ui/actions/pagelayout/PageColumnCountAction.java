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

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jvnet.flamingo.common.JCommandMenuButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 设置分栏
 * 
 * @author 闫舒寰
 * @version 0.1 2008/10/24
 */
@SuppressWarnings("serial")
public class PageColumnCountAction extends DefaultSimplePageMasterActions
		implements ChangeListener
{

	JSpinner count;

	private void getComponents()
	{

		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				Page.COLUMN_COUNT_ACTION).get(ActionCommandType.DIALOG_ACTION);

		for (List<Object> list : comSet)
		{
			for (Object uiComponents : list)
			{
				if (uiComponents instanceof JSpinner)
				{
					JSpinner ui = (JSpinner) uiComponents;
					this.count = ui;
				}
			}
		}
	}

	public void doAction(ActionEvent e)
	{

		// Ribbon界面动作
		Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
				.getUIComponents().get(this.getActionID());

		List<Object> temp = new ArrayList<Object>();

		for (Iterator<List<Object>> iterator = set.get(
				ActionCommandType.RIBBON_ACTION).iterator(); iterator.hasNext();)
		{
			List<Object> list = (List<Object>) iterator.next();
			if (!list.isEmpty() && list.get(0) instanceof JCommandMenuButton)
			{
				temp = list;
			}
		}

		if (e.getSource() instanceof JCommandMenuButton)
		{
			SimplePageMaster current = null;
			switch (temp.indexOf(e.getSource()))
			{
				case 0:
					current = setColumnCount(1);
					break;
				case 1:
					current = setColumnCount(2);
					break;
				case 2:
					current = setColumnCount(3);
					break;

				default:
					break;
			}
			SimplePageMaster old = ViewUiUtil
					.getCurrentSimplePageMaster(this.actionType);
			Object result = ViewUiUtil.getMaster(current, old, this.actionType);
			setFOProperties(result, current);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if (e.getSource() instanceof JSpinner)
		{
			JSpinner ui = (JSpinner) e.getSource();
			SimplePageMaster current = setColumnCount((Integer) ui.getValue());
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
						// 用户如果没有点击界面则这个属性为空，则没有默认值
						SimplePageMaster value = ViewUiUtil
								.getCurrentSimplePageMaster(this.actionType);
						if (value != null)
						{
							int temp = ((RegionBody) value
									.getRegion(Constants.FO_REGION_BODY))
									.getColumnCount();
							if (temp == 1)
							{
								((JCommandMenuButton) list.get(0))
										.getActionModel().setSelected(true);
							} else if (temp == 2)
							{
								((JCommandMenuButton) list.get(1))
										.getActionModel().setSelected(true);
							} else if (temp == 3)
							{
								((JCommandMenuButton) list.get(2))
										.getActionModel().setSelected(true);
							}
						}
					}
				}
			}
		}

		if (actionCommand == ActionCommandType.DIALOG_ACTION)
		{
			getComponents();
			if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType) == null)
			{
				return;
			}
			Object value = ViewUiUtil
					.getCurrentSimplePageMaster(this.actionType);
			if (value != null)
			{
				SimplePageMaster simValue = (SimplePageMaster) value;
				int temp = ((RegionBody) simValue
						.getRegion(Constants.FO_REGION_BODY)).getColumnCount();
				this.count.setValue(temp);
			}
		}
	}
}
