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

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Barcode;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 条形码大小面板
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class BarcodeSizeBand {
	
	FixedLengthSpinner barcodeHeightValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
	
	FixedLengthSpinner barcodeWidthValue = new FixedLengthSpinner(
			new SpinnerFixedLengthModel(new FixedLength(0.305, "mm"), new FixedLength(0.00001d, "mm"), /*new FixedLength(1, "mm")*/null, 1));
	
	public JRibbonBand getBand() {
		
		JRibbonBand barcodeSizeBand = new JRibbonBand(RibbonUIText.BARCODE_TYPE_BAND,
				new format_justify_left(), null);

		barcodeSizeBand.startGroup(RibbonUIText.BARCODE_TYPE_BAND);
		JRibbonComponent barcodeHeightWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00541.ico"), RibbonUIText.BARCODE_HEIGHT_LABEL, barcodeHeightValue);

		RichTooltip barcodeHeightTooltip = new RichTooltip();
		barcodeHeightTooltip.setTitle(RibbonUIText.BARCODE_HEIGHT_LABEL_TITLE);
		barcodeHeightTooltip.addDescriptionSection(RibbonUIText.BARCODE_HEIGHT_LABEL_DESCRIPTION);
		barcodeHeightWrapper.setRichTooltip(barcodeHeightTooltip);

		barcodeSizeBand.addRibbonComponent(barcodeHeightWrapper);

		JRibbonComponent barcodeBasicWidthWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("01650.ico"), RibbonUIText.BARCODE_WIDTH_LABEL, barcodeWidthValue);

		RichTooltip barcodeBasicWidthTooltip = new RichTooltip();
		barcodeBasicWidthTooltip.setTitle(RibbonUIText.BARCODE_WIDTH_LABEL_TITLE);
		barcodeBasicWidthTooltip.addDescriptionSection(RibbonUIText.BARCODE_WIDTH_LABEL_DESCRIPTION);
		barcodeBasicWidthWrapper.setRichTooltip(barcodeBasicWidthTooltip);

		barcodeSizeBand.addRibbonComponent(barcodeBasicWidthWrapper);
		
		RibbonUIManager.getInstance().bind(Barcode.HEIGHT_ACTION, barcodeHeightValue);
		RibbonUIManager.getInstance().bind(Barcode.WIDTH_ACTION, barcodeWidthValue);

		return barcodeSizeBand;
	}
}
