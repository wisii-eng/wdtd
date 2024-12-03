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
 * @ParagraphStylesWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;
import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.wsd.DocumentWirter;

/**
 * 类功能描述：段落样式表项输出类
 *
 * 作者：zhangqiang
 * 创建日期：2009-3-30
 */
public class ParagraphStylesWriter extends AbstractAttributeWriter
{
    public final static String ID = "id";
    public final static String PARAGRAPHSTYLE = "paragraphstyle";
    public final static String NAME = "name";
    public final static String STYLEID = "styleid";
	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public ParagraphStylesWriter()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	@Override
	public String write(int key, Object value)
	{
		if (value == null || !(value instanceof ParagraphStyles))
		{
			return "";
		}
		String returns = "";
		ParagraphStyles dynamicstyle = (ParagraphStyles) value;
		returns = returns + getParagraphStylesString(dynamicstyle);
		return returns;
	}
	private String getParagraphStylesString(ParagraphStyles paragraphstyles)
	{
		String returns = "";
		if (paragraphstyles != null)
		{
			String id = DocumentWirter.getParagraphStylesID(paragraphstyles)
					.trim();
			String refid = "";
			if (id != null && !id.equals(""))
			{
				refid = SPACETAG + ID + EQUALTAG + QUOTATIONTAG + id
						+ QUOTATIONTAG;
			}
			String name = SPACETAG + NAME + EQUALTAG + QUOTATIONTAG
					+ paragraphstyles.getStyleName() + QUOTATIONTAG;
			String attid = SPACETAG
					+ STYLEID
					+ EQUALTAG
					+ QUOTATIONTAG
					+ DocumentWirter.getAttributesID(paragraphstyles
							.getAttributes()) + QUOTATIONTAG;
			returns = returns + ElementWriter.TAGBEGIN + PARAGRAPHSTYLE + refid
					+ name + attid + ElementWriter.NOCHILDTAGEND
					+ ElementWriter.LINEBREAK;

		}
		return returns;
	}
}
