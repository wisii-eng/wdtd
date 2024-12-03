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
 * @AbstractGraphics.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.area.inline.Viewport;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-29
 */
public abstract class AbstractGraphics extends DefaultElement
{
	/* 【添加：START】by 李晓光 2008-10-27 */
	private Viewport area = null;
//	边框，背景，padding等属性
	private CommonBorderPaddingBackground cbpbg;

	public Viewport getArea()
	{
		return area;
	}

	public void setArea(Viewport area)
	{
		this.area = area;
	}

	/* 【添加：END】by 李晓光 2008-10-27 */
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public AbstractGraphics()
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
	public AbstractGraphics(Map<Integer, Object> attributes)
	{
		super(attributes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initFOProperty()
	{
		super.initFOProperty();
		if (cbpbg == null)
		{
			cbpbg = new CommonBorderPaddingBackground(this);
		} else
		{
			cbpbg.init(this);
		}
	}

	/**
	 * Given the ipd and the content width calculates the required x offset
	 * based on the text-align property
	 * 
	 * @param ipd
	 *            the inline-progression-dimension of the object
	 * @param cwidth
	 *            the calculated content width of the object
	 * @return the X offset
	 */
	public int computeXOffset(int ipd, int cwidth)
	{
		int xoffset = 0;
		EnumProperty pro = (EnumProperty) getAttribute(Constants.PR_TEXT_ALIGN);
		int textAlign = pro.getEnum();
		switch (textAlign)
		{
		case Constants.EN_CENTER:
			xoffset = (ipd - cwidth) / 2;
			break;
		case Constants.EN_END:
			xoffset = ipd - cwidth;
			break;
		case Constants.EN_START:
			break;
		case Constants.EN_JUSTIFY:
		default:
			break;
		}
		return xoffset;
	}

	/**
	 * Given the bpd and the content height calculates the required y offset
	 * based on the display-align property
	 * 
	 * @param bpd
	 *            the block-progression-dimension of the object
	 * @param cheight
	 *            the calculated content height of the object
	 * @return the Y offset
	 */
	public int computeYOffset(int bpd, int cheight)
	{
		int yoffset = 0;
		int displayAlign = ((EnumProperty) getAttribute(Constants.PR_DISPLAY_ALIGN))
				.getEnum();
		switch (displayAlign)
		{
		case Constants.EN_BEFORE:
			break;
		case Constants.EN_AFTER:
			yoffset = bpd - cheight;
			break;
		case Constants.EN_CENTER:
			yoffset = (bpd - cheight) / 2;
			break;
		case Constants.EN_AUTO:
		default:
			break;
		}
		return yoffset;
	}

	/**
	 * @return the Common Border, Padding, and Background Properties.
	 */
	public CommonBorderPaddingBackground getCommonBorderPaddingBackground()
	{
		return cbpbg;
	}

	/**
	 * @return the "line-height" property.
	 */
	public SpaceProperty getLineHeight()
	{
		return (SpaceProperty) getAttribute(Constants.PR_LINE_HEIGHT);
	}

	/**
	 * @return the "inline-progression-dimension" property.
	 */
	public LengthRangeProperty getInlineProgressionDimension()
	{
		return (LengthRangeProperty) getAttribute(Constants.PR_INLINE_PROGRESSION_DIMENSION);
	}

	/**
	 * @return the "block-progression-dimension" property.
	 */
	public LengthRangeProperty getBlockProgressionDimension()
	{
		return (LengthRangeProperty) getAttribute(Constants.PR_BLOCK_PROGRESSION_DIMENSION);
	}

	/**
	 * @return the "height" property.
	 */
	public Length getHeight()
	{
		return (Length) getAttribute(Constants.PR_HEIGHT);
	}

	/**
	 * @return the "width" property.
	 */
	public Length getWidth()
	{
		return (Length) getAttribute(Constants.PR_WIDTH);
	}

	/**
	 * @return the "content-height" property.
	 */
	public Length getContentHeight()
	{
		return (Length) getAttribute(Constants.PR_CONTENT_HEIGHT);
	}

	/**
	 * @return the "content-width" property.
	 */
	public Length getContentWidth()
	{
		return (Length) getAttribute(Constants.PR_CONTENT_WIDTH);
	}

	/**
	 * @return the "scaling" property.
	 */
	public int getScaling()
	{
		return ((EnumProperty) getAttribute(Constants.PR_SCALING)).getEnum();
	}

	/**
	 * @return the "overflow" property.
	 */
	public int getOverflow()
	{
		return ((EnumProperty) getAttribute(Constants.PR_OVERFLOW)).getEnum();
	}

	/**
	 * @return the "alignment-adjust" property
	 */
	public Length getAlignmentAdjust()
	{
		return (Length) getAttribute(Constants.PR_ALIGNMENT_ADJUST);
	}

	/**
	 * @return the "alignment-baseline" property
	 */
	public int getAlignmentBaseline()
	{
		return ((EnumProperty) getAttribute(Constants.PR_ALIGNMENT_BASELINE))
				.getEnum();
	}

	/**
	 * @return the "baseline-shift" property
	 */
	public Length getBaselineShift()
	{
		return (Length) getAttribute(Constants.PR_BASELINE_SHIFT);
	}

	/**
	 * @return the "dominant-baseline" property
	 */
	public int getDominantBaseline()
	{
		return ((EnumProperty) getAttribute(Constants.PR_DOMINANT_BASELINE))
				.getEnum();
	}

	/**
	 * 
	 * 判断是否可以添加子对象 如果对子对象的类型等有要求，需要在子类中进行覆写
	 * 
	 * @param newChild
	 *            ：要添加的子对象
	 * @return 是否可添加该子对象的布尔值
	 * @exception
	 */
	public boolean iscanadd(CellElement newChild)
	{
		return false;
	}

	/**
	 * 
	 * 判断是否可以在指定位置添加子对象 对插入对象有特殊要求，可覆写该方法
	 * 
	 * @param newChild
	 *            ：要插入的子对象 childIndex：要插入的位置
	 * @return 是否可在指定位置添加子对象的布尔值
	 * @exception
	 */
	public boolean iscaninsert(CellElement newChild, int childIndex)
	{
		return false;
	}

	/**
	 * 
	 * 判断是否可以在指定位置添加子对象 对插入对象有特殊要求，可覆写该方法
	 * 
	 * @param newChild
	 *            ：要插入的子对象 childIndex：要插入的位置
	 * @return 是否可在指定位置添加子对象的布尔值
	 * @exception
	 */
	public boolean iscaninsert(List<CellElement> children, int childIndex)
	{
		return false;
	}

	/**
	 * 
	 * 是否可删除某个子对象
	 * 
	 * 
	 * @param child
	 *            ：要删除的子对象
	 * @return 是否可删除该子对象的布尔值
	 * @exception
	 */
	public boolean iscanRemove(CellElement child)
	{
		return false;
	}

	/**
	 * 
	 * 是否可删除指定的子对象集合
	 * 
	 * 
	 * @param children
	 *            ：要删除的子对象集合
	 * @return true
	 * @exception
	 */
	public boolean iscanRemove(List<CellElement> children)
	{
		return false;
	}

	/**
	 * 
	 * 是否可删除所有的子对象
	 * 
	 * @param
	 * @return true表示可删除所有的子对象，false表示不能删除
	 * @exception
	 */
	public boolean iscanremoveall()
	{
		return false;
	}

	/**
	 * @return the graphics intrinsic width
	 */
	public abstract int getIntrinsicWidth();

	/**
	 * @return the graphics intrinsic height
	 */
	public abstract int getIntrinsicHeight();
}
