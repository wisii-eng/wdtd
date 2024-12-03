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
 * @XMLUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io;


/**
 * 类功能描述：XML文件的输入输出工具类
 *
 * 作者：zhangqiang
 * 创建日期：2008-11-13
 */
public final class XMLUtil
{
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
	public static boolean isXMLCharacter(int c){
		return isChar(c);
//		  //根据xml规范中的Character Range检测xml不支持的字符
//		  if(c <= 0xD7FF){
//		   if(c >= 0x20)return true;
//		   else{
//		    if (c == '\n') return true;
//		    if (c == '\r') return true;
//		    if (c == '\t') return true;
//		    return false;
//		   }
//		  }
//		  if (c < 0xE000) return false;  if (c <= 0xFFFD) return true;
//		  if (c < 0x10000) return false;  if (c <= 0x10FFFF) return true;
//		    return false;
//		 }
	}
	/**
	 * 将属性名和值输出
	 * 
	 * @param name
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return output 最终输出结果
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String outputAttributes(String name, String value)
	{
		StringBuffer output = new StringBuffer();
		if (name != null && value != null && !name.isEmpty()
				&& !value.isEmpty())
		{
			output.append(" ");
			output.append(name);
			output.append("=");
			output.append("\"");
			output.append(value);
			output.append("\"");
		}
		return output.toString();
	}
	public static boolean   isChar (int ucs4char)
    {
	// [2] Char ::= #x0009 | #x000A | #x000D
	//			| [#x0020-#xD7FF]
	//	... surrogates excluded!
	//			| [#xE000-#xFFFD]
	// 			| [#x10000-#x10ffff]
	return ((ucs4char >= 0x0020 && ucs4char <= 0xD7FF)
		|| ucs4char == 0x000A || ucs4char == 0x0009
		|| ucs4char == 0x000D
		|| (ucs4char >= 0xE000 && ucs4char <= 0xFFFD)
		|| (ucs4char >= 0x10000 && ucs4char <= 0x10ffff));
    }
}
