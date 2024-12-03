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
 * @GroupWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.util.List;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.Sort;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.wsd.DocumentWirter;

/**
 * 类功能描述：Group属性writer
 * 
 * 作者：zhangqiang 创建日期：2008-11-13
 */
public class GroupWriter extends AbstractEnumAttribyteWriter
{
	public static final String GROUPTAG = "group";
	public static final String NODEID = "nodeid";
	public static final String FLITERCONDITION = "condition";
	public static final String EDITMODE = "editmode";
	public static final String MAX = "max";
	public static final String SORTTAG = "sort";
	public static final String SORTATT = "att";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof Group))
		{
			return "";
		}
		String returns = "";
		Group group = (Group) value;
		returns = returns + getGroupString(group);
		return returns;
	}

	private String getGroupString(Group group)
	{
		String returns = "";
		BindNode node = group.getNode();
		String id = DocumentWirter.getDataNodeID(node);
		String nodestring = SPACETAG + NODEID + EQUALTAG + QUOTATIONTAG + id
				+ QUOTATIONTAG;
		String logicalstring = "";
		LogicalExpression logical = group.getFliterCondition();
		if (logical != null)
		{
			String logid = DocumentWirter.getLogicalExpressionID(logical);
			if (logid != null)
			{
				logicalstring = SPACETAG + FLITERCONDITION + EQUALTAG
						+ QUOTATIONTAG + logid + QUOTATIONTAG;
			}
		}
		EnumProperty editmode = group.getEditmode();
		String editmodeString = "";
		if (editmode != null)
		{
			editmodeString = SPACETAG + EDITMODE + EQUALTAG + QUOTATIONTAG
					+ getEnumString(editmode.getEnum()) + QUOTATIONTAG;
		}
		EnumNumber max = group.getMax();
		String maxString = "";
		if (max != null)
		{
			maxString = SPACETAG + MAX + EQUALTAG + QUOTATIONTAG
					+ getEnumNumberString(max) + QUOTATIONTAG;
		}
		returns = returns + ElementWriter.TAGBEGIN + GROUPTAG + nodestring
				+ logicalstring + editmodeString + maxString
				+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
		List<Sort> sorts = group.getSortSet();
		if (sorts != null && !sorts.isEmpty())
		{
			returns = returns + getSortsString(sorts);
		}
		returns = returns + ElementWriter.TAGENDSTART + GROUPTAG
				+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
		return returns;
	}

	private String getEnumNumberString(EnumNumber enumnumber)
	{
		String valuestring;
		int enumnum = enumnumber.getEnum();
		if (enumnum > 0)
		{
			valuestring = getEnumString(enumnum);
		} else
		{
			Number fixl = enumnumber.getNumber();
			valuestring = "" + fixl;
		}
		return valuestring;
	}

	private String getSortsString(List<Sort> sorts)
	{
		String returns = "";
		int size = sorts.size();
		for (int i = 0; i < size; i++)
		{
			Sort sort = sorts.get(i);
			returns = returns + getSortString(sort);
		}
		return returns;
	}

	private String getSortString(Sort sort)
	{
		String returns = "";
		String nodeid = DocumentWirter.getDataNodeID(sort.getNode());
		String value = nodeid + "," + sort.getLanguage() + ","
				+ sort.getCaseOrder() + "," + sort.getDataType() + ","
				+ sort.getOrder();
		returns =returns +  ElementWriter.TAGBEGIN + SORTTAG + SPACETAG + SORTATT
				+ EQUALTAG + QUOTATIONTAG + value + QUOTATIONTAG
				+ ElementWriter.NOCHILDTAGEND + ElementWriter.LINEBREAK;
		return returns;
	}

}
