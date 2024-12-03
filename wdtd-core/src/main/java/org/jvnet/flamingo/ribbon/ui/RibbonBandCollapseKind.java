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
package org.jvnet.flamingo.ribbon.ui;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import org.jvnet.flamingo.ribbon.RibbonElementPriority;

/**
 * Collapse kinds. Used during the layout to decide the display state of
 * each band component.
 * 
 * @author Kirill Grouchnikov
 */
public enum RibbonBandCollapseKind {
	/**
	 * No collapse.
	 */
	NONE(0),

	/**
	 * {@link RibbonElementPriority#TOP} is shown as
	 * {@link GalleryElementState#BIG},
	 * {@link RibbonElementPriority#MEDIUM} is shown as
	 * {@link GalleryElementState#BIG}, {@link RibbonElementPriority#LOW}
	 * is shown as {@link GalleryElementState#MEDIUM}.
	 */
	LOW_TO_MID(1),

	/**
	 * {@link RibbonElementPriority#TOP} is shown as
	 * {@link GalleryElementState#BIG},
	 * {@link RibbonElementPriority#MEDIUM} is shown as
	 * {@link GalleryElementState#MEDIUM},
	 * {@link RibbonElementPriority#LOW} is shown as
	 * {@link GalleryElementState#MEDIUM}.
	 */
	MID_TO_MID(2),

	/**
	 * {@link RibbonElementPriority#TOP} is shown as
	 * {@link GalleryElementState#BIG},
	 * {@link RibbonElementPriority#MEDIUM} is shown as
	 * {@link GalleryElementState#MEDIUM},
	 * {@link RibbonElementPriority#LOW} is shown as
	 * {@link GalleryElementState#SMALL}.
	 */
	LOW_TO_LOW(3),

	/**
	 * {@link RibbonElementPriority#TOP} is shown as
	 * {@link GalleryElementState#BIG},
	 * {@link RibbonElementPriority#MEDIUM} is shown as
	 * {@link GalleryElementState#SMALL}, {@link RibbonElementPriority#LOW}
	 * is shown as {@link GalleryElementState#SMALL}.
	 */
	MID_TO_LOW(4),

	/**
	 * {@link RibbonElementPriority#TOP} is shown as
	 * {@link RibbonElementPriority#MEDIUM},
	 * {@link RibbonElementPriority#MEDIUM} is shown as
	 * {@link RibbonElementPriority#LOW}, {@link RibbonElementPriority#LOW}
	 * is shown as {@link GalleryElementState#SMALL}.
	 */
	HIGH_TO_MID(5),

	/**
	 * {@link RibbonElementPriority#TOP} is shown as
	 * {@link GalleryElementState#SMALL},
	 * {@link RibbonElementPriority#MEDIUM} is shown as
	 * {@link GalleryElementState#SMALL}, {@link RibbonElementPriority#LOW}
	 * is shown as {@link GalleryElementState#SMALL}.
	 */
	HIGH_TO_LOW(6),

	/**
	 * The entire ribbon band is iconified.
	 */
	ICON(7);

	/**
	 * Priority.
	 */
	private int priority;

	/**
	 * Creates new collapse kind.
	 * 
	 * @param priority
	 *            Priority.
	 */
	RibbonBandCollapseKind(int priority) {
		this.priority = priority;
	}

	/**
	 * Returns the list of collapse kinds sorted by priority.
	 * 
	 * @return List of collapse kinds sorted by priority.
	 */
	public static LinkedList<RibbonBandCollapseKind> getSortedKinds() {
		LinkedList<RibbonBandCollapseKind> result = new LinkedList<RibbonBandCollapseKind>();
		for (RibbonBandCollapseKind kind : RibbonBandCollapseKind.values())
			result.add(kind);
		Collections.sort(result, new Comparator<RibbonBandCollapseKind>() {
			public int compare(RibbonBandCollapseKind o1,
					RibbonBandCollapseKind o2) {
				return o1.priority - o2.priority;
			}
		});
		return result;
	}
}