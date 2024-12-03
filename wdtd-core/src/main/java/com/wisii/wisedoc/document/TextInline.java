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
 * @TextInline.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.Map;
/**
 * 类功能描述：文本类型的Inline对象
 * 
 * 作者：zhangqiang 创建日期：2008-4-21
 */
public class TextInline extends Inline
{
	// // 文本内容

	public TextInline(final Text content, final Map<Integer,Object> attributes)
	{
		super(attributes);
		inlinecontent = content;
		// setAttribute(Constants.FO_CONTENT, content);
		final FOText text = new FOText(this, content.getText().toCharArray());
		/* 【添加：START】 by 李晓光 2008-12-10 */
		text.setWhole(content.isBindContent());
		text.setBind(getContent() != null);
		/* 【添加：END】 by 李晓光 2008-12-10 */
		add(text);
	}

	@Override
	public Text getContent()
	{
		return (Text) inlinecontent;
	}

	@Override
	public String getText()
	{
		return getContent().getText();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "text="+getText();
	}
}
