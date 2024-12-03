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
 * @MixFillWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.awt.Color;

import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.IOFactory;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-2-27
 */
public class MixFillWriter extends AbstractAttributeWriter
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(int key, Object value)
	{
		if (value instanceof Color)
		{
			return IOFactory.getAttributeIOFactory(IOFactory.WSD)
					.getAttributeWriter(Color.class).write(key, value);
		} else if (value instanceof EnumProperty)
		{
			return IOFactory.getAttributeIOFactory(IOFactory.WSD)
					.getAttributeWriter(EnumProperty.class).write(key, value);
		} else if (value instanceof String)
		{
			return IOFactory.getAttributeIOFactory(IOFactory.WSD)
					.getAttributeWriter(String.class).write(key, value);
		}
		return "";
	}
}
