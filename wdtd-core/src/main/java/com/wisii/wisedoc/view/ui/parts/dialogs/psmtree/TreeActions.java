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
package com.wisii.wisedoc.view.ui.parts.dialogs.psmtree;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.view.ui.model.psmnode.PageSequenceMasterModel;
import com.wisii.wisedoc.view.ui.model.psmnode.RepeatablePageMasterAlternativesModel;

/**
 * 选择树节点的时候所执行的动作
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class TreeActions implements TreeSelectionListener{

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		
		TreePath treePath = e.getNewLeadSelectionPath();
		
		if (treePath != null) {
			if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
				Object userO = treeNode.getUserObject();
				
				//更新对话框中RibbonUI界面上的按钮
				PSMToolBar.updateUIState(userO);
				
				//更新树节点所对应的model
				PSMPanelManager.updateCurrentTreeNodeModel(userO);
				
				//更新中央属性面板
				PSMPanelManager.updatePropertyUI(userO);
				
				//开始初始化所有节点，把所有节点的关系建立到模型层中*
				Object[] temp = treePath.getPath();
				
				DefaultMutableTreeNode psm = (DefaultMutableTreeNode) temp[1]; 
				//需要深度优先遍历这个节点，初始化所有经过节点
				if (psm.getUserObject() instanceof PageSequenceMasterModel) {
					PageSequenceMasterModel psmm = (PageSequenceMasterModel) psm.getUserObject();
					psmm.addChild(psm);
				}
				
				TreeModel tm = TreePanel.getTree().getModel();
				
				for (int i = 0; i < psm.getChildCount(); i++) {
					
					Object child = tm.getChild(psm, i);
					
					if (child instanceof DefaultMutableTreeNode) {
						DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) child;
						if (childNode.getUserObject() instanceof RepeatablePageMasterAlternativesModel) {
							RepeatablePageMasterAlternativesModel rpam = (RepeatablePageMasterAlternativesModel)childNode.getUserObject();
							rpam.addChild(childNode);
						}
					}
				}
			}
		}
	}
}
