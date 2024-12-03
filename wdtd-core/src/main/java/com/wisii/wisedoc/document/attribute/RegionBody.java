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
 * @RegionBody.java
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
 * 类功能描述：版心区域声明类
 * 
 * 作者：zhangqiang 创建日期：2008-8-26
 */
public class RegionBody extends Region
{

	// 分栏数
	private int _columnCount;

	// 栏间距
	private Length _columnGap = (Length) InitialManager.getInitialValue(
			Constants.PR_COLUMN_GAP, null);

	// margin属性
	private CommonMarginBlock _commonMarginBlock;

	/**
	 * <p>
	 * 初始化过程的描述，要初始化版心区域需要10个参数，按顺序分别是：
	 * </p>
	 * 
	 * <table width="511" border="1">
	 * <tr>
	 * <td width="16">&nbsp;</td>
	 * <td width="144">变量</td>
	 * <td width="144">解释</td>
	 * <td width="179">默认值</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>columnCount</td>
	 * <td>分栏数</td>
	 * <td>1</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>columnGap</td>
	 * <td>栏间距</td>
	 * <td>null</td>
	 * </tr>
	 * <tr>
	 * <td>3</td>
	 * <td>commonMarginBlock</td>
	 * <td>body的margin属性</td>
	 * <td>需要建立一个CommonMarginBlock对象</td>
	 * </tr>
	 * <tr>
	 * <td>4</td>
	 * <td>SimplePageMaster</td>
	 * <td>body的页布局，这个一开始创建的时候需要设置成null，
	 * 但是SimplePageMaster初始完毕以后需要把SimplePageMaster传递给region body</td>
	 * <td>null</td>
	 * </tr>
	 * <tr>
	 * <td>5</td>
	 * <td>commonBorderPaddingBackground</td>
	 * <td>背景和边框</td>
	 * <td>null（根据FO规范region-body的border和padding必须为0）</td>
	 * </tr>
	 * <tr>
	 * <td>6</td>
	 * <td>displayAlign</td>
	 * <td>对齐方式</td>
	 * <td>Constants.EN_TOP</td>
	 * </tr>
	 * <tr>
	 * <td>7</td>
	 * <td>overflow</td>
	 * <td>overflow溢出方式</td>
	 * <td>Constants.EN_AUTO</td>
	 * </tr>
	 * <tr>
	 * <td>8</td>
	 * <td>regionName</td>
	 * <td>body的region名称</td>
	 * <td>null</td>
	 * </tr>
	 * <tr>
	 * <td>9</td>
	 * <td>referenceOrientation</td>
	 * <td>body的方向（referenceOrientation）</td>
	 * <td>0</td>
	 * </tr>
	 * <tr>
	 * <td>10</td>
	 * <td>writingMode</td>
	 * <td>body的内容的写作模式</td>
	 * <td>Constants.EN_LR_TB</td>
	 * </tr>
	 * </table>
	 * 
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public RegionBody(int columnCount, Length columnGap,
			CommonMarginBlock commonMarginBlock, SimplePageMaster layoutMaster,
			CommonBorderPaddingBackground commonBorderPaddingBackground,
			int displayAlign, int overflow, String regionName,
			int referenceOrientation, int writingMode)
	{
		super(layoutMaster, commonBorderPaddingBackground, displayAlign,
				overflow, referenceOrientation, writingMode, regionName);
		if (columnCount > 0)
		{
			_columnCount = columnCount;
		}
		_commonMarginBlock = commonMarginBlock;
		if (columnGap != null)
		{
			_columnGap = columnGap;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.attribute.Region#getDefaultRegionName()
	 */
	protected String getDefaultRegionName()
	{
		return "xsl-region-body";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.attribute.Region#getViewportRectangle(com.
	 * wisii.wisedoc.document.datatype.FODimension,
	 * com.wisii.wisedoc.document.attribute.SimplePageMaster)
	 */
	public Rectangle getViewportRectangle(FODimension reldims,
			SimplePageMaster spm)
	{
		/*
		 * Special rules apply to resolving margins in the page context.
		 * Contrary to normal margins in this case top and bottom margin are
		 * resolved relative to the height. In the property subsystem all margin
		 * properties are configured to using BLOCK_WIDTH. That's why we 'cheat'
		 * here and setup a context for the height but use the
		 * LengthBase.BLOCK_WIDTH. Also the values are resolved relative to the
		 * page size and reference orientation.
		 */
		SimplePercentBaseContext pageWidthContext;
		SimplePercentBaseContext pageHeightContext;
		if (spm.getReferenceOrientation() % 180 == 0)
		{
			pageWidthContext = new SimplePercentBaseContext(null,
					LengthBase.CONTAINING_BLOCK_WIDTH, spm.getPageWidth()
							.getValue());
			pageHeightContext = new SimplePercentBaseContext(null,
					LengthBase.CONTAINING_BLOCK_WIDTH, spm.getPageHeight()
							.getValue());
		} else
		{
			// invert width and height since top left are rotated by 90 (cl or
			// ccl)
			pageWidthContext = new SimplePercentBaseContext(null,
					LengthBase.CONTAINING_BLOCK_WIDTH, spm.getPageHeight()
							.getValue());
			pageHeightContext = new SimplePercentBaseContext(null,
					LengthBase.CONTAINING_BLOCK_WIDTH, spm.getPageWidth()
							.getValue());
		}

		int start;
		int end;
		if (spm.getWritingMode() == Constants.EN_LR_TB)
		{ // Left-to-right
			start = _commonMarginBlock.getMarginLeft().getValue(
					pageWidthContext);
			end = _commonMarginBlock.getMarginRight()
					.getValue(pageWidthContext);
		} else
		{ // all other supported modes are right-to-left
			start = _commonMarginBlock.getMarginRight().getValue(
					pageWidthContext);
			end = _commonMarginBlock.getMarginLeft().getValue(pageWidthContext);
		}
		int before = _commonMarginBlock.getSpaceBefore().getOptimum(
				pageHeightContext).getLength().getValue(pageHeightContext);
		int after = _commonMarginBlock.getSpaceAfter().getOptimum(
				pageHeightContext).getLength().getValue(pageHeightContext);

		/* 【添加：START】 by 李晓光 2008-09-10 */
		left = start;
		top = before;
		/* 【添加：END】 by 李晓光 2008-09-10 */
		return new Rectangle(start, before, reldims.ipd - start - end,
				reldims.bpd - before - after);
	}

