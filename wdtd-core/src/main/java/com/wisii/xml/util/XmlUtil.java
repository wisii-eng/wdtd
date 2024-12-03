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
 * @XmlUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.xml.util;

/**
 * 类功能描述：
 *
 * 作者：wisii
 * 创建日期：2013-3-14
 */
public class XmlUtil
{
	public static boolean isChar(char ucs4char)
	{
		return ((ucs4char >= 0x0020 && ucs4char <= 0xD7FF)
				|| ucs4char == 0x000A || ucs4char == 0x0009
				|| ucs4char == 0x000D
				|| (ucs4char >= 0xE000 && ucs4char <= 0xFFFD) || (ucs4char >= 0x10000 && ucs4char <= 0x10ffff));
	}
	/*
	 * 判断两个指定节点名和namespace的是否相同
	 */
	public static boolean isNodeEqual(String name1, String namespace1,
			String name2, String namespace2)
	{
		return name1 != null
				&& name1.equals(name2)
				&& ((namespace1 == null && namespace2 == null) || (namespace1 != null && namespace1
						.equals(namespace2)));
	}
	/**
	 * 
	 * 对源字符串坐特殊处理，以生成符合XMl标准得字符串 替换掉里面得特殊字符（“&”，“<”等五个特殊字符）
	 * 
	 * @param src
	 *            ：要转换得字符串
	 * @return 符合XMl要求得字符串
	 * @exception
	 */
	public static String getXmlText(String src)
	{
		String value = null;
		if (src != null)
		{   
			char[] cs=src.toCharArray();
			StringBuffer sb=new StringBuffer();
			for(char c:cs)
			{
				if(c=='&')
				{
					sb.append("&amp;");
				}
				else if(c=='<')
				{
					sb.append("&lt;");
				}
				else if(c=='>')
				{
					sb.append("&gt;");
				}
				else if(c=='\'')
				{
					sb.append("&apos;");
				}
				else if(c=='"')
				{
					sb.append("&quot;");
				}
				else if(c=='\n')
				{
					sb.append("&#13;&#10;");
				}
				else
				{
					sb.append(c);
				}
			}
			value=sb.toString();
		}
		return value;
	}
	/**
	 * 
	 * 从xml字符串转成内部字符串
	 * 
	 * @param src
	 *            ：要转换得字符串
	 * @return 符合XMl要求得字符串
	 * @exception
	 */
	public static String fromXmlText(String src)
	{
		String value = null;
		if (src != null)
		{
			value = src.replaceAll("&amp;", "&");
			value = value.replaceAll("&lt;", "<");
			value = value.replaceAll("&gt;", ">");
			value = value.replaceAll("&apos;", "'");
			value = value.replaceAll("&quot;", "\"");
			value = value.replaceAll("&#13;&#10;", "\n");
		}
		return value;
	}
	
}
