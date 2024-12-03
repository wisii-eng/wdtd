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
 * @InlineLevel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.Color;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonFont;
import com.wisii.wisedoc.document.attribute.CommonMarginInline;
import com.wisii.wisedoc.document.attribute.SpaceProperty;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-28
 */
public class InlineLevel extends DefaultElement
{

	// 边框，背景，padding等属性
	private CommonBorderPaddingBackground commonbpbackground;
	private CommonFont commonfont;
	private CommonMarginInline commonmargininline;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public InlineLevel()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public InlineLevel(final Map<Integer, Object> attributes)
	{
		super(attributes);
		// TODO Auto-generated constructor stub
	}

	public void initFOProperty()
	{
		super.initFOProperty();
		if (commonbpbackground == null)
		{
			commonbpbackground = new CommonBorderPaddingBackground(this);
		} else
		{
			commonbpbackground.init(this);
		}
		if (commonfont == null)
		{
			commonfont = new CommonFont(this);
		} else
		{
			commonfont.init(this);
		}
		if (commonmargininline == null)
		{
			commonmargininline = new CommonMarginInline(this);
		} else
		{
			commonmargininline.init(this);
		}
	}

	public CommonMarginInline getCommonMarginInline()
	{
		return commonmargininline;
	}

	/**
	 * @return the Common Border, Padding, and Background Properties.
	 */
	public CommonBorderPaddingBackground getCommonBorderPaddingBackground()
	{
		return commonbpbackground;
	}

	public CommonFont getCommonFont()
	{
		return commonfont;
	}

	public Color getColor()
	{
		return (Color) getAttribute(Constants.PR_COLOR);
	}

	/**
	 * @return the "line-height" property
	 */
	public SpaceProperty getLineHeight()
	{
		return (SpaceProperty) getAttribute(Constants.PR_LINE_HEIGHT);
	}
}
