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
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.InitialManager;
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
 * 表格属性设置面板
 * @author 闫舒寰
 * @version 1.0 2009/03/20
 */
public class TableCellPropertyPanel extends AbstractWisePropertyPanel {
	
	JTabbedPane tablepane;
	BorderPanel borderPanel;
	BackGroundPanel backgroundpanel;
	
	PropertyPanel propertyPanel;
	
	ActionType propertyType = ActionType.TABLE_CELL;

	@Override
	protected void initialPanel() {
		setPreferredSize(new Dimension(512, 400));
		setLayout(new BorderLayout());
		
		tablepane = new JTabbedPane();
		
		propertyPanel = new PropertyPanel();
		propertyPanel.initialPanelProperty(getInitialProMap());
		tablepane.add(UiText.TABLE_CELL_PROPERTY, propertyPanel);

		borderPanel = new BorderPanel(getInitialProMap(), propertyType);
		tablepane.add(UiText.BORDER, borderPanel);
		
		backgroundpanel = new BackGroundPanel(getInitialProMap(), propertyType);
		tablepane.add(UiText.SHADING, backgroundpanel);
		
		add(tablepane, BorderLayout.CENTER);
	}
	
	@Override
	protected Map<Integer, Object> getPanelProperties() {
		Map<Integer, Object> temp = new HashMap<Integer, Object>();
		
		temp.putAll(propertyPanel.getProperties());
		
		temp.putAll(getChangedMap());
		
		return temp;
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

		private FixedLengthSpinner tcEndIn;
		private FixedLengthSpinner tcStartIn;
		private FixedLengthSpinner tcAfterIn;
		private FixedLengthSpinner tcBeforeIn;
		private WiseCombobox tcContentAlign;
		private WiseCombobox tcAutoSize;
		private FixedLengthSpinner tcWidth;
		private FixedLengthSpinner tcHeight;
		
		/**
		 * Create the panel
		 */
		public PropertyPanel() {
			super();

			JLabel label;
			label = new JLabel();
			label.setText(UiText.TABLE_CELL_HEIGHT);

			tcHeight = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));

			JLabel label_1;
			label_1 = new JLabel();
			label_1.setText(UiText.TABLE_CELL_WIDTH);

