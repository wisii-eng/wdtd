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

package com.wisii.wisedoc.view.ui.ribbon;

import javax.swing.SwingUtilities;

import org.jvnet.flamingo.common.AbstractCommandButton;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.CommandButtonLayoutManager;
import org.jvnet.flamingo.common.JCommandButtonPanel;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonPopupOrientationKind;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntryPrimary;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntrySecondary;
import org.jvnet.flamingo.ribbon.ui.appmenu.CommandButtonLayoutManagerMenuTileLevel2;

public class JRibbonApplicationMenuPopupPaneladd extends JCommandButtonPanel
{

	protected static final CommandButtonDisplayState MENU_TILE_LEVEL_2 = new CommandButtonDisplayState(
			"Ribbon application menu tile level 2", 32)
	{

		@Override
		public CommandButtonLayoutManager createLayoutManager(
				AbstractCommandButton commandButton)
		{
			return new CommandButtonLayoutManagerMenuTileLevel2();
		}

		@Override
		public CommandButtonSeparatorOrientation getSeparatorOrientation()
		{
			return CommandButtonSeparatorOrientation.VERTICAL;
		}
	};

	public JRibbonApplicationMenuPopupPaneladd(
			RibbonApplicationMenuEntryPrimary primaryMenuEntry)
	{
		super(MENU_TILE_LEVEL_2);
		this.setMaxButtonColumns(1);

		int groupCount = primaryMenuEntry.getSecondaryGroupCount();
		for (int i = 0; i < groupCount; i++)
		{
			String groupDesc = primaryMenuEntry.getSecondaryGroupTitleAt(i);
			this.addButtonGroup(groupDesc);

			for (final RibbonApplicationMenuEntrySecondary menuEntry : primaryMenuEntry
					.getSecondaryGroupEntries(i))
			{
				if ("当前章节导出为XSLT文档".equalsIgnoreCase(menuEntry.getText()))
				{
					menuEntry.setEnabled(menuEntry.isAvailable());
				}
				JCommandMenuButton commandButton = new JCommandMenuButton(
						menuEntry.getText(), menuEntry.getIcon());
				commandButton.setExtraText(menuEntry.getDescriptionText());
				commandButton.setCommandButtonKind(menuEntry.getEntryKind());
				commandButton.addActionListener(menuEntry
						.getMainActionListener());
				commandButton.setDisplayState(MENU_TILE_LEVEL_2);
				commandButton.setHorizontalAlignment(SwingUtilities.LEADING);
				commandButton
						.setPopupOrientationKind(CommandButtonPopupOrientationKind.SIDEWARD);
				commandButton.setEnabled(menuEntry.isEnabled());
				commandButton.setPopupCallback(menuEntry.getPopupCallback());
				this.addButtonToLastGroup(commandButton);
			}
		}
	}
}
