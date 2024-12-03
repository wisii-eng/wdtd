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
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.UIManager;

import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.ribbon.resize.RibbonBandResizePolicy;
import org.jvnet.flamingo.ribbon.ui.AbstractBandControlPanel;
import org.jvnet.flamingo.ribbon.ui.BasicRibbonBandUI;
import org.jvnet.flamingo.ribbon.ui.RibbonBandUI;

/**
 * Ribbon band component. Is part of a logical {@link RibbonTask}.
 * 
 * @author Kirill Grouchnikov
 */
public abstract class AbstractRibbonBand<T extends AbstractBandControlPanel>
		extends JComponent {
	/**
	 * The UI class ID string.
	 */
	public static final String uiClassID = "RibbonBandUI";

	/**
	 * Band title.
	 */
	private String title;

	/**
	 * Optional <code>expand</code> action listener. If present, the title pane
	 * shows button with plus sign. The action listener on the button will be
	 * <code>this</code> listener.
	 */
	private ActionListener expandActionListener;

	/**
	 * Band control panel. Can be not-visible when only a collapse ribbon button
	 * can fit the available width.
	 */
	protected T controlPanel;

	private AbstractRibbonBand popupRibbonBand;

	/**
	 * Icon for the collapsed state.
	 */
	private ResizableIcon icon;

	private RibbonBandResizePolicy currResizePolicy;

	protected List<RibbonBandResizePolicy> resizePolicies;

	private String expandButtonKeyTip;

	private String collapsedStateKeyTip;

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
	public AbstractRibbonBand(String title, ResizableIcon icon,
			ActionListener expandActionListener, T controlPanel) {
		super();
		this.title = title;
		this.icon = icon;
		this.expandActionListener = expandActionListener;

		this.controlPanel = controlPanel;
		this.controlPanel.setRibbonBand(this);
		this.add(this.controlPanel);

		updateUI();
	}

	public abstract AbstractRibbonBand<T> cloneBand();

	/**
	 * Returns the UI object which implements the L&F for this component.
	 * 
	 * @return a <code>RibbonBandUI</code> object
	 * @see #setUI
	 */
	public RibbonBandUI getUI() {
		return (RibbonBandUI) ui;
	}

	/**
	 * Sets the new UI delegate.
	 * 
	 * @param ui
	 *            New UI delegate.
	 */
	public void setUI(RibbonBandUI ui) {
		super.setUI(ui);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#updateUI()
	 */
	@Override
	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((RibbonBandUI) UIManager.getUI(this));
		} else {
			setUI(new BasicRibbonBandUI());
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
	 * Returns the title of <code>this</code> band.
	 * 
	 * @return Title of <code>this</code> band.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the associated icon (for collapsed state).
	 * 
	 * @return The associated icon (for collapsed state).
	 */
	public ResizableIcon getIcon() {
		return icon;
	}

	/**
	 * Changes the title of this ribbon band.
	 * 
	 * @param title
	 *            The new title for this ribbon band.
	 */
	public void setTitle(String title) {
		String old = this.title;
		this.title = title;
		this.firePropertyChange("title", old, this.title);
	}

	/**
	 * Returns the expand action listener of <code>this</code> ribbon band. The
	 * result may be <code>null</code>.
	 * 
	 * @return Expand action listener of <code>this</code> ribbon band. The
	 *         result may be <code>null</code>.
	 */
	public ActionListener getExpandActionListener() {
		return this.expandActionListener;
	}

	/**
	 * Returns the control panel of <code>this</code> ribbon band. The result
	 * may be <code>null</code>.
	 * 
	 * @return Control panel of <code>this</code> ribbon band. The result may be
	 *         <code>null</code>.
	 */
	public T getControlPanel() {
		return this.controlPanel;
	}

	/**
	 * Sets the control panel of <code>this</code> ribbon band. The parameter
	 * may be <code>null</code>. This method is for internal use only.
	 * 
	 * @param controlPanel
	 *            The control panel for <code>this</code> ribbon band. May be
	 *            <code>null</code>.
	 */
	public void setControlPanel(T controlPanel) {
		if (controlPanel == null) {
			this.remove(this.controlPanel);
		} else {
			this.add(controlPanel);
		}
		this.controlPanel = controlPanel;
	}

	public AbstractRibbonBand getPopupRibbonBand() {
		return popupRibbonBand;
	}

	public void setPopupRibbonBand(AbstractRibbonBand popupRibbonBand) {
		this.popupRibbonBand = popupRibbonBand;
	}

	public RibbonBandResizePolicy getCurrentResizePolicy() {
		return currResizePolicy;
	}

	public void setCurrentResizePolicy(RibbonBandResizePolicy resizePolicy) {
		this.currResizePolicy = resizePolicy;
	}

	public List<RibbonBandResizePolicy> getResizePolicies() {
		return Collections.unmodifiableList(resizePolicies);
	}

	public void setResizePolicies(List<RibbonBandResizePolicy> resizePolicies) {
		this.resizePolicies = Collections.unmodifiableList(resizePolicies);
	}

	public String getExpandButtonKeyTip() {
		return expandButtonKeyTip;
	}

	public void setExpandButtonKeyTip(String expandButtonKeyTip) {
		String old = this.expandButtonKeyTip;
		this.expandButtonKeyTip = expandButtonKeyTip;
		this.firePropertyChange("expandButtonKeyTip", old,
				this.expandButtonKeyTip);
	}

	public String getCollapsedStateKeyTip() {
		return this.collapsedStateKeyTip;
	}

	public void setCollapsedStateKeyTip(String collapsedStateKeyTip) {
		String old = this.collapsedStateKeyTip;
		this.collapsedStateKeyTip = collapsedStateKeyTip;
		this.firePropertyChange("collapsedStateKeyTip", old,
				this.collapsedStateKeyTip);
	}
}
