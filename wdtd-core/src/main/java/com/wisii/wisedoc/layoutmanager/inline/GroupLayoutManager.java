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
 * @GroupLayoutManager.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.layoutmanager.inline;

import com.wisii.wisedoc.area.inline.GroupInlineArea;
import com.wisii.wisedoc.area.inline.InlineArea;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.layoutmanager.LayoutContext;
import com.wisii.wisedoc.layoutmanager.PositionIterator;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-3-11
 */
public class GroupLayoutManager extends LeafNodeLayoutManager {
	private boolean isstart = Boolean.FALSE;
	private Group group = null;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public GroupLayoutManager(CellElement node, boolean isstart) {
		super(node);
		this.group = (Group) node;
		this.isstart = isstart;
	}

	/** @see com.wisii.fov.layoutmgr.inline.LeafNodeLayoutManager */
	@Override
	public InlineArea get(LayoutContext context) {
		InlineArea area = new GroupInlineArea(isstart);
		return area;
	}

	protected void addId() {
		getPSLM().addIDToPage(group.getId());
	}

	/** 
	 * @see com.wisii.wisedoc.layoutmanager.inline.LeafNodeLayoutManager#addAreas(com.wisii.wisedoc.layoutmanager.PositionIterator, com.wisii.wisedoc.layoutmanager.LayoutContext)
	 */
	@Override
	public void addAreas(PositionIterator posIter, LayoutContext context) {
		InlineArea area = getEffectiveArea();
		super.addAreas(posIter, context);
		 parentLM.addChildArea(area);
	}
}
