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
 * @PercentLengthWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.att;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.document.datatype.PercentBase;

/**
 * 类功能描述：百分比writer类
 * 
 * 作者：zhangqiang 创建日期：2008-12-19
 */
public class PercentLengthWriter extends AbstractAttributeWriter
{

	public String getValue(Object value)
	{
		if (value instanceof PercentLength)
		{
			PercentLength pl = (PercentLength) value;
			PercentBase base = pl.getBaseLength();
			if (base instanceof LengthBase)
			{
				LengthBase lenbase = (LengthBase) base;
				if (lenbase.getBaseLength() instanceof FixedLength)
				{
					return 
							 Math.round(pl.value()
									* lenbase.getBaseLength().getValue()
									/ 1000f)+"pt";
				}
			}
			String values = "" + pl.value() * 100 + "%";
			return values;
		}
		return "";
	}
}