			tcWidth = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));

			JLabel label_2;
			label_2 = new JLabel();
			label_2.setText(UiText.TABLE_CELL_AUTO_SIZE);

			tcAutoSize = new WiseCombobox(new DefaultComboBoxModel(UiText.TABLE_CELL_AUTO_SIZE_LIST));

			JLabel label_3;
			label_3 = new JLabel();
			label_3.setText(UiText.TABLE_CELL_CONTENT_ALIGN);

			tcContentAlign = new WiseCombobox(new DefaultComboBoxModel(UiText.TABLE_CELL_CONTENT_ALIGN_LIST));

			JPanel panel;
			panel = new JPanel();
			panel.setBorder(new TitledBorder(UiText.TABLE_CELL_CONTENT_INDENT_TITLE));

			JLabel label_4;
			label_4 = new JLabel();
			label_4.setText(UiText.TABLE_CELL_BEFORE_INDENT);

			tcBeforeIn = new FixedLengthSpinner();

			JLabel label_5;
			label_5 = new JLabel();
			label_5.setText(UiText.TABLE_CELL_AFTER_INDENT);

			tcAfterIn = new FixedLengthSpinner();

			JLabel label_6;
			label_6 = new JLabel();
			label_6.setText(UiText.TABLE_CELL_START_INDENT);

			tcStartIn = new FixedLengthSpinner();

			JLabel label_7;
			label_7 = new JLabel();
			label_7.setText(UiText.TABLE_CELL_END_INDENT);

			tcEndIn = new FixedLengthSpinner();
			
			initialActions();
			
			final GroupLayout groupLayout_1 = new GroupLayout(panel);
			groupLayout_1.setHorizontalGroup(
				groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout_1.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING, false)
							.addGroup(groupLayout_1.createSequentialGroup()
								.addComponent(label_4)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tcBeforeIn, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout_1.createSequentialGroup()
								.addComponent(label_6)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tcStartIn, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)))
						.addGap(20, 20, 20)
						.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING, false)
							.addGroup(groupLayout_1.createSequentialGroup()
								.addComponent(label_5)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tcAfterIn, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout_1.createSequentialGroup()
								.addComponent(label_7)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tcEndIn, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(51, Short.MAX_VALUE))
			);
			groupLayout_1.setVerticalGroup(
				groupLayout_1.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout_1.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label_4)
							.addComponent(tcBeforeIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_5)
							.addComponent(tcAfterIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(groupLayout_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label_6)
							.addComponent(label_7)
							.addComponent(tcStartIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(tcEndIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(21, Short.MAX_VALUE))
			);
			panel.setLayout(groupLayout_1);
			
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
							.addComponent(panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(label)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tcHeight, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addGap(51, 51, 51)
								.addComponent(label_1)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tcWidth, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
							.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(label_2)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tcAutoSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(22, 22, 22)
								.addComponent(label_3)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tcContentAlign, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(112, Short.MAX_VALUE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label)
							.addComponent(tcHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_1)
							.addComponent(tcWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(23, 23, 23)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label_2)
							.addComponent(tcAutoSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_3)
							.addComponent(tcContentAlign, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(24, 24, 24)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(157, Short.MAX_VALUE))
			);
			setLayout(groupLayout);
			//
		}
		
		private void initialActions() {
			
			TableDisplayAlignAction tdaa = new TableDisplayAlignAction();
			tcContentAlign.addActionListener(tdaa);
			tdaa.setDefaultState();
			
//			SizeTypeAction sta = new SizeTypeAction();
//			tcAutoSize.addActionListener(sta);
		}
		
		public Map<Integer, Object> getProperties(){
			Map<Integer, Object> temp = new HashMap<Integer, Object>();
			
			temp.put(Constants.PR_INLINE_PROGRESSION_DIMENSION, tcWidth.getValue());
			temp.put(Constants.PR_BLOCK_PROGRESSION_DIMENSION, tcHeight.getValue());
			
			temp.put(Constants.PR_PADDING_BEFORE, tcBeforeIn.getValue());
			temp.put(Constants.PR_PADDING_AFTER, tcAfterIn.getValue());
			temp.put(Constants.PR_PADDING_START, tcStartIn.getValue());
			temp.put(Constants.PR_PADDING_END, tcEndIn.getValue());
			
			return temp;
		}
		
		public void initialPanelProperty(Map<Integer, Object> pro) {
			
			tcWidth.initValue(pro.get(Constants.PR_INLINE_PROGRESSION_DIMENSION));
			tcHeight.initValue(pro.get(Constants.PR_BLOCK_PROGRESSION_DIMENSION));
			
			tcBeforeIn.initValue(pro.get(Constants.PR_PADDING_BEFORE));
			tcAfterIn.initValue(pro.get(Constants.PR_PADDING_AFTER));
			tcStartIn.initValue(pro.get(Constants.PR_PADDING_START));
			tcEndIn.initValue(pro.get(Constants.PR_PADDING_END));
		}
		
		
		public class TableDisplayAlignAction extends BaseAction {
			@Override
			public void doAction(ActionEvent e) {
				setProperty(tcContentAlign.getSelectedIndex());
			}
			
			private void setProperty(int index)
			{
				switch (index)
				{
				case 0:
					// letter
					setFOProperty(Constants.PR_TEXT_ALIGN, new EnumProperty(
							Constants.EN_START, ""));
					break;
				case 1:
					// Tabloid
					setFOProperty(Constants.PR_TEXT_ALIGN, new EnumProperty(
							Constants.EN_MIDDLE, ""));
					break;
				case 2:
					// Ledger
					setFOProperty(Constants.PR_TEXT_ALIGN, new EnumProperty(
							Constants.EN_END, ""));
					break;

				default:
					break;
				}
			}
			
			public void setDefaultState() {
				Object value = getInitialProMap().get(Constants.PR_DISPLAY_ALIGN);
				if (value instanceof Integer)
				{
					int trValue = (Integer) value;

					if (value.equals(Constants.NULLOBJECT))
					{
						// none
					} else
					{
						if (trValue == Constants.EN_START)
						{
							tcContentAlign.initIndex(0);
						} else if (trValue == Constants.EN_MIDDLE)
						{
							tcContentAlign.initIndex(1);
						} else if (trValue == Constants.EN_END)
						{
							tcContentAlign.initIndex(2);
						}
					}
				}
			}
		}
	}

}
