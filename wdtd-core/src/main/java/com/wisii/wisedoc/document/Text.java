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
 * @Text.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;

/**
 * 类功能描述：文本内容对象，可以支持静态字符串形式的文本内容 也可以是动态数据节点作为文本内容
 * 
 * 作者：zhangqiang 创建日期：2008-8-18
 */
public final class Text implements InlineContent
{
	private char _text;

	private final boolean _isbindtext;

	private final BindNode _bindnode;

	public Text(final char c)
	{
		_text = c;
		_isbindtext = false;
		_bindnode = null;
	}

	public Text(final BindNode bindnode)
	{
		_bindnode = bindnode;
		_isbindtext = true;
	}

	/**
	 * 
	 * 是否是动态数据节点判断接口
	 * 
	 * @param
	 * @return true：是动态数据节点，false：不是动态数据节点（即静态文本）
	 * @exception
	 */
	public boolean isBindContent()
	{
		return _isbindtext;
	}

	/**
	 * 
	 * 获得动态数据节点 注意：如果不是绑定文本的话，该方法返回的是空
	 * 
	 * @param
	 * @return：动态数据节点
	 * @exception
	 */
	public BindNode getBindNode()
	{
		return _bindnode;
	}

	/**
	 * 
	 * 获得静态文本，注意Desigern2.0一个字符为一个Inline， 因此返回的是一个字符
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public String getText()
	{
		if (_isbindtext)
		{
			String nodename = _bindnode.getNodeName();
			int index =nodename.indexOf(':');
			if(index>-1)
			{
				nodename = nodename.substring(index+1);
			}
			if (_bindnode instanceof AttributeBindNode)
			{
				return "@" + nodename;
			} else
			{
				return nodename;
			}
		} else
		{
			return String.valueOf(_text);
		}
	}
	@Override
	public InlineContent clone()
	{
		if(_isbindtext){
			return new Text(_bindnode);
		}
		else
		{
			return new Text(_text);
		}
	}
}
