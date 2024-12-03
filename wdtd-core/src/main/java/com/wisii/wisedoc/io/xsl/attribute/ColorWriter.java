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
 * @ColorWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.attribute;

import java.awt.Color;

import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-21
 */
public class ColorWriter extends OutputAttributeWriter
{

	public final int DEFAULTINDEX = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter#write(java.lang
	 * .Object)
	 */
	public String write(Object value)
	{
		StringBuffer output = new StringBuffer();
		if (!IoXslUtil.isStandard())
		{
			if (value instanceof WiseDocColor)
			{
				WiseDocColor color = (WiseDocColor) value;
				output.append("rgb(");
				output.append(color.getRed());
				output.append(",");
				output.append(color.getGreen());
				output.append(",");
				output.append(color.getBlue());
				output.append(",");
				output.append(color.getAlpha());
				output.append(",");
				output.append(color.getLayer());
				output.append(")");
			} else if (value instanceof Color)
			{
				output.append(getColorValue(value));
			}
		} else
		{
			if (value instanceof Color)
			{
				output.append(getColorValue(value));
			}
		}
		return output.toString();
	}

	public String getColorValue(Object value)
	{
		StringBuffer output = new StringBuffer();
		if (value instanceof Color)
		{
			Color color = (Color) value;
			output.append("rgb(");
			output.append(color.getRed());
			output.append(",");
			output.append(color.getGreen());
			output.append(",");
			output.append(color.getBlue());
			output.append(",");
			output.append(color.getAlpha());
			output.append(",");
			output.append(DEFAULTINDEX);
			output.append(")");
		}
		return output.toString();
	}

}
