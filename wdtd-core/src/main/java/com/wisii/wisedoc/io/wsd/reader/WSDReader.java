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
 * @WSDReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.Reader;
import com.wisii.wisedoc.io.xsl.util.DomParse;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：读wsd文件的功能
 * 
 * 作者：zhangqiang 创建日期：2008-8-14
 */
public class WSDReader implements Reader {
	private WsdReaderHandler handler = new WsdReaderHandler();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Reader#read(java.io.InputStream)
	 */
	public Document read(InputStream instream) throws IOException {
		try
		{
			DomParse dp = new DomParse(instream);
			String docstring = dp.getString();
			if (docstring != null && !docstring.isEmpty())
			{
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				SAXParser saxParser = saxParserFactory.newSAXParser();
				ByteArrayInputStream bin = new ByteArrayInputStream(docstring.getBytes("UTF-8"));
				saxParser.parse(bin, handler);
				bin.close();
			} else
			{
				return null;
			}
			instream.close();
			return handler.getDocument();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.debugException("读wsd文件错误", e);
			return null;
		}
	}

}
