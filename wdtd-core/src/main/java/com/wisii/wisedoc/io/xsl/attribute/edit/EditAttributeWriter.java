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

package com.wisii.wisedoc.io.xsl.attribute.edit;

import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter;

public class EditAttributeWriter implements EditAttributeFactory
{

	@Override
	public AttributeValueWriter getAttributeWriter(int key)
	{

		return null;
	}

	@Override
	public AttributeValueWriter getAttributeWriter(Object value)
	{
		AttributeValueWriter writer = null;
		if (value != null)
		{
			if (value instanceof EnumProperty)
			{
				writer = new EnumPropertyWriter();
			} else if (value instanceof FixedLength)
			{
				writer = new FixedLengthWriter();
			} else if ((value instanceof Integer) || (value instanceof Double)
					|| (value instanceof String))
			{
				writer = new DefaultWriter();
			}
//			else if (value instanceof ConnWith)
//			{
//				writer = new ConnWithWriter((ConnWith) value);
//			}
		}
		return writer;
	}
}
