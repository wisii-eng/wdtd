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
 * @TableContent.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.attribute.AttributeManager;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.LengthBase;

/**
 * 类功能描述：目录对象
 *
 * 作者：zhangqiang
 * 创建日期：2009-4-10
 */
public class TableContents extends BlockContainer
{
	private  boolean isdoctablecontents = true;
	public TableContents()
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
	public TableContents(final Map<Integer,Object> attributes)
	{
		super(attributes);
		initatt();
	
	}
	public TableContents(boolean isdoctablecontents)
	{
		this();
		this.isdoctablecontents = isdoctablecontents;
	}
	private void initatt()
	{
		Map<Integer,Object> attmap = new HashMap<Integer,Object>();
		attmap.put(Constants.PR_ABSOLUTE_POSITION, new EnumProperty(
				Constants.EN_RELATIVE, ""));
		int maxlevel = ConfigureUtil.getMaxBlockLevel();
		List<Attributes> levelattributes = new ArrayList<Attributes>();
		EnumProperty justenum = new EnumProperty(Constants.EN_JUSTIFY,"justify");
		for(int i = 0;i < maxlevel;i++)
		{
			Map<Integer,Object> blockatt = new HashMap<Integer, Object>();
			blockatt.put(Constants.PR_TEXT_ALIGN_LAST,justenum);
			if(i > 0)
			{
				blockatt.put(Constants.PR_START_INDENT, new FixedLength(12.0f*i,"pt"));
			}
			levelattributes.add(AttributeManager.getAttributeContainer(Block.class).getAttribute(blockatt));
		}
		attmap.put(Constants.PR_BLOCK_REF_STYLES, levelattributes);
		attmap.put(Constants.PR_BLOCK_REF_SHOW_LEVEL, 2);
		attmap.put(Constants.PR_BLOCK_REF_NUMBER, true);
		attmap.put(Constants.PR_BLOCK_REF_RIGHT_ALIGN, true);
		attmap.put(Constants.PR_LEADER_PATTERN, new EnumProperty(Constants.EN_DOTS,""));
		attmap.put(Constants.PR_BASELINE_SHIFT, new PercentLength(0.2,new LengthBase(LengthBase.CUSTOM_BASE)));
		LengthRangeProperty lenrange = new LengthRangeProperty(new FixedLength(
				1.0, "pt"), new FixedLength(100.0, "cm"), new FixedLength(1000.0, "cm"));
		attmap.put(Constants.PR_LEADER_LENGTH,lenrange);
		setAttributes(attmap, false);
	}
	/**
	 * 
	 * 获得指定级别的目录的属性
	 *
	 * @param    
	 * @return     
	 * @exception 
	 */
	public Attributes getAttributesofLevel(int level)
	{
		List<Attributes> levelattributes = (List<Attributes>) getAttribute(Constants.PR_BLOCK_REF_STYLES);
		return levelattributes.get(level);
	}
	public Attributes getAttributesofLeader()
	{
		Map<Integer, Object> att = new HashMap<Integer, Object>();
		att.put(Constants.PR_LEADER_LENGTH, getAttribute(Constants.PR_LEADER_LENGTH));
		att.put(Constants.PR_LEADER_PATTERN,
				getAttribute(Constants.PR_LEADER_PATTERN));
		att.put(Constants.PR_BASELINE_SHIFT,
				getAttribute(Constants.PR_BASELINE_SHIFT));
		return new Attributes(att);
	}
	@Override
	public ListIterator getChildNodes()
	{
		if(isdoctablecontents)
		{
			return super.getChildNodes();
		}
		else
		{
			return SystemManager.getCurruentDocument().getTableContents().getChildNodes();
		} 
	}
}
