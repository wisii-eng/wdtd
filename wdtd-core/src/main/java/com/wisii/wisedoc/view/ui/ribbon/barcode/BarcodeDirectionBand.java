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

import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Barcode;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ExternalGraphic;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 条形码方向标签
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class BarcodeDirectionBand {
	
	public JRibbonBand getBand() {
		
		//条形码方向设置
		JRibbonBand barcodeDirectionBand = new JRibbonBand(RibbonUIText.BARCODE_DIRECTION_BAND, MediaResource.getResizableIcon("09379.ico"));
		
		WiseSpinner orientationValue = new WiseSpinner(new SpinnerNumberModel(0, 0, 270, 90));
		RibbonUIManager.getInstance().bind(Barcode.ORIENTATION_ACTION, orientationValue);
		
		JRibbonComponent orientation = new JRibbonComponent(MediaResource.getResizableIcon("03458.ico"),
				RibbonUIText.BARCODE_DIRECTION_LABEL, orientationValue);
		barcodeDirectionBand.addRibbonComponent(orientation);
		
		WiseSpinner wideToNarrowValue = new WiseSpinner();
		wideToNarrowValue.setPreferredSize(new Dimension(55, 20));
		SpinnerNumberModel seModel = new SpinnerNumberModel(3.0, 2.0, 3.0, 0.1);
		wideToNarrowValue.setModel(seModel);
		RibbonUIManager.getInstance().bind(Barcode.WIDE_TO_NARROW_ACTION, wideToNarrowValue);
		
		JRibbonComponent wideToNarrow = new JRibbonComponent(MediaResource.getResizableIcon("01648.ico"),
				RibbonUIText.BARCODE_WIDE_NARROW_BAND, wideToNarrowValue);
		barcodeDirectionBand.addRibbonComponent(wideToNarrow);
		
		WiseCombobox layervalue = new WiseCombobox(new DefaultComboBoxModel(UiText.COMMON_COLOR_LAYER));
		layervalue.setPreferredSize(new Dimension(55,20));
		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_LAYER_ACTION, layervalue);
		
		JRibbonComponent layer = new JRibbonComponent(MediaResource.getResizableIcon("09984.ico"),
				RibbonUIText.BARCODE_COLOR_LAYER, layervalue);
		barcodeDirectionBand.addRibbonComponent(layer);
		
		return barcodeDirectionBand;
	}
}
