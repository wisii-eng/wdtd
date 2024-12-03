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
 * @LengthBase.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.datatype;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-26
 */
public class LengthBase implements PercentBase
{

	// Standard kinds of percent-based length
	/** constant for a custom percent-based length */
	public static final int CUSTOM_BASE = 0;

	/** constant for a font-size percent-based length */
	public static final int FONTSIZE = 1;

	/** constant for an inh font-size percent-based length */
	public static final int INH_FONTSIZE = 2;

	/** constant for a containing box percent-based length */
	public static final int PARENT_AREA_WIDTH = 3;

	/** constant for a containing refarea percent-based length */
	public static final int CONTAINING_REFAREA_WIDTH = 4;

	/** constant for a containing block percent-based length */
	public static final int CONTAINING_BLOCK_WIDTH = 5;

	/** constant for a containing block percent-based length */
	public static final int CONTAINING_BLOCK_HEIGHT = 6;

	/** constant for a image intrinsic percent-based length */
	public static final int IMAGE_INTRINSIC_WIDTH = 7;

	/** constant for a image intrinsic percent-based length */
	public static final int IMAGE_INTRINSIC_HEIGHT = 8;

	/** constant for a image background position horizontal percent-based length */
	public static final int IMAGE_BACKGROUND_POSITION_HORIZONTAL = 9;

	/** constant for a image background position vertical percent-based length */
	public static final int IMAGE_BACKGROUND_POSITION_VERTICAL = 10;

	/** constant for a table-unit-based length */
	public static final int TABLE_UNITS = 11;

	/** constant for a alignment adjust percent-based length */
	public static final int ALIGNMENT_ADJUST = 12;

	/**
	 * The FO for which this property is to be calculated.
	 */
	protected/* final */Element _element;

	/**
	 * One of the defined types of LengthBase
	 */
	private/* final */int baseType;

	// /** For percentages based on other length properties */
	private Length baseLength;

	/**
	 * 
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public LengthBase(Element element, int baseType)
	{
		this(baseType);
		_element = element;
		if (element != null)
		{
			if (baseType == FONTSIZE)
			{
				Object length = element.getAttribute(Constants.PR_FONT_SIZE);
				if (length instanceof Length)
				{
					baseLength = (Length) length;
				}
			} else if (baseType == INH_FONTSIZE)
			{
				Element elementparent = element.getParent();
				if (elementparent != null)
				{
					Object length = elementparent
							.getAttribute(Constants.PR_FONT_SIZE);
					if (length instanceof Length)
					{
						baseLength = (Length) length;
					}
				}
			}
		}
	}

	/**
	 * 
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public LengthBase(Length length, int baseType)
	{
		this(baseType);
		if (baseType == FONTSIZE || baseType == INH_FONTSIZE)
		{
			baseLength = length;
		}
	}

	public LengthBase(int baseType)
	{
		if (baseType > -1 && baseType < 13)
		{
			this.baseType = baseType;
		} else
		{
			this.baseType = CUSTOM_BASE;
		}

	}

	/**
	 * @return the dimension of this object (always 1)
	 */
	public int getDimension()
	{
		return 1;
	}

	public int getBaseType()
	{
		return baseType;
	}

	/**
	 * @返回 baseLength变量的值
	 */
	public Length getBaseLength()
	{
		return baseLength;
	}

	/**
	 * @return the base value of this object (always 1.0)
	 */
	public double getBaseValue()
	{
		return 1.0;
	}

	/**
	 * @see com.wisii.fov.datatypes.PercentBase#getBaseLength(PercentBaseContext)
	 */
	public int getBaseLength(PercentBaseContext context)
	{
		int baseLen = 0;

		if (baseType == FONTSIZE || baseType == INH_FONTSIZE)
		{
			if (baseLength instanceof LengthProperty)
			{
				return ((LengthProperty) baseLength).getValue(context);
			}
			return 0;
		}
		if (context != null)
		{
			baseLen = context.getBaseLength(baseType, _element);
		} else
		{
			LogUtil.error("getBaseLength called without context");
		}
		return baseLen;
	}

	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		if (obj == null || !(obj instanceof LengthBase))
		{
			return false;
		}
		LengthBase lengthbase = (LengthBase) obj;
		return (baseType == lengthbase.baseType)
				&& (baseLength == null ? lengthbase.baseLength == null
						: baseLength.equals(lengthbase.baseLength))
				&& _element == lengthbase._element;
	}

	public LengthBase clone()
	{
		LengthBase newlength = null;
		int baseType = getBaseType();
		Element element = this._element;
		Length length = this.baseLength;
		if (element == null)
		{
			if (length == null)
			{
				newlength = new LengthBase(baseType);
			} else
			{
				newlength = new LengthBase(length, baseType);
			}
		} else
		{
			newlength = new LengthBase(element, baseType);
		}

		return newlength;
	}
}
