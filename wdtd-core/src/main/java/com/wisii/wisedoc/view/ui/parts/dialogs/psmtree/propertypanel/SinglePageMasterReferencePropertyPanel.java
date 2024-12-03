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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.view.ui.model.MultiPagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.psmnode.SinglePageMasterReferenceModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.TreePanel;

/**
 * 页布局序列单次引用节点属性面板
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class SinglePageMasterReferencePropertyPanel extends JPanel implements MasterReferenceLabel {
	
	JLabel referenceNameLabel;
	String referenceName;

	/**
	 * Create the panel
	 */
	public SinglePageMasterReferencePropertyPanel() {
		super();

		JLabel masterreferenceLabel;
		masterreferenceLabel = new JLabel();
		masterreferenceLabel.setText("页布局引用：");

		referenceNameLabel = new JLabel();
		referenceNameLabel.setText("单个页布局：");
		
//		SimplePageMasterList.updateListener(referenceNameLabel);
		
		ReferenceNameAction rna = new ReferenceNameAction();
		referenceNameLabel.addPropertyChangeListener(rna);
		rna.setDefaultState();
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addComponent(referenceNameLabel))
						.addComponent(masterreferenceLabel))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(masterreferenceLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(referenceNameLabel)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}

	//用来更新list中选择的对象
	@Override
	public Object getMasterReference() {
		return referenceNameLabel;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
		this.referenceNameLabel.setText(referenceName);
	}
	
	class ReferenceNameAction implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			TreePath treePath = TreePanel.getTree().getSelectionPath();
			if (treePath != null) {
				if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
					Object objectModel = treeNode.getUserObject();
					if (objectModel instanceof SinglePageMasterReferenceModel) {
						SinglePageMasterReferenceModel model = (SinglePageMasterReferenceModel) objectModel;
						model.setMasterReference(referenceNameLabel.getText());
					}
				}
			}
		}
		
		public void setDefaultState(){
			TreePath treePath = TreePanel.getTree().getSelectionPath();
			if (treePath != null) {
				if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
					Object objectModel = treeNode.getUserObject();
					if (objectModel instanceof SinglePageMasterReferenceModel) {
						SinglePageMasterReferenceModel model = (SinglePageMasterReferenceModel) objectModel;
						if (model.getMasterReference() == null) {
							SimplePageMasterModel spmm = new SimplePageMasterModel.Builder().defaultSimplePageMaster().build();
							MultiPagelayoutModel.MultiPageLayout.addSimplePageMasterModel(spmm);
							referenceNameLabel.setText(spmm.getMasterName());
						}
					}
				}
			}
		}
	}

}
