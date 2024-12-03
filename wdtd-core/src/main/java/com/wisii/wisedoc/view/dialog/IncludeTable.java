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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.exception.DataStructureException;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

@SuppressWarnings("serial")
public class IncludeTable extends JTable
{

	private TableCellEditor typeeditor = new typeEditor();

	private TableCellEditor keyEditor;

	public IncludeTable(TableInfoMode mode)
	{
		super(mode);
	}

	public IncludeTable(IncludeMode mode)
	{
		super(mode);
	}

	public IncludeTable(DefaultTableModel mode)
	{
		super(mode);
	}

	public TableCellEditor getCellEditor(int row, int column)
	{
		if (column == 0)
		{
			return null;
		} else if (column == 1)
		{
			return typeeditor;
		} else if (column == 2)
		{
			Object value = typeeditor.getCellEditorValue();
			if (value == null)
			{
				value = this.getModel().getValueAt(row, column - 1);
			}
			try
			{
				keyEditor = new keyEditor(value);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (DataStructureException e)
			{
				e.printStackTrace();
			}
			return keyEditor;
		} else
		{
			return super.getCellEditor(row, column);
		}
	}

	private class typeEditor extends javax.swing.AbstractCellEditor implements
			TableCellEditor, ActionListener
	{

		JTextField com = new JTextField();

		JPanel panel = new JPanel();

		JButton button = new JButton("▼");

		private typeEditor()
		{

			panel.setLayout(new BorderLayout());
			button.setMargin(new Insets(0, 0, 0, 0));
			button.addMouseListener(new MouseListener()
			{

				@Override
				public void mouseClicked(MouseEvent e)
				{
					JFileChooser chooser = DialogSupport
							.getDialog(new WiseDocFileFilter(".xml", "xml"));
					chooser.setCurrentDirectory(new File(System
							.getProperty("user.dir")
							+ "/configuration"));
					chooser.setAcceptAllFileFilterUsed(false);

					int dialogResult = chooser.showOpenDialog(SystemManager
							.getMainframe());
					if (dialogResult == JFileChooser.APPROVE_OPTION)
					{
						File file = chooser.getSelectedFile();
						com.setText(file.getName());
					}
				}

				@Override
				public void mouseEntered(MouseEvent e)
				{

				}

				@Override
				public void mouseExited(MouseEvent e)
				{

				}

				@Override
				public void mousePressed(MouseEvent e)
				{

				}

				@Override
				public void mouseReleased(MouseEvent e)
				{

				}
			});

			panel.add(com, BorderLayout.CENTER);
			panel.add(button, BorderLayout.EAST);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			return panel;
		}

		public Object getCellEditorValue()
		{
			return com != null ? com.getText() : null;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			stopCellEditing();
		}
	}

	private class keyEditor extends javax.swing.AbstractCellEditor implements
			TableCellEditor, ActionListener
	{

		JTextField com = new JTextField();

		private keyEditor(Object name) throws FileNotFoundException,
				DataStructureException
		{

			final String filename = name != null && !"".equals(name) ? System
					.getProperty("user.dir")
					+ "/configuration/" + name : "";

			com.setEditable(false);

			com.addMouseListener(new MouseListener()
			{

				@Override
				public void mouseClicked(MouseEvent e)
				{
					if (filename != null)
					{
						EditXmlNodeDialog dia;
						try
						{
							if (!"".equals(filename))
							{
								dia = new EditXmlNodeDialog(filename);
								dia.setVisible(true);
								if (dia.showDialog() == DialogResult.OK)
								{
									com.setText(dia.getSelectElement());
								}
							}
						} catch (FileNotFoundException e1)
						{
							e1.printStackTrace();
						} catch (DataStructureException e1)
						{
							e1.printStackTrace();
						}

					}

				}

				@Override
				public void mouseEntered(MouseEvent e)
				{

				}

				@Override
				public void mouseExited(MouseEvent e)
				{

				}

				@Override
				public void mousePressed(MouseEvent e)
				{

				}

				@Override
				public void mouseReleased(MouseEvent e)
				{

				}
			});

		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column)
		{
			if (value == null)
			{
				com.setText("");
			} else
			{
				com.setText(value + "");
			}
			return com;
		}

		public Object getCellEditorValue()
		{
			return com != null ? com.getText() : null;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			stopCellEditing();
		}
	}
}
