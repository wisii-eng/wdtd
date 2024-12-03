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
 * @WSDElementWriterFactory.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.ElementWriterFactory;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-14
 */
public class WSDElementWriterFactory implements ElementWriterFactory {

	private Map<Class, ElementWriter> map = new HashMap<Class, ElementWriter>();
	private ElementWriter DEFAULTWRITER = new DefaultElementWriter();
	public WSDElementWriterFactory()
	{
		init();
	}
	private void init() {
		map.put(PageSequence.class, new PageSequenceWriter());
		map.put(Block.class, new BlockWriter());
		map.put(TableCell.class, new TableCellWriter());
		map.put(TextInline.class, new TextInlineWriter());
		map.put(TableContents.class, new TableContentsWriter());
		map.put(ZiMoban.class, new ZiMobanWriter());
		map.put(TableRow.class, new TableRowWriter());
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.ElementWriterFactory#getWriter(com.wisii.wisedoc
	 * .document.Element)
	 */
	public ElementWriter getWriter(CellElement element) {
		//这个地方必须返回一个新的TableRowWriter，否则嵌套表格的话字表返回时会覆盖主表的信息
		if(element instanceof TableRow)
		{
			return new TableRowWriter();
		}
		ElementWriter writer = map.get(element.getClass());
		if(writer == null)
		{
			writer = DEFAULTWRITER;
		} 
		return writer;
	}

}
