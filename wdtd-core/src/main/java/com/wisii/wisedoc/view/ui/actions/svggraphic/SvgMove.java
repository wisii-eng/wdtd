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

import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.Ellipse;
import com.wisii.wisedoc.document.svg.Line;
import com.wisii.wisedoc.document.svg.Rectangle;
import com.wisii.wisedoc.document.svg.SVGUtil;
import com.wisii.wisedoc.view.SVGLocationConvert;
import com.wisii.wisedoc.view.action.BaseAction;

@SuppressWarnings("serial")
public abstract class SvgMove extends BaseAction
{

	List<CellElement> selects;

	int selectType = 0;

	public void doAction(ActionEvent e)
	{
		FixedLength move = getMoveStep();
		boolean flg = false;
		int size = selects.size();
		if (selectType == SVGLocationConvert.SVGCONTAINER)
		{
			for (int i = 0; i < size; i++)
			{
				CellElement current = selects.get(i);
				if (!svgContainerOverFlow(current, move))
				{
					break;
				}
				if (i == size - 1)
				{
					flg = true;
				}
			}
			if (flg)
			{
				for (CellElement current : selects)
				{
					svgContainerMove(current, move);
				}
			}
		} else if (selectType == SVGLocationConvert.CANVAS)
		{
			for (int i = 0; i < size; i++)
			{
				CellElement current = selects.get(i);
				if (current instanceof Line)
				{
					if (!lineOverFlow(current, move))
					{
						break;
					}
				} else if (current instanceof Rectangle||current instanceof Ellipse)
				{
					if (!rectangleOverFlow(current, move))
					{
						break;
					}
				}
				if (i == size - 1)
				{
					flg = true;
				}
			}
			if (flg)
			{
				for (int i = 0; i < size; i++)
				{
					CellElement current = selects.get(i);

					if (current instanceof Line)
					{
						lineMove(current, move);
					} else if (current instanceof Rectangle||current instanceof Ellipse)
					{
						rectangleMove(current, move);
					}
				}
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

	public boolean lineOverFlow(CellElement current, FixedLength move)
	{
		return false;
	}

	public boolean svgContainerOverFlow(CellElement current, FixedLength move)
	{
		return false;
	}

	public boolean rectangleOverFlow(CellElement current, FixedLength move)
	{
		return false;
	}

	public void svgContainerMove(CellElement current, FixedLength move)
	{
	}

	public void lineMove(CellElement current, FixedLength move)
	{
	}

	public void rectangleMove(CellElement current, FixedLength move)
	{
	}

	public FixedLength getMoveStep()
	{
		String value = ConfigureUtil.getProperty("move");
		FixedLength step = null;
		if (value != null)
		{
			step = SVGUtil.createFixedLength(value);
		}
		if (step == null)
		{
			step = new FixedLength(5d, "pt");
		}
		return step;
	}

	public FixedLength getMinuteMoveStep()
	{
		String value = ConfigureUtil.getProperty("minutemove");
		FixedLength step = null;
		if (value != null)
		{
			step = SVGUtil.createFixedLength(value);
		}
		if (step == null)
		{
			step = new FixedLength(5d, "pt");
		}
		return step;
	}

	public CellElement getParentElement(CellElement current)
	{
		Element parent = current.getParent();

		if (parent instanceof Canvas)
		{
			parent = parent.getParent();
			if (parent instanceof Inline)
			{
				return (CellElement) parent;
			} else if (parent instanceof Block)
			{
				return (CellElement) parent.getParent();
			}
		}
		return null;
	}

	public void setAttribute(CellElement current, int key, Object value)
	{
		Map<Integer, Object> attrs = new HashMap<Integer, Object>();
		attrs.put(key, value);
		getCurrentDocument().setElementAttributes(current, attrs, false);
	}
}