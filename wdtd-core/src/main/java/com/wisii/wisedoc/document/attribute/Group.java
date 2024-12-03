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
 * @Group.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.banding.BindNode;

/**
 * 组属性
 * 
 * 作者：zhangqiang 创建日期：2007-7-4
 */
public final class Group implements Cloneable
{
	// 重复节点属性
	private final BindNode node;

	// 过滤条件属性，为条件表达式
	private final LogicalExpression fliterCondition;

	// 排序属性
	private List<Sort> sorts;
	private EnumProperty editmode;
//	最大重复次数
	EnumNumber max;

	private Group(BindNode node, LogicalExpression fliterCondition,
			List<Sort> sorts, EnumProperty editmode,EnumNumber max)
	{
		this.node = node;
		this.fliterCondition = fliterCondition;
		if (sorts != null && !sorts.isEmpty())
		{
			this.sorts = new ArrayList<Sort>();
			int size = sorts.size();
			for (int i = 0; i < size; i++)
			{
				Sort sort = sorts.get(i);
				if (sort != null)
				{
					this.sorts.add(sort);
				}
			}
		}
		this.editmode = editmode;
		this.max = max;
	}

	public static Group Instanceof(BindNode node,
			LogicalExpression fliterCondition, List<Sort> sorts,
			EnumProperty editmode,EnumNumber max)
	{
		Group group = null;
		if (node != null)
		{
			group = new Group(node, fliterCondition, sorts, editmode, max);
		}
		return group;
	}

	/**
	 * 
	 * 获得Group的节点信息
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public BindNode getNode()
	{
		return node;
	}

	public EnumProperty getEditmode()
	{
		return editmode;
	}
	/**
	 * @返回  max变量的值
	 */
	public EnumNumber getMax()
	{
		return max;
	}

	/**
	 * 
	 * 返回过滤条件
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public LogicalExpression getFliterCondition()
	{
		return fliterCondition;
	}

	/**
	 * 
	 * 返回排序设置
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public List<Sort> getSortSet()
	{
		if(sorts != null){
		return new ArrayList<Sort>(sorts);
		}
		else 
		{
			return sorts;
		}
	}

	/**
	 * 深克隆对象
	 */
	public Group clone()
	{
		List<Sort> sorts = null;
		if (sorts != null && sorts.size() > 0)
		{
			sorts = new ArrayList<Sort>();
			int size = sorts.size();
			for (int i = 0; i < size; i++)
			{
				sorts.add(sorts.get(i).clone());
			}
		}
		return new Group(node, fliterCondition, sorts, editmode,max);
	}

	/**
	 * 覆盖对象的比较属性方法
	 */
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof Group))
		{
			return false;
		}
		Group gobj = (Group) obj;
		return node.equals(gobj.node)
				&& (fliterCondition == null ? gobj.fliterCondition == null
						: fliterCondition.equals(gobj.fliterCondition))
				&& (sorts == null ? gobj.sorts == null : sorts
						.equals(gobj.sorts))
				&& (editmode == null ? gobj.editmode == null : editmode
						.equals(gobj.editmode))&& (max == null ? gobj.max == null : max
								.equals(gobj.max));
	}
}
