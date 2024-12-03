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

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 
 * 段落对齐方式面板
 * 
 * @author 闫舒寰
 * @since 2008/09/26
 *
 */
public class ParagraphDisplayAlign extends JPanel {

	
	private JComboBox comboBox_3;
	private JComboBox comboBox_2;
	private JComboBox comboBox_1;
	private JComboBox comboBox;
	/**
	 * Create the panel
	 */
	public ParagraphDisplayAlign() {
		super();
		

		JLabel label;
		label = new JLabel();
		label.setText(UiText.PARAGRAPH_TEXT_ALIGN_LABEL);

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(UiText.PARAGRAPH_DISPLAY_ALIGN_LABEL);

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(UiText.TEXT_BASELINE_DISPLAY_ALIGN_LABEL);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(UiText.PARAGRAPH_TEXT_ALIGN_LIST));
		
		

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText(UiText.PARAGRAPH_TEXT_ALIGN_LAST_LABEL);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(UiText.PARAGRAPH_TEXT_ALIGN_LIST));
		comboBox_1.setEnabled(false);
		
		//用来传递一组action的，这一组分别是文字位置的数值和单位，第一个元素为数值，第二个元素为单位
		List<Object> textAlign = new ArrayList<Object>();
		textAlign.add(comboBox);
		textAlign.add(comboBox_1);
//		comboBox.addActionListener(actionFactory.createAction(ActionConstants.PARAGRAPH_TEXT_ALIGN, textAlign));
//		comboBox_1.addActionListener(actionFactory.createAction(ActionConstants.PARAGRAPH_TEXT_ALIGN_LAST));
		

		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(UiText.PARAGRAPH_DISPLAY_ALIGN_LIST));
		//为段落间对齐方式添加监听器
//		comboBox_2.addActionListener(actionFactory.createAction(ActionConstants.PARAGRAPH_DISPLAY_ALLIGN));

		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(UiText.TEXT_BASELINE_DISPLAY_ALIGN_LIST));
		comboBox_3.setEnabled(false);
		//为文字基线对齐添加监听器
		
		
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
								.addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label_3))
								.addComponent(label, GroupLayout.Alignment.LEADING))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_1)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2)
						.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(label)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(label_1)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(label_2)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}

}
