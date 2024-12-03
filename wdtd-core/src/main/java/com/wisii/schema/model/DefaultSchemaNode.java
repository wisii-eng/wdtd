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
 * @AbstractSchemaNode.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.schema.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;
import com.wisii.util.ListEnumeration;
/**
 * 类功能描述：
 * schema节点公共父类
 * 作者：wisii
 * 创建日期：2013-3-5
 */
public  class DefaultSchemaNode implements SchemaNode
{
	private SchemaNode parent;

	private XSObjectRef xsobjectref;

	private List<SchemaNode> children;
	//是否递归
	private DefaultSchemaNode refnode;
	public DefaultSchemaNode(XSObjectRef xsobjectref)
	{
		this.xsobjectref=xsobjectref;
	}
	public DefaultSchemaNode(XSObjectRef xsobjectref,DefaultSchemaNode refnode)
	{
		this.xsobjectref=xsobjectref;
		this.refnode=refnode;
	}
	public void setParent(SchemaNode parent)
	{
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	@Override
	public SchemaNode getParent()
	{
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	@Override
	public int getIndex(TreeNode node)
	{
		if (children == null)
		{
			return -1;
		}
		return children.indexOf(node);
	}

	@Override
	public SchemaNode getChildAt(int childIndex)
	{
		if (children == null)
		{
			return null;
		}
		if (childIndex < 0 || childIndex > children.size())
		{
			return null;
		}
		return children.get(childIndex);
	}

	@Override
	public int getChildCount()
	{
		if(refnode!=null)
		{
			return refnode.getChildCount();
		}
		if (children == null)
		{
			return 0;
		}
		return children.size();
	}

	@Override
	public boolean getAllowsChildren()
	{
		return true;
	}

	@Override
	public boolean isLeaf()
	{
		if(refnode!=null)
		{
			return refnode.isLeaf();
		}
		return children == null || children.isEmpty();
	}

	@Override
	public Enumeration children()
	{
		
		if (children == null)
		{
			return null;
		}
		return new ListEnumeration(children);
	}

	@Override
	public void addChild(SchemaNode child)
	{
		if (child == null)
		{
			return;
		}
		child.setParent(this);
		if (children == null)
		{
			children = new ArrayList<SchemaNode>();
			children.add(child);

		} else if (!children.contains(child))
		{
			children.add(child);
		}

	}

	@Override
	public void addChildren(List<SchemaNode> children)
	{
		if (children == null || children.isEmpty())
		{
			return;
		}
		for (SchemaNode child : children)
		{
			addChild(child);
		}

	}

	@Override
	public void removeChild(SchemaNode child)
	{
		if (children == null || child == null)
		{
			return;
		}
		children.remove(child);
		child.setParent(null);

	}

	@Override
	public void removeChildren(List<SchemaNode> children)
	{
		if (this.children == null || children == null)
		{
			return;
		}
		for (SchemaNode child : children)
		{
			removeChild(child);
		}

	}

	@Override
	public XSObjectRef getXSObjectRef()
	{
		return xsobjectref;
	}
	public SchemaNode Clone()
	{
		DefaultSchemaNode node=new DefaultSchemaNode(xsobjectref);
		if(children!=null&&!children.isEmpty())
		{
			for(SchemaNode child:children)
			{
				node.addChild(child.Clone());
			}
		}
		return node;
	}
	public boolean isRefNode()
	{
		return refnode!=null;
	}
	public DefaultSchemaNode getRefNode()
	{
		return refnode;
	}
	@Override
	public boolean hasElementChildren()
	{
		if(children==null||children.isEmpty())
		{
			return false;
		}
		for(SchemaNode child:children)
		{
			if (child.isElement())
			{
				return true;
			}
			else if (child.hasElementChildren())
			{
				return true;
			}
		}
		return false;
	}
	public boolean isElement()
	{
		return getXSObjectRef().isElement();
	}
	@Override
	public boolean isAttribute()
	{
		
		return getXSObjectRef().isAttribute();
	}
    @Override
    public String toString()
    {
    	return getXSObjectRef().toString();
    }
	@Override
	public String getNodeName()
	{
		return xsobjectref.getXSNode().getName();
	}
	@Override
	public String getNameSpace()
	{
		return xsobjectref.getXSNode().getNamespace();
	}
}
