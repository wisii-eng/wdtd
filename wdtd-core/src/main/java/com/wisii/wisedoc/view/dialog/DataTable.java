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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.wisii.wisedoc.swing.ui.WiseCombobox;

@SuppressWarnings("serial")
public class DataTable extends JTable
{

	DataTable table = this;

	private TableCellEditor heardditor = new heardEditor();

	private TableCellEditor searchKeyditor = new searchKeyEditor();

	private TableCellEditor typeeditor = new typeEditor();

	private TableCellEditor titleTypeEditor = new titleTypeEditor();

	public DataTable()
	{
		super();
		this.tableHeader.setPreferredSize(new Dimension(300, 0));
		this.tableHeader.setVisible(false);
	}

	public DataTable(DefaultTableModel mode)
	{
		super(mode);
		this.tableHeader.setPreferredSize(new Dimension(300, 0));
		this.tableHeader.setVisible(false);
	}

	public TableCellEditor getCellEditor(int row, int column)
	{
		if (row == 0)
		{
			if (column == 0)
			{
				return searchKeyditor;
			} else
			{
				return heardditor;
			}
		} else if (row == 1)
		{
			if (column == 0)
			{
				return titleTypeEditor;
			} else
			{
				return typeeditor;
			}
		} else
		{
			if (column == 0)
			{
				return null;
			} else
			{
				return super.getCellEditor(row, column);
			}
		
		}
	}

	public TableCellRenderer getCellRenderer(int row, int column)
	{
		if (row == 0)
		{
			return new heardRenderer();
		} else
		{
			return super.getCellRenderer(row, column);
		}
	}

	private class heardRenderer extends DefaultTableCellRenderer implements
			TableCellRenderer, ActionListener
	{

		public heardRenderer()
		{
			super();
			this.setBackground(table.getTableHeader().getBackground());
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub

		}
	}

	private class heardEditor extends javax.swing.AbstractCellEditor implements
			TableCellEditor, ActionListener
	{

		JTextArea index = new JTextArea();

		private heardEditor()
		{
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			if (value != null)
			{
				index.setText(value + "");
			} else
			{
				index.setText("");
			}
			return index;
		}

		public Object getCellEditorValue()
		{
			return index.getText();
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			stopCellEditing();
		}
	}

	private class searchKeyEditor extends javax.swing.AbstractCellEditor
			implements TableCellEditor, ActionListener
	{

		JTextField index = new JTextField();

		private searchKeyEditor()
		{
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			index.setText("列标题");
			index.setEditable(false);
			return index;
		}

		public Object getCellEditorValue()
		{
			return index.getText();
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			stopCellEditing();
		}
	}

	private class titleTypeEditor extends javax.swing.AbstractCellEditor
			implements TableCellEditor, ActionListener
	{

		JTextField index = new JTextField();

		private titleTypeEditor()
		{
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			index.setText("列类型");
			index.setEditable(false);
			return index;
		}

		public Object getCellEditorValue()
		{
			return index.getText();
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			stopCellEditing();
		}
	}

	private class typeEditor extends javax.swing.AbstractCellEditor implements
			TableCellEditor, ActionListener
	{

		WiseCombobox com;

		private typeEditor()
		{
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			com = new WiseCombobox();
			com.setEditable(false);
			com.addItem("INTEGER");
			com.addItem("DOUBLE");
			com.addItem("FLOAT");
			com.addItem("VARCHAR");
			com.addItem("DATE");
			com.addItem("TIME");
			com.addItem("TIMESTAMP");
			com.addItem("DATETIME");
			com.addItem("NUMERIC");
			com.addItem("BOOLEAN");
			com.addItem("TINYINT");
			com.addItem("SMALLINT");
			com.addItem("BIGINT");
			com.addItem("REAL");
			com.addItem("BINARY");
			com.addItem("VARBINARY");
			com.addItem("LONGVARBINARY");
			return com;
		}

		public Object getCellEditorValue()
		{
			return com.getSelectedItem();
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			stopCellEditing();
		}
	}
}
