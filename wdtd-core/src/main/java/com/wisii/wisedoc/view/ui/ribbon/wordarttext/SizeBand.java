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

package com.wisii.wisedoc.view.ui.ribbon.wordarttext;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WordArtText;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-15
 */
public class SizeBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		FixedLengthSpinner heightValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
		FixedLengthSpinner widthValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(null,InitialManager.MINLEN,null,-1));
		JRibbonBand sizeband = new JRibbonBand(
				RibbonUIText.RIBBON_WORDARTTEXT_SIZE, MediaResource
						.getResizableIcon("09379.ico"), null);
		sizeband.startGroup();

		JRibbonComponent heightValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00541.ico"),
				RibbonUIText.RIBBON_WORDARTTEXT_SIZE_HEIGHT, heightValue);
		sizeband.addRibbonComponent(heightValueWrapper);

		JRibbonComponent widthValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00542.ico"),
				RibbonUIText.RIBBON_WORDARTTEXT_SIZE_WIDTH, widthValue);
		sizeband.addRibbonComponent(widthValueWrapper);
		RibbonUIManager.getInstance().bind(WordArtText.SET_HEIGHT_ACTION,
				heightValue);
		RibbonUIManager.getInstance().bind(WordArtText.SET_WIDTH_ACTION,
				widthValue);
		return sizeband;
	}

}
