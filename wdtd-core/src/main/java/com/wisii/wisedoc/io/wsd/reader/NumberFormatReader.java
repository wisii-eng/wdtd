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
 * @NumberFormatReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;
import com.wisii.wisedoc.document.attribute.NumberFormat;

/**
 * 类功能描述：NumberFormat属性reader类
 *
 * 作者：zhangqiang
 * 创建日期：2008-11-12
 */
public class NumberFormatReader extends SingleAttributeReader
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.wsd.reader.SingleAttributeReader#read(int, java.lang.String)
	 */
	public Object read(int key, String value)
	{
		if (value != null && !value.isEmpty())
		{
			String[] s = value.split(" ");
			Integer decimaldigit;
			try
			{
				decimaldigit = Integer.parseInt(s[0]);
			} catch (NumberFormatException ex)
			{
				decimaldigit = 0;
			}
//			String decimalseparator = "";
//			String thousandseparator = "";
			boolean hasthousseparator=true;
			boolean isbaifenbi=false;
			if (s.length > 2)
			{
				if ("false".equals(s[1]))
				{
					hasthousseparator = false;
				}
				if (s[2] != null)
				{
					isbaifenbi = Boolean.parseBoolean(s[2]);
				}
			} else if (s.length > 1)
			{
				if ("false".equals(s[1]))
				{
					hasthousseparator = false;
				}
			}

			return new NumberFormat(decimaldigit, hasthousseparator,
					isbaifenbi);

		}
		return null;
	}

}
