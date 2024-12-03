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
 * @RepeatablePageMasterReferenceReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterReference;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-10
 */
public class RepeatablePageMasterReferenceReader extends
		AbstractAttElementReader {
	private final String ROOTNAME = "repeatablepagemasterreference";
	private String SPMNAME = "simplepagemaster";
	private boolean isfirstnode = true;
	SimplePageMasterReader spmr = new SimplePageMasterReader();
	// 最大重复次数
	private EnumNumber _maximumRepeats;
	private RepeatablePageMasterReference rpmr;

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
			rpmr = null;
			_maximumRepeats = null;
			com.wisii.wisedoc.document.attribute.Attributes attrs = createattributes(atts);
			if (attrs != null) {
				_maximumRepeats = (EnumNumber) attrs
						.getAttribute(Constants.PR_MAXIMUM_REPEATS);
			}
			isfirstnode = false;
			return;
		}
		spmr.startElement(uri, localname, qname, atts);
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
			spmr.endElement(uri, localname, qname);
			if (SPMNAME.equals(qname)) {
				SimplePageMaster spm = spmr.getObject();
				if (spm != null) {
					rpmr = new RepeatablePageMasterReference(_maximumRepeats,
							spm);
				}
				spmr.init();
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
	public RepeatablePageMasterReference getObject() {
		// TODO Auto-generated method stub
		return rpmr;
	}

}
