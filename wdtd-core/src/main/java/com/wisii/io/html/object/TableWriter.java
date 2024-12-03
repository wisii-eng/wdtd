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
 * @TableWriter.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.object;

import java.util.List;

import com.wisii.io.html.HtmlContext;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableColumn;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-5-31
 */
public class TableWriter implements ObjectWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.io.html.object.ObjectWriter#getString(com.wisii.wisedoc.document
	 * .CellElement, com.wisii.io.html.HtmlContext)
	 */
	@Override
	public String getString(CellElement element, HtmlContext context)
	{
		if (!(element instanceof Table))
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Table table = (Table) element;
		List<TableColumn> columns = table.getColumns();
		// new DefaultObjectWriter();
		ObjectWriter writer = WriterFactory.getWriter(columns.get(0));
		for (TableColumn column : columns)
		{
			sb.append(writer.getString(column, context));
		}
		List<CellElement> children = element.getAllChildren();
		for (CellElement child : children)
		{
			sb.append(writer.getString(child, context));
		}
		return sb.toString();
	}

}
