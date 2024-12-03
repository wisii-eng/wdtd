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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.icon.ResizableIcon;

public class RibbonApplicationMenuEntryPrimary extends
		RibbonApplicationMenuEntry {
	protected PrimaryRolloverCallback rolloverCallback;

	public static interface PrimaryRolloverCallback {
		public void menuEntryActivated(JPanel targetPanel);
	}

	/**
	 * List of titles for all button groups.
	 */
	protected List<String> groupTitles;

	/**
	 * List of all button groups.
	 */
	protected List<List<RibbonApplicationMenuEntrySecondary>> groupEntries;

	public RibbonApplicationMenuEntryPrimary(ResizableIcon icon, String text,
			ActionListener mainActionListener, CommandButtonKind entryKind) {
		super(icon, text, mainActionListener, entryKind);
		this.groupTitles = new ArrayList<String>();
		this.groupEntries = new ArrayList<List<RibbonApplicationMenuEntrySecondary>>();
	}

	public synchronized void addSecondaryMenuGroup(String groupName,
			RibbonApplicationMenuEntrySecondary... entries) {
		this.groupTitles.add(groupName);
		List<RibbonApplicationMenuEntrySecondary> entryList = new ArrayList<RibbonApplicationMenuEntrySecondary>();
		this.groupEntries.add(entryList);
		for (RibbonApplicationMenuEntrySecondary entry : entries) {
			entryList.add(entry);
		}
	}

	public int getSecondaryGroupCount() {
		return this.groupTitles.size();
	}

	public String getSecondaryGroupTitleAt(int index) {
		return this.groupTitles.get(index);
	}

	public List<RibbonApplicationMenuEntrySecondary> getSecondaryGroupEntries(
			int groupIndex) {
		return Collections.unmodifiableList(this.groupEntries.get(groupIndex));
	}

	public void setRolloverCallback(PrimaryRolloverCallback rolloverCallback) {
		this.rolloverCallback = rolloverCallback;
	}

	public PrimaryRolloverCallback getRolloverCallback() {
		return rolloverCallback;
	}
}
