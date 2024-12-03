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

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

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
 * 文字方向动作类
 * 
 * @author 闫舒寰
 * @version 0.1 2008/10/15
 */
@SuppressWarnings("serial")
public class PageTextDirectionAction extends DefaultSimplePageMasterActions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JComboBox)
		{
			JComboBox textDirection = (JComboBox) e.getSource();
			SimplePageMaster current = null;
			switch (textDirection.getSelectedIndex())
			{
				case 0:
					current = setPageTextDirection(Constants.EN_LR_TB);
					break;
				case 1:
					current = setPageTextDirection(Constants.EN_RL_TB);
					break;
				case 2:
					current = setPageTextDirection(Constants.EN_TB_RL);
					break;
			}
			SimplePageMaster old = ViewUiUtil
					.getCurrentSimplePageMaster(this.actionType);
			Object result = ViewUiUtil.getMaster(current, old, this.actionType);
			setFOProperties(result, current);
		}

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
				current = setPageTextDirection(Constants.EN_LR_TB);
				break;
			case 1:
				current = setPageTextDirection(Constants.EN_RL_TB);
				break;
			case 2:
				current = setPageTextDirection(Constants.EN_TB_RL);
				break;
			default:
				current = setPageTextDirection(Constants.EN_LR_TB);
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
						int trValue = value.getWritingMode();
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

		if (actionCommand == ActionCommandType.DIALOG_ACTION)
		{
			Set<List<Object>> tempSet = RibbonUIManager.getUIComponents().get(
					this.actionID).get(actionCommand);
			for (List<Object> list : tempSet)
			{
				for (Object object : list)
				{
					if (object instanceof JComboBox)
					{
						JComboBox ui = (JComboBox) object;
						if (RibbonUIModel.getCurrentPropertiesByType().get(
								this.actionType) == null)
						{
							return;
						}
						Object value = RibbonUIModel
								.getCurrentPropertiesByType().get(
										this.actionType).get(
										Constants.PR_SIMPLE_PAGE_MASTER);
						if (value instanceof SimplePageMaster)
						{
							SimplePageMaster newValue = (SimplePageMaster) value;
							int trValue = newValue.getWritingMode();

							if (value.equals(Constants.NULLOBJECT))
							{
								JTextComponent editor = (JTextComponent) ui
										.getEditor().getEditorComponent();
								editor.setText("");
							} else
							{
								if (trValue == Constants.EN_LR_TB)
								{
									ui.setSelectedIndex(0);
								} else if (trValue == Constants.EN_RL_TB)
								{
									ui.setSelectedIndex(1);
								} else if (trValue == Constants.EN_TB_RL)
								{
									ui.setSelectedIndex(2);
								} else
								{
									ui.setSelectedIndex(3);
								}
							}
						}
					}
				}
			}
		}
	}

}
