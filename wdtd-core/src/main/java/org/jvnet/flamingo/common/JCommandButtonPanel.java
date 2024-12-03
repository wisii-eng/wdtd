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
package org.jvnet.flamingo.common;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jvnet.flamingo.common.ui.BasicCommandButtonPanelUI;
import org.jvnet.flamingo.common.ui.CommandButtonPanelUI;

/**
 * Panel that hosts command buttons. Provides support for button groups, single
 * selection mode (for toggle command buttons), same icon state / dimension and
 * automatic column layout.
 * 
 * @author Kirill Grouchnikov
 */
public class JCommandButtonPanel extends JPanel implements Scrollable {
	/**
	 * @see #getUIClassID
	 */
	public static final String uiClassID = "CommandButtonPanelUI";

	/**
	 * List of titles for all button groups.
	 */
	protected List<String> groupTitles;

	/**
	 * List of all button groups.
	 */
	protected List<List<AbstractCommandButton>> buttons;

	/**
	 * Maximum number of columns for this panel. Relevant only when the layout
	 * kind is {@link LayoutKind#ROW_FILL}.
	 */
	protected int maxButtonColumns;

	/**
	 * Maximum number of rows for this panel. Relevant only when the layout kind
	 * is {@link LayoutKind#COLUMN_FILL}.
	 */
	protected int maxButtonRows;

	/**
	 * Indicates the selection mode for the {@link JCommandToggleButton} in this
	 * panel.
	 */
	protected boolean isSingleSelectionMode;

	/**
	 * If <code>true</code>, the panel will show group labels.
	 */
	protected boolean toShowGroupLabels;

	/**
	 * The button group for the single selection mode.
	 */
	protected CommandToggleButtonGroup buttonGroup;

	/**
	 * Current icon dimension.
	 */
	protected int currDimension;

	/**
	 * Current icon state.
	 */
	protected CommandButtonDisplayState currState;

	/**
	 * Layout kind of this button panel.
	 */
	protected LayoutKind layoutKind;

	/**
	 * Enumerates the available layout kinds.
	 * 
	 * @author Kirill Grouchnikov
	 */
	public enum LayoutKind {
		/**
		 * The buttons are layed out in rows respecting the preferred width.
		 */
		ROW_FILL,

		/**
		 * The buttons are layed out in columns respecting the preferred height.
		 */
		COLUMN_FILL
	}

	/**
	 * Creates a new panel.
	 */
	protected JCommandButtonPanel() {
		this.buttons = new ArrayList<List<AbstractCommandButton>>();
		this.groupTitles = new ArrayList<String>();
		this.maxButtonColumns = -1;
		this.maxButtonRows = -1;
		this.isSingleSelectionMode = false;
		this.toShowGroupLabels = true;
		this.setLayoutKind(LayoutKind.ROW_FILL);
	}

	/**
	 * Creates a new panel.
	 * 
	 * @param startingDimension
	 *            Initial dimension for buttons.
	 */
	public JCommandButtonPanel(int startingDimension) {
		this();
		this.currDimension = startingDimension;
		this.currState = CommandButtonDisplayState.ORIG;
		this.updateUI();
	}

	/**
	 * Creates a new panel.
	 * 
	 * @param startingState
	 *            Initial state for buttons.
	 */
	public JCommandButtonPanel(CommandButtonDisplayState startingState) {
		this();
		this.currDimension = -1;
		this.currState = startingState;
		this.updateUI();
	}

	/**
	 * Adds a new button group at the specified index.
	 * 
	 * @param buttonGroupName
	 *            Button group name.
	 * @param groupIndex
	 *            Button group index.
	 */
	public void addButtonGroup(String buttonGroupName, int groupIndex) {
		this.groupTitles.add(groupIndex, buttonGroupName);
		List<AbstractCommandButton> list = new ArrayList<AbstractCommandButton>();
		this.buttons.add(groupIndex, list);
		this.fireStateChanged();
	}

	/**
	 * Adds a new button group after all the existing button groups.
	 * 
	 * @param buttonGroupName
	 *            Button group name.
	 */
	public void addButtonGroup(String buttonGroupName) {
		this.addButtonGroup(buttonGroupName, this.groupTitles.size());
	}

	/**
	 * Removes the specified button group.
	 * 
	 * @param buttonGroupName
	 *            Name of the button group to remove.
	 */
	public void removeButtonGroup(String buttonGroupName) {
		int groupIndex = this.groupTitles.indexOf(buttonGroupName);
		if (groupIndex < 0)
			return;
		this.groupTitles.remove(groupIndex);
		List<AbstractCommandButton> list = this.buttons.get(groupIndex);
		if (list != null) {
			for (AbstractCommandButton button : list) {
				this.remove(button);
				if (this.isSingleSelectionMode
						&& (button instanceof JCommandToggleButton)) {
					this.buttonGroup.remove((JCommandToggleButton) button);
				}
			}
		}
		this.buttons.remove(groupIndex);
		this.fireStateChanged();
	}

