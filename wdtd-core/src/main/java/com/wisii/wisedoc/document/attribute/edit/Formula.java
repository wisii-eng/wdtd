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
 * @Formula.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.edit;

import com.wisii.wisedoc.banding.BindNode;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-8-29
 */
public final class Formula
{
	private  BindNode xpath;
	private  String expression;

	public Formula(String expression, BindNode xpath)
	{
		this.expression = expression;
		this.xpath = xpath;
	}

	/**
	 * @返回 xpath变量的值
	 */
	public final BindNode getXpath()
	{
		return xpath;
	}

	/**
	 * @返回 expression变量的值
	 */
	public final String getExpression()
	{
		return expression;
	}

	/**
	 * @param xpath 设置xpath成员变量的值
	
	 * 值约束说明
	
	 */
	public final void setXpath(BindNode xpath)
	{
		this.xpath = xpath;
	}

	/**
	 * @param expression 设置expression成员变量的值
	
	 * 值约束说明
	
	 */
	public final void setExpression(String expression)
	{
		this.expression = expression;
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
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + ((xpath == null) ? 0 : xpath.hashCode());
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
		Formula other = (Formula) obj;
		if (expression == null)
		{
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (xpath == null)
		{
			if (other.xpath != null)
				return false;
		} else if (!xpath.equals(other.xpath))
			return false;
		return true;
	}

}
