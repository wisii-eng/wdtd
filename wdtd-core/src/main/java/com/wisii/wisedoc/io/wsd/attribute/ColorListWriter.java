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
 * @ColorListWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.awt.Color;
import java.util.List;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-6-3
 */
public class ColorListWriter extends AbstractAttributeWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof List))
		{
			return "";
		}
		String returns = "";
		List<Color> colorlist = (List<Color>) value;
		if (colorlist != null && !colorlist.isEmpty())
		{
			returns = returns + getKeyName(key);
			String colorstring = null;
			for (Color color : colorlist)
			{
				String btextvalue = null;
				if (color == null)
				{
					btextvalue = NULL;
				} else
				{
					btextvalue = "" + color.getRGB();
				}
				if (colorstring == null)
				{
					colorstring = btextvalue;
				} else
				{
					colorstring = colorstring + "," + btextvalue;
				}
			}
			returns = returns + EQUALTAG + QUOTATIONTAG
					+ colorstring+ QUOTATIONTAG + SPACETAG;
		}
		return returns;
	}

}
