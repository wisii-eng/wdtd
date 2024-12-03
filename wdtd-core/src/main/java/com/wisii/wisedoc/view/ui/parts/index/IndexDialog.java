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
package com.wisii.wisedoc.view.ui.parts.index;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.ParagraphPropertyModel;
import com.wisii.wisedoc.view.ui.model.index.IndexStylesModel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.text.UiText;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 目录属性设置对话框
 * @author 闫舒寰
 * @version 1.0 2009/04/10
 */
public class IndexDialog extends AbstractWisedocDialog {
	
	BorderPanel borderPanel;
	
	public static IndexDialog dialog;
	ActionType actionType;
	JTabbedPane tabs;
	
	public IndexDialog() {
		super();
	}
	
	public IndexDialog(final ActionType actionType){
		this();
		
		this.actionType = actionType;
		dialog = this;
		this.setTitle(UiText.INDEX_SET_TITLE);
		
		setPreferredSize(new Dimension(425, 580));//800->490
		setLayout(new BorderLayout());
		
		add(new IndexPropertyPanel(), BorderLayout.CENTER);
		
		final ButtonPanel bp = new ButtonPanel();
		add(bp, BorderLayout.SOUTH);
		
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
			setCancelButton(cancelButton);
			setOkButton(yesButton);
						
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
				//TODO 需要设置到对象上
				
				final Document doc = RibbonUpdateManager.Instance.getCurrentEditPanel().getDocument();
				
				final TableContents tc = doc.getTableContents();
				
				if (tc != null) {
					final Map<Integer, Object> cProperty = IndexStylesModel.getFinalProperties();
//					System.out.println("index property: " + cProperty);
					
					tc.setAttributes(cProperty, false);
					
					final List<IndexStyles> isList = IndexStylesModel.Instance.getIndexStylesList();
					
					final List<Attributes> attList = new ArrayList<Attributes>();
					
					for (final IndexStyles ins : isList) {
						attList.add(ins.getAttributes());
					}
					
//					System.out.println("index style list: " + attList);
					
					tc.setAttribute(Constants.PR_BLOCK_REF_STYLES, attList);
					
					doc.setElementAttributes(tc, cProperty, false);
				}
				
				dialog.dispose();
			}else if(cmd.equals(UiText.DIALOG_CANCEL)){
				FontPropertyModel.clearProperty();
				ParagraphPropertyModel.clearProperty();
				dialog.dispose();
			}else if(cmd.equals(UiText.DIALOG_HELP)){
				JOptionPane.showMessageDialog(null, "别着急，快轮到做帮助菜单了", "帮助菜单", JOptionPane.INFORMATION_MESSAGE);
//				borderDialog.dispose();
			}else if(cmd.equals(UiText.DIALOG_DEFAULT)){
				//TODO 恢复默认还没有做
			}
		}
	}
}
