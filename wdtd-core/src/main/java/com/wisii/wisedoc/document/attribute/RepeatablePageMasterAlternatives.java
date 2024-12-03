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

/* $Id: RepeatablePageMasterAlternatives.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.attribute;

// Java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wisii.wisedoc.document.Constants;

/**
 * 
 * 类功能描述：条件页布局声明类
 * 
 * 作者：zhangqiang 创建日期：2008-8-26
 */
public class RepeatablePageMasterAlternatives implements SubSequenceSpecifier
{

	public static final int INFINITE = -1;

	// 最大重复次数
	private int _maximumRepeats = INFINITE;

	private int numberConsumed = 0;

	// 条件页布局
	private List<ConditionalPageMasterReference> _conditionalPageMasterRefs;

	private boolean hasPagePositionLast = false;

	public RepeatablePageMasterAlternatives(EnumNumber maximumRepeats,
			List<ConditionalPageMasterReference> conditionalPageMasterRefs)
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
		_conditionalPageMasterRefs = conditionalPageMasterRefs;
		if (conditionalPageMasterRefs != null)
		{
			Iterator<ConditionalPageMasterReference> it = conditionalPageMasterRefs
					.iterator();
			while (it.hasNext())
			{
				ConditionalPageMasterReference cpr = it.next();
				if (cpr.getPagePosition() == Constants.EN_LAST)
				{
					this.hasPagePositionLast = true;
					break;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.attribute.SubSequenceSpecifier#getNextPageMaster
	 * (boolean, boolean, boolean, boolean)
	 */
	public SimplePageMaster getNextPageMaster(boolean isOddPage,
			boolean isFirstPage, boolean isLastPage, boolean isBlankPage)
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
		} else
		{
			numberConsumed++;
		}

		for (int i = 0; i < _conditionalPageMasterRefs.size(); i++)
		{
			ConditionalPageMasterReference cpmr = _conditionalPageMasterRefs
					.get(i);
			if (cpmr.isValid(isOddPage, isFirstPage, isLastPage, isBlankPage))
			{
				return cpmr.getMasterReference();
			}
		}
		return null;
	}

	/** @see com.wisii.fov.fo.pagination.SubSequenceSpecifier#reset() */
	public void reset()
	{
		this.numberConsumed = 0;
	}

	/** @see com.wisii.fov.fo.pagination.SubSequenceSpecifier#goToPrevious() */
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
		return this.hasPagePositionLast;
	}

	/** @return the "maximum-repeats" property. */
	public int getMaximumRepeats()
	{
		return _maximumRepeats;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof RepeatablePageMasterAlternatives))
		{
			return false;
		}
		RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) obj;
		return this._maximumRepeats == rpma._maximumRepeats
				&& rpma._conditionalPageMasterRefs
						.equals(this._conditionalPageMasterRefs);
	}

	public List<ConditionalPageMasterReference> getPageMasterRefs()
	{
		return new ArrayList<ConditionalPageMasterReference>(
				_conditionalPageMasterRefs);
	}

	public RepeatablePageMasterAlternatives clone()
	{
		int maximumRepeats = this.getMaximumRepeats();
		EnumNumber rnumber = new EnumNumber(-1, maximumRepeats);
		List<ConditionalPageMasterReference> conditionalPageMasterRefs = new ArrayList<ConditionalPageMasterReference>();
		if (this._conditionalPageMasterRefs != null
				&& !_conditionalPageMasterRefs.isEmpty())
		{
			for (ConditionalPageMasterReference current : this._conditionalPageMasterRefs)
			{
				ConditionalPageMasterReference currentnew = current.clone();
				conditionalPageMasterRefs.add(currentnew);
			}
		}
		return new RepeatablePageMasterAlternatives(rnumber,
				conditionalPageMasterRefs);
	}
}
