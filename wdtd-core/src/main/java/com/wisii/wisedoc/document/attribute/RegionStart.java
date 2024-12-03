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
 * @RegionStart.java
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
public class RegionStart extends RegionSE
{

	/**
	 * 初始化过程的描述， 初始化左区域需要8个参数，按顺序分别是 1、左区域的高度 2、左区域的页布局 3、左区域margin属性
	 * 
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public RegionStart(Length extent, SimplePageMaster layoutMaster,
			CommonBorderPaddingBackground commonBorderPaddingBackground,
			int displayAlign, int overflow, String regionName,
			int referenceOrientation, int writingMode)
	{
		super(extent, layoutMaster, commonBorderPaddingBackground,
				displayAlign, overflow, regionName, referenceOrientation,
				writingMode);
		// TODO Auto-generated constructor stub
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
			neighbourContext = pageHeightContext;
			vpRect = new Rectangle(0, 0,
					getExtent().getValue(pageWidthContext), reldims.bpd);
		} else
		{
			neighbourContext = pageWidthContext;
			vpRect = new Rectangle(0, 0, reldims.bpd, getExtent().getValue(
					pageHeightContext));
		}
		adjustIPD(vpRect, spm.getWritingMode(), neighbourContext);
		return vpRect;
	}

	/**
	 * @see com.wisii.fov.fo.pagination.Region#getDefaultRegionName()
	 */
	protected String getDefaultRegionName()
	{
		return "xsl-region-start";
	}

	public int getNameId()
	{
		return Constants.FO_REGION_START;
	}

	public RegionStart clone()
	{
		// int precedence = getPrecedence();
		Length extent = getExtent().clone();
		// SimplePageMaster layoutMaster;
		CommonBorderPaddingBackground commonBorderPaddingBackground = this
				.getCommonBorderPaddingBackground().clone();
		int displayAlign = getDisplayAlign();
		int overflow = getOverflow();
		String regionName = new String(getRegionName());
		int referenceOrientation = getRegionReferenceOrientation();
		int writingMode = getRegionWritingMode();
		RegionStart region = new RegionStart(extent, null,
				commonBorderPaddingBackground, displayAlign, overflow,
				regionName, referenceOrientation, writingMode);
		return region;
	}
}
