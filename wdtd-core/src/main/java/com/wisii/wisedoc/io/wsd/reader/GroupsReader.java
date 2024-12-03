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
 * @GroupsReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.Sort;
import com.wisii.wisedoc.io.wsd.attribute.GroupWriter;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-11-13
 */
public class GroupsReader extends AbstractHandler
{
	private List<Group> groups = new ArrayList<Group>();
	private BindNode node;
	private LogicalExpression fliterCondition;
	private EnumProperty editmode;
	EnumNumber max;
	List<Sort> sorts = new ArrayList<Sort>();
	EnumPropertyReader enumPropertyReader = new EnumPropertyReader();

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (GroupWriter.GROUPTAG.equals(qname))
		{
			creatGroupAtt(atts);
		} else if (GroupWriter.SORTTAG.equals(qname))
		{
			sorts.add(getSort(atts));
		} else
		{
			LogUtil.debug("groups 节点下的" + qname + "节点为非法");
		}
	}

	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (GroupWriter.GROUPTAG.equals(qname))
		{
			Group group = Group.Instanceof(node, fliterCondition, sorts,
					editmode, max);
			node = null;
			fliterCondition = null;
			sorts.clear();
			editmode = null;
			max = null;
			groups.add(group);
		}
	}

	public List<Group> getObject()
	{
		// TODO Auto-generated method stub
		return groups;
	}

	private Sort getSort(Attributes atts)
	{
		String attString = atts.getValue(GroupWriter.SORTATT);
		if (attString != null)
		{
			String[] values = attString.split(",");
			if (values.length < 5)
			{
				LogUtil.debug(GroupWriter.SORTTAG + "节点的" + GroupWriter.SORTATT
						+ "属性值" + attString + "不正确");
				return null;
			}
			String nodeidstring = values[0];
			String language = values[1];
			String caseorderstring = values[2];
			String orderstring = values[4];
			BindNode sortnode = wsdhandler.getNode(nodeidstring);
			try
			{
				int caseorder = Integer.parseInt(caseorderstring);
				int datatype = Integer.parseInt(caseorderstring);
				int order = Integer.parseInt(orderstring);
				Sort sort = Sort.instance(sortnode, language, datatype, order,
						caseorder);
				if (sort != null)
				{
					return sort;
				} else
				{
					LogUtil.debug(GroupWriter.SORTTAG + "节点的"
							+ GroupWriter.SORTATT + "属性值" + attString + "不正确");
					return null;
				}
			} catch (NumberFormatException e)
			{
				LogUtil.debug(GroupWriter.SORTTAG + "节点的" + GroupWriter.SORTATT
						+ "属性值" + attString + "不正确", e);
				return null;
			}

		} else
		{
			LogUtil.debug(GroupWriter.SORTTAG + "节点必须有" + GroupWriter.SORTATT
					+ "属性");
			return null;
		}

	}

	private void creatGroupAtt(Attributes atts)
	{
		String nodeidstring = atts.getValue(GroupWriter.NODEID);
		node = wsdhandler.getNode(nodeidstring);
		if (node == null)
		{
			LogUtil.debug(GroupWriter.GROUPTAG + "节点必须有" + GroupWriter.NODEID
					+ "属性");
		}
		String logicalstring = atts.getValue(GroupWriter.FLITERCONDITION);
		fliterCondition = wsdhandler.getLogicalExp(logicalstring);
		String editmodestring = atts.getValue(GroupWriter.EDITMODE);
		editmode = null;
		if (editmodestring != null)
		{
			int enumkey = enumPropertyReader.getEnumKey(editmodestring);
			editmode = new EnumProperty(enumkey, editmodestring);
		}
		String maxstring = atts.getValue(GroupWriter.MAX);
		max = null;
		if (maxstring != null)
		{
			max = getEnumNumber(maxstring);
		}
	}

	private EnumNumber getEnumNumber(String enumnumstring)
	{
		try
		{
			int d = Integer.parseInt(enumnumstring);
			return new EnumNumber(-1, d);
		} catch (NumberFormatException e)
		{
			int enumvalue = enumPropertyReader.getEnumKey(enumnumstring);
			if (enumvalue > -1)
			{
				return new EnumNumber(enumvalue, -1);
			} else
			{
				LogUtil.debug("EnumNumber类型的属性字符串不可解析，属性字符串为：" + enumnumstring);
			}
		}
		return null;
	}

}
