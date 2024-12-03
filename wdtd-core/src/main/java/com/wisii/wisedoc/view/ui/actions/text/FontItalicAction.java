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
package com.wisii.wisedoc.view.ui.actions.text;

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
 * 配合Ribbon界面所生成的动作
 * 
 * @author	闫舒寰
 * @version 0.1 2008/10/23
 */
public class FontItalicAction extends Actions {
	
	/**
	 * TODO
	 * 1、isAvailable没有精确定义什么类型的东西能设置斜体
	 * 
	 * 
	 */

	@Override
	public void doAction(final ActionEvent e) {
		if (e.getSource() instanceof JCommandToggleButton) {
			final JCommandToggleButton boldButton =  (JCommandToggleButton) e.getSource();
			if(boldButton.getActionModel().isSelected()){
				setFOProperty(Constants.PR_FONT_STYLE, Constants.EN_ITALIC);
			} else {
				setFOProperty(Constants.PR_FONT_STYLE, Constants.EN_NORMAL);
			}
		}
	}

	
	@Override
	public void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		if(hasPropertyKey(Constants.PR_FONT_STYLE)){
			if (uiComponent instanceof JCommandToggleButton) {
				final JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				if (evt.getNewValue() instanceof Integer) {
					final Integer value = (Integer) evt.getNewValue();
//						System.out.println(value);
					if (value == Constants.EN_NORMAL) {
						ui.getActionModel().setSelected(false);
					} else if(value == Constants.EN_ITALIC){
						ui.getActionModel().setSelected(true);
					}
				}
			}
		}
	}
	
	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if(evt.hasPropertyKey(Constants.PR_FONT_STYLE)){
			if (uiComponent instanceof JCommandToggleButton) {
				final JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				ui.getActionModel().setSelected(false);
			}
		}
	}
	
	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof JCommandToggleButton) {
			final JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
			ui.getActionModel().setSelected(false);
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
		for (final Object object : elementList)
		{
			if (object instanceof ExternalGraphic
					|| object instanceof ImageInline)
			{
				return false;
			}
		}
		return true;
	}
}
