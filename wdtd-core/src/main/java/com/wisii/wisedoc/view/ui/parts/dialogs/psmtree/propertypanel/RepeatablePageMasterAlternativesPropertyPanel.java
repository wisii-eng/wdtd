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
package com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.propertypanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.model.psmnode.RepeatablePageMasterAlternativesModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.TreePanel;

/**
 * 特殊位置引用面板
 * repeatable-page-master-alternatives
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class RepeatablePageMasterAlternativesPropertyPanel extends JPanel {

	private WiseSpinner spinner;
	private JComboBox comboBox;
	
	/**
	 * Create the panel
	 */
	public RepeatablePageMasterAlternativesPropertyPanel() {
		super();
		
		JLabel maximumRepeatsLabel;
		maximumRepeatsLabel = new JLabel();
		maximumRepeatsLabel.setText("最大持续页数：");

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"无限制", "指定页数"}));

		spinner = new WiseSpinner();
		SpinnerNumberModel snm = new SpinnerNumberModel(0, 0, null, 1);
		spinner.setModel(snm);
		
		MaxRepeatsAction mra = new MaxRepeatsAction();
		spinner.addActionListener(mra);
		comboBox.addActionListener(mra);
		mra.setDefaultState();
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(maximumRepeatsLabel)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(maximumRepeatsLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}
	
	public void setMaxRepeat(Object repeatNumber){
		
		if (repeatNumber == null) {
			comboBox.setSelectedIndex(0);
			spinner.setEnabled(false);
		}
		
		if (repeatNumber instanceof EnumNumber) {
			EnumNumber value = (EnumNumber) repeatNumber;
			if (value.getEnum() == Constants.EN_NO_LIMIT) {
				comboBox.setSelectedIndex(0);
				spinner.setEnabled(false);
			} else if (value.getEnum() == -1 /*&& value.getValue() != 0*/){
				comboBox.setSelectedIndex(1);
				spinner.setEnabled(true);
				spinner.setValue((Integer)value.getNumber());
			}
		}
		
		if (repeatNumber instanceof Integer) {
			Integer value = (Integer) repeatNumber;
			if (value == -1) {
				comboBox.setSelectedIndex(0);
				spinner.setEnabled(false);
			} else {
				comboBox.setSelectedIndex(1);
				spinner.setEnabled(true);
				spinner.setValue(value);
			}
		}
	}
	
	
	class MaxRepeatsAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (comboBox.getSelectedIndex() == 0) {
				spinner.setEnabled(false);
				setValue(new EnumNumber(Constants.EN_NO_LIMIT, null));
			} else if (comboBox.getSelectedIndex() == 1){
				spinner.setEnabled(true);
				setValue(new EnumNumber(-1, (Integer)spinner.getValue()));
			}
		}
		
		private void setValue(Object value){
			TreePath treePath = TreePanel.getTree().getSelectionPath();
			if (treePath != null) {
				if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
					Object objectModel = treeNode.getUserObject();
					if (objectModel instanceof RepeatablePageMasterAlternativesModel) {
						RepeatablePageMasterAlternativesModel model = (RepeatablePageMasterAlternativesModel) objectModel;
						model.setMaximumRepeats(value);
					}
				}
			}
		}
		
		public void setDefaultState(){
			
		}
	}
}
