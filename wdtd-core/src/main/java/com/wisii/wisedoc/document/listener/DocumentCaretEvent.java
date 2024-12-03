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
 * @DocumentCaretEvent.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.listener;

import java.util.EventObject;

import com.wisii.wisedoc.document.DocumentPosition;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-11-7
 */
public class DocumentCaretEvent extends EventObject
{
	// 原光标
	private DocumentPosition oldpos;
	// 新光标
	private DocumentPosition newpos;

	public DocumentCaretEvent(Object source, DocumentPosition oldpos,
			DocumentPosition newpos)
	{
		super(source);
		this.oldpos = oldpos;
		this.newpos = newpos;
	}

	/**
	 * @返回 oldpos变量的值
	 */
	public DocumentPosition getOldpos()
	{
		return oldpos;
	}

	/**
	 * @返回 newpos变量的值
	 */
	public DocumentPosition getNewpos()
	{
		return newpos;
	}
}
