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
package com.wisii.wisedoc.document.attribute;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * 类功能描述：属性集合类
 * 
 * 作者：zhangqiang 创建日期：2008-8-12
 */
public final class Attributes
{
	// 属性容器
	private Map<Integer, Object> _attribute;

	/**
	 * 
	 *属性集合类对象初始化
	 * 
	 * @param
	 * @exception
	 */
	public Attributes(final Map<Integer, Object> attr)
	{
		_attribute = new HashMap<Integer, Object>(attr);
	}

	/**
	 * 
	 * 获得指定关键字的属性值
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public Object getAttribute(final int key)
	{
		return _attribute.get(key);
	}

	public Iterator<Integer> getAttributeKeys()
	{
		return _attribute.keySet().iterator();
	}

	/**
	 * 
	 * 获得所有属性 不直接返回map是为了防止调用者拿到该map后对里面的内容重新赋值
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public Map<Integer, Object> getAttributes()
	{
		return new HashMap<Integer, Object>(_attribute);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_attribute == null) ? 0 : _attribute.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Attributes)) {
			return false;
		}
		final Attributes other = (Attributes) obj;
		if (_attribute == null) {
			if (other._attribute != null) {
				return false;
			}
		} else if (!_attribute.equals(other._attribute)) {
			return false;
		}
		return true;
	}

	//以前人工手写的equals方法
/*	public boolean equals(Object obj)
	{
		if (!(obj instanceof Attributes))
		{
			return false;
		}
		Attributes atts = (Attributes) obj;
		return _attribute.equals(atts._attribute);
	}*/
}
