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
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.BarCodeInline;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.util.WisedocUtil;

/**
 * 类功能描述：段落对象输出类，段落输出不同其他类型的元素输出，段落需要 把静态的相同属性的文本inline合并。
 * 
 * 作者：zhangqiang 创建日期：2008-9-19
 */
public class BlockWriter extends DefaultElementWriter
{
	protected String getChildrenString(Enumeration<CellElement> children)
	{
		String returns = "";
		if (children != null && children.hasMoreElements())
		{
			List<CellElement> inlines = new ArrayList<CellElement>();

			while (children.hasMoreElements())
			{
				CellElement inline = (CellElement) children.nextElement();
				if (inline != null)
				{
					inlines.add(inline);
				}
			}
			String text = "";
			CellElement preinline = null;
			CellElement currentinline;
			int size = inlines.size();
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
							TextInline ptextinline = (TextInline) preinline;
							returns = returns
									+ generateinlineString(ptextinline, text);
						}
						text = textinline.getContent().getText();
					}
				} else
				{
					// 判断之前是否有连续的静态文本Inline
					if (!text.equals(""))
					{
						TextInline ptextinline = (TextInline) preinline;
						returns = returns
								+ generateinlineString(ptextinline, text);
						text = "";
					}
					// 如果是绑定动态数据节点的文本
					if (currentinline instanceof Inline&&!(currentinline instanceof BarCodeInline)
							&& ((Inline) currentinline).getContent() != null)
					{
						returns = returns
								+ generateBindingText((Inline) currentinline);
					} else
					{
						returns = returns
								+ ewf.getWriter(currentinline).write(
										currentinline);
					}
				}
				preinline = currentinline;
			}
			if (!text.equals(""))
			{
				TextInline ptextinline = (TextInline) preinline;
				returns = returns + generateinlineString(ptextinline, text);
				text = "";
			}
		}
		return returns;
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
	private String generateBindingText(Inline inlne)
	{
		String returns = "";
		if (inlne != null && inlne.getContent() != null)
		{
			if(inlne.getContent().isBindContent()){
			String ptextname = getElementName(inlne);
			Attributes atts = inlne.getAttributes();
			String attsrefid = "";
			// 生成属性引用代码
			String refid = DocumentWirter.getAttributesID(atts);
			if (atts != null && refid != null)
			{
				attsrefid = SPACETAG + attsrefid + ATTRIBUTEREFID + EQUALTAG
						+ QUOTATIONTAG + refid + QUOTATIONTAG + SPACETAG;
			}
			BindNode node = inlne.getContent().getBindNode();
			String dataid = DocumentWirter.getDataNodeID(node);
			String dataids = "";
			if (node != null && dataid != null)
			{
				dataids = SPACETAG + dataids + DATAREFID + EQUALTAG
						+ QUOTATIONTAG + dataid + QUOTATIONTAG + SPACETAG;
			}
			returns = returns + TAGBEGIN + ptextname + attsrefid + dataids
					+ NOCHILDTAGEND;
			}
			else
			{
				String ptextname = getElementName(inlne);
				Attributes atts = inlne.getAttributes();
				String attsrefid = "";
				// 生成属性引用代码
				String refid = DocumentWirter.getAttributesID(atts);
				if (atts != null && refid != null)
				{
					attsrefid = SPACETAG + attsrefid + ATTRIBUTEREFID + EQUALTAG
							+ QUOTATIONTAG + refid + QUOTATIONTAG + SPACETAG;
				}
				String  text = inlne.getContent().getText();
				String texts = "";
				if (!"".equals(text))
				{
					texts = SPACETAG + texts + TEXT + EQUALTAG
							+ QUOTATIONTAG + text + QUOTATIONTAG + SPACETAG;
				}
				returns = returns + TAGBEGIN + ptextname + attsrefid + texts
						+ NOCHILDTAGEND;
			}
		}
		return returns;
	}

	protected String generateinlineString(TextInline textinlne, String text)
	{
		String returns = "";
		if (textinlne != null && text != null && !text.isEmpty())
		{
			String ptextname = getElementName(textinlne);
			Attributes atts = textinlne.getAttributes();
			String attsrefid = "";
			// 生成属性引用代码
			String refid = DocumentWirter.getAttributesID(atts);
			if (atts != null && refid != null)
			{
				attsrefid = SPACETAG + attsrefid + ATTRIBUTEREFID + EQUALTAG
						+ QUOTATIONTAG + refid + QUOTATIONTAG + SPACETAG;
			}
			returns = returns + TAGBEGIN + ptextname + attsrefid + TAGEND;
			returns = returns + WisedocUtil.getXmlText(text) + TAGENDSTART
					+ ptextname + TAGEND + LINEBREAK;
		}
		return returns;
	}
}
