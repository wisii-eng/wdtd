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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.text.UiText;


/**
 * 
 * 行间距和段间距设置面板
 * 
 * @author	闫舒寰
 * @since	2008/09/27
 */
public class ParagraphSpace extends JPanel {

	private JComboBox comboBox_4;
	
	private JComboBox comboBox_7;
	private JSpinner spinner_7;
	private JComboBox comboBox_3;
	private JSpinner spinner_2;
	private JComboBox comboBox_2;
	private JSpinner spinner_1;
	private JComboBox comboBox_1;
	private JSpinner spinner;
	private JComboBox comboBox;
	/**
	 * Create the panel
	 */
	public ParagraphSpace() {
		super();

		JLabel label;
		label = new JLabel();
		label.setText(UiText.PARAGRAPH_INLINE_SPACE_SET_LABEL);

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(UiText.PARAGRAPH_INLINE_SPACE_LABEL);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(UiText.PARAGRAPH_INLINE_SPACE_LIST));

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(UiText.COMMON_SET_VALUE_LABEL);

		spinner = new JSpinner();
		spinner.setEnabled(false);

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText(UiText.COMMON_MEASUREMENT_LABEL);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		comboBox_1.setEnabled(false);
		
		//用来传递一组action的，这一组分别是文字位置的数值和单位，第一个元素为数值，第二个元素为单位
		List<Object> lineSpace = new ArrayList<Object>();
		lineSpace.add(spinner);
		lineSpace.add(comboBox_1);
		comboBox.addActionListener(ActionFactory.getAction(Paragraph.LINE_SPACE_ACTION, lineSpace));
		
		//行高设置
		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText(UiText.PARAGRAPH_LINE_HEIGHT_LABEL);

		spinner_1 = new JSpinner();

		JLabel label_5;
		label_5 = new JLabel();
		label_5.setText(UiText.COMMON_MEASUREMENT_LABEL);

		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		
		//行高策略设置
		JLabel label_9;
		label_9 = new JLabel();
		label_9.setText(UiText.PARAGRAPH_LINE_STACKING_STRATEGY_LABEL);

		comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(UiText.PARAGRAPH_LINE_STACKING_STRATEGY_LIST));
		
		//用来传递一组action的，这一组分别是文字位置的数值和单位，第一个元素为数值，第二个元素为单位，第三个元素为行高策略设置
		List<Object> lineHeight = new ArrayList<Object>();
		lineHeight.add(spinner_1);
		lineHeight.add(comboBox_2);
		lineHeight.add(comboBox_4);
		spinner_1.addChangeListener((ChangeListener)ActionFactory.getAction(Paragraph.LINE_HEIGHT_ACTION, lineHeight));
		comboBox_2.addActionListener(ActionFactory.getAction(Paragraph.LINE_HEIGHT_ACTION, lineHeight));
		comboBox_4.addActionListener(ActionFactory.getAction(Paragraph.LINE_HEIGHT_STRATEGY_ACTION, lineHeight));

		JLabel label_6;
		label_6 = new JLabel();
		label_6.setText(UiText.PARAGRAPH_BLOCK_SPACE_SET_LABEL);

		JLabel label_7;
		label_7 = new JLabel();
		label_7.setText(UiText.PARAGRAPH_BLOCK_SPACE_BEFORE_LABEL);

		spinner_2 = new JSpinner();

		JLabel label_8;
		label_8 = new JLabel();
		label_8.setText(UiText.COMMON_MEASUREMENT_LABEL);

		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		
		//用来传递一组action的，这一组分别是文字位置的数值和单位，第一个元素为数值，第二个元素为单位
		List<Object> lineSpaceBefore = new ArrayList<Object>();
		lineSpaceBefore.add(spinner_2);
		lineSpaceBefore.add(comboBox_3);
		spinner_2.addChangeListener((ChangeListener)ActionFactory.getAction(Paragraph.LINE_SPACE_BEFORE_ACTION, lineSpaceBefore));
		comboBox_3.addActionListener(ActionFactory.getAction(Paragraph.LINE_SPACE_BEFORE_ACTION, lineSpaceBefore));
		
		JLabel label_7_1;
		label_7_1 = new JLabel();
		label_7_1.setText(UiText.PARAGRAPH_BLOCK_SPACE_AFTER_LABEL);

		spinner_7 = new JSpinner();

		JLabel label_8_1;
		label_8_1 = new JLabel();
		label_8_1.setText(UiText.COMMON_MEASUREMENT_LABEL);

		comboBox_7 = new JComboBox();
		comboBox_7.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		
		//用来传递一组action的，这一组分别是文字位置的数值和单位，第一个元素为数值，第二个元素为单位
		List<Object> lineSpaceAfter = new ArrayList<Object>();
		lineSpaceAfter.add(spinner_7);
		lineSpaceAfter.add(comboBox_7);
		spinner_7.addChangeListener((ChangeListener)ActionFactory.getAction(Paragraph.LINE_SPACE_AFTER_ACTION, lineSpaceAfter));
		comboBox_7.addActionListener(ActionFactory.getAction(Paragraph.LINE_SPACE_AFTER_ACTION, lineSpaceAfter));
		
		

		JButton button;
		button = new JButton();
		button.setText(UiText.PARAGRAPH_BLOCK_DETIAL_SET_BUTTON);
		button.setEnabled(false);
		
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(label)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_2)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label_3)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_4)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label_5)
							.addGap(15, 15, 15)
							.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_9)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_6)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_7)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_7_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(spinner_7, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
							.addGap(10, 10, 10)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_8_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(comboBox_7, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_8)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addComponent(button))
					.addGap(10, 10, 10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(label)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_4)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_5)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_9)
						.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(label_6)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_7)
						.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_8)
						.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_7_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_7, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_8_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_7, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(button)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}

}
