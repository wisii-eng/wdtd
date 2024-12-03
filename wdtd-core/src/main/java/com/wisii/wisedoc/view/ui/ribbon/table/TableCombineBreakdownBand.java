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
package com.wisii.wisedoc.view.ui.ribbon.table;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 合并拆分面板
 * @author 闫舒寰
 * @version 1.0 2009/01/15
 */
public class TableCombineBreakdownBand {
	
	public JRibbonBand getTableCombineBreakdownBand() {
		
		//合并拆分标签
		JRibbonBand tableCombineBreakdownBand = new JRibbonBand(RibbonUIText.TABLE_COMBINE_BREAKDOWN_BAND, MediaResource.getResizableIcon("09379.ico"));		
		
		//合并单元格按钮
		JCommandButton mergetablecell = new JCommandButton(RibbonUIText.TABLE_MERGE_CELL_BUTTON, MediaResource.getResizableIcon("00801.ico"));
		mergetablecell.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_MERGE_CELL_BUTTON_TITLE, RibbonUIText.TABLE_MERGE_CELL_BUTTON_DESCRIPTION));
		mergetablecell.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.MERGE_TABLECELL_ACTION, mergetablecell);
		tableCombineBreakdownBand.addCommandButton(mergetablecell, RibbonElementPriority.TOP);
		
		//拆分单元格按钮
		JCommandButton splittablecellbutton = new JCommandButton(RibbonUIText.TABLE_CELL_SPLIT_BUTTON, MediaResource.getResizableIcon("10009.ico"));
		splittablecellbutton.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_CELL_SPLIT_BUTTON_TITLE, RibbonUIText.TABLE_CELL_SPLIT_BUTTON_DESCRIPTION));
		splittablecellbutton.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SPLIT_TABLECELL_ACTION, splittablecellbutton);
		tableCombineBreakdownBand.addCommandButton(splittablecellbutton, RibbonElementPriority.TOP);
		
		//拆分表格按钮
		/*JCommandButton splittablebutton = new JCommandButton(RibbonUIText.TABLE_SPLIT_BUTTON, MediaResource.getResizableIcon("00808.ico"));
		splittablebutton.setActionRichTooltip(new RichTooltip(RibbonUIText.TABLE_SPLIT_BUTTON_TITLE, RibbonUIText.TABLE_SPLIT_BUTTON_DESCRIPTION));
		splittablebutton.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		tableCombineBreakdownBand.addCommandButton(splittablebutton, RibbonElementPriority.TOP);	*/	
		return tableCombineBreakdownBand;
	}	
}
