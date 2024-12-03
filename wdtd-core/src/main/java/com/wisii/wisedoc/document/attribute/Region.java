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
 * @Region.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.awt.Rectangle;
import java.util.Map;

import com.wisii.wisedoc.document.datatype.FODimension;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-21
 */
public abstract class Region
{

	// 边框属性
	private CommonBorderPaddingBackground _commonBorderPaddingBackground;

	// 对齐方式
	private int _displayAlign;

	// 溢出处理方式
	private int _overflow;

	// 旋转角度
	private int _referenceOrientation;

	// 书写模式
	private int _writingMode = -1;

	// 区域名称
	private String _regionName;

	// 页布局
	private SimplePageMaster _layoutMaster;

	public Region(SimplePageMaster layoutMaster,
			CommonBorderPaddingBackground commonBorderPaddingBackground,
			int displayAlign, int overflow, int referenceOrientation,
			int writingMode, String regionname)
	{
		_regionName = regionname;
		if (_regionName == null || _regionName.equals(""))
		{
			_regionName = getDefaultRegionName();
		}
		_layoutMaster = layoutMaster;
		_commonBorderPaddingBackground = commonBorderPaddingBackground;
		_displayAlign = displayAlign;
		_overflow = overflow;
		_referenceOrientation = referenceOrientation;
		if (writingMode > 0)
		{
			_writingMode = writingMode;
		}
	}

	public void setLayoutMaster(SimplePageMaster layoutMaster)
	{
		_layoutMaster = layoutMaster;
	}

	public SimplePageMaster getLayoutMaster()
	{
		return _layoutMaster;
	}

	public boolean generatesReferenceAreas()
	{
		return true;
	}

	/**
	 * @param pageRefRect
	 *            reference dimension of the page area.
	 * @param spm
	 *            the simple page master this region belongs to.
	 * @return the rectangle for the viewport area
	 */
	public abstract Rectangle getViewportRectangle(FODimension pageRefRect,
			SimplePageMaster spm);

	/**
	 * Returns the default region name (xsl-region-before, xsl-region-start,
	 * etc.)
	 * 
	 * @return the default region name
	 */
	protected abstract String getDefaultRegionName();

	/**
	 * Checks to see if a given region name is one of the reserved names
	 * 
	 * @param name
	 *            a region name to check
	 * @return true if the name parameter is a reserved region name
	 */
	protected boolean isReserved(String name) /* throws FOVException */
	{
		return (name.equals("xsl-region-before")
				|| name.equals("xsl-region-start")
				|| name.equals("xsl-region-end")
				|| name.equals("xsl-region-after")
				|| name.equals("xsl-before-float-separator") || name
				.equals("xsl-footnote-separator"));
	}

	/**
	 * Returns a sibling region for this region.
	 * 
	 * @param regionId
	 *            the Constants ID of the FO representing the region
	 * @return the requested region
	 */
	protected Region getSiblingRegion(int regionId)
	{
		// Ask parent for region
		return _layoutMaster.getRegion(regionId);
	}

	/**
	 * @return the Background Properties (border and padding are not used here).
	 */
	public CommonBorderPaddingBackground getCommonBorderPaddingBackground()
	{
		if (_commonBorderPaddingBackground == null)
		{
			_commonBorderPaddingBackground = new CommonBorderPaddingBackground(
					(Map<Integer, Object>) null);
		}
		return _commonBorderPaddingBackground;
	}

	/** @return the "writing-mode" property. */
	public int getWritingMode()
	{
		if (_writingMode == -1)
		{
			return _layoutMaster.getWritingMode();
		}
		return _writingMode;
	}

	/** @return the "writing-mode" property. */
	public int getRegionWritingMode()
	{
		return _writingMode;
	}

	/** @return the "overflow" property. */
	public int getOverflow()
	{
		return _overflow;
	}

	/** @return the display-align property. */
	public int getDisplayAlign()
	{
		return _displayAlign;
	}

	/** @return the "reference-orientation" property. */
	public int getReferenceOrientation()
	{
		if (_layoutMaster != null)
		{
			return _layoutMaster.getReferenceOrientation()
					+ _referenceOrientation;
		}
		return _referenceOrientation;
	}

	/** @return the "reference-orientation" property. */
	public int getRegionReferenceOrientation()
	{
		return _referenceOrientation;
	}

	/** @return the "region-name" property. */
	public String getRegionName()
	{
		return _regionName;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null && !(obj instanceof Region))
		{
			return false;
		}
		Region region = (Region) obj;
		return region._displayAlign == this._displayAlign
				&& region._overflow == this._overflow
				&& region._writingMode == this._writingMode
				&& region._referenceOrientation == this._referenceOrientation
				&& ((_regionName == null) ? (region._regionName == null)
						: (_regionName.equals(region._regionName)))
				&& region._commonBorderPaddingBackground
						.equals(this._commonBorderPaddingBackground);
	}

	public abstract int getNameId();

	public Region clone()
	{
		return null;
	}
}
