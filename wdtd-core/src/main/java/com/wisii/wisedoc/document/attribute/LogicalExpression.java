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
 * @LogicalExpression.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-11-6
 */
public class LogicalExpression implements Cloneable
{
	public static final int AND = 1;
	public static final int OR = 2;
	// 条件表达式的条件,条件可以是LogicalExpression（相当于带括号的条件表达式）和Condition对象的组合
	private List conditions;
	// 条件操作符。AND或OR,操作符数必须比条件数少
	private List<Integer> operators;

	private LogicalExpression(List conditions, List<Integer> operators)
	{
		this.conditions = new ArrayList(conditions);
		this.operators = new ArrayList<Integer>(operators);
	}

	/**
	 * 
	 * 采用instance方法初始化LogicalExpression对象主要是为了校验参数的有效性
	 * 参数校验通过，则得到LogicalExpression对象，否则返回为空
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static LogicalExpression instance(List conditions,
			List<Integer> operators)
	{
		LogicalExpression loge = null;

		if (conditions != null && !conditions.isEmpty())
		{

			int size = conditions.size();
			// 如果操作符数不够，则直接返回
			if (size > 1 && (operators == null || operators.size() < size - 1))
			{
				return null;
			}
			// 参数是否正确标识
			boolean isok = true;
			List<Integer> ops = new ArrayList<Integer>();
			// 检查参数是否正确
			for (int i = 0; i < size; i++)
			{
				Object condition = conditions.get(i);
				//如果condition为空或condition不是LogicalExpression活Condition类型，则参数检查不合格
				if (condition == null
						|| (!(condition instanceof LogicalExpression) && !(condition instanceof Condition)))
				{
					isok = false;
					break;
				}
				if (i > 0)
				{
					Integer op = operators.get(i - 1);
					// 如果操作符为空或是操作符不是逻辑与或逻辑或，则检验不通过
					if (op == null || ((op != AND) && (op != OR)))
					{
						isok = false;
						break;
					}
					ops.add(op);
				}
			}
			if (isok)
			{
				loge = new LogicalExpression(conditions, ops);
			}
		}
		return loge;
	}

	/**
	 * @返回 condition1变量的值
	 */
	public List getConditions()
	{
		return new ArrayList(conditions);
	}

	/**
	 * @返回 condition2变量的值
	 */
	public List<Integer> getOperators()
	{
		return new ArrayList(operators);
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof LogicalExpression))
		{
			return false;
		}
		LogicalExpression le = (LogicalExpression) obj;
		return (operators == null ? le.operators == null : operators
				.equals(le.operators))
				&& isconditionsequal(conditions, le.conditions);
	}
//    public void insertNewCondition(Object condition)
//    {
//    	
//    }
	private boolean isconditionsequal(List conds1, List conds2)
	{
		int size1 = conds1.size();
		int size2 = conds2.size();
		if (size1 != size2)
		{
			return false;
		}
		for (int i = 0; i < size1; i++)
		{
			Object tcon1 = conds1.get(i);
			Object tcon2 = conds2.get(i);
			if (!isConditionqual(tcon1, tcon2))
			{
				return false;
			}
		}
		return true;
	}

	private boolean isConditionqual(Object cond1, Object cond2)
	{
		if (cond1 == null)
		{
			if (cond2 == null)
			{
				return true;
			}
			return false;
		} else
		{
			if (cond2 == null)
			{
				return false;
			}
			if (cond1 instanceof Condition)
			{
				return cond1.equals(cond2);
			} else
			{
				if(!(cond2 instanceof LogicalExpression))
				{
					return false;
				}
				LogicalExpression le1 = (LogicalExpression) cond1;
				LogicalExpression le2 = (LogicalExpression) cond2;
				return (le1.operators == null ? le2.operators == null
						: le1.operators.equals(le2.operators))
						&& isconditionsequal(le1.conditions, le2.conditions);
			}
		}
	}

	public LogicalExpression clone()
	{
		List nconditions = new ArrayList();
		List<Integer> noperators = new ArrayList<Integer>(operators);
		int size = conditions.size();
		for (int i = 0; i < size; i++)
		{
			Object condition = conditions.get(i);
			if (condition instanceof LogicalExpression)
			{
				nconditions.add(clonele((LogicalExpression) condition));
			} else if (condition instanceof Condition)
			{
				nconditions.add(((Condition) condition).clone());
			}
		}
		return new LogicalExpression(nconditions, noperators);
	}

	private LogicalExpression clonele(LogicalExpression le)
	{
		if (le != null)
		{
			List nconditions = new ArrayList();
			List<Integer> noperators = le.getOperators();
			List oldconditions = le.getConditions();
			int size = oldconditions.size();
			for (int i = 0; i < size; i++)
			{
				Object condition = oldconditions.get(i);
				if (condition instanceof LogicalExpression)
				{
					nconditions.add(clonele((LogicalExpression) condition));
				} else if (condition instanceof Condition)
				{
					nconditions.add(((Condition) condition).clone());
				}
			}
			return new LogicalExpression(nconditions, noperators);
		}
		return null;
	}
	@Override
	public String toString() {
		if(conditions == null || conditions.isEmpty())
			return "";
		StringBuffer s = new StringBuffer();
		int size = conditions.size(), count = 0;
		for (Object obj : conditions) {
			count++;
			s.append(obj);
			if(count != size){
				s.append(",");
			}
		}
		return s.toString();
	}
}
