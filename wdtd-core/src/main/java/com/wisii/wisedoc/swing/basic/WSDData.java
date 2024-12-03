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
 * @WSDData.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.basic;

import java.io.Serializable;
import java.util.List;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.TransferIOUtil;

/**
 * 类功能描述：
 * 用于复制粘贴用的数据对象类
 * 作者：zhangqiang
 * 创建日期：2010-12-24
 */
public final class WSDData implements Serializable
{
	// 纯文本数据
	private final String text;
	// 带Designer可解析的样式信息的文本数据
	private final String plaintext;

	public WSDData(String text, String plaintext)
	{
		this.text = text;
		this.plaintext = plaintext;
	}

	/**
	 * @返回 text变量的值
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @返回 plaintext变量的值
	 */
	public String getPlaintext()
	{
		return plaintext;
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
				+ ((plaintext == null) ? 0 : plaintext.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		WSDData other = (WSDData) obj;
		if (plaintext == null)
		{
			if (other.plaintext != null)
				return false;
		} else if (!plaintext.equals(other.plaintext))
			return false;
		if (text == null)
		{
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public List<CellElement> getElements()
	{
      return getElements(true);
	}
	public List<CellElement> getElementsWithoutbindData()
	{
		return getElements(false);
	}
	private List<CellElement> getElements(boolean withbinddata)
	{
		DataStructureTreeModel model = null;
		if (withbinddata)
		{
			Document doc = SystemManager.getCurruentDocument();
			if (doc != null)
			{
				model = doc.getDataStructure();
			}
		}
		return (List<CellElement>) TransferIOUtil.getTransferObject(plaintext,model);
	}
}
