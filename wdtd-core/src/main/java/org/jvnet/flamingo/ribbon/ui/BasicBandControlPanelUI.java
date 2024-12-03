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
package org.jvnet.flamingo.ribbon.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;

import org.jvnet.flamingo.common.AbstractCommandButton;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.ribbon.AbstractRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import org.jvnet.flamingo.ribbon.resize.IconRibbonBandResizePolicy;
import org.jvnet.flamingo.ribbon.resize.RibbonBandResizePolicy;
import org.jvnet.flamingo.ribbon.ui.BasicRibbonBandUI.CollapsedButtonPopupPanel;
import org.jvnet.flamingo.ribbon.ui.JBandControlPanel.GroupStartedEvent;

/**
 * Basic UI for control panel of ribbon band {@link JBandControlPanel}.
 * 
 * @author Kirill Grouchnikov
 */
public class BasicBandControlPanelUI extends AbstractBandControlPanelUI {
	private JSeparator[] groupSeparators;

	private JLabel[] groupLabels;

	protected JBandControlPanel.GroupStartedListener groupStartedListener;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new BasicBandControlPanelUI();
	}

	/**
	 * Invoked by <code>installUI</code> to create a layout manager object to
	 * manage the {@link JBandControlPanel}.
	 * 
	 * @return a layout manager object
	 */
	protected LayoutManager createLayoutManager() {
		return new ControlPanelLayout();
	}

	@Override
	protected void installListeners() {
		super.installListeners();

		this.groupStartedListener = new JBandControlPanel.GroupStartedListener() {
			@Override
			public void groupStarted(GroupStartedEvent gse) {
				syncGroupHeaders();
			}
		};
		((JBandControlPanel) this.controlPanel)
				.addGroupStartedListener(this.groupStartedListener);
	}

	@Override
	protected void uninstallListeners() {
		((JBandControlPanel) this.controlPanel)
				.removeGroupStartedListener(this.groupStartedListener);
		this.groupStartedListener = null;

		super.uninstallListeners();
	}

	@Override
	protected void installComponents() {
		super.installComponents();

		this.syncGroupHeaders();
	}

	@Override
	protected void uninstallComponents() {
		if (this.groupSeparators != null) {
			for (JSeparator groupSeparator : this.groupSeparators) {
				this.controlPanel.remove(groupSeparator);
			}
		}
		if (this.groupLabels != null) {
			for (JLabel groupLabel : this.groupLabels) {
				if (groupLabel != null)
					this.controlPanel.remove(groupLabel);
			}
		}

		super.uninstallComponents();
	}

	protected void syncGroupHeaders() {
		if (this.groupSeparators != null) {
			for (JSeparator groupSeparator : this.groupSeparators) {
				this.controlPanel.remove(groupSeparator);
			}
		}
		if (this.groupLabels != null) {
			for (JLabel groupLabel : this.groupLabels) {
				if (groupLabel != null)
					this.controlPanel.remove(groupLabel);
			}
		}

		int groupCount = ((JBandControlPanel) this.controlPanel)
				.getControlPanelGroupCount();
		if (groupCount > 1) {
			this.groupSeparators = new JSeparator[groupCount - 1];
			for (int i = 0; i < groupCount - 1; i++) {
				this.groupSeparators[i] = new JSeparator(JSeparator.VERTICAL);
				this.controlPanel.add(this.groupSeparators[i]);
			}
		}
		if (groupCount > 0) {
			this.groupLabels = new JLabel[groupCount];
			for (int i = 0; i < groupCount; i++) {
				String title = ((JBandControlPanel) this.controlPanel)
						.getControlPanelGroupTitle(i);
				if (title != null) {
					this.groupLabels[i] = new JLabel(title);
					this.controlPanel.add(this.groupLabels[i]);
				}
			}
		}

	}

	/**
	 * Layout for the control panel of ribbon band.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private class ControlPanelLayout implements LayoutManager {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
		 * java.awt.Component)
		 */
		public void addLayoutComponent(String name, Component c) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
		 */
		public void removeLayoutComponent(Component c) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
		 */
		public Dimension preferredLayoutSize(Container c) {
			// The height of ribbon band control panel is
			// computed based on the preferred height of a command
			// button in BIG state.
			int buttonHeight = dummy.getPreferredSize().height;

			Insets ins = c.getInsets();
			return new Dimension(c.getWidth(), buttonHeight + ins.top
					+ ins.bottom);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
		 */
		public Dimension minimumLayoutSize(Container c) {
			return this.preferredLayoutSize(c);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
		 */
		public void layoutContainer(Container c) {
			AbstractRibbonBand ribbonBand = ((JBandControlPanel) c)
					.getRibbonBand();
			RibbonBandResizePolicy currentResizePolicy = ribbonBand
					.getCurrentResizePolicy();
			if (currentResizePolicy == null)
				return;

			// need place for border
			Insets ins = c.getInsets();
			int x = ins.left;
			int gap = getLayoutGap();
			int availableHeight = c.getHeight() - ins.top - ins.bottom;

			if (SwingUtilities.getAncestorOfClass(
					CollapsedButtonPopupPanel.class, c) != null) {
				// install the most permissive resize policy on the popup
				// panel of a collapsed ribbon band
				List<RibbonBandResizePolicy> resizePolicies = ribbonBand
						.getResizePolicies();
				resizePolicies.get(0).install(availableHeight, gap);
			} else {
				if (currentResizePolicy instanceof IconRibbonBandResizePolicy) {
					return;
				}

				// Installs the resize policy - this updates the display
				// priority of all the galleries and buttons
				currentResizePolicy.install(availableHeight, gap);
			}

			int controlPanelGroupIndex = 0;
			for (JBandControlPanel.ControlPanelGroup controlPanelGroup : ((JBandControlPanel) controlPanel)
					.getControlPanelGroups()) {
				// handle the group separators
				if (controlPanelGroupIndex > 0) {
					int prefW = groupSeparators[controlPanelGroupIndex - 1]
							.getPreferredSize().width;
					groupSeparators[controlPanelGroupIndex - 1].setBounds(x
							- gap + (gap - prefW) / 2, ins.top, prefW,
							availableHeight);
				}

				boolean isCoreContent = controlPanelGroup.isCoreContent();
				if (isCoreContent) {
					boolean hasTitle = (controlPanelGroup.getGroupTitle() != null);
					int maxWidthInCurrColumn = 0;
					int topY = ins.top;
					if (hasTitle) {
						JLabel titleLabel = groupLabels[controlPanelGroupIndex];
						int pw = titleLabel.getPreferredSize().width;
						titleLabel.setBounds(x, ins.top, pw, titleLabel
								.getPreferredSize().height);
						maxWidthInCurrColumn = pw;
						topY += titleLabel.getHeight();
					}
					// if a group has a title, then the core components in that
					// group will take two rows instead of three
					int contentRows = (controlPanelGroup.getGroupTitle() == null) ? 3
							: 2;
					List<JRibbonComponent> ribbonComps = controlPanelGroup
							.getRibbonComps();
					int rowIndex = 0;
					int y = topY;
					List<JRibbonComponent> currColumn = new ArrayList<JRibbonComponent>();
					for (int i = 0; i < ribbonComps.size(); i++) {
						JRibbonComponent coreComp = ribbonComps.get(i);
						int prefWidth = coreComp.getPreferredSize().width;
						coreComp.setBounds(x, y, prefWidth, coreComp
								.getPreferredSize().height);
						y += coreComp.getPreferredSize().height;
						maxWidthInCurrColumn = Math.max(maxWidthInCurrColumn,
								prefWidth);
						currColumn.add(coreComp);

						coreComp.putClientProperty(
								AbstractBandControlPanelUI.TOP_ROW, Boolean
										.valueOf(rowIndex == 0));
						coreComp.putClientProperty(
								AbstractBandControlPanelUI.MID_ROW,
								Boolean.valueOf((rowIndex > 0)
										&& (rowIndex < (contentRows - 1))));
						coreComp
								.putClientProperty(
										AbstractBandControlPanelUI.BOTTOM_ROW,
										Boolean
												.valueOf(rowIndex == (contentRows - 1)));

						// scan the components in this column
						for (JRibbonComponent comp : currColumn) {
							if (!comp.isSimpleWrapper()) {
								Rectangle bounds = comp.getBounds();
								comp.setBounds(bounds.x, bounds.y,
										maxWidthInCurrColumn, bounds.height);
							}
						}

						rowIndex++;
						if (rowIndex == contentRows) {
							int toSpread = c.getHeight() - ins.bottom - y;
							if (toSpread > 0) {
								int extraVGap = toSpread / contentRows;
								for (int j = 0; j < currColumn.size(); j++) {
									JComponent curr = currColumn.get(j);
									int toAddAbove = (int) (extraVGap * (j + 0.5));
									Rectangle bounds = curr.getBounds();
									curr.setBounds(bounds.x, bounds.y
											+ toAddAbove, bounds.width,
											bounds.height);
								}
							}

							x += maxWidthInCurrColumn;
							x += gap;
							rowIndex = 0;
							maxWidthInCurrColumn = 0;
							y = topY;
						}
					}
					if (rowIndex < contentRows) {
						x += maxWidthInCurrColumn;
						x += gap;
					}
				} else {
					// The galleries
					for (RibbonElementPriority elementPriority : RibbonElementPriority
							.values()) {
						for (JRibbonGallery gallery : controlPanelGroup
								.getRibbonGalleries(elementPriority)) {
							int pw = gallery.getPreferredWidth(gallery
									.getDisplayPriority(), availableHeight);
							gallery.setBounds(x, ins.top, pw, availableHeight);
							x += pw;
							x += gap;
						}
					}

					Map<CommandButtonDisplayState, List<AbstractCommandButton>> buttonMap = new HashMap<CommandButtonDisplayState, List<AbstractCommandButton>>();
					for (RibbonElementPriority elementPriority : RibbonElementPriority
							.values()) {
						for (AbstractCommandButton commandButton : controlPanelGroup
								.getRibbonButtons(elementPriority)) {
							CommandButtonDisplayState state = commandButton
									.getDisplayState();
							if (buttonMap.get(state) == null) {
								buttonMap.put(state,
										new ArrayList<AbstractCommandButton>());
							}
							buttonMap.get(state).add(commandButton);
						}
					}

					List<AbstractCommandButton> bigs = buttonMap
							.get(CommandButtonDisplayState.BIG);
					if (bigs != null) {
						for (AbstractCommandButton bigButton : bigs) {
							// Big buttons
							bigButton.setBounds(x, ins.top, bigButton
									.getPreferredSize().width, availableHeight);
							bigButton.putClientProperty(TOP_ROW, Boolean.FALSE);
							bigButton.putClientProperty(MID_ROW, Boolean.FALSE);
							bigButton.putClientProperty(BOTTOM_ROW,
									Boolean.FALSE);
							// System.out.println("BIG " + button.getText() +
							// ":" +
							// button.getUI().getKeyTipAnchorCenterPoint());
							x += bigButton.getWidth();
							x += gap;
						}
					}

					// Medium buttons
					int index3 = 0;
					int maxWidth3 = 0;
					List<AbstractCommandButton> mediums = buttonMap
							.get(CommandButtonDisplayState.MEDIUM);
					if (mediums != null) {
						for (AbstractCommandButton mediumButton : mediums) {
							int medWidth = mediumButton.getPreferredSize().width;
							maxWidth3 = Math.max(maxWidth3, medWidth);

							mediumButton.setBounds(x, ins.top + index3
									* (availableHeight) / 3, medWidth,
									(availableHeight) / 3 - 1);
							mediumButton.putClientProperty(TOP_ROW, Boolean
									.valueOf(index3 == 0));
							mediumButton.putClientProperty(MID_ROW, Boolean
									.valueOf(index3 == 1));
							mediumButton.putClientProperty(BOTTOM_ROW, Boolean
									.valueOf(index3 == 2));

							index3++;
							if (index3 == 3) {
								// last button in threesome
								index3 = 0;
								x += maxWidth3;
								x += gap;
								maxWidth3 = 0;
							}
						}
					}
					// at this point, maxWidth3 may be non-null. We can safely
					// add it, since in this case there will be no buttons
					// left in lowButtons
					x += maxWidth3;
					if (maxWidth3 > 0)
						x += gap;

					index3 = 0;
					maxWidth3 = 0;
					List<AbstractCommandButton> smalls = buttonMap
							.get(CommandButtonDisplayState.SMALL);
					if (smalls != null) {
						for (AbstractCommandButton smallButton : smalls) {
							int lowWidth = smallButton.getPreferredSize().width;
							maxWidth3 = Math.max(maxWidth3, lowWidth);
							smallButton.setBounds(x, ins.top + index3
									* (availableHeight) / 3, lowWidth,
									(availableHeight) / 3 - 1);
							smallButton.putClientProperty(TOP_ROW, Boolean
									.valueOf(index3 == 0));
							smallButton.putClientProperty(MID_ROW, Boolean
									.valueOf(index3 == 1));
							smallButton.putClientProperty(BOTTOM_ROW, Boolean
									.valueOf(index3 == 2));

							index3++;
							if (index3 == 3) {
								// last button in threesome
								index3 = 0;
								x += maxWidth3;
								x += gap;
								maxWidth3 = 0;
							}
						}
					}
					if (index3 < 3)
						x += maxWidth3;
					x += gap;
				}
				controlPanelGroupIndex++;
			}
		}
	}
}
