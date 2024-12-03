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
 * @ConnectionSeting.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.db;

/**
 * 类功能描述： 数据库链接设置
 * 
 * 作者：zhangqiang 创建日期：2010-10-22
 */
public class ConnectionSeting
{

	// 链接名称
	private final String name;

	// 驱动名称
	private final String driverclasstype;

	private final String username;

	private final String password;

	private final String url;

	public ConnectionSeting(String name, String driverclass, String url,
			String username, String password)
	{
		this.name = name;
		this.driverclasstype = driverclass;
		this.username = username;
		this.password = password;
		this.url = url;
	}

	/**
	 * @返回 name变量的值
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @返回 driverclass变量的值
	 */
	public String getDriverclassType()
	{
		return driverclasstype;
	}

	/**
	 * @返回 username变量的值
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @返回 password变量的值
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @返回 url变量的值
	 */
	public String getUrl()
	{
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ConnectionSeting [driverclasstype=" + driverclasstype
				+ ", name=" + name + "]";
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
				+ ((driverclasstype == null) ? 0 : driverclasstype.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		ConnectionSeting other = (ConnectionSeting) obj;
		if (driverclasstype == null)
		{
			if (other.driverclasstype != null)
				return false;
		} else if (!driverclasstype.equals(other.driverclasstype))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null)
		{
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (url == null)
		{
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (username == null)
		{
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
