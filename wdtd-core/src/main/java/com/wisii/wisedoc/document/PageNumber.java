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
 * @PageNumber.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.CommonTextDecoration;

/**
 * 类功能描述：页码类
 * 
 * 作者：zhangqiang 创建日期：2008-9-1
 */
public class PageNumber extends Inline {
	private CommonTextDecoration commontextdecoration;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public PageNumber() {
		this(null);
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public PageNumber(final Map<Integer,Object> attributes) {
		super(attributes);
	}
	public void initFOProperty()
	{
		super.initFOProperty();
		if(commontextdecoration == null)
		{
			commontextdecoration = new CommonTextDecoration(this);
		}
		else
		{
			commontextdecoration.init(this);
		}
	}
	/** @return the "text-decoration" property. */
	public CommonTextDecoration getTextDecoration() {
		return commontextdecoration;
	}

	/** @see com.wisii.fov.fo.FONode#getLocalName() */
	public String getLocalName() {
		return "page-number";
	}

	/** @see com.wisii.fov.fo.FObj#getNameId() */
	public int getNameId() {
		return Constants.FO_PAGE_NUMBER;
	}
}
