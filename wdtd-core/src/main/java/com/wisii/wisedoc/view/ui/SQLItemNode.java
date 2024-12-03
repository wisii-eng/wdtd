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
 * @SQLItemNode.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wisii.db.model.SQLItem;
import com.wisii.db.model.SqlItemFactory;
import com.wisii.wisedoc.swing.treetable.DefaultNode;
import com.wisii.wisedoc.swing.treetable.Node;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-11-5
 */
public final class SQLItemNode extends DefaultNode implements Serializable
{
	private String sql;

	public SQLItemNode(List<SQLItem> sqlitems)
	{
		init(sqlitems);
	}
	public SQLItemNode()
	{
		
	}
	private SQLItemNode(SQLItem sqlitem)
	{
		init(sqlitem);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private void init(SQLItem sqlitem)
	{
		if (sqlitem != null)
		{
			sql = sqlitem.getSql();
			List<SQLItem> children = sqlitem.getChildren();
			if (children != null && !children.isEmpty())
			{
				for (SQLItem child : children)
				{
					SQLItemNode childnode = new SQLItemNode(child);
					addNode(childnode);
				}
			}
		}
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private void init(List<SQLItem> sqlitems)
	{
		if (sqlitems != null && !sqlitems.isEmpty())
		{
			for (SQLItem child : sqlitems)
			{
				SQLItemNode childnode = new SQLItemNode(child);
				addNode(childnode);
			}
		} else
		{
			addNode(new SQLItemNode((SQLItem) null));
		}
	}

	/**
	 * @返回 sql变量的值
	 */
	public String getSql()
	{
		return sql;
	}

	/**
	 * @param sql
	 *            设置sql成员变量的值
	 * 
	 *            值约束说明
	 */
	public void setSql(String sql)
	{
		this.sql = sql;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		Node parent = getParent();
		if (parent == null)
		{
			return "root";
		} else
		{
			Node node = this;
			String layerstring = null;
			while (parent != null)
			{
				if (layerstring == null)
				{
					layerstring = "" + parent.getIndex(node);
				} else
				{
					layerstring = parent.getIndex(node) + "." + layerstring;
				}
				node = parent;
				parent = parent.getParent();
			}
			return "sql" + layerstring;
		}
	}
	public List<SQLItem> getSqlItems()
	{
		
		return getChildren(children);
	}
	private List<SQLItem> getChildren(List<Node> children)
	{
		List<SQLItem> sqlItems = new ArrayList<SQLItem>();
		for(Node child:children)
		{
			SQLItemNode childnode=(SQLItemNode) child;
			List<Node> subchildren = child.getChildren();
			if(subchildren==null||subchildren.isEmpty())
			{
				sqlItems.add(SqlItemFactory.getSqlItem(childnode.getSql()));
			}
			else
			{
				List<SQLItem> childitems = getChildren(subchildren);
				SQLItem sqlitem = SqlItemFactory.getSqlItem(childnode.getSql());
				sqlitem.setChildren(childitems);
				sqlItems.add(sqlitem);
			}
		}
		return sqlItems;
	}
}
