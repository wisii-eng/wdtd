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
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.parts.text.FontPropertyPanel;

/**
 * 设置字型的动作类，就是粗体、斜体的设置动作
 * 
 * @author 闫舒寰
 * @since 2009/09/18
 *
 */
public class FontStyleAction extends Actions {
	
	@Override
	public void doAction(ActionEvent e) {
		
		if (e.getSource() instanceof JComboBox) {
			JComboBox jb = (JComboBox) e.getSource();
			
			switch (jb.getSelectedIndex()) {
			//正常
			case 0:
				FontPropertyModel.setFOProperty(Constants.PR_FONT_STYLE, Constants.EN_NORMAL);
				FontPropertyModel.setFOProperty(Constants.PR_FONT_WEIGHT, Constants.EN_NORMAL);
				break;
			//粗体
			case 1:
				FontPropertyModel.setFOProperty(Constants.PR_FONT_STYLE, Constants.EN_NORMAL);
				FontPropertyModel.setFOProperty(Constants.PR_FONT_WEIGHT, Constants.EN_800);
				break;
			//斜体
			case 2:
				FontPropertyModel.setFOProperty(Constants.PR_FONT_STYLE, Constants.EN_ITALIC);
				FontPropertyModel.setFOProperty(Constants.PR_FONT_WEIGHT, Constants.EN_NORMAL);
				break;
			//粗斜体
			case 3:
				FontPropertyModel.setFOProperty(Constants.PR_FONT_STYLE, Constants.EN_ITALIC);
				FontPropertyModel.setFOProperty(Constants.PR_FONT_WEIGHT, Constants.EN_800);
				break;
				
			default:
				break;
			}
		}
		
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION)
		{
			for (List<Object> list : RibbonUIManager.getUIComponents().get(
					this.actionID).get(actionCommand))
			{
				for (Object object : list)
				{
					if (object instanceof JComboBox)
					{
						JComboBox ui = (JComboBox) object;
						Object fontWeight = InitialManager.getInitialValue(
								Constants.PR_FONT_WEIGHT, null);
						Object fontStyle = InitialManager.getInitialValue(
								Constants.PR_FONT_STYLE, null);
						Map<Integer, Object> atts = RibbonUIModel
								.getCurrentPropertiesByType().get(
										this.actionType);
						if (atts != null)
						{
							if (atts.containsKey(Constants.PR_FONT_WEIGHT))
							{
								fontWeight = atts.get(Constants.PR_FONT_WEIGHT);
							}
							if (atts.containsKey(Constants.PR_FONT_STYLE))
							{
								fontStyle = atts.get(Constants.PR_FONT_STYLE);
							}
						}
						if (fontWeight.equals(Constants.NULLOBJECT))
						{
							// 有选择多个
							JTextComponent editor = (JTextComponent) ui
									.getEditor().getEditorComponent();
							editor.setText("");
						} else if (fontWeight.equals(Constants.EN_NORMAL)
								&& fontStyle.equals(Constants.EN_NORMAL))
						{
							// 正常
							ui.setSelectedIndex(0);
						} else if (fontWeight.equals(Constants.EN_800)
								&& fontStyle.equals(Constants.EN_NORMAL))
						{
							// 粗体
							ui.setSelectedIndex(1);
						} else if (fontWeight.equals(Constants.EN_NORMAL)
								&& fontStyle.equals(Constants.EN_ITALIC))
						{
							// 斜体
							ui.setSelectedIndex(2);
						} else if (fontWeight.equals(Constants.EN_800)
								&& fontStyle.equals(Constants.EN_ITALIC))
						{
							// 粗斜体
							ui.setSelectedIndex(3);
						}
					}
				}
			}
		}
		
		if (actionCommand == ActionCommandType.DYNAMIC_ACTION) {
			for (List<Object> list : RibbonUIManager.getUIComponents().get(this.actionID).get(actionCommand)) {
				for (Object object : list) {
					if (object instanceof JComboBox) {
						JComboBox ui = (JComboBox) object;
						Object fontWeight = FontPropertyPanel.getInitialPropertise().get(Constants.PR_FONT_WEIGHT);
						Object fontStyle = FontPropertyPanel.getInitialPropertise().get(Constants.PR_FONT_STYLE);
						
						if (fontWeight == null || fontStyle == null) {
							return;
						}
						
						if (fontWeight.equals(Constants.NULLOBJECT)) {
							//有选择多个
							JTextComponent editor = (JTextComponent) ui.getEditor().getEditorComponent();
							editor.setText("");
						} else if(fontWeight.equals(Constants.EN_NORMAL) && fontStyle.equals(Constants.EN_NORMAL)){
							//正常
							ui.setSelectedIndex(0);
						} else if (fontWeight.equals(Constants.EN_800) && fontStyle.equals(Constants.EN_NORMAL)){
							//粗体
							ui.setSelectedIndex(1);
						} else if (fontWeight.equals(Constants.EN_NORMAL) && fontStyle.equals(Constants.EN_ITALIC)){
							//斜体
							ui.setSelectedIndex(2);
						}	else if (fontWeight.equals(Constants.EN_800) && fontStyle.equals(Constants.EN_ITALIC)){
							//粗斜体
							ui.setSelectedIndex(3);
						}
					}
				}
			}
		}
	}

}
