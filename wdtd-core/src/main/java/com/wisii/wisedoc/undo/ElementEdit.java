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
 * @ElementEdit.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.undo;

import java.util.List;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.listener.CompoundElementChange;
import com.wisii.wisedoc.document.listener.ElementChange;

/**
 * 类功能描述：元素编辑操作原子类，包括元素的添加和删除。
 * 
 * 作者：zhangqiang 创建日期：2008-8-15
 */
public class ElementEdit extends DocumentEdit
{
//	元素编辑内容
	ElementChange _change;

	public ElementEdit(ElementChange change)
	{
		_change = change;
	}

	@Override
	public void redo() throws CannotRedoException
	{
		super.redo();
		int edittype = _change.getType();
		int offset = _change.getOffset();
		// 当前所操作的文档。
		Element parent = _change.getParentElement();

		// 要操作的元素
		List<CellElement> editelements = _change.getEditelements();
		if (edittype == ElementChange.INSERT)
		{

			if (offset > -1)
			{
				parent.insert(editelements, offset);
			}
		}
		if (edittype == ElementChange.REMOVE)
		{
			parent.removeChildren(editelements);
		}
		_changes = new CompoundElementChange(_change);
	}

	@Override
	public void undo() throws CannotUndoException
	{
		// TODO Auto-generated method stub
		super.undo();
		int edittype = _change.getType();
		int offset = _change.getOffset();
		// 当前所操作的文档。
		Element parent = _change.getParentElement();

		// 要操作的元素
		List<CellElement> editelements = _change.getEditelements();
		int nedittype = -1;
		if (edittype == ElementChange.INSERT)
		{
			parent.removeChildren(editelements);
			nedittype = ElementChange.REMOVE;
		}
		if (edittype == ElementChange.REMOVE)
		{
			if (offset > -1)
			{

				parent.insert(editelements, offset);
				nedittype = ElementChange.INSERT;
			}
		}
		ElementChange change = new ElementChange(offset, parent, editelements,
				nedittype,_change.shouldPositionFirst());
		_changes = new CompoundElementChange(change);
	}

}
