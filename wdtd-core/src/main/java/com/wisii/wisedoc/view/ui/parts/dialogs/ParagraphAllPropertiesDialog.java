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

import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.ParagraphPropertyModel;
import com.wisii.wisedoc.view.ui.parts.paragraph.ParagraphKeepBreakPanel;
import com.wisii.wisedoc.view.ui.parts.paragraph.ParagraphPositionPanel;
import com.wisii.wisedoc.view.ui.parts.paragraph.ParagraphSpSymPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 段落详细属性设置对话框
 * @author 闫舒寰
 * @version 1.0 2009/01/16
 */
public class ParagraphAllPropertiesDialog extends AbstractWisedocDialog
{

	ParagraphAllPropertiesDialog paragraphAllPropertiesDialog;

	ActionType propertyType;

	private JTabbedPane tabs;

	public ParagraphAllPropertiesDialog()
	{
		super();
	}

	/**
	 * 创建段落属性详细设置对话框
	 * @param propertyType
	 */
	public ParagraphAllPropertiesDialog(final ActionType propertyType)
	{
		this(propertyType, 0);
	}

	/**
	 * 创建段落属性详细设置对话框
	 * @param propertyType 段落属性类型
	 * @param selectedIndex	段落详细属性所需切换面板的位置
	 */
	public ParagraphAllPropertiesDialog(final ActionType propertyType,
			final int selectedIndex)
	{
		this();

		this.propertyType = propertyType;
		paragraphAllPropertiesDialog = this;
		this.setTitle(UiText.PARAGRAPH_DIALOG_TITLE);

		setSize(566, 600);
		WisedocUtil.centerOnScreen(this);
		setLayout(new BorderLayout());

		tabs = new JTabbedPane();

		tabs.addTab(UiText.PARAGRAPH_DIALOG_POSITION_TAB, new ParagraphPositionPanel());

		tabs.addTab(UiText.PARAGRAPH_DIALOG_KEEP_BREAK_TAB, new ParagraphKeepBreakPanel());
		tabs.addTab(UiText.PARAGRAPH_DIALOG_SPY_SYM_TAB, new ParagraphSpSymPanel());

		final ButtonPanel bp = new ButtonPanel();

		add(tabs, BorderLayout.CENTER);
		add(bp, BorderLayout.SOUTH);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.NORTH);
//		pack();
		tabs.setSelectedIndex(selectedIndex);
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	//对话框下的确定取消等按钮
	class ButtonPanel extends JPanel
	{

		/**
		 * Create the panel
		 */
		public ButtonPanel()
		{
			super();

			final ButtonAction ba = new ButtonAction(propertyType);

			final JButton yesButton = new JButton(UiText.DIALOG_OK);
			final JButton cancelButton = new JButton(UiText.DIALOG_CANCEL);
			final JButton helpButton = new JButton(UiText.DIALOG_HELP);
			final JButton restoreButton = new JButton(UiText.DIALOG_DEFAULT);

			yesButton.addActionListener(ba);
			cancelButton.addActionListener(ba);
			helpButton.addActionListener(ba);

			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
					GroupLayout.Alignment.LEADING).addGroup(
					GroupLayout.Alignment.TRAILING,
					groupLayout.createSequentialGroup().addContainerGap(32,
							Short.MAX_VALUE).addComponent(yesButton)
							.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(cancelButton).addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(helpButton).addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(restoreButton).addContainerGap()));
			groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
					GroupLayout.Alignment.LEADING).addGroup(
					groupLayout.createSequentialGroup().addGap(10).addGroup(
							groupLayout.createParallelGroup(
									GroupLayout.Alignment.BASELINE)
									.addComponent(restoreButton).addComponent(
											helpButton).addComponent(
											cancelButton).addComponent(
											yesButton)).addContainerGap(
							GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

			/* 添加ESC、ENTER健处理 */
			setOkButton(yesButton);
			setCancelButton(cancelButton);
			
			setLayout(groupLayout);
		}
	}

	class ButtonAction extends Actions
	{

		public ButtonAction(final ActionType actionType)
		{
			this.actionType = actionType;
		}

		@Override
		public void doAction(final ActionEvent e)
		{
			final String cmd = e.getActionCommand();

			if (cmd.equals(UiText.DIALOG_OK))
			{
				setFOProperties(ParagraphPropertyModel.getFinalProperties());
				paragraphAllPropertiesDialog.dispose();
			} else if (cmd.equals(UiText.DIALOG_CANCEL))
			{
				ParagraphPropertyModel.clearProperty();
				paragraphAllPropertiesDialog.dispose();
			} else if (cmd.equals(UiText.DIALOG_DEFAULT))
			{
				setFOProperties(InitialManager.getInitalAttributes()
						.getAttributes());
			} else if (cmd.equals(UiText.DIALOG_HELP))
			{
				JOptionPane.showMessageDialog(null, "别着急，快轮到做帮助菜单了", "帮助菜单",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public JTabbedPane getTabs()
	{
		return tabs;
	}
}
