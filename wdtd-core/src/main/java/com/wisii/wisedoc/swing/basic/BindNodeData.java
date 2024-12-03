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
 * @BindNodeData.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.basic;

import java.io.Serializable;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.BindNodeUtil;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.Document;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-12-27
 */
public final class BindNodeData implements Serializable
{
	private final String name;
	private final String xpath;
	public BindNodeData(BindNode node)
	{
		name = node.getNodeName();
		xpath = node.getXPath();
	}
	/**
	 * @返回 name变量的值
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @返回 xpath变量的值
	 */
	public String getXpath()
	{
		return xpath;
	}
	public BindNode getBindNode()
	{
		DataStructureTreeModel model = null;
		Document doc = SystemManager.getCurruentDocument();
		if (doc != null)
		{
			model = doc.getDataStructure();
		}
		return BindNodeUtil.getNodeOfPath(xpath, model);

	}
}
