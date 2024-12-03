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
 * @Sort.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import com.wisii.wisedoc.banding.BindNode;

/**
 * 排序信息类
 * 
 * 作者：zhangqiang 创建日期：2007-8-15
 */
public final class Sort implements Cloneable
{
	// 要排序节点
	private BindNode node;

	// 语言
	private String _language;

	// 数据类型
	private int _datatype;

	// 排序顺序
	private int _order;

	// 大小写顺序
	private int _caseorder;

	// 升序静态变量
	public static final int ASCENDING = 0;

	// 降序静态变量
	public static final int DESCENDING = 1;

	// 大写先
	public static final int UPPERFIRST = 0;

	// 小写先
	public static final int LOWERFIRST = 1;

	// 文本类型数据
	public static final int TEXT = 0;

	// 数字类型数据
	public static final int NUMBER = 1;

	// qualified name属性数据
	public final static int QNAMEBUTNOTNCNAME = 2;

	private Sort(BindNode node, String language, int datatype, int order,
			int caseorder)
	{
		this.node = node;
		_language = language;
		_datatype = datatype;
		_order = order;
		_caseorder = caseorder;

	}

	public static Sort instance(BindNode node, String language, int datatype,
			int order, int caseorder)
	{
		Sort sort = null;
		if (node != null)
		{
			if (language == null)
			{
				language = "en";
			}
			language = language.trim();
			if (NUMBER != datatype && QNAMEBUTNOTNCNAME != datatype
					&& TEXT != datatype)
			{
				datatype = TEXT;
			}

			if (ASCENDING != order && DESCENDING != order)
			{
				order = ASCENDING;
			}
			if (UPPERFIRST != caseorder && LOWERFIRST != caseorder)
			{
				caseorder = UPPERFIRST;
			}
			sort = new Sort(node, language, datatype, order, caseorder);
		}
		return sort;
	}

	/**
	 * 
	 * 获得排序xpath信息
	 */
	public BindNode getNode()
	{
		return node;
	}

	/**
	 * 
	 * 获得排序语言
	 */
	public String getLanguage()
	{
		return _language;
	}

	/**
	 * 
	 * 获得数据类型
	 */
	public int getDataType()
	{
		return _datatype;
	}

	/**
	 * 
	 * 获得排序顺序
	 */
	public int getOrder()
	{
		return _order;
	}

	public int getCaseOrder()
	{
		return _caseorder;
	}

	/**
	 * 深克隆对象
	 */
	public Sort clone()
	{
		return new Sort(node, _language, _datatype, _order, _caseorder);
	}

	/**
	 * 
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Sort) || obj == null)
		{
			return false;
		}
		Sort sort = (Sort) obj;
		return node.equals(sort.node)
				&& (_language == null ? sort._language == null : _language
						.equals(sort._language)) && _datatype == sort._datatype
				&& _order == sort._order && _caseorder == sort._caseorder;
	}
}
