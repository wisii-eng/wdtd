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
 * @TableColumn.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.Map;

import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.NumberProperty;
import com.wisii.wisedoc.document.attribute.TableColLength;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-8-28
 */
public class TableColumn extends TableFObj
{
	private boolean defaultColumn = false;
	// 边框，背景，padding等属性
	private CommonBorderPaddingBackground commonbpbackground;

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public TableColumn()
	{
		// TODO Auto-generated constructor stub
		this(null);
	}

	/**
	 * 初始化过程的描述
	 *
	 * @param       初始化参数说明
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public TableColumn(final Map<Integer,Object> attributes)
	{
		super(attributes);
		// TODO Auto-generated constructor stub
	}
	public TableColumn(final Map<Integer,Object> attributes, boolean defaultColumn){
		this(attributes);
		this.defaultColumn = defaultColumn;
	}
	public void initFOProperty()
	{
		super.initFOProperty();
		if (commonbpbackground == null)
		{
			commonbpbackground = new CommonBorderPaddingBackground(this);
		} else
		{
			commonbpbackground.init(this);
		}
	}
	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.document.TableFObj#getCommonBorderPaddingBackground()
	 */
	@Override
	public CommonBorderPaddingBackground getCommonBorderPaddingBackground()
	{
		// TODO Auto-generated method stub
		return commonbpbackground;
	}

	/**
	 * @return the "column-width" property.
	 */
	public Length getColumnWidth()
	{
		Length length = (Length) getAttribute(Constants.PR_COLUMN_WIDTH);
		if(length == null)
			length = new TableColLength(1.0, getParent());
		
		return length;
		/*return (Length) getAttribute(Constants.PR_COLUMN_WIDTH);*/
	}

	/**
	 * Sets the column width.
	 * @param columnWidth the column width
	 */
	public void setColumnWidth(Length columnWidth)
	{
		setAttribute(Constants.PR_COLUMN_WIDTH, columnWidth);
	}

	/**
	 * @return the "column-number" property.
	 */
	public int getColumnNumber()
	{
		return (Integer) getAttribute(Constants.PR_COLUMN_NUMBER);
	}

	/** @return value for number-columns-repeated. */
	public int getNumberColumnsRepeated()
	{
		return (Integer) getAttribute(Constants.PR_NUMBER_COLUMNS_REPEATED);
	}

	/** @return value for number-columns-spanned. */
	public int getNumberColumnsSpanned()
	{
		return (Integer) getAttribute(Constants.PR_NUMBER_COLUMNS_SPANNED);
	}

	/** @see com.wisii.fov.fo.FONode#getLocalName() */
	public String getLocalName()
	{
		return "table-column";
	}

	/** @see com.wisii.fov.fo.FObj#getNameId() */
	public int getNameId()
	{
		return Constants.FO_TABLE_COLUMN;
	}

	/**
	 * Indicates whether this table-column has been created as default column for this table in
	 * case no table-columns have been defined. Note that this only used to provide better
	 * user feedback (see ColumnSetup).
	 * @return true if this table-column has been created as default column
	 */
	public boolean isDefaultColumn()
	{
		return defaultColumn;
	}

	/** @see java.lang.Object#toString() */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("fo:table-column");
		sb.append(" column-number=").append(getColumnNumber());
		if (getNumberColumnsRepeated() > 1)
		{
			sb.append(" number-columns-repeated=").append(
					getNumberColumnsRepeated());
		}
		if (getNumberColumnsSpanned() > 1)
		{
			sb.append(" number-columns-spanned=").append(
					getNumberColumnsSpanned());
		}
		sb.append(" column-width=").append(getColumnWidth());
		return sb.toString();
	}

}
