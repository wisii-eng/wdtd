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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jvnet.flamingo.common.JCommandMenuButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 背景图重复动作
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/05
 */
@SuppressWarnings("serial")
public class PageBodyBackgroundImageRepeatAction extends
		DefaultSimplePageMasterActions
{

	// 下拉列表中项目的个数
	private final int LIST_SIZE = 4;

	@Override
	public void doAction(ActionEvent e)
	{

		if (e.getSource() instanceof JCommandMenuButton)
		{

			Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
					.getUIComponents().get(this.getActionID());

			List<Object> list = null;
			for (List<Object> tempList : set
					.get(ActionCommandType.RIBBON_ACTION))
			{
				if (tempList.size() == LIST_SIZE)
				{
					list = tempList;
				}
			}

			setProperty(list.indexOf(e.getSource()));
		}
	}

	private void setProperty(int index)
	{
		SimplePageMaster current = null;
		switch (index)
		{
			case 0:
				current = setBodyBackgroundImageRepeat(Constants.EN_REPEAT);
				break;
			case 1:
				current = setBodyBackgroundImageRepeat(Constants.EN_REPEATX);
				break;
			case 2:
				current = setBodyBackgroundImageRepeat(Constants.EN_REPEATY);
				break;
			case 3:
				current = setBodyBackgroundImageRepeat(Constants.EN_NOREPEAT);
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
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);

		if (actionCommand == ActionCommandType.RIBBON_ACTION)
		{

			Map<ActionCommandType, Set<List<Object>>> set = RibbonUIManager
					.getUIComponents().get(this.getActionID());

			List<Object> list = null;
			for (List<Object> tempList : set
					.get(ActionCommandType.RIBBON_ACTION))
			{
				if (tempList.size() == LIST_SIZE)
				{
					list = tempList;
				}
			}

			if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType) != null)
			{
				// 用户如果没有点击界面则这个属性为空，则没有默认值
				SimplePageMaster value = ViewUiUtil
						.getCurrentSimplePageMaster(this.actionType);
				if (value != null)
				{
					int temp = ((RegionBody) value
							.getRegion(Constants.FO_REGION_BODY))
							.getCommonBorderPaddingBackground()
							.getBackgroundRepeat();
					if (temp == Constants.EN_REPEAT || temp == 0)
					{
						// temp = 0是默认值，那头没有处理过这个值
						((JCommandMenuButton) list.get(0)).getActionModel()
								.setSelected(true);
					} else if (temp == Constants.EN_REPEATX)
					{
						((JCommandMenuButton) list.get(1)).getActionModel()
								.setSelected(true);
					} else if (temp == Constants.EN_REPEATY)
					{
						((JCommandMenuButton) list.get(2)).getActionModel()
								.setSelected(true);
					} else if (temp == Constants.EN_NOREPEAT)
					{
						((JCommandMenuButton) list.get(3)).getActionModel()
								.setSelected(true);
					}
				}
			}
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		SimplePageMaster current = ViewUiUtil
				.getCurrentSimplePageMaster(this.actionType);
		if (current != null)
		{
			RegionBody body = (RegionBody) current
					.getRegion(Constants.FO_REGION_BODY);
			CommonBorderPaddingBackground bodycbpb = body
					.getCommonBorderPaddingBackground();
			if (bodycbpb != null)
			{
				return bodycbpb.getBackgroundImage() != null;
			}
		}
		return false;
	}

}
