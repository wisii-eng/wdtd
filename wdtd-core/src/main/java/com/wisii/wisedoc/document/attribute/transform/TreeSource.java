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
 * @TreeSource.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.transform;

import java.util.List;

/**
 * 类功能描述：树形数据源信息类
 * 
 * 作者：zhangqiang 创建日期：2009-8-29
 */
public class TreeSource implements DataSource
{ // 文件路径
	private String file;
	private String root;
	// 列顺序信息
	private List<String> columninfo;
	// 数据中可以作为key的列
	private List<Integer> valuenumber;

	public TreeSource(String file, String root,
			List<String> columninfo, List<Integer> valuenumber)
	{
		this.file = file;
		this.root = root;
		this.columninfo = columninfo;
		this.valuenumber = valuenumber;
	}

	/**
	 * @返回 file变量的值
	 */
	public final String getFile()
	{
		return file;
	}

	/**
	 * @返回 root变量的值
	 */
	public final String getRoot()
	{
		return root;
	}

	/**
	 * @返回 columninfo变量的值
	 */
	public final List<String> getColumninfo()
	{
		return columninfo;
	}

	/**
	 * @返回 valuenumber变量的值
	 */
	public final List<Integer> getValuenumber()
	{
		return valuenumber;
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
				+ ((columninfo == null) ? 0 : columninfo.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		result = prime * result
				+ ((valuenumber == null) ? 0 : valuenumber.hashCode());
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
		TreeSource other = (TreeSource) obj;
		if (columninfo == null)
		{
			if (other.columninfo != null)
				return false;
		} else if (!columninfo.equals(other.columninfo))
			return false;
		if (file == null)
		{
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (root == null)
		{
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		if (valuenumber == null)
		{
			if (other.valuenumber != null)
				return false;
		} else if (!valuenumber.equals(other.valuenumber))
			return false;
		return true;
	}

}
