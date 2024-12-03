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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.event.ChangeListener;

import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.parts.dialogs.BorderAndBackGroundDialog;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 文字边框设置面板
 * 
 * @author	闫舒寰
 * @version 0.1 2008/10/13
 */
public class FontBorder extends JPanel {
	
	
	private JComboBox comboBox_1;
	private JComboBox comboBox;
	private JSpinner spinner;
	
	ColorComboBox BorderColorComboBox;
	/**
	 * Create the panel
	 */
	public FontBorder() {
		super();

		JCheckBox checkBox;
		checkBox = new JCheckBox();
		checkBox.setText(UiText.PARAGRAPH_BORDER_SET_BUTTON);

		JLabel label;
		label = new JLabel();
		label.setText(UiText.PARAGRAPH_BORDER_WIDTH_LABEL);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.5));

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(UiText.COMMON_MEASUREMENT_LABEL);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(UiText.COMMON_MEASUREMENT_LIST));

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText(UiText.PARAGRAPH_BORDER_COLOR_LABEL);

		//设置颜色下拉菜单
		try {
			BorderColorComboBox = new ColorComboBox();
		} catch (IncompatibleLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		button.setText(UiText.PARAGRAPH_BORDER_COLOR_BUTTON);

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText(UiText.PARAGRAPH_BORDER_STYLE_LABEL);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(UiText.PARAGRAPH_BORDER_STYLE_LIST));
//		comboBox_1.setSelectedIndex(4);

		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText(UiText.PARAGRAPH_BORDER_DETAIL_SET_LABEL);

		JButton button_1;
		button_1 = new JButton();
		button_1.setText(UiText.PARAGRAPH_BORDER_DETAIL_SET_BUTTON);
		
		button_1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new BorderAndBackGroundDialog(ActionType.INLINE);
			}
			
		});
		
		spinner.setEnabled(false);
		comboBox.setEnabled(false);
		BorderColorComboBox.setEnabled(false);
		comboBox_1.setEnabled(false);
		button_1.setEnabled(false);
		
		//用来传递一组action的，这一组分别是文字位置的数值和单位，第0个元素为数值，第1个元素为单位，第2个元素是颜色，第三个元素是样式，第四个元素是所有属性按钮
		List<Object> paragraphBorder = new ArrayList<Object>();
		paragraphBorder.add(spinner);
		paragraphBorder.add(comboBox);
		paragraphBorder.add(BorderColorComboBox);
		paragraphBorder.add(comboBox_1);
		paragraphBorder.add(button_1);
		/*
		 * 这里和段落的边框设置属性一样，暂时先用段落边框的动作
		 */
		//单选是否设置边框
		checkBox.addActionListener(ActionFactory.getAction(Paragraph.BORDER_SETTING_ACTION, paragraphBorder));
		//添加边框宽度监听器
		spinner.addChangeListener((ChangeListener)ActionFactory.getAction(Paragraph.BORDER_WIDTH_ACTION, paragraphBorder));
		comboBox.addActionListener(ActionFactory.getAction(Paragraph.BORDER_WIDTH_ACTION, paragraphBorder));
		BorderColorComboBox.addActionListener(ActionFactory.getAction(Paragraph.BORDER_COLOR_ACTION, paragraphBorder));
		comboBox_1.addActionListener(ActionFactory.getAction(Paragraph.BORDER_STYLE_ACTION, paragraphBorder));
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(checkBox)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(label)
								.addComponent(label_2))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(label_1)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10, 10, 10)
									.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(BorderColorComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(button_1)))))
						.addComponent(label_3)
						.addComponent(label_4))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(checkBox)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(BorderColorComboBox))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_3)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_4)
						.addComponent(button_1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
