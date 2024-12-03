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
 * @SqlBindNode.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding;

import java.util.Map;

/**
 * 类功能描述：
 *由一条sql语句生成的节点
 *用来包装该sql语句生成的数据节点
 * 作者：zhangqiang
 * 创建日期：2010-10-28
 */
public class SqlBindNode extends DefaultBindNode
{
	private String sql;

	public SqlBindNode(BindNode parent, int datatype, int length,
			String nodeName, Map<Integer, Object> otherattr, String sql)
	{
		super(parent, datatype, length, nodeName, otherattr);
		this.sql = sql;
	}

	public SqlBindNode(BindNode parent, int datatype, int length,
			String nodeName, String sql)
	{
		super(parent, datatype, length, nodeName);
		this.sql = sql;
	}

	/**
	 * @返回  sql变量的值
	 */
	public String getSql()
	{
		return sql;
	}

}
