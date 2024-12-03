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
 * @EncryptInformationWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.util.List;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.EncryptInformation;
import com.wisii.wisedoc.io.wsd.DocumentWirter;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-17
 */
public class EncryptInformationWriter extends AbstractAttributeWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof EncryptInformation))
		{
			return "";
		}
		EncryptInformation encrypt = (EncryptInformation) value;
		String returns = "";
		returns = returns + getKeyName(key);
		String values = "" + encrypt.getEncrypttype();
		List<BindNode> nodes = encrypt.getNodes();
		for (BindNode node : nodes)
		{
			values = values + "," + DocumentWirter.getDataNodeID(node);
		}
		returns = returns + EQUALTAG + QUOTATIONTAG + values + QUOTATIONTAG
				+ SPACETAG;
		return returns;
	}

}
