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
 * @SetScalingAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.actions.externalgraphic;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.parts.picture.PicturePropertyPanel;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-12-23
 */
public class SetScalingAction extends Actions {
	
	private ActionCommandType actionCommandType;
	
	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JCheckBox)
		{
			JCheckBox ui = (JCheckBox) e.getSource();
			
			if (actionCommandType == ActionCommandType.DYNAMIC_ACTION) {
				if (ui.isSelected())
				{
					PicturePropertyPanel.getNewMap().put(Constants.PR_SCALING, new EnumProperty(
							Constants.EN_UNIFORM, null));
				} else
				{
					PicturePropertyPanel.getNewMap().put(Constants.PR_SCALING, new EnumProperty(
							Constants.EN_NON_UNIFORM, null));
				}
			} else {
				if (ui.isSelected())
				{
					setFOProperty(Constants.PR_SCALING, new EnumProperty(
							Constants.EN_UNIFORM, null));
				} else
				{
					setFOProperty(Constants.PR_SCALING, new EnumProperty(
							Constants.EN_NON_UNIFORM, null));
				}
			}
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_SCALING))
		{
			Object obj = evt.getNewValue();
			JCheckBox ui = (JCheckBox) uiComponent;
			if (obj instanceof EnumProperty)
			{
				EnumProperty el = (EnumProperty) obj;
				ui.setSelected(el.getEnum() == Constants.EN_UNIFORM);
			} else
			{
				ui.setSelected(true);
			}

		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand){
		
		actionCommandType = actionCommand;
		
		if (this.actionCommandType == ActionCommandType.DYNAMIC_ACTION) {
			super.setDefaultState(actionCommand);
			if (uiComponent instanceof JCheckBox) {
				JCheckBox ui = (JCheckBox) uiComponent;
				ui.setSelected(true);
			}
		} else {
			super.setDefaultState(actionCommand);
			if (uiComponent instanceof JCheckBox) {
				JCheckBox ui = (JCheckBox) uiComponent;
				ui.setSelected(true);
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof JCheckBox)
		{
			JCheckBox ui = (JCheckBox) uiComponent;
			List<CellElement> elements = getObjectSelects();
			if (elements == null || elements.isEmpty())
			{
				return;
			}
			CellElement element = elements.get(0);
			// 如果属性值不一样，则取第一个元素的属性值作为属性值
			if (element != null)
			{
				EnumProperty el = (EnumProperty) element.getAttribute(Constants.PR_SCALING);
				ui.setSelected((el != null && el.getEnum() == Constants.EN_UNIFORM));
			}
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
		CellElement element = elements.get(0);

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
