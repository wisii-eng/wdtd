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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.WisedocFrame;
import com.wisii.wisedoc.view.ui.model.CustomizeSimplePageMasterModel;
import com.wisii.wisedoc.view.ui.ribbon.RibbonPanel;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

@SuppressWarnings("serial")
public class SetContntDialog extends AbstractWisedocDialog
{

	RegionEditPanel panel;

	StaticContent currentsc;

	public SetContntDialog(String title, int type, StaticContent sc,
			CustomizeSimplePageMasterModel spmm)
	{
		super();
		this.setTitle(title);
		currentsc = sc;
		panel = new RegionEditPanel(currentsc, spmm, type);
		this.setLayout(null);
		initComponents();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	private void initComponents()
	{
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setPreferredSize(new Dimension(780, 580));
		JScrollPane jsp = new JScrollPane(panel);
		JPanel buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 40, 5));
		JButton ok = new JButton("确定");
		JButton reset = new JButton("重置");
		JButton cancle = new JButton("取消");
		buttonpanel.add(ok);
		buttonpanel.add(reset);
		buttonpanel.add(cancle);
		mainpanel.add(jsp, BorderLayout.CENTER);
		mainpanel.add(buttonpanel, BorderLayout.SOUTH);
		this.setContentPane(mainpanel);
		pack();
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.OK;
				returnRibbonUI();
				SetContntDialog.this.dispose();
			}
		});
		cancle.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				returnRibbonUI();
				SetContntDialog.this.dispose();
			}
		});

		reset.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				returnRibbonUI();
				SetContntDialog.this.dispose();
			}
		});
		this.addWindowListener(new WindowAdapter()
		{

			@Override
			public void windowClosed(final WindowEvent e)
			{
			}

			@Override
			public void windowClosing(final WindowEvent e)
			{
				super.windowClosing(e);
				returnRibbonUI();
			}
		});
	}

	public void returnRibbonUI()
	{
		final Object o = SystemManager.getMainframe();
		if (o instanceof WisedocFrame)
		{
			final WisedocFrame mPanel = (WisedocFrame) o;

			mPanel.setJToolbar(RibbonPanel.getRibbon());

			RibbonPanel.getRibbon().setSelectedTask(
					RibbonPanel.getRibbon().getTask(2));

			RibbonUpdateManager.Instance.addMainEditorListener();
		}
	}

	public StaticContent getStaticContent()
	{
		currentsc = panel.getStaticContent();
		return currentsc;
	}

	@Override
	public DialogResult showDialog()
	{
		return result;
	}
}
