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

package com.wisii.wisedoc.view.ui.actions.regions;

import java.awt.Color;
import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.actions.pagelayout.DefaultSimplePageMasterActions;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 右区域背景颜色动作
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/18
 */
@SuppressWarnings("serial")
public class RegionEndBackgroundColorAction extends
		DefaultSimplePageMasterActions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof RibbonColorList)
		{
			RibbonColorList colorCom = (RibbonColorList) e.getSource();
			SimplePageMaster current = null;
			if (colorCom.getSelectedItem() instanceof Color)
			{
				Color selectedColor = (Color) colorCom.getSelectedItem();
				current = setRegionEndColor(selectedColor);
			} else if (colorCom.getSelectedItem() == null)
			{
				// 设置为无颜色
				current = setRegionEndColor(null);
			}
			SimplePageMaster old = ViewUiUtil
					.getCurrentSimplePageMaster(this.actionType);
			Object result = ViewUiUtil.getMaster(current, old, this.actionType);
			setFOProperties(result, current);
		}
	}

}
