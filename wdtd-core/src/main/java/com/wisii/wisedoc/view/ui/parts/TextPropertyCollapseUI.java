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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.parts.dialogs.TextAllPropertiesDialog;
import com.wisii.wisedoc.view.ui.parts.text.FontBackground;
import com.wisii.wisedoc.view.ui.parts.text.FontBorder;
import com.wisii.wisedoc.view.ui.parts.text.FontEffect;
import com.wisii.wisedoc.view.ui.parts.text.FontLink;
import com.wisii.wisedoc.view.ui.parts.text.FontPosition;
import com.wisii.wisedoc.view.ui.parts.text.FontSet;
import com.wisii.wisedoc.view.ui.parts.text.FontSpecial;
import com.wisii.wisedoc.view.ui.text.UiText;


/**
 * 
 * 专门用于生成文字的可折叠的菜单，所有有关文字的可折叠菜单中的子模板都添加到这里。
 * 
 * @author 闫舒寰
 * @since 2008/09/19
 *
 */
public class TextPropertyCollapseUI extends JTaskPane {

	JTaskPaneGroup fontSet;
	JTaskPaneGroup fontStyle;
	JTaskPaneGroup fontBackground;
	JTaskPaneGroup fontPosition;
	JTaskPaneGroup fontBorder;
	JTaskPaneGroup fontLink;
	JTaskPaneGroup fontSpecial;
	JTaskPaneGroup textAllProperty;

	public TextPropertyCollapseUI() {

		JTaskPane taskPane = new JTaskPane();
//		taskPane.setLayout(new BorderLayout());
//		setLayout(new BorderLayout());
		
		fontSet = new JTaskPaneGroup();
		fontSet.setTitle(UiText.FONT_SET_LABEL);
		fontSet.add(new FontSet());
//		taskPane.setPreferredSize(fs.getPreferredSize());
//		fontSet.setMaximumSize(new Dimension(220,150));
		// fontSet.setPreferredSize(new Dimension(220,180));

		fontStyle = new JTaskPaneGroup();
		fontStyle.setTitle(UiText.FONT_STYLE_SET_LABEL);
		fontStyle.add(new FontEffect());

		fontBackground = new JTaskPaneGroup();
		fontBackground.setTitle(UiText.FONT_BACKGROUND_SET_LABEL);
		fontBackground.add(new FontBackground());
//		fontBackground.setExpanded(false);

		fontPosition = new JTaskPaneGroup();
		fontPosition.add(new FontPosition());
		fontPosition.setTitle(UiText.FONT_POSITION_SET_LABEL);
		fontPosition.setExpanded(false);

		fontBorder = new JTaskPaneGroup();
		fontBorder.add(new FontBorder());
		fontBorder.setTitle(UiText.FONT_BORDER_SET_LABEL);
		fontBorder.setExpanded(false);

		fontLink = new JTaskPaneGroup();
		fontLink.add(new FontLink());
		fontLink.setTitle(UiText.FONT_LINK_SET_LABEL);
		fontLink.setExpanded(false);

		fontSpecial = new JTaskPaneGroup();
		fontSpecial.add(new FontSpecial());
		fontSpecial.setTitle(UiText.FONT_SPECIAL_SET_LABEL);
		fontSpecial.setExpanded(false);
		
		textAllProperty = new JTaskPaneGroup();
		JButton textPanel = new JButton(UiText.FONT_ALL_PROPERTY_BUTTON);
		textPanel.setForeground(new Color(0,0,0));
		textPanel.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new TextAllPropertiesDialog(ActionType.INLINE);
			}
			
		});
		
		textAllProperty.setCollapsable(false);
		textAllProperty.add(textPanel);
		
		//添加各子可折叠菜单
		taskPane.add(fontSet);
		taskPane.add(fontStyle);
		taskPane.add(fontBackground);
		taskPane.add(fontPosition);
		taskPane.add(fontBorder);
		taskPane.add(fontLink);
		taskPane.add(fontSpecial);
		
		taskPane.add(textAllProperty);
		
//		Button textPanel = new Button("全部文本属性");
//		System.out.println(taskPane.getLayout().);
//		taskPane.add(textPanel);
//		taskPane.set

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

		TextPropertyCollapseUI tui = new TextPropertyCollapseUI();

		fr.add(tui);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
	}

}
