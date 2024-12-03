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

import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Barcode;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 条形码位置设置
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class BarcodePositionBand {
	
	FixedLengthSpinner startEndValue = new FixedLengthSpinner();
	FixedLengthSpinner topBottomValue = new FixedLengthSpinner();
	
	public JRibbonBand getBand() {
		
		JRibbonBand barcodePositionBand = new JRibbonBand(RibbonUIText.BARCODE_POSITION_BAND,
				new format_justify_left(), null);

		barcodePositionBand.startGroup(RibbonUIText.BARCODE_POSITION_GROUP);
		JRibbonComponent startEndWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00542.ico"), RibbonUIText.BARCODE_START_LABEL, startEndValue);

		RichTooltip startEndTooltip = new RichTooltip();
		startEndTooltip.setTitle(RibbonUIText.BARCODE_START_LABEL_TITLE);
		startEndTooltip.addDescriptionSection(RibbonUIText.BARCODE_START_LABEL_DESCRIPTION);
		startEndWrapper.setRichTooltip(startEndTooltip);

		barcodePositionBand.addRibbonComponent(startEndWrapper);

		JRibbonComponent topBottomWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00541.ico"), RibbonUIText.BARCODE_TOP_LABEL, topBottomValue);

		RichTooltip topBottomTooltip = new RichTooltip();
		topBottomTooltip.setTitle(RibbonUIText.BARCODE_TOP_LABEL_TITLE);
		topBottomTooltip.addDescriptionSection(RibbonUIText.BARCODE_TOP_LABEL_DESCRIPTION);
		topBottomWrapper.setRichTooltip(topBottomTooltip);

		barcodePositionBand.addRibbonComponent(topBottomWrapper);
		
		RibbonUIManager.getInstance().bind(Barcode.QUIET_HORIZONTAL_ACTION, startEndValue);
		RibbonUIManager.getInstance().bind(Barcode.QUIET_VERTICAL_ACTION, topBottomValue);

		return barcodePositionBand;
	}
}
