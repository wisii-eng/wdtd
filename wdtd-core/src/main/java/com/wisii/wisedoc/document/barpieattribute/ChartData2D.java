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

package com.wisii.wisedoc.document.barpieattribute;

public class ChartData2D extends ChartData1D
{

	// 二维索引号，即行号
	private int index2d = -1;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public ChartData2D(int i1d, int index2d, double data)
	{
		super(i1d, data);
		this.index2d = index2d;
	}

	/**
	 * @返回 index2d变量的值
	 */
	public final int getIndex2d()
	{
		return index2d;
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
		int result = super.hashCode();
		result = prime * result + index2d;
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChartData2D other = (ChartData2D) obj;
		if (index2d != other.index2d)
			return false;
		return true;
	}

	@Override
	public int compareTo(ChartData1D o)
	{
		ChartData2D chartdata2d = (ChartData2D) o;
		if (index2d > chartdata2d.index2d)
		{
			return 1;
		} else if (index2d < chartdata2d.index2d)
		{
			return -1;
		} else
		{
			return super.compareTo(o);
		}
	}
}
