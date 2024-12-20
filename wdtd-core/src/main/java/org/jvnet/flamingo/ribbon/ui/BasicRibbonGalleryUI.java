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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButtonPanel;
import org.jvnet.flamingo.common.JCommandButtonStrip;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.JCommandButtonStrip.StripOrientation;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelManager;
import org.jvnet.flamingo.common.popup.PopupPanelManager.PopupEvent;
import org.jvnet.flamingo.common.ui.BasicCommandButtonUI;
import org.jvnet.flamingo.utils.ArrowResizableIcon;
import org.jvnet.flamingo.utils.DoubleArrowResizableIcon;
import org.jvnet.flamingo.utils.FlamingoUtilities;
import org.jvnet.flamingo.utils.KeyTipManager;

/**
 * Basic UI for ribbon gallery {@link JRibbonGallery}.
 * 
 * @author Kirill Grouchnikov
 */
public class BasicRibbonGalleryUI extends RibbonGalleryUI {
	/**
	 * The associated ribbon gallery.
	 */
	protected JRibbonGallery ribbonGallery;

	/**
	 * The index of the first visible button.
	 */
	protected int firstVisibleButtonIndex;

	/**
	 * The index of the last visible button.
	 */
	protected int lastVisibleButtonIndex;

	/**
	 * The count of visible buttons.
	 */
	protected int visibleButtonsCount;

	/**
	 * The button that scrolls down the associated {@link #ribbonGallery}.
	 */
	protected JCommandButton scrollDownButton;

	/**
	 * The button that scrolls up the associated {@link #ribbonGallery}.
	 */
	protected JCommandButton scrollUpButton;

	/**
	 * The button that shows the associated popup gallery.
	 */
	protected ExpandCommandButton expandActionButton;

	/**
	 * Contains the scroll down, scroll up and show popup buttons.
	 * 
	 * @see #scrollDownButton
	 * @see #scrollUpButton
	 * @see #expandActionButton
	 */
	protected JCommandButtonStrip buttonStrip;

	/**
	 * Listener on the gallery scroll-down button.
	 */
	protected ActionListener scrollDownListener;

	/**
	 * Listener on the gallery scroll-up button.
	 */
	protected ActionListener scrollUpListener;

	/**
	 * Listener on the gallery expand button.
	 */
	protected ActionListener expandListener;

	/**
	 * Listener on the {@link PopupPanelManager} changes to sync the
	 * {@link JRibbonGallery#setShowingPopupPanel(boolean)} once the popup
	 * gallery is dismissed by the user.
	 */
	protected PopupPanelManager.PopupListener popupListener;

	/**
	 * Property change listener.
	 */
	protected PropertyChangeListener propertyChangeListener;

	/**
	 * Ribbon gallery margin.
	 */
	protected Insets margin;

