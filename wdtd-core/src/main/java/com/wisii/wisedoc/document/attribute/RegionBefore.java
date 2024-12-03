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
 * @RegionBefore.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.awt.Rectangle;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.FODimension;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.document.datatype.SimplePercentBaseContext;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-26
 */
public class RegionBefore extends RegionBA
{

	/**
	 * 初始化过程的描述，初始化页眉需要9个参数，按顺序分别是 解释 | 默认值 1、页眉的优先级 | Constants.EN_FALSE 或者
	 * EnumNumber precedence = new EnumNumber(-1,0); 根据规范precedence的默认值为false
	 * 2、页眉的高度 | 一般为FixedLength类型的属性，比如：Length extent = new FixedLength(1d,
	 * "cm"); 3、页眉的页布局 | null 4、页眉的背景和边框属性 |
	 * null（根据FO规范region-body的border和padding必须为0） 5、页眉的基线位置 | Constants.EN_TOP
	 * 6、溢出处理 | Constants.EN_AUTO 7、页眉区域的名称 | null 8、页眉的referenceOrientation | 0
	 * 9、页眉的写作模式 | Constants.EN_LR_TB
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public RegionBefore(int precedence, Length extent,
			SimplePageMaster layoutMaster,
			CommonBorderPaddingBackground commonBorderPaddingBackground,
			int displayAlign, int overflow, String regionName,
			int referenceOrientation, int writingMode)
	{
		super(precedence, extent, layoutMaster, commonBorderPaddingBackground,
				displayAlign, overflow, regionName, referenceOrientation,
				writingMode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.attribute.Region#getDefaultRegionName()
	 */
	protected String getDefaultRegionName()
	{
		return "xsl-region-before";
	}

	/**
	 * @see com.wisii.fov.fo.pagination.Region#getViewportRectangle(FODimension,
	 *      SimplePageMaster)
	 */
	public Rectangle getViewportRectangle(FODimension reldims,
			SimplePageMaster spm)
	{
		/*
		 * Special rules apply to resolving extent as values are resolved
		 * relative to the page size and reference orientation.
		 */
		SimplePercentBaseContext pageWidthContext;
		SimplePercentBaseContext pageHeightContext;
		if (spm.getReferenceOrientation() % 180 == 0)
		{
			pageWidthContext = new SimplePercentBaseContext(null,
					LengthBase.CUSTOM_BASE, spm.getPageWidth().getValue());
			pageHeightContext = new SimplePercentBaseContext(null,
					LengthBase.CUSTOM_BASE, spm.getPageHeight().getValue());
		} else
		{
			// invert width and height since top left are rotated by 90 (cl or
			// ccl)
			pageWidthContext = new SimplePercentBaseContext(null,
					LengthBase.CUSTOM_BASE, spm.getPageHeight().getValue());
			pageHeightContext = new SimplePercentBaseContext(null,
					LengthBase.CUSTOM_BASE, spm.getPageWidth().getValue());
		}
		SimplePercentBaseContext neighbourContext;
		Rectangle vpRect;
		if (spm.getWritingMode() == Constants.EN_LR_TB
				|| spm.getWritingMode() == Constants.EN_RL_TB)
		{
			neighbourContext = pageWidthContext;
			vpRect = new Rectangle(0, 0, reldims.ipd, getExtent().getValue(
					pageHeightContext));
		} else
		{
			neighbourContext = pageHeightContext;
			vpRect = new Rectangle(0, 0,
					getExtent().getValue(pageWidthContext), reldims.ipd);
		}
		if (getPrecedence() == Constants.EN_FALSE)
		{
			adjustIPD(vpRect, spm.getWritingMode(), neighbourContext);
		}
		return vpRect;
	}

	public int getNameId()
	{
		return Constants.FO_REGION_BEFORE;
	}

	public RegionBefore clone()
	{
		int precedence = getPrecedence();
		Length extent = getExtent().clone();
		// SimplePageMaster layoutMaster;
		CommonBorderPaddingBackground commonBorderPaddingBackground = this
				.getCommonBorderPaddingBackground().clone();
		int displayAlign = getDisplayAlign();
		int overflow = getOverflow();
		String regionName = new String(getRegionName());
		int referenceOrientation = getRegionReferenceOrientation();
		int writingMode = getRegionWritingMode();
		RegionBefore region = new RegionBefore(precedence, extent, null,
				commonBorderPaddingBackground, displayAlign, overflow,
				regionName, referenceOrientation, writingMode);
		return region;
	}
}
