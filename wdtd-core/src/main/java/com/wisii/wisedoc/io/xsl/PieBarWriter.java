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

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.NumberContent;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ChartData2D;
import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class PieBarWriter implements FoElementIFWriter
{

	SelectOutputAttributeWriter writerFactory = new SelectOutputAttributeWriter();

	CellElement foElement;

	Map<Integer, List<ConditionAndValue>> dongtaiMap = new HashMap<Integer, List<ConditionAndValue>>();

	Map<Integer, Object> attributemap = new HashMap<Integer, Object>();

	public PieBarWriter(CellElement element)
	{
		foElement = element;
		setAttributemap();
	}

	public String write(CellElement element)
	{
		StringBuffer code = new StringBuffer();
		code.append(beforeDeal());
		String strkey = "xmlns:piebar";
		String strvalue = "com.wisii.wisedoc.io.xsl.util.BarPieTemplate";
		NameSpace piebar = new NameSpace(strkey, strvalue);
		IoXslUtil.addNameSpace(piebar);
		String svgkey = FoXsltConstants.SPACENAMESVG;
		String svgvalue = FoXsltConstants.SVG_NAMESPACE;
		NameSpace nssvg = new NameSpace(svgkey, svgvalue);
		IoXslUtil.addNameSpace(nssvg);
		code.append(getTemplates());
		code.append(getTemplatesCalls());
		code.append(endDeal());
		return code.toString();
	}
	private String getTemplatesCalls()
	{
		String tempcode = "";
		tempcode = tempcode + "<";
		tempcode = tempcode + FoXsltConstants.VARIABLE;
		tempcode = tempcode + " " + FoXsltConstants.NAME + "=\"bc\"";
		tempcode = tempcode
				+ " "
				+ FoXsltConstants.SELECT
				+ "=\"piebar:getSvgCode($properties,$xzhou,$yzhou,$strvaluelabel,$strserteslabel,$sertesvalue)\"";
		tempcode = tempcode + "/>";
		tempcode = tempcode + "<";
		tempcode = tempcode + FoXsltConstants.COPY_OF;
		tempcode = tempcode + " " + FoXsltConstants.SELECT + "=\"$bc\"";
		tempcode = tempcode + "/>";
		return tempcode;
	}
	public void setAttributemap()
	{
		Map<Integer, Object> map = null;
		Attributes attributes = foElement.getAttributes();
		if (attributes != null)
		{
			map = attributes.getAttributes();
			// System.out.println("old size:" + map.size());
		}
		attributemap = ElementUtil.getCompleteAttributes(map, foElement
				.getClass());
	}

	public String beforeDeal()
	{
		StringBuffer output = new StringBuffer();
		LogicalExpression condition;
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_CONDTION))
		{
			condition = (LogicalExpression) attributemap
					.get(Constants.PR_CONDTION);
			output.append(ElementUtil.startElementIf(condition));
		}

		Group group = null;

		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_GROUP))
		{
			group = (Group) attributemap.get(Constants.PR_GROUP);
			output.append(ElementUtil.startGroup(group));
		}
		return output.toString();
	}

	public String endDeal()
	{
		StringBuffer output = new StringBuffer();
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_GROUP))
		{
			Group group = (Group) attributemap.get(Constants.PR_GROUP);
			output.append(ElementUtil.endGroup(group));
		}
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_CONDTION))
		{
			output.append(ElementUtil.endElement(FoXsltConstants.IF));
		}
		return output.toString();
	}

	@SuppressWarnings("unchecked")
	public String getTemplates()
	{
		Map<Integer, Object> map = attributemap;
		StringBuffer tempcode = new StringBuffer();
		String sertesvalue = "''";
		ChartDataList lists = (ChartDataList) map
				.get(Constants.PR_SERIES_VALUE);
		if (lists != null && !lists.isEmpty())
		{
			int isize = lists.size();
			for (int i = 0; i < isize; i++)
			{
				ChartData2D item = lists.get(i);
				sertesvalue = "concat(" + sertesvalue + ",'"
						+ item.getIndex1d() + "@','";
				sertesvalue = sertesvalue + item.getIndex2d() + "@',";
				// sertesvalue = sertesvalue + item.getIndex1d() + ":',";
				// sertesvalue = sertesvalue + item.getIndex2d() + ":";
				NumberContent current = item.getNumbercontent();
				if (current.isBindContent())
				{
					tempcode.append(ElementWriter.TAGBEGIN);
					tempcode.append(FoXsltConstants.VARIABLE);
					tempcode.append(ElementUtil.outputAttributes(
							FoXsltConstants.NAME, "d" + i));
					tempcode.append(ElementUtil.outputAttributes(
							FoXsltConstants.SELECT,
							IoXslUtil.compareCurrentPath(IoXslUtil
									.getXSLXpath(current.getBindNode()))));
					tempcode.append(ElementWriter.NOCHILDTAGEND);
					if (i < isize - 1)
					{
						sertesvalue = sertesvalue + "$d" + i + ",';')";
					} else
					{
						sertesvalue = sertesvalue + "$d" + i + ")";
					}
				} else
				{
					if (i < isize - 1)
					{
						sertesvalue = sertesvalue + "'" + current.getText()
								+ ";')";
					} else
					{
						sertesvalue = sertesvalue + "'" + current.getText()
								+ "')";
					}
				}
			}
		}
		List<Color> valuecolor = (List<Color>) map
				.get(Constants.PR_VALUE_COLOR);
		String strvaluecolor = "";
		if (valuecolor != null && !valuecolor.isEmpty())
		{
			int size = valuecolor.size();
			for (int i = 0; i < size; i++)
			{
				Color current = valuecolor.get(i);
				if (current != null)
				{
					strvaluecolor = strvaluecolor + current.getRGB();
				} else
				{
					strvaluecolor = strvaluecolor + "";
				}
				if (i < size - 1)
				{
					strvaluecolor = strvaluecolor + ",";
				}
			}
		}
		List<BarCodeText> valuelabel = (List<BarCodeText>) map
				.get(Constants.PR_VALUE_LABEL);
		String strvaluelabel = "''";

		if (valuelabel != null && !valuelabel.isEmpty())
		{
			int size = valuelabel.size();
			for (int i = 0; i < size; i++)
			{
				BarCodeText current = valuelabel.get(i);
				if (current.isBindContent())
				{
					tempcode.append(ElementWriter.TAGBEGIN);
					tempcode.append(FoXsltConstants.VARIABLE);
					tempcode.append(ElementUtil.outputAttributes(
							FoXsltConstants.NAME, "sv" + i));
					tempcode.append(ElementUtil.outputAttributes(
							FoXsltConstants.SELECT,
							IoXslUtil.compareCurrentPath(IoXslUtil
									.getXSLXpath(current.getBindNode()))));
					tempcode.append(ElementWriter.NOCHILDTAGEND);
					strvaluelabel = "concat(" + strvaluelabel + ",$sv" + i;
					if (i < size - 1)
					{
						strvaluelabel = strvaluelabel + ",'@@')";
					} else
					{
						strvaluelabel = strvaluelabel + ")";
					}
				} else
				{
					strvaluelabel = "concat(" + strvaluelabel + ",'"
							+ current.getText();
					if (i < size - 1)
					{
						strvaluelabel = strvaluelabel + "@@')";
					} else
					{
						strvaluelabel = strvaluelabel + "')";
					}
				}
			}
		}

		List<BarCodeText> serteslabel = (List<BarCodeText>) map
				.get(Constants.PR_SERIES_LABEL);
		String strserteslabel = "''";

		if (serteslabel != null && !serteslabel.isEmpty())
		{
			int size = serteslabel.size();
			for (int i = 0; i < size; i++)
			{
				BarCodeText current = serteslabel.get(i);
				if (current.isBindContent())
				{
					tempcode.append(ElementWriter.TAGBEGIN);
					tempcode.append(FoXsltConstants.VARIABLE);
					tempcode.append(ElementUtil.outputAttributes(
							FoXsltConstants.NAME, "sl" + i));
					tempcode.append(ElementUtil.outputAttributes(
							FoXsltConstants.SELECT,
							IoXslUtil.compareCurrentPath(IoXslUtil
									.getXSLXpath(current.getBindNode()))));
					tempcode.append(ElementWriter.NOCHILDTAGEND);

					strserteslabel = "concat(" + strserteslabel + ",$sl" + i;

					if (i < size - 1)
					{
						strserteslabel = strserteslabel + ",'@@')";
					} else
					{
						strserteslabel = strserteslabel + ")";
					}

				} else
				{
					strserteslabel = "concat(" + strserteslabel + ",'"
							+ current.getText();
					if (i < size - 1)
					{
						strserteslabel = strserteslabel + "@@')";
					} else
					{
						strserteslabel = strserteslabel + "')";
					}
				}
			}
		}
		String xzhou = "";
		String yzhou = "";
		if (map.containsKey(Constants.PR_DOMAINAXIS_LABEL))
		{
			BarCodeText text = (BarCodeText) map
					.get(Constants.PR_DOMAINAXIS_LABEL);
			if (text.isBindContent())
			{
				tempcode.append(ElementWriter.TAGBEGIN);
				tempcode.append(FoXsltConstants.VARIABLE);
				tempcode.append(ElementUtil.outputAttributes(
						FoXsltConstants.NAME, "x"));
				tempcode.append(ElementUtil.outputAttributes(
						FoXsltConstants.SELECT, IoXslUtil
								.compareCurrentPath(IoXslUtil.getXSLXpath(text
										.getBindNode()))));
				tempcode.append(ElementWriter.NOCHILDTAGEND);
				xzhou = "$x";
			} else
			{
				xzhou = "'" + text.getText() + "'";
			}
		}

		if (map.containsKey(Constants.PR_RANGEAXIS_LABEL))
		{
			BarCodeText text = (BarCodeText) map
					.get(Constants.PR_RANGEAXIS_LABEL);
			if (text.isBindContent())
			{
				tempcode.append(ElementWriter.TAGBEGIN);
				tempcode.append(FoXsltConstants.VARIABLE);
				tempcode.append(ElementUtil.outputAttributes(
						FoXsltConstants.NAME, "y"));
				tempcode.append(ElementUtil.outputAttributes(
						FoXsltConstants.SELECT, IoXslUtil
								.compareCurrentPath(IoXslUtil.getXSLXpath(text
										.getBindNode()))));
				tempcode.append(ElementWriter.NOCHILDTAGEND);
				yzhou = "$y";
			} else
			{
				yzhou = "'" + text.getText() + "'";
			}
		}
		tempcode.append(ElementWriter.TAGBEGIN);
		tempcode.append(FoXsltConstants.VARIABLE);
		tempcode.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
				"properties"));

		Object[] keys = map.keySet().toArray();

		tempcode.append(" select=\"");

		String jingtai = "'";
		for (int i = 0; i < keys.length; i++)
		{
			int key = (Integer) keys[i];
			if (!(key == Constants.PR_VALUE_COLOR
					|| key == Constants.PR_VALUE_LABEL
					|| key == Constants.PR_SERIES_LABEL
					|| key == Constants.PR_SERIES_VALUE
					|| key == Constants.PR_DOMAINAXIS_LABEL
					|| key == Constants.PR_RANGEAXIS_LABEL
					|| key == Constants.PR_BORDER_WIDTH
					|| key == Constants.PR_BORDER_BEFORE_WIDTH
					|| key == Constants.PR_BORDER_AFTER_WIDTH
					|| key == Constants.PR_BORDER_START_WIDTH
					|| key == Constants.PR_BORDER_END_WIDTH
					|| key == Constants.PR_BORDER_COLOR
					|| key == Constants.PR_BORDER_BEFORE_COLOR
					|| key == Constants.PR_BORDER_AFTER_COLOR
					|| key == Constants.PR_BORDER_START_COLOR
					|| key == Constants.PR_BORDER_END_COLOR
					|| key == Constants.PR_BORDER_STYLE
					|| key == Constants.PR_BORDER_BEFORE_STYLE
					|| key == Constants.PR_BORDER_AFTER_STYLE
					|| key == Constants.PR_BORDER_START_STYLE
					|| key == Constants.PR_BORDER_END_STYLE || key == Constants.PR_BACKGROUND_IMAGE))
			{
				Object value = map.get(key);
				jingtai = jingtai + getAttributeStr(key, value);

			}
		}

		jingtai = jingtai
				+ getAttributeStr(Constants.PR_VALUE_COLOR, strvaluecolor);
		jingtai = jingtai + "'";
		tempcode.append(jingtai);
		tempcode.append("\"");
		tempcode.append(ElementWriter.NOCHILDTAGEND);

		tempcode.append(ElementWriter.TAGBEGIN);
		tempcode.append(FoXsltConstants.VARIABLE);
		tempcode.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
				"xzhou"));
		tempcode.append(" select=\"");
		tempcode.append(xzhou);
		tempcode.append("\"");
		tempcode.append(ElementWriter.NOCHILDTAGEND);

		tempcode.append(ElementWriter.TAGBEGIN);
		tempcode.append(FoXsltConstants.VARIABLE);
		tempcode.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
				"yzhou"));
		tempcode.append(" select=\"");
		tempcode.append(yzhou);
		tempcode.append("\"");
		tempcode.append(ElementWriter.NOCHILDTAGEND);

		tempcode.append(ElementWriter.TAGBEGIN);
		tempcode.append(FoXsltConstants.VARIABLE);
		tempcode.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
				"strvaluelabel"));
		tempcode.append(" select=\"");
		tempcode.append(strvaluelabel);
		tempcode.append("\"");
		tempcode.append(ElementWriter.NOCHILDTAGEND);

		tempcode.append(ElementWriter.TAGBEGIN);
		tempcode.append(FoXsltConstants.VARIABLE);
		tempcode.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
				"strserteslabel"));
		tempcode.append(" select=\"");
		tempcode.append(strserteslabel);
		tempcode.append("\"");
		tempcode.append(ElementWriter.NOCHILDTAGEND);

		tempcode.append(ElementWriter.TAGBEGIN);
		tempcode.append(FoXsltConstants.VARIABLE);
		tempcode.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
				"sertesvalue"));
		tempcode.append(" select=\"");
		tempcode.append(sertesvalue);
		tempcode.append("\"");
		tempcode.append(ElementWriter.NOCHILDTAGEND);
		return tempcode.toString();
	}

	public String getAttributeStr(int name, Object value)
	{
		String vastr = value != null ? getValue(value) : "";
		if (vastr.equals(""))
		{
			return "";
		} else
		{
			return " a" + name + "=" + vastr;
		}
	}

	public String getValue(Object value)
	{
		String code = "";
		if (value instanceof String)
		{
			code = (String) value;
		} else if (value instanceof Color)
		{
			code = "" + ((Color) value).getRGB();
		} else if (value instanceof FixedLength)
		{
			FixedLength length = (FixedLength) value;
			NumberFormat nf = new DecimalFormat();
			nf.setMaximumFractionDigits(3);
			code = nf.format(length.getValue() / 1000.000d);
		} else if ((value instanceof Integer) || (value instanceof Double)
				|| (value instanceof Float) || (value instanceof Number))
		{
			code = "" + value;
		} else if (value instanceof EnumProperty)
		{
			code = "" + value;
		} else if (value instanceof BarCodeText)
		{
			code = ((BarCodeText) value).getText();
		}
		return code;
	}

	@Override
	public String getAttribute(int key, Object value)
	{
		return null;
	}

	@Override
	public String getAttributes()
	{
		return null;
	}

	@Override
	public String getCode()
	{
		return null;
	}

	@Override
	public String getElementName()
	{
		return null;
	}

	@Override
	public String getEndElement()
	{
		return null;
	}

	@Override
	public String getHeaderElement()
	{
		return null;
	}

	@Override
	public String getHeaderEnd()
	{
		return null;
	}

	@Override
	public String getHeaderStart()
	{
		return null;
	}
}
