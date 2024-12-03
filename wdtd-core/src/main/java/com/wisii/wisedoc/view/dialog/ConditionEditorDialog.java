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
 * @ConditionEditorDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.swing.treetable.DefaultNode;
import com.wisii.wisedoc.swing.treetable.Node;
import com.wisii.wisedoc.swing.treetable.TreeTableModelAdapter;
import com.wisii.wisedoc.swing.treetable.le.AbstractLogicalExpressionNode;
import com.wisii.wisedoc.swing.treetable.le.ConditionNode;
import com.wisii.wisedoc.swing.treetable.le.LogicalExpressionNode;
import com.wisii.wisedoc.swing.treetable.le.LogicalExpressionTreeTable;
import com.wisii.wisedoc.swing.treetable.le.LogicalExpressionTreeTableModel;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-12-5
 */
public class ConditionEditorDialog extends AbstractWisedocDialog // JDialog
{

	/* 定义放置Table的滚动Panel */
	private JScrollPane scrTablePanel = new JScrollPane();

	private LogicalExpressionTreeTable table;

	/* 放置按钮的Panel */
	private JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	/* 确定按钮 */
	private JButton btnOK = new JButton(UiText.DIALOG_OK);

	/* 取消按钮 */
	private JButton btnCancel = new JButton(UiText.DIALOG_CANCEL);

	/* 添加行按钮 */
	private JButton btnAddbefore = new JButton("添加前条件"
	/* getMessage("Wisedoc.Condition.Button.Add") */);

	private JButton btnAddafter = new JButton("添加后条件");

	private JButton btnAddsub = new JButton("添加子条件");

	/* 删除行按钮 */
	private JButton btnSubtract = new JButton("删除条件"
	/* getMessage("Wisedoc.Condition.Button.Delete") */);

	/* 放置所有空间的Panel */
	private JPanel mainPanel = new JPanel(new BorderLayout());

	private JPanel setPanel = new JPanel(new BorderLayout());

	private JPanel setconPanel = new JPanel();

	private JPanel typePanel = new JPanel();

	// 条件表达式
	private LogicalExpression logicale;

	/* 定义窗体返回装体对象 */
	/* private DialogResult result = DialogResult.Cancel; */
	private JComboBox typecombox;

	private long oldtime = -1;

	/**
	 * 根据默认值，创建默认条件设置对话框
	 * 
	 * @param 无
	 */
	public ConditionEditorDialog(LogicalExpression logicale)
	{
		this(SystemManager.getMainframe(), "条件设置", true, logicale);

	}

	/**
	 * 创建一个指定了对话框拥有者、标题、模式的对话框
	 * 
	 * @param owner
	 *            指定对话框的拥有者
	 * @param title
	 *            指定对话框的标题
	 * @param modal
	 *            指定对话框的模式
	 */
	public ConditionEditorDialog(Frame owner, String title, boolean modal,
			LogicalExpression logicale)
	{
		super(owner, title, modal);
		this.logicale = logicale;
		/* 初始对话框的所有组件 */
		initComponents();
	}

