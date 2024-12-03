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
 * @GroupUIWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.wsd.DocumentWirter;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-10-22
 */
public class GroupUIWriter extends AbstractAttributeWriter
{
	public static final String GROUPUI = "groupui";
	public static final String NAME = "name";
	public static final String CONWITHID = "conwithid";
	public static final String MAX = "max";
	public static final String MIN = "min";
	public static final String NONESELECTEDVALUE = "nsvalue";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (!(value instanceof GroupUI))
		{
			return "";
		}
		String returns = "";
		GroupUI groupui = (GroupUI) value;
		returns = returns + ElementWriter.TAGBEGIN + GROUPUI;
		returns = returns + SPACETAG + NAME + EQUALTAG + QUOTATIONTAG
				+ groupui.getName() + QUOTATIONTAG;
		ConnWith conwith = groupui.getConnwith();
		if (conwith != null)
		{
			returns = returns + SPACETAG + CONWITHID + EQUALTAG + QUOTATIONTAG
					+ DocumentWirter.getConnWithID(conwith) + QUOTATIONTAG;
		}
		Map<Integer, Object> att = groupui.getAttr();
		if (att != null && !att.isEmpty())
		{
			Integer max = (Integer) att
					.get(Constants.PR_GROUP_MAX_SELECTNUMBER);
			if (max != null)
			{
				returns = returns + SPACETAG + MAX + EQUALTAG + QUOTATIONTAG
						+ max + QUOTATIONTAG;
			}
			Integer min = (Integer) att
					.get(Constants.PR_GROUP_MIN_SELECTNUMBER);
			if (min != null)
			{
				returns = returns + SPACETAG + MIN + EQUALTAG + QUOTATIONTAG
						+ min + QUOTATIONTAG;
			}
			String noneselectedvalue = (String) att
					.get(Constants.PR_GROUP_NONE_SELECT_VALUE);
			if (min != null)
			{
				returns = returns + SPACETAG + NONESELECTEDVALUE + EQUALTAG
						+ QUOTATIONTAG + noneselectedvalue + QUOTATIONTAG;
			}
		}
		returns = returns + ElementWriter.NOCHILDTAGEND;
		return returns;
	}
}
