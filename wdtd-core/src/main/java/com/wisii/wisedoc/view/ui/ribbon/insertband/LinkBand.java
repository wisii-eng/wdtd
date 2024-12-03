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
package com.wisii.wisedoc.view.ui.ribbon.insertband;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;

public class LinkBand {
	
	public JRibbonBand getLinkBand() {
		
		//图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		JRibbonBand linkBand = new JRibbonBand("链接",
				MediaResource.getResizableIcon("Figure.gif"), new ExpandActionListener());
		
		/***********添加超链接 开始*******************/
		//图片按钮
		JCommandButton insertHyperlinkButton = new JCommandButton("超链接", MediaResource.getResizableIcon("InternetLink.gif"));
		// mainButton.setMnemonic('P');
		//主按钮的动作
		/*insertTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pasted!");
			}
		});*/		
		//下拉菜单的按钮
//		insertHyperlinkButton.addPopupMenuListener(new SamplePopupActionListener());
		//添加可下拉按钮
		insertHyperlinkButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		//鼠标浮动显示解释说明
//		insertHyperlinkButton.setToolTipText("超链接，在文档中插入超链接");
		//这个是在地方紧张的时候，显示的优先级
		linkBand.addCommandButton(insertHyperlinkButton, RibbonElementPriority.LOW);
		/***********添加超链接 结束*******************/
		
		
		
		/***********添加书签 开始*******************/
		//添加条形码按钮
		JCommandButton insertBookMarkButton = new JCommandButton("书签", MediaResource.getResizableIcon("02525.ico"));
		// mainButton.setMnemonic('P');
		//主按钮的动作
		/*insertTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pasted!");
			}
		});*/		
		//下拉菜单的按钮
//		insertBookMarkButton.addPopupMenuListener(new SamplePopupActionListener());
		//添加可下拉按钮
		insertBookMarkButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		//鼠标浮动显示解释说明
//		insertBookMarkButton.setToolTipText("书签，在文档中插入条书签");
		//这个是在地方紧张的时候，显示的优先级
		linkBand.addCommandButton(insertBookMarkButton, RibbonElementPriority.LOW);
		/***********添加书签 结束*******************/
		
		/***********添加交叉引用 开始*******************/
		//添加条形码按钮
		JCommandButton insertCrossReferenceButton = new JCommandButton("交叉引用", MediaResource.getResizableIcon("02308.ico"));
		// mainButton.setMnemonic('P');
		//主按钮的动作
		/*insertTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pasted!");
			}
		});*/		
		//下拉菜单的按钮
//		insertCrossReferenceButton.addPopupMenuListener(new SamplePopupActionListener());
		//添加可下拉按钮
		insertCrossReferenceButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		//鼠标浮动显示解释说明
//		insertCrossReferenceButton.setToolTipText("交叉引用，在文档中插入交叉引用");
		//这个是在地方紧张的时候，显示的优先级
		linkBand.addCommandButton(insertCrossReferenceButton, RibbonElementPriority.LOW);
		/***********添加条码 结束*******************/
		

		return linkBand;
	}
	
	private class ExpandActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null,
					"Expand button clicked");
		}
	}
	


}
