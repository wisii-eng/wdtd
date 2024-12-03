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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.wisii.db.util.DBUtil;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.panel.DataSourceEditPanel;
import com.wisii.wisedoc.view.panel.DataSourceNodePanel;
import com.wisii.wisedoc.view.ui.ButtonContainer;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class DataSourceManagementDialog extends AbstractWisedocDialog
{

	ButtonContainer parentdia;

	DataSourceNodePanel nodepanel;

	DataSourceEditPanel editpanel;

	com.wisii.wisedoc.view.data.Control control;

	String name = null;

	public DataSourceManagementDialog(JDialog parent)
	{
		super(parent,"",true);
		this.setTitle("数据源管理");
		this.setSize(600, 400);
		this.setLayout(null);
		initComponents();
		WisedocUtil.centerOnScreen(this);
//		this.set
	}

	public DataSourceManagementDialog(String name, JDialog parent)
	{
		super(parent,"",true);
		this.setTitle("数据源管理");
		this.setSize(600, 400);
		this.setLayout(null);
		this.name = name;
		initComponents();
		WisedocUtil.centerOnScreen(this);
	}

	private void initComponents()
	{
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setSize(600, 400);

		nodepanel = new DataSourceNodePanel(name);
		nodepanel.setPreferredSize(new Dimension(150, 300));
		editpanel = new DataSourceEditPanel();
		editpanel.setPreferredSize(new Dimension(410, 300));
		editpanel.setVisible(false);
		control = new com.wisii.wisedoc.view.data.Control(nodepanel, editpanel);
		nodepanel.setControl(control);
		editpanel.setControl(control);
		if (name != null)
		{
			nodepanel.setSelectNode(name);
		}
		JPanel buttonpanel = new JPanel();
		buttonpanel.setPreferredSize(new Dimension(550, 80));
		buttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 50, 10));
		JButton ok = new JButton(UiText.OK);
		JButton cancle = new JButton(UiText.CANCLE);
		buttonpanel.add(ok);
		buttonpanel.add(cancle);

		mainpanel.add(nodepanel, BorderLayout.WEST);
		mainpanel.add(editpanel, BorderLayout.CENTER);
		mainpanel.add(buttonpanel, BorderLayout.SOUTH);
		ok.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.OK;
				DBUtil.setConnectionSetings(nodepanel.getDataSources());
				DBUtil.writeConnectionSetings();
				DataSourceManagementDialog.this.dispose();
			}
		});

		cancle.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				DataSourceManagementDialog.this.dispose();
			}
		});

		this.add(mainpanel);
	}

	public String getResult()
	{
		return control.getLastSelectName();
	}

	// public static void main(String[] args)
	// {
	// DataSourceManagementDialog dia = new DataSourceManagementDialog(
	// "datasoure0");
	// if (dia.showDialog() == DialogResult.OK)
	// {
	// System.out.println(dia.getResult());
	// }
	// }
}
