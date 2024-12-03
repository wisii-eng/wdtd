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
 * @ForEachEditorDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.dialog;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

/**
 * 类功能描述：循环条件设置用对话框
 * 
 * 作者：李晓光 创建日期：2007-08-17
 */
@SuppressWarnings("serial")
public class ForEachEditorDialog extends AbstractWisedocDialog //JDialog
{
	/* 放置对话框中所有控件的Panel */
	private JPanel mainPanel = new JPanel(new BorderLayout());

	/* 放置按钮的Panel */
	private JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	/* 放置XMLTree的Panel */
	private JScrollPane treePanel = new JScrollPane();

	/* 定义XML文件树 */
	private JTree xmlTree = new JTree(SystemManager.getCurruentDocument().getDataStructure());

	/* 定义确定按钮 */
	private JButton btnOK = new JButton(getMessage("WiseDoc.Frame.OK"));

	/* 定义取消按钮 */
	private JButton btnCancel = new JButton(getMessage("WiseDoc.Frame.Cancel"));

	/* 定义循环条件 */
	private String forEachNode;

	/* 定义对话框返回状态【用来确定是那个按钮的触发使得对话框返回】 */
	private DialogResult result = DialogResult.Cancel;

	/* 定义对话框的初始大小 */
	private Dimension size = new Dimension(400, 300);

	/**
	 * 根据默认值，创建循环条件设置对话框对象
	 * 
	 * @param 无
	 */
	public ForEachEditorDialog()
	{
		this(SystemManager.getMainframe(), getMessage("Wisedoc.ForEach.Title"), true);
	}

	/**
	 * 创建一个指定了对话框拥有者、对话框标题、对话框模式的，循环条件设置对话框
	 * 
	 * @param owner
	 *            指定对话框拥有者
	 * @param title
	 *            指定对话框标题
	 * @param modal
	 *            指定对话框模式
	 */
	public ForEachEditorDialog(Frame owner, String title, boolean modal)
	{	
		super(owner, title, modal);
		
		initComponents();
	}

	/**
	 * 添加对话框上的所有组件
	 * 
	 * @param 无
	 * 
	 * @return void 无
	 */
	private void initComponents()
	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Container content = getContentPane();
		content.add(mainPanel, java.awt.BorderLayout.CENTER);
		mainPanel.add(treePanel, BorderLayout.CENTER);
		mainPanel.add(btnPanel, BorderLayout.SOUTH);
		treePanel.getViewport().add(xmlTree);
		btnPanel.add(btnOK);
		btnPanel.add(btnCancel);
		setPreferredSize(size);
		/* 【添加】 李晓光 2007-08-20  */
		setOkButton(btnOK);
		setCancelButton(btnCancel);
		pack();

		/* 初始化按钮的Action */
		initAction();
	}

	/**
	 * 为对话框上的按钮、和XML树添加Action事件
	 * 
	 * @param 无
	 * @return void 无
	 */
	private void initAction()
	{
		btnOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.OK;
				dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{				
				dispose();
			}
		});
		xmlTree.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e)
			{
				TreePath path = e.getNewLeadSelectionPath();

				if (path == null)
					return;

				BindNode node = (BindNode) path.getLastPathComponent();
				if (node == null)
					return;
				/* 获得从根节点到被选择节点的 Tree 路径 */
				forEachNode = node.getXPath();
			}
		});
	}

	/**
	 * 对话框对象被创建后，用次方法来显示对话框
	 * 
	 * @param 无
	 * @return {@link DialogResult} 指定对话框的返回状态【确认按钮、取消按钮使得窗体返回】
	 */
	public DialogResult showDialog()
	{
		if(SystemManager.getCurruentDocument().getDataStructure() == null)
		{
			JOptionPane.showMessageDialog(SystemManager.getMainframe(), getMessage("Wisedoc.Condition.Warn.Info"), getMessage("prompt"), JOptionPane.WARNING_MESSAGE);
			dispose();
		}
		else
		{
			DialogSupport.centerOnScreen(this);
			setVisible(true);
		}
		return result;
	}

	/**
	 * 获得在对话框中设置的用于循环的条件【字符串】
	 * 
	 * @param 无
	 * @return String 条件字符串
	 */
	public String getForEachNode()
	{
		return forEachNode;
	}

	/**
	 * 设置
	 * 
	 * @param forEachNode
	 */
	public void setForEachNode(String forEachNode)
	{
		this.forEachNode = forEachNode;
	}
}