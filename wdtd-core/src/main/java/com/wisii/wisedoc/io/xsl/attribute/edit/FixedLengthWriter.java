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

package com.wisii.wisedoc.io.xsl.attribute.edit;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class FixedLengthWriter implements AttributeValueWriter
{

	@Override
	public String write(Object value)
	{
		String attributeValue = "";
		if (value instanceof FixedLength)
		{
			FixedLength length = (FixedLength) value;
			String zhi = IoXslUtil.removePoint(length.getLengthValueString());
			if (!zhi.equalsIgnoreCase(""))
			{
				attributeValue = zhi + length.getUnits();
			}
		}
		return attributeValue;
	}

}
