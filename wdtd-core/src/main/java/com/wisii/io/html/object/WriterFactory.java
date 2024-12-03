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
 * @WriterFactory.java
 *                     北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.object;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.PositionInline;
import com.wisii.wisedoc.document.Table;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang
 * 创建日期：2012-5-31
 */
public class WriterFactory
{
	public static ObjectWriter getWriter(CellElement cell)
	{
		if (cell instanceof ImageInline)
		{
			return new ImageWtriter();
		} else if (cell instanceof Block)
		{
			return new BlockWriter();
		} else if (cell instanceof Table)
		{
			return new TableWriter();
		}
		else if (cell instanceof NumberTextInline)
		{
			return new NumberTextWriter();
		}
		else if (cell instanceof DateTimeInline)
		{
			return new DateTimeWriter();
		}
		else if (cell instanceof CheckBoxInline)
		{
			return new CheckBoxWriter();
		}
		else if (cell instanceof PositionInline)
		{
			return new PositionInlineWriter();
		}
		return new DefaultObjectWriter();
	}
}
