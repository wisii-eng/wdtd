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
package org.jvnet.flamingo.ribbon;

import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.SwingConstants;

import org.jvnet.flamingo.common.AbstractCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.StringValuePair;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.ribbon.resize.CoreRibbonResizePolicies;
import org.jvnet.flamingo.ribbon.ui.JBandControlPanel;
import org.jvnet.flamingo.ribbon.ui.JRibbonGallery;

/**
 * Ribbon band component. Is part of a logical {@link RibbonTask}.
 * 
 * @author Kirill Grouchnikov
 */
public class JRibbonBand extends AbstractRibbonBand<JBandControlPanel> {
	/**
	 * This callback allows application code to place additional menu entries in
	 * the popup menu shown when the ribbon gallery expand button is clicked.
	 * Application code should use
	 * {@link JCommandPopupMenu#addMenuButton(JCommandMenuButton)} and
	 * {@link JCommandPopupMenu#addMenuSeparator()} APIs.
	 * 
	 * @author Kirill Grouchnikov
	 */
	public static interface RibbonGalleryPopupCallback {
		/**
		 * Called just before the popup menu is about to be shown.
		 * 
		 * @param menu
		 *            The popup menu that will be shown.
		 */
		public void popupToBeShown(JCommandPopupMenu menu);
	}

	/**
	 * Creates a new ribbon band.
	 * 
	 * @param title
	 *            Band title.
	 * @param icon
	 *            Associated icon (for collapsed state).
	 */
	public JRibbonBand(String title, ResizableIcon icon) {
		this(title, icon, null);
	}

	/**
	 * Creates a new ribbon band.
	 * 
	 * @param title
	 *            Band title.
	 * @param icon
	 *            Associated icon (for collapsed state).
	 * @param expandActionListener
	 *            Expand action listener (can be <code>null</code>).
	 */
	public JRibbonBand(String title, ResizableIcon icon,
			ActionListener expandActionListener) {
		super(title, icon, expandActionListener, new JBandControlPanel());
		this.resizePolicies = Collections
				.unmodifiableList(CoreRibbonResizePolicies
						.getCorePoliciesPermissive(this));
		updateUI();
	}

	/**
	 * Adds the specified command button to <code>this</code> band.
	 * 
	 * @param commandButton
	 *            Command button to add.
	 * @param priority
	 *            Priority of the button.
	 */
	public void addCommandButton(AbstractCommandButton commandButton,
			RibbonElementPriority priority) {
		commandButton.setHorizontalAlignment(SwingConstants.LEFT);
		this.controlPanel.addCommandButton(commandButton, priority);
	}

	/**
	 * Adds new in-ribbon gallery to <code>this</code> band.
	 * 
	 * @param galleryName
	 *            Gallery name.
	 * @param buttons
	 *            Button groups.
	 * @param preferredVisibleButtonCounts
	 *            Preferred count of visible buttons of the in-ribbon gallery
	 *            under different states.
	 * @param preferredPopupMaxButtonColumns
	 *            Preferred maximum columns in the popup gallery associated with
	 *            the in-ribbon gallery.
	 * @param preferredPopupMaxVisibleButtonRows
	 *            Preferred maximum visible rows in the popup gallery associated
	 *            with the in-ribbon gallery.
	 * @param priority
	 *            The initial in-ribbon gallery priority.
	 * @see #addRibbonGalleryButtons(String, String, JCommandToggleButton...)
	 * @see #removeRibbonGalleryButtons(String, JCommandToggleButton...)
	 * @see #setSelectedRibbonGalleryButton(String, JCommandToggleButton)
	 */
	public void addRibbonGallery(String galleryName,
			List<StringValuePair<List<JCommandToggleButton>>> buttons,
			Map<RibbonElementPriority, Integer> preferredVisibleButtonCounts,
			int preferredPopupMaxButtonColumns,
			int preferredPopupMaxVisibleButtonRows,
			RibbonElementPriority priority) {
		JRibbonGallery gallery = new JRibbonGallery();
		gallery.setName(galleryName);
		for (Map.Entry<RibbonElementPriority, Integer> prefCountEntry : preferredVisibleButtonCounts
				.entrySet()) {
			gallery.setPreferredVisibleButtonCount(prefCountEntry.getKey(),
					prefCountEntry.getValue());
		}
		gallery.setGroupMapping(buttons);
		gallery.setPreferredPopupPanelDimension(preferredPopupMaxButtonColumns,
				preferredPopupMaxVisibleButtonRows);

		this.controlPanel.addRibbonGallery(gallery, priority);
	}

