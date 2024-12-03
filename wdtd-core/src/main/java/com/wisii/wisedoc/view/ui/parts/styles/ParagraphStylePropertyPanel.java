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
package com.wisii.wisedoc.view.ui.parts.styles;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;

import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.paragraphstyles.ParagraphStylesModel;

public class ParagraphStylePropertyPanel extends JPanel {

	private JTextField name;
	
	final JTextPane propertyDescription;
	
	/**
	 * Create the panel
	 */
	public ParagraphStylePropertyPanel() {
		super();

		JLabel label;
		label = new JLabel();
		label.setText("段落属性名：");

		name = new JTextField();

		JButton reNameButton;
		reNameButton = new JButton();
		reNameButton.setText("更名");

		JButton fontButton;
		fontButton = new JButton();
		fontButton.setText("设置文字属性");
		fontButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FontPropertyDialog(ActionType.BLOCK);
			}
		});

		JButton paragraphButton;
		paragraphButton = new JButton();
		paragraphButton.setText("设置段落属性");
		paragraphButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ParagraphPropertyDialog(ActionType.BLOCK);
			}
		});

		JPanel panel;
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setSize(270, 174);
		panel.setBorder(new TitledBorder(null, "属性描述：", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

		propertyDescription = new JTextPane();
		propertyDescription.setEditable(false);
		
		panel.add(propertyDescription, BorderLayout.CENTER);
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
						.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(name, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addGap(23, 23, 23)
							.addComponent(reNameButton))
						.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(fontButton)
							.addGap(20, 20, 20)
							.addComponent(paragraphButton)))
					.addGap(25, 25, 25))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(reNameButton))
					.addGap(28, 28, 28)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(fontButton)
						.addComponent(paragraphButton))
					.addGap(26, 26, 26)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(45, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}
	
	public void setPName(String pName) {
		name.setText(pName);
		
		Map<Integer, Object> temp = ParagraphStylesModel.Instance.getPsProMap(pName);
		
		if (temp != null) {
			propertyDescription.setText(pName + temp.toString());
		}
		
	}

}
