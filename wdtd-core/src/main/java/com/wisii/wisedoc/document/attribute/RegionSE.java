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
 * @RegionSE.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.awt.Rectangle;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.PercentBaseContext;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-8-26
 */
public abstract class RegionSE extends SideRegion
{

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public RegionSE(Length extent, SimplePageMaster layoutMaster,
			CommonBorderPaddingBackground commonBorderPaddingBackground,
			int displayAlign, int overflow, String regionName,
			int referenceOrientation, int writingMode)
	{
		super(extent, layoutMaster, commonBorderPaddingBackground,
				displayAlign, overflow, regionName, referenceOrientation,
				writingMode);
	}

	/**
	 * Adjust the viewport reference rectangle for a region as a function
	 * of precedence.
	 * If  before and after have precedence = true, the start and end
	 * regions only go to the limits of their extents, otherwise
	 * they extend in the BPD to the page reference rectangle
	 * diminish by extend of start and end if present.
	 * @param vpRefRect viewport reference rectangle
	 * @param wm writing mode
	 * @param siblingContext the context to use to resolve extent on siblings
	 */
	protected void adjustIPD(Rectangle vpRefRect, int wm,
			PercentBaseContext siblingContext)
	{
		int offset = 0;
		RegionBefore before = (RegionBefore) getSiblingRegion(Constants.FO_REGION_BEFORE);
		if (before != null && before.getPrecedence() == Constants.EN_TRUE)
		{
			offset = before.getExtent().getValue(siblingContext);
			vpRefRect.translate(0, offset);
		}
		RegionAfter after = (RegionAfter) getSiblingRegion(Constants.FO_REGION_AFTER);
		if (after != null && after.getPrecedence() == Constants.EN_TRUE)
		{
			offset += after.getExtent().getValue(siblingContext);
		}
		if (offset > 0)
		{
			if (wm == Constants.EN_LR_TB || wm == Constants.EN_RL_TB)
			{
				vpRefRect.height -= offset;
			} else
			{
				vpRefRect.width -= offset;
			}
		}
	}
}
