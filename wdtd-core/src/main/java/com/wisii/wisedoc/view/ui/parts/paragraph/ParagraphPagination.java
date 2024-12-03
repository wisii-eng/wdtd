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
package com.wisii.wisedoc.view.ui.parts.paragraph;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.view.ui.text.UiText;

public class ParagraphPagination extends JPanel {

	/**
	 * Create the panel
	 */
	public ParagraphPagination() {
		super();

		JCheckBox checkBox;
		checkBox = new JCheckBox();
		checkBox.setText(UiText.PARAGRAPH_ORPHAN_CONTROL_BOTTON);

		JCheckBox checkBox_1;
		checkBox_1 = new JCheckBox();
		checkBox_1.setText(UiText.PARAGRAPH_KEEP_WITH_NEXT_PARA_BUTTON);

		JCheckBox checkBox_2;
		checkBox_2 = new JCheckBox();
		checkBox_2.setText(UiText.PARAGRAPH_KEEP_IN_ONE_PAGE_BUTTON);

		JCheckBox checkBox_3;
		checkBox_3 = new JCheckBox();
		checkBox_3.setText(UiText.PARAGRAPH_BREAK_PAGE_BEFORE_BUTTON);
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(checkBox)
							.addGap(9, 9, 9)
							.addComponent(checkBox_1))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(checkBox_2)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(checkBox_3)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(checkBox)
						.addComponent(checkBox_1))
					.addGap(10, 10, 10)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(checkBox_2)
						.addComponent(checkBox_3))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}

}
