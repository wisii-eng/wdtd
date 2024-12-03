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
import com.wisii.wisedoc.view.ui.ribbon.bcb.WiseTemplateManagementPanel;

/**
 * 模板管理面板
 * @author 闫舒寰
 * @version 1.0 2009/04/02
 */
public class WiseTemplateManagementDialog extends AbstractWisedocDialog {
	
	private static WiseTemplateManagementDialog dialog;

	/**
	 * Create the dialog
	 */
	public WiseTemplateManagementDialog() {
		super();
		
//		System.out.println(getFocusOwner());
		
		setBounds(100, 100, 1024, 800);
		
		dialog = this;
		
		this.setTitle("模板管理");
		
		setLayout(new BorderLayout());
		
		add(new WiseTemplateManagementPanel(), BorderLayout.CENTER);
		
//		add(new PSMasterPanel());
		
		//确定、取消、帮助面板
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
			
			final ButtonAction ba = new ButtonAction(ActionType.LAYOUT);
			
//			JButton yesButton = new JButton("确定");
			final JButton cancelButton = new JButton("关闭");
			final JButton helpButton = new JButton("帮助");
//			JButton restoreButton = new JButton("恢复默认值");
			
//			yesButton.addActionListener(ba);
			cancelButton.addActionListener(ba);
			helpButton.addActionListener(ba);
						
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addContainerGap(32, Short.MAX_VALUE)
//						.addComponent(yesButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(cancelButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(helpButton)
//						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//						.addComponent(restoreButton)
						.addContainerGap())
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//							.addComponent(restoreButton)
							.addComponent(helpButton)
							.addComponent(cancelButton)
							/*.addComponent(yesButton)*/)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);

			/* 添加ESC、ENTER健处理 */
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
				dialog.dispose();
			}else if(cmd.equals("关闭")){
				dialog.dispose();
			}else if(cmd.equals("帮助")){
//				System.out.println("help!");
				JOptionPane.showMessageDialog(null, "别着急，快轮到做帮助菜单了", "帮助菜单", JOptionPane.INFORMATION_MESSAGE);
//				borderDialog.dispose();
			}
		}
	}

	public static WiseTemplateManagementDialog getLayoutDialog() {
		return dialog;
	}
	
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(final String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final WiseTemplateManagementDialog dialog = new WiseTemplateManagementDialog();
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
