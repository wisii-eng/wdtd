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
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableCellEditor;

import com.wisii.db.Setting;
import com.wisii.db.util.DBUtil;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.ButtonContainer;
import com.wisii.wisedoc.view.ui.ConnectSettingPanel;
import com.wisii.wisedoc.view.ui.SetParamValueTable;
import com.wisii.wisedoc.view.ui.text.UiText;

public class SetParamDialog extends AbstractWisedocDialog implements
		ButtonContainer
{

	private static Map<String, String> params;

	private static String datasourcename = "";

	SetParamValueTable paramtable;

	ButtonContainer paramdialog;

	ConnectSettingPanel csp;

	JButton ok = new JButton(UiText.OK);

	JButton cancle = new JButton(UiText.CANCLE);

	public SetParamDialog()
	{
		super();
		initComponents();
	}

	public SetParamDialog(String datasourcename, Map<String, String> params)
	{
		super();
		setDatasourcename(datasourcename);
		this.params = params;
		initComponents();
	}

	private void initComponents()
	{
		this.setTitle(MessageResource.getMessage(MessageConstants.SETPARAM));
		paramdialog = this;
		this.setPreferredSize(new Dimension(500, 500));
		this.setLayout(new BorderLayout());
		JPanel mainpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mainpanel.setPreferredSize(new Dimension(450, 350));
		JScrollPane scpanel = new JScrollPane();
		scpanel.setPreferredSize(new Dimension(450, 320));
		paramtable = new SetParamValueTable(params);
		scpanel.setViewportView(paramtable);
		mainpanel.add(scpanel);
		csp = new ConnectSettingPanel(getDatasourcename(), paramdialog);

		add(mainpanel, BorderLayout.CENTER);
		csp.setPreferredSize(new Dimension(450, 80));
		add(csp, BorderLayout.NORTH);
		JPanel bp = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		bp.setPreferredSize(new Dimension(500, 40));
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				TableCellEditor cuttentcelleditor = paramtable.getCellEditor();
				if (cuttentcelleditor != null)
				{
					cuttentcelleditor.stopCellEditing();
				}
				result = DialogResult.OK;
				SetParamDialog.this.dispose();
			}
		});
		cancle.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				SetParamDialog.this.dispose();
			}
		});
		bp.add(ok);
		bp.add(cancle);

		add(bp, BorderLayout.SOUTH);
		pack();
		WisedocUtil.centerOnScreen(this);
	}

	public Map<String, String> getParams()
	{
		paramtable.setParams();
		return params;
	}

	public static Map<String, String> getBeforeParams()
	{
		return params;
	}

	public String getDBConectionName()
	{
		return csp.getConnname();
	}

	// public static void main(String[] args)
	// {
	// Map<String, String> paramsold = new HashMap<String, String>();
	// paramsold.put("1", "1");
	// paramsold.put("2", "2");
	// paramsold.put("3", "3");
	// paramsold.put("4", "4");
	// SetParamDialog dia = new SetParamDialog(paramsold);
	// dia.setVisible(true);
	//
	// Map<String, String> params = dia.getParams();
	// if (params != null && !params.isEmpty())
	// {
	// Object[] keys = params.keySet().toArray();
	//
	// for (Object current : keys)
	// {
	// System.out.println(current);
	// System.out.println(params.get(current));
	// }
	// }
	// }

	@Override
	public void reinitButton()
	{
		ok.setEnabled(DBUtil.testConnection(DBUtil.getConnectionSeting(csp
				.getConnname())));
	}

	public static String getDatasourcename()
	{
		if ("".equals(datasourcename))
		{
			Document doc = SystemManager.getCurruentDocument();
			if (doc != null)
			{
				DataStructureTreeModel model = doc.getDataStructure();
				if (model != null)
				{
					Setting setting = model.getDbsetting();
					if (setting != null)
					{
						return setting.getConnname();
					}
				}
			}

		}
		return datasourcename;
	}

	public static void setDatasourcename(String datasourcename)
	{
		SetParamDialog.datasourcename = datasourcename;
	}
}