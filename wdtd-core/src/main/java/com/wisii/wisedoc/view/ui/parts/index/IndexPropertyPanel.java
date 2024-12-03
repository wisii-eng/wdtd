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
package com.wisii.wisedoc.view.ui.parts.index;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerListModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.istack.internal.NotNull;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.model.index.IndexStylesModel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 目录属性设置对话框
 * @author 闫舒寰
 * @version 1.0 2009/04/10
 */
public class IndexPropertyPanel extends JPanel {

	private WiseSpinner showlevel;
	private WiseCombobox leaderPattern;
	private WiseCombobox leaderPatternPosition;
	
	/**
	 * Create the panel
	 */
	public IndexPropertyPanel() {
		super();
		JLabel	shoulevellable = new JLabel(UiText.INDEX_SHOW_LEVEL_LABEL);
		showlevel = new WiseSpinner();

		JLabel label;
		label = new JLabel();
		label.setText(UiText.INDEX_LEADER_PATTERN_LABEL);

		String[] indexStyle = UiText.INDEX_STYLE;
		
		leaderPattern = new WiseCombobox(new DefaultComboBoxModel(indexStyle));

//		JLabel label_1;
//		label_1 = new JLabel();
//		label_1.setText(UiText.INDEX_SHOW_LEVEL_LABEL);

//		showLevel = new JSpinner();
		
		String[] indexLevel = UiText.INDEX_LEVEL;
		List<String> levelList = new ArrayList<String>();
		for (String string : indexLevel) {
			levelList.add(string);
		}
		SpinnerListModel slm = new SpinnerListModel(levelList);
		showlevel.setModel(slm);
		
		leaderPatternPosition = new WiseCombobox(new DefaultComboBoxModel(UiText.INDEX_LEADER_PATTERN_POSITION));
		
		JPanel panel;
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, UiText.INDEX_STYLE_BORDER_TITLE, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		
		panel.setLayout(new BorderLayout());
		
		panel.add(new IndexStylesPanel(), BorderLayout.CENTER);
		
		//添加监听器
//		ShowNumberAction sna = new ShowNumberAction();
//		showNumber.addActionListener(sna);
//		sna.setDefaultState();
		
//		AlignRightAction ara = new AlignRightAction();
//		alignRight.addActionListener(ara);
//		ara.setDefaultState();
//		
		ShowLevelAction sla = new ShowLevelAction();
		showlevel.addChangeListener(sla);
		sla.setDefaultState();
		
		LeaderPatternAction lpa = new LeaderPatternAction();
		leaderPattern.addActionListener(lpa);
		lpa.setDefaultState();
		
		LeaderPatternPositionAction lppa = new LeaderPatternPositionAction();
		leaderPatternPosition.addActionListener(lppa);
		lppa.setDefaultState();
		
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
							.addGap(9, 9, 9))
						.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(label)
								.addComponent(shoulevellable))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
//								.addComponent(leaderPattern, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(leaderPattern, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(leaderPatternPosition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(showlevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addGap(14, 14, 14))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(shoulevellable)
						.addComponent(showlevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19, 19, 19)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(leaderPattern, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(leaderPatternPosition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19, 19, 19)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
		//
	}
	
	/**
	 * 设置当前属性到该属性面板的Map中
	 * @param propertyId
	 * @param propertyValue
	 */
	protected void setFOProperty(@NotNull int propertyId, Object propertyValue){
		IndexStylesModel.setFOProperty(propertyId, propertyValue);
	}
	
	protected void setFOProperties(@NotNull Map<Integer, Object> properties){
		IndexStylesModel.setFOProperties(properties);
	}
	
	//显示页码动作
//	private class ShowNumberAction extends BaseAction {
//		
//		JCheckBox ui = showNumber;
//		
//		@Override
//		public void doAction(ActionEvent e) {
//			if (e.getSource() instanceof JCheckBox) {
//				setFOProperty(Constants.PR_BLOCK_REF_NUMBER, ui.isSelected());
//			}
//		}
//		
//		public void setDefaultState() {
//			Object value = IndexStylesModel.getCurrentProperty(Constants.PR_BLOCK_REF_NUMBER);
//			if (value instanceof Boolean) {
//				Boolean bValue = (Boolean) value;
//				ui.setSelected(bValue);
//			}
//		}
//	}
	
