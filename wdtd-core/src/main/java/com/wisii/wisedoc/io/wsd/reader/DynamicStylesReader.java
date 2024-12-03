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
 * @ConditionItemCollectionReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.ConditionItemCollection;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.io.wsd.attribute.ConditionItemCollectionWriter;
/**
 * 类功能描述：动态样式代码读取类
 *
 * 作者：zhangqiang
 * 创建日期：2009-3-24
 */
public class DynamicStylesReader extends AbstractHandler
{
	private List<ConditionItemCollection> dynamicstyles = new ArrayList<ConditionItemCollection>();
	List<ConditionItem> conditionitems = new ArrayList<ConditionItem>();

	void init()
	{
		dynamicstyles.clear();
		conditionitems.clear();
	}

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (ConditionItemCollectionWriter.DYNAMICSTYLETAG.equals(qname))
		{
			// do nothing
		} else if (ConditionItemCollectionWriter.DYNAMICSTYLEITEMAG
				.equals(qname))
		{
			ConditionItem conit = getDyConditionItem(atts);
			conditionitems.add(conit);
		} else
		{
			throw new SAXException("dynamicstyles 节点下的" + qname + "节点为非法");
		}
	}

	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (ConditionItemCollectionWriter.DYNAMICSTYLETAG.equals(qname))
		{
			ConditionItemCollection dynamicstyle = new ConditionItemCollection();
			dynamicstyle.addAll(conditionitems);
			conditionitems.clear();
			dynamicstyles.add(dynamicstyle);
		}
	}

	public List<ConditionItemCollection> getObject()
	{
		return dynamicstyles;
	}

	private ConditionItem getDyConditionItem(Attributes atts)
	{
		String lestring = atts
				.getValue(ConditionItemCollectionWriter.CONDITIONID);
		String stylestring = atts
				.getValue(ConditionItemCollectionWriter.STYLEID);
		LogicalExpression le = wsdhandler.getLogicalExp(lestring);
		com.wisii.wisedoc.document.attribute.Attributes wsdatts = wsdhandler
				.getAttributes(stylestring);
		ConditionItem coniItem = null;
		if (le != null && wsdatts != null)
		{
			coniItem = new ConditionItem(le, wsdatts);
		}
		return coniItem;
	}

}
