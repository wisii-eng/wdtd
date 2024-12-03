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
 * @CompoundElementChange.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.listener;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述：复合文档元素改变时间
 * 
 * 作者：zhangqiang 创建日期：2008-8-15
 */
public class CompoundElementChange
{
	// 多个文档改变组成的集合
	List<DocumentChange> _changes;

	public CompoundElementChange(List<DocumentChange> changes)
	{
//		_changes = new ArrayList<DocumentChange>(changes);
		if (changes != null)
		{
			_changes = new ArrayList<DocumentChange>(changes);
//			int size = changes.size();
//			for (int i = 0; i < size; i++)
//			{
//				DocumentChange change = changes.get(i);
//				if (change != null)
//				{
//					_changes.add(change);
//				}
//			}
		}
	}

	public CompoundElementChange(DocumentChange change)
	{
		_changes = new ArrayList<DocumentChange>();
		if (change != null)
		{
			_changes.add(change);
		}
	}

	/**
	 * @返回 _changes变量的值
	 */
	public List<DocumentChange> getChanges()
	{
		return new ArrayList<DocumentChange>(_changes);
	}
}
