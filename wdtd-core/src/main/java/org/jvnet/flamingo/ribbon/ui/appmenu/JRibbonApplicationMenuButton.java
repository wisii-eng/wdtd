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
/*
 * Copyright (c) 2005-2009 Flamingo Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Flamingo Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package org.jvnet.flamingo.ribbon.ui.appmenu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import org.jvnet.flamingo.common.AbstractCommandButton;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.CommandButtonLayoutManager;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.ui.BasicCommandButtonUI;
import org.jvnet.flamingo.ribbon.JRibbon;
import org.jvnet.flamingo.ribbon.JRibbonFrame;

import com.wisii.wisedoc.resource.MediaResource;

/**
 * The main application menu button for {@link JRibbon} component placed in a
 * {@link JRibbonFrame}. This class is for internal use only and is intended for
 * look-and-feel layer customization.
 * 
 * @author Kirill Grouchnikov
 */
public class JRibbonApplicationMenuButton extends JCommandButton {
	/**
	 * The UI class ID string.
	 */
	public static final String uiClassID = "RibbonApplicationMenuButtonUI";

	private final static CommandButtonDisplayState APP_MENU_BUTTON_STATE = new CommandButtonDisplayState(
			"Ribbon Application Menu Button", 24) {
		@Override
		public org.jvnet.flamingo.common.CommandButtonLayoutManager createLayoutManager(
				org.jvnet.flamingo.common.AbstractCommandButton commandButton) {
			return new CommandButtonLayoutManager() {
				public int getPreferredIconSize() {
					return 24;
				}

				@Override
				public CommandButtonLayoutInfo getLayoutInfo(
						AbstractCommandButton commandButton, Graphics g) {
					CommandButtonLayoutInfo result = new CommandButtonLayoutInfo();
					result.actionClickArea = new Rectangle(0, 0, 0, 0);
					result.popupClickArea = new Rectangle(0, 0, commandButton
							.getWidth(), commandButton.getHeight());
					result.popupActionRect = new Rectangle(0, 0, 0, 0);
					ResizableIcon icon = commandButton.getIcon();
					result.iconRect = new Rectangle(
							(commandButton.getWidth() - icon.getIconWidth()) / 2,
							(commandButton.getHeight() - icon.getIconHeight()) / 2,
							icon.getIconWidth(), icon.getIconHeight());
					return result;
				}

				@Override
				public Dimension getPreferredSize(
						AbstractCommandButton commandButton) {
					return new Dimension(40, 40);
				}

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
				}

				@Override
				public Point getKeyTipAnchorCenterPoint(
						AbstractCommandButton commandButton) {
					// dead center
					return new Point(commandButton.getWidth() / 2,
							commandButton.getHeight() / 2);
				}
			};
		}

		@Override
		public CommandButtonSeparatorOrientation getSeparatorOrientation() {
			return CommandButtonSeparatorOrientation.VERTICAL;
		}
	};

	/**
	 * Creates a new application menu button.
	 */
	public JRibbonApplicationMenuButton()
	{
		super("", new EmptyResizableIcon(16));
		this.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
		this.setDisplayState(APP_MENU_BUTTON_STATE);
		try
		{
			Image image = ImageIO.read(URL.class.getResource("/com/wisii/wisedoc/resource/image/wisii.gif"));
			setIcon(ImageWrapperResizableIcon.getIcon(image, new Dimension(image
					.getWidth(null), image.getHeight(null))));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JButton#updateUI()
	 */
	@Override
	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((BasicCommandButtonUI) UIManager.getUI(this));
		} else {
			setUI(BasicRibbonApplicationMenuButtonUI.createUI(this));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JButton#getUIClassID()
	 */
	@Override
	public String getUIClassID() {
		return uiClassID;
	}
}
