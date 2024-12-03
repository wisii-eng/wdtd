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
 * @DateTimeWriter.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.object;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.io.html.HtmlContext;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.DateInfo;
import com.wisii.wisedoc.document.attribute.DateTimeItem;
import com.wisii.wisedoc.document.attribute.TimeInfo;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.io.xsl.XslElementObj;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.util.WisedocUtil;
import date.extract.GetDay;
import date.extract.GetHour;
import date.extract.GetMinute;
import date.extract.GetMonth;
import date.extract.GetSecond;
import date.extract.GetYear;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-6-8
 */
public class DateTimeWriter extends DefaultObjectWriter
{

	/* (non-Javadoc)
	 * @see com.wisii.io.html.object.ObjectWriter#getString(com.wisii.wisedoc.document.CellElement, com.wisii.io.html.HtmlContext)
	 */
	@Override
	public String getString(CellElement element, HtmlContext context)
	{
		StringBuffer sb = new StringBuffer();
		if (element instanceof DateTimeInline)
		{
			DateTimeInline inlne = (DateTimeInline) element;
			InlineContent content = inlne.getContent();
			if (content != null && content.isBindContent())
			{
				Attributes att = inlne.getAttributes();
				sb.append(getDynicStringBegin(att, context));
				sb.append("<font");
				String classn = context.getStyleClass(inlne.getAttributes());
				if (classn != null)
				{
					sb.append(" class=\"" + classn + "\"");
				}
				sb.append(">");
				sb.append(getDynicStyleString(att,context));
				BindNode node = content.getBindNode();
				sb.append(ElementUtil.selectAssignment(
						FoXsltConstants.VARIABLE,
						FoXsltConstants.CONTENT + "0", new StringBuffer(context
								.getRelatePath(node))));
				sb.append(getFormateString(context, att.getAttributes()));
				sb.append("</font>");
				sb.append(getDynicStringEnd(att, context));
			}
		}
		return sb.toString();
	}
	public String getFormateString(HtmlContext context,Map<Integer, Object> map)
	{
		StringBuffer output = new StringBuffer();
		WisedocDateTimeFormat format = (WisedocDateTimeFormat) map
				.get(Constants.PR_DATETIMEFORMAT);
		Boolean date4 = false;
		String inputDate = "$"+FoXsltConstants.CONTENT + "0";
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
			inseparators = new String[]
			{ "", "", "" };
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
			{ "", "", "" };
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
			styles = new String[]
			{ "", "", "" };
			outseparators = new String[]
			{ "", "", "" };
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
					output.append(returnDateTemplate(context,indateformat, formatOne,
							inputDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree,date4));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorOne + "'"));
					output.append(returnDateTemplate(context,indateformat, formatTwo,
							inputDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree,date4));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorTwo + "'"));
					output.append(returnDateTemplate(context,indateformat, formatThree,
							inputDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree,date4));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorThree + "'"));
				} else
				{
					// System.out.println("this");
					output.append(returnTimeTemplate(context,intimeformat, formatFour,
							inputDate, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFour + "'"));
					output.append(returnTimeTemplate(context,intimeformat, formatFive,
							inputDate, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFive + "'"));
					output.append(returnTimeTemplate(context,intimeformat, formatSix,
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
					output.append(returnDateTemplate(context,indateformat, formatOne,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree,date4));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorOne + "'"));
					output.append(returnDateTemplate(context,indateformat, formatTwo,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree,date4));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorTwo + "'"));
					output.append(returnDateTemplate(context,indateformat, formatThree,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree,date4));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorThree + "'"));
					output
							.append(ElementUtil.ElementValueOf("'" + spout
									+ "'"));
					output.append(returnTimeTemplate(context,intimeformat, formatFour,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFour + "'"));
					output.append(returnTimeTemplate(context,intimeformat, formatFive,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFive + "'"));
					output.append(returnTimeTemplate(context,intimeformat, formatSix,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorSix + "'"));
				} else
				{
					output.append(returnTimeTemplate(context,intimeformat, formatFour,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFour + "'"));
					output.append(returnTimeTemplate(context,intimeformat, formatFive,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorFive + "'"));
					output.append(returnTimeTemplate(context,intimeformat, formatSix,
							dataTime, inSeparatorFour, inSeparatorFive,
							inSeparatorSix));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorSix + "'"));
					output
							.append(ElementUtil.ElementValueOf("'" + spout
									+ "'"));
					output.append(returnDateTemplate(context,indateformat, formatOne,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree,date4));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorOne + "'"));
					output.append(returnDateTemplate(context,indateformat, formatTwo,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree,date4));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorTwo + "'"));
					output.append(returnDateTemplate(context,indateformat, formatThree,
							dataDate, inSeparatorOne, inSeparatorTwo,
							inSeparatorThree,date4));
					output.append(ElementUtil.ElementValueOf("'"
							+ outSeparatorThree + "'"));
				}
			}
		}
		return output.toString();
	}
	public String returnDateTemplate(HtmlContext context,String formatdate, String format,
			String inputDate, String spreOne, String spreTwo, String spreThree,boolean date4)
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
			context.addFunctionTemplate(name);
			context.addFunctionTemplate(otherName);
			if (name.contains("15") || name.contains("16")
					|| name.contains("17") || name.contains("18")
					|| name.contains("19"))
			{
				NameSpace fovNameSpace = new NameSpace(
						FoXsltConstants.SPACENAMEFOV,
						FoXsltConstants.COMWISIIFOV);
				context.addNameSpace(fovNameSpace);
				context.addProfile("date");
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
				context.addOverall(xslelement);
			}

			if (!"".equalsIgnoreCase(otherName))
			{
				NameSpace fovNameSpace = new NameSpace(
						FoXsltConstants.SPACENAMEFOV,
						FoXsltConstants.COMWISIIFOV);
				context.addNameSpace(fovNameSpace);
				context.addProfile("number");
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
				context.addOverall(xslelement);
			}
		}
		return output.toString();
	}

	public String returnTimeTemplate(HtmlContext context,String formattime, String format,
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
			context.addFunctionTemplate(name);
			context.addFunctionTemplate(otherName);
			if (!"".equalsIgnoreCase(otherName))
			{
				NameSpace fovNameSpace = new NameSpace(
						FoXsltConstants.SPACENAMEFOV,
						FoXsltConstants.COMWISIIFOV);
				context.addNameSpace(fovNameSpace);
				context.addProfile("number");
				Map<Integer, Object> attributemap = new HashMap<Integer, Object>();
				attributemap.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAME,
						"num");
				attributemap.put(
						com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT,
						"document('')/xsl:stylesheet");
				XslElementObj xslelement = new XslElementObj(attributemap,
						FoXsltConstants.VARIABLE);
				context.addOverall(xslelement);
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
