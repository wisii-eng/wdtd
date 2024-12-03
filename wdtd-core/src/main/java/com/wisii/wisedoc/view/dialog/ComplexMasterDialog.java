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
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.StaticContentManeger;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class ComplexMasterDialog extends AbstractWisedocDialog
{

	Object pagemaster;

	CardLayout psmlayout = new CardLayout();

	NodePanel nodepanel;

	ShowMasterPanel complexpanel;

	Control control;

	public ComplexMasterDialog(Object pagemaster, Map<String, StaticContent> map)
	{
		super();
		StaticContentManeger.setScmap(map);
		this.pagemaster = pagemaster;
		this.setTitle("设置章节复杂布局");
		this.setSize(800, 700);
		this.setLayout(null);
		initComponents();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	private void initComponents()
	{
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setSize(780, 650);
		nodepanel = new NodePanel(pagemaster);
		nodepanel.setPreferredSize(new Dimension(150, 600));
		complexpanel = new ShowMasterPanel(pagemaster);
		control = new Control(nodepanel, complexpanel);
		nodepanel.setControl(control);
		complexpanel.setControl(control);
		JPanel buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 40, 5));
		JButton ok = new JButton(UiText.OK);
		JButton reset = new JButton(UiText.RESET);
		JButton cancle = new JButton(UiText.CANCLE);
		buttonpanel.add(ok);
		buttonpanel.add(reset);
		buttonpanel.add(cancle);

		mainpanel.add(nodepanel, BorderLayout.WEST);
		mainpanel.add(complexpanel, BorderLayout.CENTER);
		mainpanel.add(buttonpanel, BorderLayout.SOUTH);
		ok.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.OK;
				boolean isok = setMaster();
				if (isok)
				{
					ComplexMasterDialog.this.dispose();
				}

			}
		});
		reset.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				setInitialState();
			}
		});
		cancle.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				ComplexMasterDialog.this.dispose();
			}
		});

		this.add(mainpanel);
	}

	public boolean setMaster()
	{
		complexpanel.getResult();
		boolean flg = nodepanel.setMaster();
		if (flg)
		{
			this.pagemaster = nodepanel.getMaster();
		}
		return flg;
	}

	public void setInitialState()
	{
		nodepanel.setInitialState(pagemaster);
		complexpanel.setInitialState();
	}

	public DialogResult showDialog()
	{
		return result;
	}

	public Object getPagemaster()
	{
		return pagemaster;
	}

}
