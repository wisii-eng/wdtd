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
package org.jvnet.flamingo.common.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ColorConvertOp;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.CellRendererPane;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import org.jvnet.flamingo.common.AbstractCommandButton;
import org.jvnet.flamingo.common.AsynchronousLoadListener;
import org.jvnet.flamingo.common.AsynchronousLoading;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.CommandButtonLayoutManager;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButtonStrip;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.PopupActionListener;
import org.jvnet.flamingo.common.CommandButtonDisplayState.CommandButtonSeparatorOrientation;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.JCommandButtonStrip.StripOrientation;
import org.jvnet.flamingo.common.icon.FilteredResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.model.PopupButtonModel;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.common.popup.PopupPanelManager;
import org.jvnet.flamingo.utils.ButtonSizingUtils;
import org.jvnet.flamingo.utils.FlamingoUtilities;
import org.jvnet.flamingo.utils.RenderingUtils;

/**
 * Basic UI for command button {@link JCommandButton}.
 * 
 * @author Kirill Grouchnikov
 */
public class BasicCommandButtonUI extends CommandButtonUI {
	/**
	 * The associated command button.
	 */
	protected AbstractCommandButton commandButton;

	/**
	 * Indication whether the mouse pointer is over the associated command
	 * button.
	 */
	protected boolean isUnderMouse;

	/**
	 * Property change listener.
	 */
	protected PropertyChangeListener propertyChangeListener;

	/**
	 * Tracks user interaction with the command button (including keyboard and
	 * mouse).
	 */
	protected BasicPopupButtonListener basicPopupButtonListener;

	/**
	 * Action area of the command button.
	 */
	protected Rectangle actionClickArea;

	/**
	 * Popup area of the command button.
	 */
	protected Rectangle popupClickArea;

	/**
	 * Client property to mark the command button to have square corners. This
	 * client property is for internal use only.
	 */
	public static final String EMULATE_SQUARE_BUTTON = "flamingo.internal.commandButton.ui.emulateSquare";

	/**
	 * Client property to mark the command button to not dispose the popups on
	 * activation.
	 * 
	 * @see #disposePopupsActionListener
	 */
	public static final String DONT_DISPOSE_POPUPS = "flamingo.internal.commandButton.ui.dontDisposePopups";

	/**
	 * This listener disposes all popup panels when button's action is
	 * activated. An example of scenario would be a command button in the popup
	 * panel of an in-ribbon gallery. When this command button is activated, the
	 * associated popup panel is dismissed.
	 * 
	 * @see #DONT_DISPOSE_POPUPS
	 */
	protected ActionListener disposePopupsActionListener;

	/**
	 * Action listener on the popup area.
	 */
	protected PopupActionListener popupActionListener;

	/**
	 * The "expand" action icon.
	 */
	protected ResizableIcon popupActionIcon;

	protected CommandButtonLayoutManager layoutManager;

	/**
	 * Used to provide a LAF-consistent appearance under core LAFs.
	 */
	protected CellRendererPane buttonRendererPane;

	/**
	 * Used to provide a LAF-consistent appearance under core LAFs.
	 */
	protected AbstractButton rendererButton;

