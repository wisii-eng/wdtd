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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChartDataList extends ArrayList<ChartData2D>
{

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public ChartDataList()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public ChartDataList(int initialCapacity)
	{
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public ChartDataList(Collection<? extends ChartData2D> c)
	{
		super(c);
		// TODO Auto-generated constructor stub
	}

	public List<ChartData1D> get1dChartdata(int index2d)
	{
		List<ChartData1D> chartdata1dlist = new ArrayList<ChartData1D>();
		for (ChartData2D chartdata2d : this)
		{
			if (chartdata2d.getIndex2d() == index2d)
			{
				chartdata1dlist.add(chartdata2d);
			}
		}
		return chartdata1dlist;
	}

	public Double getNumberContent(int index1d, int index2d)
	{
		for (ChartData2D chartdata2d : this)
		{
			if (chartdata2d.getIndex1d() == index1d
					&& chartdata2d.getIndex2d() == index2d)
			{
				return chartdata2d.getNumbercontent();
			}
		}
		return null;
	}
}
