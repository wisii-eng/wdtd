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

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.text.UiText;


/**
 * 
 * 设置文字背景的属性的界面panel
 * 
 * @author 闫舒寰
 * @since 2008/09/19
 *
 */
public class FontBackground extends JPanel {
	
	
	ColorComboBox heighLighter;
	/**
	 * Create the panel
	 */
	public FontBackground() {
		super();

		JLabel label;
		label = new JLabel();
		label.setText(UiText.FONT_HEIGHTLIGHT_LABEL);

//		heighLighter = new JComboBox();
		try {
			heighLighter = new ColorComboBox();
		} catch (IncompatibleLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		heighLighter.setText(UiText.FONT_HEIGHTLIGHT_BUTTON);

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText(UiText.FONT_SHADING_LABEL);

		JButton button_1;
		button_1 = new JButton();
		button_1.setText(UiText.FONT_SHADING_BUTTON);
		button_1.setEnabled(false);
		
		
		//添加监听器
		heighLighter.addActionListener(com.wisii.wisedoc.view.ui.manager.ActionFactory.getAction(Font.BACKGROUND_COLOR_ACTION));
		
		//文字背景菜单按钮版面
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1, 1, 1)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label)
							.addGap(10, 10, 10)
							.addComponent(heighLighter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_1)
							.addGap(10, 10, 10)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)))
					.addGap(131, 131, 131))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1, 1, 1)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(heighLighter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(button_1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}

}
