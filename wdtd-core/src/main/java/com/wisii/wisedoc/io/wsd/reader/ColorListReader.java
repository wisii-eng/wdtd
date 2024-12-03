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
 * @ColorListReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.io.AttributeWriter;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-6-3
 */
public class ColorListReader extends SingleAttributeReader
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.SingleAttributeReader#read(int,
	 * java.lang.String)
	 */
	@Override
	public Object read(int key, String value)
	{
		if (value != null && !value.isEmpty())
		{
			String[] attstrings = value.split(",");
			List<WiseDocColor> tcsstyles = new ArrayList<WiseDocColor>();
			for (int i = 0; i < attstrings.length; i++)
			{
				tcsstyles.add(getWiseDocColor(attstrings[i]));
			}
			return tcsstyles;
		}
		return null;
	}

	private WiseDocColor getWiseDocColor(String textvalue)
	{
		if (textvalue == null || AttributeWriter.NULL.equals(textvalue))
		{
			return null;
		} else
		{
			try
			{
				Integer rgb = Integer.parseInt(textvalue);
				return new WiseDocColor(new Color(rgb));
			} catch (Exception e)
			{
				return null;
			}

		}
	}

}