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
 * @AttributeContainer.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 类功能描述:属性容器接口类，Designer中所有模型的属性 放在属性的容器中统一管理，不再是对象加对象成员变量的方式 管理属性
 * 
 * 作者：zhangqiang 创建日期：2008-8-12
 */
public abstract class AttributeContainer {
	protected List<Attributes> _attributelist = new LinkedList<Attributes>();

	/**
	 * 
	 * 获得指定属性集合的属性引用
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public Attributes getAttribute(Map<Integer, Object> atts) {
		if (atts != null) {
			int size = _attributelist.size();
			Attributes findedattr = null;
			// 从前往后查找已有属性对象
			for (int i = 0; i < size; i++) {
				Attributes attb = _attributelist.get(i);
				// 如果找到，则退出循环
				if (attb.getAttributes().equals(atts)) {
					findedattr = attb;
					break;
				}
			}
//			只保存10个属性，超过10个时清空，以减少内存使用
			if(size > 10)
			{
				_attributelist.clear();
			}
			// 如果没有在属性容器中没有找到，则新生成Attributes对象，并将其放到链表的开始位置，这样以加速查找效率
			if (findedattr == null) {
				findedattr = new Attributes(atts);
				_attributelist.add(0, findedattr);
			}
			return findedattr;
		}
		return null;
	}

	/**
	 * 
	 * 所设置的属性是否支持
	 * 
	 * @param
	 * @return 支持返回真，不支持返回假
	 * @exception
	 */
	public abstract boolean isAttributesSupport(Map<Integer, Object> atts);
}
