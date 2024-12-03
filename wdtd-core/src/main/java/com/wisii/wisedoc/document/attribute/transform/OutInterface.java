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
 * @OutInterface.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute.transform;
/**
 * 类功能描述：外部接口类型的数据源
 * 
 * 作者：zhangqiang 创建日期：2009-9-21
 */
public class OutInterface implements DataSource
{ // 完整的类名称，包括包名
	private String classname;
	//列数
	private int cloumncount = 2;

	public OutInterface(String classname, int cloumncount)
	{
		if (cloumncount > 0)
		{
			this.cloumncount = cloumncount;
		}
		this.classname = classname;
	}

	/**
	 * @返回  classname变量的值
	 */
	public final String getClassname()
	{
		return classname;
	}

	/**
	 * @返回 cloumncount变量的值
	 */
	public final int getCloumncount()
	{
		return cloumncount;
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
				+ ((classname == null) ? 0 : classname.hashCode());
		result = prime * result + cloumncount;
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
		OutInterface other = (OutInterface) obj;
		if (classname == null)
		{
			if (other.classname != null)
				return false;
		} else if (!classname.equals(other.classname))
			return false;
		if (cloumncount != other.cloumncount)
			return false;
		return true;
	}

}
