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
 * @EncryptTextInline.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.EncryptInformation;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-17
 */
public class EncryptTextInline extends Inline
{

	List<CellElement> text = new ArrayList<CellElement>();

	public EncryptTextInline()
	{
		this(null);
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public EncryptTextInline(final Map<Integer, Object> attributes)
	{
		super(attributes);
		initChildren();
	}

	public void setAttributes(Map<Integer, Object> atts, boolean isreplace)
	{
		// TODO Auto-generated method stub
		super.setAttributes(atts, isreplace);
		initChildren();
	}

	private void initChildren()
	{
		text.clear();
		String texts = createtext();
		FOText fotext = new FOText(this, texts.toCharArray());
		/* 【添加：START】by 李晓光 2008-12-12 */
		fotext.setWhole(Boolean.TRUE);
		fotext.setBind(getContent() != null);
		/* 【添加：START】by 李晓光 2008-12-12 */
		fotext.setParent(this);
		text.add(fotext);
	}

	/*
	 * 
	 * 根据属性生成日期时间显示用的字符串
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private String createtext()
	{
		EncryptInformation encry = (EncryptInformation) getAttribute(Constants.PR_ENCRYPT);
		String text = "加密区";
		if (encry != null)
		{
			text = text + encry;
		}
		return text;
	}

	@Override
	public List<CellElement> getChildren(int childIndex)
	{
		return text;
	}

	/** @see com.wisii.fov.fo.FONode#getChildNodes() */
	public ListIterator getChildNodes()
	{
		return text.listIterator();
	}
}
