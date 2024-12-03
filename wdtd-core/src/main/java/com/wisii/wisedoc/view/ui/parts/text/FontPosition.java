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
package com.wisii.wisedoc.view.ui.parts.text;

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
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 
 * 字符位置属性设置的panel
 * 
 * @author 闫舒寰
 * @since 2008/09/19
 *
 */
public class FontPosition extends JPanel {
	
	private JComboBox comboBox_7;
	private JSpinner spinner_3;
	private JComboBox comboBox_6;

	private JComboBox comboBox_4;
	private JComboBox comboBox_2;
	private JSpinner spinner_1;
	private JComboBox comboBox_3;
	private JComboBox comboBox_1;
	private JSpinner spinner;
	private JComboBox comboBox;
	
	//下拉菜单中的文字
	//正常、上标和下标
	private String[] fontPosition = UiText.FONT_POSITION_LIST; 
	//"磅", "厘米", "像素", "英尺"
	private String[] fontMeasurement = UiText.COMMON_MEASUREMENT_LIST;
	
	//"标准", "加宽", "紧缩"
	private String[] fontStretch = UiText.FONT_STRETCH_LIST;
	//文字间距下拉菜单： 正常、自定义
	private String[] fontSpace = UiText.FONT_SPACE_LIST;
	
	//tab中的文字
	//数值：
	String value = UiText.COMMON_VALUE_LABEL;
	//单位：
	String measurement = UiText.COMMON_MEASUREMENT_LABEL;
	
	/**
	 * Create the panel
	 */
	public FontPosition() {
		super();
//		setPreferredSize(new Dimension(237, 140));
		//文字位置下拉菜单
		JLabel label;
		label = new JLabel();
		label.setText(UiText.FONT_POSITION_LABEL);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(fontPosition));
		
		
		//文字位置值
		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(value);
		
		spinner = new JSpinner();
		spinner.setEnabled(false);
		
		
		//文字位置单位
		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(measurement);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(fontMeasurement));
		comboBox_1.setEnabled(false);
		
		//字符缩放
		JLabel font_stretch;
		font_stretch = new JLabel();
		font_stretch.setText(UiText.FONT_STRETCH_LABEL);

		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(fontSpace));

		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText(value);

		spinner_1 = new JSpinner();
		spinner_1.setEnabled(false);

		JLabel label_5;
		label_5 = new JLabel();
		label_5.setText(measurement);

		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(fontMeasurement));
		comboBox_2.setEnabled(false);
		
		//字符缩放
		JLabel label_6;
		label_6 = new JLabel();
		label_6.setText(UiText.FONT_SPACE_LABEL);
		
		comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(fontStretch));
		
		
		
		//位置样式
		//
		
		//添加监听器
		//用来传递一组action的，这一组分别是文字位置的数值和单位，第一个元素为数值，第二个元素为单位
		List<Object> textPositionArrayList = new ArrayList<Object>();
		textPositionArrayList.add(spinner);
		textPositionArrayList.add(comboBox_1);		
		//文字位置监听器
		comboBox.addActionListener(ActionFactory.getAction(Font.POSITION_ACTION, textPositionArrayList));
//		spinner.addChangeListener(com.wisii.wisedoc.view.ui.manager.ActionFactory.getInstance(ActionConstants.FONT_POSITION_INPUT_ACTION, spinner, comboBox_1));
		spinner.addChangeListener((ChangeListener)com.wisii.wisedoc.view.ui.manager.ActionFactory.getAction(Font.POSITION_INPUT_ACTION, spinner, comboBox_1));
		comboBox_1.addActionListener(com.wisii.wisedoc.view.ui.manager.ActionFactory.getAction(Font.POSITION_INPUT_ACTION, spinner, comboBox_1));
		/*spinner.addChangeListener(ActionFactory.getAction(FONT_POSITION_INPUT_ACTION, textPositionArrayList));
		comboBox_1.addActionListener(ActionFactory.getAction(FONT_POSITION_INPUT_ACTION, textPositionArrayList));*/
		
		//词间距那一组按钮
		List<Object> textSpaceArrayList = new ArrayList<Object>();
		textSpaceArrayList.add(spinner_1);
		textSpaceArrayList.add(comboBox_2);
		//词间距监听器
		comboBox_3.addActionListener(ActionFactory.getAction(Font.SPACE_ACTION, textSpaceArrayList));		
		spinner_1.addChangeListener((ChangeListener)ActionFactory.getAction(Font.SPACE_INPUT_ACTION, textSpaceArrayList));
		comboBox_2.addActionListener(ActionFactory.getAction(Font.SPACE_INPUT_ACTION, textSpaceArrayList));
		
		//字符缩放监听器
		comboBox_4.addActionListener(ActionFactory.getAction(Font.STRETCH_ACTION));
		
		
		//词间距
		comboBox_6 = new JComboBox();
		comboBox_6.setModel(new DefaultComboBoxModel(UiText.FONT_WORD_SPACE_LIST));

		JLabel label_4_1;
		label_4_1 = new JLabel();
		label_4_1.setText(UiText.COMMON_VALUE_LABEL);

		spinner_3 = new JSpinner();
		spinner_3.setEnabled(false);

		JLabel label_5_1;
		label_5_1 = new JLabel();
		label_5_1.setText(UiText.COMMON_MEASUREMENT_LABEL);

		JLabel label_6_1;
		label_6_1 = new JLabel();
		label_6_1.setText(UiText.FONT_WORD_SPACE_LABEL);

		comboBox_7 = new JComboBox();
		comboBox_7.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));
		comboBox_7.setEnabled(false);
		
		//词间距那一组按钮
		List<Object> wordSpaceArrayList = new ArrayList<Object>();
		wordSpaceArrayList.add(spinner_3);
		wordSpaceArrayList.add(comboBox_7);
		//词间距监听器
		comboBox_6.addActionListener(ActionFactory.getAction(Font.WORD_SPACE_ACTION, wordSpaceArrayList));		
		spinner_3.addChangeListener((ChangeListener)ActionFactory.getAction(Font.WORD_SPACE_INPUT_ACTION, wordSpaceArrayList));
		comboBox_7.addActionListener(ActionFactory.getAction(Font.WORD_SPACE_INPUT_ACTION, wordSpaceArrayList));
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(label_6)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(label)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
							.addGap(10, 10, 10)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(label_1)
								.addComponent(label_4))
							.addGap(1, 1, 1)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
							.addGap(10, 10, 10)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_5))
							.addGap(1, 1, 1)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
						.addComponent(label_6_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(comboBox_6, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(10, 10, 10)
							.addComponent(label_4_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addGap(1, 1, 1)
							.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(10, 10, 10)
							.addComponent(label_5_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addGap(15, 15, 15)
							.addComponent(comboBox_7, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_stretch))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(label)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(label_2)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(label_6)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_4)
						.addComponent(label_5)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(label_6_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(6, 6, 6)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(comboBox_6, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3, 3, 3)
							.addComponent(label_4_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3, 3, 3)
							.addComponent(label_5_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addComponent(comboBox_7, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(font_stretch)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}

}
