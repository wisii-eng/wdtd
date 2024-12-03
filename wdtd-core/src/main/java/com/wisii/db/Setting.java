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
 * @Setting.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.db;
import java.io.Serializable;
import java.util.List;

import com.wisii.db.model.SQLItem;

/**
 * 类功能描述： 数据库链接 作者：zhangqiang 创建日期：2010-10-22
 */
public class Setting implements Serializable
{

	// 数据查询设置信息
	private final List<SQLItem> sqlitems;

	// 数据源名称
	private final String connname;

	public Setting(List<SQLItem> sqlitem, String connname)
	{
		super();
		this.sqlitems = sqlitem;
		this.connname = connname;
	}

	/**
	 * @返回 sqlitem变量的值
	 */
	public List<SQLItem> getSqlitem()
	{
		return sqlitems;
	}

	/**
	 * @返回 connname变量的值
	 */
	public String getConnname()
	{
		return connname;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((connname == null) ? 0 : connname.hashCode());
		result = prime * result + ((sqlitems == null) ? 0 : sqlitems.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Setting other = (Setting) obj;
		if (connname == null)
		{
			if (other.connname != null)
				return false;
		} else if (!connname.equals(other.connname))
			return false;
		if (sqlitems == null)
		{
			if (other.sqlitems != null)
				return false;
		} else if (!sqlitems.equals(other.sqlitems))
			return false;
		return true;
	}

}
