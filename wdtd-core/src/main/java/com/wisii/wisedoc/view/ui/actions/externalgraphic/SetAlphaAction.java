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
/**
 * @SetAlaphAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.externalgraphic;

import java.awt.event.ActionEvent;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 类功能描述：设置图片的透明度
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-23
 */
public class SetAlphaAction extends Actions {
	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseSpinner)
		{
			WiseSpinner valuecom = (WiseSpinner) e.getSource();
			Object value = valuecom.getValue();
			setFOProperty(Constants.PR_APHLA, value);
		}
	}

	@Override
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
					ui.initValue(255);
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
						ui.initValue(255);
					}
				}
			}
		}
	}

	@Override
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

	@Override
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
		return super.isAvailable();
	}

}
