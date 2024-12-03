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

import java.awt.event.ActionEvent;

import javax.swing.UIManager;

import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.ui.BasicCommandMenuButtonUI;
import org.jvnet.flamingo.common.ui.CommandButtonUI;

public class JCommandMenuButton extends JCommandButton {
	/**
	 * The UI class ID string.
	 */
	public static final String uiClassID = "CommandMenuButtonUI";

	public JCommandMenuButton(String title, ResizableIcon icon) {
		super(title, icon);
	}

	public void addRolloverActionListener(RolloverActionListener l) {
		this.listenerList.add(RolloverActionListener.class, l);
	}

	public void removeRolloverActionListener(RolloverActionListener l) {
		this.listenerList.remove(RolloverActionListener.class, l);
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
			setUI(BasicCommandMenuButtonUI.createUI(this));
		}
	}

	@Override
	boolean canHaveBothKeyTips() {
		return true;
	}

	public void doActionRollover() {
		ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				this.getActionModel().getActionCommand());
		// Guaranteed to return a non-null array
		RolloverActionListener[] listeners = this
				.getListeners(RolloverActionListener.class);
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 1; i >= 0; i--) {
			(listeners[i]).actionPerformed(ae);
		}
	}
}
