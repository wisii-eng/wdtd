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

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.ParagraphPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

/**
 * 
 * 段落间对齐方式，设置上边对齐、下边对齐和中间对齐等
 * 
 * @author	闫舒寰
 * @since	2008/09/26
 *
 */
public class ParagraphDisplayAlignAction extends Actions {

	
	public void doAction(ActionEvent e) {

		if (e.getSource() instanceof JComboBox) {
			JComboBox jb = (JComboBox) e.getSource();
			
			if (jb.getSelectedIndex() == 0) {
				//自动
				ParagraphPropertyModel.setFOProperty(Constants.PR_DISPLAY_ALIGN, Constants.EN_AUTO);
			} else if (jb.getSelectedIndex() == 1){
				//和上边对齐
				ParagraphPropertyModel.setFOProperty(Constants.PR_DISPLAY_ALIGN, Constants.EN_BEFORE);
			} else if (jb.getSelectedIndex() == 2){
				//中间对齐
				ParagraphPropertyModel.setFOProperty(Constants.PR_DISPLAY_ALIGN, Constants.EN_CENTER);
			} else if (jb.getSelectedIndex() == 3) {
				//下边对齐
				ParagraphPropertyModel.setFOProperty(Constants.PR_DISPLAY_ALIGN, Constants.EN_AFTER);
			} 
					
//			setFOProperty(propertyType, Constants.PR_TEXT_ALIGN,jb.getSelectedItem());
		}
		
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			 Set<List<Object>> tempSet = RibbonUIManager.getUIComponents().get(this.actionID).get(actionCommand);
			 for (Iterator<List<Object>> iterator = tempSet.iterator(); iterator.hasNext();) {
				List<Object> list = (List<Object>) iterator.next();
				for (Iterator<Object> iterator2 = list.iterator(); iterator2.hasNext();) {
					Object object = (Object) iterator2.next();
					if (object instanceof JComboBox) {
						JComboBox ui = (JComboBox) object;
						Object value =  RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_DISPLAY_ALIGN);
						if (value.equals(Constants.NULLOBJECT)) {
							JTextComponent editor = (JTextComponent) ui.getEditor().getEditorComponent();
							editor.setText("");
						} else {
							if (value.equals(Constants.EN_AUTO)) {
								ui.setSelectedIndex(0);
							} else if (value.equals(Constants.EN_BEFORE)){
								ui.setSelectedIndex(1);
							} else if (value.equals(Constants.EN_CENTER)){
								ui.setSelectedIndex(2);
							} else if (value.equals(Constants.EN_AFTER)){
								ui.setSelectedIndex(3);
							}
						}
					}
				}
			}
		}
		
	}

}
