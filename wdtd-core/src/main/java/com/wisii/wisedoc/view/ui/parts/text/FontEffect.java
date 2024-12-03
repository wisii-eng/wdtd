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

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.wisii.wisedoc.view.ui.text.UiText;


/**
 * 
 * 文字效果的属性panel
 * 
 * @author 闫舒寰
 * @since 2008/09/19
 *
 */
public class FontEffect extends JPanel {	
	
	/**
	 * Create the panel
	 */
	public FontEffect() {
		super();
		
		/*setMinimumSize(new Dimension(170, 100));
		setMaximumSize(new Dimension(190,120));
		setPreferredSize(new Dimension(180,110));*/
		
		//下划线
		JCheckBox underline_checkBox;
		//创建下划线checkbox并且添加动作
		underline_checkBox = new JCheckBox(/*actionFactory.createAction(ActionFactoryConstance.FONT_UNDERLINE_ACTION)*/);
		underline_checkBox.setText(UiText.FONT_UNDERLINE);

		//删除线
		JCheckBox checkBox_1;
		checkBox_1 = new JCheckBox(/*actionFactory.createAction(ActionFactoryConstance.FONT_LINE_THROUGH_ACTION)*/);
		checkBox_1.setText(UiText.FONT_LINETHROUGH);

		//上划线
		JCheckBox checkBox_2;
		checkBox_2 = new JCheckBox(/*actionFactory.createAction(ActionFactoryConstance.FONT_OVERLINE_ACTION)*/);
		checkBox_2.setText(UiText.FONT_OVERLINE);
		
		//以这三个为一组添加到一个监听器中，以方便互相设置
		List<Object> fontEffects = new ArrayList<Object>();
		fontEffects.add(underline_checkBox);
		fontEffects.add(checkBox_1);
		fontEffects.add(checkBox_2);
		/*checkBox_1.addActionListener(ActionFactory.getAction(Font.EFFECT_ACTION, fontEffects));
		checkBox_2.addActionListener(ActionFactory.getAction(Font.EFFECT_ACTION, fontEffects));
		underline_checkBox.addActionListener(ActionFactory.getAction(Font.EFFECT_ACTION, fontEffects));*/
		
		
		final GroupLayout groupLayout = new GroupLayout((JComponent) this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1, 1, 1)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(underline_checkBox)
							.addGap(5, 5, 5)
							.addComponent(checkBox_2))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(checkBox_1)))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1, 1, 1)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(underline_checkBox)
						.addComponent(checkBox_2))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(checkBox_1)
					.addContainerGap(8, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		//
	}	
	
	/**
	 * 测试用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame fr = new JFrame("测试用");
//		fr.setSize(800, 600);
		
		FontEffect tui = new FontEffect();
		
		fr.add(tui);
//		System.out.println(fr.getComponent(0).getPreferredSize());
		fr.setSize(fr.getComponent(0).getPreferredSize());
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
	}

}
