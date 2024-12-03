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
 * @LogicalExpressionTreeTable.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.treetable.le;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;
import com.wisii.wisedoc.swing.treetable.JTreeTable;
import com.wisii.wisedoc.swing.treetable.TreeTableModel;
import com.wisii.wisedoc.swing.treetable.TreeTableModelAdapter;
import com.wisii.wisedoc.view.component.ForEachEditorComponent;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-12-4
 */
public class LogicalExpressionTreeTable extends JTreeTable
{

	private NodeChooseEditor nodeeditor = new NodeChooseEditor();

	private TypeEditor typeeditor = new TypeEditor();

	private OperaterEditor operatereditor = new OperaterEditor();

	private ParmEditor parmeditor = new ParmEditor();

	public LogicalExpressionTreeTable(TreeTableModel treeTableModel)
	{
		super(treeTableModel);
	}

	public TableCellEditor getCellEditor(int row, int column)
	{
		if (column > 0 && column < 5)
		{
			if (column == 1)
			{
				return nodeeditor;
			} else if (column == 2)
			{
				return typeeditor;
			} else if (column == 3)
			{
				return parmeditor;
			} else
			{
				return operatereditor;
			}
		} else
		{
			return super.getCellEditor(row, column);
		}
	}

	private class NodeChooseEditor extends DefaultCellEditor implements
			FocusListener, TreeSelectionListener
	{

		private JPopupMenu pop;

		WiseDocTree tree;

		private BindNode node;

		JScrollPane scrpanel;

		private NodeChooseEditor()
		{
			super(new JTextField());

			init();
		}

		private void init()
		{
			editorComponent.addFocusListener(this);
			tree = new WiseDocTree();
			tree.setCellRenderer(new DataStructureCellRender());
			scrpanel = new JScrollPane();
			scrpanel.getViewport().setView(tree);

		}
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			node=(BindNode) value;
			return super.getTableCellEditorComponent(table, value, isSelected, row, column);
		}
		public void focusGained(FocusEvent e)
		{
		
			Document doc = SystemManager.getCurruentDocument();
			if (doc != null && doc.getDataStructure() != null)
			{
				tree.clearSelection();
				pop = new JPopupMenu();
				int width = editorComponent.getWidth();
				if (width < 260)
				{
					width = 260;
				}
				pop.setPreferredSize(new Dimension(width, 260));
				pop.add(scrpanel);
				tree.setModel(doc.getDataStructure());
				Object oldvalue = getCellEditorValue();
				if (oldvalue != null && oldvalue instanceof TreeNode)
				{
					tree.setSelectedNode((TreeNode) oldvalue);
				}
				if (pop != null && editorComponent != null)
				{
					pop.show(editorComponent, 0, 0);
				}
				tree.addTreeSelectionListener(this);
			}

		}

		public void focusLost(FocusEvent e)
		{

		}

