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
import javax.swing.JCheckBox;
import org.jvnet.flamingo.common.JCommandToggleButton;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.parts.text.FontPropertyPanel;

/**
 * 
 * 下划线动作
 * 
 * @author	闫舒寰
 * @version	0.2 2008/10/23
 */
public class FontUnderlineAction extends Actions {

	//下划线的8421码的二进制形式
	private final int underlineIndicator = 0x1;
	private final int normalIndicator = ~0x1; //0xFFFFFFFE
	
	/**
	 * TODO
	 * 1、多种线型的下划线的设置
	 * 
	 */
	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof JCheckBox) {
			JCheckBox jc = (JCheckBox) e.getSource();
			
			
			int currentValue = 0;
			Object value = FontPropertyModel.getCurrentProperty(Constants.PR_TEXT_DECORATION);
			if (value != null) {
				if (value instanceof Integer) {
					Integer v = (Integer) value;
					currentValue = v;
				}
			}
			
			if (jc.isSelected()) {
				FontPropertyModel.setFOProperty(Constants.PR_TEXT_DECORATION, currentValue | underlineIndicator);
			} else {
				FontPropertyModel.setFOProperty(Constants.PR_TEXT_DECORATION, currentValue & normalIndicator);
			}
		}
		
		if (e.getSource() instanceof JCommandToggleButton) {
			JCommandToggleButton ui =  (JCommandToggleButton) e.getSource();
			
			int currentValue = 0;
			
			Object value = RibbonUIModel.getReadCompletePropertiesByType().get(this.actionType).get(Constants.PR_TEXT_DECORATION);
			
			if (value instanceof Integer) {
				Integer v = (Integer) value;
				currentValue = v;
			}
			
			if (ui.getActionModel().isSelected()) {
				setFOProperty(Constants.PR_TEXT_DECORATION, currentValue | underlineIndicator);
			} else {
				setFOProperty(Constants.PR_TEXT_DECORATION, currentValue & normalIndicator);
			}
		}
	}
	
	@Override
	public void uiStateChange(UIStateChangeEvent evt) {
		if (hasPropertyKey(Constants.PR_TEXT_DECORATION)) {
			if (uiComponent instanceof JCommandToggleButton) {
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				int currentValue = 0;
				
				Object value = evt.getNewValue();
				
				if (value instanceof Integer) {
					Integer v = (Integer) value;
					currentValue = v;
				}
				
				if ((currentValue & underlineIndicator) == 1) {
					ui.getActionModel().setSelected(true);
//					ui.getActionModel().setPressed(true);
				} else {
//					ui.getActionModel().setPressed(false);
					ui.getActionModel().setSelected(false);
				}
			}
		}
	}
	
	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		
		if (evt.hasPropertyKey(Constants.PR_TEXT_DECORATION)) {
			if (uiComponent instanceof JCommandToggleButton) {
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				ui.getActionModel().setSelected(false);
			}
		}
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		if (actionCommand == ActionCommandType.RIBBON_ACTION) {
			if (uiComponent != null) {
				if (uiComponent instanceof JCommandToggleButton) {
					JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
					ui.getActionModel().setSelected(false);
				}
			}
		}
		
		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			 for (List<Object> list : RibbonUIManager.getUIComponents().get(this.actionID).get(actionCommand)) {
				for (Object object : list) {
					if (object instanceof JCheckBox) {
						JCheckBox ui = (JCheckBox) object;
						Object value = InitialManager.getInitialValue(Constants.PR_TEXT_DECORATION, null);
						Map<Integer, Object> atts = RibbonUIModel
								.getCurrentPropertiesByType().get(
										this.actionType);
						if (atts != null&&atts.containsKey(Constants.PR_TEXT_DECORATION))
						{
							value = atts.get(Constants.PR_TEXT_DECORATION);
						}
						if (value.equals(Constants.NULLOBJECT)) {
							ui.setSelected(false);
						} else {
							int currentValue = 0;
							
							if (value instanceof Integer) {
								Integer v = (Integer) value;
								currentValue = v;
							}
							
							if ((currentValue & underlineIndicator) == 1) {
								ui.setSelected(true);
							} else {
								ui.setSelected(false);
							}
						}
					}
				}
			}
		}
		
		if (actionCommand == ActionCommandType.DYNAMIC_ACTION) {
			 for (List<Object> list : RibbonUIManager.getUIComponents().get(this.actionID).get(actionCommand)) {
				for (Object object : list) {
					if (object instanceof JCheckBox) {
						JCheckBox ui = (JCheckBox) object;
						Object value =  FontPropertyPanel.getInitialPropertise().get(Constants.PR_TEXT_DECORATION);
						if (value.equals(Constants.NULLOBJECT)) {
							ui.setSelected(false);
						} else {
							int currentValue = 0;
							
							if (value instanceof Integer) {
								Integer v = (Integer) value;
								currentValue = v;
							}
							
							if ((currentValue & underlineIndicator) == 1) {
								ui.setSelected(true);
							} else {
								ui.setSelected(false);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean isAvailable() {
		return super.isAvailable();
	}
	
}
	
	


