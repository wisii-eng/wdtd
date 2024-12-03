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

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.DefaultButtonModel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.model.ActionRepeatableButtonModel;
import org.jvnet.flamingo.common.model.PopupButtonModel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.common.ui.BasicCommandButtonUI;
import org.jvnet.flamingo.common.ui.CommandButtonUI;

/**
 * Command button.
 * 
 * @author Kirill Grouchnikov
 */
public class JCommandButton extends AbstractCommandButton {
	/**
	 * The UI class ID string.
	 */
	public static final String uiClassID = "CommandButtonUI";

	/**
	 * Associated popup callback. May be <code>null</code>.
	 */
	protected PopupPanelCallback popupCallback;

	/**
	 * The command button kind of this button.
	 */
	protected CommandButtonKind commandButtonKind;

	/**
	 * The popup orientation kind of this button.
	 */
	protected CommandButtonPopupOrientationKind popupOrientationKind;

	/**
	 * Indicates the auto-repeat action mode. When the button is not in the
	 * auto-repeat action mode, the registered action listeners are activated
	 * when the mouse is released (just as with the base {@link AbstractButton}
	 * ). When the button is in auto-repeat mode, the registered action
	 * listeners are activated when the mouse is pressed. In addition, is the
	 * mouse is still pressed after {@link #getAutoRepeatInitialInterval()}, the
	 * action listeners will be activated every
	 * {@link #getAutoRepeatSubsequentInterval()} until the button is disabled
	 * or the mouse is released.
	 * 
	 * @see #autoRepeatInitialInterval
	 * @see #autoRepeatSubsequentInterval
	 */
	protected boolean isAutoRepeatAction;

	/**
	 * The initial interval for invoking the registered action listeners in the
	 * auto-repeat action mode.
	 * 
	 * @see #isAutoRepeatAction
	 * @see #autoRepeatSubsequentInterval
	 */
	protected int autoRepeatInitialInterval;

	/**
	 * The subsequent interval for invoking the registered action listeners in
	 * the auto-repeat action mode.
	 * 
	 * @see #isAutoRepeatAction
	 * @see #autoRepeatInitialInterval
	 */
	protected int autoRepeatSubsequentInterval;

	/**
	 * Indicates that rollover should result in firing the action. Used in
	 * conjunction with the {@link #isAutoRepeatAction} can model quick pan
	 * buttons such as breadcrumb bar scrollers.
	 */
	protected boolean isFireActionOnRollover;

	/**
	 * Popup model of this button.
	 */
	protected PopupButtonModel popupModel;

	/**
	 * Default popup handler for this button.
	 */
	protected PopupHandler popupHandler;

	private RichTooltip popupRichTooltip;

	protected String popupKeyTip;

	/**
	 * Enumerates the available command button kinds.
	 * 
	 * @author Kirill Grouchnikov
	 */
	public static enum CommandButtonKind {
		/**
		 * Command button that has only action area.
		 */
		ACTION_ONLY(true, false),

		/**
		 * Command button that has only popup area.
		 */
		POPUP_ONLY(false, true),

		/**
		 * Command button that has both action and popup areas, with the main
		 * text click activating the action.
		 */
		ACTION_AND_POPUP_MAIN_ACTION(true, true),

		/**
		 * Command button that has both action and popup areas, with the main
		 * text click activating the popup.
		 */
		ACTION_AND_POPUP_MAIN_POPUP(true, true);

		/**
		 * <code>true</code> if the command button kind has an action.
		 */
		private boolean hasAction;

		/**
		 * <code>true</code> if the command button kind has a popup.
		 */
		private boolean hasPopup;

		/**
		 * Constructs a new command button kind.
		 * 
		 * @param hasAction
		 *            Indicates whether the command button kind has an action.
		 * @param hasPopup
		 *            Indicates whether the command button kind has a popup.
		 */
		private CommandButtonKind(boolean hasAction, boolean hasPopup) {
			this.hasAction = hasAction;
			this.hasPopup = hasPopup;
		}

		/**
		 * Returns indication whether this command button kind has an action.
		 * 
		 * @return <code>true</code> if the command button kind has an action,
		 *         <code>false</code> otherwise.
		 */
		public boolean hasAction() {
			return hasAction;
		}

