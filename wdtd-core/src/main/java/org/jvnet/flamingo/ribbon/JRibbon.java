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

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jvnet.flamingo.common.AbstractCommandButton;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.ribbon.ui.BasicRibbonUI;
import org.jvnet.flamingo.ribbon.ui.RibbonUI;

/**
 * Ribbon component.
 * 
 * @author Kirill Grouchnikov
 */
public class JRibbon extends JComponent {
	/**
	 * The general tasks.
	 */
	private ArrayList<RibbonTask> tasks;

	/**
	 * The contextual task groups.
	 */
	private ArrayList<RibbonContextualTaskGroup> contextualTaskGroups;

	/**
	 * The taskbar components (to the left of task tabs).
	 */
	private ArrayList<Component> taskbarComponents;

	/**
	 * Bands of the currently shown task.
	 */
	private ArrayList<AbstractRibbonBand> bands;

	/**
	 * Currently selected (shown) task.
	 */
	private RibbonTask currentlySelectedTask;

	/**
	 * Help icon. When not <code>null</code>, the ribbon will display a help
	 * button at the far right of the tab area.
	 * 
	 * @see #helpActionListener
	 */
	private ResizableIcon helpIcon;

	/**
	 * When the {@link #helpIcon} is not <code>null</code>, this listener will
	 * be invoked when the user activates the help button.
	 */
	private ActionListener helpActionListener;

	/**
	 * Visibility status of the contextual task group. Must contain a value for
	 * each group in {@link #contextualTaskGroups}.
	 */
	private Map<RibbonContextualTaskGroup, Boolean> groupVisibilityMap;

	/**
	 * The application menu.
	 */
	private RibbonApplicationMenu applicationMenu;

	/**
	 * The rich tooltip of {@link #applicationMenu}.
	 */
	private RichTooltip applicationMenuRichTooltip;

	private String applicationMenuKeyTip;

	private boolean isMinimized;

	private JRibbonFrame ribbonFrame;

	/**
	 * The UI class ID string.
	 */
	public static final String uiClassID = "RibbonUI";

	/**
	 * Creates a new empty ribbon component. Applications are encouraged to use
	 * {@link JRibbonFrame} and access the ribbon with
	 * {@link JRibbonFrame#getRibbon()} API.
	 */
	public JRibbon() {
		this.tasks = new ArrayList<RibbonTask>();
		this.contextualTaskGroups = new ArrayList<RibbonContextualTaskGroup>();
		this.taskbarComponents = new ArrayList<Component>();
		this.bands = new ArrayList<AbstractRibbonBand>();
		this.currentlySelectedTask = null;
		this.groupVisibilityMap = new HashMap<RibbonContextualTaskGroup, Boolean>();

		updateUI();
	}

	JRibbon(JRibbonFrame ribbonFrame) {
		this();
		this.ribbonFrame = ribbonFrame;
	}

	/**
	 * Adds the specified taskbar component to this ribbon.
	 * 
	 * @param comp
	 *            The taskbar component to add.
	 * @see #removeTaskbarComponent(Component)
	 * @see #getTaskbarComponents()
	 */
	public void addTaskbarComponent(Component comp) {
		if (comp instanceof AbstractCommandButton) {
			AbstractCommandButton button = (AbstractCommandButton) comp;
			button.setDisplayState(CommandButtonDisplayState.SMALL);
			button.setGapScaleFactor(0.5);
			button.setFocusable(false);
		}
		this.taskbarComponents.add(comp);
		this.fireStateChanged();
	}

	/**
	 * Removes the specified taskbar component from this ribbon.
	 * 
	 * @param comp
	 *            The taskbar component to remove.
	 * @see #addTaskbarComponent(Component)
	 * @see #getTaskbarComponents()
	 */
	public void removeTaskbarComponent(Component comp) {
		this.taskbarComponents.remove(comp);
		this.fireStateChanged();
	}

	/**
	 * Adds the specified task.
	 * 
	 * @param task
	 *            Task object.
	 * @see #addHelpTask(RibbonTask)
	 * @see #addContextualTaskGroup(RibbonContextualTaskGroup)
	 */
	public void addTask(RibbonTask task) {
		task.setRibbon(this);

		this.tasks.add(task);

		if (this.tasks.size() == 1) {
			this.setSelectedTask(task);
		}

		this.fireStateChanged();
	}

	public void configureHelp(ResizableIcon helpIcon,
			ActionListener helpActionListener) {
		this.helpIcon = helpIcon;
		this.helpActionListener = helpActionListener;
		this.fireStateChanged();
	}

	public ResizableIcon getHelpIcon() {
		return helpIcon;
	}

	public ActionListener getHelpActionListener() {
		return helpActionListener;
	}

