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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.swing.ui.LayerComboBox;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.ribbon.updateUI.FontColorUIUpdate;

/**
 * 设置svg图形中的文字的颜色
 * @author 闫舒寰
 * @version 1.0 2009/03/10
 */
public class SvgGraphicFontColorAction extends Actions {
	
	/**
	 * TODO
	 * 1、颜色图标没有更新
	 * 2、颜色的可用性没有判定
	 * 3、颜色的下拉菜单需要改进
	 * 4、颜色目前还不能“更新界面”
	 */
	
	private LayerComboBox comLayer = null;
	private ColorComboBox comColor = null;
	private void getComponents(){
		
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(Font.COLOR_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		for (List<Object> list : comSet) {
			for (Object object : list) {
				if(object instanceof LayerComboBox){
					comLayer = (LayerComboBox)object;
				}else if(object instanceof ColorComboBox){
					comColor = (ColorComboBox)object;
				}
			}
		}
	}
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof ColorComboBox || e.getSource() instanceof LayerComboBox)
		{
			getComponents();
			ColorComboBox colorCom = (ColorComboBox) comColor;
			Color selectedColor = (Color) colorCom.getSelectedItem();
			WiseDocColor color = null;
			if (selectedColor != null)
			{
				if(comLayer != null)
					color = new WiseDocColor(selectedColor, comLayer.getLayer());
				else
					color = new WiseDocColor(selectedColor);
				FontPropertyModel.setFOProperty(Constants.PR_COLOR, color);
			} else
			{
				FontPropertyModel.setFOProperty(Constants.PR_COLOR, color);
			}
		}

		if (e.getSource() instanceof RibbonColorList)
		{
			RibbonColorList colorCom = (RibbonColorList) e.getSource();
			Color selectedColor = (Color) colorCom.getSelectedItem();
			FontColorUIUpdate.FontColor.setColor(selectedColor);
			if (selectedColor != null)
			{
				setFOProperty(Constants.PR_COLOR, new WiseDocColor(
						selectedColor));
			} else
			{
				setFOProperty(Constants.PR_COLOR, null);
			}

		}
	}
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		Object value =  RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_COLOR);
		if(Constants.NULLOBJECT.equals(value))
			return;
		WiseDocColor color = (WiseDocColor)value;
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			getComponents();
			 Set<List<Object>> tempSet = RibbonUIManager.getUIComponents().get(this.actionID).get(actionCommand);
			 for (List<Object> list : tempSet) {
				for (Object object : list) {
					if (object instanceof ColorComboBox) {
						ColorComboBox comColor = (ColorComboBox) object;						
						comColor.setSelectedItem(new Color(color.getRGB()));
					}else if (object instanceof LayerComboBox) {
						LayerComboBox comLayer = (LayerComboBox) object;
						comLayer.setSelectedIndex(color.getLayer());
					}
				}
			}
		}
	}

}
