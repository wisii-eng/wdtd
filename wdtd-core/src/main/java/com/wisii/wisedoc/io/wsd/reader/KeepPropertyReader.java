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
 * @KeepPropertyReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.KeepProperty;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-9
 */
public class KeepPropertyReader extends CompoundAttributeReader {
	EnumPropertyReader enumreader = new EnumPropertyReader();
	private final String WITHINLINE = "within-line";
	private final String WITHINCOLUMN = "within-column";
	private final String WITHINPAGE = "within-page";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.CompoundAttributeReader#read(int,
	 * java.util.Map)
	 */
	@Override
	public Object read(int key, Map<String, String> values) {
		if (values != null) {
			EnumProperty keepline = new EnumProperty(Constants.EN_AUTO, "");
			EnumProperty keepcolumn = new EnumProperty(Constants.EN_AUTO, "");
			EnumProperty keeppage = new EnumProperty(Constants.EN_AUTO, "");
			String skeepline = values.get(WITHINLINE);
			int lineenumint = enumreader.getEnumKey(skeepline);
			if (lineenumint > -1) {
				keepline = new EnumProperty(lineenumint, "");
			}
			String skeepcolumn = values.get(WITHINCOLUMN);
			int columenumint = enumreader.getEnumKey(skeepcolumn);
			if (columenumint > -1) {
				keepcolumn = new EnumProperty(columenumint, "");
			}
			String skeeppage = values.get(WITHINPAGE);
			int pageenumint = enumreader.getEnumKey(skeeppage);
			if (pageenumint > -1) {
				keeppage = new EnumProperty(pageenumint, "");
			}
			return new KeepProperty(keepline, keepcolumn, keeppage);
		}
		return null;
	}

}
