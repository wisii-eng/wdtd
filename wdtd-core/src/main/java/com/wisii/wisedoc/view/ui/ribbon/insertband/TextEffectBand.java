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
import com.wisii.wisedoc.view.action.InsertDateTimeAction;
import com.wisii.wisedoc.view.action.InsertNumberTextAction;

public class TextEffectBand {
	
	public JRibbonBand getTextEffectBand() {
		
		//图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		JRibbonBand textEffectBand = new JRibbonBand("文本格式",
				MediaResource.getResizableIcon("Image.gif"), new ExpandActionListener());
		
		/***********字首下沉 开始*******************/
		//图片按钮
		JCommandButton insertPictureButton = new JCommandButton("格式化数字", MediaResource.getResizableIcon("00289.ico"));
		insertPictureButton.addActionListener(new InsertNumberTextAction());
		// mainButton.setMnemonic('P');
		//主按钮的动作
		/*insertTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pasted!");
			}
		});*/		
		//下拉菜单的按钮
//		insertPictureButton.addPopupMenuListener(new SamplePopupActionListener());
		//添加可下拉按钮
		insertPictureButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		//鼠标浮动显示解释说明
//		insertPictureButton.setToolTipText("字首下沉，在文档中插入字首下沉");
		//这个是在地方紧张的时候，显示的优先级
		textEffectBand.addCommandButton(insertPictureButton, RibbonElementPriority.TOP);
		/***********字首下沉 结束*******************/
		
		/***********日期和时间 开始*******************/
		//添加条形码按钮
		JCommandButton insertClosePictureButton = new JCommandButton("日期和时间", MediaResource.getResizableIcon("09443.ico"));
		insertClosePictureButton.addActionListener(new InsertDateTimeAction());
		// mainButton.setMnemonic('P');
		//主按钮的动作
		/*insertTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pasted!");
			}
		});*/		
		//下拉菜单的按钮
//		insertClosePictureButton.addPopupMenuListener(new SamplePopupActionListener());
		//添加可下拉按钮
		insertClosePictureButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		//鼠标浮动显示解释说明
//		insertClosePictureButton.setToolTipText("页脚，在文档中插入条页脚");
		//这个是在地方紧张的时候，显示的优先级
		textEffectBand.addCommandButton(insertClosePictureButton, RibbonElementPriority.TOP);
		/***********日期和时间 结束*******************/
		
		

		return textEffectBand;
	}
	
	private class ExpandActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null,
					"Expand button clicked");
		}
	}
	



}
