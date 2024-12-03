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
package com.wisii.wisedoc.view.ui.collapse;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


/**
 * 
 * 以后可以自定义可折叠的包用以下的方法，可以直接加工该包中的JCollapseiblePane.java类，就可以实现动态折叠效果
 * 
 * 
 * @author Karl
 *
 */
public class TestCollapse {

	// 测试用
	public static void main(String[] args) {

		JCollapsiblePane cp = new JCollapsiblePane();
		// JCollapsiblePane can be used like any other container
		cp.setLayout(new BorderLayout());
		// the Controls panel with a textfield to filter the tree
		JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
		/*controls.add(new JLabel("Search:"));
		controls.add(new JTextField(10));
		controls.add(new JButton("Refresh"));*/
//		controls.add(new TextPropertyEditUI());
		controls.setBorder(new TitledBorder("Filters"));
		cp.add("Center", controls);

		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());		
		// Put the "Controls" first
		frame.add("North", cp);
		
		
		/*// Then the tree - we assume the Controls would somehow filter the tree
		JScrollPane scroll = new JScrollPane(new JTree());
		frame.add("Center", scroll);*/
		
		
		
		// Show/hide the "Controls"
		JButton toggle = new JButton(cp.getActionMap().get(JCollapsiblePane.TOGGLE_ACTION));
		toggle.setText("Show/Hide Search Panel");
		frame.add("South", toggle);

		frame.pack();
		frame.setVisible(true);

		/*
		 * //测试JTaskPaneGroup JFrame frame = new JFrame();
		 *  // a container to put all JTaskPaneGroup together
		 * //一个整体的容器用来放置所有的可折叠的条 JTaskPane taskPaneContainer = new JTaskPane();
		 * 
		 * //这里创建单独的一组 JTaskPaneGroup fontSet = new JTaskPaneGroup();
		 * fontSet.setTitle("字体设置");
		 * 
		 * JLabel label = new JLabel(); label.setText("测试"); label.setBounds(10,
		 * 10, 54, 14); fontSet.add(label);
		 * 
		 * JComboBox comboBox = new JComboBox(); //字体样式 String fontNames[] =
		 * GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		 * comboBox.setModel(new DefaultComboBoxModel(fontNames));
		 * comboBox.setBounds(10, 50, 90, 20); fontSet.add(comboBox);
		 * 
		 * taskPaneContainer.add(fontSet);
		 *  // put the action list on the left frame.add(taskPaneContainer,
		 * BorderLayout.EAST); frame.pack(); frame.setVisible(true);
		 */
	}

}
