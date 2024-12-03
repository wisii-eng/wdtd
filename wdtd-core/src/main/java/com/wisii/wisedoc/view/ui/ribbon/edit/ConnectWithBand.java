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
 * @ConnectBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Edit;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-9-1
 */
public class ConnectWithBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand connectWithband = new JRibbonBand(
				RibbonUIText.EDIT_CONNWITH_TITLE, MediaResource
						.getResizableIcon("09379.ico"));
		final JCommandButton connwithbutton = new JCommandButton(
				RibbonUIText.EDIT_SET, MediaResource
						.getResizableIcon("03466.ico"));
		RibbonUIManager.getInstance().bind(Edit.EDIT_CONNWITH_ACTION,
				connwithbutton);
		connectWithband.addCommandButton(connwithbutton,
				RibbonElementPriority.MEDIUM);
		final JCommandButton reomveconnwithbutton = new JCommandButton(
				RibbonUIText.EDIT_REMOVE, MediaResource
						.getResizableIcon("03466.ico"));
		RibbonUIManager.getInstance().bind(Edit.REMOVE_CONNWITH_ACTION,
				reomveconnwithbutton);
		connectWithband.addCommandButton(reomveconnwithbutton,
				RibbonElementPriority.MEDIUM);
		return connectWithband;
	}

}
