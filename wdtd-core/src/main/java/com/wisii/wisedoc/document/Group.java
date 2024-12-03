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
 * @Group.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.Map;

/**
 * 类功能描述：多个同级别对象组合成一个组对象
 * 该组对象用来设置组属性，设置完组属性后，里面的
 * 所有对象将会同样的重复。
 *
 * 作者：zhangqiang
 * 创建日期：2009-3-10
 */
public class Group extends DefaultElement implements Groupable
{

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public Group()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public Group(final Map<Integer,Object> attributes)
	{
		super(attributes);
	}
	public boolean isGroupAble()
	{
		return true;
	}

}
