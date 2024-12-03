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

package com.wisii.wisedoc.document.attribute.transform;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.TreeNode;

public class TreeNodeDS implements TreeDSNode
{

	TreeDSNode parent;

	String name;

	public List<TreeDSNode> getChildren()
	{
		return children;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public void setValue(String value)
	{
		this.value = value;
	}

	String value;

	int type;

	List<TreeDSNode> children;

	public TreeNodeDS(TreeDSNode pd, int datatype, String nodename)
	{
		if (pd != null)
		{
			parent = pd;
		}
		type = datatype;
		name = nodename;
	}

	public TreeNodeDS(TreeDSNode pd, int datatype, String nodename, String value)
	{
		if (pd != null)
		{
			parent = pd;
		}
		type = datatype;
		name = nodename;
		this.value = value;
	}

	@Override
	public void addChild(TreeDSNode child)
	{
		if (children == null)
		{
			children = new ArrayList<TreeDSNode>();
		}
		children.add(child);
		child.setParent(this);
	}

	@Override
	public TreeDSNode getChildAt(int childIndex)
	{
		if (children != null)
		{
			return children.get(childIndex);
		}
		return null;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public TreeDSNode getParent()
	{
		return parent;
	}

	@Override
	public int getType()
	{
		return type;
	}

	@Override
	public String getValue()
	{
		return value;
	}

	@Override
	public void removeChild(TreeDSNode child)
	{
		if (children != null)
		{
			children.remove(child);
		}
	}

	@Override
	public void setChildren(List<TreeDSNode> children)
	{
		this.children = children;
	}

	@Override
	public void setParent(TreeDSNode parent)
	{
		this.parent = parent;
	}

	@Override
	public void setType(int type)
	{
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration children()
	{
		if (children != null && !children.isEmpty())
		{
			return new Vector<TreeDSNode>(children).elements();
		} else
		{
			return null;
		}

	}

	@Override
	public boolean getAllowsChildren()
	{
		return false;
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
			for (int i = 0; i < children.size(); i++)
			{
				TreeDSNode current = children.get(i);
				if (current.equals(node))
				{
					return i;
				}
			}
		}
		return 0;
	}

	@Override
	public boolean isLeaf()
	{
		return false;
	}

	public Object getElementItem(ElementItem parent)
	{
		if (type == TreeDSNode.ELEMENT)
		{
			ElementItem element = new ElementItem();
			if (name != null && !"".equals(name))
			{
				element.setName(name);
			}
			if (children != null && !children.isEmpty())
			{
				for (TreeDSNode current : children)
				{
					Object currentitem = current.getElementItem(element);
					if (currentitem instanceof ElementItem)
					{
						element.addElementItem((ElementItem) currentitem);
					} else if (currentitem instanceof AttributeItem)
					{
						element.addAttributeItem((AttributeItem) currentitem);
					} else if (currentitem instanceof String)
					{
						element.setText(currentitem.toString());
					}
				}
			}
			return element;
		} else if (type == TreeDSNode.ATTRIBUTE)
		{
			AttributeItem attributeitem = new AttributeItem(name, value);
			return attributeitem;
		} else if (type == TreeDSNode.TEXT)
		{
			String text = name;
			return text;
		}
		return null;
	}

	public TreeDSNode clone()
	{
		String name = new String(this.name);
		TreeNodeDS newnode = new TreeNodeDS(this.parent, this.type, name);
		if (this.value != null)
		{
			String newvalue = new String(this.value);
			newnode.setValue(newvalue);
		}
		if (children != null)
		{
			List<TreeDSNode> list = new ArrayList<TreeDSNode>();
			for (TreeDSNode current : children)
			{
				TreeDSNode clonenode = current.clone();
				list.add(clonenode);
			}
			newnode.setChildren(list);
		}
		return newnode;
	}
}
