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
/* $Id: EnumNumber.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.document.attribute;

/**
 * A number quantity in XSL which is specified as an enum, such as "no-limit".
 */
public class EnumNumber extends NumberProperty
{
	private int enumProperty = 0;

	/**
	 * 
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public EnumNumber(int enumProperty, Number number)
	{
		super(number);
		if (enumProperty >= 0)
		{
			this.enumProperty = enumProperty;
		}
	}

	public int getEnum()
	{
		return enumProperty;
	}

	/**
	 * Returns the length in 1/1000ths of a point (millipoints)
	 * 
	 * @return the length in millipoints
	 */
	public int getValue()
	{
		return 0;
	}

	/**
	 * Returns the value as numeric.
	 * 
	 * @return the length in millipoints
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
		return "";
	}

	/**
	 * @see com.wisii.fov.fo.properties.Property#getString()
	 */
	public Object getObject()
	{
		return enumProperty;
	}
	public boolean equals(Object obj)
	{
		if(!(obj instanceof EnumNumber))
		{
			return false;
		}
		EnumNumber en = (EnumNumber) obj;
		return enumProperty == en.enumProperty&&super.equals(obj);
	}

}
