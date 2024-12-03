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
 * @SetContentWidthAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.externalgraphic;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.parts.picture.PicturePropertyPanel;

/**
 * 类功能描述：设置宽度属性
 * 
 * 作者：zhangqiang 创建日期：2008-12-23
 */
public class SetContentWidthAction extends Actions {
	
	private Length oldlength;
	
	private ActionCommandType actionCommandType;

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof FixedLengthSpinner)
		{
			FixedLengthSpinner ui = (FixedLengthSpinner) e.getSource();
			FixedLength length = ui.getValue();
			Length newlen;
			if (oldlength != null)
			{
				newlen = new EnumLength(oldlength.getEnum(), length);
			} else
			{
				newlen = new EnumLength(Constants.EN_AUTO, length);
			}
			
			if (actionCommandType == ActionCommandType.DYNAMIC_ACTION) {
				PicturePropertyPanel.getNewMap().put(Constants.PR_CONTENT_WIDTH, newlen);
			} else {
				setFOProperty(Constants.PR_CONTENT_WIDTH, newlen);
			}
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_CONTENT_WIDTH))
		{
			oldlength = (Length) evt.getNewValue();
			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				FixedLength uivalue = null;
				if (oldlength instanceof EnumLength)
				{
					Length length = ((EnumLength) oldlength).getFixLength();
					if (length instanceof FixedLength)
					{
						uivalue = (FixedLength) length;
					}
				} else if (oldlength instanceof FixedLength)
				{
					uivalue = (FixedLength) oldlength;
				}
				ui.initValue(uivalue);

			}

		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		
		this.actionCommandType = actionCommand;
		
		if (actionCommandType == ActionCommandType.DYNAMIC_ACTION) {
			super.setDefaultState(actionCommand);

			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				oldlength = new FixedLength(0);
				ui.initValue(oldlength);
			}
		} else {
			super.setDefaultState(actionCommand);

			if (uiComponent instanceof FixedLengthSpinner)
			{
				FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				oldlength = new FixedLength(0);
				ui.initValue(oldlength);
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		oldlength = null;
		if (uiComponent instanceof FixedLengthSpinner)
		{

			FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			List<CellElement> elements = getObjectSelects();
			if (elements == null || elements.isEmpty())
			{
				return;
			}
			CellElement firstelement = elements.get(0);
			oldlength = (Length) firstelement
					.getAttribute(Constants.PR_CONTENT_WIDTH);
			FixedLength uivalue = null;
			if (oldlength instanceof EnumLength)
			{
				Length length = ((EnumLength) oldlength).getFixLength();
				if (length instanceof FixedLength)
				{
					uivalue = (FixedLength) length;
				}
			} else if (oldlength instanceof FixedLength)
			{
				uivalue = (FixedLength) oldlength;
			}
			ui.initValue(uivalue);
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		List<CellElement> elements = getObjectSelects();
		if (elements == null || elements.isEmpty())
		{
			return false;
		}
		Map<Integer, Object> temp = RibbonUIModel
				.getReadCompletePropertiesByType().get(actionType);
		if (temp != null)
		{
			Length width = (Length) temp.get(Constants.PR_CONTENT_WIDTH);
			if (width != null)
			{
				return width.getEnum() != Constants.EN_AUTO;
			}
		}
		return false;
	}

}
