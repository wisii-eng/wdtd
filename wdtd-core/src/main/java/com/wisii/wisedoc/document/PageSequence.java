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
 * @Section.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PageNumberGenerator;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.RegionAfter;
import com.wisii.wisedoc.document.attribute.RegionBefore;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.RegionEnd;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.Numeric;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.util.WisedocUtil;

/**
 * 类功能描述：章节对象，对应FO规范中的PageSquence
 * 
 * 作者：zhangqiang 创建日期：2008-4-15
 */
public class PageSequence extends DefaultElement implements Groupable
{

	private int _startingPageNumber = 1;

	private Flow _mainflow;

	private final static SimplePageMaster DEFAULTPAGEMASTER;
	static
	{
		Length height = new FixedLength(29.7d, "cm");
		Length width = new FixedLength(21d, "cm");
		Length marginT = new FixedLength(15d, "mm");
		Length marginB = new FixedLength(17.5d, "mm");
		Length marginLR = new FixedLength(21.7d, "mm");
		Map<Integer, Region> regions = new HashMap<Integer, Region>();
		EnumProperty conditionality = new EnumProperty(Constants.EN_RETAIN, "");
		EnumNumber precedence = new EnumNumber(-1, 0);
		SpaceProperty spaceB = new SpaceProperty(new FixedLength(10.4d, "mm"),
				precedence, conditionality);
		SpaceProperty spaceA = new SpaceProperty(new FixedLength(7.9d, "mm"),
				precedence, conditionality);
		CommonMarginBlock margin = new CommonMarginBlock(new FixedLength(10.4d,
				"mm"), new FixedLength(7.9d, "mm"), new FixedLength(10d, "mm"),
				new FixedLength(10d, "mm"), spaceB, spaceA, null, null);
		RegionBody body = new RegionBody(1, null, margin, null, null,
				Constants.EN_BEFORE, Constants.EN_HIDDEN, null, 0,
				Constants.EN_LR_TB);

		RegionBefore before = new RegionBefore(Constants.EN_FALSE,
				new FixedLength(10.4d, "mm"), null,
				null, Constants.EN_BEFORE,
				Constants.EN_HIDDEN, "regionbefore", 0, Constants.EN_LR_TB);

		RegionAfter after = new RegionAfter(Constants.EN_FALSE, new FixedLength(
				7.9d, "mm"), null, null,
				Constants.EN_AFTER, Constants.EN_HIDDEN, "regionafter", 0,
				Constants.EN_LR_TB);

		RegionStart start = new RegionStart(new FixedLength(10.0d, "mm"), null,
				null, Constants.EN_BEFORE,
				Constants.EN_HIDDEN, "regionstart", 0, Constants.EN_LR_TB);

		RegionEnd end = new RegionEnd(new FixedLength(10.0d, "mm"), null,
				null, Constants.EN_BEFORE,
				Constants.EN_HIDDEN, "regionend", 0, Constants.EN_LR_TB);
		
		regions.put(Constants.FO_REGION_BODY, body);
		regions.put(Constants.FO_REGION_BEFORE, before);
		regions.put(Constants.FO_REGION_AFTER, after);
		regions.put(Constants.FO_REGION_START, start);
		regions.put(Constants.FO_REGION_END, end);

		CommonMarginBlock pageMargin = new CommonMarginBlock(marginT, marginB,
				marginLR, marginLR, null, null, null, null);
		DEFAULTPAGEMASTER = new SimplePageMaster(pageMargin, height, width, 0,
				Constants.EN_LR_TB, regions, "");
		DEFAULTPAGEMASTER.setMastername("region");

	}

	/**
	 * 
	 * 初始化章节对象 一个章节有且只包含一个版心流
	 * 
	 * @param
	 * @exception
	 */
	public PageSequence(final Map<Integer, Object> attributes)
	{
		super(attributes);
		_mainflow = new Flow();
		_children.add(_mainflow);
		_mainflow.setParent(this);
		if (attributes == null
				|| (attributes != null
						&& attributes.get(Constants.PR_PAGE_SEQUENCE_MASTER) == null && attributes
						.get(Constants.PR_SIMPLE_PAGE_MASTER) == null))
		{
			setAttribute(Constants.PR_SIMPLE_PAGE_MASTER, DEFAULTPAGEMASTER);

			StaticContent regionbeforesc = WisedocUtil
					.getDefaultStaticContent("regionbefore");
			StaticContent regionaftersc = WisedocUtil
					.getDefaultStaticContent("regionafter");
			StaticContent regionstartsc = WisedocUtil
					.getDefaultStaticContent("regionstart");
			StaticContent regionendsc = WisedocUtil
					.getDefaultStaticContent("regionend");
			this.add(regionbeforesc);
			this.add(regionaftersc);
			this.add(regionstartsc);
			this.add(regionendsc);
		}

	}

