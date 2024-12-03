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
 * @ElementSelectionEvent.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.select;

import java.util.EventObject;

/**
 * 类功能描述：元素选中事件类
 * 
 * 作者：zhangqiang 创建日期：2008-9-1
 */
public class ElementSelectionEvent extends EventObject
{

	// 发生改变的事件对象
	protected Object[] elements;

	// 标识对象是否被选中
	protected boolean[] areNew;

	/**
	 * 选中事件构造函数
	 */
	public ElementSelectionEvent(Object source, Object[] cells, boolean[] areNew)
	{
		super(source);
		this.elements = cells;
		this.areNew = areNew;
	}

	/**
	 * 获得选中事件的所有对象
	 * 
	 * @return added or removed cells
	 */
	public Object[] getElements()
	{
		int numCells;
		Object[] retCells;

		numCells = elements.length;
		retCells = new Object[numCells];
		System.arraycopy(elements, 0, retCells, 0, numCells);
		return retCells;
	}

	/**
	 * 获得选中事件的第一个对象
	 * 
	 * 
	 */
	public Object getElement()
	{
		return elements[0];
	}

	/**
	 * 返回第一个对象是否是选中
	 */
	public boolean isAddedElement()
	{
		return areNew[0];
	}

	/**
	 * 返回指定对象是否是新添加的选中对象
	 */
	public boolean isAddedElement(Object cell)
	{
		for (int counter = elements.length - 1; counter >= 0; counter--)
			if (elements[counter].equals(cell))
				return areNew[counter];
		throw new IllegalArgumentException(
				"cell is not a cell identified by the ElementSelectionEvent");
	}

	/**
	 * 返回指定索引的对象是否是新添加的选中对象
	 */
	public boolean isAddedElement(int index)
	{
		if (elements == null || index < 0 || index >= elements.length)
		{
			throw new IllegalArgumentException(
					"index is beyond range of added cells identified by ElementSelectionEvent");
		}
		return areNew[index];
	}

	/**
	 * 赋值选中事件
	 */
	public Object cloneWithSource(Object newSource)
	{
		return new ElementSelectionEvent(newSource, elements, areNew);
	}

}
