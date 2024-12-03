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
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.MultiPageSequenceModel;

/**
 * 第一版有关页布局序列和正常页布局整体设置的面板
 * @author 闫舒寰
 * @version 1.0 2009/01/12
 */
public class CustomLayoutDialog extends AbstractWisedocDialog {
	
	private static CustomLayoutDialog layoutDialog;

	/**
	 * Create the dialog
	 */
	public CustomLayoutDialog() {
		super();
		
//		System.out.println(getFocusOwner());
		
		setBounds(100, 100, 1024, 800);
		
		layoutDialog = this;
		
		this.setTitle("多种页布局设置");
		
		setLayout(new BorderLayout());
		
		//page-sequence list panel
		//这个是完整page sequence属性设置加载窗口，见草图BP1的设置
		add(CustomLayoutManager.getInstance().getPageSequenceListPanel(), BorderLayout.WEST);
		add(CustomLayoutManager.getInstance().getPageSequencePropertyPanel(), BorderLayout.CENTER);
		
//		add(new PSMasterPanel());
		
		//确定、取消、帮助面板
		add(new ButtonPanel(), BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}
	
	class ButtonPanel extends JPanel {

		/**
		 * Create the panel
		 */
		public ButtonPanel() {
			super();
			
			final ButtonAction ba = new ButtonAction(ActionType.LAYOUT);
			
			final JButton yesButton = new JButton("确定");
			final JButton cancelButton = new JButton("取消");
			final JButton helpButton = new JButton("帮助");
			final JButton restoreButton = new JButton("恢复默认值");
			
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
		
		public ButtonAction(final ActionType actionType) {
			this.actionType = actionType;
		}
	
		@Override
		public void doAction(final ActionEvent e) {
			final String cmd = e.getActionCommand();
			
			if(cmd.equals("确定")){
				
				/*TreePath treePath = TreePanel.getTree().getSelectionPath();
				if (treePath != null) {
					if (treePath.getLastPathComponent() instanceof DefaultMutableTreeNode) {
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
						Object objectModel = treeNode.getUserObject();
					}
					
					DefaultMutableTreeNode psm = (DefaultMutableTreeNode) treePath.getPath()[1]; 
					//需要深度优先遍历这个节点，初始化所有经过节点
					if (psm.getUserObject() instanceof PageSequenceMasterModel) {
						PageSequenceMasterModel psmm = (PageSequenceMasterModel) psm.getUserObject();
						
						setFOProperty(Constants.PR_PAGE_SEQUENCE_MASTER, psmm.getPageSequenceMaster());
//						System.out.println(psmm.getPageSequenceMaster().getSubsequenceSpecifiers().size());
					}
				}*/
				
				
				MultiPageSequenceModel.MultiPageSeqModel.setFOProperty();
				
				layoutDialog.dispose();
			}else if(cmd.equals("取消")){
				layoutDialog.dispose();
			}else if(cmd.equals("帮助")){
//				System.out.println("help!");
				JOptionPane.showMessageDialog(null, "别着急，快轮到做帮助菜单了", "帮助菜单", JOptionPane.INFORMATION_MESSAGE);
//				borderDialog.dispose();
			}			
		}		
	}

	public static CustomLayoutDialog getLayoutDialog() {
		return layoutDialog;
	}
	
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(final String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final CustomLayoutDialog dialog = new CustomLayoutDialog();
					/*dialog.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							System.exit(0);
						}
					});*/
//					dialog.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    

}
