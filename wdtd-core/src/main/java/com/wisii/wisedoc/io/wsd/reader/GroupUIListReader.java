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
 * @GroupUIListReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.io.wsd.attribute.GroupUIWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-10-22
 */
public class GroupUIListReader extends AbstractHandler
{
	private List<GroupUI> groupuiList = new ArrayList<GroupUI>();

	void init()
	{
		groupuiList.clear();
	}

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (GroupUIWriter.GROUPUI.equals(qname))
		{
			String name = atts.getValue(GroupUIWriter.NAME);
			if (name == null)
			{
				groupuiList.add(null);
				return;
			}
			Integer max = null;
			Integer min = null;
			try
			{
				String maxstring = atts.getValue(GroupUIWriter.MAX);
				max = Integer.parseInt(maxstring);
				String minstring = atts.getValue(GroupUIWriter.MIN);
				min = Integer.parseInt(minstring);

			} catch (NumberFormatException e)
			{
			}
			Map<Integer, Object> groupuiatt = new HashMap<Integer, Object>();
			if (max != null)
			{
				groupuiatt.put(Constants.PR_GROUP_MAX_SELECTNUMBER, max);
			}
			if (min != null)
			{
				groupuiatt.put(Constants.PR_GROUP_MIN_SELECTNUMBER, min);
			}
			String noneselectvalue = atts
					.getValue(GroupUIWriter.NONESELECTEDVALUE);
			if (noneselectvalue != null)
			{
				groupuiatt.put(Constants.PR_GROUP_NONE_SELECT_VALUE,
						noneselectvalue);
			}
			GroupUI groupui = new GroupUI(new EnumProperty(Constants.EN_GROUP,
					""), groupuiatt);
			groupui.setName(name);
			String conwithid = atts.getValue(GroupUIWriter.CONWITHID);
			ConnWith conwith = wsdhandler.getConnWith(conwithid);
			if (conwith != null)
			{
				groupui.setConnwith(conwith);
			}
			groupuiList.add(groupui);
		} else
		{
			throw new SAXException("dynamicstyles 节点下的" + qname + "节点为非法");
		}
	}

	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
	}

	public List<GroupUI> getObject()
	{
		return groupuiList;
	}

}
