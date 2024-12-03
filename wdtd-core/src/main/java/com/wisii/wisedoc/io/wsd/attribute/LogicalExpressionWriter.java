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
 * @LogicalExpressionWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.util.List;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.wsd.DocumentWirter;

/**
 * 类功能描述：LogicalExpression属性输出类
 * 
 * 作者：zhangqiang 创建日期：2008-11-12
 */
public class LogicalExpressionWriter extends AbstractAttributeWriter
{
	public static final String LOGICALTAG = "logical";
	public static final String CONDITIONTAG = "condition";
	public static final String TYPE = "type";
	public static final String TYPEVALUE = "value";
	public static final String REFID = "id";
	public static final String DATANODEID = "nodeid";
	public static final String PARAM = "param";
	public static final String PARAMID = "paramid";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof LogicalExpression))
		{
			return "";
		}
		String returns = "";
		LogicalExpression expression = (LogicalExpression) value;
		returns = returns + getLogicalExpressionString(expression);
		return returns;
	}

	private String getLogicalExpressionString(LogicalExpression expression)
	{
		String returns = "";
		if (expression != null)
		{
			String id = DocumentWirter.getLogicalExpressionID(expression);
			String refid = "";
			if (id != null && !id.equals(""))
			{
				refid = SPACETAG + REFID + EQUALTAG + QUOTATIONTAG + id
						+ QUOTATIONTAG;
			}
			returns = returns + ElementWriter.TAGBEGIN + LOGICALTAG + refid
					+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
			List listCondition = expression.getConditions();
			List<Integer> listType = expression.getOperators();
			if (listCondition != null)
			{
				int number = listCondition.size();
				for (int i = 0; i < number; i++)
				{
					Object current = listCondition.get(i);
					if (current instanceof LogicalExpression)
					{
						LogicalExpression cond = (LogicalExpression) current;
						returns = returns + getLogicalExpressionString(cond);
					} else if (current instanceof Condition)
					{
						Condition cond = (Condition) current;
						returns = returns + getConditionString(cond);
					}
					if (i < number - 1)
					{
						String type = ElementWriter.TAGBEGIN + TYPE + SPACETAG
								+ TYPEVALUE + EQUALTAG + QUOTATIONTAG
								+ listType.get(i) + QUOTATIONTAG
								+ ElementWriter.NOCHILDTAGEND
								+ ElementWriter.LINEBREAK;
						returns = returns + type;
					}
				}
			}
			returns = returns + ElementWriter.TAGENDSTART + LOGICALTAG
					+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;

		}
		return returns;
	}

	/**
	 * 
	 * 生成Condition输出内容
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	private String getConditionString(Condition condition)
	{
		String returns = "";
		if (condition != null)
		{
			BindNode node = condition.getNode();
			int type = condition.getType();
			Object param = condition.getParam();
			String nodeStr = "";
			if(node!=null){
				nodeStr  = SPACETAG + DATANODEID + EQUALTAG + QUOTATIONTAG
					+ DocumentWirter.getDataNodeID(node) + QUOTATIONTAG;
			}
			String typeStr = SPACETAG + TYPE + EQUALTAG + QUOTATIONTAG + type
					+ QUOTATIONTAG;
			String paramStr = "";
			if (param instanceof BindNode)
			{

				paramStr = SPACETAG + PARAMID + EQUALTAG + QUOTATIONTAG
						+ DocumentWirter.getDataNodeID((BindNode) param)
						+ QUOTATIONTAG;
			} else if(param instanceof String)
			{
				paramStr = SPACETAG + PARAM + EQUALTAG + QUOTATIONTAG
						+ XMLUtil.getXmlText(param.toString()) + QUOTATIONTAG;
			}
			returns = returns + ElementWriter.TAGBEGIN + CONDITIONTAG + nodeStr
					+ typeStr + paramStr + ElementWriter.NOCHILDTAGEND
					+ ElementWriter.LINEBREAK;
		}
		return returns;
	}
}
