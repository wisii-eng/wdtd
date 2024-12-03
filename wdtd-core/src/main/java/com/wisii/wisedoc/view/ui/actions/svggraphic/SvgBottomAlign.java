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
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.document.svg.SVGUtil;

@SuppressWarnings("serial")
public class SvgBottomAlign extends SvgAlign
{

	public FixedLength getSvgContainerLimit(Element current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		FixedLength left = (FixedLength) currentattribute.get(Constants.PR_TOP);
		FixedLength width = getFixefLength((LengthRangeProperty) currentattribute
				.get(Constants.PR_BLOCK_PROGRESSION_DIMENSION));
		FixedLength right = SVGUtil.getSumLength(left, width);
		return right;
	}

	public FixedLength getSvgContainerLimit(List<CellElement> selects)
	{
		FixedLength xmax = null;
		for (CellElement current : selects)
		{
			FixedLength currentLength = getSvgContainerLimit(current
					.getParent());
			if (xmax == null)
			{
				xmax = currentLength;
			} else if (SVGUtil.inRightBottomSide(xmax, currentLength))
			{
				xmax = currentLength;
			}
		}
		return xmax;
	}

	public FixedLength getCanvasLimit(List<CellElement> selects)
	{
		FixedLength xmax = null;
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
			if (xmax == null)
			{
				xmax = currentx;
			} else if (SVGUtil.inRightBottomSide(xmax, currentx))
			{
				xmax = currentx;
			}
		}
		return xmax;
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
			currentx = SVGUtil.inRightBottomSide(x1, x2) ? x2 : x1;
		}
		return currentx;
	}

	public FixedLength getRectangleLimit(CellElement current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		FixedLength currentx = null;
		if (currentattribute != null)
		{
			FixedLength x = (FixedLength) currentattribute.get(Constants.PR_Y);
			FixedLength width = (FixedLength) currentattribute
					.get(Constants.PR_HEIGHT);
			currentx = SVGUtil.getSumLength(x, width);
		}
		return currentx;
	}
	public void setSvgContainerLimit(CellElement current, FixedLength newvalue)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			FixedLength width = getFixefLength((LengthRangeProperty) currentattribute
					.get(Constants.PR_BLOCK_PROGRESSION_DIMENSION));
			setAttribute(current, Constants.PR_TOP, SVGUtil.getSubLength(
					newvalue, width));
		}
	}

	public void setLineLimit(CellElement current, FixedLength newposition)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			FixedLength currentx = null;
			FixedLength x1 = (FixedLength) currentattribute
					.get(Constants.PR_Y1);
			FixedLength x2 = (FixedLength) currentattribute
					.get(Constants.PR_Y2);
			currentx = SVGUtil.inRightBottomSide(x1, x2) ? x2 : x1;
			FixedLength move = SVGUtil.getSubLength(newposition, currentx);
			setAttribute(current, Constants.PR_Y1, SVGUtil.getSumLength(x1,
					move));
			setAttribute(current, Constants.PR_Y2, SVGUtil.getSumLength(x2,
					move));
		}
	}

	public void setRectangleLimit(CellElement current, FixedLength newposition)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			FixedLength width = (FixedLength) currentattribute
					.get(Constants.PR_HEIGHT);
			setAttribute(current, Constants.PR_Y, SVGUtil.getSubLength(
					newposition, width));
		}
	}
}
