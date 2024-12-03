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
 * @PositionInlineWriter.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2011-4-12
 */
public class PositionInlineWriter extends InlineWriter
{
	public PositionInlineWriter(CellElement element, int n)
	{
		super(element, n);
		// TODO Auto-generated constructor stub
	}

	public String write(CellElement element)
	{
		StringBuffer output = new StringBuffer();
		output.append(beforeDeal());
		output.append(textNoBinding());
		output.append(endDeal());
		return output.toString();
	}
	public String textNoBinding()
	{
		StringBuffer output = new StringBuffer();
		output.append(getHeaderElement());
		output.append(getFormatContent());
		output.append(ElementUtil.endElement(FoXsltConstants.INLINE));
		return output.toString();
	}
	public String getFormatContent()
	{
		StringBuffer output = new StringBuffer();
		Inline inlne = (Inline) foElement;
		EnumProperty type = (EnumProperty) inlne.getAttribute(Constants.PR_POSITION_NUMBER_TYPE);
	    if(type==null)
	    {
	    	type = new EnumProperty(Constants.EN_POSITION_NUMBER_1, "");
	    }
	    String prostr=getAttribute(Constants.PR_POSITION_NUMBER_TYPE, type);
	    output.append("<xsl:number value=\"position()\" " + prostr + "/>");
	    return output.toString();
	}
}
