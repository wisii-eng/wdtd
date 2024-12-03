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
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import com.wisii.wisedoc.swing.ui.JComboBoxTest;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.text.UiText;


/**
 * 字体基本属性设置面板，包括四种属性，分别是字体、字号、字形和文字颜色
 * 
 * @author 闫舒寰
 * @since	2008/9/18
 *
 */
public class FontSet extends JPanel {
	
	//该界面所设置的属性类型
			
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
	
	ColorComboBox color_comboBox;
	
	//测试用，文字样式预览
	Color fontColor;

	/**
	 * Create the panel
	 */
	public FontSet() {
		super();
		setMinimumSize(new Dimension(170, 100));
		setMaximumSize(new Dimension(190,120));
		setPreferredSize(new Dimension(180,110));
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
		//test
//		FontFamilyUpdate.getInstance().setCombo(font_size_comboBox);
		
		/*font_size_comboBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e);
			}
			
		});*/
		/*ComboBoxModel cbm = font_size_comboBox.getModel();
		cbm.addListDataListener(new ListDataListener(){
			
			@Override
			public void contentsChanged(ListDataEvent e) {
				// TODO Auto-generated method stub
				ComboBoxModel cm = (ComboBoxModel) e.getSource();
				System.out.println(cm.getSize());
				
			}

			@Override
			public void intervalAdded(ListDataEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void intervalRemoved(ListDataEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});*/
		

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
		try {
			color_comboBox = new ColorComboBox();
//			color_comboBox.setLightWeightPopupEnabled(false);
		} catch (IncompatibleLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
//		button.setText(UiText.FONT_COLOR_BUTTON);
		
		/*previewArea = new PreviewArea(comboBox, comboBox_2, comboBox_1, fontColor);
		previewArea.setText("test area");
		previewArea.setBounds(10, 253, 116, 66);
		add(previewArea);*/
		
				
		//字号监听器
		font_size_comboBox.addActionListener(ActionFactory.getAction(Font.SIZE_ACTION));
		//字体监听器
		font_family_comboBox.addActionListener(ActionFactory.getAction(Font.FAMILY_ACTION));
		//字形监听器
		font_style_comboBox.addActionListener(ActionFactory.getAction(Font.STYLE_ACTION));
		//颜色监听器
		color_comboBox.addActionListener(ActionFactory.getAction(Font.COLOR_ACTION));
		
//		font_size_comboBox.setSelectedItem("5");
//		font_size_comboBox.addPropertyChangeListener(new PropertyChangeTest());
//		font_size_comboBox.addItemListener(new ItemListTest());
		
		//字体设置grouplayout布局。第一版的groupLayout
		/*final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(font_style)
						.addComponent(font_family)
						.addComponent(font_style_comboBox, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_family_comboBox, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
					.addGap(21, 21, 21)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(font_size_comboBox, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_size)
						.addComponent(font_color)
						.addComponent(button))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(font_family)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(font_family_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(font_style)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(font_style_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(font_size)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(font_size_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(font_color)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		setLayout(groupLayout);*/
		
		//第二版手写的grouplayout
		/*final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setHorizontalGroup(
				groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(font_family)
						.addComponent(font_family_comboBox)
						.addComponent(font_style)
						.addComponent(font_style_comboBox)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(font_size)
						.addComponent(font_size_comboBox)
						.addComponent(font_color)
						.addComponent(button)					
		);
		groupLayout.setVerticalGroup(
				groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE))
						.addComponent(font_family)
						.addComponent(font_size)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE))
						.addComponent(font_family_comboBox)
						.addComponent(font_size_comboBox)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE))
						.addComponent(font_style)
						.addComponent(font_color)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE))
						.addComponent(font_style_comboBox)
						.addComponent(button)
		);*/
		
		//第三版可动态变化的Grouplayout
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1, 1, 1)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(font_family, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_style)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(font_color))
						.addComponent(font_size))
					.addGap(4, 4, 4)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(color_comboBox, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_size_comboBox, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_family_comboBox, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_style_comboBox, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(30, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1, 1, 1)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(font_family, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_family_comboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(font_size, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_size_comboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(font_style, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(font_style_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(font_color)
						.addComponent(color_comboBox, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
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
	
	

	/**
	 * 测试用
	 * 
	 * @param args
	 */
	public static void createAndShowGUI() {
		// TODO Auto-generated method stub
		JFrame fr = new JFrame("测试用");
//		fr.setSize(800, 600);
		
		FontSet tui = new FontSet();
		
		fr.add(tui);
//		System.out.println(fr.getComponent(0).getPreferredSize());
		fr.setSize(fr.getComponent(0).getPreferredSize());
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
	}
	
	public static void main(String[] args){
		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
		
	}

}
