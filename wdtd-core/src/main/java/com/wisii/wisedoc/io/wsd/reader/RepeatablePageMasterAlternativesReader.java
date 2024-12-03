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
 * @RepeatablePageMasterAlternativesReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;

/**
 * 类功能描述：RepeatablePageMasterAlternatives Reader类
 * 
 * 作者：zhangqiang 创建日期：2008-10-10
 */
public class RepeatablePageMasterAlternativesReader extends
		AbstractAttElementReader {
	private final String ROOTNAME = "repeatablepagemasteralternatives";
	private final String CPMNAME = "conditionalpagemasterreference";
	private boolean isfirstnode = true;
	ConditionalPageMasterReferenceReader cpmrreader = new ConditionalPageMasterReferenceReader();
	// 最大重复次数
	private EnumNumber _maximumRepeats;

	// 条件页布局
	private List<ConditionalPageMasterReference> _conditionalPageMasterRefs;

	RepeatablePageMasterAlternativesReader() {
		init();
	}

	void init() {
		_conditionalPageMasterRefs = new ArrayList<ConditionalPageMasterReference>();
		isfirstnode = true;
		_maximumRepeats = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#startElement(java.lang
	 * .String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException {
		if (isfirstnode) {
			if (!ROOTNAME.equals(qname)) {
				throw new SAXException(
						"RepeatablePageMasterAlternativesReader的根节点必须是:"
								+ ROOTNAME + "实际是:" + qname);
			}
			_maximumRepeats = null;
			com.wisii.wisedoc.document.attribute.Attributes attrs = createattributes(atts);
			if (attrs != null) {
				_maximumRepeats = (EnumNumber) attrs
						.getAttribute(Constants.PR_MAXIMUM_REPEATS);
			}
			isfirstnode = false;
			return;
		}
		cpmrreader.startElement(uri, localname, qname, atts);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#endElement(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localname, String qname)
			throws SAXException {
		if (!ROOTNAME.equals(qname)) {
			cpmrreader.endElement(uri, localname, qname);
			if (CPMNAME.equals(qname)) {
				ConditionalPageMasterReference cpmr = cpmrreader.getObject();
				if (cpmr != null) {
					_conditionalPageMasterRefs.add(cpmr);
				}
			}
		}
		else
		{
			isfirstnode = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public SubSequenceSpecifier getObject() {
		if (_conditionalPageMasterRefs != null
				&& !_conditionalPageMasterRefs.isEmpty()) {
			return new RepeatablePageMasterAlternatives(_maximumRepeats,
					_conditionalPageMasterRefs);
		}
		return null;
	}

}
