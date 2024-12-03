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

import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Barcode;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 定义条码字符数据区列数面板
 * @author 闫舒寰
 * @version 1.0 2009/04/17
 */
public class Barcode2DColumnsBand {
	
	WiseSpinner columnsValue = new WiseSpinner(new SpinnerNumberModel(0,0,30,1));
	WiseSpinner minColumnsValue = new WiseSpinner(new SpinnerNumberModel(1,1,30,1));
	WiseSpinner maxColumnsValue = new WiseSpinner(new SpinnerNumberModel(1,1,30,1));
	
	public JRibbonBand getBand() {
		
		final JRibbonBand barcodePositionBand = new JRibbonBand("二维条形码字符区域列数",
				new format_justify_left(), null);
		
//		barcodePositionBand.startGroup(RibbonUIText.BARCODE_POSITION_GROUP);
		final JRibbonComponent startEndWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00542.ico"), "字符数据区列数", columnsValue);

		final RichTooltip startEndTooltip = new RichTooltip();
		startEndTooltip.setTitle(RibbonUIText.BARCODE_START_LABEL_TITLE);
		startEndTooltip.addDescriptionSection(RibbonUIText.BARCODE_START_LABEL_DESCRIPTION);
		startEndWrapper.setRichTooltip(startEndTooltip);

		barcodePositionBand.addRibbonComponent(startEndWrapper);

		final JRibbonComponent topBottomWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00541.ico"), "最小数据区列数", minColumnsValue);

		final RichTooltip topBottomTooltip = new RichTooltip();
		topBottomTooltip.setTitle(RibbonUIText.BARCODE_TOP_LABEL_TITLE);
		topBottomTooltip.addDescriptionSection(RibbonUIText.BARCODE_TOP_LABEL_DESCRIPTION);
		topBottomWrapper.setRichTooltip(topBottomTooltip);

		barcodePositionBand.addRibbonComponent(topBottomWrapper);
		
		final JRibbonComponent maxColumnsWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00541.ico"), "最大数据区列数", maxColumnsValue);

		final RichTooltip maxColumnsTooltip = new RichTooltip();
		maxColumnsTooltip.setTitle(RibbonUIText.BARCODE_TOP_LABEL_TITLE);
		maxColumnsTooltip.addDescriptionSection(RibbonUIText.BARCODE_TOP_LABEL_DESCRIPTION);
		maxColumnsWrapper.setRichTooltip(maxColumnsTooltip);

		barcodePositionBand.addRibbonComponent(maxColumnsWrapper);
		
		RibbonUIManager.getInstance().bind(Barcode.COLUMNS_ACTION, columnsValue);
		RibbonUIManager.getInstance().bind(Barcode.MIN_COLUMNS_ACTION, minColumnsValue);
		RibbonUIManager.getInstance().bind(Barcode.MAX_COLUMNS_ACTION, maxColumnsValue);

		return barcodePositionBand;
	}
}
