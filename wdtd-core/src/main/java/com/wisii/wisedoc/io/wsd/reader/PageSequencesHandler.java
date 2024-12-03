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
 * @PageSequencesHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-9
 */
public class PageSequencesHandler extends AbstractHandler
{
	protected boolean isfirstnode = true;
	private final String PAGESQUENCETAG = "pagesquence";
	private final String DEFAULTMASTER = "defaultmaster";
	private final String PAGESEQUENCEMASTER = "pagesequencemaster";
	private final String FLOW = "flow";
	private final String STATICCONTENT = "staticcontent";
	protected SimplePageMasterReader DPMR = new SimplePageMasterReader();
	protected PageSequenceMasterReader PSMR = new PageSequenceMasterReader();
	protected AbstractElementReader FLOWR = new FlowElementReader();
	protected Map<String, AbstractHandler> _handlermap;
	protected AbstractHandler _currenthandler;
	List<PageSequence> pses;
	List<CellElement> flows;
	com.wisii.wisedoc.document.attribute.Attributes pagesequenceatt;

	PageSequencesHandler()
	{
		init();
	}

	void init()
	{
		isfirstnode = true;
		_handlermap = new HashMap<String, AbstractHandler>();
		_handlermap.put(DEFAULTMASTER, DPMR);
		_handlermap.put(PAGESEQUENCEMASTER, PSMR);
		_handlermap.put(FLOW, FLOWR);
		_handlermap.put(STATICCONTENT, FLOWR);
		pses = new ArrayList<PageSequence>();
		flows = new ArrayList<CellElement>();
	}

	void ininHandler(AbstractElementsHandler handler)
	{
		super.ininHandler(handler);
		for (AbstractHandler subhandler : _handlermap.values())
		{
			subhandler.ininHandler(this.wsdhandler);
		}
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
		if (isfirstnode)
		{
			if (!PAGESQUENCETAG.equals(qname))
			{
				throw new SAXException("节点必须:" + PAGESQUENCETAG);
			}
			String id = atts.getValue(ATTRIBUTEREFID);
			if (id != null)
			{
				pagesequenceatt = wsdhandler.getAttributes(id);
			}
			isfirstnode = false;
		}
		AbstractHandler hander = _handlermap.get(qname);
		if (hander != null)
		{
			_currenthandler = hander;
			_currenthandler.init();
			if (_currenthandler instanceof FlowElementReader)
			{
				_currenthandler.startElement(uri, localname, qname, atts);
			}
			return;
		}
		if (_currenthandler != null)
		{
			_currenthandler.startElement(uri, localname, qname, atts);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#endElement(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String qname)
			throws SAXException
	{
		if (PAGESQUENCETAG.equals(qname))
		{
			_currenthandler = null;
			Map<Integer, Object> psatt = new HashMap<Integer, Object>();
			if (pagesequenceatt != null)
			{
				psatt.putAll(pagesequenceatt.getAttributes());
				pagesequenceatt = null;
			}

			SimplePageMaster spm = DPMR.getObject();
			if (spm != null)
			{
				psatt.put(Constants.PR_SIMPLE_PAGE_MASTER, spm);
			}
			PageSequenceMaster psm = PSMR.getObject();
			if (psm != null)
			{
				psatt.put(Constants.PR_PAGE_SEQUENCE_MASTER, psm);
			}
			PageSequence ps = new PageSequence(psatt);
			ps.insert(flows, 0);
			pses.add(ps);
			flows = new ArrayList<CellElement>();
			isfirstnode = true;
			DPMR.init();
			PSMR.init();
		}

		if (_currenthandler != null)
		{
			_currenthandler.endElement(uri, localName, qname);
			if (FLOW.equals(qname) || STATICCONTENT.equals(qname))
			{
				Object flow = _currenthandler.getObject();
				if (flow instanceof Flow)
				{
					flows.add((Flow) flow);
				}
			}
		}

	}

	public void characters(char ch[], int start, int length)
			throws SAXException
	{
		if (_currenthandler != null)
		{
			_currenthandler.characters(ch, start, length);
		}
	}

	public List<PageSequence> getObject()
	{
		return pses;
	}
}
