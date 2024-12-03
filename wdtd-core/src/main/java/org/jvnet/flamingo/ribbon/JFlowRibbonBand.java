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

import java.awt.event.ActionListener;

import javax.swing.JComponent;

import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.ribbon.resize.CoreRibbonResizePolicies;
import org.jvnet.flamingo.ribbon.ui.JFlowBandControlPanel;

/**
 * Ribbon band component. Is part of a logical {@link RibbonTask}.
 * 
 * @author Kirill Grouchnikov
 */
public class JFlowRibbonBand extends AbstractRibbonBand<JFlowBandControlPanel> {
	/**
	 * Creates a new ribbon band.
	 * 
	 * @param title
	 *            Band title.
	 * @param icon
	 *            Associated icon (for collapsed state).
	 */
	public JFlowRibbonBand(String title, ResizableIcon icon) {
		this(title, icon, null);
	}

	/**
	 * Creates a new ribbon band.
	 * 
	 * @param title
	 *            Band title.
	 * @param icon
	 *            Associated icon (for collapsed state).
	 * @param expandActionListener
	 *            Expand action listener (can be <code>null</code>).
	 */
	public JFlowRibbonBand(String title, ResizableIcon icon,
			ActionListener expandActionListener) {
		super(title, icon, expandActionListener, new JFlowBandControlPanel());
		this.resizePolicies = CoreRibbonResizePolicies
				.getCoreFlowPoliciesRestrictive(this, 3);
		updateUI();
	}

	public void addFlowComponent(JComponent comp) {
		this.controlPanel.addFlowComponent(comp);
	}

	@Override
	public AbstractRibbonBand<JFlowBandControlPanel> cloneBand() {
		return new JFlowRibbonBand(this.getTitle(), this.getIcon(), this
				.getExpandActionListener());
	}
}
