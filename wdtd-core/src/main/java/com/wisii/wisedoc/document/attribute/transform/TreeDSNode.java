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

import java.util.List;

import javax.swing.tree.TreeNode;

public interface TreeDSNode extends TreeNode
{

	// 元素节点
	public static final int ELEMENT = 0;

	// 属性节点
	public static final int ATTRIBUTE = 1;

	// 文本节点
	public static final int TEXT = 2;

	/**
	 * 
	 * 获取节点类型
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	int getType();

	/**
	 * 
	 * 设置数据类型
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setType(int type);

	// /**
	// *
	// *获得指定的属性的属性值，扩展用
	// *
	// * @param
	// * @return
	// * @exception
	// */
	// String getAttribute(String key);
	//
	// /**
	// *
	// * 设置指定属性
	// *
	// * @param
	// * @return
	// * @exception
	// */
	// void setAttribute(String key, String value);

	/**
	 * 
	 * 获得节点名称
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	String getName();

	String getValue();

	void setName(String name);

	void setValue(String value);

	public void setChildren(List<TreeDSNode> children);

	public void addChild(TreeDSNode child);

	public void removeChild(TreeDSNode child);

	public void setParent(TreeDSNode parent);

	public TreeDSNode getChildAt(int childIndex);

	public TreeDSNode getParent();

	public TreeDSNode clone();

	public Object getElementItem(ElementItem parent);
}
