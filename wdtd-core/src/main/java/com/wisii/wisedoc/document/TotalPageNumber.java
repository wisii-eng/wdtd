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
 * @TotalPageNumber.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.Map;

import com.wisii.wisedoc.document.attribute.EnumProperty;

/**
 * 类功能描述：总页码对象
 * 
 * 作者：zhangqiang 创建日期：2008-11-5
 */
public class TotalPageNumber extends PageNumberCitationLast
{

	public TotalPageNumber()
	{
		this(null);

	}

	public TotalPageNumber(final Map<Integer, Object> att)
	{
		super(att);

	}

	/**
	 * 返回主流中的最后一个对象的id
	 */
	public String getRefId()
	{
		EnumProperty enumend = (EnumProperty) getAttribute(Constants.PR_ENDOFALL);
		if (enumend != null)
		{
			if (enumend.getEnum() == Constants.EN_FALSE)
			{
				CellElement parent = (CellElement) getParent();
				while (parent != null && !(parent instanceof PageSequence))
				{
					parent = (CellElement) parent.getParent();
				}
				if (parent instanceof PageSequence)
				{
					PageSequence ps = (PageSequence) parent;
					return DefaultElement.IDLAST
							+ Integer.toHexString(ps.getMainFlow().hashCode());
				}
			} else
			{
				Element parent = getParent();
				while (parent != null && !(parent instanceof Document))
				{
					parent = parent.getParent();
				}
				if (parent instanceof Document)
				{
					Document doc = (Document) parent;
					PageSequence ps = (PageSequence) doc.getChildAt(doc
							.getChildCount() - 1);
					return DefaultElement.IDLAST
							+ Integer.toHexString(ps.getMainFlow().hashCode());
				}
			}

		}
		return "";
	}

}
