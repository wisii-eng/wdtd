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
 * @QianZhangInlineWriter.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.util.Enumeration;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.QianZhangInline;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2011-11-16
 */
public class QianZhangInlineWriter  extends InlineWriter
{

	Inline inline;

	int number;

	public QianZhangInlineWriter(CellElement element, int n)
	{
		super(element, n);
		inline = (Inline) element;
		number = n;
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.xsl.AbsElementWriter#write(com.wisii.wisedoc.document
	 * .CellElement)
	 */
	public String write(CellElement element)
	{
		StringBuffer output = new StringBuffer();
		output.append(beforeDeal());
		QianZhangInline inlne = (QianZhangInline) foElement;
		Object src = null;
		Enumeration<CellElement> child = inlne.children();
		if (child != null && child.hasMoreElements())
		{
			QianZhang externalg = (QianZhang) child.nextElement();
			src = externalg.getAttribute(Constants.PR_SRC);
		}
		if (src == null || src instanceof String)
		{
			output.append(textNoBinding());
		} 
		output.append(endDeal());
		return output.toString();
	}

	/**
	 * 
	 * 获得元素的代码部分，内容为非绑定节点。
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
		output.append(getChildCode());
		output.append(ElementUtil.endElement(FoXsltConstants.INLINE));
		return output.toString();
	}

	public String getHeaderElement()
	{
		StringBuffer code = new StringBuffer();
		code.append(getHeaderStart());
		code.append(getAttributes());
		code.append(getHeaderEnd());
		return code.toString();
	}

	/**
	 * 
	 * 获得元素的代码部分，不包括对元素代码部分用到的变量的定义。
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getCodebasic()
	{
		StringBuffer code = new StringBuffer();
		code.append(getHeaderElement());
		code.append(getChildCode());
		code.append(getEndElement());
		return code.toString();
	}
}

