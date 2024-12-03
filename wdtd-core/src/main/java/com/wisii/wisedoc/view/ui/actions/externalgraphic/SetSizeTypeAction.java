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
 * @SetSizeTypeAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.externalgraphic;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.area.inline.Viewport;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.parts.picture.PicturePropertyPanel;

/**
 * 类功能描述：设置图片大小的类型
 * 
 * 作者：zhangqiang 创建日期：2008-12-23
 */
public class SetSizeTypeAction extends Actions {
	
	private ActionCommandType actionCommandType;
	
	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox ui = (WiseCombobox) e.getSource();
			int index = ui.getSelectedIndex();

			if (index > -1)
			{
				Map<Integer, Object> newatts = new HashMap<Integer, Object>();
				if (index == 0)
				{
					EnumLength enumlen = new EnumLength(Constants.EN_AUTO, null);
					newatts.put(Constants.PR_CONTENT_WIDTH, enumlen);
					newatts.put(Constants.PR_CONTENT_HEIGHT, enumlen);
				} else if (index == 1)
				{
					List<CellElement> elements = getObjectSelects();
					if (elements == null || elements.isEmpty())
					{
						return;
					}
					CellElement element = elements.get(0);
					if (element instanceof ExternalGraphic)
					{
						ExternalGraphic eg = (ExternalGraphic) element;
						FixedLength oldwidth = new FixedLength(0);
						FixedLength oldheight = oldwidth;
						Viewport area = eg.getArea();
						if (area != null)
						{
							oldwidth = new FixedLength(area.getIPD());
							oldheight = new FixedLength(area.getBPD());
						}
						EnumLength enumwidth = new EnumLength(-1, oldwidth);
						EnumLength enumheight = new EnumLength(-1, oldheight);
						newatts.put(Constants.PR_CONTENT_WIDTH, enumwidth);
						newatts.put(Constants.PR_CONTENT_HEIGHT, enumheight);
					}
				}
				if (!newatts.isEmpty()) {
					
					if (actionCommandType == ActionCommandType.DYNAMIC_ACTION) {
						PicturePropertyPanel.getNewMap().putAll(newatts);
					} else {
						setFOProperties(newatts);
					}
				}
			}

		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		if (hasPropertyKey(Constants.PR_CONTENT_WIDTH))
		{
			Object obj = evt.getNewValue();
			if (uiComponent instanceof WiseCombobox)
			{

				WiseCombobox ui = (WiseCombobox) uiComponent;
				if (obj != null)
				{
					if (obj instanceof EnumLength)
					{
						EnumLength el = (EnumLength) obj;
						int index = ui.getSelectedIndex();
						if (el.getEnum() == Constants.EN_AUTO)
						{
							ui.initIndex(0);
						} else
						{
							ui.initIndex(1);
						}
					}

				} else
				{
					ui.initIndex(0);
				}
			}

		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		
		this.actionCommandType = actionCommand;
		
		if (actionCommandType == ActionCommandType.DYNAMIC_ACTION) {
			super.setDefaultState(actionCommand);
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				ui.initIndex(0);
			}
		} else {
			super.setDefaultState(actionCommand);
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox ui = (WiseCombobox) uiComponent;
				ui.initIndex(0);
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox ui = (WiseCombobox) uiComponent;
			ui.InitValue(null);
		}
	}

	@Override
	public boolean isAvailable()
	{
		return super.isAvailable();
	}

}
