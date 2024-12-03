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
 * @RegionBA.java
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
 * 作者：zhangqiang 创建日期：2008-8-26
 */
public abstract class RegionBA extends SideRegion
{
	private int _precedence = Constants.EN_FALSE;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public RegionBA(int precedence, Length extent,
			SimplePageMaster layoutMaster,
			CommonBorderPaddingBackground commonBorderPaddingBackground,
			int displayAlign, int overflow, String regionName,
			int referenceOrientation, int writingMode)
	{
		super(extent, layoutMaster, commonBorderPaddingBackground,
				displayAlign, overflow, regionName, referenceOrientation,
				writingMode);
		if (precedence == Constants.EN_TRUE || precedence == Constants.EN_FALSE)
		{
			_precedence = precedence;
		}
	}

	/**
	 * @return the "precedence" property.
	 */
	public int getPrecedence()
	{
		return _precedence;
	}

	/**
	 * Adjust the viewport reference rectangle for a region as a function of
	 * precedence. If precedence is false on a before or after region, its
	 * inline-progression-dimension is limited by the extent of the start and
	 * end regions if they are present.
	 * 
	 * @param vpRefRect
	 *            viewport reference rectangle
	 * @param wm
	 *            writing mode
	 * @param siblingContext
	 *            the context to use to resolve extent on siblings
	 */
	protected void adjustIPD(Rectangle vpRefRect, int wm,
			PercentBaseContext siblingContext)
	{
		int offset = 0;
		RegionStart start = (RegionStart) getSiblingRegion(Constants.FO_REGION_START);
		if (start != null)
		{
			offset = start.getExtent().getValue(siblingContext);
			vpRefRect.translate(offset, 0); // move (x, y) units
		}
		RegionEnd end = (RegionEnd) getSiblingRegion(Constants.FO_REGION_END);
		if (end != null)
		{
			offset += end.getExtent().getValue(siblingContext);
		}
		if (offset > 0)
		{
			if (wm == Constants.EN_LR_TB || wm == Constants.EN_RL_TB)
			{
				vpRefRect.width -= offset;
			} else
			{
				vpRefRect.height -= offset;
			}
		}
	}

}
