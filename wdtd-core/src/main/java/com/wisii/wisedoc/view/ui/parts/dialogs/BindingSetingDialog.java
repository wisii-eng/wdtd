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
/**
 * @BindingSetingDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.parts.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
/**
 * 类功能描述：导入新的数据结构后，文档中含有新结构中不存在的绑定节点，重复节点时的处理方式的设置对话框
 * 
 * 作者：zhangqiang 创建日期：2010-1-4
 */
public final class BindingSetingDialog extends AbstractWisedocDialog
{
	//绑定节点不存在时，是否清除相应文档元素
	public static final int BINDNODESELECT = 1;
	//是否只清除动态数据节点不存在的条件项，保留重复属性
	public static final int GROUPCONDITIONSELECT = 2;
	//是否只清除动态数据节点不存在的排序项，保留重复属性
	public static final int GROUPSORTSELECT = 4;
	//是否只清除条件项，保留显示条件属性
	public static final int CONDITIONSELECT = 8;
	//是否只清除动态样式项中的条件项，不清除动态样式项
	public static final int DYNAMICCONDITIONSELECT = 16;
	//动态样式属性中只要有绑定节点不存在，则清除整个动态样式属性
	public static final int DYNAMICSELECT = 32;
	private BindingSetingPanel setpanel = new BindingSetingPanel();
	private JButton okbutton = new JButton(MessageResource.
			getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));
	private JButton cancelbutton = new JButton(MessageResource
			.getMessage(MessageConstants.DIALOG_COMMON
					+ MessageConstants.CANCEL));

	public BindingSetingDialog()
	{
		setTitle(RibbonUIText.DATASTRUCT_NEWSTRUCT_DEAL_METHOD_TITLE);
		JPanel mainpanel = new JPanel(new BorderLayout());
		mainpanel.add(setpanel, BorderLayout.CENTER);
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		buttonpanel.add(okbutton);
		buttonpanel.add(cancelbutton);
		mainpanel.add(buttonpanel, BorderLayout.SOUTH);
		initAction();
		setOkButton(okbutton);
		setCancelButton(cancelbutton);
		getContentPane().add(mainpanel);
		setSize(500, 400);
	}

	private void initAction()
	{
		cancelbutton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		okbutton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.OK;
				dispose();
			}
		});
	}

	public int getDealMethodSetting()
	{
		return setpanel.getDealMethodSetting();
	}

	private class BindingSetingPanel extends JPanel
	{
		private JTextArea infotextarea = new JTextArea(
				RibbonUIText.DATASTRUCT_NEWSTRUCT_DEAL_INFO);
		private JCheckBox bindnodechbox = new JCheckBox(
				RibbonUIText.DATASTRUCT_NEWSTRUCT_DEAL_BINDNODE);
		private JCheckBox groupconditionchbox = new JCheckBox(
				RibbonUIText.DATASTRUCT_NEWSTRUCT_DEAL_GROUP_CONDITION);
		private JCheckBox groupsortchbox = new JCheckBox(
				RibbonUIText.DATASTRUCT_NEWSTRUCT_DEAL_GROUP_SORT);
		private JCheckBox conditionchbox = new JCheckBox(
				RibbonUIText.DATASTRUCT_NEWSTRUCT_DEAL_CONDITION);
		private JCheckBox dynamicconditionchbox = new JCheckBox(
				RibbonUIText.DATASTRUCT_NEWSTRUCT_DEAL_DYNAMIC_CONDITION);
		private JCheckBox dynamicchbox = new JCheckBox(
				RibbonUIText.DATASTRUCT_NEWSTRUCT_DEAL_DYNAMIC);

		BindingSetingPanel()
		{
			setLayout(new BorderLayout());
			infotextarea.setEditable(false);
			infotextarea.setBackground(this.getBackground());
			JPanel groupsetpanel = new JPanel(new GridLayout(6, 1));
			groupsetpanel.setBorder(new TitledBorder(
					RibbonUIText.DATASTRUCT_NEWSTRUCT_DEAL_METHOD_TITLE));
			groupsetpanel.add(bindnodechbox);
			groupsetpanel.add(groupconditionchbox);
			groupsetpanel.add(groupsortchbox);
			groupsetpanel.add(conditionchbox);
			groupsetpanel.add(dynamicconditionchbox);
			groupsetpanel.add(dynamicchbox);
			add(infotextarea, BorderLayout.NORTH);
			add(groupsetpanel, BorderLayout.CENTER);
			groupconditionchbox.setSelected(true);
			groupsortchbox.setSelected(true);
			conditionchbox.setSelected(true);
			dynamicconditionchbox.setSelected(true);
		}

		private int getDealMethodSetting()
		{
			int setting = 0;
			if (!bindnodechbox.isSelected())
			{
				setting |= BINDNODESELECT;
			}
			if (groupconditionchbox.isSelected())
			{
				setting |= GROUPCONDITIONSELECT;
			}
			if (groupsortchbox.isSelected())
			{
				setting |= GROUPSORTSELECT;
			}
			if (conditionchbox.isSelected())
			{
				setting |= CONDITIONSELECT;
			}
			if (dynamicconditionchbox.isSelected())
			{
				setting |= DYNAMICCONDITIONSELECT;
			}
			if (dynamicchbox.isSelected())
			{
				setting |= DYNAMICSELECT;
			}
			return setting;
		}
	}
}
