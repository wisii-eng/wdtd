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

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RolloverActionListener;
import org.jvnet.flamingo.utils.KeyTipRenderingUtilities;

/**
 * Basic UI delegate for the {@link JCommandMenuButton} component.
 * 
 * @author Kirill Grouchnikov
 */
public class BasicCommandMenuButtonUI extends BasicCommandButtonUI {
	/**
	 * Rollover menu mouse listener.
	 */
	protected MouseListener rolloverMenuMouseListener;

	public static ComponentUI createUI(JComponent c) {
		return new BasicCommandMenuButtonUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.flamingo.common.ui.BasicCommandButtonUI#installListeners()
	 */
	@Override
	protected void installListeners() {
		super.installListeners();

		this.rolloverMenuMouseListener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (commandButton.isEnabled()) {
					int modifiers = 0;
					AWTEvent currentEvent = EventQueue.getCurrentEvent();
					if (currentEvent instanceof InputEvent) {
						modifiers = ((InputEvent) currentEvent).getModifiers();
					} else if (currentEvent instanceof ActionEvent) {
						modifiers = ((ActionEvent) currentEvent).getModifiers();
					}
					fireRolloverActionPerformed(new ActionEvent(this,
							ActionEvent.ACTION_PERFORMED, commandButton
									.getActionModel().getActionCommand(),
							EventQueue.getMostRecentEventTime(), modifiers));

					processPopupAction();
				}
			}
		};
		this.commandButton.addMouseListener(this.rolloverMenuMouseListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jvnet.flamingo.common.ui.BasicCommandButtonUI#uninstallListeners()
	 */
	@Override
	protected void uninstallListeners() {
		this.commandButton.removeMouseListener(this.rolloverMenuMouseListener);
		this.rolloverMenuMouseListener = null;

		super.uninstallListeners();
	}

	/**
	 * Fires the rollover action on all registered handlers.
	 * 
	 * @param e
	 *            Event object.
	 */
	protected void fireRolloverActionPerformed(ActionEvent e) {
		// Guaranteed to return a non-null array
		RolloverActionListener[] listeners = commandButton
				.getListeners(RolloverActionListener.class);
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 1; i >= 0; i--) {
			(listeners[i]).actionPerformed(e);
		}
	}

	@Override
	public void update(Graphics g, JComponent c) {
		JCommandMenuButton menuButton = (JCommandMenuButton) c;
		super.update(g, c);

//		System.out.println("Updating " + menuButton.getText());
		KeyTipRenderingUtilities.renderMenuButtonKeyTips(g, menuButton,
				layoutManager);
	}
}
