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
 * @SetEncryptAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.encrypttext;

import java.awt.event.ActionEvent;
import java.util.Map;

import com.wisii.security.EncryptionDialog;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EncryptInformation;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-17
 */
public class SetEncryptAction extends Actions
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
		Map<Integer, Object> attrs = RibbonUIModel.getCurrentPropertiesByType()
				.get(ActionType.ENCRYPTTEXT);
		EncryptInformation enin = null;
		if (attrs != null)
		{
			enin = (EncryptInformation) attrs.get(Constants.PR_ENCRYPT);
		}
		EncryptionDialog dynamicstyle = new EncryptionDialog(enin);
		DialogResult result = dynamicstyle.showDialog();
		if (DialogResult.OK.equals(result))
		{
			EncryptInformation newencrypt = dynamicstyle
					.getEncryptInformation();
			setFOProperty(Constants.PR_ENCRYPT, newencrypt);
		}
	}

}
