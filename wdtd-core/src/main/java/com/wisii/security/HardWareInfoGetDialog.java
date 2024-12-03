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
 * @HardWareInfoGetFrame.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.security;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.wisii.wisedoc.SystemManager;
/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-8-5
 */
public class HardWareInfoGetDialog extends JDialog
{

	private JLabel pathlabel = new JLabel("保存路径");
	private JTextField pathtf = new JTextField(20);

	JButton generatorbutton = new JButton("产生");

	public HardWareInfoGetDialog()
	{
		super(SystemManager.getMainframe());
		setLayout(new BorderLayout());
		generatorbutton.setEnabled(false);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel filesetPanel = new JPanel(new FlowLayout());
		filesetPanel.add(pathlabel);
		filesetPanel.add(pathtf);
		pathtf.addKeyListener(new KeyAdapter()
		{

			public void keyTyped(KeyEvent e)
			{
				String text = pathtf.getText();
				if (text == null || text.trim().equals(""))
				{
					generatorbutton.setEnabled(false);
				} else
				{
					generatorbutton.setEnabled(true);
				}

			}
		});
		JButton pathbutton = new JButton("选择");
		pathbutton.addActionListener(new ActionListener()
		{

			// 【刘晓注掉20090828】
			// @Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System
						.getProperty("user.dir")));
				int result = chooser.showSaveDialog(HardWareInfoGetDialog.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					File file = chooser.getSelectedFile();
					pathtf.setText(file.getAbsolutePath());
					generatorbutton.setEnabled(true);
				}
			}
		});
		filesetPanel.add(pathbutton);
		add(filesetPanel, BorderLayout.CENTER);
		generatorbutton.addActionListener(new ActionListener()
		{

			// 【刘晓注掉20090828】
			// @Override
			public void actionPerformed(ActionEvent e)
			{
				boolean issavesucessfull = HardwareInfoSave.save(pathtf
						.getText().trim(), HardWareInfoGetter.getDiskID(),
						HardWareInfoGetter.getMac(), HardWareInfoGetter
								.getProcessorid());
				if (issavesucessfull)
				{
					JOptionPane.showMessageDialog(HardWareInfoGetDialog.this,
							"产生成功！" + "请将生成的注册信息文件发送至北京汇智互联软件技术有限公司，获取注册文件！");
					// result = DialogResult.OK;
					// HardWareInfoGetFrame.this.dispose();
				} else
				{
					JOptionPane.showMessageDialog(HardWareInfoGetDialog.this,
							"产生失败");
				}
			}
		});
		JButton exitbutton = new JButton("退出");
		exitbutton.addActionListener(new ActionListener()
		{

			// 【刘晓注掉20090828】
			// @Override
			public void actionPerformed(ActionEvent e)
			{
				HardWareInfoGetDialog.this.dispose();

			}
		});
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonpanel.add(generatorbutton);
		buttonpanel.add(exitbutton);
		add(buttonpanel, BorderLayout.SOUTH);
		setSize(400, 300);
		show();
	}

	// public DialogResult getResult()
	// {
	// return result;
	// }

	public static void main(String[] args)
	{
		new HardWareInfoGetDialog();
	}
}
