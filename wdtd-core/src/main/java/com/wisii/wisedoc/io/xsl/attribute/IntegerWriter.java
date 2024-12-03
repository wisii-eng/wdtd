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
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.log.LogUtil;

public class IntegerWriter extends OutputAttributeWriter
{

	public String write(int key, Object value)
	{
		String output = "";
		String attributeName = "";
		String attributeValue = "";
		attributeName = getKeyName(key);
		if (!"".equalsIgnoreCase(attributeName))
		{
			if (key == Constants.PR_FONT_STYLE
					|| key == Constants.PR_FONT_WEIGHT
					|| key == Constants.PR_BACKGROUND_REPEAT
					|| key == Constants.PR_WHITE_SPACE_COLLAPSE
					|| key == Constants.PR_WHITE_SPACE_TREATMENT
					|| key == Constants.PR_LINEFEED_TREATMENT
					|| key == Constants.PR_LINE_STACKING_STRATEGY)
			{
				attributeValue = new EnumPropertyWriter().write(value);
			} else
			{
				attributeValue = value.toString();
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

}
