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
 * @GroupBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.checkbox;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.CheckBox;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-12-21
 */
public class GroupBand implements WiseBand
{
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand groupband = new JRibbonBand(
				RibbonUIText.CHECKBOX_BUTTONGROUP, MediaResource
						.getResizableIcon("09379.ico"));
		final JCommandButton creatgroupbutton = new JCommandButton(
				RibbonUIText.CHECKBOX_BUTTONGROUP_CREATE, MediaResource
						.getResizableIcon("03466.ico"));
		RibbonUIManager.getInstance().bind(
				CheckBox.CHECKBOX_CREAT_GROUPUI_ACTION, creatgroupbutton);
		groupband.addCommandButton(creatgroupbutton,
				RibbonElementPriority.MEDIUM);
		final JCommandButton setgroupbutton = new JCommandButton(
				RibbonUIText.CHECKBOX_BUTTONGROUP_SET, MediaResource
						.getResizableIcon("03466.ico"));
		RibbonUIManager.getInstance().bind(
				CheckBox.CHECKBOX_SET_GROUPUI_ACTION, setgroupbutton);
		groupband
				.addCommandButton(setgroupbutton, RibbonElementPriority.MEDIUM);

		return groupband;
	}

}
