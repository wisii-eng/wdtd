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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JSpinner;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

public class ParagraphKeepTogetherAction extends Actions {
	
	JComboBox breakBefore;
	JComboBox breakAfter;
	List<JComboBox> keepTogether = new ArrayList<JComboBox>();
	List<JSpinner> spinnerKeep = new ArrayList<JSpinner>();
	
	private void getComponents(){
		
		if (keepTogether.size() != 0) {
			keepTogether.clear();
		}
		if (spinnerKeep.size() != 0) {
			spinnerKeep.clear();
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
				
				if (uiComponents instanceof JSpinner) {
					JSpinner ui = (JSpinner) uiComponents;
					this.spinnerKeep.add(ui);
					ui.setEnabled(false);
				}
			}
		}
		
		if (RibbonUIManager.getUIComponents().get(Paragraph.BREAK_BEFORE_ACTION) == null) {
			return;
		}
		
		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(Paragraph.BREAK_BEFORE_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		for (Iterator<List<Object>> iterator2 = comSet.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					this.breakBefore = ui;
				}
			}
		}
		
		if (RibbonUIManager.getUIComponents().get(Paragraph.BREAK_AFTER_ACTION) == null) {
			return;
		}
		
		Set<List<Object>> comSet1 = RibbonUIManager.getUIComponents().get(Paragraph.BREAK_AFTER_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		for (Iterator<List<Object>> iterator2 = comSet1.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					this.breakAfter = ui;
				}
			}
		}
	}
	

	@Override
	public void doAction(ActionEvent e) {
		
		if (e.getSource() instanceof JComboBox) {
			
			getComponents();
			EnumProperty line = new EnumProperty(getProperty(keepTogether.get(0)),"");
			EnumProperty column = new EnumProperty(getProperty(keepTogether.get(1)),"");
			EnumProperty page = new EnumProperty(getProperty(keepTogether.get(2)),"");
			
//			System.err.println(line.getEnum() + "; " + column.getEnum() + ";" + page.getEnum());
			if (line.getEnum() == -1 || column.getEnum() == -1 || page.getEnum() == -1) {
				return;
			}
			
			KeepProperty keep = new KeepProperty(line, column, page);
			
			setFOProperty(Constants.PR_KEEP_TOGETHER, keep);
		}
		
		/*if (e.getSource() instanceof JSpinner) {
			getComponents();
			JSpinner ui = (JSpinner) e.getSource();
			
		}*/
		
	}
	
	private int getProperty(JComboBox ui){
		/*switch (ui.getSelectedIndex()) {
		case 0:
			spinnerKeep.get(keepTogether.indexOf(ui)).setEnabled(false);
			return Constants.EN_AUTO;
//			break;
		case 1:
			spinnerKeep.get(keepTogether.indexOf(ui)).setEnabled(false);
			return Constants.EN_ALWAYS;
//			break;
		case 2:
//			spinnerKeep.get(keepTogether.indexOf(ui)).setEnabled(true);
//			EnumNumber en = new EnumNumber(-1, );
//			return (Integer)spinnerKeep.get(keepTogether.indexOf(ui)).getValue();
			return -1;
//			break;

//		default:
//			return -1;
//			break;
		}*/
		
		if (ui.getSelectedIndex() == 0) {
			return Constants.EN_AUTO;
		} else if (ui.getSelectedIndex() == 1) {
			this.breakAfter.setSelectedIndex(0);
			this.breakBefore.setSelectedIndex(0);
			return Constants.EN_ALWAYS;
		} else {
			return -1;
		}
		
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(actionCommand);
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			getComponents();
			
			for (int i = 0; i < 3; i++) {
				spinnerKeep.get(i).setEnabled(false);
			}
			
			Object currnetValue =  RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_KEEP_TOGETHER);
			
			if (currnetValue instanceof KeepProperty) {
				KeepProperty value = (KeepProperty) currnetValue;
				
				if (value.getWithinLine().getEnum() == Constants.EN_AUTO) {
					this.keepTogether.get(0).setSelectedIndex(0);
				} else if (value.getWithinLine().getEnum() == Constants.EN_ALWAYS) {
					this.keepTogether.get(0).setSelectedIndex(1);
				}/* else if (value.getWithinLine().getEnum() == Constants.EN_ALWAYS) {
					this.keepTogether.get(0).setSelectedIndex(1);
				}*/
				
				if (value.getWithinColumn().getEnum() == Constants.EN_AUTO) {
					this.keepTogether.get(1).setSelectedIndex(0);
				} else if (value.getWithinColumn().getEnum() == Constants.EN_ALWAYS) {
					this.keepTogether.get(1).setSelectedIndex(1);
				}
				
				if (value.getWithinPage().getEnum() == Constants.EN_AUTO) {
					this.keepTogether.get(2).setSelectedIndex(0);
				} else if (value.getWithinPage().getEnum() == Constants.EN_ALWAYS) {
					this.keepTogether.get(2).setSelectedIndex(1);
				}
				
			}
		}
	}

}