	/**
	 * Adds toggle command buttons to a button group in the specified in-ribbon
	 * gallery.
	 * 
	 * @param galleryName
	 *            In-ribbon gallery name.
	 * @param buttonGroupName
	 *            Button group name.
	 * @param buttons
	 *            Buttons to add.
	 * @see #addRibbonGallery(String, List, Map, int, int,
	 *      RibbonElementPriority)
	 * @see #removeRibbonGalleryButtons(String, JCommandToggleButton...)
	 * @see #setSelectedRibbonGalleryButton(String, JCommandToggleButton)
	 */
	public void addRibbonGalleryButtons(String galleryName,
			String buttonGroupName, JCommandToggleButton... buttons) {
		JRibbonGallery gallery = this.controlPanel
				.getRibbonGallery(galleryName);
		if (gallery == null)
			return;
		gallery.addRibbonGalleryButtons(buttonGroupName, buttons);
	}

	/**
	 * Removes toggle command buttons from the specified in-ribbon gallery.
	 * 
	 * @param galleryName
	 *            In-ribbon gallery name.
	 * @param buttons
	 *            Buttons to add.
	 * @see #addRibbonGallery(String, List, Map, int, int,
	 *      RibbonElementPriority)
	 * @see #addRibbonGalleryButtons(String, String, JCommandToggleButton...)
	 * @see #setSelectedRibbonGalleryButton(String, JCommandToggleButton)
	 */
	public void removeRibbonGalleryButtons(String galleryName,
			JCommandToggleButton... buttons) {
		JRibbonGallery gallery = this.controlPanel
				.getRibbonGallery(galleryName);
		if (gallery == null)
			return;
		gallery.removeRibbonGalleryButtons(buttons);
	}

	/**
	 * Selects the specified toggle command button in the specified in-ribbon
	 * gallery.
	 * 
	 * @param galleryName
	 *            In-ribbon gallery name.
	 * @param buttonToSelect
	 *            Button to select.
	 * @see #addRibbonGallery(String, List, Map, int, int,
	 *      RibbonElementPriority)
	 * @see #addRibbonGalleryButtons(String, String, JCommandToggleButton...)
	 * @see #removeRibbonGalleryButtons(String, JCommandToggleButton...)
	 */
	public void setSelectedRibbonGalleryButton(String galleryName,
			JCommandToggleButton buttonToSelect) {
		JRibbonGallery gallery = this.controlPanel
				.getRibbonGallery(galleryName);
		if (gallery == null)
			return;
		gallery.setSelectedButton(buttonToSelect);
	}

	/**
	 * Sets the application callback to place additional entries in the popup
	 * menu shown when the specified ribbon gallery is expanded.
	 * 
	 * @param galleryName
	 *            Gallery name.
	 * @param popupCallback
	 *            Application callback.
	 * @see RibbonGalleryPopupCallback
	 */
	public void setRibbonGalleryPopupCallback(String galleryName,
			RibbonGalleryPopupCallback popupCallback) {
		JRibbonGallery gallery = this.controlPanel
				.getRibbonGallery(galleryName);
		if (gallery == null)
			return;
		gallery.setPopupCallback(popupCallback);
	}

	public void setRibbonGalleryExpandKeyTip(String galleryName,
			String expandKeyTip) {
		JRibbonGallery gallery = this.controlPanel
				.getRibbonGallery(galleryName);
		if (gallery == null)
			return;
		gallery.setExpandKeyTip(expandKeyTip);
	}

	public void addRibbonComponent(JRibbonComponent comp) {
		this.controlPanel.addRibbonComponent(comp);
	}

	public void startGroup() {
		this.controlPanel.startGroup();
	}

	public void startGroup(String groupTitle) {
		this.controlPanel.startGroup(groupTitle);
	}

	@Override
	public AbstractRibbonBand<JBandControlPanel> cloneBand() {
		return new JRibbonBand(this.getTitle(), this.getIcon(), this
				.getExpandActionListener());
	}
}
