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
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.parts.text.FontPanel;
import com.wisii.wisedoc.view.ui.parts.text.FontPositionPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 文字属性详细设置对话框
 * @author 闫舒寰
 * @version 1.0 2009/01/16
 */
public class TextAllPropertiesDialog extends AbstractWisedocDialog {
	
	BorderPanel borderPanel;
	
	public static TextAllPropertiesDialog textAllPropertiesDialog;
	ActionType actionType;
	JTabbedPane tabs;
	
	public TextAllPropertiesDialog() {
		super();
	}
	
	public TextAllPropertiesDialog(final ActionType actionType){
		this();
		
		this.actionType = actionType;
		textAllPropertiesDialog = this;
		this.setTitle(UiText.FONT_DIALOG_TITLE);
		
		setPreferredSize(new Dimension(425, 430));//800->490
		setLayout(new BorderLayout());
		
		tabs = new JTabbedPane();
		tabs.addTab(UiText.FONT_DIALOG_TAB_TITLE_FONT, new FontPanel());
		tabs.addTab(UiText.FONT_DIALOG_TAB_TITLE_POSITION, new FontPositionPanel());
		/* 【删除：START】by 李晓光2009-1-6 */
		/*borderPanel = new BorderPanel();
		tabs.addTab("边框设置", borderPanel);*/
		/* 【删除：END】by 李晓光2009-1-6 */
		
//		tabs.addTab("背景设置", null);
//		tabs.addTab("文字超链接", null);
		
		final ButtonPanel bp = new ButtonPanel();
		
		add(tabs, BorderLayout.CENTER);
		add(bp, BorderLayout.SOUTH);
		/*add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.NORTH);*/
		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}
	
	//对话框下面的确定按钮面板
	private class ButtonPanel extends JPanel {
		
		public ButtonPanel() {
			super();
			
			final ButtonAction ba = new ButtonAction(actionType);
			
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
	
	//确定动作
	class ButtonAction extends Actions{
		
		public ButtonAction(final ActionType actionType) {
			this.actionType = actionType;
		}
	
		@Override
		public void doAction(final ActionEvent e) {
			final String cmd = e.getActionCommand();
			
			if(cmd.equals(UiText.DIALOG_OK)){
				setFOProperties(FontPropertyModel.getFinalProperties());
				textAllPropertiesDialog.dispose();
			}else if(cmd.equals(UiText.DIALOG_CANCEL)){
				FontPropertyModel.clearProperty();
				textAllPropertiesDialog.dispose();
			}else if(cmd.equals(UiText.DIALOG_HELP)){
				JOptionPane.showMessageDialog(null, "别着急，快轮到做帮助菜单了", "帮助菜单", JOptionPane.INFORMATION_MESSAGE);
//				borderDialog.dispose();
			}else if(cmd.equals(UiText.DIALOG_DEFAULT)){
				//TODO 恢复默认还没有做
			}
		}
	}
}