		public Object getCellEditorValue()
		{
			return node;
		}
		public void valueChanged(TreeSelectionEvent e)
		{
			TreePath path = tree.getSelectionPath();
			if (path != null)
			{
				node = (BindNode) tree.getSelectionPath()
						.getLastPathComponent();
				stopCellEditing();
				tree.removeTreeSelectionListener(this);
				tree.clearSelection();
				pop.setVisible(false);
				pop = null;

			}

		}
	}

	private class TypeEditor extends DefaultCellEditor
	{

		private String[] typestrings =
		{ "文本等于", "文本不等", "正则表达式匹配", "数字大于", "数字等于", "数字小于", "数字小于等于", "数字不等于",
				"数字大于等于", "第一条数据", "非第一条数据", "最后一条数据", "非最后一条数据", "序号为奇数",
				"序号为偶数", "序号小于", "序号大于", "序号为", "字符数小于", "字符数大于", "总条数为",
				"总条数小于", "总条数大于", "总条数为奇数", "总条数为偶数", "序号整除3余数为", "序号整除4余数为",
				"序号整除5余数为", "序号整除6余数为", "序号整除7余数为", "序号整除8余数为", "序号整除9余数为",
				"总条数整除3余数为", "总条数整除4余数为", "总条数整除5余数为", "总条数整除6余数为", "总条数整除7余数为",
				"总条数整除8余数为","总条数整除9余数为"};

		private TypeEditor()
		{
			super(new JComboBox());
			((JComboBox) editorComponent).setModel(new DefaultComboBoxModel(
					typestrings));
		}

		public Object getCellEditorValue()
		{
			String value = (String) super.getCellEditorValue();
			Integer rvalue;
			if (typestrings[1].equals(value))
			{
				rvalue = Condition.STRINGNOTEQUAL;
			} else if (typestrings[2].equals(value))
			{
				rvalue = Condition.REGULAREQUAL;
			} else if (typestrings[3].equals(value))
			{
				rvalue = Condition.GREATER;
			} else if (typestrings[4].equals(value))
			{
				rvalue = Condition.EQUAL;
			} else if (typestrings[5].equals(value))
			{
				rvalue = Condition.LESS;
			} else if (typestrings[6].equals(value))
			{
				rvalue = Condition.LESSEQUAL;
			} else if (typestrings[7].equals(value))
			{
				rvalue = Condition.NUMBERNOTEQUAL;
			} else if (typestrings[8].equals(value))
			{
				rvalue = Condition.GREATEREQUAL;
			} else if (typestrings[9].equals(value))
			{
				rvalue = Condition.FIRST;
			} else if (typestrings[10].equals(value))
			{
				rvalue = Condition.NOTFIRST;
			} else if (typestrings[11].equals(value))
			{
				rvalue = Condition.LAST;
			} else if (typestrings[12].equals(value))
			{
				rvalue = Condition.NOTLAST;
			} else if (typestrings[13].equals(value))
			{
				rvalue = Condition.ODD;
			} else if (typestrings[14].equals(value))
			{
				rvalue = Condition.EVEN;
			} else if (typestrings[15].equals(value))
			{
				rvalue = Condition.POSITIONLESS;
			} else if (typestrings[16].equals(value))
			{
				rvalue = Condition.POSITIONGREATER;
			} else if (typestrings[17].equals(value))
			{
				rvalue = Condition.POSITION;
			} else if (typestrings[18].equals(value))
			{
				rvalue = Condition.LENGTHLESS;
			} else if (typestrings[19].equals(value))
			{
				rvalue = Condition.LENGTHGREATER;
			} else if (typestrings[20].equals(value))
			{
				rvalue = Condition.COUNT;
			} else if (typestrings[21].equals(value))
			{
				rvalue = Condition.COUNTLESS;
			} else if (typestrings[22].equals(value))
			{
				rvalue = Condition.COUNTGREATER;
			} else if (typestrings[23].equals(value))
			{
				rvalue = Condition.COUNTODD;
			} else if (typestrings[24].equals(value))
			{
				rvalue = Condition.COUNTEVEN;
			}
			else if (typestrings[25].equals(value))
			{
				rvalue = Condition.POSITIONMOD3;
			} 
			else if (typestrings[26].equals(value))
			{
				rvalue = Condition.POSITIONMOD4;
			} 
			else if (typestrings[27].equals(value))
			{
				rvalue = Condition.POSITIONMOD5;
			} 
			else if (typestrings[28].equals(value))
			{
				rvalue = Condition.POSITIONMOD6;
			} 
			else if (typestrings[29].equals(value))
			{
				rvalue = Condition.POSITIONMOD7;
			} 
			else if (typestrings[30].equals(value))
			{
				rvalue = Condition.POSITIONMOD8;
			} 
			else if (typestrings[31].equals(value))
			{
				rvalue = Condition.POSITIONMOD9;
			} 
			else if (typestrings[32].equals(value))
			{
				rvalue = Condition.COUNTMOD3;
			} 
			else if (typestrings[33].equals(value))
			{
				rvalue = Condition.COUNTMOD4;
			} 
			else if (typestrings[34].equals(value))
			{
				rvalue = Condition.COUNTMOD5;
			} 
			else if (typestrings[35].equals(value))
			{
				rvalue = Condition.COUNTMOD6;
			} 
			else if (typestrings[36].equals(value))
			{
				rvalue = Condition.COUNTMOD7;
			} 
			else if (typestrings[37].equals(value))
			{
				rvalue = Condition.COUNTMOD8;
			} 
			else if (typestrings[38].equals(value))
			{
				rvalue = Condition.COUNTMOD9;
			} 
			else
			{
				rvalue = Condition.STRINGEQUAL;
			}
			return rvalue;
		}
	}

	private class OperaterEditor extends DefaultCellEditor
	{

		private String[] typestrings =
		{ "且", "或", };

		private OperaterEditor()
		{
			super(new JComboBox());
			((JComboBox) editorComponent).setModel(new DefaultComboBoxModel(
					typestrings));
		}

		public Object getCellEditorValue()
		{
			String value = (String) super.getCellEditorValue();
			Integer rvalue;
			if (typestrings[0].equals(value))
			{
				rvalue = LogicalExpression.AND;
			} else
			{
				rvalue = LogicalExpression.OR;
			}
			return rvalue;
		}
	}

	private class ParmEditor extends javax.swing.AbstractCellEditor implements
			TableCellEditor, PropertyChangeListener
	{

		ForEachEditorComponent com;;

		private ParmEditor()
		{
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			com = new ForEachEditorComponent();
			com.setValue(value);
			com.addPropertyChangeListener(this);
			return com;
		}

		public Object getCellEditorValue()
		{
			Object value =com.getValue();
			if(value instanceof String&&((String)value).isEmpty())
			{
					value=null;
			}
			return value;
		}

		public void propertyChange(PropertyChangeEvent evt)
		{
			stopCellEditing();

		}
	}

	public void setModel(TreeTableModel dataModel)
	{
		if (!(dataModel instanceof LogicalExpressionTreeTableModel))
		{
			return;
		}
		LogicalExpressionTreeTableModel lemodel = (LogicalExpressionTreeTableModel) dataModel;
		tree.setModel(lemodel);
		super.setModel(new TreeTableModelAdapter(lemodel, tree));
	}
}
