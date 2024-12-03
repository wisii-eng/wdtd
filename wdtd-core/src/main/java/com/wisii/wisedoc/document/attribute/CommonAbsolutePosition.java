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
/* $Id: CommonAbsolutePosition.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.document.attribute;

import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.svg.SVGContainer;

/**
 * Store all common absolute position properties. See Sec. 7.5 of the XSL-FO
 * Standard. Public "structure" allows direct member access.
 */
public final class CommonAbsolutePosition extends AbstractCommonAttributes
{
	private Length top;
	private Length left;

	/**
	 * Create a CommonAbsolutePosition object.
	 * 
	 * @param pList
	 *            The PropertyList with propery values.
	 */
	public CommonAbsolutePosition(CellElement cellelement)
	{
		super(cellelement);
		init(cellelement);
	
	}
    public void init(CellElement cellelement)
    {
		super.init(cellelement);
		top=null;
		left = null;
		if (cellelement instanceof SVGContainer)
		{
			List<CellElement> children = ((SVGContainer) cellelement)
					.getAllChildren();
			if (children != null && !children.isEmpty())
			{
				// 线条的粗细
				FixedLength linewidth = null;
				int size = children.size();
				for (int i = 0; i < size; i++)
				{
					CellElement child = children.get(i);
					FixedLength newlinewidth = (FixedLength) child
							.getAttribute(Constants.PR_STROKE_WIDTH);
					if (newlinewidth != null)
					{
						if (linewidth == null)
						{
							linewidth = newlinewidth;
						} else
						{
							if (linewidth.getValue() < newlinewidth.getValue())
							{
								linewidth = newlinewidth;
							}
						}
					}
				}
				if (linewidth != null)
				{
					Length oldtop = (Length) getAttribute(Constants.PR_TOP);
					Length oldleft = (Length) getAttribute(Constants.PR_LEFT);
					top = new FixedLength(((FixedLength) oldtop).getValue()
							- Math.round(linewidth.getValue() / 2.0f), 3);
					left = new FixedLength(((FixedLength) oldleft).getValue()
							- Math.round(linewidth.getValue() / 2.0f), 3);
				}
			}
		}
	}
	/**
	 * @返回 absolutePosition变量的值
	 */
	public final int getAbsolutePosition()
	{
		return ((EnumProperty) getAttribute(Constants.PR_ABSOLUTE_POSITION))
				.getEnum();
	}

	/**
	 * @返回 top变量的值
	 */
	public final Length getTop()
	{
		if (top == null)
		{
			top = (Length) getAttribute(Constants.PR_TOP);
		}
		return top;
	}

	/**
	 * @返回 right变量的值
	 */
	public final Length getRight()
	{
		return (Length) getAttribute(Constants.PR_RIGHT);
	}

	/**
	 * @返回 bottom变量的值
	 */
	public final Length getBottom()
	{
		return (Length) getAttribute(Constants.PR_BOTTOM);
	}

	/**
	 * @返回 left变量的值
	 */
	public final Length getLeft()
	{
		if (left == null)
		{
			left = (Length) getAttribute(Constants.PR_LEFT);
		}
		return left;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("CommonAbsolutePosition{");
		sb.append(" absPos=");
		sb.append(getAbsolutePosition());
		sb.append(" top=");
		sb.append(getTop());
		sb.append(" bottom=");
		sb.append(getBottom());
		sb.append(" left=");
		sb.append(getLeft());
		sb.append(" right=");
		sb.append(getRight());
		sb.append("}");
		return sb.toString();
	}
}
