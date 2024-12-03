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
package com.wisii.wisedoc.io.xsl.attribute;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonTextDecoration;
import com.wisii.wisedoc.log.LogUtil;

public class TextDecorationWriter extends EnumPropertyWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter#write(java.lang
	 * .Object)
	 */
	public String write(Object value)
	{
		String attributeValue = "";
		if (value instanceof Integer)
		{
			int type = (Integer) value;
			if ((type & CommonTextDecoration.UNDERLINE) != 0)
			{
				if ("".equalsIgnoreCase(attributeValue))
				{
					attributeValue = map.get(Constants.EN_UNDERLINE);
				} else
				{
					attributeValue = attributeValue + " "
							+ map.get(Constants.EN_UNDERLINE);
				}
			}
			if ((type & CommonTextDecoration.OVERLINE) != 0)
			{
				if ("".equalsIgnoreCase(attributeValue))
				{
					attributeValue = map.get(Constants.EN_OVERLINE);
				} else
				{
					attributeValue = attributeValue + " "
							+ map.get(Constants.EN_OVERLINE);
				}

			}
			if ((type & CommonTextDecoration.LINE_THROUGH) != 0)
			{
				if ("".equalsIgnoreCase(attributeValue))
				{
					attributeValue = map.get(Constants.EN_LINE_THROUGH);
				} else
				{
					attributeValue = attributeValue + " "
							+ map.get(Constants.EN_LINE_THROUGH);
				}
			}
			if ((type & CommonTextDecoration.BLINK) != 0)
			{
				if ("".equalsIgnoreCase(attributeValue))
				{
					attributeValue = map.get(Constants.EN_BLINK);
				} else
				{
					attributeValue = attributeValue + " "
							+ map.get(Constants.EN_BLINK);
				}
			}
		}
		if ("".equalsIgnoreCase(attributeValue))
		{
			LogUtil.debug("传入的参数\"" + value + "\"不是int类型或给定的值无效");
		}
		return attributeValue;
	}
}
