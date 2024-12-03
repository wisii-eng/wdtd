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
 * @KeepPropertyWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.KeepProperty;

/**
 * 类功能描述：KeepProperty属性writer类
 * 
 * 作者：zhangqiang 创建日期：2008-9-18
 */
public class KeepPropertyWriter extends AbstractEnumAttribyteWriter {
	private final String WITHINLINE = "within-line";
	private final String WITHINCOLUMN = "within-column";
	private final String WITHINPAGE = "within-page";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof KeepProperty))
		{
			return "";
		}
		String returns = "";
		KeepProperty keepp = (KeepProperty) value;
		String keyname = getKeyName(key);
		EnumProperty keepinline = keepp.getWithinLine();
		EnumProperty keepcolumn = keepp.getWithinColumn();
		EnumProperty keeppage = keepp.getWithinPage();
		if (keepinline == null && keepcolumn == null && keeppage == null)
		{
			return "";
		} else
		{
			if (keepinline != null)
			{
				returns = returns + keyname + DOTTAG + WITHINLINE + EQUALTAG
						+ QUOTATIONTAG + getEnumString(keepinline.getEnum())
						+ QUOTATIONTAG + SPACETAG;
			}
			if (keepcolumn != null)
			{
				returns = returns + keyname + DOTTAG + WITHINCOLUMN + EQUALTAG
						+ QUOTATIONTAG + getEnumString(keepcolumn.getEnum())
						+ QUOTATIONTAG + SPACETAG;
			}
			if (keeppage != null)
			{
				returns = returns + keyname + DOTTAG + WITHINPAGE + EQUALTAG
						+ QUOTATIONTAG + getEnumString(keeppage.getEnum())
						+ QUOTATIONTAG + SPACETAG;
			}
		}
		return returns;
	}

}
