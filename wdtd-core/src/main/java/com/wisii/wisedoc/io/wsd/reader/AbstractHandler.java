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
 * @AbstractHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-9
 */
public abstract class AbstractHandler
{
	protected final String ATTRIBUTEREFID = "attrefid";
	protected AbstractElementsHandler wsdhandler;

	public abstract void startElement(String uri, String localname,
			String qname, Attributes atts) throws SAXException;

	public abstract void endElement(String uri, String localname, String qname)
			throws SAXException;

	public void characters(char ch[], int start, int length)
			throws SAXException
	{
	}

	/**
	 * 
	 * 获得解析得到的对象
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public abstract Object getObject();

	/**
	 * 
	 *重新初始化handle对象
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void init()
	{
	}

	void ininHandler(AbstractElementsHandler handler)
	{
		this.wsdhandler = handler;
	}
}