	/**
	 * Used to paint the separator between the action and popup areas.
	 */
	protected JSeparator rendererSeparator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new BasicCommandButtonUI();
	}

	/**
	 * Creates a new UI delegate.
	 */
	public BasicCommandButtonUI() {
		// this.toTakeSavedDimension = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
	 */
	@Override
	public void installUI(JComponent c) {
		this.commandButton = (AbstractCommandButton) c;
		installDefaults();
		installComponents();
		installListeners();
		installKeyboardActions();

		this.layoutManager = this.commandButton.getDisplayState()
				.createLayoutManager(this.commandButton);

		this.updateCustomDimension();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	@Override
	public void uninstallUI(JComponent c) {
		c.setLayout(null);

		uninstallKeyboardActions();
		uninstallListeners();
		uninstallComponents();
		uninstallDefaults();
		this.commandButton = null;
	}

	/**
	 * Installs defaults on the associated command button.
	 */
	protected void installDefaults() {
		configureRenderer();

		this.updateBorder();
		this.syncDisabledIcon();
	}

	protected void configureRenderer() {
		this.buttonRendererPane = new CellRendererPane();
		this.commandButton.add(buttonRendererPane);
		this.rendererButton = createRendererButton();
		this.rendererButton.setOpaque(false);
		this.rendererSeparator = new JSeparator();
		Font currFont = this.commandButton.getFont();
		if ((currFont == null) || (currFont instanceof UIResource)) {
			this.commandButton.setFont(this.rendererButton.getFont());
		}
		// special handling for Mac OS X native look-and-feel
		this.rendererButton.putClientProperty("JButton.buttonType", "square");
	}

	protected void updateBorder() {
		Border currBorder = this.commandButton.getBorder();
		if ((currBorder == null) || (currBorder instanceof UIResource)) {
			int tb = (int) (this.commandButton.getVGapScaleFactor() * 4);
			int lr = (int) (this.commandButton.getHGapScaleFactor() * 6);
			this.commandButton
					.setBorder(new BorderUIResource.EmptyBorderUIResource(tb,
							lr, tb, lr));
		}
	}

	/**
	 * Creates the renderer button.
	 * 
	 * @return The renderer button.
	 */
	protected AbstractButton createRendererButton() {
		return new JButton("");
	}

	/**
	 * Installs subcomponents on the associated command button.
	 */
	protected void installComponents() {
		this.updatePopupActionIcon();

		ResizableIcon buttonIcon = this.commandButton.getIcon();
		if (buttonIcon instanceof AsynchronousLoading) {
			((AsynchronousLoading) buttonIcon)
					.addAsynchronousLoadListener(new AsynchronousLoadListener() {
						public void completed(boolean success) {
							if (success && (commandButton != null))
								commandButton.repaint();
						}
					});
		}

		if (this.commandButton instanceof JCommandButton) {
			this.popupActionIcon = this.createPopupActionIcon();
		}
	}

	/**
	 * Installs listeners on the associated command button.
	 */
	protected void installListeners() {
		this.basicPopupButtonListener = createButtonListener(this.commandButton);
		if (this.basicPopupButtonListener != null) {
			this.commandButton.addMouseListener(this.basicPopupButtonListener);
			this.commandButton
					.addMouseMotionListener(this.basicPopupButtonListener);
			this.commandButton.addFocusListener(this.basicPopupButtonListener);
			this.commandButton.addChangeListener(this.basicPopupButtonListener);
		}

		this.propertyChangeListener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (AbstractButton.ICON_CHANGED_PROPERTY.equals(evt
						.getPropertyName())) {
					Icon newIcon = (Icon) evt.getNewValue();
					if (newIcon instanceof AsynchronousLoading) {
						((AsynchronousLoading) newIcon)
								.addAsynchronousLoadListener(new AsynchronousLoadListener() {
									public void completed(boolean success) {
										if (success) {
											commandButton.repaint();
										}
									}
								});
					}
					syncDisabledIcon();
				}
				if ("commandButtonKind".equals(evt.getPropertyName())) {
					updatePopupActionIcon();
				}
				if ("popupOrientationKind".equals(evt.getPropertyName())) {
					updatePopupActionIcon();
				}
				if ("customDimension".equals(evt.getPropertyName())) {
					updateCustomDimension();
				}
				if ("hgapScaleFactor".equals(evt.getPropertyName())) {
					updateBorder();
				}
				if ("vgapScaleFactor".equals(evt.getPropertyName())) {
					updateBorder();
				}

				if ("popupModel".equals(evt.getPropertyName())) {
					// rewire the popup action listener on the new popup model
					PopupButtonModel oldModel = (PopupButtonModel) evt
							.getOldValue();
					PopupButtonModel newModel = (PopupButtonModel) evt
							.getNewValue();

					if (oldModel != null) {
						oldModel.removePopupActionListener(popupActionListener);
						popupActionListener = null;
					}

					if (newModel != null) {
						popupActionListener = createPopupActionListener();
						newModel.addPopupActionListener(popupActionListener);
					}
				}
				if ("displayState".equals(evt.getPropertyName())) {
					CommandButtonDisplayState newState = (CommandButtonDisplayState) evt
							.getNewValue();

					layoutManager = newState.createLayoutManager(commandButton);

					int maxHeight = layoutManager.getPreferredIconSize();
					if (maxHeight < 0) {
						maxHeight = commandButton.getIcon().getIconHeight();
					}

					if (newState != CommandButtonDisplayState.CUSTOM) {
						Dimension newDim = new Dimension(maxHeight, maxHeight);
						commandButton.getIcon().setDimension(newDim);
						// also update disabled icon
						if (commandButton.getDisabledIcon() != null) {
							commandButton.getDisabledIcon()
									.setDimension(newDim);
						}
					}

					commandButton.invalidate();
					commandButton.revalidate();
					commandButton.doLayout();
				}

				// pass the event to the layout manager
				layoutManager.propertyChange(evt);
			}
		};
		this.commandButton
				.addPropertyChangeListener(this.propertyChangeListener);

		this.disposePopupsActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!Boolean.TRUE.equals(commandButton
						.getClientProperty(DONT_DISPOSE_POPUPS))) {
					if (SwingUtilities.getAncestorOfClass(JPopupPanel.class,
							commandButton) != null) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								// command button may be cleared if the
								// button click resulted in LAF switch
								if (commandButton != null) {
									// clear the active states
									commandButton.getActionModel().setPressed(
											false);
									commandButton.getActionModel().setRollover(
											false);
									commandButton.getActionModel().setArmed(
											false);
								}
							}
						});
					}
					PopupPanelManager.defaultManager().hidePopups(null);
				}
			}
		};
		this.commandButton.addActionListener(this.disposePopupsActionListener);

		if (this.commandButton instanceof JCommandButton) {
			this.popupActionListener = this.createPopupActionListener();
			((JCommandButton) this.commandButton).getPopupModel()
					.addPopupActionListener(this.popupActionListener);
		}

	}

	/**
	 * Creates the icon for the popup area.
	 * 
	 * @return The icon for the popup area.
	 */
	protected ResizableIcon createPopupActionIcon() {
		return FlamingoUtilities
				.getCommandButtonPopupActionIcon(((JCommandButton) this.commandButton)
						.getPopupOrientationKind());
	}

	/**
	 * Creates the button listener for the specified command button.
	 * 
	 * @param b
	 *            Command button.
	 * @return The button listener for the specified command button.
	 */
	protected BasicPopupButtonListener createButtonListener(
			AbstractCommandButton b) {
		return new BasicPopupButtonListener();
	}

	/**
	 * Installs the keyboard actions on the associated command button.
	 */
	protected void installKeyboardActions() {
		if (this.basicPopupButtonListener != null) {
			basicPopupButtonListener.installKeyboardActions(this.commandButton);
		}
	}

	/**
	 * Uninstalls defaults from the associated command button.
	 */
	protected void uninstallDefaults() {
		unconfigureRenderer();
	}

	protected void unconfigureRenderer() {
		this.commandButton.remove(this.buttonRendererPane);
		this.buttonRendererPane = null;
	}

	/**
	 * Uninstalls subcomponents from the associated command button.
	 */
	protected void uninstallComponents() {
	}

	/**
	 * Uninstalls listeners from the associated command button.
	 */
	protected void uninstallListeners() {
		if (this.basicPopupButtonListener != null) {
			this.commandButton
					.removeMouseListener(this.basicPopupButtonListener);
			this.commandButton
					.removeMouseListener(this.basicPopupButtonListener);
			this.commandButton
					.removeMouseMotionListener(this.basicPopupButtonListener);
			this.commandButton
					.removeFocusListener(this.basicPopupButtonListener);
			this.commandButton
					.removeChangeListener(this.basicPopupButtonListener);
		}

		this.commandButton
				.removePropertyChangeListener(this.propertyChangeListener);
		this.propertyChangeListener = null;

		this.commandButton
				.removeActionListener(this.disposePopupsActionListener);
		this.disposePopupsActionListener = null;

		if (this.commandButton instanceof JCommandButton) {
			((JCommandButton) this.commandButton).getPopupModel()
					.removePopupActionListener(this.popupActionListener);
			this.popupActionListener = null;
		}
	}

	/**
	 * Uninstalls the keyboard actions from the associated command button.
	 */
	protected void uninstallKeyboardActions() {
		if (this.basicPopupButtonListener != null) {
			this.basicPopupButtonListener
					.uninstallKeyboardActions(this.commandButton);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#update(java.awt.Graphics,
	 * javax.swing.JComponent)
	 */
	@Override
	public void update(Graphics g, JComponent c) {
		Graphics2D g2d = (Graphics2D) g.create();
		RenderingUtils.installDesktopHints(g2d);
		super.update(g2d, c);
		g2d.dispose();
	}
	
	//TODO 这里
	//for test
	protected void paintSelectedButtonIcon(Graphics g, Rectangle iconRect) {
		Icon iconToPaint = this.getIconToPaint();
//		System.out.println(iconToPaint.getClass());
		
		/*if (iconToPaint instanceof EmptyResizableIcon) {
			Icon right = MediaResource.getResizableIcon("01087.ico");
			MediaResource.getImageURL("01087.ico");
			
			BufferedImage bi = WisedocIcoWrapper.getIcon(MediaResource.getImageURL("01087.ico"), new Dimension(16,16)).getImage();
			
			if (bi != null) {
				int dx = (16 - bi.getWidth()) / 2;
				int dy = (16- bi.getHeight()) / 2;
				g.drawImage(bi, 16 + dx, 16 + dy, null);
			}
			
			if (right instanceof IcoWrapperResizableIcon) {
				IcoWrapperResizableIcon iwri = (IcoWrapperResizableIcon) right;
				IcoWrapperResizableIcon ii = new IcoWrapperResizableIcon();
			}
			
//			System.out.println(right.getClass());
			if ((right != null) && (right != null)) {
				right.paintIcon(this.commandButton, g, iconRect.x, iconRect.y);
			}
			
//			g.draw
//			g.setColor(Color.yellow);
//			g.drawRect(iconRect.x, iconRect.y, iconRect.width, iconRect.height);
		} else {
			g.setColor(Color.yellow);
			g.drawRect(iconRect.x, iconRect.y, iconRect.width, iconRect.height);
			if ((iconRect != null) && (iconToPaint != null)) {
				iconToPaint
						.paintIcon(this.commandButton, g, iconRect.x, iconRect.y);
			}
		}*/
		
		g.setColor(Color.yellow);
		g.fillRect(iconRect.x, iconRect.y, 2, 2);
		
		final int BORDER_SIZE = 3;
		final Color OVER_BORDER_COLOR = new Color(10, 30, 106);
		final int DEFAULT_SIZE = 12;
		
        g.fillRect(0, 0, DEFAULT_SIZE + 2*BORDER_SIZE - 1, DEFAULT_SIZE + 2*BORDER_SIZE - 1);
        g.setColor(OVER_BORDER_COLOR);
        g.drawRect(0, 0, DEFAULT_SIZE + 2*BORDER_SIZE - 1, DEFAULT_SIZE + 2*BORDER_SIZE -1);
		
//		g.drawRect(iconRect.x, iconRect.y, iconRect.width, iconRect.height);
		if ((iconRect != null) && (iconToPaint != null)) {
			iconToPaint
					.paintIcon(this.commandButton, g, iconRect.x, iconRect.y);
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
		if (this.isPaintingBackground()) {
			this.paintButtonBackground(g, new Rectangle(0, 0, commandButton
					.getWidth(), commandButton.getHeight()));
			// Graphics2D g2d = (Graphics2D) g.create();
			// g2d.setColor(new Color(255, 0, 0, 128));
			// g2d.fill(getActionClickArea());
			// g2d.setColor(new Color(0, 0, 255, 128));
			// g2d.fill(getPopupClickArea());
			// g2d.dispose();
		}

		g.setFont(FlamingoUtilities.getFont(commandButton, "Ribbon.font",
				"Button.font", "Panel.font"));

		JCommandButton.CommandButtonKind buttonKind = (commandButton instanceof JCommandButton) ? ((JCommandButton) commandButton)
				.getCommandButtonKind()
				: JCommandButton.CommandButtonKind.ACTION_ONLY;

		CommandButtonLayoutManager.CommandButtonLayoutInfo layoutInfo = this.layoutManager
				.getLayoutInfo(this.commandButton, g);
		commandButton.putClientProperty("icon.bounds", layoutInfo.iconRect);

		this.actionClickArea = layoutInfo.actionClickArea;
		this.popupClickArea = layoutInfo.popupClickArea;

		if (layoutInfo.iconRect != null) {
			
			//TODO 还有这里
			//for test
			if (c instanceof JCommandMenuButton) {
				JCommandMenuButton ui = (JCommandMenuButton) c;
				if (ui.getActionModel().isSelected()) {
					this.paintSelectedButtonIcon(g, layoutInfo.iconRect);
				} else {
					this.paintButtonIcon(g, layoutInfo.iconRect);
				}
			} else {
				this.paintButtonIcon(g, layoutInfo.iconRect);
			}
			
			
//			this.paintButtonIcon(g, layoutInfo.iconRect);
		}
		if (layoutInfo.popupActionRect.getWidth() > 0) {
			paintPopupActionIcon(g, layoutInfo.popupActionRect);
		}
		FontMetrics fm = g.getFontMetrics();

		boolean isTextPaintedEnabled = commandButton.isEnabled();
		if (commandButton instanceof JCommandButton) {
			JCommandButton jCommandButton = (JCommandButton) commandButton;
			CommandButtonKind commandButtonKind = jCommandButton
					.getCommandButtonKind();
			boolean toTakePopupModel = ((commandButtonKind == CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP) || (commandButtonKind == CommandButtonKind.POPUP_ONLY));
			CommandButtonDisplayState commandButtonState = commandButton
					.getDisplayState();
			if ((commandButtonState == CommandButtonDisplayState.CUSTOM)
					|| (commandButtonState == CommandButtonDisplayState.BIG)
					|| (commandButtonState == CommandButtonDisplayState.ORIG)) {
				toTakePopupModel = true;
			}

			if (toTakePopupModel) {
				// System.out.println("Taking popup model");
				isTextPaintedEnabled = jCommandButton.getPopupModel()
						.isEnabled();
			} else {
				// System.out.println("Taking action model");
				isTextPaintedEnabled = jCommandButton.getActionModel()
						.isEnabled();
			}
		}

		g.setColor(getForegroundColor(isTextPaintedEnabled));

		if (layoutInfo.textLayoutInfoList != null) {
			for (CommandButtonLayoutManager.TextLayoutInfo mainTextLayoutInfo : layoutInfo.textLayoutInfoList) {
				BasicGraphicsUtils.drawString(g, mainTextLayoutInfo.text, -1,
						mainTextLayoutInfo.textRect.x,
						mainTextLayoutInfo.textRect.y + fm.getAscent());
			}
		}

		if (isTextPaintedEnabled) {
			g.setColor(FlamingoUtilities.getColor(Color.gray,
					"Label.disabledForeground"));
		} else {
			g.setColor(FlamingoUtilities.getColor(Color.gray,
					"Label.disabledForeground").brighter());
		}

		if (layoutInfo.extraTextLayoutInfoList != null) {
			for (CommandButtonLayoutManager.TextLayoutInfo extraTextLayoutInfo : layoutInfo.extraTextLayoutInfoList) {
				if (extraTextLayoutInfo.text != null) {
					BasicGraphicsUtils.drawString(g, extraTextLayoutInfo.text,
							-1, extraTextLayoutInfo.textRect.x,
							extraTextLayoutInfo.textRect.y + fm.getAscent());
				}
			}
		}

		if (buttonKind.hasAction() && buttonKind.hasPopup()
				&& this.isPaintingSeparators() && (actionClickArea.width > 0)
				&& (popupClickArea.width > 0)) {
			if (commandButton.getDisplayState().getSeparatorOrientation() == CommandButtonSeparatorOrientation.HORIZONTAL) {
				this.paintButtonHorizontalSeparator(g, actionClickArea.height);
			} else {
				this.paintButtonVerticalSeparator(g, actionClickArea.width);
			}
		}
		// Graphics2D g2d = (Graphics2D) g.create();
		// g2d.setColor(Color.red);
		// //g2d.draw(bigLabel1Rect);
		// //g2d.draw(bigLabel2Rect);
		// g2d.setColor(Color.blue);
		// //g2d.draw(midExtraLabelRect);
		// // g2d.draw(midLabelRect);
		// g2d.draw(layoutInfo.iconRect);
		// g2d.dispose();
	}

	protected Color getForegroundColor(boolean isTextPaintedEnabled) {
		if (isTextPaintedEnabled) {
			return FlamingoUtilities.getColor(Color.black, "Button.foreground");
		} else {
			return FlamingoUtilities.getColor(Color.gray,
					"Label.disabledForeground");
		}
	}

	/**
	 * Paints the icon of the popup area.
	 * 
	 * @param g
	 *            Graphics context.
	 * @param actionArrowRect
	 */
	protected void paintPopupActionIcon(Graphics g, Rectangle actionArrowRect) {
		int size = Math.max(actionArrowRect.width - 2, 7);
		if (size % 2 == 0)
			size--;
		popupActionIcon.setDimension(new Dimension(size, size));
		popupActionIcon.paintIcon(this.commandButton, g, actionArrowRect.x
				+ (actionArrowRect.width - size) / 2, actionArrowRect.y
				+ (actionArrowRect.height - size) / 2);
	}

	/**
	 * Returns the current icon.
	 * 
	 * @return Current icon.
	 */
	protected Icon getIconToPaint() {
		// special case for command buttons with POPUP_ONLY kind -
		// check the popup model
		boolean toUseDisabledIcon;
		if (this.commandButton instanceof JCommandButton
				&& ((JCommandButton) this.commandButton).getCommandButtonKind() == JCommandButton.CommandButtonKind.POPUP_ONLY) {
			toUseDisabledIcon = !((JCommandButton) this.commandButton)
					.getPopupModel().isEnabled();
		} else {
			toUseDisabledIcon = !this.commandButton.getActionModel()
					.isEnabled();
		}
		return (toUseDisabledIcon && this.commandButton.getDisabledIcon() != null) ? this.commandButton
				.getDisabledIcon()
				: this.commandButton.getIcon();
	}

	/**
	 * Paints command button vertical separator.
	 * 
	 * @param graphics
	 *            Graphics context.
	 * @param separatorX
	 *            X coordinate of the separator.
	 */
	protected void paintButtonVerticalSeparator(Graphics graphics,
			int separatorX) {
		this.buttonRendererPane.setBounds(0, 0, this.commandButton.getWidth(),
				this.commandButton.getHeight());
		Graphics2D g2d = (Graphics2D) graphics.create();
		this.rendererSeparator.setOrientation(JSeparator.VERTICAL);
		this.buttonRendererPane.paintComponent(g2d, this.rendererSeparator,
				this.commandButton, separatorX, 2, 2, this.commandButton
						.getHeight() - 4, true);
		g2d.dispose();
	}

	/**
	 * Paints command button horizontal separator.
	 * 
	 * @param graphics
	 *            Graphics context.
	 * @param separatorY
	 *            Y coordinate of the separator.
	 */
	protected void paintButtonHorizontalSeparator(Graphics graphics,
			int separatorY) {
		this.buttonRendererPane.setBounds(0, 0, this.commandButton.getWidth(),
				this.commandButton.getHeight());
		Graphics2D g2d = (Graphics2D) graphics.create();
		this.rendererSeparator.setOrientation(JSeparator.HORIZONTAL);
		this.buttonRendererPane.paintComponent(g2d, this.rendererSeparator,
				this.commandButton, 2, separatorY, this.commandButton
						.getWidth() - 4, 2, true);
		g2d.dispose();
	}

	/**
	 * Paints command button background.
	 * 
	 * @param graphics
	 *            Graphics context.
	 * @param toFill
	 *            Rectangle for the background.
	 */
	protected void paintButtonBackground(Graphics graphics, Rectangle toFill) {
		ButtonModel actionModel = this.commandButton.getActionModel();
		PopupButtonModel popupModel = (this.commandButton instanceof JCommandButton) ? ((JCommandButton) this.commandButton)
				.getPopupModel()
				: null;

		// first time - paint the full background passing both models
		this.paintButtonBackground(graphics, toFill, actionModel, popupModel);

		Rectangle actionArea = this.getActionClickArea();
		Rectangle popupArea = this.getPopupClickArea();
		if ((actionArea != null) && !actionArea.isEmpty()) {
			// now overlay the action area with the background matching action
			// model
			Graphics2D graphicsAction = (Graphics2D) graphics.create();
			// System.out.println(actionArea);
			graphicsAction.clip(actionArea);
			float actionAlpha = 0.4f;
			if ((popupModel != null) && !popupModel.isEnabled())
				actionAlpha = 1.0f;
			graphicsAction.setComposite(AlphaComposite.SrcOver
					.derive(actionAlpha));
			// System.out.println(graphicsAction.getClipBounds());
			this.paintButtonBackground(graphicsAction, toFill, actionModel);
			graphicsAction.dispose();
		}
		if ((popupArea != null) && !popupArea.isEmpty()) {
			// now overlay the popup area with the background matching popup
			// model
			Graphics2D graphicsPopup = (Graphics2D) graphics.create();
			// System.out.println(popupArea);
			graphicsPopup.clip(popupArea);
			// System.out.println(graphicsPopup.getClipBounds());
			float popupAlpha = 0.4f;
			if (!actionModel.isEnabled())
				popupAlpha = 1.0f;
			graphicsPopup.setComposite(AlphaComposite.SrcOver
					.derive(popupAlpha));
			this.paintButtonBackground(graphicsPopup, toFill, popupModel);
			graphicsPopup.dispose();
		}
	}

	/**
	 * Paints the background of the command button.
	 * 
	 * @param graphics
	 *            Graphics context.
	 * @param toFill
	 *            Rectangle to fill.
	 * @param modelToUse
	 *            Button models to use for computing the background fill.
	 */
	protected void paintButtonBackground(Graphics graphics, Rectangle toFill,
			ButtonModel... modelToUse) {
		if (modelToUse.length == 0)
			return;
		if ((modelToUse.length == 1) && (modelToUse[0] == null))
			return;

		this.buttonRendererPane.setBounds(toFill.x, toFill.y, toFill.width,
				toFill.height);
		this.rendererButton.setRolloverEnabled(true);
		boolean isEnabled = true;
		boolean isRollover = false;
		boolean isPressed = true;
		boolean isArmed = true;
		boolean isSelected = true;
		for (ButtonModel model : modelToUse) {
			if (model == null)
				continue;
			isEnabled = isEnabled && model.isEnabled();
			isRollover = isRollover || model.isRollover();
			isPressed = isPressed && model.isPressed();
			isArmed = isArmed && model.isArmed();
			isSelected = isSelected && model.isSelected();
			if (model instanceof PopupButtonModel) {
				isRollover = isRollover
						|| ((PopupButtonModel) model).isPopupShowing();
			}
		}
		this.rendererButton.getModel().setEnabled(isEnabled);
		this.rendererButton.getModel().setRollover(isRollover);
		this.rendererButton.getModel().setPressed(isPressed);
		this.rendererButton.getModel().setArmed(isArmed);
		this.rendererButton.getModel().setSelected(isSelected);
		// System.out.println(this.commandButton.getText() + " - e:"
		// + this.rendererButton.getModel().isEnabled() + ", s:"
		// + this.rendererButton.getModel().isSelected() + ", r:"
		// + this.rendererButton.getModel().isRollover() + ", p:"
		// + this.rendererButton.getModel().isPressed() + ", a:"
		// + this.rendererButton.getModel().isArmed());
		Graphics2D g2d = (Graphics2D) graphics.create();

		Color borderColor = FlamingoUtilities.getBorderColor();
		if (Boolean.TRUE.equals(this.commandButton
				.getClientProperty(EMULATE_SQUARE_BUTTON))) {
			this.buttonRendererPane.paintComponent(g2d, this.rendererButton,
					this.commandButton, toFill.x - toFill.width / 2, toFill.y
							- toFill.height / 2, 2 * toFill.width,
					2 * toFill.height, true);
			g2d.setColor(borderColor);
			g2d.drawRect(toFill.x, toFill.y, toFill.width - 1,
					toFill.height - 1);
		} else {
			AbstractCommandButton.CommandButtonLocationOrderKind locationKind = this.commandButton
					.getLocationOrderKind();
			Insets outsets = (this.rendererButton instanceof JToggleButton) ? ButtonSizingUtils
					.getInstance().getToggleOutsets()
					: ButtonSizingUtils.getInstance().getOutsets();
			if (locationKind != null) {
				if (locationKind == AbstractCommandButton.CommandButtonLocationOrderKind.ONLY) {
					this.buttonRendererPane.paintComponent(g2d,
							this.rendererButton, this.commandButton, toFill.x
									- outsets.left, toFill.y - outsets.top,
							toFill.width + outsets.left + outsets.right,
							toFill.height + outsets.top + outsets.bottom, true);
				} else {
					// special case for parent component which is a vertical
					// button strip
					Component parent = this.commandButton.getParent();
					if ((parent instanceof JCommandButtonStrip)
							&& (((JCommandButtonStrip) parent).getOrientation() == StripOrientation.VERTICAL)) {
						switch (locationKind) {
						case FIRST:
							this.buttonRendererPane.paintComponent(g2d,
									this.rendererButton, this.commandButton,
									toFill.x - outsets.left, toFill.y
											- outsets.top, toFill.width
											+ outsets.left + outsets.right,
									2 * toFill.height, true);
							g2d.setColor(borderColor);
							g2d.drawLine(toFill.x + 1, toFill.y + toFill.height
									- 1, toFill.x + toFill.width - 2, toFill.y
									+ toFill.height - 1);
							break;
						case LAST:
							this.buttonRendererPane.paintComponent(g2d,
									this.rendererButton, this.commandButton,
									toFill.x - outsets.left, toFill.y
											- toFill.height, toFill.width
											+ outsets.left + outsets.right, 2
											* toFill.height + outsets.bottom,
									true);
							break;
						case MIDDLE:
							this.buttonRendererPane.paintComponent(g2d,
									this.rendererButton, this.commandButton,
									toFill.x - outsets.left, toFill.y
											- toFill.height, toFill.width
											+ outsets.left + outsets.right,
									3 * toFill.height, true);
							g2d.setColor(borderColor);
							g2d.drawLine(toFill.x + 1, toFill.y + toFill.height
									- 1, toFill.x + toFill.width - 2, toFill.y
									+ toFill.height - 1);
						}
					} else {
						switch (locationKind) {
						case FIRST:
							this.buttonRendererPane.paintComponent(g2d,
									this.rendererButton, this.commandButton,
									toFill.x - outsets.left, toFill.y
											- outsets.top, 2 * toFill.width,
									toFill.height + outsets.top
											+ outsets.bottom, true);
							g2d.setColor(borderColor);
							g2d.drawLine(toFill.x + toFill.width - 1,
									toFill.y + 1, toFill.x + toFill.width - 1,
									toFill.y + toFill.height - 2);
							break;
						case LAST:
							this.buttonRendererPane.paintComponent(g2d,
									this.rendererButton, this.commandButton,
									toFill.x - toFill.width, toFill.y
											- outsets.top, 2 * toFill.width
											+ outsets.right, toFill.height
											+ outsets.top + outsets.bottom,
									true);
							break;
						case MIDDLE:
							this.buttonRendererPane.paintComponent(g2d,
									this.rendererButton, this.commandButton,
									toFill.x - toFill.width, toFill.y
											- outsets.top, 3 * toFill.width,
									toFill.height + outsets.top
											+ outsets.bottom, true);
							g2d.setColor(borderColor);
							g2d.drawLine(toFill.x + toFill.width - 1,
									toFill.y + 1, toFill.x + toFill.width - 1,
									toFill.y + toFill.height - 2);
						}
					}
				}
			} else {
				this.buttonRendererPane.paintComponent(g2d,
						this.rendererButton, this.commandButton, toFill.x
								- outsets.left, toFill.y - outsets.top,
						toFill.width + outsets.left + outsets.right,
						toFill.height + outsets.top + outsets.bottom, true);
			}
		}
		g2d.dispose();
	}

	/**
	 * Updates the custom dimension.
	 */
	protected void updateCustomDimension() {
		int dimension = this.commandButton.getCustomDimension();

		if (dimension > 0) {
			this.commandButton.getIcon().setDimension(
					new Dimension(dimension, dimension));
			this.commandButton
					.setDisplayState(CommandButtonDisplayState.CUSTOM);

			this.commandButton.invalidate();
			this.commandButton.revalidate();
			this.commandButton.doLayout();
			this.commandButton.repaint();
		}
	}

	/**
	 * Updates the popup action icon.
	 */
	protected void updatePopupActionIcon() {
		JCommandButton button = (JCommandButton) this.commandButton;
		if (button.getCommandButtonKind().hasPopup()) {
			this.popupActionIcon = this.createPopupActionIcon();
		} else {
			this.popupActionIcon = null;
		}
	}

	/**
	 * Paints the button icon.
	 * 
	 * @param g
	 *            Graphics context.
	 * @param iconRect
	 *            Icon rectangle.
	 */
	protected void paintButtonIcon(Graphics g, Rectangle iconRect) {
		Icon iconToPaint = this.getIconToPaint();
		if ((iconRect != null) && (iconToPaint != null)) {
			iconToPaint
					.paintIcon(this.commandButton, g, iconRect.x, iconRect.y);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		AbstractCommandButton button = (AbstractCommandButton) c;
		return this.layoutManager.getPreferredSize(button);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.flamingo.common.ui.CommandButtonUI#getActionClickArea()
	 */
	@Override
	public Rectangle getActionClickArea() {
		return this.actionClickArea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.flamingo.common.ui.CommandButtonUI#getPopupClickArea()
	 */
	@Override
	public Rectangle getPopupClickArea() {
		return this.popupClickArea;
	}

	/**
	 * Returns the layout gap for the visuals of the associated command button.
	 * 
	 * @return The layout gap for the visuals of the associated command button.
	 */
	protected int getLayoutGap() {
		Font font = this.commandButton.getFont();
		if (font == null)
			font = UIManager.getFont("Button.font");
		return (font.getSize() - 4) / 4;
	}

	/**
	 * Returns indication whether the action-popup areas separator is painted.
	 * 
	 * @return <code>true</code> if the action-popup areas separator is painted.
	 */
	protected boolean isPaintingSeparators() {
		PopupButtonModel popupModel = (this.commandButton instanceof JCommandButton) ? ((JCommandButton) this.commandButton)
				.getPopupModel()
				: null;
		boolean isActionRollover = this.commandButton.getActionModel()
				.isRollover();
		boolean isPopupRollover = (popupModel != null)
				&& popupModel.isRollover();
		return isActionRollover || isPopupRollover;
	}

	/**
	 * Returns indication whether the button background is painted.
	 * 
	 * @return <code>true</code> if the button background is painted.
	 */
	protected boolean isPaintingBackground() {
		PopupButtonModel popupModel = (this.commandButton instanceof JCommandButton) ? ((JCommandButton) this.commandButton)
				.getPopupModel()
				: null;
		boolean isActionSelected = this.commandButton.getActionModel()
				.isSelected();
		boolean isPopupSelected = (popupModel != null)
				&& popupModel.isSelected();
		boolean isActionRollover = this.commandButton.getActionModel()
				.isRollover();
		boolean isPopupRollover = (popupModel != null)
				&& popupModel.isRollover();
		boolean isPopupShowing = (popupModel != null)
				&& (popupModel.isPopupShowing());
		boolean isActionArmed = this.commandButton.getActionModel().isArmed();
		boolean isPopupArmed = (popupModel != null) && (popupModel.isArmed());

		return (isActionSelected || isPopupSelected || isActionRollover
				|| isPopupRollover || isPopupShowing || isActionArmed
				|| isPopupArmed || !this.commandButton.isFlat());
	}

	/**
	 * Creates the popup action listener for this command button.
	 * 
	 * @return Popup action listener for this command button.
	 */
	protected PopupActionListener createPopupActionListener() {
		return new PopupActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processPopupAction();
			}
		};
	}

	protected void processPopupAction() {
		boolean wasPopupShowing = false;
		if (this.commandButton instanceof JCommandButton) {
			wasPopupShowing = ((JCommandButton) this.commandButton)
					.getPopupModel().isPopupShowing();
		}

		// dismiss all the popups that are currently showing
		// up until <this> button.
		PopupPanelManager.defaultManager().hidePopups(commandButton);

		if (!(commandButton instanceof JCommandButton))
			return;

		if (wasPopupShowing)
			return;

		JCommandButton jcb = (JCommandButton) this.commandButton;

		// check if the command button has an associated popup
		// panel
		PopupPanelCallback popupCallback = jcb.getPopupCallback();
		final JPopupPanel popupPanel = (popupCallback != null) ? popupCallback
				.getPopupPanel(jcb) : null;
		if (popupPanel != null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if ((commandButton == null) || (popupPanel == null))
						return;

					if (!commandButton.isShowing())
						return;

					popupPanel.doLayout();

					int x = 0;
					int y = 0;

					JPopupPanel.PopupPanelCustomizer customizer = popupPanel
							.getCustomizer();
					if (customizer == null) {
						switch (((JCommandButton) commandButton)
								.getPopupOrientationKind()) {
						case DOWNWARD:
							x = commandButton.getLocationOnScreen().x;
							y = commandButton.getLocationOnScreen().y
									+ commandButton.getSize().height;
							break;
						case SIDEWARD:
							x = commandButton.getLocationOnScreen().x
									+ commandButton.getWidth();
							y = commandButton.getLocationOnScreen().y
									+ getPopupClickArea().x;
							break;
						}
					} else {
						Rectangle placementRect = customizer.getScreenBounds();
						// System.out.println(placementRect);
						x = placementRect.x;
						y = placementRect.y;
					}

					// make sure that the popup stays in bounds
					Rectangle scrBounds = commandButton
							.getGraphicsConfiguration().getBounds();
					int pw = popupPanel.getPreferredSize().width;
					if ((x + pw) > (scrBounds.x + scrBounds.width)) {
						x = scrBounds.x + scrBounds.width - pw;
					}
					int ph = popupPanel.getPreferredSize().height;
					if ((y + ph) > (scrBounds.y + scrBounds.height)) {
						y = scrBounds.y + scrBounds.height - ph;
					}

					// get the popup and show it
					if (customizer != null) {
						Rectangle placementRect = customizer.getScreenBounds();
						popupPanel.setPreferredSize(new Dimension(
								placementRect.width, placementRect.height));
					}
					Popup popup = PopupFactory.getSharedInstance().getPopup(
							commandButton, popupPanel, x, y);
					// System.out.println("Showing the popup panel");
					PopupPanelManager.defaultManager().addPopup(commandButton,
							popup, popupPanel);
				}
			});
			return;
		}
	}

	protected void syncDisabledIcon() {
		ResizableIcon currDisabledIcon = this.commandButton.getDisabledIcon();
		if ((currDisabledIcon == null)
				|| (currDisabledIcon instanceof UIResource)) {
			this.commandButton.setDisabledIcon(new ResizableIconUIResource(
					new FilteredResizableIcon(this.commandButton.getIcon(),
							new ColorConvertOp(ColorSpace
									.getInstance(ColorSpace.CS_GRAY), null))));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jvnet.flamingo.common.ui.CommandButtonUI#getKeyTipAnchorCenterPoint()
	 */
	@Override
	public Point getKeyTipAnchorCenterPoint() {
		return this.layoutManager
				.getKeyTipAnchorCenterPoint(this.commandButton);
	}
}
