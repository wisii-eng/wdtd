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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.wisii.wisedoc.document.attribute.transform.TransformTable;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class SetTransformTableDialog extends AbstractWisedocDialog
{

	SetTransformTableDialog dsdialog;

	TransformTable source;

	JTable datatable = new JTable();

	public SetTransformTableDialog()
	{
		super();
		setTitle(UiText.SELECT_DATASOURCE_INNER);
		source = null;
		dsdialog = this;
		initialize();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	public SetTransformTableDialog(AbstractWisedocDialog parent,
			TransformTable src)
	{
		super(parent, UiText.SELECT_DATASOURCE_INNER, true);
		dsdialog = this;
		if (src != null)
		{
			source = getTransformTable(src);
		} else
		{
			source = null;
		}
		initialize();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	private void initialize()
	{
		this.setSize(500, 400);
		this.setLayout(null);
		JScrollPane treepanel = new JScrollPane();

		Object[] columnNames = getColumnName();
		Object[][] models = getModel();

		DefaultTableModel newdatamode = new DefaultTableModel(models,
				columnNames);

		datatable.setModel(newdatamode);
		treepanel.setViewportView(datatable);
		treepanel.setBounds(50, 5, 400, 280);

		this.add(treepanel);
		JButton addrow = new JButton(UiText.ADD_ROW);
		JButton deleterow = new JButton(UiText.DELETE_ROW);
		JButton addcolumn = new JButton(UiText.ADD_COLUMN);
		JButton deletecolumn = new JButton(UiText.DELETE_COLUMN);
		addrow.setBounds(50, 290, 80, 25);
		deleterow.setBounds(150, 290, 80, 25);

		addcolumn.setBounds(250, 290, 80, 25);
		deletecolumn.setBounds(350, 290, 80, 25);

		JButton ok = new JButton(UiText.OK);

		JButton cancle = new JButton(UiText.CANCLE);
		ok.setBounds(300, 330, 80, 25);
		cancle.setBounds(400, 330, 80, 25);
		this.add(addrow);
		this.add(deleterow);
		this.add(addcolumn);
		this.add(deletecolumn);

		this.add(ok);
		this.add(cancle);
		addrow.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DefaultTableModel model = (DefaultTableModel) datatable
						.getModel();
				int current = datatable.getSelectedRow();
				model.insertRow(current + 1, new Object[]
				{});

			}
		});
		deleterow.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DefaultTableModel model = (DefaultTableModel) datatable
						.getModel();
				int current = datatable.getSelectedRow();
				if (current > -1)
				{
					model.removeRow(current);
				}
			}
		});
		addcolumn.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DefaultTableModel model = (DefaultTableModel) datatable
						.getModel();
				if (datatable.getRowCount() > 0)
				{
					model.addColumn("列" + (datatable.getColumnCount() + 1));
				}
			}
		});
		deletecolumn.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DefaultTableModel model = (DefaultTableModel) datatable
						.getModel();
				if (datatable.getColumnCount() > 1)
				{
					model.setColumnCount(datatable.getColumnCount() - 1);
				}
			}
		});
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				boolean flg = setDataSource();
				if (flg)
				{
					result = DialogResult.OK;
					dsdialog.dispose();
				}
			}
		});
		cancle.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				dsdialog.dispose();
			}
		});
	}

	public TransformTable getDataSource()
	{
		return source;
	}

	public boolean setDataSource()
	{
		int rownumber = datatable.getRowCount();
		int columnnumber = datatable.getColumnCount();
		List<List<String>> table = new ArrayList<List<String>>();
		for (int i = 0; i < rownumber; i++)
		{
			List<String> row = new ArrayList<String>();
			for (int j = 0; j < columnnumber; j++)
			{
				Object column = datatable.getValueAt(i, j);
				if (column != null && !"".equals(column))
				{
					String columnone = column.toString();
					columnone = columnone.trim();
					if ("".equals(columnone))
					{
						datatable.editCellAt(i, j);
						return false;
					} else
					{
						row.add(columnone);
					}

				} else
				{
					datatable.editCellAt(i, j);
					return false;
				}
			}
			table.add(row);
		}
		source = new TransformTable(table);
		return true;
	}

	public DialogResult showDialog()
	{
		return result;
	}

	// public static void main(String[] args)
	// {
	// new SetTransformTableDialog();
	// TransformTable source = getSource();
	// System.out.println(source.toString());
	// }

	public TransformTable getSource()
	{
		return source;
	}

	public void setSource(TransformTable source)
	{
		this.source = source;
	}

	public Object[][] getModel()
	{
		Object[][] defaultmodels =
		{ null };
		if (source != null)
		{
			List<List<String>> datas = source.getDatas();
			int row = datas.size();
			if (row > 0)
			{
				List<String> first = datas.get(0);
				int column = first != null ? first.size() : 0;
				if (column > 0)
				{
					Object[][] models = new Object[row][column];
					for (int i = 0; i < row; i++)
					{
						List<String> currentlist = datas.get(i);
						for (int j = 0; j < column; j++)
						{
							String current = currentlist.get(j);
							models[i][j] = current;
						}
					}
					return models;
				}
			}
		}
		return defaultmodels;
	}

	public Object[] getColumnName()
	{
		Object[] defaultmodels = new Object[2];
		defaultmodels[0] = "列1";
		defaultmodels[1] = "列2";
		if (source != null)
		{
			List<List<String>> datas = source.getDatas();
			int row = datas.size();
			if (row > 0)
			{
				List<String> first = datas.get(0);
				int column = first != null ? first.size() : 0;
				if (column > 0)
				{
					Object[] models = new Object[column];
					for (int j = 0; j < column; j++)
					{
						String current = "列" + (j + 1);
						models[j] = current;
					}
					return models;
				}
			}
		}
		return defaultmodels;
	}

	public TransformTable getTransformTable(TransformTable table)
	{
		List<List<String>> dataslist = null;
		List<List<String>> datas = table.getDatas();
		if (datas != null)
		{
			dataslist = new ArrayList<List<String>>();
			for (List<String> currentlist : datas)
			{
				List<String> newstrlist = new ArrayList<String>();
				for (String current : currentlist)
				{
					String newstring = new String(current);
					newstrlist.add(newstring);
				}
				dataslist.add(newstrlist);
			}
		}
		TransformTable newtable = new TransformTable(dataslist);
		return newtable;
	}
}
