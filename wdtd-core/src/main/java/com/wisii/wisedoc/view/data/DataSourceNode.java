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

package com.wisii.wisedoc.view.data;

import java.util.List;

import javax.swing.tree.TreeNode;

import com.wisii.db.ConnectionSeting;

public interface DataSourceNode extends TreeNode
{

	public static enum NodeType
	{
		// 数据源节点
		DATASOUREITEM,
		// 根节点
		ROOT;
	}

	/**
	 * 
	 * 获取节点类型
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	NodeType getNodetype();

	/**
	 * 
	 * 设置节点类型
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setNodetype(NodeType type);

	/**
	 * 
	 * 添加子节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void addChild(DataSourceNode child);

	/**
	 * 
	 * 添加子节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void addChild(DataSourceNode child, int index);

	/**
	 * 
	 * 获取所有子元素
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	List<DataSourceNode> getChildren();

	/**
	 * 
	 * 设置数据源
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setValue(ConnectionSeting value);

	/**
	 * 
	 * 获取数据源
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	ConnectionSeting getValue();

	/**
	 * 
	 * 设置数据源
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setParent(DataSourceNode value);

	/**
	 * 
	 * 获取父节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	DataSourceNode getParent();

	/**
	 * 
	 * 删除所有子节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void reMoveAllChildren();

	/**
	 * 
	 * 删除指定子节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void reMoveChildren(DataSourceNode child);
}
