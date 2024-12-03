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
 * @SizeBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.externalgraphic;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ExternalGraphic;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：图片大小设置面板
 * 
 * 作者：zhangqiang 创建日期：2008-12-23
 */
public class SizeBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	public JRibbonBand getBand()
	{
		JRibbonBand sizeband = new JRibbonBand(RibbonUIText.PICTURE_SIZE_BAND_TITLE, MediaResource.getResizableIcon("09379.ico"), null);
		
		JPanel setpanel = new JPanel(new GridLayout(2, 2, 0, 0));
		JLabel typelabel = new JLabel(RibbonUIText.PICTURE_SIZE_TYPE);
		WiseCombobox typevale = new WiseCombobox(RibbonUIText.PICTURE_SIZE_TYPE_LIST);
		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_SIZETYPE_ACTION, typevale);
		JPanel typepanel = new JPanel();
		typepanel.add(typelabel);
		typepanel.add(typevale);
		JCheckBox scalingvalue = new JCheckBox(RibbonUIText.PICTURE_FIX_ASPECT_RATIO);
		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_SCALING_ACTION, scalingvalue);
		JPanel scalingpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		scalingpanel.add(scalingvalue);
		JLabel heightlabel = new JLabel(RibbonUIText.PICTURE_SIZE_HEIGHT);
		FixedLengthSpinner heightValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_CONTENTHEIGHT_ACTION, heightValue);
		JPanel heightpanel = new JPanel();
		heightpanel.add(heightlabel);
		heightpanel.add(heightValue);
		JLabel widthlabel = new JLabel(RibbonUIText.PICTURE_SIZE_WIDTH);
		FixedLengthSpinner widthValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
		RibbonUIManager.getInstance().bind(ExternalGraphic.SET_CONTENTWIDTH_ACTION, widthValue);
		JPanel widthpanel = new JPanel();
		widthpanel.add(widthlabel);
		widthpanel.add(widthValue);
		setpanel.add(typepanel);
		setpanel.add(scalingpanel);
		setpanel.add(heightpanel);
		setpanel.add(widthpanel);
//		sizeband.addPanel(setpanel);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent setpanelWrapper = new JRibbonComponent(setpanel);
		sizeband.addRibbonComponent(setpanelWrapper);
		
		return sizeband;
	}
}
