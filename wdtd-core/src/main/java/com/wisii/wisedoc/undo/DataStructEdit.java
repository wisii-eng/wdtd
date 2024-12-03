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
 * @DataStructEdit.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.undo;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import com.wisii.wisedoc.document.listener.CompoundElementChange;
import com.wisii.wisedoc.document.listener.DataStructChange;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-1-5
 */
public class DataStructEdit extends DocumentEdit
{
   
	DataStructChange _change;
	/**
	 * 
	 * 传入的editelements需要是属性还没改变前的元素
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	DataStructEdit(DataStructChange change)
	{
		_change = change;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	public  void undo() throws CannotUndoException
	{
		super.undo();
		_change.getDocument().setDataStructureWithoutEdit(_change.getOldstruct());
		_changes = new CompoundElementChange(_change);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	public void redo() throws CannotRedoException
	{
		super.redo();
		_change.getDocument().setDataStructureWithoutEdit(_change.getNewstruct());
		_changes = new CompoundElementChange(_change);
	}
}
