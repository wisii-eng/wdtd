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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 用于设置边框颜色的动作
 * 
 * @author	闫舒寰
 * @since	2008/10/6
 */
public class ParagraphBorderColorAction extends Actions {
	
	

	@Override
	public void doAction(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() instanceof ColorComboBox) {
			ColorComboBox colorCom = (ColorComboBox) e.getSource();
			if (colorCom.getSelectedItem() instanceof Color) {
				Color selectedColor = (Color) colorCom.getSelectedItem();
				if(selectedColor != null){
//					SinglerObserver.getInstance().setProperty(propertyType, Constants.PR_COLOR, selectedColor);
//					setFOProperty(propertyType, Constants.PR_COLOR, selectedColor);
					setBorderColorProperty(selectedColor);
				}
			}
//			System.out.println(colorCom.getSelectedItem());
		}	
		
		/*//颜色设置窗口	
		Color selectedColor = JColorChooser.showDialog(null, 
				MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER, MessageConstants.FONT_COLOR_SET),Color.black);
		if(selectedColor != null){
			setBorderColorProperty(selectedColor);
		}	*/	

	}

	
	private void setBorderColorProperty(Color color){
		/*//设置四个边的颜色
		setFOProperty(propertyType, Constants.PR_BORDER_BEFORE_COLOR, color);
		setFOProperty(propertyType, Constants.PR_BORDER_AFTER_COLOR, color);
		setFOProperty(propertyType, Constants.PR_BORDER_START_COLOR, color);
		setFOProperty(propertyType, Constants.PR_BORDER_END_COLOR, color);*/
		
		Map<Integer, Object> properties = new HashMap<Integer, Object>();		
		//设置四个边默认的颜色
		properties.put(Constants.PR_BORDER_BEFORE_COLOR, color);
		properties.put(Constants.PR_BORDER_AFTER_COLOR, color);
		properties.put(Constants.PR_BORDER_START_COLOR, color);
		properties.put(Constants.PR_BORDER_END_COLOR, color);
		
		setFOProperties(properties);
	}

}
