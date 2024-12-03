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
 * @MultiSource.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.transform;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述：该类表示多FileSource组合而成的数据源
 * 
 * 作者：zhangqiang 创建日期：2009-8-29
 */
public final class MultiSource implements DataSource
{
	private final List<FileSource> filesources = new ArrayList<FileSource>();
	private final int bond;

	public MultiSource(int bond, List<FileSource> filesources)
	{
		this.bond = bond;
		if(filesources!=null){
		this.filesources.addAll(filesources);
		}
	}

	/**
	 * @返回 filesources变量的值
	 */
	public final List<FileSource> getFilesources()
	{
		return filesources;
	}

	/**
	 * @返回 bond变量的值
	 */
	public final int getBond()
	{
		return bond;
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
		result = prime * result + bond;
		result = prime * result
				+ ((filesources == null) ? 0 : filesources.hashCode());
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
		MultiSource other = (MultiSource) obj;
		if (bond != other.bond)
			return false;
		if (filesources == null)
		{
			if (other.filesources != null)
				return false;
		} else if (!filesources.equals(other.filesources))
			return false;
		return true;
	}

}
