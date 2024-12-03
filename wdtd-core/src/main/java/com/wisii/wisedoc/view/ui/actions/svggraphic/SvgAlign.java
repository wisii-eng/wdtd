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
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.view.SVGLocationConvert;
import com.wisii.wisedoc.view.action.BaseAction;

@SuppressWarnings("serial")
public abstract class SvgAlign extends BaseAction
{

	List<CellElement> selects;

	int selectType = 0;

	public void doAction(ActionEvent e)
	{
		if (selectType == SVGLocationConvert.SVGCONTAINER)
		{
			FixedLength currentLimit = getSvgContainerLimit(selects);
			setSvgContainerLimit(selects, currentLimit);
		} else if (selectType == SVGLocationConvert.CANVAS)
		{
			FixedLength currentLimit = getCanvasLimit(selects);
			setCanvasLimit(selects, currentLimit);
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

	public FixedLength getSvgContainerLimit(Element current)
	{
		return null;
	}

	public FixedLength getSvgContainerLimit(List<CellElement> selects)
	{
		return null;
	}

	public FixedLength getCanvasLimit(List<CellElement> selects)
	{
		return null;
	}

	public void setSvgContainerLimit(List<CellElement> selects,
			FixedLength newvalue)
	{
		for (CellElement current : selects)
		{
			setSvgContainerLimit((CellElement) current.getParent(), newvalue);
		}
	}

	public void setCanvasLimit(List<CellElement> selects, FixedLength newvalue)
	{
	}

	public FixedLength getLineLimit(CellElement current)
	{
		return null;
	}

	public FixedLength getRectangleLimit(CellElement current)
	{
		return null;
	}

	public void setSvgContainerLimit(CellElement current,
			FixedLength newposition)
	{
	}

	public void setLineLimit(CellElement current, FixedLength newposition)
	{
	}

	public void setRectangleLimit(CellElement current, FixedLength newposition)
	{
	}

	// public CellElement getParentElement(CellElement current)
	// {
	// Element parent = current.getParent();
	// if ((parent instanceof Canvas) || (parent instanceof SVGContainer))
	// {
	// return (CellElement) parent;
	// }
	// return null;
	// }

	public void setAttribute(CellElement current, int key, Object value)
	{
		Map<Integer, Object> attrs = new HashMap<Integer, Object>();
		attrs.put(key, value);
		getCurrentDocument().setElementAttributes(current, attrs, false);
	}

	public FixedLength getFixefLength(LengthRangeProperty rplength)
	{
		FixedLength length = new FixedLength(0d, "pt");
		if (rplength != null)
		{
			length = (FixedLength) rplength.getOptimum(null);
		}
		return length;
	}
}