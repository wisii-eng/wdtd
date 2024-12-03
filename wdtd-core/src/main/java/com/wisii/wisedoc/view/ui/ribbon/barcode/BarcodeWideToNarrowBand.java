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

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Barcode;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 条形码宽窄比面板， 这个面板目前弃置不用了
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class BarcodeWideToNarrowBand {
	
	public JRibbonBand getBarcodeWideToNarrowBand() {
		
		//条形码定义宽窄比
		JRibbonBand barcodeWideToNarrowBand = new JRibbonBand(RibbonUIText.BARCODE_WIDE_NARROW_BAND, MediaResource.getResizableIcon("09379.ico"));
		
		JPanel barcodeWideToNarrowPanel = new JPanel(new GridLayout(1, 1, 0, 0));
		
		JLabel wideToNarrow = new JLabel(RibbonUIText.BARCODE_WIDE_NARROW_LABEL);
		
		WiseSpinner wideToNarrowValue = new WiseSpinner();
		wideToNarrowValue.setPreferredSize(new Dimension(80, 75));
		SpinnerNumberModel seModel = new SpinnerNumberModel(3.0, 2.0, 3.0, 0.1);
		wideToNarrowValue.setModel(seModel);
		RibbonUIManager.getInstance().bind(Barcode.WIDE_TO_NARROW_ACTION, wideToNarrowValue);
		
		barcodeWideToNarrowPanel.add(wideToNarrow);
		barcodeWideToNarrowPanel.add(wideToNarrowValue);
		
//		barcodeWideToNarrowBand.addPanel(barcodeWideToNarrowPanel);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent barcodeWideToNarrowPanelWrapper = new JRibbonComponent(barcodeWideToNarrowPanel);
		barcodeWideToNarrowBand.addRibbonComponent(barcodeWideToNarrowPanelWrapper);
		
		return barcodeWideToNarrowBand;
	}
	
}
