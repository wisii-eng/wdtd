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
 * @SelectInfo.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.transform;

import java.util.List;

import com.wisii.wisedoc.document.Constants;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-8-29
 */
public final class SelectInfo
{
	public static final int SCHEMA = 1;
	public static final int TRANSFORMTABLE = 2;
	public static final int IMPORTSOURCE = 3;
	public static final int EXTERNALFUNCTION = 4;
	public static final int SWINGDS = 5;
	private final int datasourcetype;
	private final DataSource datasource;
	private final int sorttype;
	private final boolean isSearchable;
	private final List<SelectColumnInFO> columninfos;

	public SelectInfo(List<SelectColumnInFO> columninfos,
			DataSource datasource, int datasourcetype, boolean isSearchable,
			int sorttype)
	{
		this.columninfos = columninfos;
		this.datasource = datasource;
		if(datasourcetype<SCHEMA||datasourcetype > SWINGDS)
		{
			datasourcetype = SCHEMA;
		}
		this.datasourcetype = datasourcetype;
		this.isSearchable = isSearchable;
		if (sorttype < Constants.EN_SORT_P || sorttype > Constants.EN_SORT_C)
		{
			sorttype = Constants.EN_SORT_C;
		}
		this.sorttype = sorttype;
	}

	/**
	 * @返回 datasourcetype变量的值
	 */
	public final int getDatasourcetype()
	{
		return datasourcetype;
	}

	/**
	 * @返回 datasource变量的值
	 */
	public final DataSource getDatasource()
	{
		return datasource;
	}

	/**
	 * @返回 sorttype变量的值
	 */
	public final int getSorttype()
	{
		return sorttype;
	}

	/**
	 * @返回 isSearchable变量的值
	 */
	public final boolean isSearchable()
	{
		return isSearchable;
	}

	/**
	 * @返回 columninfos变量的值
	 */
	public final List<SelectColumnInFO> getColumninfos()
	{
		return columninfos;
	}

}
