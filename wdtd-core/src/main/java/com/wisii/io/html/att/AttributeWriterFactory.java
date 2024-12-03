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
 * @AttributeWriterFactory.java
 *                              北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.att;

import java.awt.Color;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.TableColLength;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang
 * 创建日期：2012-6-1
 */
public class AttributeWriterFactory
{
	public static AttributeWriter getAttributeWriter(int key, Object value)
	{
		 if(key==Constants.PR_FONT_WEIGHT)
		{
			return new FontWeightWriter();
		}
		 else if (value instanceof LengthRangeProperty)
		{
			return new LengthRangePropertyWriter();
		} else if (value instanceof Color)
		{
			return new ColorWriter();
		} else if (value instanceof Boolean)
		{
			return new BooleanWriter();
		} else if (value instanceof Character)
		{
			return new CharacterWriter();
		} else if (value instanceof CondLengthProperty)
		{
			return new CondLengthPropertyWriter();
		} else if (value instanceof Double)
		{
			return new DoubleWriter();
		} else if (value instanceof EnumLength)
		{
			return new EnumLengthWriter();
		} else if (value instanceof EnumNumber)
		{
			return new EnumNumberWriter();
		} else if (value instanceof EnumProperty)
		{
			return new EnumPropertyWriter();
		} else if (value instanceof FixedLength)
		{
			return new FixedLengthWriter();
		} else if (value instanceof Integer)
		{
			return new IntegerAttributeWriter();
		} else if (value instanceof FixedLength)
		{
			return new FixedLengthWriter();
		} else if (value instanceof Number)
		{
			return new NumberPropertyWriter();
		} else if (value instanceof PercentLength)
		{
			return new PercentLengthWriter();
		} else if (value instanceof String)
		{
			return new StringWriter();
		}
		else if(value instanceof TableColLength)
		{
			return new TableColLengthWriter();
		}
		
		return new DefaultAttributeWriter();

	}
}
