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
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.actions.MeasureConversion;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

@SuppressWarnings("serial")
public class PagePaperSizeInputAction extends DefaultSimplePageMasterActions
		implements ChangeListener
{

	JSpinner widthValue;

	JSpinner heightValue;

	JComboBox measurement;

	private void getComponents()
	{

		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				Page.PAPER_SIZE_INPUT_ACTION).get(
				ActionCommandType.DIALOG_ACTION);

		List<JSpinner> temp = new ArrayList<JSpinner>();

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
					temp.add(ui);
				}
				if (uiComponents instanceof JComboBox)
				{
					JComboBox ui = (JComboBox) uiComponents;
					this.measurement = ui;
				}
			}
		}
		this.widthValue = temp.get(0);
		this.heightValue = temp.get(1);
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
		// 设置正确的属性类型和属性值
		setProperty();
	}

	private void setProperty()
	{
		SimplePageMaster current = setPageSize(
				(Double) (widthValue).getValue(), (Double) (heightValue)
						.getValue(), MeasureConversion
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
				FixedLength tempTop = (FixedLength) (value.getPageWidth());
				widthValue.setValue(tempTop.getNumericValue());
				FixedLength tempBottom = (FixedLength) (value.getPageHeight());
				heightValue.setValue(tempBottom.getNumericValue());
				measurement.setSelectedIndex(MeasureConversion
						.getIndexConversion(tempTop.getUnits()));
			}
		}
	}

}
