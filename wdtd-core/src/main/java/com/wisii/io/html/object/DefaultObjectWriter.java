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
 * @DefaultObjectWriter.java
 *                           北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.object;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.io.html.HtmlContext;
import com.wisii.io.html.att.AttributesWriter;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableColumn;
import com.wisii.wisedoc.document.TableFooter;
import com.wisii.wisedoc.document.TableHeader;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.ConditionItemCollection;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.io.util.ConditionWriterUtil;
import com.wisii.wisedoc.io.util.GroupWriterUtil;

/**
 * 类功能描述：元素输出的默认类
 * 正常情况下，元素的模板输出均通过此类
 * 作者：zhangqiang
 * 创建日期：2012-5-24
 */
public class DefaultObjectWriter implements ObjectWriter
{

	private Map<Class, String> namemap;

	public DefaultObjectWriter()
	{
		initMap();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private void initMap()
	{
		namemap = new HashMap<Class, String>();
		namemap.put(Block.class, "p");
		namemap.put(BlockContainer.class, "div");
		namemap.put(TextInline.class, "font");
		namemap.put(DateTimeInline.class, "font");
		namemap.put(CheckBoxInline.class, "input");
		namemap.put(ImageInline.class, "img");
		namemap.put(NumberTextInline.class, "font");
		namemap.put(Table.class, "table");
		namemap.put(TableBody.class, "tbody");
		namemap.put(TableFooter.class, "tfoot");
		namemap.put(TableHeader.class, "thead");
		namemap.put(TableRow.class, "tr");
		namemap.put(TableCell.class, "td");
		namemap.put(TableColumn.class, "col");
		namemap.put(ImageInline.class, "img");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.io.html.object.ObjectWriter#getString(java.lang.Object)
	 */
	@Override
	public String getString(CellElement element, HtmlContext context)
	{
		if (element == null)
		{
			return "";
		}
		Class c = element.getClass();
		StringBuffer sb = new StringBuffer();
		Attributes att = element.getAttributes();
		String classname = null;
		if (att != null)
		{
			sb.append(getDynicStringBegin(att, context));
			classname = context.getStyleClass(att);
		}
		if (c.equals(com.wisii.wisedoc.document.Group.class)||c.equals(com.wisii.wisedoc.document.ZiMoban.class))
		{
			// 如果是分组对象，则直接输出子元素

			List<CellElement> children = element.getAllChildren();
			for (CellElement child : children)
			{
				sb.append(getString(child, context));
			}
			sb.append(getDynicStringEnd(att, context));
			return sb.toString();
		}
		String elementname = namemap.get(c);

		if (elementname == null)
		{
			return "";
		}
		sb.append("<" + elementname);
		if (classname != null)
		{
			sb.append(" class=\"" + classname + "\"");
		
		}
		if (element instanceof TableCell)
		{
			TableCell tc = (TableCell) element;
			int colspan = tc.getNumberColumnsSpanned();
			int rowspan = tc.getNumberRowsSpanned();
			if (colspan > 1)
			{
				sb.append(" colspan=\"" + colspan + "\"");
			}
			if (rowspan > 1)
			{
				sb.append(" rowspan=\"" + rowspan + "\"");
			}
		}
		else if(element instanceof Table)
		{
			sb.append(" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"");
		}
		sb.append('>');
		sb.append(getDynicStyleString(att, context));
		if (element instanceof Block)
		{
			sb.append(WriterFactory.getWriter(element).getString(element,
					context));
		} else if (element instanceof Table)
		{
			sb.append(WriterFactory.getWriter(element).getString(element,
					context));
		} else
		{
			List<CellElement> children = element.getAllChildren();
			for (CellElement child : children)
			{
				if (child.getParent() == element)
				{
					sb.append(getString(child, context));
				}
			}
			if (element instanceof TableCell
					&& (children == null || children.isEmpty()))
			{
				sb
						.append("<xsl:text disable-output-escaping=\"yes\">&amp;nbsp;</xsl:text>");
			}
		}
		sb.append("</" + elementname + ">");
		sb.append(getDynicStringEnd(att, context));
		return sb.toString();
	}

	/**
	 * {方法的功能/动作描述}
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	
	protected  String getDynicStyleString(Attributes att, HtmlContext context)
	{
		ConditionItemCollection items = (ConditionItemCollection) att
				.getAttribute(Constants.PR_DYNAMIC_STYLE);
		if (items == null || items.isEmpty())
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (ConditionItem item : items)
		{
			if (item != null)
			{
				LogicalExpression con = item.getCondition();
				sb.append("<xsl:if test=\"");
				sb.append(ConditionWriterUtil.getConditionString(con, context));
				sb.append("\">");
				Attributes atts = item.getAttributes();
				String bodyattstr = AttributesWriter.getString(atts, context);
				sb.append("<xsl:attribute name=\"style\">" + bodyattstr
						+ "</xsl:attribute>");
				sb.append("</xsl:if>");

			}
		}
		return sb.toString();
	}

	public final static String getDynicStringBegin(Attributes att, HtmlContext context)
	{
		StringBuffer sb = new StringBuffer();
		LogicalExpression con = (LogicalExpression) att
				.getAttribute(Constants.PR_CONDTION);
		int nostylecount = 0;
		if (con != null)
		{
			nostylecount++;
			sb.append("<xsl:if test=\"");
			sb.append(ConditionWriterUtil.getConditionString(con, context));
			sb.append("\">");
		}
		Group group = (Group) att.getAttribute(Constants.PR_GROUP);
		if (group != null)
		{
			nostylecount++;
			sb.append(GroupWriterUtil.getGroupString(group, context));

		}
		return sb.toString();
	}

	public final static String getDynicStringEnd(Attributes att, HtmlContext context)
	{
		StringBuffer sb = new StringBuffer();
		if (att != null)
		{
			LogicalExpression con = (LogicalExpression) att
					.getAttribute(Constants.PR_CONDTION);
			if (con != null)
			{
				sb.append("</xsl:if>");
			}
			Group group = (Group) att.getAttribute(Constants.PR_GROUP);
			if (group != null)
			{
				sb.append("</xsl:for-each>");
				context.restoreGroupContext(group);
			}
		}
		return sb.toString();
	}
}
