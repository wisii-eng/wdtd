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

import java.awt.Color;

import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.plaf.ButtonUI;

import org.jvnet.flamingo.common.model.ActionToggleButtonModel;

/**
 * Toggle button for ribbon tasks. This class is for internal use only and
 * should not be directly used by the applications.
 * 
 * @author Kirill Grouchnikov
 */
public class JRibbonTaskToggleButton extends JToggleButton {
	/**
	 * The UI class ID string.
	 */
	public static final String uiClassID = "RibbonTaskToggleButtonUI";

	/**
	 * Color of the matching contextual task group. Can be <code>null</code> if
	 * the associated task is not contextual.
	 */
	private Color contextualGroupHueColor;

	private String keyTip;

	/**
	 * Creates a new toggle button.
	 * 
	 * @param text
	 */
	public JRibbonTaskToggleButton(String text) {
		super(text);
		setModel(new ActionToggleButtonModel(true));
		updateUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JToggleButton#updateUI()
	 */
	@Override
	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((ButtonUI) UIManager.getUI(this));
		} else {
			setUI(new BasicRibbonTaskToggleButtonUI());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.AbstractButton#getUI()
	 */
	@Override
	public ButtonUI getUI() {
		return (ButtonUI) ui;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JToggleButton#getUIClassID()
	 */
	@Override
	public String getUIClassID() {
		return uiClassID;
	}

	/**
	 * Returns the hue color of the matching contextual task group if the
	 * associated task is contextual.
	 * 
	 * @return The hue color of the matching contextual task group if the
	 *         associated task is contextual, <code>null</code> otherwise.
	 */
	public Color getContextualGroupHueColor() {
		return this.contextualGroupHueColor;
	}

	/**
	 * Sets the hue color of the matching contextual task group on this button.
	 * 
	 * @param contextualGroupHueColor
	 *            The hue color of the matching contextual task group.
	 */
	public void setContextualGroupHueColor(Color contextualGroupHueColor) {
		Color old = this.contextualGroupHueColor;
		this.contextualGroupHueColor = contextualGroupHueColor;

		this.firePropertyChange("contextualGroupHueColor", old,
				this.contextualGroupHueColor);
	}

	public void setKeyTip(String keyTip) {
		this.keyTip = keyTip;
	}

	public String getKeyTip() {
		return keyTip;
	}
}
