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
 * @NumberContent.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import com.wisii.wisedoc.banding.BindNode;

/**
 * 类功能描述：数据类型的内容
 *
 * 作者：zhangqiang
 * 创建日期：2009-5-21
 */
public class NumberContent implements InlineContent
{

	private Number _number;

	private boolean _isbindtext;

	private BindNode _bindnode;

	public NumberContent(Number text)
	{
		_number = text;
		_isbindtext = false;
		_bindnode = null;
	}

	public NumberContent(BindNode bindnode)
	{
		_bindnode = bindnode;
		_isbindtext = true;
	}
	/**
	 * @返回  _number变量的值
	 */
	public final Number getNumber()
	{
		return _number;
	}
	@Override
	public boolean isBindContent()
	{
		return _isbindtext;
	}

	public String getText()
	{
		if (isBindContent())
		{
			return _bindnode.toString();
		} else
		{
			return _number.toString();
		}
	}

	public BindNode getBindNode()
	{
		return _bindnode;
	}

	public InlineContent clone()
	{
		if (_isbindtext)
		{
			return new NumberContent(_bindnode);
		} else
		{
			return new NumberContent(_number);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_bindnode == null) ? 0 : _bindnode.hashCode());
		result = prime * result + (_isbindtext ? 1231 : 1237);
		result = prime * result + ((_number == null) ? 0 : _number.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumberContent other = (NumberContent) obj;
		if (_bindnode == null)
		{
			if (other._bindnode != null)
				return false;
		} else if (!_bindnode.equals(other._bindnode))
			return false;
		if (_isbindtext != other._isbindtext)
			return false;
		if (_number == null)
		{
			if (other._number != null)
				return false;
		} else if (!_number.equals(other._number))
			return false;
		return true;
	}
}