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

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;
import com.wisii.wisedoc.view.ui.parts.text.FontSet;

/**
 * 
 * 该类已经废弃，再存放一阵应该删掉，不过也可以测试该包中的可折叠菜单。
 * 
 * @author Karl
 *
 */
public class TextCollpasePanel extends JTaskPane {
	
	
	JTaskPaneGroup fontSet;
	JTaskPaneGroup fontEffect;
	JTaskPaneGroup fontBackground;
	JTaskPaneGroup fontPosition;
	JTaskPaneGroup fontBorder;
	JTaskPaneGroup fontLink;
	JTaskPaneGroup fontSpecial;

	public TextCollpasePanel() {

		JTaskPane taskPane = new JTaskPane();

		// 字体基本设置小组
		fontSet = new JTaskPaneGroup();
		fontSet.setTitle("字体设置");
		fontSet.add(new FontSet());

		// 仔细效果小组
		fontEffect = new JTaskPaneGroup();
		fontEffect.setTitle("字形设置");

		// 文字背景小组
		fontBackground = new JTaskPaneGroup();
		fontBackground.setExpanded(false);

		fontPosition = new JTaskPaneGroup();
		fontPosition.setExpanded(false);

		fontBorder = new JTaskPaneGroup();
		fontBorder.setExpanded(false);

		fontLink = new JTaskPaneGroup();
		fontLink.setExpanded(false);

		fontSpecial = new JTaskPaneGroup();
		fontSpecial.setExpanded(false);

		taskPane.add(fontSet);
		taskPane.add(fontEffect);
		taskPane.add(fontBackground);
		taskPane.add(fontPosition);
		taskPane.add(fontBorder);
		taskPane.add(fontLink);
		taskPane.add(fontSpecial);

		/*
		 * JScrollPane scroll = new JScrollPane(taskPane);
		 * scroll.setBorder(null);
		 * 
		 * setLayout(new FlowLayout(FlowLayout.LEADING)); add(scroll);
		 * setBorder(null);
		 */

		JScrollPane scroll = new JScrollPane(taskPane);
		scroll.setBorder(null);

		setLayout(new BorderLayout());
		add("Center", scroll);

		setBorder(null);
	
	    
//	    System.out.println(taskPane.getSize().toString() + ": " + this.getHeight());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//正常的tab的测试程序
		JFrame fr = new JFrame("测试用");
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		fr.getContentPane().setLayout(flowLayout);
		fr.setSize(462, 600);
		
		TextCollpasePanel tui = new TextCollpasePanel();
		
		fr.add(tui);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
	}

}
