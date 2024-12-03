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
 * @TextInlineReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-13
 */
public class TextInlineReader extends AbstractHandler
{
	// protected com.wisii.wisedoc.document.attribute.Attributes _attributes;
	protected final String BINDINGDATAREFID = "datarefid";
	protected final String ATTRIBUTEREFID = "attrefid";
	protected final String TEXTTAG = "textinline";
	protected String datarefid;
	protected String attrefid;
	String text;
	List<Inline> _inlines;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#startElement(java.lang
	 * .String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		datarefid = null;
		attrefid = null;
		text = null;
		if (TEXTTAG.equals(qname))
		{
			datarefid = atts.getValue(BINDINGDATAREFID);
			attrefid = atts.getValue(ATTRIBUTEREFID);
		} else
		{
			throw new SAXException("TextInlineReader 类只支持:" + TEXTTAG
					+ "节点的解析，不支持:" + qname + "节点的解析");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#endElement(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (TEXTTAG.equals(qname))
		{
			if (datarefid != null)
			{
				_inlines = generatebindtext();
			} else
			{
				_inlines = generatetext();
			}
		} else
		{
			throw new SAXException("TextInlineReader 类只支持:" + TEXTTAG
					+ "节点的解析，不支持:" + qname + "节点的解析");
		}

	}

	private List<Inline> generatebindtext()
	{
		if (datarefid != null)
		{
			BindNode node = wsdhandler.getNode(datarefid);
			if (node != null)
			{
				List<Inline> inlines = new ArrayList<Inline>();
				com.wisii.wisedoc.document.attribute.Attributes attributes = wsdhandler
						.getAttributes(attrefid);
				Text text = new Text(node);
				Map<Integer,Object> attmap =  null;
				if(attributes != null)
				{
					attmap = attributes.getAttributes();
				}
				TextInline textinline = new TextInline(text, attmap);

				inlines.add(textinline);

				return inlines;
			}
		}
		return null;
	}

	private List<Inline> generatetext()
	{
		if (text != null && !text.isEmpty())
		{
			List<Inline> inlines = new ArrayList<Inline>();
			com.wisii.wisedoc.document.attribute.Attributes attributes = wsdhandler
					.getAttributes(attrefid);

			int size = text.length();
			for (int i = 0; i < size; i++)
			{
				char c = text.charAt(i);
				if (c != '\n')
				{
					Text text = new Text(c);
					Map<Integer,Object> attmap =  null;
					if(attributes != null)
					{
						attmap = attributes.getAttributes();
					}
					TextInline textinline = new TextInline(text, attmap);
					inlines.add(textinline);
				}
			}
			return inlines;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#characters(char[],
	 * int, int)
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException
	{
		if (text == null)
		{
			text = "";
		}
		text = text + new String(ch, start, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public List<Inline> getObject()
	{
		// TODO Auto-generated method stub
		return _inlines;
	}

}