	/**
	 * Adds a new button to the specified button group.
	 * 
	 * @param buttonGroupName
	 *            Name of the button group.
	 * @param commandButton
	 *            Button to add.
	 * @return Returns the index of the button on the specified group, or -1 if
	 *         no such group exists.
	 */
	public int addButtonToLastGroup(AbstractCommandButton commandButton) {
		if (this.groupTitles.size() == 0)
			return -1;
		int groupIndex = this.groupTitles.size() - 1;
		commandButton.setDisplayState(this.currState);
		return this.addButtonToGroup(this.groupTitles.get(groupIndex),
				this.buttons.get(groupIndex).size(), commandButton);
	}

	/**
	 * Adds a new button to the specified button group.
	 * 
	 * @param buttonGroupName
	 *            Name of the button group.
	 * @param commandButton
	 *            Button to add.
	 * @return Returns the index of the button on the specified group, or -1 if
	 *         no such group exists.
	 */
	public int addButtonToGroup(String buttonGroupName,
			AbstractCommandButton commandButton) {
		int groupIndex = this.groupTitles.indexOf(buttonGroupName);
		if (groupIndex < 0)
			return -1;
		commandButton.setDisplayState(this.currState);
		return this.addButtonToGroup(buttonGroupName, this.buttons.get(
				groupIndex).size(), commandButton);
	}

	/**
	 * Adds a new button to the specified button group.
	 * 
	 * @param buttonGroupName
	 *            Name of the button group.
	 * @param indexInGroup
	 *            Index of the button in group.
	 * @param commandButton
	 *            Button to add.
	 * @return Returns the index of the button on the specified group, or -1 if
	 *         no such group exists.
	 */
	public int addButtonToGroup(String buttonGroupName, int indexInGroup,
			AbstractCommandButton commandButton) {
		int groupIndex = this.groupTitles.indexOf(buttonGroupName);
		if (groupIndex < 0)
			return -1;
		// commandButton.setState(ElementState.ORIG, true);
		this.add(commandButton);
		this.buttons.get(groupIndex).add(indexInGroup, commandButton);
		if (this.isSingleSelectionMode
				&& (commandButton instanceof JCommandToggleButton)) {
			this.buttonGroup.add((JCommandToggleButton) commandButton);
		}
		this.fireStateChanged();
		return indexInGroup;
	}

	/**
	 * Removes the button at the specified index from the specified button
	 * group.
	 * 
	 * @param buttonGroupName
	 *            Name of the button group.
	 * @param indexInGroup
	 *            Index of the button to remove.
	 */
	public void removeButtonFromGroup(String buttonGroupName, int indexInGroup) {
		int groupIndex = this.groupTitles.indexOf(buttonGroupName);
		if (groupIndex < 0)
			return;

		AbstractCommandButton removed = this.buttons.get(groupIndex).remove(
				indexInGroup);
		this.remove(removed);
		if (this.isSingleSelectionMode
				&& (removed instanceof JCommandToggleButton)) {
			this.buttonGroup.remove((JCommandToggleButton) removed);
		}
		this.fireStateChanged();
	}

	/**
	 * Removes all the button groups and buttons from this panel.
	 */
	public void removeAllGroups() {
		for (List<AbstractCommandButton> ljcb : this.buttons) {
			for (AbstractCommandButton jcb : ljcb) {
				if (this.isSingleSelectionMode
						&& (jcb instanceof JCommandToggleButton)) {
					this.buttonGroup.remove((JCommandToggleButton) jcb);
				}
				this.remove(jcb);
			}
		}
		this.buttons.clear();
		this.groupTitles.clear();
		this.fireStateChanged();
	}

	/**
	 * Returns the number of button groups in this panel.
	 * 
	 * @return Number of button groups in this panel.
	 */
	public int getGroupCount() {
		if (this.groupTitles == null)
			return 0;
		return this.groupTitles.size();
	}

	/**
	 * Returns the number of buttons in this panel.
	 * 
	 * @return Number of buttons in this panel.
	 */
	public int getButtonCount() {
		int result = 0;
		for (List<AbstractCommandButton> ljcb : this.buttons) {
			result += ljcb.size();
		}
		return result;
	}

