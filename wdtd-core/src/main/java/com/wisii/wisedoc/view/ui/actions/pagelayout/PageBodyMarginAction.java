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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.jvnet.flamingo.ribbon.JRibbonBand;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.parts.dialogs.ParagraphAllPropertiesDialog;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 版心区域边距属性设置动作
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/05
 */
@SuppressWarnings("serial")
public class PageBodyMarginAction extends DefaultSimplePageMasterActions
{

	private FixedLengthSpinner top, bottom, left, right;

	private void getComponents()
	{

		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				Page.BODY_MARGIN_ACTION).get(ActionCommandType.DIALOG_ACTION);

		List<FixedLengthSpinner> temp = new ArrayList<FixedLengthSpinner>();

		for (List<Object> list : comSet)
		{
			for (Object uiComponents : list)
			{
				if (uiComponents instanceof FixedLengthSpinner)
				{
					FixedLengthSpinner ui = (FixedLengthSpinner) uiComponents;
					temp.add(ui);
				}
			}
		}

		this.top = temp.get(0);
		this.bottom = temp.get(1);
		this.left = temp.get(2);
		this.right = temp.get(3);
	}

	FixedLengthSpinner topMargin, bottomMargin, leftMargin, rightMargin;

	private void getRibbonComponents()
	{
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				Page.BODY_MARGIN_ACTION).get(ActionCommandType.RIBBON_ACTION);

		List<FixedLengthSpinner> temp = new ArrayList<FixedLengthSpinner>();

		for (List<Object> list : comSet)
		{
			for (Object uiComponents : list)
			{
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

	@Override
	public void doAction(ActionEvent e)
	{

		if (e.getSource() instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner jb = (FixedLengthSpinner) e.getSource();

			Component temp = jb.getParent();
			if (temp != null)
			{
				while (temp.getParent() != null)
				{
					if (temp instanceof ParagraphAllPropertiesDialog)
					{
						setProperty();
					} else if (temp instanceof JRibbonBand)
					{

						SwingUtilities.invokeLater(new Runnable()
						{

							@Override
							public void run()
							{
								setMarginProperty();
							}
						});
					}
					temp = temp.getParent();
				}
			}
		}
	}

	private void setProperty()
	{
		getComponents();
		SimplePageMaster current = setBodyMargin(top.getValue(), bottom
				.getValue(), left.getValue(), right.getValue());
		SimplePageMaster old = ViewUiUtil
				.getCurrentSimplePageMaster(this.actionType);
		Object result = ViewUiUtil.getMaster(current, old, this.actionType);
		setFOProperties(result, current);
	}

	private void setMarginProperty()
	{
		getRibbonComponents();
		SimplePageMaster current = setBodyMargin(topMargin.getValue(),
				bottomMargin.getValue(), leftMargin.getValue(), rightMargin
						.getValue());
		SimplePageMaster old = ViewUiUtil
				.getCurrentSimplePageMaster(this.actionType);
		Object result = ViewUiUtil.getMaster(current, old, this.actionType);
		setFOProperties(result, current);
	}

	@Override
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

				FixedLength tempTop = (FixedLength) ((RegionBody) simValue
						.getRegion(Constants.FO_REGION_BODY))
						.getCommonMarginBlock().getSpaceBefore().getOptimum(
								null);
				// 先移除监听器，以防在没有全部初始化的情况下发送状态变化监听器，初始化之后再添加监听器。
				topMargin.initValue(tempTop);

				FixedLength tempBottom = (FixedLength) ((RegionBody) simValue
						.getRegion(Constants.FO_REGION_BODY))
						.getCommonMarginBlock().getSpaceAfter()
						.getOptimum(null);
				bottomMargin.initValue(tempBottom);

				FixedLength tempLeft = (FixedLength) ((RegionBody) simValue
						.getRegion(Constants.FO_REGION_BODY))
						.getCommonMarginBlock().getMarginLeft();
				leftMargin.initValue(tempLeft);

				FixedLength tempRight = (FixedLength) ((RegionBody) simValue
						.getRegion(Constants.FO_REGION_BODY))
						.getCommonMarginBlock().getMarginRight();
				rightMargin.initValue(tempRight);
			}
		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);

		if (actionCommand == ActionCommandType.RIBBON_ACTION)
		{
			getRibbonComponents();
			if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType) != null)
			{
				SimplePageMaster value = ViewUiUtil
						.getCurrentSimplePageMaster(this.actionType);
				if (value != null)
				{
					FixedLength tempTop = (FixedLength) ((RegionBody) value
							.getRegion(Constants.FO_REGION_BODY))
							.getCommonMarginBlock().getSpaceBefore()
							.getOptimum(null);
					topMargin.initValue(tempTop);
					FixedLength tempBottom = (FixedLength) ((RegionBody) value
							.getRegion(Constants.FO_REGION_BODY))
							.getCommonMarginBlock().getSpaceAfter().getOptimum(
									null);
					bottomMargin.initValue(tempBottom);
					FixedLength tempLeft = (FixedLength) ((RegionBody) value
							.getRegion(Constants.FO_REGION_BODY))
							.getCommonMarginBlock().getMarginLeft();
					leftMargin.initValue(tempLeft);
					FixedLength tempRight = (FixedLength) ((RegionBody) value
							.getRegion(Constants.FO_REGION_BODY))
							.getCommonMarginBlock().getMarginRight();
					rightMargin.initValue(tempRight);
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
				FixedLength tempTop = (FixedLength) ((RegionBody) simValue
						.getRegion(Constants.FO_REGION_BODY))
						.getCommonMarginBlock().getSpaceBefore().getOptimum(
								null);
				top.initValue(tempTop);
				FixedLength tempBottom = (FixedLength) ((RegionBody) simValue
						.getRegion(Constants.FO_REGION_BODY))
						.getCommonMarginBlock().getSpaceAfter()
						.getOptimum(null);
				bottom.initValue(tempBottom);
				FixedLength tempLeft = (FixedLength) ((RegionBody) simValue
						.getRegion(Constants.FO_REGION_BODY))
						.getCommonMarginBlock().getMarginLeft();
				left.initValue(tempLeft);
				FixedLength tempRight = (FixedLength) ((RegionBody) simValue
						.getRegion(Constants.FO_REGION_BODY))
						.getCommonMarginBlock().getMarginRight();
				right.initValue(tempRight);
			}
		}
	}

}
