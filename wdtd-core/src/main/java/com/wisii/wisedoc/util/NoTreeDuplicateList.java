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
 * 
 */
package com.wisii.wisedoc.util;

import java.util.ArrayList;

import com.wisii.wisedoc.document.Element;

/**
 * @author HP_Administrator
 *
 */
public class NoTreeDuplicateList extends ArrayList<Element>
{
	/**
	 * 复写原来的add的方法，使得 1，如果已经包含有该对象的祖先对象时，不再添加该对象。 2，如果该对象是已添加对象的祖先对象，则清除掉原有对象
	 * 
	 */
	public boolean add(Element o)
	{
	
		Element addelement = o;
		int size = size();
		for(int i = 0;i< size;i++)
		{
			Element element = get(i);
			Element parentadd = addelement;
			while (parentadd != null)
			{
				// 如果要添加的元素的祖先对象是已添加元素，则不再添加直接返回
				if (parentadd.equals(element))
				{
					return false;
				}
				parentadd = (Element) parentadd.getParent();

			}
			Element parente = element;
			while (parente != null)
			{
				// 如果要添加的元素对象是已添加元素对象的祖先对象。则清除掉已添加的元素对象
				if (parente.equals(addelement))
				{
					remove(element);
					break;
				}
				parente = (Element) parente.getParent();

			}
		}
//		Iterator<Element> it = iterator();
//		while (it.hasNext())
//		{
//			Element element = it.next();
//			Element parentadd = addelement;
//			while (parentadd != null)
//			{
//				// 如果要添加的元素的祖先对象是已添加元素，则不再添加直接返回
//				if (parentadd.equals(element))
//				{
//					return false;
//				}
//				parentadd = (Element) parentadd.getParent();
//
//			}
//			Element parente = element;
//			while (parente != null)
//			{
//				// 如果要添加的元素对象是已添加元素对象的祖先对象。则清除掉已添加的元素对象
//				if (parente.equals(addelement))
//				{
//					remove(element);
//					break;
//				}
//				parente = (Element) parente.getParent();
//
//			}
//		}
		return super.add(o);
	}
}
