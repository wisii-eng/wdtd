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
 * 
 * @WriterContext.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.context;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.Group;


/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-5-29
 */
public class PathContext
{
	private PathContext parent;
	private Group group;
	public PathContext(PathContext parent, Group group)
	{
		this.parent = parent;
		this.group = group;
	}
	public String getRelatePath(BindNode node)
	{
		if(node==null)
		{
			return null;
		}
		String xpath = node.getXPath();
		//如果当前节点为空，则直接放回xpath
		if(group==null)
		{
			return xpath;
		}
//		否则返回和上一级的相对xpath
		String parentpath = group.getNode().getXPath();
		return comparePath(parentpath,xpath);
	}
	public boolean iscurrentGroup(Group g)
	{
		return group==g;
	}
	/**
	 * 
	 * 求xpath路径间的相对路径,两路径相同时用"."标注。
	 * 
	 * @param parentpath
	 *            ：父xpath path：要求相对路径的xpath
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private  String comparePath(String parentpath, String path)
	{

		if (parentpath == null || path == null)
		{
			return null;
		}
		if (parentpath.trim().equals(""))
		{
			return path;
		}
		parentpath = parentpath.trim();
		path = path.trim();

		if (path.startsWith(parentpath) || parentpath.startsWith(path))
		{
			return cpPath(parentpath, path);
		} else
		{
			
			// 当前重复属性的节点
			String[] pnodes = parentpath.split("/");

			String[] nodes = path.split("/");
			int psize = pnodes.length;
			int size = nodes.length;
			int fsize = psize >= size ? psize : size;
			// 记录第一个路径不同时的节点位置
			int postion = -1;
			for (int i = 0; i < fsize; i++)
			{
				String current = nodes[i];
				String pcurrent = pnodes[i];
				if (current.equals(pcurrent))
				{
					postion = i;
				} else
				{
					break;
				}
			}
			int chai = psize - postion - 1;
			String finalpath = "";
			for (int j = 0; j < chai; j++)
			{
				finalpath = finalpath + "..";
				if (j < chai - 1)
				{
					finalpath = finalpath + "/";
				}
			}
			for(int i=postion+1;i<size;i++)
			{
				finalpath = finalpath + "/"+nodes[i];
			}
			return finalpath;
		}
	}
	public static String cpPath(String parentpath, String path)
	{
		String s = "";
		String[] pnodes = parentpath.split("/");
		String[] nodes = path.split("/");
		int psize = pnodes.length;
		int size = nodes.length;
		// 记录第一个路径不同时的节点位置
		int postion = -1;
		// 找到路径相同的位置
		for (int i = 0, j = 0; i < psize && j < size
				&& pnodes[i].equals(nodes[j]); postion = i, i++, j++)
			;
		if (postion == psize - 1)
		{
			if (postion == size - 1)
			{
				s = ".";
			} else
			{
				for (int i = postion + 1; i < size; i++)
				{
					if (i == postion + 1)
					{
						s = s + nodes[i];
					} else
					{
						s = s + "/" + nodes[i];
					}
				}
			}
		}
		// 即postion < psize-1;
		else
		{
			for (int i = postion + 1; i < psize; i++)
			{
				if (i != psize - 1)
				{
					s = "../" + s;
				} else
				{
					s = s + "..";
				}
			}
			for (int i = postion + 1; i < size; i++)
			{
				s = s + "/" + nodes[i];
			}
		}
		return s;
	}
	/**
	 * @返回  parent变量的值
	 */
	public PathContext getParent()
	{
		return parent;
	}
	
}
