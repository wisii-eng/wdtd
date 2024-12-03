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
public class SvgHorizontalCenter extends SvgAlign
{

	public FixedLength getSvgContainerMin(Element current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		FixedLength left = (FixedLength) currentattribute
				.get(Constants.PR_LEFT);
		return left;
	}

	public FixedLength getSvgContainerMax(Element current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		FixedLength left = (FixedLength) currentattribute
				.get(Constants.PR_LEFT);
		FixedLength width = getFixefLength((LengthRangeProperty) currentattribute
				.get(Constants.PR_INLINE_PROGRESSION_DIMENSION));
		FixedLength right = SVGUtil.getSumLength(left, width);
		return right;
	}

	public FixedLength getSvgContainerLimit(List<CellElement> selects)
	{
		FixedLength xmin = null;
		FixedLength xmax = null;
		for (CellElement current : selects)
		{
			FixedLength currentmin = getSvgContainerMin(current.getParent());
			FixedLength currentmax = getSvgContainerMax(current.getParent());
			if (xmin == null)
			{
				xmin = currentmin;
			} else if (SVGUtil.inLeftTopSide(xmin, currentmin))
			{
				xmin = currentmin;
			}
			if (xmax == null)
			{
				xmax = currentmax;
			} else if (SVGUtil.inRightBottomSide(xmin, currentmax))
			{
				xmax = currentmax;
			}
		}
		return SVGUtil.getHalfOfSumLength(xmin, xmax);
	}

	public FixedLength getCanvasLimit(List<CellElement> selects)
	{
		FixedLength xmin = null;
		FixedLength xmax = null;
		for (CellElement current : selects)
		{
			FixedLength currentmin = null;
			FixedLength currentmax = null;
			if (current instanceof Line)
			{
				currentmin = getLineMin(current);
				currentmax = getLineMax(current);
			} else if (current instanceof Rectangle||current instanceof Ellipse)
			{
				currentmin = getRectangleMin(current);
				currentmax = getRectangleMax(current);
			} 
			if (xmin == null)
			{
				xmin = currentmin;
			} else if (SVGUtil.inLeftTopSide(xmin, currentmin))
			{
				xmin = currentmin;
			}
			if (xmax == null)
			{
				xmax = currentmax;
			} else if (SVGUtil.inLeftTopSide(xmin, currentmax))
			{
				xmax = currentmax;
			}
		}
		return SVGUtil.getHalfOfSumLength(xmin, xmax);
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

	public FixedLength getLineMin(CellElement current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		FixedLength currentx = null;
		if (currentattribute != null)
		{
			FixedLength x1 = (FixedLength) currentattribute
					.get(Constants.PR_X1);
			FixedLength x2 = (FixedLength) currentattribute
					.get(Constants.PR_X2);
			currentx = SVGUtil.inLeftTopSide(x1, x2) ? x2 : x1;
		}
		return currentx;
	}

	public FixedLength getLineMax(CellElement current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		FixedLength currentx = null;
		if (currentattribute != null)
		{
			FixedLength x1 = (FixedLength) currentattribute
					.get(Constants.PR_X1);
			FixedLength x2 = (FixedLength) currentattribute
					.get(Constants.PR_X2);
			currentx = SVGUtil.inRightBottomSide(x1, x2) ? x2 : x1;
		}
		return currentx;
	}

	public FixedLength getRectangleMin(CellElement current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		return currentattribute != null ? (FixedLength) currentattribute
				.get(Constants.PR_X) : null;
	}

	public FixedLength getRectangleMax(CellElement current)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		FixedLength x = (FixedLength) currentattribute.get(Constants.PR_X);
		FixedLength width = (FixedLength) currentattribute
				.get(Constants.PR_WIDTH);
		return SVGUtil.getSumLength(x, width);
	}
	public void setSvgContainerLimit(CellElement current, FixedLength newvalue)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		FixedLength width = getFixefLength((LengthRangeProperty) currentattribute
				.get(Constants.PR_INLINE_PROGRESSION_DIMENSION));
		setAttribute(current, Constants.PR_LEFT, SVGUtil.getSubHalfLength(
				newvalue, width));
	}

	public void setLineLimit(CellElement current, FixedLength newposition)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			FixedLength x1 = (FixedLength) currentattribute
					.get(Constants.PR_X1);
			FixedLength x2 = (FixedLength) currentattribute
					.get(Constants.PR_X2);
			setAttribute(current, Constants.PR_X1, SVGUtil.getSumHalfLength(
					newposition, SVGUtil.getSubLength(x2, x1)));
			setAttribute(current, Constants.PR_X2, SVGUtil.getSumHalfLength(
					newposition, SVGUtil.getSubLength(x1, x2)));
		}
	}

	public void setRectangleLimit(CellElement current, FixedLength newposition)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			FixedLength width = (FixedLength) currentattribute
					.get(Constants.PR_WIDTH);
			setAttribute(current, Constants.PR_X, SVGUtil.getSubHalfLength(
					newposition, width));
		}
	}
}
