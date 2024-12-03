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
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 
 * 段落的缩进属性面板
 * 
 * @author 闫舒寰
 * @since 2008/09/26
 *
 */
public class ParagraphIndent extends JPanel {

	private JSpinner spinner_3;
	
	private JComboBox comboBox_3;
	private JSpinner spinner_2;
	private JComboBox comboBox_2;
	private JComboBox comboBox_1;
	private JSpinner spinner_1;
	private JComboBox comboBox;
	private JSpinner spinner;
	/**
	 * Create the panel
	 */
	public ParagraphIndent() {
		super();

		JLabel label;
		label = new JLabel();
		label.setText(UiText.PARAGRAPH_START_INDENT_LABEL);

		spinner = new JSpinner();

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(UiText.COMMON_MEASUREMENT_LABEL);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		
		//用来传递一组action的，这一组分别是文字位置的数值和单位，第一个元素为数值，第二个元素为单位
		List<Object> startIndentArrayList = new ArrayList<Object>();
		startIndentArrayList.add(spinner);
		startIndentArrayList.add(comboBox);
		//为左缩进两个按钮添加动作
//		spinner.addChangeListener(actionFactory.createAction(ActionConstants.PARAGRAPH_START_INDENT, startIndentArrayList));
//		comboBox.addActionListener(actionFactory.createAction(ActionConstants.PARAGRAPH_START_INDENT, startIndentArrayList));
		

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(UiText.PARAGRAPH_END_INDENT_LABEL);

		spinner_1 = new JSpinner();

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText(UiText.COMMON_MEASUREMENT_LABEL);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		
		//用来传递一组action的，这一组分别是文字位置的数值和单位，第一个元素为数值，第二个元素为单位
		List<Object> endIndentArrayList = new ArrayList<Object>();
		endIndentArrayList.add(spinner_1);
		endIndentArrayList.add(comboBox_1);
		//为右缩进两个按钮添加动作
//		spinner_1.addChangeListener(actionFactory.createAction(ActionConstants.PARAGRAPH_END_INDENT, endIndentArrayList));
//		comboBox_1.addActionListener(actionFactory.createAction(ActionConstants.PARAGRAPH_END_INDENT, endIndentArrayList));
		

		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText(UiText.PARAGRAPH_SPECIAL_INDENT_LABEL);

		
		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(UiText.PARAGRAPH_SPECIAL_INDENT_LIST));

		JLabel label_5;
		label_5 = new JLabel();
		label_5.setText(UiText.PARAGRAPH_FIRST_LINE_INDENT_LABEL);

		spinner_2 = new JSpinner();
		spinner_2.setEnabled(false);

		JLabel label_6;
		label_6 = new JLabel();
		label_6.setText(UiText.COMMON_MEASUREMENT_LABEL);

		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		comboBox_3.setEnabled(false);	

		JLabel label_7;
		label_7 = new JLabel();
		label_7.setText(UiText.PARAGRAPH_INDENT_VALUE_LABEL);

		spinner_3 = new JSpinner();
		spinner_3.setEnabled(false);
		
		//用来传递一组action的，这一组分别是文字位置的数值和单位，第一个元素为数值，第二个元素为单位
		List<Object> specialIndent = new ArrayList<Object>();
		specialIndent.add(spinner_2);		
		specialIndent.add(spinner_3);
		specialIndent.add(comboBox_3);
		//悬挂缩进下拉菜单的动作
//		comboBox_2.addActionListener(actionFactory.createAction(ActionConstants.PARAGRAPH_SPECIAL_INDENT, specialIndent));
//		spinner_2.addChangeListener(actionFactory.createAction(ActionConstants.PARAGRAPH_SPECIAL_INDENT_INPUT_ACTION, specialIndent));
//		spinner_3.addChangeListener(actionFactory.createAction(ActionConstants.PARAGRAPH_SPECIAL_INDENT_INPUT_ACTION, specialIndent));
//		comboBox_3.addActionListener(actionFactory.createAction(ActionConstants.PARAGRAPH_SPECIAL_INDENT_INPUT_ACTION, specialIndent));
		
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label_1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_2)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label_3)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_4)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(label_5)
								.addComponent(label_7))
							.addGap(11, 11, 11)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_6)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(97, 97, 97))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_4)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_5)
						.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_7)
						.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_6)
						.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}

}
