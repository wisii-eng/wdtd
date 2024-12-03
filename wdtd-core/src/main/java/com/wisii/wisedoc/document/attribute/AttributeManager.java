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
 * @AttributeManager.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.TextInline;

/**
 * 类功能描述：属性管理器
 * 
 * 作者：zhangqiang 创建日期：2008-8-12
 */
public class AttributeManager {
	 private static AttributeContainer DEFAULTCONTAINER = new
	 DefaultAttributeContainer();
	//	
	private static Map<Class, AttributeContainer> CONTAINERS = new HashMap<Class, AttributeContainer>();
	static {
		CONTAINERS.put(Block.class, new BlockAttribteContainer());
		CONTAINERS.put(Table.class, new TableAttributeContainer());
		CONTAINERS.put(TableRow.class, new TableRowAttributeContainer());
		CONTAINERS.put(TableCell.class, new TableCellAttributeContainer());
		CONTAINERS.put(Inline.class, new InlineAttributeContainer());
		CONTAINERS.put(TextInline.class, new InlineAttributeContainer());
		CONTAINERS.put(ImageInline.class, new InlineAttributeContainer());
		CONTAINERS.put(PageSequence.class, new PageSequenceAttributeContainer());
		CONTAINERS.put(Object.class, DEFAULTCONTAINER);
	}

	/**
	 * 
	 * 获得指定class的属性容器
	 * 
	 * @param key
	 *            ：指定的class
	 * @return
	 * @exception
	 */
	public static AttributeContainer getAttributeContainer(Class key) {
		AttributeContainer container = CONTAINERS.get(key);
		if(container == null)
		{
			container = DEFAULTCONTAINER;
		}
		return container;
	}

	/**
	 * 
	 * 获得指定对象的属性容器类
	 * 
	 * @param object
	 *            ：指定对象
	 * @return
	 * @exception
	 */
	public static AttributeContainer getAttributeContainer(Object object) {
		return getAttributeContainer(object.getClass());
	}

	public static Map<Class, AttributeContainer> getAttributeContainers() {
		Map<Class, AttributeContainer> map = new HashMap<Class, AttributeContainer>(
				CONTAINERS);
		return map;
	}
}
