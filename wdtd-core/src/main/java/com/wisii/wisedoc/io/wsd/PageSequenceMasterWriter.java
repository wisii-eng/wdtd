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
 * @PageSequenceMasterWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterReference;
import com.wisii.wisedoc.document.attribute.SinglePageMasterReference;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.io.ElementWriter;

/**
 * 类功能描述：PageSequenceMaster的writer类
 * 
 * 作者：zhangqiang 创建日期：2008-9-22
 */
class PageSequenceMasterWriter {
	private final String NAME = "pagesequencemaster";
	Map<Class, SubSequenceSpecifierWriter> writermap = new HashMap<Class, SubSequenceSpecifierWriter>();

	PageSequenceMasterWriter() {
		init();
	}

	private void init() {
		writermap.put(RepeatablePageMasterAlternatives.class,
				new RepeatablePageMasterAlternativesWriter());
		writermap.put(RepeatablePageMasterReference.class,
				new RepeatablePageMasterReferenceWriter());
		writermap.put(SinglePageMasterReference.class,
				new SinglePageMasterReferenceWriter());
	}

	public String write(PageSequenceMaster psm) {
		String returns = "";
		if (psm != null) {
			List<SubSequenceSpecifier> subsses = psm.getSubsequenceSpecifiers();
			if (subsses != null && !subsses.isEmpty()) {
				returns = returns + ElementWriter.TAGBEGIN + NAME
						+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
				int size = subsses.size();
				for (int i = 0; i < size; i++) {
					SubSequenceSpecifier subs = subsses.get(i);
					returns = returns
							+ writermap.get(subs.getClass()).write(subs);
				}
				returns = returns + ElementWriter.TAGENDSTART + NAME
						+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
			}
		}
		return returns;
	}
}
