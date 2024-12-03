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
package com.wisii.wisedoc.view.ui.parts.pagelayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;

public class FooterPanel extends JPanel {

	private JSpinner spinner_2;
	private JComboBox comboBox_1;
	private JComboBox comboBox;
	private JSpinner spinner_1;
	private JSpinner spinner;
	/**
	 * Create the panel
	 */
	public FooterPanel() {
		super();

		JCheckBox checkBox;
		checkBox = new JCheckBox();
		checkBox.setText("显示页脚");

		JLabel label;
		label = new JLabel();
		label.setText("左边距：");

		spinner = new JSpinner();

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText("右边距：");

		spinner_1 = new JSpinner();

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText("单位：");

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText("单位：");

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"cm", "mm", "m"}));

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"cm", "mm", "m"}));

		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText("页眉高度：");

		spinner_2 = new JSpinner();

		JCheckBox checkBox_1;
		checkBox_1 = new JCheckBox();
		checkBox_1.setText("奇数页不同");

		JButton button;
		button = new JButton();
		button.setText("设置奇数页菜单");

		JCheckBox checkBox_2;
		checkBox_2 = new JCheckBox();
		checkBox_2.setText("偶数页不同");

		JButton button_1;
		button_1 = new JButton();
		button_1.setText("设置偶数页菜单");

		JCheckBox checkBox_3;
		checkBox_3 = new JCheckBox();
		checkBox_3.setText("首页不同");

		JButton button_2;
		button_2 = new JButton();
		button_2.setText("设置首页菜单");

		JButton button_3;
		button_3 = new JButton();
		button_3.setText("更多选项...");
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(8, 8, 8)
							.addComponent(checkBox))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(label)
										.addComponent(label_1)
										.addComponent(label_4))
									.addGap(16, 16, 16)
									.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
												.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
											.addGap(15, 15, 15)
											.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(label_3)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(label_2)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(checkBox_1)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(button))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(checkBox_2)
										.addComponent(checkBox_3))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(button_2)
										.addComponent(button_1)))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button_3)))
					.addContainerGap(190, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(checkBox)
					.addGap(16, 16, 16)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24, 24, 24)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_4)
						.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(22, 22, 22)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(checkBox_1)
						.addComponent(button))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(checkBox_2)
						.addComponent(button_1))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(checkBox_3)
						.addComponent(button_2))
					.addGap(14, 14, 14)
					.addComponent(button_3)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}

}
