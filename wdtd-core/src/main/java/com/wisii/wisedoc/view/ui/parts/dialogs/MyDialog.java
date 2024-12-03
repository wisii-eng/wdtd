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

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.parts.pagelayout.PageSet;

public class MyDialog extends AbstractWisedocDialog{
	
	MyDialog md;
	
	public MyDialog() {
		
		super();
		
		md = this;
		
		setSize(800, 600);
		setLayout(new BorderLayout());
		
		JTabbedPane tabs = new JTabbedPane();
		tabs.add("页面设置", new PageSet());
		tabs.add("other", null);
		
		ButtonPanel bp = new ButtonPanel();	
		
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
			
			ButtonAction ba = new ButtonAction();
			
			JButton yesButton = new JButton("确定");
			JButton cancelButton = new JButton("取消");
			JButton helpButton = new JButton("帮助");
			JButton restoreButton = new JButton("恢复默认值");
			
			yesButton.addActionListener(ba);
			cancelButton.addActionListener(ba);
			helpButton.addActionListener(ba);
						
			final GroupLayout groupLayout = new GroupLayout((JComponent) this);
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

			setLayout(groupLayout);
		}
	}
	
	class ButtonAction extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			if(cmd.equals("确定")){
				
				md.dispose();
			}else if(cmd.equals("取消")){
				md.dispose();
			}else if(cmd.equals("帮助")){
				System.out.println("help!");
				md.dispose();
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		new MyDialog();
	}

}




