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

package com.wisii.wisedoc.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;

public class OpenModelDialog extends AbstractWisedocDialog
{

	final static public int NEWDOCMENT = 1;

	final static public int EDITDOCMENT = 2;

	private static File file;

	public OpenModelDialog()
	{
		super();
		setBounds(100, 100, 1024, 800);

		this.setTitle("选择模板");

		setLayout(new BorderLayout());

		add(new WiseTemplatePanel(), BorderLayout.CENTER);

		final JPanel buttonpanel = new JPanel();
		final JButton ok = new JButton("确定");
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				result = DialogResult.OK;
				dispose();
			}
		});
		final JButton canel = new JButton("取消");
		canel.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				setFile(null);
				dispose();
			}
		});
		buttonpanel.add(ok);
		buttonpanel.add(canel);
		this.add(buttonpanel, BorderLayout.SOUTH);
		
		/* 添加ESC、ENTER健处理 */
		setOkButton(ok);
		setCancelButton(canel);
		
		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public DialogResult showDialog()
	{
		dispose();
		return result;
	}

	public static File getFile()
	{
		return file;
	}

	public static void setFile(final File f)
	{
		OpenModelDialog.file = f;
	}
}