	/**
	 * Adds the specified contextual task group.
	 * 
	 * @param group
	 *            Group object.
	 * @see #addTask(RibbonTask)
	 * @see #setVisible(RibbonContextualTaskGroup, boolean)
	 * @see #isVisible(RibbonContextualTaskGroup)
	 */
	public void addContextualTaskGroup(RibbonContextualTaskGroup group) {
		group.setRibbon(this);

		this.contextualTaskGroups.add(group);
		this.groupVisibilityMap.put(group, Boolean.valueOf(false));

		this.fireStateChanged();
	}

	/**
	 * Returns the number of regular tasks in <code>this</code> ribbon.
	 * 
	 * @return Number of regular tasks in <code>this</code> ribbon.
	 * @see #getTask(int)
	 */
	public int getTaskCount() {
		return this.tasks.size();
	}

	/**
	 * Retrieves the regular task at specified index.
	 * 
	 * @param index
	 *            Task index.
	 * @return Task that matches the specified index.
	 * @see #getTaskCount()
	 */
	public RibbonTask getTask(int index) {
		return this.tasks.get(index);
	}

	/**
	 * Returns the number of contextual task groups in <code>this</code> ribbon.
	 * 
	 * @return Number of contextual task groups in <code>this</code> ribbon.
	 * @see #getContextualTaskGroup(int)
	 */
	public int getContextualTaskGroupCount() {
		return this.contextualTaskGroups.size();
	}

	/**
	 * Retrieves contextual task group at specified index.
	 * 
	 * @param index
	 *            Group index.
	 * @return Group that matches the specified index.
	 * @see #getContextualTaskGroupCount()
	 */
	public RibbonContextualTaskGroup getContextualTaskGroup(int index) {
		return this.contextualTaskGroups.get(index);
	}

	/**
	 * Selects the specified task. The task can be either regular (added with
	 * {@link #addTask(RibbonTask)} or {@link #addHelpTask(RibbonTask)}) or a
	 * task in a visible contextual task group (added with
	 * {@link #addContextualTaskGroup(RibbonContextualTaskGroup)}.
	 * 
	 * @param task
	 *            Task to select.
	 * @throws IllegalArgumentException
	 *             If the specified task is not in the ribbon or not visible.
	 * @see #getSelectedTask()
	 */
	public void setSelectedTask(RibbonTask task) {
		boolean valid = this.tasks.contains(task);
		if (!valid) {
			for (int i = 0; i < this.getContextualTaskGroupCount(); i++) {
				RibbonContextualTaskGroup group = this
						.getContextualTaskGroup(i);
				if (!this.isVisible(group))
					continue;
				for (int j = 0; j < group.getTaskCount(); j++) {
					if (group.getTask(j) == task) {
						valid = true;
						break;
					}
				}
				if (valid)
					break;
			}
		}
		if (!valid) {
			throw new IllegalArgumentException(
					"The specified task to be selected is either not "
							+ "part of this ribbon or not marked as visible");
		}

		for (AbstractRibbonBand ribbonBand : this.bands) {
			ribbonBand.setVisible(false);
		}
		this.bands.clear();

		for (int i = 0; i < task.getBandCount(); i++) {
			AbstractRibbonBand ribbonBand = task.getBand(i);
			ribbonBand.setVisible(true);
			this.bands.add(ribbonBand);
		}

		RibbonTask old = this.currentlySelectedTask;
		this.currentlySelectedTask = task;

		this.revalidate();
		this.repaint();

		this
				.firePropertyChange("selectedTask", old,
						this.currentlySelectedTask);
	}

