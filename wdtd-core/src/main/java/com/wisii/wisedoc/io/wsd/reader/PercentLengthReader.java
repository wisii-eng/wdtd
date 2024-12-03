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
 * @PercentLengthReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：PercentLength读写类
 * 
 * 作者：zhangqiang 创建日期：2008-12-19
 */
public class PercentLengthReader extends SingleAttributeReader
{
	private FixedLengthReader fixlenreader = new FixedLengthReader();
	private EnumLengthReader enumlenreader = new EnumLengthReader();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.SingleAttributeReader#read(int,
	 * java.lang.String)
	 */
	public Object read(int key, String value)
	{
		if (value != null && !value.isEmpty())
		{
			String[] values = value.split(",");
			String factors = values[0];
			try
			{
				double factor = Double.parseDouble(factors);
				if (values.length > 1)
				{
					String basetypes = values[1];
					try
					{
						int basetype = Integer.parseInt(basetypes);
						Object baselen = null;
						if (values.length > 2)
						{
							baselen = fixlenreader.read(key, values[2]);
							if (baselen == null)
							{
								baselen = enumlenreader.read(key, values[2]);
							}
						}
						LengthBase lb = null;
						if (baselen instanceof Length)
						{
							lb = new LengthBase((Length) baselen, basetype);
						} else
						{
							lb = new LengthBase(basetype);
						}
						return new PercentLength(factor, lb);
					} catch (NumberFormatException e)
					{
						LogUtil.infoException("需要basetype属性", e);
					}
				}

			} catch (NumberFormatException e)
			{
				LogUtil.debugException("解析：" + key + "属性时出错", e);
				return null;
			}
		}
		return null;
	}

}
