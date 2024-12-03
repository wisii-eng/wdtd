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
 * @ConditionWriterUtil.java
 *                           北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.util;

import java.util.List;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.XslContext;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang
 * 创建日期：2012-5-29
 */
public class ConditionWriterUtil
{
	public static String getConditionString(LogicalExpression loc,
			XslContext xslcontext)
	{
		if (loc == null)
		{
			return "";
		}
		return returnStringIf(loc, true, xslcontext);
	}

	@SuppressWarnings("unchecked")
	private static String returnStringIf(LogicalExpression condition,
			boolean isfirest, XslContext xslcontext)
	{
		if (condition == null)
		{
			return "";
		}
		List listCondition = condition.getConditions();
		List<Integer> listType = condition.getOperators();

		StringBuffer output = new StringBuffer();
		if (listCondition != null)
		{
			int number = listCondition.size();
			for (int i = 0; i < number; i++)
			{
				Object current = listCondition.get(i);

				if (current instanceof LogicalExpression)
				{
					LogicalExpression cond = (LogicalExpression) current;
					if (!isfirest)
					{
						output.append("(");
					}
					if (number == 1)
					{
						output.append(returnStringIf(cond, true, xslcontext));
					} else
					{
						output.append(returnStringIf(cond, false, xslcontext));
					}
					if (!isfirest)
					{
						output.append(")");
					}
				} else if (current instanceof Condition)
				{
					Condition cond = (Condition) current;
					if (!(isfirest && number == 1))
					{
						output.append("(");
					}
					output.append(returnStringIf(cond, xslcontext));
					if (!(isfirest && number == 1))
					{
						output.append(")");
					}
				}

				if (listType != null && listType.size() > 0 && i < number - 1)
				{
					output.append(returnType(listType.get(i)));
				}
			}
		}
		return output.toString();
	}

	private static String returnType(int type)
	{
		String value = "";
		if (type == LogicalExpression.AND)
		{
			value = FoXsltConstants.AND;
		} else if (type == LogicalExpression.OR)
		{
			value = FoXsltConstants.OR;
		}
		return value;
	}

	public static String returnStringIf(Condition condition,
			XslContext xslcontext)
	{
		BindNode node = condition.getNode();
		int type = condition.getType();
		Object param = condition.getParam();
		String nodeStr = xslcontext.getRelatePath(node);
		String typeStr = returnYunsuanfu(type);
		String paramStr = "";
		if (param instanceof BindNode)
		{
			BindNode value = (BindNode) param;
			paramStr = xslcontext.getRelatePath(value);
		} else if (param instanceof String)
		{
			paramStr = XMLUtil.getXmlText(param.toString());
			if ("@null".equalsIgnoreCase(paramStr))
			{
				paramStr = "";
			} else if ("@dnull".equalsIgnoreCase(paramStr))
			{
				paramStr = "";
				nodeStr = "normalize-space(" + nodeStr + ")";
			}
		}
		StringBuffer output = new StringBuffer();
		if (type == Condition.STRINGNOTEQUAL)
		{
			output.append("not(contains(" + nodeStr + ",");
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(paramStr);
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(") and contains(");
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(paramStr);
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append("," + nodeStr + "))");
		} else if (type == Condition.STRINGEQUAL)
		{
			output.append("contains(" + nodeStr + ",");
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(paramStr);
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(") and contains(");
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(paramStr);
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append("," + nodeStr + ")");
		} else if (type == Condition.FIRST)
		{
			output.append("position()=1");
		} else if (type == Condition.NOTFIRST)
		{
			output.append("position()&gt;1");
		} else if (type == Condition.LAST)
		{
			output.append("position()=last()");
		} else if (type == Condition.NOTLAST)
		{
			output.append("position()&lt;last()");
		} else if (type == Condition.ODD)
		{
			output.append("(position() mod 2)=1");
		} else if (type == Condition.EVEN)
		{
			output.append("(position() mod 2)=0");
		} else if (type == Condition.POSITIONLESS)
		{
			output.append("position()&lt;" + param);
		} else if (type == Condition.POSITIONGREATER)
		{
			output.append("position()&gt;" + param);
		} else if (type == Condition.POSITION)
		{
			output.append("position()=" + param);
		} else if (type >= Condition.POSITIONMOD3
				&& type < Condition.POSITIONMOD9)
		{
			output.append("(position() mod "
					+ (type + 3 - Condition.POSITIONMOD3) + ")=" + param);
		} else if (type == Condition.LENGTHLESS)
		{
			output.append("string-length(" + nodeStr + ")&lt;" + paramStr);
		} else if (type == Condition.LENGTHGREATER)
		{
			output.append("string-length(" + nodeStr + ")&gt;" + paramStr);
		} else if (type == Condition.EQUAL)
		{
			output.append("number(" + nodeStr + ")=number(" + paramStr + ")");
		} else if (type == Condition.REGULAREQUAL)
		{
			paramStr = param.toString();
			NameSpace textlinesNameSpace = new NameSpace("xmlns:regular",
					"com.wisii.wisedoc.io.xsl.util.Regular");
			xslcontext.addNameSpace(textlinesNameSpace);
			if (param instanceof BindNode)
			{
				output.append("regular:getResult(" + paramStr + "," + nodeStr
						+ ")");
			} else if (param instanceof String)
			{
				output.append("regular:getResult('" + paramStr + "'," + nodeStr
						+ ")");
			}
		} else if (type == Condition.COUNT)
		{
			output.append("count(" + nodeStr + ")=" + param);
		} else if (type == Condition.COUNTLESS)
		{
			output.append("count(" + nodeStr + ")&lt;" + param);
		} else if (type == Condition.COUNTGREATER)
		{
			output.append("count(" + nodeStr + ")&gt;" + param);
		} else if (type == Condition.COUNTODD)
		{
			output.append("(count(" + nodeStr + ") mod 2)=1");
		} else if (type == Condition.COUNTEVEN)
		{
			output.append("(count(" + nodeStr + ") mod 2)=0");
		} else if (type >= Condition.COUNTMOD3 && type < Condition.COUNTMOD9)
		{
			output.append("(count(" + nodeStr + ") mod "
					+ (type + 3 - Condition.COUNTMOD3) + ")=" + param);
		} else
		{
			output.append(nodeStr + typeStr + paramStr);
		}
		return output.toString();
	}

	private static String returnYunsuanfu(int type)
	{
		String value = "";
		if (type == Condition.GREATER)
		{
			value = " &gt; ";
		} else if (type == Condition.EQUAL)
		{
			value = " = ";
		} else if (type == Condition.LESS)
		{
			value = " &lt; ";
		} else if (type == Condition.REGULAREQUAL)
		{
			value = " = ";
		} else if (type == Condition.GREATEREQUAL)
		{
			value = " &gt;= ";
		} else if (type == Condition.LESSEQUAL)
		{
			value = " &lt;= ";
		} else if (type == Condition.NUMBERNOTEQUAL)
		{
			value = " != ";
		}
		return value;
	}

}
