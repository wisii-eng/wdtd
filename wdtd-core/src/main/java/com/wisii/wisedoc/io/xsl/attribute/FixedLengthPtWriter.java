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
 * @AddPtWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.attribute;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-21
 */
public class FixedLengthPtWriter extends OutputAttributeWriter
{

	boolean zeroFlg = false;

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
		double valueDouble = 1;
		if (value instanceof FixedLength)
		{
			FixedLength length = (FixedLength) value;
			valueDouble = length.getNumericValue() / 1000;
			attributeValue = IoXslUtil.getDeleteLastZero(valueDouble)
					+ FoXsltConstants.PT;
		} else if (value instanceof CondLengthProperty)
		{
			CondLengthProperty length = (CondLengthProperty) value;
			if (length.isDiscard())
			{
				FixedLength realLength = (FixedLength) length.getLength();
				valueDouble = new Double(realLength.getLengthValueString())
						.doubleValue() / 1000;
				attributeValue = valueDouble + FoXsltConstants.PT;
			}
		}
		if (valueDouble == 0.0d)
		{
			zeroFlg = true;
		}
		return attributeValue;
	}

	public String write(int key, Object value)
	{
		String output = "";
		String attributeName = "";
		String attributeValue = "";
		attributeName = getKeyName(key);
		if (!"".equalsIgnoreCase(attributeName))
		{
			attributeValue = write(value);
			if (!"".equalsIgnoreCase(attributeValue))
			{
				if (!zeroFlg)
				{
					output = ElementUtil.outputAttributes(attributeName,
							attributeValue);

				} else if (key == Constants.PR_START_INDENT
						|| key == Constants.PR_END_INDENT)
				{
					output = ElementUtil.outputAttributes(attributeName,
							attributeValue);
				}

			}
		} else
		{
			LogUtil.debug("传入的参数\"" + key + "\"为null");
		}
		return output;
	}
}
