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
 * @FontWeightWriter.java
 *                        北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.att;

import com.wisii.wisedoc.document.Constants;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang
 * 创建日期：2012-6-4
 */
public class FontWeightWriter extends AbstractAttributeWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.io.html.att.AttributeWriter#getValue(java.lang.Object)
	 */
	@Override
	public String getValue(Object value)
	{
		if (value instanceof Integer)
		{

			Integer weight = (Integer) value;
			if (weight == Constants.EN_700)
			{
				return "bold";
			} else if (weight == Constants.EN_800)
			{
				return "800";
			} else if (weight == Constants.EN_900)
			{
				return "900";
			} else if (weight == Constants.EN_500)
			{
				return "500";
			} else if (weight == Constants.EN_600)
			{
				return "600";
			} else if (weight == Constants.EN_100)
			{
				return "100";
			} else if (weight == Constants.EN_200)
			{
				return "200";
			} else if (weight == Constants.EN_300)
			{
				return "300";
			} else
			{
				return "normal";
			}
		}
		return "";
	}

}
