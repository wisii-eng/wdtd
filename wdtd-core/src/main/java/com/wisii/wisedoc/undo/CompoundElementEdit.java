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
 * @DocumentEdit.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.undo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.listener.AttributeChange;
import com.wisii.wisedoc.document.listener.CompoundElementChange;
import com.wisii.wisedoc.document.listener.DataStructChange;
import com.wisii.wisedoc.document.listener.DocumentChange;
import com.wisii.wisedoc.document.listener.DocumentChangeEvent;
import com.wisii.wisedoc.document.listener.ElementChange;

/**
 * 类功能描述：由原子编辑操作组成的复合编辑操作 包含的是多个原子编辑操作
 * 
 * 作者：zhangqiang 创建日期：2008-8-13
 */
public class CompoundElementEdit extends DocumentEdit
{
	// 操作集合
	List<DocumentEdit> _edits;
	Document document;

	public CompoundElementEdit(List<DocumentChange> changes,Document doc)
	{
		document = doc;
		_edits = new ArrayList<DocumentEdit>();
		if (changes != null && !changes.isEmpty())
		{
			DocumentChange oldchange = null;
			for (DocumentChange change : changes)
			{
				DocumentEdit edit = null;
				if (change == null)
				{
					continue;
				} else if (change instanceof ElementChange)
				{
					if (oldchange != null && oldchange instanceof ElementChange)
					{
						final ElementChange oldelementchange = (ElementChange) oldchange;
						final ElementChange elementchange = (ElementChange) change;
						final int oldtype = oldelementchange.getType();
						final Element oldparent = oldelementchange
								.getParentElement();
						final Element parent = elementchange.getParentElement();
						final boolean shouldfirst = elementchange
								.shouldPositionFirst();
						if (oldtype == elementchange.getType()
								&& oldparent == parent && !shouldfirst)
						{
							// 如果是插入操作
							if (oldtype == ElementChange.INSERT)
							{
								final int oldoffset = oldelementchange
										.getOffset();
								final int offset = elementchange.getOffset();
								final int oldesize = oldelementchange
										.getEditelements().size();
								if (oldoffset + oldesize == offset)
								{
									oldelementchange
											.insertChangeElements(elementchange
													.getEditelements());
								} else
								{
									edit = new ElementEdit(
											(ElementChange) change);
								}
							}
							// 如果删除操作
							else
							{
								int oldoffset = oldelementchange.getOffset();
								int offset = elementchange.getOffset();
								int thissize = elementchange.getEditelements()
										.size();
								if (offset + thissize == oldoffset)
								{
									oldelementchange
											.insertChangeElements(elementchange
													.getEditelements());
									oldelementchange.setOffset(offset);
								} else
								{
									edit = new ElementEdit(
											(ElementChange) change);
								}
							}
						} else
						{
							edit = new ElementEdit((ElementChange) change);
						}
					} else
					{
						edit = new ElementEdit((ElementChange) change);
					}

				}
				else if(change instanceof DataStructChange)
				{
					edit = new DataStructEdit((DataStructChange)change);
				}
				else
				{
					edit = new AttributeEdit((AttributeChange) change);

				}
				if (edit != null)
				{
					_edits.add(edit);
				}
				oldchange = change;

			}
		}
	}

	/*
	 * (non-Javadoc) 注意undo和redo的操作需要相反
	 * 
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	public void undo() throws CannotUndoException
	{
		// TODO Auto-generated method stub
		super.undo();
		int size = _edits.size();
		List<DocumentChange> changes = new ArrayList<DocumentChange>();
		// 从最后一个操作开始undo。
		for (int i = size - 1; i >= 0; i--)
		{
			DocumentEdit edit = _edits.get(i);
			edit.undo();
			changes.addAll(edit.getElementChange().getChanges());
		}
		_changes = new CompoundElementChange(changes);
		document.firechangedUpdate(new DocumentChangeEvent(document, _changes));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	public void redo() throws CannotRedoException
	{
		// TODO Auto-generated method stub
		super.redo();
		int size = _edits.size();
		List<DocumentChange> changes = new ArrayList<DocumentChange>();
		for (int i = 0; i < size; i++)
		{
			DocumentEdit edit = _edits.get(i);
			edit.redo();
			changes.addAll(edit.getElementChange().getChanges());
		}
		_changes = new CompoundElementChange(changes);
		document.firechangedUpdate(new DocumentChangeEvent(document, _changes));
	}
}
