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
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;

import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.PagePropertyModel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.LayoutPanel;
import com.wisii.wisedoc.view.ui.parts.pagelayout.PageSet;

/**
 * 页面设置详细菜单
 * 
 * @author	闫舒寰
 * @version 0.1 2008/10/24
 */
public class PageLayoutDialog extends AbstractWisedocDialog{
	
	PageLayoutDialog pageLayoutDialog;
	
	JTabbedPane tabs;
	BorderPanel borderPanel;
	
	public PageLayoutDialog() {
		
		super();
		
		pageLayoutDialog = this;
		
		setSize(800, 600);
		setLocation(300, 300);
		setLayout(new BorderLayout());
		
		tabs = new JTabbedPane();
		tabs.addTab("页面设置", new PageSet());
//		tabs.addTab("页眉", new HeaderPanel());
//		tabs.addTab("页脚", new FooterPanel());
//		tabs.addTab("背景",	null);
//		borderPanel = new BorderPanel();
//		tabs.addTab("边框", borderPanel);
		tabs.addTab("版式", new LayoutPanel());
		
		final ButtonPanel bp = new ButtonPanel();
		
		add(tabs, BorderLayout.CENTER);
		add(bp, BorderLayout.SOUTH);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
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
	
/*	class ButtonAction extends AbstractAction{

		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			if(cmd.equals("确定")){
//				System.out.println(tabs.getSelectedIndex());
				switch (tabs.getSelectedIndex()) {
				case 0:
					PageSet ps = (PageSet)tabs.getComponent(0);
//					System.out.println(ps.getComponentCount());
					break;

				default:
					break;
				}
//				pageLayoutDialog.dispose();
			}else if(cmd.equals("取消")){
				pageLayoutDialog.dispose();
			}else if(cmd.equals("帮助")){
				System.out.println("help!");
				pageLayoutDialog.dispose();
			}
			
		}
		
	}*/
	
	class ButtonAction extends Actions{
		
		public ButtonAction(final ActionType actionType) {
			this.actionType = actionType;
		}
	
		@Override
		public void doAction(final ActionEvent e) {
			final String cmd = e.getActionCommand();
			
			if(cmd.equals("确定")){
//				PagePropertyModel.addFOProperties(new BorderDialogAction(borderPanel).getProperties());
				setFOProperties(PagePropertyModel.getFinalProperties());
				
//				textAllPropertiesDialog.setVisible(false);
				pageLayoutDialog.dispose();
			}else if(cmd.equals("取消")){
				pageLayoutDialog.dispose();
			}else if(cmd.equals("帮助")){
//				System.out.println("help!");
				JOptionPane.showMessageDialog(null, "别着急，快轮到做帮助菜单了", "帮助菜单", JOptionPane.INFORMATION_MESSAGE);
//				borderDialog.dispose();
			}
		}
	}
	
	
	/**
	 * 测试用
	 * 
	 * @param args
	 */
	public static void createAndShowGUI() {
		new PageLayoutDialog();
	}
	
	public static void main(final String[] args){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
		  
	}

}
