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
package com.wisii.wisedoc.view.ui.actions.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.parts.common.BorderStylePreviewPanel;

/**
 * 边框预览所需支持的动作
 * @author 闫舒寰
 * @version 1.0 2009/02/01
 */
public class BorderPreviewAction
{

	List<FixedLengthSpinner> widthValue;
	List<WiseCombobox> style;
	List<ColorComboBox> color;
	JPanel borderPreviewPanel;

	public BorderPreviewAction(final BorderPanel borderPanel)
	{
		this.widthValue = borderPanel.getWidthValue();
		this.style = borderPanel.getStyle();
		this.color = borderPanel.getColor();
		this.borderPreviewPanel = borderPanel.getBorderPreviewPanel();

		for (FixedLengthSpinner width : widthValue) {
			width.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
					((BorderStylePreviewPanel)borderPreviewPanel).updateStyle(borderPanel.getProperties());
				}
			});
		}
		
		for (WiseCombobox style : this.style) {
			style.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					((BorderStylePreviewPanel) borderPreviewPanel)
							.updateStyle(borderPanel.getProperties());
				}
			});
		}
		
		for (ColorComboBox color : this.color) {
			color.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					((BorderStylePreviewPanel) borderPreviewPanel)
							.updateStyle(borderPanel.getProperties());
				}
			});
		}
	}
}
