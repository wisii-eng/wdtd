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
 * @EncryptInformation.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.banding.BindNode;

/**
 * 类功能描述：解密属性类
 * 
 * 作者：zhangqiang 创建日期：2009-9-17
 */
public class EncryptInformation
{
	public static int ENCRYPTTYPE1 = 1;
	public static int ENCRYPTTYPE2 = 2;
	private int encrypttype = ENCRYPTTYPE1;
	List<BindNode> nodes = new ArrayList<BindNode>();

	public EncryptInformation(int type, List<BindNode> nodes)
	{
		this.encrypttype = type;
		if (nodes != null)
		{
			this.nodes.addAll(nodes);
		}
	}

	/**
	 * @返回 encrypttype变量的值
	 */
	public final int getEncrypttype()
	{
		return encrypttype;
	}

	/**
	 * @param encrypttype
	 *            设置encrypttype成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setEncrypttype(int encrypttype)
	{
		this.encrypttype = encrypttype;
	}

	/**
	 * @返回 nodes变量的值
	 */
	public final List<BindNode> getNodes()
	{
		return nodes;
	}

	/**
	 * @param nodes
	 *            设置nodes成员变量的值
	 * 
	 *            值约束说明
	 */
	public final void setNodes(List<BindNode> nodes)
	{
		this.nodes = nodes;
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
		result = prime * result + encrypttype;
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
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
		EncryptInformation other = (EncryptInformation) obj;
		if (encrypttype != other.encrypttype)
			return false;
		if (nodes == null)
		{
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "加密类型：" + encrypttype + "加密内容：" + nodes;
	}
}
