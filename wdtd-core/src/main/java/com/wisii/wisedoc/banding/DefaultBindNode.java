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
 * @DefaultBindNode.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.tree.TreeNode;

/**
 * 类功能描述：默认的绑定节点实现类
 * 
 * 作者：zhangqiang 创建日期：2008-9-1
 */
public class DefaultBindNode implements BindNode
{

	// 父几点对象
	protected BindNode _parent;

	// 节点名
	protected String nodeName;

	protected List<BindNode> children = new ArrayList<BindNode>();

	protected Map<Integer, Object> _attributes = new HashMap<Integer, Object>();

	// 数据类型属性key
	public static final int DATATYPE = 1;

	// 数据长度属性key
	public static final int LENGTH = 2;

	public static final int NODECOUNT = 3;
	protected DefaultBindNode(Map<Integer, Object> attributes)
	{
		_attributes.putAll(attributes);
	}
	public DefaultBindNode(BindNode parent, int datatype, int length,
			String nodeName)
	{
		_parent = parent;
		this.nodeName = nodeName;
		if (datatype >= 0 && datatype < DATATYPECOUNT)
		{
			_attributes.put(DATATYPE, datatype);
		} else
		{
			_attributes.put(DATATYPE, STRING);
		}
		if (length > 0)
		{
			_attributes.put(LENGTH, length);
		} else
		{
			_attributes.put(LENGTH, UNLIMT);
		}
	}

	public DefaultBindNode(BindNode parent, int datatype, int length,
			String nodeName, Map<Integer, Object> otherattr)
	{
		this(parent, datatype, length, nodeName);
		_attributes.putAll(otherattr);
	}

	public List<BindNode> getChildren()
	{
		return children;
	}

	public void setChildren(Vector children)
	{
		this.children = children;
	}

	public void addChild(BindNode child)
	{
		if (child == null)
		{
			return;
		}
		// 去除掉重名的节点
		for (BindNode oldchild : children)
		{
			if (oldchild.getNodeName().equals(child.getNodeName()))
			{
				return;
			}
		}
		children.add(child);
		child.setParent(this);
	}

	public void removeChild(BindNode child)
	{
		children.remove(child);
	}

	public void setParent(BindNode parent)
	{
		this._parent = parent;
	}

	public String getNodeName()
	{
		return nodeName;
	}

	public Enumeration<BindNode> children()
	{
		return new Vector<BindNode>(children).elements();
	}

	public boolean getAllowsChildren()
	{
		return true;
	}

	public BindNode getChildAt(int childIndex)
	{
		if(childIndex<0||childIndex>=children.size())
		{
			return null;
		}
		return (BindNode) children.get(childIndex);
	}

	public int getChildCount()
	{
		return children.size();
	}

	public int getIndex(TreeNode node)
	{
		return children.indexOf(node);
	}

	public BindNode getParent()
	{
		return _parent;
	}

	public boolean isLeaf()
	{
		return children.isEmpty();
	}

	public String toString()
	{
		return "[" +getNameWithoutNameSpace() + "]";
		
	}
	protected String getNameWithoutNameSpace()
	{
		if (nodeName == null || nodeName.isEmpty())
		{
			return "";
		} else
		{
			String name=nodeName;
			int index=nodeName.indexOf(':');
			if(index!=-1)
			{
				name=name.substring(index+1);	
			}
			return name;
		}
	}

	protected String getXPATH()
	{
		if (getParent() != null)
		{
			return ((DefaultBindNode) getParent()).getXPATH() + "/" + nodeName;
		} else
		{
			return nodeName;
		}
	}

	public int getDataType()
	{
		return (Integer) _attributes.get(DATATYPE);
	}

	public int getLength()
	{
		return (Integer) _attributes.get(LENGTH);
	}

	public String getXPath()
	{
		return getXPATH();
	}

	/**
	 * @param _datatype
	 *            设置_datatype成员变量的值 值约束说明
	 */
	public void setDataType(int datatype)
	{
		if (datatype >= 0 || datatype < DATATYPECOUNT)
		{
			_attributes.put(DATATYPE, datatype);
		}

	}

	/**
	 * @param _length
	 *            设置_length成员变量的值 值约束说明
	 */
	public void setLength(int length)
	{
		if (length > 0)
		{
			_attributes.put(LENGTH, length);
		}
	}

	public Object getAttribute(int key)
	{
		return _attributes.get(key);
	}

	public int getNodeCount()
	{
		int nodecount = NOLIMT;
		Object nodecountObject = getAttribute(NODECOUNT);
		if (nodecountObject instanceof Integer)
		{
			nodecount = (Integer) nodecountObject;
		}
		return nodecount;
	}

	public void setNodeCount(int nodecount)
	{
		_attributes.put(NODECOUNT, nodecount);
	}

	public void setAttribute(int key, Object value)
	{
		_attributes.put(key, value);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof DefaultBindNode))
		{
			return false;
		}
		DefaultBindNode dnode = (DefaultBindNode) obj;
		return getXPath().equals(dnode.getXPath())
				&& _attributes.equals(dnode._attributes);
	}
    public boolean xpathEqual(Object obj)
    {
    	if (obj == null || !(obj instanceof DefaultBindNode))
		{
			return false;
		}
		DefaultBindNode dnode = (DefaultBindNode) obj;
		return getXPath().equals(dnode.getXPath());
    }
	@Override
	public int hashCode()
	{
		int xpath = getXPath().hashCode();
		int type = getDataType();
		int len = getLength();
		int nodecount = getNodeCount();
		return (31 ^ 3) * xpath + (31 ^ 2) * type + 31 * nodecount + len;

	}
	protected DefaultBindNode Clone()
	{
		DefaultBindNode node=new DefaultBindNode(_attributes);
		node.nodeName = nodeName;
		for(BindNode child:children)
		{
			node.addChild(((DefaultBindNode)child).Clone());
		}
		return node;
	} 
}
