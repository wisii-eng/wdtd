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
 * @Node.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.treetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-3
 */
public class DefaultNode implements Node,Serializable
{
	protected Node parent;
	protected List<Node> children = new ArrayList<Node>();

	public void setParent(Node newParent)
	{
		parent = newParent;

	}

	public Node getParent()
	{
		// TODO Auto-generated method stub
		return parent;
	}

	public Node getChildAt(int childIndex)
	{
		return children.get(childIndex);
	}

	public int getChildCount()
	{
		return children.size();
	}

	public int getIndex(Node node)
	{
		return children.indexOf(node);
	}
	/**
	 * 
	 * 用新的节点值替换原节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void replace(Node oldnode, Node newnode)
	{
		if (oldnode == null || newnode == null)
		{
			return;
		}
		newnode.setParent(this);
		int index = getIndex(oldnode);
		if (index > -1)
		{
			children.set(index, newnode);
		}
	}

	public void addNode(Node node)
	{
		if (node != null)
		{
			children.add(node);
			node.setParent(this);
		}
		
	}
	public void addNode(Node node,int index)
	{
		if (node != null)
		{
			if (index > children.size())
			{
				index = children.size();
			}
			children.add(index, node);
			node.setParent(this);
		}
	}
	public void remove(Node node)
	{
		children.remove(node);
		node.setParent(null);
	}
	public List<Node> getChildren()
	{
		return children;
	}
}
