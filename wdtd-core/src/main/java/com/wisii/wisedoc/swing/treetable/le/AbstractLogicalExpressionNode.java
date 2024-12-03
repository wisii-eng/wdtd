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
 * @AbstractLogicalExpressionNode.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.treetable.le;

import com.wisii.wisedoc.swing.treetable.DefaultNode;
import com.wisii.wisedoc.swing.treetable.Node;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-11-8
 */
public abstract class AbstractLogicalExpressionNode extends DefaultNode
{
	private Integer operator;

	public void setOperator(Integer operator)
	{
		this.operator = operator;
	}

	public Integer getOperator()
	{
		return operator;
	}
	public Node  insert(Node node, boolean isbefore)
	{
		if (node == null)
		{
			return null;
		}
		int index = getIndex(node);
		if (index > -1)
		{
			DefaultNode nnode = new ConditionNode(null, null, null);
			nnode.setParent(this);
			// int toaddindex = index;
			if (isbefore)
			{
				children.add(index, nnode);
			} else
			{
				if (index == (getChildCount() - 1))
				{
					children.add(nnode);
				} else
				{
					children.add(index + 1, nnode);
				}
			}
			return nnode;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractLogicalExpressionNode other = (AbstractLogicalExpressionNode) obj;
		if (operator == null)
		{
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		return true;
	}
	
}
