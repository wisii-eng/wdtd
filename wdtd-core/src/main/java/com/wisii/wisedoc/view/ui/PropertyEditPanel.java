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
package com.wisii.wisedoc.view.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import com.l2fprod.common.swing.plaf.metal.MetalLookAndFeelAddons;
import com.wisii.wisedoc.view.ui.parts.ParagraphPropertyCollapseUI;
import com.wisii.wisedoc.view.ui.parts.TextPropertyCollapseUI;

/**
 * 
 * 属性编辑主体窗口，里面放置着各种属性分类的tabs,分别是文字、段落和页面属性设置
 * 
 * @author 闫舒寰
 * @since 2008/09/16
 *
 */
public class PropertyEditPanel extends JPanel {

	/**
	 * 创建属性界面窗口
	 */
	public PropertyEditPanel() {
		// 第一版tabs，问题是不能灵活的换面板样式也不能很好的配合layout
		/*
		 * super(); setMinimumSize(new Dimension(280, 500)); //size top level
		 * setSize(290, 500); //prefereed size setPreferredSize(new
		 * Dimension(280, 500)); setLayout(new FlowLayout(FlowLayout.LEADING)); //
		 * setLayout(new ScrollPaneLayout());
		 * 
		 * JTabbedPane tabs = new JTabbedPane(); tabs.setPreferredSize(new
		 * Dimension(280, 500)); // System.out.println(JFrame.HEIGHT);
		 * //在显示不下的时候，添加tab左右滑动按钮
		 * tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); //传统非滚动菜单 //
		 * tabs.add("文字属性设置", new TextPropertyEditUI()); tabs.add("文字属性设置", new
		 * TextCollpasePanel()); tabs.add("段落属性设置", new
		 * ParagraphPropertyEditUI()); tabs.add("页面属性设置", null);
		 * 
		 * add(tabs);
		 */

		setLayout(new BorderLayout());

		// 第二版的tabs，可以自由缩放等
		JTabbedPane tabs = new JTabbedPane();

		{ // 金属样式，可以根据需要改变成别的样式
			try {
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
				LookAndFeelAddons.setAddon(MetalLookAndFeelAddons.class);
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 在显示不下的时候，添加tab左右滑动按钮，目前显示会有bug暂时不用
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.addTab("文字属性设置", new TextPropertyCollapseUI());
		tabs.addTab("段落属性设置", new ParagraphPropertyCollapseUI());
//		tabs.addTab("表格属性设置", new TablePropertyCollapseUI());
		
		add("Center", tabs);
		
//		System.out.println(this.getSize());
		
	}
	
	
	

	// 测试用
	public static void main(String[] args) {

		// 正常的tab的测试程序
		JFrame fr = new JFrame("测试用");
		fr.setSize(800, 600);

		PropertyEditPanel tui = new PropertyEditPanel();

		fr.add(tui);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
	}

}
