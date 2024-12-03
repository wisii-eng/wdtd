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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.DateInfo;
import com.wisii.wisedoc.document.attribute.DateTimeItem;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.TimeInfo;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.util.WisedocUtil;
import date.extract.GetDay;
import date.extract.GetHour;
import date.extract.GetMinute;
import date.extract.GetMonth;
import date.extract.GetSecond;
import date.extract.GetYear;

public class DateTimeInlineWriter extends InlineWriter
{

	Boolean date4 = false;

	public DateTimeInlineWriter(CellElement element, int n)
	{
		super(element, n);
	}

	public String write(CellElement element)
	{
		StringBuffer output = new StringBuffer();
		output.append(beforeDeal());
		if (IoXslUtil.isStandard())
		{
			output.append("<");
			output.append(FoXsltConstants.INLINE);
			output.append("/>");
		} else
		{
			Inline inlne = (Inline) foElement;
			InlineContent textin = inlne.getContent();
			if (textin == null)
			{
				output.append("<");
				output.append(FoXsltConstants.INLINE);
				output.append("/>");
			} else
			{
				if (textin instanceof Text)
				{
					Text textinLine = (Text) textin;
					boolean flg = textinLine.isBindContent();
					if (flg)
					{
						output.append(getCode());
					} else
					{
						output.append(textNoBinding());
					}
				}
			}
			output.append(endDeal());
		}
		return output.toString();
	}

