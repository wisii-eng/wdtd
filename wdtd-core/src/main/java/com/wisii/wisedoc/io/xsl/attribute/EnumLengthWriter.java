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
 * @ContentWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.attribute;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-21
 */
public class EnumLengthWriter extends EnumPropertyWriter
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
	public EnumLengthWriter()
	{
		super();
		map.put(Constants.EN_AUTO, FoXsltConstants.AUTO);
		map.put(Constants.EN_SCALE_TO_FIT, FoXsltConstants.SCALE_TO_FIT);
		map
				.put(Constants.EN_USE_FONT_METRICS,
						FoXsltConstants.USE_FONT_METRICS);
		// map.put(Constants.EN_BELOW, FoXsltConstants.BELOW);
		// map.put(Constants.EN_LEVEL, FoXsltConstants.LEVEL);
		// map.put(Constants.EN_ABOVE, FoXsltConstants.ABOVE);
		// map.put(Constants.EN_HIGHER, FoXsltConstants.HIGHER);
		// map.put(Constants.EN_LOWER, FoXsltConstants.LOWER);
		// map.put(Constants.EN_SCALE_DOWN_TO_FIT,
		// FoXsltConstants.SCALE_DOWN_TO_FIT);
		// map.put(Constants.EN_SCALE_UP_TO_FIT,
		// FoXsltConstants.SCALE_UP_TO_FIT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(Object value)
	{
		String attributeValue = "";
		EnumLength valuestr = (EnumLength) value;
		int meijunumber = valuestr.getEnum();
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
			FixedLength length = (FixedLength) valuestr.getFixLength();
			attributeValue = IoXslUtil.getDeleteLastZero(length
					.getLengthValueString())
					+ length.getUnits();
		}
		return attributeValue;
	}

}
