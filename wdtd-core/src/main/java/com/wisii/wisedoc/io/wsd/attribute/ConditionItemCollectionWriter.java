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
 * @ConidtionItemCollectionWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.ConditionItemCollection;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.wsd.DocumentWirter;
/**
 * 类功能描述：条件样式输出类
 *
 * 作者：zhangqiang
 * 创建日期：2009-3-24
 */
public class ConditionItemCollectionWriter extends AbstractAttributeWriter
{
    public final static String ID = "id";
    public final static String DYNAMICSTYLETAG = "dynamicstyle";
    public final static String DYNAMICSTYLEITEMAG = "dynamicstyleitem";
    public final static String CONDITIONID = "conditionid";
    public final static String STYLEID = "styleid";
	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public ConditionItemCollectionWriter()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof ConditionItemCollection))
		{
			return "";
		}
		String returns = "";
		ConditionItemCollection dynamicstyle = (ConditionItemCollection) value;
		returns = returns + getDynamicString(dynamicstyle);
		return returns;
	}
	private String getDynamicString(ConditionItemCollection dynamicstyles)
	{
		String returns = "";
		if (dynamicstyles != null)
		{
			String id = DocumentWirter.getDynamicstyleID(dynamicstyles).trim();
			String refid = "";
			if (id != null && !id.equals(""))
			{
				refid = SPACETAG + ID + EQUALTAG + QUOTATIONTAG + id
						+ QUOTATIONTAG;
			}
			returns = returns + ElementWriter.TAGBEGIN + DYNAMICSTYLETAG
					+ refid + ElementWriter.TAGEND + ElementWriter.LINEBREAK;
			for (ConditionItem dynamicstyle : dynamicstyles)
			{
				LogicalExpression le = dynamicstyle.getCondition();
				Attributes attri = dynamicstyle.getAttributes();
				String leid = DocumentWirter.getLogicalExpressionID(le);
				String attriid = DocumentWirter.getAttributesID(attri);
				if (leid != null && attriid != null)
				{
					returns = returns + ElementWriter.TAGBEGIN
							+ DYNAMICSTYLEITEMAG + SPACETAG+CONDITIONID + EQUALTAG
							+ QUOTATIONTAG + leid + QUOTATIONTAG + SPACETAG
							+ STYLEID + EQUALTAG + QUOTATIONTAG + attriid
							+ QUOTATIONTAG + ElementWriter.NOCHILDTAGEND;
				}
			}
			returns = returns + ElementWriter.TAGENDSTART + DYNAMICSTYLETAG
					+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;

		}
		return returns;
	}
}