	/*
	 * 章节默认只有一个版心的流作为其子对象，该版心流是在章节对象初始化的时候 赋值的，因此不容许添加版心流，也不容许删除该版心流。
	 * 只容许添加或删除页眉，页脚，左，右区域等静态流
	 * 
	 * @see
	 * com.wisii.wisedoc.document.DefaultElement#iscanadd(com.wisii.wisedoc.
	 * document.CellElement)
	 */
	@Override
	public boolean iscanadd(CellElement newChild)
	{
		return newChild != null
				&& newChild instanceof StaticContent
				// 控制同名的StaticContent的插入
				&& getIndexofStaticContentbyname(((StaticContent) newChild)
						.getFlowName()) < 0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.DefaultElement#iscaninsert(com.wisii.wisedoc
	 * .document.CellElement, int)
	 */
	@Override
	public boolean iscaninsert(CellElement newChild, int childIndex)
	{
		return newChild != null && newChild instanceof StaticContent
				&& childIndex >= 0
				&& childIndex < _children.size()
				// 控制同名的StaticContent的插入
				&& getIndexofStaticContentbyname(((StaticContent) newChild)
						.getFlowName()) < 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.DefaultElement#iscaninsert(java.util.List,
	 * int)
	 */
	@Override
	public boolean iscaninsert(List<CellElement> children, int childIndex)
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.DefaultElement#insert(java.util.List,
	 * int)
	 */
	@Override
	public void insert(List<CellElement> children, int childIndex)
	{
		if (iscaninsert(children, childIndex))
		{
			if (children != null && !children.isEmpty())
			{
				int size = children.size();
				for (int i = 0; i < size; i++)
				{
					CellElement cell = children.get(i);
					if (!_children.contains(cell) && cell != null)/* cell也有可能为空 */
					{
						cell.setParent(this);
						if (cell instanceof StaticContent)
						{
							int index = getIndexofStaticContentbyname(((StaticContent) cell)
									.getFlowName());
							// 如果已经有同名的staticContent，则先删除原来的
							if (index > -1)
							{
								_children.remove(index);
							}
							_children.add(cell);
						} else if (cell instanceof Flow)
						{
							_children.remove(_mainflow);
							_children.add(cell);
							_mainflow = (Flow) cell;
						}
					}
				}
			}
		}
	}

	private int getIndexofStaticContentbyname(String name)
	{
		if (name == null)
		{
			return -1;
		}
		int size = _children.size();
		for (int i = 0; i < size; i++)
		{
			CellElement child = _children.get(i);
			if (child instanceof StaticContent
					&& name.equals(((StaticContent) child).getFlowName()))
			{
				return i;
			}
		}
		return -1;
	}

	/*
	 * 能删除非版心的流（即可以删除页眉，页脚等静态流）
	 * 
	 * @see
	 * com.wisii.wisedoc.document.DefaultElement#iscanRemove(com.wisii.wisedoc
	 * .document.CellElement)
	 */
	@Override
	public boolean iscanRemove(CellElement child)
	{
		if (child == null
				|| (child instanceof Flow && !(child instanceof StaticContent)))
		{
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.DefaultElement#iscanRemove(java.util.List)
	 */
	@Override
	public boolean iscanRemove(List<CellElement> children)
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.DefaultElement#iscanSetAttribute(int,
	 * java.lang.Object)
	 */
	@Override
	public boolean iscanSetAttribute(int key, Object value)
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.DefaultElement#iscanremoveall()
	 */
	@Override
	public boolean iscanremoveall()
	{
		return false;
	}

	/**
	 * Initialize the current page number for the start of the page sequence.
	 */
	public void initPageNumber()
	{
		int pageNumberType = 0;
		Numeric initn = getInitialPageNumber();
		if (initn.getEnum() != 0)
		{
			// auto | auto-odd | auto-even.
			// WiseDocDocument doc = (WiseDocDocument) getParent();
			_startingPageNumber = ((WiseDocDocument) getParent())
					.getEndingPageNumberOfPreviousSequence() + 1;
			pageNumberType = initn.getEnum();
			if (pageNumberType == Constants.EN_AUTO_ODD)
			{
				if (_startingPageNumber % 2 == 0)
				{
					_startingPageNumber++;
				}
			} else if (pageNumberType == Constants.EN_AUTO_EVEN)
			{
				if (_startingPageNumber % 2 == 1)
				{
					_startingPageNumber++;
				}
			}
		} else
		{ // <integer> for explicit page number
			int pageStart = initn.getValue();
			_startingPageNumber = (pageStart > 0) ? pageStart : 1; // spec rule
		}
	}

	/**
	 * Get the starting page number for this page sequence.
	 * 
	 * @return the starting page number
	 */
	public int getStartingPageNumber()
	{
		return _startingPageNumber;
	}

	/**
	 * Get the static content FO node from the flow map. This gets the static
	 * content flow for the given flow name.
	 * 
	 * @param name
	 *            the flow name to find
	 * @return the static content FO node
	 */
	public StaticContent getStaticContent(String name)
	{
		Iterator<CellElement> childit = getChildren();
		while (childit.hasNext())
		{
			Flow flow = (Flow) childit.next();
			if (flow.getFlowName().equals(name)
					&& flow instanceof StaticContent)
			{
				return (StaticContent) flow;
			}
		}
		return null;
	}

	/** @return the "id" property. */
	@Override
	public String getId()
	{
		return "";
	}

	/**
	 * Accessor method for titleFO
	 * 
	 * @return titleFO for this object
	 */
	public Title getTitleFO()
	{
		return (Title) getAttribute(Constants.FO_TITLE);
	}

	/**
	 * Public accessor for getting the MainFlow to which this PageSequence is
	 * attached.
	 * 
	 * @return the MainFlow object to which this PageSequence is attached.
	 */
	public Flow getMainFlow()
	{
		return _mainflow;
	}

	/**
	 * 
	 * 获得指定页续的页布局 注意页续的页布局属性作为章节的属性保存在章节对象中
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public SimplePageMaster getNextSimplePageMaster(int page,
			boolean isFirstPage, boolean isLastPage, boolean isBlank)
	{
		PageSequenceMaster ps = (PageSequenceMaster) getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER);
		if (ps == null)
		{
			return (SimplePageMaster) getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);
		}
		boolean isOddPage = ((page % 2) == 1);
		LogUtil.debug("getNextSimplePageMaster(page=" + page + " isOdd="
				+ isOddPage + " isFirst=" + isFirstPage + " isLast="
				+ isLastPage + " isBlank=" + isBlank + ")");
		return ps.getNextSimplePageMaster(isOddPage, isFirstPage, isLastPage,
				isBlank);
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
		PageSequenceMaster ps = (PageSequenceMaster) getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER);
		if (ps == null)
		{
			return true;
		} else
		{
			return ps.goToPreviousSimplePageMaster();
		}
	}

	/**
	 * @return true if the page-sequence has a page-master with
	 *         page-position="last"
	 */
	public boolean hasPagePositionLast()
	{
		PageSequenceMaster ps = (PageSequenceMaster) getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER);
		if (ps == null)
		{
			return false;
		} else
		{
			return ps.hasPagePositionLast();
		}
	}

	/**
	 * Retrieves the string representation of a page number applicable for this
	 * page sequence
	 * 
	 * @param pageNumber
	 *            the page number
	 * @return string representation of the page number
	 */
	public String makeFormattedPageNumber(int pageNumber)
	{
		String format = (String) getAttribute(Constants.PR_FORMAT);
		Character groupingSeparator = (Character) getAttribute(Constants.PR_GROUPING_SEPARATOR);
		;
		if (groupingSeparator == null)
		{
			groupingSeparator = ',';
		}
		Integer groupingSize = (Integer) getAttribute(Constants.PR_GROUPING_SIZE);
		if (groupingSize == null)
		{
			groupingSize = 3;
		}
		Integer letterValue = ((EnumProperty) getAttribute(Constants.PR_LETTER_VALUE))
				.getEnum();
		if (letterValue == null)
		{
			letterValue = 3;
		}
		PageNumberGenerator gen = new PageNumberGenerator(format,
				groupingSeparator, groupingSize, letterValue);
		return gen.makeFormattedPageNumber(pageNumber);
	}

	/** @return the force-page-count value */
	public int getForcePageCount()
	{
		EnumProperty prop = (EnumProperty) getAttribute(Constants.PR_FORCE_PAGE_COUNT);
		return prop.getEnum();
	}

	/** @return the initial-page-number property value */
	public Numeric getInitialPageNumber()
	{
		return ((EnumNumber) getAttribute(Constants.PR_INITIAL_PAGE_NUMBER))
				.getNumeric();
	}

	/** @return the country property value */
	public String getCountry()
	{
		return (String) getAttribute(Constants.PR_COUNTRY);
	}

	/** @return the language property value */
	public String getLanguage()
	{
		return (String) getAttribute(Constants.PR_LANGUAGE);
	}

	@Override
	public int getNameId()
	{
		return Constants.FO_PAGE_SEQUENCE;
	}

	public boolean isGroupAble()
	{
		return true;
	}

	public void reSet()
	{
		PageSequenceMaster psm = (PageSequenceMaster) getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER);
		if (psm != null)
		{
			psm.reset();
		}

	}
}