		/**
		 * Returns indication whether this command button kind has a popup.
		 * 
		 * @return <code>true</code> if the command button kind has a popup,
		 *         <code>false</code> otherwise.
		 */
		public boolean hasPopup() {
			return hasPopup;
		}
	}

	public static enum CommandButtonPopupOrientationKind {
		DOWNWARD,

		SIDEWARD
	}

	/**
	 * Extension of the default button model that supports the
	 * {@link PopupButtonModel} interface.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class DefaultPopupButtonModel extends DefaultButtonModel
			implements PopupButtonModel {
		/**
		 * Timer for the auto-repeat action mode.
		 */
		protected Timer autoRepeatTimer;

		/**
		 * Identifies the "popup showing" bit in the bitmask, which indicates
		 * that the visibility status of the associated popup.
		 */
		public final static int POPUP_SHOWING = 1 << 8;

		/**
		 * Creates a new default popup button model.
		 */
		public DefaultPopupButtonModel() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jvnet.flamingo.common.PopupButtonModel#addPopupActionListener
		 * (org.jvnet.flamingo.common.PopupActionListener)
		 */
		@Override
		public void addPopupActionListener(PopupActionListener l) {
			listenerList.add(PopupActionListener.class, l);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jvnet.flamingo.common.PopupButtonModel#removePopupActionListener
		 * (org.jvnet.flamingo.common.PopupActionListener)
		 */
		@Override
		public void removePopupActionListener(PopupActionListener l) {
			listenerList.remove(PopupActionListener.class, l);
		}

		/**
		 * Notifies all listeners that have registered interest for notification
		 * on this event type.
		 * 
		 * @param e
		 *            the <code>ActionEvent</code> to deliver to listeners
		 * @see EventListenerList
		 */
		protected void firePopupActionPerformed(ActionEvent e) {
			// Guaranteed to return a non-null array
			Object[] listeners = listenerList.getListenerList();
			// Process the listeners last to first, notifying
			// those that are interested in this event
			for (int i = listeners.length - 2; i >= 0; i -= 2) {
				if (listeners[i] == PopupActionListener.class) {
					((PopupActionListener) listeners[i + 1]).actionPerformed(e);
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.DefaultButtonModel#setPressed(boolean)
		 */
		@Override
		public void setPressed(boolean b) {
			if ((isPressed() == b) || !isEnabled()) {
				return;
			}

			if (b) {
				stateMask |= PRESSED;
			} else {
				stateMask &= ~PRESSED;
			}

			if (isPressed() && isArmed()) {
				// fire the popup action on button press and not on button
				// release - like the comboboxes
				int modifiers = 0;
				AWTEvent currentEvent = EventQueue.getCurrentEvent();
				if (currentEvent instanceof InputEvent) {
					modifiers = ((InputEvent) currentEvent).getModifiers();
				} else if (currentEvent instanceof ActionEvent) {
					modifiers = ((ActionEvent) currentEvent).getModifiers();
				}
				firePopupActionPerformed(new ActionEvent(this,
						ActionEvent.ACTION_PERFORMED, getActionCommand(),
						EventQueue.getMostRecentEventTime(), modifiers));
			}

			fireStateChanged();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jvnet.flamingo.common.PopupButtonModel#isPopupShowing()
		 */
		@Override
		public boolean isPopupShowing() {
			return (stateMask & POPUP_SHOWING) != 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jvnet.flamingo.common.PopupButtonModel#setPopupShowing(boolean)
		 */
		@Override
		public void setPopupShowing(boolean b) {
			// System.out.println(this.isPopupShowing() + "-->" + b);
			if (this.isPopupShowing() == b) {
				return;
			}

			if (b) {
				stateMask |= POPUP_SHOWING;
			} else {
				stateMask &= ~POPUP_SHOWING;
			}

			fireStateChanged();
		}

	}

	/**
	 * Creates a new command button.
	 * 
	 * @param title
	 *            Button title. May contain any number of words.
	 * @param icon
	 *            Button icon.
	 */
	public JCommandButton(String title, ResizableIcon icon) {
		super(title, icon);

		this.setActionModel(new ActionRepeatableButtonModel(this));

		// important - handler creation must be done before setting
		// the popup model so that it can be registered to track the
		// changes
		this.popupHandler = new PopupHandler();
		this.setPopupModel(new DefaultPopupButtonModel());

		this.commandButtonKind = CommandButtonKind.ACTION_ONLY;
		this.popupOrientationKind = CommandButtonPopupOrientationKind.DOWNWARD;
		this.displayState = CommandButtonDisplayState.ORIG;
		this.isAutoRepeatAction = false;
		this.autoRepeatInitialInterval = 500;
		this.autoRepeatSubsequentInterval = 100;

		this.updateUI();
	}

	/**
	 * Returns the command button kind of this button.
	 * 
	 * @return Command button kind of this button.
	 */
	public CommandButtonKind getCommandButtonKind() {
		return this.commandButtonKind;
	}

	/**
	 * Sets the kind for this button.
	 * 
	 * @param commandButtonKind
	 *            The new button kind.
	 */
	public void setCommandButtonKind(CommandButtonKind commandButtonKind) {
		CommandButtonKind old = this.commandButtonKind;
		this.commandButtonKind = commandButtonKind;
		if (old != this.commandButtonKind) {
			firePropertyChange("commandButtonKind", old, this.commandButtonKind);
		}
	}

	/**
	 * Returns the popup orientation kind of this button.
	 * 
	 * @return Popup orientation kind of this button.
	 */
	public CommandButtonPopupOrientationKind getPopupOrientationKind() {
		return this.popupOrientationKind;
	}

	/**
	 * Sets the popup orientation for this button.
	 * 
	 * @param popupOrientationKind
	 *            The new popup orientation kind.
	 */
	public void setPopupOrientationKind(
			CommandButtonPopupOrientationKind popupOrientationKind) {
		CommandButtonPopupOrientationKind old = this.popupOrientationKind;
		this.popupOrientationKind = popupOrientationKind;
		if (old != this.popupOrientationKind) {
			firePropertyChange("popupOrientationKind", old,
					this.popupOrientationKind);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#updateUI()
	 */
	@Override
	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((CommandButtonUI) UIManager.getUI(this));
		} else {
			setUI(BasicCommandButtonUI.createUI(this));
		}
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
	 * Returns the associated popup callback.
	 * 
	 * @return The associated popup callback.
	 */
	public PopupPanelCallback getPopupCallback() {
		return this.popupCallback;
	}

	/**
	 * Sets new popup callback for <code>this</code> button.
	 * 
	 * @param popupCallback
	 *            New popup callback for <code>this</code> button.
	 */
	public void setPopupCallback(PopupPanelCallback popupCallback) {
		this.popupCallback = popupCallback;
	}

	// /**
	// * Adds a popup menu listener to this button. All registered listeners
	// will
	// * be called when the "popup area" of this button is activated, providing
	// a
	// * convenient way to populate the popup menu without creating, positioning
	// * or tracking the lifecycle of the popup menu itself. Note that if
	// * {@link #getPopupPanel()} returns a non-<code>null</code> result, the
	// * popup click will show that panel and not invoke any of the registered
	// * listeners.
	// *
	// * @param l
	// * The popup menu listener to be added
	// */
	// public void addPopupMenuListener(PopupMenuListener l) {
	// listenerList.add(PopupMenuListener.class, l);
	// }

	// /**
	// * Removes an <code>PopupActionListener</code> from the button.
	// *
	// * @param l
	// * the listener to be removed
	// */
	// public void removePopupActionListener(PopupMenuListener l) {
	// listenerList.remove(PopupMenuListener.class, l);
	// }

	/**
	 * Sets the auto-repeat action indication.
	 * 
	 * @param isAutoRepeatAction
	 *            If <code>true</code>, pressing the button will activate
	 *            auto-repeat action mode. When the button is not in the
	 *            auto-repeat action mode, the registered action listeners are
	 *            activated when the mouse is released (just as with the base
	 *            {@link AbstractButton}). When the button is in auto-repeat
	 *            mode, the registered action listeners are activated when the
	 *            mouse is pressed. In addition, is the mouse is still pressed
	 *            after {@link #getAutoRepeatInitialInterval()}, the action
	 *            listeners will be activated every
	 *            {@link #getAutoRepeatSubsequentInterval()} until the button is
	 *            disabled or the mouse is released.
	 * @see #setAutoRepeatActionIntervals(int, int)
	 * @see #isAutoRepeatAction()
	 */
	public void setAutoRepeatAction(boolean isAutoRepeatAction) {
		this.isAutoRepeatAction = isAutoRepeatAction;
	}

	/**
	 * Sets the intervals for the auto-repeat action mode.
	 * 
	 * @param initial
	 *            The initial interval for invoking the registered action
	 *            listeners in the auto-repeat action mode.
	 * @param subsequent
	 *            The subsequent interval for invoking the registered action
	 *            listeners in the auto-repeat action mode.
	 * @see #setAutoRepeatAction(boolean)
	 * @see #isAutoRepeatAction()
	 * @see #getAutoRepeatInitialInterval()
	 * @see #getAutoRepeatSubsequentInterval()
	 */
	public void setAutoRepeatActionIntervals(int initial, int subsequent) {
		this.autoRepeatInitialInterval = initial;
		this.autoRepeatSubsequentInterval = subsequent;
	}

	/**
	 * Returns indication whether the button is in auto-repeat action mode.
	 * 
	 * @return <code>true</code> if the button is in auto-repeat action mode,
	 *         <code>false</code> otherwise.
	 * @see #setAutoRepeatAction(boolean)
	 * @see #setAutoRepeatActionIntervals(int, int)
	 * @see #getAutoRepeatInitialInterval()
	 * @see #getAutoRepeatSubsequentInterval()
	 */
	public boolean isAutoRepeatAction() {
		return this.isAutoRepeatAction;
	}

	/**
	 * Returns the initial interval for invoking the registered action listeners
	 * in the auto-repeat action mode.
	 * 
	 * @return The initial interval for invoking the registered action listeners
	 *         in the auto-repeat action mode.
	 * @see #setAutoRepeatActionIntervals(int, int)
	 * @see #setAutoRepeatAction(boolean)
	 * @see #isAutoRepeatAction()
	 * @see #getAutoRepeatSubsequentInterval()
	 */
	public int getAutoRepeatInitialInterval() {
		return autoRepeatInitialInterval;
	}

	/**
	 * Returns the subsequent interval for invoking the registered action
	 * listeners in the auto-repeat action mode.
	 * 
	 * @return The subsequent interval for invoking the registered action
	 *         listeners in the auto-repeat action mode.
	 * @see #setAutoRepeatActionIntervals(int, int)
	 * @see #setAutoRepeatAction(boolean)
	 * @see #isAutoRepeatAction()
	 * @see #getAutoRepeatInitialInterval()
	 */
	public int getAutoRepeatSubsequentInterval() {
		return autoRepeatSubsequentInterval;
	}

	/**
	 * Sets action-on-rollover mode. When this mode is on, button will fire
	 * action events when it gets rollover (instead of press). Combine with
	 * {@link #setAutoRepeatAction(boolean)} passing <code>true</code> to get
	 * auto-repeat action fired on rollover (useful for quicker manipulation of
	 * scroller buttons, for example).
	 * 
	 * @param isFireActionOnRollover
	 *            If <code>true</code>, the button is moved into the
	 *            action-on-rollover mode.
	 * @see #isFireActionOnRollover()
	 */
	public void setFireActionOnRollover(boolean isFireActionOnRollover) {
		this.isFireActionOnRollover = isFireActionOnRollover;
	}

	/**
	 * Returns indication whether this button is in action-on-rollover mode.
	 * 
	 * @return <code>true</code> if this button is in action-on-rollover mode,
	 *         <code>false</code> otherwise.
	 * @see #setFireActionOnRollover(boolean)
	 */
	public boolean isFireActionOnRollover() {
		return this.isFireActionOnRollover;
	}

	/**
	 * Returns the popup model of this button.
	 * 
	 * @return The popup model of this button.
	 */
	public PopupButtonModel getPopupModel() {
		return this.popupModel;
	}

	/**
	 * Sets the new popup model for this button.
	 * 
	 * @param newModel
	 *            The new popup model for this button.
	 */
	public void setPopupModel(PopupButtonModel newModel) {

		PopupButtonModel oldModel = getPopupModel();

		if (oldModel != null) {
			oldModel.removeChangeListener(this.popupHandler);
			oldModel.removeActionListener(this.popupHandler);
		}

		this.popupModel = newModel;

		if (newModel != null) {
			newModel.addChangeListener(this.popupHandler);
			newModel.addActionListener(this.popupHandler);
		}

		firePropertyChange("popupModel", oldModel, newModel);
		if (newModel != oldModel) {
			revalidate();
			repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.flamingo.common.AbstractCommandButton#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean b) {
		if (!b && popupModel.isRollover()) {
			popupModel.setRollover(false);
		}
		super.setEnabled(b);
		popupModel.setEnabled(b);
	}

	/**
	 * Default popup handler.
	 * 
	 * @author Kirill Grouchnikov
	 */
	class PopupHandler implements PopupActionListener, ChangeListener {
		public void stateChanged(ChangeEvent e) {
			fireStateChanged();
			repaint();
		}

		public void actionPerformed(ActionEvent event) {
			firePopupActionPerformed(event);
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
	protected void firePopupActionPerformed(ActionEvent event) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		ActionEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PopupActionListener.class) {
				// Lazily create the event:
				if (e == null) {
					String actionCommand = event.getActionCommand();
					e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
							actionCommand, event.getWhen(), event
									.getModifiers());
				}
				((PopupActionListener) listeners[i + 1]).actionPerformed(e);
			}
		}
	}

	public void setPopupRichTooltip(RichTooltip richTooltip) {
		this.popupRichTooltip = richTooltip;
		RichToolTipManager richToolTipManager = RichToolTipManager
				.sharedInstance();
		if (richTooltip != null) {
			richToolTipManager.registerComponent(this);
		} else {
			richToolTipManager.unregisterComponent(this);
		}
	}

	@Override
	public RichTooltip getRichTooltip(MouseEvent event) {
		CommandButtonUI ui = this.getUI();
		if (ui.getActionClickArea().contains(event.getPoint()))
			return super.getRichTooltip(event);
		if (ui.getPopupClickArea().contains(event.getPoint()))
			return this.popupRichTooltip;
		return null;
	}

	public String getPopupKeyTip() {
		return this.popupKeyTip;
	}

	public void setPopupKeyTip(String popupKeyTip) {
		if (!canHaveBothKeyTips() && (popupKeyTip != null)
				&& (this.actionKeyTip != null)) {
			throw new IllegalArgumentException(
					"Action *and* popup keytips are not supported at the same time");
		}

		String old = this.popupKeyTip;
		this.popupKeyTip = popupKeyTip;
		this.firePropertyChange("popupKeyTip", old, this.popupKeyTip);
	}

	@Override
	public void setActionKeyTip(String actionKeyTip) {
		if (!canHaveBothKeyTips() && (popupKeyTip != null)
				&& (this.actionKeyTip != null)) {
			throw new IllegalArgumentException(
					"Action *and* popup keytips are not supported at the same time");
		}

		super.setActionKeyTip(actionKeyTip);
	}

	boolean canHaveBothKeyTips() {
		return false;
	}

	/**
	 * Programmatically perform a "click" on the popup area. This does the same
	 * thing as if the user had pressed and released the button.
	 */
	public void doPopupClick() {
		Dimension size = getSize();
		PopupButtonModel popupModel = this.getPopupModel();
		popupModel.setArmed(true);
		popupModel.setPressed(true);
		paintImmediately(new Rectangle(0, 0, size.width, size.height));
		try {
			Thread.sleep(100);
		} catch (InterruptedException ie) {
		}
		popupModel.setPressed(false);
		popupModel.setArmed(false);
		popupModel.setPopupShowing(true);
		paintImmediately(new Rectangle(0, 0, size.width, size.height));
	}

}
