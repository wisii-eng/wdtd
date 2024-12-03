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
 * @ConnectSettingPanel.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.wisii.db.util.DBUtil;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.DataSourceManagementDialog;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2010-11-8
 */
public class ConnectSettingPanel extends JPanel
{

	private WiseCombobox connectbox;

	private JButton editbutton = new JButton(UiText.SQL_CONNECTION_SET);

	private JLabel noconerrlabel;

	ButtonContainer container;

	public ConnectSettingPanel(String conname, ButtonContainer container)
	{
		super(new GridLayout(2, 1, 0, 0));
		init(conname);
		this.container = container;
	}

	private void init(String conname)
	{
		Object[] names = getEnableConnectName();
		connectbox = new WiseCombobox(names);
		boolean isselect = false;
		if (conname != null)
		{
			for (Object name : names)
			{
				if (conname.equals(name))
				{
					connectbox.setSelectedItem(name);
					isselect = true;
					break;
				}
			}
		}
		if (!isselect && connectbox.getModel().getSize() > 0)
		{
			connectbox.setSelectedIndex(0);
		}
		noconerrlabel = new JLabel("");
		noconerrlabel.setForeground(Color.red);
		JPanel connenpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		connenpanel.add(connectbox);
		connenpanel.add(editbutton);
		JPanel infopanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		infopanel.add(noconerrlabel);
		add(connenpanel);
		add(infopanel);
		setBorder(new TitledBorder(UiText.SQL_CONNECTION_BORDERTITLE));
		connectbox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				container.reinitButton();
			}
		});
		editbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name = (String) connectbox.getSelectedItem();
				// ConnectionSeting setting = DBUtil.getConnectionSeting(name);
				DataSourceManagementDialog dsdialog = new DataSourceManagementDialog(
						name, (JDialog) container);
				DialogResult result = dsdialog.showDialog();
				if (result == DialogResult.OK)
				{
					connectbox.setModel(new DefaultComboBoxModel(DBUtil
							.getAvailableDataSourceNames().toArray()));
					connectbox.setSelectedItem(dsdialog.getResult());
					container.reinitButton();
				}

			}
		});
	}

	private Object[] getEnableConnectName()
	{
		return DBUtil.getAvailableDataSourceNames().toArray();
	}

	public boolean isConnectSelect()
	{

		if (connectbox.getSelectedIndex() > -1)
		{
			noconerrlabel.setText("");
			return true;
		} else
		{
			noconerrlabel.setText(UiText.SQL_CONNECTION_ERROR);
			return false;
		}

	}

	public String getConnname()
	{
		return connectbox.getSelectedItem().toString();
	}
}
