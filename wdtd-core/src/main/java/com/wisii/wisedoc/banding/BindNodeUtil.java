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
 * @BindNodeUtil.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding;

import com.wisii.wisedoc.banding.io.DataStructureTreeModel;

/**
 * 类功能描述： 动态数据节点相关的工具方法类 作者：zhangqiang 创建日期：2010-12-27
 */
public final class BindNodeUtil
{
	/**
	 * 
	 * 根据xpath找到对应数据节点功能
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static BindNode getNodeOfPath(String xpath,
			DataStructureTreeModel datamodel)
	{
		if (xpath == null || xpath.isEmpty() || datamodel == null)
		{
			return null;
		}
		String[] paths = xpath.split("/");
		BindNode node = (BindNode) datamodel.getRoot();
		if (!node.getNodeName().equals(paths[0]))
		{
			return null;
		}
		int pathlen = paths.length;
		for (int i = 1; i < pathlen; i++)
		{
			String name = paths[i];
			node = findnodeofname(name, node);
			if (node == null)
			{
				return null;
			}
			if (i == pathlen - 1)
			{
				return node;
			}
		}
		return null;
	}

	private static BindNode findnodeofname(String name, BindNode node)
	{
		if (name == null || node == null)
		{
			return null;
		}
		int len = node.getChildCount();
		for (int i = 0; i < len; i++)
		{
			BindNode child = node.getChildAt(i);
			if (child instanceof AttributeBindNode)
			{
				if (name.equals("@" + child.getNodeName()))
				{
					return child;
				}
			} else
			{
				if (name.equals(child.getNodeName()))
				{
					return child;
				}
			}
		}
		return null;
	}
}
