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

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.text.JTextComponent;

import com.wisii.wisedoc.swing.ui.JComboBoxTest;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.text.UiText;

public class FontSetPanel extends JPanel {

			
	//字体样式
//	String fontNames[] = UiText.FONT_FAMILY_NAMES_LIST;
	String fontNames[] = backSort(UiText.FONT_FAMILY_NAMES_LIST);
	//字形样式
	String fontStyle[] = UiText.FONT_STYLE_LIST;
	
	//字号列表
	String[] fontSize = UiText.FONT_SIZE_LIST;
	
	

	private JComboBox font_style_comboBox;
	private JComboBox font_size_comboBox;
	private JComboBox font_family_comboBox;
	
	JComboBox color_comboBox;
	
	//测试用，文字样式预览
	Color fontColor;

	/**
	 * Create the panel
	 */
	public FontSetPanel() {
		super();
		/*setMinimumSize(new Dimension(170, 100));
		setMaximumSize(new Dimension(190,120));
		setPreferredSize(new Dimension(180,110));*/
		//设置可折叠的菜单不能用null 布局
//		setLayout();
		
		//该Panel用GroupLayout布局，在后面给出	
		
		//字体标签
		JLabel font_family;
		font_family = new JLabel();		
		font_family.setText(UiText.FONT_FAMILY_NAMES_LABEL);
		//字体设置
		font_family_comboBox = new JComboBox();
		font_family_comboBox.setModel(new DefaultComboBoxModel(fontNames));
		font_family_comboBox.setEditable(true);
		//为字体设置添加可索引菜单
		JTextComponent editor = (JTextComponent) font_family_comboBox.getEditor().getEditorComponent();
		editor.setDocument(new JComboBoxTest(font_family_comboBox));
		
		
		//字号标签
		JLabel font_size;
		font_size = new JLabel();
		font_size.setText(UiText.FONT_SIZE_LABEL);
		//字号设置
		font_size_comboBox = new JComboBox();
		font_size_comboBox.setModel(new DefaultComboBoxModel(fontSize));
		font_size_comboBox.setEditable(true);
		

		//字形标签
		JLabel font_style;
		font_style = new JLabel();
		font_style.setText(UiText.FONT_STYLE_LABEL);
		//字形设置
		font_style_comboBox = new JComboBox();
		font_style_comboBox.setModel(new DefaultComboBoxModel(fontStyle));
		font_style_comboBox.setEditable(true);
		
		//颜色标签
		JLabel font_color;
		font_color = new JLabel();
		font_color.setText(UiText.FONT_COLOR_LABEL);
		
		//颜色设置
		color_comboBox = new JComboBox();
		/*try {
			color_comboBox = new ColorComboBox();
		} catch (IncompatibleLookAndFeelException e) {
			e.printStackTrace();
		}*/			
				
		//字号监听器
		font_size_comboBox.addActionListener(ActionFactory.getAction(Font.SIZE_ACTION));
		//字体监听器
		font_family_comboBox.addActionListener(ActionFactory.getAction(Font.FAMILY_ACTION));
		//字形监听器
		font_style_comboBox.addActionListener(ActionFactory.getAction(Font.STYLE_ACTION));
		//颜色监听器
		color_comboBox.addActionListener(ActionFactory.getAction(Font.COLOR_ACTION));			

		
		//第三版可动态变化的Grouplayout
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1, 1, 1)
							.addComponent(font_family, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addComponent(font_family_comboBox, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
					.addGap(20, 20, 20)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(font_size)
						.addComponent(font_size_comboBox, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
					.addGap(20, 20, 20)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(font_style)
						.addComponent(font_style_comboBox, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
					.addGap(20, 20, 20)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(color_comboBox, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_color))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(font_family, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_size, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_style, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_color))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(font_family_comboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addComponent(font_size_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(font_style_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(color_comboBox, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
	
	/*
	 * 逆序显示字体，暂时为了方便选择中文字体
	 */
	private String[] backSort(String[] a){
		
		String[] b = new String[a.length];
		
		for (int i = a.length - 1, j = 0; i >= 0 & j < a.length; i--, j++) {
			b[j] = a[i];
		}
		
		return b;
	}
}
