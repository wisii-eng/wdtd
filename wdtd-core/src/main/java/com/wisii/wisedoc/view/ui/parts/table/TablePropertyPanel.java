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
package com.wisii.wisedoc.view.ui.parts.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.parts.AbstractWisePropertyPanel;
import com.wisii.wisedoc.view.ui.parts.common.BackGroundPanel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 表格属性设置对话框
 * @author 闫舒寰
 * @version 1.0 2009/03/17
 */
public class TablePropertyPanel extends AbstractWisePropertyPanel {
	
	JTabbedPane tablepane;
	BorderPanel borderPanel;
	BackGroundPanel backgroundpanel;
	
	PropertyPanel propertyPanel;
	
	ActionType propertyType = ActionType.TABLE;

	@Override
	protected void initialPanel() {
		setPreferredSize(new Dimension(512, 400));
		setLayout(new BorderLayout());
		
		tablepane = new JTabbedPane();
		
		propertyPanel = new PropertyPanel();
		tablepane.add(UiText.BLOCK_CONTAINER_PROPERTY, propertyPanel);

		borderPanel = new BorderPanel(getInitialProMap(), propertyType);
		tablepane.add(UiText.BORDER, borderPanel);
		
		backgroundpanel = new BackGroundPanel(getInitialProMap(), propertyType);
		tablepane.add(UiText.SHADING, backgroundpanel);
		
		add(tablepane, BorderLayout.CENTER);
	}
	
	@Override
	protected Map<Integer, Object> getPanelProperties() {
		return getChangedMap();
	}

	@Override
	public boolean isValidate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Map<Integer, Object> getChangedMap() {
		Map<Integer, Object> borderattris = null;
		if (borderPanel != null) {
			borderattris = borderPanel.getProperties();
		}
		Map<Integer, Object> bgattris = backgroundpanel
				.getProperties();
		if (borderattris != null || bgattris != null) {
			Map<Integer, Object> attris = new HashMap<Integer, Object>();
			if (borderattris != null) {
				attris.putAll(borderattris);
			}
			if (bgattris != null) {
				attris.putAll(bgattris);
			}
			return attris;
		}
		
		return new HashMap<Integer, Object>();
	}

	private class PropertyPanel extends JPanel {

//		private JComboBox comboBox;
		private FixedLengthSpinner spinner;
		
		JCheckBox checkBox;
		
		/**
		 * Create the panel
		 */
		public PropertyPanel() {
			super();

			JLabel label;
			label = new JLabel();
			label.setText(UiText.TABLE_WIDTH_LABEL);

			spinner = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));

			JLabel label_1;
			label_1 = new JLabel();
			label_1.setText(UiText.TABLE_ALIGN);

//			comboBox = new JComboBox(new DefaultComboBoxModel(UiText.TABLE_ALIGN_LIST));

			
			checkBox = new JCheckBox();
			checkBox.setText(UiText.TABLE_AUTO_WIDTH);
			
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(checkBox)
						.addGap(18, 18, 18)
						.addComponent(label)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(label_1)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(16, Short.MAX_VALUE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(checkBox)
							.addComponent(label_1)
//							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(88, Short.MAX_VALUE))
			);
			setLayout(groupLayout);
			//
			
			
		}
		
		private void initialAction() {
			
			TableWidthAction twa = new TableWidthAction();
			spinner.addActionListener(twa);
			twa.setDefaultState();
			
			TableLayoutAction tla = new TableLayoutAction();
			checkBox.addActionListener(tla);
			tla.setDefaultState();
		}
		
		
		public class TableWidthAction extends BaseAction {
			
			@Override
			public void doAction(ActionEvent e) {
				setFOProperty(Constants.PR_INLINE_PROGRESSION_DIMENSION, new LengthRangeProperty(spinner.getValue()));
			}
			
			public void setDefaultState() {
				FixedLength uivalue = null;
				Object value = getInitialProMap().get(Constants.PR_INLINE_PROGRESSION_DIMENSION);

				if (value instanceof LengthRangeProperty)
				{
					value = ((LengthRangeProperty) value).getOptimum(null);
					if (value instanceof FixedLength)
					{
						uivalue = (FixedLength) value;
					}
				} else if (value instanceof FixedLength)
				{
					uivalue = (FixedLength) value;
				}
				spinner.initValue(uivalue);
			}
		}
		
		public class TableLayoutAction extends BaseAction {
			@Override
			public void doAction(ActionEvent e) {
				if (checkBox.isSelected()) {
					setFOProperty(Constants.PR_TABLE_LAYOUT, new EnumProperty(Constants.EN_AUTO, ""));
				} else {
					setFOProperty(Constants.PR_TABLE_LAYOUT, new EnumProperty(Constants.EN_FIXED, ""));
				}
			}
			
			public void setDefaultState() {
				Object value = getInitialProMap().get(Constants.PR_TABLE_LAYOUT);
				if (value instanceof EnumProperty) {
					EnumProperty ep = (EnumProperty) value;
					if (ep.equals(new EnumProperty(Constants.EN_AUTO, ""))) {
						checkBox.setSelected(true);
					} else if (ep.equals(new EnumProperty(Constants.EN_FIXED, ""))) {
						checkBox.setSelected(false);
					}
				}
				
				if (value == null) {
					checkBox.setSelected(true);
				}
			}
		}
	}
}
