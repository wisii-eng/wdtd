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
 * @ParamTable.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
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
import com.wisii.wisedoc.document.attribute.edit.Parameter;
import com.wisii.wisedoc.document.attribute.edit.Parameter.DataTyle;
import com.wisii.wisedoc.document.attribute.edit.Parameter.ParamTyle;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-1
 */
public class ParamTable extends JTable
{
	NodeChooseEditor nodeeditor = new NodeChooseEditor();
	ParmypeEditor paratypeeditor = new ParmypeEditor();
	DataTypeEditor datatypeeditor = new DataTypeEditor();

	ParamTable(List<Parameter> parmeters)
	{
		List<Parameter> ps = new ArrayList<Parameter>();
		if (parmeters != null)
		{
			for (Parameter parmeter : parmeters)
			{
				ps.add(new Parameter(parmeter.getDatatype(), parmeter
						.getName(), parmeter.getType(), parmeter.getValue()));
			}
		}
		setModel(new ParamTableModel(ps));
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column)
	{
		if (column == 0)
		{
			return super.getCellEditor(row, column);
			
		} else if (column == 1)
		{
			return paratypeeditor;
		} else if (column == 2)
		{
			return datatypeeditor;
		} else
		{
			String parmtype = (String) getValueAt(row, 1);
			if (RibbonUIText.EDIT_CONNWITH_PARM_TYPE_CONSTANCE.equals(parmtype)|| RibbonUIText.EDIT_CONNWITH_PARM_TYPE_UI.equals(parmtype))
			{
				return super.getCellEditor(row, column);
			} else
			{
				return nodeeditor;
			}
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

		public void focusGained(FocusEvent e)
		{
		
			Document doc = SystemManager.getCurruentDocument();
			if (doc != null && doc.getDataStructure() != null)
			{
				tree.clearSelection();
				pop = new JPopupMenu();
				pop
						.setPreferredSize(new Dimension(editorComponent.getWidth(),
								100));
				pop.add(scrpanel);
				tree.setModel(doc.getDataStructure());
                Object oldvalue = getCellEditorValue();
                if(oldvalue!=null&&oldvalue instanceof TreeNode)
                {
                	tree.setSelectedNode((TreeNode)oldvalue);
                }
				pop.show(editorComponent, 0, 0);
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

	private class ParmypeEditor extends DefaultCellEditor
	{
		private ParmypeEditor()
		{
			super(new JComboBox(new Object[]
			{ RibbonUIText.EDIT_CONNWITH_PARM_TYPE_UI,
					RibbonUIText.EDIT_CONNWITH_PARM_TYPE_CONSTANCE,
					RibbonUIText.EDIT_CONNWITH_PARM_TYPE_XPATH }));
		}

		public Object getCellEditorValue()
		{
			String value = (String) delegate.getCellEditorValue();
			if (RibbonUIText.EDIT_CONNWITH_PARM_TYPE_CONSTANCE.equals(value))
			{
				return ParamTyle.CONSTANCE;
			} else if (RibbonUIText.EDIT_CONNWITH_PARM_TYPE_XPATH.equals(value))
			{
				return ParamTyle.XPATH;
			} else
			{
				return ParamTyle.UI;
			}
		}
		@Override
		public boolean stopCellEditing()
		{
			boolean isstop =  super.stopCellEditing();
			ParamTable.this.repaint();
			return isstop;
		}
	}

	private class DataTypeEditor extends DefaultCellEditor
	{
		private DataTypeEditor()
		{
			super(new JComboBox(new Object[]
			{ RibbonUIText.EDIT_CONNWITH_PARM_DATATYPE_STRING, RibbonUIText.EDIT_CONNWITH_PARM_DATATYPE_NUMBER }));
		}
		public Object getCellEditorValue()
		{
			String value = (String) delegate.getCellEditorValue();
			if (RibbonUIText.EDIT_CONNWITH_PARM_DATATYPE_NUMBER.equals(value))
			{
				return DataTyle.NUMBER;
			} else
			{
				return DataTyle.STRING;
			}
		}
	}
	public void addRow()
	{
		int rowselect = getSelectedRow();
		if (rowselect < 0)
		{
			rowselect = getRowCount();
		}
		((ParamTableModel) getModel()).addRow(rowselect);
		updateUI();
	}

	public void delRow(int rowindex)
	{
		((ParamTableModel) getModel()).delRow(rowindex);
		updateUI();
	}
	public boolean isAllSetOk()
	{
		if(getCellEditor()!=null)
		{
			getCellEditor().stopCellEditing();
		}
		return ((ParamTableModel) getModel()).isAllSetOk();	
	}
	public List<Parameter> getParameters()
	{
		return ((ParamTableModel) getModel()).getParameters();
	}
}
