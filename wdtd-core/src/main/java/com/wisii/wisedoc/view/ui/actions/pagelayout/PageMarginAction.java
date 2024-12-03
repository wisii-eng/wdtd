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

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

@SuppressWarnings("serial")
public class PageMarginAction extends DefaultSimplePageMasterActions
{

	FixedLengthSpinner topMargin, bottomMargin, leftMargin, rightMargin;

	private void getRibbonComponents()
	{
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				Page.MARGIN_ACTION).get(ActionCommandType.RIBBON_ACTION);

		List<FixedLengthSpinner> temp = new ArrayList<FixedLengthSpinner>();

		for (Iterator<List<Object>> iterator2 = comSet.iterator(); iterator2
				.hasNext();)
		{
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3
					.hasNext();)
			{
				Object uiComponents = (Object) iterator3.next();
				if (uiComponents instanceof FixedLengthSpinner)
				{
					FixedLengthSpinner ui = (FixedLengthSpinner) uiComponents;
					temp.add(ui);
				}
			}
		}
		this.topMargin = temp.get(0);
		this.bottomMargin = temp.get(1);
		this.leftMargin = temp.get(2);
		this.rightMargin = temp.get(3);
	}

	public void doAction(ActionEvent e)
	{
		setMarginProperty();
	}

	private void setMarginProperty()
	{
		getRibbonComponents();
		SimplePageMaster current = setPageMargin(topMargin.getValue(),
				bottomMargin.getValue(), leftMargin.getValue(), rightMargin
						.getValue());
		SimplePageMaster old = ViewUiUtil
				.getCurrentSimplePageMaster(this.actionType);
		Object result = ViewUiUtil.getMaster(current, old, this.actionType);
		setFOProperties(result, current);
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		getRibbonComponents();

		if (hasPropertyKey(Constants.PR_SIMPLE_PAGE_MASTER)
				|| hasPropertyKey(Constants.PR_CURRENT_SIMPLE_PAGE_MASTER))
		{
			Object value = evt.getNewValue();
			if (value instanceof SimplePageMaster)
			{
				SimplePageMaster simValue = (SimplePageMaster) value;

				// System.err.println(simValue);

				FixedLength tempTop = (FixedLength) simValue
						.getCommonMarginBlock().getSpaceBefore().getOptimum(
								null);
				if (tempTop == null || tempTop.getValue() == 0)
				{
					tempTop = (FixedLength) simValue.getCommonMarginBlock()
							.getMarginTop();
				}
				topMargin.initValue(tempTop);
				/*
				 * JSpinner.NumberEditor editor =
				 * (JSpinner.NumberEditor)topRibbon.getEditor();
				 * editor.getTextField().setText(tempTop.getLengthValue() + " "
				 * + unitTop);
				 */

				FixedLength tempBottom = (FixedLength) simValue
						.getCommonMarginBlock().getSpaceAfter()
						.getOptimum(null);
				if (tempBottom == null || tempBottom.getValue() == 0)
				{
					tempBottom = (FixedLength) simValue.getCommonMarginBlock()
							.getMarginBottom();
				}
				bottomMargin.initValue(tempBottom);

				FixedLength tempLeft = (FixedLength) simValue
						.getCommonMarginBlock().getMarginLeft();
				leftMargin.initValue(tempLeft);

				FixedLength tempRight = (FixedLength) simValue
						.getCommonMarginBlock().getMarginRight();
				rightMargin.initValue(tempRight);

			}
		}
	}

}
