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
 * @PageSequenceMaster.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.exception.FOVException;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：章节页布局属性
 * 
 * 作者：zhangqiang 创建日期：2008-8-21
 */
public class PageSequenceMaster implements Cloneable
{

	private List<SubSequenceSpecifier> subSequenceSpecifiers;

	private SubSequenceSpecifier currentSubSequence;

	private int currentSubSequenceNumber = -1;

	private String masterName;

	public static SimplePageMaster DEFAULTPAGEMASTER = (SimplePageMaster) InitialManager
			.getInitialValue(Constants.PR_SIMPLE_PAGE_MASTER, null);

	public PageSequenceMaster(List<SubSequenceSpecifier> specifiers)
	{
		subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>(specifiers);
	}

	/**
	 * 
	 * 构造默认的PageSequenceMaster时使用
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public PageSequenceMaster(SimplePageMaster singlepagemaster)
	{
		subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
		if (singlepagemaster == null)
		{
			singlepagemaster = DEFAULTPAGEMASTER;
		}
		SinglePageMasterReference spmr = new SinglePageMasterReference(
				singlepagemaster);
		subSequenceSpecifiers.add(spmr);
	}

	/**
	 * Returns the next subsequence specifier
	 * 
	 * @return a subsequence specifier
	 */
	private SubSequenceSpecifier getNextSubSequence()
	{
		currentSubSequenceNumber++;
		if (currentSubSequenceNumber >= 0
				&& currentSubSequenceNumber < subSequenceSpecifiers.size())
		{
			return (SubSequenceSpecifier) subSequenceSpecifiers
					.get(currentSubSequenceNumber);
		}
		return null;
	}

	/**
	 * Resets the subsequence specifiers subsystem.
	 */
	public void reset()
	{
		currentSubSequenceNumber = -1;
		currentSubSequence = null;
		for (int i = 0; i < subSequenceSpecifiers.size(); i++)
		{
			((SubSequenceSpecifier) subSequenceSpecifiers.get(i)).reset();
		}
	}

	/**
	 * Used to set the "cursor position" for the page masters to the previous
	 * item.
	 * 
	 * @return true if there is a previous item, false if the current one was
	 *         the first one.
	 */
	public boolean goToPreviousSimplePageMaster()
	{
		if (currentSubSequence != null)
		{
			boolean success = currentSubSequence.goToPrevious();
			if (!success)
			{
				if (currentSubSequenceNumber > 0)
				{
					currentSubSequenceNumber--;
					currentSubSequence = (SubSequenceSpecifier) subSequenceSpecifiers
							.get(currentSubSequenceNumber);
				} else
				{
					currentSubSequence = null;
				}
			}
		}
		return (currentSubSequence != null);
	}

	/**
	 * @return true if the page-sequence-master has a page-master with
	 *         page-position="last"
	 */
	public boolean hasPagePositionLast()
	{
		if (currentSubSequence != null)
		{
			return currentSubSequence.hasPagePositionLast();
		} else
		{
			return false;
		}
	}

	/**
	 * Returns the next simple-page-master.
	 * 
	 * @param isOddPage
	 *            True if the next page number is odd
	 * @param isFirstPage
	 *            True if the next page is the first
	 * @param isLastPage
	 *            True if the next page is the last
	 * @param isBlankPage
	 *            True if the next page is blank
	 * @return the requested page master
	 * @throws FOVException
	 *             if there's a problem determining the next page master
	 */
	public SimplePageMaster getNextSimplePageMaster(boolean isOddPage,
			boolean isFirstPage, boolean isLastPage, boolean isBlankPage)
	{
		if (currentSubSequence == null)
		{
			currentSubSequence = getNextSubSequence();
			if (currentSubSequence == null)
			{
				return null;
			}
		}
		SimplePageMaster pageMaster = currentSubSequence.getNextPageMaster(
				isOddPage, isFirstPage, isLastPage, isBlankPage);
		boolean canRecover = true;
		while (pageMaster == null)
		{
			SubSequenceSpecifier nextSubSequence = getNextSubSequence();
			if (nextSubSequence == null)
			{
				if (!canRecover)
				{
				}
				LogUtil.warn("subsequences exhausted in page-sequence-master '"
						+ "', using previous subsequence");
				currentSubSequence.reset();
				canRecover = false;
			} else
			{
				currentSubSequence = nextSubSequence;
			}
			pageMaster = currentSubSequence.getNextPageMaster(isOddPage,
					isFirstPage, isLastPage, isBlankPage);
		}
		return pageMaster;
	}

	@Override
	public PageSequenceMaster clone()
	{
		return new PageSequenceMaster(new ArrayList<SubSequenceSpecifier>(
				subSequenceSpecifiers));
	}

	/**
	 * 
	 * 添加一个新的条件布局引用
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	PageSequenceMaster addConditionalPageMasterReference(
			ConditionalPageMasterReference cpmr)
	{
		if (cpmr == null)
		{
			return null;
		}
		PageSequenceMaster master = null;
		int size = subSequenceSpecifiers.size();
		RepeatablePageMasterAlternatives rpma = null;
		List<SubSequenceSpecifier> lsss = new ArrayList<SubSequenceSpecifier>();
		for (int i = 0; i < size; i++)
		{
			SubSequenceSpecifier sss = subSequenceSpecifiers.get(i);
			if (sss instanceof RepeatablePageMasterAlternatives)
			{
				rpma = (RepeatablePageMasterAlternatives) sss;
			} else
			{
				lsss.add(sss);
			}
		}
		List<ConditionalPageMasterReference> lcpr = new ArrayList<ConditionalPageMasterReference>();
		if (rpma == null)
		{
			lcpr.add(cpmr);
		} else
		{
			lcpr.addAll(rpma.getPageMasterRefs());
			int sizel = lcpr.size();
			for (int i = 0; i < sizel; i++)
			{
				ConditionalPageMasterReference tempc = lcpr.get(i);
				// 去除掉原有条件列表中相同条件的页布局类
				if (tempc.isConditionequals(cpmr))
				{
					lcpr.remove(i);
					break;
				}
			}
			lcpr.add(cpmr);

		}
		rpma = new RepeatablePageMasterAlternatives(new EnumNumber(
				Constants.EN_NO_LIMIT, -1), lcpr);
		lsss.add(rpma);
		master = new PageSequenceMaster(lsss);
		return master;
	}

	public List<SubSequenceSpecifier> getSubsequenceSpecifiers()
	{
		return new ArrayList<SubSequenceSpecifier>(subSequenceSpecifiers);
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof PageSequenceMaster))
		{
			return false;
		}
		PageSequenceMaster psm = (PageSequenceMaster) obj;
		return subSequenceSpecifiers.equals(psm.subSequenceSpecifiers);
	}

	public String getMasterName()
	{
		return masterName;
	}

	public void setMasterName(String masterName)
	{
		this.masterName = masterName;
	}

	public PageSequenceMaster copy()
	{
		List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();

		if (this.subSequenceSpecifiers != null
				&& !this.subSequenceSpecifiers.isEmpty())
		{
			for (SubSequenceSpecifier current : this.subSequenceSpecifiers)
			{
				SubSequenceSpecifier newsss = current.clone();
				subSequenceSpecifiers.add(newsss);
			}
		}
		String masterName = new String(this.masterName);

		PageSequenceMaster newpsm = new PageSequenceMaster(
				subSequenceSpecifiers);
		newpsm.setMasterName(masterName);
		return newpsm;
	}
}
