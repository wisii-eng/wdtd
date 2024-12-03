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
 * CommonMarginInline.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.document.attribute;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-28
 */
public final class CommonMarginInline extends AbstractCommonAttributes
{

	/**
	 * The "margin-top" property.
	 */
	private Length marginTop;

	/**
	 * The "margin-bottom" property.
	 */
	private Length marginBottom;

	/**
	 * The "margin-left" property.
	 */
	private Length marginLeft;

	/**
	 * The "margin-right" property.
	 */
	private Length marginRight;

	/**
	 * The "space-start" property.
	 */
	private SpaceProperty spaceStart;

	/**
	 * The "space-end" property.
	 */
	private SpaceProperty spaceEnd;

	public CommonMarginInline(Length margintop, Length marginbottom,
			Length marginleft, Length marginright, SpaceProperty spacestart,
			SpaceProperty spaceend)
	{
		super(null);
		marginTop = margintop;
		marginBottom = marginbottom;
		marginLeft = marginleft;
		marginRight = marginright;
		spaceStart = spacestart;
		spaceEnd = spaceend;
	}

	public CommonMarginInline(CellElement cellelement)
	{
		super(cellelement);
		init(cellelement);
	}

	/**
	 * Create a CommonMarginInline object.
	 * 
	 * @param pList
	 *            The PropertyList with propery values.
	 */
	public void init(CellElement cellelement)
	{
		super.init(cellelement);
		marginTop = (Length) getAttribute(Constants.PR_MARGIN_TOP);
		marginBottom = (Length) getAttribute(Constants.PR_MARGIN_BOTTOM);
		marginLeft = (Length) getAttribute(Constants.PR_MARGIN_LEFT);
		marginRight = (Length) getAttribute(Constants.PR_MARGIN_RIGHT);
		spaceStart = (SpaceProperty) getAttribute(Constants.PR_SPACE_START);
		spaceEnd = (SpaceProperty) getAttribute(Constants.PR_SPACE_END);
	}

	/**
	 * @返回 marginTop变量的值
	 */
	public final Length getMarginTop()
	{
		return marginTop;
	}

	/**
	 * @返回 marginBottom变量的值
	 */
	public final Length getMarginBottom()
	{
		return marginBottom;
	}

	/**
	 * @返回 marginLeft变量的值
	 */
	public final Length getMarginLeft()
	{
		return marginLeft;
	}

	/**
	 * @返回 marginRight变量的值
	 */
	public final Length getMarginRight()
	{
		return marginRight;
	}

	/**
	 * @返回 spaceStart变量的值
	 */
	public final SpaceProperty getSpaceStart()
	{
		return spaceStart;
	}

	/**
	 * @返回 spaceEnd变量的值
	 */
	public final SpaceProperty getSpaceEnd()
	{
		return spaceEnd;
	}
}
