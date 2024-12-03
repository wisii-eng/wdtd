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
package com.wisii.wisedoc.document;

import com.wisii.wisedoc.banding.BindNode;

public class BarCodeText implements InlineContent
{

	private String _text;

	private final boolean _isbindtext;

	private final BindNode _bindnode;

	public BarCodeText(final String text)
	{
		_text = text;
		_isbindtext = false;
		_bindnode = null;
	}

	public BarCodeText(final BindNode bindnode)
	{
		_bindnode = bindnode;
		_isbindtext = true;
	}

	@Override
	public boolean isBindContent()
	{
		return _isbindtext;
	}

	public String getText()
	{
		return _text;
	}
	@Override
	public String toString() {
		if(!isBindContent()){
			return _text;
		} else {
			return _bindnode + "";
		}
	}
	public BindNode getBindNode()
	{
		return _bindnode;
	}
	@Override
	public InlineContent clone()
	{
		if (_isbindtext)
		{
			return new BarCodeText(_bindnode);
		} else
		{
			return new BarCodeText(_text);
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
		result = prime * result + ((_text == null) ? 0 : _text.hashCode());
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
		BarCodeText other = (BarCodeText) obj;
		if (_bindnode == null)
		{
			if (other._bindnode != null)
				return false;
		} else if (!_bindnode.equals(other._bindnode))
			return false;
		if (_isbindtext != other._isbindtext)
			return false;
		if (_text == null)
		{
			if (other._text != null)
				return false;
		} else if (!_text.equals(other._text))
			return false;
		return true;
	}
	
}
