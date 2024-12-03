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
 * @ConditionalPageMasterReferenceWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.io.ElementWriter;

/**
 * 类功能描述：ConditionalPageMasterReference的writer类
 * 
 * 作者：zhangqiang 创建日期：2008-9-22
 */
final class ConditionalPageMasterReferenceWriter {
	private final String NAME = "conditionalpagemasterreference";
	private WSDAttributeIOFactory attiofactory = new WSDAttributeIOFactory();

	String write(ConditionalPageMasterReference cpmr) {
		String returns = "";
		if (cpmr != null) {
			returns = returns + ElementWriter.TAGBEGIN + NAME + " ";
			returns = returns + generateattributesString(cpmr);
			returns = returns + ElementWriter.TAGEND + ElementWriter.LINEBREAK;
			returns = returns
					+ generatpagemasterString(cpmr.getMasterReference());
			returns = returns + ElementWriter.TAGENDSTART + NAME
					+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
		}
		return returns;
	}

	private String generatpagemasterString(SimplePageMaster masterReference) {
		return new SimplePageMasterWriter().write(masterReference);

	}

	private String generateattributesString(ConditionalPageMasterReference cpmr) {
		String returns = "";
		int position = cpmr.getPagePosition();
		if (position == Constants.EN_FIRST || position == Constants.EN_LAST||
				position == Constants.EN_ONLY|| position == Constants.EN_REST|| position == Constants.EN_ANY) 
		{
			EnumProperty ep = new EnumProperty(position, "");
			returns = returns
					+ attiofactory.getAttributeWriter(ep.getClass()).write(
							Constants.PR_PAGE_POSITION, ep);
		}
		int oddoeeven = cpmr.getOddOrEven();
		if (oddoeeven == Constants.EN_EVEN || oddoeeven == Constants.EN_ODD||oddoeeven == Constants.EN_ANY) {
			EnumProperty ep = new EnumProperty(oddoeeven, "");
			returns = returns
					+ attiofactory.getAttributeWriter(ep.getClass()).write(
							Constants.PR_ODD_OR_EVEN, ep);
		}
		int blank = cpmr.getBlankOrNotBlank();
		if (blank == Constants.EN_BLANK || blank == Constants.EN_NOT_BLANK||blank == Constants.EN_ANY) {
			EnumProperty ep = new EnumProperty(blank, "");
			returns = returns
					+ attiofactory.getAttributeWriter(ep.getClass()).write(
							Constants.PR_BLANK_OR_NOT_BLANK, ep);
		}
		return returns;
	}
}
