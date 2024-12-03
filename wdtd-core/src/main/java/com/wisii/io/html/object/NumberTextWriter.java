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
 * @NumberTextWriter.java
 *                        北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.object;

import java.util.HashMap;
import java.util.Map;

import com.wisii.io.html.HtmlContext;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.NumberFormat;
import com.wisii.wisedoc.io.xsl.XslElementObj;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang
 * 创建日期：2012-6-5
 */
public class NumberTextWriter extends DefaultObjectWriter
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 
	 * 
	 * 
	 * com.wisii.io.html.object.ObjectWriter#getString(com.wisii.wisedoc.document
	 * .CellElement, com.wisii.io.html.HtmlContext)
	 */
	@Override
	public String getString(CellElement element, HtmlContext context)
	{
		StringBuffer sb = new StringBuffer();
		if (element instanceof NumberTextInline)
		{
			NumberTextInline inlne = (NumberTextInline) element;
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

	private String getFormateString(HtmlContext context,Map<Integer, Object> map)
	{

		StringBuffer output = new StringBuffer();
		EnumProperty typeEnum = null;
		typeEnum = (EnumProperty) map
				.get(com.wisii.wisedoc.document.Constants.PR_NUMBERTEXT_TYPE);
		int type = com.wisii.wisedoc.document.Constants.EN_NORMAL;
		if (typeEnum != null)
		{
			type = typeEnum.getEnum();
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();

		if (type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL)
		{
			paramMap.put("flg", "2");
		} else if (type == com.wisii.wisedoc.document.Constants.EN_CHINESELOWERCASE)
		{
			paramMap.put("flg", "1");
		} else if (type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL_ZC)
		{
			paramMap.put("flg", "3");
		} else if (type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL_ADDZHENG)
		{
			paramMap.put("flg", "2");
			paramMap.put("add", "true");
		} else if (type == com.wisii.wisedoc.document.Constants.EN_CHINESELOWERCASE_ADDZHENG)
		{
			paramMap.put("flg", "1");
			paramMap.put("add", "true");
		} else if (type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL_ZC_ADDZHENG)
		{
			paramMap.put("flg", "3");
			paramMap.put("add", "true");
		} else if (type == com.wisii.wisedoc.document.Constants.EN_NORMAL)
		{
			StringBuffer select = new StringBuffer();
			select.append(FoXsltConstants.FORMAT_NUMBER);
			select.append("(");
			String varstr = "";
			varstr = "$content0";
			select.append(varstr);
			select.append(",");
			NumberFormat numberFormat = null;
			String dotString = "";
			if (!map
					.containsKey(com.wisii.wisedoc.document.Constants.PR_NUMBERFORMAT))
			{
				select.append("'#,###.00'");
				dotString = ".00";
			} else
			{
				numberFormat = (NumberFormat) map
						.get(com.wisii.wisedoc.document.Constants.PR_NUMBERFORMAT);
				if (numberFormat == null)
				{
					select.append("'#,###.00'");
					dotString = ".00";
				} else
				{
					select.append("'" + numberFormat.toString() + "'");
					int length = numberFormat.getDecimalDigits();
					if (length > 0)
					{
						dotString = "." + getString(length);
					}
				}
			}
			select.append(")");
			output
					.append(getchooseString(varstr, select.toString(),
							dotString));

		}
		if (paramMap.containsKey("flg"))
		{

			StringBuffer numberValue = new StringBuffer();
			numberValue.append("$content0");
			paramMap.put("data", numberValue);

			if (type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL
					|| type == com.wisii.wisedoc.document.Constants.EN_CHINESELOWERCASE
					|| type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL_ADDZHENG
					|| type == com.wisii.wisedoc.document.Constants.EN_CHINESELOWERCASE_ADDZHENG)
			{
				context.addFunctionTemplate("je");
				context.addFunctionTemplate("TSxs");
				output.append(ElementUtil.outputCalltemplate("je", paramMap));
			} else if (type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL_ZC
					|| type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL_ZC_ADDZHENG)
			{
				context.addFunctionTemplate("jezc");
				context.addFunctionTemplate("TSxs");
				output.append(ElementUtil.outputCalltemplate("jezc", paramMap));
			}

			context.addProfile("number");
			Map<Integer, Object> attributemap = new HashMap<Integer, Object>();
			attributemap.put(
					com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAME, "num");
			attributemap.put(
					com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT,
					"document('')/xsl:stylesheet");
			XslElementObj xslelement = new XslElementObj(attributemap,
					FoXsltConstants.VARIABLE);
			context.addOverall(xslelement);
			NameSpace fovNameSpace = new NameSpace(
					FoXsltConstants.SPACENAMEFOV, FoXsltConstants.COMWISIIFOV);
			context.addNameSpace(fovNameSpace);
		}
		return output.toString();
	}

	private String getchooseString(String varstr, String selectsrt,
			String dotString)
	{
		StringBuffer result = new StringBuffer();
		result.append("	<xsl:choose><xsl:when test=\"number(");
		result.append(varstr);
		result.append(")=number(0)\">0");
		result.append(dotString);
		result.append("</xsl:when><xsl:otherwise>");
		result.append(ElementUtil.ElementValueOf(selectsrt));
		result.append("</xsl:otherwise></xsl:choose>");
		return result.toString();
	}

	public String getString(int length)
	{
		String text = "";
		if (length > 0)
		{
			for (int i = 0; i < length; i++)
			{
				text = text + "0";
			}
		}
		return text;
	}
}
