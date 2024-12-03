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
package com.wisii.wisedoc.view.ui.actions.paragraph;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 用于激活其他边框属性设置的动作的菜单
 * 
 * @author	闫舒寰
 * @since	2008/09/27
 */
public class ParagraphBorderSettingAction extends Actions {

	@Override
	public void doAction(ActionEvent e) {
		
		if (e.getSource() instanceof JCheckBox) {
			JCheckBox jcb = (JCheckBox) e.getSource();
			
			if (jcb.isSelected()){
				((Component)uiComponents.get(0)).setEnabled(true);
				((Component)uiComponents.get(1)).setEnabled(true);
				((Component)uiComponents.get(2)).setEnabled(true);
				((Component)uiComponents.get(3)).setEnabled(true);
				((Component)uiComponents.get(4)).setEnabled(true);
				/*//设置默认边为0
				((JSpinner)uiComponents.get(0)).setValue(new Double(1));
				//设置边框样式为solid
				((JComboBox)uiComponents.get(3)).setSelectedIndex(4);*/
				
			} else {
				((Component)uiComponents.get(0)).setEnabled(false);
				((Component)uiComponents.get(1)).setEnabled(false);
				((Component)uiComponents.get(2)).setEnabled(false);
				((Component)uiComponents.get(3)).setEnabled(false);
				((Component)uiComponents.get(4)).setEnabled(false);
				setDefaultBorder();
			}			
		}	
	}

	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}
	
	private void setDefaultBorder(){
		/*setFOProperty(propertyType, Constants.PR_BORDER_BEFORE_STYLE, Constants.EN_NONE);
		setFOProperty(propertyType, Constants.PR_BORDER_AFTER_STYLE, Constants.EN_NONE);
		setFOProperty(propertyType, Constants.PR_BORDER_START_STYLE, Constants.EN_NONE);
		setFOProperty(propertyType, Constants.PR_BORDER_END_STYLE, Constants.EN_NONE);*/
		
		Map<Integer, Object> properties = new HashMap<Integer, Object>();
		//设置四个边默认的宽度
		properties.put(Constants.PR_BORDER_BEFORE_WIDTH, null);
		properties.put(Constants.PR_BORDER_AFTER_WIDTH, null);
		properties.put(Constants.PR_BORDER_START_WIDTH, null);
		properties.put(Constants.PR_BORDER_END_WIDTH, null);
		//设置四个边默认的样式
		properties.put(Constants.PR_BORDER_BEFORE_STYLE, null);
		properties.put(Constants.PR_BORDER_AFTER_STYLE, null);
		properties.put(Constants.PR_BORDER_START_STYLE, null);
		properties.put(Constants.PR_BORDER_END_STYLE, null);
		//设置四个边默认的颜色
		properties.put(Constants.PR_BORDER_BEFORE_COLOR, null);
		properties.put(Constants.PR_BORDER_AFTER_COLOR, null);
		properties.put(Constants.PR_BORDER_START_COLOR, null);
		properties.put(Constants.PR_BORDER_END_COLOR, null);
		
		setFOProperties(properties);
		
		/*//设置四个边的长度
		setFOProperty(propertyType, Constants.PR_BORDER_BEFORE_WIDTH, null);
		setFOProperty(propertyType, Constants.PR_BORDER_AFTER_WIDTH, null);
		setFOProperty(propertyType, Constants.PR_BORDER_START_WIDTH, null);
		setFOProperty(propertyType, Constants.PR_BORDER_END_WIDTH, null);
		
		//设置四个边的样式
		setFOProperty(propertyType, Constants.PR_BORDER_BEFORE_STYLE, null);
		setFOProperty(propertyType, Constants.PR_BORDER_AFTER_STYLE, null);
		setFOProperty(propertyType, Constants.PR_BORDER_START_STYLE, null);
		setFOProperty(propertyType, Constants.PR_BORDER_END_STYLE, null);
		
		//设置四个边的颜色
		setFOProperty(propertyType, Constants.PR_BORDER_BEFORE_COLOR, null);
		setFOProperty(propertyType, Constants.PR_BORDER_AFTER_COLOR, null);
		setFOProperty(propertyType, Constants.PR_BORDER_START_COLOR, null);
		setFOProperty(propertyType, Constants.PR_BORDER_END_COLOR, null);*/
	}

}
