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

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Barcode;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 条形码文字位置设置
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class BarcodeTextPositionBand {
	
	public JRibbonBand getBand() {
		
		//条形文字码位置设置
		JRibbonBand barcodePositionBand = new JRibbonBand(RibbonUIText.BARCODE_TEXT_POSITION_BAND, MediaResource.getResizableIcon("09379.ico"));
		
		JPanel barcodePosition = new JPanel(new GridLayout(2, 2, 0, 0));
		
		JLabel startEnd = new JLabel(RibbonUIText.BARCODE_INNER_SPACE_LABEL);
		FixedLengthSpinner startEndValue = new FixedLengthSpinner();
		RibbonUIManager.getInstance().bind(Barcode.TEXT_CHAR_SPACE_ACTION, startEndValue);
		
		JLabel topBottom = new JLabel(RibbonUIText.BARCODE_TEXT_SPACE_LABEL);
		FixedLengthSpinner topBottomValue = new FixedLengthSpinner();
		RibbonUIManager.getInstance().bind(Barcode.TEXT_BLOCK_ACTION, topBottomValue);
		
		barcodePosition.add(startEnd);
		barcodePosition.add(startEndValue);
		
		barcodePosition.add(topBottom);
		barcodePosition.add(topBottomValue);
		
//		barcodePositionBand.addPanel(barcodePosition);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent barcodePositionWrapper = new JRibbonComponent(barcodePosition);
		barcodePositionBand.addRibbonComponent(barcodePositionWrapper);
		
		return barcodePositionBand;
	}
}
