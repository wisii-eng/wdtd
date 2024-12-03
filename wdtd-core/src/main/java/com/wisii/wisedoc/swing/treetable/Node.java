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
 * @Node.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.treetable;

import java.util.List;


/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-3
 */
public interface Node
{

	public Node getParent();

	public void setParent(Node newParent);

	public Node getChildAt(int childIndex);

	public int getChildCount();

	public int getIndex(Node node);

	/**
	 * 
	 * 用新的节点值替换原节点
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void replace(Node oldnode, Node newnode);

	public void addNode(Node node);
	public void addNode(Node node,int index);
	public void remove(Node node);
	public List<Node> getChildren();
}
