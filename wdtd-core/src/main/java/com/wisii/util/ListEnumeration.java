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
 * @ListEnumeration.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.util;

import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-11-22
 */
public class ListEnumeration<E> implements Enumeration<E>
{

	private List<E> list;

	/** Index. */
	private int index=0;

	/** Constructs an array enumeration. */
	public ListEnumeration(List<E> list)
	{
		this.list = list;
	} 

	/**
	 * Tests if this enumeration contains more elements.
	 * 
	 * @return <code>true</code> if this enumeration contains more elements;
	 *         <code>false</code> otherwise.
	 * @since JDK1.0
	 */
	public boolean hasMoreElements()
	{
		return list!=null&&index < list.size();
	} // hasMoreElement():boolean

	/**
	 * Returns the next element of this enumeration.
	 * 
	 * @return the next element of this enumeration.
	 * @exception NoSuchElementException
	 *                if no more elements exist.
	 * @since JDK1.0
	 */
	public E nextElement()
	{
		if (list!=null&&index < list.size())
		{
			return list.get(index++);
		}
		throw new NoSuchElementException();
	}
}
