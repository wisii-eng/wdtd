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
/**
 * @TypeBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.externalgraphic;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ExternalGraphic;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-23
 */
public class SetBand implements WiseBand
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	public JRibbonBand getBand()
	{
		JRibbonBand setband = new JRibbonBand(RibbonUIText.PICTURE_SET, MediaResource.getResizableIcon("09379.ico"), null);
		
		WiseCombobox typevale = new WiseCombobox(RibbonUIText.PICTURE_SOURCE_TYPE_LIST);
		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_DATATYPE_ACTION, typevale);
		
		JRibbonComponent games = new JRibbonComponent(MediaResource.getResizableIcon("04018.ico"),
				RibbonUIText.PICTURE_SOURCE_TYPE_TITLE, typevale);
		setband.addRibbonComponent(games);
		
		WiseCombobox layervalue = new WiseCombobox(new DefaultComboBoxModel(UiText.COMMON_COLOR_LAYER));
		layervalue.setPreferredSize(new Dimension(70,20));
		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_LAYER_ACTION, layervalue);
		
		JRibbonComponent layer = new JRibbonComponent(MediaResource.getResizableIcon("06184.ico"),
				RibbonUIText.PICTURE_LAYER, layervalue);
		setband.addRibbonComponent(layer);
		
		
		WiseSpinner alaphvalue = new WiseSpinner();
		alaphvalue.setPreferredSize(new Dimension(70, 20));
		SpinnerNumberModel bottomModel = new SpinnerNumberModel(255, 1, 255, 1);
		alaphvalue.setModel(bottomModel);
		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_ALPHA_ACTION, alaphvalue);
		
		JRibbonComponent apaph = new JRibbonComponent(MediaResource.getResizableIcon("05874.ico"),
				RibbonUIText.PICTURE_TRANSPARENCY, alaphvalue);
		setband.addRibbonComponent(apaph);
		
		return setband;
	}
}
