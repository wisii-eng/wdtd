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
package com.wisii.wisedoc.view.ui.parts;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.dialog.PropertyPanelInterface;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.parts.common.BackGroundPanel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;

/**
 * 测试对话框专用
 * @author 闫舒寰
 *
 */
public class OpenDialogTest extends AbstractWisedocDialog {
	OpenDialogTest borderDialog;
	JTabbedPane tablepane = new JTabbedPane();
	BorderPanel borderPanel;
	BackGroundPanel backgroundpanel;
	
	//当前页布局的属性
	SimplePageMasterModel spmm;

	public OpenDialogTest()
	{
		super();
	}

	public OpenDialogTest(final JPanel panel) {
		this();
		borderDialog = this;
		this.setTitle("属性对话框测试");
		setSize(800, 600);
		setLayout(new BorderLayout());
		
		add(panel, BorderLayout.CENTER);
		
		JButton jb = new JButton("test");
		
		add(jb, BorderLayout.SOUTH);
		
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (panel instanceof PropertyPanelInterface) {
					PropertyPanelInterface pp = (PropertyPanelInterface) panel;
					System.err.println(pp.getSetting());
				}
			}
			
		});
		
		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public OpenDialogTest(final JComponent...component) {
		this();
		borderDialog = this;
		this.setTitle("属性对话框测试");
		setSize(800, 600);
		setLayout(new BorderLayout());
		
//		add(panel, BorderLayout.CENTER);
		add(component[0], BorderLayout.WEST);
		add(component[1], BorderLayout.EAST);
		
		JButton jb = new JButton("test");
		
		add(jb, BorderLayout.SOUTH);
		
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				for (JComponent panel : component) {
					if (panel instanceof PropertyPanelInterface) {
						PropertyPanelInterface pp = (PropertyPanelInterface) panel;
						System.err.println(pp.getSetting());
					}
				}
				
			}
			
		});
		
		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}
}
