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

import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.Property;
import com.wisii.wisedoc.document.attribute.TableColLength;
import com.wisii.wisedoc.io.xsl.SelectOutputAttributeWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

public class LengthRangePropertyWriter extends OutputAttributeWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter#write(java.lang
	 * .Object)
	 */
	public String write(int key, Object value)
	{
		StringBuffer output = new StringBuffer();
		String attributeName = "";
		attributeName = getKeyName(key);
		if (!"".equalsIgnoreCase(attributeName))
		{
			LengthRangeProperty spaceValue = (LengthRangeProperty) value;
			Property propertyMax = spaceValue.getMaximum(null);
			Property propertyMin = spaceValue.getMinimum(null);
			Property propertyOpt = spaceValue.getOptimum(null);

			if (propertyMax.equals(propertyMin)
					&& propertyMin.equals(propertyOpt))
			{
				output.append(new SelectOutputAttributeWriter().write(key,
						propertyMax));
			} else
			{
				output.append(ElementUtil.outputAttributes(attributeName
						+ FoXsltConstants.MAXIMUM, getValueStr(propertyMax)));
				output.append(ElementUtil.outputAttributes(attributeName
						+ FoXsltConstants.MINIMUM, getValueStr(propertyMin)));
				output.append(ElementUtil.outputAttributes(attributeName
						+ FoXsltConstants.OPTIMUM, getValueStr(propertyOpt)));
			}
		}
		return output.toString();
	}

	public String getValueStr(Property property)
	{
		String value = "";
		if (property instanceof FixedLength)
		{
			value = new FixedLengthWriter().write(property);
		} else if (property instanceof EnumLength)
		{
			value = new EnumLengthWriter().write(property);
		} else if (property instanceof TableColLength)
		{
			value = new TableColLengthWriter().write(property);
		}
		return value;
	}
}
