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
 * @CheckBoxInline.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.WiseDocColor;

/**
 * 类功能描述：选择框的Inline对象
 * 
 * 作者：zhangqiang 创建日期：2009-12-18
 */
public class CheckBoxInline extends Inline
{
	List<CellElement> text = new ArrayList<CellElement>();
	private boolean needborder = true;

	public CheckBoxInline()
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
	public CheckBoxInline(final Map<Integer, Object> attributes)
	{
		super(null);
		setAttributes(attributes, false);
	}

	public void setAttributes(Map<Integer, Object> atts, boolean isreplace)
	{
		if (atts != null)
		{
			// border相关的属性对于选择框来说，设置无效
			for (int i = Constants.PR_BORDER; i <= Constants.PR_BORDER_WIDTH; i++)
			{
				atts.remove(Constants.PR_BORDER);
			}
			atts.remove(Constants.PR_FONT_FAMILY);
		}
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
		/* 【添加：END】by 李晓光 2008-12-12 */
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
		EnumProperty boxstyle = (EnumProperty) getAttribute(Constants.PR_CHECKBOX_BOXSTYLE);
		if (boxstyle != null
				&& boxstyle.getEnum() == Constants.EN_CHECKBOX_BOXSTYLE_CIRCLE)
		{
			needborder = false;
		} else
		{
			needborder = true;
		}
		EnumProperty tickmark = (EnumProperty) getAttribute(Constants.PR_CHECKBOX_TICKMARK);
		if (tickmark != null
				&& tickmark.getEnum() == Constants.EN_CHECKBOX_TICKMARK_DOT)
		{
			return "" + (char) 0xF09D;
		} else
		{
			return "" + (char) 0xF050;
		}
	}

	@Override
	public List<CellElement> getChildren(int childIndex)
	{
		return text;
	}

	public ListIterator getChildNodes()
	{
		return text.listIterator();
	}

	@Override
	public Object getAttribute(int key)
	{
		if (key == Constants.PR_FONT_FAMILY)
		{
			return "Wingdings 2";
		} else if (key >= Constants.PR_BORDER
				&& key <= Constants.PR_BORDER_WIDTH)
		{
			if (!needborder)
			{
				return null;
			} else
			{
				if (key == Constants.PR_BORDER_AFTER_COLOR
						|| key == Constants.PR_BORDER_BEFORE_COLOR
						|| key == Constants.PR_BORDER_START_COLOR
						|| key == Constants.PR_BORDER_END_COLOR)
				{
					int layer = 0;
					Object layerobj = super.getAttribute(Constants.PR_GRAPHIC_LAYER);
					if(layerobj instanceof Integer)
					{
						layer = (Integer)layerobj;
					}
					return new WiseDocColor(Color.BLACK, layer);
				} else if (key == Constants.PR_BORDER_AFTER_STYLE
						|| key == Constants.PR_BORDER_BEFORE_STYLE
						|| key == Constants.PR_BORDER_START_STYLE
						|| key == Constants.PR_BORDER_END_STYLE)
				{
					return new EnumProperty(Constants.EN_SOLID, "");
				} else if (key == Constants.PR_BORDER_AFTER_WIDTH
						|| key == Constants.PR_BORDER_BEFORE_WIDTH
						|| key == Constants.PR_BORDER_START_WIDTH
						|| key == Constants.PR_BORDER_END_WIDTH)
				{
					return new CondLengthProperty(new FixedLength(0.5d, "pt"),
							false);
				} else
				{
					return null;
				}
			}
		} else
		{
			return super.getAttribute(key);
		}
	}

	@Override
	public boolean isEditSetEnable()
	{
		return false;
	}
}