//	//页码右对齐动作
//	private class AlignRightAction extends BaseAction {
//		
//		JCheckBox ui = alignRight;
//		
//		@Override
//		public void doAction(ActionEvent e) {
//			if (e.getSource() instanceof JCheckBox) {
//				setFOProperty(Constants.PR_BLOCK_REF_RIGHT_ALIGN, ui.isSelected());
//			}
//		}
//		
//		public void setDefaultState() {
//			Object value = IndexStylesModel.getCurrentProperty(Constants.PR_BLOCK_REF_RIGHT_ALIGN);
//			if (value instanceof Boolean) {
//				Boolean bValue = (Boolean) value;
//				ui.setSelected(bValue);
//			}
//		}
//	}
	
	//显示级别动作
	private class ShowLevelAction implements ChangeListener {
		
		JSpinner ui = showlevel;
		
		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() instanceof JSpinner) {
				String[] indexLevel = UiText.INDEX_LEVEL;
				List<String> levelList = new ArrayList<String>();
				for (String string : indexLevel) {
					levelList.add(string);
				}
				setFOProperty(Constants.PR_BLOCK_REF_SHOW_LEVEL, levelList.indexOf(ui.getValue()));
			}
		}
		
		public void setDefaultState() {
			Object value = IndexStylesModel.getCurrentProperty(Constants.PR_BLOCK_REF_SHOW_LEVEL);
			
			Integer bValue = (Integer) value;
			String[] indexLevel = UiText.INDEX_LEVEL;
			List<String> levelList = new ArrayList<String>();
			for (String string : indexLevel) {
				levelList.add(string);
			}
			
			if (value instanceof Integer) {
				if (bValue < levelList.size()) {
					ui.setValue(levelList.get(bValue));
				}
			}
			
			if (value == null) {
				//默认显示4层目录
				ui.setValue(levelList.get(3));
			}
		}
	}
	
	//页码前导符动作
	private class LeaderPatternAction extends BaseAction {
		
		WiseCombobox ui = leaderPattern;
		
		@Override
		public void doAction(ActionEvent e) {
			if (e.getSource() instanceof WiseCombobox) {
				
				int index = ui.getSelectedIndex();
				switch (index) {
				case 0:
					setFOProperty(Constants.PR_LEADER_PATTERN, new EnumProperty(Constants.EN_SPACE,""));
					break;
				case 1:
					setFOProperty(Constants.PR_LEADER_PATTERN, new EnumProperty(Constants.EN_DOTS,""));
					break;
				case 2:
					setFOProperty(Constants.PR_LEADER_PATTERN, new EnumProperty(Constants.EN_RULE,""));
					break;

				default:
					break;
				}
				
				updatePosition(index);
			}
		}
		
		public void setDefaultState() {
			Object value = IndexStylesModel.getCurrentProperty(Constants.PR_LEADER_PATTERN);
			if (value instanceof EnumProperty) {
				Integer index = ((EnumProperty) value).getEnum();
				switch (index) {
				case Constants.EN_SPACE:
				{
					ui.initIndex(0);
					break;
				}
				case Constants.EN_DOTS:
				{
					ui.initIndex(1);
					break;
				}
				case Constants.EN_RULE:
				{
					ui.initIndex(2);
					break;
				}
				default:
				{
					ui.initIndex(1);
					break;
				}
				}
				updatePosition(index);
			}
			
			if (value == null) {
				//没设置的时候则用英文点
				ui.setSelectedIndex(1);
			}
		}
		
		//更新位置图标的可用性，只有当前导符没有的时候不可用
		private void updatePosition(int index) {
			if (index == 0) {
				leaderPatternPosition.setEnabled(false);
			} else {
				leaderPatternPosition.setEnabled(true);
			}
		}
	}
	
	//页码前导符显示位置动作
	private class LeaderPatternPositionAction extends BaseAction {
		
		WiseCombobox ui = leaderPatternPosition;
		
		//上
		private final Object SUPER = new PercentLength(0.4, new LengthBase(LengthBase.CUSTOM_BASE));
		//中
		private final Object CENTER = new PercentLength(0.2, new LengthBase(LengthBase.CUSTOM_BASE));
		//下
		private final Object SUB = null;
		
		@Override
		public void doAction(ActionEvent e) {
			if (e.getSource() instanceof WiseCombobox) {
				
				int index = ui.getSelectedIndex();
				
				switch (index) {
				case 0:
					setFOProperty(Constants.PR_BASELINE_SHIFT, SUPER);
					break;
				case 1:
					setFOProperty(Constants.PR_BASELINE_SHIFT, CENTER);
					break;
				case 2:
					setFOProperty(Constants.PR_BASELINE_SHIFT, SUB);
					break;

				default:
					break;
				}
			}
		}
		
		public void setDefaultState() {
			Object value = IndexStylesModel.getCurrentProperty(Constants.PR_BASELINE_SHIFT);
			if (value instanceof PercentLength) {
				PercentLength bValue = (PercentLength) value;
				
				if (bValue.equals(SUPER)) {
					ui.setSelectedIndex(0);
				}
				
				if (bValue.equals(CENTER)) {
					ui.setSelectedIndex(1);
				}
			}
			
			if (value == null) {
				ui.setSelectedIndex(2);
			}
		}
	}
}
