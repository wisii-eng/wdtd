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

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import org.jvnet.flamingo.common.JCommandMenuButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 设置页面的横纵向，目前这个和Word中的感觉还不一样，还需要调整一下
 * 
 * @author 闫舒寰
 * @version 0.1 2008/10/16
 */
@SuppressWarnings("serial")
public class PageOrientationAction extends DefaultSimplePageMasterActions
{

	// 纵向按钮
	JRadioButton vertical;

	// 横向按钮
	JRadioButton parallel;

	private void getComponents()
	{

		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				Page.ORIENTATION_ACTION).get(ActionCommandType.DIALOG_ACTION);
		List<JRadioButton> temp = new ArrayList<JRadioButton>();
		for (List<Object> list : comSet)
		{
			for (Object uiComponents : list)
			{
				if (uiComponents instanceof JRadioButton)
				{
					JRadioButton ui = (JRadioButton) uiComponents;
					temp.add(ui);
				}
			}
		}
		this.vertical = temp.get(0);
		this.parallel = temp.get(1);
	}

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JComboBox)
		{
			getComponents();
			SimplePageMaster current = null;
			if (vertical.isSelected())
			{
				current = setReferenceOrientation(0);
			} else if (parallel.isSelected())
			{
				current = setReferenceOrientation(90);
			}
			SimplePageMaster old = ViewUiUtil
					.getCurrentSimplePageMaster(this.actionType);
			Object result = ViewUiUtil.getMaster(current, old, this.actionType);
			setFOProperties(result, current);
		}

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
			int choose = temp.indexOf(e.getSource());
			SimplePageMaster current = null;
			if (choose == 0)
			{
				current = setReferenceOrientation(0);
			} else if (choose == 1)
			{
				current = setReferenceOrientation(90);
			}
			SimplePageMaster old = ViewUiUtil
					.getCurrentSimplePageMaster(this.actionType);
			Object result = ViewUiUtil.getMaster(current, old, this.actionType);
			setFOProperties(result, current);
		}
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
							if (value.getReferenceOrientation() == 0)
							{
								((JCommandMenuButton) list.get(0))
										.getActionModel().setSelected(true);
							} else
							{
								((JCommandMenuButton) list.get(1))
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
			if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType) != null)
			{
				Object value = RibbonUIModel.getCurrentPropertiesByType().get(
						this.actionType).get(Constants.PR_SIMPLE_PAGE_MASTER);
				if (value instanceof SimplePageMaster)
				{
					SimplePageMaster simValue = (SimplePageMaster) value;
					if (simValue.getReferenceOrientation() == 0)
					{
						vertical.setSelected(true);
					} else
					{
						parallel.setSelected(true);
					}
				}
			}
		}
	}
}
