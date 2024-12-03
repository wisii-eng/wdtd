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
 * @DataStructureCellRender.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import com.wisii.wisedoc.banding.BindNode;


/**
 * 类功能描述：
 * 
 * 作者：李晓光 创建日期：2008-9-10
 */
@SuppressWarnings("serial")
public class DataStructureCellRender implements TreeCellRenderer
{
	private Map<Integer, BindNodeComponent> componentmap = new HashMap<Integer, BindNodeComponent>();

	public DataStructureCellRender()
	{
		init();
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private void init()
	{
		componentmap.put(BindNode.DATE, new BindNodeComponent(BindNode.DATE));
		componentmap.put(BindNode.TIME, new BindNodeComponent(BindNode.TIME));
		componentmap.put(BindNode.DECIMAL, new BindNodeComponent(BindNode.DECIMAL));
		componentmap.put(BindNode.INTEGER, new BindNodeComponent(BindNode.INTEGER));
		componentmap.put(BindNode.BOOLEAN, new BindNodeComponent(BindNode.BOOLEAN));
		componentmap.put(BindNode.STRING, new BindNodeComponent(BindNode.STRING));

	}
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus)
	{
		BindNodeComponent componen = componentmap.get(((BindNode)value).getDataType());
		return componen.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
	}
}
