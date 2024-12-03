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
/* $Id: EnumLength.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.PercentBaseContext;

/**
 * A length quantity in XSL which is specified as an enum, such as "auto"
 */
public class EnumLength extends LengthProperty
{

	private int enumProperty = 0;

	private Length length;

	/**
	 * 
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public EnumLength(int enumProperty, Length length)
	{
		if (enumProperty >= 0)
		{
			this.enumProperty = enumProperty;
		}
		this.length = length;

	}

	/**
	 * @see com.wisii.fov.datatypes.Numeric#getEnum()
	 */
	public int getEnum()
	{
		return enumProperty;
	}

	public boolean isAbsolute()
	{
		return false;
	}

	/**
	 * @see com.wisii.fov.datatypes.Numeric#getValue()
	 */
	public int getValue()
	{
		if (length == null)
		{
			return 0;
		} else
		{
			return this.length.getValue();
		}
	}

	/**
	 * @see com.wisii.fov.datatypes.Numeric#getNumericValue()
	 */
	public double getNumericValue()
	{
		return 0;
	}

	/**
	 * @see com.wisii.fov.fo.properties.Property#getString()
	 */
	public String getString()
	{
		return enumProperty + "" + length;
	}

	public String toString()
	{
		// TODO Auto-generated method stub
		return getString();
	}

	/**
	 * @see com.wisii.fov.fo.properties.Property#getString()
	 */
	public Object getObject()
	{
		return enumProperty;
	}

	public int getValue(PercentBaseContext context)
	{
		if (length == null)
		{
			return 0;
		} else
		{
			return length.getValue(context);
		}
	}

	public double getNumericValue(PercentBaseContext context)
	{
		if (length == null)
		{
			return 0;
		} else
		{
			return length.getNumericValue(context);
		}
	}

	/**
	 * @返回 length变量的值
	 */
	public Length getFixLength()
	{
		return length;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof EnumLength))
		{
			return false;
		}
		EnumLength el = (EnumLength) obj;
		return enumProperty == el.enumProperty
				&& (length == null ? el.length == null : (length
						.equals(el.length)));
	}

	public EnumLength clone()
	{
		EnumLength 
			newlength = new EnumLength(getEnum(), getLength().clone());
		
		return newlength;
	}
}
