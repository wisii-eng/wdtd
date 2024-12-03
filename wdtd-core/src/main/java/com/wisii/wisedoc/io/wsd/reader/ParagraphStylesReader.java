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
 * @ParagraphStylesReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.io.wsd.attribute.ParagraphStylesWriter;

/**
 * 类功能描述：段落样式表reader类
 *
 * 作者：zhangqiang
 * 创建日期：2009-3-30
 */
public class ParagraphStylesReader extends AbstractHandler
{
	private List<ParagraphStyles> parastyles = new ArrayList<ParagraphStyles>();

	void init()
	{
		parastyles.clear();
	}

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (ParagraphStylesWriter.PARAGRAPHSTYLE.equals(qname))
		{
			String name = atts.getValue(ParagraphStylesWriter.NAME);
			String attstring = atts.getValue(ParagraphStylesWriter.STYLEID);
			com.wisii.wisedoc.document.attribute.Attributes att = wsdhandler
					.getAttributes(attstring);
			if (name != null && att != null)
			{
				ParagraphStyles paragraphstyle = new ParagraphStyles(name, att
						.getAttributes());
				parastyles.add(paragraphstyle);
			}
		} else
		{
			throw new SAXException("dynamicstyles 节点下的" + qname + "节点为非法");
		}
	}

	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
	}

	public List<ParagraphStyles> getObject()
	{
		return parastyles;
	}

}
