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
 * @CondLengthProperty.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;

/**
 * 类功能描述：条件长度属性writer类
 * 
 * 作者：zhangqiang 创建日期：2008-9-18
 */
public class CondLengthPropertyWriter extends AbstractAttributeWriter {
	final String ISDISCARD = "isdiscard";
	final String LENGTH = "length";
	FixedLengthWriter fixedlenwriter = new FixedLengthWriter();

	public String write(int key, Object value) {
		if (value == null || !(value instanceof CondLengthProperty)) {
			return "";
		}
		String returns = "";
		CondLengthProperty cl = (CondLengthProperty) value;
		returns = returns + getKeyName(key) + DOTTAG + ISDISCARD + EQUALTAG
				+ QUOTATIONTAG + Boolean.toString(cl.isDiscard())
				+ QUOTATIONTAG + SPACETAG;
		FixedLength fixl = (FixedLength) cl.getLength();
		returns = returns + getKeyName(key) + DOTTAG + LENGTH + EQUALTAG
				+ QUOTATIONTAG + fixedlenwriter.getValueString(fixl)
				+ QUOTATIONTAG + SPACETAG;
		return returns;
	}

}
