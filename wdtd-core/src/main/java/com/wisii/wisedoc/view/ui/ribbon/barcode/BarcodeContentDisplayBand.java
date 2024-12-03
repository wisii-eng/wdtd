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
package com.wisii.wisedoc.view.ui.ribbon.barcode;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Barcode;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 条形码内容显示和输出设置面板
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class BarcodeContentDisplayBand {
	
	public JRibbonBand getBand() {
		
		//条形码显示内容
		JRibbonBand barcodeContentDisplayBand = new JRibbonBand(RibbonUIText.BARCODE_CONTENT_DISPLAY_BAND, MediaResource.getResizableIcon("09379.ico"));
		
		JPanel barcodeContent = new JPanel(new GridLayout(2,1));
		
		JTextField content = new JTextField(RibbonUIText.BARCODE_CONTENT_DISPLAY_FIELD);
		RibbonUIManager.getInstance().bind(Barcode.STRING_ACTION, content);
		
		barcodeContent.add(content);
		
		JCheckBox printContent = new JCheckBox(RibbonUIText.BARCODE_CONTENT_PRINT);
		RibbonUIManager.getInstance().bind(Barcode.PRINT_TEXT_ACTION, printContent);
		
		barcodeContent.add(printContent);
		
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent barcodeContentWrapper = new JRibbonComponent(barcodeContent);
		barcodeContentDisplayBand.addRibbonComponent(barcodeContentWrapper);
		
		return barcodeContentDisplayBand;
	}
}
