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

package com.wisii.wisedoc.view.ui.ribbon.bcb;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;

public class DialogError extends AbstractWisedocDialog
{

	public DialogError()
	{
		super();
		this.setTitle("文件已经存在");
		this.setPreferredSize(new Dimension(200, 100));
		setLayout(new BorderLayout());
		final JPanel buttonpanel = new JPanel();
		final JButton ok = new JButton("确定");
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				dispose();
			}
		});
		buttonpanel.add(ok);
		this.add(buttonpanel, BorderLayout.SOUTH);

		pack();
		this.setLocationRelativeTo(null);
		/* 添加ESC、ENTER健处理 */
		setOkButton(ok);
		
		setVisible(true);
	}

	public static void main(final String[] args)
	{
		new DialogError();
	}

}