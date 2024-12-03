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

package com.wisii.wisedoc.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.wisii.db.ConnectionSeting;
import com.wisii.db.util.DBUtil;
import com.wisii.db.util.DriverTypeUtil;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.data.Control;
import com.wisii.wisedoc.view.data.DataSourceNode;
import com.wisii.wisedoc.view.data.DataSourceTree;
import com.wisii.wisedoc.view.data.DriverInfomation;
import com.wisii.wisedoc.view.data.DataSourceNode.NodeType;
import com.wisii.wisedoc.view.dialog.RewriteDataSourceialog;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class DataSourceEditPanel extends JPanel
{

	DataSourceNode currentnode;

	Map<String, DriverInfomation> drivers = DriverTypeUtil.getDrivers();

	JComboBox databaselist = new JComboBox();

	JTextField nametf = new JTextField();

	JTextField connectiontf = new JTextField();

	JTextField usertf = new JTextField();

	JPasswordField passwordtf = new JPasswordField();

	JTextField driverclasstf = new JTextField();

	JButton test = new JButton(UiText.TESTCONNECTION);

	String oldname = "";

	Control control;

	public DataSourceEditPanel()
	{
		super();
		initComponents();
	}

	public void initComponents()
	{
		this.setLayout(new FlowLayout());

		JPanel main = new JPanel();
		main.setLayout(new GridLayout(7, 1));
		main.setPreferredSize(new Dimension(410, 310));
		JPanel databasenamepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		databasenamepanel.setPreferredSize(new Dimension(400, 30));
		JLabel namelabel = new JLabel(MessageResource
				.getMessage(MessageConstants.DATABASENAME), JLabel.RIGHT);
		namelabel.setPreferredSize(new Dimension(110, 25));
		nametf.setPreferredSize(new Dimension(280, 25));
		databasenamepanel.add(namelabel);
		databasenamepanel.add(nametf);

		JPanel databasekindpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		databasekindpanel.setPreferredSize(new Dimension(400, 30));
		JLabel dbklabel = new JLabel(MessageResource
				.getMessage(MessageConstants.DATABASETYPE), JLabel.RIGHT);
		dbklabel.setPreferredSize(new Dimension(110, 25));
		databaselist.setPreferredSize(new Dimension(280, 25));
		setDatabaselistItem();
		databasekindpanel.add(dbklabel);
		databasekindpanel.add(databaselist);

		JPanel connectionpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		connectionpanel.setPreferredSize(new Dimension(400, 30));
		JLabel conneclabel = new JLabel(MessageResource
				.getMessage(MessageConstants.CONNECTIONURL), JLabel.RIGHT);
		conneclabel.setPreferredSize(new Dimension(110, 25));
		connectiontf.setPreferredSize(new Dimension(280, 25));
		connectionpanel.add(conneclabel);
		connectionpanel.add(connectiontf);

		JPanel ueserpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ueserpanel.setPreferredSize(new Dimension(400, 30));
		JLabel ueserlabel = new JLabel(MessageResource
				.getMessage(MessageConstants.LOGINNAME), JLabel.RIGHT);
		ueserlabel.setPreferredSize(new Dimension(110, 25));
		usertf.setPreferredSize(new Dimension(280, 25));
		ueserpanel.add(ueserlabel);
		ueserpanel.add(usertf);

		JPanel passwordpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		passwordpanel.setPreferredSize(new Dimension(400, 30));
		JLabel passwordlabel = new JLabel(MessageResource
				.getMessage(MessageConstants.LOGINPASSWORD), JLabel.RIGHT);
		passwordlabel.setPreferredSize(new Dimension(110, 25));
		passwordtf.setPreferredSize(new Dimension(280, 25));
		passwordtf.setEchoChar('*');
		passwordpanel.add(passwordlabel);
		passwordpanel.add(passwordtf);

		JPanel driverclasspanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		driverclasspanel.setPreferredSize(new Dimension(400, 30));
		JLabel driverclasslabel = new JLabel(MessageResource
				.getMessage(MessageConstants.DRIVERCLASS), JLabel.RIGHT);
		driverclasslabel.setPreferredSize(new Dimension(110, 25));
		driverclasstf.setPreferredSize(new Dimension(280, 25));
		driverclasstf.setEditable(false);
		driverclasstf.setEnabled(false);
		driverclasspanel.add(driverclasslabel);
		driverclasspanel.add(driverclasstf);

		JPanel testpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		testpanel.setPreferredSize(new Dimension(400, 30));

		test.setPreferredSize(new Dimension(150, 30));
		testpanel.add(test);
		main.add(databasenamepanel);
		main.add(databasekindpanel);
		main.add(connectionpanel);
		main.add(ueserpanel);
		main.add(passwordpanel);
		main.add(driverclasspanel);
		main.add(testpanel);
		main.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "",
				TitledBorder.LEFT, TitledBorder.TOP));
		this.add(main);
		addAction();
	}

	public void init(DataSourceNode node)
	{
		currentnode = node;
		if (node.getNodetype() == NodeType.DATASOUREITEM)
		{
			this.setVisible(true);
			ConnectionSeting currentcs = node.getValue();
			oldname = currentcs.getName();
			String name = oldname;
			String url = currentcs.getUrl();
			String un = currentcs.getUsername();
			String pw = currentcs.getPassword();
			String divertype = currentcs.getDriverclassType();
			String diverclass = DriverTypeUtil.getDriverClassName(divertype);
			nametf.setText(name);
			databaselist.setSelectedItem(divertype);
			connectiontf.setText(url);
			usertf.setText(un);
			passwordtf.setText(pw);
			driverclasstf.setText(diverclass);
		} else
		{
			this.setVisible(false);
		}
	}

	private void addAction()
	{
		test.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (testConnection())
				{
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), MessageResource
							.getMessage(MessageConstants.CONNECTIONCHENGGONG));
				} else
				{
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), MessageResource
							.getMessage(MessageConstants.CONNECTIONAGAIN));
				}
			}
		});
		databaselist.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					String current = event.getItem().toString().trim();
					if (drivers.containsKey(current))
					{
						DriverInfomation currentdi = drivers.get(current);
						String url = currentdi.getConnectionurl();
						String dc = currentdi.getDriverclass();
						driverclasstf.setText(dc);
						connectiontf.setText(url);
					}
				}
			}
		});

	}

	@SuppressWarnings("deprecation")
	public boolean testConnection()
	{
		String driverclass = driverclasstf.getText();
		try
		{
			Class.forName(driverclass);
		} catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
		String url = connectiontf.getText();
		String username = usertf.getText();
		String password = passwordtf.getText();
		Connection con = null;
		try
		{
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e)
		{
			LogUtil.debugException(MessageResource
					.getMessage(MessageConstants.DRIVERNOTFOUND), e);
			return false;
		}
		return con != null;
	}

	private void setDatabaselistItem()
	{
		if (drivers != null && !drivers.isEmpty())
		{
			Object[] keys = drivers.keySet().toArray();
			for (Object current : keys)
			{
				DriverInfomation cdinfo = drivers.get(current);
				String datype = cdinfo != null ? cdinfo.getDatabasetype() : "";
				if (!"".equals(datype))
				{
					databaselist.addItem(datype);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public ConnectionSeting getConnectionSeting()
	{
		String name = nametf.getText().trim();
		if ("".equals(name))
		{
			WiseDocOptionPane.showMessageDialog(SystemManager.getMainframe(),
					MessageResource
							.getMessage(MessageConstants.DATABASENAMEISNULL));
			nametf.requestFocus();
			return null;
		} else
		{
			if (!name.equals(oldname)
					&& DBUtil.getConnectionSetings().containsKey(name))
			{
				RewriteDataSourceialog dia = new RewriteDataSourceialog();
				if (dia.showDialog() != DialogResult.OK)
				{
					return null;
				}
			}
			return new ConnectionSeting(nametf.getText(), databaselist
					.getSelectedItem().toString(), connectiontf.getText(),
					usertf.getText(), passwordtf.getText());
		}
	}

	public void getResult()
	{
		if (currentnode.getNodetype() == NodeType.DATASOUREITEM)
		{
			ConnectionSeting cs = getConnectionSeting();
			if (cs != null)
			{
				currentnode.setValue(cs);
				String name = cs.getName();
				DataSourceTree.addDataSource(name, cs);
			}
		}
	}

	public Control getControl()
	{
		return control;
	}

	public void setControl(Control control)
	{
		this.control = control;
	}
}
