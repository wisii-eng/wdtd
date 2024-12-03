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
package com.wisii.wisedoc.view.ui.ribbon.basicband;

import org.jvnet.flamingo.ribbon.AbstractRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.view.ui.ribbon.WiseTask;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 基本属性标签task
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class BasicTask implements WiseTask {
	
	public RibbonTask getTask(){
		// 剪贴板
		final AbstractRibbonBand<?> clipBand = new ClipBand().getBand();
		// 字体板
		final AbstractRibbonBand<?> fontBand = new FontBand().getBand();
		// 段落板
		final AbstractRibbonBand<?> paragraphBand = new ParagraphBand().getBand();

		//段落样式面板
//		JRibbonBand pStyleBand = new ParagraphStylesBand().getQuickStylesBand();
		
		// 查找板
		final JRibbonBand layerBand = new LayersettingBand().getBand();
		// 查找板
		final JRibbonBand drawBand = new DrawSettingBand().getBand();

		final RibbonTask fontTask = new RibbonTask(RibbonUIText.BASIC_PROPERTY, clipBand, fontBand,
				paragraphBand,  layerBand,drawBand);
		return fontTask;
	}
}
