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

public class ParagraphKeepWithNextAction extends Actions {
	
	JComboBox breakBefore;
	JComboBox breakAfter;
	List<JComboBox> keepWithNext = new ArrayList<JComboBox>();
	List<JSpinner> spinnerKeep = new ArrayList<JSpinner>();
	
	private void getComponents(){
		
		if (keepWithNext.size() != 0) {
			keepWithNext.clear();
		}
		if (spinnerKeep.size() != 0) {
			spinnerKeep.clear();
		}
		
		Set<List<Object>> comSet0 = RibbonUIManager.getUIComponents().get(Paragraph.KEEP_WITH_NEXT_ACTION).get(ActionCommandType.DIALOG_ACTION);
		
		for (Iterator<List<Object>> iterator2 = comSet0.iterator(); iterator2.hasNext();) {
			List<Object> uiComponentList = (List<Object>) iterator2.next();
			for (Iterator<Object> iterator3 = uiComponentList.iterator(); iterator3.hasNext();) {
				Object uiComponents = (Object) iterator3.next();
				
				if (uiComponents instanceof JComboBox) {
					JComboBox ui = (JComboBox) uiComponents;
					this.keepWithNext.add(ui);
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
			EnumProperty line = new EnumProperty(getProperty(keepWithNext.get(0)),"");
			EnumProperty column = new EnumProperty(getProperty(keepWithNext.get(1)),"");
			EnumProperty page = new EnumProperty(getProperty(keepWithNext.get(2)),"");
			
			if (line.getEnum() == -1 || column.getEnum() == -1 || page.getEnum() == -1) {
				return;
			}
			
			KeepProperty keep = new KeepProperty(line, column, page);
			
			setFOProperty(Constants.PR_KEEP_WITH_NEXT, keep);
		}
		
		/*EnumProperty enmu = new EnumProperty(Constants.EN_ALWAYS,"");
		KeepProperty keep = new KeepProperty(enmu,enmu,enmu);
		setFOProperty(Constants.PR_KEEP_WITH_NEXT, keep);*/
	}
	
	private int getProperty(JComboBox ui){
		
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
			
			Object currnetValue =  RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_KEEP_WITH_NEXT);
			
			if (currnetValue instanceof KeepProperty) {
				KeepProperty value = (KeepProperty) currnetValue;
				
				if (value.getWithinLine().getEnum() == Constants.EN_AUTO) {
					this.keepWithNext.get(0).setSelectedIndex(0);
				} else if (value.getWithinLine().getEnum() == Constants.EN_ALWAYS) {
					this.keepWithNext.get(0).setSelectedIndex(1);
				}/* else if (value.getWithinLine().getEnum() == Constants.EN_ALWAYS) {
					this.keepTogether.get(0).setSelectedIndex(1);
				}*/
				
				if (value.getWithinColumn().getEnum() == Constants.EN_AUTO) {
					this.keepWithNext.get(1).setSelectedIndex(0);
				} else if (value.getWithinColumn().getEnum() == Constants.EN_ALWAYS) {
					this.keepWithNext.get(1).setSelectedIndex(1);
				}
				
				if (value.getWithinPage().getEnum() == Constants.EN_AUTO) {
					this.keepWithNext.get(2).setSelectedIndex(0);
				} else if (value.getWithinPage().getEnum() == Constants.EN_ALWAYS) {
					this.keepWithNext.get(2).setSelectedIndex(1);
				}
				
			}
		}
	}
	

}
