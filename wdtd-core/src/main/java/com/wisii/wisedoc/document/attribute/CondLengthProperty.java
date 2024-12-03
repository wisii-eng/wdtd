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
 * @CondLengthProperty.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.PercentBaseContext;

/**
 * 类功能描述：条件长度属性 即满足一定条件时长度才有效
 * 
 * 作者：zhangqiang 创建日期：2008-8-21
 */
public class CondLengthProperty
{

	// 长度
	private Length _length;

	// 长度是否可以无效
	private boolean _isdiscard;

	public CondLengthProperty(Length length, boolean isdiscard)
	{
		_length = length;
		_isdiscard = isdiscard;

	}

	public boolean isDiscard()
	{
		return _isdiscard;
	}

	/**
	 * 
	 * 得到长度所代表的千分之一pt长度值
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public int getLengthValue()
	{
		return _length.getValue();
	}

	/**
	 * 
	 * 得到长度所代表的千分之一pt长度值
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public int getLengthValue(PercentBaseContext context)
	{
		return _length.getValue(context);
	}

	public Length getLength()
	{
		return _length;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof CondLengthProperty))
		{
			return false;
		}
		CondLengthProperty cl = (CondLengthProperty) obj;
		return _isdiscard == cl._isdiscard
				&& (_length == null ? cl._length == null : _length
						.equals(cl._length));
	}

	public CondLengthProperty clone()
	{
		CondLengthProperty length = new CondLengthProperty(getLength().clone(),
				isDiscard());
		return length;
	}
}
