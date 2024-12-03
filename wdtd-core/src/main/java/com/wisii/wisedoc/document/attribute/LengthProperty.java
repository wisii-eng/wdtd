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
 * @LengthProperty.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.Numeric;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-9-2
 */
public abstract class LengthProperty extends Property implements Length
{

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public LengthProperty()
	{
		// TODO Auto-generated constructor stub
	}

	public double getTableUnits()
	{
		return 0.0;
	}

	/**
	 * @return the numeric dimension. Length always a dimension of 1.
	 */
	public int getDimension()
	{
		return 1;
	}

	/**
	 * @return this.length cast as a Numeric
	 */
	public Numeric getNumeric()
	{
		return this;
	}

	/**
	 * @return this.length
	 */
	public Length getLength()
	{
		return this;
	}

	/**
	 * @return this.length cast as an Object
	 */
	public Object getObject()
	{
		return this;
	}

	public LengthProperty clone() 
	{
		return null;
	}
}
