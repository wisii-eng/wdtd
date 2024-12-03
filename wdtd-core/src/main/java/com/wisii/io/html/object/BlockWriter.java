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
 * @BlockWriter.java
 *                   北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.object;

import java.util.List;

import com.wisii.io.html.HtmlContext;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.io.XMLUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang
 * 创建日期：2012-5-31
 */
public class BlockWriter extends DefaultObjectWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.io.html.object.ObjectWriter#getString(com.wisii.wisedoc.document
	 * .CellElement, com.wisii.io.html.HtmlContext)
	 */
	@Override
	public String getString(CellElement element, HtmlContext context)
	{
		if (!(element instanceof Block))
		{
			return "";
		}
		boolean linefeed = ((Block)element).getLinefeedTreatment()==Constants.EN_PRESERVE;
		StringBuffer sb = new StringBuffer();
		List<CellElement> inlines = element.getAllChildren();
		if (inlines != null && !inlines.isEmpty())
		{
			String text = "";
			CellElement preinline = null;
			CellElement currentinline;
			int size = inlines.size();
			boolean hastext=false;
			for (int i = 0; i < size; i++)
			{
				currentinline = inlines.get(i);
				if (currentinline instanceof TextInline
						&& !((TextInline) currentinline).getContent()
								.isBindContent())
				{
					TextInline textinline = (TextInline) currentinline;
					// 如果当前文本的属性相等，则合并两textInline
					if (preinline == null
							|| (preinline != null
									&& (preinline instanceof TextInline) && ((preinline
									.getAttributes() == null && textinline
									.getAttributes() == null) || preinline
									.getAttributes().equals(
											textinline.getAttributes()))))
					{
						text = text + textinline.getContent().getText();
					}
					// 否则如果text不等于空串，则生成TextInline代码
					else
					{
						if (!text.equals(""))
						{
							hastext=true;
							TextInline ptextinline = (TextInline) preinline;
							sb.append(generateinlineString(ptextinline, text,
									context));
						}
						text = textinline.getContent().getText();
					}
				} else
				{
					// 判断之前是否有连续的静态文本Inline
					if (!text.equals(""))
					{
						hastext=true;
						TextInline ptextinline = (TextInline) preinline;
						sb.append(generateinlineString(ptextinline, text,
								context));
						text = "";
					}
					// 如果是绑定动态数据节点的文本
					if (currentinline instanceof TextInline
							&& ((Inline) currentinline).getContent() != null)
					{
						sb.append(generateBindingText((Inline) currentinline,
								context,linefeed));
					} else
					{
						sb.append(WriterFactory.getWriter(currentinline)
								.getString(currentinline, context));
					}
				}
				preinline = currentinline;
			}
			if (!text.equals(""))
			{
				hastext=true;
				TextInline ptextinline = (TextInline) preinline;
				sb.append(generateinlineString(ptextinline, text, context));
				text = "";
			}
			if(!hastext)
			{
				sb.append("<xsl:text disable-output-escaping=\"yes\">&amp;nbsp;</xsl:text>");
			}
		}
	
		return sb.toString();
	}

	/*
	 * 
	 * 生成带有动态数据的文本节点
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * 
	 * @return {返回参数名} {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	private String generateBindingText(Inline inlne, HtmlContext context,boolean islinefeed)
	{
		StringBuffer sb = new StringBuffer();
		if (inlne != null)
		{

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
				if(islinefeed)
				{
					sb.append("<xsl:call-template name=\"linebreak\"><xsl:with-param name=\"text\" select=\""
								+ context.getRelatePath(content.getBindNode())
								+"\"/></xsl:call-template>");
					context.addFunctionTemplate("linebreak");
						
				}
				else{
				sb
						.append("<xsl:value-of select=\""
								+ context.getRelatePath(content.getBindNode())
								+ "\"/>");
				}
				sb.append("</font>");
				sb.append(getDynicStringEnd(att, context));
			}
		}
		return sb.toString();
	}

	private String generateinlineString(TextInline textinlne, String text,
			HtmlContext context)
	{
		StringBuffer sb = new StringBuffer();
		if (textinlne != null && text != null && !text.isEmpty())
		{
			Attributes att = textinlne.getAttributes();
			sb.append(getDynicStringBegin(att, context));
			sb.append("<font");
			String classn = context.getStyleClass(textinlne.getAttributes());
			if (classn != null)
			{
				sb.append(" class=\"" + classn + "\"");
			}
			sb.append(">");
			sb.append(getDynicStyleString(att,context));
			sb.append(XMLUtil.getXmlText(text));
			sb.append("</font>");
			sb.append(getDynicStringEnd(att, context));
		}
		return sb.toString();
	}
}