	/* 【添加：START】 by 李晓光 2008-09-10 */
	private int left, top;

	public int getMarginLeft()
	{
		return left;
	}

	public int getMarginTop()
	{
		return top;
	}

	/* 【添加：END】 by 李晓光 2008-09-10 */

	/**
	 * Return the Common Margin Properties-Block.
	 * 
	 * @return the Common Margin Properties-Block.
	 */
	public CommonMarginBlock getCommonMarginBlock()
	{
		return _commonMarginBlock;
	}

	/**
	 * 
	 * 获得分栏数
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getColumnCount()
	{
		return _columnCount;
	}

	/**
	 * 
	 * 获得栏间距
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getColumnGap()
	{
		return _columnGap.getValue();
	}

	/**
	 * 
	 * 获得栏间距
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public Length getColumnGapLength()
	{
		return _columnGap;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null && !(obj instanceof RegionBody))
		{
			return false;
		}
		RegionBody rb = (RegionBody) obj;
		return rb._columnCount == this._columnCount
				&& rb._columnGap.equals(this._columnGap) && super.equals(obj);
	}

	/**
	 * @see com.wisii.fov.fo.FObj#getNameId()
	 */
	public int getNameId()
	{
		return Constants.FO_REGION_BODY;
	}

	public RegionBody clone()
	{
		int columnCount = this.getColumnCount();
		Length columnGap = this.getColumnGapLength().clone();
		CommonMarginBlock commonMarginBlock = this.getCommonMarginBlock()
				.clone();
		CommonBorderPaddingBackground commonBorderPaddingBackground = this
				.getCommonBorderPaddingBackground().clone();
		int displayAlign = getDisplayAlign();
		int overflow = getOverflow();
		String regionName = new String(getRegionName());
		int referenceOrientation = getRegionReferenceOrientation();
		int writingMode = getRegionWritingMode();
		RegionBody region = new RegionBody(columnCount, columnGap,
				commonMarginBlock, null, commonBorderPaddingBackground,
				displayAlign, overflow, regionName, referenceOrientation,
				writingMode);
		return region;
	}
}
