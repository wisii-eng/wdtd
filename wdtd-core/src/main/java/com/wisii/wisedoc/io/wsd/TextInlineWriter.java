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
 * @TextInlineWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.util.WisedocUtil;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-3-11
 */
public class TextInlineWriter extends DefaultElementWriter
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.ElementWriter#write(com.wisii.wisedoc.document.Element
	 * )
	 */
	public String write(CellElement element)
	{
		String returns = "";
		if (element instanceof TextInline)
		{
			TextInline textinline = (TextInline) element;
			if (textinline instanceof TextInline
					&& !textinline.getContent().isBindContent())
			{
				returns = generateinlineString(textinline, textinline
						.getContent().getText());
			} else
			{
				returns = generateBindingText(textinline);
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
}
