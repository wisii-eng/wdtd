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
 * @EnumLengthReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-9
 */
public class EnumLengthReader extends SingleAttributeReader {
	protected EnumPropertyReader enumreader = new EnumPropertyReader();
	protected FixedLengthReader lengthreader = new FixedLengthReader();
	public Object read(int key, String value) {
		Length length = lengthreader.generateLengthvalue(value);
		if (length == null) {
			int enumint = enumreader.getEnumKey(value);
			if (enumint > -1) {
				return new EnumLength(enumint, new FixedLength(0));
			}
		} else {
			return new EnumLength(-1, length);
		}
		return null;
	}

}
