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
 * @AttributesHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-9
 */
public class AttributesReader extends AbstractAttElementReader {
	protected final String NAME = "attribute";
	// 属性map
	private Map<String, com.wisii.wisedoc.document.attribute.Attributes> _attributemap = new HashMap<String, com.wisii.wisedoc.document.attribute.Attributes>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#startElement(java.lang
	 * .String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qname,
			Attributes atts) throws SAXException {
		if (qname.equals(NAME)) {
			String id = atts.getValue(ID);
			com.wisii.wisedoc.document.attribute.Attributes attributes = createattributes(atts);
			if (id != null && !id.trim().equals("") && attributes != null) {
				_attributemap.put(id, attributes);
			} else {
				LogUtil
						.debug("com.wisii.wisedoc.io.wsd.reader.AttributesHandler类中解析attribute时出错，无id属性或无任何其他属性");
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public Map<String, com.wisii.wisedoc.document.attribute.Attributes> getObject() {
		return _attributemap;
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
	}

}
