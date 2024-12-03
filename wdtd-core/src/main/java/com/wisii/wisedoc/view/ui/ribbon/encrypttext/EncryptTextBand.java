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
 * @EncryptTextBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.encrypttext;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.EncryptText;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-17
 */
public class EncryptTextBand implements WiseBand
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	@Override
	public JRibbonBand getBand()
	{
		JRibbonBand encryband = new JRibbonBand("加密设置", MediaResource
				.getResizableIcon("09379.ico"), null);

		JCommandButton set = new JCommandButton("设置", MediaResource
				.getResizableIcon("00906.ico"));
		set.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(EncryptText.SET_ENCRYPT_ACTION, set);
		encryband.addCommandButton(set, RibbonElementPriority.TOP);
		return encryband;
	}

}