	/**
	 * Button strip as a UI resource.
	 * 
	 * @author Kirill Grouchnikov
	 */
	protected static class JButtonStripUIResource extends JCommandButtonStrip
			implements UIResource {

		/**
		 * Creates a new UI-resource button strip.
		 */
		public JButtonStripUIResource() {
			super();
		}

		/**
		 * Creates a new UI-resource button strip.
		 * 
		 * @param orientation
		 *            Orientation for this strip.
		 */
		public JButtonStripUIResource(StripOrientation orientation) {
			super(orientation);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new BasicRibbonGalleryUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
	 */
	@Override
	public void installUI(JComponent c) {
		this.ribbonGallery = (JRibbonGallery) c;
		this.firstVisibleButtonIndex = 0;
		this.visibleButtonsCount = 0;

		this.installDefaults();
		this.installComponents();
		this.installListeners();

		c.setLayout(createLayoutManager());
	}

	/**
	 * Installs subcomponents on the associated ribbon gallery.
	 */
	protected void installComponents() {
		this.buttonStrip = new JButtonStripUIResource(StripOrientation.VERTICAL);
		this.buttonStrip.setDisplayState(CommandButtonDisplayState.CUSTOM);
		this.ribbonGallery.add(this.buttonStrip);

		this.scrollUpButton = this.createScrollUpButton();
		this.scrollDownButton = this.createScrollDownButton();
		this.expandActionButton = this.createExpandButton();
		this.syncExpandKeyTip();

		this.buttonStrip.add(this.scrollUpButton);
		this.buttonStrip.add(this.scrollDownButton);
		this.buttonStrip.add(this.expandActionButton);
	}

	/**
	 * Creates the scroll-down button.
	 * 
	 * @return Scroll-down button.
	 */
	protected JCommandButton createScrollDownButton() {
		JCommandButton result = new JCommandButton(null,
				new ArrowResizableIcon(9, SwingConstants.SOUTH));
		result.setFocusable(false);
		result.setName("RibbonGallery.scrollDownButton");
		// result.setDisplayState(CommandButtonDisplayState.CUSTOM);
		result.setFlat(false);
		// result.putClientProperty(BasicCommandButtonUI.EMULATE_SQUARE_BUTTON,
		// Boolean.TRUE);
		result.putClientProperty(BasicCommandButtonUI.DONT_DISPOSE_POPUPS,
				Boolean.TRUE);
		result.setAutoRepeatAction(true);
		return result;
	}

	/**
	 * Creates the scroll-up button.
	 * 
	 * @return Scroll-up button.
	 */
	protected JCommandButton createScrollUpButton() {
		JCommandButton result = new JCommandButton(null,
				new ArrowResizableIcon(9, SwingConstants.NORTH));
		result.setFocusable(false);
		result.setName("RibbonGallery.scrollUpButton");
		// result.setDisplayState(CommandButtonDisplayState.CUSTOM);
		result.setFlat(false);
		// result.putClientProperty(BasicCommandButtonUI.EMULATE_SQUARE_BUTTON,
		// Boolean.TRUE);
		result.putClientProperty(BasicCommandButtonUI.DONT_DISPOSE_POPUPS,
				Boolean.TRUE);
		result.setAutoRepeatAction(true);
		return result;
	}

	/**
	 * Creates the expand button.
	 * 
	 * @return Expand button.
	 */
	protected ExpandCommandButton createExpandButton() {
		ExpandCommandButton result = new ExpandCommandButton(null,
				new DoubleArrowResizableIcon(9, SwingConstants.SOUTH));
		result.getActionModel().setFireActionOnPress(true);
		result.setFocusable(false);
		result.setName("RibbonGallery.expandButton");
		// result.setDisplayState(CommandButtonDisplayState.CUSTOM);
		result.setFlat(false);
		// result.putClientProperty(BasicCommandButtonUI.EMULATE_SQUARE_BUTTON,
		// Boolean.TRUE);
		result.putClientProperty(BasicCommandButtonUI.DONT_DISPOSE_POPUPS,
				Boolean.TRUE);
		return result;
	}

	/**
	 * Uninstalls subcomponents from the associated ribbon gallery.
	 */
	protected void uninstallComponents() {
		this.buttonStrip.remove(this.scrollUpButton);
		this.buttonStrip.remove(this.scrollDownButton);
		this.buttonStrip.remove(this.expandActionButton);
		this.ribbonGallery.remove(this.buttonStrip);
	}

	/**
	 * Installs defaults on the associated ribbon gallery.
	 */
	protected void installDefaults() {
		this.margin = UIManager.getInsets("RibbonGallery.margin");
		if (this.margin == null)
			this.margin = new Insets(3, 3, 3, 3);
		Border b = this.ribbonGallery.getBorder();
		if (b == null || b instanceof UIResource) {
			Border toSet = UIManager.getBorder("RibbonGallery.border");
			if (toSet == null)
				toSet = new BorderUIResource.EmptyBorderUIResource(2, 2, 2, 2);
			this.ribbonGallery.setBorder(toSet);
		}
		this.ribbonGallery.setOpaque(false);
	}

	/**
	 * Uninstalls defaults from the associated ribbon gallery.
	 */
	protected void uninstallDefaults() {
	}

	/**
	 * Installs listeners on the associated ribbon gallery.
	 */
	protected void installListeners() {
		this.scrollDownListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollOneRowDown();
				ribbonGallery.revalidate();
			}
		};

		this.scrollDownButton.addActionListener(this.scrollDownListener);

		this.scrollUpListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollOneRowUp();
				ribbonGallery.revalidate();
			}
		};
		this.scrollUpButton.addActionListener(this.scrollUpListener);

		this.expandListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupPanelManager.defaultManager().hidePopups(ribbonGallery);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						PopupFactory popupFactory = PopupFactory
								.getSharedInstance();

						JCommandButtonPanel popupButtonPanel = ribbonGallery
								.getPopupButtonPanel();

						final Point loc = ribbonGallery.getLocationOnScreen();
						final JCommandPopupMenu popupMenu = new JCommandPopupMenu(
								popupButtonPanel,
								ribbonGallery
										.getPreferredPopupMaxButtonColumns(),
								ribbonGallery
										.getPreferredPopupMaxVisibleButtonRows());

						if (ribbonGallery.getPopupCallback() != null) {
							ribbonGallery.getPopupCallback().popupToBeShown(
									popupMenu);
						}

						popupMenu
								.setCustomizer(new JPopupPanel.PopupPanelCustomizer() {
									@Override
									public Rectangle getScreenBounds() {
										Rectangle scrBounds = ribbonGallery
												.getGraphicsConfiguration()
												.getBounds();

										int x = loc.x;
										int y = loc.y;

										Dimension pref = popupMenu
												.getPreferredSize();

										int width = Math.max(pref.width,
												ribbonGallery.getWidth());
										int height = pref.height;

										// make sure that the popup stays in
										// bounds
										if ((x + width) > (scrBounds.x + scrBounds.width)) {
											x = scrBounds.x + scrBounds.width
													- width;
										}
										if ((y + height) > (scrBounds.y + scrBounds.height)) {
											y = scrBounds.y + scrBounds.height
													- height;
										}

										return new Rectangle(x, y, width,
												height);
									}
								});

						// mark the gallery so that it doesn't try to re-layout
						// itself.
						ribbonGallery.setShowingPopupPanel(true);
						// if (iconPopupGallery == null)
						// return;
						// iconPopupGallery.doLayout();
						//
						// // get the popup and show it
						Popup popup = popupFactory.getPopup(ribbonGallery,
								popupMenu, loc.x, loc.y);
						ribbonGallery.repaint();
						PopupPanelManager.defaultManager().addPopup(
								ribbonGallery, popup, popupMenu);

						// scroll to reveal the selected button
						if (popupButtonPanel.getSelectedButton() != null) {
							Rectangle selectionButtonBounds = popupButtonPanel
									.getSelectedButton().getBounds();
							popupButtonPanel
									.scrollRectToVisible(selectionButtonBounds);
						}
					}
				});
			}
		};

		this.expandActionButton.addActionListener(this.expandListener);

		this.popupListener = new PopupPanelManager.PopupListener() {
			@Override
			public void popupHidden(PopupEvent event) {
				if (event.getPopupOriginator() == ribbonGallery) {
					// reset the rollover state for all the buttons
					// in the gallery
					for (int i = 0; i < ribbonGallery.getButtonCount(); i++) {
						ribbonGallery.getButtonAt(i).getActionModel()
								.setRollover(false);
					}
					ribbonGallery.setShowingPopupPanel(false);
				}
			}

			@Override
			public void popupShown(PopupEvent event) {
			}
		};
		PopupPanelManager.defaultManager().addPopupListener(this.popupListener);

		this.propertyChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("selectedButton".equals(evt.getPropertyName())) {
					scrollToSelected();
					ribbonGallery.revalidate();
				}
				if ("expandKeyTip".equals(evt.getPropertyName())) {
					syncExpandKeyTip();
				}
			}
		};
		this.ribbonGallery
				.addPropertyChangeListener(this.propertyChangeListener);
	}

	/**
	 * Uninstalls listeners from the associated ribbon gallery.
	 */
	protected void uninstallListeners() {
		this.scrollDownButton.removeActionListener(this.scrollDownListener);
		this.scrollDownListener = null;

		this.scrollUpButton.removeActionListener(this.scrollUpListener);
		this.scrollUpListener = null;

		this.expandActionButton.removeActionListener(this.expandListener);
		this.expandListener = null;

		PopupPanelManager.defaultManager().removePopupListener(
				this.popupListener);
		this.popupListener = null;

		this.ribbonGallery
				.removePropertyChangeListener(this.propertyChangeListener);
		this.propertyChangeListener = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	@Override
	public void uninstallUI(JComponent c) {
		c.setLayout(null);

		this.uninstallListeners();
		this.uninstallDefaults();
		this.uninstallComponents();

		this.ribbonGallery = null;
	}

	/**
	 * Invoked by <code>installUI</code> to create a layout manager object to
	 * manage the {@link JCommandButtonStrip}.
	 * 
	 * @return a layout manager object
	 */
	protected LayoutManager createLayoutManager() {
		return new RibbonGalleryLayout();
	}

	/**
	 * Layout for the ribbon gallery.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private class RibbonGalleryLayout implements LayoutManager {

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
			return new Dimension(ribbonGallery.getPreferredWidth(ribbonGallery
					.getDisplayPriority(), c.getHeight()), c.getHeight());
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
			int width = c.getWidth();
			int height = c.getHeight();

			Insets borderInsets = ribbonGallery.getInsets();

			int galleryHeight = height - margin.top - margin.bottom;
			int buttonHeight = galleryHeight - borderInsets.top
					- borderInsets.bottom;

			// int topY = borderInsets.top
			// + (height - borderInsets.top - borderInsets.bottom -
			// galleryHeight)
			// / 2;
			int scrollerButtonHeight = galleryHeight / 3;
			int buttonWidth = 15;
			int buttonX = width - buttonWidth - margin.right;

			scrollDownButton.setPreferredSize(new Dimension(buttonWidth,
					scrollerButtonHeight));
			scrollUpButton.setPreferredSize(new Dimension(buttonWidth,
					scrollerButtonHeight));
			// special case (if available height doesn't divide 3
			expandActionButton.setPreferredSize(new Dimension(buttonWidth,
					galleryHeight - 2 * scrollerButtonHeight));
			buttonStrip.setBounds(buttonX, margin.top, buttonWidth,
					galleryHeight);
			buttonStrip.doLayout();

			if (!ribbonGallery.isShowingPopupPanel()) {
				// hide all buttons and compute the button width
				int maxButtonWidth = buttonHeight * 5 / 4;
				for (int i = 0; i < ribbonGallery.getButtonCount(); i++) {
					JCommandToggleButton currButton = ribbonGallery
							.getButtonAt(i);
					currButton.setVisible(false);
				}

				int gap = getLayoutGap();

				// compute how many buttons can fit in the available
				// horizontal space
				visibleButtonsCount = 0;
				while (true) {
					// gap on the left, gap in between every two adjacent
					// buttons and gap on the right
					int neededSpace = visibleButtonsCount * maxButtonWidth
							+ (visibleButtonsCount + 1) * gap;
					if (neededSpace > (buttonX - margin.right)) {
						visibleButtonsCount--;
						break;
					}
					visibleButtonsCount++;
				}

				// compute how many pixels we can distribute among the visible
				// buttons
				int neededSpace = visibleButtonsCount * maxButtonWidth
						+ (visibleButtonsCount + 1) * gap;
				int startX = margin.left + gap;
				int availableWidth = buttonX - margin.left;
				int toAddToButtonWidth = (availableWidth - neededSpace)
						/ visibleButtonsCount;

				// compute how many buttons can fit in the available horizontal
				// space and how many pixels will be left after that
				// int toDistribute =
				lastVisibleButtonIndex = firstVisibleButtonIndex
						+ visibleButtonsCount - 1;
				if (lastVisibleButtonIndex >= ribbonGallery.getButtonCount())
					lastVisibleButtonIndex = ribbonGallery.getButtonCount() - 1;
				for (int i = firstVisibleButtonIndex; i <= lastVisibleButtonIndex; i++) {
					JCommandToggleButton currButton = ribbonGallery
							.getButtonAt(i);

					// show button and set bounds
					currButton.setVisible(true);
					currButton.setBounds(startX, margin.top + borderInsets.top,
							maxButtonWidth + toAddToButtonWidth, buttonHeight);
					startX += maxButtonWidth + toAddToButtonWidth + gap;
				}
				if (ribbonGallery.getButtonCount() == 0) {
					scrollDownButton.setEnabled(false);
					scrollUpButton.setEnabled(false);
					expandActionButton.setEnabled(false);
				} else {
					// Scroll down button is enabled when the last button is not
					// showing
					scrollDownButton.setEnabled(!ribbonGallery.getButtonAt(
							ribbonGallery.getButtonCount() - 1).isVisible());
					// Scroll up button is enabled when the first button is not
					// showing
					scrollUpButton.setEnabled(!ribbonGallery.getButtonAt(0)
							.isVisible());
					expandActionButton.setEnabled(true);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics,
	 * javax.swing.JComponent)
	 */
	@Override
	public void paint(Graphics g, JComponent c) {
		Graphics2D graphics = (Graphics2D) g.create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);

		this.paintRibbonGalleryBorder(graphics);

		graphics.dispose();
	}

	/**
	 * Paints ribbon gallery border.
	 * 
	 * @param graphics
	 *            Graphics context.
	 * @param toFill
	 *            Rectangle for the background.
	 */
	protected void paintRibbonGalleryBorder(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics.create();
		g2d.setColor(FlamingoUtilities.getBorderColor());
		Shape outerContour = FlamingoUtilities.getRibbonGalleryOutline(
				this.margin.left, this.ribbonGallery.getWidth() - margin.right,
				this.margin.top, this.ribbonGallery.getHeight()
						- this.margin.bottom, 2);
		g2d.clipRect(0, 0, this.ribbonGallery.getWidth() - margin.right
				- buttonStrip.getWidth() / 2, this.ribbonGallery.getHeight());
		g2d.draw(outerContour);
		g2d.dispose();
	}

	/**
	 * Returns the layout gap for the controls in the associated ribbon gallery.
	 * 
	 * @return The layout gap for the controls in the associated ribbon gallery.
	 */
	protected int getLayoutGap() {
		return 4;
	}

	/**
	 * Returns the preferred width of the ribbon gallery for the specified
	 * parameters.
	 * 
	 * @param buttonCount
	 *            Button count.
	 * @param availableHeight
	 *            Available height in pixels.
	 * @return The preferred width of the ribbon gallery for the specified
	 *         parameters.
	 */
	public int getPreferredWidth(int buttonCount, int availableHeight) {
		Insets borderInsets = ribbonGallery.getInsets();

		int galleryHeight = availableHeight - margin.top - margin.bottom;
		int buttonHeight = galleryHeight - borderInsets.top
				- borderInsets.bottom;

		// start at the left margin
		int result = margin.left;
		// add all the gallery buttons
		result += buttonCount * buttonHeight * 5 / 4;
		// and the gaps between them (including before first and after last)
		result += (buttonCount + 1) * getLayoutGap();
		// and the control button strip width
		result += 15;
		// and the gap to the right margin
		result += margin.right;

		// System.out.println(buttonCount + "/" + availableHeight + "/"
		// + buttonHeight + " --> " + result);
		return result;
	}

	/**
	 * Scrolls the contents of this ribbon gallery one row down.
	 */
	protected void scrollOneRowDown() {
		this.firstVisibleButtonIndex += this.visibleButtonsCount;
		if (this.visibleButtonsCount > 1)
			this.firstVisibleButtonIndex--;
		int buttonCount = this.ribbonGallery.getButtonCount();
		// make sure that we don't go beyond the total button count
		if (this.firstVisibleButtonIndex >= buttonCount)
			this.firstVisibleButtonIndex = buttonCount - 1;
		// compute the last visible button
		this.lastVisibleButtonIndex = this.firstVisibleButtonIndex
				+ this.visibleButtonsCount - 1;
		if (this.lastVisibleButtonIndex >= buttonCount)
			this.lastVisibleButtonIndex = buttonCount - 1;
	}

	/**
	 * Scrolls the contents of this ribbon gallery one row up.
	 */
	protected void scrollOneRowUp() {
		// update the last visible index so there's overlap between the rows
		this.lastVisibleButtonIndex = this.firstVisibleButtonIndex;
		// but there is no overlap if only one button is visible
		if (this.visibleButtonsCount == 1)
			this.lastVisibleButtonIndex--;
		this.firstVisibleButtonIndex = this.lastVisibleButtonIndex
				- this.visibleButtonsCount + 1;
		if (this.firstVisibleButtonIndex < 0)
			this.firstVisibleButtonIndex = 0;
	}

	/**
	 * Scrolls the contents of this ribbon gallery to reveal the currently
	 * selected button.
	 */
	protected void scrollToSelected() {
		JCommandToggleButton selected = this.ribbonGallery.getSelectedButton();
		if (selected == null)
			return;
		int selIndex = -1;
		for (int i = 0; i < this.ribbonGallery.getButtonCount(); i++) {
			if (this.ribbonGallery.getButtonAt(i) == selected) {
				selIndex = i;
				break;
			}
		}
		if (selIndex < 0)
			return;

		// is already shown?
		if ((selIndex >= this.firstVisibleButtonIndex)
				&& (selIndex <= this.lastVisibleButtonIndex))
			return;

		// not visible?
		if (this.visibleButtonsCount <= 0)
			return;

		while (true) {
			if (selIndex < this.firstVisibleButtonIndex) {
				// need to scroll up
				this.scrollOneRowUp();
			} else {
				this.scrollOneRowDown();
			}
			if ((selIndex >= this.firstVisibleButtonIndex)
					&& (selIndex <= this.lastVisibleButtonIndex))
				return;
		}
	}

	protected void syncExpandKeyTip() {
		this.expandActionButton.setActionKeyTip(this.ribbonGallery
				.getExpandKeyTip());
	}

	@KeyTipManager.HasNextKeyTipChain
	protected static class ExpandCommandButton extends JCommandButton {
		public ExpandCommandButton(String title, ResizableIcon icon) {
			super(title, icon);
		}
	}
}
