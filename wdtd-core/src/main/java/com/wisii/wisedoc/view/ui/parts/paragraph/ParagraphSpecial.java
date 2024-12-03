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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.view.ui.text.UiText;

public class ParagraphSpecial extends JPanel {

	/**
	 * Create the panel
	 */
	public ParagraphSpecial() {
		super();

		JLabel label;
		label = new JLabel();
		label.setText(UiText.PARAGRAPH_PREFIX_SINK_LABEL);

		JCheckBox checkBox;
		checkBox = new JCheckBox();
		checkBox.setText(UiText.PARAGRAPH_PREFIX_SINK_BUTTON);

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(UiText.PARAGRAPH_CHINA_STYLE_LABEL);

		JCheckBox checkBox_1;
		checkBox_1 = new JCheckBox();
		checkBox_1.setText(UiText.PARAGRAPH_CHINA_FORBIDDEN_LABEL);

		JCheckBox checkBox_2;
		checkBox_2 = new JCheckBox();
		checkBox_2.setText(UiText.PARAGRAPH_CHINA_PUNCTUATION_OUT_BUTTON);

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(UiText.PARAGRAPH_CHAR_SPACE_LABEL);

		JCheckBox checkBox_3;
		checkBox_3 = new JCheckBox();
		checkBox_3.setText(UiText.PARAGRAPH_AUTO_ADJUST_CN_EN_BUTTON);

		JCheckBox checkBox_4;
		checkBox_4 = new JCheckBox();
		checkBox_4.setText(UiText.PARAGRAPH_AUTO_ADJUST_CN_NUM_BUTTON);
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label)
							.addGap(13, 13, 13)
							.addComponent(checkBox))
						.addComponent(label_1)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(checkBox_1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(checkBox_2))
						.addComponent(label_2)
						.addComponent(checkBox_3)
						.addComponent(checkBox_4))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(checkBox))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(label_1)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(checkBox_1)
						.addComponent(checkBox_2))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(label_2)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(checkBox_3)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(checkBox_4)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}

}
