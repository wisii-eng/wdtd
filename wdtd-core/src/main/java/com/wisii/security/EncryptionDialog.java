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
package com.wisii.security;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.EncryptInformation;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class EncryptionDialog extends AbstractWisedocDialog
{

	EncryptInformation encryptinformation;

	EncryptionTable table = new EncryptionTable();

	WiseCombobox combox = new WiseCombobox();
	{
		combox.addItem("加密类型一");
		combox.addItem("加密类型二");
	}

	JButton ok = new JButton(UiText.OK);

	JButton cancle = new JButton(UiText.CANCLE);
	/* 添加行按钮 */
	private JButton btnAdd = new JButton("添加");// 添加行

	/* 删除行按钮 */
	private JButton btnSubtract = new JButton("删除");// 删除行

	public EncryptionDialog()
	{
		super();
		this.setTitle("加密");
		this.setSize(650, 600);
		this.setLayout(null);
		initComponents();
	}

	public EncryptionDialog(EncryptInformation information)
	{
		super(SystemManager.getMainframe(), "设置", true);
		encryptinformation = information;
		this.setSize(650, 600);
		this.setLayout(null);
		initComponents();
	}

	private void initComponents()
	{
		JLabel jiami = new JLabel("加密类型：");
		jiami.setBounds(100, 5, 80, 25);
		combox.setBounds(180, 5, 100, 25);
		JScrollPane panel = new JScrollPane();

		Object[] columnNames =
		{"节点" };
		Object[][] models = getModel();

		DefaultTableModel newdatamode = new DefaultTableModel(models,
				columnNames);
		table.setModel(newdatamode);
		panel.setViewportView(table);
		panel.setBounds(10, 40, 630, 380);
		
		btnAdd.setBounds(20, 430, 80, 25);
		btnSubtract.setBounds(120, 430, 80, 25);
		ok.setBounds(300, 460, 80, 25);
		cancle.setBounds(400, 460, 80, 25);
		this.add(jiami);
		this.add(combox);
		this.add(panel);
		this.add(btnAdd);
		this.add(btnSubtract);
		this.add(ok);
		this.add(cancle);
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.OK;
				setEncryptInformation();
				EncryptionDialog.this.dispose();
			}
		});
		cancle.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				EncryptionDialog.this.dispose();
			}
		});
		btnAdd.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				CellEditor editor = table.getCellEditor();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int rowCount = model.getRowCount();
				if (editor != null)
					editor.stopCellEditing();

				model.setRowCount(rowCount + 1);
				table.changeSelection(rowCount, 0, false, false);
			}
		});
		btnSubtract.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				CellEditor editor = table.getCellEditor();
				if (editor != null)
					editor.stopCellEditing();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int rowCount = model.getRowCount();
				int selRow = table.getSelectedRow();
				if (rowCount <= 0 || selRow == -1)
				{
					return;
				}
				model.removeRow(selRow);
				if (selRow == 0)
				{
					selRow = 0;
				} else if (rowCount <= selRow + 1)
					selRow -= 1;
				table.changeSelection(selRow, 0, false, false);
			}
		});
	}

	public Object[][] getModel()
	{
		Object[][] defaultmodels =
		{ null };
		if (encryptinformation != null)
		{
			List<BindNode> nodes = encryptinformation.getNodes();
			int row = nodes.size();
			if (row > 0)
			{
				Object[][] models = new Object[row][1];
				for (int i = 0; i < row; i++)
				{
					BindNode currentnode = nodes.get(i);
//					String current = currentnode.getXPath();
					models[i][0] = currentnode;
				}
				return models;
			}
		}
		return defaultmodels;
	}

	public void setEncryptInformation()
	{
		int index = combox.getSelectedIndex();
		int encrypttype = EncryptInformation.ENCRYPTTYPE1;
		if (index == 1)
		{
			encrypttype = EncryptInformation.ENCRYPTTYPE2;
		}
		int rownumber = table.getRowCount();

		List<BindNode> nodes = new ArrayList<BindNode>();
		for (int i = 0; i < rownumber; i++)
		{
			BindNode column = (BindNode) table.getValueAt(i, 0);
			nodes.add(column);
		}
		encryptinformation = new EncryptInformation(encrypttype, nodes);
	}

	public EncryptInformation getEncryptInformation()
	{
		return encryptinformation;
	}

	public DialogResult showDialog()
	{
		setVisible(true);
		return result;
	}

	public static void main(String[] args)
	{
		EncryptionDialog dia = new EncryptionDialog();
		dia.setVisible(true);
	}
}
