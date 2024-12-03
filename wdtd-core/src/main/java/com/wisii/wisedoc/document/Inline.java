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
 * @Inline.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.area.inline.TextArea;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-4-14
 */
public class Inline extends InlineLevel implements EditSetAble
{
	protected InlineContent inlinecontent;

	public Inline()
	{
		super();
	}
	public Inline(final Map<Integer,Object> attributes)
	{
		super(attributes);
	}

	/**
	 * 
	 * 获得Inline的内容的接口
	 * 
	 * @param
	 * @return Inline对象的内容
	 * @exception
	 */
	public InlineContent getContent()
	{
		return inlinecontent;
	}

	protected void SetInlineContent(Object inlinecontent)
	{
		if (inlinecontent instanceof InlineContent)
		{
			this.inlinecontent = (InlineContent) inlinecontent;
		}
	}
	public String getText()
	{
		return "";
	}

	/**
	 * Return the "id" property.
	 */
	public String getId()
	{
		return (String) getAttribute(Constants.PR_ID);
	}

	/**
	 * @return the "alignment-adjust" property
	 */
	public Length getAlignmentAdjust()
	{
		return ((EnumLength) getAttribute(Constants.PR_ALIGNMENT_ADJUST))
				.getLength();
	}

	/**
	 * @return the "alignment-baseline" property
	 */
	public int getAlignmentBaseline()
	{
		return ((EnumProperty) getAttribute(Constants.PR_ALIGNMENT_BASELINE))
				.getEnum();
	}

	/**
	 * @return the "baseline-shift" property
	 */
	public Length getBaselineShift()
	{
		return ((Length) getAttribute(Constants.PR_BASELINE_SHIFT));
	}

	/**
	 * @return the "dominant-baseline" property
	 */
	public int getDominantBaseline()
	{
		return ((EnumProperty) getAttribute(Constants.PR_DOMINANT_BASELINE))
				.getEnum();
	}

	/** @see com.wisii.fov.fo.FONode#getLocalName() */
	public String getLocalName()
	{
		return "inline";
	}

	public int getNameId()
	{
		return Constants.FO_INLINE;
	}

	public void setAttributes(Map<Integer, Object> atts, boolean isreplace)
	{
		// 清除掉PR_INLINE_CONTENT属性
		if (atts != null && atts.containsKey(Constants.PR_INLINE_CONTENT))
		{
			atts = new HashMap<Integer, Object>(atts);
			SetInlineContent(atts
					.get(Constants.PR_INLINE_CONTENT));
			atts.remove(Constants.PR_INLINE_CONTENT);
		}
		super.setAttributes(atts, isreplace);
	}

	/* 【添加：START】by 李晓光 2008-11-5 */
	private TextArea area = null;

	public TextArea getArea()
	{
		return area;
	}

	public void setArea(TextArea area)
	{
		this.area = area;
	}
	/* 【添加：END】by 李晓光 2008-11-5 */
	public Element clone()
	{
		Inline inline = (Inline) super.clone();
		if (inlinecontent != null)
		{
			inline.inlinecontent = inlinecontent.clone();
		}
		return inline;
	}
	/**
	 * 如果绑定有动态数据节点，则可设置编辑相关属性
	 */
	@Override
	public boolean isEditSetEnable()
	{
		if (inlinecontent == null)
		{
			return false;
		} else
		{
			return inlinecontent.isBindContent();
		}
	}
}
