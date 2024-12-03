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
 * @ElementUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.BasicLink;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Leader;
import com.wisii.wisedoc.document.PageNumberCitation;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.Sort;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.TableWriter;
import com.wisii.wisedoc.io.xsl.XSLAttributeNameWriter;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-2
 */
public class ElementUtil
{

	/**
	 * 
	 * 获得传入的元素名对应的元素的开头部分，仅能处理xsl:choose、xsl:otherwise等不需要输出属性的元素。
	 * 
	 * @param elementname
	 *            元素名
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String startElement(String elementname)
	{
		StringBuffer output = new StringBuffer();

		output.append(ElementWriter.TAGBEGIN);
		output.append(elementname);
		output.append(ElementWriter.TAGEND);
		return output.toString();
	}

	/**
	 * 
	 * 获得传入的元素名对应的元素的开头部分，仅能处理xsl:when、xsl:if等只输出属性test的元素。
	 * 
	 * @param elementname
	 *            元素名
	 * @param test
	 *            test属性的值
	 * 
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String startElementWI(String elementname, String test)
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(elementname);
		output.append(outputAttributes(FoXsltConstants.TEST, test));
		output.append(ElementWriter.TAGEND);
		return output.toString();
	}

	public static String startElementIf(LogicalExpression condition)
	{
		if (condition == null)
		{
			return "";
		}
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.IF);
		output.append(outputAttributes(FoXsltConstants.TEST, returnStringIf(
				condition, true)));
		output.append(ElementWriter.TAGEND);
		return output.toString();
	}

	@SuppressWarnings("unchecked")
	public static String returnStringIf(LogicalExpression condition,
			boolean isfirest)
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
					output.append("(");
					if (number == 1)
					{
						output.append(returnStringIf(cond, true));
					}
					else
					{
						output.append(returnStringIf(cond, false));
					}
					output.append(")");
				} else if (current instanceof Condition)
				{
					Condition cond = (Condition) current;
					if (!(isfirest && number == 1))
					{
						output.append("(");
					}
					output.append(returnStringIf(cond));
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

	public static String returnType(int type)
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

	public static String returnStringIf(Condition condition)
	{
		BindNode node = condition.getNode();
		int type = condition.getType();
		Object param = condition.getParam();
		String topXpath = IoXslUtil.getTopXpath();
		String nodeStr = IoXslUtil.comparePath(topXpath, IoXslUtil
				.getXSLXpath(node));
		String typeStr = returnYunsuanfu(type);
		String paramStr = "";
		if (param instanceof BindNode)
		{
			BindNode value = (BindNode) param;
			paramStr = IoXslUtil.comparePath(topXpath, IoXslUtil
					.getXSLXpath(value));
		} else if (param instanceof String)
		{
			paramStr = IoXslUtil.getXmlText(param.toString());
			if (paramStr.equalsIgnoreCase("@null"))
			{
				paramStr = "";
			} else if (paramStr.equalsIgnoreCase("@dnull"))
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
		}
		else if(type>=Condition.POSITIONMOD3&&type<Condition.POSITIONMOD9)
		{
			output.append("(position() mod "+(type+3-Condition.POSITIONMOD3)+")="+param);
		}
		else if (type == Condition.LENGTHLESS)
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
			IoXslUtil.addNameSpace(textlinesNameSpace);
			if (param instanceof BindNode)
			{
				output.append("regular:getResult(" + paramStr + "," + nodeStr
						+ ")");
			} else if (param instanceof String)
			{
				output.append("regular:getResult('" + paramStr + "'," + nodeStr
						+ ")");
			}
		}
		else if (type == Condition.COUNT)
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
		}
		else if(type>=Condition.COUNTMOD3&&type<Condition.COUNTMOD9)
		{
			output.append("(count(" + nodeStr + ") mod "+(type+3-Condition.COUNTMOD3)+")="+param);
		}
		else
		{
			output.append(nodeStr + typeStr + paramStr);
		}
		return output.toString();
	}

	public static String returnYunsuanfu(int type)
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

	public static String startGroup(Group group)
	{
		if (group == null)
		{
			return "";
		}
		StringBuffer output = new StringBuffer();
		String xpath = "";
		if (group.getNode() != null)
		{
			xpath = IoXslUtil.getXSLXpath(group.getNode());
		}

		// String startName = "/" + xpath;
		// String countStr = "count(" + xpath + ")";
		// FovTableInfo tableinfo = new FovTableInfo(startName, countStr);
		// if (IoXslUtil.isXmlEditable() == Constants.EN_EDITABLE)
		// {
		// output.append( tableinfo.writeStart();
		// } else if (IoXslUtil.isXmlEditable() == Constants.EN_EDITVARIBLE)
		// {
		// output.append( startElement(FoXsltConstants.CHOOSE);
		// String test =
		// "contains($XmlEditable,'yes') and contains('yes',$XmlEditable)";
		// output.append( startElementWI(FoXsltConstants.WHEN, test);
		// output.append( tableinfo.writeStart();
		// output.append( endElement(FoXsltConstants.WHEN);
		// output.append( endElement(FoXsltConstants.CHOOSE);
		// }
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.FOR_EACH);
		String topXpath = IoXslUtil.getTopXpath();
		IoXslUtil.addXpath(group);

		if (group.getFliterCondition() != null)
		{
			String ifStr = returnStringIf(group.getFliterCondition(), true);
			if (topXpath.equals(""))
			{
				output.append(outputAttributes(FoXsltConstants.SELECT, "/"
						+ xpath + "[" + ifStr + "]"));
			} else
			{
				output.append(outputAttributes(FoXsltConstants.SELECT,
						IoXslUtil.comparePath(topXpath, xpath + "[" + ifStr
								+ "]")));
			}
		} else
		{
			if (topXpath.equals(""))
			{
				output.append(outputAttributes(FoXsltConstants.SELECT, "/"
						+ xpath));
			} else
			{
				output.append(outputAttributes(FoXsltConstants.SELECT,
						IoXslUtil.comparePath(topXpath, xpath)));
			}
		}
		output.append(ElementWriter.TAGEND);
		List<Sort> sortList = group == null ? null : group.getSortSet();
		if (sortList != null)
		{
			int num = sortList.size();
			for (int i = 0; i < num; i++)
			{
				Sort current = sortList.get(i);
				output.append(sort(current, xpath));
			}
		}
		EnumNumber maxNumber = group == null ? null : group.getMax();
		if (maxNumber != null && maxNumber.getEnum() <= 0)
		{
			int number = (Integer) maxNumber.getNumber() + 1;
			if (number > 0)
			{
				output.append(startElementWI(FoXsltConstants.IF,
						"position() &lt; " + number));
			}
		}
		// if (IoXslUtil.getAddPageNumberType() || IoXslUtil.isHavaindex())
		// {
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.VARIABLE);
		output.append(ElementUtil.outputAttributes(FoXsltConstants.NAME, "n"
				+ (IoXslUtil.getXpath().size() - 1)));
		output.append(ElementWriter.TAGEND);
		output.append(ElementValueOf("position()"));
		output.append(ElementWriter.TAGENDSTART);
		output.append(FoXsltConstants.VARIABLE);
		output.append(ElementWriter.TAGEND);
		// }
		return output.toString();
	}

	public static String startSimpleGroup(Group group)
	{
		if (group == null)
		{
			return "";
		}
		StringBuffer output = new StringBuffer();
		String xpath = "";
		if (group.getNode() != null)
		{
			xpath = IoXslUtil.getXSLXpath(group.getNode());
		}

		// String startName = "/" + xpath;
		// String countStr = "count(" + xpath + ")";
		// FovTableInfo tableinfo = new FovTableInfo(startName, countStr);
		// if (IoXslUtil.isXmlEditable() == Constants.EN_EDITABLE)
		// {
		// output.append( tableinfo.writeStart();
		// } else if (IoXslUtil.isXmlEditable() == Constants.EN_EDITVARIBLE)
		// {
		// output.append( startElement(FoXsltConstants.CHOOSE);
		// String test =
		// "contains($XmlEditable,'yes') and contains('yes',$XmlEditable)";
		// output.append( startElementWI(FoXsltConstants.WHEN, test);
		// output.append( tableinfo.writeStart();
		// output.append( endElement(FoXsltConstants.WHEN);
		// output.append( endElement(FoXsltConstants.CHOOSE);
		// }
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.FOR_EACH);
		String topXpath = IoXslUtil.getTopXpath();
		IoXslUtil.addXpath(group);

		if (group.getFliterCondition() != null)
		{
			String ifStr = returnStringIf(group.getFliterCondition(), true);
			if (topXpath.equals(""))
			{
				output.append(outputAttributes(FoXsltConstants.SELECT, "/"
						+ xpath + "[" + ifStr + "]"));
			} else
			{
				output.append(outputAttributes(FoXsltConstants.SELECT,
						IoXslUtil.comparePath(topXpath, xpath + "[" + ifStr
								+ "]")));
			}
		} else
		{
			if (topXpath.equals(""))
			{
				output.append(outputAttributes(FoXsltConstants.SELECT, "/"
						+ xpath));
			} else
			{
				output.append(outputAttributes(FoXsltConstants.SELECT,
						IoXslUtil.comparePath(topXpath, xpath)));
			}
		}
		output.append(ElementWriter.TAGEND);
		List<Sort> sortList = group == null ? null : group.getSortSet();
		if (sortList != null)
		{
			int num = sortList.size();
			for (int i = 0; i < num; i++)
			{
				Sort current = sortList.get(i);
				output.append(sort(current, xpath));
			}
		}
		EnumNumber maxNumber = group == null ? null : group.getMax();
		if (maxNumber != null && maxNumber.getEnum() <= 0)
		{
			int number = (Integer) maxNumber.getNumber() + 1;
			if (number > 0)
			{
				output.append(startElementWI(FoXsltConstants.IF,
						"position() &lt; " + number));
			}
		}
		// if (IoXslUtil.getAddPageNumberType() || IoXslUtil.isHavaindex())
		// {

		// }
		return output.toString();
	}

	public static String sort(Sort sort, String xpath)
	{
		String value = IoXslUtil.comparePath(xpath, IoXslUtil.getXSLXpath(sort
				.getNode()));
		String type = sort.getDataType() == Sort.NUMBER ? FoXsltConstants.NUMBER
				: FoXsltConstants.TEXT;
		String order = sort.getOrder() == Sort.DESCENDING ? FoXsltConstants.DESCENDING
				: FoXsltConstants.ASCENDING;
		String caseOrder = sort.getCaseOrder() == Sort.LOWERFIRST ? FoXsltConstants.LOWER_FIRST
				: FoXsltConstants.UPPER_FIRST;
		StringBuffer output = new StringBuffer();
		output.append("<");
		output.append(FoXsltConstants.SORT);
		output.append(outputAttributes(FoXsltConstants.SELECT, value));
		output.append(outputAttributes(FoXsltConstants.DATETYPE, type));
		output.append(outputAttributes(FoXsltConstants.ORDER, order));
		if (type.equalsIgnoreCase(FoXsltConstants.TEXT))
		{
			output.append(outputAttributes(FoXsltConstants.CASE_ORDER,
					caseOrder));
		}
		output.append("/>");
		return output.toString();
	}

	public static String endGroup(Group group)
	{
		if (group == null)
		{
			return "";
		}
		StringBuffer output = new StringBuffer();
		if (group.getMax() != null && group.getMax().getEnum() <= 0)
		{
			output.append(endElement(FoXsltConstants.IF));
		}
		output.append(endElement(FoXsltConstants.FOR_EACH));
		IoXslUtil.deleteXpath();
		return output.toString();
	}

	public static String endCurrentGroup(Group group)
	{
		if (group == null)
		{
			return "";
		}
		StringBuffer output = new StringBuffer();
		if (group.getMax() != null && group.getMax().getEnum() <= 0)
		{
			output.append(endElement(FoXsltConstants.IF));
		}
		output.append(endElement(FoXsltConstants.FOR_EACH));
		return output.toString();
	}

	/**
	 * 
	 * 获得传入的元素名对应的元素的开头部分，仅能处理xsl:value-of、xsl:for-each等只输出属性select的元素。
	 * 
	 * @param elementname
	 *            元素名
	 * @param select
	 *            select属性的值
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String startElementVF(String elementname, String select)
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(elementname);
		output.append(outputAttributes(FoXsltConstants.SELECT, select));
		output.append(ElementWriter.TAGEND);
		return output.toString();
	}

	public static String elementVarible(String elementname, String select)
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.VARIABLE);
		output.append(outputAttributes(FoXsltConstants.NAME, elementname));
		if (select.contains("concat")
				&& (select.contains("position") || select.contains("last")))
		{
			output.append(outputAttributes(FoXsltConstants.SELECT, select));
		} else
		{
			output.append(outputAttributes(FoXsltConstants.SELECT, "'" + select
					+ "'"));
		}
		output.append(ElementWriter.NOCHILDTAGEND);
		return output.toString();
	}

	public static String elementVaribleSimple(String elementname, String select)
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.VARIABLE);
		output.append(outputAttributes(FoXsltConstants.NAME, elementname));
		output.append(outputAttributes(FoXsltConstants.SELECT, select));
		output.append(ElementWriter.NOCHILDTAGEND);
		return output.toString();
	}

	/**
	 * 
	 * 获得传入的元素名对应的元素的代码，仅能处理xsl:value-of等可以没有text节点的元素。
	 * 
	 * @param select
	 *            select属性的值
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String ElementValueOf(String select)
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.VALUE_OF);
		output.append(outputAttributes(FoXsltConstants.SELECT, select));
		output.append(ElementWriter.NOCHILDTAGEND);
		return output.toString();
	}

	/**
	 * 
	 * 获得xsl:param元素。
	 * 
	 * @param select
	 *            select属性的值
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String ElementParam(String name)
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.PARAM);
		output.append(outputAttributes(FoXsltConstants.NAME, name));
		output.append(ElementWriter.NOCHILDTAGEND);
		return output.toString();
	}

	/**
	 * 
	 * 获得xsl:output元素对应的代码。
	 * 
	 * @param version
	 *            version属性的值
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String ElementOutput(String version)
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.OUTPUT);
		output.append(outputAttributes(FoXsltConstants.METHOD,
				FoXsltConstants.XML));
		output.append(outputAttributes(FoXsltConstants.ENCODING,
				FoXsltConstants.UTF_8));
		output.append(outputAttributes(FoXsltConstants.INDENT,
				FoXsltConstants.NO));
		output.append(outputAttributes(FoXsltConstants.VERSION, version));
		output.append(ElementWriter.NOCHILDTAGEND);
		return output.toString();
	}

	/**
	 * 
	 * 获得xsl:sort元素对应的代码。
	 * 
	 * @param select
	 *            select属性的值
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String ElementSort(Map<Integer, String> attrmap)
	{
		// HashMap<Integer, String> sortMap = new HashMap<Integer, String>();
		// sortMap.put(Constants.PR_XSL_SELECT, FoXsltConstants.SELECT);
		// sortMap.put(Constants.PR_XSL_ORDER, FoXsltConstants.ORDER);
		// sortMap.put(Constants.PR_XSL_CASE_ORDER, FoXsltConstants.CASE_ORDER);
		// sortMap.put(Constants.PR_XSL_DATE_TYPE, FoXsltConstants.DATE_TYPE);
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.SORT);
		if (attrmap != null)
		{
			Object[] keys = attrmap.keySet().toArray();
			int size = keys.length;
			for (int i = 0; i < size; i++)
			{
				int key = (Integer) keys[i];
				XSLAttributeNameWriter attributeNameWriter = new XSLAttributeNameWriter();
				String attr = attributeNameWriter.getKeyName(key);
				String attrValue = attrmap.get(key);
				output.append(outputAttributes(attr, attrValue));
			}
		} else
		{
			LogUtil.debug("传入的参数\"" + attrmap + "\"为null");
		}
		output.append(ElementWriter.NOCHILDTAGEND);
		return output.toString();
	}

	/**
	 * 
	 * 获得传入的元素名对应的元素的结束部分。
	 * 
	 * @param path
	 *            ：要求相对路径的xpath
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String endElement(String elementname)
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGENDSTART);
		output.append(elementname);
		output.append(ElementWriter.TAGEND);
		return output.toString();
	}

	/**
	 * 输出调用模板语句
	 * 
	 * @param name
	 *            模板名
	 * @param value
	 *            参数集
	 * @return output 最终输出结果
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String outputCalltemplate(String name,
			Map<String, Object> parammap)
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.CALL_TEMPLATE);
		output.append(outputAttributes(FoXsltConstants.NAME, name));
		output.append(ElementWriter.TAGEND);
		if (parammap != null)
		{
			Object[] param = parammap.keySet().toArray();
			int size = param.length;
			for (int i = 0; i < size; i++)
			{
				String key = (String) param[i];
				Object value = parammap.get(key);
				if (value != null)
				{
					if (value instanceof String)
					{
						String valuestr = (String) value;
						output.append(noSelectAssignment(
								FoXsltConstants.WITH_PARAM, key, valuestr));
					} else
					{
						output.append(selectAssignment(
								FoXsltConstants.WITH_PARAM, key, value));
					}
				}
			}
		}
		output.append(endElement(FoXsltConstants.CALL_TEMPLATE));
		return output.toString();
	}

	public static String outputCalltemplateDateTime(String name,
			Map<String, Object> parammap)
	{
		StringBuffer output = new StringBuffer();
		if (name != null && !"".equals(name))
		{
			output.append(ElementWriter.TAGBEGIN);
			output.append(FoXsltConstants.CALL_TEMPLATE);
			output.append(outputAttributes(FoXsltConstants.NAME, name));
			output.append(ElementWriter.TAGEND);
			Object[] param = parammap.keySet().toArray();
			int size = param.length;
			for (int i = 0; i < size; i++)
			{
				String key = (String) param[i];
				Object value = parammap.get(key);
				if (value != null)
				{
					if (value instanceof String)
					{
						StringBuffer valueBuffer = new StringBuffer();
						valueBuffer.append(value);
						output.append(selectAssignment(
								FoXsltConstants.WITH_PARAM, key, valueBuffer));
					} else
					{
						output.append(selectAssignment(
								FoXsltConstants.WITH_PARAM, key, value));
					}
				}
			}
			output.append(endElement(FoXsltConstants.CALL_TEMPLATE));
		}
		return output.toString();
	}

	/**
	 * 输出给元素赋值（用select属性）语句，适用于value为非String类型。
	 * 
	 * @param elementname
	 *            元素名
	 * @param attributename
	 *            元素的name属性的值
	 * @param attributename
	 *            元素的值
	 * @return output 最终输出结果
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String selectAssignment(String elementname,
			String attributename, Object value)
	{
		StringBuffer output = new StringBuffer();
		String valueStr = IoXslUtil.dealToString(value);
		if (!"".equalsIgnoreCase(valueStr)
				&& !"".equalsIgnoreCase(attributename))
		{
			output.append(ElementWriter.TAGBEGIN);
			output.append(elementname);
			output
					.append(outputAttributes(FoXsltConstants.NAME,
							attributename));
			output.append(outputAttributes(FoXsltConstants.SELECT, valueStr));
			output.append(ElementWriter.NOCHILDTAGEND);
		}
		return output.toString();
	}

	/**
	 * 输出给元素赋值（无select属性）语句，适用于value为String类型。
	 * 
	 * @param elementname
	 *            元素名
	 * @param attributename
	 *            元素的name属性的值
	 * @param attributename
	 *            元素的值
	 * @return output 最终输出结果
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String noSelectAssignment(String elementname,
			String attributename, String value)
	{
		StringBuffer output = new StringBuffer();
		if (!"".equalsIgnoreCase(value))
		{
			output.append(ElementWriter.TAGBEGIN);
			output.append(elementname);
			output
					.append(outputAttributes(FoXsltConstants.NAME,
							attributename));
			output.append(ElementWriter.TAGEND);
			output.append(value);
			output.append(ElementWriter.TAGENDSTART);
			output.append(elementname);
			output.append(ElementWriter.TAGEND);
		}
		return output.toString();
	}

	/**
	 * 将属性名和值输出
	 * 
	 * @param name
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return output 最终输出结果
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String outputAttributes(String name, String value)
	{
		StringBuffer output = new StringBuffer();
		if (name != null && value != null && !name.isEmpty()
				&& !value.isEmpty())
		{
			output.append(" ");
			output.append(name);
			output.append("=");
			output.append("\"");
			output.append(value);
			output.append("\"");
		}
		return output.toString();
	}

	/**
	 * 
	 * 获得适合模板输出用的目录对象
	 * 
	 * @param doc
	 *            ：文档对象
	 * @return TableContents: {返回参数说明}
	 * @exception
	 */
	public static TableContents getXSLTableContens(Document doc,
			TableContents tablecontents)
	{
		TableContents returntablecontents = null;
		if (doc != null)
		{
			TableContents doctablecontents = doc.getTableContents();
			if (doctablecontents != null)
			{
				// 第一步，找到所有的block对象
				List<CellElement> flows = new ArrayList<CellElement>();
				int docsize = doc.getChildCount();
				for (int i = 0; i < docsize; i++)
				{
					CellElement element = (CellElement) doc.getChildAt(i);
					if (element instanceof PageSequence)
					{
						PageSequence pageSequence = (PageSequence) element;
						// 索引只对版心区的内容进行索引，因此只取版心区的内容；
						// Flow mainflow = pageSequence.getMainFlow();
						flows.add(pageSequence.getMainFlow());
					}
				}
				List<CellElement> blocks = DocumentUtil.getElements(flows,
						Block.class);
				TableContents documenttablecontents = doc.getTableContents();
				if (documenttablecontents != null && blocks != null
						&& !blocks.isEmpty())
				{
					CellElement oldblock = null;
					CellElement contentblock = null;

					int maxblocklevel = (Integer) documenttablecontents
							.getAttribute(Constants.PR_BLOCK_REF_SHOW_LEVEL);
					for (CellElement block : blocks)
					{
						Integer blocklevel = (Integer) block
								.getAttribute(Constants.PR_BLOCK_LEVEL);

						// 找到其中设置有大纲属性的block对象
						if (blocklevel != null && blocklevel >= 0
								&& blocklevel <= maxblocklevel)
						{
							block.setAttribute(Constants.PR_ID, Integer
									.toHexString(block.hashCode())
									+ "a");
							if (contentblock == null)
							{
								contentblock = new Block();
							}
							// 如果是第一个有大纲对象的block对象
							if (oldblock == null)
							{
								Element parent = block;
								CellElement leafblock = null;
								CellElement currentblock = null;
								// 遍历其父对象，只要其中含有组属性或是条件属性，就用一个block套起来
								while (parent != null)
								{
									Object condition = parent
											.getAttribute(Constants.PR_CONDTION);
									Object group = parent
											.getAttribute(Constants.PR_GROUP);
									if (condition != null || group != null)
									{
										Map<Integer, Object> blockatt = new HashMap<Integer, Object>();
										if (condition != null)
										{
											blockatt.put(Constants.PR_CONDTION,
													condition);
										}
										if (group != null)
										{
											blockatt.put(Constants.PR_GROUP,
													group);
										}
										com.wisii.wisedoc.document.Group cblock = new com.wisii.wisedoc.document.Group(
												blockatt);
										if (leafblock == null)
										{
											leafblock = cblock;
										} else
										{
											cblock.add(currentblock);
										}
										currentblock = cblock;
									}
									parent = parent.getParent();
								}
								if (currentblock != null)
								{
									contentblock.add(currentblock);
									contentblock = leafblock;
								}
							}
							// 如果之前已经有设置有大纲属性的block对象
							else
							{
								Element oldparent = oldblock;
								CellElement insertblock = contentblock;
								Element commonparent = null;
								// 找到当前block和之前block共同的祖先对象
								l1: while (oldparent != null)
								{
									Object condition = oldparent
											.getAttribute(Constants.PR_CONDTION);
									Object group = oldparent
											.getAttribute(Constants.PR_GROUP);
									Element thisparent = block;
									while (thisparent != null)
									{
										if (thisparent == oldparent)
										{
											commonparent = thisparent;
											break l1;
										}

										thisparent = thisparent.getParent();
									}
									if (condition != null || group != null)
									{
										insertblock = (CellElement) insertblock
												.getParent();
									}
									oldparent = oldparent.getParent();
								}
								if (commonparent != null)
								{
									Element thisparent = block;
									CellElement leafgroup = null;
									CellElement currentgroup = null;
									while (thisparent != commonparent)
									{
										Object condition = thisparent
												.getAttribute(Constants.PR_CONDTION);
										Object group = thisparent
												.getAttribute(Constants.PR_GROUP);
										if (condition != null || group != null)
										{
											Map<Integer, Object> blockatt = new HashMap<Integer, Object>();
											if (condition != null)
											{
												blockatt.put(
														Constants.PR_CONDTION,
														condition);
											}
											if (group != null)
											{
												blockatt.put(
														Constants.PR_GROUP,
														group);
											}
											com.wisii.wisedoc.document.Group cgroup = new com.wisii.wisedoc.document.Group(
													blockatt);
											if (leafgroup == null)
											{
												leafgroup = cgroup;
											} else
											{
												cgroup.add(currentgroup);
											}
											currentgroup = cgroup;
										}
										thisparent = thisparent.getParent();
									}
									// 如果当前group不等于null
									if (currentgroup != null)
									{
										insertblock.add(currentgroup);
										contentblock = leafgroup;
									}
									// 如果当前group等于null，证明该block的祖先对象没有设置组属性，因此，当前的插入点位置为顶级的block上
									else
									{
										contentblock = insertblock;
									}
								}
							}
							oldblock = block;
							CellElement colneblock = new Block(doctablecontents
									.getAttributesofLevel(blocklevel)
									.getAttributes());
							List<CellElement> colneblockchildren = new ArrayList<CellElement>();
							int size = block.getChildCount();
							for (int i = 0; i < size; i++)
							{
								CellElement blockchild = (CellElement) block
										.getChildAt(i).clone();
								// 清除掉其中文本对象的文本属性，文本对象的文本属性
								if (blockchild instanceof TextInline)
								{
									blockchild.setAttributes(null, true);
								}
								colneblockchildren.add(blockchild);
							}
							// 每个目录项的标题内容放在basiclink对象中
							Map<Integer, Object> linkatt = new HashMap<Integer, Object>();
							linkatt.put(Constants.PR_INTERNAL_DESTINATION,
									block.getAttribute(Constants.PR_ID));

							BasicLink baselink = new BasicLink(linkatt);
							baselink.insert(colneblockchildren, 0);
							colneblock.insert(baselink, 0);
							Attributes levelatt = doctablecontents
									.getAttributesofLevel(blocklevel);
							if (levelatt != null)
							{
								colneblock.setAttributes(levelatt
										.getAttributes(), true);
							} else
							{
								colneblock.setAttributes(null, true);
							}
							Leader leader = new Leader(doctablecontents
									.getAttributesofLeader().getAttributes());
							colneblock.insert(leader, 1);
							Map<Integer, Object> pagenatt = new HashMap<Integer, Object>();
							pagenatt.put(Constants.PR_REF_ID, block
									.getAttribute(Constants.PR_ID));
							PageNumberCitation pagenumberc = new PageNumberCitation(
									pagenatt);
							colneblock.insert(pagenumberc, 2);
							contentblock.add(colneblock);
						}
					}
					if (contentblock != null)
					{
						CellElement rootcontentblock = contentblock;
						while (contentblock != null)
						{
							rootcontentblock = contentblock;
							contentblock = (CellElement) contentblock
									.getParent();
						}
						Map<Integer, Object> tcatt = null;
						if (tablecontents != null)
						{
							tcatt = tablecontents.getAttributes()
									.getAttributes();
							// 清除掉其中段落相关的属性
							if (tcatt != null)
							{
								tcatt.remove(Constants.PR_BLOCK_REF_STYLES);
								tcatt.remove(Constants.PR_LEADER_LENGTH);
								tcatt.remove(Constants.PR_LEADER_PATTERN);
								tcatt.remove(Constants.PR_BASELINE_SHIFT);
								tcatt.remove(Constants.PR_BLOCK_REF_SHOW_LEVEL);
							}
						}
						returntablecontents = new TableContents(tcatt);
						returntablecontents.add(rootcontentblock);
						returntablecontents.setAttributes(null, true);
					}
				}
			}
		}
		// childIt(returntablecontents);
		return returntablecontents;
	}

