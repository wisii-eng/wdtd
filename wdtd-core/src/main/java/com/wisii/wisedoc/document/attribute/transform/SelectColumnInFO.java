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
 * @SelectInFO.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.transform;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.transform.Column.ColumnType;

/**
 * 类功能描述：下拉菜单显示，关联相关的信息类
 * 
 * 作者：zhangqiang 创建日期：2009-8-29
 */
public final class SelectColumnInFO
{
	private int datacolumnindex;
	private boolean isviewable;
	private int sortorder = -1;;
	private int searchorder = -1;
	private BindNode optionpath;
	private String viewname;
	private ColumnType columntype;

	public SelectColumnInFO(int datacolumnindex, boolean isviewable,
			BindNode optionpath, int searchorder, int sortorder,
			String viewname, ColumnType columntype)
	{
		this.datacolumnindex = datacolumnindex;
		this.isviewable = isviewable;
		this.optionpath = optionpath;
		if (searchorder > 0)
		{
			this.searchorder = searchorder;
		}
		if (sortorder > 0)
		{
			this.sortorder = sortorder;
		}
		this.viewname = viewname;
		this.columntype = columntype;
	}

	/**
	 * @返回 datacolumnindex变量的值
	 */
	public final int getDatacolumnindex()
	{
		return datacolumnindex;
	}

	/**
	 * @返回 isviewable变量的值
	 */
	public final boolean isIsviewable()
	{
		return isviewable;
	}

	/**
	 * @返回 sortorder变量的值
	 */
	public final int getSortorder()
	{
		return sortorder;
	}

	/**
	 * @返回 searchorder变量的值
	 */
	public final int getSearchorder()
	{
		return searchorder;
	}

	/**
	 * @返回 optionpath变量的值
	 */
	public final BindNode getOptionpath()
	{
		return optionpath;
	}

	/**
	 * @返回 viewname变量的值
	 */
	public final String getViewname()
	{
		return viewname;
	}

	/**
	 * @param datacolumnindex
	 *            设置datacolumnindex成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setDatacolumnindex(int datacolumnindex)
	{
		this.datacolumnindex = datacolumnindex;
	}

	/**
	 * @param isviewable
	 *            设置isviewable成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setIsviewable(boolean isviewable)
	{
		this.isviewable = isviewable;
	}

	/**
	 * @param sortorder
	 *            设置sortorder成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setSortorder(int sortorder)
	{
		this.sortorder = sortorder;
	}

	/**
	 * @param searchorder
	 *            设置searchorder成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setSearchorder(int searchorder)
	{
		this.searchorder = searchorder;
	}

	/**
	 * @param optionpath
	 *            设置optionpath成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setOptionpath(BindNode optionpath)
	{
		this.optionpath = optionpath;
	}

	/**
	 * @param viewname
	 *            设置viewname成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setViewname(String viewname)
	{
		this.viewname = viewname;
	}

	/**
	 * @返回 columntype变量的值
	 */
	public final ColumnType getColumntype()
	{
		return columntype;
	}

	/**
	 * @param columntype
	 *            设置columntype成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setColumntype(ColumnType columntype)
	{
		this.columntype = columntype;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columntype == null) ? 0 : columntype.hashCode());
		result = prime * result + datacolumnindex;
		result = prime * result + (isviewable ? 1231 : 1237);
		result = prime * result
				+ ((optionpath == null) ? 0 : optionpath.hashCode());
		result = prime * result + searchorder;
		result = prime * result + sortorder;
		result = prime * result
				+ ((viewname == null) ? 0 : viewname.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectColumnInFO other = (SelectColumnInFO) obj;
		if (columntype == null)
		{
			if (other.columntype != null)
				return false;
		} else if (!columntype.equals(other.columntype))
			return false;
		if (datacolumnindex != other.datacolumnindex)
			return false;
		if (isviewable != other.isviewable)
			return false;
		if (optionpath == null)
		{
			if (other.optionpath != null)
				return false;
		} else if (!optionpath.equals(other.optionpath))
			return false;
		if (searchorder != other.searchorder)
			return false;
		if (sortorder != other.sortorder)
			return false;
		if (viewname == null)
		{
			if (other.viewname != null)
				return false;
		} else if (!viewname.equals(other.viewname))
			return false;
		return true;
	}

}
