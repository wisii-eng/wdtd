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

package com.wisii.wisedoc.view.data;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.wisii.db.ConnectionSeting;

public class DataSourceNodeReferece implements DataSourceNode
{

	public final static String DATASOURCEROOT = "datasoures";

	public final static String DATASOURCEITEM = "datasoure";

	DataSourceNode parent;

	NodeType nodetype;

	List<DataSourceNode> children;

	ConnectionSeting connectionseting;

	public DataSourceNodeReferece()
	{
		this.nodetype = NodeType.ROOT;
	}

	public DataSourceNodeReferece(ConnectionSeting cs)
	{
		this.nodetype = NodeType.DATASOUREITEM;
		setValue(cs);
	}

	@Override
	public void addChild(DataSourceNode child, int index)
	{
		if (children == null)
		{
			children = new ArrayList<DataSourceNode>();
		}
		if (children.size() >= index)
		{
			children.add(index, child);
			child.setParent(this);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration children()
	{
		return null;
	}

	@Override
	public List<DataSourceNode> getChildren()
	{
		return children;
	}

	@Override
	public boolean getAllowsChildren()
	{
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex)
	{
		if (children != null)
		{
			return children.get(childIndex);
		}
		return null;
	}

	@Override
	public int getChildCount()
	{
		if (children != null)
		{
			return children.size();
		}
		return 0;
	}

	@Override
	public int getIndex(TreeNode node)
	{
		if (children != null)
		{
			return children.indexOf(node);
		}
		return 0;
	}

	@Override
	public DataSourceNode getParent()
	{
		return parent;
	}

	@Override
	public boolean isLeaf()
	{
		return getChildCount() == 0;
	}

	@Override
	public NodeType getNodetype()
	{
		return nodetype;
	}

	@Override
	public void setNodetype(NodeType nodetype)
	{
		this.nodetype = nodetype;
	}

	@Override
	public ConnectionSeting getValue()
	{
		return connectionseting;
	}

	@Override
	public void setValue(ConnectionSeting value)
	{
		connectionseting = value;
	}

	@Override
	public void setParent(DataSourceNode value)
	{
		parent = value;
	}

	@Override
	public void reMoveAllChildren()
	{
		children.clear();

	}

	@Override
	public void reMoveChildren(DataSourceNode child)
	{
		if (children != null && !children.isEmpty())
		{
			children.remove(child);
		}
	}

	@Override
	public void addChild(DataSourceNode child)
	{
		if (children == null)
		{
			children = new ArrayList<DataSourceNode>();
		}
		children.add(child);
		child.setParent(this);
	}
}
