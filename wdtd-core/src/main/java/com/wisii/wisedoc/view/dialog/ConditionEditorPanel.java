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

package com.wisii.wisedoc.view.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;
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

@SuppressWarnings("serial")
public class ConditionEditorPanel extends JPanel
{

	/* 定义放置Table的滚动Panel */
	private JScrollPane scrTablePanel = new JScrollPane();

	private LogicalExpressionTreeTable table;

	/* 放置按钮的Panel */
	private JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

	/* 添加行按钮 */
	private JButton btnAddbefore = new JButton("添加前条件"
	/* getMessage("Wisedoc.Condition.Button.Add") */);

	private JButton btnAddafter = new JButton("添加后条件");

	private JButton btnAddsub = new JButton("添加子条件");

	/* 删除行按钮 */
	private JButton btnSubtract = new JButton("删除条件"
	/* getMessage("Wisedoc.Condition.Button.Delete") */);

	// 条件表达式
	private LogicalExpression logicale;

	/**
	 * 根据默认值，创建默认条件设置对话框
	 * 
	 * @param 无
	 */
	public ConditionEditorPanel(LogicalExpression logicale)
	{
		table = new LogicalExpressionTreeTable(
				new LogicalExpressionTreeTableModel(logicale));
		table.setVisible(true);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setSize(600, 330);
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
		scrTablePanel.setPreferredSize(new Dimension(600, 250));
		scrTablePanel.getViewport().add(table);
		this.add(scrTablePanel);
		btnPanel.setPreferredSize(new Dimension(600, 50));
		btnPanel.add(btnAddbefore);
		btnPanel.add(btnAddafter);
		btnPanel.add(btnAddsub);
		btnPanel.add(btnSubtract);
		this.add(btnPanel);

		table.getTableHeader().setReorderingAllowed(false);
		this.setPreferredSize(new Dimension(600, 300));
		// 【添加】李晓光 2007-08-20 确认按钮、取消按钮

		initActions();
	}

	/**
	 * 自定义Table编辑器条用时机
	 * 
	 * @param evt
	 *            指定用户触发的事件类型
	 * @return boolean 如果允许编辑：True 否则：False
	 */
	@SuppressWarnings("unused")
	private boolean isEditable(EventObject evt)
	{
		if (evt instanceof MouseEvent)
		{
			return ((MouseEvent) evt).getClickCount() >= 1;
		}
		return true;
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
		return logicale;
	}
    public boolean isSettingOK()
	{
    	if (table.getCellEditor() != null)
		{
    		table.getCellEditor().stopCellEditing();
		}
		TableModel tablemodel = table.getModel();
    	TreeTableModelAdapter ttma = (TreeTableModelAdapter) tablemodel;
		LogicalExpressionTreeTableModel lemodel = (LogicalExpressionTreeTableModel) ttma
				.getTreeTableModel();
		LogicalExpressionNode lenode = lemodel.getRoot();
		if (lenode == null || lenode.getChildCount() == 0)
		{
			return true;
		}
		if (lenode.getChildCount() == 1)
		{
			Node node = lenode.getChildAt(0);
			if (node == null)
			{
				return true;
			} else if (node instanceof ConditionNode)
			{
				ConditionNode cnode = (ConditionNode) node;
				if (cnode.getNode() == null && cnode.getParam() == null&&cnode.getType()==null)
				{
					return true;
				}
			}
		}
		logicale = createLogicalExpression(lenode);
		return  logicale!= null;
	}
	@SuppressWarnings("unchecked")
	private LogicalExpression createLogicalExpression(LogicalExpressionNode lenode)
	{
		if (lenode != null)
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
					LogicalExpression suble = createLogicalExpression((LogicalExpressionNode) subnode);
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
					// 类型是position相关时，bindnode可为空，类型时position且是奇数条，偶数条之类的非指定条数时，param可为空
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
