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
 * @SetSelectNextAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.edit.Next;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.ribbon.edit.NextDialog;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-12-16
 */
public class SetSelectNextAction extends AbstractEditAction
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
		Next next = null;
		if (attrs != null)
		{
			next = (Next) attrs.get(Constants.PR_SELECT_NEXT);
		}
		NextDialog dialog = new NextDialog(next);
		DialogResult result = dialog.showDialog();
		if (result == DialogResult.OK)
		{
			Next ninfo = dialog.getNext();
			setFOProperty(Constants.PR_SELECT_NEXT, ninfo);
		}
	}

}
