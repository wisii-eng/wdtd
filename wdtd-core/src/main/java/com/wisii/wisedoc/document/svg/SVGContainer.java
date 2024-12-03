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
/**
 * @SVGContainer.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.svg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wisii.wisedoc.area.BlockViewport;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DefaultElement;
import com.wisii.wisedoc.document.attribute.CommonAbsolutePosition;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：放绝对位置图形对象的容器
 *
 * 作者：zhangqiang
 * 创建日期：2009-2-26
 */
public class SVGContainer extends DefaultElement
{
	/* 【添加：START】by zhangqiang 2008-11-05 */
	private BlockViewport area = null;
	private CommonAbsolutePosition commonabsposition;
	private CommonBorderPaddingBackground commonbpbackground;
	private CommonMarginBlock commonmarginblock;
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public SVGContainer()
	{
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public SVGContainer(final Map<Integer,Object> attributes)
	{
		super(attributes);
	}
	 @Override
	    public void initFOProperty()
	    {
	    	// TODO Auto-generated method stub
	    	super.initFOProperty();
	    	if(commonmarginblock == null)
			{
				commonmarginblock = new CommonMarginBlock(this);
			}
			else
			{
				commonmarginblock.init(this);
			}
	    	if(commonabsposition == null)
			{
	    		commonabsposition = new CommonAbsolutePosition(this);
			}
			else
			{
				commonabsposition.init(this);
			}
	    	if(commonbpbackground == null)
			{
	    		commonbpbackground = new CommonBorderPaddingBackground(this);
			}
			else
			{
				commonbpbackground.init(this);
			}
	    }
	public int getNameId()
	{
		return Constants.FO_BLOCK_CONTAINER;
	}

	/**
	 * @return true (BlockContainer can generate Reference Areas)
	 */
	public boolean generatesReferenceAreas()
	{
		return true;
	}

	/**
	 * @return the Common Absolute Position Properties.
	 */
	public CommonAbsolutePosition getCommonAbsolutePosition()
	{
		return commonabsposition;
	}

	/**
	 * @return the Common Margin Properties-Block.
	 */
	public CommonMarginBlock getCommonMarginBlock()
	{
		return commonmarginblock;
	}

	/**
	 * @return the Common Border, Padding, and Background Properties.
	 */
	public CommonBorderPaddingBackground getCommonBorderPaddingBackground()
	{
		return commonbpbackground;
	}

	/**
	 * @return the "block-progression-dimension" property.
	 */
	public LengthRangeProperty getBlockProgressionDimension()
	{
		return (LengthRangeProperty) getAttribute(Constants.PR_BLOCK_PROGRESSION_DIMENSION);
	}

	/** @return the display-align property. */
	public int getDisplayAlign()
	{
		return ((EnumProperty) getAttribute(Constants.PR_DISPLAY_ALIGN))
				.getEnum();
	}

	/** @return the "break-after" property. */
	public int getBreakAfter()
	{
		return ((EnumProperty) getAttribute(Constants.PR_BREAK_AFTER))
				.getEnum();
	}

	/** @return the "break-before" property. */
	public int getBreakBefore()
	{
		return ((EnumProperty) getAttribute(Constants.PR_BREAK_BEFORE))
				.getEnum();
	}

	/** @return the "keep-with-next" property. */
	public KeepProperty getKeepWithNext()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_WITH_NEXT);
	}

	/** @return the "keep-with-previous" property. */
	public KeepProperty getKeepWithPrevious()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_WITH_PREVIOUS);
	}

	/** @return the "keep-together" property. */
	public KeepProperty getKeepTogether()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_TOGETHER);
	}

	/**
	 * @return the "inline-progression-dimension" property.
	 */
	public LengthRangeProperty getInlineProgressionDimension()
	{
		return (LengthRangeProperty) getAttribute(Constants.PR_INLINE_PROGRESSION_DIMENSION);
	}

	/**
	 * @return the "overflow" property.
	 */
	public int getOverflow()
	{
		return ((EnumProperty) getAttribute(Constants.PR_OVERFLOW)).getEnum();
	}

	/**
	 * @return the "reference-orientation" property.
	 */
	public int getReferenceOrientation()
	{
		return (Integer) getAttribute(Constants.PR_REFERENCE_ORIENTATION);
	}

	/**
	 * @return the "span" property.
	 */
	public int getSpan()
	{
		return ((EnumProperty) getAttribute(Constants.PR_SPAN)).getEnum();
	}

	/**
	 * @return the "writing-mode" property.
	 */
	public int getWritingMode()
	{
		return ((EnumProperty) getAttribute(Constants.PR_WRITING_MODE))
				.getEnum();
	}

	/**
	 * @return the width property
	 */
	public Length getWidth()
	{
		return (Length) getAttribute(Constants.PR_WIDTH);
	}

	/**
	 * @return the height property
	 */
	public Length getHeight()
	{
		return (Length) getAttribute(Constants.PR_HEIGHT);
	}

	/** @see com.wisii.fov.fo.FONode#getLocalName() */
	public String getLocalName()
	{
		return "block-container";
	}

	public ListIterator getChildNodes()
	{
		List<CellElement> list = new ArrayList<CellElement>();
		Block block = new Block();
		block.setParent(this);
		Map<Integer, Object> canvasatt = new HashMap<Integer, Object>();
		FixedLength width = (FixedLength) ((LengthRangeProperty) getInlineProgressionDimension())
				.getOptimum(null);
		FixedLength height = (FixedLength) ((LengthRangeProperty) getBlockProgressionDimension())
				.getOptimum(null);
		List<CellElement> children = getAllChildren();
		if (children == null || children.isEmpty())
		{
			return list.listIterator();
		}
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
			width = new FixedLength(width.getValue() + linewidth.getValue()
					+ 2000, 3);
			height = new FixedLength(height.getValue() + linewidth.getValue()
					+ 2000, 3);
		}
		canvasatt.put(Constants.PR_WIDTH, width);
		canvasatt.put(Constants.PR_HEIGHT, height);
		Integer layer = (Integer) getAttribute(Constants.PR_GRAPHIC_LAYER);
		if(layer!=null)
		{
			canvasatt.put(Constants.PR_GRAPHIC_LAYER,layer);
		}
		Canvas canvas = new Canvas(canvasatt);
		canvas.insert(children, 0);
		block.add(canvas);
//		将这些对象的父对象回设回去
		for (int i = 0; i < size; i++)
		{
			children.get(i).setParent(this);
		}
		list.add(block);
		return list.listIterator();
	}

	public BlockViewport getArea()
	{
		return area;
	}

	public void setArea(BlockViewport area)
	{
		this.area = area;
	}
}
