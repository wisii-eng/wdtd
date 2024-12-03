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
 * @LogicalExpressionTreeTableModel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.treetable.le;

import java.util.HashMap;
import java.util.Map;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.swing.treetable.AbstractTreeTableModel;
import com.wisii.wisedoc.swing.treetable.TreeTableModel;

/**
 * 类功能描述：条件表达式TreeTableModel
 * 
 * 作者：zhangqiang 创建日期：2008-12-3
 */
public class LogicalExpressionTreeTableModel extends AbstractTreeTableModel
		implements TreeTableModel
{

	private String[] columnnames =
	{ "条件", "前节点", "运算符", "参考值", "条件关系" };

	// 关系操作符字符串
	private Map<Integer, String> operatorstringmap;

	// 条件字符串
	private Map<Integer, String> contypestringmap;

	protected LogicalExpressionNode root;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public LogicalExpressionTreeTableModel(LogicalExpression le)
	{
		super(new LogicalExpressionNode(le));
		root = (LogicalExpressionNode) super.root;
		init();
	}

	private void init()
	{
		operatorstringmap = new HashMap<Integer, String>();
		contypestringmap = new HashMap<Integer, String>();
		operatorstringmap.put(LogicalExpression.AND, "且");
		operatorstringmap.put(LogicalExpression.OR, "或");
		contypestringmap.put(Condition.EQUAL, "数字等于");
		contypestringmap.put(Condition.GREATER, "数字大于");
		contypestringmap.put(Condition.GREATEREQUAL, "数字大于等于");
		contypestringmap.put(Condition.LESS, "数字小于");
		contypestringmap.put(Condition.LESSEQUAL, "数字小于等于");
		contypestringmap.put(Condition.NUMBERNOTEQUAL, "数字不等于");
		contypestringmap.put(Condition.REGULAREQUAL, "正则表达式匹配");
		contypestringmap.put(Condition.STRINGEQUAL, "文本等于");
		contypestringmap.put(Condition.STRINGNOTEQUAL, "文本不等");
		contypestringmap.put(Condition.FIRST, "第一条数据");
		contypestringmap.put(Condition.NOTFIRST, "非第一条数据");
		contypestringmap.put(Condition.LAST, "最后一条数据");
		contypestringmap.put(Condition.NOTLAST, "非最后一条数据");
		contypestringmap.put(Condition.ODD, "序号为奇数");
		contypestringmap.put(Condition.EVEN, "序号为偶数");
		contypestringmap.put(Condition.POSITIONLESS, "序号小于");
		contypestringmap.put(Condition.POSITIONGREATER, "序号大于");
		contypestringmap.put(Condition.POSITION, "序号为");
		contypestringmap.put(Condition.LENGTHLESS, "字符数小于");
		contypestringmap.put(Condition.LENGTHGREATER, "字符数大于");
		contypestringmap.put(Condition.COUNT, "总条数为");
		contypestringmap.put(Condition.COUNTLESS, "总条数小于");
		contypestringmap.put(Condition.COUNTGREATER, "总条数大于");
		contypestringmap.put(Condition.COUNTODD, "总条数为奇数");
		contypestringmap.put(Condition.COUNTEVEN, "总条数为偶数");
		contypestringmap.put(Condition.POSITIONMOD3, "序号整除3余数为");
		contypestringmap.put(Condition.POSITIONMOD4, "序号整除4余数为");
		contypestringmap.put(Condition.POSITIONMOD5, "序号整除5余数为");
		contypestringmap.put(Condition.POSITIONMOD6, "序号整除6余数为");
		contypestringmap.put(Condition.POSITIONMOD7, "序号整除7余数为");
		contypestringmap.put(Condition.POSITIONMOD8, "序号整除8余数为");
		contypestringmap.put(Condition.POSITIONMOD9, "序号整除3余数为");
		contypestringmap.put(Condition.COUNTMOD3, "总条数整除3余数为");
		contypestringmap.put(Condition.COUNTMOD4, "总条数整除4余数为");
		contypestringmap.put(Condition.COUNTMOD5, "总条数整除5余数为");
		contypestringmap.put(Condition.COUNTMOD6, "总条数整除6余数为");
		contypestringmap.put(Condition.COUNTMOD7, "总条数整除7余数为");
		contypestringmap.put(Condition.COUNTMOD8, "总条数整除8余数为");
		contypestringmap.put(Condition.COUNTMOD9, "总条数整除10余数为");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.swing.treetable.TreeTableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		return columnnames.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.swing.treetable.TreeTableModel#getColumnName(int)
	 */
	public String getColumnName(int column)
	{
		// TODO Auto-generated method stub
		return columnnames[column];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.swing.treetable.TreeTableModel#getValueAt(java.lang
	 * .Object, int)
	 */
	public Object getValueAt(Object object, int column)
	{
		if (!(object instanceof AbstractLogicalExpressionNode))
		{
			return null;
		}
		AbstractLogicalExpressionNode node = (AbstractLogicalExpressionNode) object;
		if (column == 0)
		{
			return node;
		} else if (column == 4)
		{
			Integer it = node.getOperator();
			if (it != null)
			{
				return operatorstringmap.get(it);
			}
			return null;
		} else
		{
			if (node instanceof LogicalExpressionNode)
			{
				return "";
			} else if (node instanceof ConditionNode)
			{
				ConditionNode cnode = (ConditionNode) node;
				if (column == 1)
				{
					return cnode.getNode();
				} else if (column == 2)
				{

					return contypestringmap.get(cnode.getType());
				} else
				{
					return cnode.getParam();
				}
			}
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object parent, int index)
	{
		if (parent instanceof LogicalExpressionNode)
		{
			LogicalExpressionNode lenode = (LogicalExpressionNode) parent;
			return lenode.getChildAt(index);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object parent)
	{
		if (parent instanceof LogicalExpressionNode)
		{
			LogicalExpressionNode lenode = (LogicalExpressionNode) parent;
			return lenode.getChildCount();
		}
		return 0;
	}

	public void setValueAt(Object aValue, Object object, int column)
	{
		if (object instanceof AbstractLogicalExpressionNode)
		{

			AbstractLogicalExpressionNode node = (AbstractLogicalExpressionNode) object;
			if (column == 4)
			{
				node.setOperator((Integer) aValue);
			} else
			{
				if (node instanceof ConditionNode)
				{
					ConditionNode cnode = (ConditionNode) node;
					if (column == 1)
					{
						cnode.setNode((BindNode) aValue);
					} else if (column == 2)
					{
						cnode.setType((Integer) aValue);
					} else
					{
						if (cnode.getType() != null)
						{
							int type = cnode.getType();
							if (type >= Condition.POSITIONMOD3
									&& type <= Condition.COUNTMOD9)
							{
								if (aValue instanceof String)
								{
									try
									{
										Integer.parseInt((String) aValue);
										cnode.setParam(aValue);
									} catch (NumberFormatException e)
									{

									}
								}
							} else
							{
								cnode.setParam(aValue);
							}

						} else
						{
							cnode.setParam(aValue);
						}
					}
				}
			}
		}
	}

	public Class getColumnClass(int column)
	{
		if (column == 0)
		{
			return TreeTableModel.class;
		}
		return Object.class;
	}

	public boolean isCellEditable(Object object, int column)
	{

		if (!(object instanceof AbstractLogicalExpressionNode))
		{
			return false;
		}
		AbstractLogicalExpressionNode node = (AbstractLogicalExpressionNode) object;
		if (column == 0)
		{
			return true;
		} else if (column == 4)
		{
			AbstractLogicalExpressionNode pnode = (AbstractLogicalExpressionNode) node
					.getParent();
			if (pnode != null)
			{
				if (pnode.getChildCount() == (pnode.getIndex(node) + 1))
				{
					return false;
				} else
				{
					return true;
				}
			}
			return false;
		} else
		{
			if (node instanceof ConditionNode)
			{
				ConditionNode cnode = (ConditionNode) node;
				if (cnode.getType() == null)
				{
					return true;
				}
				int type = cnode.getType();
				if (type >= Condition.FIRST && type <= Condition.EVEN)
				{
					if (column == 2)
					{
						return true;
					} else
					{
						return false;
					}
				} else if (type >= Condition.POSITIONLESS
						&& type <= Condition.POSITION)
				{
					if (column == 1)
					{
						return false;
					} else
					{
						return true;
					}
				}
				else if (type == Condition.COUNTEVEN
						|| type == Condition.COUNTODD)
				{
					if (column == 3)
					{
						return false;
					} else
					{
						return true;
					}
				}
				else if (type >= Condition.POSITIONMOD3
						&& type <= Condition.COUNTMOD9)
				{
					if (column == 1)
					{
						return false;
					} else
					{
						return true;
					}
				}
				return true;
			} else
			{
				return false;
			}
		}
	}

	public LogicalExpressionNode getRoot()
	{
		return this.root;
	}
	// @Override
	// public boolean isLeaf(Object object)
	// {
	// boolean isleaf = true;
	// if((object instanceof
	// LogicalExpressionNode)&&((LogicalExpressionNode)object).getChildCount() >
	// 0)
	// {
	// isleaf = false;
	// }
	// return isleaf;
	// }
}
