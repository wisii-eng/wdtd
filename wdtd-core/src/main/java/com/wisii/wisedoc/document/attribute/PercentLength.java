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
/* $Id: PercentLength.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.datatype.PercentBase;
import com.wisii.wisedoc.document.datatype.PercentBaseContext;

/**
 * 
 * 类功能描述：相对长度属性
 * 
 * 作者：zhangqiang 创建日期：2008-12-18
 */
public class PercentLength extends LengthProperty
{

	/**
	 * The percentage itself, expressed as a decimal value, e.g. for 95%, set
	 * the value to .95
	 */
	private double factor;

	/**
	 * A PercentBase implementation that contains the base length to which the
	 * {@link #factor} should be applied to compute the actual length
	 */
	private PercentBase lbase = null;

	private double resolvedValue;
	public static double PRECISION = 0.00001;
	/**
	 * 
	 * 初始化过程的描述,factor:为缩放比率，如果lbase是LengthBase类型的
	 * 且lbase的baseType属性值是LengthBase.
	 * FONTSIZE或LengthBase.INH_FONTSIZE，相当factor属性就相当
	 * 于是em的概念，如factor==2，则相当于是2em的概念，如果lbase的baseType属性值是其他类型，则相当于是
	 * 百分比的概念，如factor = 0.5，则相当于是50%。
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public PercentLength(double factor, PercentBase lbase)
	{
		this.factor = factor;
		this.lbase = lbase;
	}

	/**
	 * @return the base
	 */
	public PercentBase getBaseLength()
	{
		return this.lbase;
	}

	/**
	 * 
	 * @return the factor TODO: Should this really exists?
	 */
	public double value()
	{
		return factor;
	}

	/**
	 * Return false because percent-length are always relative.
	 * 
	 * @see com.wisii.fov.datatypes.Numeric#isAbsolute()
	 */
	public boolean isAbsolute()
	{
		return false;
	}

	/**
	 * @see com.wisii.fov.datatypes.Numeric#getNumericValue()
	 */
	public double getNumericValue()
	{
		return getNumericValue(null);
	}

	/**
	 * @see com.wisii.fov.datatypes.Numeric#getNumericValue(PercentBaseContext)
	 */
	public double getNumericValue(PercentBaseContext context)
	{
		resolvedValue = factor * lbase.getBaseLength(context);
		return resolvedValue;

	}

	/**
	 * Return the length of this PercentLength.
	 * 
	 * @see com.wisii.fov.datatypes.Length#getValue()
	 */
	public int getValue()
	{
		return (int) getNumericValue();
	}

	/**
	 * @see com.wisii.fov.datatypes.Numeric#getValue(PercentBaseContext)
	 */
	public int getValue(PercentBaseContext context)
	{
		return (int) getNumericValue(context);
	}

	/**
	 * @return the String equivalent of this
	 */
	public String toString()
	{
		return factor + " em";
	}

	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		if (obj == null || !(obj instanceof PercentLength))
		{
			return false;
		}
		PercentLength pbase = (PercentLength) obj;
		return (factor == pbase.factor)
				&& (lbase == null ? pbase.lbase == null : lbase
						.equals(pbase.lbase));
	}

	/* 【添加：START】 by 李晓光2008-12-23 */
	public void setFactor(double factor)
	{
		this.factor = factor;
	}

	/* 【添加：END】 by 李晓光2008-12-23 */

	public PercentLength clone()
	{
		PercentLength newlength = null;
		try
		{
			newlength = new PercentLength(value(), getBaseLength().clone());
		} catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newlength;
	}
}
