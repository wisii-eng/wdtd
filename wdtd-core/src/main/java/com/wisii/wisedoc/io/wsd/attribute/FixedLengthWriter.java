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
 * @FixedLengthWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.attribute.FixedLength;

/**
 * 类功能描述：长度值writer
 * 
 * 作者：zhangqiang 创建日期：2008-9-18
 */
public class FixedLengthWriter extends AbstractAttributeWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof FixedLength))
		{
			return "";
		}
		String returns = "";
		FixedLength fixl = (FixedLength) value;
		returns = returns + getKeyName(key);
		returns = returns + EQUALTAG + QUOTATIONTAG + getValueString(fixl)
				+ QUOTATIONTAG + SPACETAG;
		return returns;
	}

	public String getValueString(Object value)
	{
		if (value instanceof FixedLength)
		{
			FixedLength fixl = (FixedLength) value;
			String precisions = "";
//			如果是默认精度，则不保存精度信息
			if (fixl.getPrecision() != ConfigureUtil.getPrecision(fixl
					.getUnits()))
			{
				precisions = precisions + "," + fixl.getPrecision();
			}
			return fixl.getLengthValueString() + fixl.getUnits() + precisions;
		}
		return "";
	}
}
