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
 * @WiseDocColorReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.awt.Color;

import com.wisii.wisedoc.document.attribute.WiseDocColor;

/**
 * 类功能描述：WiseDocColor reader类
 * 
 * 作者：zhangqiang 创建日期：2008-10-27
 */
public class WiseDocColorReader extends SingleAttributeReader
{

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
			try
			{
				int index = value.indexOf(",");
				if (index > 0)
				{
					String rgbs = value.substring(0, index);
					int rgb = Integer.parseInt(rgbs);
					int layer = 0;
					if (index < value.length())
					{
						String layers = value.substring(index + 1);
						layer = Integer.parseInt(layers);
					}
					return new WiseDocColor(new Color(rgb), layer);
				}
//				如果只有颜色值
				else
				{
					int rgb = Integer.parseInt(value);
					return new WiseDocColor(new Color(rgb), 0);
				}

			} catch (NumberFormatException e)
			{
				return null;
			}
		}
		return null;
	}

}
