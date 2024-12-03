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

import org.jvnet.flamingo.common.JCommandMenuButton;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionBefore;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 根据给定型号的纸张类型设置页面大小
 * 
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
@SuppressWarnings("serial")
public class PagePaperSizeAction extends DefaultSimplePageMasterActions
{

	@Override
	public void doAction(ActionEvent e)
	{

		if (e.getSource() instanceof JComboBox)
		{
			JComboBox page = (JComboBox) e.getSource();
			setProperty(page.getSelectedIndex());
		}

		if (e.getSource() instanceof JCommandMenuButton)
		{

			Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
					.getUIComponents().get(this.getActionID());

			List<Object> temp = new ArrayList<Object>();

			for (List<Object> list2 : set.get(ActionCommandType.RIBBON_ACTION))
			{
				List<Object> list = list2;
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
		switch (index)
		{
			case 0:
				// letter
				setSize(21.59, 27.94);
				break;
			case 1:
				// Tabloid
				setSize(27.94, 43.17);
				break;
			case 2:
				// Ledger
				setSize(43.17, 27.94);
				break;
			case 3:
				// A3
				setSize(29.7, 42);
				break;
			case 4:
				// A4
				setSize(21, 29.7);
				break;
			case 5:
				// A5
				setSize(14.8, 21);
				break;
			case 6:
				// B4
				setSize(25.7, 36.4);
				break;
			case 7:
				// B5
				setSize(18.2, 25.7);
				break;
			case 8:
				// 16开
				setSize(18.4, 26);
				break;
			case 9:
				// 32开
				setSize(13, 18.4);
				break;
			case 10:
				// 大32开
				setSize(14, 20.3);
				break;

			default:
				break;
		}
	}

	private void setSize(double width, double height)
	{
		SimplePageMaster current = setPageSize(width, height, "cm");
		SimplePageMaster old = ViewUiUtil
				.getCurrentSimplePageMaster(this.actionType);
		Object result = ViewUiUtil.getMaster(current, old, this.actionType);
		setFOProperties(result, current);
	}

	private List<JCommandMenuButton> uiList;

	@SuppressWarnings("unused")
	private void getComponents()
	{

		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				RegionBefore.WRITING_MODE_ACTION).get(
				ActionCommandType.RIBBON_ACTION);

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
						FixedLength width = (FixedLength) value
								.getPageWidth();
						FixedLength height = (FixedLength) value
								.getPageHeight();
						String widthLength = width.getLengthValueString();
						String heightLength = height.getLengthValueString();
						String widthUnits = width.getUnits();
						String heightUnits = height.getUnits();
						if (widthUnits.equalsIgnoreCase("cm")
								&& heightUnits.equalsIgnoreCase("cm"))
						{
							int index = indexPosition(widthLength, heightLength);
							((JCommandMenuButton) list.get(index))
									.getActionModel().setSelected(true);
						}
					}
				}
			}
		}

	}

	private int indexPosition(String width, String height)
	{
		int value = 0;
		if (width.equalsIgnoreCase("21.59") && height.equalsIgnoreCase("27.94"))
		{
			value = 0;
		} else if (width.equalsIgnoreCase("27.94")
				&& height.equalsIgnoreCase("43.17"))
		{
			value = 1;
		} else if (width.equalsIgnoreCase("43.17")
				&& height.equalsIgnoreCase("27.94"))
		{
			value = 2;
		} else if (width.equalsIgnoreCase("29.7")
				&& height.equalsIgnoreCase("42"))
		{
			value = 3;
		} else if (width.equalsIgnoreCase("21")
				&& height.equalsIgnoreCase("29.7"))
		{
			value = 4;
		} else if (width.equalsIgnoreCase("14.8")
				&& height.equalsIgnoreCase("21"))
		{
			value = 5;
		} else if (width.equalsIgnoreCase("25.7")
				&& height.equalsIgnoreCase("36.4"))
		{
			value = 6;
		} else if (width.equalsIgnoreCase("18.2")
				&& height.equalsIgnoreCase("25.7"))
		{
			value = 7;
		} else if (width.equalsIgnoreCase("18.4")
				&& height.equalsIgnoreCase("26"))
		{
			value = 8;
		} else if (width.equalsIgnoreCase("13")
				&& height.equalsIgnoreCase("18.4"))
		{
			value = 9;
		} else if (width.equalsIgnoreCase("14")
				&& height.equalsIgnoreCase("20.3"))
		{
			value = 10;
		} else
		{
			value = 11;
		}
		return value;
	}
}
