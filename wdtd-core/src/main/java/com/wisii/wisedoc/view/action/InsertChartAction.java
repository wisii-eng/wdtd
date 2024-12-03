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
 * @InsertChartAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.ChartInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-5-19
 */
public class InsertChartAction extends InsertObjectAction
{
	protected List<CellElement> creatCells()
	{
		List<CellElement> list = new ArrayList<CellElement>();
		Map<Integer, Object> att = new HashMap<Integer, Object>();
		att.put(Constants.PR_WIDTH, new FixedLength(13.0f,"cm"));
		att.put(Constants.PR_HEIGHT, new FixedLength(8.0f,"cm"));
		list.add(new ChartInline(att));
		return list;
	}
}
