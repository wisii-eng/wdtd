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
package com.wisii.wisedoc.view.ui.ribbon.qianzhang;



import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.QianZhang;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-23
 */
public class InsertBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	public JRibbonBand getBand()
	{

		JRibbonBand band = new JRibbonBand(RibbonUIText.QIANZHANG_INSERT_BAND,
				MediaResource.getResizableIcon("09443.ico"));
		JCommandButton insert = new JCommandButton(
				RibbonUIText.QIANZHANG_INSERT_BUTTON, MediaResource
						.getResizableIcon("09443.ico"));
		insert.setActionRichTooltip(new RichTooltip(
				RibbonUIText.QIANZHANG_INSERT_BUTTON,
				RibbonUIText.QIANZHANG_INSERT_BUTTON_TOOLTIP));
		RibbonUIManager.getInstance().bind(QianZhang.INSERT_ACTION, insert);
		JCommandButton content = new JCommandButton(RibbonUIText.QIANZHANG_CHANGE_BUTTON, MediaResource
				.getResizableIcon("09443.ico"));
		content.setActionRichTooltip(new RichTooltip(RibbonUIText.QIANZHANG_CHANGE_BUTTON, RibbonUIText.QIANZHANG_CHANGE_BUTTON_TOOLTIP));
		RibbonUIManager.getInstance().bind(QianZhang.CHANGE_ACTION, content);
		band.addCommandButton(insert, RibbonElementPriority.TOP);
		band.addCommandButton(content, RibbonElementPriority.TOP);
		return band;
	}
}
