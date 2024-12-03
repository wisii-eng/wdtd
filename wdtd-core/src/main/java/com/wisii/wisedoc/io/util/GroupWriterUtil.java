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
 * @GroupWriterUtil.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.util;

import java.util.List;

import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.Sort;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.XslContext;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-5-29
 */
public class GroupWriterUtil
{
	public static String getGroupString(Group group,
			XslContext xslcontext)
	{
		if (group == null)
		{
			return "";
		}
		StringBuffer sb=new StringBuffer();
		sb.append("<xsl:for-each select=\"");
    	String relatepath = xslcontext.getRelatePath(group.getNode());
    	xslcontext.saveGroupContext(group);
    	LogicalExpression le = group.getFliterCondition();
    	if(le!=null)
    	{
    		String cond = ConditionWriterUtil.getConditionString(le, xslcontext);
    		relatepath=relatepath+"["+cond+"]";
    	}
    	sb.append(relatepath);
    	sb.append("\">");
    	List<Sort> sortList = group == null ? null : group.getSortSet();
		if (sortList != null)
		{
			int num = sortList.size();
			for (int i = 0; i < num; i++)
			{
				Sort current = sortList.get(i);
				sb.append(sort(current, xslcontext));
			}
		}
    	return sb.toString();
	}
	private static String sort(Sort sort, XslContext xslcontext)
	{
		String value = xslcontext.getRelatePath(sort.getNode());
		String type = sort.getDataType() == Sort.NUMBER ? FoXsltConstants.NUMBER
				: FoXsltConstants.TEXT;
		String order = sort.getOrder() == Sort.DESCENDING ? FoXsltConstants.DESCENDING
				: FoXsltConstants.ASCENDING;
		String caseOrder = sort.getCaseOrder() == Sort.LOWERFIRST ? FoXsltConstants.LOWER_FIRST
				: FoXsltConstants.UPPER_FIRST;
		StringBuffer output = new StringBuffer();
		output.append("<");
		output.append(FoXsltConstants.SORT);
		output.append(XMLUtil.outputAttributes(FoXsltConstants.SELECT, value));
		output.append(XMLUtil.outputAttributes(FoXsltConstants.DATETYPE, type));
		output.append(XMLUtil.outputAttributes(FoXsltConstants.ORDER, order));
		if (type.equalsIgnoreCase(FoXsltConstants.TEXT))
		{
			output.append(XMLUtil.outputAttributes(FoXsltConstants.CASE_ORDER,
					caseOrder));
		}
		output.append("/>");
		return output.toString();
	}
}
