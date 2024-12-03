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

package com.wisii.wisedoc.view.ui.actions.svggraphic;

import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.document.svg.SVGUtil;

@SuppressWarnings("serial")
public class SvgTopAlign extends SvgAlign
{

	public FixedLength getSvgContainerLimit(Element current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		return currentattribute != null ? (FixedLength) currentattribute
				.get(Constants.PR_TOP) : null;
	}

	public FixedLength getSvgContainerLimit(List<CellElement> selects)
	{
		FixedLength xmin = null;
		for (CellElement current : selects)
		{
			FixedLength currentLength = getSvgContainerLimit(current.getParent());
			if (xmin == null)
			{
				xmin = currentLength;
			} else if (SVGUtil.inLeftTopSide(xmin, currentLength))
			{
				xmin = currentLength;
			}
		}
		return xmin;
	}

	public FixedLength getCanvasLimit(List<CellElement> selects)
	{
		FixedLength xmin = null;
		for (CellElement current : selects)
		{
			FixedLength currentx = null;
			if (current instanceof Line)
			{
				currentx = getLineLimit(current);

			} else if (current instanceof Rectangle||current instanceof Ellipse)
			{
				currentx = getRectangleLimit(current);
			} 
			if (xmin == null)
			{
				xmin = currentx;
			} else if (SVGUtil.inLeftTopSide(xmin, currentx))
			{
				xmin = currentx;
			}
		}
		return xmin;
	}

	public void setCanvasLimit(List<CellElement> selects, FixedLength newvalue)
	{
		for (CellElement current : selects)
		{
			if (current instanceof Line)
			{
				setLineLimit(current, newvalue);
			} else if (current instanceof Rectangle||current instanceof Ellipse)
			{
				setRectangleLimit(current, newvalue);
			} 
		}
	}

	public FixedLength getLineLimit(CellElement current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		FixedLength currentx = null;
		if (currentattribute != null)
		{
			FixedLength x1 = (FixedLength) currentattribute
					.get(Constants.PR_Y1);
			FixedLength x2 = (FixedLength) currentattribute
					.get(Constants.PR_Y2);
			currentx = SVGUtil.inLeftTopSide(x1, x2) ? x2 : x1;
		}
		return currentx;
	}

	public FixedLength getRectangleLimit(CellElement current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		return currentattribute != null ? (FixedLength) currentattribute
				.get(Constants.PR_Y) : null;
	}

	public void setSvgContainerLimit(CellElement current, FixedLength newvalue)
	{
		setAttribute(current,Constants.PR_TOP, newvalue);
	}

	public void setLineLimit(CellElement current, FixedLength newposition)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			FixedLength x1 = (FixedLength) currentattribute
					.get(Constants.PR_Y1);
			FixedLength x2 = (FixedLength) currentattribute
					.get(Constants.PR_Y2);
			FixedLength currentx = SVGUtil.inLeftTopSide(x1, x2) ? x2 : x1;
			FixedLength move = SVGUtil.getSubLength(currentx, newposition);
			setAttribute(current,Constants.PR_Y1, SVGUtil
					.getSubLength(x1, move));
			setAttribute(current,Constants.PR_Y2, SVGUtil
					.getSubLength(x2, move));
		}
	}

	public void setRectangleLimit(CellElement current, FixedLength newposition)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			setAttribute(current,Constants.PR_Y, newposition);
		}
	}
}
