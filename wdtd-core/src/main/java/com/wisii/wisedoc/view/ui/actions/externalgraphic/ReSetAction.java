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
 * @ReSetAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.externalgraphic;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 类功能描述：重设图片属性，即清空图片属性
 *
 * 作者：zhangqiang
 * 创建日期：2008-12-23
 */
public class ReSetAction extends Actions
{

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent)
	 */
	public void doAction(ActionEvent e)
	{
		Map<Integer,Object> properties = new HashMap<Integer, Object>();
		properties.put(Constants.PR_CONTENT_HEIGHT, null);
		properties.put(Constants.PR_APHLA, null);
		properties.put(Constants.PR_CONTENT_WIDTH, null);
		properties.put(Constants.PR_SRC_TYPE, null);
		properties.put(Constants.PR_GRAPHIC_LAYER, null);
		properties.put(Constants.PR_SCALING, null);
		properties.put(Constants.PR_BORDER_BEFORE_WIDTH, null);
		properties.put(Constants.PR_BORDER_BEFORE_STYLE, null);
		properties.put(Constants.PR_BORDER_BEFORE_COLOR, null);
		properties.put(Constants.PR_BORDER_AFTER_WIDTH, null);
		properties.put(Constants.PR_BORDER_AFTER_STYLE, null);
		properties.put(Constants.PR_BORDER_AFTER_COLOR, null);
		properties.put(Constants.PR_BORDER_START_WIDTH, null);
		properties.put(Constants.PR_BORDER_START_STYLE, null);
		properties.put(Constants.PR_BORDER_START_COLOR, null);
		properties.put(Constants.PR_BORDER_END_WIDTH, null);
		properties.put(Constants.PR_BORDER_END_STYLE, null);
		properties.put(Constants.PR_BORDER_END_COLOR, null);
		properties.put(Constants.PR_BACKGROUND_COLOR, null);
		properties.put(Constants.PR_BACKGROUNDGRAPHIC_LAYER, null);
		properties.put(Constants.PR_BACKGROUND_IMAGE, null);
		properties.put(Constants.PR_BACKGROUND_REPEAT, null);
		properties.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, null);
		properties.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, null);
        setFOProperties(properties);
	}

}
