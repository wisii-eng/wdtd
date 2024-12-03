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
package com.wisii.wisedoc.view.ui.ribbon.insertband;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.view.ui.ribbon.WiseTask;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 插入标签集合
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class InsertTask implements WiseTask {

	public RibbonTask getTask() {
		JRibbonBand pagesequenceBand = new PagesequenceBand().getBand();
		JRibbonBand talbeBand = new TableBand().getBand();
		JRibbonBand pictureBand = new PictureBand().getBand();
		JRibbonBand foBand = new FOBand().getBand();
		// JRibbonBand linkBand = new LinkBand().getLinkBand(); //链接引用面板
		JRibbonBand headerFooterBand = new PageNumberBand().getBand();
		// JRibbonBand textEffectBand = new
		// TextEffectBand().getTextEffectBand(); //字首下沉面板
		// JRibbonBand symbolBand = new SymbolBand().getSymbolBand(); //符号面板
		JRibbonBand numberFormatBand = new NumberFormatBand()
				.getNumberFormatBand();

		JRibbonBand indexBand = new IndexBand().getBand();
		JRibbonBand specialcharacterband = new InsertSpecialCharacterBand().getBand();
		
		RibbonTask insertTask = new RibbonTask(RibbonUIText.INSERT_TASK,
				pagesequenceBand, talbeBand, pictureBand, foBand, /* linkBand, */
				headerFooterBand, numberFormatBand, indexBand,specialcharacterband /*
													 * textEffectBand,
													 *//* symbolBand */);
		return insertTask;
	}

}
