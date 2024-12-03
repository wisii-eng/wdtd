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
 * @Data1D.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.NumberContent;

/**
 * 类功能描述：带索引标识的一维数据
 *
 * 作者：zhangqiang
 * 创建日期：2009-5-21
 */
public class ChartData1D implements Comparable<ChartData1D>
{
	// 一维索引,即列号
	private int index1d = -1;
	// 数据内容
	private NumberContent numbercontent;

	public ChartData1D(int index, NumberContent numbercontent)
	{
		index1d = index;
		this.numbercontent = numbercontent;
	}

	@Override
	public String toString()
	{
		return index1d +":"+numbercontent.getText();
	}

	/**
	 * @返回 index1d变量的值
	 */
	public final int getIndex1d()
	{
		return index1d;
	}

	/**
	 * @返回 numbercontent变量的值
	 */
	public final NumberContent getNumbercontent()
	{
		return numbercontent;
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
		result = prime * result + index1d;
		result = prime * result
				+ ((numbercontent == null) ? 0 : numbercontent.hashCode());
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
		ChartData1D other = (ChartData1D) obj;
		if (index1d != other.index1d)
			return false;
		if (numbercontent == null)
		{
			if (other.numbercontent != null)
				return false;
		} else if (!numbercontent.equals(other.numbercontent))
			return false;
		return true;
	}

	@Override
	public int compareTo(ChartData1D o)
	{
		return (index1d<o.index1d ? -1 : (index1d==o.index1d ? 0 : 1));
	}
}