	/**
	 * 初始对话框的所有组件,放置所有的组件到对话框Panel上
	 * 
	 * @param 无
	 * 
	 * @return void wu
	 */
	private void initComponents()
	{
		table = new LogicalExpressionTreeTable(
				new LogicalExpressionTreeTableModel(logicale));
		table.setEnabled(true);
		mainPanel.add(scrTablePanel, BorderLayout.CENTER);
		getContentPane().add(mainPanel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Container content = getContentPane();
		scrTablePanel.getViewport().add(table);
		mainPanel.add(btnPanel, BorderLayout.SOUTH);
		btnPanel.add(btnAddbefore);
		btnPanel.add(btnAddafter);
		btnPanel.add(btnAddsub);
		btnPanel.add(btnSubtract);
		btnPanel.add(btnOK);
		btnPanel.add(btnCancel);
		table.getTableHeader().setReorderingAllowed(false);
		setPreferredSize(new Dimension(600, 400));
		// 【添加】李晓光 2007-08-20 确认按钮、取消按钮
		setOkButton(btnOK);
		setCancelButton(btnCancel);
		pack();
		initActions();
	}
	/**
	 * 初始化该对话框中的按钮的Action
	 * 
	 * @param 无
	 * @return void 无
	 */
	private void initActions()
	{
		btnAddbefore.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				CellEditor editor = table.getCellEditor();
				if (editor != null)
				{
					editor.stopCellEditing();
				}
				TreeTableModelAdapter model = (TreeTableModelAdapter) table
						.getModel();
				int row = table.getSelectedRow();
				if (row < 0)
				{
					return;
				}
				Node node = (Node) model.nodeForRow(row);

				AbstractLogicalExpressionNode parentnode = (AbstractLogicalExpressionNode) node
						.getParent();
				if (parentnode != null)
				{
					table.setNodeSelect(parentnode.insert(node, true));
				}
			}
		});
		btnAddafter.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				CellEditor editor = table.getCellEditor();
				if (editor != null)
				{
					editor.stopCellEditing();
				}
				TreeTableModelAdapter model = (TreeTableModelAdapter) table
						.getModel();
				int row = table.getSelectedRow();
				if (row < 0)
				{
					return;
				}
				Node node = (Node) model.nodeForRow(row);
				AbstractLogicalExpressionNode parentnode = (AbstractLogicalExpressionNode) node
						.getParent();
				if (parentnode != null)
				{
					table.setNodeSelect(parentnode.insert(node, false));
				}
			}
		});
		btnAddsub.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				CellEditor editor = table.getCellEditor();
				if (editor != null)
				{
					editor.stopCellEditing();
				}
				TreeTableModelAdapter model = (TreeTableModelAdapter) table
						.getModel();
				int row = table.getSelectedRow();
				if (row < 0)
				{
					return;
				}
				DefaultNode node = (DefaultNode) model.nodeForRow(row);

				Node parentnode = node.getParent();
				LogicalExpressionNode lenode = null;
				if (node instanceof ConditionNode)
				{
					if (parentnode != null)
					{
						lenode = new LogicalExpressionNode();
						lenode.addNode(node);
						parentnode.replace(node, lenode);
					}
				} else
				{
					lenode = (LogicalExpressionNode) node;
					lenode.addNode(new ConditionNode(null, null, null));
				}
				table.setSelectLastChildNode(lenode);
			}
		});
		btnSubtract.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				CellEditor editor = table.getCellEditor();
				if (editor != null)
				{
					editor.stopCellEditing();
				}
				TreeTableModelAdapter model = (TreeTableModelAdapter) table
						.getModel();
				int selectrow = table.getSelectedRow();

				DefaultNode node = (DefaultNode) model.nodeForRow(selectrow);
				AbstractLogicalExpressionNode parentnode = (AbstractLogicalExpressionNode) node
						.getParent();
				if (node != null && parentnode != null)
				{
					parentnode.remove(node);
				}
				if (selectrow > 0)
				{
					if (selectrow >= table.getRowCount())
					{
						table.setRowSelectionInterval(table
								.getRowCount() - 1,
								table.getRowCount() - 1);
					} else
					{
						table.setRowSelectionInterval(selectrow,
								selectrow);
					}
				}
				table.updateUI();
			}
		});
		btnCancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnOK.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if (table.getCellEditor() != null)
					table.getCellEditor().stopCellEditing();
				LogicalExpression nlogicale = getLogicalExpression();
				if(nlogicale==null)
				{
					return;
				}
				result = DialogResult.OK;
				dispose();
			
				// 如果新的条件值和原来的条件值相等，则将返回的条件值设置为null
				if (nlogicale != null && !nlogicale.equals(logicale))
				{
					logicale = nlogicale;
				} else
				{
					logicale = null;
				}
				/*
				 * if (!ConditionEditorDialog.this.hasFocus() &&
				 * !table.hasFocus()) { if (table.getCellEditor() != null)
				 * table.getCellEditor().stopCellEditing();
				 * ConditionEditorDialog.this.requestFocus(); oldtime =
				 * e.getWhen(); } else { long newtime = e.getWhen(); if
				 * ((oldtime == -1) || (newtime - oldtime > 300)) {
				 * LogicalExpression nlogicale = getLogicalExpression(); if
				 * (nlogicale != null) { // 如果新的条件值和原来的条件值相等，则将返回的条件值设置为null if
				 * (nlogicale.equals(logicale)) { logicale = null; } else {
				 * logicale = nlogicale; } result = DialogResult.OK; dispose();
				 * } } oldtime = -1; }
				 */

			}
		});
	}

	/**
	 * 显示对话框本身
	 * 
	 * @param 无
	 * @return {@link DialogResult} 返回对话框的返回状态【确认是那个按钮的触发使得该对话框返回】
	 */
	public DialogResult showDialog()
	{
		if (SystemManager.getCurruentDocument().getDataStructure() == null)
		{
			JOptionPane.showMessageDialog(SystemManager.getMainframe(),
					"有问题"/*
							* getMessage( "Wisedoc.Condition.Warn.Info" )
							*/, "没有树"/* getMessage("prompt") */,
					JOptionPane.WARNING_MESSAGE);
			dispose();
		} else
		{
			DialogSupport.centerOnScreen(this);
			setVisible(true);
		}
		return result;
	}

	/**
	 * 取得编辑后的条件字符串
	 * 
	 * @param wu
	 * @return String 条件字符串
	 */
	public LogicalExpression getCondition()
	{
		return logicale;
	}

	public LogicalExpression getLogicalExpression()
	{
		TableModel tablemodel = table.getModel();

		if (tablemodel instanceof TreeTableModelAdapter)
		{
			TreeTableModelAdapter ttma = (TreeTableModelAdapter) tablemodel;
			LogicalExpressionTreeTableModel lemodel = (LogicalExpressionTreeTableModel) ttma
					.getTreeTableModel();
			LogicalExpressionNode lenode = lemodel.getRoot();
			if (lenode != null)
			{
				return createLogicalExpression(ttma, lenode);
			}
		}
		return null;
	}

	private LogicalExpression createLogicalExpression(
			TreeTableModelAdapter ttma, LogicalExpressionNode lenode)
	{
		if (ttma != null && lenode != null)
		{
			int count = lenode.getChildCount();
			List<Integer> operates = new ArrayList<Integer>();
			List subconditions = new ArrayList();
			for (int i = 0; i < count; i++)
			{
				AbstractLogicalExpressionNode subnode = (AbstractLogicalExpressionNode) lenode
						.getChildAt(i);
				if (subnode instanceof LogicalExpressionNode)
				{
					LogicalExpression suble = createLogicalExpression(ttma,
							(LogicalExpressionNode) subnode);
					if (suble == null)
					{
						return null;
					}
					subconditions.add(suble);
				} else
				{
					ConditionNode cnode = (ConditionNode) subnode;
					BindNode bindnode = cnode.getNode();
					Integer type = cnode.getType();
					Object param = cnode.getParam();
					//类型是position相关时，bindnode可为空，类型时position且是奇数条，偶数条之类的非指定条数时，param可为空
					if ((type == null)
							|| (bindnode == null && (type < Condition.FIRST || (type > Condition.POSITION&&type<Condition.POSITIONMOD3)))
							|| (param == null && ((type >= Condition.POSITIONLESS && type <= Condition.POSITION) || (type >= Condition.POSITIONMOD3
									&& type <= Condition.COUNTMOD9))))
					{
						table.setNodeSelect(cnode);
						return null;
					}
					Condition con = Condition.instance(bindnode, param, type);
					subconditions.add(con);
				}
				if (i < count - 1)
				{
					Integer operate = subnode.getOperator();
					if (operate == null)
					{
						table.setNodeSelect(subnode);
						return null;
					}
					operates.add(operate);
				}
			}
			return LogicalExpression.instance(subconditions, operates);
		}
		return null;

	}
}