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
 * @SideRegion.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-26
 */
public abstract class SideRegion extends Region
{
	private Length _extent;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public SideRegion(Length extent, SimplePageMaster layoutMaster,
			CommonBorderPaddingBackground commonBorderPaddingBackground,
			int displayAlign, int overflow, String regionName,
			int referenceOrientation, int writingMode)
	{
		super(layoutMaster, commonBorderPaddingBackground, displayAlign,
				overflow, referenceOrientation, writingMode, regionName);
		_extent = extent;
	}

	public Length getExtent()
	{
		if(_extent == null) 
		{
			return (Length) InitialManager.getInitialValue(Constants.PR_EXTENT, null);
		}
		return _extent;
	}

}
