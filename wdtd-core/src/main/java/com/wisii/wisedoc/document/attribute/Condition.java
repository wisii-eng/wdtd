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
 * @Condition.java
 *                 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.banding.BindNode;

/**
 * 类功能描述：原子条件,原子条件为一个二元的表达式， 有两个条件变量(其中一个变量必须是动态数据节点)和一个条件操作符
 * 
 * 作者：zhangqiang 创建日期：2008-11-6
 */
public final class Condition implements Cloneable
{

	// 数字大于
	public final static int GREATER = 1;

	// 数字等于
	public final static int EQUAL = 2;

	// 数字小于
	public final static int LESS = 3;

	// 字符串等于
	public final static int STRINGEQUAL = 4;

	// 正则表达式相等
	public final static int REGULAREQUAL = 5;

	// 字符串不等
	public final static int STRINGNOTEQUAL = 6;

	// 数字大于等于
	public final static int GREATEREQUAL = 7;

	// 数字小于等于
	public final static int LESSEQUAL = 8;

	// 数字不等
	public final static int NUMBERNOTEQUAL = 9;

	// 第一条数据
	public final static int FIRST = 10;

	// 非第一条数据
	public final static int NOTFIRST = 11;

	// 最后一条数据
	public final static int LAST = 12;

	// 非最后一条数据
	public final static int NOTLAST = 13;

	// 奇数条数据
	public final static int ODD = 14;

	// 偶数条数据
	public final static int EVEN = 15;

	// 当前序号小于
	public final static int POSITIONLESS = 16;

	// 当前序号大于
	public final static int POSITIONGREATER = 17;

	// 序号为
	public final static int POSITION = 18;
	// 当前组环境下的节点条数
	public final static int LENGTHLESS = 19;

	public final static int LENGTHGREATER = 20;
	// 总条数为
	public final static int COUNT = 21;
	// 条数小于
	public final static int COUNTLESS = 22;
	// 条数大于
	public final static int COUNTGREATER = 23;
	// 总条数为奇数
	public final static int COUNTODD = 24;
	// 总条数为偶数
	public final static int COUNTEVEN = 25;
	// 序号整除3余数为
	public final static int POSITIONMOD3 = 26;
	// 序号整除4余数为
	public final static int POSITIONMOD4 = 27;
	// 序号整除5余数为
	public final static int POSITIONMOD5 = 28;
	// 序号整除6余数为
	public final static int POSITIONMOD6 = 29;
	// 序号整除7余数为
	public final static int POSITIONMOD7 = 30;
	// 序号整除8余数为
	public final static int POSITIONMOD8 = 31;
	// 序号整除9余数为
	public final static int POSITIONMOD9 = 32;
	
	// 条数整除3余数为
	public final static int COUNTMOD3 = 33;
	// 条数整除4余数为
	public final static int COUNTMOD4 = 34;
	// 条数整除5余数为
	public final static int COUNTMOD5 = 35;
	// 条数整除6余数为
	public final static int COUNTMOD6 = 36;
	// 条数整除7余数为
	public final static int COUNTMOD7 = 37;
	// 条数整除8余数为
	public final static int COUNTMOD8 = 38;
	// 条数整除9余数为
	public final static int COUNTMOD9 = 39;
	//条数操作符数目
	private final static int TYPECOUNT = 39;

	// 动态数据节点
	private BindNode node;

	// 条件操作符
	private int type;

	// 第二个条件变量
	private Object param;

	/**
	 * @返回 node变量的值
	 */
	public BindNode getNode()
	{
		return node;
	}

	/**
	 * @返回 type变量的值
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @返回 param变量的值
	 */
	public Object getParam()
	{
		return param;
	}

	private Condition(BindNode node, Object param, int type)
	{
		this.node = node;
		this.param = param;
		this.type = type;
	}

	public static Condition instance(BindNode node, Object param, int type)
	{
		if (type > 0 && type < (TYPECOUNT + 1))
		{
			if (type == FIRST || type == NOTFIRST || type == LAST
					|| type == NOTLAST || type == ODD || type == EVEN)
			{
				return new Condition(null, null, type);
			} else if (type == POSITIONLESS || type == POSITIONGREATER
					|| type == POSITION||(type>=POSITIONMOD3&&type<=COUNTMOD9))
			{
				if (param != null && (param instanceof String)
						&& (!param.equals("")))
				{
					if (isNumber((String) param))
					{
						return new Condition(null, param, type);
					}
				}
			} else if (type == COUNT || type == COUNTLESS
					|| type == COUNTGREATER)
			{
				if (node != null && param != null && (param instanceof String)
						&& (!param.equals("")) && isNumber((String) param))
				{
					return new Condition(node, param, type);
				}
			} else if (type == COUNTODD || type == COUNTEVEN)
			{
				if (node != null)
				{
					return new Condition(node, null, type);
				}
			} else if (node != null
					&& param != null
					&& ((param instanceof String) || (param instanceof BindNode)))
			{
				return new Condition(node, param, type);
			}
		}
		return null;
	}

	public static boolean isNumber(String number)
	{
		boolean flg = true;
		int length = number.length();
		for (int i = 0; i < length; i++)
		{
			char current = number.charAt(i);
			if (current < '0' || current > '9')
			{
				return false;
			}
		}
		if (length > 1 && number.startsWith("0"))
		{
			return false;
		}
		return flg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		result = prime * result + ((param == null) ? 0 : param.hashCode());
		result = prime * result + type;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Condition other = (Condition) obj;
		if (node == null)
		{
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		if (param == null)
		{
			if (other.param != null)
				return false;
		} else if (!param.equals(other.param))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public Condition clone()
	{
		return new Condition(node, param, type);
	}

	private final static String[] t =
	{ "数字大于", "数字等于", "数字小于", "文本等于", "正则表达式相等", "文本不等", "数字大于等于", "数字小于等于",
			"数字不等", "第一条数据", "非第一条数据", "最后一条数据", "非最后一条数据", "奇数条数据", "偶数条数据",
			"数据条数小于", "数据条数大于", "当前数据条数为", "字符数小于", "字符数大于" };

	@Override
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		if (node != null)
		{
			s.append(node.toString());
		}
		s.append(t[type - 1]);
		if (param != null)
		{
			s.append(param.toString());
		}
		return s.toString();
	}
}
