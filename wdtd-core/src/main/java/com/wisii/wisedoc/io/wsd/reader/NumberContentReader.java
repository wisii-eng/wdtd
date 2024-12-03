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
 * @NumberContentReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.NumberContent;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：NumberContent Reader类
 *
 * 作者：zhangqiang
 * 创建日期：2009-5-25
 */
public class NumberContentReader extends SingleAttributeReader
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.wsd.reader.SingleAttributeReader#read(int, java.lang.String)
	 */
	@Override
	public Object read(int key, String value)
	{
		if (value != null)
		{
			if (value.startsWith("N@"))
			{
				String valuestring = value.substring(2);
				Number number = null;
				try
				{
					if (valuestring.contains("."))
					{
						number = Double.parseDouble(valuestring);
					} else
					{
						number = Integer.parseInt(valuestring);
					}
				} catch (NumberFormatException e)
				{
					LogUtil.debugException(key + "属性的属性值：" + value + "不是合法的数字",
							e);
				}
				if (number != null)
				{
					return new NumberContent(number);
				}
			} else
			{
				BindNode node = wsdhandler.getNode(value);
				if (node != null)
				{
					return new NumberContent(node);
				}
			}
		}
		return null;
	}

}
