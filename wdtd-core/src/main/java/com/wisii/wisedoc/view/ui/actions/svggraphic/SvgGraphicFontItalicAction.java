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

import org.jvnet.flamingo.common.JCommandToggleButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 设置svg图片内文字斜体动作
 * @author 闫舒寰
 * @version 1.0 2009/03/04
 */
public class SvgGraphicFontItalicAction extends Actions {
	
	/**
	 * TODO
	 * 1、isAvailable没有精确定义什么类型的东西能设置斜体
	 * 
	 * 
	 */

	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JCommandToggleButton) {
			JCommandToggleButton boldButton =  (JCommandToggleButton) e.getSource();
			if(boldButton.getActionModel().isSelected()){
				setFOProperty(Constants.PR_FONT_STYLE, Constants.EN_ITALIC);
			} else {
				setFOProperty(Constants.PR_FONT_STYLE, Constants.EN_NORMAL);
			}
		}
	}

	
	@Override
	public void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_FONT_STYLE))
		{
			if (uiComponent instanceof JCommandToggleButton)
			{
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				Object fs = evt.getNewValue();
				ui.getActionModel().setSelected(
						fs instanceof Integer
								&& (Integer) fs == Constants.EN_ITALIC);
			}
		}
	}
	
	
	
	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if(evt.hasPropertyKey(Constants.PR_FONT_STYLE)){
			if (uiComponent instanceof JCommandToggleButton) {
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				ui.getActionModel().setSelected(false);
			}
		}
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof JCommandToggleButton) {
			JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
			ui.getActionModel().setSelected(false);
		}
	}
	
	@Override
	public boolean isAvailable() {
		if (!super.isAvailable())
		{
			return false;
		}
		List<Object> elementList = RibbonUIModel.getElementList();
		if (elementList == null)
		{
			return false;
		}
		for (Object object : elementList) {
			if (object instanceof ExternalGraphic || object instanceof ImageInline) {
				return false;
			}
		}
		return true;
	}

}