	@SuppressWarnings("unchecked")
	public static void childIt(CellElement element)
	{
		@SuppressWarnings("unused")
		Map<Integer, Object> attributemap = new HashMap<Integer, Object>();
		if (element != null && element.getAttributes() != null)
		{
			attributemap = element.getAttributes().getAttributes();
		}
		if (element != null && element.children() != null)
		{
			Enumeration<CellElement> listobj = element.children();
			while (listobj.hasMoreElements())
			{
				CellElement obj = listobj.nextElement();
				childIt(obj);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<Integer, Object> getCompleteAttributes(
			Map<Integer, Object> map, Class type)
	{
		Map<Integer, Object> mapdif = DefaultValueMap
				.getDifferentAttributes(type);
		if (mapdif != null)
		{
			if (map == null)
			{
				return mapdif;
			} else
			{
				Object[] keys = mapdif.keySet().toArray();
				for (int i = 0; i < keys.length; i++)
				{
					int key = (Integer) keys[i];
					if (!map.containsKey(key))
					{
						map.put(key, mapdif.get(key));
					}
				}
			}
		}
		if (map != null)
		{
			Object[] mapkeys = map.keySet().toArray();
			for (int i = 0; i < mapkeys.length; i++)
			{
				int key = (Integer) mapkeys[i];
				if (DefaultValueMap.initmap.isEmpty())
				{
					DefaultValueMap.setInitMap();
				}
				if (DefaultValueMap.initmap.containsKey(key))
				{
					Object mapobj = map.get(key);
					Object initmapobj = DefaultValueMap.initmap.get(key);
					if (mapobj != null && mapobj.equals(initmapobj))
					{
						map.remove(key);
					}
				}
			}
		}

		if (type == TableCell.class)
		{

			if (TableWriter.getCurrentStatic())
			{
				if (map.containsKey(Constants.PR_WIDTH))
				{
				} else if (map
						.containsKey(Constants.PR_INLINE_PROGRESSION_DIMENSION))
				{
					Object width = map
							.get(Constants.PR_INLINE_PROGRESSION_DIMENSION);
					map.remove(Constants.PR_INLINE_PROGRESSION_DIMENSION);
					map.put(Constants.PR_WIDTH, width);
				}
			} else
			{
				if (map.containsKey(Constants.PR_WIDTH))
				{
					map.remove(Constants.PR_WIDTH);
				} else if (map
						.containsKey(Constants.PR_INLINE_PROGRESSION_DIMENSION))
				{
					map.remove(Constants.PR_INLINE_PROGRESSION_DIMENSION);
				}
			}
		}
		return map;
	}

	public static String repalaceSpecialSymbols(String src)
	{
		String value = null;
		if (src != null)
		{
			value = src.replaceAll("&", "&amp;");
			value = value.replaceAll("<", "&lt;");
			value = value.replaceAll(">", "&gt;");
			value = value.replaceAll("'", "&apos;");
			value = value.replaceAll("\"", "&quot;");
		}
		return value;
	}

	public static Map<Integer, Object> clearMap(Map<Integer, Object> map)
	{
		if (map.get(Constants.PR_BACKGROUND_IMAGE) == null)
		{
			map.remove(Constants.PR_BACKGROUND_POSITION_HORIZONTAL);
			map.remove(Constants.PR_BACKGROUND_POSITION_VERTICAL);
			map.remove(Constants.PR_BACKGROUND_REPEAT);
			map.remove(Constants.PR_BACKGROUNDGRAPHIC_LAYER);
		}
		if ((map.get(Constants.PR_BORDER_STYLE) == null)
				|| ((EnumProperty) map.get(Constants.PR_BORDER_STYLE))
						.getEnum() == Constants.EN_NONE)
		{
			map.remove(Constants.PR_BORDER_COLOR);
			map.remove(Constants.PR_BORDER_WIDTH);
		}
		if ((map.get(Constants.PR_BORDER_BEFORE_STYLE) == null)
				|| ((EnumProperty) map.get(Constants.PR_BORDER_BEFORE_STYLE))
						.getEnum() == Constants.EN_NONE)
		{
			map.remove(Constants.PR_BORDER_BEFORE_COLOR);
			map.remove(Constants.PR_BORDER_BEFORE_WIDTH);
		}
		if ((map.get(Constants.PR_BORDER_AFTER_STYLE) == null)
				|| ((EnumProperty) map.get(Constants.PR_BORDER_AFTER_STYLE))
						.getEnum() == Constants.EN_NONE)
		{
			map.remove(Constants.PR_BORDER_AFTER_COLOR);
			map.remove(Constants.PR_BORDER_AFTER_WIDTH);
		}
		if ((map.get(Constants.PR_BORDER_START_STYLE) == null)
				|| ((EnumProperty) map.get(Constants.PR_BORDER_START_STYLE))
						.getEnum() == Constants.EN_NONE)
		{
			map.remove(Constants.PR_BORDER_START_COLOR);
			map.remove(Constants.PR_BORDER_START_WIDTH);
		}
		if ((map.get(Constants.PR_BORDER_END_STYLE) == null)
				|| ((EnumProperty) map.get(Constants.PR_BORDER_END_STYLE))
						.getEnum() == Constants.EN_NONE)
		{
			map.remove(Constants.PR_BORDER_END_COLOR);
			map.remove(Constants.PR_BORDER_END_WIDTH);
		}
		// if (!map.containsKey(Constants.PR_LINE_HEIGHT))
		// {
		// map.put(Constants.PR_LINE_HEIGHT, "normal");
		// }
		// // if (map.containsKey(Constants.PR_LINE_HEIGHT))
		// // {
		// // map.remove(Constants.PR_LINE_HEIGHT);
		// // }
		// if (!map.containsKey(Constants.PR_LINE_HEIGHT_SHIFT_ADJUSTMENT))
		// {
		// map.put(Constants.PR_LINE_HEIGHT_SHIFT_ADJUSTMENT,"consider-shifts");
		// }
		// if (!map.containsKey(Constants.PR_LINE_STACKING_STRATEGY))
		// {
		// map.put(Constants.PR_LINE_STACKING_STRATEGY, InitialManager
		// .getInitalAttributes().getAttribute(
		// Constants.PR_LINE_STACKING_STRATEGY));
		// }
		return map;
	}
	// public static void main(String[] args)
	// {
	// String str = "as\"'&<>";
	// System.out.println(str);
	// System.out.println(repalaceSpecialSymbols(str));
	// }
}
