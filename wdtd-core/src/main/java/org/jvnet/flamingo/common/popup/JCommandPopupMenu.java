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
package org.jvnet.flamingo.common.popup;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButtonPanel;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.ui.BasicCommandPopupMenuUI;
import org.jvnet.flamingo.common.ui.PopupPanelUI;

public class JCommandPopupMenu extends JPopupPanel {
	/**
	 * @see #getUIClassID
	 */
	public static final String uiClassID = "CommandPopupMenuUI";

	protected JCommandButtonPanel mainButtonPanel;

	protected java.util.List<Component> menuComponents;

	protected int maxButtonColumns;

	protected int maxVisibleButtonRows;

	public JCommandPopupMenu() {
		this.menuComponents = new ArrayList<Component>();
	}

	public JCommandPopupMenu(JCommandButtonPanel buttonPanel,
			int maxButtonColumns, int maxVisibleButtonRows) {
		this();

		this.mainButtonPanel = buttonPanel;
		this.maxButtonColumns = maxButtonColumns;
		this.maxVisibleButtonRows = maxVisibleButtonRows;

		this.updateUI();
	}

	public void addMenuButton(JCommandMenuButton menuButton) {
		menuButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
		menuButton.setHorizontalAlignment(SwingUtilities.LEFT);
		this.menuComponents.add(menuButton);
		this.fireStateChanged();
	}

	public void addMenuSeparator() {
		this.menuComponents.add(new JPopupMenu.Separator());
		this.fireStateChanged();
	}

	public boolean hasCommandButtonPanel() {
		return (this.mainButtonPanel != null);
	}

	public JCommandButtonPanel getMainButtonPanel() {
		return mainButtonPanel;
	}

	public java.util.List<Component> getMenuComponents() {
		if (this.menuComponents == null)
			return null;
		return Collections.unmodifiableList(menuComponents);
	}

	public int getMaxButtonColumns() {
		return maxButtonColumns;
	}

	public int getMaxVisibleButtonRows() {
		return maxVisibleButtonRows;
	}

	@Override
	public String getUIClassID() {
		return uiClassID;
	}

	@Override
	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((PopupPanelUI) UIManager.getUI(this));
		} else {
			setUI(BasicCommandPopupMenuUI.createUI(this));
		}
	}

	/**
	 * Adds the specified change listener to track changes to this popup menu.
	 * 
	 * @param l
	 *            Change listener to add.
	 * @see #removeChangeListener(ChangeListener)
	 */
	public void addChangeListener(ChangeListener l) {
		this.listenerList.add(ChangeListener.class, l);
	}

	/**
	 * Removes the specified change listener from tracking changes to this popup
	 * menu.
	 * 
	 * @param l
	 *            Change listener to remove.
	 * @see #addChangeListener(ChangeListener)
	 */
	public void removeChangeListener(ChangeListener l) {
		this.listenerList.remove(ChangeListener.class, l);
	}

	/**
	 * Notifies all registered listener that the state of this popup menu has
	 * changed.
	 */
	protected void fireStateChanged() {
		// Guaranteed to return a non-null array
		Object[] listeners = this.listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		ChangeEvent event = new ChangeEvent(this);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				((ChangeListener) listeners[i + 1]).stateChanged(event);
			}
		}
	}
}
