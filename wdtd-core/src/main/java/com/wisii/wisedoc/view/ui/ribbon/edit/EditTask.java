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
 * @EditTask.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.edit;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.view.ui.actions.edit.EditTypeAction;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Edit;
import com.wisii.wisedoc.view.ui.ribbon.WiseTask;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：编辑面板，用来设置编辑相关的属性
 * 
 * 作者：zhangqiang 创建日期：2009-7-6
 */
public class EditTask implements WiseTask
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseTask#getTask()
	 */
	@Override
	public RibbonTask getTask()
	{
		JRibbonBand comband = new EditCommonPropertyBand().getBand();
		Componentband componentband = new Componentband();
		JRibbonBand componband = componentband.getBand();

	//	JRibbonBand uiprotyband = new EditCommonUIPropertyBand().getBand();
		JRibbonBand validationband = new EditValiDationBand().getBand();
		JRibbonBand connwithband = new ConnectWithBand().getBand();
		RibbonTask editTask = new RibbonTask(RibbonUIText.Edit_TASK, comband,
				componband,validationband,connwithband);
		EditTypeAction typeaction = (EditTypeAction) ActionFactory
				.getAction(Edit.EDIT_TYPE_ACTION);
		typeaction.setComponentband(componentband);
		return editTask;
	}

}
