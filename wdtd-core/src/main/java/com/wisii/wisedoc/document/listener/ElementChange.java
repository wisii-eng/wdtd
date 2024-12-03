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
 * @ElementChange.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.listener;

import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Element;

/**
 * 类功能描述：元素插入或删除事件信息类，通过该类能获得插入和删除的相关信息
 * 
 * 作者：zhangqiang 创建日期：2008-8-15
 */
public class ElementChange implements DocumentChange
{
	// 元素编辑类型：插入
	public static final int INSERT = 0;

	// 元素编辑类型：删除
	public static final int REMOVE = 1;

	// 元素编辑类型
	private int _edittype;

	// 操作位置,如果是删除对象操作，则_offset值为删除的第一个对象的在删除前所在元素中的位置的前一个位置
	private int _offset;

	// 当前所操作的文档。
	Element _parent;

	// 要操作的元素
	List<CellElement> _editelements;
	// 是否需要定位到操作元素的第一个元素
	private boolean _shouldposfirst = false;

	public ElementChange(int offset, Element parent,
			List<CellElement> editelements, int edittype)
	{
		// Element;
		_offset = offset;
		_parent = parent;
		_editelements = new ArrayList<CellElement>(editelements);
		_edittype = edittype;
	}

	public ElementChange(int offset, Element parent,
			List<CellElement> editelements, int edittype, boolean shouldposfirst)
	{
		// Element;
		_offset = offset;
		_parent = parent;
		_editelements = new ArrayList<CellElement>(editelements);
		_edittype = edittype;
		_shouldposfirst = shouldposfirst;
	}

	public int getType()
	{
		return _edittype;
	}

	public Element getParentElement()
	{
		return _parent;
	}

	/**
	 * @返回 _editelements变量的值
	 */
	public List<CellElement> getEditelements()
	{
		return _editelements;
	}
    public void insertChangeElements(List<CellElement> editelements)
    {
    	_editelements.addAll(editelements);
    }
	/**
	 * @返回 _offset变量的值
	 */
	public int getOffset()
	{
		return _offset;
	}
	public void setOffset(int offset)
	{
		_offset = offset;
	}

	public boolean shouldPositionFirst()
	{
		return _shouldposfirst;
	}
}
