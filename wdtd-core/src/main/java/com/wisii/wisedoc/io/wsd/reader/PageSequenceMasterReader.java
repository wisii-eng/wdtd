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
 * @PageSequenceMasterReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;

/**
 * 类功能描述：PageSequenceMaster reader
 * 
 * 作者：zhangqiang 创建日期：2008-10-10
 */
public class PageSequenceMasterReader extends AbstractHandler
{
	private final String RPMANAME = "repeatablepagemasteralternatives";
	private final String RPMRNAME = "repeatablepagemasterreference";
	private final String SPMRNAME = "singlepagemasterreference";
	private boolean isrootnode = true;
	protected Map<String, AbstractHandler> readermap = new HashMap<String, AbstractHandler>();
	AbstractHandler currenthander;
	List<SubSequenceSpecifier> subSequenceSpecifiers;

	PageSequenceMasterReader()
	{
		readermap.put(RPMANAME, new RepeatablePageMasterAlternativesReader());
		readermap.put(RPMRNAME, new RepeatablePageMasterReferenceReader());
		readermap.put(SPMRNAME, new SinglePageMasterReferenceReader());
		init();
	}

	void init()
	{
		subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
	}

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
		if (isrootnode)
		{
			AbstractHandler handler = readermap.get(qname);
			if (handler == null)
			{
				throw new SAXException("节点名称(" + qname
						+ ")不正确，PageSequenceMaster节点下不容许有该节点");
			}
			currenthander = handler;
			isrootnode = false;
		}
		if (currenthander != null)
		{
			currenthander.startElement(uri, localname, qname, atts);
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
		if (currenthander != null)
		{
			currenthander.endElement(uri, localname, qname);
		}
		AbstractHandler handler = readermap.get(qname);
		if (handler != null)
		{
			SubSequenceSpecifier subss = (SubSequenceSpecifier) currenthander
					.getObject();
			if (subss != null)
			{
				subSequenceSpecifiers.add(subss);
			}
			isrootnode = true;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public PageSequenceMaster getObject()
	{
		if (subSequenceSpecifiers != null && !subSequenceSpecifiers.isEmpty())
		{
			PageSequenceMaster psm = new PageSequenceMaster(
					subSequenceSpecifiers);
			return psm;
		}
		return null;
	}

}
