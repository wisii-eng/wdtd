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
 * @KeepWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.attribute;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.document.attribute.Property;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-21
 */
public class KeepWriter extends EnumPropertyWriter
{

	public KeepWriter()
	{
		map.put(Constants.EN_AUTO, FoXsltConstants.AUTO);
		map.put(Constants.EN_ALWAYS, FoXsltConstants.ALWAYSA);
	}

	public String write(int key, Object value)
	{
		int auto = Constants.EN_AUTO;
		StringBuffer output = new StringBuffer();
		String attributeName = getKeyName(key);
		if (!"".equalsIgnoreCase(attributeName))
		{
			KeepProperty keepValue = (KeepProperty) value;
			Property attributeValueWithinLine = keepValue.getWithinLine();
			Property attributeValueWithinColumn = keepValue.getWithinColumn();
			Property attributeValueWithinPage = keepValue.getWithinPage();
			if (attributeValueWithinLine != null)
			{
				int intValueWithinLine = attributeValueWithinLine.getEnum();
				if (intValueWithinLine == -1)
				{
					EnumNumber withinLine = (EnumNumber) attributeValueWithinLine;
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.WITHIN_LINE, ""
							+ withinLine.getNumber()));
				} else if (intValueWithinLine != auto)
				{
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.WITHIN_LINE, map
							.get(intValueWithinLine)));
				}
			}
			if (attributeValueWithinColumn != null)
			{
				int intValueWithinColumn = attributeValueWithinColumn.getEnum();
				if (intValueWithinColumn == -1)
				{
					EnumNumber withinColumn = (EnumNumber) attributeValueWithinColumn;
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.WITHIN_COLUMN, ""
							+ withinColumn.getNumber()));
				} else if (intValueWithinColumn != auto)
				{
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.WITHIN_COLUMN, map
							.get(intValueWithinColumn)));
				}
			}
			if (attributeValueWithinPage != null)
			{
				int intValueWithinPage = attributeValueWithinPage.getEnum();
				if (intValueWithinPage == -1)
				{
					EnumNumber withinPage = (EnumNumber) attributeValueWithinPage;
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.WITHIN_PAGE, ""
							+ withinPage.getNumber()));
				} else if (intValueWithinPage != auto)
				{
					output.append(ElementUtil.outputAttributes(attributeName
							+ FoXsltConstants.WITHIN_PAGE, map
							.get(intValueWithinPage)));
				}
			}
		}
		return output.toString();
	}
}