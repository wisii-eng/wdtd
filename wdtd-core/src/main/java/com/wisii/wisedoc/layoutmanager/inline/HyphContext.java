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
/* $Id: HyphContext.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.layoutmanager.inline;

import java.util.Arrays;

/**
 * This class is used to pass information to the getNextBreakPoss() method
 * concerning hyphenation. A reference to an instance of the class is contained
 * in the LayoutContext object passed to each LayoutManager. It contains
 * information concerning the hyphenation points in a word and the how many of
 * those have previously been processed by a Layout Manager to generate size
 * information.
 */
public class HyphContext implements Cloneable
{
	private int[] hyphPoints;
	private int currentOffset = 0;
	private int currentIndex = 0;

	public HyphContext(int[] hyphPoints)
	{
		this.hyphPoints = hyphPoints;
	}

	public int getNextHyphPoint()
	{
		for (; currentIndex < hyphPoints.length; currentIndex++)
		{
			if (hyphPoints[currentIndex] > currentOffset)
			{
				return (hyphPoints[currentIndex] - currentOffset);
			}
		}
		return -1; // AT END!
	}

	public boolean hasMoreHyphPoints()
	{
		for (; currentIndex < hyphPoints.length; currentIndex++)
		{
			if (hyphPoints[currentIndex] > currentOffset)
			{
				return true;
			}
		}
		return false;
	}

	public void updateOffset(int iCharsProcessed)
	{
		currentOffset += iCharsProcessed;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof HyphContext))
		{
			return false;
		}
		HyphContext hc = (HyphContext) obj;
		return currentOffset == hc.currentIndex
				&& currentIndex == hc.currentIndex
				&& (hyphPoints == null ? hc.hyphPoints == null : Arrays.equals(
						hyphPoints, hc.hyphPoints));
	}
    /**
     * @see java.lang.Object#clone()
     */
    public HyphContext clone() {
        try {
        	HyphContext hc = (HyphContext) super.clone();
        	hc.hyphPoints = hyphPoints;
        	return hc;
        } catch (CloneNotSupportedException cnse) {
            return null;
        }

    }
}
