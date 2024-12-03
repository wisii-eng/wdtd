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
/* $Id: PageNumberCitation.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.document;

import java.util.Map;

import com.wisii.wisedoc.document.attribute.CommonTextDecoration;

/**
 * 
 * 类功能描述：页码引用对象
 * 
 * 作者：zhangqiang 创建日期：2008-11-5
 */
public class PageNumberCitation extends Inline
{
	private CommonTextDecoration commontextdecoration;
	public PageNumberCitation()
	{
		this(null);
	}

	/**
	 * @param parent
	 *            FONode that is the parent of this object
	 */
	public PageNumberCitation(final Map<Integer,Object> att)
	{
		super(att);
	}
	public void initFOProperty()
	{
		super.initFOProperty();
		if(commontextdecoration == null)
		{
			commontextdecoration = new CommonTextDecoration(this);
		}
		else
		{
			commontextdecoration.init(this);
		}
	}
	/** @return the "text-decoration" property. */
	public CommonTextDecoration getTextDecoration()
	{
		return commontextdecoration;
	}

	public String getRefId()
	{
		return (String) getAttribute(Constants.PR_REF_ID);
	}
}
