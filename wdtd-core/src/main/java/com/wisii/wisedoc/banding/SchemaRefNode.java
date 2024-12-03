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
 * @SchemaRefNode.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;


/**
 * 类功能描述：
 *
 * 作者：wisii
 * 创建日期：2013-3-18
 */
public class SchemaRefNode extends DefaultBindNode
{
	private DefaultBindNode refnode;
	private boolean isinit=false;
    protected SchemaRefNode(Map<Integer, Object> attributes,DefaultBindNode refnode)
    {
    	super(attributes);
    	this.refnode=refnode;
    }
	public SchemaRefNode(BindNode parent, int datatype, int length,
			String nodeName, DefaultBindNode refnode)
	{
		super(parent, datatype, length, nodeName);
		this.refnode = refnode;
	}

	SchemaRefNode(BindNode parent, int datatype, int length, String nodeName,
			Map<Integer, Object> otherattr, DefaultBindNode refnode)
	{
		super(parent, datatype, length, nodeName, otherattr);
		this.refnode = refnode;
	}

	@Override
	public int getChildCount()
	{
		return refnode.getChildCount();
	}
	public boolean isLeaf()
	{
		return false;
	}
	@Override
	public List<BindNode> getChildren()
	{
		initChildren();
		return super.getChildren();
	}

	@Override
	public BindNode getChildAt(int childIndex)
	{
		initChildren();
		return super.getChildAt(childIndex);
	}

	public Enumeration<BindNode> children()
	{
		initChildren();
		return super.children();
	}

	private void initChildren()
	{
		if (!isinit)
		{
			List<BindNode> redchildren=refnode.children;
			for(BindNode redchild:redchildren)
			{
				addChild(((DefaultBindNode)redchild).Clone());
			}
			isinit=true;
		}
	}
    protected  DefaultBindNode Clone()
	{
    	
    	SchemaRefNode node= new SchemaRefNode(_attributes, refnode);
    	node.nodeName = this.nodeName;
    	return node;
	}
	public DefaultBindNode getRefnode()
	{
		return refnode;
	}
    
}
