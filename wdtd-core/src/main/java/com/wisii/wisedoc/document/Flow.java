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
 * @Flow.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.Map;

import com.wisii.wisedoc.area.RegionReference;

/**
 * 类功能描述：pagesequence的主流
 *
 * 作者：zhangqiang
 * 创建日期：2008-8-19
 */
public class Flow extends DefaultElement
{
	private static final String DEFAULTNAME = "xsl-region-body";
	/* 【添加：START】 by 李晓光 2008-11-15 */
	private RegionReference area = null;
	public RegionReference getArea() {
		return area;
	}

	public void setArea(RegionReference area) {
		this.area = area;
	}

	/* 【添加：END】 by 李晓光 2008-11-15 */
	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public Flow()
	{
		this(DEFAULTNAME);
	}

	protected Flow(String name)
	{
		if (name == null)
		{
			name = DEFAULTNAME;
		}
		setAttribute(Constants.PR_FLOW_NAME, name);
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public Flow(final Map<Integer,Object> attributes)
	{
		super(attributes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return true (Flow can generate reference areas)
	 */
	public boolean generatesReferenceAreas()
	{
		return true;
	}

	/**
	 * 
	 * 返回Flow名
	 *
	 * @param     
	 * @return     
	 * @exception  
	 */
	public String getFlowName()
	{
		return (String) getAttribute(Constants.PR_FLOW_NAME);
	}

	public int getNameId()
	{
		return Constants.FO_FLOW;
	}
}