	/**
	 * Returns the currently selected task.
	 * 
	 * @return The currently selected task.
	 * @see #setSelectedTask(RibbonTask)
	 */
	public RibbonTask getSelectedTask() {
		return this.currentlySelectedTask;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#updateUI()
	 */
	@Override
	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((RibbonUI) UIManager.getUI(this));
		} else {
			setUI(new BasicRibbonUI());
		}
		for (Component comp : this.taskbarComponents) {
			SwingUtilities.updateComponentTreeUI(comp);
		}
	}

	/**
	 * Returns the UI object which implements the L&F for this component.
	 * 
	 * @return a <code>RibbonUI</code> object
	 * @see #setUI
	 */
	public RibbonUI getUI() {
		return (RibbonUI) ui;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getUIClassID()
	 */
	@Override
	public String getUIClassID() {
		return uiClassID;
	}

	/**
	 * Gets all taskbar components of <code>this</code> ribbon.
	 * 
	 * @return All taskbar components of <code>this</code> ribbon.
	 * @see #addTaskbarComponent(Component)
	 * @see #removeTaskbarComponent(Component)
	 */
	public List<Component> getTaskbarComponents() {
		return Collections.unmodifiableList(this.taskbarComponents);
	}

	/**
	 * Adds the specified change listener to track changes to this ribbon.
	 * 
	 * @param l
	 *            Change listener to add.
	 * @see #removeChangeListener(ChangeListener)
	 */
	public void addChangeListener(ChangeListener l) {
		this.listenerList.add(ChangeListener.class, l);
	}

	/**
	 * Removes the specified change listener from tracking changes to this
	 * ribbon.
	 * 
	 * @param l
	 *            Change listener to remove.
	 * @see #addChangeListener(ChangeListener)
	 */
	public void removeChangeListener(ChangeListener l) {
		this.listenerList.remove(ChangeListener.class, l);
	}

	/**
	 * Notifies all registered listener that the state of this ribbon has
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

	/**
	 * Sets the visibility of ribbon tasks in the specified contextual task
	 * group. Visibility of all ribbon tasks in the specified group is affected.
	 * Note that the ribbon component is capable of showing ribbon tasks of
	 * multiple groups at the same time.
	 * 
	 * @param group
	 *            Contextual task group.
	 * @param isVisible
	 *            If <code>true</code>, all ribbon tasks in the specified group
	 *            will be visible. If <code>false</code>, all ribbon tasks in
	 *            the specified group will be hidden.
	 * @see #isVisible(RibbonContextualTaskGroup)
	 */
	public void setVisible(RibbonContextualTaskGroup group, boolean isVisible) {
		this.groupVisibilityMap.put(group, Boolean.valueOf(isVisible));

		// special handling of selected tab
		if (!isVisible) {
			boolean isSelectedBeingHidden = false;
			for (int i = 0; i < group.getTaskCount(); i++) {
				if (this.getSelectedTask() == group.getTask(i)) {
					isSelectedBeingHidden = true;
					break;
				}
			}
			if (isSelectedBeingHidden) {
				this.setSelectedTask(this.getTask(0));
			}
		}

		this.fireStateChanged();
		this.revalidate();
		SwingUtilities.getWindowAncestor(this).repaint();
	}

	/**
	 * Returns the visibility of ribbon tasks in the specified contextual task
	 * group.
	 * 
	 * @param group
	 *            Contextual task group.
	 * @return <code>true</code> if the ribbon tasks in the specified group are
	 *         visible, <code>false</code> otherwise.
	 */
	public boolean isVisible(RibbonContextualTaskGroup group) {
		return this.groupVisibilityMap.get(group);
	}

	/**
	 * Sets the application menu for this ribbon. If <code>null</code> is
	 * passed, the application menu button is hidden.
	 * 
	 * @param applicationMenu
	 *            The application menu. Can be <code>null</code>.
	 */
	public void setApplicationMenu(RibbonApplicationMenu applicationMenu) {
		RibbonApplicationMenu old = this.applicationMenu;
		if (old != applicationMenu) {
			this.applicationMenu = applicationMenu;
			if (this.applicationMenu != null) {
				this.applicationMenu.setFrozen(true);
			}
			this.firePropertyChange("applicationMenu", old,
					this.applicationMenu);
		}
	}

	/**
	 * Returns the application menu of this ribbon.
	 * 
	 * @return The application menu of this ribbon.
	 */
	public RibbonApplicationMenu getApplicationMenu() {
		return this.applicationMenu;
	}

	/**
	 * Sets the rich tooltip of the application menu.
	 * 
	 * @param tooltip
	 *            The rich tooltip of the application menu.
	 */
	public void setApplicationMenuRichTooltip(RichTooltip tooltip) {
		RichTooltip old = this.applicationMenuRichTooltip;
		this.applicationMenuRichTooltip = tooltip;
		this.firePropertyChange("applicationMenuRichTooltip", old,
				this.applicationMenuRichTooltip);
	}

	/**
	 * Returns the rich tooltip of the application menu.
	 * 
	 * @return The rich tooltip of the application menu.
	 */
	public RichTooltip getApplicationMenuRichTooltip() {
		return this.applicationMenuRichTooltip;
	}

	public void setApplicationMenuKeyTip(String keyTip) {
		String old = this.applicationMenuKeyTip;
		this.applicationMenuKeyTip = keyTip;
		this.firePropertyChange("applicationMenuKeyTip", old,
				this.applicationMenuKeyTip);
	}

	public String getApplicationMenuKeyTip() {
		return this.applicationMenuKeyTip;
	}

	public boolean isMinimized() {
		return isMinimized;
	}

	public void setMinimized(boolean isMinimized) {
		//System.out.println("Ribbon minimized -> " + isMinimized);
		boolean old = this.isMinimized;
		if (old != isMinimized) {
			this.isMinimized = isMinimized;
			this.firePropertyChange("minimized", old, this.isMinimized);
		}
	}

	public JRibbonFrame getRibbonFrame() {
		return ribbonFrame;
	}
	
	
	//闫舒寰添加
	/**
	 * Sets the new UI delegate.
	 * 
	 * @param ui
	 *            New UI delegate.
	 */
	public void setUI(RibbonUI ui) {
		super.setUI(ui);
	}
	
}
