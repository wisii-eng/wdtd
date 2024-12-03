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

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;

/**
 * 貌似FO对象的颜色属性没有那么多子种类，目前统一用ColorActionListener这个类，这个类不用。
 * 
 * @author 闫舒寰
 * 
 */
public class ParagraphBackgroundColorAction extends Actions
{

	public void doAction(ActionEvent e)
	{
		/*
		 * //颜色设置窗口 Color selectedColor = JColorChooser.showDialog(null,
		 * MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER,
		 * MessageConstants.FONT_COLOR_SET),Color.black); if(selectedColor !=
		 * null){ setFOProperty(propertyType, Constants.PR_BACKGROUND_COLOR,
		 * selectedColor); }
		 */

		if (e.getSource() instanceof ColorComboBox)
		{
			ColorComboBox colorCom = (ColorComboBox) e.getSource();
			Color selectedColor = (Color) colorCom.getSelectedItem();
			if (selectedColor != null)
			{
				setFOProperty(Constants.PR_BACKGROUND_COLOR, new WiseDocColor(
						selectedColor));
			} else
			{
				setFOProperty(Constants.PR_BACKGROUND_COLOR, null);
			}
		}

		if (e.getSource() instanceof RibbonColorList)
		{
			RibbonColorList colorCom = (RibbonColorList) e.getSource();
			Color selectedColor = (Color) colorCom.getSelectedItem();
			if (selectedColor != null)
			{
				setFOProperty(Constants.PR_BACKGROUND_COLOR, new WiseDocColor(
						selectedColor));
			} else
			{
				setFOProperty(Constants.PR_BACKGROUND_COLOR, null);
			}
		}
	}

	public void uiStateChange(UIStateChangeEvent evt)
	{
		// TODO Auto-generated method stub

	}

	public boolean isAvailable()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
