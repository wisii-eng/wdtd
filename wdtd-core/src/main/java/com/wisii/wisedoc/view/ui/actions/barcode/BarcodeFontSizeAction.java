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
package com.wisii.wisedoc.view.ui.actions.barcode;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 条形码字号设置动作
 * @author 闫舒寰
 * @version 1.0 2009/02/02
 */
public class BarcodeFontSizeAction extends Actions {
	
	/**
	 * TODO
	 * 1、输入字号边界判定
	 * 2、字号所对应的中文字号有问题
	 */

	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			
			JComboBox jb = (JComboBox) e.getSource();
			double fontSize = -1;
			
			if (jb.getSelectedItem() instanceof String) {
				String selectedSize = (String) jb.getSelectedItem();
				//把中文字号转化成数字形式的pt字号
				if(selectedSize.equals(UiText.FONT_SIZE_LIST[0])){
					fontSize = 63;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SPECIAL))){
					fontSize = 54;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_BIG))){
					fontSize = 42;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_LITTLE_BIG))){
					fontSize = 36;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_BIG_FIRST))){
					fontSize = 31.5;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_FIRST))){
					fontSize = 28;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SECOND))){
					fontSize = 21;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SECOND_LITTLE))){
					fontSize = 18;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_THIRD))){
					fontSize = 16;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_FOURTH))){
					fontSize = 14;
				} else if(selectedSize.equals(UiText.FONT_SIZE_LIST[10])){
					fontSize = 12;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_FIFTH))){
					fontSize = 10.5;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_FIFTH_LITTLE))){
					fontSize = 9;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SIXTH))){
					fontSize = 8;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SIXTH_LITTLE))){
					fontSize = 6.875;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_SEVENTH))){
					fontSize = 5.25;
				} else if(selectedSize.equals(MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,MessageConstants.FONT_SIZE_EIGHTH))){
					fontSize = 4.5;
				} else {
					//解析本来是数字形式的字符
					try{
						fontSize = Double.parseDouble(selectedSize);
					}catch (NumberFormatException ee) {
						// TODO: handle exception
//						System.out.println("请输入正确的数字");
					}
				}				
			}		
			
			//设置字号到属性中
			if (fontSize != -1) {
				setFOProperty(Constants.PR_BARCODE_FONT_HEIGHT,
						new FixedLength(fontSize,"pt"));
			}
		}
	}
	
	@Override
	public void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_BARCODE_FONT_HEIGHT))
		{
			if (uiComponent instanceof JComboBox)
			{
				JComboBox ui = (JComboBox) uiComponent;
				Object fontheight = evt.getNewValue();
				if (fontheight instanceof FixedLength)
				{
					FixedLength value = (FixedLength) evt.getNewValue();
					ui.setSelectedItem(value.toString());
				} else
				{
					ui.setSelectedItem(UiText.FONT_SIZE_LIST[10]);
				}
			}
		}
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof JComboBox) {
			JComboBox ui = (JComboBox) uiComponent;
			ui.setSelectedItem(UiText.FONT_SIZE_LIST[10]);
		}
	}
	
	@Override
	public void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if(evt.hasPropertyKey(Constants.PR_BARCODE_FONT_HEIGHT)){
			if (uiComponent instanceof JComboBox) {
				JComboBox ui = (JComboBox) uiComponent;
				ui.setSelectedItem("");
			}
		}
	}
	
	@Override
	public boolean isAvailable() {
		return true;
	}

}
