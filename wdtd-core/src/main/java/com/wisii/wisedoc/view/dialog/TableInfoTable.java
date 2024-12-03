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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.wisii.wisedoc.swing.ui.WiseCombobox;

@SuppressWarnings("serial")
public class TableInfoTable extends JTable
{

	private TableCellEditor typeeditor = new typeEditor();

	public TableInfoTable(TableInfoMode mode)
	{
		super(mode);
	}

	public TableInfoTable(DefaultTableModel mode)
	{
		super(mode);
	}

	public TableCellEditor getCellEditor(int row, int column)
	{
		if (column == 0)
		{
			return null;
		} else if (column == 2)
		{
			return typeeditor;
		} else
		{
			return super.getCellEditor(row, column);
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
