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
package com.wisii.wisedoc.view.ui.parts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;
import com.wisii.wisedoc.view.ui.parts.paragraph.ParagraphBackground;
import com.wisii.wisedoc.view.ui.parts.paragraph.ParagraphBorder;
import com.wisii.wisedoc.view.ui.parts.paragraph.ParagraphDisplayAlign;
import com.wisii.wisedoc.view.ui.parts.paragraph.ParagraphIndent;
import com.wisii.wisedoc.view.ui.parts.paragraph.ParagraphPagination;
import com.wisii.wisedoc.view.ui.parts.paragraph.ParagraphSpace;
import com.wisii.wisedoc.view.ui.parts.paragraph.ParagraphSpecial;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 侧边栏段落属性可滚动菜单
 * 
 * @author	闫舒寰
 * @since	2008/09/25
 */
public class ParagraphPropertyCollapseUI extends JTaskPane{
	
	/**
	 * 段落对齐方式面板
	 */
	JTaskPaneGroup paragraphAlign;
	/**
	 * 段落缩进
	 */
	JTaskPaneGroup paragraphIndent;
	/**
	 * 段落间距
	 */
	JTaskPaneGroup paragraphSpace;
	/**
	 * 段落背景设置
	 */
	JTaskPaneGroup paragraphBackground;
	/**
	 * 段落边框
	 */
	JTaskPaneGroup paragraphBorder;
	/**
	 * 换行和分页
	 */
	JTaskPaneGroup pagination;
	/**
	 * 特殊段落格式
	 */
	JTaskPaneGroup paragraphSpecial;
	/**
	 * 所有段落属性设置按钮
	 */
	JTaskPaneGroup paragraphAllProperty;

	public ParagraphPropertyCollapseUI() {

		JTaskPane taskPane = new JTaskPane();
//		taskPane.setLayout(new BorderLayout());
//		setLayout(new BorderLayout());
		
		paragraphAlign = new JTaskPaneGroup();
		paragraphAlign.setTitle(UiText.PARAGRAPH_DISPLAY_ALIGN_SET_LABEL);
		//for test
//		JPanel fs = new FontSet();
//		paragraphAlign.add(new JLabel("内含段落内容基线对齐方式、段落内容对齐方式、段落之间对齐方式和垂直对齐方式"));
		paragraphAlign.add(new ParagraphDisplayAlign());
//		taskPane.setPreferredSize(fs.getPreferredSize());
//		fontSet.setMaximumSize(new Dimension(220,150));
		// fontSet.setPreferredSize(new Dimension(220,180));

		paragraphIndent = new JTaskPaneGroup();
		paragraphIndent.setTitle(UiText.PARAGRAPH_INDENT_SET_LABEL);
//		paragraphIndent.add(new JLabel("内含左缩进、右缩进和特殊缩进"));
		paragraphIndent.add(new ParagraphIndent());

		paragraphSpace = new JTaskPaneGroup();
		paragraphSpace.setTitle(UiText.PARAGRAPH_SPACE_SET_LABEL);
//		paragraphSpace.add(new JLabel("内含行内间距和段落间距"));
		paragraphSpace.add(new ParagraphSpace());
//		fontBackground.setExpanded(false);

		paragraphBackground = new JTaskPaneGroup();
		paragraphBackground.setTitle(UiText.PARAGRAPH_BACKGROUND_SET_LABEL);
//		paragraphBackground.add(new JLabel("段落背景设置设置面板，有可能会和边框整合"));
		paragraphBackground.add(new ParagraphBackground());
		paragraphBackground.setExpanded(false);

		paragraphBorder = new JTaskPaneGroup();
		paragraphBorder.setTitle(UiText.PARAGRAPH_BORDER_SET_LABEL);
		paragraphBorder.add(new ParagraphBorder());
		paragraphBorder.setExpanded(false);

		pagination = new JTaskPaneGroup();
		pagination.setTitle(UiText.PARAGRAPH_PAGINATION_SET_LABEL);
//		pagination.add(new JLabel("内含孤行控制、段前分页等内容"));
		pagination.add(new ParagraphPagination());
		pagination.setExpanded(false);

		paragraphSpecial = new JTaskPaneGroup();
		paragraphSpecial.setTitle(UiText.PARAGRAPH_SPECIAL_SET_LABEL);
//		paragraphSpecial.add(new JLabel("内含字首下沉、中文版式、字符间距等内容"));
		paragraphSpecial.add(new ParagraphSpecial());
		paragraphSpecial.setExpanded(false);
		
		paragraphAllProperty = new JTaskPaneGroup();
		JButton textPanel = new JButton(UiText.PARAGRAPH_ALL_PROPERTY_BUTTON);
		textPanel.setForeground(new Color(0,0,0));
		paragraphAllProperty.setCollapsable(false);
		paragraphAllProperty.add(textPanel);
		
		
		//添加各子可折叠菜单
		taskPane.add(paragraphAlign);
		taskPane.add(paragraphIndent);
		taskPane.add(paragraphSpace);
		taskPane.add(paragraphBackground);
		taskPane.add(paragraphBorder);
		taskPane.add(pagination);
		taskPane.add(paragraphSpecial);
		
		taskPane.add(paragraphAllProperty);
		


		JScrollPane scroll = new JScrollPane(taskPane);		
		scroll.setBorder(null);

		setLayout(new BorderLayout());
		add("Center", scroll);
		
		setBorder(null);
		
//		System.out.println(fs.getPreferredSize());
	}

	// 测试用
	public static void main(String[] args) {

		// 正常的tab的测试程序
		JFrame fr = new JFrame("测试用");
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		fr.getContentPane().setLayout(flowLayout);
		fr.setSize(462, 600);

		ParagraphPropertyCollapseUI tui = new ParagraphPropertyCollapseUI();

		fr.add(tui);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
	}

}
