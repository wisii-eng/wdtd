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

import java.awt.Color;
import java.util.ArrayList;

/**
 * A contextual group of {@link RibbonTask}s. Unlike general ribbon tasks,
 * contextual ribbon tasks are shown and hidden depending on the selected
 * element in the application. This class is a logical entity that groups ribbon
 * tasks belonging to the same contextual group.
 * 
 * @author Kirill Grouchnikov
 */
public class RibbonContextualTaskGroup {
	private JRibbon ribbon;
	
	/**
	 * List of all tasks.
	 */
	private ArrayList<RibbonTask> tasks;

	/**
	 * Group title.
	 */
	private String title;

	/**
	 * Hue color for this group.
	 */
	private Color hueColor;

	/**
	 * Alpha factor for colorizing the toggle tab buttons of tasks in contextual
	 * groups.
	 */
	public static final double HUE_ALPHA = 0.25;

	/**
	 * Creates a task contextual group that contains the specified tasks.
	 * 
	 * @param title
	 *            Group title.
	 * @param hueColor
	 *            Hue color for this group. Should be a saturated non-dark color
	 *            for good visuals.
	 * @param tasks
	 *            Tasks to add to the group.
	 */
	public RibbonContextualTaskGroup(String title, Color hueColor,
			RibbonTask... tasks) {
		this.title = title;
		this.hueColor = hueColor;
		this.tasks = new ArrayList<RibbonTask>();
		for (RibbonTask ribbonTask : tasks) {
			ribbonTask.setContextualGroup(this);
			this.tasks.add(ribbonTask);
		}
	}

	/**
	 * Returns the number of tasks in <code>this</code> group.
	 * 
	 * @return Number of tasks in <code>this</code> group.
	 */
	public int getTaskCount() {
		return this.tasks.size();
	}

	/**
	 * Returns task at the specified index from <code>this</code> group.
	 * 
	 * @param index
	 *            Task index.
	 * @return Task at the specified index.
	 */
	public RibbonTask getTask(int index) {
		return this.tasks.get(index);
	}

	/**
	 * Returns the name of this group.
	 * 
	 * @return The name of this group.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns the hue color for this group.
	 * 
	 * @return The hue color for this group.
	 */
	public Color getHueColor() {
		return this.hueColor;
	}

	/**
	 * Changes the title of this ribbon contextual task group. 
	 * 
	 * @param title
	 *            The new title for this ribbon contextual task group.
	 */
	public void setTitle(String title) {
		this.title = title;
		if (this.ribbon != null)
			this.ribbon.fireStateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getTitle() + " (" + getTaskCount() + " tasks)";
	}
	
	void setRibbon(JRibbon ribbon) {
		if (this.ribbon != null) {
			throw new IllegalStateException(
					"The contextual task group already belongs to another ribbon");
		}
		this.ribbon = ribbon;
	}

}
