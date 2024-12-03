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

/* $Id: ConditionalPageMasterReference.java 426576 2006-07-28 15:44:37Z jeremias $ */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.document.Constants;

/**
 * 
 * 类功能描述：条件页布局属性类
 * 
 * 作者：zhangqiang 创建日期：2008-8-26
 */
public class ConditionalPageMasterReference
{

	private SimplePageMaster _masterReference;

	private int _pagePosition;

	private int _oddOrEven;

	private int _blankOrNotBlank;

	public ConditionalPageMasterReference(SimplePageMaster masterReference,
			int pagePosition, int oddOrEven, int blankOrNotBlank)
	{
		_masterReference = masterReference;
		_pagePosition = pagePosition;
		_oddOrEven = oddOrEven;
		_blankOrNotBlank = blankOrNotBlank;
	}

	/**
	 * Check if the conditions for this reference are met. checks the page
	 * number and emptyness to determine if this matches.
	 * 
	 * @param isOddPage
	 *            True if page number odd
	 * @param isFirstPage
	 *            True if page is first page
	 * @param isLastPage
	 *            True if page is last page
	 * @param isBlankPage
	 *            True if page is blank
	 * @return True if the conditions for this reference are met
	 */
	protected boolean isValid(boolean isOddPage, boolean isFirstPage,
			boolean isLastPage, boolean isBlankPage)
	{
		// page-position
		if (isFirstPage)
		{
			if (_pagePosition == Constants.EN_REST)
			{
				return false;
			} else if (_pagePosition == Constants.EN_LAST)
			{
				return false;
			}
		} else if (isLastPage)
		{
			if (_pagePosition == Constants.EN_REST)
			{
				return false;
			} else if (_pagePosition == Constants.EN_FIRST)
			{
				return false;
			}
		} else
		{
			if (_pagePosition == Constants.EN_FIRST)
			{
				return false;
			} else if (_pagePosition == Constants.EN_LAST)
			{
				return false;
			}
		}

		// odd-or-even
		if (isOddPage)
		{
			if (_oddOrEven == Constants.EN_EVEN)
			{
				return false;
			}
		} else
		{
			if (_oddOrEven == Constants.EN_ODD)
			{
				return false;
			}
		}

		// blank-or-not-blank
		if (isBlankPage)
		{
			if (_blankOrNotBlank == Constants.EN_NOT_BLANK)
			{
				return false;
			}
		} else
		{
			if (_blankOrNotBlank == Constants.EN_BLANK)
			{
				return false;
			}
		}
		return true;
	}

	/** @return the "master-reference" property. */
	public SimplePageMaster getMasterReference()
	{
		return _masterReference;
	}

	/** @return the page-position property value */
	public int getPagePosition()
	{
		return this._pagePosition;
	}

	public int getOddOrEven()
	{
		return _oddOrEven;
	}

	public int getBlankOrNotBlank()
	{
		return _blankOrNotBlank;
	}

	/**
	 * 
	 * 判断条件是否相等，以控制一个PageSequenceMaster中所有的该条件对象的条件都不一样
	 * 
	 * @param cpmr
	 *            ：
	 * @return
	 * @exception
	 */
	public boolean isConditionequals(ConditionalPageMasterReference cpmr)
	{
		if (cpmr == null)
		{
			return false;
		}
		return _blankOrNotBlank == cpmr._blankOrNotBlank
				&& _oddOrEven == cpmr._oddOrEven
				&& _pagePosition == cpmr._pagePosition;
	}

	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof ConditionalPageMasterReference))
		{
			return false;
		}
		ConditionalPageMasterReference conps = (ConditionalPageMasterReference) obj;
		return this._masterReference.equals(conps.getMasterReference())
				&& this._blankOrNotBlank == conps._blankOrNotBlank
				&& this._oddOrEven == conps._oddOrEven
				&& this._pagePosition == conps._pagePosition;
	}

	public ConditionalPageMasterReference clone()
	{

		SimplePageMaster masterReference = this.getMasterReference().clone();
		return new ConditionalPageMasterReference(masterReference,
				this._pagePosition, this._oddOrEven, this._blankOrNotBlank);
	}
}
