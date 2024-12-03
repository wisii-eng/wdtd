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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class RewriteDataSourceialog extends AbstractWisedocDialog
{

	RewriteDataSourceialog dia;

	public RewriteDataSourceialog()
	{
		super();

		this.setTitle(MessageResource
				.getMessage(MessageConstants.DATABASENAMEIS));
		dia = this;
		this.setLayout(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		this.setSize(400, 120);
		panel.setBounds(0, 0, 400, 120);
		JLabel text = new JLabel(MessageResource
				.getMessage(MessageConstants.REWRITEDATABASE), JLabel.CENTER);

		JButton ok = new JButton(UiText.OK);
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.OK;
				dia.dispose();
			}
		});
		JButton cancle = new JButton(UiText.CANCLE);
		cancle.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				dia.dispose();
			}
		});
		text.setBounds(0, 0, 380, 40);
		ok.setBounds(110, 50, 80, 25);
		cancle.setBounds(210, 50, 80, 25);
		panel.add(text);
		panel.add(ok);
		panel.add(cancle);
		this.add(panel);
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);

	}

	public DialogResult showDialog()
	{
		return result;
	}

}
