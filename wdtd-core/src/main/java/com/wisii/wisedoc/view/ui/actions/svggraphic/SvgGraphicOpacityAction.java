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
package com.wisii.wisedoc.view.ui.actions.svggraphic;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg透明度的设置动作
 * @author 闫舒寰
 * @version 1.0 2009/03/04
 */
public class SvgGraphicOpacityAction extends Actions {
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseSpinner)
		{
			WiseSpinner valuecom = (WiseSpinner) e.getSource();
			Object value = valuecom.getValue();
			setFOProperty(Constants.PR_APHLA, value);
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_APHLA))
		{
			Object obj = evt.getNewValue();
			if (uiComponent instanceof WiseSpinner)
			{
				WiseSpinner ui = (WiseSpinner) uiComponent;
				if (obj != null)
				{

					ui.initValue(obj);

				} else
				{
					ui.initValue(1);
				}
			}

		} else {
			//为默认值添加一些判断
			if (uiComponent instanceof WiseSpinner)
			{
				WiseSpinner ui = (WiseSpinner) uiComponent;
				Map<Integer, Object> obj = RibbonUIModel.getCurrentPropertiesByType().get(this.actionType);
				
				if (obj != null) {
					//这个是针对Ribbon界面设置属性时也会引发默认值判断的分支
					if (obj.containsKey(Constants.PR_APHLA)) {
						ui.initValue(obj.get(Constants.PR_APHLA));
					} else {
						ui.initValue(1.0);
					}
				} 
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseSpinner)
		{
			WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(InitialManager.getInitialValue(Constants.PR_APHLA,
					null));
		}
	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof WiseSpinner)
		{
			WiseSpinner ui = (WiseSpinner) uiComponent;
			ui.initValue(null);
		}
	}
	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		List<Object> elementList = RibbonUIModel.getElementList();
		if (elementList == null)
		{
			return false;
		}
		for (Object object : elementList)
		{
			if (object instanceof AbstractSVG && !(object instanceof Line))
			{
				return true;
			}
		}
		return false;
	}

}
