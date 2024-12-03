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
 * @FoElement.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-26
 */
public class FoElementWriter extends AbsElementWriter
{

	String name = "";

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public FoElementWriter(CellElement element)
	{
		super(element);
		name = element.getClass().getName();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.ElementObject#getElementName()
	 */
	public String getElementName()
	{
		return new XSLElementNameWriter().getElementName(name);
	}

	@Override
	public String getEndElement()
	{
		StringBuffer code = new StringBuffer();
		CellElement ps = foElement;
		while (!(ps instanceof PageSequence))
		{
			Element parent = foElement.getParent();
			if (parent != null)
			{
				ps = (CellElement) ps.getParent();
			} else
			{
				break;
			}
		}
		WiseDocDocument doc = (WiseDocDocument) ps.getParent();
		int index = doc.getIndex(ps);
		int total = doc.getChildCount() - 1;
		boolean isadd = false;
		if ((foElement instanceof Flow)
				&& !(foElement instanceof StaticContent))
		{

			if (PageSequenceWriter.isAddBlock())
			{
				code.append(ElementWriter.TAGBEGIN);
				code.append(FoXsltConstants.VARIABLE);
				code.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
						"l"));
				code.append(ElementWriter.TAGEND);
				// code = code
				// + ElementUtil.startElementVF(FoXsltConstants.VALUE_OF,
				// IoXslUtil.getCurrentId("e" + index + "e"));
				code.append(IoXslUtil.getCurrentId("e" + index + "e"));
				// code = code +
				// ElementUtil.endElement(FoXsltConstants.VALUE_OF);
				code.append(ElementWriter.TAGENDSTART);
				code.append(FoXsltConstants.VARIABLE);
				code.append(ElementWriter.TAGEND);

				code.append(ElementWriter.TAGBEGIN);
				code.append(FoXsltConstants.BLOCK);
				code.append(ElementUtil.outputAttributes("id", "{$l}"));
				code.append(ElementWriter.NOCHILDTAGEND);
				isadd = true;
			}
			if (IoXslUtil.getAddPageNumberType() && !isadd)
			{
				for (int i = total; i > -1; i--)
				{
					PageSequence current = (PageSequence) doc.getChildAt(i);
					if (current.getAttribute(Constants.PR_GROUP) != null)
					{
						code.append(ElementUtil.startElementWI(
								FoXsltConstants.IF, "position()=last()"));

						code.append(ElementWriter.TAGBEGIN);
						code.append(FoXsltConstants.VARIABLE);
						code.append(ElementUtil.outputAttributes(
								FoXsltConstants.NAME, "l"));
						code.append(ElementWriter.TAGEND);
						// code = code
						// +
						// ElementUtil.startElementVF(FoXsltConstants.VALUE_OF,
						// IoXslUtil.getCurrentId("e" + index + "e"));
						code.append(IoXslUtil.getCurrentId("e" + i + "e"));
						// code = code +
						// ElementUtil.endElement(FoXsltConstants.VALUE_OF);
						code.append(ElementWriter.TAGENDSTART);
						code.append(FoXsltConstants.VARIABLE);
						code.append(ElementWriter.TAGEND);

						code.append(ElementWriter.TAGBEGIN);
						code.append(FoXsltConstants.BLOCK);

						// add var
						// code = code
						// + ElementUtil.outputAttributes("id", IoXslUtil
						// .getCurrentId("e" + i + "e"));
						code.append(ElementUtil.outputAttributes("id", "{$l}"));
						code.append(ElementWriter.NOCHILDTAGEND);
						code.append(ElementUtil.endElement(FoXsltConstants.IF));
					} else if (index == i)
					{
						code.append(ElementWriter.TAGBEGIN);
						code.append(FoXsltConstants.VARIABLE);
						code.append(ElementUtil.outputAttributes(
								FoXsltConstants.NAME, "l"));
						code.append(ElementWriter.TAGEND);
						// code = code
						// +
						// ElementUtil.startElementVF(FoXsltConstants.VALUE_OF,
						// IoXslUtil.getCurrentId("e" + index + "e"));
						code.append(IoXslUtil.getCurrentId("e" + index + "e"));
						// code = code +
						// ElementUtil.endElement(FoXsltConstants.VALUE_OF);
						code.append(ElementWriter.TAGENDSTART);
						code.append(FoXsltConstants.VARIABLE);
						code.append(ElementWriter.TAGEND);

						code.append(ElementWriter.TAGBEGIN);
						code.append(FoXsltConstants.BLOCK);

						// add var
						// code = code
						// + ElementUtil.outputAttributes("id", IoXslUtil
						// .getCurrentId("e" + index + "e"));
						code.append(ElementUtil.outputAttributes("id", "{$l}"));
						code.append(ElementWriter.NOCHILDTAGEND);
						break;
					}
				}
			}
		}
		code.append(ElementWriter.TAGENDSTART);
		code.append(getElementName());
		code.append(ElementWriter.TAGEND);
		return code.toString();
	}
}
