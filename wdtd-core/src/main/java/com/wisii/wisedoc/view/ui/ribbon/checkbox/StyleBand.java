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
 * @CheckBoxBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.checkbox;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-12-21
 */
public class StyleBand implements WiseBand
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand checkboxBand = new JRibbonBand(RibbonUIText.CHECKBOX_STYLE_BAND,
				MediaResource.getResizableIcon("09379.ico"));
		CheckboxPanel checkboxpanel = new CheckboxPanel();
		JRibbonComponent maincom = new JRibbonComponent(checkboxpanel);
		checkboxBand.addRibbonComponent(maincom);
		return checkboxBand;
	}

}
