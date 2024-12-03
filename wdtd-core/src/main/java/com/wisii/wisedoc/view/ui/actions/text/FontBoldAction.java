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

import javax.swing.ButtonModel;

import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.ribbon.JRibbon;

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

public class FontBoldAction extends Actions {
	
	//根据规范7.9.9粗体就是700
	private final int BOLD = Constants.EN_700;

	/**
	 * TODO
	 * 1、文字粗体的不同程度不能选择
	 * 2、需要把不同程度的粗体直接显示到菜单中
	 */

	@Override
	public void doAction(final ActionEvent e) {
//		System.out.println(e.getSource());
//		this.uiComponent = e.getSource();
		/*System.out.println("~~~~~~~~~~~~~~~~~~~~~~分割线~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("property Type: " + propertyType);
		System.out.println("Action command: " + e.getActionCommand());
		System.out.println(e.getSource().getClass());*/
		//Ribbon界面的动作设置
		if (e.getSource() instanceof JCommandToggleButton) {
			final JCommandToggleButton boldButton =  (JCommandToggleButton) e.getSource();
//			System.out.println("event modifiers:" + e.getModifiers() + " button modifiers:" + boldButton.getActionModel().getActionCommand());
			if(boldButton.getActionModel().isSelected() || e.getModifiers() == 2){
				setFOProperty(Constants.PR_FONT_WEIGHT, BOLD);
			} else {
				//当处理到快捷键的时候还需要看用户选择的字是否是粗体
				setFOProperty(Constants.PR_FONT_WEIGHT, Constants.EN_NORMAL);
			}
		}
		
		if (e.getSource() instanceof ButtonModel) {
			final ButtonModel boldButton =  (ButtonModel) e.getSource();
			/*System.out.println("event modifiers:" + e.getModifiers() + " button modifiers:" + boldButton.getActionCommand());
			System.out.println("is selected: " + boldButton.isSelected() + " is pressed: " + boldButton.isPressed());
			System.out.println(boldButton.toString());*/
			if(boldButton.isSelected() /*|| e.getModifiers() == 2*/){
				setFOProperty(Constants.PR_FONT_WEIGHT, BOLD);
			} else {
				//当处理到快捷键的时候还需要看用户选择的字是否是粗体
				setFOProperty(Constants.PR_FONT_WEIGHT, Constants.EN_NORMAL);
			}
		}
		
		//快捷键绑定到Ribbon界面上所产生的动作源则是JRibbon
		if (e.getSource() instanceof JRibbon) {
			final Object o = RibbonUIModel.getReadCompletePropertiesByType().get(this.actionType).get(Constants.PR_FONT_WEIGHT);
			if (o instanceof Integer) {
				final Integer value = (Integer) o;
				if (value == Constants.EN_NORMAL) {
					setFOProperty(Constants.PR_FONT_WEIGHT, BOLD);
				} else {
					setFOProperty(Constants.PR_FONT_WEIGHT, Constants.EN_NORMAL);
				}
			} else {
				//这种情况是处理用户选择了两部分不同属性的对象时发出的Constants.NULLOBJECT对象。
				setFOProperty(Constants.PR_FONT_WEIGHT, Constants.EN_NORMAL);
			}
		}
	}
	
	
	@Override
	public void uiStateChange(final UIStateChangeEvent evt) {
		
		if(hasPropertyKey(Constants.PR_FONT_WEIGHT)){
//			System.out.println("in bold action: " + evt.getNewValue());
			if (uiComponent instanceof JCommandToggleButton) {
				final JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				if (evt.getNewValue() instanceof Integer) {
					final Integer value = (Integer) evt.getNewValue();
//					System.out.println(value);
					if (value == Constants.EN_NORMAL) {
						ui.getActionModel().setSelected(false);
					} else if(value == BOLD){
						ui.getActionModel().setSelected(true);
					}
				}
			}
		}
	}


	@Override
	public void setDefaultState(final ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent != null) {
			if (uiComponent instanceof JCommandToggleButton) {
				final JCommandToggleButton button = (JCommandToggleButton) uiComponent;
				button.getActionModel().setSelected(false);
			}
		}
		
	}
	
	@Override
	protected void setDifferentPropertyState(final UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
//		if(hasPropertyKey(Constants.PR_FONT_WEIGHT)){
			setDefaultState(null);
//		}
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
