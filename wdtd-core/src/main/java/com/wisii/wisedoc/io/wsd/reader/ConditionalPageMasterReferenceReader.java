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
 * @ConditionalPageMasterReferenceReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-10
 */
public class ConditionalPageMasterReferenceReader extends
		AbstractAttElementReader
{
	private final String ROOTNAME = "conditionalpagemasterreference";
	private boolean isfirstnode = true;
	protected SimplePageMasterReader SPMR = new SimplePageMasterReader();
	private ConditionalPageMasterReference cpmr;
	private int _pagePosition;
	private int _oddOrEven;
	private int _blankOrNotBlank;

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
			if (!ROOTNAME.equals(qname.trim()))
			{
				throw new SAXException("ConditionalPageMasterReference 的节点名("
						+ qname + ")不正确");
			}
			cpmr = null;
			com.wisii.wisedoc.document.attribute.Attributes attrs = createattributes(atts);
			_pagePosition = -1;
			_oddOrEven = -1;
			_blankOrNotBlank = -1;
			if (attrs != null)
			{
				EnumProperty posi = (EnumProperty) attrs
						.getAttribute(Constants.PR_PAGE_POSITION);
				if (posi != null)
				{
					int posienum = posi.getEnum();
					if (posienum > -1)
					{
						_pagePosition = posienum;
					} else
					{
						LogUtil.debug(ROOTNAME + "节点的pagePosition属性的属性值("
								+ posi + ")不正确");
					}
				}
				EnumProperty oddeven = (EnumProperty) attrs
						.getAttribute(Constants.PR_ODD_OR_EVEN);
				if (oddeven != null)
				{
					int enumi = oddeven.getEnum();
					if (enumi > -1)
					{
						_oddOrEven = enumi;
					} else
					{
						LogUtil.debug(ROOTNAME + "节点的oddOrEven属性的属性值("
								+ oddeven + ")不正确");
					}
				}
				EnumProperty blankOrNot = (EnumProperty) attrs
						.getAttribute(Constants.PR_BLANK_OR_NOT_BLANK);
				if (blankOrNot != null)
				{
					int enumi = blankOrNot.getEnum();
					if (enumi > -1)
					{
						_blankOrNotBlank = enumi;
					} else
					{
						LogUtil.debug(ROOTNAME + "节点的blankOrNotBlank属性的属性值("
								+ blankOrNot + ")不正确");
					}
				}
			}
			isfirstnode = false;
			return;
		}
		SPMR.startElement(uri, localname, qname, atts);

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
		if (!ROOTNAME.equals(qname.trim()))
		{
			SPMR.endElement(uri, localname, qname);
		} else
		{
			SimplePageMaster spm = SPMR.getObject();
			if (spm != null
					&& (_pagePosition != -1 || _oddOrEven != -1 || _blankOrNotBlank != -1))
			{
				cpmr = new ConditionalPageMasterReference(spm, _pagePosition,
						_oddOrEven, _blankOrNotBlank);
			} else
			{
				throw new SAXException(ROOTNAME + "解析出错，请确认文件是否正确");
			}
			isfirstnode = true;
			SPMR.init();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public ConditionalPageMasterReference getObject()
	{
		return cpmr;
	}

}
