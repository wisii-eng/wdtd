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

package com.wisii.wisedoc.view.ui;

import java.util.List;
import com.wisii.db.model.SQLItem;
import com.wisii.wisedoc.swing.treetable.AbstractTreeTableModel;
import com.wisii.wisedoc.swing.treetable.TreeTableModel;

/**
 * 类功能描述：条件表达式TreeTableModel
 * 
 * 作者：zhangqiang 创建日期：2008-12-3
 */
public final class SQLTreeTableModel extends AbstractTreeTableModel
		implements TreeTableModel
{
	private SQLItemNode root;
	private String[] columnnames =
	{ "", "SQL" };

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public SQLTreeTableModel(List<SQLItem> sqlitems)
	{
		super(new SQLItemNode(sqlitems));
		root = (SQLItemNode) super.root;
	}
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public SQLTreeTableModel(SQLItemNode node)
	{
		super(node);
		root = (SQLItemNode) super.root;
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
		if (!(object instanceof SQLItemNode))
		{
			return null;
		}
		SQLItemNode sqlnode = (SQLItemNode) object;

		return sqlnode.getSql();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object parent, int index)
	{
		if (parent instanceof SQLItemNode)
		{
			SQLItemNode lenode = (SQLItemNode) parent;
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
		if (parent instanceof SQLItemNode)
		{
			SQLItemNode lenode = (SQLItemNode) parent;
			return lenode.getChildCount();
		}
		return 0;
	}

	public void setValueAt(Object aValue, Object object, int column)
	{
		if (object instanceof SQLItemNode)
		{

			SQLItemNode node = (SQLItemNode) object;
			node.setSql((String) aValue);
		}
	}

	public Class getColumnClass(int column)
	{
		if (column == 0)
		{
			return TreeTableModel.class;
		}
		return String.class;
	}

	public boolean isCellEditable(Object object, int column)
	{
		if(column==0)
		{
			return true;
		}
		else{
		return false;
		}
	}

	public SQLItemNode getRoot()
	{
		return this.root;
	}
}
