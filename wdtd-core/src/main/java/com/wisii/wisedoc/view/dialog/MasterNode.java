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

import java.util.List;

import javax.swing.tree.TreeNode;

import com.wisii.wisedoc.document.attribute.SimplePageMaster;

public interface MasterNode extends TreeNode
{

	public static enum MasterType
	{
		// 单次引用
		SINGLE,
		// 多次引用
		REPEAT,
		// 特殊位置
		POSITION,
		// 条件引用首页
		CONDITION_FIRST,
		// 条件引用尾页
		CONDITION_LAST,
		// 条件引用其它页
		CONDITION_REST,
		// 条件引用奇数页
		CONDITION_ODD,
		// 条件引用偶数页
		CONDITION_EVEN,
		// 条件引用空白页
		CONDITION_BLANK,
		// 页布局序列
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
	MasterType getType();

	/**
	 * 
	 * 设置节点类型
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setType(MasterType type);

	/**
	 * 
	 * 获取引用次数
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	int getNumber();

	/**
	 * 
	 * 设置引用次数
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setNumber(int type);

	/**
	 * 
	 * 获得节点名称
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	String getName();

	/**
	 * 
	 * 设置节点名称
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setName(String value);

	/**
	 * 
	 * 获得节点引用的布局名称
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	SimplePageMaster getValue();

	/**
	 * 
	 * 设置节点引用的布局名称
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setValue(SimplePageMaster value);

	/**
	 * 
	 * 添加子节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void addChild(MasterNode child);

	/**
	 * 
	 * 添加子节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void addChild(MasterNode child, int index);

	/**
	 * 
	 * 移除子节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void removeChild(MasterNode child);

	/**
	 * 
	 * 设置父节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setParent(MasterNode parent);

	/**
	 * 
	 * 获得所有子节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	List<MasterNode> getChildren();
}
