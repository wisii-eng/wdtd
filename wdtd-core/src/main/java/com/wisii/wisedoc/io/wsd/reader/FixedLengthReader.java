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
 * @FixedLengthReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-9
 */
public class FixedLengthReader extends SingleAttributeReader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.SingleAttributeReader#read(int,
	 * java.lang.String)
	 */
	public Object read(int key, String value) {
		return generateLengthvalue(value);
	}

	FixedLength generateLengthvalue(String value)
	{
		FixedLength length = null;
		if (value != null && !value.trim().equals(""))
		{
			String slength = value;
			int precion = -1;
			int indexduhao = value.indexOf(",");
			if (indexduhao > 0)
			{
				slength = value.substring(0, indexduhao);
				if (indexduhao < value.length())
				{
					String precions = value.substring(indexduhao + 1);
					precion = Integer.parseInt(precions);
				}
			}
			slength = slength.trim();
			String lengths = "";
			String units = "";
			int size = slength.length();
			int index = -1;
			for (int i = 0; i < size; i++)
			{
				// 取出其中的长度数字值
				char c = slength.charAt(i);
				//处理负数
				if (c == '-' && i == 0)
				{

				}
				// 找到第一个不是数字的字符
				else if ((c < '0' || c > '9') && c != '.')
				{
					index = i;
					break;
				}
				lengths = lengths + c;
			}
			try
			{
				units = slength.substring(index).trim();
			} catch (IndexOutOfBoundsException e)
			{
				LogUtil.debug("无有效单位,将取默认单位pt", e);
				units = "";
			}
			if (!lengths.equals(""))
			{
				try
				{
					double len = Double.parseDouble(lengths);
					length = new FixedLength(len, units,precion);
				} catch (Exception e)
				{
					LogUtil.debug(e);
				}

			}

		}
		return length;
	}

}
