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

package com.wisii.wisedoc.document.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentParser
{

	public String TAGBEGIN = "<";

	public String TAGENDSTART = "</";

	public String TAGEND = ">";

	public String LINEBREAK = "\n";

	public String NOCHILDTAGEND = "/>";

	private Document doc;

	public DocumentParser()
	{

	}

	/**
	 * 构造函数。
	 * 
	 * @param doc
	 *            通过schema文件产生的文档对象。
	 */
	public DocumentParser(Document doc)
	{
		this.doc = doc;
	}

	public String getDocText()
	{
		String result = "";
		if (doc != null)
		{
			Element rootelement = doc.getDocumentElement();
			result = getElementText(rootelement);
		}
		return result;
	}

	public String getElementText(Element element)
	{
		String result = "";
		result = result + TAGBEGIN;
		result = result + element.getLocalName();
		NamedNodeMap attr = element.getAttributes();
		result = result + getAttributeText(attr);
		result = result + TAGEND;
		if (element != null && element.getTextContent() != null)
		{
			result = result + element.getTextContent().toString();
		}
		NodeList children = element.getChildNodes();
		if (children != null && children.getLength() > 0)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					result = result + getElementText((Element) node);
				}
			}
		}
		result = result + TAGENDSTART;
		result = result + element.getTagName();
		result = result + TAGEND;
		return result;
	}

	public String getAttributeText(NamedNodeMap attrs)
	{
		String result = "";
		if (attrs != null)
		{
			int length = attrs.getLength();
			if (length > 0)
			{
				for (int i = 0; i < length; i++)
				{
					Node current = attrs.item(i);
					result = result + " ";
					result = result + current.getNodeName();
					result = result + "=\"";
					result = result + current.getNodeValue();
					result = result + "\"";
				}
			}
		}

		return result;
	}
}
