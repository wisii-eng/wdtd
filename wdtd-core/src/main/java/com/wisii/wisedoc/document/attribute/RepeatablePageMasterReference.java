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
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.wisii.com/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: RepeatablePageMasterReference.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.Constants;

/**
 * 
 * 类功能描述：重复页布局引用类
 * 
 * 作者：zhangqiang 创建日期：2008-8-26
 */
public class RepeatablePageMasterReference implements SubSequenceSpecifier
{

	// The value of properties relevant for fo:repeatable-page-master-reference.
	private SimplePageMaster _masterReference;

	private int _maximumRepeats = INFINITE;

	private static final int INFINITE = -1;

	private int numberConsumed = 0;

	public RepeatablePageMasterReference(EnumNumber maximumRepeats,
			SimplePageMaster masterReference)
	{
		if (maximumRepeats != null)
		{
			if (maximumRepeats.getEnum() == Constants.EN_NO_LIMIT)
			{
				_maximumRepeats = INFINITE;
			} else
			{
				_maximumRepeats = maximumRepeats.getNumber().intValue();
			}
		}
		_masterReference = masterReference;
	}

	/**
	 * @see com.wisii.fov.fo.pagination.SubSequenceSpecifier
	 */
	public SimplePageMaster getNextPageMaster(boolean isOddPage,
			boolean isFirstPage, boolean isLastPage, boolean isEmptyPage)
	{
		if (getMaximumRepeats() != INFINITE)
		{
			if (numberConsumed < getMaximumRepeats())
			{
				numberConsumed++;
			} else
			{
				return null;
			}
		}
		return _masterReference;
	}

	/** @return the "maximum-repeats" property. */
	public int getMaximumRepeats()
	{
		if (_maximumRepeats == Constants.EN_NO_LIMIT)
		{
			return INFINITE;
		}
		return _maximumRepeats;

	}

	public SimplePageMaster getMasterReference()
	{
		return _masterReference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.attribute.SubSequenceSpecifier#reset()
	 */
	public void reset()
	{
		this.numberConsumed = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.attribute.SubSequenceSpecifier#goToPrevious()
	 */
	public boolean goToPrevious()
	{
		if (numberConsumed == 0)
		{
			return false;
		} else
		{
			numberConsumed--;
			return true;
		}
	}

	/** @see com.wisii.fov.fo.pagination.SubSequenceSpecifier#hasPagePositionLast() */
	public boolean hasPagePositionLast()
	{
		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof RepeatablePageMasterReference))
		{
			return false;
		}
		RepeatablePageMasterReference rpmr = (RepeatablePageMasterReference) obj;
		return rpmr._maximumRepeats == this._maximumRepeats
				&& rpmr._masterReference.equals(this._masterReference);
	}

	public RepeatablePageMasterReference clone()
	{
		SimplePageMaster masterReference = this.getMasterReference().clone();
		int maximumRepeats = this.getMaximumRepeats();
		EnumNumber rnumber = new EnumNumber(-1, maximumRepeats);
		return new RepeatablePageMasterReference(rnumber, masterReference);

	}
}
