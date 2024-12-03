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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.parts.text.FontPropertyPanel;

/**
 * 该动作类用于进行文字上下标属性的设置
 * 
 * @author 闫舒寰
 * @since 2008/09/26
 */
public class FontPositionAction extends Actions {

	private Object value;
	/* private Object measurement; */

	private ActionCommandType actionCommandType;

	private void getComponents() {

		Set<List<Object>> comSet = RibbonUIManager.getUIComponents().get(
				Font.POSITION_INPUT_ACTION)
				.get(actionCommandType);

		for (List<Object> list : comSet) {
			for (Object object : list) {
				if (object instanceof JSpinner) {
					JSpinner ui = (JSpinner) object;
					this.value = ui;
				}
			}
		}
	}

	@Override
	public void doAction(ActionEvent e) {
		getComponents();
		if (e.getSource() instanceof JComboBox) {
			JComboBox jb = (JComboBox) e.getSource();
			if (jb.getSelectedIndex() != 3) {
				((JSpinner) value).setEnabled(false);
				if (jb.getSelectedIndex() == 0) {
					// 正常情况下
					FontPropertyModel.setFOProperties(getNormalProperties());

				} else if (jb.getSelectedIndex() == 1) {
					// 上标
					FontPropertyModel.setFOProperties(getSuperProperties());
				} else if (jb.getSelectedIndex() == 2) {
					// 下标
					FontPropertyModel.setFOProperties(getSubProperties());
				}
			} else {
				((JSpinner) value).setEnabled(true);
			}
		}
	}

	private Map<Integer, Object> getSubProperties() {
		Length normalFontSize = (Length) FontPropertyModel
				.getCurrentProperty(Constants.PR_FONT_SIZE);
		Map<Integer, Object> autoProperties = null;
		if (normalFontSize instanceof FixedLength) {
			normalFontSize = new PercentLength(0.65, new LengthBase(
					normalFontSize, LengthBase.FONTSIZE));
		}
		if (normalFontSize != Constants.NULLOBJECT) {
			autoProperties = new HashMap<Integer, Object>();
			autoProperties.put(Constants.PR_BASELINE_SHIFT, new EnumLength(
					Constants.EN_SUB, null));
			autoProperties.put(Constants.PR_FONT_SIZE, normalFontSize);
		}
		return autoProperties;
	}

	private Map<Integer, Object> getSuperProperties() {
		Length normalFontSize = (Length) FontPropertyModel
				.getCurrentProperty(Constants.PR_FONT_SIZE);
		Map<Integer, Object> autoProperties = null;
		if (normalFontSize instanceof FixedLength) {
			normalFontSize = new PercentLength(0.65, new LengthBase(
					normalFontSize, LengthBase.FONTSIZE));
		}
		if (normalFontSize != Constants.NULLOBJECT) {
			autoProperties = new HashMap<Integer, Object>();
			autoProperties.put(Constants.PR_BASELINE_SHIFT, new EnumLength(
					Constants.EN_SUPER, null));
			autoProperties.put(Constants.PR_FONT_SIZE, normalFontSize);
		}
		return autoProperties;
	}

	private Map<Integer, Object> getNormalProperties() {
		Length normalFontSize = (Length) FontPropertyModel
				.getCurrentProperty(Constants.PR_FONT_SIZE);
		Map<Integer, Object> autoProperties = null;
		if (normalFontSize instanceof PercentLength) {
			PercentLength perlen = (PercentLength) normalFontSize;
			Length baselen = ((LengthBase) perlen.getBaseLength())
					.getBaseLength();
			if (baselen instanceof FixedLength) {
				normalFontSize = baselen;
			}
		}
		if (normalFontSize != Constants.NULLOBJECT) {
			autoProperties = new HashMap<Integer, Object>();
			autoProperties.put(Constants.PR_BASELINE_SHIFT, new EnumLength(
					Constants.EN_AUTO, null));
			autoProperties.put(Constants.PR_FONT_SIZE, normalFontSize);
		}
		return autoProperties;
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		
		actionCommandType = actionCommand;
		
		getComponents();

		if (actionCommand == ActionCommandType.DIALOG_ACTION) {
			Set<List<Object>> tempSet = RibbonUIManager.getUIComponents().get(
					this.actionID).get(actionCommand);
			for (List<Object> list : tempSet) {
				for (Object object : list) {
					if (object instanceof JComboBox) {
						JComboBox ui = (JComboBox) object;
						Object value = InitialManager.getInitialValue(Constants.PR_BASELINE_SHIFT, null);
						Map<Integer,Object> atts = RibbonUIModel
								.getCurrentPropertiesByType().get(
										this.actionType);
						if (atts != null&&atts.containsKey(Constants.PR_BASELINE_SHIFT))
						{
							value = atts.get(Constants.PR_BASELINE_SHIFT);
						}
						if (value.equals(Constants.NULLOBJECT)) {
							JTextComponent editor = (JTextComponent) ui
									.getEditor().getEditorComponent();
							editor.setText("");
						} else
						{
							if (value instanceof EnumLength)
							{
								int len = ((EnumLength)value).getEnum(); 
								if (len == Constants.EN_AUTO)
								{
									ui.setSelectedIndex(0);
									setComponentEnable(false);
								} else if (len == Constants.EN_SUPER)
								{
									ui.setSelectedIndex(1);
									setComponentEnable(false);
								} else if (len == Constants.EN_SUB)
								{
									ui.setSelectedIndex(2);
									setComponentEnable(false);
								} else
								{
									ui.setSelectedIndex(3);
									setComponentEnable(true);
								}
							} else
							{
								ui.setSelectedIndex(3);
								setComponentEnable(true);
							}
						}
					}
				}
			}
		}
		
		if (actionCommand == ActionCommandType.DYNAMIC_ACTION) {
			Set<List<Object>> tempSet = RibbonUIManager.getUIComponents().get(
					this.actionID).get(actionCommand);
			for (List<Object> list : tempSet) {
				for (Object object : list) {
					if (object instanceof JComboBox) {
						JComboBox ui = (JComboBox) object;
						Object value = FontPropertyPanel.getInitialPropertise().get(
										Constants.PR_BASELINE_SHIFT);
						if (value.equals(Constants.NULLOBJECT)) {
							JTextComponent editor = (JTextComponent) ui
									.getEditor().getEditorComponent();
							editor.setText("");
						} else {
							if (value.equals(new EnumLength(Constants.EN_AUTO,
									new FixedLength(0)))) {
								ui.setSelectedIndex(0);
								setComponentEnable(false);
							} else if (value.equals(new EnumLength(
									Constants.EN_SUPER, new FixedLength(0)))) {
								ui.setSelectedIndex(1);
								setComponentEnable(false);
							} else if (value.equals(new EnumLength(
									Constants.EN_SUB, new FixedLength(0)))) {
								ui.setSelectedIndex(2);
								setComponentEnable(false);
							} else {
								ui.setSelectedIndex(3);
								setComponentEnable(true);
							}
						}
					}
				}
			}
		}

	}

	private void setComponentEnable(boolean state) {
		if (value != null) {
			((JSpinner) value).setEnabled(state);
		}
	}

}
