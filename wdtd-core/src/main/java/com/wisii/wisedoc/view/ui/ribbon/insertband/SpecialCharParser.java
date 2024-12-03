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
 * @SpecialCharParser.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.insertband;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：特殊字符配置文件解析器
 * 
 * 作者：zhangqiang 创建日期：2009-8-11
 */
public final class SpecialCharParser
{
	private String filepath = "configuration/specialcharconfigure.xml";

	/*
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数}: {引入参数说明}
	 * 
	 * @return List<String>: {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	List<String> getSpecialCharMaps()
	{
		try
		{
			FileInputStream filein = new FileInputStream(filepath);
			CharParseHander handler = new CharParseHander();
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(filein, handler);
			return handler.getSpecialCharMaps();
		} catch (Exception e)
		{
			LogUtil.debugException("解析特殊字符配置文件时出错", e);
			return null;
		}

	}

	private class CharParseHander extends DefaultHandler
	{
		private List<String> charmaps;
		private String tabitemtag = "charsitem";
		private String tabitemtitletag = "title";
		private String tabitemcharstag = "chars";

		@Override
		public void startDocument() throws SAXException
		{
			super.startDocument();
			charmaps = new ArrayList<String>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException
		{
			super.startElement(uri, localName, qName, atts);
			if (tabitemtag.equals(qName))
			{
				String title = atts.getValue(tabitemtitletag);
				String chars = atts.getValue(tabitemcharstag);
				if (title != null && !title.trim().isEmpty() && chars != null
						&& !chars.trim().equals(""))
				{
					title = title.trim();
					chars = chars.trim();
					charmaps.add(title);
					charmaps.add(chars);
				}
			}

		}

		public List<String> getSpecialCharMaps()
		{
			return charmaps;
		}

	}
}
