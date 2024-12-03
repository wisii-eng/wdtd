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
 * @NextWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;
import com.wisii.wisedoc.document.attribute.edit.Next;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-12-16
 */
public class NextWriter extends AbstractAttributeWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof Next))
		{
			return "";
		}
		String returns = "";
		Next next = (Next) value;
		String valuestring = next.getName() + TEXTSPLIT + next.getColumn()
				+ TEXTSPLIT + next.getNextcolumn();
		returns = returns + getKeyName(key);
		returns = returns + EQUALTAG + QUOTATIONTAG + valuestring
				+ QUOTATIONTAG + SPACETAG;
		return returns;
	}

}
