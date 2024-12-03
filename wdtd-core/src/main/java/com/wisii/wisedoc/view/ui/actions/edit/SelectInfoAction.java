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
 * @SelectInfoAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.ribbon.edit.SelectDataSettingDialog;
/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-1
 */
public class SelectInfoAction extends AbstractEditAction
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.ui.actions.Actions#doAction(java.awt.event.ActionEvent
	 * )
	 */
	@Override
	public void doAction(ActionEvent e)
	{
		Map<Integer, Object> attrs = RibbonUIModel
				.getReadCompletePropertiesByType().get(actionType);
		SelectInfo selectinfo = null;
		if (attrs != null)
		{
			selectinfo = (SelectInfo) attrs.get(Constants.PR_SELECT_INFO);
		}
		SelectDataSettingDialog dialog = new SelectDataSettingDialog(selectinfo);
		DialogResult result = dialog.showDialog();
		if (result == DialogResult.OK)
		{
			SelectInfo ninfo = dialog.getSelectInFo();
			setFOProperty(Constants.PR_SELECT_INFO, ninfo);
		}
	}

}
