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
 * @AttributesWriter.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.att;

import java.util.Map;
import java.util.Set;

import com.wisii.io.html.HtmlContext;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.xsl.util.StyleUtil;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-6-1
 */
public class AttributesWriter
{

	public static String getString(Attributes att, HtmlContext context)
	{
		if (att == null)
		{
			return "";
		}
		return getStringByMap(att.getAttributes(),context);
	}
	public static String getStringByMap(Map<Integer, Object> atts, HtmlContext context)
	{
		if (atts == null||atts.isEmpty())
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(getBoderString(atts));
		sb.append(getBackGroupImage(atts, context));
		Set<Integer> keyit = atts.keySet();
		if (!keyit.isEmpty())
		{
			for (Integer key : keyit)
			{
				if (StyleUtil.isStyleKey(key))
				{
					Object value = atts.get(key);
					if(value==null)
					{
						continue;
					}
					AttributeWriter writer = AttributeWriterFactory
							.getAttributeWriter(key, value);
					sb.append(writer.getString(key, value));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private static String getBackGroupImage(Map<Integer, Object> atts,
			HtmlContext context)
	{
		Object bgimage = atts.get(Constants.PR_BACKGROUND_IMAGE);
		if (bgimage == null)
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		String value = null;
		if (bgimage instanceof String)
		{
			value = (String) bgimage;
		} else if (bgimage instanceof BindNode)
		{
			sb.append("<xsl:variable name=\"content0\" select=\""
					+ context.getRelatePath((BindNode) bgimage) + "\"/>");
			value = "$content0";
		}
		sb.append("background-image: ");
		sb.append(value);
		sb.append(';');
		return sb.toString();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private static String getBoderString(Map<Integer, Object> atts)
	{
		if (atts == null || atts.isEmpty())
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(getBoderSizeString(atts, "border-top",
				Constants.PR_BORDER_BEFORE_STYLE,
				Constants.PR_BORDER_BEFORE_WIDTH,
				Constants.PR_BORDER_BEFORE_COLOR));
		sb.append(getBoderSizeString(atts, "border-bottom",
				Constants.PR_BORDER_AFTER_STYLE,
				Constants.PR_BORDER_AFTER_WIDTH,
				Constants.PR_BORDER_AFTER_COLOR));
		sb.append(getBoderSizeString(atts, "border-left",
				Constants.PR_BORDER_START_STYLE,
				Constants.PR_BORDER_START_WIDTH,
				Constants.PR_BORDER_START_COLOR));
		sb.append(getBoderSizeString(atts, "border-right",
				Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
				Constants.PR_BORDER_END_COLOR));
		sb.append("border-collapse:collapse;border-spacing:0 0;");
		return sb.toString();
	}

	private static String getBoderSizeString(Map<Integer, Object> atts,
			String keyname, int stylekey, int widthkey, int colorkey)
	{
		EnumProperty style = (EnumProperty) atts.get(stylekey);
		CondLengthProperty length = (CondLengthProperty) atts.get(widthkey);
		if (style != null && style.getEnum() != Constants.EN_NONE
				&& length != null)
		{
			StringBuffer sb = new StringBuffer();
			sb.append(keyname);
			sb.append(": ");
			sb.append(AttributeWriterFactory
					.getAttributeWriter(stylekey, style).getValue(style));
			sb.append(' ');
			sb.append(AttributeWriterFactory.getAttributeWriter(widthkey,
					length).getValue(length));
			sb.append(' ');
			Object color = atts.get(colorkey);
			sb.append(AttributeWriterFactory
					.getAttributeWriter(colorkey, color).getValue(color));
			sb.append(';');
			atts.remove(stylekey);
			atts.remove(widthkey);
			atts.remove(colorkey);
			return sb.toString();
		}
		return "";
	}
}
