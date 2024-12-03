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

import javax.swing.JCheckBox;

import org.jvnet.flamingo.common.JCommandToggleButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * svg图片内文字删除线设置动作
 * @author 闫舒寰
 * @version 1.0 2009/03/04
 */
public class SvgGraphicFontLineThroughAction extends Actions {
	
	/**
	 * TODO
	 * 1、图片还不能设置删除线
	 * 2、删除线的适用范围还没有定义
	 */
	
	//删除线的8421码的二进制的形式
	int lineThroughIndicator = 0x4;
	int normalIndicator = ~0x4;
	
	public void doAction(ActionEvent e) {
		
		if (e.getSource() instanceof JCheckBox) {
			JCheckBox jc = (JCheckBox) e.getSource();
			
			int currentValue = 0;
			Object value = FontPropertyModel.getCurrentProperty(Constants.PR_TEXT_DECORATION);
			if (value != null) {
				if (value instanceof Integer) {
					Integer v = (Integer) value;
					currentValue = (Integer) v;
				}
			}
			
			
			if (jc.isSelected()) {
				FontPropertyModel.setFOProperty(Constants.PR_TEXT_DECORATION, currentValue | lineThroughIndicator);
			} else {
				FontPropertyModel.setFOProperty(Constants.PR_TEXT_DECORATION, currentValue & normalIndicator);
			}
			
		}
		
		if (e.getSource() instanceof JCommandToggleButton) {
			JCommandToggleButton ui =  (JCommandToggleButton) e.getSource();
			
			int currentValue = -1;
			
			Object value = RibbonUIModel.getReadCompletePropertiesByType().get(this.actionType).get(Constants.PR_TEXT_DECORATION);
			
			if (value instanceof Integer) {
				Integer v = (Integer) value;
				currentValue = (Integer) v;
			}
			
			if (ui.getActionModel().isSelected()) {
				setFOProperty(Constants.PR_TEXT_DECORATION, currentValue | lineThroughIndicator);
			} else {
				setFOProperty(Constants.PR_TEXT_DECORATION, currentValue & normalIndicator);
			}
			
		}
	}	
	
	@Override
	public void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_TEXT_DECORATION))
		{
			if (uiComponent instanceof JCommandToggleButton)
			{
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				int currentValue = -1;
				Object value = evt.getNewValue();
				if (value instanceof Integer)
				{
					Integer v = (Integer) value;
					currentValue = (Integer) v;
				}
				ui.getActionModel().setSelected(
						(currentValue & lineThroughIndicator) == 4);
			}
		}
	}
	
	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		
		if (evt.hasPropertyKey(Constants.PR_TEXT_DECORATION)) {
			if (uiComponent instanceof JCommandToggleButton) {
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				ui.getActionModel().setSelected(false);
			}
		}
	}
		
		
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		if (actionCommand == ActionCommandType.RIBBON_ACTION) {
			if (uiComponent != null) {
				if (uiComponent instanceof JCommandToggleButton) {
					JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
					ui.getActionModel().setSelected(false);
				}
			}
		}
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			 for (List<Object> list : RibbonUIManager.getUIComponents().get(this.actionID).get(actionCommand)) {
				for (Object object : list) {
					if (object instanceof JCheckBox) {
						JCheckBox ui = (JCheckBox) object;
						Object value =  RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_TEXT_DECORATION);
						if (value.equals(Constants.NULLOBJECT)) {
							ui.setSelected(false);
						} else {
							int currentValue = 0;
							
							if (value instanceof Integer) {
								Integer v = (Integer) value;
								currentValue = (Integer) v;
							}
							
							if ((currentValue & lineThroughIndicator) == 4) {
								ui.setSelected(true);
							} else {
								ui.setSelected(false);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean isAvailable() {
		return true;
	}

}
