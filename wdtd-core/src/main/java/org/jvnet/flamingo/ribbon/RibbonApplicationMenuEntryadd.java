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

package org.jvnet.flamingo.ribbon;

import java.awt.event.ActionListener;

import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;

import com.wisii.wisedoc.SystemManager;

public class RibbonApplicationMenuEntryadd extends
		RibbonApplicationMenuEntrySecondary
{

	/**
	 * Extra description text for this secondary menu entry.
	 */
	protected String descriptionText;

	/**
	 * Popup menu listener for this menu entry. Must be not <code>null</code> if
	 * the menu entry kind has popup part.
	 */
	protected PopupPanelCallback popupCallback;

	public RibbonApplicationMenuEntryadd(ResizableIcon icon, String text,
			ActionListener mainActionListener, CommandButtonKind entryKind)
	{
		super(icon, text, mainActionListener, entryKind);
	}

	public boolean isAvailable()
	{
		boolean flg = false;
		if (SystemManager.getMainframe() != null
				&& SystemManager.getMainframe().getEidtComponent() != null
				&& SystemManager.getMainframe().getEidtComponent()
						.getCaretPosition() != null)
		{
			flg = true;
		}
		if (SystemManager.getMainframe() != null
				&& SystemManager.getMainframe().getEidtComponent() != null
				&& SystemManager.getMainframe().getEidtComponent()
						.getSelectionModel() != null
				&& SystemManager.getMainframe().getEidtComponent()
						.getSelectionModel().getSelectedObject() != null)
		{
			flg = true;
		}

		return flg;
	}
}
