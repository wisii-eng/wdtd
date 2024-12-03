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
package com.wisii.wisedoc.view.ui.actions.text;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import org.jvnet.flamingo.common.JCommandToggleButton;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.document.datatype.PercentBase;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 为Ribbon界面提供的上标动作
 * 
 * @author 闫舒寰
 * @version 0.1 2008/10/23
 */
public class FontSuperAction extends Actions
{

	/**
	 * TODO
	 * 1、该动作的有效性覆盖范围不正确
	 * 2、当用户选择上标和正常文字混合的情况下会出现设置属性不成功的问题
	 */
	
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCommandToggleButton)
		{
			JCommandToggleButton superButton = (JCommandToggleButton) e
					.getSource();
			Object normalFontSize = RibbonUIModel.getCurrentPropertiesByType().get(this.actionType).get(Constants.PR_FONT_SIZE);
			if (superButton.getActionModel().isSelected())
			{
				// setFOProperties(superProperties);
				Map<Integer, Object> temp = new HashMap<Integer, Object>();
				if(normalFontSize instanceof PercentLength)
				{
					PercentBase lenbase = ((PercentLength) normalFontSize).getBaseLength();
					if(lenbase instanceof LengthBase)
					{
						normalFontSize = ((LengthBase)lenbase).getBaseLength();
					}
				}
				if (normalFontSize instanceof FixedLength)
				{
					temp.put(Constants.PR_BASELINE_SHIFT, new EnumLength(
							Constants.EN_SUPER, null));
					temp.put(Constants.PR_FONT_SIZE, new PercentLength(0.65,
							new LengthBase((Length) normalFontSize,
									LengthBase.FONTSIZE)));

					setFOProperties(temp);
				}
			} else
			{
				if (normalFontSize instanceof PercentLength)
				{
					PercentLength perlen = (PercentLength) normalFontSize;
					if (perlen.getBaseLength() instanceof LengthBase)
					{
						LengthBase lenbase = (LengthBase) perlen.getBaseLength();
						Map<Integer, Object> temp = new HashMap<Integer, Object>();
						temp.put(Constants.PR_BASELINE_SHIFT, new EnumLength(
								Constants.EN_AUTO, null));
						temp.put(Constants.PR_FONT_SIZE, lenbase
								.getBaseLength());

						setFOProperties(temp);
					}
				}

			}
		}
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_BASELINE_SHIFT))
		{
			if (uiComponent instanceof JCommandToggleButton)
			{
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				Object shiftvalue = evt.getNewValue();
				ui
						.getActionModel()
						.setSelected(
								shiftvalue instanceof EnumLength
										&& ((EnumLength) shiftvalue).getEnum() == Constants.EN_SUPER);

			}
		}
	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_BASELINE_SHIFT))
		{
			if (uiComponent instanceof JCommandToggleButton)
			{
				JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
				ui.getActionModel().setSelected(false);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (uiComponent instanceof JCommandToggleButton)
		{
			JCommandToggleButton ui = (JCommandToggleButton) uiComponent;
			ui.getActionModel().setSelected(false);
		}
	}
}
