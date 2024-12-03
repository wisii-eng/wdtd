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
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

@SuppressWarnings("serial")
public class AboutDialog extends AbstractWisedocDialog
{

	JButton ok = new JButton(UiText.OK);

	public AboutDialog()
	{
		super();
		this.setTitle(RibbonUIText.ABOUT);
		this.setSize(400, 300);
		this.setLayout(new BorderLayout());
		initComponents();
		WisedocUtil.centerOnScreen(this);
		this.setVisible(true);
	}

	public void initComponents()
	{
		JPanel buttonpanel = new JPanel(
				new FlowLayout(FlowLayout.RIGHT, 30, 10));
		JTextPane textpanel = new JTextPane();

		// textpanel.setText("版本\n" + "Version "
		// + ConfigureUtil.getProperty("realse") + "\n" + "版权申明\n"
		// + "本软件版权归 北京汇智互联软件技术有限公司 所有。\n"
		// + "未经授权许可，不得使用、复制或传播本软件部分或全部内容，\n" + "不得以任何方式反编译本软件程序。");

		textpanel
				.setText(RibbonUIText.ABOUT_FIRST
						+ ConfigureUtil.getProperty("realse")
						+ RibbonUIText.ABOUT_LAST);

		textpanel.setEditable(false);
		ok.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent event)
			{
				AboutDialog.this.dispose();
			}
		});
		buttonpanel.add(ok);
		this.add(textpanel, BorderLayout.CENTER);
		this.add(buttonpanel, BorderLayout.SOUTH);
	}
}
