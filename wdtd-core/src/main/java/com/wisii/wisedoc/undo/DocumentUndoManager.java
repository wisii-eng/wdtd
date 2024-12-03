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
 * @DocumentUndoManager.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.undo;

import javax.swing.undo.UndoManager;
/**
 * 类功能描述：文档撤销操作管理器，通过该管理器实现文档的撤销重做操作。
 * 
 * 作者：zhangqiang 创建日期：2008-4-18
 */
public class DocumentUndoManager extends UndoManager
{
	/*
	 * 修改jdk的实现，改为超过撤销次数时，只删除撤销列表中处于队列前的内容
	 * 
	 * @see javax.swing.undo.UndoManager#trimForLimit()
	 */
	protected void trimForLimit()
	{
		int limit = getLimit();
		if (limit >= 0)
		{
			int size = edits.size();
			if (size > limit)
			{
				// 如果超出队列大小，则只保留其中的3/4的大小
				int left = limit * 3 / 4;
				int deleteFrom = 0;
				int deleteto = size - left - 1;
				trimEdits(deleteFrom, deleteto);
			}
		}
	}
}
