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
 * @BlockContainer.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wisii.wisedoc.area.BlockViewport;
import com.wisii.wisedoc.document.attribute.CommonAbsolutePosition;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.document.datatype.Length;
/**
 * 类功能描述：BlockContainer类
 * 
 * 作者：zhangqiang 创建日期：2008-8-27
 */
public class BlockContainer extends DefaultElement implements Groupable
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
	public BlockContainer()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public BlockContainer(final Map<Integer, Object> attributes)
	{
		super(attributes);
		if (!(this instanceof TableContents))
		{
			Map<Integer, Object> map = new HashMap<Integer, Object>();
			/* 边框样式 */
			map.put(Constants.PR_BORDER_BEFORE_STYLE, new EnumProperty(
					Constants.EN_SOLID, "SOLID"));
			map.put(Constants.PR_BORDER_AFTER_STYLE, new EnumProperty(
					Constants.EN_SOLID, "SOLID"));
			map.put(Constants.PR_BORDER_START_STYLE, new EnumProperty(
					Constants.EN_SOLID, "SOLID"));
			map.put(Constants.PR_BORDER_END_STYLE, new EnumProperty(
					Constants.EN_SOLID, "SOLID"));
			/* 边框宽度 */
			map.put(Constants.PR_BORDER_BEFORE_WIDTH, new CondLengthProperty(
					new FixedLength(0.5d, "pt"), true));
			map.put(Constants.PR_BORDER_AFTER_WIDTH, new CondLengthProperty(
					new FixedLength(0.5d, "pt"), true));
			map.put(Constants.PR_BORDER_START_WIDTH, new CondLengthProperty(
					new FixedLength(0.5d, "pt"), true));
			map.put(Constants.PR_BORDER_END_WIDTH, new CondLengthProperty(
					new FixedLength(0.5d, "pt"), true));
			/* 边框颜色 */
			WiseDocColor color = new WiseDocColor(Color.BLACK);
			map.put(Constants.PR_BORDER_BEFORE_COLOR, color);
			map.put(Constants.PR_BORDER_AFTER_COLOR, color);
			map.put(Constants.PR_BORDER_START_COLOR, color);
			map.put(Constants.PR_BORDER_END_COLOR, color);
			map.put(Constants.PR_PADDING_START, new CondLengthProperty(
					new FixedLength(1.9d,
							"mm",2), false));
			map.put(Constants.PR_PADDING_END, new CondLengthProperty(
					new FixedLength(1.9d,
							"mm",2), false));
			EnumProperty isrelative = (EnumProperty) getAttribute(Constants.PR_ABSOLUTE_POSITION);
			setAttributes(map, false);
		}
		// super(attributes);
	}

	@Override
	public void initFOProperty()
	{
		// TODO Auto-generated method stub
		super.initFOProperty();
		if (commonmarginblock == null)
		{
			commonmarginblock = new CommonMarginBlock(this);
		} else
		{
			commonmarginblock.init(this);
		}
		if (commonabsposition == null)
		{
			commonabsposition = new CommonAbsolutePosition(this);
		} else
		{
			commonabsposition.init(this);
		}
		if (commonbpbackground == null)
		{
			commonbpbackground = new CommonBorderPaddingBackground(this);
		} else
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
		int padingheight = 0;
		CondLengthProperty padingbefore = (CondLengthProperty) getAttribute(Constants.PR_PADDING_BEFORE);
		if (padingbefore != null)
		{
			padingheight = padingbefore.getLengthValue();
		}
		CondLengthProperty padingafter = (CondLengthProperty) getAttribute(Constants.PR_PADDING_AFTER);
		if (padingafter != null)
		{
			padingheight = padingheight + padingafter.getLengthValue();
		}
		// 由于FO的pading属性石往外扩的，所以高度需要减去高度方向上的pading
		if (padingheight > 0)
		{
			LengthRangeProperty height = (LengthRangeProperty) getAttribute(Constants.PR_BLOCK_PROGRESSION_DIMENSION);
			LengthProperty optheight = height.getOptimum(null);
			//如果是固定长度，则减去相应的pading
			if (optheight instanceof FixedLength)
			{
				return new LengthRangeProperty(new FixedLength(optheight
						.getValue()
						- padingheight));
			}
			//如果是自动，则返回原来的值
			else
			{
				return height;
			}
		} else
		{
			return (LengthRangeProperty) getAttribute(Constants.PR_BLOCK_PROGRESSION_DIMENSION);
		}
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
		int padingwidth = 0;
		CondLengthProperty padingstart = (CondLengthProperty) getAttribute(Constants.PR_PADDING_START);
		if (padingstart != null)
		{
			padingwidth = padingstart.getLengthValue();
		}
		CondLengthProperty padingend = (CondLengthProperty) getAttribute(Constants.PR_PADDING_END);
		if (padingend != null)
		{
			padingwidth = padingwidth + padingend.getLengthValue();
		}
		// 由于FO的pading属性石往外扩的，所以宽度需要减去宽度方向上的pading
		if (padingwidth > 0)
		{
			LengthRangeProperty width = (LengthRangeProperty) getAttribute(Constants.PR_INLINE_PROGRESSION_DIMENSION);
			LengthProperty optwidth = width.getOptimum(null);
			//如果是固定长度，则减去相应的pading
			if (optwidth instanceof FixedLength)
			{
				return new LengthRangeProperty(new FixedLength(optwidth
						.getValue()
						- padingwidth));
			}
			//如果是自动，则直接返回原来的值
			else
			{
				return width;
			}

		} else
		{
			return (LengthRangeProperty) getAttribute(Constants.PR_INLINE_PROGRESSION_DIMENSION);
		}
	}

	/**
	 * @return the "overflow" property.
	 */
	public int getOverflow()
	{
		Object overflow = getAttribute(Constants.PR_OVERFLOW);
		return ((EnumProperty) overflow).getEnum();
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
		List<CellElement> list;
		if (_children != null && !_children.isEmpty())
		{
			list = _children;
		}
		// 如果block为空，则显示一换行符
		else
		{
			Block block = new Block();
			block.setParent(this);
			list = new ArrayList<CellElement>();
			list.add(block);

		}
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

	public boolean isGroupAble()
	{
		Object abspos = getAttribute(Constants.PR_ABSOLUTE_POSITION);
		if (abspos instanceof EnumProperty)
		{
			EnumProperty epabspos = (EnumProperty) abspos;
			if (epabspos.getEnum() == Constants.EN_RELATIVE)
			{
				return true;
			}
		}
		return false;
	}
}
