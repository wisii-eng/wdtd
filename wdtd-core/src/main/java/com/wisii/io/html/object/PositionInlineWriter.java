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
 *                            北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.object;

import java.util.Map;

import com.wisii.io.html.HtmlContext;
import com.wisii.io.html.att.EnumIntWriter;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.PositionInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.EnumProperty;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang
 * 创建日期：2012-6-11
 */
public class PositionInlineWriter extends DefaultObjectWriter
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 
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
		if (element instanceof PositionInline)
		{
			PositionInline inlne = (PositionInline) element;

			Attributes att = inlne.getAttributes();
			sb.append(getDynicStringBegin(att, context));
			sb.append("<font");
			String classn = context.getStyleClass(inlne.getAttributes());
			if (classn != null)
			{
				sb.append(" class=\"" + classn + "\"");
			}
			sb.append(">");
			sb.append(getDynicStyleString(att, context));
			sb.append(getFormateString(context, att.getAttributes()));
			sb.append("</font>");
			sb.append(getDynicStringEnd(att, context));
		}
		return sb.toString();
	}

	public String getFormateString(HtmlContext context, Map<Integer, Object> map)
	{
		StringBuffer output = new StringBuffer();
		EnumProperty type = (EnumProperty) map
				.get(Constants.PR_POSITION_NUMBER_TYPE);
		if (type == null)
		{
			type = new EnumProperty(Constants.EN_POSITION_NUMBER_1, "");
		}
		String prostr = getAttribute(Constants.PR_POSITION_NUMBER_TYPE, type);
		output.append("<xsl:number value=\"position()\" " + prostr + "/>");
		return output.toString();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private String getAttribute(int prPositionNumberType, EnumProperty type)
	{
		return "format=\"" + new EnumIntWriter().getValue(type.getEnum())
				+ "\"";
	}

}
