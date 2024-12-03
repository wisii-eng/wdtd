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
 * @abstractValidationsetAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.edit.Validation;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.ribbon.edit.ValidationSetDialog;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-20
 */
public abstract class AbstractValidationsetAction extends AbstractEditAction
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
		Validation oldvalidation = null;
		if (attrs != null)
		{
			oldvalidation = (Validation) attrs.get(getkey());
		}
		ValidationSetDialog dia = new ValidationSetDialog(oldvalidation);
		DialogResult result = dia.showDialog();
		if (result == DialogResult.OK)
		{
			Validation newvalidation = dia.getValidation();
			if (!newvalidation.equals(oldvalidation))
			{
				setFOProperty(getkey(), newvalidation);
			}
		}
	}

	public abstract int getkey();
	protected int getEditType()
	{
		Map<Integer, Object> temp = RibbonUIModel
				.getReadCompletePropertiesByType().get(actionType);
		if (temp != null)
		{
			EnumProperty typeenum = (EnumProperty) temp
					.get(Constants.PR_EDITTYPE);
			if (typeenum != null)
			{
				return typeenum.getEnum();
			}
		}
		return -1;
	}
}
