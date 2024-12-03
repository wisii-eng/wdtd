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
 * @QianZhangWriter.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2011-11-16
 */
public class QianZhangWriter extends AbsElementWriter
{

	public QianZhangWriter(CellElement element)
	{
		super(element);
		// TODO Auto-generated constructor stub
	}

	public String getElementName()
	{
		return FoXsltConstants.QIANZHANG;
	}

	public String write(CellElement element)
	{
		StringBuffer output = new StringBuffer();
		output.append(beforeDeal());
		output.append(getSrcVar());
		output.append(getCode());
		output.append(endDeal());
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getAttributes()
	 */
	public String getAttributes()
	{
		StringBuffer code = new StringBuffer();
		code.append(super.getAttributes());
		if (attributemap.containsKey(Constants.PR_SRC))
		{
			code.append(getAttribute(Constants.PR_SRC, "{$"
					+ FoXsltConstants.SRC + "}"));
		}
		return code.toString();
	}

	public String getChildCode()
	{
		return "";
	}

	// @SuppressWarnings("unchecked")
	public String getSrcVar()
	{
		StringBuffer code = new StringBuffer();
		Object src = attributemap.get(Constants.PR_SRC);
		if (src instanceof String)
		{
			String srcname = (String) src;
			srcname = IoXslUtil.getXmlText(srcname);
			code.append(ElementUtil.selectAssignment(FoXsltConstants.VARIABLE,
					FoXsltConstants.SRC + "Value", srcname));
		}
		return code.toString();
	}
}
