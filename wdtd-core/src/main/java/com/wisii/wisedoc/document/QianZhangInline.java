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
 * @QianZhangInline.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.Map;

import com.wisii.wisedoc.banding.BindNode;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2011-11-10
 */
public class QianZhangInline extends Inline
{
	public QianZhangInline()
	{
		super(null);
	}
	public QianZhangInline(final Map<Integer,Object> attributes) {
		super(attributes);
	}

	public QianZhangInline(String Imagefile) {
		super(null);
		QianZhang eg = new QianZhang(Imagefile);
		add(eg);
	}

	/**
	 * 
	 * 图象对象内容类型是否是图象数据
	 * 
	 * @param
	 * @return true：是图象数据，false：不是图象数据
	 * @exception
	 */
	boolean isDataType() {
		return false;
	}

	/**
	 * 
	 * 图象对象内容类型是否是图象地址数据
	 * 
	 * @param
	 * @return true：是地址数据，false：不是地址数据
	 * @exception
	 */
	boolean isUrlType() {
		return false;
	}
	protected void SetInlineContent(Object inlinecontent)
	{
		if (inlinecontent instanceof InlineContent)
		{
			getChildAt(0).setAttribute(Constants.PR_SRC,
					((InlineContent) inlinecontent).getBindNode());
		} else if (inlinecontent instanceof String
				|| inlinecontent instanceof BindNode)
		{
			getChildAt(0).setAttribute(Constants.PR_SRC, inlinecontent);
		} else if (inlinecontent == null)
		{
			getChildAt(0).setAttribute(Constants.PR_SRC, null);
		}
	}
}
