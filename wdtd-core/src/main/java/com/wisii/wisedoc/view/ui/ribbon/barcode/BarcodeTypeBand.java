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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.StringValuePair;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Barcode;
import com.wisii.wisedoc.view.ui.svg.NumberedResizableIcon;
import com.wisii.wisedoc.view.ui.svg.SimpleResizableIcon;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 调整条形码类型的面板，目前不用
 * @author 闫舒寰
 * @version 1.0 2009/02/10
 */
public class BarcodeTypeBand {
	
	public JRibbonBand getBand() {
		
		JRibbonBand barcodeType = new JRibbonBand(RibbonUIText.BARCODE_TYPE_BAND, new SimpleResizableIcon(RibbonElementPriority.TOP, 32, 32));

		//更改条形码类型
		//目前不用
		Map<RibbonElementPriority, Integer> transitionGalleryVisibleButtonCounts = new HashMap<RibbonElementPriority, Integer>();
		transitionGalleryVisibleButtonCounts.put(RibbonElementPriority.LOW, 2);
		transitionGalleryVisibleButtonCounts.put(RibbonElementPriority.MEDIUM,	4);
		transitionGalleryVisibleButtonCounts.put(RibbonElementPriority.TOP, 6);

		List<StringValuePair<List<JCommandToggleButton>>> transitionGalleryButtons = new ArrayList<StringValuePair<List<JCommandToggleButton>>>();
		List<JCommandToggleButton> transitionGalleryButtonsList = new ArrayList<JCommandToggleButton>();
		
		String[] codeType = {"39码", "128码", "en128", "20f5", "ean13", "ean8", "upca", "upce" };
		for (int i = 0; i < codeType.length; i++) {
			JCommandToggleButton additionalButton = new JCommandToggleButton(
					codeType[i], new NumberedResizableIcon(i, 100, 80));
			
			transitionGalleryButtonsList.add(additionalButton);
		}
		
		RibbonUIManager.getInstance().bind(Barcode.TYPE_39_ACTION, transitionGalleryButtonsList.get(0));

		transitionGalleryButtons
				.add(new StringValuePair<List<JCommandToggleButton>>(
						"条形码类型", transitionGalleryButtonsList));
		/*barcodeType.addRibbonGallery("条形码类型",
				transitionGalleryButtons, transitionGalleryVisibleButtonCounts,
				4, 3, RibbonElementPriority.TOP);*/

		
		
		/*JPanel barcodeColor = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
		barcodeColor.add(new JLabel("条码颜色", MediaResource.getResizableIcon("00417.ico"), JLabel.LEADING));*/
		
		//条码颜色
		//目前不用设置条形码颜色，条形码只能是黑色
		/*JCommandButton barcodeColorButton = new JCommandButton("null", MediaResource.getResizableIcon("00417.ico"));
		FontColorUIUpdate.FontColor.setButton(barcodeColorButton);
		barcodeColorButton.addActionListener(new FontColorButtonAction());
		barcodeColorButton.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		barcodeColorButton.setDisplayState(CommandButtonDisplayState.SMALL);
		barcodeColorButton.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new FontColor();
			}
		});
		barcodeColor.add(barcodeColorButton);*/
//		barcodeInfo.add(barcodeColor);		

//		barcodeType.addPanel(barcodeInfo);

		return barcodeType;
	}

}