	/**
	 * 
	 * 获得inline内容的非转换的代码，根据inline的类型确定该处的输出（普通文本，格式化数字，日期时间格式等）
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	@SuppressWarnings("unchecked")
	public String getFormatContent()
	{
		StringBuffer code = new StringBuffer();

		DateTimeInline datetimeinle = (DateTimeInline) inline;
		Attributes att = datetimeinle.getAttributes();
		if (att != null)
		{
			Map<Integer, Object> am = att.getAttributes();
			if (am != null && am.containsKey(Constants.PR_DYNAMIC_STYLE))
			{
				List<ConditionItem> conditions = (List<ConditionItem>) att
						.getAttributes().get(Constants.PR_DYNAMIC_STYLE);
				if (conditions != null && conditions.size() > 0)
				{
					for (int i = 0; i < conditions.size(); i++)
					{
						if (i == 0)
						{
							code.append(ElementUtil
									.startElement(FoXsltConstants.CHOOSE));
						}
						ConditionItem currentitem = conditions.get(i);
						LogicalExpression condition = currentitem
								.getCondition();
						Map<Integer, Object> styles = currentitem.getStyles();

						if (condition != null)
						{
							code.append(ElementWriter.TAGBEGIN
									+ FoXsltConstants.WHEN + " ");
							code.append(ElementUtil.outputAttributes(
									FoXsltConstants.TEST, ElementUtil
											.returnStringIf(condition, true)));
							code.append(ElementWriter.TAGEND);
							WisedocDateTimeFormat formatcurrent = (WisedocDateTimeFormat) styles
									.get(Constants.PR_DATETIMEFORMAT);
							code.append(getOneFormat(formatcurrent));
							code.append(ElementUtil
									.endElement(FoXsltConstants.WHEN));
						}
					}
					code.append(ElementUtil
							.startElement(FoXsltConstants.OTHERWISE));
					WisedocDateTimeFormat format = (WisedocDateTimeFormat) att
							.getAttribute(Constants.PR_DATETIMEFORMAT);
					code.append(getOneFormat(format));
					code.append(ElementUtil
							.endElement(FoXsltConstants.OTHERWISE));
					code.append(ElementUtil.endElement(FoXsltConstants.CHOOSE));
				}
			} else
			{
				WisedocDateTimeFormat format = (WisedocDateTimeFormat) att
						.getAttribute(Constants.PR_DATETIMEFORMAT);
				code.append(getOneFormat(format));

			}
		}
		return code.toString();
	}

	public String getOneFormat(WisedocDateTimeFormat format)
	{		StringBuffer output = new StringBuffer();
		DateTimeInline datetimeinle = (DateTimeInline) inline;
		Text text = (Text) datetimeinle.getContent();
		String inputDate = "";
		if (text.isBindContent())
		{
			inputDate = "$" + FoXsltConstants.CONTENT + number;
		} else
		{
			inputDate = text.getText();
		}
		if (format != null)
		{

			DateInfo datein = format.getDatein();
			TimeInfo timein = format.getTimein();

			DateInfo dateout = format.getDateout();
			TimeInfo timeout = format.getTimeout();

			boolean firstin = format.isIndatefirst();
			boolean firstout = format.isOutdatefirst();
			String spin = format.getInputseperate() != null ? format
					.getInputseperate() : "";
			String spout = format.getOutseperate() != null ? format
					.getOutseperate() : "";

			String inSeparatorOne = "";
			String inSeparatorTwo = "";
			String inSeparatorThree = "";
			String inSeparatorFour = "";
			String inSeparatorFive = "";
			String inSeparatorSix = "";
			String formatOne = "";
			String formatTwo = "";
			String formatThree = "";
			String formatFour = "";
			String formatFive = "";
			String formatSix = "";
			String outSeparatorOne = "";
			String outSeparatorTwo = "";
			String outSeparatorThree = "";
			String outSeparatorFour = "";
			String outSeparatorFive = "";
			String outSeparatorSix = "";

			String indateformat = "";
			String[] inseparators =
			{ "", "", "", };
			int index = -1;

			if (datein != null)
			{
				List<DateTimeItem> dtidatein = datein.getDateitems();
				// System.out.println("dtidatein:" + (dtidatein == null));
				for (DateTimeItem item : dtidatein)
				{
					if (item == null)
					{
						continue;
					}
					index++;
					String separator = item.getSeparator();
					if (!WisedocUtil.isEmpty(separator))
					{
						inseparators[index] = separator;
					}
					indateformat = indateformat
							+ item.repairePatternWithoutSeparator();
					if (indateformat.contains("YYYY"))
					{
						date4 = true;
					}
				}
				inSeparatorOne = inseparators[0];
				inSeparatorTwo = inseparators[1];
				inSeparatorThree = inseparators[2];
			}

			String intimeformat = "";
			inseparators =
				new String[]{ "", "", "" };
			if (timein != null)
			{
				List<DateTimeItem> dtitimein = timein.getDateitems();
				index = -1;
				for (DateTimeItem item : dtitimein)
				{
					if (item == null)
						continue;
					index++;
					String separator = item.getSeparator();
					if (!WisedocUtil.isEmpty(separator))
					{
						inseparators[index] = separator;
					}
					intimeformat = intimeformat
							+ item.repairePatternWithoutSeparator();
				}
				inSeparatorFour = inseparators[0];
				inSeparatorFive = inseparators[1];
				inSeparatorSix = inseparators[2];
			}
			String[] styles =
			{ "", "", ""};
			String[] outseparators =
			{ "", "", "" };
			if (dateout != null)
			{
				List<DateTimeItem> dtidateout = dateout.getDateitems();
				index = -1;
				for (DateTimeItem item : dtidateout)
				{
					// System.out.println("item!=null:" + (item == null));
					if (item == null)
						continue;
					index++;
					String separator = item.getSeparator();
					if (!WisedocUtil.isEmpty(separator))
					{
						outseparators[index] = separator;
					}
					styles[index] = item.getItemInfo();
				}
				outSeparatorOne = outseparators[0];
				outSeparatorTwo = outseparators[1];
				outSeparatorThree = outseparators[2];
				formatOne = styles[0];
				formatTwo = styles[1];
				formatThree = styles[2];
			}
			styles =
				new String[]{ "", "", "" };
			outseparators =
			new String[]{ "", "", "" };
			if (timeout != null)
			{
				List<DateTimeItem> dtitimeout = timeout.getDateitems();
				index = -1;
				for (DateTimeItem item : dtitimeout)
				{
					if (item == null)
						continue;
					index++;
					String separator = item.getSeparator();
					if (!WisedocUtil.isEmpty(separator))
					{
						outseparators[index] = separator;
					} 
					styles[index] = item.getItemInfo();
				}
				outSeparatorFour = outseparators[0];
				outSeparatorFive = outseparators[1];
				outSeparatorSix = outseparators[2];
				formatFour = styles[0];
				formatFive = styles[1];
				formatSix = styles[2];
			}

			if ("".equalsIgnoreCase(spin))
			{
				if (datein != null)
				{
					output.append(returnDateTemplate(indateformat, formatOne,
							inputDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorOne + "'"));
					output.append(returnDateTemplate(indateformat, formatTwo,
							inputDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorTwo + "'"));
					output.append(returnDateTemplate(indateformat, formatThree,
							inputDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorThree + "'"));
				} else
				{
					// System.out.println("this");
					output.append(returnTimeTemplate(intimeformat, formatFour,
							inputDate, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFour + "'"));
					output.append(returnTimeTemplate(intimeformat, formatFive,
							inputDate, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFive + "'"));
					output.append(returnTimeTemplate(intimeformat, formatSix,
							inputDate, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorSix + "'"));
				}
			} else
			{
				String dataDate = "";
				String dataTime = "";
				if (firstin)
				{
					dataDate = "substring-before(" + inputDate + "," + "'"
							+ spin + "'" + ")";
					dataTime = "substring-after(" + inputDate + "," + "'"
							+ spin + "'" + ")";
				} else
				{
					dataTime = "substring-before(" + inputDate + "," + "'"
							+ spin + "'" + ")";
					dataDate = "substring-after(" + inputDate + "," + "'"
							+ spin + "'" + ")";
				}
				if (firstout)
				{
					output.append(returnDateTemplate(indateformat, formatOne,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorOne + "'"));
					output.append(returnDateTemplate(indateformat, formatTwo,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorTwo + "'"));
					output.append(returnDateTemplate(indateformat, formatThree,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorThree + "'"));
					output
							.append(ElementUtil.ElementValueOf("'" + spout
									+ "'"));
					output.append(returnTimeTemplate(intimeformat, formatFour,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFour + "'"));
					output.append(returnTimeTemplate(intimeformat, formatFive,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFive + "'"));
					output.append(returnTimeTemplate(intimeformat, formatSix,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorSix + "'"));
				} else
				{
					output.append(returnTimeTemplate(intimeformat, formatFour,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFour + "'"));
					output.append(returnTimeTemplate(intimeformat, formatFive,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFive + "'"));
					output.append(returnTimeTemplate(intimeformat, formatSix,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorSix + "'"));
					output
							.append(ElementUtil.ElementValueOf("'" + spout
									+ "'"));
					output.append(returnDateTemplate(indateformat, formatOne,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorOne + "'"));
					output.append(returnDateTemplate(indateformat, formatTwo,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorTwo + "'"));
					output.append(returnDateTemplate(indateformat, formatThree,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorThree + "'"));
				}
			}
		}
		return output.toString();
	}

	/**
	 * 
	 * 数据为非绑定节点时的代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String textNoBinding()
	{
		StringBuffer output = new StringBuffer();
		output.append(getHeaderElement());
		output.append(getFormatContent());
		output.append(ElementUtil.endElement(FoXsltConstants.INLINE));
		return output.toString();
	}

	public String returnDateTemplate(String formatdate, String format,
			String inputDate, String spreOne, String spreTwo, String spreThree)
	{
		// System.out.println("formatdate" + formatdate);
		StringBuffer output = new StringBuffer();
		if (!"".equalsIgnoreCase(format))
		{
			String[] fOne = format.split(",");
			Map<String, Object> parammap = new HashMap<String, Object>();
			String name = "";
			String otherName = "";
			if ("1".equalsIgnoreCase(fOne[0]))
			{
				name = "sY";
				if ("0".equalsIgnoreCase(fOne[1]))
				{
					name = name + fOne[1] + fOne[2];
				} else if ("7".equalsIgnoreCase(fOne[1]))
				{
					name = name + 1 + fOne[2];

				} else
				{
					parammap.put("format", fOne[1]);
					name = name + 2 + fOne[2];
				}
				parammap.put("data", new GetYear().returnYear(formatdate,
						inputDate, spreOne, spreTwo, spreThree));
				if (!date4)
				{
					name = name + 2;
				}
				if (fOne[2].equalsIgnoreCase("2"))
				{
					otherName = "ts02";
				} else if (fOne[2].equalsIgnoreCase("3"))
				{
					otherName = "ts03";
				} else if (fOne[2].equalsIgnoreCase("4"))
				{
					otherName = "ts04";
				}
			} else if ("2".equalsIgnoreCase(fOne[0]))
			{
				if ("1".equalsIgnoreCase(fOne[1]))
				{
					name = "sM" + fOne[1] + fOne[2];

				} else if ("2".equalsIgnoreCase(fOne[1]))
				{
					if ("1".equalsIgnoreCase(fOne[2])
							|| "2".equalsIgnoreCase(fOne[2])
							|| "3".equalsIgnoreCase(fOne[2])
							|| "4".equalsIgnoreCase(fOne[2]))
					{
						name = "sD" + fOne[1] + fOne[2];
					} else if ("5".equalsIgnoreCase(fOne[2])
							|| "6".equalsIgnoreCase(fOne[2])
							|| "7".equalsIgnoreCase(fOne[2])
							|| "8".equalsIgnoreCase(fOne[2])
							|| "9".equalsIgnoreCase(fOne[2]))
					{
						name = "sM" + 1 + fOne[2];
					}
				}
				parammap.put("data", new GetMonth().returnMonth(formatdate,
						inputDate, spreOne, spreTwo, spreThree));
				if (fOne[2].equalsIgnoreCase("2"))
				{
					otherName = "tsu02";
				} else if (fOne[2].equalsIgnoreCase("3"))
				{
					otherName = "tsu03";
				} else if (fOne[2].equalsIgnoreCase("4"))
				{
					otherName = "tsu04";
				}
				// if (fOne[1].equalsIgnoreCase("1")
				// && !"".equalsIgnoreCase(otherName))
				// {
				// otherName = otherName + 0;
				// }
			} else if ("3".equalsIgnoreCase(fOne[0]))
			{
				name = "sD" + fOne[1] + fOne[2];
				parammap.put("data", new GetDay().returnDay(formatdate,
						inputDate, spreOne, spreTwo, spreThree));
				if (fOne[2].equalsIgnoreCase("2"))
				{
					otherName = "tsu02";
				} else if (fOne[2].equalsIgnoreCase("3"))
				{
					otherName = "tsu03";
				} else if (fOne[2].equalsIgnoreCase("4"))
				{
					otherName = "tsu04";
				}
				// if (fOne[1].equalsIgnoreCase("1")
				// && !"".equalsIgnoreCase(otherName))
				// {
				// otherName = otherName + 0;
				// }
			}
			output.append(ElementUtil
					.outputCalltemplateDateTime(name, parammap));
			IoXslUtil.addFunctionTemplate(name);
			IoXslUtil.addFunctionTemplate(otherName);
			if (name.contains("15") || name.contains("16")
					|| name.contains("17") || name.contains("18")
					|| name.contains("19"))
			{
				NameSpace fovNameSpace = new NameSpace(
						FoXsltConstants.SPACENAMEFOV,
						FoXsltConstants.COMWISIIFOV);
				IoXslUtil.addNameSpace(fovNameSpace);
				IoXslUtil.addProfile("date");
				// System.out.println("add date");
				Map<Integer, Object> attributemap = new HashMap<Integer, Object>();
				attributemap.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAME,
						"num");
				attributemap.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT,
						"document('')/xsl:stylesheet");
				XslElementObj xslelement = new XslElementObj(attributemap,
						FoXsltConstants.VARIABLE);
				IoXslUtil.addOverall(xslelement);
			}

			if (!"".equalsIgnoreCase(otherName))
			{
				NameSpace fovNameSpace = new NameSpace(
						FoXsltConstants.SPACENAMEFOV,
						FoXsltConstants.COMWISIIFOV);
				IoXslUtil.addNameSpace(fovNameSpace);
				IoXslUtil.addProfile("number");
				// System.out.println("add number");
				Map<Integer, Object> attributemap = new HashMap<Integer, Object>();
				attributemap.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAME,
						"num");
				attributemap.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT,
						"document('')/xsl:stylesheet");
				XslElementObj xslelement = new XslElementObj(attributemap,
						FoXsltConstants.VARIABLE);
				IoXslUtil.addOverall(xslelement);
			}
		}
		return output.toString();
	}

	public String returnTimeTemplate(String formattime, String format,
			String inputDate, String spreOne, String spreTwo, String spreThree)
	{
		// System.out.println("formattime" + formattime);
		// System.out.println("format" + format);
		// System.out.println("inputDate" + inputDate);
		// System.out.println("spreOne" + spreOne);
		// System.out.println("spreTwo" + spreTwo);
		// System.out.println("spreThree" + spreThree);
		StringBuffer output = new StringBuffer();
		if (!"".equalsIgnoreCase(format))
		{
			String[] fOne = format.split(",");
			HashMap<String, Object> parammap = new HashMap<String, Object>();
			String name = "";
			if ("4".equalsIgnoreCase(fOne[0]))
			{
				name = "sH" + fOne[1] + fOne[2];
				parammap.put("data", new GetHour().returnHour(formattime,
						inputDate, spreOne, spreTwo, spreThree));
				// System.out.println("hhhhhhhhhh:::"
				// + new GetHour().returnHour(formattime, inputDate,
				// spreOne, spreTwo, spreThree));
			} else if ("5".equalsIgnoreCase(fOne[0]))
			{
				name = "sD" + fOne[1] + fOne[2];
				parammap.put("data", new GetMinute().returnMinute(formattime,
						inputDate, spreOne, spreTwo, spreThree));
				// System.out.println("mmmmmmmmmm::"
				// + new GetMinute().returnMinute(formattime, inputDate,
				// spreOne, spreTwo, spreThree));
			} else if ("6".equalsIgnoreCase(fOne[0]))
			{
				name = "sD" + fOne[1] + fOne[2];
				parammap.put("data", new GetSecond().returnSecond(formattime,
						inputDate, spreOne, spreTwo, spreThree));
				// System.out.println("dddddddddd::"
				// + new GetSecond().returnSecond(formattime, inputDate,
				// spreOne, spreTwo, spreThree));
			}
			String otherName = "";
			if (fOne[2].equalsIgnoreCase("2"))
			{
				otherName = "tsu02";
			} else if (fOne[2].equalsIgnoreCase("3"))
			{
				otherName = "tsu03";
			} else if (fOne[2].equalsIgnoreCase("4"))
			{
				otherName = "tsu04";
			}
			// if (fOne[1].equalsIgnoreCase("1")
			// && !"".equalsIgnoreCase(otherName))
			// {
			// otherName = otherName + 0;
			// }
			output.append(ElementUtil
					.outputCalltemplateDateTime(name, parammap));
			IoXslUtil.addFunctionTemplate(name);
			IoXslUtil.addFunctionTemplate(otherName);
			if (!"".equalsIgnoreCase(otherName))
			{
				NameSpace fovNameSpace = new NameSpace(
						FoXsltConstants.SPACENAMEFOV,
						FoXsltConstants.COMWISIIFOV);
				IoXslUtil.addNameSpace(fovNameSpace);
				IoXslUtil.addProfile("number");
				Map<Integer, Object> attributemap = new HashMap<Integer, Object>();
				attributemap.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAME,
						"num");
				attributemap.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT,
						"document('')/xsl:stylesheet");
				XslElementObj xslelement = new XslElementObj(attributemap,
						FoXsltConstants.VARIABLE);
				IoXslUtil.addOverall(xslelement);
			}

		}
		return output.toString();
	}

	public int getFlg(String style)
	{
		int flg = 0;
		if (style.contains("Y") || style.contains("M") || style.contains("D"))
		{
			flg = 1;
		} else if (style.contains("h") || style.contains("m")
				|| style.contains("s"))
		{
			flg = 2;
		}
		return flg;
	}
}
