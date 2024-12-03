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
 * SinglePageMasterReference.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

/**
 * 
 * 类功能描述：单页布局引用声明类
 * 
 * 作者：zhangqiang 创建日期：2008-8-26
 */
public class SinglePageMasterReference implements SubSequenceSpecifier
{

	// 引用的页布局.
	private SimplePageMaster _masterReference;

	private static final int FIRST = 0;

	private static final int DONE = 1;

	private int state;

	public SinglePageMasterReference(SimplePageMaster masterReference)
	{
		_masterReference = masterReference;
	}

	/** @see com.wisii.fov.fo.pagination.SubSequenceSpecifier */
	public SimplePageMaster getNextPageMaster(boolean isOddPage,
			boolean isFirstPage, boolean isLastPage, boolean isEmptyPage)
	{
		if (this.state == FIRST)
		{
			this.state = DONE;
			return _masterReference;
		} else
		{
			return null;
		}
	}

	/** @see com.wisii.fov.fo.pagination.SubSequenceSpecifier#reset() */
	public void reset()
	{
		this.state = FIRST;
	}

	/** @see com.wisii.fov.fo.pagination.SubSequenceSpecifier#goToPrevious() */
	public boolean goToPrevious()
	{
		if (state == FIRST)
		{
			return false;
		} else
		{
			this.state = FIRST;
			return true;
		}
	}

	/** @see com.wisii.fov.fo.pagination.SubSequenceSpecifier#hasPagePositionLast() */
	public boolean hasPagePositionLast()
	{
		return false;
	}

	public SimplePageMaster getMasterReference()
	{
		return _masterReference;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof SinglePageMasterReference))
		{
			return false;
		}
		SinglePageMasterReference spmr = (SinglePageMasterReference) obj;
		return spmr._masterReference.equals(this._masterReference);
	}

	public SinglePageMasterReference clone()
	{
		SimplePageMaster masterReference = this.getMasterReference().clone();
		return new SinglePageMasterReference(masterReference);
	}
}
