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
 * @SpeakrateWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.attribute;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-19
 */
public class EnumNumberWriter extends EnumPropertyWriter
{

	/**
	 * 
	 * 类的构造方法
	 * 
	 * 将map初始化
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public EnumNumberWriter()
	{
		super();
		map.put(Constants.EN_AUTO, FoXsltConstants.AUTO);
		map.put(Constants.EN_AUTO_ODD, FoXsltConstants.AUTO_ODD);
		map.put(Constants.EN_AUTO_EVEN, FoXsltConstants.AUTO_EVEN);
		// map.put(Constants.EN_SILENT, FoXsltConstants.SILENT);
		// map.put(Constants.EN_X_SOFT, FoXsltConstants.X_SOFT);
		// map.put(Constants.EN_SOFT, FoXsltConstants.SOFT);
		// map.put(Constants.EN_MEDIUM, FoXsltConstants.MEDIUM);
		// map.put(Constants.EN_LOUD, FoXsltConstants.LOUD);
		// map.put(Constants.EN_X_LOUD, FoXsltConstants.X_LOUD);
		// map.put(Constants.EN_SLOW, FoXsltConstants.SLOW);
		// map.put(Constants.EN_X_SLOW, FoXsltConstants.X_SLOW);
		// map.put(Constants.EN_MEDIUM, FoXsltConstants.MEDIUM);
		// map.put(Constants.EN_FAST, FoXsltConstants.FAST);
		// map.put(Constants.EN_X_FAST, FoXsltConstants.X_FAST);
		// map.put(Constants.EN_FASTER, FoXsltConstants.FASTER);
		// map.put(Constants.EN_SLOWER, FoXsltConstants.SLOWER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(Object value)
	{
		String attributeValue = "";
		EnumNumber valueEnumNumber = (EnumNumber) value;
		int meijunumber = valueEnumNumber != null ? valueEnumNumber.getEnum()
				: -1;
		if (meijunumber > 0)
		{
			if (map.containsKey(meijunumber))
			{
				attributeValue = map.get(meijunumber);
			} else
			{
				LogUtil.debug("传入的参数\"" + value + "\"在属性值map中获取不到相应的值");
			}
		} else
		{
			attributeValue = valueEnumNumber != null ? valueEnumNumber
					.getNumber()
					+ "" : "";
		}
		return attributeValue;
	}
}
