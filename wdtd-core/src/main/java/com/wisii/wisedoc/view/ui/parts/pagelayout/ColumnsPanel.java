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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;

import com.wisii.wisedoc.view.ui.text.UiText;

public class ColumnsPanel extends JPanel {

	private JComboBox comboBox;
	private JSpinner spinner_1;
	private JSpinner spinner;
	/**
	 * Create the panel
	 */
	public ColumnsPanel() {
		super();

		JLabel label;
		label = new JLabel();
		label.setText("分栏数：");

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, null, null, 1));

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText("栏间距：");

		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0.0, null, null, 1));

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(UiText.COMMON_MEASUREMENT_LABEL);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		
		List<Object> columns = new ArrayList<Object>();
		columns.add(spinner);
		columns.add(spinner_1);
		columns.add(comboBox);
		
		
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(21, 21, 21)
					.addComponent(label_1)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addComponent(label_2)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(197, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(347, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}

}
