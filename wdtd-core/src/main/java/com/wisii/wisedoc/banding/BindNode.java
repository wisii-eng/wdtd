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
 * @BindNode.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding;

import java.util.Vector;

import javax.swing.tree.TreeNode;

/**
 * 类功能描述：动态数据节点类
 *
 * 作者：zhangqiang
 * 创建日期：2008-8-18
 */
public interface BindNode extends TreeNode
{
	//	字符串数据类型
	public static final int STRING = 0;

	// 日期时间数据类型
	public static final int DATE = 1;
	//十进制数
	public static final int TIME = 2;

	//十进制数
	public static final int DECIMAL = 3;
	//十进制数
	public static final int INTEGER = 4;
	//十进制数
	public static final int BOOLEAN = 5;
	
//	数据类型数，增加一种数据类型，该长度值得加一
	public static final int DATATYPECOUNT = 6;
	//无限制长度标识
	public static final int UNLIMT = -1;
//	必须为多个节点
	public static final int MULTICOUNT = 1;
//	节点数不确定
	public static final int NOLIMT = 2;
//	
	public static final int ONE = 3;

	/**
	 * 
	 * 获取数据类型
	 *
	 * @param     
	 * @return      
	 * @exception   
	 */
	int getDataType();
	/**
	 * 
	 * 设置数据类型
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setDataType(int type);

	/**
	 * 
	 * 获取数据长度
	 *
	 * @param     
	 * @return  是以字符为单位的数据长度， UNLIMT(即-1)标识无限制长度 
	 * @exception  
	 */
	int getLength();
	/**
	 * 
	 * 设置数据长度
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setLength(int length);
	/**
	 * 
	 * 获得节点数限制属性
	 *
	 * @param   
	 * @return    
	 * @exception  
	 */
	int getNodeCount();
	/**
	 * 
	 * 设置节点数约束
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	void setNodeCount(int nodecount);
	/**
	 * 
	 *获得指定的属性的属性值，扩展用
	 *
	 * @param     
	 * @return     
	 * @exception   
	 */
	Object getAttribute(int key);
	/**
	 * 
	 * 设置指定属性
	 *
	 * @param     
	 * @return     
	 * @exception  
	 */
	void setAttribute(int key,Object value);
	


	/**
	 * 
	 * 获得数据的XPath值
	 *
	 * @param     
	 * @return xpath字符串   
	 * @exception  
	 */
	String getXPath();
	public void setChildren(Vector children);

	public void addChild(BindNode child);

	public void removeChild(BindNode child);
	public void setParent(BindNode parent);
	public BindNode getChildAt(int childIndex);
	BindNode getParent();
	public String getNodeName();
	public boolean xpathEqual(Object obj);

}
