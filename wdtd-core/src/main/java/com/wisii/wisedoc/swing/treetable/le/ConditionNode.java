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
 * @ConditionNode.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.treetable.le;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.Condition;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-3
 */
public class ConditionNode extends AbstractLogicalExpressionNode
{
	BindNode node;
	Object param;
	Integer type;

	ConditionNode(Condition condition)
	{
      this(condition.getNode(), condition.getParam(), condition.getType());
	}

	public ConditionNode(BindNode node, Object param, Integer type)
	{
		this.node = node;
		this.param = param;
		this.type = type;
	}

	/**
	 * @返回 node变量的值
	 */
	public BindNode getNode()
	{
		return node;
	}

	/**
	 * @param node
	 *            设置node成员变量的值
	 * 
	 *            值约束说明
	 */
	public void setNode(BindNode node)
	{
		this.node = node;
	}

	/**
	 * @返回 param变量的值
	 */
	public Object getParam()
	{
		return param;
	}

	/**
	 * @param param
	 *            设置param成员变量的值
	 * 
	 *            值约束说明
	 */
	public void setParam(Object param)
	{
		this.param = param;
	}

	/**
	 * @返回 type变量的值
	 */
	public Integer getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            设置type成员变量的值
	 * 
	 *            值约束说明
	 */
	public void setType(Integer type)
	{
		this.type = type;
		if (type == Condition.FIRST || type == Condition.NOTFIRST
				|| type == Condition.LAST || type == Condition.NOTLAST
				|| type == Condition.ODD || type == Condition.EVEN)
		{
			node = null;
			param = null;
		} else if (type == Condition.POSITIONLESS
				|| type == Condition.POSITIONGREATER
				|| type == Condition.POSITION)
		{
			node = null;
		} else if (type == Condition.COUNTEVEN || type == Condition.COUNTODD)
		{
			param = null;
		}
		else if(type >= Condition.POSITIONMOD3
				&& type <= Condition.COUNTMOD9)
		{
			node=null;
			if(param instanceof String)
			{
				try
				{
					Integer.parseInt((String) param);
				} catch (NumberFormatException e)
				{
					param=null;
				}
			}
			else
			{
				param=null;
			}
		}
	}
	public String toString()
	{
		String text = "单一条件";
		if (parent != null)
		{
			text = text + parent.getIndex(this);
		}
		return text;
	}
}
