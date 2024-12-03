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
 * @InsertEncryptInlineTextAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.EncryptTextInline;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-17
 */
public class InsertEncryptTextAction extends InsertObjectAction
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.action.InsertObjectAction#creatCells()
	 */
	@Override
	protected List<CellElement> creatCells()
	{
		List<CellElement> list = new ArrayList<CellElement>();
		list.add(new EncryptTextInline(null));
		return list;
	}

}