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
 * @EnumProperty.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-9-2
 */
public class EnumProperty extends Property {
	private int value;

	private String text;

	/**
	 * 初始化过程的描述
	 * 
	 * @param int类型的value值对应Constants属性中的一个属性值，一般来说String类型的text没有什么用
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public EnumProperty(int value, String text) {
		this.value = value;
		this.text = text;
	}

	/**
	 * @return this.value
	 */
	public int getEnum() {
		return this.value;
	}

	/**
	 * @return this.value cast as an Object
	 */
	public Object getObject() {
		return text;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof EnumProperty)) {
			return false;
		}
		return value == ((EnumProperty) obj).value;
	}
	public String toString()
	{
		return ""+value;
	}
}
