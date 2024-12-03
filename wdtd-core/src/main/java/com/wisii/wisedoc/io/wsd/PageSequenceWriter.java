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
 * @PageSquenceWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.Iterator;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.io.IOFactory;

/**
 * 类功能描述：PageSequence对象writer类。PageSequence对象具有页布局属性等 大对象属性，需针对这些大对象属性做特殊处理
 * 
 * 作者：zhangqiang 创建日期：2008-9-19
 */
public final class PageSequenceWriter extends AbstractElementWriter {
	private final String PAGESQUENCETAG = "pagesquence";
	private final String DEFAULTMASTER = "defaultmaster";
	private final SimplePageMasterWriter spmw = new SimplePageMasterWriter();
	private final PageSequenceMasterWriter psmw = new PageSequenceMasterWriter();

	public String write(CellElement element)
	{
		if (element == null || !(element instanceof PageSequence))
		{
			return "";
		}
		String returns = "";
		PageSequence ps = (PageSequence) element;
		String attributestring = "";
		Attributes psatt = ps.getAttributes();
		if (psatt != null)
		{
			Map<Integer, Object> psattmap = psatt.getAttributes();
			if (psattmap != null)
			{
				psattmap.remove(Constants.PR_SIMPLE_PAGE_MASTER);
				psattmap.remove(Constants.PR_PAGE_SEQUENCE_MASTER);

				if (!psattmap.isEmpty())
				{
					String refid = DocumentWirter
							.getAttributesID(new Attributes(psattmap));
					if (refid != null && !refid.isEmpty())
					{
						attributestring = SPACETAG + ATTRIBUTEREFID + EQUALTAG
								+ QUOTATIONTAG + refid + QUOTATIONTAG
								+ SPACETAG;
					}
				}
			}
		}

		returns = returns + TAGBEGIN + PAGESQUENCETAG + attributestring
				+ TAGEND + LINEBREAK;
		returns = returns + generateAttributeString(ps);
		returns = returns + generateChildrenString(ps);
		returns = returns + TAGENDSTART + PAGESQUENCETAG + TAGEND + LINEBREAK;
		return returns;
	}

	/*
	 * 
	 * 生成PageSequence子对象（即flow的代码）代码
	 * 
	 * @param ps:pagesequence对象
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private String generateChildrenString(PageSequence ps) {
		Iterator<CellElement> it = ps.getChildren();
		String returns = "";
		while(it.hasNext())
		{
			CellElement el = it.next();
			returns = returns + IOFactory.getElementWriterFactory(IOFactory.WSD).getWriter(el).write(el);
		}
		return returns;
	}

	/*
	 * 
	 * 生成PageSequence属性代码
	 * 
	 * @param ps:pagesequence对象
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private String generateAttributeString(PageSequence ps) {
		String returns = "";
		Object spmobject = ps.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);
//		如果默认页布局对象不为空，则生成默认页布局对象的相关代码
		if (spmobject != null) {
			SimplePageMaster spm = (SimplePageMaster) spmobject;
			returns = returns + TAGBEGIN + DEFAULTMASTER + TAGEND + LINEBREAK;
			returns = returns + spmw.write(spm);
			returns = returns + TAGENDSTART + DEFAULTMASTER + TAGEND
					+ LINEBREAK;
		}
//		否则生成pagesquenceMaster相关代码
		else {
			PageSequenceMaster psm = (PageSequenceMaster) ps
					.getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER);
			returns = returns + psmw.write(psm);
		}
		return returns;
	}

}
