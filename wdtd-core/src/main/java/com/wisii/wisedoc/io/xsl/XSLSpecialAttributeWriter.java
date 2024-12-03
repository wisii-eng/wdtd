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

package com.wisii.wisedoc.io.xsl;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

public class XSLSpecialAttributeWriter
{

	/**
	 * 
	 * 获取特殊属性的处理方法
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getSpecialAttributesWriter(int key)
	{
		String attrName = "";
		if (specialAttributes.containsKey(key))
		{
			// Object value = specialAttributes.get(key);
		}
		return attrName;
	}

	// 特殊处理属性
	private static Map<Integer, Object> specialAttributes = new HashMap<Integer, Object>();
	static
	{
		specialAttributes.put(
				com.wisii.wisedoc.io.xsl.util.Constants.XSL_FOR_EACH,
				FoXsltConstants.FOR_EACH);
		specialAttributes.put(com.wisii.wisedoc.io.xsl.util.Constants.XSL_SORT,
				FoXsltConstants.SORT);
		specialAttributes.put(com.wisii.wisedoc.io.xsl.util.Constants.XSL_IF,
				FoXsltConstants.IF);
		specialAttributes.put(Constants.PR_SRC, FoXsltConstants.VARIABLE);
		specialAttributes.put(Constants.PR_BACKGROUND_IMAGE,
				FoXsltConstants.VARIABLE);
		specialAttributes.put(Constants.PR_EDITMODE, FoXsltConstants.VARIABLE);
		specialAttributes.put(
				com.wisii.wisedoc.io.xsl.util.Constants.FOV_TRANSLATE,
				FoXsltConstants.VARIABLE);
	}
}
