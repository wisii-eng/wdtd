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
 * @AttributeBindNode.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding;

import java.util.Map;

/**
 * 类功能描述：
 * 
 * 作者：李晓光 创建日期：2008-9-10
 */
public class AttributeBindNode extends DefaultBindNode
{
	protected AttributeBindNode(Map<Integer, Object> attributes)
	{
		super(attributes);
	}
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public AttributeBindNode(BindNode parent, int datatype, int length,
			String nodeName)
	{
		super(parent, datatype, length, nodeName);
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public AttributeBindNode(BindNode parent, int datatype, int length,
			String nodeName, Map<Integer, Object> otherattr)
	{
		super(parent, datatype, length, nodeName, otherattr);
		// TODO Auto-generated constructor stub
	}

	protected String getXPATH()
	{
		if (getParent() != null)
		{
			return ((DefaultBindNode) getParent()).getXPATH() + "/@" + nodeName;
		} else
		{
			return toString();
		}
	}

	@Override
	protected String getNameWithoutNameSpace()
	{
		return "@"+super.getNameWithoutNameSpace();
	}
	@Override
	protected DefaultBindNode Clone()
	{
		// TODO Auto-generated method stub
		DefaultBindNode node= new AttributeBindNode(_attributes);
		node.nodeName=nodeName;
		return node;
	}
}
