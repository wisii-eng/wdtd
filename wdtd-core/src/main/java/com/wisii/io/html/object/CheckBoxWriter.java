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
 * @CheckBoxWriter.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.object;

import java.util.Map;

import com.wisii.io.html.HtmlContext;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-6-8
 */
public class CheckBoxWriter extends DefaultObjectWriter
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
		if (element instanceof CheckBoxInline)
		{
			CheckBoxInline inlne = (CheckBoxInline) element;
			EnumProperty tickmark = (EnumProperty) inlne.getAttribute(Constants.PR_CHECKBOX_TICKMARK);
			String type="checkbox";
			if (tickmark != null
					&& tickmark.getEnum() == Constants.EN_CHECKBOX_TICKMARK_DOT)
			{
				type="radio";
			} 
			InlineContent content = inlne.getContent();
			if (content != null && content.isBindContent())
			{
				Attributes att = inlne.getAttributes();
				sb.append(getDynicStringBegin(att, context));
				sb.append("<input type=\""+type+"\" disabled=\"true\" readOnly=\"true\"");
				String classn = context.getStyleClass(inlne.getAttributes());
				if (classn != null)
				{
					sb.append(" class=\"" + classn + "\"");
				}
				sb.append(">");
				BindNode node = content.getBindNode();
				sb.append(ElementUtil.selectAssignment(
						FoXsltConstants.VARIABLE,
						FoXsltConstants.CONTENT + "0", new StringBuffer(context
								.getRelatePath(node))));
				sb.append(getformate(context, att.getAttributes()));
				sb.append("</input>");
				sb.append(getDynicStringEnd(att, context));
			}
		}
		return sb.toString();
	}

	/**
	 * {方法的功能/动作描述}
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	
	private String getformate(HtmlContext context,
			Map<Integer, Object> attributes)
	{
		StringBuffer sb = new StringBuffer();
		String selectvalue = (String) attributes
				.get(Constants.PR_RADIO_CHECK_VALUE);
		String unselectvalue = (String) attributes
				.get(Constants.PR_CHECKBOX_UNSELECT_VALUE);
		if (selectvalue != null && !selectvalue.isEmpty())
		{
			sb
					.append("<xsl:if test=\"$content0='"
							+ selectvalue
							+ "'\"><xsl:attribute name=\"checked\">true</xsl:attribute></xsl:if>");
		} else if (unselectvalue != null && !unselectvalue.isEmpty())
		{
			sb
					.append("<xsl:if test=\"$content0='"
							+ unselectvalue
							+ "'\"><xsl:attribute name=\"checked\">false</xsl:attribute></xsl:if>");
		} else
		{
			EnumProperty defaultvalue = (EnumProperty) attributes
					.get(Constants.PR_RADIO_CHECK_CHECKED);
			if (defaultvalue != null
					&& defaultvalue.getEnum() == Constants.EN_TRUE)
			{
				sb
						.append("<xsl:attribute name=\"checked\">true</xsl:attribute>");
			}
		}

		return sb.toString();
	}

}
