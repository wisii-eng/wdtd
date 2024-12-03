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
 * @ParagraphLineSpaceofMultipleAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.paragraph;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;

/**
 * 类功能描述：倍数行间距设置操作类
 * 
 * 作者：zhangqiang 创建日期：2009-1-12
 */
public abstract class ParagraphLineSpaceofMultipleAction extends Actions
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.ui.actions.Actions#doAction(java.awt.event.ActionEvent
	 * )
	 */
	public void doAction(ActionEvent e)
	{
		SpaceProperty old = (SpaceProperty) RibbonUIModel.getCurrentPropertiesByType().get(ActionType.BLOCK).get(Constants.PR_LINE_HEIGHT);
		PercentLength percentLength = null;
		double factor = getMultiple();
		if (factor <= 0)
		{
			return;
		}
		LengthProperty lp = old.getLengthRange().getOptimum(null);
		if (lp instanceof FixedLength)
		{
			percentLength = new PercentLength(factor, new LengthBase(
					(FixedLength) lp, LengthBase.FONTSIZE));
		} else if (lp instanceof PercentLength)
		{
			percentLength = new PercentLength(factor, ((PercentLength) lp)
					.getBaseLength());
		}

		EnumNumber precedence = old != null ? old.getPrecedence() : null;
		EnumProperty conditionality = old != null ? old.getConditionality()
				: null;
		setFOProperty(Constants.PR_LINE_HEIGHT, new SpaceProperty(
				percentLength, precedence, conditionality));
	}

	protected abstract double getMultiple();
}
