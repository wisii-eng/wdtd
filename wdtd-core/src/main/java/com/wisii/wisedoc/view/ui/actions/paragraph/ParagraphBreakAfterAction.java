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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

public class ParagraphBreakAfterAction extends Actions {

	
	JComboBox breakAfter;
	List<JComboBox> keepTogether = new ArrayList<JComboBox>();
	List<JComboBox> keepWithNext = new ArrayList<JComboBox>();
	List<JComboBox> keepWithPrevious = new ArrayList<JComboBox>();
	
	private void getComponents(){
		
		if (keepTogether.size() != 0) {
			keepTogether.clear();
		}
		if (keepWithNext.size() != 0) {
			keepWithNext.clear();
		}
		if (keepWithPrevious.size() != 0) {
			keepWithPrevious.clear();
		}
		
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(Paragraph.BREAK_AFTER_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		for (Iterator<List<Object>> iterator2 = comSet.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					this.breakAfter = ui;
				}
			}
		}
		
		if (RibbonUIManager.getUIComponents().get(Paragraph.KEEP_TOGETHER_ACTION) == null) {
			return;
		}
		
		Set<List<Object>> comSet0 = RibbonUIManager.getUIComponents().get(Paragraph.KEEP_TOGETHER_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		for (Iterator<List<Object>> iterator2 = comSet0.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					this.keepTogether.add(ui);
				}
			}
		}
		
		Set<List<Object>> comSet1 = RibbonUIManager.getUIComponents().get(Paragraph.KEEP_WITH_NEXT_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		for (Iterator<List<Object>> iterator2 = comSet1.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					this.keepWithNext.add(ui);
				}
			}
		}
		
		Set<List<Object>> comSet3 = RibbonUIManager.getUIComponents().get(Paragraph.KEEP_WITH_PREVIOUS_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		for (Iterator<List<Object>> iterator2 = comSet3.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					this.keepWithPrevious.add(ui);
				}
			}
		}
		
	}

	@Override
	public void doAction(ActionEvent e) {
		
		getComponents();
		if (e.getSource() instanceof JComboBox) {
			JComboBox jb = (JComboBox) e.getSource();
			if (true) {
				
				Map<Integer, Object> properties = new HashMap<Integer, Object>();
				
				if (jb.getSelectedIndex() == 0) {
//					setFOProperty(Constants.PR_BREAK_AFTER, new EnumProperty(Constants.EN_PAGE, "break after"));
					setFOProperty(Constants.PR_BREAK_AFTER, new EnumProperty(Constants.EN_AUTO, "break after"));
					
				} else if (jb.getSelectedIndex() == 1){
					
					setFOProperty(Constants.PR_BREAK_AFTER, new EnumProperty(Constants.EN_PAGE, "break after"));
					
					for (int i = 0; i < 3; i++) {
						keepTogether.get(i).setSelectedIndex(0);
						keepWithNext.get(i).setSelectedIndex(0);
						keepWithPrevious.get(i).setSelectedIndex(0);
					}
					
				} else if (jb.getSelectedIndex() == 2){
					
					setFOProperty(Constants.PR_BREAK_AFTER, new EnumProperty(Constants.EN_EVEN_PAGE, "break after"));
					
					for (int i = 0; i < 3; i++) {
						keepTogether.get(i).setSelectedIndex(0);
						keepWithNext.get(i).setSelectedIndex(0);
						keepWithPrevious.get(i).setSelectedIndex(0);
					}
					
				} else if (jb.getSelectedIndex() == 3){
					
					setFOProperty(Constants.PR_BREAK_AFTER, new EnumProperty(Constants.EN_ODD_PAGE, "break after"));
					
					for (int i = 0; i < 3; i++) {
						keepTogether.get(i).setSelectedIndex(0);
						keepWithNext.get(i).setSelectedIndex(0);
						keepWithPrevious.get(i).setSelectedIndex(0);
					}
					
				} else if (jb.getSelectedIndex() == 4){
					
					setFOProperty(Constants.PR_BREAK_AFTER, new EnumProperty(Constants.EN_COLUMN, "break after"));
					
					for (int i = 0; i < 3; i++) {
						keepTogether.get(i).setSelectedIndex(0);
						keepWithNext.get(i).setSelectedIndex(0);
						keepWithPrevious.get(i).setSelectedIndex(0);
					}
					
				}	
			}					
		}
		
	}
	
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(actionCommand);
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			getComponents();
			
			Object currnetValue =  RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_BREAK_AFTER);
			
			if (currnetValue instanceof EnumProperty) {
				EnumProperty value = (EnumProperty) currnetValue;
				if (value.getEnum() == Constants.EN_AUTO) {
					breakAfter.setSelectedIndex(0);
				} else if (value.getEnum() == Constants.EN_PAGE){
					breakAfter.setSelectedIndex(1);
				} else if (value.getEnum() == Constants.EN_EVEN_PAGE){
					breakAfter.setSelectedIndex(2);
				} else if (value.getEnum() == Constants.EN_ODD_PAGE){
					breakAfter.setSelectedIndex(3);
				} else if (value.getEnum() == Constants.EN_COLUMN){
					breakAfter.setSelectedIndex(4);
				}
			}
			
		}
		
	}
	
}
