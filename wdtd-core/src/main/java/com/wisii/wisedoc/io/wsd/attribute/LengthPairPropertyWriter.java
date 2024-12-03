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
 * @LengthPairPropertyWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthPairProperty;

/**
 * 类功能描述：双向大小属性
 * 
 * 作者：zhangqiang 创建日期：2008-9-18
 */
public class LengthPairPropertyWriter extends AbstractAttributeWriter {
	private final String BPD = "bpd";
	private final String IPD = "ipd";
	private final FixedLengthWriter fixedlenwriter = new FixedLengthWriter();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(int key, Object value) {
		if (value == null || !(value instanceof LengthPairProperty)) {
			return "";
		}
		String returns = "";
		String keyname = getKeyName(key);
		LengthPairProperty lengthpairp = (LengthPairProperty) value;
		FixedLength BPDlength = (FixedLength) lengthpairp.getBPD();
		if (BPDlength != null) {
			returns = returns + keyname + DOTTAG + BPD + EQUALTAG
					+ QUOTATIONTAG + fixedlenwriter.getValueString(BPDlength) + QUOTATIONTAG + SPACETAG;
		}
		FixedLength IPDlength = (FixedLength) lengthpairp.getIPD();
		if (IPDlength != null) {
			returns = returns + keyname + DOTTAG + IPD + EQUALTAG
					+ QUOTATIONTAG + fixedlenwriter.getValueString(IPDlength) + QUOTATIONTAG + SPACETAG;
		}
		return returns;
	}

}
