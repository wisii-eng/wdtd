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

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.view.ui.model.psmnode.PageSequenceMasterModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.TreePanel;

/**
 * 页布局序列属性面板
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class PageSequenceMasterPropertyPanel extends JPanel {

	
	JTextField masterNameField;
	
	/**
	 * 页布局序列属性面板
	 */
	public PageSequenceMasterPropertyPanel() {
		super();

		JLabel masternameLabel;
		masternameLabel = new JLabel();
		masternameLabel.setText("页布局序列名：");

		
		masterNameField = new JTextField();
		masterNameField.setText("页布局序列： ");
		
		//用于实时更新名字
		masterNameField.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent e) {
				setProperty();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setProperty();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setProperty();
			}
			
			private void setProperty(){
				TreePath treePath = TreePanel.getTree().getSelectionPath();
				if (treePath != null) {
					if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode) {
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
						Object objectModel = treeNode.getUserObject();
						if (objectModel instanceof PageSequenceMasterModel) {
							PageSequenceMasterModel model = (PageSequenceMasterModel) objectModel;
							model.setMasterName(masterNameField.getText());
						}
					}
				}
			}
		});
		
		//用于处理回车事件
		masterNameField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				TreePath treePath = TreePanel.getTree().getSelectionPath();
				if (treePath != null) {
					if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode) {
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
						Object objectModel = treeNode.getUserObject();
						if (objectModel instanceof PageSequenceMasterModel) {
							PageSequenceMasterModel model = (PageSequenceMasterModel) objectModel;
							model.setMasterName(masterNameField.getText());
						}
					}
				}
			}
			
		});
		
//		SimplePageMasterList.updateListener(masterNameLabel);
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addComponent(masterNameField))
						.addComponent(masternameLabel))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(masternameLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(masterNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}
	
	public void setMasterName(String masterName){
		masterNameField.setText(masterName);
	}
}
