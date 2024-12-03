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

package com.wisii.wisedoc.view.dialog;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.ui.StaticContentManeger;

public class MasterReference implements MasterNode
{

	List<MasterNode> children;

	int number = 1;

	MasterType type;

	TreeNode parent;

	SimplePageMaster value;

	String name;

	public MasterReference(MasterType mastertype, String name)
	{
		type = mastertype;
		this.name = name;

	}

	public MasterReference(MasterType mastertype, String name,
			SimplePageMaster master, int refnumber)
	{
		type = mastertype;
		value = master;
		number = refnumber;
		this.name = name;
	}

	@Override
	public void addChild(MasterNode child)
	{
		if (children == null)
		{
			children = new ArrayList<MasterNode>();
		}
		children.add(child);
		child.setParent(this);
	}

	@Override
	public List<MasterNode> getChildren()
	{
		return children;
	}

	@Override
	public int getNumber()
	{
		return number;
	}

	@Override
	public MasterType getType()
	{
		return type;
	}

	@Override
	public SimplePageMaster getValue()
	{
		return value;
	}

	@Override
	public void removeChild(MasterNode child)
	{
		if (children != null)
		{
			children.remove(child);
		}
	}

	@Override
	public void setNumber(int number)
	{
		this.number = number;
	}

	@Override
	public void setParent(MasterNode parent)
	{
		this.parent = parent;
	}

	@Override
	public void setType(MasterType type)
	{
		this.type = type;
	}

	@Override
	public void setValue(SimplePageMaster value)
	{
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration children()
	{
		return null;
	}

	@Override
	public boolean getAllowsChildren()
	{
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex)
	{
		if (children != null && children.size() > childIndex)
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
		if (children != null && !children.isEmpty())
		{
			for (int i = 0; i < children.size(); i++)
			{
				TreeNode current = children.get(i);
				if (current.equals(node))
				{
					return i;
				}
			}
		}
		return 0;
	}

	@Override
	public TreeNode getParent()
	{
		return parent;
	}

	@Override
	public boolean isLeaf()
	{
		return getChildCount() == 0;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String value)
	{
		name = value;
	}

	@Override
	public void addChild(MasterNode child, int index)
	{
		if (children == null || children.isEmpty())
		{
			if (index == 0)
			{
				children = new ArrayList<MasterNode>();
				addChild(child);
			}
		} else
		{
			int size = children.size();
			if (index >= 0 && index <= size)
			{
				children.add(index, child);
				child.setParent(this);
			}
		}
	}

	public MasterReference getChild(MasterType type)
	{
		List<MasterNode> childrens = this.getChildren();
		if (childrens != null && !childrens.isEmpty())
		{
			for (MasterNode current : childrens)
			{
				MasterType currenttype = current.getType();
				if (currenttype == type)
				{
					return (MasterReference) current;
				}
			}
		}
		return null;
	}

	public void removeChild(MasterType type)
	{
		List<MasterNode> childrens = this.getChildren();

		if (childrens != null && !childrens.isEmpty())
		{
			MasterNode delete = null;
			for (MasterNode current : childrens)
			{
				MasterType currenttype = current.getType();
				if (currenttype == type)
				{
					delete = current;
				}
			}
			if (delete != null)
			{
				if (delete.getValue() != null)
				{
					WisedocUtil.removeStaticContents(delete.getValue()
							.getMasterName(), StaticContentManeger.getScmap());
				}
				childrens.remove(delete);
			}
		}
	}
}
