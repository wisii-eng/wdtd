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

public class SymbolBand {
	
	public JRibbonBand getSymbolBand() {
		
		//图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		JRibbonBand symbolBand = new JRibbonBand("符号",
				MediaResource.getResizableIcon("Image.gif"), new ExpandActionListener());
		
		/***********添加符号 开始*******************/
		//图片按钮
		JCommandButton insertSymbolButton = new JCommandButton("符号", MediaResource.getResizableIcon("00308.ico"));
		// mainButton.setMnemonic('P');
		//主按钮的动作
			
		//下拉菜单的按钮
//		insertPictureButton.addPopupMenuListener(new SamplePopupActionListener());
		//添加可下拉按钮
		insertSymbolButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		//鼠标浮动显示解释说明
//		insertPictureButton.setToolTipText("符号，在文档中插入符号");
		//这个是在地方紧张的时候，显示的优先级
		symbolBand.addCommandButton(insertSymbolButton, RibbonElementPriority.TOP);
		insertSymbolButton.setEnabled(false);
		/***********添加符号 结束*******************/
		
		return symbolBand;
	}
	
	private class ExpandActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null,
					"Expand button clicked");
		}
	}
	



}
