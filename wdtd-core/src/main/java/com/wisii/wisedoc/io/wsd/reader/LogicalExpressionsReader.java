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
 * @LogicalExpressionsReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.wsd.attribute.LogicalExpressionWriter;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：LogicalExpression属性reader
 * 
 * 作者：zhangqiang 创建日期：2008-11-13
 */
public class LogicalExpressionsReader extends AbstractHandler
{
	private List<LogicalExpression> logexps = new ArrayList<LogicalExpression>();
	private Stack<List> logexpstacks = new Stack<List>();
	private Stack<List<Integer>> typesstacks = new Stack<List<Integer>>();
	private List<LogicalExpression> sublogexps = new ArrayList<LogicalExpression>();

	// private List<Integer> subtypes = new ArrayList<Integer>();
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#startElement(java.lang
	 * .String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (qname.equals(LogicalExpressionWriter.LOGICALTAG))
		{
			logexpstacks.push(new ArrayList());
			typesstacks.push(new ArrayList<Integer>());
		} else if (qname.equals(LogicalExpressionWriter.CONDITIONTAG))
		{
			Condition cond = getCondition(atts);
			if(cond!=null){
			logexpstacks.peek().add(getCondition(atts));
			}
		} else if (qname.equals(LogicalExpressionWriter.TYPE))
		{
			int type = getType(atts);
			typesstacks.peek().add(type);
		} else
		{
			throw new SAXException("无效节点：" + qname);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.wsd.reader.AbstractHandler#endElement(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (qname.equals(LogicalExpressionWriter.LOGICALTAG))
		{
			List conditionslist = logexpstacks.pop();
			List<Integer> typelist = typesstacks.pop();
			LogicalExpression logical = LogicalExpression.instance(
					conditionslist, typelist);
			if (!logexpstacks.isEmpty())
			{
				if (logical != null)
				{
					logexpstacks.peek().add(logical);
				}
			} else
			{
				logexps.add(logical);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractHandler#getObject()
	 */
	public List<LogicalExpression> getObject()
	{
		// TODO Auto-generated method stub
		return logexps;
	}

	private Condition getCondition(Attributes atts)
	{
		String nodestring = atts.getValue(LogicalExpressionWriter.DATANODEID);
		BindNode node = null;
		if (nodestring != null)
		{
			node = wsdhandler.getNode(nodestring);
		}
		String paramstring = atts.getValue(LogicalExpressionWriter.PARAM);
		String paramidstring = atts.getValue(LogicalExpressionWriter.PARAMID);
		Object param = null;
		if (paramstring != null)
		{
			param = XMLUtil.fromXmlText(paramstring);
		} else if (paramidstring != null)
		{
			param = wsdhandler.getNode(paramidstring);
		}
		// else
		// {
		// throw new SAXException(LogicalExpressionWriter.CONDITIONTAG
		// + "节点的必须有至少得有以下两个属性之一：" + LogicalExpressionWriter.PARAMID
		// + "，" + LogicalExpressionWriter.PARAM);
		// }
		int type = 0;
		String typestring = atts.getValue(LogicalExpressionWriter.TYPE);
		try
		{
			type = Integer.parseInt(typestring);
		} catch (NumberFormatException e)
		{
			LogUtil.debug(LogicalExpressionWriter.CONDITIONTAG + "节点 的："
					+ LogicalExpressionWriter.TYPE + "属性值：" + typestring
					+ "不合法", e);
		}
		Condition condition = Condition.instance(node, param, type);
		if (condition == null)
		{
			LogUtil.debug(LogicalExpressionWriter.CONDITIONTAG + "node" + node
					+ ":param:" + param + "节点 的："
					+ LogicalExpressionWriter.TYPE + "属性值：" + typestring
					+ "不合法");
			return null;
		}
		return condition;
	}

	private int getType(Attributes atts) throws SAXException
	{
		String value = atts.getValue(LogicalExpressionWriter.TYPEVALUE);
		if (value != null)
		{
			try
			{
				int type = Integer.parseInt(value.trim());
				return type;
			} catch (NumberFormatException e)
			{
				throw new SAXException(LogicalExpressionWriter.TYPE + "节点 的："
						+ LogicalExpressionWriter.TYPEVALUE + "属性值：" + value
						+ "不合法", e);
			}
		} else
		{
			throw new SAXException(LogicalExpressionWriter.TYPE + "节点缺少"
					+ LogicalExpressionWriter.TYPEVALUE + "属性");
		}
	}

}
