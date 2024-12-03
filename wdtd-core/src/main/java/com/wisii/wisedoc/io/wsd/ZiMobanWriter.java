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
 * @TextInlineWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.attribute.Attributes;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-3-11
 */
public class ZiMobanWriter extends DefaultElementWriter
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.ElementWriter#write(com.wisii.wisedoc.document.Element
	 * )
	 */
	public String write(CellElement element)
	{
		String returns = "";
		if (element instanceof ZiMoban)
		{
			Attributes atts = element.getAttributes();
			String attsrefid = "";
			// 生成属性引用代码
			String refid = DocumentWirter.getAttributesID(atts);
			if (atts != null && refid != null)
			{
				attsrefid = attsrefid + ATTRIBUTEREFID + EQUALTAG
						+ QUOTATIONTAG + refid + QUOTATIONTAG;
			}
			String elementname = getElementName(element);
			// 生成元素头代码
			returns = returns + TAGBEGIN + elementname + SPACETAG + attsrefid
					+ NOCHILDTAGEND + LINEBREAK;
		}
		return returns;
	}
}
