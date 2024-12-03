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

package com.wisii.wisedoc.io.xsl;

import java.util.List;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.PageNumberCitationLast;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.TotalPageNumber;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.Sort;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class PageNumberCitationWriter extends AbsElementWriter
{

	String name = "";

	public PageNumberCitationWriter(CellElement element)
	{
		super(element);
		name = element.getClass().getName();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getElementName()
	{
		String elementName = FoXsltConstants.PAGE_NUMBER_CITATION;
		if (name.equalsIgnoreCase(PageNumberCitationLast.class.getName()))
		{
			elementName = FoXsltConstants.PAGE_NUMBER_CITATION_LAST;
		}
		return elementName;
	}

	public String getHeaderElement()
	{
		StringBuffer code = new StringBuffer();
		String[] att2 = getAttributes2();
		code.append(att2[0]);
		code.append(getHeaderStart());
		code.append(att2[1]);
		code.append(getHeaderEnd());
		return code.toString();
	}

	public String[] getAttributes2()
	{
		StringBuffer code = new StringBuffer();
		StringBuffer refCode = new StringBuffer();
		Object[] keys = attributemap.keySet().toArray();
		int size = keys.length;
		CellElement ps = foElement;
		while (!(ps instanceof PageSequence))
		{
			ps = (CellElement) ps.getParent();
		}
		WiseDocDocument doc = (WiseDocDocument) ps.getParent();
		int index = doc.getIndex(ps);
		int total = doc.getChildCount() - 1;
		if (foElement instanceof TotalPageNumber)
		{
			EnumProperty showtotal = attributemap
					.containsKey(Constants.PR_ENDOFALL) ? (EnumProperty) attributemap
					.get(Constants.PR_ENDOFALL)
					: null;
			int showTotal = showtotal == null ? 0 : showtotal.getEnum();
			if (showTotal == Constants.EN_TRUE)
			{
				PageSequenceWriter.setAddBlock(true);
				int currentps = 0;
				String docstart = "";
				String docend = "";

				for (int i = total; i > -1; i--)
				{
					PageSequence current = (PageSequence) doc.getChildAt(i);
					if (i == total)
					{
						docstart = setDocStart(current);
						docend = setDocEnd(current);
					}
					if (current.getAttribute(Constants.PR_GROUP) != null)
					{
						Group cg = (Group) current
								.getAttribute(Constants.PR_GROUP);
						refCode.append(ElementWriter.TAGBEGIN);
						refCode.append(FoXsltConstants.VARIABLE);
						refCode.append(ElementUtil.outputAttributes(
								FoXsltConstants.NAME, "v" + i));
						refCode.append(ElementWriter.TAGEND);
						refCode.append(docstart);
						refCode.append(startCurrentGroup(cg, (Group) current
								.getParent().getAttribute(Constants.PR_GROUP)));
						refCode.append(ElementUtil.startElementWI(
								FoXsltConstants.IF, "position()=last()"));
						refCode.append(ElementUtil.startElementVF(
								FoXsltConstants.VALUE_OF, "last()"));
						refCode.append(ElementUtil
								.endElement(FoXsltConstants.VALUE_OF));
						refCode.append(ElementUtil
								.endElement(FoXsltConstants.IF));

						refCode.append(ElementUtil.endCurrentGroup(cg));
						refCode.append(docend);
						refCode.append(ElementWriter.TAGENDSTART);
						refCode.append(FoXsltConstants.VARIABLE);
						refCode.append(ElementWriter.TAGEND);

						refCode.append(ElementWriter.TAGBEGIN);
						refCode.append(FoXsltConstants.VARIABLE);
						refCode.append(ElementUtil.outputAttributes(
								FoXsltConstants.NAME, "p" + i));
						refCode.append(ElementWriter.TAGEND);
						refCode.append(getLastId("e" + i + "e", cg,
								(Group) current.getParent().getAttribute(
										Constants.PR_GROUP)));
						refCode.append(ElementWriter.TAGENDSTART);
						refCode.append(FoXsltConstants.VARIABLE);
						refCode.append(ElementWriter.TAGEND);
					} else
					{

						refCode.append(ElementWriter.TAGBEGIN);
						refCode.append(FoXsltConstants.VARIABLE);
						refCode.append(ElementUtil.outputAttributes(
								FoXsltConstants.NAME, "p" + i));
						refCode.append(ElementWriter.TAGEND);
						refCode.append(getLastId("e" + i + "e", null,
								(Group) current.getParent().getAttribute(
										Constants.PR_GROUP)));
						refCode.append(ElementWriter.TAGENDSTART);
						refCode.append(FoXsltConstants.VARIABLE);
						refCode.append(ElementWriter.TAGEND);
						currentps = i;
						break;
					}

				}
				refCode.append(getRealVariable(total, currentps));
				code.append(ElementUtil.outputAttributes(
						FoXsltConstants.REF_ID, "{$" + "v" + "}"));
			} else if (showTotal == Constants.EN_FALSE)
			{
				PageSequenceWriter.setAddBlock(true);

				refCode.append(ElementWriter.TAGBEGIN);
				refCode.append(FoXsltConstants.VARIABLE);
				refCode.append(ElementUtil.outputAttributes(
						FoXsltConstants.NAME, "c" + index));
				refCode.append(ElementWriter.TAGEND);
				// refCode = refCode
				// + ElementUtil.outputAttributes(FoXsltConstants.SELECT,
				// IoXslUtil.getCurrentId("e" + index + "e"));
				refCode.append(IoXslUtil.getCurrentId("e" + index + "e"));
				refCode
						.append(ElementUtil
								.endElement(FoXsltConstants.VARIABLE));

				code.append(ElementUtil.outputAttributes(
						FoXsltConstants.REF_ID, "{$" + "c" + index + "}"));
			}
		}
		for (int i = 0; i < size; i++)
		{
			int key = (Integer) keys[i];
			if (key == Constants.PR_REF_ID)
			{
				if (!(foElement instanceof TotalPageNumber))
				{
					String refid = foElement.getId();
					code.append(getAttribute(key, IoXslUtil
							.getId(index + refid)));
				}
			} else if (key == Constants.PR_FONT_FAMILY)
			{
				code
						.append(getAttribute(key, "'" + attributemap.get(key)
								+ "'"));
			} else if (key == Constants.PR_ENDOFALL
					|| key == Constants.PR_CONDTION)
			{
			} else
			{
				code.append(getAttribute(key, attributemap.get(key)));
			}
		}
		String[] attStr =
		{ refCode.toString(), code.toString() };
		return attStr;
	}

	public String setDocStart(PageSequence ps)
	{
		StringBuffer start = new StringBuffer();
		Group parent = (Group) ps.getParent().getAttribute(Constants.PR_GROUP);
		if (parent != null)
		{
			start.append(startCurrentGroup(parent, null));
			start.append(ElementUtil.startElementWI(FoXsltConstants.IF,
					"position()=last()"));
		}
		return start.toString();
	}

	public String setDocEnd(PageSequence ps)
	{
		StringBuffer end = new StringBuffer();
		Group parent = (Group) ps.getParent().getAttribute(Constants.PR_GROUP);
		if (parent != null)
		{
			end.append(ElementUtil.endElement(FoXsltConstants.IF));
			end.append(ElementUtil.endCurrentGroup(parent));
		}
		return end.toString();
	}

	public String startCurrentGroup(Group group, Group docgroup)
	{
		if (group == null)
		{
			return "";
		}
		// System.out.println("doc group::" + (docgroup == null));
		StringBuffer output = new StringBuffer();
		String xpath = "";
		if (group.getNode() != null)
		{
			xpath = IoXslUtil.getXSLXpath(group.getNode());
		}
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.FOR_EACH);

		if (group.getFliterCondition() != null)
		{
			String ifStr = returnStringIf(group.getFliterCondition(), true,
					xpath);
			if (docgroup == null || group.equals(docgroup))
			{
				output.append(ElementUtil
						.outputAttributes(FoXsltConstants.SELECT, "/" + xpath
								+ "[" + ifStr + "]"));
			} else
			{
				output.append(ElementUtil.outputAttributes(
						FoXsltConstants.SELECT, IoXslUtil.comparePath(IoXslUtil
								.getXSLXpath(docgroup.getNode()), xpath)
								+ "[" + ifStr + "]"));
			}
		} else
		{
			if (docgroup == null || group.equals(docgroup))
			{
				output.append(ElementUtil.outputAttributes(
						FoXsltConstants.SELECT, "/" + xpath));
			} else
			{
				output.append(ElementUtil.outputAttributes(
						FoXsltConstants.SELECT, IoXslUtil.comparePath(IoXslUtil
								.getXSLXpath(docgroup.getNode()), xpath)));
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
				output.append(ElementUtil.startElementWI(FoXsltConstants.IF,
						"position() &lt; " + number));
			}
		}
		return output.toString();
	}

	@SuppressWarnings("unchecked")
	public String returnStringIf(LogicalExpression condition, boolean isfirest,
			String topXpath)
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
						output.append(returnStringIf(cond, true, topXpath));
					} else
					{
						output.append(returnStringIf(cond, false, topXpath));
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
					output.append(returnStringIf(cond, topXpath));
					if (!(isfirest && number == 1))
					{
						output.append(")");
					}
				}

				if (listType != null && listType.size() > 0 && i < number - 1)
				{
					output.append(ElementUtil.returnType(listType.get(i)));
				}
			}
		}
		return output.toString();
	}

	public String returnStringIf(Condition condition, String topXpath)
	{
		BindNode node = condition.getNode();
		int type = condition.getType();
		Object param = condition.getParam();
		String nodeStr = IoXslUtil.comparePath(topXpath, IoXslUtil
				.getXSLXpath(node));
		String typeStr = ElementUtil.returnYunsuanfu(type);
		String paramStr = "";
		if (param instanceof BindNode)
		{
			BindNode value = (BindNode) param;
			paramStr = IoXslUtil.comparePath(topXpath, IoXslUtil
					.getXSLXpath(value));
		} else if (param instanceof String)
		{
			paramStr = param.toString();
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

	public String sort(Sort sort, String xpath)
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
		output.append(ElementUtil.outputAttributes(FoXsltConstants.SELECT,
				value));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.DATETYPE,
				type));
		output.append(ElementUtil
				.outputAttributes(FoXsltConstants.ORDER, order));
		if (type.equalsIgnoreCase(FoXsltConstants.TEXT))
		{
			output.append(ElementUtil.outputAttributes(
					FoXsltConstants.CASE_ORDER, caseOrder));
		}
		output.append("/>");
		return output.toString();
	}

	public String getLastId(String id, Group gp, Group docgroup)
	{

		StringBuffer lastid = new StringBuffer();
		if (docgroup != null)
		{
			lastid.append(startCurrentGroup(docgroup, null));
			lastid.append(ElementUtil.startElementWI(FoXsltConstants.IF,
					"position()=last()"));
			lastid.append(ElementUtil.startElementVF(FoXsltConstants.VALUE_OF,
					"concat('" + id + "',last(),'d')"));
			lastid.append(ElementUtil.endElement(FoXsltConstants.VALUE_OF));
			if (gp != null)
			{
				lastid.append(startCurrentGroup(gp, docgroup));
				lastid.append(ElementUtil.startElementWI(FoXsltConstants.IF,
						"position()=last()"));
				lastid.append(ElementUtil.startElementVF(
						FoXsltConstants.VALUE_OF, "concat(last(),'d')"));
				lastid.append(ElementUtil.endElement(FoXsltConstants.VALUE_OF));
				lastid.append(ElementUtil.endElement(FoXsltConstants.IF));
				lastid.append(ElementUtil.endCurrentGroup(gp));

			}
			lastid.append(ElementUtil.endElement(FoXsltConstants.IF));
			lastid.append(ElementUtil.endCurrentGroup(docgroup));
		} else
		{
			if (gp != null)
			{
				lastid.append(startCurrentGroup(gp, docgroup));
				lastid.append(ElementUtil.startElementWI(FoXsltConstants.IF,
						"position()=last()"));
				lastid.append(ElementUtil.startElementVF(
						FoXsltConstants.VALUE_OF, "concat('" + id
								+ "',last(),'d')"));
				lastid.append(ElementUtil.endElement(FoXsltConstants.VALUE_OF));
				lastid.append(ElementUtil.endElement(FoXsltConstants.IF));
				lastid.append(ElementUtil.endCurrentGroup(gp));

			} else
			{
				lastid.append(ElementUtil.startElementVF(
						FoXsltConstants.VALUE_OF, "'" + id + "'"));
				lastid.append(ElementUtil.endElement(FoXsltConstants.VALUE_OF));
			}
		}
		return lastid.toString();
	}

	public String getRealVariable(int total, int index)
	{
		StringBuffer result = new StringBuffer();
		result.append(ElementWriter.TAGBEGIN);
		result.append(FoXsltConstants.VARIABLE);
		result.append(ElementUtil.outputAttributes(FoXsltConstants.NAME, "v"));
		result.append(ElementWriter.TAGEND);

		if (total == index)
		{
			result.append(ElementUtil.startElementVF(FoXsltConstants.VALUE_OF,
					"$p" + index));
			result.append(ElementUtil.endElement(FoXsltConstants.VALUE_OF));
		} else
		{
			result.append(ElementUtil.startElement(FoXsltConstants.CHOOSE));
			for (int i = total; i > index - 1; i--)
			{
				if (i > index)
				{
					result.append(ElementUtil.startElementWI(
							FoXsltConstants.WHEN, "not(contains($v" + i
									+ ",'') and contains('',$v" + i + "))"));
					result.append(ElementUtil.startElementVF(
							FoXsltConstants.VALUE_OF, "$p" + i));
					result.append(ElementUtil
							.endElement(FoXsltConstants.VALUE_OF));
					result.append(ElementUtil.endElement(FoXsltConstants.WHEN));
				} else
				{
					result.append(ElementWriter.TAGBEGIN);
					result.append(FoXsltConstants.OTHERWISE);
					result.append(ElementWriter.TAGEND);
					result.append(ElementUtil.startElementVF(
							FoXsltConstants.VALUE_OF, "$p" + i));
					result.append(ElementUtil
							.endElement(FoXsltConstants.VALUE_OF));
					result.append(ElementWriter.TAGENDSTART);
					result.append(FoXsltConstants.OTHERWISE);
					result.append(ElementWriter.TAGEND);

				}

			}
			result.append(ElementUtil.endElement(FoXsltConstants.CHOOSE));
		}

		result.append(ElementWriter.TAGENDSTART);
		result.append(FoXsltConstants.VARIABLE);
		result.append(ElementWriter.TAGEND);
		return result.toString();
	}

}
