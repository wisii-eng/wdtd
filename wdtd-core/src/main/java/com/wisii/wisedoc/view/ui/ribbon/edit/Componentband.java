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
 * @Componentband.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import java.awt.CardLayout;

import javax.swing.JPanel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：控件面板
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-10
 */
public class Componentband implements WiseBand
{
	private CardLayout cardlayout;
	private JPanel cardpanel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand componentBand = new JRibbonBand(RibbonUIText.EDIT_COMPONENT,
				MediaResource.getResizableIcon("09379.ico"));
		cardlayout = new CardLayout();
		cardpanel = new JPanel(cardlayout);
		cardpanel.add(new InputPanel(), "" + Constants.EN_INPUT);
		cardpanel.add(new SelectPanel(), "" + Constants.EN_SELECT);
		cardpanel.add(new DatePanel(), "" + Constants.EN_DATE);
		cardpanel.add(new PopupBrowserPanel(), "" + Constants.EN_POPUPBROWSER);
		JRibbonComponent maincom = new JRibbonComponent(cardpanel);
		componentBand.addRibbonComponent(maincom);
		return componentBand;
	}

	public void typeChanged(EnumProperty edittype)
	{
		if (edittype != null)
		{
			String name = "" + edittype.getEnum();
			cardlayout.show(cardpanel, name);
		}
	}
}
