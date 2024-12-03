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
 * @RepeatablePageMasterAlternativesWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.List;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.io.ElementWriter;

/**
 * 类功能描述：RepeatablePageMasterAlternatives的Writer类
 * 
 * 作者：zhangqiang 创建日期：2008-9-22
 */
public final class RepeatablePageMasterAlternativesWriter extends
		SubSequenceSpecifierWriter {
	private final String NAME = "repeatablepagemasteralternatives";
	private final ConditionalPageMasterReferenceWriter cpmrwriter = new ConditionalPageMasterReferenceWriter();

	String write(SubSequenceSpecifier seqspecifier) {
		String returns = "";
		if (seqspecifier != null
				&& seqspecifier instanceof RepeatablePageMasterAlternatives) {
			returns = returns + ElementWriter.TAGBEGIN + NAME + " ";
			RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) seqspecifier;
			int maxrep = rpma.getMaximumRepeats();

			int enumn = -1;
			if (maxrep == RepeatablePageMasterAlternatives.INFINITE) {
				enumn = Constants.EN_NO_LIMIT;
			}
			EnumNumber en = new EnumNumber(enumn, maxrep);
			returns = returns
					+ attiofactory.getAttributeWriter(en.getClass()).write(
							Constants.PR_MAXIMUM_REPEATS, en);
			returns = returns + ElementWriter.TAGEND + ElementWriter.LINEBREAK;
			returns = returns
					+ generatePageMasterRefsString(rpma.getPageMasterRefs());
			returns = returns + ElementWriter.TAGENDSTART + NAME
					+ ElementWriter.TAGEND;

		}
		return returns;
	}

	private String generatePageMasterRefsString(
			List<ConditionalPageMasterReference> pageMasterRefs) {
		String returns = "";
		if (pageMasterRefs != null && !pageMasterRefs.isEmpty()) {
			int size = pageMasterRefs.size();
			for (int i = 0; i < size; i++) {
				returns = returns + cpmrwriter.write(pageMasterRefs.get(i));
			}
		}
		return returns;
	}

}
