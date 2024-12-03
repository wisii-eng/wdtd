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
 * @HtmlContext.java
 *                   北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html;

import java.util.ArrayList;
import java.util.List;

import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.io.xsl.util.StyleUtil;
import com.wisii.wisedoc.io.xsl.util.XslContext;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang
 * 创建日期：2012-5-30
 */
public class HtmlContext extends XslContext
{
	private List<String> stylelist = new ArrayList<String>();

	public void addStyle(String style)
	{
		if (style != null && !stylelist.contains(style))
		{
			stylelist.add(style);
		}
	}

	/**
	 * @返回 stylelist变量的值
	 */
	public List<String> getStylelist()
	{
		return stylelist;
	}

	public String getStyleClass(Attributes att)
	{
		if (att == null)
		{
			return null;
		}
		if (!StyleUtil.hasStylekey(att))
		{
			return null;
		}
		int index = attributelist.indexOf(att);
		if (index != -1)
		{
			return "wisestle" + index;
		} else
		{
			addAttribute(att);
			return "wisestle" + (attributelist.size() - 1);
		}
	}
}
