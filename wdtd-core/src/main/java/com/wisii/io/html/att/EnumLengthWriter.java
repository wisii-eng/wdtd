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
 * @EnumLength.java
 *                  汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.att;

import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-9-18
 */
public class EnumLengthWriter extends AbstractEnumAttribyteWriter
{

	FixedLengthWriter fixedlenwriter = new FixedLengthWriter();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.io.html.att.AttributeWriter#getValue(java.lang.Object)
	 */
	@Override
	public String getValue(Object value)
	{
		if (value instanceof EnumLength)
		{
			EnumLength enumlength = (EnumLength) value;
			String valuestring;
			int enumnum = enumlength.getEnum();
			if (enumnum > 0)
			{
				valuestring = getEnumString(enumnum);
			} else
			{
				FixedLength fixl = (FixedLength) enumlength.getFixLength();
				valuestring = fixedlenwriter.getValue(fixl);
			}
			return valuestring;
		}
		return "";
	}
}