	/**
	 * Returns the title of the button group at the specified index.
	 * 
	 * @param index
	 *            Button group index.
	 * @return Title of the button group at the specified index.
	 */
	public String getGroupTitleAt(int index) {
		return this.groupTitles.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JPanel#updateUI()
	 */
	@Override
	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((CommandButtonPanelUI) UIManager.getUI(this));
		} else {
			setUI(BasicCommandButtonPanelUI.createUI(this));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JPanel#getUIClassID()
	 */
	@Override
	public String getUIClassID() {
		return uiClassID;
	}

	/**
	 * Sets the maximum button columns for this panel. When this panel is shown
	 * and the layout kind is {@link LayoutKind#ROW_FILL}, it will have no more
	 * than this number of buttons in each row.
	 * 
	 * @param maxButtonColumns
	 *            Maximum button columns for this panel.
	 */
	public void setMaxButtonColumns(int maxButtonColumns) {
		if (maxButtonColumns != this.maxButtonColumns) {
			int oldValue = this.maxButtonColumns;
			this.maxButtonColumns = maxButtonColumns;
			this.firePropertyChange("maxButtonColumns", oldValue,
					this.maxButtonColumns);
		}
	}

	/**
	 * Returns the maximum button columns for this panel. The return value is
	 * relevant only when the layout kind is {@link LayoutKind#ROW_FILL}.
	 * 
	 * @return Maximum button columns for this panel.
	 */
	public int getMaxButtonColumns() {
		return this.maxButtonColumns;
	}

	/**
	 * Sets the maximum button rows for this panel. When this panel is shown and
	 * the layout kind is {@link LayoutKind#COLUMN_FILL}, it will have no more
	 * than this number of buttons in each column.
	 * 
	 * @param maxButtonRows
	 *            Maximum button rows for this panel.
	 */
	public void setMaxButtonRows(int maxButtonRows) {
		if (maxButtonRows != this.maxButtonRows) {
			int oldValue = this.maxButtonRows;
			this.maxButtonRows = maxButtonRows;
			this.firePropertyChange("maxButtonRows", oldValue,
					this.maxButtonRows);
		}
	}

	/**
	 * Returns the maximum button rows for this panel. The return value is
	 * relevant only when the layout kind is {@link LayoutKind#COLUMN_FILL}.
	 * 
	 * @return Maximum button rows for this panel.
	 */
	public int getMaxButtonRows() {
		return this.maxButtonRows;
	}

	/**
	 * Returns the list of all buttons in the specified button group.
	 * 
	 * @param groupIndex
	 *            Group index.
	 * @return Unmodifiable view on the list of all buttons in the specified
	 *         button group.
	 */
	public List<AbstractCommandButton> getGroupButtons(int groupIndex) {
		return Collections.unmodifiableList(this.buttons.get(groupIndex));
	}

	/**
	 * Sets the selection mode for this panel. If <code>true</code> is passed as
	 * the parameter, all {@link JCommandToggleButton} in this panel are set to
	 * belong to the same button group.
	 * 
	 * @param isSingleSelectionMode
	 *            If <code>true</code>,all {@link JCommandToggleButton} in this
	 *            panel are set to belong to the same button group.
	 * @see #getSelectedButton()
	 */
	public void setSingleSelectionMode(boolean isSingleSelectionMode) {
		if (this.isSingleSelectionMode == isSingleSelectionMode)
			return;

		this.isSingleSelectionMode = isSingleSelectionMode;
		if (this.isSingleSelectionMode) {
			this.buttonGroup = new CommandToggleButtonGroup();
			for (List<AbstractCommandButton> ljrb : this.buttons) {
				for (AbstractCommandButton jrb : ljrb) {
					if (jrb instanceof JCommandToggleButton) {
						this.buttonGroup.add((JCommandToggleButton) jrb);
					}
				}
			}
		} else {
			for (List<AbstractCommandButton> ljrb : this.buttons) {
				for (AbstractCommandButton jrb : ljrb) {
					if (jrb instanceof JCommandToggleButton) {
						this.buttonGroup.remove((JCommandToggleButton) jrb);
					}
				}
			}
			this.buttonGroup = null;
		}
	}

	/**
	 * Sets indication whether button group labels should be shown.
	 * 
	 * @param toShowGroupLabels
	 *            If <code>true</code>, this panel will show the labels of the
	 *            button groups.
	 */
	public void setToShowGroupLabels(boolean toShowGroupLabels) {
		if ((layoutKind == LayoutKind.COLUMN_FILL) && toShowGroupLabels) {
			throw new IllegalArgumentException(
					"Column fill layout is not supported when group labels are shown");
		}
		if (this.toShowGroupLabels != toShowGroupLabels) {
			boolean oldValue = this.toShowGroupLabels;
			this.toShowGroupLabels = toShowGroupLabels;
			this.firePropertyChange("toShowGroupLabels", oldValue,
					this.toShowGroupLabels);
		}
	}

	/**
	 * Returns indication whether button group labels should be shown.
	 * 
	 * @return If <code>true</code>, this panel shows the labels of the button
	 *         groups, and <code>false</code> otherwise.
	 */
	public boolean isToShowGroupLabels() {
		return this.toShowGroupLabels;
	}

	/**
	 * Sets the new dimension for the icons in this panel. The state for all the
	 * icons is set to {@link CommandButtonDisplayState#CUSTOM}.
	 * 
	 * @param dimension
	 *            New dimension for the icons in this panel.
	 */
	public void setIconDimension(int dimension) {
		this.currDimension = dimension;
		this.currState = CommandButtonDisplayState.CUSTOM;
		for (List<AbstractCommandButton> buttonList : this.buttons) {
			for (AbstractCommandButton button : buttonList) {
				button.updateCustomDimension(dimension);
			}
		}
		this.revalidate();
		this.doLayout();
		this.repaint();
	}

	/**
	 * Sets the new state for the icons in this panel. The dimension for all the
	 * icons is set to -1; this method should only be called with a state that
	 * has an associated default size (like
	 * {@link CommandButtonDisplayState#BIG},
	 * {@link CommandButtonDisplayState#TILE},
	 * {@link CommandButtonDisplayState#MEDIUM} and
	 * {@link CommandButtonDisplayState#SMALL}).
	 * 
	 * @param state
	 *            New state for the icons in this panel.
	 */
	public void setIconState(CommandButtonDisplayState state) {
		this.currDimension = -1;
		this.currState = state;
		for (List<AbstractCommandButton> ljrb : this.buttons) {
			for (AbstractCommandButton jrb : ljrb) {
				jrb.setDisplayState(state);
				jrb.revalidate();
				jrb.doLayout();
			}
		}
		this.revalidate();
		this.doLayout();
		this.repaint();
	}

	/**
	 * Returns the selected button of this panel. Only relevant for single
	 * selection mode (set by {@link #setSingleSelectionMode(boolean)}),
	 * returning <code>null</code> otherwise.
	 * 
	 * @return The selected button of this panel.
	 * @see #setSingleSelectionMode(boolean)
	 */
	public JCommandToggleButton getSelectedButton() {
		if (this.isSingleSelectionMode) {
			for (List<AbstractCommandButton> ljrb : this.buttons) {
				for (AbstractCommandButton jrb : ljrb) {
					if (jrb instanceof JCommandToggleButton) {
						JCommandToggleButton jctb = (JCommandToggleButton) jrb;
						if (jctb.getActionModel().isSelected())
							return jctb;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Returns the layout kind of this panel.
	 * 
	 * @return Layout kind of this panel.
	 */
	public LayoutKind getLayoutKind() {
		return layoutKind;
	}

	/**
	 * Sets the new layout kind for this panel.
	 * 
	 * @param layoutKind
	 *            New layout kind for this panel.
	 */
	public void setLayoutKind(LayoutKind layoutKind) {
		if (layoutKind == null)
			throw new IllegalArgumentException("Layout kind cannot be null");
		if ((layoutKind == LayoutKind.COLUMN_FILL)
				&& this.isToShowGroupLabels()) {
			throw new IllegalArgumentException(
					"Column fill layout is not supported when group labels are shown");
		}
		if (layoutKind != this.layoutKind) {
			LayoutKind old = this.layoutKind;
			this.layoutKind = layoutKind;
			this.firePropertyChange("layoutKind", old, this.layoutKind);
		}
	}

	/**
	 * Adds the specified change listener to this button panel.
	 * 
	 * @param l
	 *            Change listener to add.
	 */
	public void addChangeListener(ChangeListener l) {
		this.listenerList.add(ChangeListener.class, l);
	}

	/**
	 * Removes the specified change listener from this button panel.
	 * 
	 * @param l
	 *            Change listener to remove.
	 */
	public void removeChangeListener(ChangeListener l) {
		this.listenerList.remove(ChangeListener.class, l);
	}

	/**
	 * Notifies all registered listener that the state of this command button
	 * panel has changed.
	 */
	protected void fireStateChanged() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		ChangeEvent event = new ChangeEvent(this);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				((ChangeListener) listeners[i + 1]).stateChanged(event);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
	 */
	public Dimension getPreferredScrollableViewportSize() {
		return this.getPreferredSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle,
	 * int, int)
	 */
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 30;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
	 */
	public boolean getScrollableTracksViewportHeight() {
		return (this.layoutKind == LayoutKind.COLUMN_FILL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
	 */
	public boolean getScrollableTracksViewportWidth() {
		return (this.layoutKind == LayoutKind.ROW_FILL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle,
	 * int, int)
	 */
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 10;
	}
}
