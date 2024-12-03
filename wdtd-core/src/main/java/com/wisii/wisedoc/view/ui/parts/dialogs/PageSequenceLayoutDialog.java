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
package com.wisii.wisedoc.view.ui.parts.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.psmnode.PageSequenceMasterModel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.PSMasterPanel;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmtree.TreePanel;
import com.wisii.wisedoc.view.ui.text.UiText;
import com.wisii.wisedoc.view.ui.update.RegionDocumentManager;

/**
 * 页布局序列属性设置对话框
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class PageSequenceLayoutDialog extends AbstractWisedocDialog {
	
	/**
	 * 设置页布局序列时所需要指定的类型
	 * @author 闫舒寰
	 *
	 */
	public enum PSMLayoutType {
		setFO, //暂时不用
		addLayout,//添加页布局序列
		editLayout; //编辑当前页布局序列
	}
	
	PSMLayoutType layoutType;
	
	public static PageSequenceLayoutDialog pageSequenceLayoutDialog;
	
	public PageSequenceLayoutDialog(final PSMLayoutType actionType){
		super();
		
		layoutType = actionType;
		
		setBounds(100, 100, 1024, 800);
		
		pageSequenceLayoutDialog = this;
		
		this.setTitle("页布局序列设置");
		setLayout(new BorderLayout());
		
		add(new PSMasterPanel(), BorderLayout.CENTER);
		add(new ButtonPanel(), BorderLayout.SOUTH);
		
		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}
	
	class ButtonPanel extends JPanel {

		/**
		 * Create the panel
		 */
		public ButtonPanel() {
			super();
			
			final ButtonAction ba = new ButtonAction();
			
			final JButton yesButton = new JButton(UiText.DIALOG_OK);
			final JButton cancelButton = new JButton(UiText.DIALOG_CANCEL);
			final JButton helpButton = new JButton(UiText.DIALOG_HELP);
			final JButton restoreButton = new JButton(UiText.DIALOG_DEFAULT);
			
			yesButton.addActionListener(ba);
			cancelButton.addActionListener(ba);
			helpButton.addActionListener(ba);
						
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addContainerGap(32, Short.MAX_VALUE)
						.addComponent(yesButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(cancelButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(helpButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(restoreButton)
						.addContainerGap())					
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()					
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(restoreButton)
							.addComponent(helpButton)
							.addComponent(cancelButton)
							.addComponent(yesButton))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			/* 添加ESC、ENTER健处理 */
			setOkButton(yesButton);
			setCancelButton(cancelButton);
			
			setLayout(groupLayout);
		}
	}
	
	class ButtonAction extends Actions{
		
		public ButtonAction() {
			this.actionType = ActionType.LAYOUT;
		}
	
		@Override
		public void doAction(final ActionEvent e) {
			final String cmd = e.getActionCommand();
			
			if(cmd.equals(UiText.DIALOG_OK)){
				
				if (layoutType == PSMLayoutType.setFO) {
//					setFOProperty(Constants.PR_SIMPLE_PAGE_MASTER, SinglePagelayoutModel.SinglePageLayoutModel.getSinglePageLayoutModel().getSimplePageMaster());
				} else if (layoutType == PSMLayoutType.addLayout) {
					final TreePath treePath = TreePanel.getTree().getSelectionPath();
					if (treePath != null) {
						final DefaultMutableTreeNode psm = (DefaultMutableTreeNode) treePath.getPath()[1]; 
						//需要深度优先遍历这个节点，初始化所有经过节点
						if (psm.getUserObject() instanceof PageSequenceMasterModel) {
							final PageSequenceMasterModel psmm = (PageSequenceMasterModel) psm.getUserObject();
							setFOProperty(Constants.PR_PAGE_SEQUENCE_MASTER, psmm.getPageSequenceMaster());
						}
					}
					
					//for test
					RegionDocumentManager.Instance.setSPMRegionContent();
				} else if (layoutType == PSMLayoutType.editLayout) {
					final TreePath treePath = TreePanel.getTree().getSelectionPath();
					if (treePath != null) {
						final DefaultMutableTreeNode psm = (DefaultMutableTreeNode) treePath.getPath()[1]; 
						//需要深度优先遍历这个节点，初始化所有经过节点
						if (psm.getUserObject() instanceof PageSequenceMasterModel) {
							final PageSequenceMasterModel psmm = (PageSequenceMasterModel) psm.getUserObject();
							setFOProperty(Constants.PR_PAGE_SEQUENCE_MASTER, psmm.getPageSequenceMaster());
						}
					}
					
					//for test
					RegionDocumentManager.Instance.setSPMRegionContent();
				}
				
				pageSequenceLayoutDialog.dispose();
			}else if(cmd.equals(UiText.DIALOG_CANCEL)){
				pageSequenceLayoutDialog.dispose();
			}else if(cmd.equals(UiText.DIALOG_HELP)){
				JOptionPane.showMessageDialog(null, "别着急，快轮到做帮助菜单了", "帮助菜单", JOptionPane.INFORMATION_MESSAGE);
//				borderDialog.dispose();
			}			
		}		
	}
}
