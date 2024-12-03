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
 * @AddMmWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.attribute;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-21
 */
public class FixedLengthWriter extends OutputAttributeWriter
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
		if (value instanceof FixedLength)
		{
			FixedLength length = (FixedLength) value;
			String zhi = IoXslUtil.removePoint(length.getLengthValueString());
			if (!zhi.isEmpty())
			{
				attributeValue = zhi + length.getUnits();
			}
			if (zhi.equals("0"))
			{
				zeroFlg = true;
			}
		}
		return attributeValue;
	}

	public String write(int key, Object value)
	{
		String output = "";
		String attributeName = "";
		String attributeValue = "";
		attributeName = getKeyName(key);
		if (!attributeName.isEmpty())
		{
			attributeValue = write(value);
			if (!attributeValue.isEmpty())
			{
				if (!zeroFlg)
				{
					output = ElementUtil.outputAttributes(attributeName,
							attributeValue);

				} else if (key == Constants.PR_START_INDENT
						|| key == Constants.PR_END_INDENT
						|| key == Constants.PR_COLUMN_GAP
						|| key == Constants.PR_FONT_SIZE
						|| key == Constants.PR_RULE_THICKNESS
						|| key == Constants.PR_PROVISIONAL_LABEL_SEPARATION
						|| key == Constants.PR_PROVISIONAL_DISTANCE_BETWEEN_STARTS
						|| key == Constants.PR_COLUMN_WIDTH)
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
