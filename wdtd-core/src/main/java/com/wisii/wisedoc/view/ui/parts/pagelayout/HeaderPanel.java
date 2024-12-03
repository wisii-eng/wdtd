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

import java.util.ArrayList;
import java.util.List;

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
import javax.swing.SpinnerNumberModel;

import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Page;
import com.wisii.wisedoc.view.ui.text.UiText;

public class HeaderPanel extends JPanel {

	
	private JComboBox comboBox_3;
	private JSpinner spinner_2;
	private JComboBox comboBox_1;
	private JComboBox comboBox;
	private JSpinner spinner_1;
	private JSpinner spinner;
	/**
	 * Create the panel
	 */
	public HeaderPanel() {
		super();

		JCheckBox checkBox;
		checkBox = new JCheckBox();
		checkBox.setText("显示页眉");
		
		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText("页眉高度：");

		spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(1.0, null, null, 1));
		spinner_2.setEnabled(false);
		
		JLabel label_2_1;
		label_2_1 = new JLabel();
		label_2_1.setText(UiText.COMMON_MEASUREMENT_LABEL);

		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		comboBox_3.setEnabled(false);
		
		
		/*******************左右边距菜单*************************/
		JLabel label;
		label = new JLabel();
		label.setText("左边距：");

		spinner = new JSpinner();
		spinner.setEnabled(false);

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText("右边距：");

		spinner_1 = new JSpinner();
		spinner_1.setEnabled(false);

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText("单位：");

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText("单位：");

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		comboBox.setEnabled(false);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		comboBox_1.setEnabled(false);
		/*******************左右边距菜单*************************/	
		
		/*
		 * 用来记录页眉面板上的所有的按钮，顺序是：
		 * 0：显示页眉按钮
		 * 1：页眉高度
		 * 2：页眉高度单位
		 * 3：左边距
		 * 4：左边距单位
		 * 5：右边距
		 * 6：右边距单位
		 */
		List<Object> headerProperties = new ArrayList<Object>();
		headerProperties.add(checkBox);
		headerProperties.add(spinner_2);
		headerProperties.add(comboBox_3);
		headerProperties.add(spinner);
		headerProperties.add(comboBox);
		headerProperties.add(spinner_1);
		headerProperties.add(comboBox_1);
		checkBox.addActionListener(ActionFactory.getAction(Page.SHOW_HEADER_ACTION, headerProperties));
		
		
		
		/*****还没想好怎么弄********/
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
		/*****还没想好怎么弄********/
		
		
		JButton button_3;
		button_3 = new JButton();
		button_3.setText("更多选项...");

		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(label)
										.addComponent(label_1)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(8, 8, 8)
									.addComponent(checkBox))
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(label_4)))
							.addGap(4, 4, 4)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addGap(15, 15, 15)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(label_2_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_3)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_2)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
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
										.addComponent(button_1)))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 4, GroupLayout.PREFERRED_SIZE)
									.addComponent(button_3)))))
					.addContainerGap(188, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(checkBox)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_4)
						.addComponent(label_2_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(21, 21, 21)
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
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}

}
