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
package com.wisii.wisedoc.view.ui.parts.blockcontainer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
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
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.parts.AbstractWisePropertyPanel;
import com.wisii.wisedoc.view.ui.parts.common.BackGroundPanel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 高级块容器属性对话框
 * @author 闫舒寰
 * @version 1.0 2009/03/17
 */
public class BlockContainerPropertyPanel extends AbstractWisePropertyPanel {
	
	JTabbedPane tablepane;
	BorderPanel borderPanel;
	BackGroundPanel backgroundpanel;
	
	PropertyPanel propertyPanel;
	
	ActionType propertyType = ActionType.BLOCK_CONTAINER;

	@Override
	protected void initialPanel() {
		setPreferredSize(new Dimension(512, 390));
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

		private WiseCombobox overFlowComboBox;
		private WiseCombobox alignComboBox;
		private FixedLengthSpinner widthValue;
		private FixedLengthSpinner heightValue;
		private FixedLengthSpinner leftValue;
		private FixedLengthSpinner topValue;
		private JCheckBox autoResize;
		
		/**
		 * Create the panel
		 */
		public PropertyPanel() {
			super();

			JLabel label;
			label = new JLabel();
			label.setText(UiText.BLOCK_CONTAINER_TOP);

			topValue = new FixedLengthSpinner();

			JLabel label_1;
			label_1 = new JLabel();
			label_1.setText(UiText.BLOCK_CONTAINER_LEFT);

			leftValue = new FixedLengthSpinner();

			JLabel label_2;
			label_2 = new JLabel();
			label_2.setText(UiText.BLOCK_CONTAINER_HEIGHT);

			heightValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));

			JLabel label_3;
			label_3 = new JLabel();
			label_3.setText(UiText.BLOCK_CONTAINER_WIDTH);

			widthValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));

			JLabel label_4;
			label_4 = new JLabel();
			label_4.setText(UiText.BLOCK_CONTAINER_CONTENT_ALIGN);

			alignComboBox = new WiseCombobox(new DefaultComboBoxModel(UiText.BLOCK_CONTAINER_CONTENT_ALIGN_LIST));

			JLabel label_5;
			label_5 = new JLabel();
			label_5.setText(UiText.BLOCK_CONTAINER_OVER_FLOW);

			overFlowComboBox = new WiseCombobox(new DefaultComboBoxModel(UiText.BLOCK_CONTAINER_OVER_FLOW_LIST));

			
			autoResize = new JCheckBox();
			autoResize.setText(UiText.BLOCK_CONTAINER_AUTO_RESIZE);
			
			initialActions();
			
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(autoResize)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(label_2)
									.addComponent(label))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(topValue, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
									.addComponent(heightValue, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
								.addGap(25, 25, 25)
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(label_3)
									.addComponent(label_1))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(leftValue, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
									.addComponent(widthValue, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(label_4)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(alignComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(label_5)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(overFlowComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(23, Short.MAX_VALUE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label)
							.addComponent(label_1)
							.addComponent(topValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(leftValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(23, 23, 23)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label_2)
							.addComponent(heightValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_3)
							.addComponent(widthValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(28, 28, 28)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label_4)
							.addComponent(alignComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(24, 24, 24)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label_5)
							.addComponent(overFlowComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(26, 26, 26)
						.addComponent(autoResize)
						.addContainerGap(19, Short.MAX_VALUE))
			);
			setLayout(groupLayout);
			//
		}
		
		private void initialActions() {
			BlockContainerTopPositionAction bta = new BlockContainerTopPositionAction();
			topValue.addActionListener(bta);
			bta.setDefaultState();
			
			BlockContainerLeftPositionAction bclpa = new BlockContainerLeftPositionAction();
			leftValue.addActionListener(bclpa);
			bclpa.setDefaultState();
			
			BlockContainerBPDAction bcBPD = new BlockContainerBPDAction();
			heightValue.addActionListener(bcBPD);
			bcBPD.setDefaultState();
			
			BlockContainerIPDAction bcIPD = new BlockContainerIPDAction();
			widthValue.addActionListener(bcIPD);
			bcIPD.setDefaultState();
			
			BlockContainerDisplayAlignAction bcDA = new BlockContainerDisplayAlignAction();
			alignComboBox.addActionListener(bcDA);
			bcDA.setDefaultState();
			
			BlockContainerOverFlowAction bcOF = new BlockContainerOverFlowAction();
			overFlowComboBox.addActionListener(bcOF);
			bcOF.setDefaultState();
			
			SizeByContentAction sbca = new SizeByContentAction();
			autoResize.addActionListener(sbca);
			sbca.setDefaultState();
		}
		
		public class BlockContainerTopPositionAction extends BaseAction {
			
			FixedLengthSpinner ui = topValue;
			
			int properID = Constants.PR_TOP;
			
			@Override
			public void doAction(ActionEvent e)	{
				if (e.getSource() instanceof FixedLengthSpinner)
				{
					setFOProperty(properID, ui.getValue());
				}
			}
			
			public void setDefaultState() {
				FixedLength uivalue = (FixedLength) getInitialProMap().get(properID);
				ui.initValue(uivalue);;
			}
		}
		
		public class BlockContainerLeftPositionAction extends BaseAction{
			
			FixedLengthSpinner ui = leftValue;
			
			@Override
			public void doAction(ActionEvent e)	{
				if (e.getSource() instanceof FixedLengthSpinner) {
					setFOProperty(Constants.PR_LEFT, ui.getValue());
				}
			}
			
			public void setDefaultState() {
				FixedLength uivalue = (FixedLength) getInitialProMap().get(Constants.PR_LEFT);
				ui.initValue(uivalue);;
			}
		}
		
		
		public class BlockContainerBPDAction extends BaseAction {
			
			FixedLengthSpinner ui = heightValue;
			
			@Override
			public void doAction(ActionEvent e)	{
				if (e.getSource() instanceof FixedLengthSpinner) {
					setFOProperty(Constants.PR_BLOCK_PROGRESSION_DIMENSION,
							new LengthRangeProperty(ui.getValue()));
				}
			}
			
			public void setDefaultState() {
				FixedLength uivalue = null;
				Object value = getInitialProMap().get(Constants.PR_BLOCK_PROGRESSION_DIMENSION);

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
				ui.initValue(uivalue);
			}
		}
		
		
		
		public class BlockContainerIPDAction extends BaseAction {
			
			FixedLengthSpinner ui = widthValue;
			
			int properID = Constants.PR_INLINE_PROGRESSION_DIMENSION;
			
			@Override
			public void doAction(ActionEvent e)	{
				if (e.getSource() instanceof FixedLengthSpinner) {
					setFOProperty(properID,
							new LengthRangeProperty(ui.getValue()));
				}
			}
			
			public void setDefaultState() {
				FixedLength uivalue = null;
				Object value = getInitialProMap().get(properID);

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
				ui.initValue(uivalue);
			}
		}
		
		public class BlockContainerDisplayAlignAction extends BaseAction {
			
			WiseCombobox ui = alignComboBox;
			
			int properID = Constants.PR_DISPLAY_ALIGN;
			
			@Override
			public void doAction(ActionEvent e)	{
				if (e.getSource() instanceof FixedLengthSpinner)
				{
					setProperty(ui.getSelectedIndex());
				}
			}
			
			private void setProperty(int index){
				switch (index) {
				case 0:
					//letter
					setFOProperty(Constants.PR_DISPLAY_ALIGN, new EnumProperty(Constants.EN_TOP, ""));
					break;
				case 1:
					//Tabloid
					setFOProperty(Constants.PR_DISPLAY_ALIGN, new EnumProperty(Constants.EN_CENTER, ""));
					break;
				case 2:
					//Ledger
					setFOProperty(Constants.PR_DISPLAY_ALIGN, new EnumProperty(Constants.EN_AFTER, ""));
					break;
					
				default:
					break;
				}
			}
			
			public void setDefaultState() {
				Object value =  getInitialProMap().get(properID);
				
				if (value == null) {
					ui.initIndex(0);
					return;
				}
				
				if (value instanceof EnumProperty) {
					EnumProperty trValue = (EnumProperty) value;
					
					if (value.equals(Constants.NULLOBJECT)) {
						//none
					} else {
						if (trValue.equals(new EnumProperty(Constants.EN_TOP, ""))) {
							ui.initIndex(0);
						} else if (trValue.equals(new EnumProperty(Constants.EN_CENTER, ""))){
							ui.initIndex(1);
						} else if (trValue.equals(new EnumProperty(Constants.EN_AFTER, ""))){
							ui.initIndex(2);
						}
					}
				}
			}
		}
		
		public class BlockContainerOverFlowAction extends BaseAction {
			
			WiseCombobox ui = overFlowComboBox;
			
			int properID = Constants.PR_OVERFLOW;
			
			@Override
			public void doAction(ActionEvent e)	{
				if (e.getSource() instanceof FixedLengthSpinner)
				{
					setProperty(ui.getSelectedIndex());
				}
			}
			
			private void setProperty(int index){
				switch (index) {
				case 0:
					//letter
					setFOProperty(Constants.PR_DISPLAY_ALIGN, new EnumProperty(Constants.EN_VISIBLE, ""));
					break;
				case 1:
					//Tabloid
					setFOProperty(Constants.PR_DISPLAY_ALIGN, new EnumProperty(Constants.EN_HIDDEN, ""));
					break;
					
				default:
					setFOProperty(Constants.PR_OVERFLOW, new EnumProperty(Constants.EN_AUTO, ""));
					break;
				}
			}
			
			public void setDefaultState() {
				
				Object value =  getInitialProMap().get(properID);
				if (value != null) {
					if (value.equals(Constants.NULLOBJECT)) {
						//none
					} else {
						if (value.equals(new EnumProperty(Constants.EN_HIDDEN, ""))) {
							ui.initIndex(0);
						} else if (value.equals(new EnumProperty(Constants.EN_HIDDEN, ""))){
							ui.initIndex(1);
						}
					}
				} else {
					ui.initIndex(0);
				}
			}
		}
		
		public class SizeByContentAction extends BaseAction {
			
			JCheckBox ui = autoResize;
			
			int properID = Constants.PR_BLOCK_PROGRESSION_DIMENSION;
			
			@Override
			public void doAction(ActionEvent e)	{
				if (e.getSource() instanceof FixedLengthSpinner) {
					setFOProperty(properID, null);
				}
			}
			
			public void setDefaultState() {
				ui.setSelected(false);
			}
			
			@Override
			public boolean isAvailable() {
				if (!super.isAvailable()) {
					return false;
				}
				Map<Integer, Object> attmap = getInitialProMap();
				EnumProperty posenum = null;
				if (attmap != null) {
					posenum = (EnumProperty) attmap.get(Constants.PR_ABSOLUTE_POSITION);
				}
				// 如果是绝对定位，则不能设置大小随内容变化而变化。
				if (posenum == null) {
					posenum = (EnumProperty) InitialManager.getInitialValue(
							Constants.PR_ABSOLUTE_POSITION, null);
				}
				if (posenum != null && posenum.getEnum() == Constants.EN_ABSOLUTE) {
					return false;
				} else {
					return true;
				}
			}
		
		}
		
		
	}

	
}
