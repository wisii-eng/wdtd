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

package com.wisii.wisedoc.view.ui.actions.edit;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

public class EditAppearanceAction extends AbstractEditAction
{

	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox editbox = (WiseCombobox) e.getSource();
			EnumProperty type = create(editbox);
			setFOProperty(Constants.PR_APPEARANCE, type);
		}

	}

	private EnumProperty create(WiseCombobox editbox)
	{
		int selectindex = editbox.getSelectedIndex();
		EnumProperty type = null;
		switch (selectindex)
		{
		case 0:
		{
			type = new EnumProperty(Constants.EN_FULL, "");
			break;
		}
		case 1:
		{
			type = new EnumProperty(Constants.EN_COMPACT, "");
			break;
		}
		case 2:
		{
			type = new EnumProperty(Constants.EN_MINIMAL, "");
			break;
		}
		default:
			break;
		}
		return type;
	}

	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);
		if (uiComponent instanceof WiseCombobox
				&& hasPropertyKey(Constants.PR_APPEARANCE))
		{
			Object obj = evt.getNewValue();

			updateUI((WiseCombobox) uiComponent, obj);
		}
	}

	private void updateUI(WiseCombobox uiComponent, Object obj)
	{
		if (obj == null)
		{
			uiComponent.initIndex(0);
		} else if (obj instanceof EnumProperty)
		{
			int type = ((EnumProperty) obj).getEnum();
			switch (type)
			{
			case Constants.EN_FULL:
			{
				uiComponent.initIndex(0);
				break;
			}
			case Constants.EN_COMPACT:
			{
				uiComponent.initIndex(1);
				break;
			}
			case Constants.EN_MINIMAL:
			{
				uiComponent.initIndex(2);
				break;
			}
			default:
				break;
			}
		}

	}

	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_APPEARANCE))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				WiseCombobox box = (WiseCombobox) uiComponent;
				box.InitValue(null);
			}
		}
	}

	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(actionCommand);
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox box = (WiseCombobox) uiComponent;
			box.initIndex(0);
		}
	}
}
