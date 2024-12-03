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

import java.awt.Color;
import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;

/**
 * 文字背景颜色设置动作
 * 
 * @author 闫舒寰
 * @version 0.2 2008/10/23
 */
public class FontBackgroundColorAction extends Actions
{
	/**
	 * TODO
	 * 1、颜色图标没有更新
	 * 2、颜色的可用性没有判定
	 * 3、颜色的下拉菜单需要改进
	 * 4、颜色目前还不能“更新界面”
	 */

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof ColorComboBox)
		{
			ColorComboBox colorCom = (ColorComboBox) e.getSource();
			Color selectedColor = (Color) colorCom.getSelectedItem();
			if (selectedColor != null)
			{
				setFOProperty(Constants.PR_COLOR, new WiseDocColor(
						selectedColor));
			} else
			{
				setFOProperty(Constants.PR_COLOR, null);
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

}
