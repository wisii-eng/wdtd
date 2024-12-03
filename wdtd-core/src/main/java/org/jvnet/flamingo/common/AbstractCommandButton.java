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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.accessibility.AccessibleContext;
import javax.swing.ButtonModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.model.ActionButtonModel;
import org.jvnet.flamingo.common.ui.CommandButtonUI;

/**
 * Abstract command button
 * 
 * @author Kirill Grouchnikov
 */
public abstract class AbstractCommandButton extends
		RichToolTipManager.JTrackableComponent {
	/**
	 * Associated icon.
	 */
	protected ResizableIcon icon;

	/**
	 * Associated disabled icon.
	 */
	protected ResizableIcon disabledIcon;

	/**
	 * The button text.
	 */
	private String text;

	/**
	 * The button action model.
	 */
	protected ActionButtonModel actionModel;

	/**
	 * Additional text. This is shown for {@link CommandButtonDisplayState#TILE}
	 * .
	 */
	protected String extraText;

	/**
	 * Current display state of <code>this</code> button.
	 */
	protected CommandButtonDisplayState displayState;

	/**
	 * The dimension of the icon of the associated command button in the
	 * {@link CommandButtonDisplayState#CUSTOM} state.
	 */
	protected int customDimension;

	/**
	 * Indication whether this button is flat.
	 */
	protected boolean isFlat;

	/**
	 * Horizontal alignment of the content.
	 */
	private int horizontalAlignment;

	private double hgapScaleFactor;

	private double vgapScaleFactor;

	private RichTooltip actionRichTooltip;

	private CommandButtonLocationOrderKind locationOrderKind;

	/**
	 * Action handler for the button.
	 */
	protected ActionHandler actionHandler;

	protected String actionKeyTip;

	public static enum CommandButtonLocationOrderKind {
		ONLY,

		FIRST,

		MIDDLE,

		LAST
	}

	/**
	 * Creates a new command button.
	 * 
	 * @param text
	 *            Button title. May contain any number of words.
	 * @param icon
	 *            Button icon.
	 */
	public AbstractCommandButton(String text, ResizableIcon icon) {
		this.icon = icon;
		this.customDimension = -1;
		this.displayState = CommandButtonDisplayState.ORIG;
		this.horizontalAlignment = SwingConstants.CENTER;
		this.actionHandler = new ActionHandler();
		this.isFlat = true;
		this.hgapScaleFactor = 1.0;
		this.vgapScaleFactor = 1.0;
		this.setText(text);
		this.setOpaque(false);
	}

	/**
	 * Sets the new UI delegate.
	 * 
	 * @param ui
	 *            New UI delegate.
	 */
	public void setUI(CommandButtonUI ui) {
		super.setUI(ui);
	}

	/**
	 * Returns the UI delegate for this button.
	 * 
	 * @return The UI delegate for this button.
	 */
	public CommandButtonUI getUI() {
		return (CommandButtonUI) ui;
	}

	/**
	 * Sets new display state for <code>this</code> button.
	 * 
	 * @param state
	 *            New display state.
	 */
	public void setDisplayState(CommandButtonDisplayState state) {
		CommandButtonDisplayState old = this.displayState;
		this.displayState = state;

		this.firePropertyChange("displayState", old, this.displayState);
	}

	/**
	 * Returns the associated icon.
	 * 
	 * @return The associated icon.
	 */
	public ResizableIcon getIcon() {
		return icon;
	}

	/**
	 * Sets new icon for this button.
	 * 
	 * @param defaultIcon
	 *            New default icon for this button.
	 */
	public void setIcon(ResizableIcon defaultIcon) {
		ResizableIcon oldValue = this.icon;
		this.icon = defaultIcon;

		firePropertyChange("icon", oldValue, defaultIcon);
		if (defaultIcon != oldValue) {
			if (defaultIcon == null || oldValue == null
					|| defaultIcon.getIconWidth() != oldValue.getIconWidth()
					|| defaultIcon.getIconHeight() != oldValue.getIconHeight()) {
				revalidate();
			}
			repaint();
		}
	}

	/**
	 * Sets the disabled icon for this button.
	 * 
	 * @param disabledIcon
	 *            Disabled icon for this button.
	 */
	public void setDisabledIcon(ResizableIcon disabledIcon) {
		this.disabledIcon = disabledIcon;
	}

	/**
	 * Returns the associated disabled icon.
	 * 
	 * @return The associated disabled icon.
	 */
	public ResizableIcon getDisabledIcon() {
		return disabledIcon;
	}

	/**
	 * Return the current display state of <code>this</code> button.
	 * 
	 * @return The current display state of <code>this</code> button.
	 */
	public CommandButtonDisplayState getDisplayState() {
		return displayState;
	}

	/**
	 * Returns the extra text of this button.
	 * 
	 * @return Extra text of this button.
	 */
	public String getExtraText() {
		return this.extraText;
	}

	/**
	 * Sets the extra text for this button.
	 * 
	 * @param extraText
	 *            Extra text for this button.
	 */
	public void setExtraText(String extraText) {
		String oldValue = this.extraText;
		this.extraText = extraText;
		firePropertyChange("extraText", oldValue, extraText);

		if (accessibleContext != null) {
			accessibleContext.firePropertyChange(
					AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
					oldValue, extraText);
		}
		if (extraText == null || oldValue == null
				|| !extraText.equals(oldValue)) {
			revalidate();
			repaint();
		}
	}

	/**
	 * Returns the text of this button.
	 * 
	 * @return The text of this button.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the new text for this button.
	 * 
	 * @param text
	 *            The new text for this button.
	 */
	public void setText(String text) {
		String oldValue = this.text;
		this.text = text;
		firePropertyChange("text", oldValue, text);

		if (accessibleContext != null) {
			accessibleContext.firePropertyChange(
					AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
					oldValue, text);
		}
		if (text == null || oldValue == null || !text.equals(oldValue)) {
			revalidate();
			repaint();
		}
	}

	/**
	 * Updates the dimension of the icon of the associated command button in the
	 * {@link CommandButtonDisplayState#CUSTOM} state.
	 * 
	 * @param dimension
	 *            New dimension of the icon of the associated command button in
	 *            the {@link CommandButtonDisplayState#CUSTOM} state.
	 */
	public void updateCustomDimension(int dimension) {
		if (this.customDimension != dimension) {
			int old = this.customDimension;
			this.customDimension = dimension;
			this.firePropertyChange("customDimension", old,
					this.customDimension);
		}
	}

	/**
	 * Returns the dimension of the icon of the associated command button in the
	 * {@link CommandButtonDisplayState#CUSTOM} state.
	 * 
	 * @return The dimension of the icon of the associated command button in the
	 *         {@link CommandButtonDisplayState#CUSTOM} state.
	 */
	public int getCustomDimension() {
		return this.customDimension;
	}

	/**
	 * Returns indication whether this button has flat appearance.
	 * 
	 * @return <code>true</code> if this button has flat appearance,
	 *         <code>false</code> otherwise.
	 */
	public boolean isFlat() {
		return this.isFlat;
	}

	/**
	 * Sets the flat appearance of this button.
	 * 
	 * @param isFlat
	 *            If <code>true</code>, this button will have flat appearance,
	 *            otherwise this button will not have flat appearance.
	 */
	public void setFlat(boolean isFlat) {
		boolean old = this.isFlat;
		this.isFlat = isFlat;
		if (old != this.isFlat) {
			this.firePropertyChange("flat", old, this.isFlat);
		}

		if (old != isFlat) {
			repaint();
		}
	}

	/**
	 * Returns the action model for this button.
	 * 
	 * @return The action model for this button.
	 */
	public ActionButtonModel getActionModel() {
		return this.actionModel;
	}

	/**
	 * Sets the new action model for this button.
	 * 
	 * @param newModel
	 *            The new action model for this button.
	 */
	public void setActionModel(ActionButtonModel newModel) {
		ButtonModel oldModel = getActionModel();

		if (oldModel != null) {
			oldModel.removeChangeListener(this.actionHandler);
			oldModel.removeActionListener(this.actionHandler);
		}

		actionModel = newModel;

		if (newModel != null) {
			newModel.addChangeListener(this.actionHandler);
			newModel.addActionListener(this.actionHandler);
		}

		firePropertyChange("actionModel", oldModel, newModel);
		if (newModel != oldModel) {
			revalidate();
			repaint();
		}
	}

	/**
	 * Adds the specified action listener to this button.
	 * 
	 * @param l
	 *            Action listener to add.
	 */
	public void addActionListener(ActionListener l) {
		this.listenerList.add(ActionListener.class, l);
	}

	/**
	 * Removes the specified action listener from this button.
	 * 
	 * @param l
	 *            Action listener to remove.
	 */
	public void removeActionListener(ActionListener l) {
		this.listenerList.remove(ActionListener.class, l);
	}

	/**
	 * Adds the specified change listener to this button.
	 * 
	 * @param l
	 *            Change listener to add.
	 */
	public void addChangeListener(ChangeListener l) {
		this.listenerList.add(ChangeListener.class, l);
	}

	/**
	 * Removes the specified change listener from this button.
	 * 
	 * @param l
	 *            Change listener to remove.
	 */
	public void removeChangeListener(ChangeListener l) {
		this.listenerList.remove(ChangeListener.class, l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean b) {
		if (!b && actionModel.isRollover()) {
			actionModel.setRollover(false);
		}
		super.setEnabled(b);
		actionModel.setEnabled(b);
	}

	/**
	 * Default action handler for this button.
	 * 
	 * @author Kirill Grouchnikov
	 */
	class ActionHandler implements ActionListener, ChangeListener {
		public void stateChanged(ChangeEvent e) {
			fireStateChanged();
			repaint();
		}

		public void actionPerformed(ActionEvent event) {
			fireActionPerformed(event);
		}
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created.
	 * 
	 * @see EventListenerList
	 */
	protected void fireStateChanged() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		ChangeEvent ce = new ChangeEvent(this);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				// Lazily create the event:
				((ChangeListener) listeners[i + 1]).stateChanged(ce);
			}
		}
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * <code>event</code> parameter.
	 * 
	 * @param event
	 *            the <code>ActionEvent</code> object
	 * @see EventListenerList
	 */
	protected void fireActionPerformed(ActionEvent event) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		ActionEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ActionListener.class) {
				// Lazily create the event:
				if (e == null) {
					String actionCommand = event.getActionCommand();
					e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
							actionCommand, event.getWhen(), event
									.getModifiers());
				}
				((ActionListener) listeners[i + 1]).actionPerformed(e);
			}
		}
	}

	/**
	 * Sets new horizontal alignment for the content of this button.
	 * 
	 * @param alignment
	 *            New horizontal alignment for the content of this button.
	 */
	public void setHorizontalAlignment(int alignment) {
		if (alignment == this.horizontalAlignment)
			return;
		int oldValue = this.horizontalAlignment;
		this.horizontalAlignment = alignment;
		firePropertyChange("horizontalAlignment", oldValue,
				this.horizontalAlignment);
		repaint();
	}

	/**
	 * Returns the horizontal alignment for the content of this button.
	 * 
	 * @return The horizontal alignment for the content of this button.
	 */
	public int getHorizontalAlignment() {
		return this.horizontalAlignment;
	}

	/**
	 * Sets new horizontal gap scale factor for the content of this button.
	 * 
	 * @param hgapScaleFactor
	 *            New horizontal gap scale factor for the content of this
	 *            button.
	 */
	public void setHGapScaleFactor(double hgapScaleFactor) {
		if (hgapScaleFactor == this.hgapScaleFactor)
			return;
		double oldValue = this.hgapScaleFactor;
		this.hgapScaleFactor = hgapScaleFactor;
		firePropertyChange("hgapScaleFactor", oldValue, this.hgapScaleFactor);
		if (this.hgapScaleFactor != oldValue) {
			revalidate();
			repaint();
		}
	}

	/**
	 * Sets new vertical gap scale factor for the content of this button.
	 * 
	 * @param vgapScaleFactor
	 *            New vertical gap scale factor for the content of this button.
	 */
	public void setVGapScaleFactor(double vgapScaleFactor) {
		if (vgapScaleFactor == this.vgapScaleFactor)
			return;
		double oldValue = this.vgapScaleFactor;
		this.vgapScaleFactor = vgapScaleFactor;
		firePropertyChange("vgapScaleFactor", oldValue, this.vgapScaleFactor);
		if (this.vgapScaleFactor != oldValue) {
			revalidate();
			repaint();
		}
	}

	/**
	 * Sets new gap scale factor for the content of this button.
	 * 
	 * @param gapScaleFactor
	 *            New gap scale factor for the content of this button.
	 */
	public void setGapScaleFactor(double gapScaleFactor) {
		setHGapScaleFactor(gapScaleFactor);
		setVGapScaleFactor(gapScaleFactor);
	}

	/**
	 * Returns the horizontal gap scale factor for the content of this button.
	 * 
	 * @return The horizontal gap scale factor for the content of this button.
	 */
	public double getHGapScaleFactor() {
		return this.hgapScaleFactor;
	}

	/**
	 * Returns the vertical gap scale factor for the content of this button.
	 * 
	 * @return The vertical gap scale factor for the content of this button.
	 */
	public double getVGapScaleFactor() {
		return this.vgapScaleFactor;
	}

	/**
	 * Programmatically perform a "click". This does the same thing as if the
	 * user had pressed and released the button.
	 */
	public void doActionClick() {
		Dimension size = getSize();
		ButtonModel actionModel = this.getActionModel();
		actionModel.setArmed(true);
		actionModel.setPressed(true);
		paintImmediately(new Rectangle(0, 0, size.width, size.height));
		try {
			Thread.sleep(100);
		} catch (InterruptedException ie) {
		}
		actionModel.setPressed(false);
		actionModel.setArmed(false);
	}

	public void setActionRichTooltip(RichTooltip richTooltip) {
		this.actionRichTooltip = richTooltip;
		RichToolTipManager richToolTipManager = RichToolTipManager
				.sharedInstance();
		if (richTooltip != null) {
			richToolTipManager.registerComponent(this);
		} else {
			richToolTipManager.unregisterComponent(this);
		}
	}

	@Override
	public RichTooltip getRichTooltip(MouseEvent mouseEvent) {
		return this.actionRichTooltip;
	}

	@Override
	public void setToolTipText(String text) {
		throw new UnsupportedOperationException("Use rich tooltip APIs");
	}

	public CommandButtonLocationOrderKind getLocationOrderKind() {
		return locationOrderKind;
	}

	public void setLocationOrderKind(
			CommandButtonLocationOrderKind locationOrderKind) {
		CommandButtonLocationOrderKind old = this.locationOrderKind;
		if (old != locationOrderKind) {
			this.locationOrderKind = locationOrderKind;
			this.firePropertyChange("locationOrderKind", old,
					this.locationOrderKind);
		}
	}

	public String getActionKeyTip() {
		return this.actionKeyTip;
	}

	public void setActionKeyTip(String actionKeyTip) {
		String old = this.actionKeyTip;
		this.actionKeyTip = actionKeyTip;
		this.firePropertyChange("actionKeyTip", old, this.actionKeyTip);
	}
}
