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
 * @AttributeEdit.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.undo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.listener.AttributeChange;
import com.wisii.wisedoc.document.listener.CompoundElementChange;
import com.wisii.wisedoc.document.listener.DocumentChange;

/**
 * 类功能描述：属性设置编辑原子操作
 * 
 * 作者：zhangqiang 创建日期：2008-8-15
 */
public class AttributeEdit extends DocumentEdit
{
   
	AttributeChange _change;
//	List<Map<Integer, Object>> _oldvalues;
	/**
	 * 
	 * 传入的editelements需要是属性还没改变前的元素
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	AttributeEdit(AttributeChange change)
	{
		_change = change;
//		List<Element> editelements = _change.getEditelements();
//		int size = editelements.size();
////		_oldvalues = new ArrayList<Map<Integer, Object>>();
////		for (int i = 0; i < size; i++)
////		{
////			Element element = editelements.get(i);
////			_oldvalues.add(element.getAttributes().getAttributes());
////		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	public  void undo() throws CannotUndoException
	{
		super.undo();
		// 要操作的元素
		List<Element> editelements = _change.getEditelements();
		int size = editelements.size();
		List<DocumentChange> changs = new ArrayList<DocumentChange>();
		List<Map<Integer, Object>> oldvalues = _change.getOldvalues();
		for (int i = 0; i < size; i++)
		{
			Element element = editelements.get(i);
			Map<Integer,Object> map = oldvalues.get(i);
			element.setAttributes(map, _change.isReplace());
			List<Element> cells = new ArrayList<Element>();
			cells.add(element);
			changs.add(new AttributeChange(cells,map,_change.isReplace()));
		}
		_changes = new CompoundElementChange(changs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	public void redo() throws CannotRedoException
	{
		super.redo();
		// 要操作的元素
		List<Element> editelements = _change.getEditelements();
		int size = editelements.size();
		Map<Integer,Object> newvalue = _change.getNewvalue();
		boolean isreplace  = _change.isReplace();
		List<DocumentChange> changs = new ArrayList<DocumentChange>();
		for (int i = 0; i < size; i++)
		{
			Element element = editelements.get(i);
			element.setAttributes(newvalue, isreplace);
		}
		changs.add(_change);
		_changes = new CompoundElementChange(changs);
	}
}
