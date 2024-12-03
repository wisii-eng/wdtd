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
 * @SinglePageMasterReferenceReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SinglePageMasterReference;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-10
 */
public class SinglePageMasterReferenceReader extends AbstractHandler {
	private final String ROOTNAME = "singlepagemasterreference";
	private String SPMNAME = "simplepagemaster";
	private boolean isfirstnode = true;
	private SinglePageMasterReference spmr;
	SimplePageMasterReader spmreader = new SimplePageMasterReader();

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
						"SinglePageMasterReferenceReader的根节点必须是:" + ROOTNAME
								+ "实际是:" + qname);
			}
			spmr = null;
			isfirstnode = false;
			return;
		}
		spmreader.startElement(uri, localname, qname, atts);
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
			spmreader.endElement(uri, localname, qname);
			if (SPMNAME.equals(qname)) {
				SimplePageMaster spm = spmreader.getObject();
				if (spm != null) {
					spmr = new SinglePageMasterReference(spm);
				}
				spmreader.init();
			}
		} else {
			isfirstnode = true;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public Object getObject() {
		return spmr;
	}
}
