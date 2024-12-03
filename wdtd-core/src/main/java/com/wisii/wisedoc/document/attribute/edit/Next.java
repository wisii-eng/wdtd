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
 * @Next.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.edit;

/**
 * 类功能描述：级联属性类
 *
 * 作者：zhangqiang
 * 创建日期：2009-12-15
 */
public final class Next
{
	//级联的下拉列表的名称
	private String name;
	//当前下拉列表的级联列号
	private int column;
	//级联的下拉列表的列号
	private int nextcolumn;

	public Next(String name,int column,int nextcolumn)
	{
		this.column = column;
		this.name = name;
		this.nextcolumn = nextcolumn;
	}

	/**
	 * @返回  name变量的值
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * @返回  column变量的值
	 */
	public final int getColumn()
	{
		return column;
	}

	/**
	 * @返回  nextcolumn变量的值
	 */
	public final int getNextcolumn()
	{
		return nextcolumn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nextcolumn;
		return result;
	}

	/* (non-Javadoc)
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
		Next other = (Next) obj;
		if (column != other.column)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nextcolumn != other.nextcolumn)
			return false;
		return true;
	}
}
