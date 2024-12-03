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
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.log.LogUtil;

public class PercentLengthWriter extends OutputAttributeWriter
{

	public String write(int key, Object value)
	{
		String output = "";
		String attributeName = "";
		String attributeValue = "";
		attributeName = getKeyName(key);
		if (!"".equalsIgnoreCase(attributeName))
		{
			if (key == Constants.PR_BACKGROUND_POSITION_HORIZONTAL)
			{
				PercentLength property = (PercentLength) value;
				double factor = property.value();
				double deviation = 0.01;
//				if (Math.abs(factor) <= deviation)
//				{
//					attributeValue = "left";
//				} else 
					
					if (Math.abs(factor - 0.5d) <= deviation)
				{
					attributeValue = "center";
				} else if (Math.abs(factor - 1d) <= deviation)
				{
					attributeValue = "right";
				}
			} else if (key == Constants.PR_BACKGROUND_POSITION_VERTICAL)
			{
				PercentLength property = (PercentLength) value;
				double factor = property.value();
				double deviation = 0.01;
//				if (Math.abs(factor) <= deviation)
//				{
//					attributeValue = "top";
//				} else 
					if (Math.abs(factor - 0.5d) <= deviation)
				{
					attributeValue = "center";
				} else if (Math.abs(factor - 1d) <= deviation)
				{
					attributeValue = "bottom";
				}
			} else
			{
				attributeValue = write(value);
			}
			if (!"".equalsIgnoreCase(attributeValue))
			{
				output = ElementUtil.outputAttributes(attributeName,
						attributeValue);
			}
		} else
		{
			LogUtil.debug("传入的参数\"" + key + "\"为null");
		}
		return output;
	}

	public String write(Object value)
	{
		PercentLength property = (PercentLength) value;
		String output = "";
		double factor = property.value();
		if (((LengthBase) property.getBaseLength()).getBaseType() == LengthBase.CUSTOM_BASE)
		{
			output = IoXslUtil.getPersent(factor);
		} else
		{
			FixedLength length = (FixedLength) ((LengthBase) property
					.getBaseLength()).getBaseLength();
			if (length == null)
			{
				output = IoXslUtil.getPersent(factor);
			} else
			{
				if (factor != 1d)
				{
					output = output + factor + "*";
				}
				output = output + length.toString();
			}
		}
		return output.replaceAll(" ", "");
	}
}
