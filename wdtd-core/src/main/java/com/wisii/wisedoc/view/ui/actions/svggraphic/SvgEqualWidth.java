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

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.document.svg.SVGUtil;
import com.wisii.wisedoc.view.SVGLocationConvert;
import com.wisii.wisedoc.view.action.BaseAction;

@SuppressWarnings("serial")
public class SvgEqualWidth extends BaseAction
{

	List<CellElement> selects;

	int selectType = 0;

	@Override
	public void doAction(ActionEvent e)
	{
		if (selectType == SVGLocationConvert.SVGCONTAINER
				|| selectType == SVGLocationConvert.CANVAS)
		{
			FixedLength length = getSvgObjectLength();
			if (length != null)
			{
				setSvgObjectLength(length);
			}
		}
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		selects = getObjectSelects();
		selectType = SVGLocationConvert.setSelectType(selects);
		return selectType != 0;
	}

	public FixedLength getSvgObjectLength()
	{
		FixedLength length = null;
		if (selects != null && selects.size() > 0)
		{
			CellElement first = selects.get(0);
			if (first instanceof Line)
			{
				length = getLineLength(first);
			} else if (first instanceof Rectangle||first instanceof Ellipse)
			{
				length = getRectangleLength(first);
			} 
		}
		return length;
	}

	public FixedLength getLineLength(CellElement current)
	{
		FixedLength length = null;
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			FixedLength x1 = (FixedLength) currentattribute
					.get(Constants.PR_X1);
			FixedLength x2 = (FixedLength) currentattribute
					.get(Constants.PR_X2);
			length = SVGUtil.getSubAbsLength(x1, x2);
		}
		return length;
	}

	public FixedLength getRectangleLength(CellElement current)
	{
		FixedLength length = null;
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			length = (FixedLength) currentattribute.get(Constants.PR_WIDTH);
		}
		return length;
	}

	public void setSvgObjectLength(FixedLength length)
	{
		if (selects != null && selects.size() > 1)
		{
			for (int i = 1; i < selects.size(); i++)
			{
				CellElement current = selects.get(i);
				if (current instanceof Line)
				{
					setLineLength(current, length);
				} else if (current instanceof Rectangle||current instanceof Ellipse)
				{
					setRectangleLength(current, length);
				}
			}
		}
	}

	public void setLineLength(CellElement current, FixedLength length)
	{
		Map<Integer, Object> currentattribute = current.getAttributes()
				.getAttributes();
		if (currentattribute != null)
		{
			FixedLength x1 = (FixedLength) currentattribute
					.get(Constants.PR_X1);
			FixedLength x2 = (FixedLength) currentattribute
					.get(Constants.PR_X2);
			if (SVGUtil.inLeftTopSide(x1, x2))
			{
				setAttribute(current, Constants.PR_X1, SVGUtil.getSumLength(x2,
						length));
			} else
			{
				setAttribute(current, Constants.PR_X2, SVGUtil.getSumLength(x1,
						length));
			}
		}
	}

	public void setRectangleLength(CellElement current, FixedLength length)
	{
		setAttribute(current, Constants.PR_WIDTH, length);
	}
	public void setAttribute(CellElement current, int key, Object value)
	{
		Map<Integer, Object> attrs = new HashMap<Integer, Object>();
		attrs.put(key, value);
		getCurrentDocument().setElementAttributes(current, attrs, false);
	}

}
