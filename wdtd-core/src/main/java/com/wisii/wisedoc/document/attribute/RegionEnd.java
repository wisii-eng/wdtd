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
 * @RegionEnd.java
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
public class RegionEnd extends RegionSE
{

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public RegionEnd(Length extent, SimplePageMaster layoutMaster,
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
			vpRect = new Rectangle(reldims.ipd
					- getExtent().getValue(pageWidthContext), 0, getExtent()
					.getValue(pageWidthContext), reldims.bpd);
		} else
		{
			// Rectangle: x , y (of top left point), width, height
			neighbourContext = pageWidthContext;
			vpRect = new Rectangle(reldims.ipd
					- getExtent().getValue(pageHeightContext), 0, reldims.bpd,
					getExtent().getValue(pageHeightContext));
		}
		adjustIPD(vpRect, spm.getWritingMode(), neighbourContext);
		return vpRect;
	}

	/**
	 * @see com.wisii.fov.fo.pagination.Region#getDefaultRegionName()
	 */
	protected String getDefaultRegionName()
	{
		return "xsl-region-end";
	}

	public int getNameId()
	{
		return Constants.FO_REGION_END;
	}

	public RegionEnd clone()
	{
		Length extent = getExtent().clone();
		// SimplePageMaster layoutMaster;
		CommonBorderPaddingBackground commonBorderPaddingBackground = this
				.getCommonBorderPaddingBackground().clone();
		int displayAlign = getDisplayAlign();
		int overflow = getOverflow();
		String regionName = new String(getRegionName());
		int referenceOrientation = getRegionReferenceOrientation();
		int writingMode = getRegionWritingMode();
		RegionEnd region = new RegionEnd(extent, null,
				commonBorderPaddingBackground, displayAlign, overflow,
				regionName, referenceOrientation, writingMode);
		return region;
	}
}
