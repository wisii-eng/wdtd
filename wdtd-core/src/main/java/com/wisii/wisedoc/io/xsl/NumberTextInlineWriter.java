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
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.NumberFormat;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.Constants;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class NumberTextInlineWriter extends InlineWriter
{

	private static int numberText = 0;

	public NumberTextInlineWriter(CellElement element, int n)
	{
		super(element, n);
		// TODO Auto-generated constructor stub
	}

	public String write(CellElement element)
	{
		StringBuffer output = new StringBuffer();
		output.append(beforeDeal());
		Inline inlne = (Inline) foElement;
		InlineContent textin = inlne.getContent();
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
		output.append(endDeal());
		return output.toString();
	}

	/**
	 * 
	 * 获得inline内容的非转换的代码，根据inline的类型确定该处的输出（普通文本，格式化数字，日期时间格式等）
	 * 
	 * @param 引入参数名
	 *            <xsl:value-of
	 *            select="format-number($content,'###,###.00','NumberText0')"/>
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	@SuppressWarnings("unchecked")
	public String getFormatContent()
	{
		StringBuffer code = new StringBuffer();
		NumberTextInline numbertextinle = (NumberTextInline) inline;
		Text text = (Text) numbertextinle.getContent();
		if (attributemap != null
				&& attributemap
						.containsKey(com.wisii.wisedoc.document.Constants.PR_DYNAMIC_STYLE))
		{
			List<ConditionItem> conditions = (List<ConditionItem>) attributemap
					.get(com.wisii.wisedoc.document.Constants.PR_DYNAMIC_STYLE);
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
					LogicalExpression condition = currentitem.getCondition();
					Map<Integer, Object> styles = currentitem.getStyles();
					if (condition != null)
					{
						code.append(ElementWriter.TAGBEGIN
								+ FoXsltConstants.WHEN + " ");
						code.append(ElementUtil.outputAttributes(
								FoXsltConstants.TEST, ElementUtil
										.returnStringIf(condition, true)));
						code.append(ElementWriter.TAGEND);
						code.append(getOnetypeEnum(IoXslUtil.getReplaceMap(
								attributemap, styles), text));
						code.append(ElementUtil
								.endElement(FoXsltConstants.WHEN));
					}
				}
				code
						.append(ElementUtil
								.startElement(FoXsltConstants.OTHERWISE));
				code.append(getOnetypeEnum(attributemap, text));
				code.append(ElementUtil.endElement(FoXsltConstants.OTHERWISE));
				code.append(ElementUtil.endElement(FoXsltConstants.CHOOSE));
			}
		} else
		{
			code.append(getOnetypeEnum(attributemap, text));
		}
		return code.toString();
	}

	private String getOnetypeEnum(Map<Integer, Object> map, Text text)
	{
		StringBuffer output = new StringBuffer();
		EnumProperty typeEnum = null;
		if (map
				.containsKey(com.wisii.wisedoc.document.Constants.PR_NUMBERTEXT_TYPE))
		{
			typeEnum = (EnumProperty) map
					.get(com.wisii.wisedoc.document.Constants.PR_NUMBERTEXT_TYPE);
		}
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
		}
		else if (type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL_ADDZHENG)
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
		}
		else if (type == com.wisii.wisedoc.document.Constants.EN_NORMAL)
		{
			StringBuffer select = new StringBuffer();
			select.append(FoXsltConstants.FORMAT_NUMBER);
			select.append("(");
			String varstr="";
			if (text.isBindContent())
			{
				varstr="$content"+number;
				
			} else
			{
				varstr=text.getText();
			}
			select.append(varstr);
			select.append(",");
			NumberFormat numberFormat = null;
			String dotString="";
			if (!map
					.containsKey(com.wisii.wisedoc.document.Constants.PR_NUMBERFORMAT))
			{
				select.append("'#,###.00')");
				dotString=".00";
			} else
			{
				numberFormat = (NumberFormat) map
						.get(com.wisii.wisedoc.document.Constants.PR_NUMBERFORMAT);
				if (numberFormat == null)
				{
					select.append("'#,###.00')");
					dotString=".00";
				} else
				{
					select.append("'"+numberFormat.toString()+"')");
					int length = numberFormat.getDecimalDigits();
					if (length > 0)
					{
						dotString = "."+getString(length);
					}
				}
			}
			output.append(getchooseString(varstr,select.toString(),dotString));

		}
		if (paramMap.containsKey("flg"))
		{
			StringBuffer numberValue = new StringBuffer();
			numberValue.append("$content" + number);
			paramMap.put("data", numberValue);
			// paramMap.put("error", error);

			if (type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL
					|| type == com.wisii.wisedoc.document.Constants.EN_CHINESELOWERCASE
					|| type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL_ADDZHENG
					|| type == com.wisii.wisedoc.document.Constants.EN_CHINESELOWERCASE_ADDZHENG)
			{
				IoXslUtil.addFunctionTemplate("je");
				IoXslUtil.addFunctionTemplate("TSxs");
				output.append(ElementUtil.outputCalltemplate("je", paramMap));
			} else if (type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL_ZC
					|| type == com.wisii.wisedoc.document.Constants.EN_CHINESECAPITAL_ZC_ADDZHENG)
			{
				IoXslUtil.addFunctionTemplate("jezc");
				IoXslUtil.addFunctionTemplate("TSxs");
				output.append(ElementUtil.outputCalltemplate("jezc", paramMap));
			}

			IoXslUtil.addProfile("number");
			Map<Integer, Object> attributemap = new HashMap<Integer, Object>();
			attributemap.put(
					com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAME, "num");
			attributemap.put(
					com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT,
					"document('')/xsl:stylesheet");
			XslElementObj xslelement = new XslElementObj(attributemap,
					FoXsltConstants.VARIABLE);
			IoXslUtil.addOverall(xslelement);
			NameSpace fovNameSpace = new NameSpace(
					FoXsltConstants.SPACENAMEFOV, FoXsltConstants.COMWISIIFOV);
			IoXslUtil.addNameSpace(fovNameSpace);
		}

		return output.toString();
	}
    private String getchooseString(String varstr,String selectsrt,String dotString)
    {
    	StringBuffer result=new StringBuffer();
    	result.append("	<xsl:choose><xsl:when test=\"number(");
    	result.append(varstr);
    	result.append(")=number(0)\">0");
    	result.append(dotString);
    	result.append("</xsl:when><xsl:otherwise>");
    	result.append(ElementUtil.ElementValueOf(selectsrt));
    	result.append("</xsl:otherwise></xsl:choose>");
    	return result.toString();
    }
	public String textNoBinding()
	{
		StringBuffer output = new StringBuffer();
		output.append(getHeaderElement());
		output.append(getFormatContent());
		output.append(ElementUtil.endElement(FoXsltConstants.INLINE));
		return output.toString();
	}

	/**
	 * @返回 numberText变量的值
	 */
	public static int getNumberText()
	{
		return numberText;
	}

	/**
	 * @param numberText
	 *            设置numberText成员变量的值 值约束说明
	 */
	public static void setNumberText(int numberText)
	{
		NumberTextInlineWriter.numberText = numberText;
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
