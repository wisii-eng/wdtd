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
 * @LogicalExpressionNode.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.treetable.le;

import java.util.List;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.LogicalExpression;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-3
 */
public class LogicalExpressionNode extends AbstractLogicalExpressionNode
{
	public LogicalExpressionNode(LogicalExpression le)
	{
		init(le);
	}

	public LogicalExpressionNode()
	{

	}

	private void init(LogicalExpression le)
	{
		if (le != null)
		{
			List conditions = le.getConditions();
			List<Integer> opers = le.getOperators();
			int size = conditions.size();
			for (int i = 0; i < size; i++)
			{
				Object condition = conditions.get(i);
				AbstractLogicalExpressionNode node;
				if (condition instanceof LogicalExpression)
				{
					LogicalExpressionNode lenode = new LogicalExpressionNode();
					initChildren(lenode, (LogicalExpression) condition);
					node = lenode;
				} else
				{
					node = new ConditionNode((Condition) condition);
				}

				if (i < size - 1)
				{
					Integer operator;
					operator = opers.get(i);
					node.setOperator(operator);
				}
				children.add(node);
				node.setParent(this);
			}
		} else
		{
			ConditionNode node = new ConditionNode(null, null, null);
			children.add(node);
			node.setParent(this);
		}

	}

	private void initChildren(LogicalExpressionNode lenode, LogicalExpression le)
	{
		if (lenode != null && le != null)
		{
			List conditions = le.getConditions();
			List<Integer> opers = le.getOperators();
			int size = conditions.size();
			for (int i = 0; i < size; i++)
			{
				Object condition = conditions.get(i);
				AbstractLogicalExpressionNode node;
				if (condition instanceof LogicalExpression)
				{
					LogicalExpressionNode nlenode = new LogicalExpressionNode();
					initChildren(nlenode, (LogicalExpression) condition);
					node = nlenode;
				} else
				{
					node = new ConditionNode((Condition) condition);
				}

				if (i < size - 1)
				{
					Integer operator;
					operator = opers.get(i);
					node.setOperator(operator);
				}
				lenode.children.add(node);
				node.setParent(lenode);
			}
		}

	}
	public String toString()
	{
		String text = "组合条件";
		if (parent != null)
		{
			text = text + parent.getIndex(this);
		}
		return text;
	}
}
