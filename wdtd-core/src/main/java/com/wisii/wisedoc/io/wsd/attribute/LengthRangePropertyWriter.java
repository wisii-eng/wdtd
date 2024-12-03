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
 * @LengthRangePropertyWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;

/**
 * 类功能描述：LengthRangeProperty属性writer
 * 
 * 作者：zhangqiang 创建日期：2008-9-18
 */
public class LengthRangePropertyWriter extends AbstractAttributeWriter
{
	private final String MIN = "min";
	private final String MAX = "max";
	private final String OPTIMUM = "optimun";
	protected MixLengthWriter mixlengthwriter = new MixLengthWriter();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof LengthRangeProperty))
		{
			return "";
		}
		String returns = "";
		LengthRangeProperty lengthrange = (LengthRangeProperty) value;
		String keyname = getKeyName(key);
		LengthProperty minlength = (LengthProperty) lengthrange
				.getMinimum(null);
		LengthProperty maxlength = (LengthProperty) lengthrange
				.getMaximum(null);
		LengthProperty optimumlength = (LengthProperty) lengthrange
				.getOptimum(null);
		if (minlength == null && maxlength == null && optimumlength == null)
		{
			return "";
		} else
		{
			if (minlength != null)
			{
				String valuestring = mixlengthwriter.getValueString(minlength);
				if (valuestring != null)
				{
					returns = returns + keyname + DOTTAG + MIN + EQUALTAG
							+ QUOTATIONTAG + valuestring + QUOTATIONTAG
							+ SPACETAG;
				}
			}
			if (optimumlength != null)
			{
				String valuestring = mixlengthwriter
						.getValueString(optimumlength);
				if (valuestring != null)
				{
					returns = returns + keyname + DOTTAG + OPTIMUM + EQUALTAG
							+ QUOTATIONTAG + valuestring + QUOTATIONTAG
							+ SPACETAG;
				}
			}
			if (maxlength != null)
			{
				String valuestring = mixlengthwriter.getValueString(maxlength);
				if (valuestring != null)
				{
					returns = returns + keyname + DOTTAG + MAX + EQUALTAG
							+ QUOTATIONTAG + valuestring + QUOTATIONTAG
							+ SPACETAG;
				}
			}
		}
		return returns;
	}

}
