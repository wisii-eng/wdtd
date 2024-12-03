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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.actions.MeasureConversion;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 分栏间距控制，这个暂时没用
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/12
 */
@SuppressWarnings("serial")
public class PageColumnGapAction extends DefaultSimplePageMasterActions
		implements ChangeListener
{

	JSpinner columnGap;

	JComboBox measurement;

	private void getComponents()
	{

		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				Page.COLUMN_GAPE_ACTION).get(ActionCommandType.DIALOG_ACTION);

		for (Iterator<List<Object>> iterator2 = comSet.iterator(); iterator2
				.hasNext();)
		{
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3
					.hasNext();)
			{
				Object uiComponents = (Object) iterator3.next();
				if (uiComponents instanceof JSpinner)
				{
					JSpinner ui = (JSpinner) uiComponents;
					columnGap = ui;
				}
				if (uiComponents instanceof JComboBox)
				{
					JComboBox ui = (JComboBox) uiComponents;
					this.measurement = ui;
				}
			}
		}
	}

	@Override
	public void doAction(ActionEvent e)
	{
		getComponents();
		setProperty();
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		getComponents();
		setProperty();
	}

	private void setProperty()
	{
		SimplePageMaster current = setColumnGap(
				(Double) (columnGap).getValue(), MeasureConversion
						.getIndexConversion((measurement).getSelectedIndex()));
		SimplePageMaster old = ViewUiUtil
				.getCurrentSimplePageMaster(this.actionType);
		Object result = ViewUiUtil.getMaster(current, old, this.actionType);
		setFOProperties(result, current);
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);

		if (actionCommand == ActionCommandType.DIALOG_ACTION)
		{
			getComponents();
			if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType) == null)
			{
				return;
			}
			SimplePageMaster value = ViewUiUtil
					.getCurrentSimplePageMaster(this.actionType);
			if (value != null)
			{
				FixedLength temp = (FixedLength) ((RegionBody) value
						.getRegion(Constants.FO_REGION_BODY))
						.getColumnGapLength();
				columnGap.setValue(temp.getNumericValue());
				measurement.setSelectedIndex(MeasureConversion
						.getIndexConversion(temp.getUnits()));
			}
		}
	}
}
