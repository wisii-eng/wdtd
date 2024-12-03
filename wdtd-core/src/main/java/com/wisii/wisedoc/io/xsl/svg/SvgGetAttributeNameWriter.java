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
 * @SvgGetAttributeNameWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.svg;

import java.util.HashMap;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.io.AttributeKeyNameFactory;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-5
 */
public class SvgGetAttributeNameWriter implements AttributeKeyNameFactory
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeKeyNameFactory#getKey(java.lang.String)
	 */
	public int getKey(String name)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeKeyNameFactory#getKeyName(int)
	 */
	public String getKeyName(int key)
	{
		String attrName = "";
		if (svgAttribute.containsKey(key))
		{
			String keymap = svgAttribute.get(key);
			attrName = keymap;
		} else
		{
			LogUtil.debug("传入的参数\"" + key + "\"在属性map中获取不到相应的属性名");
		}
		return attrName;
	}

	private static HashMap<Integer, String> svgAttribute;
	static
	{
		svgAttribute.put(Constants.PR_ABSOLUTE_POSITION,
				FoXsltConstants.ABSOLUTE_POSITION);
		svgAttribute.put(Constants.PR_ACTIVE_STATE,
				FoXsltConstants.ACTIVE_STATE);
		svgAttribute.put(Constants.PR_ALIGNMENT_ADJUST,
				FoXsltConstants.ALIGNMENT_ADJUST);
	}
}
